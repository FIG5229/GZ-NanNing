/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.exam.dao.ExamOfficeDao;
import com.thinkgem.jeesite.modules.exam.entity.*;
import com.thinkgem.jeesite.modules.exam.service.*;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * 考评数据表Controller
 * 新版考评流程（不需要改动的仍在ExamWorkflowController中）
 * 修改时请务必添加注释
 * 修改时请务必添加注释
 * @author bradley.zhao
 * @version 2019-12-19
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examWorkflowDatas")
public class ExamWorkflowDatasController extends BaseController {

	@Autowired
	private ExamWorkflowDatasService examWorkflowDatasService;

	@Autowired
	private ExamWorkflowService examWorkflowService;

	@Autowired
	private ExamWorkflowDefineService examWorkflowDefineService;

	@Autowired
	private ExamStandardBaseInfoService examStandardBaseInfoService;

	@Autowired
	ExamWorkflowSegmentsService examWorflowSegmentsService;

	@Autowired
	private ExamStandardTemplateDefineService examStandardTemplateDefineService;

	@Autowired
	private ExamWeightsMainService examWeightsMainService;

	@Autowired
	private ExamUnitAllPublicService examUnitAllPublicService;

	@Autowired
	private ExamUnitAllPublicYearService examUnitAllPublicYearService;

	@Autowired
	private ExamLdScoreMonthService examLdScoreMonthService;

	@Autowired
	private ExamLdScoreService examLdScoreService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private ExamOfficeDao examOfficeDao;

	@Autowired
	private ExamWeightsService examWeightsService;

	@ModelAttribute
	public ExamWorkflowDatas get(@RequestParam(required=false) String id) {
		ExamWorkflowDatas entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examWorkflowDatasService.get(id);
		}
		if (entity == null){
			entity = new ExamWorkflowDatas();
		}
		return entity;
	}
	
//	@RequiresPermissions("exam:examWorkflowDatas:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamWorkflowDatas examWorkflowDatas, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamWorkflowDatas> page = examWorkflowDatasService.findPage(new Page<ExamWorkflowDatas>(request, response), examWorkflowDatas); 
		model.addAttribute("page", page);
		return "modules/exam/examWorkflowDatasList";
	}

//	@RequiresPermissions("exam:examWorkflowDatas:view")
	@RequestMapping(value = "form")
	public String form(ExamWorkflowDatas examWorkflowDatas, Model model) {
		model.addAttribute("examWorkflowDatas", examWorkflowDatas);
		return "modules/exam/examWorkflowDatasForm";
	}

//	@RequiresPermissions("exam:examWorkflowDatas:edit")
	@RequestMapping(value = "save")
	public String save(ExamWorkflowDatas examWorkflowDatas, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examWorkflowDatas)){
			return form(examWorkflowDatas, model);
		}
		examWorkflowDatasService.save(examWorkflowDatas);
		addMessage(redirectAttributes, "保存考评数据表成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examWorkflowDatas/?repage";
	}
	@RequestMapping(value = "updateTerm")
	@ResponseBody
	public Result updateBeta(ExamWorkflowDatas examWorkflowDatas, Model model, RedirectAttributes redirectAttributes) {

		examWorkflowDatas.setIsAlreadySelected("66");
		examWorkflowDatasService.save(examWorkflowDatas);
//		ExamPushHistoryService examPushHistoryService;
//		examPushHistoryService.save();
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("成功");
		return result;
	}

	/*批量更新被考评对象下一环节审核人为空的数据*/
	@RequestMapping(value = "selectPersonByIdsBeta")
	@ResponseBody
	public Result selectPersonByIdsBeta(ExamWorkflowDatas examWorkflowDatas,HttpServletRequest request,@RequestParam(value = "fillPersonIds[]") String[] ids){
		String nextStatus = request.getParameter("nextStatus");
		String personId = request.getParameter("personId");
		User u = UserUtils.get(personId);
		examWorkflowDatas.setIds(ids);
		Result result = new Result();
		int tempStatus = examWorkflowDatasService.getStatusById(ids[0],examWorkflowDatas.getWorkflowId(),examWorkflowDatas.getStatus(),UserUtils.getUser().getId());
		nextStatus = getNextStepStatus(examWorkflowDatas.getWorkflowId(),tempStatus);
		String nNextStatus = getNextStepStatus(examWorkflowDatas.getWorkflowId(), Integer.valueOf(nextStatus));
		/*下一环节状态为3时，设置下下环节的审核人*/
		if (StringUtils.isNotBlank(nextStatus) && nextStatus.equals("3")){
			nextStatus = getNextStepStatus(examWorkflowDatas.getWorkflowId(),Integer.valueOf(nextStatus));
		}
		try {
			examWorkflowDatasService.updateExamPersonByIds(examWorkflowDatas,nextStatus,u.getName(),personId);
			result.setSuccess(true);
		}catch (Exception e){
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 查看未审核的 初核人
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "unExam")
	public String unExam(HttpServletRequest request, Model model){
		String workflowId = request.getParameter("workflowId");
		List<ExamWorkflowDatas> list = examWorkflowDatasService.findUnExamList(workflowId);

		model.addAttribute("list",list);
		return "modules/exam/examWorkflowUnExamList";
	}

	/**	此方法修改时需要同时修改：saveByIdsBetaHistory
	 * 批量通过和退回考评对象
	 * @param examWorkflowDatas
	 * @param model
	 * @param request
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveByIdsBeta")
	public Result saveByIdsBeta(ExamWorkflowDatas examWorkflowDatas, Model model, HttpServletRequest request, @RequestParam(value = "fillPersonIds[]") String[] ids) {
		Result result = new Result();
		/*workflowId 为空  和状态为空时推送失败*/
		if (StringUtils.isBlank(examWorkflowDatas.getWorkflowId()) || examWorkflowDatas.getStatus() == null) {
			result.setSuccess(false);
			return result;
		} else {
			/*推送到下一步时，查询是考评对象下一步是否有审核人*/
			List<Map<String, String>> resultList = new ArrayList<>();
			String history = request.getParameter("history");
			String nextStatus = request.getParameter("nextStatus");
			if (examWorkflowDatas.getStatus()>=3){

			}
			/*此处状态要根据被考评对象的状态来处理，整体流程为自评时，该考评对象状态可能已经是初核状态*/
			int tempStatus = examWorkflowDatasService.getStatusById(ids[0],examWorkflowDatas.getWorkflowId(),examWorkflowDatas.getStatus(),UserUtils.getUser().getId());
			examWorkflowDatas.setStatus(tempStatus);
			nextStatus = getNextStepStatus(examWorkflowDatas.getWorkflowId(),tempStatus);

			if (StringUtils.isBlank(history)) {
				result.setSuccess(false);
				return result;
			}
			if (StringUtils.isNotBlank(history) && history.equals("end") && !nextStatus.equals("8")) {
				/*先查询每个考评对象的下一步的审评人是否为空*/
				{
					StringBuffer stringBuffer = new StringBuffer();
					Map<String, Object> segmentsParam = new HashMap<String, Object>();
					segmentsParam.put("flowId", examWorkflowDatas.getWorkflowId());
					segmentsParam.put("DEL_FLAG_NORMAL", "0");
					List<Map<String, Object>> segmentsList = examWorflowSegmentsService.findSegmentsList(segmentsParam);
					for (String id : ids) {
						Map<String, String> map = new HashMap<>();
						List<Map<String, String>> nextList = examWorkflowDatasService.findNextExamPerson(id, nextStatus,examWorkflowDatas.getWorkflowId(),examWorkflowDatas.getStatus());
						Map<String, String> nextPerson = nextList.get(0);
						String fillPerson = nextPerson.get("fill_person");
						if (nextStatus.equals("3")){
							nextStatus = getNextStepStatus(segmentsList, 3);
						}
						switch (nextStatus) {
							case "1":
								break;
							case "2":
								break;
							case "3":
								String nNextStep = getNextStepStatus(segmentsList, 3);

								break;
							case "4":
								if (StringUtils.isBlank(nextPerson.get("depart_sign_id"))) {
									stringBuffer.append(fillPerson + "，");
									resultList.add(map);
								}

								break;
							case "5":
								if (StringUtils.isBlank(nextPerson.get("part_bureaus_sign_id"))) {
									stringBuffer.append(fillPerson + "，");
									resultList.add(map);
								}

								break;
							case "6":
								if (StringUtils.isBlank(nextPerson.get("adjust_person_id"))) {
									resultList.add(map);
									stringBuffer.append(fillPerson + "，");
								}

								break;
							case "7":
								if (StringUtils.isBlank(nextPerson.get("main_bureaus_sign_id"))) {
									stringBuffer.append(fillPerson + "，");
									resultList.add(map);
								}
								break;
							case "8":
								break;
						}
					}

					if (resultList.size() > 0) {
						String message = "请选择以下考评对象的推送人：";
						result.setSuccess(false);
						result.setMessage(message + stringBuffer.toString());
						return result;
					}
				}

				int status = examWorkflowDatas.getStatus();
				/*更新选择的考评对象的考评项的流程状态（考评对象中）*/
				/*当前是自评环节时 ，下一步状态为2，进行初核时，是对自评完的对象初核（考评对象所有考评项状态为2），所以需要获取初核的下一步状态*/
				nextStatus = getNextStepStatus(examWorkflowDatas.getWorkflowId(),Integer.valueOf(tempStatus));

				/*只更新我考核的数据*/
				examWorkflowDatasService.updateStatusByIdsBeta(examWorkflowDatas, ids,nextStatus);
				/*结束考核时  必须所有的考评对象都推送到下一环节时才能更新workflow中的状态*/

				/*更新考评流程状态*/
				ExamWorkflowDatas param = new ExamWorkflowDatas();
				param.setWorkflowId(examWorkflowDatas.getWorkflowId());
				//param.setStandardId(standardId);
				String condition = "a.status < " + nextStatus;
				param.setStatus(status);
				//condition += " AND a.operation_status <> 3";
				param.setCondition(condition);
				Map<String, Object> datasNumber = examWorkflowDatasService.selectDatasNumberBeta(param);
				/*所有的考评项所有被考聘那个对象全部结束当前环节 才推送到下一环节
				 * 否则，一个被考评对象进入下一环节后，其他考评对象无法再自评*/
				if (null == datasNumber || "0".equals(datasNumber.get("number").toString())) {
					Map<String, Object> params = new HashMap<>();
					params.put("id", examWorkflowDatas.getWorkflowId());
					params.put("status", examWorkflowDatas.getStatus());
					examWorkflowService.updateStatus(params);
					result.setResult("history");
				}
			}
			if (history.equals("back")) {
				String perStatus = request.getParameter("perStatus");
				/*更新所有考评对象的数据的环节状态*/
				examWorkflowDatasService.updateStatusByIdsBeta(examWorkflowDatas, ids,perStatus);

				/*回退时  有一个考评对象被回退 则考评直接回退*/
				Map<String, Object> params = new HashMap<>();
				params.put("id", examWorkflowDatas.getWorkflowId());
				params.put("status", examWorkflowDatas.getStatus());
				examWorkflowService.updateStatus(params);
				result.setResult("history");
			}
//			examWorkflowDatasService.updateOperationByIdsBeta(examWorkflowDatas,ids);
			result.setSuccess(true);
			return result;
		}
	}


	/**	此方法修改时需要同时修改：saveByIdsBeta
	 * 当整体流程未完成时，对操作完的对象进行审核
	 * 列：反恐怖支队完成自评时，先审核反恐怖支队，让其进入系统公示
	 * @param examWorkflowDatas
	 * @param model
	 * @param request
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveByIdsBetaHistory")
	public Result saveByIdsBetaHistory(ExamWorkflowDatas examWorkflowDatas, Model model, HttpServletRequest request, @RequestParam(value = "fillPersonIds[]") String[] ids) {
		Result result = new Result();
		/*workflowId 为空  和状态为空时推送失败*/
		if (StringUtils.isBlank(examWorkflowDatas.getWorkflowId()) || examWorkflowDatas.getStatus() == null) {
			result.setSuccess(false);
			return result;
		} else {
			/*推送到下一步时，查询是考评对象下一步是否有审核人*/
			List<Map<String, String>> resultList = new ArrayList<>();
			String history = request.getParameter("history");
			String nextStatus = request.getParameter("nextStatus");
			/*此处状态要根据被考评对象的状态来处理，整体流程为自评时，该考评对象状态可能已经是初核状态*/
			int tempStatus = examWorkflowDatasService.getStatusById(ids[0],examWorkflowDatas.getWorkflowId(),examWorkflowDatas.getStatus(),UserUtils.getUser().getId());
			examWorkflowDatas.setStatus(tempStatus);
			nextStatus = getNextStepStatus(examWorkflowDatas.getWorkflowId(),tempStatus);
			if (StringUtils.isBlank(history)) {
				result.setSuccess(false);
				return result;
			}
			if (StringUtils.isNotBlank(history) && history.equals("end") && !nextStatus.equals("8")) {
				/*先查询每个考评对象的下一步的审评人是否为空*/
				{
					StringBuffer stringBuffer = new StringBuffer();
					Map<String, Object> segmentsParam = new HashMap<String, Object>();
					segmentsParam.put("flowId", examWorkflowDatas.getWorkflowId());
					segmentsParam.put("DEL_FLAG_NORMAL", "0");
					List<Map<String, Object>> segmentsList = examWorflowSegmentsService.findSegmentsList(segmentsParam);
					for (String id : ids) {
						Map<String, String> map = new HashMap<>();
						List<Map<String, String>> nextList = examWorkflowDatasService.findNextExamPerson(id, nextStatus,examWorkflowDatas.getWorkflowId(),examWorkflowDatas.getStatus());
						if (nextList== null){
							User user = UserUtils.get(id);
							if (user != null ){
								stringBuffer.append(user.getName());
							}
							continue;
						}
						Map<String, String> nextPerson = nextList.get(0);
						String fillPerson = nextPerson.get("fill_person");
						if (nextStatus.equals("3")){
							nextStatus = getNextStepStatus(segmentsList, 3);
						}
						switch (nextStatus) {
							case "1":
								break;
							case "2":
								break;
							case "3":
								String nNextStep = getNextStepStatus(segmentsList, 3);

								break;
							case "4":
								if (StringUtils.isBlank(nextPerson.get("depart_sign_id"))) {
									stringBuffer.append(fillPerson + "，");
									resultList.add(map);
								}

								break;
							case "5":
								if (StringUtils.isBlank(nextPerson.get("part_bureaus_sign_id"))) {
									stringBuffer.append(fillPerson + "，");
									resultList.add(map);
								}

								break;
							case "6":
								if (StringUtils.isBlank(nextPerson.get("adjust_person_id"))) {
									resultList.add(map);
									stringBuffer.append(fillPerson + "，");
								}

								break;
							case "7":
								if (StringUtils.isBlank(nextPerson.get("main_bureaus_sign_id"))) {
									stringBuffer.append(fillPerson + "，");
									resultList.add(map);
								}
								break;
							case "8":
								break;
						}
					}

					if (resultList.size() > 0) {
						String message = "请选择以下考评对象的推送人：";
						result.setSuccess(false);
						result.setMessage(message + stringBuffer.toString());
						return result;
					}
				}

				int status = examWorkflowDatas.getStatus();
				/*更新选择的考评对象的考评项的流程状态（考评对象中）*/
				/*当前是自评环节时 ，下一步状态为2，进行初核时，是对自评完的对象初核（考评对象所有考评项状态为2），所以需要获取初核的下一步状态*/
				nextStatus = getNextStepStatus(examWorkflowDatas.getWorkflowId(),Integer.valueOf(tempStatus));

				/*只更新我考核的数据*/
				examWorkflowDatasService.updateStatusByIdsBeta(examWorkflowDatas, ids,nextStatus);
				/*结束考核时  必须所有的考评对象都推送到下一环节时才能更新workflow中的状态*/

				/*更新考评流程状态*/
				ExamWorkflowDatas param = new ExamWorkflowDatas();
				param.setWorkflowId(examWorkflowDatas.getWorkflowId());
				//param.setStandardId(standardId);
				String condition = "a.status < " + getNextStepStatus(examWorkflowDatas.getWorkflowId(),status);
				param.setStatus(status);
				//condition += " AND a.operation_status <> 3";
				param.setCondition(condition);
				Map<String, Object> datasNumber = examWorkflowDatasService.selectDatasNumberBeta(param);
				/*所有的考评项所有被考聘那个对象全部结束当前环节 才推送到下一环节
				 * 否则，一个被考评对象进入下一环节后，其他考评对象无法再自评*/
				if (null == datasNumber || "0".equals(datasNumber.get("number").toString())) {
					Map<String, Object> params = new HashMap<>();
					params.put("id", examWorkflowDatas.getWorkflowId());
					params.put("status", examWorkflowDatas.getStatus());
					examWorkflowService.updateStatus(params);
					result.setResult("history");
				}
			}
			if (history.equals("back")) {
				String perStatus = request.getParameter("perStatus");
				/*更新所有考评对象的数据的环节状态*/
				examWorkflowDatasService.updateStatusByIdsBeta(examWorkflowDatas, ids,perStatus);

				/*回退时  有一个考评对象被回退 则考评直接回退*/
				Map<String, Object> params = new HashMap<>();
				params.put("id", examWorkflowDatas.getWorkflowId());
				params.put("status", examWorkflowDatas.getStatus());
				examWorkflowService.updateStatus(params);
				result.setResult("history");
			}
//			examWorkflowDatasService.updateOperationByIdsBeta(examWorkflowDatas,ids);
			result.setSuccess(true);
			return result;
		}
	}


	@RequestMapping(value = "/saveBeta")
	public String appraiseSaveBeta(ExamWorkflowDatas examWorkflowDatas, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String personId = request.getParameter("personId");
		String personName = request.getParameter("personName");
		String personType = request.getParameter("personType");
		String processType = request.getParameter("processType");
		String workflowId = request.getParameter("workflowId");
		/*左侧为树时，结束并推送到下一级时，重定向时使用*/
		String fillPersonId = request.getParameter("fillPersonId");
		String examType = request.getParameter("examType");
		String noTree = request.getParameter("noTree");


		int  nextStatus = new Integer(request.getParameter("nextStatus"));
		int status = -1;
		if (null != request.getParameter("status") && !"".equals(request.getParameter("status"))) {
			status = new Integer(request.getParameter("status"));
		}
		//初核人员、绩效办改分
		String isUpdateSorce = "";
		if (null != request.getParameter("isUpdateSorce")) {
			isUpdateSorce = request.getParameter("isUpdateSorce").toString();
		}
		boolean isHashNoPass = false;
		if (WorkFloatConstant.SIMPLE_EXAM == status) {
			for (ExamWorkflowDatas examWorkflowData : examWorkflowDatas.getDatas()) {
				//不通过
				if (null != examWorkflowData.getOperationStatus() && "2".equals(examWorkflowData.getOperationStatus().toString())) {
					isHashNoPass = true;
					break;
				}
			}
		}


		int tempStatus =status;
	    ArrayList<ExamWorkflowDatas> workflowDatasList= examWorkflowDatas.getDatas();
		for (ExamWorkflowDatas item :workflowDatasList) {
			/*设置权重使用*/
			item.setExamType(examType);

		    if (examWorkflowDatas.getOperationStatus() != null){
		        item.setOperationStatus(examWorkflowDatas.getOperationStatus());
            }
		    if (examWorkflowDatas.getStatus() != null){
		        item.setStatus(examWorkflowDatas.getStatus());
            }
//		    setPerson(personId,personName,item,"");
			if (beanValidator(model, item) && null != item.getRowId()) {
				if (null != request.getParameter("personId") && null != request.getParameter("personName")) {
					this.setPerson(personId, personName, item, personType);
				}
				if ("selfAppraise".equals(processType)) {
				    /*自评人第一次查看时算分*/
//					this.setWeight(item);
					tempStatus = nextStatus;
					item.setOperationStatus(1);
				} else if ("appraiseExam".equals(processType)) {
					tempStatus = nextStatus;
					item.setOperationStatus(1);
				} else if ("departSign".equals(processType)) {
//                    examWorkflowData.setDepartSignId(UserUtils.getUser().getId());
//                    examWorkflowData.setDepartSign(UserUtils.getUser().getName());
					tempStatus = nextStatus;
					item.setOperationStatus(1);
				} else if ("partBureausSign".equals(processType)) {
					tempStatus = nextStatus;
					item.setOperationStatus(1);
				} else if ("mainBureausSign".equals(processType)) {
					tempStatus = nextStatus;
					item.setOperationStatus(1);
				} else if ("groupAdjust".equals(processType)) {
					tempStatus = nextStatus;
					item.setOperationStatus(1);
				} else if ("save".equals(processType)) {
				    /*未提交状态改为以保存，其他 是什么状态就什么状态*/
				    if (item.getOperationStatus() != null && item.getOperationStatus().equals("-1")){
					    item.setOperationStatus(0);
                    }
				    /*自评人第一次查看时算分*/
//					this.setWeight(item);
				}
				if ("true".equals(isUpdateSorce)) {
					item.setStatus(null);
					item.setStatus(null);
				} else {
					item.setStatus(new Integer(tempStatus));
				}
			}
			examWorkflowDatasService.saveBeta(item);
		}


		//查询本阶段是否所有被考评对象的数据都已经处理，若已处理则推送到下一环节
		if ("selfAppraise".equals(processType) || "appraiseExam".equals(processType) || "departSign".equals(processType) ||
				"partBureausSign".equals(processType) || "groupAdjust".equals(processType) ||
				"mainBureausSign".equals(processType) || "mainBureausSignAll".equals(processType) &&
				WorkFloatConstant.ALL_PUBLIC != nextStatus) {
		    /*1、被考评人员自评完，推送到系统自评时，把未自评的项推送到下一环节，当所有考评人员对象完成考评时，此考评流程进入下一环节*/
			/*2、					把未自评的项添加上考评人*/
            ExamWorkflowDatas datas = new ExamWorkflowDatas();
            datas.setWorkflowId(workflowId);
            datas.setFillPersonId(fillPersonId);
            /*更新未选择的考评项的状态*/
			datas.setStatus(nextStatus);
            examWorkflowDatasService.updateStatusBeta(datas);
     		setPersonBeta(nextStatus,workflowId,fillPersonId,personId,personName);

			ExamWorkflowDatas param = new ExamWorkflowDatas();
			param.setWorkflowId(workflowId);
			//param.setStandardId(standardId);
			String condition = "a.status < " + nextStatus;
			//condition += " AND a.operation_status <> 3";
			param.setStatus(nextStatus);
			param.setCondition(condition);
			Map<String, Object> datasNumber = examWorkflowDatasService.selectDatasNumberBeta(param);
			/*所有的考评项所有被考聘那个对象全部结束当前环节 才推送到下一环节
			* 否则，一个被考评对象进入下一环节后，其他考评对象无法再自评*/
			if (null == datasNumber || "0".equals(datasNumber.get("number").toString())) {
				if (!"mainBureausSignAll".equals(processType)) {
					Map<String, Object> uptParam = new HashMap<String, Object>();
					uptParam.put("id", workflowId);
					uptParam.put("status", nextStatus);
					/*operationStatus为当前环节的操作状态，下一环节的操作状态是 未处理*/
					uptParam.put("operationStatus", new Integer("-1"));
					examWorkflowService.updateStatus(uptParam);
				}
				Map<String, Object> uptDatasParam = new HashMap<String, Object>();
				uptDatasParam.put("workflowId", workflowId);
				uptDatasParam.put("status", nextStatus);
				/*最后一个人提交时 会改变所有数据的状态
				uptDatasParam.put("condition", "fill_person_id = '"+fillPersonId+"'");*/
				if (WorkFloatConstant.SELF_EXAM != Integer.valueOf(status)) {
					uptDatasParam.put("operationStatus", new Integer("-1"));
				}

				/*更新到下一个流程*/
				examWorkflowDatasService.updateStatus(uptDatasParam);
			}
		}
		/*系统初核时，如果有不同过的考评项则回退至系统自评流程，被考评A通过 B不通过，此时A也要重新自评*/
		if (WorkFloatConstant.SIMPLE_EXAM == Integer.valueOf(status) && isHashNoPass) {
			Map<String, Object> uptParam = new HashMap<String, Object>();
			uptParam.put("id", workflowId);
			uptParam.put("status", WorkFloatConstant.SELF_EXAM);
			uptParam.put("operationStatus", new Integer("1"));
			examWorkflowService.updateStatus(uptParam);
		}
		/*if (StringUtils.isNotBlank(examWorkflowDatas.getWorkflowId())){
            ExamWorkflow examWorkflow = new ExamWorkflow();
            examWorkflow.setStatus(examWorkflowDatas.getStatus());
            examWorkflow.setOperationStatus(examWorkflowDatas.getOperationStatus());
		    examWorkflowService.save(examWorkflow);
        }*/

		/*自评完应，推送到下一个流程时，应重定向到历史查看*/

		if (processType.equals("save")){
		return "redirect:"+Global.getAdminPath()+"/exam/examWorkflowDatas/examBeta?repage&examWorkflowId="+workflowId
				+"&fillPersonId="+fillPersonId+"&noTree="+noTree;
		}else {
		return "redirect:" + Global.getAdminPath() + "/exam/examWorkflowDatas/examBeta?history=true&examWorkflowId=" + workflowId + "&examType=" +
				examType + "&hisStatus=" + status+"&fillPersonId="+fillPersonId+"&noTree="+noTree;
		}

	}

	/**
	 * 初核保存 只有初核使用此方法
	 * @param examWorkflowDatas
	 * @param model
	 * @param redirectAttributes
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveBetaHistory")
	public String saveBetaHistory(ExamWorkflowDatas examWorkflowDatas, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String personId = request.getParameter("personId");
		String personName = request.getParameter("personName");
		String personType = request.getParameter("personType");
		String processType = request.getParameter("processType");
		String workflowId = request.getParameter("workflowId");
		/*左侧为树时，结束并推送到下一级时，重定向时使用*/
		String fillPersonId = request.getParameter("fillPersonId");
		String examType = request.getParameter("examType");
		String noTree = request.getParameter("noTree");


		int  nextStatus = new Integer(request.getParameter("nextStatus"));
		int status = -1;
		if (null != request.getParameter("status") && !"".equals(request.getParameter("status"))) {
			status = new Integer(request.getParameter("status"));
		}
		//初核人员、绩效办改分
		String isUpdateSorce = "";
		if (null != request.getParameter("isUpdateSorce")) {
			isUpdateSorce = request.getParameter("isUpdateSorce").toString();
		}
		boolean isHashNoPass = false;
		if (WorkFloatConstant.SIMPLE_EXAM == status) {
			for (ExamWorkflowDatas examWorkflowData : examWorkflowDatas.getDatas()) {
				//不通过
				if (null != examWorkflowData.getOperationStatus() && "2".equals(examWorkflowData.getOperationStatus().toString())) {
					isHashNoPass = true;
					break;
				}
			}
		}


		int tempStatus =status;
	    ArrayList<ExamWorkflowDatas> workflowDatasList= examWorkflowDatas.getDatas();
		for (ExamWorkflowDatas item :workflowDatasList) {
			/*设置权重使用*/
			item.setExamType(examType);

		    if (examWorkflowDatas.getOperationStatus() != null){
		        item.setOperationStatus(examWorkflowDatas.getOperationStatus());
            }
		    if (examWorkflowDatas.getStatus() != null){
		        item.setStatus(examWorkflowDatas.getStatus());
            }
//		    setPerson(personId,personName,item,"");
			if (beanValidator(model, item) && null != item.getRowId()) {
				if (null != request.getParameter("personId") && null != request.getParameter("personName")) {
					this.setPerson(personId, personName, item, personType);
				}
				if ("selfAppraise".equals(processType)) {
					/*改为查看时设置权重*/
//					this.setWeight(item);
					tempStatus = nextStatus;
					item.setOperationStatus(1);
				} else if ("appraiseExam".equals(processType)) {
					tempStatus = nextStatus;
					item.setOperationStatus(1);
				} else if ("departSign".equals(processType)) {
//                    examWorkflowData.setDepartSignId(UserUtils.getUser().getId());
//                    examWorkflowData.setDepartSign(UserUtils.getUser().getName());
					tempStatus = nextStatus;
					item.setOperationStatus(1);
				} else if ("partBureausSign".equals(processType)) {
					tempStatus = nextStatus;
					item.setOperationStatus(1);
				} else if ("mainBureausSign".equals(processType)) {
					tempStatus = nextStatus;
					item.setOperationStatus(1);
				} else if ("groupAdjust".equals(processType)) {
					tempStatus = nextStatus;
					item.setOperationStatus(1);
				} else if ("save".equals(processType)) {
				    /*未提交状态改为以保存，其他 是什么状态就什么状态*/
				    if (item.getOperationStatus() != null && item.getOperationStatus().equals("-1")){
					    item.setOperationStatus(0);
                    }
				    /*改为查看时设置权重*/
//					this.setWeight(item);
				}
				if ("true".equals(isUpdateSorce)) {
					item.setStatus(null);
					item.setStatus(null);
				} else {
					item.setStatus(new Integer(tempStatus));
				}
			}
			examWorkflowDatasService.saveBeta(item);
		}


		//查询本阶段是否所有被考评对象的数据都已经处理，若已处理则推送到下一环节
		if ("selfAppraise".equals(processType) || "appraiseExam".equals(processType) || "departSign".equals(processType) ||
				"partBureausSign".equals(processType) || "groupAdjust".equals(processType) ||
				"mainBureausSign".equals(processType) || "mainBureausSignAll".equals(processType) &&
				WorkFloatConstant.ALL_PUBLIC != nextStatus) {
		    /*1、被考评人员自评完，推送到系统自评时，把未自评的项推送到下一环节，当所有考评人员对象完成考评时，此考评流程进入下一环节*/
			/*2、					把未自评的项添加上考评人*/
            ExamWorkflowDatas datas = new ExamWorkflowDatas();
            datas.setWorkflowId(workflowId);
            datas.setFillPersonId(fillPersonId);
            /*更新未选择的考评项的状态*/
			datas.setStatus(nextStatus);
            examWorkflowDatasService.updateStatusBeta(datas);
     		setPersonBeta(nextStatus,workflowId,fillPersonId,personId,personName);

			ExamWorkflowDatas param = new ExamWorkflowDatas();
			param.setWorkflowId(workflowId);
			//param.setStandardId(standardId);
			String condition = "a.status < " + nextStatus;
			//condition += " AND a.operation_status <> 3";
			param.setStatus(nextStatus);
			param.setCondition(condition);
			Map<String, Object> datasNumber = examWorkflowDatasService.selectDatasNumberBeta(param);
			/*所有的考评项所有被考聘那个对象全部结束当前环节 才推送到下一环节
			* 否则，一个被考评对象进入下一环节后，其他考评对象无法再自评*/
			if (null == datasNumber || "0".equals(datasNumber.get("number").toString())) {
				if (!"mainBureausSignAll".equals(processType)) {
					Map<String, Object> uptParam = new HashMap<String, Object>();
					uptParam.put("id", workflowId);
					uptParam.put("status", nextStatus);
					/*operationStatus为当前环节的操作状态，下一环节的操作状态是 未处理*/
					uptParam.put("operationStatus", new Integer("-1"));
					examWorkflowService.updateStatus(uptParam);
				}
				Map<String, Object> uptDatasParam = new HashMap<String, Object>();
				uptDatasParam.put("workflowId", workflowId);
				uptDatasParam.put("status", nextStatus);
				if (WorkFloatConstant.SELF_EXAM != Integer.valueOf(status)) {
					uptDatasParam.put("operationStatus", new Integer("-1"));
				}

				/*更新到下一个流程*/
				examWorkflowDatasService.updateStatus(uptDatasParam);
			}
		}
		/*系统初核时，如果有不同过的考评项则回退至系统自评流程，被考评A通过 B不通过，此时A也要重新自评*/
		if (WorkFloatConstant.SIMPLE_EXAM == Integer.valueOf(status) && isHashNoPass) {
			Map<String, Object> uptParam = new HashMap<String, Object>();
			uptParam.put("id", workflowId);
			uptParam.put("status", WorkFloatConstant.SELF_EXAM);
			uptParam.put("operationStatus", new Integer("1"));
			examWorkflowService.updateStatus(uptParam);
		}
		/*if (StringUtils.isNotBlank(examWorkflowDatas.getWorkflowId())){
            ExamWorkflow examWorkflow = new ExamWorkflow();
            examWorkflow.setStatus(examWorkflowDatas.getStatus());
            examWorkflow.setOperationStatus(examWorkflowDatas.getOperationStatus());
		    examWorkflowService.save(examWorkflow);
        }*/

		/*自评完应，推送到下一个流程时，应重定向到历史查看*/

		if (processType.equals("save")){
		return "redirect:"+Global.getAdminPath()+"/exam/examWorkflowDatas/examBetaById?repage&examWorkflowId="+workflowId
				+"&fillPersonId="+fillPersonId+"&noTree="+noTree;
		}else {
		return "redirect:" + Global.getAdminPath() + "/exam/examWorkflowDatas/examBetaById?history=true&examWorkflowId=" + workflowId + "&examType=" +
				examType + "&hisStatus=" + status+"&fillPersonId="+fillPersonId+"&noTree="+noTree;
		}

	}

	/**
	 *
	 * @param status	下一步的状态
	 * @param workflowId
	 * @param fillPersonId
	 * @param personId
	 * @param personName
	 */
	private void setPersonBeta(int status,String workflowId,String fillPersonId,String personId,String personName) {
		if ( StringUtils.isBlank(fillPersonId)){
			return;

		}		switch (status){
			case 2:
				ExamWorkflowDatas params = new ExamWorkflowDatas();
				params.setWorkflowId(workflowId);
				params.setFillPersonId(fillPersonId);
				params.setExamPersonId(personId);
				params.setExamPerson(personName);
				params.setStatus(status);
				examWorkflowDatasService.updatePersonBeta(params);
				break;
			case 3:
				;
				break;
			case 4:
				ExamWorkflowDatas depart = new ExamWorkflowDatas();
				depart.setWorkflowId(workflowId);
				depart.setFillPersonId(fillPersonId);
				depart.setDepartSignId(personId);
				depart.setDepartSign(personName);
				examWorkflowDatasService.updatePersonBeta(depart);
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
		}
	}


	@ResponseBody
	@RequestMapping(value = "selectData")
	public Result selectData(String standardId, String workflowId, String  workflowStatus ,@RequestParam(value="rowId[]")List<String> rowId,String fillPersonId) {
		ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
		if (StringUtils.isNotBlank(workflowStatus)){
			if (workflowStatus.equals("2") ||workflowStatus.equals("3") ){
				examWorkflowDatas.setExamPersonId(UserUtils.getUser().getId());
				examWorkflowDatas.setExamPerson(UserUtils.getUser().getName());
			}
			if (workflowStatus.equals("6")){
				examWorkflowDatas.setAdjustPersonId(UserUtils.getUser().getId());
				examWorkflowDatas.setAdjustPerson(UserUtils.getUser().getName());
			}

		}
		examWorkflowDatas.setStandardId(standardId);
		examWorkflowDatas.setFillPersonId(fillPersonId);
		examWorkflowDatas.setWorkflowId(workflowId);
		examWorkflowDatas.setSelectPersonId(UserUtils.getUser().getId());
		examWorkflowDatasService.selectedWorkflowDatas(examWorkflowDatas,rowId);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("成功");
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "recoveryData")
	public Result recoverySelectData(ExamWorkflowDatas examWorkflowDatas) {

		examWorkflowDatas.setIsSelected("1");
		examWorkflowDatasService.save(examWorkflowDatas);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("成功");
		return result;
	}


