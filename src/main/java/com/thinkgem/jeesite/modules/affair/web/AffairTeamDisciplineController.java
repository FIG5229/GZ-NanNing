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
import com.thinkgem.jeesite.modules.affair.entity.AffairTeamDiscipline;
import com.thinkgem.jeesite.modules.affair.service.AffairTeamDisciplineService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
 * 队伍作风纪律整顿Controller
 * @author cecil.li
 * @version 2019-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTeamDiscipline")
public class AffairTeamDisciplineController extends BaseController {

	@Autowired
	private AffairTeamDisciplineService affairTeamDisciplineService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairTeamDiscipline get(@RequestParam(required=false) String id) {
		AffairTeamDiscipline entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTeamDisciplineService.get(id);
		}
		if (entity == null){
			entity = new AffairTeamDiscipline();
		}
		return entity;
	}
	
//	@RequiresPermissions("affair:affairTeamDiscipline:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTeamDiscipline affairTeamDiscipline, HttpServletRequest request, HttpServletResponse response, Model model) {
		affairTeamDiscipline.setHasAuth(false);
		if ("1".equals(UserUtils.getUser().getCompany().getId())){
			affairTeamDiscipline.setQxUnitId(UserUtils.getUser().getCompany().getId());
		}else if ("31".equals(UserUtils.getUser().getOffice().getId()) || "146".equals(UserUtils.getUser().getOffice().getId()) || "266".equals(UserUtils.getUser().getOffice().getId())){
			affairTeamDiscipline.setQxUnitId("1");
		}else if ("34".equals(UserUtils.getUser().getCompany().getId())){
			affairTeamDiscipline.setQxUnitId("34");
		}else if ("95".equals(UserUtils.getUser().getCompany().getId())){
			affairTeamDiscipline.setQxUnitId("95");
		}else if ("156".equals(UserUtils.getUser().getCompany().getId())){
			affairTeamDiscipline.setQxUnitId("156");
		}else {
			affairTeamDiscipline.setQxUnitId(UserUtils.getUser().getOffice().getId());
		}
		affairTeamDiscipline.setCreateBy(UserUtils.getUser());
		String reason = request.getParameter("reason");//2 全部 3 其他年份
		model.addAttribute("reason",reason);
		if("3".equals(reason)){
			affairTeamDiscipline.setStartDate(null);
			affairTeamDiscipline.setEndDate(null);
		}else if("2".equals(reason)){
			//全部
			if(affairTeamDiscipline.getStartDate()==null && affairTeamDiscipline.getEndDate() == null){
				String year =DateUtils.getYear();//获取当前年
				affairTeamDiscipline.setOtherYear(year);
			}else{
				affairTeamDiscipline.setOtherYear(null);
			}
		}else{
			//默认显示当前年度数据
			String year =DateUtils.getYear();//获取当前年
			affairTeamDiscipline.setOtherYear(year);
			affairTeamDiscipline.setStartDate(null);
			affairTeamDiscipline.setEndDate(null);
		}
		Page<AffairTeamDiscipline> page = affairTeamDisciplineService.findPage(new Page<AffairTeamDiscipline>(request, response), affairTeamDiscipline);
		model.addAttribute("page", page);
		/*if (affairTeamDiscipline.getStartDate() != null || affairTeamDiscipline.getEndDate() != null){
			Page<AffairTeamDiscipline> page = affairTeamDisciplineService.findPage(new Page<AffairTeamDiscipline>(request, response), affairTeamDiscipline);
			model.addAttribute("page", page);
		}else if (affairTeamDiscipline.getStartDate() == null && affairTeamDiscipline.getEndDate() == null ){
			if ("".equals(affairTeamDiscipline.getOtherYear()) || affairTeamDiscipline.getOtherYear() == null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				Date date = new Date();
				String nowDate = sdf.format(date);
				Date yearDate = null;
				try {
					yearDate = sdf.parse(nowDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				affairTeamDiscipline.setStartYear(yearDate);
				model.addAttribute("startYear", yearDate);
				Page<AffairTeamDiscipline> page = affairTeamDisciplineService.findPage(new Page<AffairTeamDiscipline>(request, response), affairTeamDiscipline);
				model.addAttribute("page", page);
			}

		}else if (affairTeamDiscipline.getOtherYear() != null && !"".equals(affairTeamDiscipline.getOtherYear())){
			Page<AffairTeamDiscipline> page = affairTeamDisciplineService.findPage(new Page<AffairTeamDiscipline>(request, response), affairTeamDiscipline);
			model.addAttribute("page", page);
		}*/

		return "modules/affair/affairTeamDisciplineList";
	}

	/**
	 * 管理页面
	 * @param affairTeamDiscipline
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("affair:affairFileNotice:manage")
	@RequestMapping(value = {"manageList"})
	public String manageList(AffairTeamDiscipline affairTeamDiscipline, HttpServletRequest request, HttpServletResponse response, Model model) {
		affairTeamDiscipline.setHasAuth(true);
		if ("1".equals(UserUtils.getUser().getCompany().getId())){
			affairTeamDiscipline.setQxUnitId(UserUtils.getUser().getCompany().getId());
		}else if ("31".equals(UserUtils.getUser().getOffice().getId()) || "146".equals(UserUtils.getUser().getOffice().getId()) || "266".equals(UserUtils.getUser().getOffice().getId())){
			affairTeamDiscipline.setQxUnitId("1");
		}else if ("34".equals(UserUtils.getUser().getCompany().getId())){
			affairTeamDiscipline.setQxUnitId("34");
		}else if ("95".equals(UserUtils.getUser().getCompany().getId())){
			affairTeamDiscipline.setQxUnitId("95");
		}else if ("156".equals(UserUtils.getUser().getCompany().getId())){
			affairTeamDiscipline.setQxUnitId("156");
		}else {
			affairTeamDiscipline.setQxUnitId(UserUtils.getUser().getOffice().getId());
		}
		affairTeamDiscipline.setCreateBy(UserUtils.getUser());
		String reason = request.getParameter("reason");//2 全部 3 其他年份
		model.addAttribute("reason",reason);
		if("3".equals(reason)){
			affairTeamDiscipline.setStartDate(null);
			affairTeamDiscipline.setEndDate(null);
		}else if("2".equals(reason)){
			//全部
			if(affairTeamDiscipline.getStartDate()==null && affairTeamDiscipline.getEndDate() == null){
				String year =DateUtils.getYear();//获取当前年
				affairTeamDiscipline.setOtherYear(year);
			}else{
				affairTeamDiscipline.setOtherYear(null);
			}
		}else{
			//默认显示当前年度数据
			String year =DateUtils.getYear();//获取当前年
			affairTeamDiscipline.setOtherYear(year);
			affairTeamDiscipline.setStartDate(null);
			affairTeamDiscipline.setEndDate(null);
		}
		Page<AffairTeamDiscipline> page = affairTeamDisciplineService.findPage(new Page<AffairTeamDiscipline>(request, response), affairTeamDiscipline);
		model.addAttribute("page", page);
		/*if (affairTeamDiscipline.getStartDate() != null || affairTeamDiscipline.getEndDate() != null){
			Page<AffairTeamDiscipline> page = affairTeamDisciplineService.findPage(new Page<AffairTeamDiscipline>(request, response), affairTeamDiscipline);
			model.addAttribute("page", page);
		}else if (affairTeamDiscipline.getStartDate() == null && affairTeamDiscipline.getEndDate() == null ){
			if ("".equals(affairTeamDiscipline.getOtherYear()) || affairTeamDiscipline.getOtherYear() == null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				Date date = new Date();
				String nowDate = sdf.format(date);
				Date yearDate = null;
				try {
					yearDate = sdf.parse(nowDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				affairTeamDiscipline.setStartYear(yearDate);
				model.addAttribute("startYear", yearDate);
				Page<AffairTeamDiscipline> page = affairTeamDisciplineService.findPage(new Page<AffairTeamDiscipline>(request, response), affairTeamDiscipline);
				model.addAttribute("page", page);
			}

		}else if (affairTeamDiscipline.getOtherYear() != null && !"".equals(affairTeamDiscipline.getOtherYear())){
			Page<AffairTeamDiscipline> page = affairTeamDisciplineService.findPage(new Page<AffairTeamDiscipline>(request, response), affairTeamDiscipline);
			model.addAttribute("page", page);
		}*/
		return "modules/affair/affairTeamDisciplineManage";
	}

