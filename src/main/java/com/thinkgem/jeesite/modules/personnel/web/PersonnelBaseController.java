package com.thinkgem.jeesite.modules.personnel.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.common.utils.excel.ExcelTemplate;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.utils.word.CustomXWPFDocument;
import com.thinkgem.jeesite.common.utils.word.WordTemplate;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.dao.AffairDisciplinaryActionDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairPersonalRewardDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDisciplinaryAction;
import com.thinkgem.jeesite.modules.affair.entity.AffairPersonalReward;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelBaseDao;
import com.thinkgem.jeesite.modules.personnel.entity.*;
import com.thinkgem.jeesite.modules.personnel.service.*;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
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
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 人员基本情况信息Controller
 * @author cecil.li
 * @version 2019-11-09
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelBase")
public class PersonnelBaseController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private PersonnelBaseService personnelBaseService;
	@Autowired
	private PersonnelBaseDao personnelBaseDao;
	@Autowired
	private AffairPersonalRewardDao affairPersonalRewardDao;
	@Autowired
	private AffairDisciplinaryActionDao affairDisciplinaryActionDao;
	@Autowired
	private PersonnelXueliService personnelXueliService;
	@Autowired
	private PersonnelResumeService personnelResumeService;
	@Autowired
	private PersonnelFamilyService personnelFamilyService;
	@Autowired
	private PersonnelRewardService personnelRewardService;
	@Autowired
	private PersonnelYearCheckService personnelYearCheckService;
	@Autowired
	private PersonnelPoliceWork1Service personnelPoliceWork1Service;
	@Autowired
	private PersonnelComplexSelService personnelComplexSelService;
    @Autowired
    private PersonnelPoliceRankService personnelPoliceRankService;

    @Autowired
	private UserDao userDao;

	@ModelAttribute
	public PersonnelBase get(@RequestParam(required=false) String id) {
		PersonnelBase entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelBaseService.get(id);
		}
		if (entity == null){
			entity = new PersonnelBase();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelBase:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response, Model model) {
		//复合查询判断及返回相应页面数据
		HttpSession session = request.getSession();
		if(request.getParameter("isComplexSelForm")!=null && request.getParameter("isComplexSelForm").equals("isComplexSelForm")&&session.getAttribute("customSql")!=null){
			String customSql = (String) session.getAttribute("customSql");
			List<String> columnTextList = (List<String>) session.getAttribute("columnTextList");
			List<String> columnList = (List<String>) session.getAttribute("columnList");
			/*Page<Map<String, Object>> complexSelPage = new Page<Map<String, Object>>(request,response);*/
			try{
				Page<Map<String, Object>> complexSelPage = personnelComplexSelService.execCustomSql(new Page<Map<String, Object>>(request,response),customSql,columnTextList,columnList);
				model.addAttribute("isComplexSelForm",true);
				model.addAttribute("page",complexSelPage);
				model.addAttribute("columnTextList",columnTextList);
			}catch (Exception e){
				e.printStackTrace();
				logger.error(e.toString());
				model.addAttribute("isComplexSelForm",true);
				model.addAttribute("columnTextList",columnTextList);
				model.addAttribute("message","列表重新生成时发生错误，请联系管理员，查看日志"+ DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			}
			return "modules/personnel/personnelBaseList";
		}else{
			if(session.getAttribute("customSql")!=null){
				session.removeAttribute("customSql");
			}
			if(session.getAttribute("columnTextList")!=null){
				session.removeAttribute("columnTextList");
			}
			if(session.getAttribute("columnList")!=null){
				session.removeAttribute("columnList");
			}
		}
		//批量查询生成列表
		if(request.getParameter("isBatchSelForm")!=null && request.getParameter("isBatchSelForm").equals("isBatchSelForm")&&session.getAttribute("customBatchSql")!=null){
			try{
				String customSql = (String) session.getAttribute("customBatchSql");
				Page<PersonnelBase> batchSqlResultPage = personnelComplexSelService.execBatchCustomSql(new Page<PersonnelBase>(request,response),customSql);
				model.addAttribute("page",batchSqlResultPage);
				model.addAttribute("isBatchSelTable",true);
			}catch (Exception e){
				e.printStackTrace();
				logger.error(e.toString());
				model.addAttribute("isBatchSelTable",true);
				model.addAttribute("message","列表重新生成时发生错误，请联系管理员，查看日志"+ DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			}
			return "modules/personnel/personnelBaseList";
		}else{
			if(session.getAttribute("customBatchSql")!=null){
				session.removeAttribute("customBatchSql");
			}
		}
		//r人员信息管理 查看人员树时  身份证号不为空
		//personnel为身份证号 避免与下方冲突 不为空 修改后还是一条信息
		if (!StringUtils.isEmpty(personnelBase.getIdNumber())){
			request.setAttribute("type","1");
			request.setAttribute("personnel",personnelBase.getIdNumber());
		}

		String type = request.getParameter("type");
		if ("2".equals(request.getParameter("type"))){
//			Page<PersonnelBase> page = personnelBaseService.findAllWorkInfo(new Page<PersonnelBase>(request, response), personnelBase);
			Page<PersonnelBase> page = personnelBaseService.findAllWorkInfoTwo(new Page<PersonnelBase>(request, response), personnelBase,false);
			if(request.getParameter("type")!=null && !"".equals(request.getParameter("type")))
				request.setAttribute("type", request.getParameter("type"));
			if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber")))
				personnelBase.setIdNumber(request.getParameter("idNumber"));
			model.addAttribute("page", page);
		}else {
			if (!StringUtils.isNotEmpty(personnelBase.getPeopleStatus())){
				personnelBase.setPeopleStatus("1");
			}
			//Page<PersonnelBase> page = personnelBaseService.findPage(new Page<PersonnelBase>(request, response), personnelBase);
			/*根据人事命令单位只看本处*/
			String userId = UserUtils.getUser().getId();
			/*人事单位按着人事命令字段进行数据过滤*/
			if (userId.equals("bd42300887ad417fa3f2fa9f554e6419") || userId.equals("1c19f6cc935f430f9f27295b761b1236") || userId.equals("3850cecf34be44188f94b0edc552aff3")
					|| userId.equals("69f857c3e1854021b5dee55c514026e3")){
				Page<PersonnelBase> page = personnelBaseService.findPageOfWorkUnit(new Page<PersonnelBase>(request, response), personnelBase);
				model.addAttribute("page", page);

			}else {
				/*根据实际单位进行数据过滤*/
				Page<PersonnelBase> page = personnelBaseService.findPageBeta(new Page<PersonnelBase>(request, response), personnelBase,false);
				model.addAttribute("page", page);
			}
			if(request.getParameter("type")!=null && !"".equals(request.getParameter("type")))
				request.setAttribute("type", request.getParameter("type"));
			if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber")))
				personnelBase.setIdNumber(request.getParameter("idNumber"));
			model.addAttribute("ps",personnelBase.getPeopleStatus());
		}

		return "modules/personnel/personnelBaseList";
	}

	@RequiresPermissions("personnel:personnelBase:view")
	@RequestMapping(value = {"copylist"})
	public String listCopy(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response, Model model) {
		//r人员信息管理 查看人员树时  身份证号不为空
		//personnel为身份证号 避免与下方冲突 不为空 修改后还是一条信息
		if (!StringUtils.isEmpty(personnelBase.getIdNumber())){
			request.setAttribute("type","1");
			request.setAttribute("personnel",personnelBase.getIdNumber());
		}

		Page<PersonnelBase> page = personnelBaseService.findPageCopy(new Page<PersonnelBase>(request, response), personnelBase);
		if(request.getParameter("type")!=null && !"".equals(request.getParameter("type")))
			request.setAttribute("type", request.getParameter("type"));
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber")))
			personnelBase.setIdNumber(request.getParameter("idNumber"));
		model.addAttribute("page", page);

		return "modules/personnel/personnelBaseCopyList";
	}

	@RequestMapping(value = {"getPersonByName"})
	@ResponseBody
	public Result findPersonByName(String name){

		PersonnelBase param = new PersonnelBase();
		param.setName(name);
		List<PersonnelBase> list = personnelBaseService.findPerson(param);
		Result result = new Result();
		if(list != null && list.size()> 0){
			result.setResult(list);
			result.setSuccess(true);
		}else{
			result.setSuccess(false);
		}
		return result;
	}

	/*
	* 根据身份证号查找个人信息
	*
	* */
	@RequestMapping(value = {"getPersonByIdNumber"})
	@ResponseBody
	public Result findPersonByIdNumber(String idNumber){
		PersonnelBase personnelBase = personnelBaseService.findPersoByIdNumber(idNumber);
		Result result = new Result();
		if(personnelBase != null ){
			result.setResult(personnelBase);
			result.setSuccess(true);
		}else{
			result.setSuccess(false);
		}
		return result;
	}

	@RequiresPermissions("personnel:personnelBase:view")
	@RequestMapping(value = "form")
	public String form(PersonnelBase personnelBase, Model model) {
		model.addAttribute("personnelBase", personnelBase);
		return "modules/personnel/personnelBaseForm";
	}

	@RequiresPermissions("personnel:personnelBase:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelBase personnelBase, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelBase)){
			return form(personnelBase, model);
		}
		if (personnelBase.getWorkunitName()!=null&&!"".equals(personnelBase.getWorkunitName())) {
			personnelBase.setWorkunitId(officeService.findByName(personnelBase.getWorkunitName()));
			personnelBase.setWorkunitCode(officeService.findCodeById(personnelBase.getWorkunitId()));
		}
		personnelBaseService.save(personnelBase);
		addMessage(redirectAttributes, "保存人员基本情况信息成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelBaseForm";
	}
	
	@RequiresPermissions("personnel:personnelBase:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelBase personnelBase, RedirectAttributes redirectAttributes) {
		personnelBaseService.delete(personnelBase);
		addMessage(redirectAttributes, "删除人员基本情况信息成功");
		return "redirect:"+Global.getAdminPath()+"/personnel/personnelBase/?repage";
	}

	@ResponseBody
	@RequiresPermissions("personnel:personnelBase:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelBaseService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("personnel:personnelBase:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelBase personnelBase, Model model) {
		personnelBase = personnelBaseService.findInfoByIdNumber(personnelBase.getIdNumber());
		model.addAttribute("personnelBase", personnelBase);
		PersonnelPoliceRank policeRank = new PersonnelPoliceRank();
		policeRank.setIdNumber(personnelBase.getIdNumber());
        PersonnelPoliceRank personnelPoliceRank = personnelPoliceRankService.findNowPoliceRankByIdNumber(policeRank);
        model.addAttribute("personnelPoliceRank",personnelPoliceRank);
//		return "modules/personnel/personnelBaseFormDetail";
		return "modules/personnel/personnelBaseFormDetail2";
	}

	@RequiresPermissions("personnel:personnelBase:view")
	@RequestMapping(value = "generalFormDetail")
	public String generalFormDetail(PersonnelBase personnelBase, Model model) {
		//身份证
		String userId = personnelBase.getIdNumber();

		//奖励信息
		List<AffairPersonalReward> reward = affairPersonalRewardDao.selectRewardByUserId(userId);

		//惩戒信息
		List<AffairDisciplinaryAction> disciplinaryActions = affairDisciplinaryActionDao.selectDisciplinaryActionsById(userId);


		personnelBase = personnelBaseService.findInfoByIdNumber(personnelBase.getIdNumber());
		/*警衔信息*/
		PersonnelPoliceRank policeRank = new PersonnelPoliceRank();
		policeRank.setIdNumber(personnelBase.getIdNumber());
		PersonnelPoliceRank personnelPoliceRank = personnelPoliceRankService.findNowPoliceRankByIdNumber(policeRank);
		model.addAttribute("personnelPoliceRank",personnelPoliceRank);
		model.addAttribute("AffairPersonalReward",reward);
		model.addAttribute("disciplinaryActions",disciplinaryActions);
		model.addAttribute("personnelBase", personnelBase);
		//return "modules/personnel/personnelBaseGeneralFormDetail";
		return "modules/personnel/personnelBaseGeneralFormDetail2";
	}

	@RequiresPermissions("personnel:personnelBase:view")
	@RequestMapping(value = "excel")
	public String excel(PersonnelBase personnelBase, Model model) {
		personnelBase = personnelBaseService.findInfoByIdNumber(personnelBase.getIdNumber());
		model.addAttribute("personnelBase", personnelBase);
		return "modules/personnel/personnelBaseExcel";
	}

	@RequiresPermissions("personnel:personnelBase:view")
	@RequestMapping(value = {"index"})
	public String index(PersonnelBase personnelBase, Model model, HttpServletRequest request) {
		model.addAttribute("idNumber",personnelBase.getIdNumber());
		String ps = request.getParameter("peopleStatus");
		model.addAttribute("peopleStatus",ps);
		return "modules/personnel/personnelNavIndex";
	}

	/**
	 * 获得人员概况树形选择器 ，未放入缓存中
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, String>> treeData() {
		List<Map<String, String>> list=personnelBaseService.findNavTree();
		return list;
	}

	@RequestMapping(value = "nav")
	public String personNav(PersonnelBase personnelBase,HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelBase.setIdNumber(request.getParameter("idNumber"));
			request.setAttribute("mType","2");//人员详细信息
		}
		List<Map<String, String>> list=personnelBaseService.findNavList();
		StringBuffer sqlBuffer= new StringBuffer();
		for(int i=0;i<list.size();i++){
			if(i==0){
				sqlBuffer.append("SELECT count(1) totalnum,'");
			}
			else{
				sqlBuffer.append("union all SELECT count(1) totalnum,'");
			}
			Map<String,String> item= list.get(i);
			sqlBuffer.append(item.get("table_name"));
			sqlBuffer.append("' tableName FROM ");
			sqlBuffer.append(item.get("table_name"));
			sqlBuffer.append(" t where t.id_number='");
			sqlBuffer.append(personnelBase.getIdNumber());
			sqlBuffer.append("' ");
			sqlBuffer.append(" and del_flag='0' ");
		}
		Map<String,String> param = new HashMap<String, String>();
		param.put("sql",sqlBuffer.toString());
		List<Map<String, String>> totalNumlist = personnelBaseService.findTotalNum(param);
		for(int i=0;i<list.size();i++){
			Map<String,String> item= list.get(i);
			for(Map<String,String> numItem:totalNumlist){
				if(item.get("table_name").equals(numItem.get("tablename"))){
                    //item.put("totalnum",numItem.get("totalnum"));
				}
			}
		}
		request.setAttribute("list", list);
		request.setAttribute("personnelBase", personnelBase);
		return "modules/personnel/nav";
	}

	/**
	 * 导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "exportOne", method= RequestMethod.POST)
	public String exportOneByTemplate(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag, int idNumber) {
		try{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName")))
				fileName= request.getParameter("fileName");
			Page<PersonnelBase> page = null;
			if(flag == true){
//				page = personnelBaseService.findPage(new Page<PersonnelBase>(request, response), personnelBase);
				page = personnelBaseService.findPageBeta(new Page<PersonnelBase>(request, response), personnelBase,false);
			}else
//				page = personnelBaseService.findPage(new Page<PersonnelBase>(request, response,-1), personnelBase);
				page = personnelBaseService.findPageBeta(new Page<PersonnelBase>(request, response), personnelBase,true);
			String filePath= Global.getUserfilesBaseDir() + File.separator + "userfiles" + File.separator + "template" + File.separator;
			InputStream inputStream = new FileInputStream(filePath + fileName);
			XSSFWorkbook wb = null;
			if (null != inputStream)
				try{
					wb = new  XSSFWorkbook(inputStream);
				} catch (IOException e){
					e.printStackTrace();
				}
			ExportExcelNew exportExcelNew = new ExportExcelNew(2, PersonnelBase.class);
			exportExcelNew.setWb(wb);
			List<PersonnelBase> list = page.getList();
			exportExcelNew.setDataList(list);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		} catch (Exception ex){
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出用户失败！失败信息："+ex);
		}
		return "redirect:" + adminPath + "/personnel/personnelBase/?repage";
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
	public String exportExcelByTemplate(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		try{
			String peopleStatus = request.getParameter("peopleStatus");
			personnelBase.setPeopleStatus(peopleStatus);
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName")))
				fileName= request.getParameter("fileName");
			Page<PersonnelBase> page = null;
			if(flag == true){
//				page = personnelBaseService.findPage(new Page<PersonnelBase>(request, response), personnelBase);
				page = personnelBaseService.findPageBeta(new Page<PersonnelBase>(request, response), personnelBase,false);
			}else
//				page = personnelBaseService.findPage(new Page<PersonnelBase>(request, response,-1), personnelBase);
				page = personnelBaseService.findPageBeta(new Page<PersonnelBase>(request, response), personnelBase,true);
			String filePath= Global.getUserfilesBaseDir() + File.separator + "userfiles" + File.separator + "template" + File.separator;
			InputStream inputStream = new FileInputStream(filePath + fileName);
			XSSFWorkbook wb = null;
			if (null != inputStream)
				try{
					wb = new  XSSFWorkbook(inputStream);
				} catch (IOException e){
					e.printStackTrace();
				}
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelBase.class);
			exportExcelNew.setWb(wb);
			List<PersonnelBase> list = page.getList();
			exportExcelNew.setDataList(list);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		} catch (Exception ex){
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出用户失败！失败信息："+ex);
		}
		return "redirect:" + adminPath + "/personnel/personnelBase/?repage";
	}

	/**
	 * 导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "exportCopy", method= RequestMethod.POST)
	public String exportCopy(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag, int idNumber) {
		try{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName")))
				fileName= request.getParameter("fileName");
			Page<PersonnelBase> page = null;
			if(flag == true){
				page = personnelBaseService.findPageCopy(new Page<PersonnelBase>(request, response), personnelBase);
			}else
				page = personnelBaseService.findPageCopy(new Page<PersonnelBase>(request, response,-1), personnelBase);
			String filePath= Global.getUserfilesBaseDir() + File.separator + "userfiles" + File.separator + "template" + File.separator;
			InputStream inputStream = new FileInputStream(filePath + fileName);
			XSSFWorkbook wb = null;
			if (null != inputStream)
				try{
					wb = new  XSSFWorkbook(inputStream);
				} catch (IOException e){
					e.printStackTrace();
				}
			ExportExcelNew exportExcelNew = new ExportExcelNew(2, PersonnelBase.class);
			exportExcelNew.setWb(wb);
			List<PersonnelBase> list = page.getList();
			exportExcelNew.setDataList(list);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		} catch (Exception ex){
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出用户失败！失败信息："+ex);
		}
		return "redirect:" + adminPath + "/personnel/personnelBase/?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelBase> list = ei.getDataList(PersonnelBase.class);
			for (PersonnelBase personnelBase : list){
				try{
					BeanValidators.validateWithException(validator, personnelBase);
					if (personnelBase.getWorkunitName()!=null&&!"".equals(personnelBase.getWorkunitName())) {
						String workunitId = officeService.findByName(personnelBase.getWorkunitName());
						if(StringUtils.isNotBlank(workunitId)){
							personnelBase.setWorkunitId(workunitId);
						}else{
							if(StringUtils.isNotBlank(personnelBase.getUnitCode())){
								Office office = officeService.findOfficeByCode(personnelBase.getUnitCode());
								workunitId = office.getId();
								if(StringUtils.isNotBlank(workunitId)){
									personnelBase.setWorkunitId(workunitId);
									personnelBase.setWorkunitName(office.getName());
								}else{
									throw new NullPointerException("导入单位名称与本系统存储单位名称匹配失败");
								}
							}else{
								throw new NullPointerException("导入单位名称与本系统存储单位名称匹配失败");
							}
						}

					}
					if (personnelBase.getActualUnit()!=null&&!"".equals(personnelBase.getActualUnit())) {
						personnelBase.setActualUnitId(officeService.findByName(personnelBase.getActualUnit()));
					}
					// 设置图片路径   /userfiles/photo/450204195912230611.jpg
					if(StringUtils.isNotBlank(personnelBase.getIdNumber())){
						personnelBase.setIdNumber(personnelBase.getIdNumber().trim());//去除两边空格
						personnelBase.setPhoto("/userfiles/photo/"+personnelBase.getIdNumber().trim()+".jpg");
					}
					personnelBaseService.save(personnelBase);
					successNum++;
				}catch(ConstraintViolationException ex){
					ex.printStackTrace();
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					ex.printStackTrace();
					failureMsg.append("(身份证号码:"+personnelBase.getIdNumber()+")"+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
			e.printStackTrace();
		}
		//redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view?id=personnel_personnelBase";
	}

	@RequestMapping(value = "importCopy", method=RequestMethod.POST)
	public String importCopy(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelBase> list = ei.getDataList(PersonnelBase.class);
			for (PersonnelBase personnelBase : list){
				try{
					BeanValidators.validateWithException(validator, personnelBase);
					if (personnelBase.getWorkunitName()!=null&&!"".equals(personnelBase.getWorkunitName())) {
						personnelBase.setWorkunitId(officeService.findByName(personnelBase.getWorkunitName()));
					}
					personnelBaseService.save(personnelBase);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+personnelBase.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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

    /**
     * 修改工作单位的弹窗
     *
     * @param ids
     * @param model
     * @return
     */
    @RequiresPermissions("personnel:personnelBase:edit")
    @RequestMapping(value = {"editUnitDialog"})
    public String editUnitDialog(String ids, Model model) {
        model.addAttribute("editPersonIds", ids);
        return "modules/personnel/personnelBaseUnitTree";
    }

    /**
     * 保存批量修改工作单位
     *
     * @param personnelBase
     * @param request
     * @return
     */
    @RequiresPermissions("personnel:personnelBase:edit")
    @RequestMapping(value = {"editWorkUnit"})
    public String editWorkUnit(PersonnelBase personnelBase, HttpServletRequest request) {
		String editPersonIds = request.getParameter("editPersonIds");
		personnelBaseService.updateWorkUnitByIds(Arrays.asList(personnelBase.getEditPersonIds()), personnelBase.getEditWorkUnitId(), personnelBase.getEditWorkUnitName());
		String[] split = editPersonIds.split(",");
		for (int i = 0; i < split.length; i++) {
			String idNumber = personnelBaseService.getIdNumberById(split[i]);
			String companyIdAct = "";
			if ("南宁铁路公安局".equals(personnelBase.getEditWorkUnitName()) || personnelBase.getEditWorkUnitName().contains("公安局")){
				companyIdAct = "1";
			}else if ("南宁公安处".equals(personnelBase.getEditWorkUnitName()) || personnelBase.getEditWorkUnitName().contains("南宁处")){
				companyIdAct = "34";
			}else if ("柳州公安处".equals(personnelBase.getEditWorkUnitName()) || personnelBase.getEditWorkUnitName().contains("柳州处")){
				companyIdAct = "95";
			}else if ("北海公安处".equals(personnelBase.getEditWorkUnitName()) || personnelBase.getEditWorkUnitName().contains("北海处")){
				companyIdAct = "156";
			}
			User userInfo = userDao.getInfoByIdNumber(idNumber);
			userDao.updateOfficeInfo(userInfo.getId(), personnelBase.getEditWorkUnitId(), companyIdAct);
		}
        request.setAttribute("saveResult", "sucess");
        return "modules/personnel/personnelBaseUnitTree";
    }

	/**
	 * 干部任免
	 * word模板需转为xml再进行修改
	 * appointment or dismissal of cadres
	 * @param personnelBase
	 * @param request
	 */
	@RequiresPermissions("personnel:personnelBase:edit")
	@RequestMapping(value = {"exportAppointRemoveCadre"})
	public void exportAppointRemoveCadre(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response) {

		//获取模板
		String fileSeperator = File.separator;
		String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;
		String fileName=request.getParameter("fileName");
		//初始化工具
		ExcelTemplate excel = new ExcelTemplate(filePath + fileName);
		Map<String,Object> map = getPersonnelBaseInfo(personnelBase,request,response);
		Map<String,Object> photo = (Map<String, Object>) map.get("excelPhoto");
			byte[] imageBytes = null;
		if (photo!= null && photo.get("imageBytes")!= null){
			imageBytes = (byte[]) photo.get("imageBytes");
		}
		try {
			excel.fillVariable(0, (Map<String, String>) map.get("sheet1"));
			int i = excel.addRowByExist(1,5,5,6, (LinkedHashMap<Integer, LinkedList<String>>) map.get("rows"),true);
			excel.fillVariable(1, (Map<String, String>) map.get("sheet2"));
			if (StringUtils.isNotBlank(personnelBase.getPhoto()) && imageBytes!=null){
				int imageType = (int) photo.get("imageType");
				excel.insertPicture(0,imageBytes, imageType,1,5,8,8);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		//下载
		try {
			/*https://blog.csdn.net/cs373616511/article/details/80325458*/
			OutputStream fout = response.getOutputStream();
			ZipOutputStream out = new ZipOutputStream(fout);
			// 清除buffer缓存
			String zipName = personnelBase.getName()+"干部任免申请表.zip";
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			//指定下载名字
			response.setHeader("Content-Disposition", "attachment; filename="+
					Encodes.urlEncode(zipName));
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");


			/*doc*/
			ZipEntry docx = new ZipEntry( personnelBase.getName()+"干部任免审批表.docx");
			out.putNextEntry(docx);
			String fileNameInResource = filePath + "干部任免审批表.docx";
//			CustomXWPFDocument document = WordTemplate.changWord(fileNameInResource, personnelBase.getName() + "干部任免审批表.docx", (Map<String, Object>) map.get("doc"), null);
//			document.write(out);
			//写入一个压缩文件
//			OutputStreamWriter writer = new OutputStreamWriter(fout);
//			String fileNameInResource = filePath+"干部任免审批表.docx";
			CustomXWPFDocument document = WordTemplate.changWord(fileNameInResource,personnelBase.getName()+"干部任免审批表.docx",(Map<String, Object>) map.get("doc"),null);
			document.write(out);
			ZipEntry xlsx = new ZipEntry( personnelBase.getName()+"干部任免审批表.xlsx");
			out.putNextEntry(xlsx);
			//写入一个压缩文件
			excel.getWorkbook().write(out);
			/*注意顺序 否则导致文件错误*/
			out.flush();
			out.close();
//			writer.close();
			fout.close();
//			response.flushBuffer();
//			doc.write(fout);

//			ZipOutputStream t = new ZipOutputStream(fout);
//			response.flushBuffer();
//			fout.close();
//			out.close();
//			t.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {

		}



	}

	/**
	 * 批量导出干部任免
	 * @param personnelBase
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 */
	@RequestMapping(value = "exportAppointRemoveBatch")
	public void exportAppointRemoveBatch(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag){
		Page<PersonnelBase> page;
		if(flag == true){
//			page = personnelBaseService.findPage(new Page<PersonnelBase>(request, response), personnelBase);
			page = personnelBaseService.findPageBeta(new Page<PersonnelBase>(request, response), personnelBase,false);
		}else
//			page = personnelBaseService.findPage(new Page<PersonnelBase>(request, response,-1), personnelBase);
			page = personnelBaseService.findPageBeta(new Page<PersonnelBase>(request, response,-1), personnelBase,true);
		response.reset();
		response.setContentType("application/octet-stream; charset=utf-8");
		//指定下载名字
		response.setHeader("Content-Disposition", "attachment; filename="+
				Encodes.urlEncode("干部任免.zip"));
		response.addHeader("Pargam", "no-cache");
		response.addHeader("Cache-Control", "no-cache");

		String fileSeperator = File.separator;
		String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;

		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ZipOutputStream finalOut = out;
		try {
			for (PersonnelBase item : page.getList()) {
				Map<String, Object> map = getPersonnelBaseInfo(item, request,response);

				/*写入xlsx*/
				Map<String,Object> photo = (Map<String, Object>) map.get("excelPhoto");
				byte[] imageBytes = (byte[]) photo.get("imageBytes");
				ExcelTemplate excel = new ExcelTemplate(filePath + "干部任免审批表.xlsx");
				excel.fillVariable(0, (Map<String, String>) map.get("sheet1"));
				excel.fillVariable(1, (Map<String, String>) map.get("sheet2"));
				int line = excel.addRowByExist(1, 5, 5, 6, (LinkedHashMap<Integer, LinkedList<String>>) map.get("rows"), true);
				if (photo.get("imageType") != null && imageBytes != null) {
					int imageType = (int) photo.get("imageType");
					excel.insertPicture(0,imageBytes, imageType,1,5,8,8);
				}
				ZipEntry xlsx = new ZipEntry(item.getName() + "干部任免审批表.xlsx");
				finalOut.putNextEntry(xlsx);
				excel.getWorkbook().write(finalOut);

				/*写入doc*/
				ZipEntry docx = new ZipEntry(item.getName() + "干部任免审批表.docx");
				finalOut.putNextEntry(docx);
				String fileNameInResource = filePath + "干部任免审批表.docx";
				CustomXWPFDocument document = WordTemplate.changWord(fileNameInResource, personnelBase.getName() + "干部任免审批表.docx", (Map<String, Object>) map.get("doc"), null);
				document.write(finalOut);


			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				/*注意顺序 否则导致文件错误*/
				finalOut.flush();
				finalOut.close();
				response.getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 民警卡片
	 * @param personnelBase
	 * @param request
	 */
	@RequiresPermissions("personnel:personnelBase:edit")
	@RequestMapping(value = {"policeCard"})
	public void exportPoliceCard(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response) {

		//获取模板
		String fileSeperator = File.separator;
		String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;

		String fileName=request.getParameter("fileName");
		//初始化工具
		ExcelTemplate excel = new ExcelTemplate(filePath + fileName);

		try {
			Map<String, Object> map = getPersonnelBaseCard(personnelBase, request,response);

			Map<String,Object> photo = (Map<String, Object>) map.get("excelPhoto");
			//写入模板
			excel.fillVariable(0, (Map<String, String>) map.get("param"));
			int i = excel.addRowByExist(0, 14, 14, 15, (LinkedHashMap<Integer, LinkedList<String>>) map.get("rows"), true);
			/*最后插入图片*/
			byte[] imageBytes = (byte[]) photo.get("imageBytes");
			if (StringUtils.isNotBlank(personnelBase.getPhoto()) && imageBytes!=null && photo.size()>0){
				int imageType = (int) photo.get("imageType");
				excel.insertPicture(0,imageBytes, imageType,1,4,10,10);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//下载
		try {
			// 清除buffer缓存
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			//指定下载名字
			response.setHeader("Content-Disposition", "attachment; filename="+
					Encodes.urlEncode(personnelBase.getName()+fileName));
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
			OutputStream fout = response.getOutputStream();
			excel.getWorkbook().write(fout);
			fout.close();

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	/**
	 * 批量导出民警卡片
	 * @param personnelBase
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 */
	@RequestMapping("exportPoliceCardBatch")
	public void exportPoliceCardBatch(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		Page<PersonnelBase> page;
		if (flag == true) {
//			page = personnelBaseService.findPage(new Page<PersonnelBase>(request, response), personnelBase);
			page = personnelBaseService.findPageBeta(new Page<PersonnelBase>(request, response), personnelBase,false);
		} else
//			page = personnelBaseService.findPage(new Page<PersonnelBase>(request, response, -1), personnelBase);
			page = personnelBaseService.findPageBeta(new Page<PersonnelBase>(request, response, -1), personnelBase,true);
		response.reset();
		response.setContentType("application/octet-stream; charset=utf-8");
		//指定下载名字
		response.setHeader("Content-Disposition", "attachment; filename=" +
				Encodes.urlEncode("民警卡片.zip"));
		response.addHeader("Pargam", "no-cache");
		response.addHeader("Cache-Control", "no-cache");

		String fileSeperator = File.separator;
		String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;

		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ZipOutputStream finalOut = out;
		try {
			for (PersonnelBase item : page.getList()) {
				Map<String, Object> map = getPersonnelBaseCard(item, request,response);
				/*写入xlsx*/
				ExcelTemplate excel = new ExcelTemplate(filePath + "民警卡片.xlsx");
				excel.fillVariable(0, (Map<String, String>) map.get("param"));
				int line = excel.addRowByExist(0,14,14,15, (LinkedHashMap<Integer, LinkedList<String>>) map.get("rows"), true);
				Map<String,Object> photo = (Map<String, Object>) map.get("excelPhoto");
				byte[] imageBytes = (byte[]) photo.get("imageBytes");
				if (photo.get("imageType") != null && imageBytes!=null){
				int imageType = (int) photo.get("imageType");
					excel.insertPicture(0,imageBytes, imageType,1,4,10,10);
				}
				ZipEntry xlsx = new ZipEntry(item.getName() + "民警卡片.xlsx");
				finalOut.putNextEntry(xlsx);
				excel.getWorkbook().write(finalOut);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				/*注意顺序 否则导致文件错误*/
				finalOut.flush();
				finalOut.close();
				response.getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取人员信息，导出民警卡片模板
	 * @param personnelBase
	 * @param request
	 * @return
	 */
	public Map<String, Object> getPersonnelBaseCard(PersonnelBase personnelBase, HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();
		if (StringUtils.isBlank(personnelBase.getIdNumber())){
			personnelBase.setIdNumber("-1");
		}
		//个人基本信息
		//基本信息
		List<Map<String, String>> registerBase = personnelBaseService.findLastRegisterBase(personnelBase);
		//查询的信息集为空时 new一个map
		Map<String, String> personnelMap;
		if (registerBase != null && registerBase.size() > 0) {
			personnelMap = registerBase.get(0);
		} else {
			personnelMap = new HashMap<>();
		}

		PersonnelXueli fullTimeEducation = new PersonnelXueli();
		fullTimeEducation.setIdNumber(personnelBase.getIdNumber());
		fullTimeEducation.setType1("1");
		fullTimeEducation = personnelXueliService.getLastByIdNumber(fullTimeEducation);
		//全日制最高学历信息
	/*	List<Map<String, String>> qrzXueLiInfo = personnelBaseService.findQrzXueLiInfo(personnelBase);
		Map<String, String> qrzXueLi;
		if (qrzXueLiInfo.size() > 0) {
			qrzXueLi = qrzXueLiInfo.get(0);
		} else {
			qrzXueLi = new HashMap<>();
		}*/
		/*Map<String, String> fullTimeEducation = Optional.ofNullable(qrzXueLi).orElseGet(() -> {
			return new HashMap<>();
		});*/
		//在职最高学历信息
		PersonnelXueli onJobEducation = new PersonnelXueli();
		onJobEducation.setIdNumber(personnelBase.getIdNumber());
		onJobEducation.setType1("2");
		onJobEducation = personnelXueliService.getLastByIdNumber(onJobEducation);
//		List<Map<String, String>> zzXueLiInfo = personnelBaseService.findZzXueLiInfo(personnelBase);
		/*Map<String, String> onJobEducation;
		if (zzXueLiInfo != null && zzXueLiInfo.size() > 0) {
			onJobEducation = zzXueLiInfo.get(0);
		} else {
			onJobEducation = new HashMap<>();
		}*/
		//全日制最高学位信息
		List<Map<String, String>> qrzXueWeiInfo = personnelBaseService.findQrzXueWeiInfo(personnelBase);
		Map<String, String> fullTimeDegree;
		if (qrzXueWeiInfo != null && qrzXueWeiInfo.size() > 0) {
			fullTimeDegree = qrzXueWeiInfo.get(0);
		} else {
			fullTimeDegree = new HashMap<>();
		}
		//在职最高学位信息
		List<Map<String, String>> zzXueWeiInfo = personnelBaseService.findZzXueWeiInfo(personnelBase);
		Map<String, String> onJobDegree;
		if (zzXueWeiInfo != null && zzXueWeiInfo.size() > 0) {
			onJobDegree = zzXueWeiInfo.get(0);
		} else {
			onJobDegree = new HashMap<>();
		}

		//履历信息
		PersonnelResume personnelResume = new PersonnelResume();
		personnelResume.setIdNumber(personnelBase.getIdNumber());
		Page<PersonnelResume> resumePage = new Page<PersonnelResume>(request,response,-1);
		resumePage.setOrderBy("start_date");
		resumePage = personnelResumeService.findPage(resumePage,personnelResume);
		List<PersonnelResume> lvLiInfo = resumePage.getList();

		//获取模板
		String fileSeperator = File.separator;
		String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;

		String fileName = request.getParameter("fileName");
		//初始化工具
		ExcelTemplate excel = new ExcelTemplate(filePath + fileName);

		Map<String, String> paramMap = new HashMap<>(16);
		/*籍贯和出生地简化*/
		/*待优化，可使用递归筛选出所有级别的行政区域*/
		personnelBase.setNativePlace(simplifyAddress(personnelBase.getNativePlace()));
		personnelBase.setBirthPlace(simplifyAddress(personnelBase.getBirthPlace()));

		//转化字典
		personnelBase.setNation(DictUtils.getDictLabel(personnelBase.getNation(), "nation", ""));
		personnelBase.setSex(DictUtils.getDictLabel(personnelBase.getSex(), "sex", ""));
		personnelBase.setPoliticsFace(DictUtils.getDictLabel(personnelBase.getPoliticsFace(), "political_status", ""));
		/*单位改成全称*/
		if (!personnelBase.getWorkunitName().contains("公安局") || !personnelBase.getWorkunitName().contains("处公安局")) {
			String tempUnit = personnelBase.getWorkunitName().replaceAll("公安局","");
			personnelBase.setWorkunitName("南宁铁路公安局" +tempUnit);
		}
		try {
			paramMap = BeanUtils.describe(personnelBase);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat s = new SimpleDateFormat("yyyy.MM");
		paramMap.put("birthday", personnelBase.getBirthday() == null ? "" : s.format(personnelBase.getBirthday()));
		int age = TimeUtils.getCurrentAge(personnelBase.getBirthday());
		paramMap.put("age", String.valueOf(age));
		/*入警时间*/
		paramMap.put("publicSecurityDate", personnelBase.getPublicSecurityDate() == null ? "" : sdf.format(personnelBase.getPublicSecurityDate()));
		//入党时间		党员信息集
		String joinPartyTime = String.valueOf(personnelMap.get("organization_date"));
		paramMap.put("organizationDate", joinPartyTime);
		/*参加工作时间*/
		paramMap.put("workDate", personnelBase.getWorkDate() == null ? "" : sdf.format(personnelBase.getWorkDate()));
		//公务员登记时间
		String civilServiceTime = String.valueOf(personnelMap.get("register_date"));
		paramMap.put("civilServiceTime", civilServiceTime);
		//职务	job_name
		//任职时间	work_date
		//职级	job_level
		//任职级时间	start_date
		//警务技术任职资格	rzzg_name
		//警务技术资格起算时间	qdzg_date
		String jobAbbreviation = personnelMap.get("job_name");
		String tenureTime = String.valueOf(personnelMap.get("work_date"));
		String jobLevel = personnelMap.get("job_level");
		jobLevel = DictUtils.getDictLabel(jobLevel,"personnel_zwcc","");
		String jobLevelTime = String.valueOf(personnelMap.get("start_date"));
		String policeTechnicalLevel = personnelMap.get("level");
		if (StringUtils.isNotBlank(policeTechnicalLevel)){
			policeTechnicalLevel = "("+policeTechnicalLevel+")";
		}else {
			policeTechnicalLevel = "";
		}
		/*警务技术任职资格 显示任职资格级别*/
		String policeTechnical = personnelMap.get("rzzg_name") + policeTechnicalLevel;
		String policeTechnicalTime = String.valueOf(personnelMap.get("qdzg_date"));

		//把数据存放到写入模板的map中
//		paramMap.put("jobAbbreviation", jobAbbreviation);
		paramMap.put("tenureTime", tenureTime);
		/*职务与职级分开显示*/
		if (personnelBase.getJobAbbreviation().equals(jobLevel)){

		}else {
			String jobName = personnelBase.getJobAbbreviation().replaceAll(jobLevel,"");
			jobName = jobName.replaceAll("、","");
			paramMap.put("jobAbbreviation",jobName);
		}
		paramMap.put("jobLevel", jobLevel);
		paramMap.put("jobLevelTime", jobLevelTime);
		paramMap.put("policeTechnical", policeTechnical);
		paramMap.put("policeTechnicalTime", policeTechnicalTime);
		//警衔
		paramMap.put("policeRank", personnelMap.get("jx_name"));
		//授衔时间

		paramMap.put("rankTime", String.valueOf(personnelMap.get("jxqs_date")));

		/*教育相关信息*/
		//全日制教育
		paramMap.put("fullTimeEducation", fullTimeEducation.getName());
		paramMap.put("fullTimeSchool", fullTimeEducation.getSchoolName());
		paramMap.put("fullTimeAcademicDegree", fullTimeDegree.get("name"));
		String fullTimeGraduation = DateUtils.formatDate(fullTimeEducation.getEndDate(),"yyyy-MM-dd");
		paramMap.put("fullTimeGraduation", fullTimeGraduation);
		/*代码存名称  名称存代码？？？*/
		paramMap.put("fullTimeMajor", fullTimeEducation.getMajorCode());
		//在职教育
		paramMap.put("onJobEducation", onJobEducation.getName());
		paramMap.put("onJobSchool", onJobEducation.getSchoolName());
		paramMap.put("onJobAcademicDegree", onJobDegree.get("name"));
		String onJobGraduation = DateUtils.formatDate(onJobEducation.getEndDate());
		paramMap.put("onJobGraduation", onJobGraduation);
		paramMap.put("onJobMajor", onJobEducation.getMajorCode());

		//简历信息
		StringBuffer resumeBuffer = new StringBuffer();
		lvLiInfo.forEach(lvLiMap -> {
			Date start_date = (lvLiMap.getStartDate());
			Date end_date = (lvLiMap.getEndDate());
			String startDate  = DateUtils.formatDate(start_date,"yyyy.MM");
			String endDate = DateUtils.formatDate(end_date,"yyyy.MM");
			if (StringUtils.isBlank(endDate)){
				endDate = "       ";
			}
			resumeBuffer.append( startDate+ "--" + endDate + "  " + lvLiMap.getUnit() +lvLiMap.getExplain()+lvLiMap.getIdentityJobExplain()+" "+ "\n");
		});

		paramMap.put("resume",StringUtils.replace(resumeBuffer.toString(),"null","  ") );
		Map<String, String> finalParamMap = paramMap;
		paramMap.forEach((key, value) -> {
			if (value==null || value.equals("null")){
				finalParamMap.put(key,"");
			}
		});
		//写入模板

		//家庭关系
		LinkedHashMap<Integer, LinkedList<String>> rows = new LinkedHashMap<>();
		PersonnelFamily personnelFamily = new PersonnelFamily();
		personnelFamily.setIdNumber(personnelBase.getIdNumber());
		List<PersonnelFamily> personnelFamilies = personnelFamilyService.findList(personnelFamily);
		for (int i = 0; i < personnelFamilies.size(); i++) {
			LinkedList<String> row = new LinkedList<>();
			row.add(personnelFamilies.get(i).getRelationship());
			row.add(personnelFamilies.get(i).getName());
			row.add(personnelFamilies.get(i).getBirthday() == null ? "" : sdf.format(personnelFamilies.get(i).getBirthday()));
			row.add(DictUtils.getDictLabel(personnelFamilies.get(i).getPoliticsFace(), "political_status", ""));
			row.add(personnelFamilies.get(i).getUnitNameJob());
			rows.put(i, row);
		}

		String photo = personnelBase.getPhoto();
		Map<String, Object> excelPhotoMap = new HashMap<>();
		if (StringUtils.isNotBlank(photo)) {
			String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();      //项目名称
			url = url + personnelBase.getPhoto();
			byte[] imageBytes = FileUtils.getImageFromNetByUrl(url);
			String pictureType = FileUtils.getContentType(photo);
			int imageType = Workbook.PICTURE_TYPE_JPEG;
			switch (pictureType) {
				case "image/png":
					imageType = Workbook.PICTURE_TYPE_PNG;
					break;
				default:
					break;
			}
			excelPhotoMap.put("imageBytes", imageBytes);
			excelPhotoMap.put("imageType", imageType);
		}


		result.put("param",finalParamMap);
		result.put("rows",rows);
		result.put("excelPhoto",excelPhotoMap);
		return result;
	}

	@RequestMapping(value = "renYuan")
	@ResponseBody
	public String exportExcelByTemplate(PersonnelRoster personnelRoster, String fileNames , HttpServletResponse response,HttpServletRequest request, String flag) {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		//获取模板
		String fileSeperator = File.separator;
		String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;

		//初始化工具
		ExcelTemplate excel = new ExcelTemplate(filePath + fileNames);

		//数据转为map
		Map<String, String> map = new HashMap<>();
		//处理字典以及日期格式
		//java8新加线程安全
//		DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");//代替simpleDateFormat
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateFormat=new SimpleDateFormat();
		//获取今年年份以及去年、前年
		Calendar calendar = Calendar.getInstance();
		int nowYer = calendar.get(Calendar.YEAR);
		int lastYear = nowYer - 1;
		int previousYear = lastYear - 1;
//		dateFormat.format(simpleDateFormat.format(affairSevenKnowledge.getBirthday()));
		try {
			//写入表头
			map.put("xuhao", "序号");
			map.put("danwei", "单位");
			map.put("bumen", "部门（队、所）");
			map.put("xingming", "姓名");
			map.put("xingbie", "性别");
			map.put("sfz", "居民身份证号");
			map.put("chusheng", "出生日期");
			map.put("minzu", "民族");
			map.put("mianmao", "政治面貌");
			map.put("dangtuan", "入党/团时间");
			map.put("jiguan", "籍贯");
			map.put("jinghao", "警号");
			map.put("gzshijian", "参加工作时间");
			map.put("gashijian", "参加公安工作时间");
			map.put("gwyshijian", "公务员登记时间");
			map.put("ryleibie", "人员类别或状态");
			map.put("zhiwu", "职务");
			map.put("renzhi", "任职时间");
			map.put("zhiji", "职级");
			map.put("zjshijian", "任职级时间");
			map.put("zige", "警务技术任职资格");
			map.put("zgshijian", "资格起算时间");
			map.put("qrzxueli", "全日制学历");
			map.put("qrzbiye", "全日制毕业时间");
			map.put("qrzzhuanye", "全日制专业");
			map.put("qrzxuexiao", "全日制学校");
			map.put("zzxueli", "在职学历");
			map.put("zzbiye", "在职毕业时间");
			map.put("zzzhuanye", "在职专业");
			map.put("zzxuexiao", "在职学校");
			map.put("qrzxuewei", "全日制学位");
			map.put("zzxuewei", "在职学位");
			map.put("jingxian", "警衔");
			map.put("jxriqi", "警衔起算日期");
			map.put("jiankang", "健康状况");
			map.put("chushengdi", "出生地");
			map.put("zhuanchang", "专长");
			map.put("gxmingcheng1", "人员于该人关系名称1");
			map.put("gxxingming1", "人员姓名1");
			map.put("gxriqi1", "人员出生日期1");
			map.put("gxmianmao1", "人员政治面貌1");
			map.put("gxzhiwu1", "人员工作单位职务1");
			map.put("gxmingcheng2", "人员于该人关系名称2");
			map.put("gxxingming2", "人员姓名2");
			map.put("gxriqi2", "人员出生日期2");
			map.put("gxmianmao2", "人员政治面貌2");
			map.put("gxzhiwu2", "人员工作单位职务2");
			map.put("gxmingcheng3", "人员于该人关系名称3");
			map.put("gxxingming3", "人员姓名3");
			map.put("gxriqi3", "人员出生日期3");
			map.put("gxmianmao3", "人员政治面貌3");
			map.put("gxzhiwu3", "人员工作单位职务3");
			map.put("gxmingcheng4", "人员于该人关系名称4");
			map.put("gxxingming4", "人员姓名4");
			map.put("gxriqi4", "人员出生日期4");
			map.put("gxmianmao4", "人员政治面貌4");
			map.put("gxzhiwu4", "人员工作单位职务4");
			map.put("gxmingcheng5", "人员于该人关系名称5");
			map.put("gxxingming5", "人员姓名5");
			map.put("gxriqi5", "人员出生日期5");
			map.put("gxmianmao5", "人员政治面貌5");
			map.put("gxzhiwu5", "人员工作单位职务5");
			map.put("gxmingcheng6", "人员于该人关系名称6");
			map.put("gxxingming6", "人员姓名6");
			map.put("gxriqi6", "人员出生日期6");
			map.put("gxmianmao6", "人员政治面貌6");
			map.put("gxzhiwu6", "人员工作单位职务6");
			map.put("gxmingcheng7", "人员于该人关系名称7");
			map.put("gxxingming7", "人员姓名7");
			map.put("gxriqi7", "人员出生日期7");
			map.put("gxmianmao7", "人员政治面貌7");
			map.put("gxzhiwu7", "人员工作单位职务7");
			map.put("jianli", "简历");
			map.put("jigou", "机构");
			map.put("jiangli", "奖励");
			map.put("chengjie", "惩戒");
			map.put("qiannian", previousYear + "年考核");
			map.put("qunian", lastYear + "年考核");
			map.put("jinian", nowYer + "年考核");
			//往模板中写入数据
			excel.fillVariable(0, map);
		} catch (IOException e) {
			e.printStackTrace();
		}

		LinkedHashMap<Integer, LinkedList<String>> rows = new LinkedHashMap<>();
		//家庭关系
		// 创建第一个行区域里面填充的值，ExcelTemplate会按从左至右，
		// 从上往下的顺序，挨个填充区域里面的${}，所以创建的时候注意顺序就好
		// 把第一个行区域row1添加进入rows
		List<PersonnelRoster> pList = new ArrayList<>();
		//基本信息
		List<Map<String, String>> registerBase = new ArrayList<>();
		if ("1".equals(flag)){
			registerBase = personnelBaseService.findRegisterBase(new PersonnelBase());
		}else {
			registerBase = personnelBaseService.findNoRegisterBase(new PersonnelBase());
		}
		//全日制最高学历信息
		List<Map<String, String>> qrzXueLiInfo = personnelBaseService.findQrzXueLiInfo(new PersonnelBase());
		//在职最高学历信息
		List<Map<String, String>> zzXueLiInfo = personnelBaseService.findZzXueLiInfo(new PersonnelBase());
		//全日制最高学位信息
		List<Map<String, String>> qrzXueWeiInfo = personnelBaseService.findQrzXueWeiInfo(new PersonnelBase());
		//在职最高学位信息
		List<Map<String, String>> zzXueWeiInfo = personnelBaseService.findZzXueWeiInfo(new PersonnelBase());
		//年度考核信息
		List<Map<String, String>> checkInfo = personnelBaseService.findCheckInfo(new PersonnelBase());
		//履历信息
		List<Map<String, String>> lvLiInfo = personnelBaseService.findLvLiInfo(new PersonnelBase());
		//奖励信息
		List<Map<String, String>> jiangLiInfo = personnelBaseService.findJiangLiInfo(new PersonnelBase());
		//惩戒信息
		List<Map<String, String>> chengJieInfo = personnelBaseService.findChengJieInfo(new PersonnelBase());
		//家庭成员信息
		List<Map<String, String>> familyInfo = personnelBaseService.findFamilyInfo(new PersonnelBase());
		int xuhao = 1;
		for (int i = 0; i < registerBase.size(); i++) {
			PersonnelRoster p = new PersonnelRoster();
			p.setXuhao(String.valueOf(i+1));
			//导出人员名册时单位显示全程
			String workunitName = registerBase.get(i).get("workunit_name");
			String unitName = "";
			if (StringUtils.isNotEmpty(workunitName)){
				if (registerBase.get(i).get("workunit_name").contains("公安局") && !registerBase.get(i).get("workunit_name").contains("南宁铁路公安局")){
					unitName = "南宁铁路公安局"+workunitName;
				}else if (registerBase.get(i).get("workunit_name").contains("南宁处")&& !registerBase.get(i).get("workunit_name").contains("南宁公安处")){
					unitName = "南宁铁路公安局南宁公安处"+workunitName;
				}else if (registerBase.get(i).get("workunit_name").contains("柳州处")&&!registerBase.get(i).get("workunit_name").contains("柳州公安处")){
					unitName = "南宁铁路公安局柳州公安处"+workunitName;
				}else if (registerBase.get(i).get("workunit_name").contains("北海处")&& !registerBase.get(i).get("workunit_name").contains("北海公安处")){
					unitName = "南宁铁路公安局北海公安处"+workunitName;
				}else if (registerBase.get(i).get("workunit_name").contains("南宁铁路公安局")){
					unitName = workunitName;
				}else if (registerBase.get(i).get("workunit_name").contains("南宁公安处") || registerBase.get(i).get("workunit_name").contains("柳州公安处") || registerBase.get(i).get("workunit_name").contains("北海公安处")){
					unitName = "南宁铁路公安局" + workunitName;
				}
			}else {
				unitName = " ";
			}

			p.setDanwei(unitName);
			p.setBumen(registerBase.get(i).get("bmhjz"));
			p.setXingming(registerBase.get(i).get("name"));
			p.setXingbie(DictUtils.getDictLabel(registerBase.get(i).get("sex"),"sex",""));
			p.setSfz(registerBase.get(i).get("id_number"));
			p.setChusheng(String.valueOf(registerBase.get(i).get("birthday")));
			p.setMinzu(DictUtils.getDictLabel(registerBase.get(i).get("nation"),"nation",""));
			p.setMianmao(DictUtils.getDictLabel(registerBase.get(i).get("politics_face"),"political_status",""));
			//入党\入团时间
			p.setDangtuan(String.valueOf(registerBase.get(i).get("organization_date")));
			if (registerBase.get(i).get("hometown") != null && registerBase.get(i).get("hometown") != ""){
				p.setJiguan(simplifyAddress(registerBase.get(i).get("hometown")));
			}else {
				p.setJiguan("");
			}
//			p.setJiguan(registerBase.get(i).get("hometown"));
			p.setJinghao(registerBase.get(i).get("police_id_number"));
			p.setGzshijian(String.valueOf(registerBase.get(i).get("work_date")));
			p.setGashijian(String.valueOf(registerBase.get(i).get("public_security_date")));
			//公务员登记时间
			p.setGwyshijian(String.valueOf(registerBase.get(i).get("register_date")));
			String personnelStatus = registerBase.get(i).get("status");
			p.setRyleibie(DictUtils.getDictLabel(personnelStatus,"personnel_status",""));
			//职务
			p.setZhiwu(registerBase.get(i).get("job_name"));
			//任职时间
			p.setRenzhi(String.valueOf(registerBase.get(i).get("work_date")));
			//职级
			String jobLevel= registerBase.get(i).get("job_level");
			p.setZhiji(DictUtils.getDictLabel(jobLevel,"personnel_zwcc",""));
//			任职级时间
			p.setZjshijian(String.valueOf(registerBase.get(i).get("start_date")));
			//警务技术任职资格
			p.setZige(registerBase.get(i).get("rzzg_name"));
//			警务技术资格起算时间
			p.setZgshijian(String.valueOf(registerBase.get(i).get("qdzg_date")));
			//警衔
			p.setJingxian(registerBase.get(i).get("jx_name"));
			//授衔时间
			p.setJxriqi(String.valueOf(registerBase.get(i).get("jxqs_date")));
			p.setJiankang(registerBase.get(i).get("health_status"));
			p.setChushengdi(simplifyAddress(registerBase.get(i).get("birth_place")));
//			p.setChushengdi(registerBase.get(i).get("birth_place"));
			p.setZhuanchang(registerBase.get(i).get("expertise"));
			String id_number = registerBase.get(i).get("id_number");
			/*教育相关信息*/
			for (int j = 0; j < qrzXueLiInfo.size(); j++) {
				String id_number1 = qrzXueLiInfo.get(j).get("id_number");
				if (registerBase.get(i).get("id_number").equals(qrzXueLiInfo.get(j).get("id_number"))){
					p.setQrzxueli(qrzXueLiInfo.get(j).get("name"));
					p.setQrzbiye(String.valueOf(qrzXueLiInfo.get(j).get("end_date")));
					p.setQrzzhuanye(qrzXueLiInfo.get(j).get("major_code"));
					p.setQrzxuexiao(qrzXueLiInfo.get(j).get("school_name"));
				}
			}
			for (int n = 0; n < zzXueLiInfo.size(); n++) {
				String id_number1 = zzXueLiInfo.get(n).get("id_number");
				if (registerBase.get(i).get("id_number").equals(zzXueLiInfo.get(n).get("id_number"))){
					p.setZzxueli(zzXueLiInfo.get(n).get("name"));
					p.setZzbiye(String.valueOf(zzXueLiInfo.get(n).get("end_date")));
					p.setZzzhuanye(zzXueLiInfo.get(n).get("major_code"));
					p.setZzxuexiao(zzXueLiInfo.get(n).get("school_name"));
				}
			}
			for (int k = 0; k < qrzXueWeiInfo.size(); k++) {
				String id_number1 = qrzXueWeiInfo.get(k).get("id_number");
				if (registerBase.get(i).get("id_number").equals(qrzXueWeiInfo.get(k).get("id_number"))) {
					p.setQrzxuewei(qrzXueWeiInfo.get(k).get("name"));
				}
			}
			for (int h = 0; h < zzXueWeiInfo.size(); h++) {
				String id_number1 = zzXueWeiInfo.get(h).get("id_number");
				if (registerBase.get(i).get("id_number").equals(zzXueWeiInfo.get(h).get("id_number"))) {
					p.setZzxuewei(zzXueWeiInfo.get(h).get("name"));
				}
			}
			int m= 1;
			for (int l = 0; l < familyInfo.size(); l++) {
				if (registerBase.get(i).get("id_number").equals(familyInfo.get(l).get("id_number"))) {
					switch (m){
						case 1:
							p.setGxmingcheng1(familyInfo.get(l).get("relationship"));
							p.setGxxingming1(familyInfo.get(l).get("name"));
							p.setGxriqi1(String.valueOf(familyInfo.get(l).get("birthday")));
							p.setGxmianmao1(familyInfo.get(l).get("politics_face"));
							p.setGxzhiwu1(familyInfo.get(l).get("unit_name_job"));
							break;
						case 2:
							p.setGxmingcheng2(familyInfo.get(l).get("relationship"));
							p.setGxxingming2(familyInfo.get(l).get("name"));
							p.setGxriqi2(String.valueOf(familyInfo.get(l).get("birthday")));
							p.setGxmianmao2(familyInfo.get(l).get("politics_face"));
							p.setGxzhiwu2(familyInfo.get(l).get("unit_name_job"));
							break;
						case 3:
							p.setGxmingcheng3(familyInfo.get(l).get("relationship"));
							p.setGxxingming3(familyInfo.get(l).get("name"));
							p.setGxriqi3(String.valueOf(familyInfo.get(l).get("birthday")));
							p.setGxmianmao3(familyInfo.get(l).get("politics_face"));
							p.setGxzhiwu3(familyInfo.get(l).get("unit_name_job"));
							break;
						case 4:
							p.setGxmingcheng4(familyInfo.get(l).get("relationship"));
							p.setGxxingming4(familyInfo.get(l).get("name"));
							p.setGxriqi4(String.valueOf(familyInfo.get(l).get("birthday")));
							p.setGxmianmao4(familyInfo.get(l).get("politics_face"));
							p.setGxzhiwu4(familyInfo.get(l).get("unit_name_job"));
							break;
						case 5:
							p.setGxmingcheng5(familyInfo.get(l).get("relationship"));
							p.setGxxingming5(familyInfo.get(l).get("name"));
							p.setGxriqi5(String.valueOf(familyInfo.get(l).get("birthday")));
							p.setGxmianmao5(familyInfo.get(l).get("politics_face"));
							p.setGxzhiwu5(familyInfo.get(l).get("unit_name_job"));
							break;
						case 6:
							p.setGxmingcheng6(familyInfo.get(l).get("relationship"));
							p.setGxxingming6(familyInfo.get(l).get("name"));
							p.setGxriqi6(String.valueOf(familyInfo.get(l).get("birthday")));
							p.setGxmianmao6(familyInfo.get(l).get("politics_face"));
							p.setGxzhiwu6(familyInfo.get(l).get("unit_name_job"));
							break;
						case 7:
							p.setGxmingcheng7(familyInfo.get(l).get("relationship"));
							p.setGxxingming7(familyInfo.get(l).get("name"));
							p.setGxriqi7(String.valueOf(familyInfo.get(l).get("birthday")));
							p.setGxmianmao7(familyInfo.get(l).get("politics_face"));
							p.setGxzhiwu7(familyInfo.get(l).get("unit_name_job"));
							break;
					}
					m++;
				}
			}
			int c = 1;
			for (int z = 0; z < checkInfo.size(); z++) {
				if (registerBase.get(i).get("id_number").equals(checkInfo.get(z).get("id_number"))) {
					if (checkInfo.get(z).get("year").equals(String.valueOf(nowYer))){
						p.setJinian(checkInfo.get(z).get("conclusion"));
					}else if (checkInfo.get(z).get("year").equals(String.valueOf(lastYear))){
						p.setQunian(checkInfo.get(z).get("conclusion"));
					}else if (checkInfo.get(z).get("year").equals(String.valueOf(previousYear))){
						p.setQiannian(checkInfo.get(z).get("conclusion"));
					}
					/*switch (c){
						case 1:
							p.setJinian(checkInfo.get(z).get("conclusion"));
							break;
						case 2:
							p.setQunian(checkInfo.get(z).get("conclusion"));
							break;
						case 3:
							p.setQiannian(checkInfo.get(z).get("conclusion"));
							break;
					}
					c++;*/
				}
			}
			/*简历信息*/
			for (int x = 0; x < lvLiInfo.size(); x++) {
				if (registerBase.get(i).get("id_number").equals(lvLiInfo.get(x).get("id_number"))) {
					String start_date = String.valueOf(lvLiInfo.get(x).get("start_date"));
					String end_date = String.valueOf(lvLiInfo.get(x).get("end_date"));
					String re_start_date = start_date.replace("-", ".");
					String re_end_date = end_date.replace("-", ".");
					String explain = lvLiInfo.get(x).get("explain");
					sb.append(re_start_date + "-" + re_end_date + " " + explain + "\n");
					p.setJianli(sb.toString());
				}
			}
			sb = new StringBuffer();
			for (int f = 0; f < jiangLiInfo.size(); f++) {
				if (registerBase.get(i).get("id_number").equals(jiangLiInfo.get(f).get("id_number"))) {
					String name = jiangLiInfo.get(f).get("name");
					String file_no = jiangLiInfo.get(f).get("file_no");
					String approcal_date = String.valueOf(jiangLiInfo.get(i).get("approcal_date"));
					String approval_org_name = jiangLiInfo.get(f).get("approval_org_name");
					sb1.append(approcal_date + " " +name + " " + file_no + " " + approval_org_name + "\n" );
					p.setJiangli(sb1.toString());
				}
			}
			sb1 = new StringBuffer();
			for (int d = 0; d < chengJieInfo.size(); d++) {
				if (registerBase.get(i).get("id_number").equals(chengJieInfo.get(d).get("id_number"))) {
					String name = chengJieInfo.get(d).get("name");
					String file_no = chengJieInfo.get(d).get("file_no");
					String approval_org = chengJieInfo.get(d).get("approval_org");
					sb2.append(name + " " + file_no + " " + approval_org + "\n");
					p.setChengjie(sb2.toString());
				}
			}
			sb2 = new StringBuffer();
			pList.add(p);
		}
		for (int i=0 ;i<pList.size();i++){

			LinkedList<String> row = new LinkedList<>();
			row.add(pList.get(i).getXuhao()==null?"":pList.get(i).getXuhao());
			row.add(pList.get(i).getDanwei()==null?"":pList.get(i).getDanwei());
			row.add(pList.get(i).getBumen()==null?"":pList.get(i).getBumen());
			row.add(pList.get(i).getXingming()==null?"":pList.get(i).getXingming());
			row.add(pList.get(i).getXingbie()==null?"":pList.get(i).getXingbie());
			row.add(pList.get(i).getSfz()==null?"":pList.get(i).getSfz());
			row.add(pList.get(i).getChusheng()==null?"":pList.get(i).getChusheng());
			row.add(pList.get(i).getMinzu()==null?"":pList.get(i).getMinzu());
			row.add(pList.get(i).getMianmao()==null?"":pList.get(i).getMianmao());
			row.add(pList.get(i).getDangtuan()==null?"":pList.get(i).getDangtuan());
			row.add(pList.get(i).getJiguan()==null?"":pList.get(i).getJiguan());
			row.add(pList.get(i).getJinghao()==null?"":pList.get(i).getJinghao());
			row.add(pList.get(i).getGzshijian()==null?"":pList.get(i).getGzshijian());
			row.add(pList.get(i).getGashijian()==null?"":pList.get(i).getGashijian());
			row.add(pList.get(i).getGwyshijian()==" "?"":pList.get(i).getGwyshijian());
			row.add(pList.get(i).getRyleibie()==null?"":pList.get(i).getRyleibie());
			row.add(pList.get(i).getZhiwu()==null?"":pList.get(i).getZhiwu());
			row.add(pList.get(i).getRenzhi()==null?"":pList.get(i).getRenzhi());
			row.add(pList.get(i).getZhiji()==null?"":pList.get(i).getZhiji());
			row.add(pList.get(i).getZjshijian()==null?"":pList.get(i).getZjshijian());
			row.add(pList.get(i).getZige()==null?"":pList.get(i).getZige());
			row.add(pList.get(i).getZgshijian()==null?"":pList.get(i).getZgshijian());
			//
			row.add(pList.get(i).getQrzxueli()==null?"":pList.get(i).getQrzxueli());
			row.add(pList.get(i).getQrzbiye()==null?"":pList.get(i).getQrzbiye());
			row.add(pList.get(i).getQrzzhuanye()==null?"":pList.get(i).getQrzzhuanye());
			row.add(pList.get(i).getQrzxuexiao()==null?"":pList.get(i).getQrzxuexiao());
			row.add(pList.get(i).getZzxueli()==null?"":pList.get(i).getZzxueli());
			row.add(pList.get(i).getZzbiye()==null?"":pList.get(i).getZzbiye());
			row.add(pList.get(i).getZzzhuanye()==null?"":pList.get(i).getZzzhuanye());
			row.add(pList.get(i).getZzxuexiao()==null?"":pList.get(i).getZzxuexiao());
			row.add(pList.get(i).getQrzxuewei()==null?"":pList.get(i).getQrzxuewei());
			row.add(pList.get(i).getZzxuewei()==null?"":pList.get(i).getZzxuewei());
			row.add(pList.get(i).getJingxian()==null?"":pList.get(i).getJingxian());
			row.add(pList.get(i).getJxriqi()==null?"":pList.get(i).getJxriqi());
			row.add(pList.get(i).getJiankang()==null?"":pList.get(i).getJiankang());
			row.add(pList.get(i).getChushengdi()==null?"":pList.get(i).getChushengdi());
			row.add(pList.get(i).getZhuanchang()==null?"":pList.get(i).getZhuanchang());
			row.add(pList.get(i).getGxmingcheng1()==null?"":pList.get(i).getGxmingcheng1());
			row.add(pList.get(i).getGxxingming1()==null?"":pList.get(i).getGxxingming1());
			row.add(pList.get(i).getGxriqi1()==null?"":pList.get(i).getGxriqi1());
			row.add(pList.get(i).getGxmianmao1()==null?"":pList.get(i).getGxmianmao1());
			row.add(pList.get(i).getGxzhiwu1()==null?"":pList.get(i).getGxzhiwu1());
			row.add(pList.get(i).getGxmingcheng2()==null?"":pList.get(i).getGxmingcheng2());
			row.add(pList.get(i).getGxxingming2()==null?"":pList.get(i).getGxxingming2());
			row.add(pList.get(i).getGxriqi2()==null?"":pList.get(i).getGxriqi2());
			row.add(pList.get(i).getGxmianmao2()==null?"":pList.get(i).getGxmianmao2());
			row.add(pList.get(i).getGxzhiwu2()==null?"":pList.get(i).getGxzhiwu2());
			row.add(pList.get(i).getGxmingcheng3()==null?"":pList.get(i).getGxmingcheng3());
			row.add(pList.get(i).getGxxingming3()==null?"":pList.get(i).getGxxingming3());
			row.add(pList.get(i).getGxriqi3()==null?"":pList.get(i).getGxriqi3());
			row.add(pList.get(i).getGxmianmao3()==null?"":pList.get(i).getGxmianmao3());
			row.add(pList.get(i).getGxzhiwu3()==null?"":pList.get(i).getGxzhiwu3());
			row.add(pList.get(i).getGxmingcheng4()==null?"":pList.get(i).getGxmingcheng4());
			row.add(pList.get(i).getGxxingming4()==null?"":pList.get(i).getGxxingming4());
			row.add(pList.get(i).getGxriqi4()==null?"":pList.get(i).getGxriqi4());
			row.add(pList.get(i).getGxmianmao4()==null?"":pList.get(i).getGxmianmao4());
			row.add(pList.get(i).getGxzhiwu4()==null?"":pList.get(i).getGxzhiwu4());
			row.add(pList.get(i).getGxmingcheng5()==null?"":pList.get(i).getGxmingcheng5());
			row.add(pList.get(i).getGxxingming5()==null?"":pList.get(i).getGxxingming5());
			row.add(pList.get(i).getGxriqi5()==null?"":pList.get(i).getGxriqi5());
			row.add(pList.get(i).getGxmianmao5()==null?"":pList.get(i).getGxmianmao5());
			row.add(pList.get(i).getGxzhiwu5()==null?"":pList.get(i).getGxzhiwu5());
			row.add(pList.get(i).getGxmingcheng6()==null?"":pList.get(i).getGxmingcheng6());
			row.add(pList.get(i).getGxxingming6()==null?"":pList.get(i).getGxxingming6());
			row.add(pList.get(i).getGxriqi6()==null?"":pList.get(i).getGxriqi6());
			row.add(pList.get(i).getGxmianmao6()==null?"":pList.get(i).getGxmianmao6());
			row.add(pList.get(i).getGxzhiwu6()==null?"":pList.get(i).getGxzhiwu6());
			row.add(pList.get(i).getGxmingcheng7()==null?"":pList.get(i).getGxmingcheng7());
			row.add(pList.get(i).getGxxingming7()==null?"":pList.get(i).getGxxingming7());
			row.add(pList.get(i).getGxriqi7()==null?"":pList.get(i).getGxriqi7());
			row.add(pList.get(i).getGxmianmao7()==null?"":pList.get(i).getGxmianmao7());
			row.add(pList.get(i).getGxzhiwu7()==null?"":pList.get(i).getGxzhiwu7());
			row.add(pList.get(i).getJianli()==null?"":pList.get(i).getJianli());
			row.add(pList.get(i).getJiangli()==null?"":pList.get(i).getJiangli());
			row.add(pList.get(i).getChengjie()==null?"":pList.get(i).getChengjie());
			row.add(pList.get(i).getQiannian()==null?"":pList.get(i).getQiannian());
			row.add(pList.get(i).getQunian()==null?"":pList.get(i).getQunian());
			row.add(pList.get(i).getJinian()==null?"":pList.get(i).getJinian());
			for (int gg =0;gg<row.size();gg++){
				if (row.get(gg).equals("null"))
					row.set(gg,"");
			}
			rows.put(i,row);

		}
		/*PersonnelFamily personnelFamily = new PersonnelFamily();
		personnelFamily.setIdNumber(affairSevenKnowledge.getIdNumber());
		List<PersonnelFamily> personnelFamilies = personnelFamilyService.findList(personnelFamily);
		personnelFamilies.forEach(family -> {
			LinkedList<String> row = new LinkedList<>();
			row.add(family.getName());
			row.add(family.getRelationship());

			row.add(family.getBirthday()==null?"":simpleDateFormat.format(family.getBirthday()));
			row.add(family.getUnitNameJob());
			row.add(family.getIdentityJob());
			row.add(DictUtils.getDictLabel(family.getNation(),"nation",""));
			row.add(family.getEducation());
			row.add(DictUtils.getDictLabel(family.getPoliticsFace(),"political_status",""));
			row.add(DictUtils.getDictLabel(family.getJobLevel(),"personnel_zwcc",""));
			row.add(family.getStatus());
			rows.put(personnelFamilies.indexOf(family),row);

		});*/
//		response.setContentType("octets/stream");
//		response.addHeader("Content-Disposition", "attachment;filename=test.xlsx");

		// 第一个参数，需要操作的sheet的索引
		// 第二个参数，需要复制的区域的第一行索引（占位符所在的第一行）
		// 第三个参数，需要复制的区域的最后一行索引（占位符所在的最后一行）
		// 第四个参数，需要插入的位置的索引(占位符的下一行)
		// 第五个参数，填充行区域中${}的值
		// 第六个参数，是否需要删除原来的区域
		// 需要注意的是，行的索引一般要减一
		try {
			int i = excel.addRowByExist(0,1,1,2,rows,true);
			//家庭成员和主要社会关系下方多处来的列合并到一个单元格
			//没有关系就不需要合并了
			if (i>0){
//				excel.mergedRegion(0,16,16+i+1,1,1);
			}
//			excel.addRowByExist(0,14,14,15,rows,true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 第一个参数，需要操作的sheet的索引
		// 第二个参数，替换sheet当中${<变量的值>}的值

		//下载
		try {
			// 清除buffer缓存
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			//指定下载名字
			response.setHeader("Content-Disposition", "attachment; fileName="+
					Encodes.urlEncode(fileNames));
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
			OutputStream fout = response.getOutputStream();
			excel.getWorkbook().write(fout);
			fout.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		/*//修改
		return "redirect:" + adminPath + "/personnel/personnelBase/?repage";*/
		return "redirect:" + adminPath + "/personnel/personnelBase/?repage";
	}

	/**
	 * 健康体检明细
	 * @param personnelBase
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("healthReferenceDetail")
	public String healthReferenceDetail(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PersonnelBase> page = personnelBaseService.findHealthReferencePage(new Page<PersonnelBase>(request,response),personnelBase);
		model.addAttribute("page", page);
		return "modules/charts/chartPersonnelBaseList";
	}

	@RequestMapping("politicsFaceDetail")
	public String politicsFaceDetail(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PersonnelBase> page = personnelBaseService.findPoliticsFacePage(new Page<PersonnelBase>(request,response),personnelBase);
		model.addAttribute("page", page);
		return "modules/charts/chartPersonnelBaseList";
	}

	public String simplifyAddress(String address){
		String siplifyAddress = "";  // 返回的结果
		if (address != null && address != ""){
			siplifyAddress= address;
			String regex="((?<province>[^省]+省|[^自治区]+自治区))(?<city>[^市]+市|[^州]+州|.+自治州)(?<county>[^县]+县|.+区|.+镇|.+局)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
			Matcher mt= Pattern.compile(regex).matcher(address);
			String province=null,city=null,county=null,town=null,village=null;

			String p = "";  // 省或者自治区
			String tp = "";  // 自治区一共五个，不好截取，如果是自治区返回这个
			String c = "";  // 市
			String cy = "";  // 县或区
			while (mt.find()){
				province = mt.group("province");
				province =  province==null?"":province.trim();
				city=mt.group("city");
				city = city==null?"":city.trim();
				county=mt.group("county");
				county = county==null?"":county.trim();
				town=mt.group("town");
				town = town==null?"":town.trim();
				village=mt.group("village");
				village = village==null?"":village.trim();
				if (province.contains("自治区") && !county.contains("区") && county.contains("县")){
					p = province.substring(0,province.indexOf("自治区"));
					switch (p){
						case "内蒙古":
							tp = "内蒙古";
							break;
						case "广西壮族":
							tp = "广西";
							break;
						case "西藏":
							tp = "西藏";
							break;
						case "宁夏回族":
							tp = "宁夏";
							break;
						case "新疆维吾尔":
							tp = "新疆维吾尔";
							break;
					}
					c = county.substring(0,county.indexOf("县"));
					int count = 0;
					String regEx = "[\\u4e00-\\u9fa5]";
					Pattern pa = Pattern.compile(regEx);
					Matcher m = pa.matcher(c);
					while (m.find()) {
						for (int i = 0; i <= m.groupCount(); i++) {
							count = count + 1;
						}
					}
					if (count == 1){
						cy = c + "县";
					}else {
						cy = c;
					}
					siplifyAddress = tp + cy;
				}else if (province.contains("省") && !county.contains("区") && county.contains("县")){
					p = province.substring(0,province.indexOf("省"));
					c = county.substring(0,county.indexOf("县"));
					int count = 0;
					String regEx = "[\\u4e00-\\u9fa5]";
					Pattern pa = Pattern.compile(regEx);
					Matcher m = pa.matcher(c);
					while (m.find()) {
						for (int i = 0; i <= m.groupCount(); i++) {
							count = count + 1;
						}
					}
					if (count == 1){
						cy = c + "县";
					}else {
						cy = c;
					}
					siplifyAddress = p + cy;
				}else if (province.contains("自治区") && county.contains("区") && city.contains("市")){
					p = province.substring(0,province.indexOf("自治区"));
					switch (p){
						case "内蒙古":
							tp = "内蒙古";
							break;
						case "广西壮族":
							tp = "广西";
							break;
						case "西藏":
							tp = "西藏";
							break;
						case "宁夏回族":
							tp = "宁夏";
							break;
						case "新疆维吾尔":
							tp = "新疆维吾尔";
							break;
					}
					c = city.substring(0,city.indexOf("市"));
					int count = 0;
					String regEx = "[\\u4e00-\\u9fa5]";
					Pattern pa = Pattern.compile(regEx);
					Matcher m = pa.matcher(c);
					while (m.find()) {
						for (int i = 0; i <= m.groupCount(); i++) {
							count = count + 1;
						}
					}
					if (count == 1){
						cy = c + "市";
					}else {
						cy = c;
					}
					siplifyAddress = tp + cy;
				}else if (province.contains("省") && county.contains("区") && city.contains("市")){
					p = province.substring(0,province.indexOf("省"));
					c = city.substring(0,city.indexOf("市"));
					int count = 0;
					String regEx = "[\\u4e00-\\u9fa5]";
					Pattern pa = Pattern.compile(regEx);
					Matcher m = pa.matcher(c);
					while (m.find()) {
						for (int i = 0; i <= m.groupCount(); i++) {
							count = count + 1;
						}
					}
					if (count == 1){
						cy = c + "市";
					}else {
						cy = c;
					}
					siplifyAddress = p + cy;
				}else {
					siplifyAddress = address;
				}

			}

		}else {
			siplifyAddress = "";
		}
		return siplifyAddress;
	}


	/**
	 * 获取干部任免信息，用户导出模板
	 * @param personnelBase
	 * @param request
	 * @return
	 */
	public Map<String,Object> getPersonnelBaseInfo(PersonnelBase personnelBase, HttpServletRequest request,HttpServletResponse response){
		if (StringUtils.isBlank(personnelBase.getIdNumber())){
			personnelBase.setIdNumber("-1");
		}
		/*结果返回*/
		Map<String,Object> resultMap = new HashMap<>();
		/*excel中的sheet1需要参数*/
		Map<String, String> paramMap = new HashMap<>(16);
		/*excel中的sheet2需要参数*/
		Map<String,String> map=new HashMap();
		//excel家庭关系
		LinkedHashMap<Integer, LinkedList<String>> rows = new LinkedHashMap<>();

		/*导出到doc*/
		Map<String,Object> objectMap = new HashMap<>();

		//学历
		//全日制最高学历信息
		/*改为按照时间  不是按照学历等级*/
//		List<Map<String, String>> qrzXueLiInfo = personnelBaseService.findQrzXueLiInfo(personnelBase);
		PersonnelXueli fullTimeEducation = new PersonnelXueli();
		fullTimeEducation.setType1("1");
		fullTimeEducation.setIdNumber(personnelBase.getIdNumber());
		fullTimeEducation = personnelXueliService.getLastByIdNumber(fullTimeEducation);
		//在职最高学历信息

		PersonnelXueli onJobEducation = new PersonnelXueli();
		onJobEducation.setType1("2");
		onJobEducation.setIdNumber(personnelBase.getIdNumber());
		onJobEducation = personnelXueliService.getLastByIdNumber(onJobEducation);
//		List<Map<String, String>> zzXueLiInfo = personnelBaseService.findZzXueLiInfo(personnelBase);
		//全日制最高学位信息
		List<Map<String, String>> qrzXueWeiInfo = personnelBaseService.findQrzXueWeiInfo(personnelBase);
		//在职最高学位信息
		List<Map<String, String>> zzXueWeiInfo = personnelBaseService.findZzXueWeiInfo(personnelBase);

		//履历信息
		PersonnelResume personnelResume = new PersonnelResume();
		personnelResume.setIdNumber(personnelBase.getIdNumber());
		Page<PersonnelResume> lvLiPage = new Page<>(request,response,-1);
		lvLiPage.setOrderBy("start_date");
		lvLiPage = personnelResumeService.findPage(lvLiPage,personnelResume);
		List<PersonnelResume> lvLiInfo = lvLiPage.getList();

		//基本信息  入党时间
		List<Map<String, String>> registerBase = personnelBaseService.findLastRegisterBase(personnelBase);
		Map<String,String> registerMap = new HashMap<>();
		if (registerBase!=null && registerBase.size()>0){
			registerMap = registerBase.get(0);
		}
		PersonnelPoliceWork1 personnelPoliceWork1 = new PersonnelPoliceWork1();
		personnelPoliceWork1.setIdNumber(personnelBase.getIdNumber());
		//专业警务技术
		List<PersonnelPoliceWork1> policeWork1List = personnelPoliceWork1Service.findList(personnelPoliceWork1);

		try {
			/*籍贯和出生地简化*/
			/*待优化，可使用递归筛选出所有级别的行政区域*/
			personnelBase.setNativePlace(simplifyAddress(personnelBase.getNativePlace()));
			personnelBase.setBirthPlace(simplifyAddress(personnelBase.getBirthPlace()));
			//转化字典
//			affairSevenKnowledge.setHasGun(DictUtils.getDictLabel(affairSevenKnowledge.getHasGun(),"yes_no",""));
			personnelBase.setNation(DictUtils.getDictLabel(personnelBase.getNation(),"nation",""));
			personnelBase.setSex(DictUtils.getDictLabel(personnelBase.getSex(),"sex",""));

			paramMap = BeanUtils.describe(personnelBase);
			paramMap.put("jobFullname",personnelBase.getJobFullname());
			try {
				paramMap.put("jobAbbreviation",policeWork1List.get(0).getJobName());
			}catch (Exception e){
				paramMap.put("jobAbbreviation","");
			}


			//格式化日期	修改日期格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
			String birthday = personnelBase.getBirthday()==null?"":sdf.format(personnelBase.getBirthday());
			paramMap.put("birthday",birthday);
			int age = TimeUtils.getCurrentAge(personnelBase.getBirthday());
			paramMap.put("age", String.valueOf(age));
			//入党时间		党员信息集
			String joinPartyTime = String.valueOf(registerMap.get("organization_date"));


			paramMap.put("joinPartyTime",DateUtils.parseDate(joinPartyTime)==null?"":sdf.format(DateUtils.parseDate(joinPartyTime)));
			joinPartyTime = DateUtils.formatDate(DateUtils.parseDate(joinPartyTime),"yyyy.MM");
			paramMap.put("joinPartyTime",joinPartyTime+" ");
			//工作时间
			paramMap.put("workDate",personnelBase.getWorkDate()==null?"":sdf.format(personnelBase.getWorkDate())+" ");
			//学历学位
			//全日制
			/*学历名称*/
			/*String fullEducationName = null;
			String fullSchoolName = null;
			String fullMajorName = null;
			if (qrzXueLiInfo.size() > 0) {
				fullEducationName = qrzXueLiInfo.get(0).get("name");
				fullSchoolName = qrzXueLiInfo.get(0).get("school_name");
				fullMajorName = qrzXueLiInfo.get(0).get("classify");
			}*/
			String fullTimeDegree = "";
			if (qrzXueWeiInfo.size()>0){
				fullTimeDegree= qrzXueWeiInfo.get(0).get("name");
			}
			//全日制教育
			paramMap.put("fullTimeEducation", fullTimeEducation.getName()+"\n"+fullTimeDegree);
			//毕业院校及专业
			String fullTimeSchool = fullTimeEducation.getSchoolName()+ "\n" + fullTimeEducation.getMajorCode();
			paramMap.put("fullTimeSchool", StringUtils.replace(fullTimeSchool,"null",""));
			//在职
			/*String jobEducationName = null;
			String jobSchoolName = null;
			String jobMajorName = null;
			if (zzXueLiInfo.size() > 0) {
				jobEducationName = zzXueLiInfo.get(0).get("name");
				jobSchoolName = zzXueLiInfo.get(0).get("school_name");
				jobMajorName = zzXueLiInfo.get(0).get("classify");
			}*/
			String onJobDegree = "";
			if (zzXueWeiInfo.size() > 0) {
				onJobDegree=zzXueWeiInfo.get(0).get("name");
			}
			//在职制教育
			paramMap.put("onJobEducation", onJobEducation.getName()+"\n"+onJobDegree);
			//毕业院校及专业
			String onJobSchool =onJobEducation.getSchoolName()+ "\n" + onJobEducation.getMajorCode();
			paramMap.put("onJobSchool", StringUtils.replace(onJobSchool,"null",""));

			//简历信息
			StringBuffer lvLiStringBuffer=new StringBuffer();
			lvLiInfo.forEach(lvLiMap -> {
				Date start_date = (lvLiMap.getStartDate());

				Date end_date = (lvLiMap.getEndDate());
				String startDate = DateUtils.formatDate(start_date,"yyyy.MM");
				String endDate = DateUtils.formatDate(end_date,"yyyy.MM");
				if (StringUtils.isBlank(endDate)){
					endDate = "       ";
				}
				lvLiStringBuffer.append(startDate + "--" + endDate + "  " + lvLiMap.getUnit() +lvLiMap.getExplain()+lvLiMap.getIdentityJobExplain()+ "\r\n");
			});
			paramMap.put("resume",StringUtils.replace(lvLiStringBuffer.toString(),"null","  ") );

			//年度考核信息
			List<Map<String, String>> checkInfo = personnelBaseService.findCheckInfo(personnelBase);
			Calendar calendar = Calendar.getInstance();
			int nowYer = calendar.get(Calendar.YEAR);
			StringBuffer assessment=new StringBuffer();
			if (checkInfo.size()>0){
				for (Map<String,String> checkInfoMap:checkInfo){
					assessment.append(checkInfoMap.get("year"));
					assessment.append("  ");
					assessment.append(checkInfoMap.get("conclusion"));
					assessment.append("\n");
				}
			}
			map.put("assessment",assessment.toString());

			//奖励信息
			List<Map<String, String>> jiangLiInfo = personnelBaseService.findJiangLiInfo(personnelBase);
			//惩戒信息
			List<Map<String, String>> chengJieInfo = personnelBaseService.findChengJieInfo(personnelBase);

			/*奖惩*/
			/*奖惩显示字段：
			批准日期、批准机构名称、奖励（惩戒）名称
			先奖励  后惩戒，时间先后排序*/
			StringBuffer rewardsPunishments = new StringBuffer();
			jiangLiInfo.forEach(prizeStringMap ->  {
				try{

				String name = prizeStringMap.get("name");
//				String file_no = prizeStringMap.get("file_no");
				Object approcal = prizeStringMap.get("approcal_date");
				String approvalDate = DateUtils.formatDate(DateUtils.parseDate(approcal),"yyyy-MM-dd");
				String approvalOrgName = prizeStringMap.get("approval_org_name");
				rewardsPunishments.append(approvalDate + " " + approvalOrgName + " " + name + "\n" );
				}catch (Exception e){
					logger.debug("干部任免导出  奖惩信息 时间转换异常");
					e.printStackTrace();
				}
			});
			chengJieInfo.forEach( punishMentMap -> {
				try {
				String name = punishMentMap.get("name");
//				String file_no = punishMentMap.get("file_no");

				Object approval = punishMentMap.get("approval_date");
				String approvalDate = DateUtils.formatDate(DateUtils.parseDate(approval),"yyyy-MM-dd");
				String approval_org = punishMentMap.get("approval_org");
				rewardsPunishments.append(approvalDate + " " + approval_org + " " + name + "\n");
				}catch (Exception  e){
					logger.debug("干部任免导出  奖惩信息 时间转换异常");
					e.printStackTrace();
				}
			});

			map.put("rewardsPunishments",rewardsPunishments.toString());


			PersonnelFamily personnelFamily = new PersonnelFamily();
			personnelFamily.setIdNumber(personnelBase.getIdNumber());
			List<PersonnelFamily> personnelFamilies = personnelFamilyService.findList(personnelFamily);
			personnelFamilies.forEach(family -> {
				LinkedList<String> row = new LinkedList<>();
				row.add(family.getRelationship());
				row.add(family.getName());
				row.add(family.getBirthday()==null?"": String.valueOf(TimeUtils.getCurrentAge(family.getBirthday())));
				row.add(DictUtils.getDictLabel(family.getPoliticsFace(),"political_status",""));
				row.add(family.getUnitNameJob());
				rows.put(personnelFamilies.indexOf(family),row);
			});
			Map<String, String> finalParamMap = paramMap;
			/*去除字符为空null的 并把数据添加到objectMap,以供导出doc使用*/
			paramMap.forEach((key, value) -> {
				if (value==null || value.equals("null")){
					finalParamMap.put(key,"");
					objectMap.put(key,"");
				}else{
					finalParamMap.put(key,value);
					objectMap.put(key,value);
				}
			});
			map.forEach((key, value) -> {
				objectMap.put(key,value);
			});
			/*家庭关系*/
			rows.forEach((key, value) -> {
				for (int cloumn = 1;cloumn<=value.size();cloumn++){
					objectMap.put("f"+key+cloumn,value.get(cloumn-1));
				}
			});
			/*清空剩余的家庭关系*/
			for (int j =rows.size()+1;j<=7;j++){
				for (int s=1;s<=4;s++){
					objectMap.put("f"+j+s,"");
				}
			}

			/*word使用图片*/
			String photo = personnelBase.getPhoto();
			Map<String, Object> excelPhotoMap = new HashMap<>();
			if (StringUtils.isNotBlank(photo)) {
				String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();      //项目名称
				url = url + personnelBase.getPhoto();
				byte[] imageBytes = FileUtils.getImageFromNetByUrl(url);
				String pictureType = FileUtils.getContentType(photo);
				int imageType = Workbook.PICTURE_TYPE_JPEG;
				switch (pictureType) {
					case "image/png":
						imageType = Workbook.PICTURE_TYPE_PNG;
						break;
					default:
						break;
				}
				excelPhotoMap.put("imageBytes", imageBytes);
				excelPhotoMap.put("imageType", imageType);
			}

			objectMap.put("jc",objectMap.get("rewardsPunishments"));
			objectMap.put("age","("+objectMap.get("age")+"岁)");
			resultMap.put("sheet1",finalParamMap);
			resultMap.put("sheet2",map);
			resultMap.put("rows",rows);
			resultMap.put("doc",objectMap);
			resultMap.put("excelPhoto",excelPhotoMap);

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}


		return resultMap;

	}

	@ResponseBody
	@RequestMapping(value = "treeDataByOfficeId")
	public List<Map<String, Object>> treeDataByOfficeId(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<PersonnelBase> list = personnelBaseService.findInfoByIdUnitId(officeId);
		for (int i=0; i<list.size(); i++){
			PersonnelBase personnelBase = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", personnelBase.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(personnelBase.getName(), " ", ""));
			map.put("idNumber",personnelBase.getIdNumber());
			mapList.add(map);
		}
		return mapList;
	}

	@RequiresPermissions("personnel:personnelBase:view")
	@RequestMapping(value = "complexSelForm")
	public String complexSelForm(PersonnelBase personnelBase, Model model,HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("customSql")!=null){
			session.removeAttribute("customSql");//每次打开复合查询页面，清除下存储在session中的customSql数据
		}
		if(session.getAttribute("columnTextList")!=null){
			session.removeAttribute("columnTextList");//每次打开复合查询页面，清除下存储在session中的columnTextList数据
		}
		if(session.getAttribute("columnList")!=null){
			session.removeAttribute("columnList");
		}
		List<Map<String, String>> tableList=personnelBaseService.findNavTableName();
        List<Dict> operatorList = DictUtils.getDictList("personnel_sel_operator");
        model.addAttribute("tableList",tableList);
        model.addAttribute("operatorList",operatorList);
		return "modules/personnel/personnelComplexSelForm";
	}
	/**
	 * 获得人员概况树形选择器 ，未放入缓存中
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "complexSelTreeData")
	public List<Map<String, String>> complexSelTreeData() {
		List<Map<String, String>> list=personnelBaseService.findNavTableName();
		return list;
	}

	@RequiresPermissions("personnel:personnelBase:view")
	@RequestMapping(value = {"ComplexSelIndex"})
	public String ComplexSelIndex(PersonnelBase personnelBase, Model model) {
		return "modules/personnel/personnelComplexSelIndex";
	}
	@RequestMapping(value = {"selColumnByTableName"})
	@ResponseBody
	public Result selColumnByTableName(String tableName){
		Result result = new Result();
		List<Map<String,String>> list = personnelComplexSelService.selColumnByTableName(tableName);
		result.setSuccess(true);
		result.setResult(list);
		return result;
	}

	/*
	 * 根据表名查出相应字段
	 * */
	@RequestMapping(value = {"selColumnListByTableName"})
	@RequiresPermissions("personnel:personnelBase:view")
	public String selColumnListByTableName(@RequestParam("tableName") String tableName,Model model,@RequestParam("tableId")String tableId){
		List<Map<String,String>> list = personnelComplexSelService.selColumnByTableName(tableName);
		model.addAttribute("list",list);
		model.addAttribute("tableName",tableName);
		model.addAttribute("tableId",tableId);
		return "modules/personnel/personnelComolexColumnList";
	}

	/**
	 * 复合条件查询具体方法
	 * @param  submitTableName 展示字段来自表
	 * @param  submitCheckColumntext 选择展示的字段名
	 * @param  submitCheckColumnvalue 选择展示的字段
	 * @param  mapListStr where条件json字符串
	 */
	@RequestMapping(value = {"customSelSql"})
	@RequiresPermissions("personnel:personnelBase:view")
	@ResponseBody
	public Result customSelSql(HttpServletRequest request, Model model,
							   @RequestParam("submitTableName") String submitTableName,
							   @RequestParam("submitCheckColumntext") String submitCheckColumntext,
							   @RequestParam("submitCheckColumnvalue") String submitCheckColumnvalue,
							   @RequestParam("listMap") String mapListStr,
							   RedirectAttributes redirectAttributes) {
		Result result = new Result();
		try{
			GsonBuilder builder = new GsonBuilder();
			/*如果不设置serializeNulls,序列化时默认忽略NULL*/
			builder.serializeNulls();   // 1
			/*使打印的json字符串更美观，如果不设置，打印出来的字符串不分行*/
			builder.setPrettyPrinting();
			Gson gson = builder.create();
			// 转义处理
			String tempStr = StringEscapeUtils.unescapeHtml4(mapListStr);//将字符串中&quot;反转义为"
			//String tempStr = mapListStr.replace("&quot;","");//将json字符串中的&quot;替换为 空
			List<Map<String,Object>> mapList = gson.fromJson(tempStr, new TypeToken<List<HashMap<String,String>>>(){}.getType());
			Map<String,Object> resultMap = personnelComplexSelService.createCustomSql(submitTableName,submitCheckColumntext,submitCheckColumnvalue,mapList);
			List<Map<String,Object>> resultList = (List<Map<String, Object>>) resultMap.get("resultList");
			List<String> columnTextList = (List<String>) resultMap.get("columnText");
			List<String> columnList = (List<String>) resultMap.get("columnList");
			String customSql = (String) resultMap.get("customSql");
			if(columnTextList.size()>0){
				result.setSuccess(true);
				result.setResult(resultMap);
			}else{
				result.setSuccess(false);
				result.setResult(resultMap);
			}
			HttpSession session = request.getSession();
			session.setAttribute("customSql",customSql);
			session.setAttribute("columnTextList",columnTextList);
			session.setAttribute("columnList",columnList);
			//session.setAttribute("resultList",resultList);
		}catch (JsonSyntaxException e){
			e.printStackTrace();
			logger.error(e.toString());
			result.setSuccess(false);
			result.setMessage("运行时发生错误，请联系管理员查看日志"+DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.toString());
			result.setSuccess(false);
			result.setMessage("查询失败,请按照要求选择；如果填写正确，请联系管理员查看日志"+DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		}
		return result;
	}


	/*
	 * 批量查询页面跳转
	 * */
	@RequiresPermissions("personnel:personnelBase:view")
	@RequestMapping(value = "batchSelForm")
	public String batchSelForm(PersonnelBase personnelBase, Model model,HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("customBatchSql")!=null){
			session.removeAttribute("customBatchSql");//进入批量查询页面后，首先将session存储的sql语句删除
		}
		List<Dict> personnelStatusList = DictUtils.getDictList("personnel_status");
		model.addAttribute("personnelStatusList",personnelStatusList);
		model.addAttribute("personnelBase",personnelBase);
		return "modules/personnel/personnelBatchSelForm";
	}

	/**
	 * 批量查询具体方法
	 * @param  file excel文件
	 * @param  selItems 查询项
	 * @param  sheetPage 所在sheet
	 * @param  startLine 数据开始行
	 * @param  excelColumn 所在列
	 * @param  unitId 所属单位
	 * @param  status 人员状态
	 */
	@RequiresPermissions("personnel:personnelBase:view")
	@RequestMapping(value = "batchSelQuery")
	@ResponseBody
	public Result batchSelQuery(@RequestParam("file") MultipartFile file ,
								@RequestParam("selItems") String selItems,
								@RequestParam("sheetPage") String sheetPage,
								@RequestParam("startLine") String startLine,
								@RequestParam("excelColumn") String excelColumn,
								@RequestParam("unitId") String unitId,
								@RequestParam("status") String status,
								Model model,HttpServletRequest request) {
		Result result = new Result();
		try {
			Map<String,Object> resultMap = personnelComplexSelService.createCustomBatchSql(file,selItems,sheetPage,startLine,excelColumn,unitId,status);
			boolean returnResult = (boolean) resultMap.get("result");
			if(returnResult){
				List<PersonnelBatchSel> resultList = (List<PersonnelBatchSel>) resultMap.get("resultList");
				String customBatchSql = (String) resultMap.get("customBatchSql");
				HttpSession session = request.getSession();
				session.setAttribute("customBatchSql",customBatchSql);
				result.setSuccess(true);
				result.setResult(resultList);
			}else{
				String failMessage = (String) resultMap.get("failMessage");
				result.setSuccess(false);
				result.setMessage(failMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("发生错误，确认所有信息填写正确后，再次重试");
		}
		return result;
	}

	/**
	 * 判断字段是否采用字典
	 * @param  tableSel 表名
	 * @param  columnSel 字段
	 */
	@RequiresPermissions("personnel:personnelBase:view")
	@RequestMapping(value = "isDictFields")
	@ResponseBody
	public Result isDictFields(HttpServletRequest request, Model model, @RequestParam("tableSel") String tableSel,
							   @RequestParam("columnSel") String columnSel, RedirectAttributes redirectAttributes) {
		Result result = new Result();
		try {
			Map<String,Object> resultMap = personnelComplexSelService.isDictFields(tableSel,columnSel);
			result.setSuccess(true);
			result.setResult(resultMap);
		}catch (Exception e){
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("运行中发生错误，请重试,"+e.getMessage());
		}
		return result;
	}


	/**
	 * 复合查询结果导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "complexSelexport", method= RequestMethod.POST)
	public String complexSelexport(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		try{
			HttpSession session = request.getSession();
			String customSql = (String) session.getAttribute("customSql");
			List<String> columnTextList = (List<String>) session.getAttribute("columnTextList");
			List<String> columnList = (List<String>) session.getAttribute("columnList");
			String fileName = "复合查询结果导出表.xlsx";
			Page<Map<String, Object>> complexSelPage = null;
			if(flag == true){
				//当前页
				complexSelPage = personnelComplexSelService.execCustomSql(new Page<Map<String, Object>>(request,response),customSql,columnTextList,columnList);
			}else{
				//全部
				complexSelPage = personnelComplexSelService.execCustomSqlAllResult(new Page<Map<String, Object>>(request,response),customSql,columnTextList,columnList);
			}
			List<Map<String, Object>> mapList = complexSelPage.getList();
			//创建XSSFSheet对象
			XSSFWorkbook wb = new XSSFWorkbook();
			//创建XSSFSheet对象
			XSSFSheet sheet = wb.createSheet("sheet1");
			//设置表头
			XSSFRow titleRow = sheet.createRow(0);
			for(int i = 0;i<columnTextList.size();i++){
				//创建XSSFCell对象
				XSSFCell cell=titleRow.createCell(i);
				//设置单元格的值
				cell.setCellValue(columnTextList.get(i));
				XSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setAlignment(HorizontalAlignment.CENTER); // 指定单元格居中对齐
				XSSFFont font = wb.createFont();
				font.setBold(true);
				cellStyle.setFont(font);//设置字体格式
				cell.setCellStyle(cellStyle);
			}
			//设置表格数据
			for(int i = 0;i<mapList.size();i++){
				XSSFRow row = sheet.createRow(i+1);
				Map<String,Object> map = mapList.get(i);
				for(int j = 0;j<columnTextList.size();j++){
					XSSFCell cell = row.createCell(j);
					if(map.get(columnTextList.get(j))!=null){
						cell.setCellValue(map.get(columnTextList.get(j)).toString());
					}else{
						cell.setCellValue("");
					}
					if(i==0){
						int length = cell.getStringCellValue().getBytes().length * 256 + 512;
						if(cell.getStringCellValue().isEmpty()){
							length = columnTextList.get(j).getBytes().length * 256 + 512;
						}
						if((cell.getStringCellValue().getBytes().length * 256 + 512)<(columnTextList.get(j).getBytes().length * 256 + 512)){
							length = columnTextList.get(j).getBytes().length * 256 + 512;
						}
						//这里把宽度最大限制到15000
						if (length > 15000) {
							length = 15000;
						}
						sheet.setColumnWidth(j,length);
					}
				}
			}
			// 清除buffer缓存
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			//指定下载名字
			response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		} catch (Exception ex){
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出复合查询结果失败！失败信息："+ex);
		}

		return "redirect:" + adminPath + "/personnel/personnelBase/?isComplexSelForm=isComplexSelForm";
	}

	/**
	 * 批量查询结果导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "batchSelexport", method= RequestMethod.POST)
	public String batchSelexport(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		try {
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName")))
				fileName = request.getParameter("fileName");
			HttpSession session = request.getSession();
			String customSql = (String) session.getAttribute("customBatchSql");
			Page<PersonnelBase> batchSqlResultPage = personnelComplexSelService.execBatchCustomSql(new Page<PersonnelBase>(request, response), customSql);
			Page<PersonnelBase> page = null;
			if (flag == true) {
				page = personnelComplexSelService.execBatchCustomSql(new Page<PersonnelBase>(request, response), customSql);
			} else {
				page = personnelComplexSelService.execBatchCustomSqlAllResult(new Page<PersonnelBase>(request, response), customSql);
			}
			String filePath = Global.getUserfilesBaseDir() + File.separator + "userfiles" + File.separator + "template" + File.separator;
			InputStream inputStream = new FileInputStream(filePath + fileName);
			XSSFWorkbook wb = null;
			if (null != inputStream)
				try {
					wb = new XSSFWorkbook(inputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelBase.class);
			exportExcelNew.setWb(wb);
			//通过身份证获取人员详细信息
			List<PersonnelBase> list = personnelBaseService.findInfoByList(page.getList());
			//List<PersonnelBase> list = page.getList();
			exportExcelNew.setDataList(list);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出批量查询结果失败！失败信息：" + ex);
		}
		return "redirect:" + adminPath + "/personnel/personnelBase/?isBatchSelForm=isBatchSelForm";
	}

	@RequestMapping(value = {"personnelAgeList"})
	public String personnelAgeDetail(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response, Model model) {
			Page<PersonnelBase> page = personnelBaseService.findAgePage(new Page<PersonnelBase>(request, response), personnelBase);
			if(request.getParameter("type")!=null && !"".equals(request.getParameter("type")))
				request.setAttribute("type", request.getParameter("type"));
			if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber")))
				personnelBase.setIdNumber(request.getParameter("idNumber"));
			model.addAttribute("page", page);

			model.addAttribute("type","age");
		return "modules/charts/chartsPersonnelBaseList";
	}

	@RequestMapping(value = {"chartsNationList"})
	public String chartsNationDetail(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response, Model model) {
			Page<PersonnelBase> page = personnelBaseService.findChartsNationPage(new Page<PersonnelBase>(request, response), personnelBase);
			if(request.getParameter("type")!=null && !"".equals(request.getParameter("type")))
				request.setAttribute("type", request.getParameter("type"));
			if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber")))
				personnelBase.setIdNumber(request.getParameter("idNumber"));
			model.addAttribute("page", page);
		model.addAttribute("type","nation");
		return "modules/charts/chartsPersonnelBaseList";
	}

	@RequestMapping(value = {"chartsCategoryList"})
	public String chartsCategoryDetail(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response, Model model) {
			Page<PersonnelBase> page = personnelBaseService.findChartsCategoryPage(new Page<PersonnelBase>(request, response), personnelBase);
			if(request.getParameter("type")!=null && !"".equals(request.getParameter("type")))
				request.setAttribute("type", request.getParameter("type"));
			if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber")))
				personnelBase.setIdNumber(request.getParameter("idNumber"));
			model.addAttribute("page", page);
		model.addAttribute("type","category");
		return "modules/charts/chartsPersonnelBaseList";
	}

	@RequestMapping(value = {"chartsWorkYearList"})
	public String chartsWorkYearDetail(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response, Model model) {
			Page<PersonnelBase> page = personnelBaseService.findChartsWorkYearPage(new Page<PersonnelBase>(request, response), personnelBase);
			if(request.getParameter("type")!=null && !"".equals(request.getParameter("type")))
				request.setAttribute("type", request.getParameter("type"));
			if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber")))
				personnelBase.setIdNumber(request.getParameter("idNumber"));
			model.addAttribute("page", page);
		model.addAttribute("type","workYear");
		return "modules/charts/chartsPersonnelBaseList";
	}
}