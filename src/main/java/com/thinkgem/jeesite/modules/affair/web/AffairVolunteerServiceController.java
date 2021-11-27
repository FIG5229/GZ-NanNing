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
import com.thinkgem.jeesite.modules.affair.entity.AffairVolunteerService;
import com.thinkgem.jeesite.modules.affair.service.AffairVolunteerServiceService;
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
 * 志愿服务活动Controller
 * @author eav.liu
 * @version 2019-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairVolunteerService")
public class AffairVolunteerServiceController extends BaseController {

	@Autowired
	private AffairVolunteerServiceService affairVolunteerServiceService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairVolunteerService get(@RequestParam(required=false) String id) {
		AffairVolunteerService entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairVolunteerServiceService.get(id);
		}
		if (entity == null){
			entity = new AffairVolunteerService();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairVolunteerService:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairVolunteerService affairVolunteerService, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairVolunteerService.getTreeId());
		Page<AffairVolunteerService> page = affairVolunteerServiceService.findPage(new Page<AffairVolunteerService>(request, response), affairVolunteerService); 
		model.addAttribute("page", page);
		return "modules/affair/affairVolunteerServiceList";
	}

	@RequiresPermissions("affair:affairVolunteerService:view")
	@RequestMapping(value = "form")
	public String form(AffairVolunteerService affairVolunteerService, Model model) {
		model.addAttribute("affairVolunteerService", affairVolunteerService);
		return "modules/affair/affairVolunteerServiceForm";
	}

	@RequiresPermissions("affair:affairVolunteerService:edit")
	@RequestMapping(value = "save")
	public String save(AffairVolunteerService affairVolunteerService, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairVolunteerService)){
			return form(affairVolunteerService, model);
		}
		/*审核状态为空时 默认设置为 未上报*/
		if (StringUtils.isBlank(affairVolunteerService.getExamineStatus())){
			affairVolunteerService.setExamineStatus("1");
		}
		affairVolunteerServiceService.save(affairVolunteerService);
		addMessage(redirectAttributes, "保存志愿服务活动成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairVolunteerServiceForm";
	}
	
	@RequiresPermissions("affair:affairVolunteerService:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairVolunteerService affairVolunteerService, RedirectAttributes redirectAttributes) {
		affairVolunteerServiceService.delete(affairVolunteerService);
		addMessage(redirectAttributes, "删除志愿服务活动成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairVolunteerService/?repage&treeId="+affairVolunteerService.getTreeId();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairVolunteerService:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairVolunteerServiceService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 详情
	 * @param affairVolunteerService
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairVolunteerService:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairVolunteerService affairVolunteerService, Model model) {
		model.addAttribute("affairVolunteerService", affairVolunteerService);
		if(affairVolunteerService.getMaterialPath1() != null && affairVolunteerService.getMaterialPath1().length() > 0){
			List<Map<String, String>> filePathList1 = uploadController.filePathHandle(affairVolunteerService.getMaterialPath1());
			model.addAttribute("filePathList1", filePathList1);
		}
		if(affairVolunteerService.getMaterialPath2() != null && affairVolunteerService.getMaterialPath2().length() > 0){
			List<Map<String, String>> filePathList2 = uploadController.filePathHandle(affairVolunteerService.getMaterialPath2());
			model.addAttribute("filePathList2", filePathList2);
		}
		return "modules/affair/affairVolunteerServiceFormDetail";
	}

	/**
	 * 审核页面
	 * @return
	 */
	@RequestMapping("examineView")
	public String examineView(){
		return "modules/affair/affairVolunteerServiceExamineDialog";
	}

	/*审核*/
	@RequiresPermissions("affair:affairVolunteerService:edit")
	@RequestMapping(value = "examine")
	public String examine(AffairVolunteerService affairVolunteerService, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairVolunteerService)){
			return form(affairVolunteerService, model);
		}
		affairVolunteerServiceService.save(affairVolunteerService);
		addMessage(redirectAttributes, "审核志愿服务成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairVolunteerServiceForm";
	}

	@RequiresPermissions("affair:affairVolunteerService:edit")
	@RequestMapping(value = "report")
	public String report(AffairVolunteerService affairVolunteerService, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairVolunteerService)){
			return form(affairVolunteerService, model);
		}
		affairVolunteerService.setExamineStatus("2");
		affairVolunteerServiceService.save(affairVolunteerService);
		addMessage(redirectAttributes, "上报志愿服务成功");
		request.setAttribute("saveResult","sucess");
		return "redirect:"+Global.getAdminPath()+"/affair/affairVolunteerService/list?repage&treeId="+affairVolunteerService.getTreeId();
	}
}