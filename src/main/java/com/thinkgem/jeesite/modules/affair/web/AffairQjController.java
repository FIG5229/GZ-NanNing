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
import com.thinkgem.jeesite.modules.affair.entity.AffairQj;
import com.thinkgem.jeesite.modules.affair.service.AffairLaborOfficeService;
import com.thinkgem.jeesite.modules.affair.service.AffairQjService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
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
import java.util.Calendar;
import java.util.List;

/**
 * 请假信息Controller
 * @author mason.xv
 * @version 2019-11-27
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairQj")
public class AffairQjController extends BaseController {

	@Autowired
	private AffairQjService affairQjService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairLaborOfficeService affairLaborOfficeService;

	@ModelAttribute
	public AffairQj get(@RequestParam(required=false) String id) {
		AffairQj entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairQjService.get(id);
		}
		if (entity == null){
			entity = new AffairQj();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairQj:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairQj affairQj, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isNotBlank(request.getParameter("mType"))){
			request.setAttribute("mType", request.getParameter("mType"));
			affairQj.setmType(request.getParameter("mType"));
		}
		Calendar calendar = Calendar.getInstance();
		if (affairQj.getYear() == null || "".equals(affairQj.getYear())) {
			affairQj.setYear(calendar.get(Calendar.YEAR));
			affairQj.setMonth(calendar.get(Calendar.MONTH) + 1);
		}
		Page<AffairQj> page = affairQjService.findPage(new Page<AffairQj>(request, response,-1), affairQj);
		model.addAttribute("page", page);
		return "modules/affair/affairQjList";
	}

	@RequiresPermissions("affair:affairQj:view")
	@RequestMapping(value = "form")
	public String form(AffairQj affairQj, Model model) {
		model.addAttribute("affairQj", affairQj);
		return "modules/affair/affairQjForm";
	}
   //销假页跳转Controller
	@RequiresPermissions("affair:affairQj:view")
	@RequestMapping(value = "formCancel")
	public String formCancel(AffairQj affairQj, Model model) {
		model.addAttribute("affairQj", affairQj);
		return "modules/affair/affairQjCancel";
	}

	@RequiresPermissions("affair:affairQj:edit")
	@RequestMapping(value = "save")
	public String save(AffairQj affairQj, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairQj)){
			return form(affairQj, model);
		}
		/*String[] values=request.getParameterValues("myCheckBox");
		if(values != null && !"".equals(values)){
			for (String i:values){
				if(i.equals("0")){
					affairQj.setHrStatus("1");
				}else if (i.equals("1")){
					affairQj.setDepStatus("1");
				}else if (i.equals("2")){
					affairQj.setLeaderStatus("1");
				}
			}
			affairQj.setStatus("11");
		}else{
			affairQj.setStatus("0");
		}*/
		affairQjService.save(affairQj);
		addMessage(redirectAttributes, "保存请假信息成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairQjForm";
	}
	
	@RequiresPermissions("affair:affairQj:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairQj affairQj, RedirectAttributes redirectAttributes) {
		affairQjService.delete(affairQj);
		addMessage(redirectAttributes, "删除请假信息成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairQj/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairQj:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairQjService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/*//这是用来跳人事dialog页面的controller
	@RequiresPermissions("affair:affairQj:hrManage")
	@RequestMapping(value = {"hrShenHeDialog"})
	public String shenHeDialog2() {
		return "modules/affair/affairQjHrCheckDialog";
	}
	//这是用来跳部门审核dialog页面的
	@RequiresPermissions("affair:affairQj:depManage")
	@RequestMapping(value = {"depShenHeDialog"})
	public String shenHeDialog1() {
		return "modules/affair/affairQjDepCheckDialog";
	}
	//这是用来跳转居处dialog页面的controller
	@RequiresPermissions("affair:affairQj:officeManage")
	@RequestMapping(value = {"leaderShenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairQjOfficeCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairQj:hrManage")
	@RequestMapping(value = "hrShenHe")
	public Result hrShenHe(AffairQj affairQj) {
		if ("2".equals(affairQj.getStatus())){
			affairQj.setHrStatus("2");
		}else{
			affairQj.setHrStatus("3");
		}
		affairQjService.hrShenHe(affairQj);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

	@ResponseBody
	@RequiresPermissions("affair:affairQj:depManage")
	@RequestMapping(value = "depShenHe")
	public Result depShenHe(AffairQj affairQj) {
		if ("5".equals(affairQj.getStatus())){
			affairQj.setDepStatus("2");
		}else{
			affairQj.setDepStatus("3");
		}
		affairQjService.depShenHe(affairQj);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

	@ResponseBody
	@RequiresPermissions("affair:affairQj:officeManage")
	@RequestMapping(value = "leaderShenHe")
	public Result leaderShenhe(AffairQj affairQj) {
		if ("8".equals(affairQj.getStatus())){
			affairQj.setLeaderStatus("2");
		}else{
			affairQj.setLeaderStatus("3");
		}
		affairQjService.leaderShenhe(affairQj);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}*/
	/**
	 * 导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairQj affairQj, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			Page<AffairQj> page = null;
			if(flag == true){
				page = affairQjService.findPage(new Page<AffairQj>(request, response), affairQj);
			}else {
				page = affairQjService.findPage(new Page<AffairQj>(request, response,-1), affairQj);
			}
			String fileSeperator = File.separator;
			String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
			InputStream inputStream = new FileInputStream(filePath+fileName);
			if (null != inputStream)
			{
				try
				{
					wb = new XSSFWorkbook(inputStream);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			//修改
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairQj.class);
			exportExcelNew.setWb(wb);
			List<AffairQj> list =page.getList();
			exportExcelNew.setDataList(list);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出用户失败！失败信息："+ex);
		}
		//修改
		return "redirect:" + adminPath + "/affair/affairQj/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairQj> list = ei.getDataList(AffairQj.class);
			for (AffairQj affairQj : list){
				try{
					BeanValidators.validateWithException(validator, affairQj);
					if (affairQj.getUnit()!=null&&!"".equals(affairQj.getUnit())){
						affairQj.setUnitId(affairLaborOfficeService.findByName(affairQj.getUnit()));
					}
					affairQjService.save(affairQj);
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

	@RequiresPermissions("affair:affairQj:edit")
	@RequestMapping(value = {"affairQjSum"})
	public String affairSum() {
		return "modules/affair/affairQjSum";
	}
}