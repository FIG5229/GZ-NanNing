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
import com.thinkgem.jeesite.modules.affair.entity.AffairWenhua;
import com.thinkgem.jeesite.modules.affair.service.AffairWenhuaService;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.ArticleData;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
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
import java.util.*;

/**
 * ????????????Controller
 * @author cecil.li
 * @version 2020-03-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairWenhua")
public class AffairWenhuaController extends BaseController {

	@Autowired
	private AffairWenhuaService affairWenhuaService;
	@Autowired
	private UploadController uploadController;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private DictDao dictDao;


	@ModelAttribute
	public AffairWenhua get(@RequestParam(required=false) String id) {
		AffairWenhua entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairWenhuaService.get(id);
		}
		if (entity == null){
			entity = new AffairWenhua();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairWenhua:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairWenhua affairWenhua, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairWenhua> page = affairWenhuaService.findPage(new Page<AffairWenhua>(request, response), affairWenhua); 
		model.addAttribute("page", page);
		return "modules/affair/affairWenhuaList";
	}

	@RequiresPermissions("affair:affairWenhua:view")
	@RequestMapping(value = "form")
	public String form(AffairWenhua affairWenhua, Model model) {

        String id = affairWenhua.getId();
        String name = affairWenhuaService.selectName(id);
        List<String> strings = affairWenhuaService.findusernameList(name);
        affairWenhua.setLeave(strings);

        String workSituation = affairWenhua.getWorkSituation();
        List<String> strings1 = affairWenhuaService.fingproductionList(workSituation);

        affairWenhua.setAwards(strings1);

        /*String manuscriptList = affairWenhuaService.findManuscriptList(name);
        model.addAttribute("manuscriptList",manuscriptList);*/

        model.addAttribute("affairWenhua", affairWenhua);
		return "modules/affair/affairWenhuaForm";
	}

	@RequiresPermissions("affair:affairWenhua:edit")
	@RequestMapping(value = "save")
	public String save(AffairWenhua affairWenhua, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairWenhua)){
			return form(affairWenhua, model);
		}

		affairWenhuaService.save(affairWenhua);
		affairWenhua.getWorkSituation();
		addMessage(redirectAttributes, "????????????????????????");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairWenhuaForm";
	}

	@RequiresPermissions("affair:affairWenhua:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairWenhua affairWenhua, RedirectAttributes redirectAttributes) {
		affairWenhuaService.delete(affairWenhua);
		addMessage(redirectAttributes, "????????????????????????");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWenhua/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairWenhua:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairWenhuaService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("????????????");
		}else{
			result.setSuccess(false);
			result.setMessage("??????????????????????????????");
		}
		return result;
	}

	/**
	 * ??????????????????
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairWenhua:edit")
	@RequestMapping(value = {"findUserNameList"})
	public List<String> findUserNameList(@RequestParam(value = "name") String name){
		List<String> affairWenyis = affairWenhuaService.findusernameList(name);

		return affairWenyis;
	}

	/**
	 * ????????????
	 * @param workSituation
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairWenhua:edit")
	@RequestMapping(value = {"fingproductionList"})
	public List<String> fingproductionList(@RequestParam(value = "workSituation") String workSituation,Model model){
		List<String> affairWenyis = affairWenhuaService.fingproductionList(workSituation);
		model.addAttribute("affairWenyis",affairWenyis);
		return affairWenyis;
	}

	/**
	 * ?????????????????????
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairWenhua:edit")
	@RequestMapping(value = {"findManuscriptList"})
	public String findManuscriptList(@RequestParam(value = "name") String name){
		String manuscriptList = affairWenhuaService.findManuscriptList(name);
		if(manuscriptList != null && manuscriptList.length() > 0 && manuscriptList != ""){
			return manuscriptList;
		}else {
			manuscriptList = "0";
			return manuscriptList;
		}
	}
	/**
	 * ????????????
	 * @param affairWenhua
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairJobTraining:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairWenhua affairWenhua, Model model) {
		model.addAttribute("affairWenhua", affairWenhua);
		if(affairWenhua.getUpdateIdNo() != null && affairWenhua.getUpdateIdNo().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairWenhua.getUpdateIdNo());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairWenhuaFormDetail";
	}

	/**
	 * ??????excel????????????
	 * @param affairWenhua
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairWenhua affairWenhua, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairWenhua> page = null;
			if(flag == true){
				page = affairWenhuaService.findPage(new Page<AffairWenhua>(request, response), affairWenhua);
			}else{
				page = affairWenhuaService.findPage(new Page<AffairWenhua>(request, response,-1), affairWenhua);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairWenhua.class);
			exportExcelNew.setWb(wb);
			List<AffairWenhua> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairWenhua?repage";
	}

	/**
	 * ??????excel??????
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("affair:affairWenhua:edit")
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairWenhua> list = ei.getDataList(AffairWenhua.class);
			for (AffairWenhua affairWenhua : list){
				try{

					BeanValidators.validateWithException(validator, affairWenhua);
					if (!StringUtils.isEmpty(affairWenhua.getUnit())){
						String unitId = officeService.findByName(affairWenhua.getUnit());
						affairWenhua.setUnitId(unitId);
					}

					affairWenhuaService.save(affairWenhua);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairWenhua.getName()+"(????????????:"+")"+" ???????????????"+ex.getMessage());
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
		List <AffairWenhua> list = affairWenhuaService.findByIds(userList);
		for (AffairWenhua affairWenhua: list){
			affairWenhua.setCheckType("2");
			affairWenhua.setChuCheckMan(chuCheckMan);
			affairWenhua.setChuCheckId(chuCheckId);
			affairWenhua.setSubmitId(user.getId());
			affairWenhua.setSubmitMan(user.getName());
			affairWenhuaService.save(affairWenhua);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairWenhua/list";
	}

	/**
	 * ????????????
	 * @return
	 */
	@RequiresPermissions("affair:affairWenhua:shenhe")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairWenhuaCheckDialog";
	}
	@ResponseBody
	@RequiresPermissions("affair:affairWenhua:shenhe")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairWenhua affairWenhua, HttpServletRequest request) {
		if (!affairWenhua.getJuCheckId().isEmpty()) {
			//????????????????????????????????????,?????????juCheckId,???juCheckMan????????????
			String juCheckMan = dictDao.findLabelByValue(affairWenhua.getJuCheckId());
			affairWenhua.setJuCheckMan(juCheckMan);
		}
		affairWenhuaService.save(affairWenhua);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("????????????");
		return result;
	}
	/**
	 * ??????
	 * @param affairWenhua
	 * @param model
	 * @return
	 * */
	@RequiresPermissions("affair:affairWenhua:view")
	@RequestMapping(value = "propelling")
	public String propelling(AffairWenhua affairWenhua, Model model) {
		//??????
		Category category = new Category();
		category.setId(affairWenhua.getId());
		//????????????
		ArticleData articleData = new ArticleData();
		//??????-->????????????
		String name = affairWenhua.getName();
		String specialty = DictUtils.getDictLabel(Optional.ofNullable(affairWenhua.getSpecialty()).orElseGet(() -> ""),"affair_wenyi_specialty","");
		String workSituation = Optional.ofNullable(affairWenhua.getWorkSituation()).orElseGet(() -> "");
		String reward = Optional.ofNullable(affairWenhua.getReward()).orElseGet(() -> "");
		String content = name + specialty + workSituation + reward;
		articleData.setContent(content);
		Article articles = new Article();
		String title=affairWenhua.getName()+"????????????????????????";
		articles.setTitle(title);
		articles.setCategory(category);
		//??????????????????
		articles.setArticleData(articleData);
		//??????-->????????????
		articles.setDescription(affairWenhua.getJoinType());
		articles.setDescription(affairWenhua.getSpecialty());
		articles.setDescription(affairWenhua.getWorkSituation());
		articles.setDescription(affairWenhua.getPublicity());
		//????????????-->????????????
		articles.setCreateDate(affairWenhua.getCreateDate());
		//??????-->??????
		articles.setAppendfile("???");
		model.addAttribute("articles", articles);

		return "modules/affair/affairWenhuaPropelling";
	}

	@RequestMapping(value = {"literaryTalent"})
	public String literaryTalent(AffairWenhua affairWenhua, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairWenhua> page = affairWenhuaService.findLiteraryTalentPage(new Page<AffairWenhua>(request, response), affairWenhua);
		model.addAttribute("page", page);
		model.addAttribute("talent", "literaryTalent");
		return "modules/charts/chartWenhuaList";
	}

	@RequestMapping(value = {"talentJoin"})
	public String talentJoin(AffairWenhua affairWenhua, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairWenhua> page = affairWenhuaService.findTalentJoinPage(new Page<AffairWenhua>(request, response), affairWenhua);
		model.addAttribute("page", page);
		return "modules/charts/chartWenhuaList";
	}

}