//	@RequiresPermissions("exam:examWorkflowDatas:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamWorkflowDatas examWorkflowDatas, RedirectAttributes redirectAttributes) {
		examWorkflowDatasService.delete(examWorkflowDatas);
		addMessage(redirectAttributes, "删除考评数据表成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examWorkflowDatas/?repage";
	}


	/**
	 * 考评树
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "appraise/examIndex")
	public String appraiseAdjustIndexBeta(HttpServletRequest request, HttpServletResponse response, Model model) {
		String workflowId = request.getParameter("examWorkflowId");
		ExamWorkflow examWorkflow =examWorkflowService.get(workflowId);
		int status = examWorkflow.getStatus();
		String targetUrl  = "modules/exam/appraiseExamIndexBeta";
		if (null != request.getParameter("history") || "true".equals(request.getParameter("history")) && null != request.getParameter("hisStatus") && status <= 3) {
			model.addAttribute("history", "true");
			model.addAttribute("hisCurStatus", String.valueOf(status));
			targetUrl = "modules/exam/appraiseExamIndexBetaHistory";
		}
		String examType = request.getParameter("examType");
		if (StringUtils.isNotBlank(examType)){
			model.addAttribute("examType",examType);
		}
		Map<String, Object> segmentsParam = new HashMap<String, Object>();
		segmentsParam.put("flowId", examWorkflow.getId());
		segmentsParam.put("DEL_FLAG_NORMAL", "0");
		List<Map<String, Object>> segmentsList = examWorflowSegmentsService.findSegmentsList(segmentsParam);
		model.addAttribute("segmentsList", segmentsList);
		model.addAttribute("workflowId", examWorkflow.getId());
		String nNextStatus = getNextStepStatus(segmentsList,status);
		String perStatus = getPreStepStatus(examWorkflow.getId(),status);
		/*下一步最终结果公示时，由绩效办开启，并进行分值计算*/
		model.addAttribute("nNextStatus",nNextStatus);
		model.addAttribute("perStatus",perStatus);
//        List<ExamStandardBaseInfo> standardInfoList = new ArrayList<ExamStandardBaseInfo>();
//        for (String id : standardIds.split(",")) {
//            ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(id);
//            standardInfoList.add(examStandardBaseInfo);
//        }
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("flowId", examWorkflow.getId());
		param.put("DEL_FLAG_NORMAL", "0");
		model.addAttribute("status", status);
		model.addAttribute("objId", request.getParameter("objId"));
		model.addAttribute("processType", request.getParameter("processType"));
		model.addAttribute("personType", request.getParameter("personType"));
		model.addAttribute("history", request.getParameter("history"));
		model.addAttribute("flowTemplateId", examWorkflow.getFlowTemplateId());
		if("1".equals(examWorkflow.getExamType()) && examWorkflow.getStatus()==3 && StringUtils.isBlank(request.getParameter("goExamIndex"))){
			//考评类别为局考处，且考评流程状态为系统公示
			Map<String,Object> resultMap = examWorkflowDatasService.getChuScoreListInPublic(examWorkflow);
			List<Map<String,Object>> resultList = (List<Map<String, Object>>) resultMap.get("resultList");
			List<Map<String, Object>> weigthsList = (List<Map<String, Object>>) resultMap.get("weigthsList");
			model.addAttribute("resultList",resultList);
			model.addAttribute("weigthsList",weigthsList);
			model.addAttribute("examType",examWorkflow.getExamType());
			model.addAttribute("examWorkflowId",examWorkflow.getId());
			model.addAttribute("status",examWorkflow.getStatus());
			return "modules/exam/public";
		}
		return targetUrl;
