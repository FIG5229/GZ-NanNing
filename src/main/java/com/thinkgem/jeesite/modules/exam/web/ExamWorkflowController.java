/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.ExceptionInfoUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExcelTemplate;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exam.dao.ExamWorkflowDatasDao;
import com.thinkgem.jeesite.modules.exam.entity.*;
import com.thinkgem.jeesite.modules.exam.service.*;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

//import com.thinkgem.jeesite.modules.exam.dao.ExamStandardSelfTermDao;

/**
 * 考评管理Controller
 *
 * @author bradley.zhao
 * @version 2019-12-20
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examWorkflow")
public class ExamWorkflowController extends BaseController {

    @Autowired
    private ExamWorkflowService examWorkflowService;

    @Autowired
    private ExamWorkflowDefineService examWorkflowDefineService;

    @Autowired
    private ExamWorkflowSegmentsDefineService examWorkflowSegmentsDefineService;

    @Autowired
    ExamWorkflowSegmentsService examWorflowSegmentsService;

    @Autowired
    private ExamStandardBaseInfoService examStandardBaseInfoService;

    @Autowired
    private ExamStandardTemplateDefineService examStandardTemplateDefineService;

    @Autowired
    private ExamStandardTemplateItemDefineService examStandardTemplateItemDefineService;

    @Autowired
    private ExamWorkflowDatasService examWorkflowDatasService;

    @Autowired
    private ExamWorkflowSegmentsTaskService examWorkflowSegmentsTaskService;

    @Autowired
    private ExamWeightsService examWeightsService;

    @Autowired
    private ExamWeightsMainService examWeightsMainService;

    @Autowired
    private ExamUnitAllPublicService examUnitAllPublicService;

    @Autowired
    private ExamLdScoreMonthService examLdScoreMonthService;

    @Autowired
    private ExamLdScoreService examLdScoreService;

    @Autowired
    private ExamUnitAllPublicYearService examUnitAllPublicYearService;

    @Autowired
    private ExamCheckService examCheckService;

    @Autowired
    private ExamPushHistoryService examPushHistoryService;

    @Autowired
    private ExamWorkflowDatasDao examWorkflowDatasDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ExamAutoEvaluationService examAutoEvaluationService;
    private final double MONTHWEIGHT = 0.2;
    private final double YEARWEIGHT = 0.8;

    @Autowired
    private ExamJcInfoService examJcInfoService;

    @Autowired
    private ExamCheckChildService examCheckChildService;

    @Autowired
    private ExamKpPersonRelationService examKpPersonRelationService;

//    @Autowired
//    private ExamStandardSelfTermDao examStandardSelfTermDao;

    @ModelAttribute
    public ExamWorkflow get(@RequestParam(required = false) String id) {
        ExamWorkflow entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = examWorkflowService.get(id);
        }
        if (entity == null) {
            entity = new ExamWorkflow();
            entity.setStatus(-1);
        }
        return entity;
    }

    //@RequiresPermissions("exam:examWorkflow:view")
    @RequestMapping(value = {"list", ""})
    public String list(ExamWorkflow examWorkflow, HttpServletRequest request, HttpServletResponse response, Model model) {
        /*考评启动失败的提示信息*/
        String message = request.getParameter("message");
        model.addAttribute("message",message);

        //现在此处挂在全局公示的页面，后期删除
        if (null != request.getParameter("examType") && "9".equals(request.getParameter("examType"))) {
            Map<String, Object> map = examUnitAllPublicService.getScoreList1("2935ea0f6cb8414a85d7b958e3a7fb30");
            model.addAttribute("conventionScoreList", map.get("conventionScoreList"));
            model.addAttribute("commonSorceList", map.get("commonSorceList"));
            model.addAttribute("weightSorceList", map.get("weightSorceList"));
            model.addAttribute("sumSorceList", map.get("sumSorceList"));
            model.addAttribute("workflowName", examWorkflow.getName());
            return "modules/exam/all_jukaochu";
        }
        if (null != request.getParameter("examType") && "10".equals(request.getParameter("examType"))) {
            ArrayList<Map<String, String>> list = examUnitAllPublicService.getScoreList2("ba8cc41c8bce40f1a211b6e34ecb9ef1");
            model.addAttribute("list", list);
            model.addAttribute("workflowName", examWorkflow.getName());
            return "modules/exam/all_jukaojujiguan";
        }
        if (null != request.getParameter("examType") && "11".equals(request.getParameter("examType"))) {
            Map<String, Object> map = examUnitAllPublicService.getScoreList1("11111111111111");
            model.addAttribute("conventionScoreList", map.get("conventionScoreList"));
            model.addAttribute("weightSorceList", map.get("weightSorceList"));
            model.addAttribute("sumSorceList", map.get("sumSorceList"));
            model.addAttribute("workflowName", examWorkflow.getName());
            model.addAttribute("workflowName", examWorkflow.getName());
            return "modules/exam/all_jukaochu";
        }
        if (null != request.getParameter("examType") && "12".equals(request.getParameter("examType"))) {
            ArrayList<Map<String, String>> list = examUnitAllPublicService.getScoreList2("222");
            model.addAttribute("list", list);
            model.addAttribute("workflowName", examWorkflow.getName());
            return "modules/exam/all_jukaojujiguan";
        }
        //年度局考处 处考队所
        if (null != request.getParameter("examType") && "13".equals(request.getParameter("examType"))) {
            Map<String, Object> map = examUnitAllPublicYearService.getScoreList1("3333");
            model.addAttribute("conventionScoreList", map.get("conventionScoreList"));
            model.addAttribute("commonSorceList", map.get("commonSorceList"));
            model.addAttribute("weightSorceList", map.get("weightSorceList"));
            model.addAttribute("sumSorceList", map.get("sumSorceList"));
            model.addAttribute("workflowName", examWorkflow.getName());
            return "modules/exam/all_jukaochu";
        }
        //年度局考局机关、处考处机关
        if (null != request.getParameter("examType") && "14".equals(request.getParameter("examType"))) {
            ArrayList<Map<String, String>> list = examUnitAllPublicYearService.getScoreList2("666");
            model.addAttribute("list", list);
            model.addAttribute("workflowName", examWorkflow.getName());
            return "modules/exam/all_jukaojujiguan";
        }
        if (null != request.getParameter("examType")) {
            examWorkflow.setExamType(request.getParameter("examType").toString());
        }
        Page<ExamWorkflow> page = examWorkflowService.findPage(new Page<ExamWorkflow>(request, response), examWorkflow);
        if (null != page.getList()) {
            List list = page.getList();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> flowObj = (Map<String, Object>) list.get(i);
                String status = flowObj.get("status").toString();
                String nextStatus = "";
                if (Integer.parseInt(status) < 8) {
                    nextStatus = this.getNextStepStatus(flowObj.get("id").toString(), new Integer(status));
                } else {
                    nextStatus = "99";
                }
                flowObj.put("nextStatus", new Integer(nextStatus));
            }
        }
        model.addAttribute("page", page);
        String examType = request.getParameter("examType");
        model.addAttribute("examType", examType);
        String userId = UserUtils.getUser().getId();
        /*局考处的时候 处绩效办只看*/
        if (userId.equals("6af0e615e9b0477da82eff06ff532c8b") || userId.equals("46c521bf67e24db28772b3eac52dc454") || userId.equals("978958003ea44a4bba3eed8ee6ceff3c")){
            if (examType.equals("1")){
                return "modules/exam/examWorkflowListChu";
            }
            if (examType.equals("5")){
                return "modules/exam/examWorkflowListChuLeader";
            }
        }
        return "modules/exam/examWorkflowList";
    }

    //    @RequiresPermissions("exam:examWorkflow:view")
    @RequestMapping(value = {"flowList"})
    public String selectFlowList(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("DEL_FLAG_NORMAL", "0");
        String examType = "";
        if (request.getParameter("examType") != null) {
            examType = request.getParameter("examType");
            param.put("examType", examType);
            model.addAttribute("examType", examType);
        }
        if (request.getParameter("history") != null) {
            param.put("history", "true");
        }
        List<Map<String, String>> list = examWorkflowService.selectFlowList(param);
        List<Map<String, String>> resultList = this.getCurPersonFlowsInfo(list, examType, UserUtils.getUser().getId(), request);
        model.addAttribute("list", resultList);
        String url = "modules/exam/examFlowList";
        if (null != request.getParameter("history") && "true".equals(request.getParameter("history").toString())) {
            url = "modules/exam/examAlreadyFlowList";
        }
        return url;
    }

    /**
     * 历史查看
     *
     * @return
     */
    @RequestMapping(value = {"history"})
    String showHistory(ExamWorkflow examWorkflow, HttpServletRequest request) {
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
        return "redirect:" + Global.getAdminPath() + "/exam/examWorkflow/exam?history=true&id=" + examWorkflow.getId() + "&examType=" + examWorkflow.getExamType() + "&hisStatus=" + status;
    }

    /**
     *  获取用户的考评数据
     *  存在问题 ，当多个环节为同一个人时，环节可能卡在第一个
     *
     * @param list
     * @param examType  考评类型
     * @param person    用户ID
     * @param request
     * @return
     */
    List<Map<String, String>> getCurPersonFlowsInfo(List<Map<String, String>> list, String examType, String person, HttpServletRequest request) {
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        //List<Map<String, Object>> taskList = examWorkflowSegmentsTaskService.getAllTaskSegmentList();
        Map<String, String> param = new HashMap<String, String>();
        param.put("personId", person);
//        List<Map<String, String>> datasList = examWorkflowService.selectByPerson(param);
        //int personStatus = -1;
        if (null != list) {
            for (Map<String, String> flowItem : list) {
                //是否相关
                param.put("id", flowItem.get("id"));
                List<Map<String, String>> datasList = examWorkflowService.selectByPerson(param);
                if (null != datasList && datasList.size() > 0) {
                    List<Map<String, String>> tempDatasList = datasList.stream().filter(item -> item.get("id").equals(flowItem.get("id"))).collect(Collectors.toList());
                    for (Map<String, String> data : tempDatasList) {
                        if (data.get("id").equals(flowItem.get("id"))) {
                            /*被考评对象查看时没有树*/
                            if (person.equals(data.get("fill_person_id"))){
                                flowItem.put("noTree","true");
                            }else {
                                flowItem.put("noTree",null);
                            }
                            try {
                                int tempStatus = Integer.valueOf(String.valueOf(flowItem.get("status")));
                                int personnelStatus = examWorkflowDatasService.getStatusById(UserUtils.getUser().getId(),flowItem.get("id"),tempStatus,UserUtils.getUser().getId());
                                /*当人员状态大于流程状态是，才显示个人状态*/
                                if (tempStatus<personnelStatus){
                                    flowItem.put("status",String.valueOf(personnelStatus));
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if (request.getParameter("history") != null) {
                                if (null != data.get("fill_person_id") && person.equals(data.get("fill_person_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.SELF_EXAM));
                                } else if (null != data.get("exam_person_id") && person.equals(data.get("exam_person_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.SIMPLE_EXAM));
                                } else if (null != data.get("depart_sign_id") && person.equals(data.get("depart_sign_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.DEP_SIGN));
                                } else if (null != data.get("adjust_person_id") && person.equals(data.get("adjust_person_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.GROUP_ADJUST));
                                } else if (null != data.get("part_bureaus_sign_id") && person.equals(data.get("part_bureaus_sign_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.PART_SIGN));
                                } else if (null != data.get("main_bureaus_sign_id") && person.equals(data.get("main_bureaus_sign_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.COMPLET_SIGN));
                                }
                                String personStatus = String.valueOf(flowItem.get("personStatus"));
                                String status = String.valueOf(flowItem.get("status"));
                                /*存在人员流程状态小于考评流程状态时 就跳出此循环*/
                                if (Integer.valueOf(personStatus)<Integer.valueOf(status)){
                                    resultList.add(flowItem);
                                    break;
                                }
                            } else {
                                if (null != data.get("main_bureaus_sign_id") && person.equals(data.get("main_bureaus_sign_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.COMPLET_SIGN));
                                } else if (null != data.get("part_bureaus_sign_id") && person.equals(data.get("part_bureaus_sign_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.PART_SIGN));
                                } else if (null != data.get("adjust_person_id") && person.equals(data.get("adjust_person_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.GROUP_ADJUST));
                                } else if (null != data.get("depart_sign_id") && person.equals(data.get("depart_sign_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.DEP_SIGN));
                                } else if (null != data.get("exam_person_id") && person.equals(data.get("exam_person_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.SIMPLE_EXAM));
                                } else if (null != data.get("fill_person_id") && person.equals(data.get("fill_person_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.SELF_EXAM));
                                }
                                try{
                                    String personStatus = String.valueOf(flowItem.get("personStatus"));
                                    String status = String.valueOf(flowItem.get("status"));
                                    /*存在人员流程状态大于等于待办时 跳出此循环  系统公示时，自评人查询也是带待办中*/
                                    if (Integer.valueOf(personStatus)>=Integer.valueOf(status) || (personStatus.equals("1") && status.equals("3"))){
                                        resultList.add(flowItem);
                                        break;
                                    }
                                }catch (Exception e){

                                }
                            }

                            /*不能再次add 和break，只有找到满足条件的才能add break，不能遍历一次就add  break*/
//                            resultList.add(flowItem);
//                            break;
                        }
                    }
                }
//                if (null != datasList && datasList.size() > 0) {
//                    resultList.add(flowItem);
//                } else {
//                    String flowId = flowItem.get("flow_template_id");
//                    for (Map<String, Object> taskItem : taskList) {
//                        if (flowId.equals(taskItem.get("workflow_id").toString()) && null != taskItem.get("ids")) {
//                            String personStr = taskItem.get("ids").toString();
//                            Set<String> personsSet = new HashSet<String>();
//                            CollectionUtils.addAll(personsSet, personStr.split(","));
//                            if (personsSet.contains(person)) {
//                                resultList.add(flowItem);
//                                break;
//                            }
//                        }
//                    }
//                }

//                //是否可以编辑
//                ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
//                examWorkflowDatas.setWorkflowId(flowItem.get("id"));
//                Object flowStatus = flowItem.get("status");
//                String segmentId = flowStatus.toString();
//                switch (segmentId) {
//                    case "1"://自评
//                        if ((this.isExamRole(UserUtils.getRoleList(), examType))) {
//                            examWorkflowDatas.setExamPersonId(person);
//                        } else {
//                            examWorkflowDatas.setFillPersonId(person);
//                        }
//                        break;
//                    case "2"://初核
//                        examWorkflowDatas.setExamPersonId(person);
//                        break;
//                    case "3"://系统公示
//                        if ((this.isExamRole(UserUtils.getRoleList(), examType))) {
//                            examWorkflowDatas.setExamPersonId(person);
//                        } else {
//                            examWorkflowDatas.setFillPersonId(person);
//                        }
//                        break;
//                    case "4"://	部门负责人签字
//                        examWorkflowDatas.setDepartSignId(person);
//                        break;
//                    case "5"://分管局领导签字
//                        examWorkflowDatas.setPartBureausSignId(person);
//                        break;
//                    case "6"://绩效考评领导小组复核及调整
//                        examWorkflowDatas.setAdjustPersonId(person);
//                        break;
//                    case "7"://局主管领导最终审签
//                        examWorkflowDatas.setMainBureausSignId(person);
//                        break;
//                    case "8"://最终结果全局公示
//                        break;
//                }
//                Map<String, Object> datasNumber = examWorkflowDatasService.selectDatasNumber(examWorkflowDatas);
//                String status = "-1";
//                if (null != datasNumber && null != datasNumber.get("status")) {
////                    Object statusObj = datasNumber.get("status");
////                    status = statusObj.toString();
////                    if ("3".equals(status) || "8".equals(status) || segmentId.equals(status)) {
////                        flowItem.put("isEdit", "true");
////                    } else if ((this.isExamRole(UserUtils.getRoleList(), examType) && "1".equals(segmentId))) {
////                        flowItem.put("isEdit", "true");
////                    } else if ((this.isExamRole(UserUtils.getRoleList(), examType) && "2".equals(segmentId))) {
////                        flowItem.put("isEdit", "true");
////                    } else if ((this.isExamRole(UserUtils.getRoleList(), examType) && "3".equals(segmentId))) {
////                        flowItem.put("isEdit", "true");
////                    } else {
////                        flowItem.put("isEdit", "false");
////                    }
//                    flowItem.put("isEdit", "true");
//                } else {
//                    flowItem.put("isEdit", "false");
//                }
            }
        }
        return resultList;
    }

    private String getStatus(List<Map<String, Object>> objList, String personId) {
        String status = "-1";
        if (null != objList) {
            for (Map<String, Object> obj : objList) {
                if (personId.equals(obj.get("id"))) {
                    status = obj.get("status").toString();
                    break;
                }
            }
        }
        return status;
    }

    //    @RequiresPermissions("exam:examWorkflow:view")
    @RequestMapping(value = {"getExamFlowSegments"})
    public String getExamFlowSegments(HttpServletRequest request, Model model) {
        String flowTemplateId = "";
        if (null != request.getParameter("flowTemplateId") && !"".equals(request.getParameter("flowTemplateId"))) {
            flowTemplateId = request.getParameter("flowTemplateId").toString();
        }

        if (request.getParameter("flowId") != null && !"".equals(request.getParameter("flowId"))) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("flowId", request.getParameter("flowId").toString());
            param.put("DEL_FLAG_NORMAL", "0");
            List<Map<String, Object>> segmentsList = examWorflowSegmentsService.findSegmentsList(param);
            model.addAttribute("segmentsList", segmentsList);
        } else {
            ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(flowTemplateId);
            List<ExamWorkflowSegmentsDefine> list = examWorkflowSegmentsDefineService.findByType(examWorkflowDefine.getExamType());
            model.addAttribute("list", list);
        }
        return "modules/exam/examWorkflowSegmentsList";
    }

    //    @RequiresPermissions("exam:examWorkflow:view")
    @RequestMapping(value = "form")
    public String form(ExamWorkflow examWorkflow, Model model, HttpServletRequest request) {
        model.addAttribute("examWorkflow", examWorkflow);
        ExamWorkflowDefine examWorkflowDefine = new ExamWorkflowDefine();
        examWorkflowDefine.setDelFlag("0");
        examWorkflowDefine.setIsUse("1");
        if (request.getParameter("examType") != null) {
            examWorkflowDefine.setExamType(request.getParameter("examType"));
            model.addAttribute("examType", request.getParameter("examType"));
        }
        List<ExamWorkflowDefine> workList = examWorkflowDefineService.findList(examWorkflowDefine);
        model.addAttribute("workList", workList);
        String url = "modules/exam/examWorkflowForm";
        if (null == examWorkflow || null == examWorkflow.getId() || "".equals(examWorkflow.getId())) {
            url = "modules/exam/examWorkflowAddForm";
        }
        return url;
    }
    @RequestMapping(value = "workList")
    @ResponseBody
    public Result workList(String examCycle,String examType){
        Result result = new Result();
        ExamWorkflowDefine examWorkflowDefine = new ExamWorkflowDefine();
        examWorkflowDefine.setDelFlag("0");
        examWorkflowDefine.setIsUse("1");
        examWorkflowDefine.setExamType(examType);
        examWorkflowDefine.setExamCycle(examCycle);
        List<ExamWorkflowDefine> workList = examWorkflowDefineService.findList(examWorkflowDefine);
        result.setSuccess(true);
        result.setResult(workList);
        return result;
    }

    // @RequiresPermissions("exam:examWorkflow:edit")
    @RequestMapping(value = "save")
    public String save(ExamWorkflow examWorkflow, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, examWorkflow)) {
            return form(examWorkflow, model, request);
        }
        String[] checkedSegments = request.getParameterValues("checkedSegment");
        if (null != checkedSegments) {
            Set<String> segmentsSet = new HashSet<String>();
            for (String segment : checkedSegments) {
                segmentsSet.add(segment);
            }
            List<ExamWorflowSegments> segmentsListCopy = new ArrayList<ExamWorflowSegments>();
            segmentsListCopy.addAll(examWorkflow.getExamWorflowSegmentsList());
            for (ExamWorflowSegments examWorflowSegments : segmentsListCopy) {
                if (!segmentsSet.contains(examWorflowSegments.getSegmentId())) {
                    examWorkflow.getExamWorflowSegmentsList().remove(examWorflowSegments);
                }
            }
        }
        examWorkflowService.save(examWorkflow);
        addMessage(redirectAttributes, "保存考评标准成功");
        request.setAttribute("saveResult", "success");
        //return "redirect:"+Global.getAdminPath()+"/exam/examWorkflow/?repage";
        return "modules/exam/examWorkflowForm";
    }

    //    @RequiresPermissions("exam:examWorkflow:view")
    @RequestMapping(value = "appaise/data/save")
    public String appraiseSave(ExamWorkflow examWorkflow, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String standardId = "";
        if (null != request.getParameter("standardId")) {
            standardId = request.getParameter("standardId");
        }
        int status = -1;
        if (null != request.getParameter("status") && !"".equals(request.getParameter("status"))) {
            status = new Integer(request.getParameter("status"));
        }
        String processType = "";
        if (null != request.getParameter("processType")) {
            processType = request.getParameter("processType").toString();
        }

        String personType = "";
        if (null != request.getParameter("personType")) {
            personType = request.getParameter("personType").toString();
        }

        //初核人员、绩效办改分
        String isUpdateSorce = "";
        if (null != request.getParameter("isUpdateSorce")) {
            isUpdateSorce = request.getParameter("isUpdateSorce").toString();
        }

        boolean isHashNoPass = false;
        if (WorkFloatConstant.SIMPLE_EXAM == status) {
            for (ExamWorkflowDatas examWorkflowData : examWorkflow.getDatas()) {
                //不通过
                if (null != examWorkflowData.getOperationStatus() && "2".equals(examWorkflowData.getOperationStatus().toString())) {
                    isHashNoPass = true;
                    break;
                }
            }
        }
        int nextStatus = Integer.parseInt(this.getNextStepStatus(examWorkflow.getId(), status));
        for (ExamWorkflowDatas examWorkflowData : examWorkflow.getStandardDatas()) {
            int tempStatus = status;
            if (beanValidator(model, examWorkflowData) && null != examWorkflowData.getRowId()) {
                if (null != request.getParameter("personId") && null != request.getParameter("personName")) {
                    String personId = request.getParameter("personId");
                    String personName = request.getParameter("personName");
                    this.setPerson(personId, personName, examWorkflowData, personType);
                }
                if ("selfAppraise".equals(processType)) {
                    this.setWeight(examWorkflowData);
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("appraiseExam".equals(processType)) {
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("departSign".equals(processType)) {
//                    examWorkflowData.setDepartSignId(UserUtils.getUser().getId());
//                    examWorkflowData.setDepartSign(UserUtils.getUser().getName());
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("partBureausSign".equals(processType)) {
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("mainBureausSign".equals(processType)) {
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("groupAdjust".equals(processType)) {
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("save".equals(processType)) {
                    examWorkflowData.setOperationStatus(0);
                }
                //是否被选中
                boolean isSelected = false;
                if (null != examWorkflow.getDatas()) {
                    for (ExamWorkflowDatas selectedExamWorkflowData : examWorkflow.getDatas()) {
                        if (examWorkflowData.getRowId().equals(selectedExamWorkflowData.getRowId())) {
                            if (null != selectedExamWorkflowData.getDetail()) {
                                examWorkflowData.setDetail(selectedExamWorkflowData.getDetail());
                            }
                            if (null != selectedExamWorkflowData.getValue()) {
                                examWorkflowData.setValue(selectedExamWorkflowData.getValue());
                            }
                            if (null != selectedExamWorkflowData.getPath()) {
                                examWorkflowData.setPath(selectedExamWorkflowData.getPath());
                            }
                            if (null != selectedExamWorkflowData.getItems()) {
                                examWorkflowData.setItems(selectedExamWorkflowData.getItems());
                            }
                            if (null != selectedExamWorkflowData.getOperationStatus()) {
                                examWorkflowData.setOperationStatus(selectedExamWorkflowData.getOperationStatus());
                                //不通过
                                if ("2".equals(selectedExamWorkflowData.getOperationStatus().toString())) {
                                    tempStatus = WorkFloatConstant.SELF_EXAM;
                                }
                            }
                            isSelected = true;
                            break;
                        }
                    }
                }
                if (!isSelected) {
                    examWorkflowData.setValue("0");
                    examWorkflowData.setIsSelected("0");//未选中
                } else {
                    examWorkflowData.setIsSelected("1");//选中
                }
                if ("true".equals(isUpdateSorce)) {
                    examWorkflowData.setStatus(null);
                    examWorkflowData.setStatus(null);
                } else {
                    examWorkflowData.setStatus(new Integer(tempStatus));
                }
                examWorkflowDatasService.save(examWorkflowData);
            }
        }

        //查询本阶段是否所有数据都已经处理
        if ("selfAppraise".equals(processType) || "appraiseExam".equals(processType) || "departSign".equals(processType) ||
                "partBureausSign".equals(processType) || "groupAdjust".equals(processType) ||
                "mainBureausSign".equals(processType) || "mainBureausSignAll".equals(processType) &&
                WorkFloatConstant.ALL_PUBLIC != Integer.parseInt(this.getNextStepStatus(examWorkflow.getId(), status))) {
            ExamWorkflowDatas param = new ExamWorkflowDatas();
            param.setWorkflowId(examWorkflow.getId());
            //param.setStandardId(standardId);
            String condition = "a.status < " + this.getNextStepStatus(examWorkflow.getId(), status);
            //condition += " AND a.operation_status <> 3";
            param.setCondition(condition);
            Map<String, Object> datasNumber = examWorkflowDatasService.selectDatasNumber(param);
            /*为什么查不到才更新到下一阶段*/
            if (null == datasNumber || "0".equals(datasNumber.get("number").toString())) {
                if (!"mainBureausSignAll".equals(processType)) {
                    Map<String, Object> uptParam = new HashMap<String, Object>();
                    uptParam.put("id", examWorkflow.getId());
                    uptParam.put("status", Integer.parseInt(this.getNextStepStatus(examWorkflow.getId(), status)));
                    uptParam.put("operationStatus", new Integer("-1"));
                    examWorkflowService.updateStatus(uptParam);
                }
                Map<String, Object> uptDatasParam = new HashMap<String, Object>();
                uptDatasParam.put("workflowId", examWorkflow.getId());
                uptDatasParam.put("status", Integer.parseInt(this.getNextStepStatus(examWorkflow.getId(), status)));
                if (WorkFloatConstant.SELF_EXAM != status) {
                    uptDatasParam.put("operationStatus", new Integer("-1"));
                }
                examWorkflowDatasService.updateStatus(uptDatasParam);
            }
        }
        if (WorkFloatConstant.SIMPLE_EXAM == status && isHashNoPass) {
            Map<String, Object> uptParam = new HashMap<String, Object>();
            uptParam.put("id", examWorkflow.getId());
            uptParam.put("status", WorkFloatConstant.SELF_EXAM);
            uptParam.put("operationStatus", new Integer("1"));
            examWorkflowService.updateStatus(uptParam);
        }
//        if (WorkFloatConstant.ALL_PUBLIC == Integer.parseInt(this.getNextStepStatus(examWorkflow.getId(), status))) {//全局公示
//            this.generateScores(examWorkflow);
//        }
        addMessage(redirectAttributes, "数据保存成功");
        return "redirect:" + Global.getAdminPath() + "/exam/examWorkflow/appaise/content?standardId=" + standardId + "&workflowId=" + examWorkflow.getId() + "&status=" + status + "&processType=" + processType + "&personType=" + personType + "&result=success&examType=" + request.getParameter("examType");
    }

    /**
     * 批量审核
     * @param redirectAttributes
     * @param request
     * @param ids   选中的审核用户 Id
     * @return
     */
    @RequestMapping(value = "appraise/data/saveByIdsBeta")
    @ResponseBody
    public String appraiseSaveByIdsBeta(RedirectAttributes redirectAttributes, HttpServletRequest request,@RequestParam(value="ids[]")String[] ids) {

        String standardId = "";
        if (null != request.getParameter("standardId")) {
            standardId = request.getParameter("standardId");
        }
        int status = -1;
        if (null != request.getParameter("status") && !"".equals(request.getParameter("status"))) {
            status = new Integer(request.getParameter("status"));
        }
        String processType = "";
        if (null != request.getParameter("processType")) {
            processType = request.getParameter("processType").toString();
        }

        String personType = "";
        if (null != request.getParameter("personType")) {
            personType = request.getParameter("personType").toString();
        }

        //初核人员、绩效办改分
        String isUpdateSorce = "";
        if (null != request.getParameter("isUpdateSorce")) {
            isUpdateSorce = request.getParameter("isUpdateSorce").toString();
        }

        boolean isHashNoPass = false;
        ExamWorkflow examWorkflow = new ExamWorkflow();
        if (WorkFloatConstant.SIMPLE_EXAM == status) {
            for (ExamWorkflowDatas examWorkflowData : examWorkflow.getDatas()) {
                //不通过
                if (null != examWorkflowData.getOperationStatus() && "2".equals(examWorkflowData.getOperationStatus().toString())) {
                    isHashNoPass = true;
                    break;
                }
            }
        }
        int nextStatus = Integer.parseInt(this.getNextStepStatus(examWorkflow.getId(), status));
        for (ExamWorkflowDatas examWorkflowData : examWorkflow.getStandardDatas()) {
            int tempStatus = status;
            if ( null != examWorkflowData.getRowId()) {
                if (null != request.getParameter("personId") && null != request.getParameter("personName")) {
                    String personId = request.getParameter("personId");
                    String personName = request.getParameter("personName");
                    this.setPerson(personId, personName, examWorkflowData, personType);
                }
                if ("selfAppraise".equals(processType)) {
                    this.setWeight(examWorkflowData);
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("appraiseExam".equals(processType)) {
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("departSign".equals(processType)) {
//                    examWorkflowData.setDepartSignId(UserUtils.getUser().getId());
//                    examWorkflowData.setDepartSign(UserUtils.getUser().getName());
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("partBureausSign".equals(processType)) {
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("mainBureausSign".equals(processType)) {
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("groupAdjust".equals(processType)) {
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("save".equals(processType)) {
                    examWorkflowData.setOperationStatus(0);
                }
                //是否被选中
                boolean isSelected = false;
                if (null != examWorkflow.getDatas()) {
                    for (ExamWorkflowDatas selectedExamWorkflowData : examWorkflow.getDatas()) {
                        if (examWorkflowData.getRowId().equals(selectedExamWorkflowData.getRowId())) {
                            if (null != selectedExamWorkflowData.getDetail()) {
                                examWorkflowData.setDetail(selectedExamWorkflowData.getDetail());
                            }
                            if (null != selectedExamWorkflowData.getValue()) {
                                examWorkflowData.setValue(selectedExamWorkflowData.getValue());
                            }
                            if (null != selectedExamWorkflowData.getPath()) {
                                examWorkflowData.setPath(selectedExamWorkflowData.getPath());
                            }
                            if (null != selectedExamWorkflowData.getItems()) {
                                examWorkflowData.setItems(selectedExamWorkflowData.getItems());
                            }
                            if (null != selectedExamWorkflowData.getOperationStatus()) {
                                examWorkflowData.setOperationStatus(selectedExamWorkflowData.getOperationStatus());
                                //不通过
                                if ("2".equals(selectedExamWorkflowData.getOperationStatus().toString())) {
                                    tempStatus = WorkFloatConstant.SELF_EXAM;
                                }
                            }
                            isSelected = true;
                            break;
                        }
                    }
                }
                if (!isSelected) {
                    examWorkflowData.setValue("0");
                    examWorkflowData.setIsSelected("0");//未选中
                } else {
                    examWorkflowData.setIsSelected("1");//选中
                }
                if ("true".equals(isUpdateSorce)) {
                    examWorkflowData.setStatus(null);
                    examWorkflowData.setStatus(null);
                } else {
                    examWorkflowData.setStatus(new Integer(tempStatus));
                }
                examWorkflowDatasService.save(examWorkflowData);
            }
        }

        //查询本阶段是否所有数据都已经处理
        if ("selfAppraise".equals(processType) || "appraiseExam".equals(processType) || "departSign".equals(processType) || "partBureausSign".equals(processType) || "groupAdjust".equals(processType) || "mainBureausSign".equals(processType) || "mainBureausSignAll".equals(processType) && WorkFloatConstant.ALL_PUBLIC != Integer.parseInt(this.getNextStepStatus(examWorkflow.getId(), status))) {
            ExamWorkflowDatas param = new ExamWorkflowDatas();
            param.setWorkflowId(examWorkflow.getId());
            //param.setStandardId(standardId);
            String condition = "a.status < " + this.getNextStepStatus(examWorkflow.getId(), status);
            //condition += " AND a.operation_status <> 3";
            param.setCondition(condition);
            Map<String, Object> datasNumber = examWorkflowDatasService.selectDatasNumber(param);
            if (null == datasNumber || "0".equals(datasNumber.get("number").toString())) {
                if (!"mainBureausSignAll".equals(processType)) {
                    Map<String, Object> uptParam = new HashMap<String, Object>();
                    uptParam.put("id", examWorkflow.getId());
                    uptParam.put("status", Integer.parseInt(this.getNextStepStatus(examWorkflow.getId(), status)));
                    uptParam.put("operationStatus", new Integer("-1"));
                    examWorkflowService.updateStatus(uptParam);
                }
                Map<String, Object> uptDatasParam = new HashMap<String, Object>();
                uptDatasParam.put("workflowId", examWorkflow.getId());
                uptDatasParam.put("status", Integer.parseInt(this.getNextStepStatus(examWorkflow.getId(), status)));
                if (WorkFloatConstant.SELF_EXAM != status) {
                    uptDatasParam.put("operationStatus", new Integer("-1"));
                }
                examWorkflowDatasService.updateStatus(uptDatasParam);
            }
        }
        if (WorkFloatConstant.SIMPLE_EXAM == status && isHashNoPass) {
            Map<String, Object> uptParam = new HashMap<String, Object>();
            uptParam.put("id", examWorkflow.getId());
            uptParam.put("status", WorkFloatConstant.SELF_EXAM);
            uptParam.put("operationStatus", new Integer("1"));
            examWorkflowService.updateStatus(uptParam);
        }
//        if (WorkFloatConstant.ALL_PUBLIC == Integer.parseInt(this.getNextStepStatus(examWorkflow.getId(), status))) {//全局公示
//            this.generateScores(examWorkflow);
//        }
        addMessage(redirectAttributes, "数据保存成功");
        return "redirect:" + Global.getAdminPath() + "/exam/examWorkflow/appaise/content?standardId=" + standardId + "&workflowId=" + examWorkflow.getId() + "&status=" + status + "&processType=" + processType + "&personType=" + personType + "&result=success&examType=" + request.getParameter("examType");
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

    private void setWeightB(ExamWorkflowDatas examWorkflowData, List<ExamWeightsMain> datasList) {

        Double weight = 100.0;
        String workName = "32";
        for (ExamWeightsMain weights : datasList) {
            String name = weights.getWorkName();
            name = DictUtils.getDictLabel(name, "exam_weigths", "");
            if (null != examWorkflowData.getType()) {
                String type = examWorkflowData.getType();
                String project = examWorkflowData.getProject();
                type = type.replaceAll("[\\t\\n\\r]", "");
                if (StringUtils.isNotBlank(project)){
                    project = project.replaceAll("[\\t\\n\\r]", "");
                }
                if (type.indexOf(name) > -1) {
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



    private void setWeightBeta(ExamWorkflowDatas examWorkflowData,String examType) {
        ExamWeightsMain examWeightsMain = new ExamWeightsMain();
        String companyId = UserUtils.getUser().getCompany().getId();
        ExamWeights examWeights = new ExamWeights();
        examWeights.setKpType(examType);
        String officeId = UserUtils.getUser().getOffice().getId();
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
        if(StringUtils.isNotBlank(examType))
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
            if (null != examWorkflowData.getType()) {
                String type = examWorkflowData.getType();
                String project = examWorkflowData.getProject();
                type = type.replaceAll("[\\t\\n\\r]", "");
                if (StringUtils.isNotBlank(project)){
                    project = project.replaceAll("[\\t\\n\\r]", "");
                }
                if (type.indexOf(name) > -1) {
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
    private void setWeight(ExamWorkflowDatas examWorkflowData) {
//        ExamWeights examWeights = new ExamWeights();
//        List<ExamWeights> weightsList = examWeightsService.findWeightsList(examWeights);
//        String weightsId = "";
//        for (ExamWeights weights : weightsList) {
//            String objs = weights.getDepartmentId();
//            Set<String> departSet = new HashSet<String>();
//            CollectionUtils.addAll(departSet, objs.split(","));
//            String objDepart = "-1";
//            if(null !=examWorkflowData.getObjId() && null != UserUtils.get(examWorkflowData.getObjId())){
//                objDepart = UserUtils.get(examWorkflowData.getObjId()).getOffice().getId();
//            }
//            String fillPersonDepart = "-1";
//            if(null !=examWorkflowData.getFillPersonId() && null != UserUtils.get(examWorkflowData.getFillPersonId())){
//                fillPersonDepart = UserUtils.get(examWorkflowData.getFillPersonId()).getOffice().getId();
//            }
//            if (departSet.contains(objDepart)||departSet.contains(fillPersonDepart)) {
//                weightsId = weights.getId();
//                break;
//            }
//        }
        ExamWeightsMain examWeightsMain = new ExamWeightsMain();
        //examWeightsMain.setEwId(weightsId);
        List<ExamWeightsMain> datasList = examWeightsMainService.findWeightsDataList(examWeightsMain);
        Double weight = 100.0;
        String workName = "32";//其他公共加扣分情况
        for (ExamWeightsMain weights : datasList) {
            String name = weights.getWorkName();
            if (name.equals("7")){
                System.out.println();
            }
            name = DictUtils.getDictLabel(name, "exam_weigths", "");
            if (null != examWorkflowData.getType()) {
                String type = examWorkflowData.getType().toString();
                type = type.replaceAll("[\\t\\n\\r]", "");
                if (type.indexOf(name) > -1) {
                    weight = weights.getWeights();
                    workName = weights.getWorkName().toString();
                    break;
                }
            }
        }
        examWorkflowData.setWorkName(workName);
        examWorkflowData.setWeight(weight);
    }

    //    @RequiresPermissions("exam:examWorkflow:edit")
    @RequestMapping(value = "appraise/adjust")
    public String appraiseAdjust(ExamWorkflow examWorkflow, HttpServletRequest request, HttpServletResponse response, Model model) {
        int status = examWorkflow.getStatus();
        if (null != request.getParameter("history") || "true".equals(request.getParameter("history")) && null != request.getParameter("hisStatus")) {
            model.addAttribute("history", "true");
            model.addAttribute("hisCurStatus", String.valueOf(status));
        }
        model.addAttribute("workflowId", examWorkflow.getId());
        ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
        String standardIds = examWorkflowDefine.getTemplatesIds();
        List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoList(standardIds, examWorkflow, UserUtils.getUser().getId());
//        List<ExamStandardBaseInfo> standardInfoList = new ArrayList<ExamStandardBaseInfo>();
//        for (String id : standardIds.split(",")) {
//            ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(id);
//            standardInfoList.add(examStandardBaseInfo);
//        }
        model.addAttribute("standardInfoList", standardInfoList);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("flowId", examWorkflow.getId());
        param.put("DEL_FLAG_NORMAL", "0");
        List<Map<String, Object>> list = examWorflowSegmentsService.findSegmentsList(param);
        model.addAttribute("segmentsList", list);
        model.addAttribute("status", request.getParameter("status"));
        model.addAttribute("objId", request.getParameter("objId"));
        model.addAttribute("processType", request.getParameter("processType"));
        model.addAttribute("personType", request.getParameter("personType"));
        model.addAttribute("history", request.getParameter("history"));
        return "modules/exam/appraise_adjust";
    }

    /**
     * 考评树
     * @param examWorkflow
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "appraise/adjustIndex")
    public String appraiseAdjustIndexBeta(ExamWorkflow examWorkflow, HttpServletRequest request, HttpServletResponse response, Model model) {
        int status = examWorkflow.getStatus();
        if (null != request.getParameter("history") || "true".equals(request.getParameter("history")) && null != request.getParameter("hisStatus")) {
            model.addAttribute("history", "true");
            model.addAttribute("hisCurStatus", String.valueOf(status));
        }
        model.addAttribute("workflowId", examWorkflow.getId());
//        List<ExamStandardBaseInfo> standardInfoList = new ArrayList<ExamStandardBaseInfo>();
//        for (String id : standardIds.split(",")) {
//            ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(id);
//            standardInfoList.add(examStandardBaseInfo);
//        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("flowId", examWorkflow.getId());
        param.put("DEL_FLAG_NORMAL", "0");
        model.addAttribute("status", request.getParameter("status"));
        model.addAttribute("objId", request.getParameter("objId"));
        model.addAttribute("processType", request.getParameter("processType"));
        model.addAttribute("personType", request.getParameter("personType"));
        model.addAttribute("history", request.getParameter("history"));
        model.addAttribute("flowTemplateId", examWorkflow.getFlowTemplateId());
        return "modules/exam/appraiseAdjustIndexBeta";
//        return "modules/exam/appraise_adjust";
    }

    //TODO @RequiresPermissions("exam:examWorkflow:edit")
    @RequestMapping(value = "appraise/public")
    public String appraisePublic(ExamWorkflow examWorkflow, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("workflowId", examWorkflow.getId());
        ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
        String standardIds = examWorkflowDefine.getTemplatesIds();
        List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoList(standardIds, examWorkflow, UserUtils.getUser().getId());
//        List<ExamStandardBaseInfo> standardInfoList = new ArrayList<ExamStandardBaseInfo>();
//        for (String id : standardIds.split(",")) {
//            ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(id);
//            standardInfoList.add(examStandardBaseInfo);
//        }
        model.addAttribute("standardInfoList", standardInfoList);
        model.addAttribute("status", request.getParameter("status"));
        model.addAttribute("fillPersonId", request.getParameter("fillPersonId"));
        Map<String, Object> objParam = new HashMap<String, Object>();
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
        return "modules/exam/appraise_public";
    }

    /**
     * 下一步状态
     *
     * @param workflowId
     * @param curStatus
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
     * 启动考评
     * @param examWorkflow
     * @param model
     * @param redirectAttributes
     * @return
     */
    //@RequiresPermissions("exam:examWorkflow:edit")
    @RequestMapping(value = "start")
    public String start(ExamWorkflow examWorkflow, Model model, RedirectAttributes redirectAttributes) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("flowId", examWorkflow.getId());
        param.put("DEL_FLAG_NORMAL", "0");
        /*查询考评流程*/
        List<Map<String, Object>> list = examWorflowSegmentsService.findSegmentsList(param);
        if (list == null || list.size()==0){
            /*message = 请点击修改，查看流程考评流程
            * url编码*/
            String message = "%E8%AF%B7%E7%82%B9%E5%87%BB%E4%BF%AE%E6%94%B9%EF%BC%8C%E6%9F%A5%E7%9C%8B%E8%80%83%E8%AF%84%E6%B5%81%E7%A8%8B";
            return "redirect:" + Global.getAdminPath() + "/exam/examWorkflow?examType=" + examWorkflow.getExamType()+"&message="+message;
        }
        Map<String, Object> firstSegment = list.get(0);
        Map<String, Object> updateParam = new HashMap<String, Object>();
        updateParam.put("id", examWorkflow.getId());
        updateParam.put("status", new Integer(firstSegment.get("sort").toString()));
        updateParam.put("operationStatus", new Integer("-1"));//操作状态修改为未处理
        /*流程走完再改状态,先删除再启动*/
//        examWorkflowService.updateStatus(updateParam);
        ExamWorkflowDatas temp = new ExamWorkflowDatas();
        temp.setWorkflowId(examWorkflow.getId());
        examWorkflowDatasService.deleteByWorkflowId(temp);

        //生成考评项数据
        ExamWorkflowSegmentsTask examWorkflowSegmentsTask = new ExamWorkflowSegmentsTask();
        examWorkflowSegmentsTask.setWorkflowId(examWorkflow.getFlowTemplateId());
        /*查询考评流程任务分配*/
        List<ExamWorkflowSegmentsTask> taskList = examWorkflowSegmentsTaskService.findList(examWorkflowSegmentsTask);
        ExamWorkflowDefine define = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
        if (StringUtils.isBlank(define.getTemplatesIds())){
            /*message = 请检查创建的流程是否存在
            * url编码 防止乱码*/
            String message = "%E8%AF%B7%E6%A3%80%E6%9F%A5%E5%88%9B%E5%BB%BA%E7%9A%84%E6%B5%81%E7%A8%8B%E6%98%AF%E5%90%A6%E5%AD%98%E5%9C%A8";
            return "redirect:" + Global.getAdminPath() + "/exam/examWorkflow?examType=" + examWorkflow.getExamType()+"&message="+message;
        }
        List<String> defineList = Arrays.stream(define.getTemplatesIds().split(",")).collect(Collectors.toList());

        // FIXME: 2020/8/20/*此list过大需优化处理,会  下边的循环才是真的慢*/
        List<ExamWorkflowDatas> dataslist = new ArrayList<ExamWorkflowDatas>();
        if (null != taskList && taskList.size() > 0) {
            for (ExamWorkflowSegmentsTask segmentsTask : taskList) {
                String segmentId = segmentsTask.getSegmentId();
                String idsStr = segmentsTask.getIds();
                if (null != idsStr && !"".equals(idsStr)) {
                    String[] ids = idsStr.replaceAll("\r","").replaceAll("\n","").split(",");
                    String[] persons = segmentsTask.getPersonNames().replaceAll("\r","").replaceAll("\n","").split(",");
                    for (int i = 0; i < ids.length; i++) {
                        if ("1".equals(segmentId)) {
                            ExamWorkflowDatas data = new ExamWorkflowDatas();
                            /*以前一直存的segmentsTask.getWorkflowId()  没有问题？？？？*/
                            data.setWorkflowId(examWorkflow.getId());
                            data.setStandardId(segmentsTask.getStandardId());
                            data.setRowId(segmentsTask.getRowNum().toString());
                            data.setIsSelected("0");
                            data.setDetail("");
                            data.setStatus(new Integer(1));
                            data.setOperationStatus(new Integer(-1));
                            //自评人及考核对象
                            String userId = ids[i];
                            String userName = persons[i];
                            data.setFillPersonId(userId);
                            data.setFillPerson(userName);
                            if ("1".equals(examWorkflow.getExamType())) {
                                /*if (UserUtils.get(userId) == null){
                                    System.out.println(userId);
                                }*/
                                data.setObjId(UserUtils.get(userId).getCompany().getId());
                                data.setObjName(UserUtils.get(userId).getCompany().getName());
                            } else {
                                data.setObjId(userId);
                                data.setObjName(userName);
                            }
                            dataslist.add(data);
                        }
                    }
                }
            }
            //// FIXME: 2020/11/25  这两个一循环 ，循环次数炸了。。。
            if (null != dataslist && dataslist.size()>0) {
                examWorkflowSegmentsTask.setWorkflowId(examWorkflow.getFlowTemplateId());
                Date startDate = new Date();
                List<ExamWorkflowSegmentsTask> tempTaskList = new ArrayList<>();
                for (ExamWorkflowDatas data : dataslist) {
//                    examWorkflowSegmentsTask.setRowNum(Integer.valueOf(data.getRowId()));

                    tempTaskList = taskList.stream().filter(item -> item.getStandardId().equals(data.getStandardId()) && item.getRowNum().toString().equals(data.getRowId())).collect(Collectors.toList());
//                    taskList = examWorkflowSegmentsTaskService.findList(examWorkflowSegmentsTask);
                    for (ExamWorkflowSegmentsTask segmentsTask : tempTaskList) {
//                        boolean isWorkflowId = data.getWorkflowId().equals(segmentsTask.getWorkflowId());
//                        if (isWorkflowId) {

                        String segmentId = segmentsTask.getSegmentId();

                        //考核人
                        if ("2".equals(segmentId)  && data.getStandardId().equals(segmentsTask.getStandardId())) {
                            data.setExamPersonId(segmentsTask.getIds());
                            data.setExamPerson(segmentsTask.getPersonNames());
                        }
                        //部门负责人
                        if ("4".equals(segmentId) ) {
                            data.setDepartSignId(segmentsTask.getIds());
                            data.setDepartSign(segmentsTask.getPersonNames());
                        }
                        //分管局领导签字
                        if ("5".equals(segmentId) ) {
                            data.setPartBureausSignId(segmentsTask.getIds());
                            data.setPartBureausSign(segmentsTask.getPersonNames());
                        }
                        //绩效考核领导小组复核及调整
                        if ("6".equals(segmentId) ) {
                            data.setAdjustPersonId(segmentsTask.getIds());
                            data.setAdjustPerson(segmentsTask.getPersonNames());
                        }
                        //局主管领导最终审签
                        if ("7".equals(segmentId) ) {
                            data.setMainBureausSignId(segmentsTask.getIds());
                            data.setMainBureausSign(segmentsTask.getPersonNames());
                        }
                    }
//                    }
                }
                System.out.println("爆炸循环时间------------》"+DateUtils.getTwoDateSecond(startDate,new Date())/1000);
            }
        }else {
            /*message = 请检查任务分配
            * url编码 房子乱码*/
            String message = "%E8%AF%B7%E6%A3%80%E6%9F%A5%E4%BB%BB%E5%8A%A1%E5%88%86%E9%85%8D";
            return "redirect:" + Global.getAdminPath() + "/exam/examWorkflow?examType=" + examWorkflow.getExamType()+"&message="+message;
        }
        if (null != dataslist && dataslist.size()>0) {


            /*启动时，查询是否有被推送的考评项，有则标记，无则添加*//*
            ExamPushHistory pushHistory = new ExamPushHistory();
            pushHistory.setObjId(data.getFillPersonId());
//                pushHistory.setStandardId(data.getStandardId());
//                pushHistory.setRowNum(data.getRowId());
            List<ExamPushHistory> pushHistoryList = examPushHistoryService.findList(pushHistory);
                *//*先判断有没有给推送
                    如果有则判断推送的考评项是否存在
                        考评项存在则进行标记
                        不存在则进行添加
                    没有则不做任何处理
                *//*
            if (pushHistoryList != null && pushHistoryList.size()>0){
                List<ExamPushHistory> filterList = pushHistoryList.stream().filter(examPushHistory -> examPushHistory.getStandardId().equals(data.getStandardId()) && examPushHistory.getRowNum().equals(data.getRowId())).collect(Collectors.toList());
                if (filterList != null && filterList.size()>0){
                    data.setIsAlreadySelected("9");
                }else {
                    ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
                    examWorkflowDatas.setIsAlreadySelected("9");
                }
            }else {
//                    ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
//                    examWorkflowDatas.set
            }*/
            String examType = examWorkflow.getExamType();
            if (examType.equals("7")){
                /*普通民警考核时，获取用户的单位名，根据单位名获取单位账号，由单位账号进行初核*/
                List<String> personList = dataslist.stream().map(ExamWorkflowDatas::getFillPersonId).collect(Collectors.toList());
                String startOfficeId = UserUtils.get(dataslist.get(0).getFillPersonId()).getOffice().getId();
                String endOfficeId = UserUtils.get(dataslist.get(dataslist.size()-1).getFillPersonId()).getOffice().getId();
                if (startOfficeId.equals(endOfficeId)) {
                    User userPolice = userDao.getUnitUserByOfficeId(endOfficeId);
                    for (ExamWorkflowDatas data : dataslist) {
                        if (userPolice != null) {
                            data.setExamPersonId(userPolice.getId());
                            data.setExamPerson(userPolice.getName());
                        }
                    }
                }else {
                    for (ExamWorkflowDatas data :dataslist){
                        String officeId =  UserUtils.get(data.getFillPersonId()).getOffice().getId();
                        User userPolice = userDao.getUnitUserByOfficeId(officeId);
                        if (userPolice != null){
                            data.setExamPersonId(userPolice.getId());
                            data.setExamPerson(userPolice.getName());
                        }
                    }
                }

            }
            /*局考处的 公共模块设置初核人员*/
            /*查询考评的所有模板，对公共标准筛选出考评数据中的数据，通过局处关系对应设置初核人员*/
            if (examType.equals("1")) {
                ExamStandardBaseInfo baseInfo= new ExamStandardBaseInfo();
                baseInfo.setStandardIds(defineList);
                List<ExamStandardBaseInfo> standardBaseInfoList = examStandardBaseInfoService.findPublicInList(baseInfo);
                ExamKpPersonRelation kpPersonRelation = new ExamKpPersonRelation();
                kpPersonRelation.setKpType("1");
                List<ExamKpPersonRelation> relationList = examKpPersonRelationService.findList(kpPersonRelation);
              /*  standardBaseInfoList.stream().forEach(standardBaseInfo -> {
                    dataslist.stream().filter(item -> item.getStandardId().equals(standardBaseInfo.getId())).forEach(item -> {
                        relationList.stream().filter(relation -> relation.getKpUserId().contains(item.getFillPersonId())).forEach(relation ->{
                            item.setExamPerson(relation.getCsUserName());
                            item.setExamPersonId(relation.getCsUserId());
                        });
                    });
                });*/
                for (ExamStandardBaseInfo examStandardBaseInfo : standardBaseInfoList){
                    for (ExamWorkflowDatas data :dataslist){
                        if (data.getStandardId().equals(examStandardBaseInfo.getId())){
                            for (ExamKpPersonRelation relation : relationList){
                                if (relation.getKpUserId().contains(data.getFillPersonId())){
                                    data.setExamPerson(relation.getCsUserName());
                                    data.setExamPersonId(relation.getCsUserId());

                                }
                            }
                        }
                    }
                }

            }


            /*先查询此考评的被考评对象*/
            Date pushStartDate = new Date();
            List<ExamWorkflowSegmentsTask> dfg = taskList.stream().filter(item -> item.getSegmentId().equals("1")).collect(Collectors.toList());
            /*所有被考评对象Id*/
            List<String > ids =new ArrayList<>();
            dfg.stream().forEach(item -> {
                for(String id :item.getIds().split(",")){
                    ids.add(id);
                }
            });

            /*遍历被考评对象  查看是否有推送*/
            List<String> myList = ids.stream().distinct().collect(Collectors.toList());
            myList.removeIf(item -> StringUtils.isBlank(item));

            /*自动考评*/
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            /*模板id */
//            examAutoEvaluation.setModelId(data.getStandardId());
            /*被考评对象Id*/
//            examAutoEvaluation.setEvaluationId(data.getFillPersonId());
            /*考评模板行号*/
//            examAutoEvaluation.setRowNum(data.getRowId());
            /*考评周期 月度 年度*/
            examAutoEvaluation.setPeriod(examWorkflow.getExamCycle());
            /*考评类别*/
            examAutoEvaluation.setEvalType(examWorkflow.getExamType());
            String cycle = examWorkflow.getExamCycle();
            if (StringUtils.isNotBlank(cycle) && cycle.equals("1")){
                examAutoEvaluation.setYear(examWorkflow.getTime().substring(0,4));
                examAutoEvaluation.setMonth(examWorkflow.getTime().substring(examWorkflow.getTime().length()-2));
            }else if (StringUtils.isNotBlank(cycle) && cycle.equals("2")){
                examAutoEvaluation.setYear(examWorkflow.getTime());
            }
            List<ExamAutoEvaluation> autoList = examAutoEvaluationService.findList(examAutoEvaluation);
            Date autoStartDate = new Date();
            /*自动考评*/
            if (dataslist!= null && autoList != null && autoList.size()>0){
                for (ExamWorkflowDatas data : dataslist) {
                    data.setWorkflowId(examWorkflow.getId());

                    /* *//*自动考评*//*
                ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
                *//*模板id *//*
                examAutoEvaluation.setModelId(data.getStandardId());
                *//*被考评对象Id*//*
                examAutoEvaluation.setEvaluationId(data.getFillPersonId());
                *//*考评模板行号*//*
                examAutoEvaluation.setRowNum(data.getRowId());
                *//*考评周期 月度 年度*//*
                examAutoEvaluation.setPeriod(examWorkflow.getExamCycle());
                *//*考评类别*//*
                examAutoEvaluation.setEvalType(examWorkflow.getExamType());
                String cycle = examWorkflow.getExamCycle();
                if (StringUtils.isNotBlank(cycle) && cycle.equals("1")){
                    examAutoEvaluation.setYear(examWorkflow.getTime().substring(0,4));
                    examAutoEvaluation.setMonth(examWorkflow.getTime().substring(examWorkflow.getTime().length()-2));
                }else if (StringUtils.isNotBlank(cycle) && cycle.equals("2")){
                    examAutoEvaluation.setYear(examWorkflow.getTime());
                }*/
                    /*模板ID  被考评对象Id  行号*/
                    List<ExamAutoEvaluation> evaluationList =autoList.stream().filter(item -> item.getModelId()!= null&&item.getModelId().equals(data.getStandardId())
                            &&item.getEvaluationId()!= null&& item.getEvaluationId().equals(data.getFillPersonId()) &&item.getRowNum()!=null&& item.getRowNum().equals(data.getRowId()))
                            .collect(Collectors.toList());
//                List<ExamAutoEvaluation> evaluationList = examAutoEvaluationService.findList(examAutoEvaluation);
                    ExamAutoEvaluation resultEvaluation = new ExamAutoEvaluation();
                    if (evaluationList != null && evaluationList.size()>0){
                        resultEvaluation = evaluationList.get(0);
                    }
                    /*如果当前被考评对象有自动考评的数据就进行自动添加 算分 并且不能修改*/
                    if (evaluationList != null && evaluationList.size()>0){
                        data.setIsSelected("1");
                        data.setIsAlreadySelected("77");
                        data.setSelectPersonId(data.getFillPersonId());
                        data.setValue(resultEvaluation.getScore());
                        String item = (resultEvaluation.getMonth()==null?"":resultEvaluation.getMonth()+"月 ")+resultEvaluation.getDetails();
                        data.setItems(item);
                        if (StringUtils.isNotBlank(resultEvaluation.getAssessId()) && StringUtils.isNotBlank(resultEvaluation.getAssess()) ){
                            data.setExamPersonId(resultEvaluation.getAssessId());
                            data.setExamPerson(resultEvaluation.getAssess());
                        }
                    }
                    /*设置权重*/
                    /*获取不到考评标准项目类型  改为自评保存时设置*/
//                setWeight(data);

                }
            }

            Date end = new Date();
            double autoTime = DateUtils.getTwoDateSecond(autoStartDate,end);
            System.out.println("自动考评耗费时间--------------------------------------------->"+autoTime);

            /*奖惩信息进行自动考评*/  //奖惩单位为党组织未实现
            /*推送*/
            /*ExamJcInfo examJcInfo = new ExamJcInfo();
            if (examType.equals("1") || examType.equals("2") || examType.equals("3") || examType.equals("4")){
                examJcInfo.setJcType("0");
            }else {
                examJcInfo.setJcType("1");
            }*/
            String tempMonth = examWorkflow.getTime().substring(examWorkflow.getTime().length()-2);
            String tempYear = examWorkflow.getTime().substring(0,4);
            int month = Integer.valueOf(tempMonth);
            int year = Integer.valueOf(tempYear);
            /*if(examWorkflow.getExamCycle().equals("1")){
                examJcInfo.setMonth(month);
                examJcInfo.setYear(year);
            }else {
                examJcInfo.setYear(year);
            }
            examJcInfo.setDefineList(defineList);
            List<ExamJcInfo> jCInfoList = examJcInfoService.findExamList(examJcInfo);
            jCInfoList.stream().forEach(item -> {
                dataslist
                        .stream()
                        .filter(data ->
                                data.getFillPersonId().equals(item.getObjId()) &&
                                        data.getStandardId().equals(item.getMyUseModel()) &&
                                        data.getRowId().equals(item.getRowNum())
                        ).forEach(d ->{
                   d.setIsSelected("1");
                   d.setIsAlreadySelected("77");
                   d.setValue(item.getJcCode());
                   d.setItems(item.getJcTypeName());
                });
            });*/

            /*检查情况自动考评*/
            ExamCheckChild examCheckChild = new ExamCheckChild();
            examCheckChild.setUseModel("");
            examCheckChild.setExamType(examType);
            examCheckChild.setExamCycle(cycle);
            tempMonth = examWorkflow.getTime().substring(examWorkflow.getTime().length()-2);
            tempYear = examWorkflow.getTime().substring(0,4);
            month = Integer.valueOf(tempMonth);
            year = Integer.valueOf(tempYear);
            if(examWorkflow.getExamCycle().equals("1")){
                examCheckChild.setMonthStr(tempMonth);
                examCheckChild.setYear(year);
            }else {
                examCheckChild.setYear(year);
            }
            examCheckChild.setStandardList(defineList);
            List<ExamCheckChild> checkChildList = examCheckChildService.findExamCheckChildList(examCheckChild);
            /*检查情况进行自动考评*/
            checkChildList.stream().forEach(item -> {
                dataslist
                        .stream()
                        .filter(data ->
                                data.getFillPersonId().equals(item.getObjId()) &&
                                        data.getStandardId().equals(item.getUseModel()) &&
                                        data.getRowId().equals(item.getRowNum())
                        ).forEach(d ->{
                    d.setIsSelected("1");
                    d.setIsAlreadySelected("77");
                    d.setSelectPersonId(d.getFillPersonId());
                    if (examType.equals("1") || examType.equals("1") || examType.equals("3") ||examType.equals("4") ){
                        d.setValue(item.getDutyUnitScore());
                    }else if (examType.equals("5") || examType.equals("6") ){
                        d.setValue(item.getDutyLeaderScore());
                    }else if (examType.equals("7") ){
                        d.setValue(item.getDutyPersonScore());
                    }
                    d.setItems(item.getScortSituation());
                });
            });


            /*启动的时候根据自评人查询 是否有被推送记录，4
             * 如果有被推送考评项 则把此考评项标记
             * 找不到此考评项则进行添加，进行标识，让其知道是推送的考评项*/


            myList.stream().forEach(id -> {
                ExamPushHistory pushHistory =new ExamPushHistory();
                pushHistory.setObjId(id);
                User user  = UserUtils.get(id);
                if (user == null)
                    return;
                pushHistory.setObjName(user.getName());
                pushHistory.setStatus("1");
                /*只考评指定类型的推送，之前应该是没按着类型考评推送*/
                pushHistory.setExamType(examType);
                List<ExamPushHistory> pushHistoryList = examPushHistoryService.findList(pushHistory);

                /*查询到推送 对考评项进行更新*/
                pushHistoryList.stream().forEach(examPushHistory -> {
                    ExamWorkflowDatas params = new ExamWorkflowDatas();
                    params.setWorkflowId(examWorkflow.getId());
                    params.setFillPersonId(id);
                    params.setRowId(examPushHistory.getRowNum());
                    params.setStandardId(examPushHistory.getStandardId());
                    params.setIsSelected("1");
                    params.setIsAlreadySelected("9");
                    params.setItems(examPushHistory.getItemDetail());
                    params.setPushHistoryId(examPushHistory.getId());
                    params.setSelectPersonId(id);
                    int result = examWorkflowDatasService.updatePush(params);
                    /*考评项中存在推送的信息则更新*/
                    if (result > 0){
                        /*推送历史标记为推送成功*/
                        examPushHistory.setStatus("2");
                        examPushHistoryService.save(examPushHistory);
                    }else {
                        /*推送历史标记为推送失败
                         * 考评数据里添加考评项*/
                        examPushHistory.setStatus("3");
                        examPushHistoryService.save(examPushHistory);
                        ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
                        examWorkflowDatas.setStatus(1);
                        examWorkflowDatas.setIsAlreadySelected("99");
                        examWorkflowDatas.setObjId(id);
                        examWorkflowDatas.setObjName(user.getName());
                        examWorkflowDatas.setRowId(examPushHistory.getRowNum());
                        examWorkflowDatas.setIsSelected("1");
                        examWorkflowDatas.setSelectPersonId(id);
                        examWorkflowDatas.setWorkflowId(examWorkflow.getId());
                        examWorkflowDatas.setFillPersonId(id);
                        examWorkflowDatas.setItems(examPushHistory.getItemDetail());
                        /*局考处时存上companyId */
                        if (examWorkflow.getExamType().equals("1")){
                            examWorkflowDatas.setObjId(user.getCompany().getId());
                            examWorkflowDatas.setObjId(user.getCompany().getName());
                        }
                        examWorkflowDatas.setPushHistoryId(examPushHistory.getId());
                        examWorkflowDatasService.save(examWorkflowDatas);
                    }
                });
            });
            Date pushEndDate = new Date();
            logger.info("推送查询耗时-----------------------------------------------》"+DateUtils.getTwoDateSecond(pushStartDate,pushEndDate));
            /*自动考评，查询examAutoEvaluation,根据当前的考评类型和 姓名或者单位 和模板Id 和选择项Id查出分值 更新到当前的被考评对象*/
            new Thread(new Runnable() {
                @Override
                public void run() {

                }
            }).start();

            /*局考处的  局处关系对应不好 只能给你设置成绩效办审核了*/
            if (examType.equals("1")) {
                dataslist.stream().forEach(item -> {
                    if (StringUtils.isBlank(item.getExamPerson())) {
                        item.setExamPerson("南宁局绩效办");
                        item.setExamPersonId("cca66e1339f14799b01f6db43ed16e16");
                    }
                    examWorkflowDatasService.save(item);
                });
            } else {
                dataslist.stream().forEach(item -> {
                    examWorkflowDatasService.save(item);
                });
            }
            /*考评数据保存完修改考评状态*/
            examWorkflowService.updateStatus(updateParam);
            /*在这里设权重太慢了  得半个小时*/
            /**
             *    Date weightStartDate = new Date();
             *             ExamWeightsMain examWeightsMain = new ExamWeightsMain();
             *             String companyId = UserUtils.getUser().getCompany().getId();
             *             ExamWeights examWeights = new ExamWeights();
             *             examWeights.setKpType(examType);
             *             String officeId = UserUtils.getUser().getOffice().getId();
             *
             * //        String officeId = UserUtils.getUser().getOffice().getId();
             *             if (StringUtils.isNotBlank(examType) && examType.equals("1")) {
             *                 examWeights.setKpType(examType);
             * //            examWeights.setKpChu(companyId);
             *                 examWeights.setKpChu(null);
             *                 examWeights.setDepartmentId(null);
             *             }
             *             if (StringUtils.isNotBlank(examType) && examType.equals("3") && !companyId.equals("156")) {
             *                 examWeights.setKpType(examType);
             *                 examWeights.setKpChu(companyId);
             *                 examWeights.setDepartmentId(officeId);
             *             }
             *             if (StringUtils.isNotBlank(examType) && examType.equals("3") && companyId.equals("156")) {
             *                 examWeights.setKpType(examType);
             *                 examWeights.setKpChu(companyId);
             *                 examWeights.setDepartmentId(null);//北海处考派出所无需判断
             *             }
             *             examWeights = examWeightsService.getWeightByUnit(examWeights);
             *
             *             examWeightsMain.setEwId(examWeights.getId());
             *             //examWeightsMain.setEwId(weightsId);
             *             List<ExamWeightsMain> datasList = examWeightsMainService.findWeightsDataList(examWeightsMain);
             *             for (ExamWorkflowDatas item : dataslist) {
             *                 if (examWeights == null) {
             *                     continue;
             *                 }
             *                 setWeightB(item, datasList);
             *             }
             *             Date weightEndDate = new Date();
             *             double weightTime = DateUtils.getTwoDateSecond(weightStartDate,weightEndDate);
             *             logger.info("推送查询耗时-----------------------------------------------》"+weightTime);
             */
       /*     new Thread(new Runnable() {
                @Override
                public void run() {
                    dataslist.stream().forEach(item -> {
                        setWeightBeta(item,examType);
                    });
                }
            }).start();*/
        }
        //examWorkflowDatasService.batchInsert(dataslist);
        return "redirect:" + Global.getAdminPath() + "/exam/examWorkflow?examType=" + examWorkflow.getExamType();
    }

    //    @RequiresPermissions("exam:examWorkflow:view")
    @RequestMapping(value = {"exam"})
    public String exam(ExamWorkflow examWorkflow, HttpServletRequest request, HttpServletResponse response, Model model) {
        String targetUrl = "modules/exam/examFlowList";
        int status = examWorkflow.getStatus();
        if (null != request.getParameter("history") || "true".equals(request.getParameter("history")) && null != request.getParameter("hisStatus")) {
            model.addAttribute("history", "true");
            model.addAttribute("hisCurStatus", String.valueOf(status));
            if (WorkFloatConstant.ALL_PUBLIC != status && WorkFloatConstant.EXAM_PUBLIC != status) {
                status = Integer.parseInt(request.getParameter("hisStatus").toString());
            }
            if (WorkFloatConstant.EXAM_PUBLIC == status && Integer.parseInt(request.getParameter("hisStatus").toString()) == WorkFloatConstant.SIMPLE_EXAM) {
                status = WorkFloatConstant.SIMPLE_EXAM;
            }
        }
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
                ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
                String standardIds = examWorkflowDefine.getTemplatesIds();
                List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoList(standardIds, examWorkflow, UserUtils.getUser().getId());
                model.addAttribute("processType", "selefAppraise");
                model.addAttribute("standardIds", standardIds);
                model.addAttribute("standardInfoList", standardInfoList);
                targetUrl = "modules/exam/appraise";
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
                targetUrl = "modules/exam/appraise_simple";
            } else if (WorkFloatConstant.EXAM_PUBLIC == status) {
                Map<String, Object> objParam = new HashMap<String, Object>();
                objParam.put("workflowId", examWorkflow.getId());
                List<Map<String, Object>> objDatasList = examWorkflowDatasService.getAppraiseObjDataList(objParam);
                List<Map<String, Object>> objScoreList = examWorkflowDatasService.getAppraiseObjScoreList(objDatasList);
                model.addAttribute("objScoreList", this.getCurrentObjs(objScoreList));
                targetUrl = "modules/exam/public";
            } else if (WorkFloatConstant.DEP_SIGN == status || WorkFloatConstant.PART_SIGN == status || WorkFloatConstant.COMPLET_SIGN == status) {
                ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
                String standardIds = examWorkflowDefine.getTemplatesIds();
                List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoList(standardIds, examWorkflow, UserUtils.getUser().getId());
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

                String processType = "";
                if (WorkFloatConstant.DEP_SIGN == status) {
                    processType = "departSign";
                } else if (WorkFloatConstant.PART_SIGN == status) {
                    processType = "partBureausSign";
                } else if (WorkFloatConstant.COMPLET_SIGN == status) {
                    processType = "mainBureausSign";
                }
                model.addAttribute("processType", processType);
                targetUrl = "modules/exam/sign";
            } else if (WorkFloatConstant.GROUP_ADJUST == status) {
                ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
                String standardIds = examWorkflowDefine.getTemplatesIds();
                List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoList(standardIds, examWorkflow, UserUtils.getUser().getId());
                model.addAttribute("standardInfoList", standardInfoList);
                Map<String, Object> objParam = new HashMap<String, Object>();
                objParam.put("workflowId", examWorkflow.getId());
                objParam.put("group", "obj");
                List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
                model.addAttribute("objList", objList);
                String objId = "";
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
                targetUrl = "modules/exam/appraise_adjust_leader";
            } else if (WorkFloatConstant.ALL_PUBLIC == status) {
                targetUrl = this.allPublic(examWorkflow, model, request, response);
            } else {
                targetUrl = "modules/exam/examFlowList";
            }
            model.addAttribute("workflowId", examWorkflow.getId());
            model.addAttribute("status", status);
            model.addAttribute("segmentsList", list);
            model.addAttribute("personType", request.getParameter("personType"));
            String nextStatus = String.valueOf(WorkFloatConstant.EXAM_END);
            if (status < WorkFloatConstant.ALL_PUBLIC) {
                nextStatus = this.getNextStepStatus(examWorkflow.getId(), examWorkflow.getStatus());
            }
            model.addAttribute("nextStep", nextStatus);
        }
        model.addAttribute("examType", request.getParameter("examType"));
        return targetUrl;
    }

    /*此方法不再使用，已在workflowDataController中实现此功能*/
    @RequestMapping(value = {"examBeta"})
    public String examBeta(ExamWorkflow examWorkflow, HttpServletRequest request, HttpServletResponse response, Model model) {
        String targetUrl = "modules/exam/examFlowList";
        int status = examWorkflow.getStatus();
        ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
        String standardIds = examWorkflowDefine.getTemplatesIds();
        List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoList(standardIds, examWorkflow, UserUtils.getUser().getId());
        model.addAttribute("processType", "selefAppraise");
        model.addAttribute("standardIds", standardIds);
        model.addAttribute("workflowId", examWorkflow.getId());
        model.addAttribute("standardInfoList", standardInfoList);
        model.addAttribute("examType", request.getParameter("examType"));

        targetUrl = "modules/exam/appraiseBeta";
        model.addAttribute("examWorkflow", examWorkflow);
        model.addAttribute("examWorkflowDefine", examWorkflowDefine);

//        model.addAttribute("fillPersonId",examWorkflowDatas.getFillPersonId());

//        standardInfoList.get(0).getObj();
        List<ExamWorkflowDatas> selectedList =new ArrayList<>();
        for (ExamStandardBaseInfo sd:standardInfoList){
            ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
            examWorkflowDatas.setWorkflowId(examWorkflow.getId());
            examWorkflowDatas.setStandardId(sd.getId());
            examWorkflowDatas.setFillPersonId(UserUtils.getUser().getId());
        /*if (!"".equals(objId)) {
            examWorkflowDatas.setObjId(objId);
        } else if (!"".equals(fillPersonId)) {
            examWorkflowDatas.setFillPersonId(fillPersonId);
        } else {
            examWorkflowDatas.setFillPersonId(UserUtils.getUser().getId());
        }*/
            model.addAttribute("fillPersonId",examWorkflowDatas.getFillPersonId());

//        standardInfoList.get(0).getObj();
            List<ExamWorkflowDatas> list = examWorkflowDatasService.findList(examWorkflowDatas);
            selectedList.addAll(list);
        }
        List<ExamWorkflowDatas> workflowDatasList=selectedList.stream().filter(workflowDatas -> workflowDatas.getIsSelected().equals("1")).collect(Collectors.toList());
        workflowDatasList.stream().forEach(examWorkflowDatas -> {
            //获取考评项数据
            Map<String, String> param = new HashMap<String, String>();
            param.put("examStardardId", examWorkflowDatas.getStandardId());
            List<Map<String, String>> rowList=examStandardTemplateDefineService.findTemplateDatas(param,new String[]{examWorkflowDatas.getRowId()});
            for (Map<String, String> item :rowList){
                switch (item.get("column_type")){
                    case "9":
                        examWorkflowDatas.setS1(item.get("item_value"));
                        break;
                    case "4":
                        examWorkflowDatas.setS2(item.get("item_value"));
                        break;
                    case "1":
                        examWorkflowDatas.setS3(item.get("item_value"));
                        break;
                    case "2":
                        examWorkflowDatas.setS4(item.get("item_value"));
                        break;
                    case "3":
                        examWorkflowDatas.setS5(item.get("item_value"));
                        break;
                    case "6":
                        examWorkflowDatas.setS6(item.get("item_value"));
                        break;
                }

            }
        });
       /* standardInfoList.forEach(examStandardBaseInfo -> {
            this.setItems(examWorkflow,examStandardBaseInfo,selectedList,null,request,response);
        });*/
//        this.setItems(examWorkflow,standardInfoList.get(0),selectedList,null,request,response);
        String[] columnList =new String[] {"9","4","1","2","3","6"};
        /*查询已经保存的所有的考评项 */
        model.addAttribute("columnList",columnList);
        model.addAttribute("workflowDatasList",workflowDatasList);


        return targetUrl;
    }

    @RequestMapping(value = {"examStandardList"})
    public String examStandardList(ExamWorkflow examWorkflow, HttpServletRequest request, HttpServletResponse response, Model model) {
        String targetUrl = "modules/exam/term_beta";
        String standardId = request.getParameter("standardId");
        String workflowId = request.getParameter("workflowId");
        String objId = request.getParameter("objId");
        String fillPersonId = request.getParameter("fillPersonId");
        ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(standardId);
        if (examStandardBaseInfo == null){
            return targetUrl;
        }
        int status = examWorkflow.getStatus();
        if (null != request.getParameter("history") || "true".equals(request.getParameter("history")) && null != request.getParameter("hisStatus")) {
            model.addAttribute("history", "true");
            model.addAttribute("hisCurStatus", String.valueOf(status));
            if (WorkFloatConstant.ALL_PUBLIC != status && WorkFloatConstant.EXAM_PUBLIC != status) {
                status = Integer.parseInt(request.getParameter("hisStatus").toString());
            }
            if (WorkFloatConstant.EXAM_PUBLIC == status && Integer.parseInt(request.getParameter("hisStatus").toString()) == WorkFloatConstant.SIMPLE_EXAM) {
                status = WorkFloatConstant.SIMPLE_EXAM;
            }
        }
        /*考评的所有标准*/
        ExamWorkflowDefine define = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
        String s = define.getTemplatesIds();
        List<ExamStandardBaseInfo> infoList = this.getStandardInfoList(s, examWorkflow, UserUtils.getUser().getId());
        model.addAttribute("standardInfoList", infoList);

        /*考评标准的列*/
        ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
        examWorkflowDatas.setWorkflowId(examWorkflow.getId());
        examWorkflowDatas.setStandardId(standardId);
        if (!"".equals(objId)) {
            examWorkflowDatas.setObjId(objId);
        } else if (!"".equals(fillPersonId)) {
            examWorkflowDatas.setFillPersonId(fillPersonId);
        } else {
            examWorkflowDatas.setFillPersonId(UserUtils.getUser().getId());
        }
        examWorkflowDatas.setExamPersonId(UserUtils.getUser().getId());
        model.addAttribute("fillPersonId",examWorkflowDatas.getFillPersonId());

        List<ExamWorkflowDatas> selectedList = examWorkflowDatasService.findList(examWorkflowDatas);
        this.setItems(examWorkflow,examStandardBaseInfo,selectedList,null,request,response);

        workflowId= examWorkflow.getId();
        model.addAttribute("workflowId",workflowId);
        model.addAttribute("standardId",standardId);
        model.addAttribute("objId",objId);
        model.addAttribute("fillPersonId",fillPersonId);
        targetUrl = "modules/exam/term_beta";
        return targetUrl;
    }

    /**
     *
     * @param standardIds 选择的测评标准
     * @param examWorkflow
     * @param personId
     * @return  考评标准
     */
    private List<ExamStandardBaseInfo> getStandardInfoList(String standardIds, ExamWorkflow examWorkflow, String personId) {
        List<ExamStandardBaseInfo> standardInfoList = new ArrayList<ExamStandardBaseInfo>();
        Set<String> examTypeSet = new HashSet<String>();
        //examTypeSet.add("1");     //	局考处
//        examTypeSet.add("2");       //局考局机关
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
                standardInfoList.add(examStandardBaseInfo);
            }
        }
        return standardInfoList;
    }


    /**
     *
     * @param standardIds 选择的测评标准
     * @param examWorkflow
     * @param personId
     * @return  考评标准
     */
    private List<ExamStandardBaseInfo> getStandardInfoListBeta(String standardIds, ExamWorkflow examWorkflow, String personId) {
        List<ExamStandardBaseInfo> standardInfoList = new ArrayList<ExamStandardBaseInfo>();
        Set<String> examTypeSet = new HashSet<String>();
        examTypeSet.add("1");     //	局考处
        examTypeSet.add("2");       //局考局机关
        examTypeSet.add("3");    //	处考队所
        examTypeSet.add("4");       //处考处机关
        examTypeSet.add("5");   //	民警考核
        examTypeSet.add("6");
        examTypeSet.add("7");
        if (examTypeSet.contains(examWorkflow.getExamType())) {
            Map<String, String> param = new HashMap<String, String>();
            param.put("DEL_FLAG_NORMAL", "0");
            param.put("workflowId", examWorkflow.getId());
            /*流程状态*/
           /* switch (examWorkflow.getStatus()) {
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
            }*/
            /*根据考评流程Id和填写人Id查询考评模板*/
            /*不管你是哪里  自评对象能选什么模板  你就能看到什么模板*/
            param.put("fillPersonId", personId);
            List<Map<String, String>> standardList = examWorkflowDatasService.findStandardsDataNum(param);
            for (Map<String, String> standard : standardList) {
                if (null != standard.get("number") && !"0".equals(standard.get("number"))) {
                    ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(standard.get("standardid"));
                    if (examStandardBaseInfo !=null){
                        standardInfoList.add(examStandardBaseInfo);
                    }
                }
            }
        } else {
            for (String id : standardIds.split(",")) {
                ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(id);
                if (examStandardBaseInfo != null){
                    standardInfoList.add(examStandardBaseInfo);
                }
            }
        }
        return standardInfoList;
    }

    private String allPublic(ExamWorkflow examWorkflow, Model model, HttpServletRequest request, HttpServletResponse response) {
        String targetUrl = "";
        /**
         * 局考处、处考队所一套
         * 局考局机关、处考处机关一套
         * 民警考评一套；处领导、中基层领导、民警
         */
        //月度局考处、处考队所考评
        if ("1".equals(examWorkflow.getExamCycle()) && ("1".equals(examWorkflow.getExamType()) || "3".equals(examWorkflow.getExamType()))) {
            //最终结果显示
            Map<String, Object> map = examUnitAllPublicService.getScoreList1(examWorkflow.getId());
            model.addAttribute("conventionScoreList", map.get("conventionScoreList"));
            model.addAttribute("commonSorceList", map.get("commonSorceList"));
            model.addAttribute("weightSorceList", map.get("weightSorceList"));
            model.addAttribute("sumSorceList", map.get("sumSorceList"));
            targetUrl = "modules/exam/all_jukaochu";
        }
        //年度局考处、处考队所考评
        else if ("2".equals(examWorkflow.getExamCycle()) && ("1".equals(examWorkflow.getExamType()) || "3".equals(examWorkflow.getExamType()))) {
            Map<String, Object> map = examUnitAllPublicYearService.getScoreList1(examWorkflow.getId());
            model.addAttribute("conventionScoreList", map.get("conventionScoreList"));
            model.addAttribute("commonSorceList", map.get("commonSorceList"));
            model.addAttribute("weightSorceList", map.get("weightSorceList"));
            model.addAttribute("sumSorceList", map.get("sumSorceList"));
            targetUrl = "modules/exam/all_jukaochu";
        }
        //月度局考局机关、处考处机关考评
        else if ("1".equals(examWorkflow.getExamCycle()) && ("2".equals(examWorkflow.getExamType()) || "4".equals(examWorkflow.getExamType()))) {
            ArrayList<Map<String, String>> list = examUnitAllPublicService.getScoreList2(examWorkflow.getId());
            model.addAttribute("list", list);
            model.addAttribute("workflowName", examWorkflow.getName());
            targetUrl = "modules/exam/all_jukaojujiguan";
        }
        //年度局考局机关、处考处机关考评
        else if ("2".equals(examWorkflow.getExamCycle()) && ("2".equals(examWorkflow.getExamType()) || "4".equals(examWorkflow.getExamType()))) {
            ArrayList<Map<String, String>> list = examUnitAllPublicYearService.getScoreList2(examWorkflow.getId());
            model.addAttribute("list", list);
            model.addAttribute("workflowName", examWorkflow.getName());
            targetUrl = "modules/exam/all_jukaojujiguan";
        }
        //月度处领导、中基层领导、民警
        else if ("1".equals(examWorkflow.getExamCycle()) && "2".equals(examWorkflow.getExamObjectType())) {
            ExamLdScoreMonth examLdScoreMonth = new ExamLdScoreMonth();
            examLdScoreMonth.setWorkflowId(examWorkflow.getId());
            Page<ExamLdScoreMonth> page = examLdScoreMonthService.findPage(new Page<ExamLdScoreMonth>(request, response), examLdScoreMonth);
            model.addAttribute("page", page);
            targetUrl = "modules/exam/examLdScoreMonthList";
            //年度处领导、中基层领导、民警
        } else if ("2".equals(examWorkflow.getExamCycle()) && "2".equals(examWorkflow.getExamObjectType())) {
            ExamLdScore examLdScore = new ExamLdScore();
            examLdScore.setWorkflowId(examWorkflow.getId());
            Page<ExamLdScore> page = examLdScoreService.findPage(new Page<ExamLdScore>(request, response), examLdScore);
            model.addAttribute("page", page);
            targetUrl = "modules/exam/examLdScoreList";
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("flowId", examWorkflow.getId());
        param.put("DEL_FLAG_NORMAL", "0");
        List<Map<String, Object>> segmentsList = examWorflowSegmentsService.findSegmentsList(param);
        model.addAttribute("hisCurStatus", String.valueOf(WorkFloatConstant.ALL_PUBLIC));
        model.addAttribute("status", String.valueOf(WorkFloatConstant.ALL_PUBLIC));
        model.addAttribute("segmentsList", segmentsList);
        model.addAttribute("workflowId", examWorkflow.getId());
        return targetUrl;
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
                if (null != role.getName() && role.getName().indexOf("绩效考核") > -1) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

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
        return "modules/exam/public_detail";
    }

    //    @RequiresPermissions("exam:examWorkflow:view")

    /**
     * 自评 考评的考评项
     * 多个阶段使用此方法
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "appaise/content")
    public String appraiseContent(HttpServletRequest request, HttpServletResponse response, Model model) {
        String standardId = "";
        if (null != request.getParameter("standardId")) {
            standardId = request.getParameter("standardId").toString();
        }
        String standardIds = "";
        if (null != request.getParameter("standardIds")) {
            standardIds = request.getParameter("standardIds").toString();
        }
        String workflowId = "";
        List<ExamWorkflowDatas> selectedList = null;
        String objId = "";
        if (null != request.getParameter("objId")) {
            objId = request.getParameter("objId");
        }
        String fillPersonId = "";
        if (null != request.getParameter("fillPersonId")) {
            fillPersonId = request.getParameter("fillPersonId");
        }
        String ids = UserUtils.getUser().getId();
        if (!"".equals(objId)) {
            ids = objId;
        } else if (!"".equals(fillPersonId)) {
            ids = fillPersonId;
        }
        if (null != request.getParameter("workflowId")) {
            workflowId = request.getParameter("workflowId").toString();
            ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
            examWorkflowDatas.setWorkflowId(workflowId);
            examWorkflowDatas.setStandardId(standardId);
            if (!"".equals(objId)) {
                examWorkflowDatas.setObjId(objId);
            } else if (!"".equals(fillPersonId)) {
                examWorkflowDatas.setFillPersonId(fillPersonId);
            } else {
                examWorkflowDatas.setFillPersonId(UserUtils.getUser().getId());
            }
            model.addAttribute("fillPersonId",examWorkflowDatas.getFillPersonId());

            selectedList = examWorkflowDatasService.findList(examWorkflowDatas);
        }
        if (null != standardId) {
            ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(standardId);
            ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
            this.setItems(examWorkflow, examStandardBaseInfo, selectedList, ids, request, response);
            model.addAttribute("modleType", examStandardBaseInfo.getModelType());
        }
        model.addAttribute("standardId", standardId);
        model.addAttribute("standardIds", standardIds);
        model.addAttribute("workflowId", workflowId);
        String status = String.valueOf(WorkFloatConstant.SIMPLE_EXAM);
        if (null != request.getParameter("status") && "3".equals(request.getParameter("status"))) {
            ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
            if (this.isExamRole(UserUtils.getUser().getRoleList(), examWorkflow.getExamType())) {
                //model.addAttribute("status", "2");
                status = "2";
            }
            status =  String.valueOf(WorkFloatConstant.SIMPLE_EXAM);
        } else if (null != request.getParameter("status")) {
            //model.addAttribute("status", request.getParameter("status"));
            status = request.getParameter("status");
        }
        model.addAttribute("status", status);
        model.addAttribute("objId", objId);
        if (null != request.getParameter("objName")) {
            model.addAttribute("objName", request.getParameter("objName"));
        }
        if (null != request.getParameter("examType")) {
            model.addAttribute("examType", request.getParameter("examType"));
        }
        String processUrl = "modules/exam/appraise_content";
        if (request.getParameter("processType") != null) {
            String processType = request.getParameter("processType").toString();
            if ("appraiseExam".equals(processType)) {
                /*审核页面*/
                processUrl = "modules/exam/appraise_exam_content";
//                processUrl = "modules/exam/appraise_exam_content_beta";
            } else if ("appraisePublic".equals(processType)) {
                processUrl = "modules/exam/appraise_public_content";
            } else if ("departSign".equals(processType)) {
                processUrl = "modules/exam/sign_content2";
            } else if ("partBureausSign".equals(processType)) {
                processUrl = "modules/exam/sign_content_part2";
            } else if ("mainBureausSign".equals(processType)) {
                processUrl = "modules/exam/sign_content_main2";
            } else if ("selefAppraise".equals(processType)) {
                /*自评页面*/
                processUrl = "modules/exam/appraise_content_beta";
//                processUrl = "modules/exam/appraise_content";
            } else if ("groupAdjust".equals(processType)) {
                processUrl = "modules/exam/appraise_adjust_content_leader";
            } else if ("publicDetail".equals(processType)) {
                processUrl = "modules/exam/public_detail_content";
            }

            model.addAttribute("processType", processType);
        }
        if (null != request.getParameter("result")) {
            model.addAttribute("result", request.getParameter("result"));
        }
        //局领导列表
        //List<User> userList = this.getLeaders(examWorkflowService.get(workflowId));
        //model.addAttribute("userList", userList);
        ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
        String preStatus = String.valueOf(WorkFloatConstant.SELF_EXAM);
        if (examWorkflow.getStatus() < WorkFloatConstant.ALL_PUBLIC) {
            preStatus = this.getPreStepStatus(examWorkflow.getId(), examWorkflow.getStatus());
            model.addAttribute("preStep", preStatus);
        }

        String nextStatus = String.valueOf(WorkFloatConstant.EXAM_END);
        if (examWorkflow.getStatus() < WorkFloatConstant.ALL_PUBLIC) {
            nextStatus = this.getNextStepStatus(examWorkflow.getId(), examWorkflow.getStatus());
            model.addAttribute("nextStep", nextStatus);
        }
        String NNextStep = this.getNextStepStatus(examWorkflow.getId(), Integer.parseInt(nextStatus));
        if (nextStatus.equals(WorkFloatConstant.EXAM_PUBLIC)) {
            this.setPersonModel(nextStatus, examWorkflow.getExamType(), selectedList, model);
        } else {
            this.setPersonModel(NNextStep, examWorkflow.getExamType(), selectedList, model);
        }
        model.addAttribute("nnextStep", NNextStep);

        //根据状态查询当前用户是否可以编辑当前数据
        Map<String, Object> param = new HashMap<>();
        param.put("status", new Integer(status));
        param.put("personId", UserUtils.getUser().getId());
        if (null != objId && !"".equals(objId)) {
            param.put("objId", objId);
        } else if (null != fillPersonId) {
            param.put("fillPersonId", fillPersonId);
        }
        param.put("isNotCommit", "1");
        param.put("DEL_FLAG_NORMAL", "0");
        param.put("workflowId", workflowId);
        param.put("standardId", standardId);
        //param.put("isSelected", "1");

        Map<String, Object> datasNumber = examWorkflowDatasService.selectNumberByInfo(param);
        String isEdit = "false";
        if (null != datasNumber && datasNumber.size() > 0 && Integer.parseInt(datasNumber.get("number").toString()) > 0) {
            isEdit = "true";
        }
        model.addAttribute("isEdit", isEdit);
        if (null != request.getParameter("history")) {
            model.addAttribute("history", request.getParameter("history").toString());
            Map<String, String> sParam = new HashMap<String, String>();
            String personId = UserUtils.getUser().getId();
            sParam.put("id", examWorkflow.getId());
            sParam.put("personId", personId);
            List<Map<String, String>> datasList = examWorkflowService.selectByPerson(sParam);
            if (examWorkflow.getStatus() == WorkFloatConstant.EXAM_PUBLIC || examWorkflow.getStatus() == WorkFloatConstant.COMPLET_SIGN) {
                model.addAttribute("isSave", String.valueOf(this.isSave(examWorkflow, datasList, personId)));
            }
        }
        return processUrl;
    }

    @RequestMapping(value = "appaise/indexBeta")
    public String appraiseIndexBeta(HttpServletRequest request, HttpServletResponse response, Model model) {
        if (null != request.getParameter("fillPersonId")) {
            String fillPersonId = request.getParameter("fillPersonId").toString();
            model.addAttribute("fillPersonId",fillPersonId);
        }
        if (null != request.getParameter("objName")) {
            String objName = request.getParameter("objName").toString();
            model.addAttribute("objName",objName);
        }
        if (null != request.getParameter("standardId")) {
            String standardId = request.getParameter("standardId").toString();
            model.addAttribute("standardId",standardId);
        }
        if (null != request.getParameter("workflowId")) {
            String workflowId = request.getParameter("workflowId").toString();
            model.addAttribute("workflowId",workflowId);
        }
        if (null != request.getParameter("status")) {
            String status = request.getParameter("status").toString();
            model.addAttribute("status",status);
        }
        if (null != request.getParameter("history")) {
            String history = request.getParameter("history").toString();
            model.addAttribute("history",history);
        }
        if (null != request.getParameter("personType")) {
            String personType = request.getParameter("personType").toString();
            model.addAttribute("personType",personType);
        }
        if (null != request.getParameter("processType")) {
            String processType = request.getParameter("processType").toString();
            model.addAttribute("processType",processType);
        }
        return "modules/exam/appraiseAdjustIndexBeta";
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
     * 设置考评项目格式
     *
     * @param examStandardBaseInfo
     * @param request
     * @param response
     */
    private void setItems(ExamWorkflow examWorkflow, ExamStandardBaseInfo examStandardBaseInfo, List<ExamWorkflowDatas> selectedList, String ids, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> dparam = new HashMap<String, String>();
        dparam.put("examStardardId", examStandardBaseInfo.getId());
        ExamStandardTemplateDefine entity = examStandardTemplateDefineService.getNew(dparam);
        if (null != entity) {
            //获取考评项数据格式列
            ExamStandardTemplateItemDefine examStandardTemplateItemDefine = new ExamStandardTemplateItemDefine();
            examStandardTemplateItemDefine.setTemplateId(entity.getId());
            Page<ExamStandardTemplateItemDefine> page = null;
            Page<ExamStandardTemplateItemDefine> pageParam = new Page<ExamStandardTemplateItemDefine>(request, response, -1);
            pageParam.setOrderBy("column_order");
            page = examStandardTemplateItemDefineService.findPage(pageParam, examStandardTemplateItemDefine);
            List<ExamStandardTemplateItemDefine> columnList = page.getList();
            /*根据考评标准内容查询时使用*/

            Optional<ExamStandardTemplateItemDefine> temp= columnList.stream().filter(itemDefine -> itemDefine.getColumnType().equals("1")).findFirst();
            /*考评标准模板中的 考评标准 列 Id*/

            String standardId ;
            /*中基层领导 存在temp为空的情况*/
            if (!temp.isPresent()){
                standardId = "";
            }else {
                standardId = temp.get().getId();
            }
            request.setAttribute("columnList", columnList);

            //获取考评项数据
            Map<String, String> param = new HashMap<String, String>();
            param.put("examStardardId", examStandardBaseInfo.getId());
            List<Map<String, String>> list = examStandardTemplateDefineService.findTemplateDatas(param);
            List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
            Map<String, String> rowMap = null;
            String rowNum = "-1";
            for (Map<String, String> item : list) {
                if (!rowNum.equals(String.valueOf(item.get("row_num")))) {
                    rowNum = String.valueOf(item.get("row_num"));
                    rowMap = new HashMap<String, String>();
                    rowMap.put("row_num", rowNum);
                    rowList.add(rowMap);
                }
                rowMap.put(item.get("item_id"), item.get("item_value"));
            }

            List<Map<String, String>> taskRowlist = new ArrayList<Map<String, String>>();
            int selectedNumber = 0;
            if (null != selectedList) {
                for (ExamWorkflowDatas selectedItem : selectedList) {
                    if (null != rowList) {
                        for (Map<String, String> item : rowList) {
                            if (selectedItem.getRowId().equals(item.get("row_num").toString())) {
                                if ("1".equals(selectedItem.getIsSelected())) {
                                    selectedNumber++;
                                }
                                item.put("isSelected", selectedItem.getIsSelected());
                                item.remove("value");
                                item.put("value", selectedItem.getValue());
                                item.remove("detail");
                                item.put("detail", selectedItem.getDetail());
                                item.remove("path");
                                item.put("path", selectedItem.getPath());
                                item.remove("id");
                                item.put("id", selectedItem.getId());
                                item.remove("operationStatus");
                                item.put("operationStatus", String.valueOf(selectedItem.getOperationStatus()));
                                item.remove("fillPerson");
                                item.put("fillPerson", selectedItem.getFillPerson());
                                item.remove("examPerson");
                                item.put("examPerson", selectedItem.getExamPerson());
                                item.remove("departSign");
                                item.put("departSign", selectedItem.getDepartSign());
                                item.remove("partBureausSign");
                                item.put("partBureausSign", selectedItem.getPartBureausSign());
                                item.remove("adjustPerson");
                                item.put("adjustPerson", selectedItem.getAdjustPerson());
                                item.remove("mainBureausSign");
                                item.put("mainBureausSign", selectedItem.getMainBureausSign());
                                item.remove("fillPersonId");
                                item.put("fillPersonId", selectedItem.getFillPersonId());
                                item.remove("examPersonId");
                                item.put("examPersonId", selectedItem.getExamPersonId());
                                item.remove("departSignId");
                                item.put("departSignId", selectedItem.getDepartSignId());
                                item.remove("partBureausSignId");
                                item.put("partBureausSignID", selectedItem.getPartBureausSignId());
                                item.remove("adjustPersonId");
                                item.put("adjustPersonId", selectedItem.getAdjustPersonId());
                                item.remove("mainBureausSignId");
                                item.put("mainBureausSignId", selectedItem.getMainBureausSignId());
                                item.put("status", String.valueOf(selectedItem.getStatus()));
                                item.put("items", selectedItem.getItems());
                                taskRowlist.add(item);
                            }
                        }
                    }
                }
            }

            //获取上级检查数据
//            ExamCheck examCheck = new ExamCheck();
//            examCheck.setUseModel(examStandardBaseInfo.getId());
//            List<ExamCheck> checkList = examCheckService.findList(examCheck);
//            if (null != checkList) {
//                for (ExamCheck check : checkList) {
//                    String checkTime = examWorkflow.getTime();
//                    if ("1".equals(examWorkflow.getExamType())) {
//                        checkTime = checkTime.substring(0, 4);
//                    } else {
//                        checkTime = checkTime.substring(0, 4) + checkTime.substring(6, 7);
//                    }
//                    for (Map<String, String> task : taskRowlist) {
//                        if (check.getChooseOptions().equals(task.get("row_num")) && checkTime.equals(examWorkflow.getTime())) {
//                            task.remove("isSelected");
//                            task.put("isSelected", "1");
//                        }
//                    }
//                }
//            }
            request.setAttribute("selectedNumber", selectedNumber);
//            JSONObject jsonObject = JSONObject.fromObject(obj);
            /*替换特殊字符串  临时 未优化*/
            taskRowlist.stream().forEach(map -> {
                map.forEach((key, value) -> {
                    if (StringUtils.isNotBlank(value) && (value.contains("\r") || value.contains("\n") || value.contains("\"")|| value.contains("\\"))){
                        value=value.replaceAll("\r","");
                        value=value.replaceAll("\n","");
                        value = value.replaceAll("\"","“");
                        value = value.replace("\\","");

                        map.put(key,value);
                    }
                });
            });
            if (StringUtils.isNotBlank(examWorkflow.getCondition())) {

                taskRowlist = taskRowlist.stream().filter(map -> map.get(standardId).contains(examWorkflow.getCondition())).collect(Collectors.toList());
            }
            request.setAttribute("rowlist", taskRowlist);
            request.setAttribute("rowlistJson", JSONObject.toJSONString(taskRowlist));
        }
    }

    // @RequiresPermissions("exam:examWorkflow:edit")

    /**
     * 更新考评流程环节
     *      当全局公示时，计算分值存入到结果表
     * @param examWorkflow
     * @param request
     * @return
     */
    @RequestMapping("updateStatus")
    public String updateStatus(ExamWorkflow examWorkflow, HttpServletRequest request,RedirectAttributes redirectAttributes) {
        int status = examWorkflow.getStatus();
        String message = null;//存储错误信息
        /*结束自评时，环节更新到下一状态，不能在初核状态上跟新到下一状态*/
        /*if (1 == status) {
            status = 2;
        }*/
        status = Integer.parseInt(this.getNextStepStatus(examWorkflow.getId(), status));
        /*当最终结果公示连续操作时，不能达到考评已结束状态*/
        if (status == 99 || examWorkflow.getStatus()==8){
            return "redirect:" + Global.getAdminPath() + "/exam/examWorkflow/?examType=" + request.getParameter("examType");
        }
        Map<String, Object> uptParam = new HashMap<String, Object>();
        uptParam.put("id", examWorkflow.getId());
        uptParam.put("status", new Integer(status));
        examWorkflowService.updateStatus(uptParam);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("workflowId", examWorkflow.getId());
        param.put("status", new Integer(status));
        param.put("condition","status < "+new Integer(status));
        examWorkflowDatasService.updateStatus(param);
//        if ("1".equals(request.getParameter("flag"))) {//全局公示
//            examUnitAllPublicService.handleScore(examWorkflow.getId(), examWorkflow.getExamCycle(), examWorkflow.getExamType());
//        }
        /*计算分值存入结果表*/
        if (WorkFloatConstant.ALL_PUBLIC == status) {//全局公示
            try {
                this.generateScores(examWorkflow);
            }catch (Exception e){
                status = examWorkflow.getStatus();
                uptParam.put("status", new Integer(status));
                examWorkflowService.updateStatus(uptParam);
                param.put("status", new Integer(status));
                param.put("condition","status < 99");
                examWorkflowDatasService.updateStatus(param);
                e.printStackTrace();
                logger.error(ExceptionInfoUtils.getExceptionCauseInfo(e));
                message = "最终结果公示失败,异常信息："+ ExceptionInfoUtils.getExceptionCauseInfo(e);
            }
            String examCycle = examWorkflow.getExamCycle();
            String examType = examWorkflow.getExamType();
            /*如果是年度个人考评 则计算绩效等次*/
            if (StringUtils.isNotBlank(examCycle) && examCycle.equals("2") &&
                StringUtils.isNotBlank(examType) && (examType.equals("5") || examType.equals("6") || examType.equals("7"))){
                judgeGradesById(examWorkflow.getId());
            }
        }
        redirectAttributes.addAttribute("message",message);
        return "redirect:" + Global.getAdminPath() + "/exam/examWorkflow/?examType=" + request.getParameter("examType");
    }

    public void judgeGradesById(String workflowId) {
        //2
        String notPass=DictUtils.getDictValue("不达标","performance_grade","不达标");
        //3
        String basicPass=DictUtils.getDictValue("基本达标","performance_grade","基本达标");
        //4
        String pass=DictUtils.getDictValue("达标","performance_grade","达标");
        //5
        String excellent=DictUtils.getDictValue("优秀","performance_grade","优秀");
        //1
        String unDone=DictUtils.getDictValue("未评定","performance_grade","未评定");
        ExamLdScore examLdScore = new ExamLdScore();
        examLdScore.setWorkflowId(workflowId);
        List<ExamLdScore> list=examLdScoreService.findList(examLdScore);
        List<ExamLdScoreMonth> monthList = examLdScoreMonthService.findMonthList(new ExamLdScoreMonth());
        for(ExamLdScore score:list){
            List<ExamLdScoreMonth> monthScoreList = monthList.stream().filter(item -> item.getPersonId().equals(score.getPersonId())).collect(Collectors.toList());
            monthScoreList.stream().forEach(item ->{
                String  month = item.getMonth();
                String monthScore = item.getScore();
                if (StringUtils.isNotBlank(month)){
                    switch (month){
                        case "01":
                            score.setJanuaryScore(monthScore);
                            break;
                        case "02":
                            score.setFebruaryScore(monthScore);
                            break;
                        case "03":
                            score.setMarchScore(monthScore);
                            break;
                        case "04":
                            score.setAprilScore(monthScore);
                            break;
                        case "05":
                            score.setMayScore(monthScore);
                            break;
                        case "06":
                            score.setJuneScore(monthScore);
                            break;
                        case "07":
                            score.setJulyScore(monthScore);
                            break;
                        case "08":
                            score.setAugustScore(monthScore);
                            break;
                        case "09":
                            score.setSeptemberScore(monthScore);
                            break;
                        case "10":
                            score.setOctoberScore(monthScore);
                            break;
                        case "11":
                            score.setNovemberSocre(monthScore);
                            break;
                        case "12":
                            score.setDecemberScore(monthScore);
                            break;
                    }
                }
            });
            if (StringUtils.isEmpty(score.getSumScore())){
                score.setGrades(unDone);
                examLdScoreService.save(score);
                continue;
            }
            float s= Float.parseFloat(score.getSumScore());
            if (s<60){
                score.setGrades(notPass);
            }else if(s<70){
                score.setGrades(basicPass);
            }else{
                score.setGrades(pass);
            }
            examLdScoreService.save(score);
        }
        return ;

    }

    /**
     * 计算分值存入结果表
     *
     * @param examWorkflow
     */
    private void generateScores(ExamWorkflow examWorkflow) {
        //单位月度考评
        if ("1".equals(examWorkflow.getExamCycle()) && "1".equals(examWorkflow.getExamObjectType())) {
            Map<String, String> param = new HashMap<String, String>();
            param.put("workflowId", examWorkflow.getId());
            param.put("DEL_FLAG_NORMAL", "0");
            if("3".equals(examWorkflow.getExamType())){
                //处考派出所
                String fillPersonId = examWorkflowDatasService.findFirstFillPersonId(examWorkflow.getId());
                User user = UserUtils.get(fillPersonId);
                param.put("kpChu",user.getCompany().getId());
                if("156".equals(user.getCompany().getId())){
                    param.put("departmentId",user.getOffice().getId());//21年7月19日，北海处绩效办、局绩效办负责人提出北海处派出所区分高铁、普铁分别进行权重考评
                }else{
                    param.put("departmentId",user.getOffice().getId());
                }
                List<Map<String, Object>> list = examWorkflowDatasService.calScores2(param);
                //examUnitAllPublicService.monthOrgDatasSave(list, examWorkflow);
               // examUnitAllPublicService.monthOrgDatasSave2(list, examWorkflow);
                examUnitAllPublicService.ckdsMonthOrgDatasSaveBeta(list, examWorkflow);
            }else if("1".equals(examWorkflow.getExamType())){
                //List<Map<String, Object>> list = examWorkflowDatasService.calScores2(param);
                //examUnitAllPublicService.monthOrgDatasSave(list, examWorkflow);
                //examUnitAllPublicService.monthOrgDatasSave2(list, examWorkflow);
                examUnitAllPublicService.monthOrgDatasSaveBeta(examWorkflow);
            }else{
//                List<Map<String, String>> list = examWorkflowDatasService.calScores(param);
                List<Map<String, Object>> list = examWorkflowDatasService.calScoresJGUnit(param);
                examUnitAllPublicService.monthOrgDatasSave(list, examWorkflow);
            }

        }
        //单位年度考评
        else if ("2".equals(examWorkflow.getExamCycle()) && "1".equals(examWorkflow.getExamObjectType())) {
            Map<String, String> param = new HashMap<String, String>();
            param.put("workflowId", examWorkflow.getId());
            param.put("DEL_FLAG_NORMAL", "0");
            //计算月平均值
            List<Map<String, String>> yearDatasList = new ArrayList<Map<String, String>>();
            if("1".equals(examWorkflow.getExamType()) || "3".equals(examWorkflow.getExamType())){
                List<Map<String, Object>> yearList;
                if("3".equals(examWorkflow.getExamType())){
                    //处考派出所
                    String fillPersonId = examWorkflowDatasService.findFirstFillPersonId(examWorkflow.getId());
                    User user = UserUtils.get(fillPersonId);
                    param.put("kpChu",user.getCompany().getId());
                    if("156".equals(user.getCompany().getId())){
                        param.put("departmentId",user.getOffice().getId());//21年7月19日，北海处绩效办、局绩效办负责人提出北海处派出所区分高铁、普铁分别进行权重考评
                    }else{
                        param.put("departmentId",user.getOffice().getId());
                    }
                    yearList = examWorkflowDatasService.calScores2(param);
                    //examUnitAllPublicYearService.yearUnitSave2(yearList, examWorkflow);
                    examUnitAllPublicYearService.ckdsYearUnitDatasSaveBeta(yearList, examWorkflow);
                }else{
                    //局考处
                    /*yearList = examWorkflowDatasService.calScores2(param);
                    examUnitAllPublicYearService.yearUnitSave2(yearList, examWorkflow);*/
                    examUnitAllPublicYearService.yearUnitSaveBeta(examWorkflow);
                }
                //获取年度所有月份工作项分数
                Map<String, String> yMonthParam = new HashMap<String, String>();
                //yMonthParam.put("workflowId",examWorkflow.getId());
                String time = examWorkflow.getTime();
                yMonthParam.put("year", time.substring(0, 4));
                yMonthParam.put("examType", examWorkflow.getExamType());
                yMonthParam.put("examObjectType", examWorkflow.getExamObjectType());
                yMonthParam.put("examCycle", "1");
                List<Map<String, String>> list = examUnitAllPublicService.findUnitYearScores(yMonthParam);

                //计算月平均值
                //List<Map<String, String>> yearDatasList = new ArrayList<Map<String, String>>();
                /*if (null != yearDatasList && null != list) {
                    String unitId = "";
                    String workName = "";
                    double hundred = 0;
                    double zsqzhScore = 0;
                    double publicScore = 0;
                    int num = 0;
                    Map<String, String> yearMap = null;
                    for (int i = 0; i < list.size(); i++) {
                        Map<String, String> item = list.get(i);
                        if (!unitId.equals(item.get("unitId").toString()) || !workName.equals(item.get("workName").toString())) {
                            unitId = item.get("unitId");
                            workName = item.get("workName");
                            yearMap = new HashMap<String, String>();
                            yearMap.put("unitId", unitId);
                            yearMap.put("uniteName", item.get("unitName"));
                            yearMap.put("workName", workName);
                            yearMap.put("analysis", item.get("analysis"));
                            yearMap.put("workflow_id", item.get("workflowId"));
                            yearMap.put("valueType", item.get("valueType"));
                            num = 0;
                            yearDatasList.add(yearMap);
                        }
                        if (null != item.get("hundred")) {
                            Object obj = item.get("hundred");
                            hundred += Double.parseDouble(obj.toString());
                        }
                        if (null != item.get("zsqzhScore")) {
                            Object obj = item.get("zsqzhScore");
                            zsqzhScore += Double.parseDouble(obj.toString());
                        }
//                    if (null != item.get("publicScore")) {
//                        Object obj = item.get("publicScore");
//                        publicScore += Double.parseDouble(obj.toString());
//                    }
                        num++;
                        if (i == list.size() - 1 || !unitId.equals(list.get(i).get("unitId").toString()) || !workName.equals(list.get(i).get("workName").toString())) {
                            yearMap.put("hundred", String.valueOf(hundred / num));
                            yearMap.put("zsqzhScore", String.valueOf(zsqzhScore / num));
                            //yearMap.put("publicScore", String.valueOf(publicScore / num));
                        }
                    }
                }
                if (null != yearDatasList && yearDatasList.size()>0) {
                    for (Map<String, String> mYearData : yearDatasList) {
                        if (null != yearList) {
                            for (Map<String, Object> yearData : yearList) {
                                if (mYearData.get("unitId").equals(yearData.get("unitId")) && mYearData.get("workName").equals(yearData.get("workName"))) {
                                    Object mHundred = mYearData.get("hundred");
                                    Object yHundred = yearData.get("origalValue");
                                    double hundred = Double.parseDouble(mHundred.toString()) * MONTHWEIGHT + Double.parseDouble(yHundred.toString()) * YEARWEIGHT;
                                    mYearData.remove("hundred");
                                    mYearData.put("hundred", String.valueOf(hundred));
                                    Object mZsqzhScore = mYearData.get("zsqzhScore");
                                    Object yZsqzhScore = yearData.get("finalValue");
                                    double zsqzhScore = Double.parseDouble(mZsqzhScore.toString()) * MONTHWEIGHT + Double.parseDouble(yZsqzhScore.toString()) * YEARWEIGHT;
                                    mYearData.remove("zsqzhScore");
                                    mYearData.put("zsqzhScore", String.valueOf(zsqzhScore));
                                }
                            }
                        }
                    }
                }else {
                    try{
                        if (null != yearList) {
                            for (Map<String, Object> yearData : yearList) {
                                Map<String,String> result = new HashMap<>();
                                Object yHundred = yearData.get("origalValue");
                                double hundred =  + Double.parseDouble(yHundred.toString()) * YEARWEIGHT;
                                Object yZsqzhScore = yearData.get("finalValue");
                                double zsqzhScore = + Double.parseDouble(yZsqzhScore.toString()) * YEARWEIGHT;
                                result.put("hundred", String.valueOf(hundred));
                                result.put("zsqzhScore", String.valueOf(zsqzhScore));
                                result.put("unitName",yearData.get("objName").toString());
                                result.put("unitId",yearData.get("objId").toString());
                                result.put("workName",yearData.get("workName").toString());
                                result.put("valueType",yearData.get("valueType").toString());
                                yearDatasList.add(result);
                            }

                        }
                    }catch (NullPointerException e){

                    }

                }*/
            }
            /*else{
                List<Map<String, String>> yearList = examWorkflowDatasService.calScores(param);
                examUnitAllPublicService.monthOrgDatasSave(yearList, examWorkflow);
                //获取年度所有月份工作项分数
                Map<String, String> yMonthParam = new HashMap<String, String>();
                //yMonthParam.put("workflowId",examWorkflow.getId());
                String time = examWorkflow.getTime();
                yMonthParam.put("year", time.substring(0, 4));
                yMonthParam.put("examType", examWorkflow.getExamType());
                yMonthParam.put("examObjectType", examWorkflow.getExamObjectType());
                yMonthParam.put("examCycle", "1");
                List<Map<String, String>> list = examUnitAllPublicService.findUnitYearScores(yMonthParam);


                if (null != yearDatasList && null != list) {
                    String unitId = "";
                    String workName = "";
                    double hundred = 0;
                    double zsqzhScore = 0;
                    double publicScore = 0;
                    int num = 0;
                    Map<String, String> yearMap = null;
                    for (int i = 0; i < list.size(); i++) {
                        Map<String, String> item = list.get(i);
                        if (!unitId.equals(item.get("unitId").toString()) || !workName.equals(item.get("workName").toString())) {
                            unitId = item.get("unitId");
                            workName = item.get("workName");
                            yearMap = new HashMap<String, String>();
                            yearMap.put("unitId", unitId);
                            yearMap.put("uniteName", item.get("unitName"));
                            yearMap.put("workName", workName);
                            yearMap.put("analysis", item.get("analysis"));
                            yearMap.put("workflow_id", item.get("workflowId"));
                            yearMap.put("valueType", item.get("valueType"));
                            num = 0;
                            yearDatasList.add(yearMap);
                        }
                        if (null != item.get("hundred")) {
                            Object obj = item.get("hundred");
                            hundred += Double.parseDouble(obj.toString());
                        }
                        if (null != item.get("zsqzhScore")) {
                            Object obj = item.get("zsqzhScore");
                            zsqzhScore += Double.parseDouble(obj.toString());
                        }
//                    if (null != item.get("publicScore")) {
//                        Object obj = item.get("publicScore");
//                        publicScore += Double.parseDouble(obj.toString());
//                    }
                        num++;
                        if (i == list.size() - 1 || !unitId.equals(list.get(i).get("unitId").toString()) || !workName.equals(list.get(i ).get("workName").toString())) {
                            yearMap.put("hundred", String.valueOf(hundred / num));
                            yearMap.put("zsqzhScore", String.valueOf(zsqzhScore / num));
                            //yearMap.put("publicScore", String.valueOf(publicScore / num));
                        }
                    }
                }
                if (null != yearDatasList && yearDatasList.size()>0) {
                    for (Map<String, String> mYearData : yearDatasList) {
                        if (null != yearList) {
                            for (Map<String, String> yearData : yearList) {
                                if (mYearData.get("unitId").equals(yearData.get("unitId")) && mYearData.get("workName").equals(yearData.get("workName"))) {
                                    Object mHundred = mYearData.get("hundred");
                                    Object yHundred = yearData.get("origalValue");
                                    double hundred = Double.parseDouble(mHundred.toString()) * MONTHWEIGHT + Double.parseDouble(yHundred.toString()) * YEARWEIGHT;
                                    mYearData.remove("hundred");
                                    mYearData.put("hundred", String.valueOf(hundred));
                                    Object mZsqzhScore = mYearData.get("zsqzhScore");
                                    Object yZsqzhScore = yearData.get("finalValue");
                                    double zsqzhScore = Double.parseDouble(mZsqzhScore.toString()) * MONTHWEIGHT + Double.parseDouble(yZsqzhScore.toString()) * YEARWEIGHT;
                                    mYearData.remove("zsqzhScore");
                                    mYearData.put("zsqzhScore", String.valueOf(zsqzhScore));
                                }
                            }
                        }
                    }
                }else {
                    try{
                        if (null != yearList) {
                            for (Map<String, String> yearData : yearList) {
                                Map<String,String> result = new HashMap<>();
                                Object yHundred = yearData.get("origalValue");
                                double hundred =  + Double.parseDouble(yHundred.toString()) * YEARWEIGHT;
                                Object yZsqzhScore = yearData.get("finalValue");
                                double zsqzhScore = + Double.parseDouble(yZsqzhScore.toString()) * YEARWEIGHT;
                                result.put("hundred", String.valueOf(hundred));
                                result.put("zsqzhScore", String.valueOf(zsqzhScore));
                                result.put("unitName",yearData.get("objName"));
                                result.put("unitId",yearData.get("objId"));
                                result.put("workName",yearData.get("workName"));
                                result.put("valueType",yearData.get("valueType"));
                                yearDatasList.add(result);
                            }
                        }
                    }catch (NullPointerException e){

                    }

                }

            }*/
            else{
                //处考处机关/局考局机关
                //List<Map<String, String>>  yearList = examWorkflowDatasService.calScores(param);
                List<Map<String, Object>>  yearList = examWorkflowDatasService.calScoresJGUnit(param);
                examUnitAllPublicYearService.yearJGUnitSave(yearList, examWorkflow);
            }

            examUnitAllPublicYearService.yearUnitSave(yearDatasList, examWorkflow);
        }
        //民警月度考评
        else if ("1".equals(examWorkflow.getExamCycle()) && "2".equals(examWorkflow.getExamObjectType())) {
            Map<String, String> param = new HashMap<String, String>();
            param.put("workflowId", examWorkflow.getId());
            param.put("groupType", "person");
            param.put("DEL_FLAG_NORMAL", "0");
            List<Map<String, String>> list = examWorkflowDatasService.calScores(param);
            //examLdScoreMonthService.monthPersonDatasSave(list, examWorkflow);
            examLdScoreMonthService.monthPersonDatasSaveBeta(list, examWorkflow);
        }
        //民警年度考评
        else if ("2".equals(examWorkflow.getExamCycle()) && "2".equals(examWorkflow.getExamObjectType())) {
            //计算本次考评得分情况
            Map<String, String> param = new HashMap<String, String>();
            param.put("workflowId", examWorkflow.getId());
            param.put("groupType", "person");
            param.put("DEL_FLAG_NORMAL", "0");
            List<Map<String, String>> yearList = examWorkflowDatasService.calScores(param);
            //examLdScoreMonthService.monthPersonDatasSaveBeta(yearList, examWorkflow);
            examLdScoreService.yearPersonSaveBeta(yearList, examWorkflow);
            /*月度比例调整为0  直接保存年度便可*/
/*
            //获取年度所有月份分数
            Map<String, String> yMonthParam = new HashMap<String, String>();
            //yMonthParam.put("workflowId",examWorkflow.getId());
            String time = examWorkflow.getTime();
            yMonthParam.put("year", time.substring(0, 4));
            yMonthParam.put("examType", examWorkflow.getExamType());
            yMonthParam.put("examObjectType", examWorkflow.getExamObjectType());
            yMonthParam.put("examCycle", "1");
            List<Map<String, String>> list = examLdScoreMonthService.findYearMonthScores(yMonthParam);
            List<Map<String, String>> yearDatsList = new ArrayList<Map<String, String>>();
            if (null != yearDatsList && null != list) {
                String personId = "";
                Map<String, String> yearMap = null;
                for (Map<String, String> item : list) {
                    if (!personId.equals(item.get("personId").toString())) {
                        personId = item.get("personId");
                        yearMap = new HashMap<String, String>();
                        yearMap.put("personId", personId);
                        yearDatsList.add(yearMap);
                    }
                    String timeStr = item.get("time").toString();
                    String key = timeStr.substring(4, 6);
                    yearMap.put("m" + key, item.get("score"));
                    yearMap.put("time", timeStr);
                    yearMap.put("name", item.get("name"));
                }
            }

            //TODO 添加考核组综合评分(workflow_id,personId,type,value)，并按照比例进行合并
            examLdScoreService.yearPersonSave(yearDatsList, examWorkflow);*/
        }
    }


    //    @RequiresPermissions("exam:examWorkflow:edit")
    @RequestMapping(value = "delete")
    public String delete(ExamWorkflow examWorkflow, RedirectAttributes redirectAttributes) {
        examWorkflowService.delete(examWorkflow);
        ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
        examWorkflowDatas.setWorkflowId(examWorkflow.getId());
        examWorkflowDatasService.deleteByWorkflowId(examWorkflowDatas);
        try {
            String examCycle = examWorkflow.getExamCycle();//考评周期  1月 2年
            String examObjectType = examWorkflow.getExamObjectType();//考评对象类型
            Integer status = examWorkflow.getStatus();//考评状态
            if(status>=8){
                //8：最终结果公示，99：考评结束
                if("1".equals(examCycle)&&"1".equals(examObjectType)){
                    examUnitAllPublicService.deleteByWorkflowId(examWorkflow.getId());
                }
                if("1".equals(examCycle)&&"2".equals(examObjectType)){
                    examLdScoreMonthService.deleteByWorkflowId(examWorkflow.getId());
                }
                if("2".equals(examCycle)&&"1".equals(examObjectType)){
                    examUnitAllPublicYearService.deleteByWorkflowId(examWorkflow.getId());
                }
                if("2".equals(examCycle)&&"2".equals(examObjectType)){
                    examLdScoreService.deleteByWorkflowId(examWorkflow.getId());
                }
            }
        }catch (Exception e){
            logger.error("单位/民警最终结果表数据删除失败"+e.toString());
            e.printStackTrace();
        }
        addMessage(redirectAttributes, "删除考评管理成功");
        return "redirect:" + Global.getAdminPath() + "/exam/examWorkflow?examType=" + examWorkflow.getExamType();
    }

    //局领导列表
    private List<User> getLeaders(ExamWorkflow examWorkflow) {
        String roleId = "608bf7d2b0f940caa7a083c59f76c9a2";
        if ("1".equals(examWorkflow.getExamType())) {
            roleId = "608bf7d2b0f940caa7a083c59f76c9a2";
        } else {

        }
        List<User> userList = UserUtils.findUserByRoleId(roleId);
        return userList;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setAutoGrowNestedPaths(true);
        binder.setAutoGrowCollectionLimit(2048);
    }

    // @RequiresPermissions("exam:examWorkflow:edit")
    @RequestMapping(value = "qingkuang")
    public String qingKuang(ExamWorkflow examWorkflow, Model model, HttpServletRequest request) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("flowId", examWorkflow.getId());
        param.put("DEL_FLAG_NORMAL", "0");
        List<Map<String, Object>> list = examWorflowSegmentsService.findSegmentsList(param);
        model.addAttribute("status", examWorkflow.getStatus());
        model.addAttribute("segmentsList", list);
        return "modules/exam/examWorkflowFormDetail";
    }

    //    @RequiresPermissions("exam:examWorkflow:edit")
    @RequestMapping(value = "xiangxi")
    public String xiangXi(ExamWorkflow examWorkflow, Model model, HttpServletRequest request) {
        int status = examWorkflow.getStatus();
        model.addAttribute("status", status);
        String targetUrl = "modules/exam/examWorkflowDetail";
        return targetUrl;
    }

    @RequestMapping(value = "selectTerm")
    public String selectTerm(ExamWorkflow examWorkflow,HttpServletRequest request,HttpServletResponse response){
        String standardId = request.getParameter("standardId");
        /*选择考评时的流程状态*/
        String status = request.getParameter("status");
        /*考评标准内容*/
        String standardText = request.getParameter("standardText");
        if (StringUtils.isBlank(standardId)){
            return "modules/exam/term_beta";
        }
        ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(standardId);
        String workflowId = request.getParameter("workflowId");
        examWorkflow=examWorkflowService.get(workflowId);

        String objId = request.getParameter("objId");
        String fillPersonId = request.getParameter("fillPersonId");
        status = examWorkflowDatasService.getStatusById(fillPersonId,examWorkflow.getId(),examWorkflow.getStatus(),null)+"";
        if (StringUtils.isBlank(fillPersonId)){
            fillPersonId = UserUtils.getUser().getId();
        }
        ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
        examWorkflowDatas.setStandardId(standardId);
        examWorkflowDatas.setWorkflowId(workflowId);
        examWorkflowDatas.setObjId(objId);
        examWorkflowDatas.setFillPersonId(fillPersonId);
        examWorkflowDatas.setStandardId(standardId);

        request.setAttribute("standardId",standardId);
        request.setAttribute("standardName",examStandardBaseInfo.getName());
        request.setAttribute("workflowId",workflowId);
        request.setAttribute("objId",objId);
        request.setAttribute("fillPersonId",fillPersonId);

        List<ExamWorkflowDatas> selectedList = examWorkflowDatasService.findList(examWorkflowDatas);
        ExamWorkflow query = new ExamWorkflow();
        query.setCondition(standardText);
        /*模板中的考评项处理*/
        setItems(query,examStandardBaseInfo,selectedList ,null,request,response);

        /*查询所有的考评标准*/
        ExamWorkflowDefine define = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
        String s = define.getTemplatesIds();
        List<ExamStandardBaseInfo> infoList = this.getStandardInfoListBeta(s, examWorkflow, fillPersonId);
        /*代码中过滤查询条件  考评标准的内容*/
//        List<ExamStandardBaseInfo> resultList= infoList.stream().filter(item -> item.getName().contains(standardText)).collect(Collectors.toList());
        infoList.removeIf(item -> item==null);
        request.setAttribute("standardInfoList", infoList);
        request.setAttribute("workflowStatus",status);
        return "modules/exam/term_beta";
    }

    /**
     * 推送失败的数据进行选择标准
     * 更新考standardId row
     * @param examWorkflow
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "updateTermView")
    public String updateTerm(ExamWorkflow examWorkflow,HttpServletRequest request,HttpServletResponse response){
        String standardId = request.getParameter("standardId");
        String workflowDatasId = request.getParameter("workflowDatasId");
        if (StringUtils.isNotBlank(workflowDatasId)){
            request.setAttribute("workflowDatasId",workflowDatasId);
        }
        /*考评标准内容*/
        String standardText = request.getParameter("standardText");
        if (StringUtils.isBlank(standardId)){
            return "modules/exam/term_beta";
        }
        ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(standardId);
        String workflowId =examWorkflow.getId();

        String objId = request.getParameter("objId");
        String fillPersonId = request.getParameter("fillPersonId");
        if (StringUtils.isBlank(fillPersonId)){
            fillPersonId = UserUtils.getUser().getId();
        }
        ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
        examWorkflowDatas.setStandardId(standardId);
        examWorkflowDatas.setWorkflowId(workflowId);
        examWorkflowDatas.setObjId(objId);
        examWorkflowDatas.setFillPersonId(fillPersonId);
        examWorkflowDatas.setStandardId(standardId);

        request.setAttribute("standardId",standardId);
        request.setAttribute("standardName",examStandardBaseInfo.getName());
        request.setAttribute("workflowId",workflowId);
        request.setAttribute("objId",objId);
        request.setAttribute("fillPersonId",fillPersonId);

        List<ExamWorkflowDatas> selectedList = examWorkflowDatasService.findList(examWorkflowDatas);
        ExamWorkflow query = new ExamWorkflow();
        query.setCondition(standardText);
        setItems(query,examStandardBaseInfo,selectedList ,null,request,response);

        /*查询所有的考评标准*/
        ExamWorkflowDefine define = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
        String s = define.getTemplatesIds();
        List<ExamStandardBaseInfo> infoList = this.getStandardInfoList(s, examWorkflow, UserUtils.getUser().getId());
        /*代码中过滤查询条件  考评标准的内容*/
//        List<ExamStandardBaseInfo> resultList= infoList.stream().filter(item -> item.getName().contains(standardText)).collect(Collectors.toList());
        request.setAttribute("standardInfoList", infoList);
        return "modules/exam/updateTermBeta";
    }

    /**
     * 自动考评
     * @param examWorkflow
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "selectTermBeta")
    public String selectTermBeta(ExamWorkflow examWorkflow,HttpServletRequest request,HttpServletResponse response){
        String standardId = request.getParameter("standardId");
        /*考评标准内容*/
        String standardText = request.getParameter("standardText");
        if (StringUtils.isBlank(standardId)){
            return "modules/exam/term_beta";
        }
        ExamStandardBaseInfo entity = examStandardBaseInfoService.get(standardId);
        ExamStandardTemplateDefine i = new ExamStandardTemplateDefine();
        i.setExamStardardId(standardId);
        List<ExamStandardTemplateDefine> defineList = examStandardTemplateDefineService.findList(i);
        String templateId= "";
        if(null != defineList){
            templateId = defineList.get(0).getId();
            //获取考评项数据格式列
            ExamStandardTemplateItemDefine examStandardTemplateItemDefine = new ExamStandardTemplateItemDefine();
            examStandardTemplateItemDefine.setTemplateId(templateId);
//            Page<ExamStandardTemplateItemDefine> page = null;
            /*可实现comparable接口，对上边查询到的列对column_order排序*/
            Page<ExamStandardTemplateItemDefine> pageParam = new Page<ExamStandardTemplateItemDefine>(request, response, -1);
            pageParam.setOrderBy("column_order");
            //查找考评模板第一条的考评项数据格式列
            Page<ExamStandardTemplateItemDefine> page = examStandardTemplateItemDefineService.findPage(pageParam, examStandardTemplateItemDefine);
            List<ExamStandardTemplateItemDefine> columnList= page.getList();
            request.setAttribute("columnList",columnList);

            //获取考评项数据
            Map<String,String> param = new HashMap<String,String>();
            param.put("examStardardId",standardId);
            List<Map<String,String>> list=examStandardTemplateDefineService.findTemplateDatas(param);
            List<Map<String,String>> rowlist = new ArrayList<Map<String,String>>();
            Map<String,String> rowMap= null;
            String rowNum="-1";
            for(Map<String,String> item:list){
                if(!rowNum.equals(String.valueOf(item.get("row_num")))) {
                    rowNum = String.valueOf(item.get("row_num"));
                    rowMap= new HashMap<String,String>();
                    rowlist.add(rowMap);
                }
                rowMap.put(item.get("item_id"),item.get("item_value"));
                rowMap.put("rowNum",rowNum);
            }
            request.setAttribute("rowlist",rowlist);
            request.setAttribute("rowlistJson", JSONObject.toJSONString(rowlist));
        }

        return "modules/exam/selectTermBeta";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /****************以下为自评优化*********************/
    /****************beta为新版优化的内容**********************/
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "selfEvaluationTerm")
    @ResponseBody
    public Result selfEvaluationTerm(HttpServletRequest request, HttpServletResponse response) {

        String standardId = request.getParameter("standardId");
        /*选择的考评项*/
        String rowNum = request.getParameter("rowNum");
        ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(standardId);
        String workflowId = request.getParameter("workflowId");

        String objId = request.getParameter("objId");
        String fillPersonId = request.getParameter("fillPersonId");
        ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
        examWorkflowDatas.setStandardId(standardId);
        examWorkflowDatas.setWorkflowId(workflowId);
        examWorkflowDatas.setObjId(objId);
        examWorkflowDatas.setFillPersonId(fillPersonId);
        examWorkflowDatas.setStandardId(standardId);
        request.setAttribute("standardId",standardId);
        request.setAttribute("rowNum",rowNum);
        request.setAttribute("workflowId",workflowId);
        request.setAttribute("objId",objId);
        request.setAttribute("fillPersonId",fillPersonId);

        List<ExamWorkflowDatas> selectedList = examWorkflowDatasService.findList(examWorkflowDatas);
        Result result  = setItems(new ExamWorkflow(), examStandardBaseInfo, selectedList, null,rowNum, request, response);
        return result;
    }

    /**
     * 保存选择的自评考评项
     * @param request
     * @param response
     * @return
     */
    /*@RequestMapping(value = "saveSelfEvaluationTerm")
    @ResponseBody
    public Result saveSelfEvaluationTerm(HttpServletRequest request, HttpServletResponse response,Model model) {

        String standardId = request.getParameter("standardId");
        String rowNums = request.getParameter("rowNum");
        ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(standardId);
        String workflowId = request.getParameter("workflowId");

        String objId = request.getParameter("objId");
        String fillPersonId = request.getParameter("fillPersonId");
        ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
        examWorkflowDatas.setWorkflowId(workflowId);
        examWorkflowDatas.setObjId(objId);
        examWorkflowDatas.setFillPersonId(fillPersonId);
        examWorkflowDatas.setStandardId(standardId);

        List<ExamWorkflowDatas> selectedList = examWorkflowDatasService.findList(examWorkflowDatas);
        Map<String,String> param = new HashMap<>();
        param.put("examStardardId",standardId);
        List<Map<String, String>> list = examStandardTemplateDefineService.findTemplateDatas(param,rowNums.split(","));
        list.stream().forEach(map -> {
            map.put("standardId",standardId);
            map.put("workflowId",workflowId);
            map.put("id", IdGen.uuid());
            map.put("delFlag", "0");
            map.put("userId", UserUtils.getUser().getId());
*//*            	#{params.id},
			#{params.itemId},
			#{params.itemValue},
			#{params.rowNum},
			#{params.columnType},
			#{params.columnOrder},
			#{params.standardId},
			#{params.workflowid},
			#{params.delFlag}*//*
            examStandardSelfTermDao.insertMap(map);
        });
//        Result resul  = setItems(new ExamWorkflow(), examStandardBaseInfo, selectedList, null,rowNums, request, response);
        ExamStandardSelfTerm examStandardSelfTerm = new ExamStandardSelfTerm();
        examStandardSelfTerm.setStandardId(standardId);
        examStandardSelfTerm.setWorkflowId(workflowId);
//        List<ExamStandardSelfTerm> result = examStandardSelfTermDao.findList(examStandardSelfTerm);
//        model.addAttribute("list",request);
        request.setAttribute("saveResult","success");
        Result result = new Result();
        result.setSuccess(true);
        return result;
    }*/

    private Result setItems(ExamWorkflow examWorkflow, ExamStandardBaseInfo examStandardBaseInfo, List<ExamWorkflowDatas> selectedList, String ids, String rNum, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> dparam = new HashMap<String, String>();
        dparam.put("examStardardId", examStandardBaseInfo.getId());
        ExamStandardTemplateDefine entity = examStandardTemplateDefineService.getNew(dparam);
        if (null != entity) {
            //获取考评项数据格式列
            ExamStandardTemplateItemDefine examStandardTemplateItemDefine = new ExamStandardTemplateItemDefine();
            examStandardTemplateItemDefine.setTemplateId(entity.getId());
            Page<ExamStandardTemplateItemDefine> page = null;
            Page<ExamStandardTemplateItemDefine> pageParam = new Page<ExamStandardTemplateItemDefine>(request, response, -1);
            pageParam.setOrderBy("column_order");
            page = examStandardTemplateItemDefineService.findPage(pageParam, examStandardTemplateItemDefine);
            List<ExamStandardTemplateItemDefine> columnList = page.getList();
            /*根据columnOrder 升序排序*/
            columnList.sort(Comparator.comparing(ExamStandardTemplateItemDefine::getColumnOrder));
            request.setAttribute("columnList", columnList);

            //获取考评项数据
            Map<String, String> param = new HashMap<String, String>();
            param.put("examStardardId", examStandardBaseInfo.getId());

            List<Map<String, String>> list = examStandardTemplateDefineService.findTemplateDatas(param,rNum.split(","));
            List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
            Map<String, String> rowMap = null;
            String rowNum = "-1";
            for (Map<String, String> item : list) {
                if (!rowNum.equals(String.valueOf(item.get("row_num")))) {
                    rowNum = String.valueOf(item.get("row_num"));
                    rowMap = new HashMap<String, String>();
                    rowMap.put("row_num", rowNum);
                    rowList.add(rowMap);
                }
                rowMap.put(item.get("item_id"), item.get("item_value"));
            }

            List<Map<String, String>> taskRowlist = new ArrayList<Map<String, String>>();
            int selectedNumber = 0;
            if (null != selectedList) {
                for (ExamWorkflowDatas selectedItem : selectedList) {
                    if (null != rowList) {
                        for (Map<String, String> item : rowList) {
                            if (selectedItem.getRowId().equals(item.get("row_num").toString())) {
                                if ("1".equals(selectedItem.getIsSelected())) {
                                    selectedNumber++;
                                }
                                item.put("isSelected", selectedItem.getIsSelected());
                                item.remove("value");
                                item.put("value", selectedItem.getValue());
                                item.remove("detail");
                                item.put("detail", selectedItem.getDetail());
                                item.remove("path");
                                item.put("path", selectedItem.getPath());
                                item.remove("id");
                                item.put("id", selectedItem.getId());
                                item.remove("operationStatus");
                                item.put("operationStatus", String.valueOf(selectedItem.getOperationStatus()));
                                item.remove("fillPerson");
                                item.put("fillPerson", selectedItem.getFillPerson());
                                item.remove("examPerson");
                                item.put("examPerson", selectedItem.getExamPerson());
                                item.remove("departSign");
                                item.put("departSign", selectedItem.getDepartSign());
                                item.remove("partBureausSign");
                                item.put("partBureausSign", selectedItem.getPartBureausSign());
                                item.remove("adjustPerson");
                                item.put("adjustPerson", selectedItem.getAdjustPerson());
                                item.remove("mainBureausSign");
                                item.put("mainBureausSign", selectedItem.getMainBureausSign());
                                item.remove("fillPersonId");
                                item.put("fillPersonId", selectedItem.getFillPersonId());
                                item.remove("examPersonId");
                                item.put("examPersonId", selectedItem.getExamPersonId());
                                item.remove("departSignId");
                                item.put("departSignId", selectedItem.getDepartSignId());
                                item.remove("partBureausSignId");
                                item.put("partBureausSignID", selectedItem.getPartBureausSignId());
                                item.remove("adjustPersonId");
                                item.put("adjustPersonId", selectedItem.getAdjustPersonId());
                                item.remove("mainBureausSignId");
                                item.put("mainBureausSignId", selectedItem.getMainBureausSignId());
                                item.put("status", String.valueOf(selectedItem.getStatus()));
                                item.put("items", selectedItem.getItems());
                                taskRowlist.add(item);
                            }
                        }
                    }
                }
            }

            //获取上级检查数据
//            ExamCheck examCheck = new ExamCheck();
//            examCheck.setUseModel(examStandardBaseInfo.getId());
//            List<ExamCheck> checkList = examCheckService.findList(examCheck);
//            if (null != checkList) {
//                for (ExamCheck check : checkList) {
//                    String checkTime = examWorkflow.getTime();
//                    if ("1".equals(examWorkflow.getExamType())) {
//                        checkTime = checkTime.substring(0, 4);
//                    } else {
//                        checkTime = checkTime.substring(0, 4) + checkTime.substring(6, 7);
//                    }
//                    for (Map<String, String> task : taskRowlist) {
//                        if (check.getChooseOptions().equals(task.get("row_num")) && checkTime.equals(examWorkflow.getTime())) {
//                            task.remove("isSelected");
//                            task.put("isSelected", "1");
//                        }
//                    }
//                }
//            }
            request.setAttribute("selectedNumber", selectedNumber);
            request.setAttribute("rowlist", taskRowlist);
            Result result = new Result();
            Map<String,Object> map = new HashMap<>();
            map.put("columnList",columnList);
            map.put("selectedNumber",selectedNumber);
            map.put("rowlist",taskRowlist);
            result.setResult(map);
            result.setSuccess(true);
            return result;
        }
        return null;
    }
/*
选择的考评项不再临时保存到ExamStandardSelfTerm
    @RequestMapping(value = "selfTermList")
    @ResponseBody
    public Result findSelfTermList(HttpServletRequest request, HttpServletResponse response){
       String standardId = request.getParameter("standardId");
        String workflowId = request.getParameter("workflowId");
        ExamStandardSelfTerm examStandardSelfTerm = new ExamStandardSelfTerm();
        examStandardSelfTerm.setStandardId(standardId);
        examStandardSelfTerm.setWorkflowId(workflowId);
        List<ExamStandardSelfTerm> list = examStandardSelfTermDao.findList(examStandardSelfTerm);


        Map<String, String> dparam = new HashMap<String, String>();
        dparam.put("examStardardId", standardId);
        ExamStandardTemplateDefine entity = examStandardTemplateDefineService.getNew(dparam);
        ExamStandardTemplateItemDefine examStandardTemplateItemDefine = new ExamStandardTemplateItemDefine();
        examStandardTemplateItemDefine.setTemplateId(entity.getId());
        Page<ExamStandardTemplateItemDefine> page = null;
        Page<ExamStandardTemplateItemDefine> pageParam = new Page<ExamStandardTemplateItemDefine>(request, response, -1);
        pageParam.setOrderBy("column_order");
        page = examStandardTemplateItemDefineService.findPage(pageParam, examStandardTemplateItemDefine);
        List<ExamStandardTemplateItemDefine> columnList = page.getList();


        Result result = new Result();
        result.setSuccess(true);
        result.setResult(columnList);
        return result;
    }*/

  /*  @RequestMapping(value = "selfTermDelete")
    @ResponseBody
    public Result findSelfTermDelete(HttpServletRequest request, HttpServletResponse response,ExamStandardSelfTerm examStandardSelfTerm ){

        examStandardSelfTerm.setUserId(UserUtils.getUser().getId());
        if (StringUtils.isNotBlank(examStandardSelfTerm.getRowNum()) && StringUtils.isNotBlank(examStandardSelfTerm.getStandardId())
                && StringUtils.isNotBlank(examStandardSelfTerm.getWorkflowId()))
        examStandardSelfTermDao.deleteByRowNum(examStandardSelfTerm);

        Result result = new Result();
        result.setSuccess(true);
        result.setResult("");
        return result;
    }*/

    @RequestMapping(value = "appaise/data/saveBeta")
    public String appraiseSaveBeta(ExamWorkflow examWorkflow, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String standardId = "";
        if (null != request.getParameter("standardId")) {
            standardId = request.getParameter("standardId");
        }
        int status = -1;
        if (null != request.getParameter("status") && !"".equals(request.getParameter("status"))) {
            status = new Integer(request.getParameter("status"));
        }
        String processType = "";
        if (null != request.getParameter("processType")) {
            processType = request.getParameter("processType").toString();
        }

        String personType = "";
        if (null != request.getParameter("personType")) {
            personType = request.getParameter("personType").toString();
        }

        //初核人员、绩效办改分
        String isUpdateSorce = "";
        if (null != request.getParameter("isUpdateSorce")) {
            isUpdateSorce = request.getParameter("isUpdateSorce").toString();
        }
        ArrayList<ExamWorkflowDatas> dataBeta = (ArrayList<ExamWorkflowDatas>) examWorkflow.getDatas().stream().filter(item -> StringUtils.isNotBlank(item.getRowId())).collect(Collectors.toList());
        boolean isHashNoPass = false;
        if (WorkFloatConstant.SIMPLE_EXAM == status) {
            for (ExamWorkflowDatas examWorkflowData : dataBeta) {
                //不通过
                if (null != examWorkflowData.getOperationStatus() && "2".equals(examWorkflowData.getOperationStatus().toString())) {
                    isHashNoPass = true;
                    break;
                }
            }
        }
        /*获取下一步状态*/
        int nextStatus = Integer.parseInt(this.getNextStepStatus(examWorkflow.getId(), status));
        for (ExamWorkflowDatas examWorkflowData : dataBeta) {
            int tempStatus = status;
            if (beanValidator(model, examWorkflowData) && null != examWorkflowData.getRowId()) {
                if (null != request.getParameter("personId") && null != request.getParameter("personName")) {
                    String personId = request.getParameter("personId");
                    String personName = request.getParameter("personName");
                    if (StringUtils.isBlank(personId)){
                        personId = UserUtils.getUser().getId();
                    }
                    if (StringUtils.isBlank(personName)){
                        personName = UserUtils.getUser().getName();
                    }
                    this.setPerson(personId, personName, examWorkflowData, personType);
                }
                /*考评进程*/
                /*
                 * selfAppraise      自评
                 * appraiseExam      系统初步考核
                 * partBureausSign   分管局领导签字
                 * mainBureausSign   主管领导审核
                 * groupAdjust       绩效考评领导小组复核及调整
                 * */
                if ("selfAppraise".equals(processType)) {
                    this.setWeight(examWorkflowData);
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("appraiseExam".equals(processType)) {
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("departSign".equals(processType)) {
//                    examWorkflowData.setDepartSignId(UserUtils.getUser().getId());
//                    examWorkflowData.setDepartSign(UserUtils.getUser().getName());
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("partBureausSign".equals(processType)) {
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("mainBureausSign".equals(processType)) {
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("groupAdjust".equals(processType)) {
                    tempStatus = nextStatus;
                    examWorkflowData.setOperationStatus(1);
                } else if ("save".equals(processType)) {
                    examWorkflowData.setOperationStatus(0);
                }
                /*以下应该用不到了，选择的考评项已保存到数据库，从数据库获取到选择的直接传过来*/
                //是否被选中
                boolean isSelected = false;
                if (null != dataBeta) {
                    for (ExamWorkflowDatas selectedExamWorkflowData : dataBeta) {
                        if (examWorkflowData.getRowId().equals(selectedExamWorkflowData.getRowId())) {
                            if (null != selectedExamWorkflowData.getDetail()) {
                                examWorkflowData.setDetail(selectedExamWorkflowData.getDetail());
                            }
                            if (null != selectedExamWorkflowData.getValue()) {
                                examWorkflowData.setValue(selectedExamWorkflowData.getValue());
                            }
                            if (null != selectedExamWorkflowData.getPath()) {
                                examWorkflowData.setPath(selectedExamWorkflowData.getPath());
                            }
                            if (null != selectedExamWorkflowData.getItems()) {
                                examWorkflowData.setItems(selectedExamWorkflowData.getItems());
                            }
                            if (null != selectedExamWorkflowData.getOperationStatus()) {
                                examWorkflowData.setOperationStatus(selectedExamWorkflowData.getOperationStatus());
                                //不通过
                                if ("2".equals(selectedExamWorkflowData.getOperationStatus().toString())) {
                                    tempStatus = WorkFloatConstant.SELF_EXAM;
                                }
                            }
                            isSelected = true;
                            break;
                        }
                    }
                }
                if (!isSelected) {
                    examWorkflowData.setValue("0");
                    examWorkflowData.setIsSelected("0");//未选中
                } else {
                    examWorkflowData.setIsSelected("1");//选中
                }
                if ("true".equals(isUpdateSorce)) {
                    examWorkflowData.setStatus(null);
                    examWorkflowData.setStatus(null);
                } else {
                    examWorkflowData.setStatus(new Integer(tempStatus));
                }
                examWorkflowDatasService.save(examWorkflowData);
            }
        }

        //查询本阶段是否所有数据都已经处理
        if ("selfAppraise".equals(processType) || "appraiseExam".equals(processType) || "departSign".equals(processType) || "partBureausSign".equals(processType) || "groupAdjust".equals(processType) || "mainBureausSign".equals(processType) || "mainBureausSignAll".equals(processType) && WorkFloatConstant.ALL_PUBLIC != Integer.parseInt(this.getNextStepStatus(examWorkflow.getId(), status))) {
            ExamWorkflowDatas param = new ExamWorkflowDatas();
            param.setWorkflowId(examWorkflow.getId());
            //param.setStandardId(standardId);
            String condition = "a.status < " + this.getNextStepStatus(examWorkflow.getId(), status);
            //condition += " AND a.operation_status <> 3";
            param.setCondition(condition);
            Map<String, Object> datasNumber = examWorkflowDatasService.selectDatasNumber(param);
            if (null == datasNumber || "0".equals(datasNumber.get("number").toString())) {
                if (!"mainBureausSignAll".equals(processType)) {
                    Map<String, Object> uptParam = new HashMap<String, Object>();
                    uptParam.put("id", examWorkflow.getId());
                    uptParam.put("status", Integer.parseInt(this.getNextStepStatus(examWorkflow.getId(), status)));
                    uptParam.put("operationStatus", new Integer("-1"));
                    examWorkflowService.updateStatus(uptParam);
                }
                Map<String, Object> uptDatasParam = new HashMap<String, Object>();
                uptDatasParam.put("workflowId", examWorkflow.getId());
                uptDatasParam.put("status", Integer.parseInt(this.getNextStepStatus(examWorkflow.getId(), status)));
                if (WorkFloatConstant.SELF_EXAM != status) {
                    uptDatasParam.put("operationStatus", new Integer("-1"));
                }
                examWorkflowDatasService.updateStatus(uptDatasParam);
            }
        }
        if (WorkFloatConstant.SIMPLE_EXAM == status && isHashNoPass) {
            Map<String, Object> uptParam = new HashMap<String, Object>();
            uptParam.put("id", examWorkflow.getId());
            uptParam.put("status", WorkFloatConstant.SELF_EXAM);
            uptParam.put("operationStatus", new Integer("1"));
            examWorkflowService.updateStatus(uptParam);
        }
//        if (WorkFloatConstant.ALL_PUBLIC == Integer.parseInt(this.getNextStepStatus(examWorkflow.getId(), status))) {//全局公示
//            this.generateScores(examWorkflow);
//        }
        addMessage(redirectAttributes, "数据保存成功");
        return "redirect:" + Global.getAdminPath() + "/exam/examWorkflow/appaise/content?standardId=" + standardId + "&workflowId=" + examWorkflow.getId() + "&status=" + status + "&processType=" + processType + "&personType=" + personType + "&result=success&examType=" + request.getParameter("examType");
    }

    /**
     * 获得党组织概况树形选择器 ，未放入缓存中
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(HttpServletRequest request) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<ExamWorkflowDatas> list = examWorkflowDatasService.findList(new ExamWorkflowDatas());
        for (int i=0; i<list.size(); i++){
            ExamWorkflowDatas e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", e.getFillPersonId());
//            map.put("pId", e.getParentId());
            map.put("name", e.getFillPerson());
            mapList.add(map);
            if("pm".equals(request.getParameter("flag"))){
                map.put("isParent", true);
            }
        }
        return mapList;
    }

    /**  生成表格
     * @param workflowId  考评流程id
     * */
    @RequestMapping(value = "createTable")
    public String createTable(@RequestParam("workflowId") String workflowId,HttpServletRequest request,HttpServletResponse response,Model model){
        ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
        String examType = examWorkflow.getExamType();//考评类别  kp_type
        String examCycle = examWorkflow.getExamCycle();//考评周期  cycle
        String examObjectType = examWorkflow.getExamObjectType();//考评对象  exam_object_type
        String url = "modules/sys/blank";
        if(StringUtils.isNotBlank(examType)&&"1".equals(examType)){
            //局考处
            Map<String, Object> resultMap = examWorkflowDatasService.getIngScoreList(workflowId,null,examWorkflow.getStatus());
            // Map<String, Object> map = examUnitAllPublicService.getScoreList1(workflowId);
            model.addAttribute("unitList", resultMap.get("unitList"));
            model.addAttribute("weigthsList", resultMap.get("weigthsList"));
            model.addAttribute("conventionScoreList", resultMap.get("conventionScoreList"));
            model.addAttribute("isFillPerson", resultMap.get("isFillPerson"));
            url = "modules/exam/ing_jukaochu_dialog";
        }
        if(StringUtils.isNotBlank(examType)&&("2".equals(examType)||"4".equals(examType))){
            //局考局机关  或 处考处机关
            Map<String, Object> resultMap = examWorkflowDatasService.getIngJuJiGuanScoreList(workflowId,null,examWorkflow.getStatus());
            model.addAttribute("unitMapList",resultMap.get("unitMapList"));
            url = "modules/exam/ing_jukaojujiguan_dialog";
        }
        if(StringUtils.isNotBlank(examType)&&"3".equals(examType)){
            //处考队所
            //Map<String,Object> resultMap = examWorkflowDatasService.getIngChuKaoDuisuo(workflowId,examWorkflow.getStatus());
            Map<String,Object> resultMap = examWorkflowDatasService.getIngChuKaoDuisuo2(workflowId,examWorkflow.getStatus(),null);
            model.addAttribute("pcsResultList",resultMap.get("pcsResultList"));
            model.addAttribute("cgNameList",resultMap.get("cgNameList"));
            model.addAttribute("cgWeightList",resultMap.get("cgWeightList"));
            model.addAttribute("zdNameList",resultMap.get("zdNameList"));
            model.addAttribute("zdWeightList",resultMap.get("zdWeightList"));
            model.addAttribute("cgScoreNameList",resultMap.get("cgScoreNameList"));
            model.addAttribute("zdScoreNameList",resultMap.get("zdScoreNameList"));
            url = "modules/exam/ing_chukaoduisuo_dialog2";
        }
       /* if(StringUtils.isNotBlank(examType)&&"4".equals(examType)){
            //处考处机关
        }*/
        if(StringUtils.isNotBlank(examType)&&"5".equals(examType)){
            //处领导考评
            Map<String, Object> resultMap = examWorkflowDatasService.getIngChuLDScoreList(workflowId,examWorkflow.getStatus());
            model.addAttribute("unitList",resultMap.get("unitList"));
            url = "modules/exam/ing_chuLDExam_dialog";
        }
        if(StringUtils.isNotBlank(examType)&&("6".equals(examType)||"7".equals(examType))){
            //中基层领导考评 或 民警考评
            //Map<String, Object> resultMap = examWorkflowDatasService.getIngPoliceManExamScoreList(workflowId);
            List<Map<String,Object>> resultList = examWorkflowDatasService.getIngPoliceManExamScoreList(workflowId);
            model.addAttribute("resultList",resultList);
            url = "modules/exam/ing_policemanExam_dialog";
        }
       /* if(StringUtils.isNotBlank(examType)&&"7".equals(examType)){
            //民警考评
        }*/
        // 查询该考评拥有的工作流环节
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("flowId", workflowId);
        param.put("DEL_FLAG_NORMAL", "0");
        List<Map<String, Object>> segmentsList = examWorflowSegmentsService.findSegmentsList(param);//工作流环节
        model.addAttribute("segmentsList", segmentsList);
        model.addAttribute("hisCurStatus", examWorkflow.getStatus());//当前工作流状态
        model.addAttribute("workflowId", workflowId);
        model.addAttribute("workflowName", examWorkflow.getName());
        model.addAttribute("status", examWorkflow.getStatus());//当前工作流状态
        return url;
    }


    /**  生成表格
     * @param workflowId  考评流程id
     * */
    @RequestMapping(value = "ckdsAllPublicDetail")
    public String ckdsAllPublicDetail(@RequestParam("workflowId") String workflowId,@RequestParam("fillPersonId") String fillPersonId,HttpServletRequest request,HttpServletResponse response,Model model){
        ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
        String examType = examWorkflow.getExamType();//考评类别  kp_type
        String examCycle = examWorkflow.getExamCycle();//考评周期  cycle
        String examObjectType = examWorkflow.getExamObjectType();//考评对象  exam_object_type
        String url = "modules/sys/blank";
        //处考队所
        //Map<String,Object> resultMap = examWorkflowDatasService.getIngChuKaoDuisuo(workflowId,examWorkflow.getStatus());
        Map<String,Object> resultMap = examWorkflowDatasService.getIngChuKaoDuisuo2(workflowId,examWorkflow.getStatus(),fillPersonId);
        model.addAttribute("pcsResultList",resultMap.get("pcsResultList"));
        model.addAttribute("cgNameList",resultMap.get("cgNameList"));
        model.addAttribute("cgWeightList",resultMap.get("cgWeightList"));
        model.addAttribute("zdNameList",resultMap.get("zdNameList"));
        model.addAttribute("zdWeightList",resultMap.get("zdWeightList"));
        model.addAttribute("cgScoreNameList",resultMap.get("cgScoreNameList"));
        model.addAttribute("zdScoreNameList",resultMap.get("zdScoreNameList"));
        url = "modules/exam/ing_chukaoduisuo_dialog2";

        // 查询该考评拥有的工作流环节
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("flowId", workflowId);
        param.put("DEL_FLAG_NORMAL", "0");
        List<Map<String, Object>> segmentsList = examWorflowSegmentsService.findSegmentsList(param);//工作流环节
        model.addAttribute("segmentsList", segmentsList);
        model.addAttribute("hisCurStatus", examWorkflow.getStatus());//当前工作流状态
        model.addAttribute("workflowId", workflowId);
        model.addAttribute("workflowName", examWorkflow.getName());
        model.addAttribute("status", examWorkflow.getStatus());//当前工作流状态
        return url;
    }


    /**局考处最终结果公示点击分数查看详情
     * @param workflowId
     * 考评流程id
     * @param objId
     * 所点击的处id
     * */
    @RequestMapping(value = "jkcAllPublicDetail")
    public String jkcAllPublicDetail(@RequestParam("workflowId") String workflowId,@RequestParam("objId")String objId, HttpServletRequest request,HttpServletResponse response,Model model){
        ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
        String examType = examWorkflow.getExamType();//考评类别  kp_type
        String examCycle = examWorkflow.getExamCycle();//考评周期  cycle
        String examObjectType = examWorkflow.getExamObjectType();//考评对象  exam_object_type
        String url = "modules/sys/blank";
        //局考处
        //Map<String, Object> resultMap = examWorkflowDatasService.getIngScoreList(workflowId,examWorkflow.getStatus());
//        Map<String, Object> resultMap = examWorkflowDatasService.jkcAllPublicDetail(workflowId,objId,examWorkflow.getStatus());
        Map<String, Object> resultMap = examWorkflowDatasService.getIngScoreList(workflowId,objId,examWorkflow.getStatus());
        // Map<String, Object> map = examUnitAllPublicService.getScoreList1(workflowId);
        model.addAttribute("unitList", resultMap.get("unitList"));
        model.addAttribute("weigthsList", resultMap.get("weigthsList"));
        model.addAttribute("conventionScoreList", resultMap.get("conventionScoreList"));
        model.addAttribute("isFillPerson", null);
        model.addAttribute("workflowId", workflowId);
        model.addAttribute("objId", objId);
        model.addAttribute("workflowName", examWorkflow.getName());
        url = "modules/exam/ing_jukaochu_dialog";
        return url;
    }

    /**
     * 批量通过  退回考评
     * @param examWorkflow
     * @param model
     * @param redirectAttributes
     * @param request
     * @return
     */
    @RequestMapping(value = "saveBeta")
    public String saveExamBeta(ExamWorkflow examWorkflow, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, examWorkflow)) {
            return form(examWorkflow, model, request);
        }
        String operation = request.getParameter("operation");
        String nextStatus = String.valueOf(examWorkflow.getStatus());
        /*退回时 状态更新为上一步*/
        if (operation.equals("back")){
            nextStatus = getPreStepStatus(examWorkflow.getId(),examWorkflow.getStatus());
        }else if (operation.equals("end")){
            /*结束时，状态更新为下一步*/
            nextStatus = this.getNextStepStatus(examWorkflow.getId(), examWorkflow.getStatus());
        }
        examWorkflow.setStatus(Integer.valueOf(nextStatus));
        examWorkflowService.saveExamBeta(examWorkflow);
        addMessage(redirectAttributes, "保存考评标准成功");
        request.setAttribute("saveResult", "success");
        model.addAttribute("history","true");
        return "redirect:"+Global.getAdminPath()+"/exam/examWorkflowDatas/appraise/examIndex?repage&examWorkflowId="+examWorkflow.getId()+"&history=true";
//        return "modules/exam/examWorkflowForm";
    }

    /**
     * 局考处 生成表格 导出
     *
     */
    @RequestMapping("jukaochuExport")
    public String jukaochuExport(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        String fileName = "生成表格导出.xlsx";
        //workflowId
        String workflowId = request.getParameter("workflowId");
        String objId = request.getParameter("objId");
        try {
            //创建XSSFSheet对象
            XSSFWorkbook wb = new XSSFWorkbook();
            //创建XSSFSheet对象
            XSSFSheet sheet = wb.createSheet("sheet1");
            //当前用户是否为填报人
            String isFillPerson = request.getParameter("isFillPerson");
            ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
            //导出excel名，根据考评名称确定
            fileName =  examWorkflow.getName()+".xlsx";
            Map<String, Object> resultMap;
            if(StringUtils.isNotBlank(objId)){
                //resultMap = examWorkflowDatasService.jkcAllPublicDetail(workflowId, objId,examWorkflow.getStatus());
                resultMap = examWorkflowDatasService.getIngScoreList(workflowId, objId,examWorkflow.getStatus());
            }else{
                resultMap = examWorkflowDatasService.getIngScoreList(workflowId,null, examWorkflow.getStatus());
            }
            //涉及处集合
            List<Map<String, String>> unitList = (List<Map<String, String>>) resultMap.get("unitList");
            //权重工作项集合
            List<Map<String, Object>> weigthsList = (List<Map<String, Object>>) resultMap.get("weigthsList");
            //得分情况集合
            List<List<Map<String, Object>>> conventionScoreList = (List<List<Map<String, Object>>>) resultMap.get("conventionScoreList");
            /*================标题行====================*/
            CellRangeAddress cra = new CellRangeAddress(0, 0, 0, 2 + 3 * unitList.size());////参数 起始行,结束行,起始列,结束列，合并单元格，第一行，第一列，结束列根据unitList决定
            sheet.addMergedRegion(cra);
            //设置表头
            XSSFRow titleRow = sheet.createRow(0);
            //创建XSSFCell对象
            XSSFCell cell = titleRow.createCell(0);
            //设置单元格的值
            cell.setCellValue(examWorkflow.getName());
            XSSFCellStyle titleCellStyle = wb.createCellStyle();
            //设置合并单元格边框
            RegionUtil.setBorderBottom(BorderStyle.THIN,cra,sheet);
            RegionUtil.setBorderTop(BorderStyle.THIN,cra,sheet);
            RegionUtil.setBorderLeft(BorderStyle.THIN,cra,sheet);
            RegionUtil.setBorderRight(BorderStyle.THIN,cra,sheet);
            titleCellStyle.setAlignment(HorizontalAlignment.CENTER); // 指定单元格居中对齐
            titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
            XSSFFont titlefont = wb.createFont();
            titlefont.setBold(true);
            titlefont.setFontHeightInPoints((short) 18);
            titleCellStyle.setFont(titlefont);//设置字体格式
            cell.setCellStyle(titleCellStyle);
            /*================标题行====================*/
            //自评且当前用户为填报人，无需含有各处总得分情况
            if(StringUtils.isNotBlank(isFillPerson)&&"isFillPerson".equals(isFillPerson)){
                XSSFCellStyle cellStyle = wb.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
                cellStyle.setWrapText(true);//自动换行
                //设置边框
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                XSSFFont font = wb.createFont();
                font.setFontHeightInPoints((short) 12);//设置字体大小
                cellStyle.setFont(font);//设置字体格式
                /*==================第二、三行表头=================*/
                // 前三列
                CellRangeAddress secondOneCra = new CellRangeAddress(1, 2, 0, 0);//序号单元格
                CellRangeAddress secondTwoCra = new CellRangeAddress(1, 2, 1, 1);//各业务工作及权重单元格
                CellRangeAddress secondThreeCra = new CellRangeAddress(1, 2, 2, 2);//各业务工作所占权重分单元格
                sheet.addMergedRegion(secondOneCra);
                sheet.addMergedRegion(secondTwoCra);
                sheet.addMergedRegion(secondThreeCra);
                XSSFRow secondRow = sheet.createRow(1);//第二行
                XSSFCell secondCell0 = secondRow.createCell(0);
                XSSFCell secondCell1 = secondRow.createCell(1);
                XSSFCell secondCell2 = secondRow.createCell(2);
                //设置边框
                setCellBorder(BorderStyle.THIN,secondOneCra,sheet);
                setCellBorder(BorderStyle.THIN,secondTwoCra,sheet);
                setCellBorder(BorderStyle.THIN,secondThreeCra,sheet);
                //设置单元格值
                secondCell0.setCellValue("序号");
                secondCell1.setCellValue("各业务工作及权重");
                secondCell2.setCellValue("各业务工作所占权重分");
                secondCell0.setCellStyle(cellStyle);
                secondCell1.setCellStyle(cellStyle);
                secondCell2.setCellStyle(cellStyle);
                XSSFRow thirdRow = sheet.createRow(2);//第三行
                thirdRow.createCell(0).setCellStyle(cellStyle);
                thirdRow.createCell(1).setCellStyle(cellStyle);
                thirdRow.createCell(2).setCellStyle(cellStyle);
                for(int i = 0 ;i < unitList.size();i++){
                    CellRangeAddress secondCra = new CellRangeAddress(1, 1, i*3+3, i*3+5);//序号
                    sheet.addMergedRegion(secondCra);
                    setCellBorder(BorderStyle.THIN,secondCra,sheet);
                    XSSFCell secondCell = secondRow.createCell(3 + i * 3);
                    secondCell.setCellValue(unitList.get(i).get("unitName"));
                    secondCell.setCellStyle(cellStyle);
                    XSSFCell thirdCell0 = thirdRow.createCell(3 + i * 3);
                    XSSFCell thirdCell1 = thirdRow.createCell(4 + i * 3);
                    XSSFCell thirdCell2 = thirdRow.createCell(5 + i * 3);
                    thirdCell0.setCellValue("业务100分制得分");
                    thirdCell1.setCellValue("业务折算权重后得分");
                    thirdCell2.setCellValue("具体事项");
                    thirdCell0.setCellStyle(cellStyle);
                    thirdCell1.setCellStyle(cellStyle);
                    thirdCell2.setCellStyle(cellStyle);
                }
                /*==================第二、三行表头=================*/

                /*==================展示各个工作项，名称，权重分数，百分制分数，业务折算权重后得分，具体事项=================*/
                for(int i = 0; i<conventionScoreList.size();i++){
                    XSSFRow tempRow = sheet.createRow(3+i);//2+i+1
                    int no = i+1;//序号
                    String workName = (String) conventionScoreList.get(i).get(0).get("workName");
                    Double weight = (Double) conventionScoreList.get(i).get(0).get("weight");
                    XSSFCell tempCell0 = tempRow.createCell(0);
                    XSSFCell tempCell1 = tempRow.createCell(1);
                    XSSFCell tempCell2 = tempRow.createCell(2);
                    //设置值
                    tempCell0.setCellValue(no);
                    tempCell1.setCellValue(workName);
                    tempCell2.setCellValue(weight);
                    //设置样式
                    tempCell0.setCellStyle(cellStyle);
                    tempCell1.setCellStyle(cellStyle);
                    tempCell2.setCellStyle(cellStyle);
                    List<Map<String,Object>> list = conventionScoreList.get(i);
                    for(int j = 0; j<list.size();j++){
                        double weightScore = Double.valueOf(list.get(j).get("weightScore").toString());
                        String specificItem = StringEscapeUtils.unescapeHtml4(((String) list.get(j).get("specificItem")).replaceAll("<br>",""));
                        XSSFCell tempCell3 = tempRow.createCell(3+3*j);
                        XSSFCell tempCell4 = tempRow.createCell(4+3*j);
                        XSSFCell tempCell5 = tempRow.createCell(5+3*j);

                        try{
                            double hundredScore = Double.valueOf(list.get(j).get("hundredScore").toString());
                            tempCell3.setCellValue(hundredScore);
                        }catch (Exception e){
                            tempCell3.setCellValue("-");
                        }
                        //设置值
                        tempCell4.setCellValue(weightScore);
                        tempCell5.setCellValue(specificItem);
                        //设置样式
                        tempCell3.setCellStyle(cellStyle);
                        tempCell4.setCellStyle(cellStyle);
                        tempCell5.setCellStyle(cellStyle);
                    }

                }
                /*==================展示各个工作项，名称，权重分数，百分制分数，业务折算权重后得分，具体事项=================*/

                /*==================excel尾部各个类型总分===============*/
                int bottomStartIndex = 3 + conventionScoreList.size();//尾部第一行开始索引 从0开始
                XSSFRow bottomFirstRow = sheet.createRow(bottomStartIndex);//业务权重合计得分行
                XSSFRow bottomSecondRow = sheet.createRow(bottomStartIndex+1);//总公共扣分行
                XSSFRow bottomThirdRow = sheet.createRow(bottomStartIndex+2);//总公共加分行
                XSSFRow bottomFourthRow = sheet.createRow(bottomStartIndex+3);//总得分行
                //业务权重合计得分行  label标签单元格
                CellRangeAddress bottomFirstCra0 = new CellRangeAddress(bottomStartIndex, bottomStartIndex, 0, 2);//起始行,结束行,起始列,结束列  业务权重合计得分文字 合并单元格
                sheet.addMergedRegion(bottomFirstCra0);
                setCellBorder(BorderStyle.THIN,bottomFirstCra0,sheet);
                XSSFCell firstRowCell0 = bottomFirstRow.createCell(0);//单元格
                firstRowCell0.setCellValue("业务权重合计得分");
                firstRowCell0.setCellStyle(cellStyle);
                //总公共扣分行  label标签单元格
                CellRangeAddress bottomSecondCra0 = new CellRangeAddress(bottomStartIndex+1, bottomStartIndex+1, 0, 2);//起始行,结束行,起始列,结束列  总公共扣分文字 合并单元格
                sheet.addMergedRegion(bottomSecondCra0);
                setCellBorder(BorderStyle.THIN,bottomSecondCra0,sheet);
                XSSFCell secondRowCell0 = bottomSecondRow.createCell(0);//单元格
                secondRowCell0.setCellValue("总公共扣分");
                secondRowCell0.setCellStyle(cellStyle);
                //总公共加分行  label标签单元格
                CellRangeAddress bottomThirdtCra0 = new CellRangeAddress(bottomStartIndex+2, bottomStartIndex+2, 0, 2);//起始行,结束行,起始列,结束列  总公共加分文字 合并单元格
                sheet.addMergedRegion(bottomThirdtCra0);
                setCellBorder(BorderStyle.THIN,bottomThirdtCra0,sheet);
                XSSFCell thirdRowCell0 = bottomThirdRow.createCell(0);//单元格
                thirdRowCell0.setCellValue("总公共加分");
                thirdRowCell0.setCellStyle(cellStyle);
                //总得分行  label标签单元格
                CellRangeAddress bottomFourthCra0 = new CellRangeAddress(bottomStartIndex+3, bottomStartIndex+3, 0, 2);//起始行,结束行,起始列,结束列  总得分文字  合并单元格
                sheet.addMergedRegion(bottomFourthCra0);
                setCellBorder(BorderStyle.THIN,bottomFourthCra0,sheet);
                XSSFCell fourthRowCell0 = bottomFourthRow.createCell(0);//单元格
                fourthRowCell0.setCellValue("总得分");
                fourthRowCell0.setCellStyle(cellStyle);
                for(int i = 0;i<unitList.size();i++){
                    /*业务权重合计得分行*/
                    CellRangeAddress bottomFirstCra1 = new CellRangeAddress(bottomStartIndex, bottomStartIndex, i*3+3, i*3+5);//业务权重合计得分分数 合并单元格
                    sheet.addMergedRegion(bottomFirstCra1);
                    setCellBorder(BorderStyle.THIN,bottomFirstCra1,sheet);
                    XSSFCell firstRowCell = bottomFirstRow.createCell(i*3+3);
                    firstRowCell.setCellValue(unitList.get(i).get("totalWeightScore"));
                    firstRowCell.setCellStyle(cellStyle);
                    /*总公共扣分行*/
                    CellRangeAddress bottomSecondCra1 = new CellRangeAddress(bottomStartIndex+1, bottomStartIndex+1, i*3+3, i*3+5);//总公共扣分分数 合并单元格
                    sheet.addMergedRegion(bottomSecondCra1);
                    setCellBorder(BorderStyle.THIN,bottomSecondCra1,sheet);
                    XSSFCell secondRowCell = bottomSecondRow.createCell(i*3+3);
                    secondRowCell.setCellValue(unitList.get(i).get("totalPublicDeductScore"));
                    secondRowCell.setCellStyle(cellStyle);
                    /*总公共加分行*/
                    CellRangeAddress bottomThirdtCra1 = new CellRangeAddress(bottomStartIndex+2, bottomStartIndex+2, i*3+3, i*3+5);//总公共加分分数 合并单元格
                    sheet.addMergedRegion(bottomThirdtCra1);
                    setCellBorder(BorderStyle.THIN,bottomThirdtCra1,sheet);
                    XSSFCell thirdRowCell = bottomThirdRow.createCell(i*3+3);
                    thirdRowCell.setCellValue(unitList.get(i).get("totalPublicAddScore"));
                    thirdRowCell.setCellStyle(cellStyle);
                    /*总得分行*/
                    CellRangeAddress bottomFourthCra1 = new CellRangeAddress(bottomStartIndex+3, bottomStartIndex+3, i*3+3, i*3+5);//总得分分数  合并单元格
                    sheet.addMergedRegion(bottomFourthCra1);
                    setCellBorder(BorderStyle.THIN,bottomFourthCra1,sheet);
                    XSSFCell fourthRowCell = bottomFourthRow.createCell(i*3+3);
                    fourthRowCell.setCellValue(unitList.get(i).get("totaltScore"));
                    fourthRowCell.setCellStyle(cellStyle);
                }
                /*==================excel尾部各个类型总分===============*/
            }
            else{
                /*=====================第二行，各处总得分情况=======================*/
                //参数 起始行,结束行,起始列,结束列
                CellRangeAddress secondCra = new CellRangeAddress(1, 1, 0, 2 + 3 * unitList.size());
                sheet.addMergedRegion(secondCra);
                //第二行数据
                XSSFRow secondRow = sheet.createRow(1);
                XSSFCell cell2 = secondRow.createCell(0);
                //设置合并单元格边框
                //setCellBorder(BorderStyle.THIN,secondCra,sheet);
                RegionUtil.setBorderBottom(BorderStyle.THIN,secondCra,sheet);
                RegionUtil.setBorderTop(BorderStyle.THIN,secondCra,sheet);
                RegionUtil.setBorderLeft(BorderStyle.THIN,secondCra,sheet);
                RegionUtil.setBorderRight(BorderStyle.THIN,secondCra,sheet);

                //设置单元格的值
                StringBuffer unitScores = new StringBuffer();
                for(Map<String,String> unitMap : unitList){
                    String unitName = unitMap.get("unitName");
                    String totaltScore = unitMap.get("totaltScore");
                    unitScores.append(unitName+"总得分："+totaltScore+"分        ");
                }
                cell2.setCellValue(unitScores.toString());
                XSSFCellStyle cellStyle = wb.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
                cellStyle.setWrapText(true);//自动换行
                //设置边框
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                XSSFFont font = wb.createFont();
                font.setFontHeightInPoints((short) 12);//设置字体大小
                cellStyle.setFont(font);//设置字体格式
                cell2.setCellStyle(cellStyle);//设置字体格式
                /*==================第二行，各处总得分情况==========================*/
                /*==================第三、四行表头=================*/
                // 前三列
                CellRangeAddress thirdOneCra = new CellRangeAddress(2, 3, 0, 0);//序号单元格
                CellRangeAddress thirdTwoCra = new CellRangeAddress(2, 3, 1, 1);//各业务工作及权重单元格
                CellRangeAddress thirdThreeCra = new CellRangeAddress(2, 3, 2, 2);//各业务工作所占权重分单元格
                sheet.addMergedRegion(thirdOneCra);
                sheet.addMergedRegion(thirdTwoCra);
                sheet.addMergedRegion(thirdThreeCra);
                XSSFRow thirdRow = sheet.createRow(2);//第三行
                XSSFCell thirdCell0 = thirdRow.createCell(0);
                XSSFCell thirdCell1 = thirdRow.createCell(1);
                XSSFCell thirdCell2 = thirdRow.createCell(2);
                //设置边框
                setCellBorder(BorderStyle.THIN,thirdOneCra,sheet);
                setCellBorder(BorderStyle.THIN,thirdTwoCra,sheet);
                setCellBorder(BorderStyle.THIN,thirdThreeCra,sheet);
                //设置单元格值
                thirdCell0.setCellValue("序号");
                thirdCell1.setCellValue("各业务工作及权重");
                thirdCell2.setCellValue("各业务工作所占权重分");
                thirdCell0.setCellStyle(cellStyle);
                thirdCell1.setCellStyle(cellStyle);
                thirdCell2.setCellStyle(cellStyle);
                XSSFRow fourthRow = sheet.createRow(3);//第四行
                fourthRow.createCell(0).setCellStyle(cellStyle);
                fourthRow.createCell(1).setCellStyle(cellStyle);
                fourthRow.createCell(2).setCellStyle(cellStyle);
                for(int i = 0 ;i < unitList.size();i++){
                    CellRangeAddress thirdCra = new CellRangeAddress(2, 2, i*3+3, i*3+5);//序号
                    sheet.addMergedRegion(thirdCra);
                    setCellBorder(BorderStyle.THIN,thirdCra,sheet);
                    XSSFCell thirdCell = thirdRow.createCell(3 + i * 3);
                    thirdCell.setCellValue(unitList.get(i).get("unitName"));
                    thirdCell.setCellStyle(cellStyle);
                    XSSFCell fourthCell0 = fourthRow.createCell(3 + i * 3);
                    XSSFCell fourthCell1 = fourthRow.createCell(4 + i * 3);
                    XSSFCell fourthCell2 = fourthRow.createCell(5 + i * 3);
                    fourthCell0.setCellValue("业务100分制得分");
                    fourthCell1.setCellValue("业务折算权重后得分");
                    fourthCell2.setCellValue("具体事项");
                    fourthCell0.setCellStyle(cellStyle);
                    fourthCell1.setCellStyle(cellStyle);
                    fourthCell2.setCellStyle(cellStyle);
                }
                /*==================第三、四行表头=================*/

                /*==================展示各个工作项，名称，权重分数，百分制分数，业务折算权重后得分，具体事项=================*/
                for(int i = 0; i<conventionScoreList.size();i++){
                    XSSFRow tempRow = sheet.createRow(4+i);//3+i+1
                    int no = i+1;//序号
                    String workName = (String) conventionScoreList.get(i).get(0).get("workName");
                    Double weight = (Double) conventionScoreList.get(i).get(0).get("weight");
                    XSSFCell tempCell0 = tempRow.createCell(0);
                    XSSFCell tempCell1 = tempRow.createCell(1);
                    XSSFCell tempCell2 = tempRow.createCell(2);
                    //设置值
                    tempCell0.setCellValue(no);
                    tempCell1.setCellValue(workName);
                    tempCell2.setCellValue(weight);
                    //设置样式
                    tempCell0.setCellStyle(cellStyle);
                    tempCell1.setCellStyle(cellStyle);
                    tempCell2.setCellStyle(cellStyle);
                    List<Map<String,Object>> list = conventionScoreList.get(i);
                    for(int j = 0; j<list.size();j++){
                        double weightScore = Double.valueOf(list.get(j).get("weightScore").toString());
                        String specificItem = StringEscapeUtils.unescapeHtml4(((String) list.get(j).get("specificItem")).replaceAll("<br>",""));
                        XSSFCell tempCell3 = tempRow.createCell(3+3*j);
                        XSSFCell tempCell4 = tempRow.createCell(4+3*j);
                        XSSFCell tempCell5 = tempRow.createCell(5+3*j);
                        try {
                            double hundredScore = Double.valueOf(list.get(j).get("hundredScore").toString());
                            //设置值
                            tempCell3.setCellValue(hundredScore);
                        }catch (Exception e){
                            //设置值
                            tempCell3.setCellValue("-");
                        }
                        tempCell4.setCellValue(weightScore);
                        tempCell5.setCellValue(specificItem);
                        //设置样式
                        tempCell3.setCellStyle(cellStyle);
                        tempCell4.setCellStyle(cellStyle);
                        tempCell5.setCellStyle(cellStyle);
                    }

                }
                /*==================展示各个工作项，名称，权重分数，百分制分数，业务折算权重后得分，具体事项=================*/

                /*==================excel尾部各个类型总分===============*/
                int bottomStartIndex = 4 + conventionScoreList.size();//尾部第一行开始索引 从0开始
                XSSFRow bottomFirstRow = sheet.createRow(bottomStartIndex);//业务权重合计得分行
                XSSFRow bottomSecondRow = sheet.createRow(bottomStartIndex+1);//总公共扣分行
                XSSFRow bottomThirdRow = sheet.createRow(bottomStartIndex+2);//总公共加分行
                XSSFRow bottomFourthRow = sheet.createRow(bottomStartIndex+3);//总得分行
                //业务权重合计得分行  label标签单元格
                CellRangeAddress bottomFirstCra0 = new CellRangeAddress(bottomStartIndex, bottomStartIndex, 0, 2);//起始行,结束行,起始列,结束列  业务权重合计得分文字 合并单元格
                sheet.addMergedRegion(bottomFirstCra0);
                setCellBorder(BorderStyle.THIN,bottomFirstCra0,sheet);
                XSSFCell firstRowCell0 = bottomFirstRow.createCell(0);//单元格
                firstRowCell0.setCellValue("业务权重合计得分");
                firstRowCell0.setCellStyle(cellStyle);
                //总公共扣分行  label标签单元格
                CellRangeAddress bottomSecondCra0 = new CellRangeAddress(bottomStartIndex+1, bottomStartIndex+1, 0, 2);//起始行,结束行,起始列,结束列  总公共扣分文字 合并单元格
                sheet.addMergedRegion(bottomSecondCra0);
                setCellBorder(BorderStyle.THIN,bottomSecondCra0,sheet);
                XSSFCell secondRowCell0 = bottomSecondRow.createCell(0);//单元格
                secondRowCell0.setCellValue("总公共扣分");
                secondRowCell0.setCellStyle(cellStyle);
                //总公共加分行  label标签单元格
                CellRangeAddress bottomThirdtCra0 = new CellRangeAddress(bottomStartIndex+2, bottomStartIndex+2, 0, 2);//起始行,结束行,起始列,结束列  总公共加分文字 合并单元格
                sheet.addMergedRegion(bottomThirdtCra0);
                setCellBorder(BorderStyle.THIN,bottomThirdtCra0,sheet);
                XSSFCell thirdRowCell0 = bottomThirdRow.createCell(0);//单元格
                thirdRowCell0.setCellValue("总公共加分");
                thirdRowCell0.setCellStyle(cellStyle);
                //总得分行  label标签单元格
                CellRangeAddress bottomFourthCra0 = new CellRangeAddress(bottomStartIndex+3, bottomStartIndex+3, 0, 2);//起始行,结束行,起始列,结束列  总得分文字  合并单元格
                sheet.addMergedRegion(bottomFourthCra0);
                setCellBorder(BorderStyle.THIN,bottomFourthCra0,sheet);
                XSSFCell fourthRowCell0 = bottomFourthRow.createCell(0);//单元格
                fourthRowCell0.setCellValue("总得分");
                fourthRowCell0.setCellStyle(cellStyle);
                for(int i = 0;i<unitList.size();i++){
                    /*业务权重合计得分行*/
                    CellRangeAddress bottomFirstCra1 = new CellRangeAddress(bottomStartIndex, bottomStartIndex, i*3+3, i*3+5);//业务权重合计得分分数 合并单元格
                    sheet.addMergedRegion(bottomFirstCra1);
                    setCellBorder(BorderStyle.THIN,bottomFirstCra1,sheet);
                    XSSFCell firstRowCell = bottomFirstRow.createCell(i*3+3);
                    firstRowCell.setCellValue(unitList.get(i).get("totalWeightScore"));
                    firstRowCell.setCellStyle(cellStyle);
                    /*总公共扣分行*/
                    CellRangeAddress bottomSecondCra1 = new CellRangeAddress(bottomStartIndex+1, bottomStartIndex+1, i*3+3, i*3+5);//总公共扣分分数 合并单元格
                    sheet.addMergedRegion(bottomSecondCra1);
                    setCellBorder(BorderStyle.THIN,bottomSecondCra1,sheet);
                    XSSFCell secondRowCell = bottomSecondRow.createCell(i*3+3);
                    secondRowCell.setCellValue(unitList.get(i).get("totalPublicDeductScore"));
                    secondRowCell.setCellStyle(cellStyle);
                    /*总公共加分行*/
                    CellRangeAddress bottomThirdtCra1 = new CellRangeAddress(bottomStartIndex+2, bottomStartIndex+2, i*3+3, i*3+5);//总公共加分分数 合并单元格
                    sheet.addMergedRegion(bottomThirdtCra1);
                    setCellBorder(BorderStyle.THIN,bottomThirdtCra1,sheet);
                    XSSFCell thirdRowCell = bottomThirdRow.createCell(i*3+3);
                    thirdRowCell.setCellValue(unitList.get(i).get("totalPublicAddScore"));
                    thirdRowCell.setCellStyle(cellStyle);
                    /*总得分行*/
                    CellRangeAddress bottomFourthCra1 = new CellRangeAddress(bottomStartIndex+3, bottomStartIndex+3, i*3+3, i*3+5);//总得分分数  合并单元格
                    sheet.addMergedRegion(bottomFourthCra1);
                    setCellBorder(BorderStyle.THIN,bottomFourthCra1,sheet);
                    XSSFCell fourthRowCell = bottomFourthRow.createCell(i*3+3);
                    fourthRowCell.setCellValue(unitList.get(i).get("totaltScore"));
                    fourthRowCell.setCellStyle(cellStyle);
                }
                /*==================excel尾部各个类型总分===============*/
            }
            // 清除buffer缓存
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            //指定下载名字
            response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
            ServletOutputStream fout = response.getOutputStream();
            wb.write(fout);
            fout.close();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            addMessage(redirectAttributes, "导出生成表格结果失败！失败信息：" + ex);
        }
        return "redirect:" + adminPath + "/exam/examWorkflow/createTable?workflowId="+workflowId;

    }


    /**
     * 局考局机关，处考处机关导出
     * */
    @RequestMapping("jukaojujiguanExport")
    public String jukaojujiguanExport(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
        String workflowId = request.getParameter("workflowId");
        String fileName = "单位考评-生成表格.xlsx";
        try {
            String fileSeperator = File.separator;
            String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
            String path = filePath + fileName;
            ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
            Map<String, Object> resultMap = examWorkflowDatasService.getIngJuJiGuanScoreList(workflowId,null,examWorkflow.getStatus());

            Map<String,String> fill = new HashMap<>();
            fill.put("title",examWorkflow.getName());
            List<Map<String, Object>> unitMapList = (List<Map<String, Object>>) resultMap.get("unitMapList");
            ExcelTemplate excelTemplate = new ExcelTemplate(path);
            if (unitMapList == null) {
                unitMapList = new ArrayList<>();
            }
            LinkedHashMap<Integer, LinkedList<String>> areaValues = new LinkedHashMap<>();
            for (int i = 0; i < unitMapList.size(); i++) {
                LinkedList<String> lineList = new LinkedList<>();
                Map<String,Object> tempMap = unitMapList.get(i);
                int no = i+1;//序号
                lineList.add(String.valueOf(no));//序号
                lineList.add(tempMap.get("objName").toString());//部门
                String deductScoreItems =  StringEscapeUtils.unescapeHtml4(tempMap.get("deductScoreItems").toString().replace("<br>",";"));
                lineList.add(deductScoreItems);//扣分项目
                String addScoreItems =  StringEscapeUtils.unescapeHtml4(tempMap.get("addScoreItems").toString().replace("<br>",";"));
                lineList.add(addScoreItems);//加分项目
                lineList.add(tempMap.get("deductScore").toString());//扣分
                lineList.add(tempMap.get("addScore").toString());//加分
                lineList.add(tempMap.get("totalScore").toString());//总得分
                areaValues.put(i, lineList);
            }
            try {
                excelTemplate.addRowByExist(0, 2, 2, 3, areaValues, true);
                excelTemplate.fillVariable(0,fill);
                response.reset();
                response.setContentType("application/octet-stream; charset=utf-8");
                /*导入导出模板不一致，导处模板需要占位符*/
                response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(examWorkflow.getName()+".xlsx"));
                ServletOutputStream fout = response.getOutputStream();
                excelTemplate.getWorkbook().write(fout);
                fout.close();
                return null;

            } catch (IOException e) {
                e.printStackTrace();
                addMessage(redirectAttributes, "导出生成表格结果失败！失败信息：" + e.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            addMessage(redirectAttributes, "导出生成表格结果失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/exam/examWorkflow/createTable?workflowId="+workflowId;
    }

    /**
     * 处考队所
     * */
    @RequestMapping("chuKaoDuiSuoExport")
    public String chuKaoDuiSuoExport(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
        String fileName = "处考派出所-导出.xlsx";
        String workflowId = request.getParameter("workflowId");
        String fillPersonId = request.getParameter("fillPersonId");
        try {
            String fileSeperator = File.separator;
            String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
            String path = filePath + fileName;
            ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
            Map<String, Object> resultMap = examWorkflowDatasService.getCkDSResultMap(workflowId,fillPersonId);
            Map<String,String> map = new HashMap<>();

            Iterator<Map.Entry<String, Object>> entries = resultMap.entrySet().iterator();
            while(entries.hasNext()){
                Map.Entry<String, Object> entry = entries.next();
                String key = entry.getKey();
                String value = StringEscapeUtils.unescapeHtml4(String.valueOf(entry.getValue()));
                map.put(key,value);
            }
            //初始化工具
            ExcelTemplate excel = new ExcelTemplate(filePath + fileName);
            try {
                //写入模板
                int count = excel.fillVariable(0, map);
            } catch (IOException e) {
                e.printStackTrace();
                e.printStackTrace();
                logger.error(e.toString());
                addMessage(redirectAttributes, "联系系统管理员导入模板");
                return "redirect:" + adminPath + "/exam/examWorkflow/ingchuKPcsDetail?workflowId="+workflowId+"&fillPersonId="+fillPersonId;
            }
            //下载
            try {
                // 清除buffer缓存
                response.reset();
                response.setContentType("application/octet-stream; charset=utf-8");
                //指定下载名字
                response.setHeader("Content-Disposition", "attachment; filename=" +
                        Encodes.urlEncode(map.get("fillPersonName")+"-绩效考核评分表")+".xlsx");
                response.addHeader("Pargam", "no-cache");
                response.addHeader("Cache-Control", "no-cache");
                OutputStream fout = response.getOutputStream();
                excel.getWorkbook().write(fout);
                fout.close();

            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.toString());
                addMessage(redirectAttributes, e.getMessage());
            }
            return "redirect:" + adminPath + "/exam/examWorkflow/ingchuKPcsDetail?workflowId="+workflowId+"&fillPersonId="+fillPersonId;

        }catch (Exception e){
            e.printStackTrace();
            addMessage(redirectAttributes, "导出生成表格结果失败！失败信息：" + e.getMessage());
            return "redirect:" + adminPath + "/exam/examWorkflow/ingchuKPcsDetail?workflowId="+workflowId+"&fillPersonId="+fillPersonId;

        }
    }

    /*
     * 处领导班子考核-生成表格导出
     * */
    @RequestMapping("chuLDExamExport")
    public String chuLDExamExport(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
        String workflowId = request.getParameter("workflowId");
        String fileName = "处领导考核生成表格导出.xlsx";
        try{
            ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
            Map<String, Object> resultMap = examWorkflowDatasService.getIngChuLDScoreList(workflowId, examWorkflow.getStatus());
            List<Map<String,Object>> unitList = (List<Map<String, Object>>) resultMap.get("unitList");
            //创建XSSFSheet对象
            XSSFWorkbook wb = new XSSFWorkbook();
            //创建XSSFSheet对象
            XSSFSheet sheet = wb.createSheet("sheet1");
            /*===============设置标题行====================*/
            CellRangeAddress titleCra = new CellRangeAddress(0, 0, 0, 5);////参数 起始行,结束行,起始列,结束列，合并单元格
            sheet.addMergedRegion(titleCra);
            setCellBorder(BorderStyle.THIN,titleCra,sheet);//为合并单元格设置边框
            XSSFRow titleRow = sheet.createRow(0);
            XSSFCell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(examWorkflow.getName());
            XSSFCellStyle titleCellStyle = wb.createCellStyle();//单元格格式
            titleCellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
            titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
            titleCellStyle.setBorderBottom(BorderStyle.THIN);
            titleCellStyle.setBorderTop(BorderStyle.THIN);
            titleCellStyle.setBorderRight(BorderStyle.THIN);
            titleCellStyle.setBorderLeft(BorderStyle.THIN);
            XSSFFont titleFont = wb.createFont();
            titleFont.setFontHeightInPoints((short) 18);//字体大小
            titleCellStyle.setFont(titleFont);
            titleCell.setCellStyle(titleCellStyle);
            titleRow.createCell(1).setCellStyle(titleCellStyle);//为标题单元格设置边框
            titleRow.createCell(2).setCellStyle(titleCellStyle);//为标题单元格设置边框
            titleRow.createCell(3).setCellStyle(titleCellStyle);//为标题单元格设置边框
            titleRow.createCell(4).setCellStyle(titleCellStyle);//为标题单元格设置边框
            titleRow.createCell(5).setCellStyle(titleCellStyle);//为标题单元格设置边框
            /*===============设置标题行====================*/
            fileName = examWorkflow.getName()+".xlsx";//导出文件名
            int rowIndex = 0;//行索引
            /*=============单元格格式==================*/
            XSSFCellStyle cellStyle =wb.createCellStyle();//单元格格式
            cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
            cellStyle.setWrapText(true);//自动换行
            //设置边框
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            XSSFFont Font = wb.createFont();
            Font.setFontHeightInPoints((short)12);//字体大小
            cellStyle.setFont(Font);
            /*=============单元格格式==================*/
            /*=============生成各处人员信息数据=================*/
            for(int i = 0; i < unitList.size();i++){
                //各处集合
                /*=========处标题行==========*/
                rowIndex = rowIndex+1;
                CellRangeAddress chuTitleCra = new CellRangeAddress(rowIndex, rowIndex, 0, 5);////参数 起始行,结束行,起始列,结束列，合并单元格
                sheet.addMergedRegion(chuTitleCra);
                setCellBorder(BorderStyle.THIN,chuTitleCra,sheet);//为合并单元格设置边框
                XSSFRow chuTitleRow = sheet.createRow(rowIndex);//处标题行 例如：南宁公安处领导班子
                XSSFCell chuTitleCell = chuTitleRow.createCell(0);//处标题单元格
                chuTitleCell.setCellValue(unitList.get(i).get("unitName").toString()+"领导班子");
                chuTitleCell.setCellStyle(cellStyle);
                chuTitleRow.createCell(1).setCellStyle(cellStyle);//为标题单元格设置边框
                chuTitleRow.createCell(2).setCellStyle(cellStyle);//为标题单元格设置边框
                chuTitleRow.createCell(3).setCellStyle(cellStyle);//为标题单元格设置边框
                chuTitleRow.createCell(4).setCellStyle(cellStyle);//为标题单元格设置边框
                chuTitleRow.createCell(5).setCellStyle(cellStyle);//为标题单元格设置边框
                /*=========处标题行==========*/
                /*=========表头行============*/
                rowIndex = rowIndex+1;
                XSSFRow btRow = sheet.createRow(rowIndex);//表头行
                XSSFCell btCell0 = btRow.createCell(0);//序号
                btCell0.setCellValue("序号");
                btCell0.setCellStyle(cellStyle);
                XSSFCell btCell1 = btRow.createCell(1);//单位
                btCell1.setCellValue("单位");
                btCell1.setCellStyle(cellStyle);
                XSSFCell btCell2 = btRow.createCell(2);//姓名
                btCell2.setCellValue("姓名");
                btCell2.setCellStyle(cellStyle);
                XSSFCell btCell3 = btRow.createCell(3);//职务
                btCell3.setCellValue("职务");
                btCell3.setCellStyle(cellStyle);
                XSSFCell btCell4 = btRow.createCell(4);//分数
                btCell4.setCellValue("分数");
                btCell4.setCellStyle(cellStyle);
                XSSFCell btCell5 = btRow.createCell(5);//加扣分事项
                btCell5.setCellValue("加扣分事项");
                btCell5.setCellStyle(cellStyle);
                /*=========表头行============*/
                /*=========相应处下领导班子考核信息============*/
                List<Map<String, Object>>  ldInfoList = (List<Map<String, Object>>) unitList.get(i).get("ldInfoMap");
                for(int j = 0;j<ldInfoList.size();j++){
                    rowIndex = rowIndex+1;
                    XSSFRow ldInfoRow = sheet.createRow(rowIndex);
                    XSSFCell ldInfocell0 = ldInfoRow.createCell(0);//序号
                    ldInfocell0.setCellValue(j+1);
                    ldInfocell0.setCellStyle(cellStyle);
                    XSSFCell ldInfocell1 = ldInfoRow.createCell(1);//单位
                    ldInfocell1.setCellValue(ldInfoList.get(j).get("unitName").toString());
                    ldInfocell1.setCellStyle(cellStyle);
                    XSSFCell ldInfocell2 = ldInfoRow.createCell(2);//姓名
                    ldInfocell2.setCellValue(ldInfoList.get(j).get("objName").toString());
                    ldInfocell2.setCellStyle(cellStyle);
                    XSSFCell ldInfocell3 = ldInfoRow.createCell(3);//职务
                    ldInfocell3.setCellValue(ldInfoList.get(j).get("job").toString());
                    ldInfocell3.setCellStyle(cellStyle);
                    XSSFCell ldInfocell4 = ldInfoRow.createCell(4);//分数
                    ldInfocell4.setCellValue(String.valueOf((double)ldInfoList.get(j).get("totalScore")));
                    ldInfocell4.setCellStyle(cellStyle);
                    XSSFCell ldInfocell5 = ldInfoRow.createCell(5);//加扣分项目
                    ldInfocell5.setCellValue(StringEscapeUtils.unescapeHtml4(ldInfoList.get(j).get("items").toString().replace("<br>",";")));
                    ldInfocell5.setCellStyle(cellStyle);
                }
                /*=========相应处下领导班子考核信息============*/
                /*=============生成各处人员信息数据=================*/
            }
            // 清除buffer缓存
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            //指定下载名字
            response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
            ServletOutputStream fout = response.getOutputStream();
            wb.write(fout);
            fout.close();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            addMessage(redirectAttributes, "导出生成表格结果失败！失败信息：" + e);

        }
        return "redirect:" + adminPath + "/exam/examWorkflow/createTable?workflowId="+workflowId;
    }


    /*
     * 中基层考核、民警考核-生成表格导出
     * */
    @RequestMapping("policemanExamExport")
    public String policemanExamExport(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
        String workflowId = request.getParameter("workflowId");
        String fileName = "民警考评-生成表格.xlsx";
        try{
            String fileSeperator = File.separator;
            String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
            String path = filePath + fileName;
            ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
            List<Map<String, Object>> resultList = examWorkflowDatasService.getIngPoliceManExamScoreList(workflowId);
            Map<String,String> fill = new HashMap<>();
            fill.put("title",examWorkflow.getName());
            ExcelTemplate excelTemplate = new ExcelTemplate(path);
            if(resultList == null){
                resultList = new ArrayList<>();
            }
            LinkedHashMap<Integer,LinkedList<String>> areaValues = new LinkedHashMap<Integer, LinkedList<String>>();
            for(int i = 0;i<resultList.size();i++){
                LinkedList<String> linkedList = new LinkedList<>();
                Map<String,Object> tempMap = resultList.get(i);
                int no = i+1;
                linkedList.add(String.valueOf(no));//序号
                linkedList.add(tempMap.get("unitName").toString());//单位
                linkedList.add(tempMap.get("objName").toString());//姓名
                try{
                    linkedList.add(tempMap.get("job").toString());//职务
                }catch (Exception e){
                    System.out.printf("发生错误:"+e.toString());
                    linkedList.add("");//职务
                }

                linkedList.add(String.valueOf((double)tempMap.get("totalScore")));//分数
                linkedList.add(StringEscapeUtils.unescapeHtml4(tempMap.get("items").toString().replace("<br>",";")));
                areaValues.put(i,linkedList);
            }
            excelTemplate.addRowByExist(0,2,2,3,areaValues,true);
            excelTemplate.fillVariable(0,fill);
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            /*导入导出模板不一致，导处模板需要占位符*/
            response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(examWorkflow.getName()+".xlsx"));
            ServletOutputStream fout = response.getOutputStream();
            excelTemplate.getWorkbook().write(fout);
            fout.close();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            addMessage(redirectAttributes, "导出生成表格结果失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/exam/examWorkflow/createTable?workflowId="+workflowId;
    }

    //为合并单元格设置边框
    public static void setCellBorder(BorderStyle borderStyle, CellRangeAddress cra, XSSFSheet sheet){
        RegionUtil.setBorderBottom(borderStyle,cra,sheet);
        RegionUtil.setBorderTop(borderStyle,cra,sheet);
        RegionUtil.setBorderLeft(borderStyle,cra,sheet);
        RegionUtil.setBorderRight(borderStyle,cra,sheet);
    }
    /**
     *  处考队所，生成表格 详情
     */
    @RequestMapping("ingchuKPcsDetail")
    public String ingchuKPcsDetail(String workflowId,String fillPersonId,Model model,HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> resultMap =  examWorkflowDatasService.getCkDSResultMap(workflowId,fillPersonId);
        model.addAttribute("resultMap",resultMap);
        model.addAttribute("workflowId",workflowId);
        model.addAttribute("fillPersonId",fillPersonId);
        return "modules/exam/ing_chukaoduisuo_dialog_detail";
    }
}
