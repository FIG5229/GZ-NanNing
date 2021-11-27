/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPoliceRankDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelJob;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceCertificate;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceRank;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelJobService;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelPoliceRankService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
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
import java.util.stream.Collectors;

/**
 * 警衔信息管理Controller
 * @author cecil.li
 * @version 2019-11-09
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelPoliceRank")
public class PersonnelPoliceRankController extends BaseController {

	@Autowired
	private PersonnelPoliceRankService personnelPoliceRankService;

	@Autowired
	private PersonnelPoliceRankDao personnelPoliceRankDao;

	@Autowired
	private PersonnelBaseService personnelBaseService;

	@Autowired
	private PersonnelJobService personnelJobService;

	@ModelAttribute
	public PersonnelPoliceRank get(@RequestParam(required=false) String id) {
		PersonnelPoliceRank entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelPoliceRankService.get(id);
		}
		if (entity == null){
			entity = new PersonnelPoliceRank();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelPoliceRank:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelPoliceRank personnelPoliceRank, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelPoliceRank.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelPoliceRank> page = personnelPoliceRankService.findPage(new Page<PersonnelPoliceRank>(request, response), personnelPoliceRank);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/personnel/personnelPoliceRankList";
	}

	/**
	 * 警衔测算优化
	 * @param personnelPoliceRank
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("personnel:personnelPoliceRank:view")
	@RequestMapping(value = {"calculateBeta"})
	public String calculateBeta(PersonnelPoliceRank personnelPoliceRank, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("calculateType",personnelPoliceRank.getCalculateType());
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelPoliceRank.setIdNumber(request.getParameter("idNumber").toString());
		}
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			String mType = request.getParameter("mType").toString();
			request.setAttribute("mType",mType);
		}
//		//预测类别为空时  不进行测算，测算时响应很慢
//		if (StringUtils.isBlank(personnelPoliceRank.getCalculateType())){
//			return "modules/personnel/personnelPoliceRankCalculationListBeta";
////			personnelPoliceRank.setCalculateType("1");
//		}

		/*展示两表数据 无法用词分页，查询全部数据*/
		String no = request.getParameter("pageNo");
		String size = request.getParameter("pageSize");
		String removeCache = request.getParameter("removeCache");
		Page<PersonnelPoliceRank> page = new Page<>(request,response);
		if (StringUtils.isNotBlank(no) && StringUtils.isNotBlank(size) && StringUtils.isBlank(removeCache) ){
			if (size.equals("-1")){
				size = "10";
			}
			int pageNo = page.getPageNo();
			int pageSize = page.getPageSize();
			List<PersonnelPoliceRank> result = (List<PersonnelPoliceRank>) CacheUtils.get("policeRankList");
			int count = result.size();
				List<PersonnelPoliceRank> pageList;
				if (pageSize * pageNo < count) {
					pageList = result.subList((pageNo - 1) * pageSize, pageSize * pageNo);
				} else {
					pageList = result.subList((pageNo - 1) * pageSize, count);
				}
				page.setCount(result.size());
				page.setList(pageList);

			model.addAttribute("page", page);
		}else {
			CacheUtils.remove("policeRankList");
					Page<PersonnelPoliceRank> i= personnelPoliceRankService.findLastPage(new Page<PersonnelPoliceRank>(request, response,-1), personnelPoliceRank);
//			page =
//			page.setPageNo(1);
			page.setPageSize(10);

				List<PersonnelPoliceRank> list = new ArrayList<>();
				list  = i.getList();
			page.setCount(list.size());
			if (i.getList().size() >= 10) {
				page.setList(list.subList(0,10));
			} else {
				page.setList(i.getList());
			}
			model.addAttribute("page", page);
		}
		return "modules/personnel/personnelPoliceRankCalculationListBeta";
	}

	/*切换为beta版本  不在使用此方法*/
	@RequiresPermissions("personnel:personnelPoliceRank:view")
	@RequestMapping(value = {"calculate"})
	public String calculate(PersonnelPoliceRank personnelPoliceRank, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelPoliceRank.setIdNumber(request.getParameter("idNumber").toString());
		}
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			String mType = request.getParameter("mType").toString();
			request.setAttribute("mType",mType);
		}
		//预测类别为空时  不进行测算，测算时响应很慢
		if (StringUtils.isBlank(personnelPoliceRank.getCalculateType())){
			return "modules/personnel/personnelPoliceRankCalculationList";
//			personnelPoliceRank.setCalculateType("1");
		}
		/*展示两表数据 无法用词分页，查询全部数据*/
		Page<PersonnelPoliceRank> page = personnelPoliceRankService.findPage(new Page<PersonnelPoliceRank>(request, response,-1), personnelPoliceRank);

		List<PersonnelBase> policeRankList = new ArrayList<>();
		List<Map<String,Object>> list = new ArrayList<>();
		page.getList().forEach(personnelPoliceRank1 -> {
			if (StringUtils.isNotBlank(personnelPoliceRank1.getIdNumber()))
			policeRankList.add(calculateRank(personnelPoliceRank1,personnelPoliceRank.getCalculateType()));
		});
		policeRankList.removeAll(Collections.singleton(null));
		policeRankList.stream().forEach(personnelBase -> {
			Map<String,Object> objectMap = new HashMap<>();
			objectMap.put("base",personnelBase);
			PersonnelPoliceRank rank = new PersonnelPoliceRank(personnelBase.getIdNumber());
			rank.setIdNumber(personnelBase.getIdNumber());
			rank.setCalculateType(personnelPoliceRank.getCalculateType());
			List<PersonnelPoliceRank> rankList = personnelPoliceRankService.findList(rank);
			objectMap.put("rank",rank);
			/*不为空 则覆盖上班的rank*/
			if (rankList!=null && rankList.size()>0){
				objectMap.put("rank",rankList.get(0));
			}
			PersonnelJob personnelJob = new PersonnelJob();
			personnelJob.setIdNumber(personnelBase.getIdNumber());
			List<PersonnelJob> jobList = personnelJobService.findList(personnelJob);
			objectMap.put("job",personnelJob);
			if (jobList!=null && jobList.size()>0){
				objectMap.put("job",jobList.get(0));
			}
			list.add(objectMap);
		});
		//去空