//        return "modules/exam/appraise_adjust";
	}


	/**
	 * 此页面仅供绩效办查看
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "examChuIndexBeta")
	public String examChuIndexBeta(HttpServletRequest request, HttpServletResponse response, Model model) {
		String workflowId = request.getParameter("examWorkflowId");
		ExamWorkflow examWorkflow =examWorkflowService.get(workflowId);
		/*查看环节详情时，isAll为true 查看本公司所有的被考评对象*/
		String isAll = request.getParameter("isAll");
		if (StringUtils.isNotBlank(isAll) && isAll.equals("true")){
			model.addAttribute("isAll",true);
		}else {
			model.addAttribute("isAll",false);
		}
		int status = examWorkflow.getStatus();
		String targetUrl  = "modules/exam/examChuIndexBeta";
		if (null != request.getParameter("history") || "true".equals(request.getParameter("history")) && null != request.getParameter("hisStatus") && status <= 3) {
			model.addAttribute("history", "true");
			model.addAttribute("hisCurStatus", String.valueOf(status));
			targetUrl = "modules/exam/examChuIndexBeta";
		}
		String examType = request.getParameter("examType");
		if (StringUtils.isNotBlank(examType)){
			model.addAttribute("examType",examType);
		}
		Map<String, Object> segmentsParam = new HashMap<String, Object>();
		segmentsParam.put("flowId", examWorkflow.getId());
		segmentsParam.put("DEL_FLAG_NORMAL", "0");
		List<Map<String, Object>> segmentsList = examWorflowSegmentsService.findSegmentsList(segmentsParam);
		model.addAttribute("segmentsList", segmentsList);
		model.addAttribute("workflowId", examWorkflow.getId());
		String nNextStatus = getNextStepStatus(segmentsList,status);
		String perStatus = getPreStepStatus(examWorkflow.getId(),status);
		/*下一步最终结果公示时，由绩效办开启，并进行分值计算*/
		model.addAttribute("nNextStatus",nNextStatus);
		model.addAttribute("perStatus",perStatus);
//        List<ExamStandardBaseInfo> standardInfoList = new ArrayList<ExamStandardBaseInfo>();
//        for (String id : standardIds.split(",")) {
//            ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(id);
//            standardInfoList.add(examStandardBaseInfo);
//        }
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("flowId", examWorkflow.getId());
		param.put("DEL_FLAG_NORMAL", "0");
		model.addAttribute("status", status);
		model.addAttribute("objId", request.getParameter("objId"));
		model.addAttribute("processType", request.getParameter("processType"));
		model.addAttribute("personType", request.getParameter("personType"));
		model.addAttribute("history", request.getParameter("history"));
		model.addAttribute("flowTemplateId", examWorkflow.getFlowTemplateId());
		model.addAttribute("isAll",true);
		if("1".equals(examWorkflow.getExamType()) && examWorkflow.getStatus()==3 && StringUtils.isBlank(request.getParameter("goExamIndex"))){
			//考评类别为局考处，且考评流程状态为系统公示
			Map<String,Object> resultMap = examWorkflowDatasService.getChuScoreListInPublic(examWorkflow);
			List<Map<String,Object>> resultList = (List<Map<String, Object>>) resultMap.get("resultList");
			List<Map<String, Object>> weigthsList = (List<Map<String, Object>>) resultMap.get("weigthsList");
			model.addAttribute("resultList",resultList);
			model.addAttribute("weigthsList",weigthsList);
			model.addAttribute("examType",examWorkflow.getExamType());
			model.addAttribute("examWorkflowId",examWorkflow.getId());
			model.addAttribute("status",examWorkflow.getStatus());
			return "modules/exam/examChuIndexBeta";
		}
		return targetUrl;
//        return "modules/exam/appraise_adjust";
	}

	/**
	 * 自评修改，查询已选择的考评项，然后找到考评模板的考评项数据
	 * @param redirectAttributes
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "examBeta")
	public String examBeta(RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model) {
		String fillPersonId = request.getParameter("fillPersonId");
		String workflowId = request.getParameter("examWorkflowId");
		/*全局公示时查看详细*/
		String publicDetail = request.getParameter("publicDetail");
		/*被考评对象查看时 noTree 为true*/
		String noTree = request.getParameter("noTree");
		model.addAttribute("noTree",noTree);
		String userId = UserUtils.getUser().getId();
		String examType = request.getParameter("examType");
		if (StringUtils.isBlank(examType)){
			examType = "";
		}

		ExamWorkflow examWorkflow =examWorkflowService.get(workflowId);

		Map<String, Object> segmentsParam = new HashMap<String, Object>();
		segmentsParam.put("flowId", examWorkflow.getId());
		segmentsParam.put("DEL_FLAG_NORMAL", "0");
		List<Map<String, Object>> segmentsList = examWorflowSegmentsService.findSegmentsList(segmentsParam);
		model.addAttribute("segmentsList", segmentsList);

		String targetUrl = "modules/exam/examFlowList";
		int status = examWorkflow.getStatus();
		ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
		String standardIds = examWorkflowDefine.getTemplatesIds();

//		List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoList(standardIds, examWorkflow, userId);
		/*自评时，无左侧树机构，fillPersonId为空，设置为登录用户的Id*/
		if (StringUtils.isBlank(fillPersonId)){
			fillPersonId = UserUtils.getUser().getId();
		}
		List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoListBeta(standardIds, examWorkflow, fillPersonId);
		model.addAttribute("processType", "selfAppraise");
		model.addAttribute("standardIds", standardIds);
		model.addAttribute("workflowId", examWorkflow.getId());
		model.addAttribute("standardInfoList", standardInfoList);
		model.addAttribute("examType", examWorkflow.getExamType());

		targetUrl = "modules/exam/appraiseBeta";
		model.addAttribute("examWorkflow", examWorkflow);
		model.addAttribute("examWorkflowDefine", examWorkflowDefine);


//        model.addAttribute("fillPersonId",examWorkflowDatas.getFillPersonId());

//        standardInfoList.get(0).getObj();
		List<ExamWorkflowDatas> selectedList =new ArrayList<>();

		/*查询出所有的考评项*/
		Date startDate = new Date();
		String objId = request.getParameter("objId");
//		for (ExamStandardBaseInfo sd:standardInfoList){
			ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
			examWorkflowDatas.setWorkflowId(examWorkflow.getId());
//			examWorkflowDatas.setStandardId(sd.getId());
			/*局考处使用objId，其他使用fillPersonnelId*/
        if (StringUtils.isNotBlank(objId)) {
            examWorkflowDatas.setObjId(objId);
			noTree="true";
        } else if (StringUtils.isNotBlank(fillPersonId)) {
            examWorkflowDatas.setFillPersonId(fillPersonId);
        } else {
            examWorkflowDatas.setFillPersonId(userId);
        }
			model.addAttribute("fillPersonId",examWorkflowDatas.getFillPersonId());

        /*再加上系统推送过来的考评项  条件 推送失败的*/

//        standardInfoList.get(0).getObj();
			List<ExamWorkflowDatas> tempDatasList;
			/*noTree为空时，查看当前用户审核的数据,不为空时，查看自己自评的所有数据*/
			if (StringUtils.isNotBlank(examWorkflowDatas.getFillPersonId()) && examWorkflowDatas.getFillPersonId().equals(userId)){
				noTree = "true";
			}
			/*绩效办看所有，不是自己相关的不能操作*/
			if (userId.equals("6af0e615e9b0477da82eff06ff532c8b")|| userId.equals("46c521bf67e24db28772b3eac52dc454")||
			userId.equals("978958003ea44a4bba3eed8ee6ceff3c")|| userId.equals("cca66e1339f14799b01f6db43ed16e16")){
//				noTree="true";
				if (examWorkflow.getStatus() == 6){
					noTree="true";
				}
			}
			if (StringUtils.isBlank(noTree)){
				/*查询审核的考评项*/
			examWorkflowDatas.setExamPersonId(userId);
			examWorkflowDatas.setDepartSignId(userId);
			examWorkflowDatas.setPartBureausSignId(userId);
			examWorkflowDatas.setAdjustPersonId(userId);
			examWorkflowDatas.setMainBureausSignId(userId);
				tempDatasList = examWorkflowDatasService.findAboutMeList(examWorkflowDatas);
			}else {
				/*noTree 不为空时查看考评对象的所有数据*/
				tempDatasList = examWorkflowDatasService.findList(examWorkflowDatas);
			}
			/*过滤掉未选择的考评项 后续只对选择的考评进行处理(设置权重、显示分值列等)*/
			selectedList.addAll(tempDatasList.stream().filter(item -> !item.getIsSelected().equals("0")).collect(Collectors.toList()));
//		}
		double sdsd = DateUtils.getTwoDateSecond(startDate,new Date());
		logger.error("查询所有datas数据时间-------------------------------->"+sdsd);
			//获取考评项数据
			Map<String, String> paramStandard = new HashMap<String, String>();
			/*查询出此考评的所有相关标准 并设置valueType 未选择的考评项不设置valueType 和 权重应该没有影响 此处可以先进行过滤 加快计算速度 代码未改*/
			/*缺少扣分的列  其他领导等，待核对补充*/
		selectedList.stream().forEach(workflowItem -> {
			if (StringUtils.isBlank(workflowItem.getStandardId()))
				return;
			paramStandard.put("examStardardId", workflowItem.getStandardId());
			List<Map<String, String>> rowList=examStandardTemplateDefineService.findTemplateDatas(paramStandard,new String[]{workflowItem.getRowId()});
			for (Map<String, String> item :rowList){
				String itemValue = item.get("item_value");
				String columnName = item.get("column_name");
				if (StringUtils.isBlank(columnName)){
					columnName="";
				}

				switch (item.get("column_type")){
					case "10":
						/*项目*/
						workflowItem.setS1(itemValue);
						workflowItem.setProject(itemValue);
						break;
					case "4":
						/*分类*/
						workflowItem.setS2(itemValue);
						workflowItem.setType(itemValue);
						break;
					case "1":
						/*评分标准*/
						workflowItem.setS3(itemValue);
						break;
					case "2":
						/*分值（+）*/
						workflowItem.setS4(itemValue);
						workflowItem.setValueType(1);
						break;
					case "3":
						/*分值（-）*/
						workflowItem.setS5(itemValue);
						workflowItem.setValueType(-1);
						break;
					case "6":
						/*备注*/
						workflowItem.setS6(itemValue);
						break;

				/*	case "11":
						workflowItem.setReduceOne(itemValue);
						break;
					case "12":
						workflowItem.setReduceTwo(itemValue);
						break;*/
				}

				if (columnName.equals("主任")){
					workflowItem.setZhuRen(itemValue);
					model.addAttribute("zhuRen","show");
				}
				if (columnName.equals("书记")){
					workflowItem.setShuJi(itemValue);
					model.addAttribute("shuJi","show");
				}
				if (columnName.contains("主管领导")){
					workflowItem.setZhuGuanLingDao(itemValue);
					model.addAttribute("zhuGuanLingDao","show");
				}
				if (columnName.equals("处长")){
					workflowItem.setChuZhang(itemValue);
					model.addAttribute("chuZhang","show");
				}
				if (columnName.equals("副处长")){
					workflowItem.setFuChuZhang(itemValue);
					model.addAttribute("fuChuZhang","show");
				}
				if (columnName.equals("副职")){
					workflowItem.setFuZhi(itemValue);
					model.addAttribute("fuZhi","show");
				}
				if (columnName.equals("副所长")){
					workflowItem.setFuSuoZhang(itemValue);
					model.addAttribute("fuSuoZhang","show");
				}
				if (columnName.equals("副支队长")){
					workflowItem.setFuZhiDuiZhang(itemValue);
					model.addAttribute("fuZhiDuiZhang","show");
				}
				if (columnName.equals("所长")){
					workflowItem.setSuoZhang(itemValue);
					model.addAttribute("suoZhang","show");
				}
				if (columnName.equals("教导员")){
					workflowItem.setJiaoDaoYuan(itemValue);
					model.addAttribute("jiaoDaoYuan","show");
				}
				if (columnName.equals("政委")){
					workflowItem.setZhengWei(itemValue);
					model.addAttribute("zhengWei","show");
				}
				if (columnName.equals("处长")){
					workflowItem.setChuZhang(itemValue);
					model.addAttribute("chuZhang","show");
				}
				if (columnName.equals("支队长")){
					workflowItem.setZhiDuiZhang(itemValue);
					model.addAttribute("zhiDuiZhang","show");
				}
				if (columnName.equals("大队长")){
					workflowItem.setDaDuiZhang(itemValue);
					model.addAttribute("daDuiZhang","show");
				}
				if (columnName.equals("主席")){
					workflowItem.setZhuXi(itemValue);
					model.addAttribute("zhuXi","show");
				}
				if (columnName.equals("分管领导")){
					workflowItem.setFenGuanLingDao(itemValue);
					model.addAttribute("fenGuanLingDao","show");
				}

				if (columnName.contains("公安处")){
					workflowItem.setGongAnChu(itemValue);
					model.addAttribute("gongAnChu","show");
				}
				if (columnName.contains("责任单位")){
					workflowItem.setZeRenDanWei(itemValue);
					model.addAttribute("zeRenDanWei","show");
				}
				if (columnName.equals("副主任")){
					workflowItem.setFuZhuRen(itemValue);
					model.addAttribute("fuZhuRen","show");
				}
				if (columnName.equals("主要领导")){
					workflowItem.setZhuYaoLingDao(itemValue);
					model.addAttribute("zhuYaoLingDao","show");
				}
				if (columnName.equals("其他副职")){
					workflowItem.setQiTaFuZhi(itemValue);
					model.addAttribute("qiTaFuZhi","show");
				}
				if (columnName.equals("副大队长")){
					workflowItem.setFuDaDuiZhang(itemValue);
					model.addAttribute("fuDaDuiZhang","show");
				}
				if (columnName.equals("包保支队领导")){
					workflowItem.setBaoBaoZhiDui(itemValue);
					model.addAttribute("baoBaoZhiDui","show");
				}
				if (columnName.equals("其他领导")){
					workflowItem.setQiTaLingDao(itemValue);
					model.addAttribute("qiTaLingDao","show");
				}
				if (columnName.equals("本人")){
					workflowItem.setBenRen(itemValue);
					model.addAttribute("benRen","show");
				}
				if (StringUtils.isBlank(workflowItem.getSpecial())) {
					if ( columnName.equals("职务") || columnName.equals("加扣分") || columnName.equals("扣分") ||
							columnName.equals("项目") || columnName.equals("相关业务") || columnName.equals("类别") || columnName.equals("备注") ||
							columnName.equals("岗位")) {
						workflowItem.setSpecial(null);
					}
					if (columnName.equals("本人") || columnName.equals("主任") || columnName.equals("书记") || columnName.contains("主管领导") || columnName.equals("处长") ||
							columnName.equals("副处长") || columnName.equals("副职") || columnName.equals("副支队长") || columnName.equals("所长") ||
							columnName.equals("所长") || columnName.equals("支队长") || columnName.equals("大队长") || columnName.equals("主席") ||
							columnName.equals("分管领导") || columnName.contains("公安处") || columnName.contains("责任单位") || columnName.equals("副主任") ||
							columnName.equals("主要领导") || columnName.equals("其他副职") || columnName.equals("副大队长") || columnName.equals("包保支队领导") ||
							columnName.equals("其他领导")) {
						workflowItem.setSpecial("no");
					}
				}
				/*保存加扣分类别*/

			}
//			examWorkflowDatasService.save(examWorkflowDatas);
		});

		double sdfs = DateUtils.getTwoDateSecond(startDate,new Date());
		logger.error("查询所有考评标准时间-------------------------------->"+sdfs);
		List<ExamWorkflowDatas> valueTypeList = selectedList.stream().filter(item -> item.getValueType()==null).collect(Collectors.toList());
		if (valueTypeList != null && valueTypeList.size()>0){
			/*设置权重以便算分*/
			new Thread(new Runnable() {
				@Override
				public void run() {
					selectedList.stream().forEach(item ->{
						examWorkflowDatasService.save(item);
					});
				}
			}).start();
		}
		List<ExamWorkflowDatas> weightList = selectedList.stream().filter(item -> item.getWeight()==null).collect(Collectors.toList());
		if (weightList != null && weightList.size()>0){
			/*设置权重以便算分*/
			new Thread(new Runnable() {
				@Override
				public void run() {
					selectedList.stream().filter(item->item.getWeight()==null || StringUtils.isBlank(item.getWorkName())).forEach(item->setWeightBeta(item,examWorkflow.getExamType()));
				}
			}).start();
		}

