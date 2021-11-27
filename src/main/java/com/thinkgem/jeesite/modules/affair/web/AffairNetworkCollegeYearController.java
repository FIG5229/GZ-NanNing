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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairLearnPowerYear;
import com.thinkgem.jeesite.modules.affair.entity.AffairNetworkCollegeYear;
import com.thinkgem.jeesite.modules.affair.service.AffairNetworkCollegeService;
import com.thinkgem.jeesite.modules.affair.service.AffairNetworkCollegeYearService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import java.util.Calendar;
import java.util.List;

/**
 * 中国干部网络学院--年度Controller
 * @author alan.wu
 * @version 2020-08-03
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairNetworkCollegeYear")
public class AffairNetworkCollegeYearController extends BaseController {

	@Autowired
	private AffairNetworkCollegeYearService affairNetworkCollegeYearService;

	@Autowired
	private AffairNetworkCollegeService affairNetworkCollegeService;
	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AffairNetworkCollegeYear get(@RequestParam(required=false) String id) {
		AffairNetworkCollegeYear entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairNetworkCollegeYearService.get(id);
		}
		if (entity == null){
			entity = new AffairNetworkCollegeYear();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairNetworkCollegeYear:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairNetworkCollegeYear affairNetworkCollegeYear1, HttpServletRequest request, HttpServletResponse response, Model model) {
		String userId = "";
		String unitId = "";
		String name1 = affairNetworkCollegeYear1.getName();
		String name = affairNetworkCollegeYear1.getName();
		String yearStr = request.getParameter("year");
		int year;
		if(StringUtils.isNotBlank(yearStr)){
			year = Integer.parseInt(yearStr);
		}else{
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		List<AffairNetworkCollegeYear> list = new ArrayList<>();
		if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
			unitId = UserUtils.getUser().getCompany().getId();
		}else {
			unitId = UserUtils.getUser().getOffice().getId();
		}
		AffairNetworkCollegeYear affairNetworkCollegeYear = new AffairNetworkCollegeYear();
		affairNetworkCollegeYear.setYear(year);
		affairNetworkCollegeYear.setLastYear(year-1);
		affairNetworkCollegeYear.setUnitId(unitId);
		affairNetworkCollegeYear.setName(name);
		affairNetworkCollegeYear.setTime(String.valueOf(year));
		Page<AffairNetworkCollegeYear> page = affairNetworkCollegeYearService.findListByYear(new Page<AffairNetworkCollegeYear>(request,response),affairNetworkCollegeYear,year);
		model.addAttribute("page",page);
		model.addAttribute("year",year);
		/*if (StringUtils.isBlank(name1)){
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			//今年年份
			String ye = String.valueOf(year);

			//去年年份
			int lyear = year - 1;
			String lastYear = String.valueOf(lyear);

			List<AffairNetworkCollegeYear> list = new ArrayList<>();
			if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
				userId = UserUtils.getUser().getCompany().getId();
			}else {
				userId = UserUtils.getUser().getOffice().getId();
			}
			//所有的身份证
			List<String> idList = new ArrayList<>();
			idList = affairNetworkCollegeService.selectAllId(userId);
			for (int i = 0; i< idList.size(); i++){
				AffairNetworkCollegeYear affairNetworkCollegeYear = new AffairNetworkCollegeYear();
				affairNetworkCollegeYear.setTime(ye);
				String id = idList.get(i);
				String unit = affairNetworkCollegeService.selectUnit(id);
				String name = affairNetworkCollegeService.selectName(id);
				affairNetworkCollegeYear.setUnit(unit);
				affairNetworkCollegeYear.setIdNumber(id);
				affairNetworkCollegeYear.setName(name);

				//今年分数
				String thisYear = affairNetworkCollegeService.selectBean(id,ye);
				Integer thisYearScore ;
				if(StringUtils.isNotBlank(thisYear)){
					thisYearScore = Integer.valueOf(thisYear);
				}else{
					thisYearScore = 0;
				}
				Double tyScore = Double.valueOf(thisYearScore);
				affairNetworkCollegeYear.setThisYearIntegral(tyScore);
				//去年分数
				String lastYearScore = affairNetworkCollegeService.selectBean(id,lastYear);
				if (StringUtils.isNotBlank(lastYearScore)){
					Integer laYearScore = Integer.valueOf(lastYearScore);
					Double lyScore = Double.valueOf(laYearScore);
					affairNetworkCollegeYear.setLastYearIntegral(lyScore);

					//今年和去年一共的分数
					Double sumScore = tyScore + lyScore;
					affairNetworkCollegeYear.setThisYearStatistics(sumScore);
				}
				else{
					affairNetworkCollegeYear.setThisYearStatistics(tyScore);
				}

				list.add(affairNetworkCollegeYear);
			}

			*//*Page<affairNetworkCollegeYear> page = affairNetworkCollegeYearService.findPage(new Page<affairNetworkCollegeYear>(request, response), affairNetworkCollegeYear);*//*
			model.addAttribute("list", list);
		}else if (StringUtils.isNotBlank(name1)){

			List<AffairNetworkCollegeYear> list = new ArrayList<>();

			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			//今年年份
			String ye = String.valueOf(year);

			//去年年份
			int lyear = year - 1;
			String lastYear = String.valueOf(lyear);

			//身份证号
			String idNumber = affairNetworkCollegeService.selectIdNumber(name1);

			if (idNumber == null){
				return "modules/affair/affairNetworkCollegeYearList";
			}

			AffairNetworkCollegeYear affairNetworkCollegeYear = new AffairNetworkCollegeYear();
			affairNetworkCollegeYear.setTime(ye);

			String unit = affairNetworkCollegeService.selectUnit(idNumber);
			String name = affairNetworkCollegeService.selectName(idNumber);
			affairNetworkCollegeYear.setUnit(unit);
			affairNetworkCollegeYear.setIdNumber(idNumber);
			affairNetworkCollegeYear.setName(name);

			//今年分数
			String thisYear = affairNetworkCollegeService.selectBean(idNumber,ye);
			Integer thisYearScore = Integer.valueOf(thisYear);
			Double tyScore = Double.valueOf(thisYearScore);
			affairNetworkCollegeYear.setThisYearIntegral(tyScore);
			//去年分数
			String lastYearScore = affairNetworkCollegeService.selectBean(idNumber,lastYear);
			if (StringUtils.isNotBlank(lastYearScore)){
				Integer laYearScore = Integer.valueOf(lastYearScore);
				Double lyScore = Double.valueOf(laYearScore);
				affairNetworkCollegeYear.setLastYearIntegral(lyScore);

				//今年和去年一共的分数
				Double sumScore = tyScore + lyScore;
				affairNetworkCollegeYear.setThisYearStatistics(sumScore);
			}
			else{
				affairNetworkCollegeYear.setThisYearStatistics(tyScore);
			}
			list.add(affairNetworkCollegeYear);
			model.addAttribute("list", list);

		}*/
		return "modules/affair/affairNetworkCollegeYearList";
	}

	@RequiresPermissions("affair:affairNetworkCollegeYear:view")
	@RequestMapping(value = "form")
	public String form(AffairNetworkCollegeYear affairNetworkCollegeYear, Model model) {
		model.addAttribute("affairNetworkCollegeYear", affairNetworkCollegeYear);
		return "modules/affair/affairNetworkCollegeYearForm";
	}

	@RequiresPermissions("affair:affairNetworkCollegeYear:edit")
	@RequestMapping(value = "save")
	public String save(AffairNetworkCollegeYear affairNetworkCollegeYear, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairNetworkCollegeYear)){
			return form(affairNetworkCollegeYear, model);
		}
		affairNetworkCollegeYearService.save(affairNetworkCollegeYear);
		addMessage(redirectAttributes, "保存中国干部网络学院--年度成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairNetworkCollegeYear/?repage";
	}
	
	@RequiresPermissions("affair:affairNetworkCollegeYear:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairNetworkCollegeYear affairNetworkCollegeYear, RedirectAttributes redirectAttributes) {
		affairNetworkCollegeYearService.delete(affairNetworkCollegeYear);
		addMessage(redirectAttributes, "删除中国干部网络学院--年度成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairNetworkCollegeYear/?repage";
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
	public String exportExcelByTemplate(AffairNetworkCollegeYear affairNetworkCollegeYear, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String unitId = "";
			if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
				unitId=UserUtils.getUser().getCompany().getId();
			}else {
				unitId=UserUtils.getUser().getOffice().getId();
			}
			affairNetworkCollegeYear.setUnitId(unitId);
			Page<AffairNetworkCollegeYear> page = null;
			if(flag == true){
				page = affairNetworkCollegeYearService.findListByYear(new Page<AffairNetworkCollegeYear>(request, response), affairNetworkCollegeYear,affairNetworkCollegeYear.getYear());
			}else{
				page = affairNetworkCollegeYearService.findListByYear(new Page<AffairNetworkCollegeYear>(request, response,-1), affairNetworkCollegeYear,affairNetworkCollegeYear.getYear());
			}
			/*if ("5".equals(UserUtils.getUser().getOffice().getId())){
				affairNetworkCollegeYear.setUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairNetworkCollegeYear.setUnitId(UserUtils.getUser().getOffice().getId());
			}*/
			affairNetworkCollegeYear.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}


			/*String userId = "";
			String name1 = affairNetworkCollegeYear.getName();

			if (StringUtils.isBlank(name1)){
				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				//今年年份
				String ye = String.valueOf(year);

				//去年年份
				int lyear = year - 1;
				String lastYear = String.valueOf(lyear);

				List<AffairNetworkCollegeYear> list = new ArrayList<>();
				if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
					userId = UserUtils.getUser().getCompany().getId();
				}else {
					userId = UserUtils.getUser().getOffice().getId();
				}
				//所有的身份证
				List<String> idList = new ArrayList<>();
				idList = affairNetworkCollegeService.selectAllId(userId);
				for (int i = 0; i< idList.size(); i++){
					AffairNetworkCollegeYear affairNetworkCollegeYear1 = new AffairNetworkCollegeYear();
					affairNetworkCollegeYear1.setTime(ye);
					String id = idList.get(i);
					String unit = affairNetworkCollegeService.selectUnit(id);
					String name = affairNetworkCollegeService.selectName(id);
					affairNetworkCollegeYear1.setUnit(unit);
					affairNetworkCollegeYear1.setIdNumber(id);
					affairNetworkCollegeYear1.setName(name);

					//今年分数
					String thisYear = affairNetworkCollegeService.selectBean(id,ye);
					Integer thisYearScore = Integer.valueOf(thisYear);
					Double tyScore = Double.valueOf(thisYearScore);
					affairNetworkCollegeYear1.setThisYearIntegral(tyScore);
					//去年分数
					String lastYearScore = affairNetworkCollegeService.selectBean(id,lastYear);
					if (StringUtils.isNotBlank(lastYearScore)){
						Integer laYearScore = Integer.valueOf(lastYearScore);
						Double lyScore = Double.valueOf(laYearScore);
						affairNetworkCollegeYear1.setLastYearIntegral(lyScore);

						//今年和去年一共的分数
						Double sumScore = tyScore + lyScore;
						affairNetworkCollegeYear1.setThisYearStatistics(sumScore);
					}
					else{
						affairNetworkCollegeYear1.setThisYearStatistics(tyScore);
					}

					list.add(affairNetworkCollegeYear1);
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
					ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairLearnPowerYear.class);
					exportExcelNew.setWb(wb);
					exportExcelNew.setDataList(list);
				}

			}
			else if (StringUtils.isNotBlank(name1)){

				List<AffairNetworkCollegeYear> list = new ArrayList<>();

				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				//今年年份
				String ye = String.valueOf(year);

				//去年年份
				int lyear = year - 1;
				String lastYear = String.valueOf(lyear);

				//身份证号
				String idNumber = affairNetworkCollegeService.selectIdNumber(name1);

				if (idNumber == null){
					return "modules/affair/affairNetworkCollegeYearList";
				}

				AffairNetworkCollegeYear affairNetworkCollegeYear1 = new AffairNetworkCollegeYear();
				affairNetworkCollegeYear1.setTime(ye);

				String unit = affairNetworkCollegeService.selectUnit(idNumber);
				String name = affairNetworkCollegeService.selectName(idNumber);
				affairNetworkCollegeYear1.setUnit(unit);
				affairNetworkCollegeYear1.setIdNumber(idNumber);
				affairNetworkCollegeYear1.setName(name);

				//今年分数
				String thisYear = affairNetworkCollegeService.selectBean(idNumber,ye);
				Integer thisYearScore = Integer.valueOf(thisYear);
				Double tyScore = Double.valueOf(thisYearScore);
				affairNetworkCollegeYear1.setThisYearIntegral(tyScore);
				//去年分数
				String lastYearScore = affairNetworkCollegeService.selectBean(idNumber,lastYear);
				if (StringUtils.isNotBlank(lastYearScore)){
					Integer laYearScore = Integer.valueOf(lastYearScore);
					Double lyScore = Double.valueOf(laYearScore);
					affairNetworkCollegeYear1.setLastYearIntegral(lyScore);

					//今年和去年一共的分数
					Double sumScore = tyScore + lyScore;
					affairNetworkCollegeYear1.setThisYearStatistics(sumScore);
				}
				else{
					affairNetworkCollegeYear1.setThisYearStatistics(tyScore);
				}
				list.add(affairNetworkCollegeYear1);
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
				ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairNetworkCollegeYear.class);
				exportExcelNew.setWb(wb);

				exportExcelNew.setDataList(list);
			}*/
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairNetworkCollegeYear.class);
			exportExcelNew.setWb(wb);

			exportExcelNew.setDataList(page.getList());
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
			addMessage(redirectAttributes, "导出失败！失败信息："+ex);
		}
		return "redirect:" + adminPath + "/affair/affairNetworkCollegeYear?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairNetworkCollegeYear> list = ei.getDataList(AffairNetworkCollegeYear.class);
			for (AffairNetworkCollegeYear affairNetworkCollegeYear : list){
				try{
					affairNetworkCollegeYear.setUnitId(officeService.findByName(affairNetworkCollegeYear.getUnit()));
					BeanValidators.validateWithException(validator, affairNetworkCollegeYear);
					affairNetworkCollegeYearService.save(affairNetworkCollegeYear);
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