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
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainApprove;
import com.thinkgem.jeesite.modules.affair.service.AffairTrainApproveService;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 培训计划审批Controller
 * @author jack.xu
 * @version 2020-07-28
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTrainApprove")
public class AffairTrainApproveController extends BaseController {

	@Autowired
	private AffairTrainApproveService affairTrainApproveService;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AffairTrainApprove get(@RequestParam(required=false) String id) {
		AffairTrainApprove entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTrainApproveService.get(id);
		}
		if (entity == null){
			entity = new AffairTrainApprove();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTrainApprove:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTrainApprove affairTrainApprove, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTrainApprove> page = affairTrainApproveService.findPage(new Page<AffairTrainApprove>(request, response), affairTrainApprove); 
		model.addAttribute("page", page);
		return "modules/affair/affairTrainApproveList";
	}

	@RequiresPermissions("affair:affairTrainApprove:view")
	@RequestMapping(value = "form")
	public String form(AffairTrainApprove affairTrainApprove, Model model) {
		model.addAttribute("affairTrainApprove", affairTrainApprove);
		return "modules/affair/affairTrainApproveForm";
	}

	@RequiresPermissions("affair:affairTrainApprove:edit")
	@RequestMapping(value = "save")
	public String save(AffairTrainApprove affairTrainApprove, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairTrainApprove)){
			return form(affairTrainApprove, model);
		}
		affairTrainApproveService.save(affairTrainApprove);
		addMessage(redirectAttributes, "保存培训计划审批成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTrainApproveForm";
	}
	
	@RequiresPermissions("affair:affairTrainApprove:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTrainApprove affairTrainApprove, RedirectAttributes redirectAttributes) {
		affairTrainApproveService.delete(affairTrainApprove);
		addMessage(redirectAttributes, "删除培训计划审批成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTrainApprove/?repage";
	}
	@ResponseBody
	@RequiresPermissions("affair:affairTrainApprove:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTrainApproveService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:affairTrainApprove:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTrainApprove affairTrainApprove, Model model) {
		model.addAttribute("affairTrainApprove", affairTrainApprove);
		return "modules/affair/affairTrainApproveFormDetail";
	}
	/**
	 * 审核
	 * @return
	 */
	@RequiresPermissions("affair:affairTrainApprove:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog(String ids,Model model) {
		model.addAttribute("ids", ids);
		return "modules/affair/affairTrainApproveCheckDialog";
	}

	//	这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairTrainApprove:edit")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairTrainApprove affairTrainApprove, HttpServletRequest request) {
		String approveResult = affairTrainApprove.getApproveResult();
		String approveStatus = affairTrainApprove.getApproveStatus();
		String idsStr = request.getParameter("ids");
		String[] idsArray = idsStr.split(",");
		List<String> userList = new ArrayList<String>();
		Collections.addAll(userList,idsArray);
		List <AffairTrainApprove> list = affairTrainApproveService.findByIds(userList);
		for (AffairTrainApprove  affairTrainApproveFromDb:list){
			affairTrainApproveFromDb.setApproveResult(approveResult);
			affairTrainApproveFromDb.setApproveStatus(approveStatus);
			affairTrainApproveService.save(affairTrainApproveFromDb);
		}
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审批成功");
		return result;
	}
	/**
	 * 导出excel格式数据
	 * @param affairTrainApprove
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportExcelByTemplate(AffairTrainApprove affairTrainApprove, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

		XSSFWorkbook wb = null;
		try {

			affairTrainApprove.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName").toString();
			}
			Page<AffairTrainApprove> page = null;
			if (flag == true) {
				page = affairTrainApproveService.findPage(new Page<AffairTrainApprove>(request, response), affairTrainApprove);
			} else {
				page = affairTrainApproveService.findPage(new Page<AffairTrainApprove>(request, response, -1), affairTrainApprove);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTrainApprove.class);
			exportExcelNew.setWb(wb);
			List<AffairTrainApprove> list = page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTrainApprove?repage";
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
			List<AffairTrainApprove> list = ei.getDataList(AffairTrainApprove.class);
			for (AffairTrainApprove thoughtAnalysis : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(thoughtAnalysis.getUnit());
					if(orgId != null){
						thoughtAnalysis.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, thoughtAnalysis);
					affairTrainApproveService.save(thoughtAnalysis);
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