/*		*//*查询推送过来的考评项*//*
        ExamWorkflowDatas temp = new ExamWorkflowDatas();
        temp.setIsAlreadySelected("99");
        if (StringUtils.isNotBlank(objId)) {
            temp.setObjId(objId);
        } else if (StringUtils.isNotBlank(fillPersonId)) {
            temp.setFillPersonId(fillPersonId);
        } else {
            temp.setFillPersonId(UserUtils.getUser().getId());
        }
        selectedList.addAll(examWorkflowDatasService.findList(temp));*/

		/*推送失败的重新选择后进行过滤*/
		List<ExamWorkflowDatas> workflowDatasList=selectedList.stream().filter(workflowDatas -> !workflowDatas.getIsSelected().equals("0")&& StringUtils.isNotBlank(workflowDatas.getStandardId())).collect(Collectors.toList());
		/*查询初核人*/
		List<Map<String,String>> noExamList = examWorkflowDatasService.findNoExamList(workflowId,fillPersonId);
		noExamList .removeAll(Collections.singleton(null)); //移除所有的null元素
		/*初核人为空则显示 选择审核人*/

		/*workflowDatasList.stream().forEach(examWorkflowDatas -> {
			//获取考评项数据
			Map<String, String> param = new HashMap<String, String>();
			param.put("examStardardId", examWorkflowDatas.getStandardId());
			List<Map<String, String>> rowList=examStandardTemplateDefineService.findTemplateDatas(param,new String[]{examWorkflowDatas.getRowId()});
			for (Map<String, String> item :rowList){
				switch (item.get("column_type")){
					case "10":
						*//*项目*//*
						examWorkflowDatas.setS1(item.get("item_value"));
						examWorkflowDatas.setProject(item.get("item_value"));
						break;
					case "4":
						*//*分类*//*
						examWorkflowDatas.setS2(item.get("item_value"));
						examWorkflowDatas.setType(item.get("item_value"));
						break;
					case "1":
						*//*评分标准*//*
						examWorkflowDatas.setS3(item.get("item_value"));
						break;
					case "2":
						*//*分值（+）*//*
						examWorkflowDatas.setS4(item.get("item_value"));
						examWorkflowDatas.setValueType(1);
						break;
					case "3":
						*//*分值（-）*//*
						examWorkflowDatas.setS5(item.get("item_value"));
						examWorkflowDatas.setValueType(-1);
						break;
					case "6":
						*//*备注*//*
						examWorkflowDatas.setS6(item.get("item_value"));
						break;
				}

			}
		});*/


		String nextStatus = String.valueOf(WorkFloatConstant.EXAM_END);
		model.addAttribute("status", examWorkflow.getStatus());
		if (examWorkflow.getStatus() < WorkFloatConstant.ALL_PUBLIC) {
			nextStatus = this.getNextStepStatus(segmentsList, examWorkflow.getStatus());
			model.addAttribute("nextStep", nextStatus);
		}
		String NNextStep = this.getNextStepStatus(segmentsList, Integer.parseInt(nextStatus));
		/*设置下一环节的推送人  不是系统公示就设置下一步，系统公示的时候初审人员修正，推送到下下一步*/
		if (nextStatus.equals(String.valueOf(WorkFloatConstant.EXAM_PUBLIC))) {
			this.setPersonModel(NNextStep, examWorkflow.getExamType(), workflowDatasList, model);
		} else {
			this.setPersonModel(nextStatus, examWorkflow.getExamType(), workflowDatasList, model);
		}
		model.addAttribute("nnextStep", NNextStep);
			String history = request.getParameter("history");

			/*当考评一个考评对象推动到下一个流程时 ， 使用history来控制 提交按钮是否显示*/
		status = examWorkflowDatasService.getStatusById(fillPersonId,workflowId,status,UserUtils.getUser().getId());
			if (selectedList != null && selectedList.size()>0 && String.valueOf(status).equals(nextStatus) ){
				history ="true";
			}
		if (null != history) {
			model.addAttribute("history", history);
			Map<String, String> sParam = new HashMap<String, String>();
			String personId = UserUtils.getUser().getId();
			sParam.put("id", examWorkflow.getId());
			sParam.put("personId", personId);
			List<Map<String, String>> datasList = examWorkflowService.selectByPerson(sParam);
			if (examWorkflow.getStatus() == WorkFloatConstant.EXAM_PUBLIC || examWorkflow.getStatus() == WorkFloatConstant.COMPLET_SIGN) {
				model.addAttribute("isSave", String.valueOf(this.isSave(examWorkflow, datasList, personId)));
			}
		}
		String[] columnList =new String[] {"9","4","1","2","3","6"};
		/*查询已经保存的所有的考评项 */
		model.addAttribute("columnList",columnList);

		/*查询推送失败，添加的考评项*/
        ExamWorkflowDatas temp = new ExamWorkflowDatas();
		temp.setIsSelected("1");
        temp.setWorkflowId(workflowId);
        temp.setIsAlreadySelected("99");
        if (StringUtils.isNotBlank(objId)) {
            temp.setObjId(objId);
        } else if (StringUtils.isNotBlank(fillPersonId)) {
            temp.setFillPersonId(fillPersonId);
        } else {
            temp.setFillPersonId(UserUtils.getUser().getId());
        }
		Page<ExamWorkflowDatas> pageParam = new Page<ExamWorkflowDatas>(request,response,-1);
		pageParam.setOrderBy("id");
		List<ExamWorkflowDatas> pushList = new ArrayList<>();
		if (StringUtils.isBlank(noTree)){
			/*查询审核的考评项*/
			temp.setExamPersonId(userId);
			temp.setDepartSignId(userId);
			temp.setPartBureausSignId(userId);
			temp.setAdjustPersonId(userId);
			temp.setMainBureausSignId(userId);
			pushList = examWorkflowDatasService.findAboutMeList(temp);
		}else {
			/*noTree 不为空时查看考评对象的所有数据*/
			pushList = examWorkflowDatasService.findList(temp);
		}
//		Page<ExamWorkflowDatas> pushExam = examWorkflowDatasService.findPage(pageParam,temp);
//        workflowDatasList.addAll(pushExam.getList().stream().filter(examWorkflowDatas -> examWorkflowDatas.getStandardId()!=null).collect(Collectors.toList()));
		workflowDatasList.addAll(pushList);
		if (workflowDatasList== null || workflowDatasList.size()==0){

			if (noExamList == null || noExamList.size() == 0){

			}else if (noExamList.size()==1) {
				/*选择项为空 并且初核人只有一个则显示初核人*/
				String examPerson = noExamList.get(0).get("examperson");
				model.addAttribute("person",examPerson);
				model.addAttribute("personId",noExamList.get(0).get("exampersonid"));
			}else {
				/*未选择 但是审核人有多个时无法显示*/
				model.addAttribute("noSelect",false);
			}
			model.addAttribute("noSelect",true);
		}
		model.addAttribute("workflowDatasList",workflowDatasList);
		/*根据状态跳转到不同的页面*/
		status = examWorkflowDatasService.getStatusById(fillPersonId,workflowId,status,UserUtils.getUser().getId());
		if (WorkFloatConstant.NO_START != status && WorkFloatConstant.EXAM_END != status) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("flowId", examWorkflow.getId());
			param.put("DEL_FLAG_NORMAL", "0");
			List<Map<String, Object>> list = examWorflowSegmentsService.findSegmentsList(param);
			if (WorkFloatConstant.SELF_EXAM == status && this.isExamRole(UserUtils.getUser().getRoleList(), examWorkflow.getExamType())) {
				Map<String, Object> objParam = new HashMap<String, Object>();
				objParam.put("workflowId", examWorkflow.getId());
//                List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
//                model.addAttribute("objList", objList);
				List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjDataList(objParam);
				List<Map<String, Object>> resultObjList = this.checkObjectList(objList);
				model.addAttribute("objList", resultObjList);
				model.addAttribute("processType", "appraiseExam");



				targetUrl = "modules/exam/appraise_simple";
			} else if (WorkFloatConstant.SELF_EXAM == status) {
//				ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
//				String standardIds = examWorkflowDefine.getTemplatesIds();
//				List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoList(standardIds, examWorkflow, UserUtils.getUser().getId());
				model.addAttribute("processType", "selefAppraise");
				model.addAttribute("standardIds", standardIds);
				model.addAttribute("standardInfoList", standardInfoList);
				targetUrl = "modules/exam/appraiseBeta";
               /* String objId = "";
                String objName = "";
                if (null != request.getParameter("objId")) {
                    objId = request.getParameter("objId");
                    objName = request.getParameter("objName");
                }
                model.addAttribute("objId", objId);
                model.addAttribute("objName", objName);
                targetUrl = "modules/exam/appraiseBeta";*/
			} else if (WorkFloatConstant.SIMPLE_EXAM == status || (this.isExamRole(UserUtils.getUser().getRoleList(), examWorkflow.getExamType()) && WorkFloatConstant.EXAM_PUBLIC == status)) {
				Map<String, Object> objParam = new HashMap<String, Object>();
				objParam.put("workflowId", examWorkflow.getId());
				//objParam.put("group", "personAndStandId");
				//List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
				List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjDataList(objParam);
				List<Map<String, Object>> resultObjList = this.checkObjectList(objList);
				model.addAttribute("objList", resultObjList);
				model.addAttribute("processType", "appraiseExam");
				targetUrl = "modules/exam/appraiseBeta";
				/*初步审核页面*/
				targetUrl = "modules/exam/appraiseExamBeta";
//				targetUrl = "modules/exam/appraiseExamIndexBeta";
			} else if (WorkFloatConstant.EXAM_PUBLIC == status) {
				Map<String, Object> objParam = new HashMap<String, Object>();

				objParam.put("workflowId", examWorkflow.getId());
				//系统公示显示分数
				objParam.put("fillPersonId",fillPersonId);//自评人id
				List<Map<String, Object>> objDatasList = examWorkflowDatasService.getAppraiseObjDataList2(objParam);
				List<Map<String, Object>> objScoreList = examWorkflowDatasService.getAppraiseObjScoreList2(objDatasList,workflowId,fillPersonId,examWorkflow);
				model.addAttribute("objScoreList", this.getCurrentObjs(objScoreList));
				//List<Map<String, Object>> objDatasList = examWorkflowDatasService.getAppraiseObjDataList(objParam);
				//<Map<String, Object>> objScoreList = examWorkflowDatasService.getAppraiseObjScoreList(objDatasList);
				//model.addAttribute("objScoreList", this.getCurrentObjs(objScoreList));
				if(objScoreList!=null && objScoreList.size()>0 && objScoreList.get(0).get("addWorkItemScore")!=null){
					model.addAttribute("isAddWorkItemExam","isAddWorkItemExam");
				}
				objParam.put("workflowId", examWorkflow.getId());
				List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
				List<Map<String, Object>> resObjList = new ArrayList<Map<String, Object>>();
				String personId = UserUtils.getUser().getId();
				//绩效考评员
				Set<String> examSet = new HashSet<String>();
				examSet.add("cca66e1339f14799b01f6db43ed16e16");
				examSet.add("978958003ea44a4bba3eed8ee6ceff3c");
				examSet.add("46c521bf67e24db28772b3eac52dc454");
				examSet.add("6af0e615e9b0477da82eff06ff532c8b");
				if (null != objList) {
					for (Map<String, Object> obj : objList) {
						if (personId.equals(obj.get("id")) || examSet.contains(personId)) {
							resObjList.add(obj);
						}
					}
				}
				model.addAttribute("objList", resObjList);
				model.addAttribute("examTypePublic", examWorkflow.getExamType());//考评类型 1 局考处 2 局考局机关 3 处考队所 4 处考处机关 5 处领导 6 中基层 7民警
				/*局考处的页面*/
				if (examWorkflow.getExamType().equals("1")){
					Map<String, Object> resultMap = examWorkflowDatasService.getPublicBetaScoreList(workflowId,fillPersonId);
					// Map<String, Object> map = examUnitAllPublicService.getScoreList1(workflowId);
					model.addAttribute("unitList", resultMap.get("unitList"));
					model.addAttribute("weigthsList", resultMap.get("weigthsList"));
					model.addAttribute("conventionScoreList", resultMap.get("conventionScoreList"));
					if (fillPersonId.equals(UserUtils.getUser().getId())){
						/*公示时 自评人员只能查看，显示列表*/
						model.addAttribute("isFillPerson", resultMap.get("isFillPerson"));
						targetUrl = "modules/exam/publicListJuKaoChuBeta";
					}else {

						targetUrl = "modules/exam/publicJuKaoChuBeta";
					}
				}else {
					/*其他页面*/
					if (fillPersonId.equals(UserUtils.getUser().getId())){
						/*公示时 自评人员只能查看，显示列表*/
						targetUrl = "modules/exam/publicListBeta";
					}else {
						targetUrl = "modules/exam/publicBeta";
					}
				}

			} else if (WorkFloatConstant.DEP_SIGN == status || WorkFloatConstant.PART_SIGN == status || WorkFloatConstant.COMPLET_SIGN == status) {
//				ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
//				String standardIds = examWorkflowDefine.getTemplatesIds();
//				List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoList(standardIds, examWorkflow, UserUtils.getUser().getId());
				model.addAttribute("standardInfoList", standardInfoList);
				Map<String, Object> objParam = new HashMap<String, Object>();
				objParam.put("workflowId", examWorkflow.getId());
				objParam.put("group", "obj");
				List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
				model.addAttribute("objList", objList);
				if (null != request.getParameter("objId")) {
					model.addAttribute("objId", request.getParameter("objId"));
				} else if (null != objList) {
					model.addAttribute("objId", objList.get(0).get("id"));
				}
				targetUrl = "modules/exam/signBeta";

				String processType = "";
				if (WorkFloatConstant.DEP_SIGN == status) {
					processType = "departSign";
                    targetUrl = "modules/exam/signContent2Beta";
				} else if (WorkFloatConstant.PART_SIGN == status) {
					processType = "partBureausSign";
                    targetUrl = "modules/exam/signContentPart2Beta";
				} else if (WorkFloatConstant.COMPLET_SIGN == status) {
					processType = "mainBureausSign";
                    targetUrl = "modules/exam/signContentMain2Beta";
				}
				model.addAttribute("processType", processType);
//				targetUrl = "modules/exam/sign";
			} else if (WorkFloatConstant.GROUP_ADJUST == status) {
//				ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
//				String standardIds = examWorkflowDefine.getTemplatesIds();
//				List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoList(standardIds, examWorkflow, UserUtils.getUser().getId());
				model.addAttribute("standardInfoList", standardInfoList);
				Map<String, Object> objParam = new HashMap<String, Object>();
				objParam.put("workflowId", examWorkflow.getId());
				objParam.put("group", "obj");
				List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
				model.addAttribute("objList", objList);
//				String objId = "";
				String objName = "";
				if (null != request.getParameter("objId")) {
					objId = request.getParameter("objId");
					objName = request.getParameter("objName");
				} else {
					User user = UserUtils.get(fillPersonId);
					objId = user.getCompany().getId();
					objName = user.getCompany().getName();
				}
				model.addAttribute("objId", objId);
				model.addAttribute("objName", objName);
				model.addAttribute("processType", "groupAdjust");
//				targetUrl = "modules/exam/appraise_adjust_leader";
				targetUrl = "modules/exam/appraiseAdjustLeaderBeta";
			} else if (WorkFloatConstant.ALL_PUBLIC == status) {
				if (StringUtils.isNotBlank(publicDetail)&& publicDetail.equals("true")){
					targetUrl = "modules/exam/publicDetailBeta";
				}else {
					targetUrl = this.allPublic(examWorkflow, model, request, response);
				}
			} else {
				targetUrl = "modules/exam/examFlowList";
			}
			model.addAttribute("workflowId", examWorkflow.getId());
			model.addAttribute("status", status);
			model.addAttribute("segmentsList", list);
			model.addAttribute("personType", request.getParameter("personType"));
//			String nextStatus = String.valueOf(WorkFloatConstant.EXAM_END);
			if (status < WorkFloatConstant.ALL_PUBLIC) {
				nextStatus = this.getNextStepStatus(segmentsList, examWorkflow.getStatus());
			}
			model.addAttribute("nextStep", nextStatus);
			model.addAttribute("hisCurStatus", String.valueOf(status));
		}
		if (WorkFloatConstant.ALL_PUBLIC != status){
			//最终结果公示不显示
			//标题头部分  得分  11.18 kevin.jia
			Map<String,Object> headItemScoreMap = examWorkflowDatasService.getHeadItemScore(examWorkflow,fillPersonId);
//			Map<String,Object> headItemScoreMap = examWorkflowDatasService.getHeadItemScore2(examWorkflow,fillPersonId);
			model.addAttribute("headItemScoreMap",headItemScoreMap);
		}

		/*初核人员*/
		if (workflowDatasList!= null && workflowDatasList.size()>0){
			String examPersonId = workflowDatasList.get(0).getExamPersonId();
			model.addAttribute("examPersonId",examPersonId);
		}
		return targetUrl;
	}


	/**
	 * 此页面仅供绩效办查看
	 * @param redirectAttributes
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "examBetaView")
	public String examBetaView(RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model) {
		String fillPersonId = request.getParameter("fillPersonId");
		String workflowId = request.getParameter("examWorkflowId");
		/*全局公示时查看详细*/
		String publicDetail = request.getParameter("publicDetail");
		/*被考评对象查看时 noTree 为true*/
		String noTree = request.getParameter("noTree");
		model.addAttribute("noTree",noTree);
		String userId = UserUtils.getUser().getId();

		ExamWorkflow examWorkflow =examWorkflowService.get(workflowId);

		Map<String, Object> segmentsParam = new HashMap<String, Object>();
		segmentsParam.put("flowId", examWorkflow.getId());
		segmentsParam.put("DEL_FLAG_NORMAL", "0");
		List<Map<String, Object>> segmentsList = examWorflowSegmentsService.findSegmentsList(segmentsParam);
		model.addAttribute("segmentsList", segmentsList);

		String targetUrl = "modules/exam/examFlowList";
		int status = examWorkflow.getStatus();
		ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
		String standardIds = examWorkflowDefine.getTemplatesIds();

//		List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoList(standardIds, examWorkflow, userId);
		/*自评时，无左侧树机构，fillPersonId为空，设置为登录用户的Id*/
		if (StringUtils.isBlank(fillPersonId)){
			fillPersonId = UserUtils.getUser().getId();
		}
		List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoListBeta(standardIds, examWorkflow, fillPersonId);
		model.addAttribute("processType", "selfAppraise");
		model.addAttribute("standardIds", standardIds);
		model.addAttribute("workflowId", examWorkflow.getId());
		model.addAttribute("standardInfoList", standardInfoList);
		model.addAttribute("examType", examWorkflow.getExamType());

		targetUrl = "modules/exam/appraiseBeta";
		model.addAttribute("examWorkflow", examWorkflow);
		model.addAttribute("examWorkflowDefine", examWorkflowDefine);


//        model.addAttribute("fillPersonId",examWorkflowDatas.getFillPersonId());

