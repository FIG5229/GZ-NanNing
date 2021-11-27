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
import com.thinkgem.jeesite.modules.affair.entity.AffairActivityMien;
import com.thinkgem.jeesite.modules.affair.service.AffairActivityMienService;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.ArticleData;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
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
import java.util.Map;

/**
 * 活动风采Controller
 * @author kevin.jia
 * @version 2020-07-23
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairActivityMien")
public class AffairActivityMienController extends BaseController {

	@Autowired
	private AffairActivityMienService affairActivityMienService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private DictDao dictDao;

	@ModelAttribute
	public AffairActivityMien get(@RequestParam(required=false) String id) {
		AffairActivityMien entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairActivityMienService.get(id);
		}
		if (entity == null){
			entity = new AffairActivityMien();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairActivityMien:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairActivityMien affairActivityMien, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairActivityMien> page = affairActivityMienService.findPage(new Page<AffairActivityMien>(request, response), affairActivityMien);
		model.addAttribute("page", page);
		return "modules/affair/affairActivityMienList";
	}

	@RequiresPermissions("affair:affairActivityMien:view")
	@RequestMapping(value = "form")
	public String form(AffairActivityMien affairActivityMien, Model model) {
		model.addAttribute("affairActivityMien", affairActivityMien);
		return "modules/affair/affairActivityMienForm";
	}

	@RequiresPermissions("affair:affairActivityMien:edit")
	@RequestMapping(value = "save")
	public String save(AffairActivityMien affairActivityMien, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairActivityMien)){
			return form(affairActivityMien, model);
		}
		affairActivityMienService.save(affairActivityMien);
		addMessage(redirectAttributes, "保存活动风采成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairActivityMienForm";
		//return "redirect:"+Global.getAdminPath()+"/affair/affairActivityMien/?repage";
	}
	
	@RequiresPermissions("affair:affairActivityMien:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairActivityMien affairActivityMien, RedirectAttributes redirectAttributes) {
		affairActivityMienService.delete(affairActivityMien);
		addMessage(redirectAttributes, "删除活动风采成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairActivityMien/?repage";
	}
	/**
	 * 查看详情
	 * @param affairActivityMien
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairActivityMien:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairActivityMien affairActivityMien, Model model) {
		model.addAttribute("affairActivityMien", affairActivityMien);
		if(affairActivityMien.getAppendfile() != null && affairActivityMien.getAppendfile().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairActivityMien.getAppendfile());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairActivityMienFormDetail";
	}
	@ResponseBody
	@RequiresPermissions("affair:affairActivityMien:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairActivityMienService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
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
			List<AffairActivityMien> list = ei.getDataList(AffairActivityMien.class);
			for (AffairActivityMien affairActivityMien : list){
				try{

					BeanValidators.validateWithException(validator, affairActivityMien);
					if (!StringUtils.isEmpty(affairActivityMien.getUnit())){
						String unitId = officeService.findByName(affairActivityMien.getUnit());
						affairActivityMien.setUnitId(unitId);
					}
					affairActivityMienService.save(affairActivityMien);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairActivityMien.getName()+"(活动名称:"+")"+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		//redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view?id=affair_activityMien";
	}
	/**
	 * 导出excel格式数据
	 * @param affairActivityMien
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairActivityMien affairActivityMien, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairActivityMien> page = null;
			if(flag == true){
				page = affairActivityMienService.findPage(new Page<AffairActivityMien>(request, response), affairActivityMien);
			}else{
				page = affairActivityMienService.findPage(new Page<AffairActivityMien>(request, response,-1), affairActivityMien);
			}
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairActivityMien.class);
			exportExcelNew.setWb(wb);
			List<AffairActivityMien> list =page.getList();
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
			addMessage(redirectAttributes, "导出活动风采列表失败！失败信息："+ex);
		}
		return "redirect:" + adminPath + "/affair/affairActivityMien?repage";
	}
	/**
	 * 批量提交
	 * @param request
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
		String chuCheckId = request.getParameter("chuCheckId");
		String chuCheckMan = dictDao.findLabelByValue(chuCheckId);
		Collections.addAll(userList,idsArray);
		/*String companyId = UserUtils.getUser().getId();*/
		List <AffairActivityMien> list = affairActivityMienService.findByIds(userList);
		for (AffairActivityMien affairActivityMien: list){
			affairActivityMien.setCheckType("2");
			affairActivityMien.setChuCheckMan(chuCheckMan);
			affairActivityMien.setChuCheckId(chuCheckId);
			affairActivityMien.setSubmitId(user.getId());
			affairActivityMien.setSubmitMan(user.getName());
			affairActivityMienService.save(affairActivityMien);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairActivityMien/list";
	}

	/*
	* 审核页面
	* */
	@RequiresPermissions("affair:affairActivityMien:shenhe")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairActivityMienCheckDialog";
	}

	/*
	* 审核
	* @param affairActivityMien
	* @param request
	* @return
	* */
	@ResponseBody
	@RequiresPermissions("affair:affairActivityMien:shenhe")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairActivityMien affairActivityMien, HttpServletRequest request) {
		if (!affairActivityMien.getJuCheckId().isEmpty()) {
			//如果审核方式为转为上一级,则通过juCheckId,为juCheckMan字段赋值
			String juCheckMan = dictDao.findLabelByValue(affairActivityMien.getJuCheckId());
			affairActivityMien.setJuCheckMan(juCheckMan);
		}
		affairActivityMienService.save(affairActivityMien);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}
	/*
	* 推送
	* @param affairActivityMien
	* @param model
	* @return
	* */
	@RequiresPermissions("affair:affairActivityMien:view")
	@RequestMapping(value = "propelling")
	public String propelling(AffairActivityMien affairActivityMien, Model model) {
		//栏目
		Category category = new Category();
		category.setId(affairActivityMien.getId());
		//文章副表
		ArticleData articleData = new ArticleData();
		//内容-->活动风采主要内容
		articleData.setContent(affairActivityMien.getContent());
		//
		Article article = new Article();
		article.setCategory(category);
		//设置文章副表
		article.setArticleData(articleData);
		//标题-->活动风采名称
		article.setTitle(affairActivityMien.getName());
		//摘要-->活动风采简要情况
		article.setDescription(affairActivityMien.getBrief());
		//创建时间-->活动风采创建时间
		article.setCreateDate(affairActivityMien.getCreateDate());
		//附件-->附件
		article.setAppendfile("/politics"+affairActivityMien.getAppendfile());
		model.addAttribute("article", article);
		return "modules/affair/affairActivityMienPropelling";
	}

}