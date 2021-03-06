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
 * ????????????????????????Controller
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
		//?????????????????????????????????????????????
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
				model.addAttribute("message","?????????????????????????????????????????????????????????????????????"+ DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
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
		//????????????????????????
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
				model.addAttribute("message","?????????????????????????????????????????????????????????????????????"+ DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			}
			return "modules/personnel/personnelBaseList";
		}else{
			if(session.getAttribute("customBatchSql")!=null){
				session.removeAttribute("customBatchSql");
			}
		}
		//r?????????????????? ??????????????????  ?????????????????????
		//personnel??????????????? ????????????????????? ????????? ???????????????????????????
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
			/*????????????????????????????????????*/
			String userId = UserUtils.getUser().getId();
			/*??????????????????????????????????????????????????????*/
			if (userId.equals("bd42300887ad417fa3f2fa9f554e6419") || userId.equals("1c19f6cc935f430f9f27295b761b1236") || userId.equals("3850cecf34be44188f94b0edc552aff3")
					|| userId.equals("69f857c3e1854021b5dee55c514026e3")){
				Page<PersonnelBase> page = personnelBaseService.findPageOfWorkUnit(new Page<PersonnelBase>(request, response), personnelBase);
				model.addAttribute("page", page);

			}else {
				/*????????????????????????????????????*/
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
		//r?????????????????? ??????????????????  ?????????????????????
		//personnel??????????????? ????????????????????? ????????? ???????????????????????????
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
	* ????????????????????????????????????
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
		addMessage(redirectAttributes, "????????????????????????????????????");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelBaseForm";
	}
	
	@RequiresPermissions("personnel:personnelBase:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelBase personnelBase, RedirectAttributes redirectAttributes) {
		personnelBaseService.delete(personnelBase);
		addMessage(redirectAttributes, "????????????????????????????????????");
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
			result.setMessage("????????????");
		}else{
			result.setSuccess(false);
			result.setMessage("??????????????????????????????");
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
		//?????????
		String userId = personnelBase.getIdNumber();

		//????????????
		List<AffairPersonalReward> reward = affairPersonalRewardDao.selectRewardByUserId(userId);

		//????????????
		List<AffairDisciplinaryAction> disciplinaryActions = affairDisciplinaryActionDao.selectDisciplinaryActionsById(userId);


		personnelBase = personnelBaseService.findInfoByIdNumber(personnelBase.getIdNumber());
		/*????????????*/
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
	 * ????????????????????????????????? ?????????????????????
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
			request.setAttribute("mType","2");//??????????????????
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
	 * ??????excel????????????
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
			addMessage(redirectAttributes, "????????????????????????????????????"+ex);
		}
		return "redirect:" + adminPath + "/personnel/personnelBase/?repage";
	}

	/**
	 * ??????excel????????????
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
			addMessage(redirectAttributes, "????????????????????????????????????"+ex);
		}
		return "redirect:" + adminPath + "/personnel/personnelBase/?repage";
	}

	/**
	 * ??????excel????????????
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
			addMessage(redirectAttributes, "????????????????????????????????????"+ex);
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
									throw new NullPointerException("????????????????????????????????????????????????????????????");
								}
							}else{
								throw new NullPointerException("????????????????????????????????????????????????????????????");
							}
						}

					}
					if (personnelBase.getActualUnit()!=null&&!"".equals(personnelBase.getActualUnit())) {
						personnelBase.setActualUnitId(officeService.findByName(personnelBase.getActualUnit()));
					}
					// ??????????????????   /userfiles/photo/450204195912230611.jpg
					if(StringUtils.isNotBlank(personnelBase.getIdNumber())){
						personnelBase.setIdNumber(personnelBase.getIdNumber().trim());//??????????????????
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
					failureMsg.append("(???????????????:"+personnelBase.getIdNumber()+")"+" ???????????????"+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "????????? "+failureNum+" ???????????????????????????");
			}
			addMessage(redirectAttributes, "??????????????? "+successNum+" ???"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "??????????????????????????????"+e.getMessage());
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
					failureMsg.append("(???????????????:"+personnelBase.getIdNumber()+")"+" ???????????????"+ex.getMessage());
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
		return "redirect:" + adminPath + "/file/template/download/view";
	}

    /**
     * ???????????????????????????
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
     * ??????????????????????????????
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
			if ("?????????????????????".equals(personnelBase.getEditWorkUnitName()) || personnelBase.getEditWorkUnitName().contains("?????????")){
				companyIdAct = "1";
			}else if ("???????????????".equals(personnelBase.getEditWorkUnitName()) || personnelBase.getEditWorkUnitName().contains("?????????")){
				companyIdAct = "34";
			}else if ("???????????????".equals(personnelBase.getEditWorkUnitName()) || personnelBase.getEditWorkUnitName().contains("?????????")){
				companyIdAct = "95";
			}else if ("???????????????".equals(personnelBase.getEditWorkUnitName()) || personnelBase.getEditWorkUnitName().contains("?????????")){
				companyIdAct = "156";
			}
			User userInfo = userDao.getInfoByIdNumber(idNumber);
			userDao.updateOfficeInfo(userInfo.getId(), personnelBase.getEditWorkUnitId(), companyIdAct);
		}
        request.setAttribute("saveResult", "sucess");
        return "modules/personnel/personnelBaseUnitTree";
    }

	/**
	 * ????????????
	 * word???????????????xml???????????????
	 * appointment or dismissal of cadres
	 * @param personnelBase
	 * @param request
	 */
	@RequiresPermissions("personnel:personnelBase:edit")
	@RequestMapping(value = {"exportAppointRemoveCadre"})
	public void exportAppointRemoveCadre(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response) {

		//????????????
		String fileSeperator = File.separator;
		String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;
		String fileName=request.getParameter("fileName");
		//???????????????
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

		//??????
		try {
			/*https://blog.csdn.net/cs373616511/article/details/80325458*/
			OutputStream fout = response.getOutputStream();
			ZipOutputStream out = new ZipOutputStream(fout);
			// ??????buffer??????
			String zipName = personnelBase.getName()+"?????????????????????.zip";
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			//??????????????????
			response.setHeader("Content-Disposition", "attachment; filename="+
					Encodes.urlEncode(zipName));
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");


			/*doc*/
			ZipEntry docx = new ZipEntry( personnelBase.getName()+"?????????????????????.docx");
			out.putNextEntry(docx);
			String fileNameInResource = filePath + "?????????????????????.docx";
//			CustomXWPFDocument document = WordTemplate.changWord(fileNameInResource, personnelBase.getName() + "?????????????????????.docx", (Map<String, Object>) map.get("doc"), null);
//			document.write(out);
			//????????????????????????
//			OutputStreamWriter writer = new OutputStreamWriter(fout);
//			String fileNameInResource = filePath+"?????????????????????.docx";
			CustomXWPFDocument document = WordTemplate.changWord(fileNameInResource,personnelBase.getName()+"?????????????????????.docx",(Map<String, Object>) map.get("doc"),null);
			document.write(out);
			ZipEntry xlsx = new ZipEntry( personnelBase.getName()+"?????????????????????.xlsx");
			out.putNextEntry(xlsx);
			//????????????????????????
			excel.getWorkbook().write(out);
			/*???????????? ????????????????????????*/
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
	 * ????????????????????????
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
		//??????????????????
		response.setHeader("Content-Disposition", "attachment; filename="+
				Encodes.urlEncode("????????????.zip"));
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

				/*??????xlsx*/
				Map<String,Object> photo = (Map<String, Object>) map.get("excelPhoto");
				byte[] imageBytes = (byte[]) photo.get("imageBytes");
				ExcelTemplate excel = new ExcelTemplate(filePath + "?????????????????????.xlsx");
				excel.fillVariable(0, (Map<String, String>) map.get("sheet1"));
				excel.fillVariable(1, (Map<String, String>) map.get("sheet2"));
				int line = excel.addRowByExist(1, 5, 5, 6, (LinkedHashMap<Integer, LinkedList<String>>) map.get("rows"), true);
				if (photo.get("imageType") != null && imageBytes != null) {
					int imageType = (int) photo.get("imageType");
					excel.insertPicture(0,imageBytes, imageType,1,5,8,8);
				}
				ZipEntry xlsx = new ZipEntry(item.getName() + "?????????????????????.xlsx");
				finalOut.putNextEntry(xlsx);
				excel.getWorkbook().write(finalOut);

				/*??????doc*/
				ZipEntry docx = new ZipEntry(item.getName() + "?????????????????????.docx");
				finalOut.putNextEntry(docx);
				String fileNameInResource = filePath + "?????????????????????.docx";
				CustomXWPFDocument document = WordTemplate.changWord(fileNameInResource, personnelBase.getName() + "?????????????????????.docx", (Map<String, Object>) map.get("doc"), null);
				document.write(finalOut);


			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				/*???????????? ????????????????????????*/
				finalOut.flush();
				finalOut.close();
				response.getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ????????????
	 * @param personnelBase
	 * @param request
	 */
	@RequiresPermissions("personnel:personnelBase:edit")
	@RequestMapping(value = {"policeCard"})
	public void exportPoliceCard(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response) {

		//????????????
		String fileSeperator = File.separator;
		String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;

		String fileName=request.getParameter("fileName");
		//???????????????
		ExcelTemplate excel = new ExcelTemplate(filePath + fileName);

		try {
			Map<String, Object> map = getPersonnelBaseCard(personnelBase, request,response);

			Map<String,Object> photo = (Map<String, Object>) map.get("excelPhoto");
			//????????????
			excel.fillVariable(0, (Map<String, String>) map.get("param"));
			int i = excel.addRowByExist(0, 14, 14, 15, (LinkedHashMap<Integer, LinkedList<String>>) map.get("rows"), true);
			/*??????????????????*/
			byte[] imageBytes = (byte[]) photo.get("imageBytes");
			if (StringUtils.isNotBlank(personnelBase.getPhoto()) && imageBytes!=null && photo.size()>0){
				int imageType = (int) photo.get("imageType");
				excel.insertPicture(0,imageBytes, imageType,1,4,10,10);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//??????
		try {
			// ??????buffer??????
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			//??????????????????
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
	 * ????????????????????????
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
		//??????????????????
		response.setHeader("Content-Disposition", "attachment; filename=" +
				Encodes.urlEncode("????????????.zip"));
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
				/*??????xlsx*/
				ExcelTemplate excel = new ExcelTemplate(filePath + "????????????.xlsx");
				excel.fillVariable(0, (Map<String, String>) map.get("param"));
				int line = excel.addRowByExist(0,14,14,15, (LinkedHashMap<Integer, LinkedList<String>>) map.get("rows"), true);
				Map<String,Object> photo = (Map<String, Object>) map.get("excelPhoto");
				byte[] imageBytes = (byte[]) photo.get("imageBytes");
				if (photo.get("imageType") != null && imageBytes!=null){
				int imageType = (int) photo.get("imageType");
					excel.insertPicture(0,imageBytes, imageType,1,4,10,10);
				}
				ZipEntry xlsx = new ZipEntry(item.getName() + "????????????.xlsx");
				finalOut.putNextEntry(xlsx);
				excel.getWorkbook().write(finalOut);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				/*???????????? ????????????????????????*/
				finalOut.flush();
				finalOut.close();
				response.getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ?????????????????????????????????????????????
	 * @param personnelBase
	 * @param request
	 * @return
	 */
	public Map<String, Object> getPersonnelBaseCard(PersonnelBase personnelBase, HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();
		if (StringUtils.isBlank(personnelBase.getIdNumber())){
			personnelBase.setIdNumber("-1");
		}
		//??????????????????
		//????????????
		List<Map<String, String>> registerBase = personnelBaseService.findLastRegisterBase(personnelBase);
		//??????????????????????????? new??????map
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
		//???????????????????????????
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
		//????????????????????????
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
		//???????????????????????????
		List<Map<String, String>> qrzXueWeiInfo = personnelBaseService.findQrzXueWeiInfo(personnelBase);
		Map<String, String> fullTimeDegree;
		if (qrzXueWeiInfo != null && qrzXueWeiInfo.size() > 0) {
			fullTimeDegree = qrzXueWeiInfo.get(0);
		} else {
			fullTimeDegree = new HashMap<>();
		}
		//????????????????????????
		List<Map<String, String>> zzXueWeiInfo = personnelBaseService.findZzXueWeiInfo(personnelBase);
		Map<String, String> onJobDegree;
		if (zzXueWeiInfo != null && zzXueWeiInfo.size() > 0) {
			onJobDegree = zzXueWeiInfo.get(0);
		} else {
			onJobDegree = new HashMap<>();
		}

		//????????????
		PersonnelResume personnelResume = new PersonnelResume();
		personnelResume.setIdNumber(personnelBase.getIdNumber());
		Page<PersonnelResume> resumePage = new Page<PersonnelResume>(request,response,-1);
		resumePage.setOrderBy("start_date");
		resumePage = personnelResumeService.findPage(resumePage,personnelResume);
		List<PersonnelResume> lvLiInfo = resumePage.getList();

		//????????????
		String fileSeperator = File.separator;
		String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;

		String fileName = request.getParameter("fileName");
		//???????????????
		ExcelTemplate excel = new ExcelTemplate(filePath + fileName);

		Map<String, String> paramMap = new HashMap<>(16);
		/*????????????????????????*/
		/*???????????????????????????????????????????????????????????????*/
		personnelBase.setNativePlace(simplifyAddress(personnelBase.getNativePlace()));
		personnelBase.setBirthPlace(simplifyAddress(personnelBase.getBirthPlace()));

		//????????????
		personnelBase.setNation(DictUtils.getDictLabel(personnelBase.getNation(), "nation", ""));
		personnelBase.setSex(DictUtils.getDictLabel(personnelBase.getSex(), "sex", ""));
		personnelBase.setPoliticsFace(DictUtils.getDictLabel(personnelBase.getPoliticsFace(), "political_status", ""));
		/*??????????????????*/
		if (!personnelBase.getWorkunitName().contains("?????????") || !personnelBase.getWorkunitName().contains("????????????")) {
			String tempUnit = personnelBase.getWorkunitName().replaceAll("?????????","");
			personnelBase.setWorkunitName("?????????????????????" +tempUnit);
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

		//???????????????
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat s = new SimpleDateFormat("yyyy.MM");
		paramMap.put("birthday", personnelBase.getBirthday() == null ? "" : s.format(personnelBase.getBirthday()));
		int age = TimeUtils.getCurrentAge(personnelBase.getBirthday());
		paramMap.put("age", String.valueOf(age));
		/*????????????*/
		paramMap.put("publicSecurityDate", personnelBase.getPublicSecurityDate() == null ? "" : sdf.format(personnelBase.getPublicSecurityDate()));
		//????????????		???????????????
		String joinPartyTime = String.valueOf(personnelMap.get("organization_date"));
		paramMap.put("organizationDate", joinPartyTime);
		/*??????????????????*/
		paramMap.put("workDate", personnelBase.getWorkDate() == null ? "" : sdf.format(personnelBase.getWorkDate()));
		//?????????????????????
		String civilServiceTime = String.valueOf(personnelMap.get("register_date"));
		paramMap.put("civilServiceTime", civilServiceTime);
		//??????	job_name
		//????????????	work_date
		//??????	job_level
		//???????????????	start_date
		//????????????????????????	rzzg_name
		//??????????????????????????????	qdzg_date
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
		/*???????????????????????? ????????????????????????*/
		String policeTechnical = personnelMap.get("rzzg_name") + policeTechnicalLevel;
		String policeTechnicalTime = String.valueOf(personnelMap.get("qdzg_date"));

		//?????????????????????????????????map???
//		paramMap.put("jobAbbreviation", jobAbbreviation);
		paramMap.put("tenureTime", tenureTime);
		/*???????????????????????????*/
		if (personnelBase.getJobAbbreviation().equals(jobLevel)){

		}else {
			String jobName = personnelBase.getJobAbbreviation().replaceAll(jobLevel,"");
			jobName = jobName.replaceAll("???","");
			paramMap.put("jobAbbreviation",jobName);
		}
		paramMap.put("jobLevel", jobLevel);
		paramMap.put("jobLevelTime", jobLevelTime);
		paramMap.put("policeTechnical", policeTechnical);
		paramMap.put("policeTechnicalTime", policeTechnicalTime);
		//??????
		paramMap.put("policeRank", personnelMap.get("jx_name"));
		//????????????

		paramMap.put("rankTime", String.valueOf(personnelMap.get("jxqs_date")));

		/*??????????????????*/
		//???????????????
		paramMap.put("fullTimeEducation", fullTimeEducation.getName());
		paramMap.put("fullTimeSchool", fullTimeEducation.getSchoolName());
		paramMap.put("fullTimeAcademicDegree", fullTimeDegree.get("name"));
		String fullTimeGraduation = DateUtils.formatDate(fullTimeEducation.getEndDate(),"yyyy-MM-dd");
		paramMap.put("fullTimeGraduation", fullTimeGraduation);
		/*???????????????  ????????????????????????*/
		paramMap.put("fullTimeMajor", fullTimeEducation.getMajorCode());
		//????????????
		paramMap.put("onJobEducation", onJobEducation.getName());
		paramMap.put("onJobSchool", onJobEducation.getSchoolName());
		paramMap.put("onJobAcademicDegree", onJobDegree.get("name"));
		String onJobGraduation = DateUtils.formatDate(onJobEducation.getEndDate());
		paramMap.put("onJobGraduation", onJobGraduation);
		paramMap.put("onJobMajor", onJobEducation.getMajorCode());

		//????????????
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
		//????????????

		//????????????
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
			String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();      //????????????
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
		//????????????
		String fileSeperator = File.separator;
		String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;

		//???????????????
		ExcelTemplate excel = new ExcelTemplate(filePath + fileNames);

		//????????????map
		Map<String, String> map = new HashMap<>();
		//??????????????????????????????
		//java8??????????????????
//		DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");//??????simpleDateFormat
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateFormat=new SimpleDateFormat();
		//???????????????????????????????????????
		Calendar calendar = Calendar.getInstance();
		int nowYer = calendar.get(Calendar.YEAR);
		int lastYear = nowYer - 1;
		int previousYear = lastYear - 1;
//		dateFormat.format(simpleDateFormat.format(affairSevenKnowledge.getBirthday()));
		try {
			//????????????
			map.put("xuhao", "??????");
			map.put("danwei", "??????");
			map.put("bumen", "?????????????????????");
			map.put("xingming", "??????");
			map.put("xingbie", "??????");
			map.put("sfz", "??????????????????");
			map.put("chusheng", "????????????");
			map.put("minzu", "??????");
			map.put("mianmao", "????????????");
			map.put("dangtuan", "??????/?????????");
			map.put("jiguan", "??????");
			map.put("jinghao", "??????");
			map.put("gzshijian", "??????????????????");
			map.put("gashijian", "????????????????????????");
			map.put("gwyshijian", "?????????????????????");
			map.put("ryleibie", "?????????????????????");
			map.put("zhiwu", "??????");
			map.put("renzhi", "????????????");
			map.put("zhiji", "??????");
			map.put("zjshijian", "???????????????");
			map.put("zige", "????????????????????????");
			map.put("zgshijian", "??????????????????");
			map.put("qrzxueli", "???????????????");
			map.put("qrzbiye", "?????????????????????");
			map.put("qrzzhuanye", "???????????????");
			map.put("qrzxuexiao", "???????????????");
			map.put("zzxueli", "????????????");
			map.put("zzbiye", "??????????????????");
			map.put("zzzhuanye", "????????????");
			map.put("zzxuexiao", "????????????");
			map.put("qrzxuewei", "???????????????");
			map.put("zzxuewei", "????????????");
			map.put("jingxian", "??????");
			map.put("jxriqi", "??????????????????");
			map.put("jiankang", "????????????");
			map.put("chushengdi", "?????????");
			map.put("zhuanchang", "??????");
			map.put("gxmingcheng1", "???????????????????????????1");
			map.put("gxxingming1", "????????????1");
			map.put("gxriqi1", "??????????????????1");
			map.put("gxmianmao1", "??????????????????1");
			map.put("gxzhiwu1", "????????????????????????1");
			map.put("gxmingcheng2", "???????????????????????????2");
			map.put("gxxingming2", "????????????2");
			map.put("gxriqi2", "??????????????????2");
			map.put("gxmianmao2", "??????????????????2");
			map.put("gxzhiwu2", "????????????????????????2");
			map.put("gxmingcheng3", "???????????????????????????3");
			map.put("gxxingming3", "????????????3");
			map.put("gxriqi3", "??????????????????3");
			map.put("gxmianmao3", "??????????????????3");
			map.put("gxzhiwu3", "????????????????????????3");
			map.put("gxmingcheng4", "???????????????????????????4");
			map.put("gxxingming4", "????????????4");
			map.put("gxriqi4", "??????????????????4");
			map.put("gxmianmao4", "??????????????????4");
			map.put("gxzhiwu4", "????????????????????????4");
			map.put("gxmingcheng5", "???????????????????????????5");
			map.put("gxxingming5", "????????????5");
			map.put("gxriqi5", "??????????????????5");
			map.put("gxmianmao5", "??????????????????5");
			map.put("gxzhiwu5", "????????????????????????5");
			map.put("gxmingcheng6", "???????????????????????????6");
			map.put("gxxingming6", "????????????6");
			map.put("gxriqi6", "??????????????????6");
			map.put("gxmianmao6", "??????????????????6");
			map.put("gxzhiwu6", "????????????????????????6");
			map.put("gxmingcheng7", "???????????????????????????7");
			map.put("gxxingming7", "????????????7");
			map.put("gxriqi7", "??????????????????7");
			map.put("gxmianmao7", "??????????????????7");
			map.put("gxzhiwu7", "????????????????????????7");
			map.put("jianli", "??????");
			map.put("jigou", "??????");
			map.put("jiangli", "??????");
			map.put("chengjie", "??????");
			map.put("qiannian", previousYear + "?????????");
			map.put("qunian", lastYear + "?????????");
			map.put("jinian", nowYer + "?????????");
			//????????????????????????
			excel.fillVariable(0, map);
		} catch (IOException e) {
			e.printStackTrace();
		}

		LinkedHashMap<Integer, LinkedList<String>> rows = new LinkedHashMap<>();
		//????????????
		// ?????????????????????????????????????????????ExcelTemplate?????????????????????
		// ???????????????????????????????????????????????????${}??????????????????????????????????????????
		// ?????????????????????row1????????????rows
		List<PersonnelRoster> pList = new ArrayList<>();
		//????????????
		List<Map<String, String>> registerBase = new ArrayList<>();
		if ("1".equals(flag)){
			registerBase = personnelBaseService.findRegisterBase(new PersonnelBase());
		}else {
			registerBase = personnelBaseService.findNoRegisterBase(new PersonnelBase());
		}
		//???????????????????????????
		List<Map<String, String>> qrzXueLiInfo = personnelBaseService.findQrzXueLiInfo(new PersonnelBase());
		//????????????????????????
		List<Map<String, String>> zzXueLiInfo = personnelBaseService.findZzXueLiInfo(new PersonnelBase());
		//???????????????????????????
		List<Map<String, String>> qrzXueWeiInfo = personnelBaseService.findQrzXueWeiInfo(new PersonnelBase());
		//????????????????????????
		List<Map<String, String>> zzXueWeiInfo = personnelBaseService.findZzXueWeiInfo(new PersonnelBase());
		//??????????????????
		List<Map<String, String>> checkInfo = personnelBaseService.findCheckInfo(new PersonnelBase());
		//????????????
		List<Map<String, String>> lvLiInfo = personnelBaseService.findLvLiInfo(new PersonnelBase());
		//????????????
		List<Map<String, String>> jiangLiInfo = personnelBaseService.findJiangLiInfo(new PersonnelBase());
		//????????????
		List<Map<String, String>> chengJieInfo = personnelBaseService.findChengJieInfo(new PersonnelBase());
		//??????????????????
		List<Map<String, String>> familyInfo = personnelBaseService.findFamilyInfo(new PersonnelBase());
		int xuhao = 1;
		for (int i = 0; i < registerBase.size(); i++) {
			PersonnelRoster p = new PersonnelRoster();
			p.setXuhao(String.valueOf(i+1));
			//???????????????????????????????????????
			String workunitName = registerBase.get(i).get("workunit_name");
			String unitName = "";
			if (StringUtils.isNotEmpty(workunitName)){
				if (registerBase.get(i).get("workunit_name").contains("?????????") && !registerBase.get(i).get("workunit_name").contains("?????????????????????")){
					unitName = "?????????????????????"+workunitName;
				}else if (registerBase.get(i).get("workunit_name").contains("?????????")&& !registerBase.get(i).get("workunit_name").contains("???????????????")){
					unitName = "????????????????????????????????????"+workunitName;
				}else if (registerBase.get(i).get("workunit_name").contains("?????????")&&!registerBase.get(i).get("workunit_name").contains("???????????????")){
					unitName = "????????????????????????????????????"+workunitName;
				}else if (registerBase.get(i).get("workunit_name").contains("?????????")&& !registerBase.get(i).get("workunit_name").contains("???????????????")){
					unitName = "????????????????????????????????????"+workunitName;
				}else if (registerBase.get(i).get("workunit_name").contains("?????????????????????")){
					unitName = workunitName;
				}else if (registerBase.get(i).get("workunit_name").contains("???????????????") || registerBase.get(i).get("workunit_name").contains("???????????????") || registerBase.get(i).get("workunit_name").contains("???????????????")){
					unitName = "?????????????????????" + workunitName;
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
			//??????\????????????
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
			//?????????????????????
			p.setGwyshijian(String.valueOf(registerBase.get(i).get("register_date")));
			String personnelStatus = registerBase.get(i).get("status");
			p.setRyleibie(DictUtils.getDictLabel(personnelStatus,"personnel_status",""));
			//??????
			p.setZhiwu(registerBase.get(i).get("job_name"));
			//????????????
			p.setRenzhi(String.valueOf(registerBase.get(i).get("work_date")));
			//??????
			String jobLevel= registerBase.get(i).get("job_level");
			p.setZhiji(DictUtils.getDictLabel(jobLevel,"personnel_zwcc",""));
//			???????????????
			p.setZjshijian(String.valueOf(registerBase.get(i).get("start_date")));
			//????????????????????????
			p.setZige(registerBase.get(i).get("rzzg_name"));
//			??????????????????????????????
			p.setZgshijian(String.valueOf(registerBase.get(i).get("qdzg_date")));
			//??????
			p.setJingxian(registerBase.get(i).get("jx_name"));
			//????????????
			p.setJxriqi(String.valueOf(registerBase.get(i).get("jxqs_date")));
			p.setJiankang(registerBase.get(i).get("health_status"));
			p.setChushengdi(simplifyAddress(registerBase.get(i).get("birth_place")));
//			p.setChushengdi(registerBase.get(i).get("birth_place"));
			p.setZhuanchang(registerBase.get(i).get("expertise"));
			String id_number = registerBase.get(i).get("id_number");
			/*??????????????????*/
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
			/*????????????*/
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

		// ?????????????????????????????????sheet?????????
		// ??????????????????????????????????????????????????????????????????????????????????????????
		// ????????????????????????????????????????????????????????????????????????????????????????????????
		// ????????????????????????????????????????????????(?????????????????????)
		// ????????????????????????????????????${}??????
		// ???????????????????????????????????????????????????
		// ????????????????????????????????????????????????
		try {
			int i = excel.addRowByExist(0,1,1,2,rows,true);
			//??????????????????????????????????????????????????????????????????????????????
			//?????????????????????????????????
			if (i>0){
//				excel.mergedRegion(0,16,16+i+1,1,1);
			}
//			excel.addRowByExist(0,14,14,15,rows,true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ?????????????????????????????????sheet?????????
		// ????????????????????????sheet??????${<????????????>}??????

		//??????
		try {
			// ??????buffer??????
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			//??????????????????
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

		/*//??????
		return "redirect:" + adminPath + "/personnel/personnelBase/?repage";*/
		return "redirect:" + adminPath + "/personnel/personnelBase/?repage";
	}

	/**
	 * ??????????????????
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
		String siplifyAddress = "";  // ???????????????
		if (address != null && address != ""){
			siplifyAddress= address;
			String regex="((?<province>[^???]+???|[^?????????]+?????????))(?<city>[^???]+???|[^???]+???|.+?????????)(?<county>[^???]+???|.+???|.+???|.+???)?(?<town>[^???]+???|.+???)?(?<village>.*)";
			Matcher mt= Pattern.compile(regex).matcher(address);
			String province=null,city=null,county=null,town=null,village=null;

			String p = "";  // ??????????????????
			String tp = "";  // ?????????????????????????????????????????????????????????????????????
			String c = "";  // ???
			String cy = "";  // ?????????
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
				if (province.contains("?????????") && !county.contains("???") && county.contains("???")){
					p = province.substring(0,province.indexOf("?????????"));
					switch (p){
						case "?????????":
							tp = "?????????";
							break;
						case "????????????":
							tp = "??????";
							break;
						case "??????":
							tp = "??????";
							break;
						case "????????????":
							tp = "??????";
							break;
						case "???????????????":
							tp = "???????????????";
							break;
					}
					c = county.substring(0,county.indexOf("???"));
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
						cy = c + "???";
					}else {
						cy = c;
					}
					siplifyAddress = tp + cy;
				}else if (province.contains("???") && !county.contains("???") && county.contains("???")){
					p = province.substring(0,province.indexOf("???"));
					c = county.substring(0,county.indexOf("???"));
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
						cy = c + "???";
					}else {
						cy = c;
					}
					siplifyAddress = p + cy;
				}else if (province.contains("?????????") && county.contains("???") && city.contains("???")){
					p = province.substring(0,province.indexOf("?????????"));
					switch (p){
						case "?????????":
							tp = "?????????";
							break;
						case "????????????":
							tp = "??????";
							break;
						case "??????":
							tp = "??????";
							break;
						case "????????????":
							tp = "??????";
							break;
						case "???????????????":
							tp = "???????????????";
							break;
					}
					c = city.substring(0,city.indexOf("???"));
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
						cy = c + "???";
					}else {
						cy = c;
					}
					siplifyAddress = tp + cy;
				}else if (province.contains("???") && county.contains("???") && city.contains("???")){
					p = province.substring(0,province.indexOf("???"));
					c = city.substring(0,city.indexOf("???"));
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
						cy = c + "???";
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
	 * ?????????????????????????????????????????????
	 * @param personnelBase
	 * @param request
	 * @return
	 */
	public Map<String,Object> getPersonnelBaseInfo(PersonnelBase personnelBase, HttpServletRequest request,HttpServletResponse response){
		if (StringUtils.isBlank(personnelBase.getIdNumber())){
			personnelBase.setIdNumber("-1");
		}
		/*????????????*/
		Map<String,Object> resultMap = new HashMap<>();
		/*excel??????sheet1????????????*/
		Map<String, String> paramMap = new HashMap<>(16);
		/*excel??????sheet2????????????*/
		Map<String,String> map=new HashMap();
		//excel????????????
		LinkedHashMap<Integer, LinkedList<String>> rows = new LinkedHashMap<>();

		/*?????????doc*/
		Map<String,Object> objectMap = new HashMap<>();

		//??????
		//???????????????????????????
		/*??????????????????  ????????????????????????*/
//		List<Map<String, String>> qrzXueLiInfo = personnelBaseService.findQrzXueLiInfo(personnelBase);
		PersonnelXueli fullTimeEducation = new PersonnelXueli();
		fullTimeEducation.setType1("1");
		fullTimeEducation.setIdNumber(personnelBase.getIdNumber());
		fullTimeEducation = personnelXueliService.getLastByIdNumber(fullTimeEducation);
		//????????????????????????

		PersonnelXueli onJobEducation = new PersonnelXueli();
		onJobEducation.setType1("2");
		onJobEducation.setIdNumber(personnelBase.getIdNumber());
		onJobEducation = personnelXueliService.getLastByIdNumber(onJobEducation);
//		List<Map<String, String>> zzXueLiInfo = personnelBaseService.findZzXueLiInfo(personnelBase);
		//???????????????????????????
		List<Map<String, String>> qrzXueWeiInfo = personnelBaseService.findQrzXueWeiInfo(personnelBase);
		//????????????????????????
		List<Map<String, String>> zzXueWeiInfo = personnelBaseService.findZzXueWeiInfo(personnelBase);

		//????????????
		PersonnelResume personnelResume = new PersonnelResume();
		personnelResume.setIdNumber(personnelBase.getIdNumber());
		Page<PersonnelResume> lvLiPage = new Page<>(request,response,-1);
		lvLiPage.setOrderBy("start_date");
		lvLiPage = personnelResumeService.findPage(lvLiPage,personnelResume);
		List<PersonnelResume> lvLiInfo = lvLiPage.getList();

		//????????????  ????????????
		List<Map<String, String>> registerBase = personnelBaseService.findLastRegisterBase(personnelBase);
		Map<String,String> registerMap = new HashMap<>();
		if (registerBase!=null && registerBase.size()>0){
			registerMap = registerBase.get(0);
		}
		PersonnelPoliceWork1 personnelPoliceWork1 = new PersonnelPoliceWork1();
		personnelPoliceWork1.setIdNumber(personnelBase.getIdNumber());
		//??????????????????
		List<PersonnelPoliceWork1> policeWork1List = personnelPoliceWork1Service.findList(personnelPoliceWork1);

		try {
			/*????????????????????????*/
			/*???????????????????????????????????????????????????????????????*/
			personnelBase.setNativePlace(simplifyAddress(personnelBase.getNativePlace()));
			personnelBase.setBirthPlace(simplifyAddress(personnelBase.getBirthPlace()));
			//????????????
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


			//???????????????	??????????????????
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
			String birthday = personnelBase.getBirthday()==null?"":sdf.format(personnelBase.getBirthday());
			paramMap.put("birthday",birthday);
			int age = TimeUtils.getCurrentAge(personnelBase.getBirthday());
			paramMap.put("age", String.valueOf(age));
			//????????????		???????????????
			String joinPartyTime = String.valueOf(registerMap.get("organization_date"));


			paramMap.put("joinPartyTime",DateUtils.parseDate(joinPartyTime)==null?"":sdf.format(DateUtils.parseDate(joinPartyTime)));
			joinPartyTime = DateUtils.formatDate(DateUtils.parseDate(joinPartyTime),"yyyy.MM");
			paramMap.put("joinPartyTime",joinPartyTime+" ");
			//????????????
			paramMap.put("workDate",personnelBase.getWorkDate()==null?"":sdf.format(personnelBase.getWorkDate())+" ");
			//????????????
			//?????????
			/*????????????*/
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
			//???????????????
			paramMap.put("fullTimeEducation", fullTimeEducation.getName()+"\n"+fullTimeDegree);
			//?????????????????????
			String fullTimeSchool = fullTimeEducation.getSchoolName()+ "\n" + fullTimeEducation.getMajorCode();
			paramMap.put("fullTimeSchool", StringUtils.replace(fullTimeSchool,"null",""));
			//??????
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
			//???????????????
			paramMap.put("onJobEducation", onJobEducation.getName()+"\n"+onJobDegree);
			//?????????????????????
			String onJobSchool =onJobEducation.getSchoolName()+ "\n" + onJobEducation.getMajorCode();
			paramMap.put("onJobSchool", StringUtils.replace(onJobSchool,"null",""));

			//????????????
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

			//??????????????????
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

			//????????????
			List<Map<String, String>> jiangLiInfo = personnelBaseService.findJiangLiInfo(personnelBase);
			//????????????
			List<Map<String, String>> chengJieInfo = personnelBaseService.findChengJieInfo(personnelBase);

			/*??????*/
			/*?????????????????????
			????????????????????????????????????????????????????????????
			?????????  ??????????????????????????????*/
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
					logger.debug("??????????????????  ???????????? ??????????????????");
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
					logger.debug("??????????????????  ???????????? ??????????????????");
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
			/*??????????????????null??? ?????????????????????objectMap,????????????doc??????*/
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
			/*????????????*/
			rows.forEach((key, value) -> {
				for (int cloumn = 1;cloumn<=value.size();cloumn++){
					objectMap.put("f"+key+cloumn,value.get(cloumn-1));
				}
			});
			/*???????????????????????????*/
			for (int j =rows.size()+1;j<=7;j++){
				for (int s=1;s<=4;s++){
					objectMap.put("f"+j+s,"");
				}
			}

			/*word????????????*/
			String photo = personnelBase.getPhoto();
			Map<String, Object> excelPhotoMap = new HashMap<>();
			if (StringUtils.isNotBlank(photo)) {
				String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();      //????????????
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
			objectMap.put("age","("+objectMap.get("age")+"???)");
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
			session.removeAttribute("customSql");//???????????????????????????????????????????????????session??????customSql??????
		}
		if(session.getAttribute("columnTextList")!=null){
			session.removeAttribute("columnTextList");//???????????????????????????????????????????????????session??????columnTextList??????
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
	 * ????????????????????????????????? ?????????????????????
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
	 * ??????????????????????????????
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
	 * ??????????????????????????????
	 * @param  submitTableName ?????????????????????
	 * @param  submitCheckColumntext ????????????????????????
	 * @param  submitCheckColumnvalue ?????????????????????
	 * @param  mapListStr where??????json?????????
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
			/*???????????????serializeNulls,????????????????????????NULL*/
			builder.serializeNulls();   // 1
			/*????????????json????????????????????????????????????????????????????????????????????????*/
			builder.setPrettyPrinting();
			Gson gson = builder.create();
			// ????????????
			String tempStr = StringEscapeUtils.unescapeHtml4(mapListStr);//???????????????&quot;????????????"
			//String tempStr = mapListStr.replace("&quot;","");//???json???????????????&quot;????????? ???
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
			result.setMessage("??????????????????????????????????????????????????????"+DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.toString());
			result.setSuccess(false);
			result.setMessage("????????????,???????????????????????????????????????????????????????????????????????????"+DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		}
		return result;
	}


	/*
	 * ????????????????????????
	 * */
	@RequiresPermissions("personnel:personnelBase:view")
	@RequestMapping(value = "batchSelForm")
	public String batchSelForm(PersonnelBase personnelBase, Model model,HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("customBatchSql")!=null){
			session.removeAttribute("customBatchSql");//???????????????????????????????????????session?????????sql????????????
		}
		List<Dict> personnelStatusList = DictUtils.getDictList("personnel_status");
		model.addAttribute("personnelStatusList",personnelStatusList);
		model.addAttribute("personnelBase",personnelBase);
		return "modules/personnel/personnelBatchSelForm";
	}

	/**
	 * ????????????????????????
	 * @param  file excel??????
	 * @param  selItems ?????????
	 * @param  sheetPage ??????sheet
	 * @param  startLine ???????????????
	 * @param  excelColumn ?????????
	 * @param  unitId ????????????
	 * @param  status ????????????
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
			result.setMessage("???????????????????????????????????????????????????????????????");
		}
		return result;
	}

	/**
	 * ??????????????????????????????
	 * @param  tableSel ??????
	 * @param  columnSel ??????
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
			result.setMessage("?????????????????????????????????,"+e.getMessage());
		}
		return result;
	}


	/**
	 * ????????????????????????excel????????????
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
			String fileName = "???????????????????????????.xlsx";
			Page<Map<String, Object>> complexSelPage = null;
			if(flag == true){
				//?????????
				complexSelPage = personnelComplexSelService.execCustomSql(new Page<Map<String, Object>>(request,response),customSql,columnTextList,columnList);
			}else{
				//??????
				complexSelPage = personnelComplexSelService.execCustomSqlAllResult(new Page<Map<String, Object>>(request,response),customSql,columnTextList,columnList);
			}
			List<Map<String, Object>> mapList = complexSelPage.getList();
			//??????XSSFSheet??????
			XSSFWorkbook wb = new XSSFWorkbook();
			//??????XSSFSheet??????
			XSSFSheet sheet = wb.createSheet("sheet1");
			//????????????
			XSSFRow titleRow = sheet.createRow(0);
			for(int i = 0;i<columnTextList.size();i++){
				//??????XSSFCell??????
				XSSFCell cell=titleRow.createCell(i);
				//?????????????????????
				cell.setCellValue(columnTextList.get(i));
				XSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setAlignment(HorizontalAlignment.CENTER); // ???????????????????????????
				XSSFFont font = wb.createFont();
				font.setBold(true);
				cellStyle.setFont(font);//??????????????????
				cell.setCellStyle(cellStyle);
			}
			//??????????????????
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
						//??????????????????????????????15000
						if (length > 15000) {
							length = 15000;
						}
						sheet.setColumnWidth(j,length);
					}
				}
			}
			// ??????buffer??????
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			//??????????????????
			response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		} catch (Exception ex){
			ex.printStackTrace();
			addMessage(redirectAttributes, "????????????????????????????????????????????????"+ex);
		}

		return "redirect:" + adminPath + "/personnel/personnelBase/?isComplexSelForm=isComplexSelForm";
	}

	/**
	 * ????????????????????????excel????????????
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
			//???????????????????????????????????????
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
			addMessage(redirectAttributes, "????????????????????????????????????????????????" + ex);
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