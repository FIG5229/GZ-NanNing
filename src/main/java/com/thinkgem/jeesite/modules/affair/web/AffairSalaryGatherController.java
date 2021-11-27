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
import com.thinkgem.jeesite.modules.affair.dao.AffairLaborDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLabor;
import com.thinkgem.jeesite.modules.affair.entity.AffairSalaryGather;
import com.thinkgem.jeesite.modules.affair.service.AffairLaborService;
import com.thinkgem.jeesite.modules.affair.service.AffairSalaryGatherService;
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
 * 个人工资汇总Controller
 * @author cecil.li
 * @version 2020-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairSalaryGather")
public class AffairSalaryGatherController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private AffairSalaryGatherService affairSalaryGatherService;
	@Autowired
	private AffairLaborService affairLaborService;
	@Autowired
	private AffairLaborDao affairLaborDao;
	
	@ModelAttribute
	public AffairSalaryGather get(@RequestParam(required=false) String id) {
		AffairSalaryGather entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairSalaryGatherService.get(id);
		}
		if (entity == null){
			entity = new AffairSalaryGather();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairLabor:view")
	@RequestMapping(value = {"list"})
	public String list(AffairSalaryGather affairSalaryGather, HttpServletRequest request, HttpServletResponse response, Model model) {

		Calendar calendar = Calendar.getInstance();
		if (affairSalaryGather.getYear() == null || "".equals(affairSalaryGather.getYear())) {
			affairSalaryGather.setYear(String.valueOf(calendar.get(Calendar.YEAR)));
		}
		String year = affairSalaryGather.getYear();

//        String treeId = affairSalaryGather.getTreeId();
		String treeId = "";


		String name = affairSalaryGather.getName();

        String unit = affairSalaryGather.getUnit();
		List<AffairSalaryGather> asg1 = affairLaborDao.selectAsgListMonth(year);
	/*	List<AffairSalaryGather> affairSalaryGather2 = affairLaborDao.selectAsgList(year,treeId,name,unit);

		List<AffairSalaryGather> asg = new ArrayList<>();
 		for (int a = 0; a < asg1.size(); a++){//1月份list
			AffairSalaryGather affairSalaryGather1 = asg1.get(a);
			String name1 = affairSalaryGather1.getName();
				//年度list
			for(int as = 0; as < affairSalaryGather2.size(); as++){
				AffairSalaryGather affairSalaryGather3 = affairSalaryGather2.get(as);
				String name2 = affairSalaryGather3.getName();
				if(name1.equals(name2)){
					 asg.add(affairSalaryGather3);
					 break;

				}
			}
		}*/
		String userOffice = UserUtils.getUser().getCompany().getId();
		if ("34".equals(userOffice)){
			userOffice = "777";
		}else if ("95".equals(userOffice)){
			userOffice = "888";
		}else if ("156".equals(userOffice)){
			userOffice = "999";
		}
		List<AffairSalaryGather> asg  = affairLaborDao.selectAsgList(year,treeId,name,unit,userOffice);

		/*List<AffairSalaryGather> asg = new ArrayList<>();
		for (int a = 0; a < asg1.size(); a++){//1月份list
			AffairSalaryGather affairSalaryGather1 = asg1.get(a);
			String name1 = affairSalaryGather1.getName();
			//年度list
			for(int as = 0; as < affairSalaryGather2.size(); as++){
				AffairSalaryGather affairSalaryGather3 = affairSalaryGather2.get(as);
				String name2 = affairSalaryGather3.getName();
				if(name1.equals(name2)){
					asg.add(affairSalaryGather3);
					break;

				}
			}
		}*/



		model.addAttribute("treeId", affairSalaryGather.getTreeId());
/*
		AffairLabor affairLabor = new AffairLabor();
		affairLabor.setUnit(affairSalaryGather.getUnit());
		affairLabor.setUnitId(affairSalaryGather.getUnitId());
		affairLabor.setYear(affairSalaryGather.getYear());
		affairLabor.setMonth(affairSalaryGather.getMonth());
		affairLabor.setName(affairSalaryGather.getName());
		affairLabor.setIdNumber(affairSalaryGather.getIdNumber());
		affairLabor.setLevel(affairSalaryGather.getLevel());
		affairLabor.setSum(affairSalaryGather.getSum());
		affairLabor.setJbSalary(affairSalaryGather.getJbSalary());
		affairLabor.setJbGradeSalary(affairSalaryGather.getJbGradeSalary());
		affairLabor.setGjSum(affairSalaryGather.getGjSum());
		affairLabor.setJiabanAllowance(affairSalaryGather.getJiabanAllowance());
		affairLabor.setJingxianAllowance(affairSalaryGather.getJingxianAllowance());
		affairLabor.setZhiqinAllowance(affairSalaryGather.getZhiqinAllowance());
		affairLabor.setJkbyAllowance(affairSalaryGather.getJkbyAllowance());
		affairLabor.setTelephoneFee(affairSalaryGather.getTelephoneFee());
		affairLabor.setMobileFee(affairSalaryGather.getMobileFee());
		affairLabor.setJszrAllowance(affairSalaryGather.getJszrAllowance());
		affairLabor.setNvHygieneFee(affairSalaryGather.getNvHygieneFee());
		affairLabor.setOnlyChildAllowance(affairSalaryGather.getOnlyChildAllowance());
		affairLabor.setFangshuAllowance(affairSalaryGather.getFangshuAllowance());
		affairLabor.setXinfangAllowance(affairSalaryGather.getXinfangAllowance());
		affairLabor.setGonggai1993Allowance(affairSalaryGather.getGonggai1993Allowance());
		affairLabor.setXizangAllowance(affairSalaryGather.getXizangAllowance());
		affairLabor.setHighAltitudeAllowance(affairSalaryGather.getHighAltitudeAllowance());
		affairLabor.setXinjiangAllowance(affairSalaryGather.getXinjiangAllowance());
		affairLabor.setSarAllowance(affairSalaryGather.getSarAllowance());
		affairLabor.setTownshipAllowance(affairSalaryGather.getTownshipAllowance());
		affairLabor.setLinshiAllowance(affairSalaryGather.getLinshiAllowance());
		affairLabor.setGuojia19(affairSalaryGather.getGuojia19());
		affairLabor.setGuojia20(affairSalaryGather.getGuojia20());
		affairLabor.setGuojia21(affairSalaryGather.getGuojia21());
		affairLabor.setGuojia22(affairSalaryGather.getGuojia22());
		affairLabor.setGuojia23(affairSalaryGather.getGuojia23());
		affairLabor.setGuojia24(affairSalaryGather.getGuojia24());
		affairLabor.setGuojia25(affairSalaryGather.getGuojia25());
		affairLabor.setGuojia26(affairSalaryGather.getGuojia26());
		affairLabor.setGuojia27(affairSalaryGather.getGuojia27());
		affairLabor.setGuojia28(affairSalaryGather.getGuojia28());
		affairLabor.setGuojia29(affairSalaryGather.getGuojia29());
		affairLabor.setGuojia30(affairSalaryGather.getGuojia30());
		affairLabor.setGuifanSum(affairSalaryGather.getGuifanSum());
		affairLabor.setWorkingAllowance(affairSalaryGather.getWorkingAllowance());
		affairLabor.setLivingAllowance(affairSalaryGather.getLivingAllowance());
		affairLabor.setGaigeSum(affairSalaryGather.getGaigeSum());
		affairLabor.setZhuzhaiAllowance(affairSalaryGather.getZhuzhaiAllowance());
		affairLabor.setZhufangAllowance(affairSalaryGather.getZhufangAllowance());
		affairLabor.setWuyeAllowance(affairSalaryGather.getWuyeAllowance());
		affairLabor.setJiaotongAllowance(affairSalaryGather.getJiaotongAllowance());
		affairLabor.setGaigeAllowance(affairSalaryGather.getGaigeAllowance());
		affairLabor.setGaige6(affairSalaryGather.getGaige6());
		affairLabor.setGaige7(affairSalaryGather.getGaige7());
		affairLabor.setGaige8(affairSalaryGather.getGaige8());
		affairLabor.setGaige9(affairSalaryGather.getGaige9());
		affairLabor.setGaige10(affairSalaryGather.getGaige10());
		affairLabor.setRewardSum(affairSalaryGather.getRewardSum());
		affairLabor.setChengwuAllowance(affairSalaryGather.getChengwuAllowance());
		affairLabor.setXianluAllowance(affairSalaryGather.getXianluAllowance());
		affairLabor.setAnquanAllowance(affairSalaryGather.getAnquanAllowance());
		affairLabor.setJingshenAllowance(affairSalaryGather.getJingshenAllowance());
		affairLabor.setJingchaAllowance(affairSalaryGather.getJingchaAllowance());
		affairLabor.setGongwuyuanAllowance(affairSalaryGather.getGongwuyuanAllowance());
		affairLabor.setJiangkeAllowance(affairSalaryGather.getJiangkeAllowance());
		affairLabor.setReward8(affairSalaryGather.getReward8());
		affairLabor.setReward9(affairSalaryGather.getReward9());
		affairLabor.setReward10(affairSalaryGather.getReward10());
		affairLabor.setReward11(affairSalaryGather.getReward11());
		affairLabor.setReward12(affairSalaryGather.getReward12());
		affairLabor.setReward13(affairSalaryGather.getReward13());
		affairLabor.setJianyuanAllowance(affairSalaryGather.getJianyuanAllowance());
		affairLabor.setGongziAllowance(affairSalaryGather.getGongziAllowance());
		affairLabor.setYearEndAwards(affairSalaryGather.getYearEndAwards());
		affairLabor.setBaoxianSum(affairSalaryGather.getBaoxianSum());
		affairLabor.setBaseYiliaoAllowance(affairSalaryGather.getBaseYiliaoAllowance());
		affairLabor.setBuchongYiliaoAllowance(affairSalaryGather.getBuchongYiliaoAllowance());
		affairLabor.setShengyuAllowance(affairSalaryGather.getShengyuAllowance());
		affairLabor.setGongjijin(affairSalaryGather.getGongjijin());
		affairLabor.setPersonalIncomeTax(affairSalaryGather.getPersonalIncomeTax());
		affairLabor.setWhereGonganju(affairSalaryGather.getWhereGonganju());
		affairLabor.setWhereGonganchu(affairSalaryGather.getWhereGonganchu());
		affairLabor.setDepartment(affairSalaryGather.getDepartment());
		affairLabor.setPersonnelType(affairSalaryGather.getPersonnelType());
		affairLabor.setIsLogo(affairSalaryGather.getIsLogo());
		affairLabor.setRemark(affairSalaryGather.getRemark());
		affairLabor.setCreateOrgId(affairSalaryGather.getCreateOrgId());
		affairLabor.setCreateIdNo(affairSalaryGather.getCreateIdNo());
		affairLabor.setUpdateOrgId(affairSalaryGather.getUpdateOrgId());
		affairLabor.setUpdateIdNo(affairSalaryGather.getUpdateIdNo());
		affairLabor.setTreeId(affairSalaryGather.getTreeId());

		Page<AffairLabor> page = affairLaborService.findPage(new Page<AffairLabor>(request, response), affairLabor);
		model.addAttribute("page", page);*/
		model.addAttribute("affairSalaryGatherList",asg);
		return "modules/affair/affairSalaryGatherList";
	}

	@RequiresPermissions("affair:affairLabor:view")
	@RequestMapping(value = "form")
	public String form(AffairSalaryGather affairSalaryGather, Model model) {
		model.addAttribute("affairSalaryGather", affairSalaryGather);
		return "modules/affair/affairSalaryGatherForm";
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
	public String exportExcelByTemplate(AffairSalaryGather affairSalaryGather, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
 			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			/*Page<AffairLabor> page = null;

			AffairLabor affairLabor = new AffairLabor();

			affairLabor.setYear(year);
			affairLabor.setTreeId(treeId);*/

			/*List<AffairLabor> asg = null;
			asg = affairLaborDao.selectByYearAndTreeId(year,treeId);
			for (int i = 0; i < asg.size(); i++){
				AffairLabor affairLabor1 = asg.get(i);
				affairLabor1.setMonth(year);
			}*/

			String name = affairSalaryGather.getName();
			String unit = affairSalaryGather.getUnit();
			String year = affairSalaryGather.getYear();
			String treeId = affairSalaryGather.getTreeId();
			List<AffairSalaryGather> asg1 = affairLaborDao.selectAsgListMonth(year);
			List<AffairSalaryGather> affairSalaryGather2 = affairLaborDao.selectAsgList(year,treeId,name,unit,"");

			/*List<AffairSalaryGather> asg = new ArrayList<>();
			for (int a = 0; a < asg1.size(); a++){//1月份list
				AffairSalaryGather affairSalaryGather1 = asg1.get(a);
				String name1 = affairSalaryGather1.getName();
				//年度list
				for(int as = 0; as < affairSalaryGather2.size(); as++){
					AffairSalaryGather affairSalaryGather3 = affairSalaryGather2.get(as);
					String name2 = affairSalaryGather3.getName();
					if(name1.equals(name2)){
						asg.add(affairSalaryGather3);
						break;

					}
				}
			}*/
			List<AffairSalaryGather> asg  = affairLaborDao.selectAsgList(year,treeId,name,unit,"");
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(3, AffairLabor.class);
			exportExcelNew.setWb(wb);

			List<AffairSalaryGather> list = asg;
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
		return "redirect:" + adminPath + "/affair/affairSalaryGather/list?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 3, 0);
			//修改
			List<AffairSalaryGather> list = ei.getDataList(AffairSalaryGather.class);
			for (AffairSalaryGather affairSalaryGather : list){
				try{
					if (affairSalaryGather.getUnit()!=null&&!"".equals(affairSalaryGather.getUnit())) {
						affairSalaryGather.setUnitId(officeService.findByName(affairSalaryGather.getUnit()));
					}
					BeanValidators.validateWithException(validator, affairSalaryGather);
					affairSalaryGatherService.save(affairSalaryGather);
					successNum++;

				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(单位:"+affairSalaryGather.getUnit()+")"+" 导入失败："+ex.getMessage());
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