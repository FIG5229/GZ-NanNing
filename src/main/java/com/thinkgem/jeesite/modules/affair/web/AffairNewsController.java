/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.dao.AffairNewsAuthorDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairNewsChildDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairNewsNameDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairNewsUnitDao;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.affair.service.AffairNewsService;
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
import java.util.List;
import java.util.Map;

/**
 * 新闻宣传Controller
 * @author cecil.li
 * @version 2019-11-02
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairNews")
public class AffairNewsController extends BaseController {
	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairNewsService affairNewsService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairNewsAuthorDao affairNewsAuthorDao;

	@Autowired
	private AffairNewsUnitDao affairNewsUnitDao;

	@Autowired
	private AffairNewsNameDao affairNewsNameDao;

	@Autowired
	private AffairNewsChildDao affairNewsChildDao;


	@ModelAttribute
	public AffairNews get(@RequestParam(required=false) String id) {
		AffairNews entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairNewsService.get(id);

			AffairNewsAuthor affairNewsAuthor = new AffairNewsAuthor();
			affairNewsAuthor.setNewsId(id);
			List<AffairNewsAuthor> newsAuthorList = affairNewsAuthorDao.findList(affairNewsAuthor);
			entity.setAffairNewsAuthorList(newsAuthorList);

			AffairNewsUnit affairNewsUnit = new AffairNewsUnit();
			affairNewsUnit.setNewsId(id);
			List<AffairNewsUnit> newsUnitList = affairNewsUnitDao.findList(affairNewsUnit);
			entity.setAffairNewsUnitList(newsUnitList);

			AffairNewsName affairNewsName = new AffairNewsName();
			affairNewsName.setNewsId(id);
			List<AffairNewsName> newsNameList = affairNewsNameDao.findList(affairNewsName);
			entity.setAffairNewsNameList(newsNameList);


			/*AffairNewsChild affairNewsChild = new AffairNewsChild();
			affairNewsAuthor.setNewsId(id);
			List<AffairNewsChild> childList = affairNewsChildDao.findList(affairNewsChild);
			entity.setNewsChildList(childList);*/
		}
		if (entity == null){
			entity = new AffairNews();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairNews:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairNews affairNews, HttpServletRequest request, HttpServletResponse response, Model model) {
		if ("5".equals(UserUtils.getUser().getOffice().getId())){
			affairNews.setUnitId(UserUtils.getUser().getCompany().getId());
		}else {
			affairNews.setUnitId(UserUtils.getUser().getOffice().getId());
		}
		affairNews.setCreateBy(UserUtils.getUser());
		Page<AffairNews> page = affairNewsService.findPage(new Page<AffairNews>(request, response), affairNews);
		if(page.getList().size() > 0 && page.getList() != null){
			for (int i = 0; i < page.getList().size(); i++) {
				AffairNews news = page.getList().get(i);
				String authorId = news.getId();
				StringBuffer authors = new StringBuffer();
				List<AffairNewsAuthor> affairNewsList = affairNewsService.findAuthor(authorId);
				if(affairNewsList.size() > 0 && affairNewsList != null) {
					for (int j = 0; j < affairNewsList.size(); j++) {
						String author = affairNewsList.get(j).getNewsAuthor();
						if(!"".equals(author) && author != null) {
							if (j == 0) {
								authors.append(author);
							} else {
								authors.append(","+author);
							}
						}
					}
				}
				StringBuffer units = new StringBuffer();
				List<AffairNewsUnit> affairNewsUnitList = affairNewsService.findUnit(authorId);
				if(affairNewsUnitList.size() > 0 && affairNewsUnitList != null) {
					for (int k = 0; k < affairNewsUnitList.size(); k++) {
						String unit = affairNewsUnitList.get(k).getNewsUnit();
						if(!"".equals(unit) && unit != null) {
							if (k == 0) {
								units.append(unit);
							} else {
								units.append(","+unit);
							}
						}
					}
				}
				news.setAuthor(authors.toString());
				news.setUnit(units.toString());
			}
		}
		model.addAttribute("page", page);
		return "modules/affair/affairNewsList";
	}

	@RequiresPermissions("affair:affairNews:add")
	@RequestMapping(value = "form")
	public String form(AffairNews affairNews, Model model) {
		model.addAttribute("affairNews", affairNews);
		return "modules/affair/affairNewsForm";
	}

	@RequiresPermissions("affair:affairNews:add")
	@RequestMapping(value = "addForm")
	public String addForm(AffairNews affairNews, Model model) {
		model.addAttribute("affairNews", affairNews);
		return "modules/affair/affairNewsAddForm";
	}

	@RequiresPermissions("affair:affairNews:edit")
	@RequestMapping(value = "save")
	public String save(AffairNews affairNews, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairNews)){
			return form(affairNews, model);
		}
		affairNewsService.save(affairNews);
		addMessage(redirectAttributes, "保存新闻成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairNewsForm";
	}
	
	@RequiresPermissions("affair:affairNews:delete")
	@RequestMapping(value = "delete")
	public String delete(AffairNews affairNews, RedirectAttributes redirectAttributes) {
		affairNewsService.delete(affairNews);
		AffairNewsAuthor affairNewsAuthor = new AffairNewsAuthor();
		affairNewsAuthor.setNewsId(affairNews.getId());
		affairNewsAuthorDao.delete(affairNewsAuthor);
		AffairNewsName affairNewsName = new AffairNewsName();
		affairNewsName.setNewsId(affairNews.getId());
		affairNewsNameDao.delete(affairNewsName);
		AffairNewsUnit affairNewsUnit = new AffairNewsUnit();
		affairNewsUnit.setNewsId(affairNews.getId());
		affairNewsUnitDao.delete(affairNewsUnit);
		addMessage(redirectAttributes, "删除新闻成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairNews/?repage";
	}

	/**
	 * 详情
	 * @param affairNews
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairNews:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairNews affairNews, Model model) {
		model.addAttribute("affairNews", affairNews);
		if(affairNews.getFilePath() != null && affairNews.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairNews.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairNewsFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairNews:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairNewsService.deleteByIds(ids);
			affairNewsAuthorDao.deleteByIds(ids);
			affairNewsNameDao.deleteByIds(ids);
			affairNewsUnitDao.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairNews affairNews, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{

			if ("5".equals(UserUtils.getUser().getOffice().getId())){
				affairNews.setUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairNews.setUnitId(UserUtils.getUser().getOffice().getId());
			}
			affairNews.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairNews> page = null;
			if(flag == true){
				page = affairNewsService.findPage(new Page<AffairNews>(request, response), affairNews);
			}else{
				page = affairNewsService.findPage(new Page<AffairNews>(request, response,-1), affairNews);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairNews.class);
			exportExcelNew.setWb(wb);
			List<AffairNews> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairNews/?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<AffairNews> list = ei.getDataList(AffairNews.class);
			for (AffairNews affairNews : list){
				try{
					affairNews.setUnitId(officeService.findByName(affairNews.getUnit()));
					BeanValidators.validateWithException(validator, affairNews);
					affairNewsService.save(affairNews);
					if(affairNews.getName()!=null || affairNews.getAuthor() != null || affairNews.getUnit() != null) {
						if (affairNews.getUnit() != null) {//单位
							AffairNewsUnit affairNewsUnit = new AffairNewsUnit();
							String unit = affairNews.getUnit();
							unit=unit.replace('，',',');
							String[] str = unit.split(",");
							for (String s : str) {
								affairNewsUnit.setNewsId(affairNews.getId());
								affairNewsUnit.setNewsUnit(s.trim());
								String unitId = affairNewsService.findUnitId(s.trim());
								affairNewsUnit.setNewsUnitId(unitId);
								IdGen idGen = new IdGen();
								String id = idGen.getNextId();
								affairNewsUnit.setId(id);
								affairNewsUnitDao.insert(affairNewsUnit);
							}
						}
						if (affairNews.getAuthor() != null) {//作者
							AffairNewsAuthor affairNewsAuthor = new AffairNewsAuthor();

							String author = affairNews.getAuthor();
							//测试作者（852085208520852096），测试作者二（85208520852085209X）,.......
							//测试作者（852085208520852096）测试作者二（85208520852085209X）
							String name = "";
							String concat = "";
							author=author.replace('，',',');
							String[] str = author.split(",");
							for (String s : str) {
								String idNumber = "";
								if (s != null && !"".equals(s)) {
									for (int i = 0; i < s.length(); i++) {
										if (s.charAt(i) >= 48 && s.charAt(i) <= 57) {
											idNumber += s.charAt(i);
										}
									}
								}
								//身份中的英文字符
								String idNumberLetter = s.replaceAll("\\s*", "").replaceAll("[^(A-Za-z)]", "");
								concat = idNumber.concat(idNumberLetter);
								name = s.replaceAll("[^\\u4e00-\\u9fa5]", "");

								affairNewsAuthor.setNewsAuthor(name);
								affairNewsAuthor.setIdNumber(concat);
								affairNewsAuthor.setNewsId(affairNews.getId());
								IdGen idGen = new IdGen();
								String id = idGen.getNextId();
								affairNewsAuthor.setId(id);
								affairNewsAuthorDao.insert(affairNewsAuthor);
							}
						}
						if (affairNews.getName() != null) {//所属人
							AffairNewsName affairNewsName = new AffairNewsName();
							String name = affairNews.getName();
							String nameO = "";
							String concat = "";
							name=name.replace('，',',');
							String[] str = name.split(",");
							for (String s : str) {
								String idNumber = "";
								if (s != null && !"".equals(s)) {
									for (int i = 0; i < s.length(); i++) {
										if (s.charAt(i) >= 48 && s.charAt(i) <= 57) {
											idNumber += s.charAt(i);
										}
									}
								}
								//身份中的英文字符
								String idNumberLetter = s.replaceAll("\\s*", "").replaceAll("[^(A-Za-z)]", "");
								concat = idNumber.concat(idNumberLetter);
								nameO = s.replaceAll("[^\\u4e00-\\u9fa5]", "");

								affairNewsName.setNewsName(nameO);
								affairNewsName.setNewsId(affairNews.getId());
								affairNewsName.setNewsIdNumber(concat);
								IdGen idGen = new IdGen();
								String id = idGen.getNextId();
								affairNewsName.setId(id);
								affairNewsNameDao.insert(affairNewsName);
							}
						}
					}
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

	@RequestMapping(value = {"getPersonByName"})
	@ResponseBody
	public Result findPersonByName(String name){

		AffairCorrespondent param = new AffairCorrespondent();
		param.setName(name);
		List<AffairCorrespondent> list = affairNewsService.findPerson(param);
		Result result = new Result();
		if(list != null && list.size()> 0){
			result.setResult(list);
			result.setSuccess(true);
		}else{
			result.setSuccess(false);
		}
		return result;
	}

	@RequestMapping(value = "news")
	public String findEchartsDetailInfo(AffairNews affairNews, HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam("typr") String typr){

		if("中央级（中央电视台、新华社/中新社、中央人民广播电台、中央级报纸）".equals(typr)){
			affairNews.setTypr("1");
		}else if("省部级（省卫视、专题、其他）".equals(typr)){
			affairNews.setTypr("2");
		}else if("地市级".equals(typr)){
			affairNews.setTypr("3");
		}else if("各大网络".equals(typr)){
			affairNews.setTypr("4");
		}else if("新媒体平台".equals(typr)){
			affairNews.setTypr("5");
		}
		Page<AffairNews> page;
		page = affairNewsService.findEchartsDetailInfo(new Page<AffairNews>(request, response), affairNews);
		if(page.getList().size() > 0 && page.getList() != null){
			for (int i = 0; i < page.getList().size(); i++) {
				//获取子表中的作者名称，
				AffairNews news = page.getList().get(i);
				String authorId = news.getId();
				StringBuffer authors = new StringBuffer();
				List<AffairNewsAuthor> affairNewsList = affairNewsService.findAuthor(authorId);
				if(affairNewsList.size() > 0 && affairNewsList != null) {
					for (int j = 0; j < affairNewsList.size(); j++) {
						String author = affairNewsList.get(j).getNewsAuthor();
						if(!"".equals(author) && author != null) {
							if (j == 0) {
								authors.append(author);
							} else {
								authors.append(","+author);
							}
						}
					}
				}
				news.setAuthor(authors.toString());
			}
		}

		model.addAttribute("page", page);
		model.addAttribute("affairNews", affairNews);
		return "modules/charts/publicityNewsDetail";
	}

}