//		page.setList(policeRankList);
		Page<PersonnelBase> personnelBasePage  = new Page<PersonnelBase>(request,response);
		personnelBasePage.setList(policeRankList);
		model.addAttribute("page", personnelBasePage);
		model.addAttribute("list", list);
		return "modules/personnel/personnelPoliceRankCalculationList";
	}

	/**
	 * 类型			条件
	 * 			首评警督	从警司晋升为警督
	 * 							副科	任现职满2年，工龄满16年；或工龄满20年的。
	 * 							正科	任现职满2年，工龄满10年的；或工龄满14年的
	 * 			首评警司	从警员晋升为警司
	 * 							办事员及警员级	工龄满8年的。
	 * 			选升警监	根据选升条件（未包含首评、晋升）
	 * 							三级警监
	 * 								领导	一督满3年,正处满2年,工龄满25年的；或一督满6年的。
	 * 								非领导	一督满5年，正处满3年，工龄满35年的；或一督满7年的。
	 * 							二级警监
	 * 								领导	三监满3年,副厅满2年,工龄满25年的；或三监满6年的。
	 * 								非领导	三监满5年，副厅满3年，工龄满35年的；或三监满6年的。
	 * 							一级警监
	 * 								领导	二监满2年,正厅满4年,工龄满25年的；或二监满6年的。
	 * 								非领导	二监满4年，正厅满3年，工龄满35年的；或二监满6年的。
	 *
	 *
	 * 			晋升警督	三级或二级晋升上一级
	 * 							三级晋升为二级
	 * 								副科	任现职满2年，工龄满26年；或工龄满30年的。
	 * 								正科	县(市)公安局和地级市下设的公安分局的局长、政委达不到一督的。其他任现职满2年,工龄满20年的；工龄满24年的。
	 * 								副处	任现职满3年,工龄满16年；或工龄满20年的。
	 * 							二级晋升为一级
	 * 								正科	县(市)公安局和地级市下设的公安分局的局长、政委任职满3年,工龄满22年的；或工龄满26年的。其他任现职满5年,工龄满32年的；工龄满38年的。
	 * 								副处	任现职满3年,工龄满22年；或工龄满26年的。
	 * 								正处	任现职满3年，工龄满18年的；或工龄满22年的。
	 * 			晋升警司	三级或二级晋升上一级
	 * 							三级晋升为二级
	 * 								办事员及警员级	工龄满12年的。
	 * 								科员及警长		工龄满8年的；现任县(市)公安局股所、队长不够一司的
	 * 							二级晋升为一级
	 * 								科员及警长		工龄满18年的；现任县(市)公安局股所、队长工龄满14年的。
	 * 								副科			任现职满2年，工龄满8年的；或工龄满12年的。
	 * 			晋督培训
	 * 			晋司培训
	 * @param personnelPoliceRank1
	 */
	private PersonnelBase calculateRank(PersonnelPoliceRank personnelPoliceRank1,String calculateType) {

		//根据身份证号查询相关信息 判断是否满足条件
		PersonnelBase personnelBase = personnelBaseService.findInfoByIdNumber(personnelPoliceRank1.getIdNumber());
		if (personnelBase==null){
			return null;
		}
		if (StringUtils.isBlank(personnelBase.getStatus())){
			return null;
		}
	/*	PersonnelJob personnelJob = new PersonnelJob();
		personnelJob.setIdNumber(personnelPoliceRank1.getIdNumber());
		List<PersonnelJob> personnelJobList = personnelJobService.findList(personnelJob);
		PersonnelJob job = null;
		if (personnelJobList!=null && personnelJobList.size()>0){
			job=personnelJobList.get(0);
		}else {
			job = new PersonnelJob();
		}*/
//		String jobLevel=job.getJobLevel()==null?"":job.getJobLevel();
		String jobLevel=personnelPoliceRank1.getJobLevel();
		String policeRank = personnelPoliceRank1.getPoliceRankLevel();
		if (StringUtils.isBlank(policeRank)){
			return null;
		}
		//在职级年限
//		int appoinment=TimeUtils.getCurrentAge(job.getStartDate());
		int appoinment=TimeUtils.getCurrentAge(personnelPoliceRank1.getStartDate());
		//工龄
		int workingYears= TimeUtils.getCurrentAge(personnelBase.getPublicSecurityDate());
		switch (calculateType){
			case "1":
//				首评警督
				if ("6".equals(jobLevel)){
				//副科级
					if (appoinment >= 2 && workingYears >= 16 || workingYears >= 20) {
						return personnelBase;
					}
				}
				if ("7".equals(jobLevel)){
					//正科级
					if (appoinment >= 2 && workingYears >= 10 || workingYears >= 14) {
						return personnelBase;
					}
				}
				break;
			case "2":
//				首评警司
				if ("2".equals(jobLevel) || "3".equals(jobLevel)){
					//办事员及警员
					if (workingYears >= 8){
						return  personnelBase;
					}
				}
				break;
			case "3":
//				选升警监
				boolean isOne = policeRank.equals("8");
				boolean isTwo= policeRank.equals("7");
				boolean isThree = policeRank.equals("6");
				//正处
				boolean isZhengChu = "9".equals(jobLevel);
				boolean isFuTing = "10".equals(jobLevel);
				boolean isZhengTing = "11".equals(jobLevel);
				int rankTime = TimeUtils.getCurrentAge(personnelPoliceRank1.getStartDate());

				if (isThree){
					//选升三级警监
					if ("2".equals(personnelBase.getStatus())) {
						//领导	一督满3年,正处满2年,工龄满25年的；或一督满6年的。
						if (isZhengChu && isOne && rankTime > 3 && appoinment> 2 && workingYears > 25 || rankTime > 6) {
							return personnelBase;
						}
					}
					if ("3".equals(personnelBase.getStatus())){
						//非领导	一督满5年，正处满3年，工龄满35年的；或一督满7年的。
						if (isZhengChu && isOne && rankTime > 5 && appoinment> 3 && workingYears > 35 || rankTime > 7) {
							return personnelBase;
						}
					}
				}
				if (isTwo){
					//选升二级警监
					if ("2".equals(personnelBase.getStatus())) {
						//领导	一督满3年,正处满2年,工龄满25年的；或一督满6年的。
						if (isFuTing && isOne && rankTime > 3 && appoinment> 2 && workingYears > 25 || rankTime > 6) {
							return personnelBase;
						}
					}
					if ("3".equals(personnelBase.getStatus())){
						//非领导	一督满5年，正处满3年，工龄满35年的；或一督满7年的。
						if (isFuTing && isOne && rankTime > 5 && appoinment> 3 && workingYears > 35 || rankTime > 6) {
							return personnelBase;
						}
					}
				}
				if (isOne){
					//选升一级警监
					if ("2".equals(personnelBase.getStatus())) {
						//领导	一督满3年,正处满2年,工龄满25年的；或一督满6年的。
						if (isZhengTing && isOne && rankTime > 2 && appoinment> 4 && workingYears > 25 || rankTime > 6) {
							return personnelBase;
						}
					}
					if ("3".equals(personnelBase.getStatus())) {
						//非领导	一督满5年，正处满3年，工龄满35年的；或一督满7年的。
						if (isZhengTing && isOne && rankTime > 4 && appoinment> 3 && workingYears > 35 || rankTime > 6) {
							return personnelBase;
						}
					}
				}
				break;
			case "4":
//				晋升警督
				if (policeRank.equals("6")){
					//三级升为二级
					if ("6".equals(jobLevel)){
						//副科
						if (appoinment>2&& workingYears>26 || workingYears>30){
							return personnelBase;
						}
					}
					if ("7".equals(jobLevel)){
						//正科
						//// FIXME: 2020/7/22 县(市)公安局和地级市下设的公安分局的局长、政委达不到一督的(为添加)

						if (appoinment>2&& workingYears>20 || workingYears>24){
							return personnelBase;
						}
					}
					if ("8".equals(jobLevel)){
						//副处
						if (appoinment>3&& workingYears>16 || workingYears>20){
							return personnelBase;
						}
					}

				}
				if (policeRank.equals("7")){
					//二级晋升为一级
					if ("7".equals(jobLevel)){
						//正科
						if (appoinment>5&& workingYears>32 || workingYears>38){
							return personnelBase;
						}
					}
					if ("8".equals(jobLevel)){
						//副处
						if (appoinment>3&& workingYears>22 || workingYears>26){
							return personnelBase;
						}
					}
					if ("9".equals(jobLevel)){
						//正处
						if (appoinment>3&& workingYears>18 || workingYears>22){
							return personnelBase;
						}
					}
				}
				break;
			case "5":
//				晋升警司
				//三级晋升为二级
				if (policeRank.equals("3")){
						//办事员及警员
					if (jobLevel.equals("2") || jobLevel.equals("3")){
						if (workingYears>12){
							return personnelBase;
						}
					}
					if (jobLevel.equals("4") || jobLevel.equals("5")){
						//科员及警长
						if (workingYears>12){
							return personnelBase;
						}
						//现任县(市)公安局股所、队长不够一司的(此条件未筛选)
					}
				}
				//二级晋升为一级
				if (policeRank.equals("2")){
					if (jobLevel.equals("4") || jobLevel.equals("5")){
						//科员及警长
						if (workingYears>18){
							return personnelBase;
						}
						//现任县(市)公安局股所、队长工龄满14年的(此条件未筛选)
					}
					if (jobLevel.equals("6")){
						//副科
						if (appoinment >2 && workingYears>8 || workingYears>12){
							return personnelBase;
						}
					}
				}
				break;
			case "6":
//				晋督培训
				break;
			case "7":
//				晋司培训
				break;

		}
		return null;
	}


	@RequiresPermissions("personnel:personnelPoliceRank:view")
	@RequestMapping(value = {"analysis"})
	public String analysis(PersonnelPoliceRank personnelPoliceRank, HttpServletRequest request, HttpServletResponse response, Model model) {
//		personnelPoliceRank.setYears((DateUtils.formatDate(personnelPoliceRank.getYear(),"yyyy")));
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelPoliceRank.setIdNumber(request.getParameter("idNumber").toString());
		}
		if (StringUtils.isEmpty(personnelPoliceRank.getAnalysisType())){
			personnelPoliceRank.setAnalysisType("1");
		}
		List<Dict> dicts = DictUtils.getDictList("dict_change_reason");
		switch (personnelPoliceRank.getAnalysisType()){
			/*变动情况*/
			case "1":

				/*行政警衔*/
				personnelPoliceRank.setType("1");
				List<Map<String, String>> administrationChangeList = personnelPoliceRankService.countRankChange(personnelPoliceRank);
				/*技术警衔*/
				personnelPoliceRank.setType("2");
				List<Map<String, String>> technologyChangeList = personnelPoliceRankService.countRankChange(personnelPoliceRank);
				model.addAttribute("administrationChangeList",administrationChangeList);
				model.addAttribute("technologyChangeList",technologyChangeList);
				break;
			/*警衔年统报表*/
			case "2":

				List<Map<String,String>> result = new ArrayList<>();
				dicts.stream().forEach(dict -> {
					String changeReason = dict.getValue();
					personnelPoliceRank.setChangeReason(changeReason);
					List<Map<String,String>> map = personnelPoliceRankService.countYearReport(personnelPoliceRank);
					if (map==null){
						map = new ArrayList<>();
					}
					result.addAll(map);
				});
				model.addAttribute("list",result);
				break;
			/*报衔报告*/
			case "3":
				break;
			/*报衔名册*/
			case "4":
				break;
			/*晋升警衔人员名单*/
			case "5":
				List<Map<String, String>> list = personnelPoliceRankService.findPromotionList(personnelPoliceRank);
				model.addAttribute("list",list);
				break;
			default:

				break;
		}
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("analysisType", personnelPoliceRank.getAnalysisType());
		return "modules/personnel/personnelPoliceRankStatisticsList";
	}

	@RequiresPermissions("personnel:personnelPoliceRank:view")
	@RequestMapping(value = "form")
	public String form(PersonnelPoliceRank personnelPoliceRank, Model model, HttpServletRequest request) {
		model.addAttribute("personnelPoliceRank", personnelPoliceRank);
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelPoliceRank.setIdNumber(request.getParameter("idNumber").toString());
		}
		return "modules/personnel/personnelPoliceRankForm";
	}

	@RequiresPermissions("personnel:personnelPoliceRank:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelPoliceRank personnelPoliceRank, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelPoliceRank)){
			return form(personnelPoliceRank, model,request);
		}
		personnelPoliceRankService.save(personnelPoliceRank);
		addMessage(redirectAttributes, "保存警衔信息成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelPoliceRankForm";
	}
	
	@RequiresPermissions("personnel:personnelPoliceRank:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelPoliceRank personnelPoliceRank, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		personnelPoliceRankService.delete(personnelPoliceRank);
		String url ="redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceRank/?repage&idNumber="+personnelPoliceRank.getIdNumber();

		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url = "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceRank/?repage&mType="+request.getParameter("mType").toString();

		}
		addMessage(redirectAttributes, "删除警衔信息成功");
		return url;
	}
	@ResponseBody
	@RequiresPermissions("personnel:personnelPoliceRank:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelPoliceRankService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("personnel:personnelPoliceRank:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelPoliceRank personnelPoliceRank, Model model) {
		model.addAttribute("personnelPoliceRank", personnelPoliceRank);
		return "modules/personnel/personnelPoliceRankFormDetail";
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
	public String exportExcelByTemplate(PersonnelPoliceRank personnelPoliceRank, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<PersonnelPoliceRank> page = null;
			if(flag == true){
				page = personnelPoliceRankService.findPage(new Page<PersonnelPoliceRank>(request, response), personnelPoliceRank);
			}else{
				page = personnelPoliceRankService.findPage(new Page<PersonnelPoliceRank>(request, response,-1), personnelPoliceRank);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelPoliceRank.class);
			exportExcelNew.setWb(wb);
			List<PersonnelPoliceRank> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelPoliceRank";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(HttpServletRequest request,MultipartFile file, RedirectAttributes redirectAttributes) {
		String isCover = request.getParameter("isCover");
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<PersonnelPoliceRank> list = ei.getDataList(PersonnelPoliceRank.class);
			//选择覆盖模式，须先将改身份证下相关履历信息删除
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(personnelResume -> {
					if(personnelResume.getIdNumber()!=null){
						idNumbers.add(personnelResume.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null){
					personnelPoliceRankService.deleteByIdNumbers(idNumbers);
				}
			}
			for (PersonnelPoliceRank personnelPoliceRank : list){
				try{
					BeanValidators.validateWithException(validator, personnelPoliceRank);
					//根据身份证查询人员基本信息，设置姓名,单位，单位id，性别，出生时间，参加工作时间
					if(personnelPoliceRank.getIdNumber()!=null && !personnelPoliceRank.getIdNumber().isEmpty()){
						PersonnelBase personnelBase = personnelBaseService.findInfoByIdNumber(personnelPoliceRank.getIdNumber());
						if(personnelBase!=null){
							personnelPoliceRank.setPeopleName(personnelBase.getName());
							personnelPoliceRank.setUnit(personnelBase.getWorkunitName());
							personnelPoliceRank.setUnitId(personnelBase.getWorkunitId());
							personnelPoliceRank.setSex(personnelBase.getSex());
							personnelPoliceRank.setBirthdayTime(personnelBase.getBirthday());
							personnelPoliceRank.setWorkTime(personnelBase.getWorkDate());
							personnelPoliceRank.setPersonName(personnelBase.getName());
						}
					}
					personnelPoliceRankService.save(personnelPoliceRank);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(personnelPoliceRank.getName()+"(身份证号码:"+personnelPoliceRank.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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

/*
		return "redirect:" + adminPath + "/file/template/personnelPoliceRank/view?id=personnel_personnelPoliceRank&isCover="+isCover;
*/
	}


	/**
	 * 历史记录
	 * @return
	 */
	@RequiresPermissions("personnel:personnelPoliceRank:view")
	@RequestMapping(value = "historyform")
	public String historyform(String idNumber,Model model) {

		List<PersonnelPoliceRank> list = new ArrayList<>();
		list = personnelPoliceRankDao.selectHistoryByIdNumber(idNumber);

		model.addAttribute("idNumber",idNumber);
		model.addAttribute("list", list);

		return "modules/personnel/personnelPoliceRankHistoryForm";
	}

	@RequiresPermissions("personnel:personnelPoliceRank:view")
	@RequestMapping(value = "newForm")
	public String newForm(PersonnelPoliceRank personnelPoliceRank, Model model,HttpServletRequest request) {
		if(personnelPoliceRank!=null && personnelPoliceRank.getIdNumber()!= null && !personnelPoliceRank.getIdNumber().isEmpty()){
			PersonnelBase personnelBase = personnelBaseService.findInfoByIdNumber(personnelPoliceRank.getIdNumber());
			if(personnelBase!=null){
				personnelPoliceRank.setPeopleName(personnelBase.getName());
				personnelPoliceRank.setSex(personnelBase.getSex());
			}
		}
		model.addAttribute("personnelPoliceRank", personnelPoliceRank);
		model.addAttribute("isHistory", true);
		return "modules/personnel/personnelPoliceRankForm";
	}


	/**
	 * 预警生成晋升名单
	 *
	 */
	@RequestMapping(value = "warnCreatePersonList")
	public String warnCreatePersonList(Model model,HttpServletRequest request,HttpServletResponse response,PersonnelPoliceRank personnelPoliceRank){
		List<PersonnelPoliceRank> mingdanList = new ArrayList<>();
		//今年  12月31日
		Calendar nowYearCalendar = Calendar.getInstance();
		nowYearCalendar.set(Calendar.MONTH,12);
		nowYearCalendar.set(Calendar.DAY_OF_MONTH,1);
		nowYearCalendar.add(Calendar.DAY_OF_MONTH,-1);
		//下一年 12月31日
		Calendar nextYearCalendar = Calendar.getInstance();
		nextYearCalendar.add(Calendar.YEAR,1);
		nextYearCalendar.set(Calendar.MONTH,12);
		nextYearCalendar.set(Calendar.DAY_OF_MONTH,1);
		nextYearCalendar.add(Calendar.DAY_OF_MONTH,-1);
		try {

			if(CacheUtils.get(UserUtils.getUser().getLoginName()+"warnCreatePersonList") == null){
				List<Dict> rankTypeList = DictUtils.getDictList("type_rank_calculation");
				//截至时间  今年  12月31日
				personnelPoliceRank.setEndFinishDate(nowYearCalendar.getTime());
				List<PersonnelPoliceRank> nowList = new ArrayList<>();
				List<PersonnelPoliceRank> nextList = new ArrayList<>();
				PersonnelPoliceRank nextPoliceRank0 = new PersonnelPoliceRank();
				//截至时间 下一年 12月31日
				nextPoliceRank0.setEndFinishDate(nextYearCalendar.getTime());
				for(Dict tempDict : rankTypeList){
					personnelPoliceRank.setCalculateType(tempDict.getValue());
					Page<PersonnelPoliceRank> now= personnelPoliceRankService.findLastPage(new Page<PersonnelPoliceRank>(request, response,-1), personnelPoliceRank);
					nowList.addAll(now.getList());
					nextPoliceRank0.setCalculateType(tempDict.getValue());
					Page<PersonnelPoliceRank> next= personnelPoliceRankService.findLastPage(new Page<PersonnelPoliceRank>(request, response,-1), nextPoliceRank0);
					nextList.addAll(next.getList());
				}
				if(nowList!=null && nextList.size()>0){
					for(PersonnelPoliceRank nextPoliceRank : nextList){
						boolean isNow = false;
						String nextStartDate = DateUtils.formatDate(nextPoliceRank.getStartDate());//起算日期
						for(PersonnelPoliceRank nowPoliceRank : nowList){
							String nowStartDate = DateUtils.formatDate(nowPoliceRank.getStartDate());//起算日期
							if(nowPoliceRank.getIdNumber().equals(nextPoliceRank.getIdNumber()) && nextStartDate.equals(nowStartDate)){
								isNow = true;
							}
						}
						if(!isNow){
							mingdanList.add(nextPoliceRank);
						}
					}
				}else{
					mingdanList.addAll(nextList);
				}
				CacheUtils.put(UserUtils.getUser().getLoginName()+"warnCreatePersonList",mingdanList);
			}else{
				mingdanList = (List<PersonnelPoliceRank>) CacheUtils.get(UserUtils.getUser().getLoginName()+"warnCreatePersonList");
			}
		}catch (Exception e){
			logger.error("晋升名单生成失败,错误信息："+e.toString());
			model.addAttribute("message","晋升名单生成失败,请重试");
			model.addAttribute("content","晋升名单生成失败,请重试");
		}
		model.addAttribute("mingdanList",mingdanList);
		model.addAttribute("endFinishDate",nextYearCalendar.getTime());
		return "modules/warn/policeRankPersonList";
	}

	@RequestMapping(value = {"chartsPoliceRankList"})
	public String chartsPoliceRankDetail(PersonnelPoliceRank personnelPoliceRank, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelPoliceRank.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelPoliceRank> page = personnelPoliceRankService.findChartsPoliceRankPage(new Page<PersonnelPoliceRank>(request, response), personnelPoliceRank);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/charts/chartsPersonnelPoliceRankList";
	}

}