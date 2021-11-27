/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairFileNotice;
import com.thinkgem.jeesite.modules.affair.entity.AffairFileNoticeSign;
import com.thinkgem.jeesite.modules.affair.service.AffairFileNoticeService;
import com.thinkgem.jeesite.modules.affair.service.AffairFileNoticeSignService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.warn.entity.Warning;
import com.thinkgem.jeesite.modules.warn.service.WarningService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 党建文件通知通报签收Controller
 * @author eav.liu
 * @version 2019-11-01
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairFileNoticeSign")
public class AffairFileNoticeSignController extends BaseController {

	@Autowired
	private AffairFileNoticeSignService affairFileNoticeSignService;

	@Autowired
	private WarningService warningService;

	@Autowired
	private AffairFileNoticeService affairFileNoticeService;

	@Autowired
	private UserDao userDao;

	@ModelAttribute
	public AffairFileNoticeSign get(@RequestParam(required=false) String id) {
		AffairFileNoticeSign entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairFileNoticeSignService.get(id);
		}
		if (entity == null){
			entity = new AffairFileNoticeSign();
		}
		return entity;
	}
	//权限为affair:affairFileNotice:manage，不是affair:affairFileNoticeSign:manage
	//@RequiresPermissions("affair:affairFileNotice:manage")
	@RequestMapping(value = {"list", ""})
	public String list(AffairFileNoticeSign affairFileNoticeSign, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairFileNoticeSign> signList =affairFileNoticeSignService.findSign(affairFileNoticeSign);
		List<AffairFileNoticeSign> notSignList =affairFileNoticeSignService.findNotSign(affairFileNoticeSign);
		model.addAttribute("signList", signList);
		model.addAttribute("notSignList", notSignList);
		model.addAttribute("noticeId",affairFileNoticeSign.getNoticeId());
		return "modules/affair/affairFileNoticeSignList";
	}

	@RequiresPermissions("affair:affairFileNoticeSign:view")
	@RequestMapping(value = "form")
	public String form(AffairFileNoticeSign affairFileNoticeSign, Model model) {
		model.addAttribute("affairFileNoticeSign", affairFileNoticeSign);
		return "modules/affair/affairFileNoticeSignForm";
	}

	@RequiresPermissions("affair:affairFileNoticeSign:edit")
	@RequestMapping(value = "save")
	public String save(AffairFileNoticeSign affairFileNoticeSign, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairFileNoticeSign)){
			return form(affairFileNoticeSign, model);
		}
		affairFileNoticeSignService.save(affairFileNoticeSign);
		addMessage(redirectAttributes, "保存通知通报成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairFileNoticeSign/?repage";
	}
	
	@RequiresPermissions("affair:affairFileNoticeSign:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairFileNoticeSign affairFileNoticeSign, RedirectAttributes redirectAttributes) {
		affairFileNoticeSignService.delete(affairFileNoticeSign);
		addMessage(redirectAttributes, "删除通知通报成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairFileNoticeSign/?repage";
	}

	/**
	 * 催办(权限为affair:affairFileNotice:manage，不是affair:affairFileNoticeSign:manage)
	 * @param affairFileNoticeSign
	 * @param redirectAttributes
	 * @return
	 */
	//@RequiresPermissions("affair:affairFileNotice:manage")
	@RequestMapping(value = "urge")
	public String urge(AffairFileNoticeSign affairFileNoticeSign, RedirectAttributes redirectAttributes) {
		affairFileNoticeSignService.urge(affairFileNoticeSign);
		warn(affairFileNoticeSign,false);
		addMessage(redirectAttributes, "催办成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairFileNoticeSign/?repage&noticeId="+affairFileNoticeSign.getNoticeId();
	}

	@RequestMapping(value = "oneKeyUrge")
	public String oneKeyUrge(AffairFileNoticeSign affairFileNoticeSign, RedirectAttributes redirectAttributes){
		List<AffairFileNoticeSign> notSignList =affairFileNoticeSignService.findNotSign(affairFileNoticeSign);

		for (AffairFileNoticeSign sign:notSignList){
			affairFileNoticeSignService.urge(sign);
			//添加到预警
		}
		warn(affairFileNoticeSign,true);
		addMessage(redirectAttributes, "一键催办成功");

		return "redirect:"+Global.getAdminPath()+"/affair/affairFileNoticeSign/?repage&noticeId="+affairFileNoticeSign.getNoticeId();

	}

	public void warn (AffairFileNoticeSign affairFileNoticeSign,boolean isOneKey){
		AffairFileNotice affairFileNotice = affairFileNoticeService.get(affairFileNoticeSign.getNoticeId());
		//获取通知通报
		//添加到预警
		Warning warning = new Warning();
		warning.setName("党建文件签收");
		//预警接收人
		if (isOneKey){
			//根据通知通报接收单位获取预警接收人
			String receiveName = affairFileNotice.getReceiveDep();
			List<String> nameList = Arrays.asList(receiveName.split(","));
			StringBuffer receiveId = new StringBuffer();
			//根据部门名获取以此部门名为用户名的Id
			for(String name:nameList){
				User user = userDao.getByUserName(name);
				receiveId.append(user.getId()+",");
			}
			warning.setReceivePerName(receiveName);
			warning.setReceivePerId(receiveId.toString());
		}else {
			User user = userDao.getByUserName(affairFileNoticeSign.getUnit());
			if (user!=null){
				warning.setReceivePerName(user.getName());
				warning.setReceivePerId(user.getId());
			}
		}
		warning.setRemind("5");
		warning.setRepeatCycle("4");
		//获取时间设置开始时间
		Date date = new Date();
		//保存后 一分钟开始
//		Date startTime=new Date(date.getTime()+1000*60*1);
		//预警执行时 未执行会进行重复提醒  加重复时间
		warning.setDate(date);
		//有弹窗
		warning.setIsAlert("1");
		//查找通知通报标题 写入预警弹窗内容
		warning.setAlertContent("请签收"+affairFileNotice.getTitle());
		warning.setAlertDegree("1");
		warningService.save(warning);
	}

	/**
	 * 批量签收
	 * @param ids
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairFileNoticeSign:edit")
	@RequestMapping(value = {"sign"})
	public Result sign(@RequestParam("ids[]") List<String> ids, RedirectAttributes redirectAttributes) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairFileNoticeSignService.sign(ids);
			addMessage(redirectAttributes, "签收成功");
			result.setSuccess(true);
			result.setMessage("签收成功");
		}else{
			addMessage(redirectAttributes, "请选择签收内容");
		}
		return result;
	}

	/**
	 * 单条签收
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairFileNoticeSign:edit")
	@RequestMapping(value = {"signOne"})
	public Result signOne(String id) {
		affairFileNoticeSignService.signOne(id);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("签收成功");
		return result;
	}
}