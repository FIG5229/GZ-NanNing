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
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyDayActivities;
import com.thinkgem.jeesite.modules.affair.service.AffairPartyDayActivitiesService;
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
 * 党日活动Controller
 * @author cecil.li
 * @version 2020-04-12
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPartyDayActivities")
public class AffairPartyDayActivitiesController extends BaseController {

	@Autowired
	private AffairPartyDayActivitiesService affairPartyDayActivitiesService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairPartyDayActivities get(@RequestParam(required=false) String id) {
		AffairPartyDayActivities entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPartyDayActivitiesService.get(id);
		}
		if (entity == null){
			entity = new AffairPartyDayActivities();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairPartyDayActivities:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairPartyDayActivities affairPartyDayActivities, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairPartyDayActivities.getTreeId());
		Page<AffairPartyDayActivities> page = affairPartyDayActivitiesService.findPage(new Page<AffairPartyDayActivities>(request, response), affairPartyDayActivities);
		model.addAttribute("page", page);
		return "modules/affair/affairPartyDayActivitiesList";
	}

	@RequiresPermissions("affair:affairPartyDayActivities:view")
	@RequestMapping(value = "form")
	public String form(AffairPartyDayActivities affairPartyDayActivities, Model model) {
		model.addAttribute("affairPartyDayActivities", affairPartyDayActivities);
		return "modules/affair/affairPartyDayActivitiesForm";
	}

	@RequiresPermissions("affair:affairPartyDayActivities:edit")
	@RequestMapping(value = "save")
	public String save(AffairPartyDayActivities affairPartyDayActivities, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairPartyDayActivities)){
			return form(affairPartyDayActivities, model);
		}
		/*审核状态为空时 默认设置为 未上报*/
		if (StringUtils.isBlank(affairPartyDayActivities.getExamineStatus())){
			affairPartyDayActivities.setExamineStatus("1");
		}
		affairPartyDayActivitiesService.save(affairPartyDayActivities);
		addMessage(redirectAttributes, "保存党日活动成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairPartyDayActivitiesForm";
	}
	
	@RequiresPermissions("affair:affairPartyDayActivities:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPartyDayActivities affairPartyDayActivities, RedirectAttributes redirectAttributes) {
		affairPartyDayActivitiesService.delete(affairPartyDayActivities);
		addMessage(redirectAttributes, "删除党日活动成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPartyDayActivities/?repage&treeId="+affairPartyDayActivities.getTreeId();
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairPartyDayActivities:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPartyDayActivitiesService.deleteByIds(ids);
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
	 * @param affairPartyDayActivities
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPartyDayActivities:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairPartyDayActivities affairPartyDayActivities, Model model) {
		model.addAttribute("affairPartyDayActivities", affairPartyDayActivities);
		if(affairPartyDayActivities.getMaterialPath1() != null && affairPartyDayActivities.getMaterialPath1().length() > 0){
			List<Map<String, String>> filePathList1 = uploadController.filePathHandle(affairPartyDayActivities.getMaterialPath1());
			model.addAttribute("filePathList1", filePathList1);
		}
		if(affairPartyDayActivities.getMaterialPath2() != null && affairPartyDayActivities.getMaterialPath2().length() > 0){
			List<Map<String, String>> filePathList2 = uploadController.filePathHandle(affairPartyDayActivities.getMaterialPath2());
			model.addAttribute("filePathList2", filePathList2);
		}
		return "modules/affair/affairPartyDayActivitiesFormDetail";
	}

	/**
	 * 审核页面
	 * @return
	 */
	@RequestMapping("examineView")
	public String examineView(){
		return "modules/affair/affairPartyDayActivitiesExamineDialog";
	}

	/*审核*/
	@RequiresPermissions("affair:affairPartyDayActivities:edit")
	@RequestMapping(value = "examine")
	public String examine(AffairPartyDayActivities affairPartyDayActivities, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairPartyDayActivities)){
			return form(affairPartyDayActivities, model);
		}
		affairPartyDayActivitiesService.save(affairPartyDayActivities);
		addMessage(redirectAttributes, "审核月度学习计划成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairPartyDayActivitiesForm";
	}

	@RequiresPermissions("affair:affairPartyDayActivities:edit")
	@RequestMapping(value = "report")
	public String report(AffairPartyDayActivities affairPartyDayActivities, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairPartyDayActivities)){
			return form(affairPartyDayActivities, model);
		}
		affairPartyDayActivities.setExamineStatus("2");
		affairPartyDayActivitiesService.save(affairPartyDayActivities);
		addMessage(redirectAttributes, "上报月度学习计划成功");
		request.setAttribute("saveResult","sucess");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPartyDayActivities/list?repage&treeId="+affairPartyDayActivities.getTreeId();
	}

}