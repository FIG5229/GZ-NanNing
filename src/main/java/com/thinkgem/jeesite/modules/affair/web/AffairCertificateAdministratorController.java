/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.elasticsearch.action.ValidateActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairCertificateAdministrator;
import com.thinkgem.jeesite.modules.affair.service.AffairCertificateAdministratorService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.MulticastChannel;
import java.util.List;

/**
 * 证书管理员Controller
 * @author jack.xu
 * @version 2020-07-27
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCertificateAdministrator")
public class AffairCertificateAdministratorController extends BaseController {

	@Autowired
	private AffairCertificateAdministratorService affairCertificateAdministratorService;

	@Autowired
	private OfficeService officeService;
	@ModelAttribute
	public AffairCertificateAdministrator get(@RequestParam(required=false) String id) {
		AffairCertificateAdministrator entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCertificateAdministratorService.get(id);
		}
		if (entity == null){
			entity = new AffairCertificateAdministrator();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairCertificateAdministrator:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCertificateAdministrator affairCertificateAdministrator, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairCertificateAdministrator> page = affairCertificateAdministratorService.findPage(new Page<AffairCertificateAdministrator>(request, response), affairCertificateAdministrator); 
		model.addAttribute("page", page);
		return "modules/affair/affairCertificateAdministratorList";
	}

	@RequiresPermissions("affair:affairCertificateAdministrator:view")
	@RequestMapping(value = "form")
	public String form(AffairCertificateAdministrator affairCertificateAdministrator, Model model) {
		model.addAttribute("affairCertificateAdministrator", affairCertificateAdministrator);
		return "modules/affair/affairCertificateAdministratorForm";
	}

	@RequiresPermissions("affair:affairCertificateAdministrator:edit")
	@RequestMapping(value = "save")
	public String save(AffairCertificateAdministrator affairCertificateAdministrator, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairCertificateAdministrator)){
			return form(affairCertificateAdministrator, model);
		}
		affairCertificateAdministratorService.save(affairCertificateAdministrator);
		addMessage(redirectAttributes, "保存证书管理员成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairCertificateAdministratorForm";

	}
	
	@RequiresPermissions("affair:affairCertificateAdministrator:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCertificateAdministrator affairCertificateAdministrator, RedirectAttributes redirectAttributes) {
		affairCertificateAdministratorService.delete(affairCertificateAdministrator);
		addMessage(redirectAttributes, "删除证书管理员成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCertificateAdministrator/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairCertificateAdministrator:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() >0 ){
			affairCertificateAdministratorService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairCertificateAdministrator:view")
	@RequestMapping(value = {"formDetail"})
	public String formDetail(AffairCertificateAdministrator affairCertificateAdministrator, Model model){
		model.addAttribute("affairCertificateAdministrator",affairCertificateAdministrator);
		return "modules/affair/affairCertificateAdministratorFormDetail";
	}

	/**
	 * 导入excel格式数据
	 * @Param affairCertificateAdministrator
	 * @Param request
	 * @Param response
	 * @Param redirectAttributes
	 * @Param flag
	 * @return
	 * */
	@RequestMapping(value = "export",method = RequestMethod.POST)
	public String exportExcelByTemplate(AffairCertificateAdministrator affairCertificateAdministrator,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes,boolean flag){

		XSSFWorkbook wb = null;
		try{
			affairCertificateAdministrator.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))){
				fileName = request.getParameter("fileName").toString();
			}
			Page<AffairCertificateAdministrator> page = null;
			if (flag == true){
				page = affairCertificateAdministratorService.findPage(new Page<AffairCertificateAdministrator>(request,response), affairCertificateAdministrator);
			}else {
				page = affairCertificateAdministratorService.findPage(new Page<AffairCertificateAdministrator>(request,response,-1),affairCertificateAdministrator);
			}
			String fileSeperator = File.separator;
			String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;
			InputStream inputStream = new FileInputStream(filePath + fileName);
			if (null != inputStream){
				try{
					wb = new XSSFWorkbook(inputStream);
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			ExportExcelNew exportExcelNew = new ExportExcelNew(0,AffairCertificateAdministrator.class);
			exportExcelNew.setWb(wb);
			List<AffairCertificateAdministrator> list = page.getList();
			exportExcelNew.setDataList(list);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("content-Disposition","attachment; filename=" + Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		}catch (Exception ex){
			ex.printStackTrace();
			addMessage(redirectAttributes,"导出用户失败！失败信息：" +ex);
		}
		return "redirect:" + adminPath + "/affair/affairCertificateAdministrator?repage";
	}

	/**
	 * 导入excel数据
	 * @Param file
	 * @Param redirectAttributes
	 *
	 * */
	@RequestMapping(value = "import",method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes){
		try{
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file,0,0);
			List<AffairCertificateAdministrator> list = ei.getDataList(AffairCertificateAdministrator.class);
			for (AffairCertificateAdministrator thoughAnalysis : list){
				try{
					String orgId = officeService.findByName(thoughAnalysis.getUnit());
					if(orgId != null){
						thoughAnalysis.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator,thoughAnalysis);
					affairCertificateAdministratorService.save(thoughAnalysis);
					successNum++;
				}catch (ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex,":");
					for (String message : messageList){
						failureMsg.append(message+";");
						failureNum++;
					}
				}catch (Exception ex){
					failureMsg.append("导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0,",失败"+failureNum+"条，导入信息如下");
			}
			addMessage(redirectAttributes,"已成功导入"+successNum+"条"+failureMsg);
		}catch (Exception e){
			addMessage(redirectAttributes,"导入失败！失败信息："+e.getMessage());
		}
		redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view";

	}

}

