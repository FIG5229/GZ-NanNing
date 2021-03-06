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
 * ????????????Controller
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
		addMessage(redirectAttributes, "????????????????????????");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairActivityMienForm";
		//return "redirect:"+Global.getAdminPath()+"/affair/affairActivityMien/?repage";
	}
	
	@RequiresPermissions("affair:affairActivityMien:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairActivityMien affairActivityMien, RedirectAttributes redirectAttributes) {
		affairActivityMienService.delete(affairActivityMien);
		addMessage(redirectAttributes, "????????????????????????");
		return "redirect:"+Global.getAdminPath()+"/affair/affairActivityMien/?repage";
	}
	/**
	 * ????????????
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
					failureMsg.append(affairActivityMien.getName()+"(????????????:"+")"+" ???????????????"+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "????????? "+failureNum+" ???????????????????????????");
			}
			addMessage(redirectAttributes, "??????????????? "+successNum+" ???"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "??????????????????????????????"+e.getMessage());
		}
		//redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view?id=affair_activityMien";
	}
	/**
	 * ??????excel????????????
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
			addMessage(redirectAttributes, "????????????????????????????????????????????????"+ex);
		}
		return "redirect:" + adminPath + "/affair/affairActivityMien?repage";
	}
	/**
	 * ????????????
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"submitByIds"})
	public String submitByIds(HttpServletRequest request) {
		User user = UserUtils.getUser();
		//????????????????????????ids
		String idsStr = request.getParameter("ids");
		//??????????????????
		String[] idsArray = idsStr.split(",");
		//???????????????
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
	* ????????????
	* */
	@RequiresPermissions("affair:affairActivityMien:shenhe")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairActivityMienCheckDialog";
	}

	/*
	* ??????
	* @param affairActivityMien
	* @param request
	* @return
	* */
	@ResponseBody
	@RequiresPermissions("affair:affairActivityMien:shenhe")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairActivityMien affairActivityMien, HttpServletRequest request) {
		if (!affairActivityMien.getJuCheckId().isEmpty()) {
			//????????????????????????????????????,?????????juCheckId,???juCheckMan????????????
			String juCheckMan = dictDao.findLabelByValue(affairActivityMien.getJuCheckId());
			affairActivityMien.setJuCheckMan(juCheckMan);
		}
		affairActivityMienService.save(affairActivityMien);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("????????????");
		return result;
	}
	/*
	* ??????
	* @param affairActivityMien
	* @param model
	* @return
	* */
	@RequiresPermissions("affair:affairActivityMien:view")
	@RequestMapping(value = "propelling")
	public String propelling(AffairActivityMien affairActivityMien, Model model) {
		//??????
		Category category = new Category();
		category.setId(affairActivityMien.getId());
		//????????????
		ArticleData articleData = new ArticleData();
		//??????-->????????????????????????
		articleData.setContent(affairActivityMien.getContent());
		//
		Article article = new Article();
		article.setCategory(category);
		//??????????????????
		article.setArticleData(articleData);
		//??????-->??????????????????
		article.setTitle(affairActivityMien.getName());
		//??????-->????????????????????????
		article.setDescription(affairActivityMien.getBrief());
		//????????????-->????????????????????????
		article.setCreateDate(affairActivityMien.getCreateDate());
		//??????-->??????
		article.setAppendfile("/politics"+affairActivityMien.getAppendfile());
		model.addAttribute("article", article);
		return "modules/affair/affairActivityMienPropelling";
	}

}