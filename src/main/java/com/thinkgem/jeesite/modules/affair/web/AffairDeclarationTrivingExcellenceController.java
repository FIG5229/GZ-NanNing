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
import com.thinkgem.jeesite.modules.affair.entity.AffairDeclarationTrivingExcellence;
import com.thinkgem.jeesite.modules.affair.service.AffairDeclarationTrivingExcellenceService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 创先争优申报Controller
 * @author daniel.liu
 * @version 2020-06-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairDeclarationTrivingExcellence")
public class AffairDeclarationTrivingExcellenceController extends BaseController {

	@Autowired
	private AffairDeclarationTrivingExcellenceService affairDeclarationTrivingExcellenceService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private DictDao dictDao;

	@ModelAttribute
	public AffairDeclarationTrivingExcellence get(@RequestParam(required=false) String id) {
		AffairDeclarationTrivingExcellence entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairDeclarationTrivingExcellenceService.get(id);
		}
		if (entity == null){
			entity = new AffairDeclarationTrivingExcellence();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairDeclarationTrivingExcellence:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairDeclarationTrivingExcellence affairDeclarationTrivingExcellence, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairDeclarationTrivingExcellence> page = affairDeclarationTrivingExcellenceService.findPage(new Page<AffairDeclarationTrivingExcellence>(request, response), affairDeclarationTrivingExcellence); 
		model.addAttribute("page", page);
		return "modules/affair/affairDeclarationTrivingExcellenceList";
	}

	@RequiresPermissions("affair:affairDeclarationTrivingExcellence:view")
	@RequestMapping(value = "form")
	public String form(AffairDeclarationTrivingExcellence affairDeclarationTrivingExcellence, Model model) {
		model.addAttribute("affairDeclarationTrivingExcellence", affairDeclarationTrivingExcellence);
		return "modules/affair/affairDeclarationTrivingExcellenceForm";
	}
	@RequiresPermissions("affair:affairDeclarationTrivingExcellence:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairDeclarationTrivingExcellence affairDeclarationTrivingExcellence, Model model) {
		model.addAttribute("affairDeclarationTrivingExcellence", affairDeclarationTrivingExcellence);
		if(affairDeclarationTrivingExcellence.getFilePath() != null && affairDeclarationTrivingExcellence.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairDeclarationTrivingExcellence.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairDeclarationTrivingExcellenceFormDetail";
	}

	@RequiresPermissions("affair:affairDeclarationTrivingExcellence:edit")
	@RequestMapping(value = "save")
	public String save(AffairDeclarationTrivingExcellence affairDeclarationTrivingExcellence, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairDeclarationTrivingExcellence)){
			return form(affairDeclarationTrivingExcellence, model);
		}

		//审核状态为空时 添加默认值1 未审核
		if (StringUtils.isEmpty(affairDeclarationTrivingExcellence.getCheckType())){
			affairDeclarationTrivingExcellence.setCheckType("1");
		}
		affairDeclarationTrivingExcellenceService.save(affairDeclarationTrivingExcellence);
		addMessage(redirectAttributes, "保存创先争优申报成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairDeclarationTrivingExcellenceForm";
	}
	
	@RequiresPermissions("affair:affairDeclarationTrivingExcellence:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairDeclarationTrivingExcellence affairDeclarationTrivingExcellence, RedirectAttributes redirectAttributes) {
		affairDeclarationTrivingExcellenceService.delete(affairDeclarationTrivingExcellence);
		addMessage(redirectAttributes, "删除创先争优申报成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairDeclarationTrivingExcellence/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairDeclarationTrivingExcellence:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairDeclarationTrivingExcellenceService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairDeclarationTrivingExcellence:manage")
	@RequestMapping(value = {"examineView"})
	public String examineView() {
		return "modules/affair/affairDeclarationTrivingCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairDeclarationTrivingExcellence:manage")
	@RequestMapping(value = "examine")
	public Result examine(AffairDeclarationTrivingExcellence excellence, HttpServletRequest request) {
		if (StringUtils.isNotBlank(excellence.getCheckId())){
			excellence.setCheckMan(dictDao.findLabelByValue(excellence.getCheckId()));
		}
		affairDeclarationTrivingExcellenceService.save(excellence);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

	@RequiresPermissions("affair:affairDeclarationTrivingExcellence:edit")
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
		List <AffairDeclarationTrivingExcellence> list = affairDeclarationTrivingExcellenceService.findByIds(userList);
		for (AffairDeclarationTrivingExcellence trivingExcellence: list){
			trivingExcellence.setCheckType("2");
			trivingExcellence.setCheckMan(CheckMan);
			trivingExcellence.setCheckId(CheckId);
			trivingExcellence.setSubmitId(user.getId());
			trivingExcellence.setSubmitMan(user.getName());
			affairDeclarationTrivingExcellenceService.save(trivingExcellence);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairDeclarationTrivingExcellence/list";
	}

}