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
import com.thinkgem.jeesite.modules.affair.entity.AffairElection;
import com.thinkgem.jeesite.modules.affair.service.AffairElectionService;
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
 * 党组织换届选举Controller
 * @author eav.liu
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairElection")
public class AffairElectionController extends BaseController {

	@Autowired
	private AffairElectionService affairElectionService;

	@Autowired
	private UploadController uploadController;

	@ModelAttribute
	public AffairElection get(@RequestParam(required=false) String id) {
		AffairElection entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairElectionService.get(id);
		}
		if (entity == null){
			entity = new AffairElection();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairElection:view")
	@RequestMapping(value = {""})
	public String index() {
		return "modules/affair/affairElectionIndex";
	}

	/**
	 * 需求：非审核人员可以看到自己的信息和其他人已经提交的信息，不能看到其他人未审核的信息
	 * @param affairElection
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairElection:view")
	@RequestMapping(value = {"list"})
	public String list(AffairElection affairElection, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairElection.getTreeId());
		model.addAttribute("parentId", request.getParameter("parentId"));
		affairElection.setHasAuth(false);
		Page<AffairElection> page = affairElectionService.findPage(new Page<AffairElection>(request, response), affairElection);
		model.addAttribute("page", page);
		return "modules/affair/affairElectionList";
	}

	@RequiresPermissions("affair:affairElection:view")
	@RequestMapping(value = "form")
	public String form(AffairElection affairElection, Model model) {
		model.addAttribute("affairElection", affairElection);
		return "modules/affair/affairElectionForm";
	}

	@RequiresPermissions("affair:affairElection:edit")
	@RequestMapping(value = "save")
	public String save(AffairElection affairElection, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairElection)){
			return form(affairElection, model);
		}
		affairElectionService.save(affairElection);
		addMessage(redirectAttributes, "保存党组织换届选举成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairElectionForm";
	}
	
	@RequiresPermissions("affair:affairElection:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairElection affairElection, RedirectAttributes redirectAttributes) {
		affairElectionService.delete(affairElection);
		addMessage(redirectAttributes, "删除党组织换届选举成功");
		return "redirect:" + Global.getAdminPath() + "/affair/affairElection/list?repage&treeId="+affairElection.getTreeId();
	}

	/**
	 * 审核人员管理页面
	 * @param affairElection
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
    @RequiresPermissions("affair:affairElection:manage")
    @RequestMapping(value = {"manageList"})
    public String manageList(AffairElection affairElection, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairElection.getTreeId());
		affairElection.setHasAuth(true);
        Page<AffairElection> page = affairElectionService.findPage(new Page<AffairElection>(request, response), affairElection);
        model.addAttribute("page", page);
        return "modules/affair/affairElectionManage";
    }

   /* @RequiresPermissions("affair:affairElection:manage")
    @RequestMapping(value = {"manageInfo"})
    public String manageInfo(AffairElection affairElection, Model model) {
        model.addAttribute("affairElection", affairElection);
        return "modules/affair/affairElectionManageInfo";
    }*/

	@RequiresPermissions("affair:affairElection:manage")
	@RequestMapping(value = {"dialog"})
	public String dialog(AffairElection affairElection, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/affair/affairElectionDialog";
	}

	@RequiresPermissions("affair:affairElection:manage")
	@RequestMapping(value = "shenHe")
	public String shenHe(AffairElection affairElection, RedirectAttributes redirectAttributes) {
		affairElectionService.shenHe(affairElection);
		addMessage(redirectAttributes, "审核党组织换届选举成功");
		return "modules/affair/affairElectionDialog";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairElection:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairElectionService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 批量提交
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairElection:edit")
	@RequestMapping(value = {"submitByIds"})
	public Result submitByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairElectionService.submitByIds(ids);
			result.setSuccess(true);
			result.setMessage("提交成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要提交的内容");
		}
		return result;
	}

	/**
	 * form表单详情
	 * @param affairElection
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairElection:view")
	@RequestMapping(value = {"formDetail"})
	public String formDetail(AffairElection affairElection, Model model) {
		model.addAttribute("affairElection", affairElection);
		if(affairElection.getFilePath() != null && affairElection.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairElection.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairElectionFormDetail";
	}


	@RequestMapping(value = {"electionDetail"})
	public String electionDetail(AffairElection affairElection, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairElection> page = affairElectionService.findElectionDetailPage(new Page<AffairElection>(request, response), affairElection);
		model.addAttribute("page", page);
		return "modules/charts/chartElectionList";
	}

}