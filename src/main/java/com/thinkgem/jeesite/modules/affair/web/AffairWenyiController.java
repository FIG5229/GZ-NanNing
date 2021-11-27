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
import com.thinkgem.jeesite.modules.affair.entity.AffairWenyi;
import com.thinkgem.jeesite.modules.affair.service.AffairWenyiService;
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
 * 文艺作品Controller
 * @author kevin.jia
 * @version 2020-08-03
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairWenyi")
public class AffairWenyiController extends BaseController {

	@Autowired
	private AffairWenyiService affairWenyiService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private DictDao dictDao;

	@ModelAttribute
	public AffairWenyi get(@RequestParam(required=false) String id) {
		AffairWenyi entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairWenyiService.get(id);
		}
		if (entity == null){
			entity = new AffairWenyi();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairWenyi:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairWenyi affairWenyi, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairWenyi> page = affairWenyiService.findPage(new Page<AffairWenyi>(request, response), affairWenyi); 
		model.addAttribute("page", page);
		return "modules/affair/affairWenyiList";
	}

	@RequiresPermissions("affair:affairWenyi:view")
	@RequestMapping(value = "form")
	public String form(AffairWenyi affairWenyi, Model model) {
		model.addAttribute("affairWenyi", affairWenyi);
		return "modules/affair/affairWenyiForm";
	}
	/**
	 * 查看详情
	 * @param affairWenyi
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairWenyi:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairWenyi affairWenyi, Model model) {
		model.addAttribute("affairWenyi", affairWenyi);
		if(affairWenyi.getAppendfile() != null && affairWenyi.getAppendfile().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairWenyi.getAppendfile());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairWenyiFormDetail";
	}
	@RequiresPermissions("affair:affairWenyi:edit")
	@RequestMapping(value = "save")
	public String save(AffairWenyi affairWenyi, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairWenyi)){
			return form(affairWenyi, model);
		}
		affairWenyiService.save(affairWenyi);
		addMessage(redirectAttributes, "保存文艺作品成功");
		model.addAttribute("saveResult","success");
		return "modules/affair/affairWenyiForm";
	}
	
	@RequiresPermissions("affair:affairWenyi:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairWenyi affairWenyi, RedirectAttributes redirectAttributes) {
		affairWenyiService.delete(affairWenyi);
		addMessage(redirectAttributes, "删除文艺作品成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWenyi/?repage";
	}
	/*
	* 批量删除
	* */
	@ResponseBody
	@RequiresPermissions("affair:affairWenyi:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairWenyiService.deleteByIds(ids);
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
	 * @param affairWenyi
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairWenyi affairWenyi, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairWenyi> page = null;
			if(flag == true){
				page = affairWenyiService.findPage(new Page<AffairWenyi>(request, response), affairWenyi);
			}else{
				page = affairWenyiService.findPage(new Page<AffairWenyi>(request, response,-1), affairWenyi);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairWenyi.class);
			exportExcelNew.setWb(wb);
			List<AffairWenyi> list =page.getList();
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
			addMessage(redirectAttributes, "导出文艺作品列表失败！失败信息："+ex);
		}
		return "redirect:" + adminPath + "/affair/affairWenyi?repage";
	}

	/**
	 * 导入excel数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("affair:affairWenyi:edit")
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairWenyi> list = ei.getDataList(AffairWenyi.class);
			for (AffairWenyi affairWenyi : list){
				try{

					BeanValidators.validateWithException(validator, affairWenyi);
					if (!StringUtils.isEmpty(affairWenyi.getUnitName())){
						String unitId = officeService.findByName(affairWenyi.getUnitName());
						affairWenyi.setUnitId(unitId);
					}
					affairWenyiService.save(affairWenyi);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureNum++;
					failureMsg.append("<br>(作品名称:"+affairWenyi.getProName()+")"+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "从文件中读取到"+list.size()+"条数据，已成功导入 "+successNum+" 条"+failureMsg);

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		//redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view?id=affair_wenyi";
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
		List<String> ids = new ArrayList<String>();
		String chuCheckId = request.getParameter("chuCheckId");
		String chuCheckMan = dictDao.findLabelByValue(chuCheckId);
		Collections.addAll(ids,idsArray);
		/*String companyId = UserUtils.getUser().getId();*/
		List <AffairWenyi> list = affairWenyiService.findByIds(ids);
		for (AffairWenyi affairWenyi: list){
			affairWenyi.setCheckType("2");
			affairWenyi.setChuCheckMan(chuCheckMan);
			affairWenyi.setChuCheckId(chuCheckId);
			affairWenyi.setSubmitId(user.getId());
			affairWenyi.setSubmitMan(user.getName());
			affairWenyiService.save(affairWenyi);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairWenyi/list";
	}

	/*
	 * 审核页面
	 * */
	@RequiresPermissions("affair:affairWenyi:shenhe")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairWenyiCheckDialog";
	}

	/*
	 * 审核
	 * @param affairWenyi
	 * @param request
	 * @return
	 * */
	@ResponseBody
	@RequiresPermissions("affair:affairWenyi:shenhe")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairWenyi affairWenyi, HttpServletRequest request) {
		if (!affairWenyi.getJuCheckId().isEmpty()) {
			//如果审核方式为转为上一级,则通过juCheckId,为juCheckMan字段赋值
			String juCheckMan = dictDao.findLabelByValue(affairWenyi.getJuCheckId());
			affairWenyi.setJuCheckMan(juCheckMan);
		}
		affairWenyiService.save(affairWenyi);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

	/*
	 * 推送
	 * @param affairWenyi
	 * @param model
	 * @return
	 * */
	@RequiresPermissions("affair:affairWenyi:view")
	@RequestMapping(value = "propelling")
	public String propelling(AffairWenyi affairWenyi, Model model) {
		//栏目
		Category category = new Category();
		category.setId(affairWenyi.getId());
		//文章副表
		ArticleData articleData = new ArticleData();
		//内容-->作品内容
		articleData.setContent(affairWenyi.getProContent());
		//
		Article article = new Article();
		article.setCategory(category);
		//设置文章副表
		article.setArticleData(articleData);
		//标题-->作品名称
		article.setTitle(affairWenyi.getProName());
		//摘要-->作品名称，作者姓名
		article.setDescription(affairWenyi.getProName()+"  "+affairWenyi.getPeoName());
		//创建时间-->活动风采创建时间
		article.setCreateDate(affairWenyi.getCreateDate());
		//附件-->附件
		article.setAppendfile("/politics"+affairWenyi.getAppendfile());
		model.addAttribute("article", article);
		return "modules/affair/affairWenyiPropelling";
	}

	@RequestMapping(value = {"literaryWorks"})
	public String literaryWorkDetail(AffairWenyi affairWenyi, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairWenyi> page = affairWenyiService.findLiteraryWorkPage(new Page<AffairWenyi>(request, response), affairWenyi);
		model.addAttribute("page", page);
		return "modules/charts/chartWenyiList";
	}

}