//	@RequiresPermissions("affair:affairTeamDiscipline:add")
	@RequestMapping(value = "form")
	public String form(AffairTeamDiscipline affairTeamDiscipline, Model model) {
		model.addAttribute("affairTeamDiscipline", affairTeamDiscipline);
		return "modules/affair/affairTeamDisciplineForm";
	}

//	@RequiresPermissions("affair:affairTeamDiscipline:edit")
	@RequestMapping(value = "save")
	public String save(AffairTeamDiscipline affairTeamDiscipline, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairTeamDiscipline)){
			return form(affairTeamDiscipline, model);
		}
		affairTeamDisciplineService.save(affairTeamDiscipline);
		addMessage(redirectAttributes, "保存队伍纪律作风教育整顿成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTeamDisciplineForm";
	}
	
//	@RequiresPermissions("affair:affairTeamDiscipline:delete")
	@RequestMapping(value = "delete")
	public String delete(AffairTeamDiscipline affairTeamDiscipline, RedirectAttributes redirectAttributes) {
		affairTeamDisciplineService.delete(affairTeamDiscipline);
		addMessage(redirectAttributes, "删除队伍纪律作风教育整顿成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTeamDiscipline/manageList?repage";
	}

	/**
	 * 详情
	 * @param affairTeamDiscipline
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("affair:affairTeamDiscipline:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTeamDiscipline affairTeamDiscipline, Model model) {
		model.addAttribute("affairTeamDiscipline", affairTeamDiscipline);
		if(affairTeamDiscipline.getAnnex() != null && affairTeamDiscipline.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairTeamDiscipline.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairTeamDisciplineFormDetail";
	}

	@ResponseBody
//	@RequiresPermissions("affair:affairTeamDiscipline:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTeamDisciplineService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 批量发布
	 * @param ids
	 * @return
	 */
	@ResponseBody
	//@RequiresPermissions("affair:affairFileNotice:manage")
	@RequestMapping(value = {"publishByIds"})
	public Result publishByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTeamDisciplineService.publishByIds(ids);
			result.setSuccess(true);
			result.setMessage("发布成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要发布的内容");
		}
		return result;
	}

	/**
	 * 批量取消发布
	 * @param ids
	 * @return
	 */
	@ResponseBody
	//@RequiresPermissions("affair:affairFileNotice:manage")
	@RequestMapping(value = {"cancelByIds"})
	public Result cancelByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTeamDisciplineService.cancelByIds(ids);
			result.setSuccess(true);
			result.setMessage("取消发布成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要取消发布的内容");
		}
		return result;
	}

	/**
	 * 管理页面form表单详情
	 * @param affairTeamDiscipline
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("affair:affairFileNotice:manage")
	/*@RequestMapping(value = {"formDetail"})
	public String formDetail(AffairTeamDiscipline affairTeamDiscipline, Model model) {
		model.addAttribute("affairTeamDiscipline", affairTeamDiscipline);
		if(affairTeamDiscipline.getAnnex() != null && affairTeamDiscipline.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairTeamDiscipline.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairTeamDisciplineFormDetail";
	}*/

