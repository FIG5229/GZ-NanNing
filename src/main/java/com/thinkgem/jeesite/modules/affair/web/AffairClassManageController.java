/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassInformation;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassManage;
import com.thinkgem.jeesite.modules.affair.entity.AffairPersonnelMessage;
import com.thinkgem.jeesite.modules.affair.service.AffairClassManageService;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardBaseInfo;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.elasticsearch.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 培训班管理Controller
 * @author jack.xu
 * @version 2020-07-08
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairClassManage")
public class AffairClassManageController extends BaseController {

	@Autowired
	private AffairClassManageService affairClassManageService;

	@Autowired
	private OfficeService officeService;

	static String ids;
	static String looks;
	static String lookPer;
	static String basics;

	@ModelAttribute
	public AffairClassManage get(@RequestParam(required=false) String id) {
		AffairClassManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairClassManageService.get(id);
		}
		if (entity == null){
			entity = new AffairClassManage();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairClassManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairClassManage affairClassManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairClassManage> page = affairClassManageService.findPage(new Page<AffairClassManage>(request, response), affairClassManage);
		model.addAttribute("page", page);
		model.addAttribute("basic1","basicLook");
		model.addAttribute("basic2","basicUpdate");
		String year = affairClassManage.getYear();
		model.addAttribute("year",year);
		return "modules/affair/affairClassManageList";
	}
	@RequiresPermissions("affair:affairClassManage:view")
	@RequestMapping(value = "form")
	public String form(AffairClassManage affairClassManage, Model model,@RequestParam(value = "basic",required = false) String basic) {
		model.addAttribute("affairClassManage", affairClassManage);

		String id = affairClassManage.getId();
		model.addAttribute("id",id);
		model.addAttribute("look","look111");
		model.addAttribute("lookPers","lookPers111");
		model.addAttribute("basic", basic);
		System.out.println(basic);
		basics=basic;
		return "modules/affair/affairClassManageForm";
	}
	@RequiresPermissions("affair:affairClassManage:view")
	@RequestMapping(value = "formTwo")
	public String formTwo(AffairClassManage affairClassManage, Model model,@RequestParam(value = "basic",required = false) String basic) {
		model.addAttribute("affairClassManage", affairClassManage);
		//model.addAttribute("id",ids);
		model.addAttribute("look","look111");
		model.addAttribute("lookPers","lookPers111");
		model.addAttribute("basic", basic);
		basics=basic;
		return "modules/affair/affairClassManageFormTwo";
	}

	@RequiresPermissions("affair:affairClassManage:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairClassManage affairClassManage, Model model,@RequestParam(value = "basic") String basic) {
		model.addAttribute("affairClassManage", affairClassManage);
		String id = affairClassManage.getId();
		ids=id;
		model.addAttribute("id",id);

		model.addAttribute("look","look");
		model.addAttribute("lookPers","lookPers");
		model.addAttribute("basic", basic);
		basics=basic;
		return "modules/affair/affairClassManageFormDetail";
	}

