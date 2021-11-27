/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExcelTemplate;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelJob;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceCertificate;
import com.thinkgem.jeesite.modules.personnel.entity.PoliceCertificate;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelJobService;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelPoliceCertificateService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
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
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 人民警察证信息集Controller
 * @author cecil.li
 * @version 2019-11-11
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelPoliceCertificate")
public class PersonnelPoliceCertificateController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private PersonnelPoliceCertificateService personnelPoliceCertificateService;

	@Autowired
	private PersonnelBaseService personnelBaseService;

	@Autowired
	private PersonnelJobService personnelJobService;

	@ModelAttribute
	public PersonnelPoliceCertificate get(@RequestParam(required=false) String id) {
		PersonnelPoliceCertificate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelPoliceCertificateService.get(id);
		}
		if (entity == null){
			entity = new PersonnelPoliceCertificate();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelPoliceCertificate:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelPoliceCertificate personnelPoliceCertificate, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelPoliceCertificate.setIdNumber(request.getParameter("idNumber").toString());
		}
		String status = personnelPoliceCertificate.getStatus();
		if (StringUtils.isBlank(status)){
			personnelPoliceCertificate.setStatus("1");
		}
		Page<PersonnelPoliceCertificate> page = personnelPoliceCertificateService.findPage(new Page<PersonnelPoliceCertificate>(request, response), personnelPoliceCertificate);

		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/personnel/personnelPoliceCertificateList";
	}

	@RequiresPermissions("personnel:personnelPoliceCertificate:view")
	@RequestMapping(value = "form")
	public String form(PersonnelPoliceCertificate personnelPoliceCertificate, Model model,HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelPoliceCertificate.setIdNumber(request.getParameter("idNumber").toString());
		}
		model.addAttribute("personnelPoliceCertificate", personnelPoliceCertificate);
		return "modules/personnel/personnelPoliceCertificateForm";
	}

	@RequiresPermissions("personnel:personnelPoliceCertificate:view")
	@RequestMapping(value = "newForm")
	public String newForm(PersonnelPoliceCertificate personnelPoliceCertificate, Model model,HttpServletRequest request,HttpSession session) {

		Object idnumber = session.getAttribute("idnumber");
		String id = String.valueOf(idnumber);
		String name = personnelPoliceCertificateService.selectBeanByIdNumber(id);
		personnelPoliceCertificate.setName(name);
		model.addAttribute("personnelPoliceCertificate", personnelPoliceCertificate);
		return "modules/personnel/personnelPoliceCertificateForm";
	}


	@RequiresPermissions("personnel:personnelPoliceCertificate:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelPoliceCertificate personnelPoliceCertificate, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelPoliceCertificate)){
			return form(personnelPoliceCertificate, model,request);
		}
		personnelPoliceCertificateService.save(personnelPoliceCertificate);
		addMessage(redirectAttributes, "保存人民警察证信息成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelPoliceCertificateForm";
	}
	
	@RequiresPermissions("personnel:personnelPoliceCertificate:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelPoliceCertificate personnelPoliceCertificate, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		personnelPoliceCertificateService.delete(personnelPoliceCertificate);
		String url = "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceCertificate/?repage&idNumber="+personnelPoliceCertificate.getIdNumber();

		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url = "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceCertificate/?repage&mType="+request.getParameter("mType").toString();

		}
		addMessage(redirectAttributes, "删除人民警察证信息成功");
        return url;
	}

	@RequiresPermissions("personnel:personnelPoliceCertificate:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelPoliceCertificate personnelPoliceCertificate, Model model) {

		//身份证号
		String idNumber = personnelPoliceCertificate.getIdNumber();
		PersonnelBase personnelBase = new PersonnelBase();
		personnelBase = personnelBaseService.selectBean(idNumber);

		personnelPoliceCertificate.setBirthday(personnelBase.getBirthday());
		personnelPoliceCertificate.setSex(personnelBase.getSex());
		personnelPoliceCertificate.setJob(personnelBase.getJobAbbreviation());
		personnelPoliceCertificate.setCertificateNo(personnelBase.getPoliceIdNumber());

		model.addAttribute("personnelPoliceCertificate", personnelPoliceCertificate);
		return "modules/personnel/personnelPoliceCertificateFormDetail";
	}
	@RequiresPermissions("personnel:personnelPoliceCertificate:view")
	@RequestMapping(value = "newFormDetail")
	public String newFormDetail(PersonnelPoliceCertificate personnelPoliceCertificate, Model model,HttpSession session) {

		Object status = session.getAttribute("status");
		String status1 = String.valueOf(status);

		//身份证号
		String idNumber = personnelPoliceCertificate.getIdNumber();
		PersonnelBase personnelBase = new PersonnelBase();
		personnelBase = personnelBaseService.selectBean(idNumber);

		personnelPoliceCertificate.setStatus(status1);
		PersonnelPoliceCertificate personnelPoliceCertificate1 = personnelPoliceCertificateService.findNewBean(personnelPoliceCertificate);

		personnelPoliceCertificate1.setBirthday(personnelBase.getBirthday());
		personnelPoliceCertificate1.setSex(personnelBase.getSex());
		personnelPoliceCertificate1.setJob(personnelBase.getJobAbbreviation());
		personnelPoliceCertificate1.setCertificateNo(personnelBase.getPoliceIdNumber());

		model.addAttribute("personnelPoliceCertificate", personnelPoliceCertificate1);
		return "modules/personnel/personnelPoliceCertificateFormDetail";
	}

	//筛选出需要办理警察证的人员信息
	@RequiresPermissions("personnel:personnelPoliceCertificate:view")
	@RequestMapping(value = "screen")
	public String screen(PersonnelPoliceCertificate personnelPoliceCertificate, Model model,HttpServletRequest request,HttpServletResponse response,HttpSession session) {

		Page<PersonnelPoliceCertificate> policeCertificateList = personnelPoliceCertificateService.findPage(new Page<>(request,response),personnelPoliceCertificate);
		List<String> idNumbers=new ArrayList<>();
		policeCertificateList.getList().forEach(personnelPoliceCertificate1 -> {
			idNumbers.add(personnelPoliceCertificate1.getIdNumber());
		});
		Page<PoliceCertificate> certificateList = new Page<>();
		if (idNumbers.size()>0){
			certificateList = personnelBaseService.selectByIdNumbers(new Page<>(request, response),idNumbers,personnelPoliceCertificate.getStatus());
			model.addAttribute("page", certificateList);
		}else {
			model.addAttribute("page", certificateList);
		}
		session.setAttribute("status",personnelPoliceCertificate.getStatus());
		return "modules/personnel/personnelPoliceCertificateScreen";
	}

	@ResponseBody
	@RequiresPermissions("personnel:personnelPoliceCertificate:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelPoliceCertificateService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	/*
	 * 导出excel格式数据
	 * 导出模板和导入模板不一致，不在使用此方法
	 * @param
	 * @param
	 * @param
	 * @param redirectAttributes
	 * @return
	 */
	/*@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(PersonnelPoliceCertificate personnelPoliceCertificate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			//修改
			Page<PersonnelPoliceCertificate> page = null;
			if(flag == true){
				page = personnelPoliceCertificateService.findPage(new Page<PersonnelPoliceCertificate>(request, response), personnelPoliceCertificate);
			}else{
				page = personnelPoliceCertificateService.findPage(new Page<PersonnelPoliceCertificate>(request, response,-1), personnelPoliceCertificate);
			}
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(1, PersonnelPoliceCertificate.class);
			exportExcelNew.setWb(wb);
			List<PersonnelPoliceCertificate> list =page.getList();
			list.forEach(personnelPoliceCertificate1 -> {
//				personnelPoliceCertificate1.setIndex(String.valueOf(list.indexOf(personnelPoliceCertificate1)+1));
			});
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
		return "redirect:" + adminPath + "/personnel/personnelPoliceCertificate/?repage";
	}*/

	@RequestMapping(value = "export")
	public String export(PersonnelPoliceCertificate personnelPoliceCertificate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag){
		String fileName = "";
		if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
			fileName= request.getParameter("fileName").toString();
		}
		//修改
		Page<PersonnelPoliceCertificate> page = null;
		if(flag == true){
			page = personnelPoliceCertificateService.findPage(new Page<PersonnelPoliceCertificate>(request, response), personnelPoliceCertificate);
		}else{
			page = personnelPoliceCertificateService.findPage(new Page<PersonnelPoliceCertificate>(request, response,-1), personnelPoliceCertificate);
		}
		String fileSeperator = File.separator;
		String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;

		ExcelTemplate excelTemplate = new ExcelTemplate(filePath+fileName);
		LinkedHashMap<Integer, LinkedList<String>> rows = new LinkedHashMap<>();
		for (int i =0;i<page.getList().size();i++){
			PersonnelPoliceCertificate item = page.getList().get(i);
			PersonnelBase personnelBase = personnelBaseService.findInfoByIdNumber(item.getIdNumber());
			if (personnelBase==null){
				personnelBase = new PersonnelBase();
			}
			PersonnelJob personnelJob = new PersonnelJob();
			personnelJob.setIdNumber(item.getIdNumber());
			List<PersonnelJob> personnelJobList = personnelJobService.findList(personnelJob);
			if (personnelJobList!=null && personnelJobList.size()>0){
				personnelJob = personnelJobList.get(0);
			}
			LinkedList<String> row = new LinkedList<>();
			row.add(String.valueOf(i+1));
			row.add(DictUtils.getDictLabel(item.getUnitId(),"company",""));
			/*此处部门和单位 是什么*/
			row.add(item.getDepartment()==null?"":item.getDepartment());
			row.add(item.getName());
			row.add(DictUtils.getDictLabel(personnelBase.getSex(),"sex",""));
			String birthday = DateUtils.formatDate(personnelBase.getBirthday());
			row.add(birthday);
			row.add(item.getCertificateNo());
			row.add(personnelBase.getJobFullname());
			row.add(DictUtils.getDictLabel(personnelJob .getJobLevel(),"personnel_zwcc",""));
			String sendDate = DateUtils.formatDate(item.getSendDate());
			row.add(sendDate);
			row.add(DictUtils.getDictLabel(item.getCreateReason(),"certification_type",""));
			String endDate = DateUtils.formatDate(item.getEndDate());
			row.add(endDate);
			row.add(item.getOrgName());
			rows.put(i,row);
		}
		try {
			excelTemplate.addRowByExist(0,2,2,3,rows,true);

			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			excelTemplate.getWorkbook().write(fout);
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(HttpServletRequest request,MultipartFile file, RedirectAttributes redirectAttributes) {
		String isCover = request.getParameter("isCover");

		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelPoliceCertificate> list = ei.getDataList(PersonnelPoliceCertificate.class);
			//选择覆盖模式，须先将改身份证下相关履历信息删除
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(personnelResume -> {
					if(personnelResume.getIdNumber()!=null){
						idNumbers.add(personnelResume.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null)
					personnelPoliceCertificateService.deleteByIdNumbers(idNumbers);
			}
			for (PersonnelPoliceCertificate personnelPoliceCertificate : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(personnelPoliceCertificate.getOrgName());
					if(orgId != null){
						personnelPoliceCertificate.setOrgNameId(orgId);
					}

					if (StringUtils.isBlank(personnelPoliceCertificate.getIdNumber())){
						PersonnelBase personnelBase = new PersonnelBase();
						personnelBase.setPoliceIdNumber(personnelPoliceCertificate.getCertificateNo());
						List<PersonnelBase> personnelBaseList = personnelBaseService.findList(personnelBase);
						if (personnelBaseList!=null && personnelBaseList.size()>0){
							personnelPoliceCertificate.setIdNumber(personnelBaseList.get(0).getIdNumber());
						}
					}
					BeanValidators.validateWithException(validator, personnelPoliceCertificate);
					personnelPoliceCertificateService.save(personnelPoliceCertificate);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(personnelPoliceCertificate.getName()+"(身份证号码:"+personnelPoliceCertificate.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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

	@RequestMapping(value = "makeCertificate", method= RequestMethod.POST)
	public String makeCertificate(PersonnelPoliceCertificate personnelPoliceCertificate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			//查询身份证信息
			Page<PersonnelPoliceCertificate> page = null;
			if(flag == true){
				page = personnelPoliceCertificateService.findPage(new Page<PersonnelPoliceCertificate>(request, response), personnelPoliceCertificate);
			}else{
				page = personnelPoliceCertificateService.findPage(new Page<PersonnelPoliceCertificate>(request, response,-1), personnelPoliceCertificate);
			}
			List<String> idNumber=new ArrayList<>();
			page.getList().forEach(personnelPoliceCertificate1 -> {
				idNumber.add(personnelPoliceCertificate1.getIdNumber());
			});

			//导出的数据	根据身份证信息查询结果
			List<PoliceCertificate> list=new ArrayList<>();
			if (idNumber.size()>0){
				Page<PoliceCertificate> policeCertificatePage = personnelBaseService.findByIdNumbers(new Page<>(request,response,-1),idNumber);
				list=policeCertificatePage.getList();
			}
			//填充年份
			Calendar cal = Calendar.getInstance();
			String year=String.valueOf(cal.get(Calendar.YEAR));

			//获取模板路径
			String fileSeperator = File.separator;
			String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;

			ExcelTemplate excelTemplate=new ExcelTemplate(filePath+fileName);
			Map<String,String> map= new HashMap<>();
			map.put("year",year);
			//填充年份
			excelTemplate.fillVariable(map);
			//合并第一行单元格
			excelTemplate.mergedRegion(0,0,0,1,9);

			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			//生成导出数据集合
			LinkedHashMap<Integer, LinkedList<String>> areaValues=new LinkedHashMap<>();
			for (int i=0;i<list.size();i++){
				LinkedList<String> linkedList=new LinkedList<>();
				PoliceCertificate certificate=list.get(i);
				linkedList.add(String.valueOf(i+1));
				linkedList.add(certificate.getName()+"");
				linkedList.add(sdf.format(certificate.getBirthday()==null?"":certificate.getBirthday()));
				linkedList.add(certificate.getPoliceIdNumber()+"");
				linkedList.add(certificate.getBloodType()+"");
				linkedList.add(certificate.getIdNumber()+"");
				/*警衔为空是为null 不是 ""...*/
				linkedList.add(certificate.getRank()==null?"":certificate.getRank());
				linkedList.add(DictUtils.getDictLabel(certificate.getSex(),"sex",""));
				linkedList.add(certificate.getWorkunitName()+"");
				linkedList.add(certificate.getJobAbbreviation()==null?"":certificate.getJobAbbreviation());
				linkedList.add(certificate.getUnitCode()+"");
				areaValues.put(i,linkedList);
			}
			//导出数据
			excelTemplate.addRowByExist(0,2,2,3,areaValues,true);
			//修改

			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(year+"年"+fileName));
			ServletOutputStream fout = response.getOutputStream();
			excelTemplate.getWorkbook().write(fout);
			fout.close();
			return null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出用户失败！失败信息："+ex);
		}
		//修改
		return "redirect:" + adminPath + "/personnel/personnelPoliceCertificate/screen?repage";
	}

	@RequiresPermissions("personnel:personnelPoliceCertificate:edit")
	@RequestMapping(value = "backFillView")
	public String backFillView(HttpServletRequest request,Model model){
		String status=request.getParameter("status");
		String startEndDate=request.getParameter("startEndDate");
		String endEndDate=request.getParameter("endEndDate");
		model.addAttribute("status",status);
		model.addAttribute("startEndDate",startEndDate);
		model.addAttribute("endEndDate",endEndDate);
		return "modules/personnel/personnelPoliceCertificateFillBack";
	}

	@RequiresPermissions("personnel:personnelPoliceCertificate:edit")
	@RequestMapping(value = "backFill")
	public String backFill(PersonnelPoliceCertificate personnelPoliceCertificate,Model model,HttpServletRequest request,HttpServletResponse response){
		Page<PersonnelPoliceCertificate> page = personnelPoliceCertificateService.findPage(new Page<PersonnelPoliceCertificate>(request, response), personnelPoliceCertificate);
		List<String> idNumbers=new ArrayList<>();
		page.getList().forEach(personnelPoliceCertificate1 -> {
			idNumbers.add(personnelPoliceCertificate1.getIdNumber());
		});

		String backFillDate = request.getParameter("backFillDate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(backFillDate);
			personnelPoliceCertificateService.updateByIdNumbers(idNumbers,date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		request.setAttribute("backFillResult","success");
		return "modules/personnel/personnelPoliceCertificateFillBack";
	}


	/**
	 * 历史记录
	 * @return
	 */
	@RequiresPermissions("personnel:personnelPoliceCertificate:view")
	@RequestMapping(value = "historyform")
	public String historyform(String idNumber, Model model, HttpSession session) {

		List<PersonnelPoliceCertificate> list = new ArrayList<>();
		list = personnelPoliceCertificateService.selectHistoryByIdNumber(idNumber);

		session.setAttribute("idnumber",idNumber);
		 model.addAttribute("list", list);

		return "modules/personnel/personnelPoliceCertificateHistoryForm";
	}

}