//        standardInfoList.get(0).getObj();
		List<ExamWorkflowDatas> selectedList =new ArrayList<>();

		/*查询出所有的考评项*/
		String objId = request.getParameter("objId");
		for (ExamStandardBaseInfo sd:standardInfoList){
			if (sd == null){
				continue;
			}
			ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
			examWorkflowDatas.setWorkflowId(examWorkflow.getId());
			examWorkflowDatas.setStandardId(sd.getId());
			/*局考处使用objId，其他使用fillPersonnelId*/
        if (StringUtils.isNotBlank(objId)) {
            examWorkflowDatas.setObjId(objId);
			noTree="true";
        } else if (StringUtils.isNotBlank(fillPersonId)) {
            examWorkflowDatas.setFillPersonId(fillPersonId);
        } else {
            examWorkflowDatas.setFillPersonId(userId);
        }
			model.addAttribute("fillPersonId",examWorkflowDatas.getFillPersonId());

        /*再加上系统推送过来的考评项  条件 推送失败的*/

//        standardInfoList.get(0).getObj();
			List<ExamWorkflowDatas> list;
			/*noTree为空时，查看当前用户审核的数据,不为空时，查看自己自评的所有数据*/
			if (StringUtils.isNotBlank(examWorkflowDatas.getFillPersonId()) && examWorkflowDatas.getFillPersonId().equals(userId)){
				noTree = "true";
			}
			/*绩效办看所有，不是自己相关的不能操作*/
			if (userId.equals("6af0e615e9b0477da82eff06ff532c8b")|| userId.equals("46c521bf67e24db28772b3eac52dc454")||
			userId.equals("978958003ea44a4bba3eed8ee6ceff3c")|| userId.equals("cca66e1339f14799b01f6db43ed16e16")){
				noTree="true";
			}
			if (StringUtils.isBlank(noTree)){
				/*查询审核的考评项*/
			examWorkflowDatas.setExamPersonId(userId);
			examWorkflowDatas.setDepartSignId(userId);
			examWorkflowDatas.setPartBureausSignId(userId);
			examWorkflowDatas.setAdjustPersonId(userId);
			examWorkflowDatas.setMainBureausSignId(userId);
			list = examWorkflowDatasService.findAboutMeList(examWorkflowDatas);
			}else {
				/*noTree 不为空时查看考评对象的所有数据*/
			 list = examWorkflowDatasService.findList(examWorkflowDatas);
			}
			selectedList.addAll(list.stream().filter(item -> !item.getIsSelected().equals("0")).collect(Collectors.toList()));
		}
		selectedList.stream().forEach(examWorkflowDatas -> {
			//获取考评项数据
			Map<String, String> param = new HashMap<String, String>();
			param.put("examStardardId", examWorkflowDatas.getStandardId());
			List<Map<String, String>> rowList=examStandardTemplateDefineService.findTemplateDatas(param,new String[]{examWorkflowDatas.getRowId()});
			for (Map<String, String> item :rowList){
				String itemValue = item.get("item_value");
				String columnName = item.get("column_name");
				if (StringUtils.isBlank(columnName)){
					columnName="";
				}
				switch (item.get("column_type")){
					case "10":
						/*项目*/
						examWorkflowDatas.setS1(itemValue);
						examWorkflowDatas.setProject(itemValue);
						break;
					case "4":
						/*分类*/
						examWorkflowDatas.setS2(itemValue);
						examWorkflowDatas.setType(itemValue);
						break;
					case "1":
						/*评分标准*/
						examWorkflowDatas.setS3(itemValue);
						break;
					case "2":
						/*分值（+）*/
						examWorkflowDatas.setS4(itemValue);
						examWorkflowDatas.setValueType(1);
						break;
					case "3":
						/*分值（-）*/
						examWorkflowDatas.setS5(itemValue);
						examWorkflowDatas.setValueType(-1);
						break;
					case "6":
						/*备注*/
						examWorkflowDatas.setS6(itemValue);
						break;
				}
				if (columnName.equals("主任")){
					examWorkflowDatas.setZhuRen(itemValue);
					model.addAttribute("zhuRen","show");
				}
				if (columnName.equals("书记")){
					examWorkflowDatas.setShuJi(itemValue);
					model.addAttribute("shuJi","show");
				}
				if (columnName.contains("主管领导")){
					examWorkflowDatas.setZhuGuanLingDao(itemValue);
					model.addAttribute("zhuGuanLingDao","show");
				}
				if (columnName.equals("处长")){
					examWorkflowDatas.setChuZhang(itemValue);
					model.addAttribute("chuZhang","show");
				}
				if (columnName.equals("副处长")){
					examWorkflowDatas.setFuChuZhang(itemValue);
					model.addAttribute("fuChuZhang","show");
				}
				if (columnName.equals("副职")){
					examWorkflowDatas.setFuZhi(itemValue);
					model.addAttribute("fuZhi","show");
				}
				if (columnName.equals("副所长")){
					examWorkflowDatas.setFuSuoZhang(itemValue);
					model.addAttribute("fuSuoZhang","show");
				}
				if (columnName.equals("副支队长")){
					examWorkflowDatas.setFuZhiDuiZhang(itemValue);
					model.addAttribute("fuZhiDuiZhang","show");
				}
				if (columnName.equals("所长")){
					examWorkflowDatas.setSuoZhang(itemValue);
					model.addAttribute("suoZhang","show");
				}
				if (columnName.equals("教导员")){
					examWorkflowDatas.setJiaoDaoYuan(itemValue);
					model.addAttribute("jiaoDaoYuan","show");
				}
				if (columnName.equals("政委")){
					examWorkflowDatas.setZhengWei(itemValue);
					model.addAttribute("zhengWei","show");
				}
				if (columnName.equals("处长")){
					examWorkflowDatas.setChuZhang(itemValue);
					model.addAttribute("chuZhang","show");
				}
				if (columnName.equals("支队长")){
					examWorkflowDatas.setZhiDuiZhang(itemValue);
					model.addAttribute("zhiDuiZhang","show");
				}
				if (columnName.equals("大队长")){
					examWorkflowDatas.setDaDuiZhang(itemValue);
					model.addAttribute("daDuiZhang","show");
				}
				if (columnName.equals("主席")){
					examWorkflowDatas.setZhuXi(itemValue);
					model.addAttribute("zhuXi","show");
				}
				if (columnName.equals("分管领导")){
					examWorkflowDatas.setFenGuanLingDao(itemValue);
					model.addAttribute("fenGuanLingDao","show");
				}

				if (columnName.contains("公安处")){
					examWorkflowDatas.setGongAnChu(itemValue);
					model.addAttribute("gongAnChu","show");
				}
				if (columnName.contains("责任单位")){
					examWorkflowDatas.setZeRenDanWei(itemValue);
					model.addAttribute("zeRenDanWei","show");
				}
				if (columnName.equals("副主任")){
					examWorkflowDatas.setFuZhuRen(itemValue);
					model.addAttribute("fuZhuRen","show");
				}
				if (columnName.equals("主要领导")){
					examWorkflowDatas.setZhuYaoLingDao(itemValue);
					model.addAttribute("zhuYaoLingDao","show");
				}
				if (columnName.equals("其他副职")){
					examWorkflowDatas.setQiTaFuZhi(itemValue);
					model.addAttribute("qiTaFuZhi","show");
				}
				if (columnName.equals("副大队长")){
					examWorkflowDatas.setFuDaDuiZhang(itemValue);
					model.addAttribute("fuDaDuiZhang","show");
				}
				if (columnName.equals("包保支队领导")){
					examWorkflowDatas.setBaoBaoZhiDui(itemValue);
					model.addAttribute("baoBaoZhiDui","show");
				}
				if (columnName.equals("其他领导")){
					examWorkflowDatas.setQiTaLingDao(itemValue);
					model.addAttribute("qiTaLingDao","show");
				}
				if (columnName.equals("本人")){
					examWorkflowDatas.setBenRen(itemValue);
					model.addAttribute("benRen","show");
				}
				if (StringUtils.isBlank(examWorkflowDatas.getSpecial())) {
					if (columnName.equals("职务") || columnName.equals("加扣分") || columnName.equals("扣分") ||
							columnName.equals("项目") || columnName.equals("相关业务") || columnName.equals("类别") || columnName.equals("备注") ||
							columnName.equals("岗位")) {
						examWorkflowDatas.setSpecial(null);
					}
					if (columnName.equals("本人") || columnName.equals("主任") || columnName.equals("书记") || columnName.contains("主管领导") || columnName.equals("处长") ||
							columnName.equals("副处长") || columnName.equals("副职") || columnName.equals("副支队长") || columnName.equals("所长") ||
							columnName.equals("所长") || columnName.equals("支队长") || columnName.equals("大队长") || columnName.equals("主席") ||
							columnName.equals("分管领导") || columnName.contains("公安处") || columnName.contains("责任单位") || columnName.equals("副主任") ||
							columnName.equals("主要领导") || columnName.equals("其他副职") || columnName.equals("副大队长") || columnName.equals("包保支队领导") ||
							columnName.equals("其他领导")) {
						examWorkflowDatas.setSpecial("no");
					}
				}
			}
		});
		/*设置权重以便算分*/
		new Thread(new Runnable() {
			@Override
			public void run() {
				selectedList.stream().filter(item->item.getWeight()==null || StringUtils.isBlank(item.getWorkName())).forEach(item->setWeightBeta(item,examWorkflow.getExamType()));
			}
		}).start();



		/*推送失败的重新选择后进行过滤*/
		List<ExamWorkflowDatas> workflowDatasList=selectedList.stream().filter(workflowDatas -> !workflowDatas.getIsSelected().equals("0")&& StringUtils.isNotBlank(workflowDatas.getStandardId())).collect(Collectors.toList());

		String nextStatus = String.valueOf(WorkFloatConstant.EXAM_END);
		model.addAttribute("status", examWorkflow.getStatus());
		if (examWorkflow.getStatus() < WorkFloatConstant.ALL_PUBLIC) {
			nextStatus = this.getNextStepStatus(segmentsList, examWorkflow.getStatus());
			model.addAttribute("nextStep", nextStatus);
		}
		String NNextStep = this.getNextStepStatus(segmentsList, Integer.parseInt(nextStatus));
		/*设置下一环节的推送人  不是系统公示就设置下一步，系统公示的时候初审人员修正，推送到下下一步*/
		if (nextStatus.equals(String.valueOf(WorkFloatConstant.EXAM_PUBLIC))) {
			this.setPersonModel(NNextStep, examWorkflow.getExamType(), workflowDatasList, model);
		} else {
			this.setPersonModel(nextStatus, examWorkflow.getExamType(), workflowDatasList, model);
		}
		model.addAttribute("nnextStep", NNextStep);
			String history = request.getParameter("history");

			/*当考评一个考评对象推动到下一个流程时 ， 使用history来控制 提交按钮是否显示*/
			if (selectedList != null && selectedList.size()>0 && String.valueOf(selectedList.get(0).getStatus()).equals(nextStatus) ){
				history ="true";
			}
		if (null != history) {
			model.addAttribute("history", history);
			Map<String, String> sParam = new HashMap<String, String>();
			String personId = UserUtils.getUser().getId();
			sParam.put("id", examWorkflow.getId());
			sParam.put("personId", personId);
			List<Map<String, String>> datasList = examWorkflowService.selectByPerson(sParam);
			if (examWorkflow.getStatus() == WorkFloatConstant.EXAM_PUBLIC || examWorkflow.getStatus() == WorkFloatConstant.COMPLET_SIGN) {
				if (datasList != null && datasList.size()>0)
				model.addAttribute("isSave", String.valueOf(this.isSave(examWorkflow, datasList, personId)));
			}
		}
		String[] columnList =new String[] {"9","4","1","2","3","6"};
		/*查询已经保存的所有的考评项 */
		model.addAttribute("columnList",columnList);

		/*查询推送失败，添加的考评项*/
        ExamWorkflowDatas temp = new ExamWorkflowDatas();
        /*查看环节详情优化*/
        temp.setWorkflowId(workflowId);
        temp.setIsAlreadySelected("99");
        if (StringUtils.isNotBlank(objId)) {
            temp.setObjId(objId);
        } else if (StringUtils.isNotBlank(fillPersonId)) {
            temp.setFillPersonId(fillPersonId);
        } else {
            temp.setFillPersonId(UserUtils.getUser().getId());
        }
		Page<ExamWorkflowDatas> pageParam = new Page<ExamWorkflowDatas>(request,response,-1);
		pageParam.setOrderBy("id");
//		Page<ExamWorkflowDatas> pushExam = examWorkflowDatasService.findPage(pageParam,temp);
		List<ExamWorkflowDatas> pushList = new ArrayList<>();
		if (StringUtils.isBlank(noTree)){
			/*查询审核的考评项*/
			temp.setExamPersonId(userId);
			temp.setDepartSignId(userId);
			temp.setPartBureausSignId(userId);
			temp.setAdjustPersonId(userId);
			temp.setMainBureausSignId(userId);
			pushList = examWorkflowDatasService.findAboutMeList(temp);
		}else {
			/*noTree 不为空时查看考评对象的所有数据*/
			pushList = examWorkflowDatasService.findList(temp);
		}
//        workflowDatasList.addAll(pushExam.getList().stream().filter(examWorkflowDatas -> examWorkflowDatas.getStandardId()!=null).collect(Collectors.toList()));
		workflowDatasList.addAll(pushList);
		model.addAttribute("workflowDatasList",workflowDatasList);
		/*根据状态跳转到不同的页面*/
 		status = examWorkflowDatasService.getStatusById(fillPersonId,workflowId,status,UserUtils.getUser().getId());
		if (WorkFloatConstant.ALL_PUBLIC != status){
			//最终结果公示不显示
			//标题头部分  得分  11.18 kevin.jia
			Map<String,Object> headItemScoreMap = examWorkflowDatasService.getHeadItemScore(examWorkflow,fillPersonId);
//			Map<String,Object> headItemScoreMap = examWorkflowDatasService.getHeadItemScore2(examWorkflow,fillPersonId);
			model.addAttribute("headItemScoreMap",headItemScoreMap);
		}

		/*初核人员*/
		if (workflowDatasList!= null && workflowDatasList.size()>0){
			String examPersonId = workflowDatasList.get(0).getExamPersonId();
			model.addAttribute("examPersonId",examPersonId);
		}
        model.addAttribute("hisCurStatus", String.valueOf(status));
		targetUrl = "modules/exam/examBetaChu";


		if (WorkFloatConstant.SELF_EXAM == status && this.isExamRole(UserUtils.getUser().getRoleList(), examWorkflow.getExamType())) {

		} else if (WorkFloatConstant.SELF_EXAM == status) {
//
		} else if (WorkFloatConstant.SIMPLE_EXAM == status || (this.isExamRole(UserUtils.getUser().getRoleList(), examWorkflow.getExamType()) && WorkFloatConstant.EXAM_PUBLIC == status)) {

		} else if (WorkFloatConstant.EXAM_PUBLIC == status) {
			//系统公示显示分数
			Map<String, Object> objParam = new HashMap<String, Object>();
			objParam.put("workflowId", examWorkflow.getId());
			objParam.put("fillPersonId",fillPersonId);//自评人id
			List<Map<String, Object>> objDatasList = examWorkflowDatasService.getAppraiseObjDataList2(objParam);
			List<Map<String, Object>> objScoreList = examWorkflowDatasService.getAppraiseObjScoreList2(objDatasList,workflowId,fillPersonId,examWorkflow);
			model.addAttribute("objScoreList", objScoreList);
			//List<Map<String, Object>> objDatasList = examWorkflowDatasService.getAppraiseObjDataList(objParam);
			//List<Map<String, Object>> objScoreList = examWorkflowDatasService.getAppraiseObjScoreList(objDatasList);
			//model.addAttribute("objScoreList", this.getCurrentObjs(objScoreList));
			if(objScoreList!=null && objScoreList.size()>0 && objScoreList.get(0).get("addWorkItemScore")!=null){
				model.addAttribute("isAddWorkItemExam","isAddWorkItemExam");
			}
			objParam.put("workflowId", examWorkflow.getId());
			List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
			List<Map<String, Object>> resObjList = new ArrayList<Map<String, Object>>();
			String personId = UserUtils.getUser().getId();
			//绩效考评员
			Set<String> examSet = new HashSet<String>();
			examSet.add("cca66e1339f14799b01f6db43ed16e16");
			examSet.add("978958003ea44a4bba3eed8ee6ceff3c");
			examSet.add("46c521bf67e24db28772b3eac52dc454");
			examSet.add("6af0e615e9b0477da82eff06ff532c8b");
			if (null != objList) {
				for (Map<String, Object> obj : objList) {
					if (personId.equals(obj.get("id")) || examSet.contains(personId)) {
						resObjList.add(obj);
					}
				}
			}
			model.addAttribute("objList", resObjList);



//				targetUrl = "modules/exam/public";
			model.addAttribute("examTypePublic", examWorkflow.getExamType());//考评类型 1 局考处 2 局考局机关 3 处考队所 4 处考处机关 5 处领导 6 中基层 7民警
			/*局考处的页面*/
			if (examWorkflow.getExamType().equals("1")){
				Map<String, Object> resultMap = examWorkflowDatasService.getPublicBetaScoreList(workflowId,fillPersonId);
				// Map<String, Object> map = examUnitAllPublicService.getScoreList1(workflowId);
				model.addAttribute("unitList", resultMap.get("unitList"));
				model.addAttribute("weigthsList", resultMap.get("weigthsList"));
				model.addAttribute("conventionScoreList", resultMap.get("conventionScoreList"));
				if (fillPersonId.equals(UserUtils.getUser().getId())){
					/*公示时 自评人员只能查看，显示列表*/
					model.addAttribute("isFillPerson", resultMap.get("isFillPerson"));
					targetUrl = "modules/exam/publicListJuKaoChuBeta";
				}else {
					targetUrl = "modules/exam/publicJuKaoChuBetaView";
				}
			}else {
				/*其他页面*/
				if (fillPersonId.equals(UserUtils.getUser().getId())){
					/*公示时 自评人员只能查看，显示列表*/
					targetUrl = "modules/exam/publicListBeta";
				}else {
					targetUrl = "modules/exam/publicBetaView";
				}
			}
		} else if (WorkFloatConstant.ALL_PUBLIC == status) {
			if (StringUtils.isNotBlank(publicDetail)&& publicDetail.equals("true")){
				targetUrl = "modules/exam/publicDetailBeta";
			}else {
				targetUrl = this.allPublic(examWorkflow, model, request, response);
			}
		} else {
//			targetUrl = "modules/exam/examFlowList";
		}
		return targetUrl;
	}


	/**
	 * 根据被考评对象考评状态显示
	 * 		整体流程为自评，被考评对象状态为初核时，显示初核页面
	 * @param redirectAttributes
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "examBetaById")
	public String examBetaById(RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model) {
		String workflowType = request.getParameter("workflowType");
		String isEditExam = request.getParameter("isEdit");
		model.addAttribute("isEditExam",isEditExam);
		String fillPersonId = request.getParameter("fillPersonId");
		String workflowId = request.getParameter("examWorkflowId");
		String examType =request.getParameter("examType");
		/*全局公示时查看详细*/
		String publicDetail = request.getParameter("publicDetail");
		/*被考评对象查看时 noTree 为true*/
		String noTree = request.getParameter("noTree");
		model.addAttribute("noTree",noTree);
		String userId = UserUtils.getUser().getId();

		ExamWorkflow examWorkflow =examWorkflowService.get(workflowId);

		Map<String, Object> segmentsParam = new HashMap<String, Object>();
		segmentsParam.put("flowId", examWorkflow.getId());
		segmentsParam.put("DEL_FLAG_NORMAL", "0");
		List<Map<String, Object>> segmentsList = examWorflowSegmentsService.findSegmentsList(segmentsParam);
		model.addAttribute("segmentsList", segmentsList);

		String targetUrl = "modules/exam/examFlowList";
		int status = examWorkflow.getStatus();


		ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
		String standardIds = examWorkflowDefine.getTemplatesIds();

//		List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoList(standardIds, examWorkflow, userId);
		/*自评时，无左侧树机构，fillPersonId为空，设置为登录用户的Id*/
		if (StringUtils.isBlank(fillPersonId)){
			fillPersonId = UserUtils.getUser().getId();
		}

		/*流程状态根据当前考评对象的实际状态更新显示*/
		status = examWorkflowDatasService.getStatusById(fillPersonId,workflowId,status,UserUtils.getUser().getId());
		List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoListBeta(standardIds, examWorkflow, fillPersonId);
		model.addAttribute("processType", "selfAppraise");
		model.addAttribute("standardIds", standardIds);
		model.addAttribute("workflowId", examWorkflow.getId());
		model.addAttribute("standardInfoList", standardInfoList);
		model.addAttribute("examType", examWorkflow.getExamType());

		targetUrl = "modules/exam/appraiseBeta";
		model.addAttribute("examWorkflow", examWorkflow);
		model.addAttribute("examWorkflowDefine", examWorkflowDefine);


//        model.addAttribute("fillPersonId",examWorkflowDatas.getFillPersonId());

//        standardInfoList.get(0).getObj();
		List<ExamWorkflowDatas> selectedList =new ArrayList<>();

		/*查询出所有的考评项*/
		String objId = request.getParameter("objId");
//		for (ExamStandardBaseInfo sd:standardInfoList){
			ExamWorkflowDatas paramDatas = new ExamWorkflowDatas();
		paramDatas.setWorkflowId(examWorkflow.getId());
//			examWorkflowDatas.setStandardId(sd.getId());
			/*局考处使用objId，其他使用fillPersonnelId*/
			if (StringUtils.isNotBlank(objId)) {
				paramDatas.setObjId(objId);
				noTree="true";
			} else if (StringUtils.isNotBlank(fillPersonId)) {
				paramDatas.setFillPersonId(fillPersonId);
			} else {
				paramDatas.setFillPersonId(userId);
			}
			model.addAttribute("fillPersonId",paramDatas.getFillPersonId());

			/*再加上系统推送过来的考评项  条件 推送失败的*/

