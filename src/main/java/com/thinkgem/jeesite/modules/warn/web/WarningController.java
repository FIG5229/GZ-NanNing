/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.warn.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.warn.entity.WarnReceive;
import com.thinkgem.jeesite.modules.warn.entity.Warning;
import com.thinkgem.jeesite.modules.warn.service.WarningService;
import com.thinkgem.jeesite.modules.warn.service.WarningTask;
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
import java.util.List;
import java.util.Map;

/**
 * 预警信息Controller
 * @author eav.liu
 * @version 2019-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/warn/warning")
public class WarningController extends BaseController {

	@Autowired
	private WarningService warningService;
	
	@ModelAttribute
	public Warning get(@RequestParam(required=false) String id) {
		Warning entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = warningService.get(id);
		}
		if (entity == null){
			entity = new Warning();
		}
		return entity;
	}
	
	@RequiresPermissions("warn:warning:view")
	@RequestMapping(value = {"list", ""})
	public String list(Warning warning, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Warning> page = warningService.findPage(new Page<Warning>(request, response), warning); 
		model.addAttribute("page", page);
		return "modules/warn/warningList";
	}

	@RequiresPermissions("warn:warning:view")
	@RequestMapping(value = "form")
	public String form(Warning warning, Model model) {
		model.addAttribute("warning", warning);
		return "modules/warn/warningForm";
	}
	@RequiresPermissions("warn:warning:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(Warning warning, Model model) {
		model.addAttribute("warning", warning);
		return "modules/warn/warningFormDetail";
	}

	@RequiresPermissions("warn:warning:edit")
	@RequestMapping(value = "save")
	public String save(Warning warning, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, warning)){
			return form(warning, model);
		}
		warningService.save(warning);
		addMessage(redirectAttributes, "保存预警信息成功");
		return "redirect:"+Global.getAdminPath()+"/warn/warning/?repage";
	}
	
	@RequiresPermissions("warn:warning:edit")
	@RequestMapping(value = "delete")
	public String delete(Warning warning, RedirectAttributes redirectAttributes) {
		warningService.delete(warning);
		addMessage(redirectAttributes, "删除预警信息成功");
		return "redirect:"+Global.getAdminPath()+"/warn/warning/?repage";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("warn:warning:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			warningService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 获得预警通知的数量和对应的id
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"getWarningNum"})
	public Result getWarningNum(Model model) {
		Result result = new Result();
		Map<String, Object> map = warningService.findNumByUserId(UserUtils.getUser().getId());
		model.addAttribute("num", map.get("num").toString());
		if(map.get("idStr") != null){
			model.addAttribute("idStr", map.get("idStr").toString());
		}
		result.setSuccess(true);
		result.setResult(map);
		return result;
	}

	/**
	 * 传回预警通知的数量和对应的id
	 * @param num
	 * @param idStr
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"warningMessage"})
	public String warningMessage(Integer num, String idStr, Model model) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if(num>0){
						boolean b = warningService.saveHistory(idStr);
					}
				}catch (Exception e){
					e.printStackTrace();
					System.out.println("预警信息历史记录存储失败");
				}
			}
		}).start();


		model.addAttribute("num", num);
		model.addAttribute("idStr", idStr);
		return "modules/warn/warningMessage";
	}

	/**
	 * 查看预警信息详情
	 * @param idStr
	 * @param warning
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"handel"})
	public String warningHandel(String idStr,Warning warning, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Warning> page = warningService.showWarnInfoByIds(idStr, new Page<Warning>(request, response), warning);
		model.addAttribute("page", page);
		model.addAttribute("idStr", idStr);
		return "modules/warn/warningMessageList";
	}


	/**
	 预警通知当天不再提醒
	 通过预警id ：warn_id
	 * @param warnId
	 * @param model
	 * @param idStr
	 * @return
	 * */
	@RequestMapping(value = {"noRemindById"})
	@ResponseBody
	public Result noRemindById(String warnId,Model model,String idStr){
		Result result = new Result();
		try {
			String userId = UserUtils.getUser().getId();
			//idStr = warningService.updateNoRemindTimeByWarnId(warnId,userId,idStr);
			idStr = warningService.handelByWarnId(warnId,userId,idStr,"noRemind");
			result.setSuccess(true);
			result.setMessage("提交成功");
            result.setResult(idStr);
		}catch (Exception e){
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("提交失败,请再次尝试");
		}
		return result;
	}

	/**
	 处理预警，将处理状态调整为1
	 下次重复提醒时间字段设置为null
	 通过预警id ：warn_id
	 * @param warnId
	 * @param model
	 * @param idStr
	 * @return
	 * */
	@RequestMapping(value = {"handelByWarnId"})
	@ResponseBody
	public Result handelByWarnId(String warnId,Model model,String idStr){
		Result result = new Result();
		try {
		String userId = UserUtils.getUser().getId();
		 	idStr = warningService.handelByWarnId(warnId,userId,idStr,"isHandel");
			result.setSuccess(true);
			result.setMessage("处理成功");
			result.setResult(idStr);
		}catch (Exception e){
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("处理失败,请再次尝试");
		}
		//return "redirect:"+Global.getAdminPath()+"/warn/warning/?repage";
		return result;
	}


}