	@RequiresPermissions("affair:affairClassManage:edit")
	@RequestMapping(value = "save")
	public String save(AffairClassManage affairClassManage, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
			if (!beanValidator(model, affairClassManage)){
			return form(affairClassManage, model,basics);
		}
		String id = affairClassManage.getId();
		//session.setAttribute("id",id);
		affairClassManageService.save(affairClassManage);
		addMessage(redirectAttributes, "保存培训班管理成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairClassManageForm";
	}

	@RequiresPermissions("affair:affairClassManage:edit")
	@RequestMapping(value = "status")
	public String status(AffairClassManage affairClassManage, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		affairClassManage.setStatus(request.getParameter("status"));
		affairClassManageService.save(affairClassManage);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairClassManage/?repage";
	}

	@RequiresPermissions("affair:affairClassManage:edit")
	@RequestMapping(value = "updateStatus")
	public String updateStatus(AffairClassManage affairClassManage, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		affairClassManage.setOpenStatus(request.getParameter("status"));
		affairClassManageService.save(affairClassManage);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairClassManage/?repage";
	}
	@RequiresPermissions("affair:affairClassManage:edit")
	@RequestMapping(value = "updateClassStatus")
	public String updateClassStatus(AffairClassManage affairClassManage, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		affairClassManage.setClassStatus(request.getParameter("status"));
		affairClassManageService.save(affairClassManage);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairClassManage/?repage";
	}
	@RequiresPermissions("affair:affairClassManage:edit")
	@RequestMapping(value = "updateResultStatus")
	public String updateResultStatus(AffairClassManage affairClassManage, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		affairClassManage.setPospStatus(request.getParameter("status"));
		affairClassManageService.save(affairClassManage);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairClassManage/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairClassManage:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairClassManageService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairClassManage:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairClassManage affairClassManage, RedirectAttributes redirectAttributes) {
		affairClassManageService.delete(affairClassManage);
		addMessage(redirectAttributes, "删除培训班管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairClassManage/?repage";
	}

	/**
	 * 导出excel格式数据
	 * @param affairClassManage
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportExcelByTemplate(AffairClassManage affairClassManage, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

		XSSFWorkbook wb = null;
		try {

			affairClassManage.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName").toString();
			}
			Page<AffairClassManage> page = null;
			if (flag == true) {
				page = affairClassManageService.findPage(new Page<AffairClassManage>(request, response), affairClassManage);
			} else {
				page = affairClassManageService.findPage(new Page<AffairClassManage>(request, response, -1), affairClassManage);
			}

			String fileSeperator = File.separator;
			String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;
			InputStream inputStream = new FileInputStream(filePath + fileName);
			if (null != inputStream) {
				try {
					wb = new XSSFWorkbook(inputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairClassManage.class);
			exportExcelNew.setWb(wb);
			List<AffairClassManage> list = page.getList();
			exportExcelNew.setDataList(list);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + ex);
		}
		return "redirect:" + adminPath + "/affair/affairClassManage?repage";
	}

	/**
	 * 导入excel数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairClassManage> list = ei.getDataList(AffairClassManage.class);
			for (AffairClassManage thoughtAnalysis : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(thoughtAnalysis.getUnit());
					if(orgId != null){
						thoughtAnalysis.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, thoughtAnalysis);

					String content = thoughtAnalysis.getContent();
					System.out.println(content);



					affairClassManageService.save(thoughtAnalysis);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view";
	}

	/**
	 *
	 *
	 * 课程信息
	 * @param model
	 * @param id
	 * @return
*/
	@RequiresPermissions("affair:affairClassManage:view")
	@RequestMapping(value = "idList")
	public String idList(Model model, @RequestParam(value = "classId") String id,AffairClassInformation affairClassInformation,@RequestParam(value = "look") String look,HttpSession session) {
		List<AffairClassInformation> page = affairClassManageService.idList(id,affairClassInformation);
		model.addAttribute("id",id);
		session.setAttribute("classManageId",id);
		//ids=id;
		model.addAttribute("page", page);
		model.addAttribute("look",look);
		looks=look;
		//model.addAttribute("lookPers","lookPers");
		model.addAttribute("lookPers",lookPer);
		model.addAttribute("basic",basics);
		return "modules/affair/affairClassInformationList";
	}

	/**
	 * 人员信息
	 * @param affairPersonnelMessage
	 * @param model
	 * @param id
	 * @return
	 */
	@RequiresPermissions("affair:affairClassManage:view")
	@RequestMapping(value = "idListRenYuan")
	public String idListRenYuan(AffairPersonnelMessage affairPersonnelMessage,HttpSession session, Model model,@RequestParam(value = "id") String id,@ModelAttribute("affairPersonnelMessage") AffairPersonnelMessage affairPersonnel,@RequestParam(value = "lookPers") String lookPers) {
		List<AffairPersonnelMessage> page = affairClassManageService.idListRenYuan(id,affairPersonnelMessage);
		model.addAttribute("id",id);
		session.setAttribute("classId",id);
		model.addAttribute("page", page);
		model.addAttribute("look",looks);
		//model.addAttribute("lookPers",lookPers);
		lookPer=lookPers;
		if(basics.equals("basicLook")){
			model.addAttribute("lookPers","lookPers");
		}else if(basics.equals("basicUpdate")){
			model.addAttribute("lookPers","lookPers111");
		}
		model.addAttribute("basic",basics);
		return "modules/affair/affairPersonnelMessageList";
	}


}