//        standardInfoList.get(0).getObj();
			List<ExamWorkflowDatas> tempList;
			/*noTree为空时，查看当前用户审核的数据,不为空时，查看自己自评的所有数据*/
			if (StringUtils.isNotBlank(paramDatas.getFillPersonId()) && paramDatas.getFillPersonId().equals(userId)){
				noTree = "true";
			}
			/*绩效办看所有，不是自己相关的不能操作*/
			if (userId.equals("6af0e615e9b0477da82eff06ff532c8b")|| userId.equals("46c521bf67e24db28772b3eac52dc454")||
					userId.equals("978958003ea44a4bba3eed8ee6ceff3c")|| userId.equals("cca66e1339f14799b01f6db43ed16e16")){
//				noTree="true";
			}
			if (StringUtils.isBlank(noTree)){
				/*查询审核的考评项*/
				paramDatas.setExamPersonId(userId);
				paramDatas.setDepartSignId(userId);
				paramDatas.setPartBureausSignId(userId);
				paramDatas.setAdjustPersonId(userId);
				paramDatas.setMainBureausSignId(userId);
				tempList = examWorkflowDatasService.findAboutMeList(paramDatas);
			}else {
				/*noTree 不为空时查看考评对象的所有数据*/
				tempList = examWorkflowDatasService.findList(paramDatas);
			}
			selectedList.addAll(tempList.stream().filter(item -> !item.getIsSelected().equals("0")).collect(Collectors.toList()));


		List<ExamWorkflowDatas> valueTypeList = selectedList.stream().filter(item -> item.getValueType()==null).collect(Collectors.toList());
		if (valueTypeList != null && valueTypeList.size()>0){
			/*设置权重以便算分*/
			new Thread(new Runnable() {
				@Override
				public void run() {
					selectedList.stream().forEach(item ->{
						examWorkflowDatasService.save(item);
					});
				}
			}).start();
		}
		List<ExamWorkflowDatas> weightList = selectedList.stream().filter(item -> item.getWeight()==null).collect(Collectors.toList());
		if (weightList != null && weightList.size()>0){
			/*设置权重以便算分*/
			new Thread(new Runnable() {
				@Override
				public void run() {
					selectedList.stream().filter(item->item.getWeight()==null || StringUtils.isBlank(item.getWorkName())).forEach(item->setWeightBeta(item,examWorkflow.getExamType()));
				}
			}).start();
		}
//		}

		/*		*//*查询推送过来的考评项*//*
        ExamWorkflowDatas temp = new ExamWorkflowDatas();
        temp.setIsAlreadySelected("99");
        if (StringUtils.isNotBlank(objId)) {
            temp.setObjId(objId);
        } else if (StringUtils.isNotBlank(fillPersonId)) {
            temp.setFillPersonId(fillPersonId);
        } else {
            temp.setFillPersonId(UserUtils.getUser().getId());
        }
        selectedList.addAll(examWorkflowDatasService.findList(temp));*/

		/*推送失败的重新选择后进行过滤*/
		List<ExamWorkflowDatas> workflowDatasList=selectedList.stream().filter(workflowDatas -> !workflowDatas.getIsSelected().equals("0") && StringUtils.isNotBlank(workflowDatas.getStandardId())).collect(Collectors.toList());
		workflowDatasList.stream().forEach(examWorkflowDatas -> {
			//获取考评项数据
			Map<String, String> param = new HashMap<String, String>();
			param.put("examStardardId", examWorkflowDatas.getStandardId());
			List<Map<String, String>> rowList=examStandardTemplateDefineService.findTemplateDatas(param,new String[]{examWorkflowDatas.getRowId()});
			for (Map<String, String> item :rowList){
				String itemValue = item.get("item_value");
				String columnName = item.get("column_name");
				if (StringUtils.isBlank(columnName)){
					columnName="";
				}
				switch (item.get("column_type")){
					case "10":
						/*项目*/
						examWorkflowDatas.setS1(item.get("item_value"));
						examWorkflowDatas.setProject(item.get("item_value"));
						break;
					case "4":
						/*分类*/
						examWorkflowDatas.setS2(item.get("item_value"));
						examWorkflowDatas.setType(item.get("item_value"));
						break;
					case "1":
						/*评分标准*/
						examWorkflowDatas.setS3(item.get("item_value"));
						break;
					case "2":
						/*分值（+）*/
						examWorkflowDatas.setS4(item.get("item_value"));
						examWorkflowDatas.setValueType(1);
						break;
					case "3":
						/*分值（-）*/
						examWorkflowDatas.setS5(item.get("item_value"));
						examWorkflowDatas.setValueType(-1);
						break;
					case "6":
						/*备注*/
						examWorkflowDatas.setS6(item.get("item_value"));
						break;
				}
				if (columnName.equals("主任")){
					examWorkflowDatas.setZhuRen(itemValue);
					model.addAttribute("zhuRen","show");
				}
				if (columnName.equals("书记")){
					examWorkflowDatas.setShuJi(itemValue);
					model.addAttribute("shuJi","show");
				}
				if (columnName.contains("主管领导")){
					examWorkflowDatas.setZhuGuanLingDao(itemValue);
					model.addAttribute("zhuGuanLingDao","show");
				}
				if (columnName.equals("处长")){
					examWorkflowDatas.setChuZhang(itemValue);
					model.addAttribute("chuZhang","show");
				}
				if (columnName.equals("副处长")){
					examWorkflowDatas.setFuChuZhang(itemValue);
					model.addAttribute("fuChuZhang","show");
				}
				if (columnName.equals("副职")){
					examWorkflowDatas.setFuZhi(itemValue);
					model.addAttribute("fuZhi","show");
				}
				if (columnName.equals("副所长")){
					examWorkflowDatas.setFuSuoZhang(itemValue);
					model.addAttribute("fuSuoZhang","show");
				}
				if (columnName.equals("副支队长")){
					examWorkflowDatas.setFuZhiDuiZhang(itemValue);
					model.addAttribute("fuZhiDuiZhang","show");
				}
				if (columnName.equals("所长")){
					examWorkflowDatas.setSuoZhang(itemValue);
					model.addAttribute("suoZhang","show");
				}
				if (columnName.equals("教导员")){
					examWorkflowDatas.setJiaoDaoYuan(itemValue);
					model.addAttribute("jiaoDaoYuan","show");
				}
				if (columnName.equals("政委")){
					examWorkflowDatas.setZhengWei(itemValue);
					model.addAttribute("zhengWei","show");
				}
				if (columnName.equals("处长")){
					examWorkflowDatas.setChuZhang(itemValue);
					model.addAttribute("chuZhang","show");
				}
				if (columnName.equals("支队长")){
					examWorkflowDatas.setZhiDuiZhang(itemValue);
					model.addAttribute("zhiDuiZhang","show");
				}
				if (columnName.equals("大队长")){
					examWorkflowDatas.setDaDuiZhang(itemValue);
					model.addAttribute("daDuiZhang","show");
				}
				if (columnName.equals("主席")){
					examWorkflowDatas.setZhuXi(itemValue);
					model.addAttribute("zhuXi","show");
				}
				if (columnName.equals("分管领导")){
					examWorkflowDatas.setFenGuanLingDao(itemValue);
					model.addAttribute("fenGuanLingDao","show");
				}

				if (columnName.contains("公安处")){
					examWorkflowDatas.setGongAnChu(itemValue);
					model.addAttribute("gongAnChu","show");
				}
				if (columnName.contains("责任单位")){
					examWorkflowDatas.setZeRenDanWei(itemValue);
					model.addAttribute("zeRenDanWei","show");
				}
				if (columnName.equals("副主任")){
					examWorkflowDatas.setFuZhuRen(itemValue);
					model.addAttribute("fuZhuRen","show");
				}
				if (columnName.equals("主要领导")){
					examWorkflowDatas.setZhuYaoLingDao(itemValue);
					model.addAttribute("zhuYaoLingDao","show");
				}
				if (columnName.equals("其他副职")){
					examWorkflowDatas.setQiTaFuZhi(itemValue);
					model.addAttribute("qiTaFuZhi","show");
				}
				if (columnName.equals("副大队长")){
					examWorkflowDatas.setFuDaDuiZhang(itemValue);
					model.addAttribute("fuDaDuiZhang","show");
				}
				if (columnName.equals("包保支队领导")){
					examWorkflowDatas.setBaoBaoZhiDui(itemValue);
					model.addAttribute("baoBaoZhiDui","show");
				}
				if (columnName.equals("其他领导")){
					examWorkflowDatas.setQiTaLingDao(itemValue);
					model.addAttribute("qiTaLingDao","show");
				}
				if (columnName.equals("本人")){
					examWorkflowDatas.setBenRen(itemValue);
					model.addAttribute("benRen","show");
				}
				if (StringUtils.isBlank(examWorkflowDatas.getSpecial())) {
					if (columnName.equals("职务") || columnName.equals("加扣分") || columnName.equals("扣分") ||
							columnName.equals("项目") || columnName.equals("相关业务") || columnName.equals("类别") || columnName.equals("备注") ||
							columnName.equals("岗位")) {
						examWorkflowDatas.setSpecial(null);
					}
					if (columnName.equals("本人") || columnName.equals("主任") || columnName.equals("书记") || columnName.contains("主管领导") || columnName.equals("处长") ||
							columnName.equals("副处长") || columnName.equals("副职") || columnName.equals("副支队长") || columnName.equals("所长") ||
							columnName.equals("所长") || columnName.equals("支队长") || columnName.equals("大队长") || columnName.equals("主席") ||
							columnName.equals("分管领导") || columnName.contains("公安处") || columnName.contains("责任单位") || columnName.equals("副主任") ||
							columnName.equals("主要领导") || columnName.equals("其他副职") || columnName.equals("副大队长") || columnName.equals("包保支队领导") ||
							columnName.equals("其他领导")) {
						examWorkflowDatas.setSpecial("no");
					}
				}

			}
		});


		String nextStatus = String.valueOf(WorkFloatConstant.EXAM_END);
		model.addAttribute("status", status);
		if (status < WorkFloatConstant.ALL_PUBLIC) {
			nextStatus = this.getNextStepStatus(segmentsList, status);
			model.addAttribute("nextStep", nextStatus);
		}
		String NNextStep = this.getNextStepStatus(segmentsList, Integer.parseInt(nextStatus));
		/*设置下一环节的推送人  不是系统公示就设置下一步，系统公示的时候初审人员修正，推送到下下一步*/
		if (nextStatus.equals(String.valueOf(WorkFloatConstant.EXAM_PUBLIC))) {
			this.setPersonModel(NNextStep, examWorkflow.getExamType(), workflowDatasList, model);
		} else {
			this.setPersonModel(nextStatus, examWorkflow.getExamType(), workflowDatasList, model);
		}
		model.addAttribute("nnextStep", NNextStep);
		String history = request.getParameter("history");

		/*当考评一个考评对象推动到下一个流程时 ， 使用history来控制 提交按钮是否显示*/
		if (selectedList != null && selectedList.size()>0 && String.valueOf(selectedList.get(0).getStatus()).equals(nextStatus) ){
			history ="true";
		}
		if (null != history) {
			model.addAttribute("history", history);
			Map<String, String> sParam = new HashMap<String, String>();
			String personId = UserUtils.getUser().getId();
			sParam.put("id", examWorkflow.getId());
			sParam.put("personId", personId);
			List<Map<String, String>> datasList = examWorkflowService.selectByPerson(sParam);
			if (examWorkflow.getStatus() == WorkFloatConstant.EXAM_PUBLIC || examWorkflow.getStatus() == WorkFloatConstant.COMPLET_SIGN) {
				model.addAttribute("isSave", String.valueOf(this.isSave(examWorkflow, datasList, personId)));
			}
		}
		String[] columnList =new String[] {"9","4","1","2","3","6"};
		/*查询已经保存的所有的考评项 */
		model.addAttribute("columnList",columnList);

		/*查询推送失败，添加的考评项*/
		ExamWorkflowDatas temp = new ExamWorkflowDatas();
		temp.setIsAlreadySelected("99");
		temp.setWorkflowId(workflowId);
		if (StringUtils.isNotBlank(objId)) {
			temp.setObjId(objId);
		} else if (StringUtils.isNotBlank(fillPersonId)) {
			temp.setFillPersonId(fillPersonId);
		} else {
			temp.setFillPersonId(UserUtils.getUser().getId());
		}
		/*查询推送的考评的考评数据*/
		Page<ExamWorkflowDatas> pageParam = new Page<ExamWorkflowDatas>(request,response,-1);
		pageParam.setOrderBy("id");
		List<ExamWorkflowDatas> pushList = new ArrayList<>();
		if (StringUtils.isBlank(noTree)){
			/*查询审核的考评项*/
			temp.setExamPersonId(userId);
			temp.setDepartSignId(userId);
			temp.setPartBureausSignId(userId);
			temp.setAdjustPersonId(userId);
			temp.setMainBureausSignId(userId);
			pushList = examWorkflowDatasService.findAboutMeList(temp);
		}else {
			/*noTree 不为空时查看考评对象的所有数据*/
			pushList = examWorkflowDatasService.findList(temp);
		}
//		Page<ExamWorkflowDatas> pushExam = examWorkflowDatasService.findPage(pageParam,temp);
//        workflowDatasList.addAll(pushExam.getList().stream().filter(examWorkflowDatas -> examWorkflowDatas.getStandardId()!=null).collect(Collectors.toList()));
		workflowDatasList.addAll(pushList);
		model.addAttribute("workflowDatasList",workflowDatasList);
		/*根据状态跳转到不同的页面*/
		if (WorkFloatConstant.NO_START != status && WorkFloatConstant.EXAM_END != status) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("flowId", examWorkflow.getId());
			param.put("DEL_FLAG_NORMAL", "0");
			List<Map<String, Object>> list = examWorflowSegmentsService.findSegmentsList(param);
			if (WorkFloatConstant.SELF_EXAM == status && this.isExamRole(UserUtils.getUser().getRoleList(), examWorkflow.getExamType())) {
				Map<String, Object> objParam = new HashMap<String, Object>();
				objParam.put("workflowId", examWorkflow.getId());
//                List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
//                model.addAttribute("objList", objList);
				List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjDataList(objParam);
				List<Map<String, Object>> resultObjList = this.checkObjectList(objList);
				model.addAttribute("objList", resultObjList);
				model.addAttribute("processType", "appraiseExam");



				targetUrl = "modules/exam/appraise_simple";
			} else if (WorkFloatConstant.SELF_EXAM == status) {
//				ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
//				String standardIds = examWorkflowDefine.getTemplatesIds();
//				List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoList(standardIds, examWorkflow, UserUtils.getUser().getId());
				model.addAttribute("processType", "selefAppraise");
				model.addAttribute("standardIds", standardIds);
				model.addAttribute("standardInfoList", standardInfoList);
				targetUrl = "modules/exam/appraiseBeta";
               /* String objId = "";
                String objName = "";
                if (null != request.getParameter("objId")) {
                    objId = request.getParameter("objId");
                    objName = request.getParameter("objName");
                }
                model.addAttribute("objId", objId);
                model.addAttribute("objName", objName);
                targetUrl = "modules/exam/appraiseBeta";*/
			} else if (WorkFloatConstant.SIMPLE_EXAM == status || (this.isExamRole(UserUtils.getUser().getRoleList(), examWorkflow.getExamType()) && WorkFloatConstant.EXAM_PUBLIC == status)) {
				Map<String, Object> objParam = new HashMap<String, Object>();
				objParam.put("workflowId", examWorkflow.getId());
				//objParam.put("group", "personAndStandId");
				//List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
				List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjDataList(objParam);
				List<Map<String, Object>> resultObjList = this.checkObjectList(objList);
				model.addAttribute("objList", resultObjList);
				model.addAttribute("processType", "appraiseExam");
				targetUrl = "modules/exam/appraiseBeta";
				/*初步审核页面*/
				targetUrl = "/modules/exam/appraiseExamBetaHistory";
//				targetUrl = "modules/exam/appraiseExamIndexBeta";
			} else if (WorkFloatConstant.EXAM_PUBLIC == status) {
				// 系统公示显示分数
				Map<String, Object> objParam = new HashMap<String, Object>();
				objParam.put("workflowId", examWorkflow.getId());
				objParam.put("fillPersonId",fillPersonId);//自评人id
				List<Map<String, Object>> objDatasList = examWorkflowDatasService.getAppraiseObjDataList2(objParam);
				List<Map<String, Object>> objScoreList = examWorkflowDatasService.getAppraiseObjScoreList2(objDatasList,workflowId,fillPersonId,examWorkflow);
				model.addAttribute("objScoreList", objScoreList);
				//List<Map<String, Object>> objDatasList = examWorkflowDatasService.getAppraiseObjDataList(objParam);
				//List<Map<String, Object>> objScoreList = examWorkflowDatasService.getAppraiseObjScoreList(objDatasList);
				//model.addAttribute("objScoreList", this.getCurrentObjs(objScoreList));
				if(objScoreList!=null && objScoreList.size()>0 && objScoreList.get(0).get("addWorkItemScore")!=null){
					model.addAttribute("isAddWorkItemExam","isAddWorkItemExam");
				}
				objParam.put("workflowId", examWorkflow.getId());
				List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
				List<Map<String, Object>> resObjList = new ArrayList<Map<String, Object>>();
				String personId = UserUtils.getUser().getId();
				//绩效考评员
				Set<String> examSet = new HashSet<String>();
				examSet.add("cca66e1339f14799b01f6db43ed16e16");
				examSet.add("978958003ea44a4bba3eed8ee6ceff3c");
				examSet.add("46c521bf67e24db28772b3eac52dc454");
				examSet.add("6af0e615e9b0477da82eff06ff532c8b");
				if (null != objList) {
					for (Map<String, Object> obj : objList) {
						if (personId.equals(obj.get("id")) || examSet.contains(personId)) {
							resObjList.add(obj);
						}
					}
				}
				model.addAttribute("objList", resObjList);
//				targetUrl = "modules/exam/public";
				model.addAttribute("examTypePublic", examWorkflow.getExamType());//考评类型 1 局考处 2 局考局机关 3 处考队所 4 处考处机关 5 处领导 6 中基层 7民警
				/*局考处的页面*/
				if (examWorkflow.getExamType().equals("1")){
					Map<String, Object> resultMap = examWorkflowDatasService.getPublicBetaScoreList(workflowId,fillPersonId);
					// Map<String, Object> map = examUnitAllPublicService.getScoreList1(workflowId);
					model.addAttribute("unitList", resultMap.get("unitList"));
					model.addAttribute("weigthsList", resultMap.get("weigthsList"));
					model.addAttribute("conventionScoreList", resultMap.get("conventionScoreList"));
					if (fillPersonId.equals(UserUtils.getUser().getId())){
						/*公示时 自评人员只能查看，显示列表*/
						model.addAttribute("isFillPerson", resultMap.get("isFillPerson"));
						targetUrl = "modules/exam/publicListJuKaoChuBeta";
					}else {
						targetUrl = "modules/exam/publicJuKaoChuBeta";
					}
				}else {
					/*其他页面*/
					if (fillPersonId.equals(UserUtils.getUser().getId())){
						/*公示时 自评人员只能查看，显示列表*/
						targetUrl = "modules/exam/publicListBeta";
					}else {
						targetUrl = "modules/exam/publicBeta";
					}
				}
			} else if (WorkFloatConstant.DEP_SIGN == status || WorkFloatConstant.PART_SIGN == status || WorkFloatConstant.COMPLET_SIGN == status) {
//				ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
//				String standardIds = examWorkflowDefine.getTemplatesIds();
//				List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoList(standardIds, examWorkflow, UserUtils.getUser().getId());
				model.addAttribute("standardInfoList", standardInfoList);
				Map<String, Object> objParam = new HashMap<String, Object>();
				objParam.put("workflowId", examWorkflow.getId());
				objParam.put("group", "obj");
				List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
				model.addAttribute("objList", objList);
				if (null != request.getParameter("objId")) {
					model.addAttribute("objId", request.getParameter("objId"));
				} else if (null != objList) {
					model.addAttribute("objId", objList.get(0).get("id"));
				}
				targetUrl = "modules/exam/signBeta";

				String processType = "";
				if (WorkFloatConstant.DEP_SIGN == status) {
					processType = "departSign";
					targetUrl = "modules/exam/signContent2Beta";
				} else if (WorkFloatConstant.PART_SIGN == status) {
					processType = "partBureausSign";
					targetUrl = "modules/exam/signContentPart2Beta";
				} else if (WorkFloatConstant.COMPLET_SIGN == status) {
					processType = "mainBureausSign";
					targetUrl = "modules/exam/signContentMain2Beta";
				}
				model.addAttribute("processType", processType);
//				targetUrl = "modules/exam/sign";
			} else if (WorkFloatConstant.GROUP_ADJUST == status) {
//				ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
//				String standardIds = examWorkflowDefine.getTemplatesIds();
//				List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoList(standardIds, examWorkflow, UserUtils.getUser().getId());
				model.addAttribute("standardInfoList", standardInfoList);
				Map<String, Object> objParam = new HashMap<String, Object>();
				objParam.put("workflowId", examWorkflow.getId());
				objParam.put("group", "obj");
				List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
				model.addAttribute("objList", objList);
//				String objId = "";
				String objName = "";
				if (null != request.getParameter("objId")) {
					objId = request.getParameter("objId");
					objName = request.getParameter("objName");
				} else {
					objId = objList.get(0).get("id").toString();
					objName = objList.get(0).get("name").toString();
				}
				model.addAttribute("objId", objId);
				model.addAttribute("objName", objName);
				model.addAttribute("processType", "groupAdjust");
//				targetUrl = "modules/exam/appraise_adjust_leader";
				targetUrl = "modules/exam/appraiseAdjustLeaderBeta";
			} else if (WorkFloatConstant.ALL_PUBLIC == status) {
				if (StringUtils.isNotBlank(publicDetail)&& publicDetail.equals("true")){
					targetUrl = "modules/exam/publicDetailBeta";
				}else {
					targetUrl = this.allPublic(examWorkflow, model, request, response);
				}
			} else {
				targetUrl = "modules/exam/examFlowList";
			}
			model.addAttribute("workflowId", examWorkflow.getId());
			model.addAttribute("status", status);
			model.addAttribute("segmentsList", list);
			model.addAttribute("personType", request.getParameter("personType"));
