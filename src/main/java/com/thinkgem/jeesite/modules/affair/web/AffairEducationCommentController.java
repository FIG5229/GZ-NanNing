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
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairEducationComment;
import com.thinkgem.jeesite.modules.affair.service.AffairEducationCommentService;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
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
import java.util.Map;

/**
 * 团员教育评议Controller
 * @author cecil.li
 * @version 2019-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairEducationComment")
public class AffairEducationCommentController extends BaseController {

	@Autowired
	private DictDao dictDao;
	@Autowired
	private AffairEducationCommentService affairEducationCommentService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairEducationComment get(@RequestParam(required=false) String id) {
		AffairEducationComment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairEducationCommentService.get(id);
		}
		if (entity == null){
			entity = new AffairEducationComment();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairEducationComment:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairEducationComment affairEducationComment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairEducationComment> page = affairEducationCommentService.findPage(new Page<AffairEducationComment>(request, response), affairEducationComment); 
		model.addAttribute("page", page);
		// 34 南宁处  95  柳州处  156 北海处  1 南宁局
		String unitId = UserUtils.getUser().getOffice().getId();
		String companyId = UserUtils.getUser().getCompany().getId();
		String id = "";
		String label = "";
		if(("34".equals(unitId)||"34".equals(companyId))&&!unitId.equals("33")){
			id = "28f59642a1e74d0588f0d515fe462775";
			label = "南宁处团委";
		}
		if(("156".equals(unitId)||"156".equals(companyId))&&!unitId.equals("268")){
			id = "78d0e07ed2e14ca0b6c73e14c11f4d55";
			label = "北海处团委";
		}
		if(("95".equals(unitId)||"95".equals(companyId))&&!unitId.equals("148")){
			id = "11d94fe57ede47a9bae4bffa36af487c";
			label = "柳州处团委";
		}
		if(("1".equals(unitId)||"1".equals(companyId)||unitId.equals("148")||unitId.equals("268")||unitId.equals("33"))&& !"95".equals(unitId) && !"156".equals(unitId) && !"34".equals(unitId)){
			id = "ff7f9fe2597b40429ded58f8b76a2f65";
			label = "南宁局团委";
		}
		model.addAttribute("lable",label);
		model.addAttribute("idValue",id);
		return "modules/affair/affairEducationCommentList";
	}

	@RequiresPermissions("affair:affairEducationComment:view")
	@RequestMapping(value = "form")
	public String form(AffairEducationComment affairEducationComment, Model model) {
		model.addAttribute("affairEducationComment", affairEducationComment);
		return "modules/affair/affairEducationCommentForm";
	}

	@RequiresPermissions("affair:affairEducationComment:edit")
	@RequestMapping(value = "save")
	public String save(AffairEducationComment affairEducationComment, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairEducationComment)){
			return form(affairEducationComment, model);
		}
		affairEducationCommentService.save(affairEducationComment);
		addMessage(redirectAttributes, "保存团员教育评议成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairEducationCommentForm";
	}
	
	@RequiresPermissions("affair:affairEducationComment:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairEducationComment affairEducationComment, RedirectAttributes redirectAttributes) {
		affairEducationCommentService.delete(affairEducationComment);
		addMessage(redirectAttributes, "删除团员教育评议成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairEducationComment/?repage";
	}
	/**
	 * 详情
	 * @param affairEducationComment
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairEducationComment:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairEducationComment affairEducationComment, Model model) {
		model.addAttribute("affairEducationComment", affairEducationComment);
		if(affairEducationComment.getFilePath() != null && affairEducationComment.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairEducationComment.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairEducationCommentFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairEducationComment:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairEducationCommentService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	/**
	 * 批量提交
	 * @param
	 * @return
	 */
	@RequestMapping(value = {"submitByIds"})
	public String submitByIds(HttpServletRequest request) {
        User user = UserUtils.getUser();
        //接受前台传过来的ids
        String idsStr = request.getParameter("ids");
        //字符串转数组
        String[] idsArray = idsStr.split(",");
        //数组转集合
        List<String> userList = new ArrayList<String>();

        String oneCheckId = request.getParameter("oneCheckId");
        String oneCheckMan = dictDao.findLabelByValue(oneCheckId);
        Collections.addAll(userList,idsArray);
		List <AffairEducationComment> list = affairEducationCommentService.findByIds(userList);
        for (AffairEducationComment affairEducationComment: list){
            affairEducationComment.setCheckType("2");
            affairEducationComment.setOneCheckMan(oneCheckMan);
            affairEducationComment.setOneCheckId(oneCheckId);
            affairEducationComment.setSubmitId(user.getId());
            affairEducationComment.setSubmitMan(user.getName());
            affairEducationCommentService.save(affairEducationComment);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairEducationComment/list";
	}
	@RequiresPermissions("affair:affairEducationComment:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairEducationCommentCheckDialog";
	}
	//这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairEducationComment:manage")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairEducationComment affairEducationComment, HttpServletRequest request) {
		if (StringUtils.isNotBlank(affairEducationComment.getTwoCheckId())){
			affairEducationComment.setTwoCheckMan(dictDao.findLabelByValue(affairEducationComment.getTwoCheckId()));
		}
		affairEducationCommentService.save(affairEducationComment);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
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
	public String exportExcelByTemplate(AffairEducationComment affairEducationComment, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairEducationComment> page = null;
			if(flag == true){
				page = affairEducationCommentService.findPage(new Page<AffairEducationComment>(request, response), affairEducationComment);
			}else {
				page = affairEducationCommentService.findPage(new Page<AffairEducationComment>(request, response,-1), affairEducationComment);
			}
/*
			Page<AffairEducationComment> page = affairEducationCommentService.findPage(new Page<AffairEducationComment>(request, response,-1), affairEducationComment);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairEducationComment.class);
			exportExcelNew.setWb(wb);
			List<AffairEducationComment> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairEducationComment/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairEducationComment> list = ei.getDataList(AffairEducationComment.class);
			for (AffairEducationComment affairEducationComment : list){
				try{
					BeanValidators.validateWithException(validator, affairEducationComment);
					affairEducationCommentService.save(affairEducationComment);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+affairEducationComment.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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


	@RequestMapping(value = {"echart/educationComment"})
	public String educationComment(AffairEducationComment affairEducationComment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairEducationComment> page = affairEducationCommentService.findEducationCommentPage(new Page<AffairEducationComment>(request, response), affairEducationComment);
		model.addAttribute("page", page);
		return "modules/charts/chartsEducationCommentList";
	}
}