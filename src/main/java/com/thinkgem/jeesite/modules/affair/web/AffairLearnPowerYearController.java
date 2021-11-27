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
import com.thinkgem.jeesite.modules.affair.entity.AffairGhActivityRecord;
import com.thinkgem.jeesite.modules.affair.entity.AffairLearnPowerYear;
import com.thinkgem.jeesite.modules.affair.service.AffairLearnPowerService;
import com.thinkgem.jeesite.modules.affair.service.AffairLearnPowerYearService;
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
 * 学习强国--年度统计Controller
 * @author Alan
 * @version 2020-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairLearnPowerYear")
public class AffairLearnPowerYearController extends BaseController {

	@Autowired
	private AffairLearnPowerYearService affairLearnPowerYearService;

	@Autowired
	private AffairLearnPowerService affairLearnPowerService;

	@Autowired
	OfficeService officeService;
	
	@ModelAttribute
	public AffairLearnPowerYear get(@RequestParam(required=false) String id) {
		AffairLearnPowerYear entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairLearnPowerYearService.get(id);
		}
		if (entity == null){
			entity = new AffairLearnPowerYear();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairLearnPowerYear:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairLearnPowerYear affairLearnPowerYear1, HttpServletRequest request, HttpServletResponse response, Model model) {
		String idN = "";
		String unitId = "";
		String name1 = affairLearnPowerYear1.getName();
		String name = affairLearnPowerYear1.getName();
		String yearStr = request.getParameter("year");
		int year;
		if(StringUtils.isNotBlank(yearStr)){
			year = Integer.parseInt(yearStr);
		}else{
			Calendar calendar = Calendar.getInstance();
		 	year = calendar.get(Calendar.YEAR);
		}
		if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
			unitId=UserUtils.getUser().getCompany().getId();
		}else {
			unitId=UserUtils.getUser().getOffice().getId();
		}
		AffairLearnPowerYear affairLearnPowerYear = new AffairLearnPowerYear();
		affairLearnPowerYear.setYear(year);
		affairLearnPowerYear.setLastYear(year-1);
		affairLearnPowerYear.setUnitId(unitId);
		affairLearnPowerYear.setName(name);
		affairLearnPowerYear.setTime(String.valueOf(year));
		Page<AffairLearnPowerYear> page = affairLearnPowerYearService.findListByYear(new Page<AffairLearnPowerYear>(request,response),affairLearnPowerYear,year);
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

			List<AffairLearnPowerYear> list = new ArrayList<>();
			//所有的身份证
			List<String> idList = new ArrayList<>();
			if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
				idN=UserUtils.getUser().getCompany().getId();
			}else {
				idN=UserUtils.getUser().getOffice().getId();
			}
			idList = affairLearnPowerService.selectAllId(idN);
			for (int i = 0; i< idList.size(); i++){
				AffairLearnPowerYear affairLearnPowerYear = new AffairLearnPowerYear();
				affairLearnPowerYear.setTime(ye);
				String id = idList.get(i);
				String unit = affairLearnPowerService.selectUnit(id);
				String name = affairLearnPowerService.selectName(id);
				affairLearnPowerYear.setUnit(unit);
				affairLearnPowerYear.setIdNumber(id);
				affairLearnPowerYear.setName(name);

				//今年分数
				String thisYear = affairLearnPowerService.selectBean(id,ye);
				Integer thisYearScore ;
				if(StringUtils.isNotBlank(thisYear)){
					thisYearScore  =  Integer.valueOf(thisYear);
				}else{
					thisYearScore = 0;
				}
				Double tyScore = Double.valueOf(thisYearScore);
				affairLearnPowerYear.setThisYearIntegral(tyScore);
				//去年分数
				String lastYearScore = affairLearnPowerService.selectBean(id,lastYear);
				if (StringUtils.isNotBlank(lastYearScore)){
					Integer laYearScore = Integer.valueOf(lastYearScore);
					Double lyScore = Double.valueOf(laYearScore);
					affairLearnPowerYear.setLastYearIntegral(lyScore);

					//今年和去年一共的分数
					Double sumScore = tyScore + lyScore;
					affairLearnPowerYear.setThisYearStatistics(sumScore);
				}
				else{
					affairLearnPowerYear.setThisYearStatistics(tyScore);
				}

				list.add(affairLearnPowerYear);
			}

			*//*Page<AffairLearnPowerYear> page = affairLearnPowerYearService.findPage(new Page<AffairLearnPowerYear>(request, response), affairLearnPowerYear);*//*
			model.addAttribute("list", list);
		}
		else if (StringUtils.isNotBlank(name1)){

			List<AffairLearnPowerYear> list = new ArrayList<>();

			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			//今年年份
			String ye = String.valueOf(year);

			//去年年份
			int lyear = year - 1;
			String lastYear = String.valueOf(lyear);

			//身份证号
			String idNumber = affairLearnPowerService.selectIdNumber(name1);

			if (idNumber == null){
				return "modules/affair/affairLearnPowerYearList";
			}

			AffairLearnPowerYear affairLearnPowerYear = new AffairLearnPowerYear();
			affairLearnPowerYear.setTime(ye);

			String unit = affairLearnPowerService.selectUnit(idNumber);
			String name = affairLearnPowerService.selectName(idNumber);
			affairLearnPowerYear.setUnit(unit);
			affairLearnPowerYear.setIdNumber(idNumber);
			affairLearnPowerYear.setName(name);

			//今年分数
			String thisYear = affairLearnPowerService.selectBean(idNumber,ye);
			Integer thisYearScore = Integer.valueOf(thisYear);
			Double tyScore = Double.valueOf(thisYearScore);
			affairLearnPowerYear.setThisYearIntegral(tyScore);
			//去年分数
			String lastYearScore = affairLearnPowerService.selectBean(idNumber,lastYear);
			if (StringUtils.isNotBlank(lastYearScore)){
				Integer laYearScore = Integer.valueOf(lastYearScore);
				Double lyScore = Double.valueOf(laYearScore);
				affairLearnPowerYear.setLastYearIntegral(lyScore);

				//今年和去年一共的分数
				Double sumScore = tyScore + lyScore;
				affairLearnPowerYear.setThisYearStatistics(sumScore);
			}
			else{
				affairLearnPowerYear.setThisYearStatistics(tyScore);
			}
			list.add(affairLearnPowerYear);
			model.addAttribute("list", list);

		}*/