//	转发
	@RequestMapping(value = "zhuanfa")
	public String zhuanfa(AffairTeamDiscipline affairTeamDiscipline, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		/*AffairTeamDiscipline affairTeamDiscipline1 = new AffairTeamDiscipline();
		affairTeamDisciplineService.save(affairTeamDiscipline1);
		addMessage(redirectAttributes, "保存队伍纪律作风教育整顿成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTeamDisciplineForm";*/
		AffairTeamDiscipline affairTeamDiscipline1 = new AffairTeamDiscipline();
		affairTeamDiscipline1.setEventName(affairTeamDiscipline.getEventName());
		affairTeamDiscipline1.setUnit(affairTeamDiscipline.getUnit());
		affairTeamDiscipline1.setUnitId(affairTeamDiscipline.getUnitId());
		affairTeamDiscipline1.setEventLocation(affairTeamDiscipline.getEventLocation());
		affairTeamDiscipline1.setEventDate(affairTeamDiscipline.getEventDate());
		affairTeamDiscipline1.setParticipants(affairTeamDiscipline.getParticipants());
		affairTeamDiscipline1.setForm(affairTeamDiscipline.getForm());
		affairTeamDiscipline1.setContent(affairTeamDiscipline.getContent());
		affairTeamDiscipline1.setAnnex(affairTeamDiscipline.getAnnex());
		affairTeamDiscipline1.setFinishDate(affairTeamDiscipline.getFinishDate());
		affairTeamDiscipline1.setQxUnitId(affairTeamDiscipline.getQxUnitId());
		affairTeamDiscipline1.setFlag(affairTeamDiscipline.getFlag());
		affairTeamDiscipline1.setStatus(null);
		affairTeamDiscipline1.setPublishDep(UserUtils.getUser().getOffice().getName());
		affairTeamDiscipline1.setPublisher(UserUtils.getUser().getName());
		affairTeamDiscipline1.setPublishOrgId(UserUtils.getUser().getOffice().getId());
		affairTeamDiscipline1.setRemarks(affairTeamDiscipline.getRemarks());
		model.addAttribute("affairTeamDiscipline", affairTeamDiscipline1);
		return "modules/affair/affairTeamDisciplineForm";
	}
}