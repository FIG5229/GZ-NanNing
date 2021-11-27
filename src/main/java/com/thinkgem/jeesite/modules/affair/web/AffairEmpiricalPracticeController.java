/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairEmpiricalPractice;
import com.thinkgem.jeesite.modules.affair.service.AffairEmpiricalPracticeService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 教育整顿经验做法Controller
 * @author cecil.li
 * @version 2019-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairEmpiricalPractice")
public class AffairEmpiricalPracticeController extends BaseController {

	@Autowired
	private AffairEmpiricalPracticeService affairEmpiricalPracticeService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairEmpiricalPractice get(@RequestParam(required=false) String id) {
		AffairEmpiricalPractice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairEmpiricalPracticeService.get(id);
		}
		if (entity == null){
			entity = new AffairEmpiricalPractice();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairEmpiricalPractice:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairEmpiricalPractice affairEmpiricalPractice, HttpServletRequest request, HttpServletResponse response, Model model) {
		if ("1".equals(UserUtils.getUser().getCompany().getId())){
			affairEmpiricalPractice.setQxUnitId(UserUtils.getUser().getCompany().getId());
		}else if ("31".equals(UserUtils.getUser().getOffice().getId()) || "146".equals(UserUtils.getUser().getOffice().getId()) || "266".equals(UserUtils.getUser().getOffice().getId())){
			affairEmpiricalPractice.setQxUnitId("1");
		}else if ("34".equals(UserUtils.getUser().getCompany().getId())){
			affairEmpiricalPractice.setQxUnitId("34");
		}else if ("95".equals(UserUtils.getUser().getCompany().getId())){
			affairEmpiricalPractice.setQxUnitId("95");
		}else if ("156".equals(UserUtils.getUser().getCompany().getId())){
			affairEmpiricalPractice.setQxUnitId("156");
		}else {
			affairEmpiricalPractice.setQxUnitId(UserUtils.getUser().getOffice().getId());
		}
		affairEmpiricalPractice.setCreateBy(UserUtils.getUser());
		String reason = request.getParameter("reason");//2 全部 3 其他年份
		model.addAttribute("reason",reason);
		if("3".equals(reason)){
			affairEmpiricalPractice.setStartDate(null);
			affairEmpiricalPractice.setEndDate(null);
		}else if("2".equals(reason)){
			//全部
			if(affairEmpiricalPractice.getStartDate()==null && affairEmpiricalPractice.getEndDate() == null){
				String year = DateUtils.getYear();//获取当前年
				affairEmpiricalPractice.setOtherYear(year);
			}else{
				affairEmpiricalPractice.setOtherYear(null);
			}
		}else{
			//默认显示当前年度数据
			String year =DateUtils.getYear();//获取当前年
			affairEmpiricalPractice.setOtherYear(year);
			affairEmpiricalPractice.setStartDate(null);
			affairEmpiricalPractice.setEndDate(null);
		}
		Page<AffairEmpiricalPractice> page = affairEmpiricalPracticeService.findPage(new Page<AffairEmpiricalPractice>(request, response), affairEmpiricalPractice);
		model.addAttribute("page", page);
		/*if (affairEmpiricalPractice.getStartDate() != null || affairEmpiricalPractice.getEndDate() != null ){
			Page<AffairEmpiricalPractice> page = affairEmpiricalPracticeService.findPage(new Page<AffairEmpiricalPractice>(request, response), affairEmpiricalPractice);
			model.addAttribute("page", page);
		}else if (affairEmpiricalPractice.getStartDate() == null && affairEmpiricalPractice.getEndDate() == null){
			if ("".equals(affairEmpiricalPractice.getOtherYear()) || affairEmpiricalPractice.getOtherYear() == null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				Date date = new Date();
				String nowDate = sdf.format(date);
				Date yearDate = null;
				try {
					yearDate = sdf.parse(nowDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				affairEmpiricalPractice.setStartYear(yearDate);
				model.addAttribute("startYear", yearDate);
				Page<AffairEmpiricalPractice> page = affairEmpiricalPracticeService.findPage(new Page<AffairEmpiricalPractice>(request, response), affairEmpiricalPractice);
				model.addAttribute("page", page);
			}

		}else if (affairEmpiricalPractice.getOtherYear() != null && !"".equals(affairEmpiricalPractice.getOtherYear())){
			Page<AffairEmpiricalPractice> page = affairEmpiricalPracticeService.findPage(new Page<AffairEmpiricalPractice>(request, response), affairEmpiricalPractice);
			model.addAttribute("page", page);
		}*/

		return "modules/affair/affairEmpiricalPracticeList";
	}

	@RequiresPermissions("affair:affairEmpiricalPractice:add")
	@RequestMapping(value = "form")
	public String form(AffairEmpiricalPractice affairEmpiricalPractice, Model model) {
		model.addAttribute("affairEmpiricalPractice", affairEmpiricalPractice);
		return "modules/affair/affairEmpiricalPracticeForm";
	}

	@RequiresPermissions("affair:affairEmpiricalPractice:edit")
	@RequestMapping(value = "save")
	public String save(AffairEmpiricalPractice affairEmpiricalPractice, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairEmpiricalPractice)){
			return form(affairEmpiricalPractice, model);
		}
		affairEmpiricalPracticeService.save(affairEmpiricalPractice);
		addMessage(redirectAttributes, "保存教育整顿经验做法成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairEmpiricalPracticeForm";
	}
	
	@RequiresPermissions("affair:affairEmpiricalPractice:delete")
	@RequestMapping(value = "delete")
	public String delete(AffairEmpiricalPractice affairEmpiricalPractice, RedirectAttributes redirectAttributes) {
		affairEmpiricalPracticeService.delete(affairEmpiricalPractice);
		addMessage(redirectAttributes, "删除教育整顿经验做法成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairEmpiricalPractice/?repage";
	}

	/**
	 * 详情
	 * @param affairEmpiricalPractice
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairEmpiricalPractice:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairEmpiricalPractice affairEmpiricalPractice, Model model) {
		model.addAttribute("affairEmpiricalPractice", affairEmpiricalPractice);
		if(affairEmpiricalPractice.getAnnex() != null && affairEmpiricalPractice.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairEmpiricalPractice.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairEmpiricalPracticeFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairEmpiricalPractice:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairEmpiricalPracticeService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

}