/*
		Page<AffairLearnPowerYear> page = affairLearnPowerYearService.findPage(new Page<AffairLearnPowerYear>(request, response), affairLearnPowerYear);
		model.addAttribute("list", list);
*/

		return "modules/affair/affairLearnPowerYearList";
	}

	@RequiresPermissions("affair:affairLearnPowerYear:view")
	@RequestMapping(value = "form")
	public String form(AffairLearnPowerYear affairLearnPowerYear, Model model) {
		model.addAttribute("affairLearnPowerYear", affairLearnPowerYear);
		return "modules/affair/affairLearnPowerYearForm";
	}

	@RequiresPermissions("affair:affairLearnPowerYear:edit")
	@RequestMapping(value = "save")
	public String save(AffairLearnPowerYear affairLearnPowerYear, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairLearnPowerYear)){
			return form(affairLearnPowerYear, model);
		}
		affairLearnPowerYearService.save(affairLearnPowerYear);
		addMessage(redirectAttributes, "保存学习强国--年度统计成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairLearnPowerYear/?repage";
	}
	
	@RequiresPermissions("affair:affairLearnPowerYear:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairLearnPowerYear affairLearnPowerYear, RedirectAttributes redirectAttributes) {
		affairLearnPowerYearService.delete(affairLearnPowerYear);
		addMessage(redirectAttributes, "删除学习强国--年度统计成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairLearnPowerYear/?repage";
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
	public String exportExcelByTemplate(AffairLearnPowerYear affairLearnPowerYear, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String unitId = "";
			if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
				unitId=UserUtils.getUser().getCompany().getId();
			}else {
				unitId=UserUtils.getUser().getOffice().getId();
			}
			affairLearnPowerYear.setUnitId(unitId);
			Page<AffairLearnPowerYear> page = null;
			if(flag == true){
				page = affairLearnPowerYearService.findListByYear(new Page<AffairLearnPowerYear>(request, response), affairLearnPowerYear,affairLearnPowerYear.getYear());
			}else{
				page = affairLearnPowerYearService.findListByYear(new Page<AffairLearnPowerYear>(request, response,-1), affairLearnPowerYear,affairLearnPowerYear.getYear());
			}
			/*if ("5".equals(UserUtils.getUser().getOffice().getId())){
				affairLearnPowerYear.setUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairLearnPowerYear.setUnitId(UserUtils.getUser().getOffice().getId());
			}
			affairLearnPowerYear.setCreateBy(UserUtils.getUser());*/
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			/*String idN = "";
			String name1 = affairLearnPowerYear.getName();

			if (StringUtils.isBlank(name1)){
				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				//今年年份
				String ye = String.valueOf(year);

				//去年年份
				int lyear = year - 1;
				String lastYear = String.valueOf(lyear);

				List<AffairLearnPowerYear> list = new ArrayList<>();
				//所有的身份证
				List<String> idList = new ArrayList<>();
				if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
					idN=UserUtils.getUser().getCompany().getId();
				}else {
					idN=UserUtils.getUser().getOffice().getId();
				}
				idList = affairLearnPowerService.selectAllId(idN);
				for (int i = 0; i< idList.size(); i++){
					AffairLearnPowerYear affairLearnPowerYear1 = new AffairLearnPowerYear();
					affairLearnPowerYear1.setTime(ye);
					String id = idList.get(i);
					String unit = affairLearnPowerService.selectUnit(id);
					String name = affairLearnPowerService.selectName(id);
					affairLearnPowerYear1.setUnit(unit);
					affairLearnPowerYear1.setIdNumber(id);
					affairLearnPowerYear1.setName(name);

					//今年分数
					String thisYear = affairLearnPowerService.selectBean(id,ye);
					Double thisYearScore = Double.valueOf(thisYear);
					Double tyScore = Double.valueOf(thisYearScore);
					affairLearnPowerYear1.setThisYearIntegral(tyScore);
					//去年分数
					String lastYearScore = affairLearnPowerService.selectBean(id,lastYear);
					if (StringUtils.isNotBlank(lastYearScore)){
						Double laYearScore = Double.valueOf(lastYearScore);
						Double lyScore = Double.valueOf(laYearScore);
						affairLearnPowerYear1.setLastYearIntegral(lyScore);

						//今年和去年一共的分数
						Double sumScore = tyScore + lyScore;
						affairLearnPowerYear1.setThisYearStatistics(sumScore);
					}
					else{
						affairLearnPowerYear1.setThisYearStatistics(tyScore);
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
					list.add(affairLearnPowerYear1);
					ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairLearnPowerYear.class);
					exportExcelNew.setWb(wb);
					exportExcelNew.setDataList(list);
				}

			}
			else if (StringUtils.isNotBlank(name1)){

				List<AffairLearnPowerYear> list = new ArrayList<>();

				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				//今年年份
				String ye = String.valueOf(year);

				//去年年份
				int lyear = year - 1;
				String lastYear = String.valueOf(lyear);

				//身份证号
				String idNumber = affairLearnPowerService.selectIdNumber(name1);

				if (idNumber == null){
					return "modules/affair/affairLearnPowerYearList";
				}

				AffairLearnPowerYear affairLearnPowerYear1 = new AffairLearnPowerYear();
				affairLearnPowerYear1.setTime(ye);

				String unit = affairLearnPowerService.selectUnit(idNumber);
				String name = affairLearnPowerService.selectName(idNumber);
				affairLearnPowerYear1.setUnit(unit);
				affairLearnPowerYear1.setIdNumber(idNumber);
				affairLearnPowerYear1.setName(name);

				//今年分数
				String thisYear = affairLearnPowerService.selectBean(idNumber,ye);
				Integer thisYearScore = Integer.valueOf(thisYear);
				Double tyScore = Double.valueOf(thisYearScore);
				affairLearnPowerYear1.setThisYearIntegral(tyScore);
				//去年分数
				String lastYearScore = affairLearnPowerService.selectBean(idNumber,lastYear);
				if (StringUtils.isNotBlank(lastYearScore)){
					Integer laYearScore = Integer.valueOf(lastYearScore);
					Double lyScore = Double.valueOf(laYearScore);
					affairLearnPowerYear1.setLastYearIntegral(lyScore);

					//今年和去年一共的分数
					Double sumScore = tyScore + lyScore;
					affairLearnPowerYear1.setThisYearStatistics(sumScore);
				}
				else{
					affairLearnPowerYear1.setThisYearStatistics(tyScore);
				}
				list.add(affairLearnPowerYear1);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairLearnPowerYear.class);
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
		return "redirect:" + adminPath + "/affair/affairLearnPowerYear?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairLearnPowerYear> list = ei.getDataList(AffairLearnPowerYear.class);
			for (AffairLearnPowerYear affairFocusStudy : list){
				try{
					affairFocusStudy.setUnitId(officeService.findByName(affairFocusStudy.getUnit()));
					BeanValidators.validateWithException(validator, affairFocusStudy);
					affairLearnPowerYearService.save(affairFocusStudy);
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