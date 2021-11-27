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
import com.thinkgem.jeesite.modules.affair.entity.AffairClassManage;
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainOutsource;
import com.thinkgem.jeesite.modules.affair.service.AffairTrainOutsourceService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 委外培训Controller
 * @author jack.xu
 * @version 2020-07-17
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTrainOutsource")
public class AffairTrainOutsourceController extends BaseController {

	@Autowired
	private AffairTrainOutsourceService affairTrainOutsourceService;

	@Autowired
	private OfficeService officeService;

	@ModelAttribute
	public AffairTrainOutsource get(@RequestParam(required=false) String id) {
		AffairTrainOutsource entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTrainOutsourceService.get(id);
		}
		if (entity == null){
			entity = new AffairTrainOutsource();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairSendTeacher:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
		return "modules/affair/affairTrainOutsourceIndex";
	}
	
	@RequiresPermissions("affair:affairTrainOutsource:view")
	@RequestMapping(value = {"list"})
	public String list(AffairTrainOutsource affairTrainOutsource, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTrainOutsource> page = affairTrainOutsourceService.findPage(new Page<AffairTrainOutsource>(request, response), affairTrainOutsource); 
		model.addAttribute("page", page);
		model.addAttribute("unit",affairTrainOutsource.getUnit());
		model.addAttribute("unitId",affairTrainOutsource.getUnitId());
		return "modules/affair/affairTrainOutsourceList";
	}

	@RequiresPermissions("affair:affairTrainOutsource:view")
	@RequestMapping(value = "form")
	public String form(AffairTrainOutsource affairTrainOutsource, Model model) {
		model.addAttribute("affairTrainOutsource", affairTrainOutsource);
		return "modules/affair/affairTrainOutsourceForm";
	}

	@RequiresPermissions("affair:affairTrainOutsource:edit")
	@RequestMapping(value = "save")
	public String save(AffairTrainOutsource affairTrainOutsource, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairTrainOutsource)){
			return form(affairTrainOutsource, model);
		}
		affairTrainOutsourceService.save(affairTrainOutsource);
		addMessage(redirectAttributes, "保存委外培训成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTrainOutsourceForm";
	}
	
	@RequiresPermissions("affair:affairTrainOutsource:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTrainOutsource affairTrainOutsource, RedirectAttributes redirectAttributes,String unitId) {
		affairTrainOutsourceService.delete(affairTrainOutsource);
		addMessage(redirectAttributes, "删除委外培训成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTrainOutsource/list/?unitId="+unitId;
	}

	@ResponseBody
	@RequiresPermissions("affair:affairTrainOutsource:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTrainOutsourceService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairTrainOutsource:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTrainOutsource affairTrainOutsource, Model model) {
		model.addAttribute("affairTrainOutsource", affairTrainOutsource);
		return "modules/affair/affairTrainOutsourceFormDetail";
	}

	/**
	 * 导出excel格式数据
	 * @param affairTrainOutsource
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportExcelByTemplate(AffairTrainOutsource affairTrainOutsource, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

		XSSFWorkbook wb = null;
		try {

			affairTrainOutsource.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName").toString();
			}
			Page<AffairTrainOutsource> page = null;
			if (flag == true) {
				page = affairTrainOutsourceService.findPage(new Page<AffairTrainOutsource>(request, response), affairTrainOutsource);
			} else {
				page = affairTrainOutsourceService.findPage(new Page<AffairTrainOutsource>(request, response, -1), affairTrainOutsource);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTrainOutsource.class);
			exportExcelNew.setWb(wb);
			List<AffairTrainOutsource> list = page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTrainOutsource?repage";
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
			List<AffairTrainOutsource> list = ei.getDataList(AffairTrainOutsource.class);
			for (AffairTrainOutsource thoughtAnalysis : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(thoughtAnalysis.getUnit());
					if(orgId != null){
						thoughtAnalysis.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, thoughtAnalysis);
					affairTrainOutsourceService.save(thoughtAnalysis);
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