/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairActivityMien;
import com.thinkgem.jeesite.modules.affair.entity.AffairWenhua;
import com.thinkgem.jeesite.modules.affair.entity.AffairXunLiDetails;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.ArticleData;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.http.HttpRequest;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairPersonnelMessage;
import com.thinkgem.jeesite.modules.affair.service.AffairPersonnelMessageService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * ??????????????????Controller
 * @author tom.fu
 * @version 2020-08-12
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPersonnelMessage")
public class AffairPersonnelMessageController extends BaseController {

	@Autowired
	private AffairPersonnelMessageService affairPersonnelMessageService;
	@Autowired
	private UploadController uploadController;

	static String personnelIdOne;


	@ModelAttribute
	public AffairPersonnelMessage get(@RequestParam(required=false) String id) {
		AffairPersonnelMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPersonnelMessageService.get(id);
		}
		if (entity == null){
			entity = new AffairPersonnelMessage();
		}
		return entity;
	}
	
	/*@RequiresPermissions("affair:affairPersonnelMessage:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairPersonnelMessage affairPersonnelMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairPersonnelMessage> page = affairPersonnelMessageService.findPage(new Page<AffairPersonnelMessage>(request, response), affairPersonnelMessage); 
		model.addAttribute("page", page);
		return "modules/affair/affairPersonnelMessageList";personnelId
	}*/

	@RequiresPermissions("affair:affairPersonnelMessage:view")
	@RequestMapping(value = "form")
	public String form(AffairPersonnelMessage affairPersonnelMessage, Model model,@RequestParam(value = "personnelId") String personnelId) {
		model.addAttribute("affairPersonnelMessage", affairPersonnelMessage);
		model.addAttribute("personnelId",personnelId);
		return "modules/affair/affairPersonnelMessageForm";
	}

	@RequiresPermissions("affair:affairPersonnelMessage:edit")
	@RequestMapping(value = "save")
	public String save(AffairPersonnelMessage affairPersonnelMessage, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request,String personnelId,HttpSession session,@RequestParam(value = "lookPers")String lookPers) {
		if (!beanValidator(model, affairPersonnelMessage)){
			return form(affairPersonnelMessage, model,personnelId);
		}
		personnelIdOne = request.getParameter("personnelId");
		if (null != personnelIdOne) {
			affairPersonnelMessage.setClassManageId(personnelIdOne);
			session.setAttribute("personnelIdOne",personnelIdOne);
		}
		affairPersonnelMessageService.save(affairPersonnelMessage);
		addMessage(redirectAttributes, "??????????????????????????????");
		request.setAttribute("saveResult","success");
		//return "modules/affair/affairPersonnelMessageList";
		return "redirect:"+Global.getAdminPath()+"/affair/affairClassManage/idListRenYuan?id="+personnelId+"&lookPers="+lookPers;

	}
	
	@RequiresPermissions("affair:affairPersonnelMessage:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPersonnelMessage affairPersonnelMessage, RedirectAttributes redirectAttributes,@RequestParam(value = "personnelId") String personnelId,@RequestParam(value = "lookPers") String lookPers) {
		affairPersonnelMessageService.delete(affairPersonnelMessage);
		addMessage(redirectAttributes, "??????????????????????????????");

        return "redirect:"+Global.getAdminPath()+"/affair/affairClassManage/idListRenYuan?id="+personnelId+"&lookPers="+lookPers;
	}

	/**
	 * ????????????
	 * @param affairPersonnelMessage
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPersonnelMessage:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairPersonnelMessage affairPersonnelMessage, Model model) {
		model.addAttribute("affairPersonnelMessage", affairPersonnelMessage);
		if(affairPersonnelMessage.getUpdateIdNo() != null && affairPersonnelMessage.getUpdateIdNo().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairPersonnelMessage.getUpdateIdNo());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairPersonnelMessageFormDetail";
	}

	/**
	 * ????????????
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairPersonnelMessage:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPersonnelMessageService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("????????????");
		}else{
			result.setSuccess(false);
			result.setMessage("??????????????????????????????");
		}
		return result;
	}


	/**
	 * ??????excel??????
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpSession session) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairPersonnelMessage> list = ei.getDataList(AffairPersonnelMessage.class);
			for (AffairPersonnelMessage affairPersonnelMessage : list){
				try{

					BeanValidators.validateWithException(validator, affairPersonnelMessage);

					String classId = (String) session.getAttribute("classId");

					affairPersonnelMessage.setClassManageId(classId);

					affairPersonnelMessageService.save(affairPersonnelMessage);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairPersonnelMessage.getUsername()+"(???????????????:"+")"+" ???????????????"+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "????????? "+failureNum+" ???????????????????????????");
			}
			addMessage(redirectAttributes, "??????????????? "+successNum+" ???"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "??????????????????????????????"+e.getMessage());
		}
		redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view?repage";
	}
	/**
	 * ??????excel????????????
	 * @param affairPersonnelMessage
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairPersonnelMessage affairPersonnelMessage, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag,@RequestParam(value = "id") String id,Model model) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			List<AffairPersonnelMessage> pageTwo = affairPersonnelMessageService.findPageTwo(id);
			System.out.println(id);

			model.addAttribute("id",id);
			String fileSeperator = File.separator;
			String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
			InputStream inputStream = new FileInputStream(filePath+fileName);
			if (null != inputStream)
			{
				try
				{
					wb = new  XSSFWorkbook(inputStream);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairPersonnelMessage.class);
			exportExcelNew.setWb(wb);
			exportExcelNew.setDataList(pageTwo);
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
			addMessage(redirectAttributes, "????????????????????????????????????????????????"+ex);
		}
		return "redirect:" + adminPath + "/affair/affairPersonnelMessage?repage";
	}




}