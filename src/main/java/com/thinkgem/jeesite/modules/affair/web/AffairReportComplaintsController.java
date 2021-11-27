/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairReportComplaints;
import com.thinkgem.jeesite.modules.affair.service.AffairReportComplaintsService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 举报投诉Controller
 * @author cecil.li
 * @version 2020-06-16
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairReportComplaints")
public class AffairReportComplaintsController extends BaseController {

	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairReportComplaintsService affairReportComplaintsService;
	
	@ModelAttribute
	public AffairReportComplaints get(@RequestParam(required=false) String id) {
		AffairReportComplaints entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairReportComplaintsService.get(id);
		}
		if (entity == null){
			entity = new AffairReportComplaints();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairReportComplaints:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairReportComplaints affairReportComplaints, HttpServletRequest request, HttpServletResponse response, Model model) {
		if("1".equals(UserUtils.getUser().getCompany().getId())){
			affairReportComplaints.setResponsibleUnitId(UserUtils.getUser().getCompany().getId());
		}else {
			affairReportComplaints.setResponsibleUnitId(UserUtils.getUser().getOffice().getId());
		}
		affairReportComplaints.setCreateBy(UserUtils.getUser());
		affairReportComplaints.setRange(UserUtils.getUser().getId());
		Page<AffairReportComplaints> page = affairReportComplaintsService.findPage(new Page<AffairReportComplaints>(request, response), affairReportComplaints);
		model.addAttribute("page", page);
		return "modules/affair/affairReportComplaintsList";
	}

	@RequiresPermissions("affair:affairReportComplaints:view")
	@RequestMapping(value = "form")
	public String form(AffairReportComplaints affairReportComplaints, Model model) {
		if (affairReportComplaints.getRange() != null && affairReportComplaints.getRange().length() >0){
			String[] problemCategoryArr = affairReportComplaints.getRange().split(",");
			affairReportComplaints.setProblemCategoryArr(problemCategoryArr);
		}
		model.addAttribute("affairReportComplaints", affairReportComplaints);
		return "modules/affair/affairReportComplaintsForm";
	}

	@RequiresPermissions("affair:affairReportComplaints:edit")
	@RequestMapping(value = "save")
	public String save(AffairReportComplaints affairReportComplaints, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairReportComplaints)){
			return form(affairReportComplaints, model);
		}
		if(null != affairReportComplaints.getProblemCategoryArr()){
			affairReportComplaints.setRange(StringUtils.join(affairReportComplaints.getProblemCategoryArr(),","));
		}
		affairReportComplaintsService.save(affairReportComplaints);
		addMessage(redirectAttributes, "保存举报投诉成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairReportComplaintsForm";
	}
	
	@RequiresPermissions("affair:affairReportComplaints:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairReportComplaints affairReportComplaints, RedirectAttributes redirectAttributes) {
		affairReportComplaintsService.delete(affairReportComplaints);
		addMessage(redirectAttributes, "删除举报投诉成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairReportComplaints/?repage";
	}

	/**
	 * 详情
	 * @param affairReportComplaints
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairReportComplaints:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairReportComplaints affairReportComplaints, Model model) {
		model.addAttribute("affairReportComplaints", affairReportComplaints);
		if(affairReportComplaints.getAnnex() != null && affairReportComplaints.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairReportComplaints.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairReportComplaintsFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairRightsProtection:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairReportComplaintsService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

}