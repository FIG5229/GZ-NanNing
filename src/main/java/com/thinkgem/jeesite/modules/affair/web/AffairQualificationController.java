/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairQualification;
import com.thinkgem.jeesite.modules.affair.service.AffairQualificationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 警务技术任职资格表Controller
 * @author mason.xv
 * @version 2019-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairQualification")
public class AffairQualificationController extends BaseController {

	@Autowired
	private AffairQualificationService affairQualificationService;
	
	@ModelAttribute
	public AffairQualification get(@RequestParam(required=false) String id) {
		AffairQualification entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairQualificationService.get(id);
		}
		if (entity == null){
			entity = new AffairQualification();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairQualification:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairQualification affairQualification, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairQualification> page = affairQualificationService.findPage(new Page<AffairQualification>(request, response), affairQualification); 
		model.addAttribute("page", page);
		return "modules/affair/affairQualificationList";
	}

	@RequiresPermissions("affair:affairQualification:view")
	@RequestMapping(value = "form")
	public String form(AffairQualification affairQualification, Model model) {
		model.addAttribute("affairQualification", affairQualification);
		return "modules/affair/affairQualificationForm";
	}

	@RequiresPermissions("affair:affairQualification:edit")
	@RequestMapping(value = "save")
	public String save(AffairQualification affairQualification, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairQualification)){
			return form(affairQualification, model);
		}
		affairQualificationService.save(affairQualification);
		addMessage(redirectAttributes, "保存警务技术任职资格表成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairQualificationForm";
	}
	
	@RequiresPermissions("affair:affairQualification:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairQualification affairQualification, RedirectAttributes redirectAttributes) {
		affairQualificationService.delete(affairQualification);
		addMessage(redirectAttributes, "删除警务技术任职资格表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairQualification/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairQualification:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairQualificationService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:affairQualification:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairQualification affairQualification, Model model) {
		model.addAttribute("affairQualification", affairQualification);
		return "modules/affair/affairQualificationFormDetail";
	}
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairQualification> list = ei.getDataList(AffairQualification.class);
			for (AffairQualification affairQualification : list){
				try{
					BeanValidators.validateWithException(validator, affairQualification);
					affairQualificationService.save(affairQualification);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairQualification.getName()+"(身份证号码:"+affairQualification.getIdNumber()+")"+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view";
	}
}