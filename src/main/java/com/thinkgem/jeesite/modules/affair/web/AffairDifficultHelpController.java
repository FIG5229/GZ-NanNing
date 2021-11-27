/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairDifficultHelp;
import com.thinkgem.jeesite.modules.affair.service.AffairDifficultHelpService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 困难帮扶申报Controller
 * @author daniel.liu
 * @version 2020-06-03
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairDifficultHelp")
public class AffairDifficultHelpController extends BaseController {

	@Autowired
	private AffairDifficultHelpService affairDifficultHelpService;

	@Autowired
	private DictDao dictDao;
	
	@ModelAttribute
	public AffairDifficultHelp get(@RequestParam(required=false) String id) {
		AffairDifficultHelp entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairDifficultHelpService.get(id);
		}
		if (entity == null){
			entity = new AffairDifficultHelp();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairDifficultHelp:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairDifficultHelp affairDifficultHelp, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairDifficultHelp> page = affairDifficultHelpService.findPage(new Page<AffairDifficultHelp>(request, response), affairDifficultHelp); 
		model.addAttribute("page", page);
		return "modules/affair/affairDifficultHelpList";
	}

	@RequiresPermissions("affair:affairDifficultHelp:view")
	@RequestMapping(value = "form")
	public String form(AffairDifficultHelp affairDifficultHelp, Model model) {
		model.addAttribute("affairDifficultHelp", affairDifficultHelp);
		return "modules/affair/affairDifficultHelpForm";
	}

	@RequiresPermissions("affair:affairDifficultHelp:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairDifficultHelp affairDifficultHelp, Model model) {
		model.addAttribute("affairDeclarationOther", affairDifficultHelp);
		return "modules/affair/affairDifficultHelpFormDetail";
	}

	@RequiresPermissions("affair:affairDifficultHelp:edit")
	@RequestMapping(value = "save")
	public String save(AffairDifficultHelp affairDifficultHelp, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairDifficultHelp)){
			return form(affairDifficultHelp, model);
		}

		//审核状态为空时 添加默认值1 未审核
		if (StringUtils.isEmpty(affairDifficultHelp.getCheckType())){
			affairDifficultHelp.setCheckType("1");
		}
		affairDifficultHelpService.save(affairDifficultHelp);
		addMessage(redirectAttributes, "保存困难帮扶申报成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairDifficultHelpForm";
	}
	
	@RequiresPermissions("affair:affairDifficultHelp:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairDifficultHelp affairDifficultHelp, RedirectAttributes redirectAttributes) {
		affairDifficultHelpService.delete(affairDifficultHelp);
		addMessage(redirectAttributes, "删除困难帮扶申报成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairDifficultHelp/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairDifficultHelp:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairDifficultHelpService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairDifficultHelp:manage")
	@RequestMapping(value = {"examineView"})
	public String examineView() {
		return "modules/affair/affairDifficultHelpCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairDifficultHelp:manage")
	@RequestMapping(value = "examine")
	public Result examine(AffairDifficultHelp affairDifficultHelp, HttpServletRequest request) {
		if (StringUtils.isNotBlank(affairDifficultHelp.getCheckId())){
			affairDifficultHelp.setCheckMan(dictDao.findLabelByValue(affairDifficultHelp.getCheckId()));
		}
		affairDifficultHelpService.save(affairDifficultHelp);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

	@RequiresPermissions("affair:affairDifficultHelp:edit")
	@RequestMapping(value = {"submitByIds"})
	public String submitByIds(HttpServletRequest request) {
		User user = UserUtils.getUser();
		//接受前台传过来的ids
		String idsStr = request.getParameter("ids");
		//字符串转数组
		String[] idsArray = idsStr.split(",");
		//数组转集合
		List<String> userList = new ArrayList<String>();
		String CheckId = request.getParameter("checkId");
		String CheckMan = dictDao.findLabelByValue(CheckId);
		Collections.addAll(userList,idsArray);
		/*String companyId = UserUtils.getUser().getId();*/
		List <AffairDifficultHelp> list = affairDifficultHelpService.findByIds(userList);
		for (AffairDifficultHelp affairTnActivityRecord: list){
			affairTnActivityRecord.setCheckType("2");
			affairTnActivityRecord.setCheckMan(CheckMan);
			affairTnActivityRecord.setCheckId(CheckId);
			affairTnActivityRecord.setSubmitId(user.getId());
			affairTnActivityRecord.setSubmitMan(user.getName());
			affairDifficultHelpService.save(affairTnActivityRecord);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairDifficultHelp/list";
	}
}