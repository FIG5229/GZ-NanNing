/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairApplyPersonnel;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassInformation;
import com.thinkgem.jeesite.modules.affair.service.AffairClassInformationService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
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
import java.util.List;

import static com.thinkgem.jeesite.modules.affair.web.AffairClassManageController.looks;

/**
 * 课程信息Controller
 * @author jack.xu
 * @version 2020-07-23
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairClassInformation")
public class AffairClassInformationController extends BaseController {

	@Autowired
	private AffairClassInformationService affairClassInformationService;

	static String classIdOne;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AffairClassInformation get(@RequestParam(required=false) String id) {
		AffairClassInformation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairClassInformationService.get(id);
		}
		if (entity == null){
			entity = new AffairClassInformation();
		}
		return entity;
	}
	/*@RequiresPermissions("affair:affairClassInformation:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairClassInformation affairClassInformation, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session,@RequestParam("courseId")String courseId) {
		Page<AffairClassInformation> page = affairClassInformationService.findPage(new Page<AffairClassInformation>(request, response), affairClassInformation);
		model.addAttribute("page", page);

		return "modules/affair/affairClassInformationList";
	}*/
	@RequiresPermissions("affair:affairClassInformation:view")
	@RequestMapping(value = "form")
	public String form(AffairClassInformation affairClassInformation, Model model,@RequestParam(value = "classId") String classId,@RequestParam(value = "look",required = false) String look) {
		model.addAttribute("affairClassInformation", affairClassInformation);
		model.addAttribute("classId",classId);
		return "modules/affair/affairClassInformationForm";
	}

	@RequiresPermissions("affair:affairClassInformation:edit")
	@RequestMapping(value = "save")
	public String save(AffairClassInformation affairClassInformation, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request,String classId,HttpSession session,@RequestParam(value = "look")String look) {
		if (!beanValidator(model, affairClassInformation)){
			return form(affairClassInformation, model,classId,looks);
		}
		classIdOne = request.getParameter("classId");
		if(null != classIdOne) {
			affairClassInformation.setClassManageId(classIdOne);
			session.setAttribute("classIdOne",classIdOne);
		}

		String classManageId = affairClassInformation.getClassManageId();
		System.out.println(classManageId);
		affairClassInformationService.save(affairClassInformation);
		addMessage(redirectAttributes, "保存课程信息成功");
		request.setAttribute("saveResult","success");
		//return "modules/affair/affairClassInformationForm";
		return "redirect:"+Global.getAdminPath()+"/affair/affairClassManage/idList?classId="+classId+"&look="+look;
	}

	@RequiresPermissions("affair:affairClassInformation:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairClassInformation affairClassInformation, Model model) {
		model.addAttribute("affairClassInformation", affairClassInformation);
		return "modules/affair/affairClassInformationFormDetail";
	}
	@RequiresPermissions("affair:affairClassInformation:edit")
	@RequestMapping(value = "delete")
	public String deleteClassInfo(AffairClassInformation affairClassInformation, RedirectAttributes redirectAttributes,HttpServletRequest request,@RequestParam("classId") String classId,@RequestParam("look") String look) {
		affairClassInformationService.delete(affairClassInformation);
		addMessage(redirectAttributes, "删除课程信息成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairClassManage/idList?classId="+classId+"&look="+look;


	}
	@ResponseBody
	@RequiresPermissions("affair:affairClassInformation:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairClassInformationService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 导出excel格式数据
	 * @param affairClassInformation
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportExcelByTemplate(AffairClassInformation affairClassInformation, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag,@RequestParam(value = "id") String classManageId,Model model) {

		XSSFWorkbook wb = null;
		try {

			affairClassInformation.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName").toString();
			}
			List<AffairClassInformation> pageTwo = affairClassInformationService.findPageThree(classManageId);

			for (AffairClassInformation classInformation : pageTwo) {
				System.out.println(classInformation);
			}

			System.out.println(classManageId);

			model.addAttribute("id",classManageId);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(1, AffairClassInformation.class);
			exportExcelNew.setWb(wb);
			exportExcelNew.setDataList(pageTwo);
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
		return "redirect:" + adminPath + "/affair/affairClassInformation?repage";
	//  return "redirect:" + adminPath + "/affair/affairPersonnelMessage?repage";
	}

	/**
	 * 导入excel数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpSession session) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<AffairClassInformation> list = ei.getDataList(AffairClassInformation.class);
			for (AffairClassInformation thoughtAnalysis : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(thoughtAnalysis.getUnit());
					if(orgId != null){
						thoughtAnalysis.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, thoughtAnalysis);

					/*String classIdOne = session.getAttribute("classIdOne").toString();
					*/
					thoughtAnalysis.setClassManageId(classIdOne);
					System.out.println(classIdOne);
					Object classManageId = session.getAttribute("classManageId");
					String classManaId = String.valueOf(classManageId);
					thoughtAnalysis.setClassManageId(classManaId);
					affairClassInformationService.save(thoughtAnalysis);
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
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view";
	}
}