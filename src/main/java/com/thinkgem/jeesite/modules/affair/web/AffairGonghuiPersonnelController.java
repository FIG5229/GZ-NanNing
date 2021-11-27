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
import com.thinkgem.jeesite.modules.affair.entity.AffairGonghuiPersonnel;
import com.thinkgem.jeesite.modules.affair.service.AffairGonghuiPersonnelService;
import com.thinkgem.jeesite.modules.affair.service.AffairOrganizationBulidService;
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
 * 工会名册Controller
 * @author mason.xv
 * @version 2019-12-30
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairGonghuiPersonnel")
public class AffairGonghuiPersonnelController extends BaseController {

	@Autowired
	private AffairOrganizationBulidService affairOrganizationBulidService;
	@Autowired
	private AffairGonghuiPersonnelService affairGonghuiPersonnelService;
	@Autowired
	private AffairGonghuiPersonnelService affairGonghuiPersonnel1S;
	@ModelAttribute
	public AffairGonghuiPersonnel get(@RequestParam(required=false) String id) {
		AffairGonghuiPersonnel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairGonghuiPersonnelService.get(id);
		}
		if (entity == null){
			entity = new AffairGonghuiPersonnel();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairGonghuiPersonnel:view")
	@RequestMapping(value = {""})
	public String index() {
		return "modules/affair/affairGonghuiPersonnelIndex";
	}
	
	@RequiresPermissions("affair:affairGonghuiPersonnel:view")
	@RequestMapping(value = {"list"})
	public String list(AffairGonghuiPersonnel affairGonghuiPersonnel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairGonghuiPersonnel> page = affairGonghuiPersonnelService.findPage(new Page<AffairGonghuiPersonnel>(request, response), affairGonghuiPersonnel); 
		model.addAttribute("page", page);
		return "modules/affair/affairGonghuiPersonnelList";
	}

	@RequiresPermissions("affair:affairGonghuiPersonnel:view")
	@RequestMapping(value = "form")
	public String form(AffairGonghuiPersonnel affairGonghuiPersonnel, Model model) {
		model.addAttribute("affairGonghuiPersonnel", affairGonghuiPersonnel);
		return "modules/affair/affairGonghuiPersonnelForm";
	}

	@RequiresPermissions("affair:affairGonghuiPersonnel:edit")
	@RequestMapping(value = "save")
	public String save(AffairGonghuiPersonnel affairGonghuiPersonnel, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairGonghuiPersonnel)){
			return form(affairGonghuiPersonnel, model);
		}
		affairGonghuiPersonnelService.save(affairGonghuiPersonnel);
		addMessage(redirectAttributes, "保存工会名册成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairGonghuiPersonnelForm";
	}
	
	@RequiresPermissions("affair:affairGonghuiPersonnel:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairGonghuiPersonnel affairGonghuiPersonnel, RedirectAttributes redirectAttributes) {
		String partyBranchId = affairGonghuiPersonnel.getPartyBranchId();
		affairGonghuiPersonnelService.delete(affairGonghuiPersonnel);
		addMessage(redirectAttributes, "删除工会名册成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairGonghuiPersonnel/list?partyBranchId="+partyBranchId;
	}
	/**
	 * 详情
	 * @param affairGonghuiPersonnel
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairGonghuiPersonnel:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairGonghuiPersonnel affairGonghuiPersonnel, Model model) {
		model.addAttribute("affairGonghuiPersonnel", affairGonghuiPersonnel);
		return "modules/affair/affairGonghuiPersonnelFormDetail";
	}
	@ResponseBody
	@RequiresPermissions("affair:affairGonghuiPersonnel:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairGonghuiPersonnelService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	/**
	 * 修改工作单位的弹窗
	 *
	 * @param ids
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairGonghuiPersonnel:edit")
	@RequestMapping(value = {"editGonghuiDialog"})
	public String editGonghuiDialog(String ids, Model model) {
		model.addAttribute("editPersonIds", ids);
		return "modules/affair/affairGonghuiPersonnelTree";
	}
	/**
	 * 保存批量修改工作单位
	 *
	 * @param
	 * @param request
	 * @return
	 */
	@RequiresPermissions("affair:affairGonghuiPersonnel:edit")
	@RequestMapping(value = {"editGonghui"})
	public String editWorkUnit(AffairGonghuiPersonnel affairGonghuiPersonnel ,HttpServletRequest request) {
	/*	affairGonghuiPersonnelService.updateWorkUnitByIds(Arrays.asList(affairGonghuiPersonnel.getEditPersonIds()), affairGonghuiPersonnel.getEditWorkUnitId(), personnelBase.getEditWorkUnitName());
	*/
	    String partyBranchId = affairGonghuiPersonnel.getPartyBranchId();
	    String partyBranch = affairGonghuiPersonnel.getPartyBranch();
		String idsStr = request.getParameter("editPersonIds");
		String[] idsArray = idsStr.split(",");
		List<String> userList = new ArrayList<String>();
		Collections.addAll(userList,idsArray);
		List <AffairGonghuiPersonnel> list = affairGonghuiPersonnelService.findByIds(userList);
		for (AffairGonghuiPersonnel  affairGonghuiPersonnelFromDb:list){
			affairGonghuiPersonnelFromDb.setPartyBranchId(partyBranchId);
			affairGonghuiPersonnelFromDb.setPartyBranch(partyBranch);
			affairGonghuiPersonnelService.save(affairGonghuiPersonnelFromDb);
		}
	    request.setAttribute("saveResult", "sucess");
		return "modules/affair/affairGonghuiPersonnelTree";
	}

	/**
	 * 导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairGonghuiPersonnel affairGonghuiPersonnel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairGonghuiPersonnel> page = null;
			if(flag == true){
				page = affairGonghuiPersonnelService.findPage(new Page<AffairGonghuiPersonnel>(request, response), affairGonghuiPersonnel);
			}else {
				page = affairGonghuiPersonnelService.findPage(new Page<AffairGonghuiPersonnel>(request, response,-1), affairGonghuiPersonnel);
			}
/*
			Page<affairGonghuiPersonnel> page = affairGonghuiPersonnelService.findPage(new Page<affairGonghuiPersonnel>(request, response,-1), affairGonghuiPersonnel);
*/
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairGonghuiPersonnel.class);
			exportExcelNew.setWb(wb);
			List<AffairGonghuiPersonnel> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairGonghuiPersonnel/list?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes, AffairGonghuiPersonnel affairGonghuiPersonnel0) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairGonghuiPersonnel> list = ei.getDataList(AffairGonghuiPersonnel.class);
			for (AffairGonghuiPersonnel affairGonghuiPersonnel : list){
				try{
                    if (affairGonghuiPersonnel.getPartyBranch()!=null&&!"".equals(affairGonghuiPersonnel.getPartyBranch())){
						affairGonghuiPersonnel.setPartyBranchId(affairOrganizationBulidService.findByName(affairGonghuiPersonnel.getPartyBranch()));
					}
					BeanValidators.validateWithException(validator, affairGonghuiPersonnel);
					affairGonghuiPersonnelService.save(affairGonghuiPersonnel);
					successNum++;

				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+affairGonghuiPersonnel.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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