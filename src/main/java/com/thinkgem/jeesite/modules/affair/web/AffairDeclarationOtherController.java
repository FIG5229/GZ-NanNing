/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.UploadController;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairDeclarationOther;
import com.thinkgem.jeesite.modules.affair.service.AffairDeclarationOtherService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 其他申报Controller
 * @author daniel.liu
 * @version 2020-06-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairDeclarationOther")
public class AffairDeclarationOtherController extends BaseController {

	@Autowired
	private AffairDeclarationOtherService affairDeclarationOtherService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private DictDao dictDao;
	
	@ModelAttribute
	public AffairDeclarationOther get(@RequestParam(required=false) String id) {
		AffairDeclarationOther entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairDeclarationOtherService.get(id);
		}
		if (entity == null){
			entity = new AffairDeclarationOther();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairDeclarationOther:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairDeclarationOther affairDeclarationOther, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairDeclarationOther> page = affairDeclarationOtherService.findPage(new Page<AffairDeclarationOther>(request, response), affairDeclarationOther); 
		model.addAttribute("page", page);
		return "modules/affair/affairDeclarationOtherList";
	}

	@RequiresPermissions("affair:affairDeclarationOther:view")
	@RequestMapping(value = "form")
	public String form(AffairDeclarationOther affairDeclarationOther, Model model) {
		model.addAttribute("affairDeclarationOther", affairDeclarationOther);
		return "modules/affair/affairDeclarationOtherForm";
	}
	@RequiresPermissions("affair:affairDeclarationOther:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairDeclarationOther affairDeclarationOther, Model model) {
		model.addAttribute("affairDeclarationOther", affairDeclarationOther);
		if(affairDeclarationOther.getFilePath() != null && affairDeclarationOther.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairDeclarationOther.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairDeclarationOtherFormDetail";
	}

	@RequiresPermissions("affair:affairDeclarationOther:edit")
	@RequestMapping(value = "save")
	public String save(AffairDeclarationOther affairDeclarationOther, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairDeclarationOther)){
			return form(affairDeclarationOther, model);
		}
		//审核状态为空时 添加默认值1 未审核
		if (StringUtils.isEmpty(affairDeclarationOther.getCheckType())){
			affairDeclarationOther.setCheckType("1");
		}
		affairDeclarationOtherService.save(affairDeclarationOther);
		addMessage(redirectAttributes, "保存其他申报成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairDeclarationOtherForm";
	}
	
	@RequiresPermissions("affair:affairDeclarationOther:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairDeclarationOther affairDeclarationOther, RedirectAttributes redirectAttributes) {
		affairDeclarationOtherService.delete(affairDeclarationOther);
		addMessage(redirectAttributes, "删除其他申报成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairDeclarationOther/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairDeclarationOther:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairDeclarationOtherService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairDeclarationOther:manage")
	@RequestMapping(value = {"examineView"})
	public String examineView() {
		return "modules/affair/affairDifficultOtherCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairDeclarationOther:manage")
	@RequestMapping(value = "examine")
	public Result examine(AffairDeclarationOther affairDeclarationOther, HttpServletRequest request) {
		if (StringUtils.isNotBlank(affairDeclarationOther.getCheckId())){
			affairDeclarationOther.setCheckMan(dictDao.findLabelByValue(affairDeclarationOther.getCheckId()));
		}
		affairDeclarationOtherService.save(affairDeclarationOther);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

	@RequiresPermissions("affair:affairDeclarationOther:edit")
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
		List <AffairDeclarationOther> list = affairDeclarationOtherService.findByIds(userList);
		for (AffairDeclarationOther affairDeclarationOther: list){
			affairDeclarationOther.setCheckType("2");
			affairDeclarationOther.setCheckMan(CheckMan);
			affairDeclarationOther.setCheckId(CheckId);
			affairDeclarationOther.setSubmitId(user.getId());
			affairDeclarationOther.setSubmitMan(user.getName());
			affairDeclarationOtherService.save(affairDeclarationOther);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairDeclarationOther/list";
	}
}