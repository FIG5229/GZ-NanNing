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
import com.thinkgem.jeesite.modules.affair.dao.AffairWentiTalentNextDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPersonalAward;
import com.thinkgem.jeesite.modules.affair.entity.AffairWentiTalentNext;
import com.thinkgem.jeesite.modules.affair.service.AffairPersonalAwardService;
import com.thinkgem.jeesite.modules.affair.service.AffairWentiTalentNextService;
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
 * 文体人才库Controller
 * @author cecil.li
 * @version 2019-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairWentiTalentNext")
public class AffairWentiTalentNextController extends BaseController {

	@Autowired
	private AffairPersonalAwardService affairPersonalAwardService;
	@Autowired
	private AffairWentiTalentNextService affairWentiTalentNextService;
	@Autowired
	private AffairWentiTalentNextDao affairWentiTalentNextDao;
	@ModelAttribute
	public AffairWentiTalentNext get(@RequestParam(required=false) String id) {
		AffairWentiTalentNext entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairWentiTalentNextService.get(id);
		}
		if (entity == null){
			entity = new AffairWentiTalentNext();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairWentiTalentNext:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairWentiTalentNext affairWentiTalentNext, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairWentiTalentNext> page = affairWentiTalentNextService.findPage(new Page<AffairWentiTalentNext>(request, response), affairWentiTalentNext);
		model.addAttribute("page", page);
		return "modules/affair/affairWentiTalentNextList";
	}

	@RequiresPermissions("affair:affairWentiTalentNext:view")
	@RequestMapping(value = "form")
	public String form(AffairWentiTalentNext affairWentiTalentNext, Model model) {
		model.addAttribute("affairWentiTalentNext", affairWentiTalentNext);
		return "modules/affair/affairWentiTalentNextForm";
	}

	@RequiresPermissions("affair:affairWentiTalentNext:edit")
	@RequestMapping(value = "save")
	public String save(AffairWentiTalentNext affairWentiTalentNext, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairWentiTalentNext)){
			return form(affairWentiTalentNext, model);
		}
		affairWentiTalentNextService.save(affairWentiTalentNext);
		addMessage(redirectAttributes, "保存文体人才库成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairWentiTalentNextForm";
	}

	/**
	 *
	 * 推送页面
	 * @return
	 */
	@RequiresPermissions("affair:affairWentiTalentNext:edit")
	@RequestMapping(value = {"pushForm"})
	public String pushForm(@RequestParam("id") String id, Model model, AffairWentiTalentNext affairWentiTalentNext, AffairPersonalAward affairPersonalAward) {

		affairPersonalAward = affairPersonalAwardService.get(id);
		affairWentiTalentNext.setIdNumber(affairPersonalAward.getIdNumber());
		affairWentiTalentNext.setName(affairPersonalAward.getPoliceName());
		model.addAttribute("affairWentiTalentNext", affairWentiTalentNext);
		return "modules/affair/affairWentiTalentNextPushForm";
	}

	@RequiresPermissions("affair:affairWentiTalentNext:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairWentiTalentNext affairWentiTalentNext, RedirectAttributes redirectAttributes) {
		affairWentiTalentNextService.delete(affairWentiTalentNext);
		addMessage(redirectAttributes, "删除文体人才库成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWentiTalentNext/?repage";
	}
	/**
	 * 详情
	 * @param affairWentiTalentNext
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairWentiTalentNext:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairWentiTalentNext affairWentiTalentNext, Model model) {
		model.addAttribute("affairWentiTalentNext", affairWentiTalentNext);
		return "modules/affair/affairWentiTalentNextFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairWentiTalentNext:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairWentiTalentNextService.deleteByIds(ids);
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
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairWentiTalentNext affairWentiTalentNext, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairWentiTalentNext> page = null;
			if(flag == true){
				page = affairWentiTalentNextService.findPage(new Page<AffairWentiTalentNext>(request, response), affairWentiTalentNext);
			}else {
				page = affairWentiTalentNextService.findPage(new Page<AffairWentiTalentNext>(request, response,-1), affairWentiTalentNext);
			}
/*
			Page<AffairThreeOne> page = affairThreeOneService.findPage(new Page<AffairThreeOne>(request, response,-1), affairThreeOne);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairWentiTalentNext.class);
			exportExcelNew.setWb(wb);
			List<AffairWentiTalentNext> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairWentiTalentNext/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,AffairWentiTalentNext affairWentiTalentNext0) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairWentiTalentNext> list = ei.getDataList(AffairWentiTalentNext.class);
			//先从数据库查数据
			/*List<affairWentiTalentNext> list1= affairWentiTalentNextDao.findAllList(affairWentiTalentNext0);*/
			for (AffairWentiTalentNext affairWentiTalentNext : list){
				try{
					//使用身份证号查重
					BeanValidators.validateWithException(validator, affairWentiTalentNext);
					/*if (list1.size()!= 0){
					for (affairWentiTalentNext affairWentiTalentNext1 : list1) {
							if (!affairWentiTalentNext.getIdNumber().equals(affairWentiTalentNext1.getIdNumber())) {
								affairWentiTalentNextService.save(affairWentiTalentNext);
								successNum++;

							} else {
								//这个地方要加友好提示
								affairWentiTalentNext.setId(affairWentiTalentNext1.getId());
								affairWentiTalentNextService.save(affairWentiTalentNext);
								successNum++;
							}
						}
					}else{
						affairWentiTalentNextService.save(affairWentiTalentNext);
					}*/
					affairWentiTalentNextService.save(affairWentiTalentNext);
					successNum++;

				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+affairWentiTalentNext.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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