//			String nextStatus = String.valueOf(WorkFloatConstant.EXAM_END);
			if (status < WorkFloatConstant.ALL_PUBLIC) {
				nextStatus = this.getNextStepStatus(segmentsList, status);
			}
			model.addAttribute("nextStep", nextStatus);
			model.addAttribute("hisCurStatus", String.valueOf(status));
		}
		if (WorkFloatConstant.ALL_PUBLIC != status){
			//最终结果公示不显示
			// 标题头部分  得分  11.18 kevin.jia
			Map<String,Object> headItemScoreMap = examWorkflowDatasService.getHeadItemScore(examWorkflow,fillPersonId);
//			Map<String,Object> headItemScoreMap = examWorkflowDatasService.getHeadItemScore2(examWorkflow,fillPersonId);
			model.addAttribute("headItemScoreMap",headItemScoreMap);
		}
		/*初核人员*/
		List<String > ids =new ArrayList<>();
		tempList.stream().forEach(item -> {
			ids.add(item.getExamPersonId());
		});

		/*遍历被考评对象  查看是否有推送*/
		List<String> myList = ids.stream().distinct().collect(Collectors.toList());
		for (String examPersonId:myList){
			if (StringUtils.isNotBlank(examPersonId) && examPersonId.equals(UserUtils.getUser().getId())){
				model.addAttribute("examPersonId",examPersonId);
				break;
			}
		}
		return targetUrl;
	}

	/**
	 * 历史查看
	 *
	 * @return
	 */
	@RequestMapping(value = {"history"})
	String showHistory( HttpServletRequest request) {
		String examWorkflowId = request.getParameter("examWorkflowId");
		ExamWorkflow examWorkflow = examWorkflowService.get(examWorkflowId);
		ExamWorkflowDatas datas = new ExamWorkflowDatas();
		datas.setWorkflowId(examWorkflow.getId());
		List<ExamWorkflowDatas> list = examWorkflowDatasService.findList(datas);
		int status = -1;
		int tmpStatus = -1;
		if (WorkFloatConstant.EXAM_END == examWorkflow.getStatus()) {
			status = WorkFloatConstant.ALL_PUBLIC;
		} else if (null != list) {
			String personId = UserUtils.getUser().getId();
			for (ExamWorkflowDatas data : list) {
				if (personId.equals(data.getFillPersonId())) {
					tmpStatus = WorkFloatConstant.SELF_EXAM;
				} else if (personId.equals(data.getExamPersonId())) {
					tmpStatus = WorkFloatConstant.SIMPLE_EXAM;
				} else if (personId.equals(data.getDepartSignId())) {
					if (examWorkflow.getStatus() == WorkFloatConstant.EXAM_PUBLIC) {
						tmpStatus = WorkFloatConstant.EXAM_PUBLIC;
					} else if (examWorkflow.getStatus() == WorkFloatConstant.DEP_SIGN) {
						tmpStatus = WorkFloatConstant.DEP_SIGN;
					}
				} else if (personId.equals(data.getPartBureausSignId())) {
					tmpStatus = WorkFloatConstant.PART_SIGN;
				} else if (personId.equals(data.getAdjustPersonId())) {
					tmpStatus = WorkFloatConstant.GROUP_ADJUST;
				} else if (personId.equals(data.getMainBureausSignId())) {
					tmpStatus = WorkFloatConstant.COMPLET_SIGN;
				}
				// 考评员预览（解决单位考评自评阶段预览问题）
				if (null != request.getParameter("preview") && "true".equals(request.getParameter("preview"))) {
					status = tmpStatus;
				}
				//当多个环节为换一个账号时，取最近状态
				else if (tmpStatus > status && tmpStatus < examWorkflow.getStatus()) {
					status = tmpStatus;
				}
			}
		}
		return "redirect:" + Global.getAdminPath() + "/exam/examWorkflowDatas/examBeta?history=true&examWorkflowId=" + examWorkflow.getId() + "&examType=" + examWorkflow.getExamType() + "&hisStatus=" + status;
	}

	/*全局公示  未用，在examBeta 中用publicDetail控制*/
	@RequestMapping(value = "publicDetail")
	public String publicDetail(HttpServletRequest request, Model model) {
		if (null != request.getParameter("workflowId")) {
			String workflowId = request.getParameter("workflowId");
			String objId = request.getParameter("objId");
			String objName = request.getParameter("objName");
			ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
			ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
			String standardIds = examWorkflowDefine.getTemplatesIds();
			List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoList(standardIds, examWorkflow, objId);
			model.addAttribute("standardInfoList", standardInfoList);
			Map<String, Object> objParam = new HashMap<String, Object>();
			objParam.put("workflowId", examWorkflow.getId());
			objParam.put("group", "obj");
			List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
			model.addAttribute("objList", objList);
			model.addAttribute("objId", objId);
			model.addAttribute("objName", objName);
			model.addAttribute("workflowId", workflowId);
			model.addAttribute("status", String.valueOf(WorkFloatConstant.ALL_PUBLIC));
			model.addAttribute("processType", "publicDetail");
		}
		return "modules/exam/publicDetailBeta";
	}

	@ResponseBody
	@RequestMapping(value = {"removeByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids,HttpServletRequest request){
		String type = request.getParameter("type");
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			ids.stream().forEach(itemId -> {
				ExamWorkflowDatas workflowDatas = examWorkflowDatasService.get(itemId);
				if (StringUtils.isNotBlank(workflowDatas.getSelectPersonId()) && workflowDatas.getSelectPersonId().equals(UserUtils.getUser().getId())){
					examWorkflowDatasService.selfRemoveById(itemId);
				}else {
					examWorkflowDatasService.othersRemoveById(itemId,UserUtils.getUser().getId());
				}
			});
//			examWorkflowDatasService.removeByIds(ids,type);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 绩效审核时获取左侧被考评人的机构树  只获取被考评对象的树结构
	 * @param workflowId
	 * @param type
	 * @param grade
	 * @param isAll
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "userTree")
	public List<Map<String, Object>> userTreeBeta(@RequestParam(required=false) String workflowId, @RequestParam(required=false) String type,
			 @RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response,HttpServletRequest request) {
		workflowId = request.getParameter("workflowId");
		List<Map<String, Object>> mapList = new ArrayList<>();
		/*处绩效办查看处领导时使用*/
		String examType = request.getParameter("examType");
		if (StringUtils.isBlank(type) || type.equals("undefined")){
		    type = "1";
        }
		if (isAll == null){
			isAll =false;
		}
		mapList = officeService.findExamObjTree(workflowId,type,isAll,examType);
		/*List<Map<String, Object>> resultList = new ArrayList<>();

		mapList =officeService.findExamObjTree("true");
		mapList.stream().forEach(map -> {
			Map<String, Object> m = Maps.newHashMap();
			m.put("id",map.get("id"));
			m.put("pId",map.get("parentId"));
			m.put("name",map.get("name"));
			m.put("isParent", true);
			resultList.add(m);
		});

		mapList =officeService.findExamObjTree("");
		mapList.stream().forEach(map -> {
			Map<String, Object> m = Maps.newHashMap();
			m.put("id",map.get("id"));
			m.put("pId",map.get("parentId"));
			m.put("name",map.get("name"));
			resultList.add(m);
		});*/
//		officeService

		return mapList;
	}


	@RequestMapping("pushView")
	public String pushView(ExamWorkflowDatas examWorkflowDatas,Model model){
		model.addAttribute("workflowId",examWorkflowDatas.getWorkflowId());
		model.addAttribute("workflowDataId",examWorkflowDatas.getId());
		model.addAttribute("rowNum",examWorkflowDatas.getRowId());
		model.addAttribute("type",examWorkflowDatas.getType());
		model.addAttribute("standardContent",examWorkflowDatas.getS3());
		model.addAttribute("items",examWorkflowDatas.getItems());
		model.addAttribute("standardId",examWorkflowDatas.getStandardId());

		return "modules/exam/pushDialog";
	}
	/**
	 * 初核时  把此考评项推送到其他考评
	 * @param examWorkflowDatas
	 * @return
	 */
	@ResponseBody
	@RequestMapping("pushTree")
	public List<Map<String,Object>> push(ExamWorkflowDatas examWorkflowDatas,@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
										 @RequestParam(required=false) String examType,@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response){
		ExamOffice examOffice = new ExamOffice();
		List<Map<String, Object>> mapList = Lists.newArrayList();

		switch (examWorkflowDatas.getCondition()){
			/*局考处*/
			case "1":
				examOffice.setType("2");
				Map<String,Object> map = new HashMap<>();
				map.put("id","1");
				map.put("name","局考处");
				mapList.add(map);
				break;
			/*局考局机关*/
			case "2":
				examOffice.setType("1");
				Map<String,Object> jukaojujiguan = new HashMap<>();
				jukaojujiguan.put("id","1");
				jukaojujiguan.put("name","局考局机关");
				mapList.add(jukaojujiguan);
				break;
			/*处考所*/
			case "3":
				examOffice.setType("3");
				Map<String,Object> chukaosuo = new HashMap<>();
				chukaosuo.put("id","1");
				chukaosuo.put("name","处考所");
				mapList.add(chukaosuo);
				Map<String,Object> nanning = new HashMap<>();
				nanning.put("id","34");
				nanning.put("pId","1");
				nanning.put("name","南宁处");
				mapList.add(nanning);

				Map<String,Object> beihai = new HashMap<>();
				beihai.put("id","95");
				beihai.put("pId","1");
				beihai.put("name","北海处");
				mapList.add(beihai);

				Map<String,Object> liuzhou = new HashMap<>();
				liuzhou.put("id","156");
				liuzhou.put("pId","1");
				liuzhou.put("name","柳州处");
				mapList.add(liuzhou);
				break;
				/*处考处机关*/
			case "4":
				examOffice.setType("2");
				Map<String,Object> chukaojiguan = new HashMap<>();
				chukaojiguan.put("id","1");
				chukaojiguan.put("name","处考处机关");
				mapList.add(chukaojiguan);
				break;
				/*处考领导班子*/
			case "5":
				examOffice.setType("5");
				/*Map<String,Object> chulingdaobanzi = new HashMap<>();
				chulingdaobanzi.put("id","1");
				chulingdaobanzi.put("name","处领导班子");
				mapList.add(chulingdaobanzi);*/
				break;
				/*中基层领导*/
			case "6":
				examOffice.setType("6");
				/*Map<String,Object> zhongjicenglingdao = new HashMap<>();
				zhongjicenglingdao.put("id","1");
				zhongjicenglingdao.put("name","中基层领导");
				mapList.add(zhongjicenglingdao);*/
				break;
				/*民警*/
			case "7":
				examOffice.setType("7");
				/*Map<String,Object> minjing = new HashMap<>();
				minjing.put("id","1");
				minjing.put("name","民警");
				mapList.add(minjing);*/
				break;
		}
		List<ExamOffice> list = examOfficeDao.findList(examOffice);
		/*去重*/
		list = list.stream().collect(
				collectingAndThen(
						toCollection(() -> new TreeSet<ExamOffice>(Comparator.comparing(item -> item.getId()))), ArrayList::new)
		);
		list.sort(Comparator.comparing(ExamOffice::getCode));
		for (int i=0; i<list.size(); i++){
			ExamOffice e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (!type.equals("1") || type.equals(e.getType()))))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& Global.YES.equals(e.getUseable())){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
					map.put("pId", e.getParentId());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}

		return mapList;
	}












	/*以下为调用的工具方法 无接口*/

	private List<ExamStandardBaseInfo> getStandardInfoListBeta(String standardIds, ExamWorkflow examWorkflow, String fillPersonId) {

		List<ExamStandardBaseInfo> standardInfoList = new ArrayList<ExamStandardBaseInfo>();
		/*有的考评流程没有选择标准*/
		if (StringUtils.isBlank(standardIds)){
			return standardInfoList;
		}
		Set<String> examTypeSet = new HashSet<String>();
		//examTypeSet.add("1");     //	局考处
		examTypeSet.add("2");       //局考局机关
		// examTypeSet.add("3");    //	处考队所
		examTypeSet.add("4");       //处考处机关
		//  examTypeSet.add("5");   //	民警考核
		examTypeSet.add("6");
		//examTypeSet.add("7");
		if (examTypeSet.contains(examWorkflow.getExamType())) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("DEL_FLAG_NORMAL", "0");
			param.put("workflowId", examWorkflow.getId());
			param.put("fillPersonId", fillPersonId);
			/*流程状态*/
			/*根据考评流程Id和填写人Id查询考评模板*/
			List<Map<String, String>> standardList = examWorkflowDatasService.findStandardsDataNum(param);
			for (Map<String, String> standard : standardList) {
				if (null != standard.get("number") && !"0".equals(standard.get("number"))) {
					ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(standard.get("standardid"));
					standardInfoList.add(examStandardBaseInfo);
				}
			}
		} else {
			for (String id : standardIds.split(",")) {
				ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(id);
				standardInfoList.add(examStandardBaseInfo);
			}
		}
		return standardInfoList;
	}

	/**
	 * 修改为树结构后，方便获得被考评对象，不再使用此方法
	 * @param standardIds 选择的测评标准
	 * @param examWorkflow
	 * @param personId
	 * @return  考评标准
	 */
	private List<ExamStandardBaseInfo> getStandardInfoList(String standardIds, ExamWorkflow examWorkflow, String personId) {
		List<ExamStandardBaseInfo> standardInfoList = new ArrayList<ExamStandardBaseInfo>();
		Set<String> examTypeSet = new HashSet<String>();
		//examTypeSet.add("1");     //	局考处
        examTypeSet.add("2");       //局考局机关
		// examTypeSet.add("3");    //	处考队所
		examTypeSet.add("4");       //处考处机关
		//  examTypeSet.add("5");   //	民警考核
		examTypeSet.add("6");
		//examTypeSet.add("7");
		if (examTypeSet.contains(examWorkflow.getExamType())) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("DEL_FLAG_NORMAL", "0");
			param.put("workflowId", examWorkflow.getId());
			/*流程状态*/
			switch (examWorkflow.getStatus()) {
				case 1:
					param.put("fillPersonId", personId);
					break;
				case 2:
					param.put("examPersonId", personId);
					break;
				case 3:
					param.put("objId", personId);
					break;
				case 4:
					param.put("departSignId", personId);
					break;
				case 5:
					param.put("partBureausSignId", personId);
					break;
				case 6:
					param.put("adjustPersonId", personId);
					break;
				case 7:
					param.put("mainBureausSignId", personId);
					break;
			}
			/*根据考评流程Id和填写人Id查询考评模板*/
			List<Map<String, String>> standardList = examWorkflowDatasService.findStandardsDataNum(param);
			for (Map<String, String> standard : standardList) {
				if (null != standard.get("number") && !"0".equals(standard.get("number"))) {
					ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(standard.get("standardid"));
					standardInfoList.add(examStandardBaseInfo);
				}
			}
		} else {
			for (String id : standardIds.split(",")) {
				ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(id);
				if (examStandardBaseInfo!=null){
					standardInfoList.add(examStandardBaseInfo);
				}
			}
		}
		return standardInfoList;
	}

    private void setPerson(String personId, String personName, ExamWorkflowDatas examWorkflowData, String personType) {
        if (null != personId && null != personName) {
            if ("selfAppraise".equals(personType)) {
                examWorkflowData.setFillPersonId(personId);
                examWorkflowData.setFillPerson(personName);
            } else if ("appraiseExam".equals(personType)) {
                examWorkflowData.setExamPersonId(personId);
                examWorkflowData.setExamPerson(personName);
            } else if ("departSign".equals(personType)) {
                examWorkflowData.setDepartSignId(personId);
                examWorkflowData.setDepartSign(personName);
            } else if ("partBureausSign".equals(personType)) {
                examWorkflowData.setPartBureausSignId(personId);
                examWorkflowData.setPartBureausSign(personName);
            } else if ("mainBureausSign".equals(personType)) {
                examWorkflowData.setMainBureausSignId(personId);
                examWorkflowData.setMainBureausSign(personName);
            } else if ("groupAdjust".equals(personType)) {
                examWorkflowData.setAdjustPersonId(personId);
                examWorkflowData.setAdjustPerson(personName);
            }
        }
    }



	/**
	 * 下一步状态
	 *
	 * @param segmentList
	 * @param curStatus
	 */
	private String getNextStepStatus(List<Map<String,Object>> segmentList, Integer curStatus) {

		String nextStepStatus = "99";
		for (int i = 0; i < segmentList.size() - 1; i++) {
			Map<String, Object> segment = segmentList.get(i);
			String sort = segment.get("sort").toString();
			if (String.valueOf(curStatus).equals(sort)) {
				nextStepStatus = segmentList.get(i + 1).get("sort").toString();
				break;
			}
		}
		return nextStepStatus;
	}

	/**
	 * 下一环节状态
	 * @param workflowId
	 * @param curStatus
	 * @return
	 */
	private String getNextStepStatus(String workflowId, Integer curStatus) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("flowId", workflowId);
		param.put("DEL_FLAG_NORMAL", "0");
		List<Map<String, Object>> list = examWorflowSegmentsService.findSegmentsList(param);
		String nextStepStatus = "99";
		for (int i = 0; i < list.size() - 1; i++) {
			Map<String, Object> segment = list.get(i);
			String sort = segment.get("sort").toString();
			if (String.valueOf(curStatus).equals(sort)) {
				nextStepStatus = list.get(i + 1).get("sort").toString();
				break;
			}
		}
		return nextStepStatus;
	}

	/**
	 * 上一步状态
	 *
	 * @param workflowId
	 * @param curStatus
	 */
	private String getPreStepStatus(String workflowId, Integer curStatus) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("flowId", workflowId);
		param.put("DEL_FLAG_NORMAL", "0");
		List<Map<String, Object>> list = examWorflowSegmentsService.findSegmentsList(param);
		String preStepStatus = "1";
		for (int i = 2; i < list.size(); i++) {
			Map<String, Object> segment = list.get(i);
			String sort = segment.get("sort").toString();
			if (String.valueOf(curStatus).equals(sort)) {
				preStepStatus = list.get(i - 1).get("sort").toString();
				break;
			}
		}
		return preStepStatus;
	}


	/**
	 * 设置推送人
	 * @param nextStatus
	 * @param examType
	 * @param selectedList
	 * @param model
	 */
	private void setPersonModel(String nextStatus, String examType, List<ExamWorkflowDatas> selectedList, Model model) {
		if (null != selectedList && selectedList.size() > 0 && selectedList.get(0) != null) {
			if ("1".equals(nextStatus)) {
				model.addAttribute("person", selectedList.get(0).getFillPerson());
				model.addAttribute("personId", selectedList.get(0).getFillPersonId());
			} else if ("2".equals(nextStatus)) {
				model.addAttribute("person", selectedList.get(0).getExamPerson());
				model.addAttribute("personId", selectedList.get(0).getExamPersonId());
			} else if ("3".equals(nextStatus) || "4".equals(nextStatus)) {
				model.addAttribute("person", selectedList.get(0).getDepartSign());
				model.addAttribute("personId", selectedList.get(0).getDepartSignId());
			} else if ("5".equals(nextStatus)) {
				model.addAttribute("person", selectedList.get(0).getPartBureausSign());
				model.addAttribute("personId", selectedList.get(0).getPartBureausSignId());
			} else if ("7".equals(nextStatus)) {
				model.addAttribute("person", selectedList.get(0).getMainBureausSign());
				model.addAttribute("personId", selectedList.get(0).getMainBureausSignId());
			} else if ("6".equals(nextStatus)) {
				if (null != selectedList.get(0).getAdjustPersonId()) {
					model.addAttribute("person", selectedList.get(0).getAdjustPerson());
					model.addAttribute("personId", selectedList.get(0).getAdjustPersonId());
				} else {
					Map<String, String> personMap = this.getDefaultPerson(examType);
					model.addAttribute("person", personMap.get("person"));
					model.addAttribute("personId", personMap.get("personId"));
				}
			}
		}
	}

	/**
	 * 设置默认考评员
	 * @param examType
	 * @return
	 */
	private Map<String, String> getDefaultPerson(String examType) {
		Map<String, String> resultMap = new HashMap<String, String>();
		//局考处，局考局机关
		if ("1".equals(examType) || "2".equals(examType)) {
			resultMap.put("person", "南宁局绩效办");
			resultMap.put("personId", "cca66e1339f14799b01f6db43ed16e16");
		} else{
			String company = UserUtils.getUser().getCompany().getId();
			switch (company) {
				case "1":
					resultMap.put("person", "南宁局绩效办");
					resultMap.put("personId", "cca66e1339f14799b01f6db43ed16e16");
					break;
				case "34":
					resultMap.put("person", "南宁处绩效办");
					resultMap.put("personId", "978958003ea44a4bba3eed8ee6ceff3c");
					break;
				case "95":
					resultMap.put("person", "柳州处绩效办");
					resultMap.put("personId", "6af0e615e9b0477da82eff06ff532c8b");
					break;
				case "156":
					resultMap.put("person", "北海处绩效办");
					resultMap.put("personId", "46c521bf67e24db28772b3eac52dc454");
					break;
			}
		}
		return resultMap;
	}

	/**
	 * 是否可以保存
	 *
	 * @param examWorkflow
	 * @param datasList
	 * @return
	 */
	private boolean isSave(ExamWorkflow examWorkflow, List<Map<String, String>> datasList, String personId) {
		boolean isSave = false;
		if (null != datasList) {
			if (examWorkflow.getStatus() == WorkFloatConstant.EXAM_PUBLIC && personId.equals(datasList.get(0).get("exam_person_id"))) {
				isSave = true;
			} else if (examWorkflow.getStatus() == WorkFloatConstant.COMPLET_SIGN && personId.equals(datasList.get(0).get("adjust_person_id"))) {
				isSave = true;
			}
		}
		return isSave;
	}
	private void setWeight(ExamWorkflowDatas examWorkflowData) {
		ExamWeightsMain examWeightsMain = new ExamWeightsMain();
		String companyId = UserUtils.getUser().getCompany().getId();
		ExamWeights examWeights = new ExamWeights();
		String examType = examWorkflowData.getExamType();

		String officeId = UserUtils.getUser().getOffice().getId();
		if (StringUtils.isNotBlank(examType) && !examType.equals("1")) {
            examWeights.setKpType(examWorkflowData.getExamType());
//            examWeights.setKpChu(companyId);
		}
        if (StringUtils.isNotBlank(examType) && !examType.equals("3") && !companyId.equals("156")) {
            examWeights.setKpType(examWorkflowData.getExamType());
			examWeights.setKpChu(companyId);
            examWeights.setDepartmentId(officeId);
        }
		examWeights = examWeightsService.getWeightByUnit(examWeights);
		if (examWeights == null){
			return;
		}
		examWeightsMain.setEwId(examWeights.getId());
		//examWeightsMain.setEwId(weightsId);
		List<ExamWeightsMain> datasList = examWeightsMainService.findWeightsDataList(examWeightsMain);
		Double weight = 100.0;
		String workName = "32";
		for (ExamWeightsMain weights : datasList) {
			String name = weights.getWorkName();
			name = DictUtils.getDictLabel(name, "exam_weigths", "");
			if (null != examWorkflowData.getType()) {
				String type = examWorkflowData.getType().toString();
				String project = examWorkflowData.getProject().toString();
				type = type.replaceAll("[\\t\\n\\r]", "");
				project = project.replaceAll("[\\t\\n\\r]", "");
				if (type.indexOf(name) > -1) {
					weight = weights.getWeights();
					workName = weights.getWorkName().toString();
					break;
				}else{
					if(project.indexOf(name)>-1){
						weight = weights.getWeights();
						workName = weights.getWorkName().toString();
						break;
					}
				}
			}
		}
		examWorkflowData.setWorkName(workName);
		examWorkflowData.setWeight(weight);
	}


	private synchronized void setWeightBeta(ExamWorkflowDatas examWorkflowData,String examType) {
		ExamWeightsMain examWeightsMain = new ExamWeightsMain();
		String companyId = UserUtils.get(examWorkflowData.getFillPersonId()).getCompany().getId();
		ExamWeights examWeights = new ExamWeights();
		examWeights.setKpType(examType);
        String officeId = UserUtils.get(examWorkflowData.getFillPersonId()).getOffice().getId();
        if(StringUtils.isNotBlank(examType) && (examType.equals("3") || examType.equals("4"))){
			try {
				/*根据自评对象设置所属处，防止局绩效办/admin查看导致权重设置错误*/
				User fillPersonUser = UserUtils.get(examWorkflowData.getFillPersonId());
				if(fillPersonUser!=null){
					if(fillPersonUser.getOffice().getParentIds().contains(",34,")){
						companyId = "34";
					}
					if(fillPersonUser.getOffice().getParentIds().contains(",95,")){
						companyId = "95";
					}
					if(fillPersonUser.getOffice().getParentIds().contains(",156,")){
						companyId = "156";
					}
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
//        String officeId = UserUtils.getUser().getOffice().getId();
        if (StringUtils.isNotBlank(examType) && (examType.equals("1") || examType.equals("2"))) {
            examWeights.setKpType(examType);
//            examWeights.setKpChu(companyId);
         	examWeights.setKpChu(null);
         	examWeights.setDepartmentId(null);
        }
        if (StringUtils.isNotBlank(examType) && examType.equals("3") && !companyId.equals("156")) {
            examWeights.setKpType(examType);
            examWeights.setKpChu(companyId);
            examWeights.setDepartmentId(officeId);
        }
		if (StringUtils.isNotBlank(examType) && examType.equals("3") && companyId.equals("156")) {
			examWeights.setKpType(examType);
			examWeights.setKpChu(companyId);
			examWeights.setDepartmentId(officeId);//21年7月19，北海处绩效办、局绩效办负责人提出，北海处开启两类派出所考评（普铁、高铁）
		}
		if (StringUtils.isNotBlank(examType) && examType.equals("4")){
			//处考处机关
			examWeights.setKpType(examType);
			examWeights.setKpChu(companyId);
			examWeights.setDepartmentId(null);
		}
		examWeights = examWeightsService.getWeightByUnit(examWeights);
		if (examWeights == null){
			examWorkflowData.setWorkName("99");
			examWorkflowData.setWeight(100.0);
			examWorkflowDatasService.save(examWorkflowData);
			return;
		}
		examWeightsMain.setEwId(examWeights.getId());
		//examWeightsMain.setEwId(weightsId);
		List<ExamWeightsMain> datasList = examWeightsMainService.findWeightsDataList(examWeightsMain);
		Double weight = 100.0;
		String workName = "32";
		for (ExamWeightsMain weights : datasList) {
			String name = weights.getWorkName();
			name = DictUtils.getDictLabel(name, "exam_weigths", "");
			if (null != examWorkflowData.getType() || StringUtils.isNotBlank(examWorkflowData.getProject())) {
				String type = examWorkflowData.getType();
				String project = examWorkflowData.getProject();
				if (StringUtils.isNotBlank(type)){
					type = type.replaceAll("[\\t\\n\\r]", "");
					type = type.replaceAll(" ", "");
				}
				if (StringUtils.isNotBlank(project)){
                    project = project.replaceAll("[\\t\\n\\r]", "");
                    project = project.replaceAll(" ", "");
                }
				if (StringUtils.isNotBlank(type) && type.indexOf(name) > -1) {
					weight = weights.getWeights();
					workName = weights.getWorkName().toString();
					break;
				}else{
					if(StringUtils.isNotBlank(project) && project.indexOf(name)>-1){
						weight = weights.getWeights();
						workName = weights.getWorkName().toString();
						break;
					}else {
                        weight = 100.0;
                        workName = "32";
                    }
				}
			}
		}
		examWorkflowData.setWorkName(workName);
		examWorkflowData.setWeight(weight);
		examWorkflowDatasService.save(examWorkflowData);
	}



	/**
	 * 判断是否绩效考评组
	 *
	 * @param roleList
	 * @return
	 */
	private boolean isExamRole(List<Role> roleList, String examType) {
		boolean result = false;
		if (null != roleList) {
			for (Role role : roleList) {
//                if ("绩效考核办".equals(role.getName())) {
//                    result = true;
//                    break;
//                }
				/*目前没有角色含有 绩效考核的*/
				if (null != role.getName() && role.getName().indexOf("绩效考核") > -1) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	private List<Map<String, Object>> checkObjectList(List<Map<String, Object>> objList) {
		List<Map<String, Object>> resObjectList = new ArrayList<Map<String, Object>>();
		if (null != objList) {
			String objId = "";
			String standardId = "";
			int status = -1;
			Map<String, Object> tmpObj = null;
			for (Map<String, Object> obj : objList) {
				if (null != obj.get("objId") && !objId.equals(obj.get("objId").toString()) && null != obj.get("status")) {
					objId = obj.get("objId").toString();
					status = Integer.parseInt(obj.get("status").toString());
					resObjectList.add(obj);
					tmpObj = obj;
				}
				if (null != obj.get("standardId") && !objId.equals(obj.get("standardId").toString())) {
					standardId = obj.get("standardId").toString();
				}
				if (null != obj.get("objId") && objId.equals(obj.get("objId").toString()) && null != obj.get("standardId") && !objId.equals(obj.get("standardId").toString()) && null != obj.get("status")) {
					int objStatus = Integer.parseInt(obj.get("status").toString());
					if (status > objStatus) {
						tmpObj.remove("status");
						tmpObj.put("status", String.valueOf(objStatus));
					}
				}
			}
		}
		return resObjectList;
	}


	private List<Map<String, Object>> getCurrentObjs(List<Map<String, Object>> objScoreList) {
		List<Map<String, Object>> resObjList = new ArrayList<Map<String, Object>>();
		String personId = UserUtils.getUser().getId();
		//绩效考评员
		Set<String> examSet = new HashSet<String>();
		examSet.add("cca66e1339f14799b01f6db43ed16e16");
		examSet.add("978958003ea44a4bba3eed8ee6ceff3c");
		examSet.add("46c521bf67e24db28772b3eac52dc454");
		examSet.add("6af0e615e9b0477da82eff06ff532c8b");
		if (null != objScoreList) {
			for (Map<String, Object> obj : objScoreList) {
				if (personId.equals(obj.get("objId")) || examSet.contains(personId)) {
					resObjList.add(obj);
				}
			}
		}
		return resObjList;
	}


	/**
	 * 全局公示，相关分值计算的逻辑
	 * @param examWorkflow
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	private String allPublic(ExamWorkflow examWorkflow, Model model, HttpServletRequest request, HttpServletResponse response) {
		String targetUrl = "";
		/**
		 * 局考处、处考队所一套
		 * 局考局机关、处考处机关一套
		 * 民警考评一套；处领导、中基层领导、民警
		 */
		//月度局考处、处考队所考评
		//if ("1".equals(examWorkflow.getExamCycle()) && ("1".equals(examWorkflow.getExamType()) || "3".equals(examWorkflow.getExamType()))) {
		model.addAttribute("allPublicExamType",examWorkflow.getExamType());//考评类别
		if ("1".equals(examWorkflow.getExamCycle()) && "1".equals(examWorkflow.getExamType())) {
			Map<String, Object> map = examUnitAllPublicService.getScoreList1(examWorkflow.getId());
			model.addAttribute("conventionScoreList", map.get("conventionScoreList"));
			model.addAttribute("commonSorceList", map.get("commonSorceList"));
			model.addAttribute("weightSorceList", map.get("weightSorceList"));
			model.addAttribute("sumSorceList", map.get("sumSorceList"));
			targetUrl = "modules/exam/all_jukaochu";
		}
		//年度局考处、处考队所考评
		//else if ("2".equals(examWorkflow.getExamCycle()) && ("1".equals(examWorkflow.getExamType()) || "3".equals(examWorkflow.getExamType()))) {
		//年度局考处、
		else if ("2".equals(examWorkflow.getExamCycle()) && "1".equals(examWorkflow.getExamType())) {
			Map<String, Object> map = examUnitAllPublicYearService.getScoreList1(examWorkflow.getId());
			model.addAttribute("conventionScoreList", map.get("conventionScoreList"));
			model.addAttribute("commonSorceList", map.get("commonSorceList"));
			model.addAttribute("weightSorceList", map.get("weightSorceList"));
			model.addAttribute("sumSorceList", map.get("sumSorceList"));
			targetUrl = "modules/exam/all_jukaochu";
		}
		//月度局考局机关、处考处机关考评
		//else if ("1".equals(examWorkflow.getExamCycle()) && ("2".equals(examWorkflow.getExamType())  || "4".equals(examWorkflow.getExamType()))) {
		// 月度局考局机关、处考处机关考评
		else if ("1".equals(examWorkflow.getExamCycle()) && ("2".equals(examWorkflow.getExamType())  || "4".equals(examWorkflow.getExamType()))) {
			ArrayList<Map<String, String>> list = examUnitAllPublicService.getScoreListJG(examWorkflow.getId());
			model.addAttribute("list", list);
			model.addAttribute("workflowName", examWorkflow.getName());
			if(list!=null && list.size()>0 && "isWeightCalculate".equals(list.get(0).get("isWeightCalculate"))){
				model.addAttribute("isWeightCalculate","isWeightCalculate");
			}
			targetUrl = "modules/exam/all_jukaojujiguan";
		}
		//月度处考队所
		else if("1".equals(examWorkflow.getExamCycle()) &&  "3".equals(examWorkflow.getExamType())){
			ArrayList<Map<String, String>> list = examUnitAllPublicService.getScoreList2(examWorkflow.getId());
			model.addAttribute("list", list);
			model.addAttribute("workflowName", examWorkflow.getName());
			targetUrl = "modules/exam/all_jukaojujiguan";
		}
		//年度局考局机关、处考处机关考评
		//else if ("2".equals(examWorkflow.getExamCycle()) && ("2".equals(examWorkflow.getExamType()) || "4".equals(examWorkflow.getExamType()))) {
		//年度局考局机关、处考处机关考评
		else if ("2".equals(examWorkflow.getExamCycle()) && ("2".equals(examWorkflow.getExamType()) || "4".equals(examWorkflow.getExamType()))) {
			ArrayList<Map<String, String>> list = examUnitAllPublicYearService.getScoreListJG(examWorkflow.getId());
			model.addAttribute("list", list);
			model.addAttribute("workflowName", examWorkflow.getName());
			if(list!=null && list.size()>0 && "isWeightCalculate".equals(list.get(0).get("isWeightCalculate"))){
				model.addAttribute("isWeightCalculate","isWeightCalculate");
			}
			targetUrl = "modules/exam/all_jukaojujiguan";
		}
		//年度处考队所
		else if("2".equals(examWorkflow.getExamCycle()) && "3".equals(examWorkflow.getExamType())){
			ArrayList<Map<String, String>> list = examUnitAllPublicYearService.getScoreList2(examWorkflow.getId());
			model.addAttribute("list", list);
			model.addAttribute("workflowName", examWorkflow.getName());
			targetUrl = "modules/exam/all_jukaojujiguan";
		}
		//月度处领导、中基层领导、民警
		else if ("1".equals(examWorkflow.getExamCycle()) && "2".equals(examWorkflow.getExamObjectType())) {
			ExamLdScoreMonth examLdScoreMonth = new ExamLdScoreMonth();
			examLdScoreMonth.setWorkflowId(examWorkflow.getId());
			Page<ExamLdScoreMonth> page = examLdScoreMonthService.findPage(new Page<ExamLdScoreMonth>(request, response,-1), examLdScoreMonth);
			model.addAttribute("page", page);
			if("5".equals(examWorkflow.getExamType())){
				model.addAttribute("isChuLD","isChuLD");
			}else{
				model.addAttribute("isChuLD","noIsChuLD");
			}
			targetUrl = "modules/exam/examLdScoreMonthListBeta";
			//年度处领导、中基层领导、民警
		} else if ("2".equals(examWorkflow.getExamCycle()) && "2".equals(examWorkflow.getExamObjectType())) {
			ExamLdScore examLdScore = new ExamLdScore();
			examLdScore.setWorkflowId(examWorkflow.getId());
			Page<ExamLdScore> page = examLdScoreService.findPage(new Page<ExamLdScore>(request, response,-1), examLdScore);
			model.addAttribute("page", page);
			if("5".equals(examWorkflow.getExamType())){
				model.addAttribute("isChuLD","isChuLD");
			}else{
				model.addAttribute("isChuLD","noIsChuLD");
			}
			targetUrl = "modules/exam/examLdScoreListBeta";
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("flowId", examWorkflow.getId());
		param.put("DEL_FLAG_NORMAL", "0");
		List<Map<String, Object>> segmentsList = examWorflowSegmentsService.findSegmentsList(param);
		model.addAttribute("hisCurStatus", String.valueOf(WorkFloatConstant.ALL_PUBLIC));
		model.addAttribute("status", String.valueOf(WorkFloatConstant.ALL_PUBLIC));
		model.addAttribute("segmentsList", segmentsList);
		model.addAttribute("workflowId", examWorkflow.getId());
		model.addAttribute("workflowName",examWorkflow.getName());
		return targetUrl;
	}

}