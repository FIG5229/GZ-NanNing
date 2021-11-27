/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exam.entity.*;
import com.thinkgem.jeesite.modules.exam.service.*;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 考评流程任务分配Controller
 *
 * @author eav.liu
 * @version 2019-12-10
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examWorkflowSegmentsTask")
public class ExamWorkflowSegmentsTaskController extends BaseController {

    @Autowired
    private ExamWorkflowSegmentsTaskService examWorkflowSegmentsTaskService;

    @Autowired
    private ExamWorkflowSegmentGuanlianService examWorkflowSegmentGuanlianService;

    @Autowired
    private ExamWorkflowDefineService examWorkflowDefineService;

    @Autowired
    private ExamStandardBaseInfoService examStandardBaseInfoService;

    @Autowired
    private ExamStandardTemplateDefineService examStandardTemplateDefineService;

    @Autowired
    private ExamStandardTemplateItemDefineService examStandardTemplateItemDefineService;

    @Autowired
    private ExamWorkflowService examWorkflowService;

    @Autowired
    private ExamPersonsAssignRuleChildService examPersonsAssignRuleChildService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private ExamObjectTypeService examObjectTypeService;

    @ModelAttribute
    public ExamWorkflowSegmentsTask get(@RequestParam(required = false) String id) {
        ExamWorkflowSegmentsTask entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = examWorkflowSegmentsTaskService.get(id);
        }
        if (entity == null) {
            entity = new ExamWorkflowSegmentsTask();
        }
        return entity;
    }

    @RequiresPermissions("exam:examWorkflowSegmentsTask:view")
    @RequestMapping(value = {"list", ""})
    public String list(ExamWorkflowSegmentsTask examWorkflowSegmentsTask, HttpServletRequest request, HttpServletResponse response, Model model) {
        String templatesIds = request.getParameter("fileTemplatesIds");
        String[] split = null;
        List<Map<String, String>> tabList = null;
        if (templatesIds != null) {
            split = templatesIds.split(",");
            model.addAttribute("templatesIds", split);
            tabList = examWorkflowSegmentsTaskService.findTabList(Arrays.asList(split));

        } else {
            model.addAttribute("templatesIds", examWorkflowSegmentsTask.getTemplatesIds());
            tabList = examWorkflowSegmentsTaskService.findTabList(Arrays.asList(examWorkflowSegmentsTask.getTemplatesIds()));
        }
        model.addAttribute("tabList", tabList);
        //tag页顺序保持一致
        examWorkflowSegmentsTask.setTagType(tabList.get(0).get("id"));
        Page<ExamWorkflowSegmentsTask> page = examWorkflowSegmentsTaskService.findPage(new Page<ExamWorkflowSegmentsTask>(request, response, -1), examWorkflowSegmentsTask);
        model.addAttribute("page", page);
        model.addAttribute("segmentId", examWorkflowSegmentsTask.getSegmentId());
        model.addAttribute("workflowId", page.getList() != null && page.getList().size() > 0 ? page.getList().get(0).getWorkflowId() : null);

        return "modules/exam/examWorkflowSegmentsTaskList";
    }

    /**
     * 考评流程任务分配
     * @param workflowId
     * @param segmentId 考评环节id
     * @param model
     * @return
     */
    /*@RequiresPermissions("exam:examWorkflowSegmentsTask:view")*/
    @RequestMapping(value = "personAssign")
    public String personAssign(String workflowId, String segmentId,String segmentName, Model model) {
        ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(workflowId);
        String standardIds = examWorkflowDefine.getTemplatesIds();
        List<ExamStandardBaseInfo> standardInfoList = new ArrayList<ExamStandardBaseInfo>();
        for (String id : standardIds.split(",")) {
            ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(id);
            standardInfoList.add(examStandardBaseInfo);
        }
        /*考评标准*/
        model.addAttribute("standardInfoList", standardInfoList);
        /**/
        model.addAttribute("workflowId", workflowId);
        model.addAttribute("segmentId", segmentId);
        model.addAttribute("segmentName", segmentName);
        return "modules/exam/examWorkflowSegmentPersonAssign";
    }

    /**
     * 任务分配页面
     * @param request
     * @param response
     * @param model
     * @return
     */
    /*@RequiresPermissions("exam:examWorkflowSegmentsTask:view")*/
    @RequestMapping(value = "content")
    public String appraiseContent(HttpServletRequest request, HttpServletResponse response, Model model) {

        /*是否多选*/
        if (null != request.getParameter("segmentId")) {
            String segmentId = request.getParameter("segmentId").toString();
            String segmentName = request.getParameter("segmentName").toString();
            if (StringUtils.isNotBlank(segmentId) && segmentId.contains("1")) {
                model.addAttribute("check", "isCheck");
                model.addAttribute("segmentName", segmentName);
                model.addAttribute("segmentId", segmentId);
            }
        }
        String standardId = "";
        if (null != request.getParameter("standardId")) {
            standardId = request.getParameter("standardId").toString();
        }
        String workflowId = "";
        if (null != request.getParameter("workflowId")) {
            workflowId = request.getParameter("workflowId").toString();
        }
        String segmentId = "";
        if (null != request.getParameter("segmentId")) {
            segmentId = request.getParameter("segmentId").toString();
        }
        Map<String, String> dparam = new HashMap<String, String>();
        dparam.put("examStardardId", standardId);
        ExamStandardTemplateDefine entity = examStandardTemplateDefineService.getNew(dparam);
        List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
        List<ExamStandardTemplateItemDefine> columnList =null;
        /*获取模板的列*/
        if (null != entity) {
            //获取考评项数据格式列
            ExamStandardTemplateItemDefine examStandardTemplateItemDefine = new ExamStandardTemplateItemDefine();
            examStandardTemplateItemDefine.setTemplateId(entity.getId());
            Page<ExamStandardTemplateItemDefine> page = null;
            Page<ExamStandardTemplateItemDefine> pageParam = new Page<ExamStandardTemplateItemDefine>(request, response, -1);
            pageParam.setOrderBy("column_order");
            page = examStandardTemplateItemDefineService.findPage(pageParam, examStandardTemplateItemDefine);
            columnList = page.getList();
            request.setAttribute("columnList", columnList);

            //获取考评项数据
            Map<String, String> param = new HashMap<String, String>();
            param.put("examStardardId", standardId);
            List<Map<String, String>> list = examStandardTemplateDefineService.findTemplateDatas(param);
            Map<String, String> rowMap = null;
            String rowNum = "-1";
            for (Map<String, String> item : list) {
                if (!rowNum.equals(String.valueOf(item.get("row_num")))) {
                    rowNum = String.valueOf(item.get("row_num"));
                    rowMap = new HashMap<String, String>();
                    rowMap.put("row_num", rowNum);
                    rowList.add(rowMap);
                }
                //审核单位
                if ("5".equals(String.valueOf(item.get("column_type")))) {
                    String depart = String.valueOf(item.get("item_value"));
                    depart = depart.replaceAll("\r","");
                    depart = depart.replaceAll("\t","");
                    rowMap.put("examDepart", depart);
                }
                //自评单位
                if ("8".equals(String.valueOf(item.get("column_type")))) {
                    String depart = String.valueOf(item.get("item_value"));
                    depart = depart.replaceAll("\r","");
                    depart = depart.replaceAll("\t","");
                    rowMap.put("selfDepart", depart);
                }
                rowMap.put(item.get("item_id"), item.get("item_value"));
            }
            ExamWorkflowSegmentsTask examWorkflowSegmentsTask = new ExamWorkflowSegmentsTask();
            examWorkflowSegmentsTask.setWorkflowId(workflowId);
            examWorkflowSegmentsTask.setSegmentId(segmentId);
            examWorkflowSegmentsTask.setStandardId(standardId);
            List<ExamWorkflowSegmentsTask> tasksList = examWorkflowSegmentsTaskService.findList(examWorkflowSegmentsTask);
            boolean isFirstAssign = true;
            if (null != rowList) {
                for (Map<String, String> item : rowList) {
                    if (null != tasksList) {
                        for (ExamWorkflowSegmentsTask task : tasksList) {
                            if (item.get("row_num").equals(task.getRowNum().toString()) && null != task.getIds() && !"".equals(task.getIds())) {
                                item.put("ids", task.getIds());
                                item.put("personNames", task.getPersonNames());
                                item.put("taskId", task.getId());
                                isFirstAssign = false;
                            }
                        }
                    }
                }
            }
//            if (null != rowList && isFirstAssign) {
//                //ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
//                ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(standardId);
//                ExamPersonsAssignRuleChild examPersonsAssignRuleChild = new ExamPersonsAssignRuleChild();
//                String ruleId = "";
//                //局考处
//                if ("1".equals(examStandardBaseInfo.getKpType())||"2".equals(examStandardBaseInfo.getKpType())) {
//                    ruleId = "655784b4886c4d59b286c6f5ee61fc95";
//                }
//                examPersonsAssignRuleChild.setRuleId(ruleId);
//                List<ExamPersonsAssignRuleChild> ruleList = examPersonsAssignRuleChildService.findList(examPersonsAssignRuleChild);
//                for (Map<String, String> item : rowList) {
//                    if ("2".equals(examStandardBaseInfo.getKpType())||"4".equals(examStandardBaseInfo.getKpType())||"6".equals(examStandardBaseInfo.getKpType())||"7".equals(examStandardBaseInfo.getKpType())){
//                        item.put("ids", examStandardBaseInfo.getUnitId());
//                        item.put("personNames", examStandardBaseInfo.getUnitName());
//                    }else{
//                        Map<String, String> personInfo = this.getPersonInfo(ruleList, item, segmentId);
//                        item.put("ids", personInfo.get("ids"));
//                        item.put("personNames", personInfo.get("personNames"));
//                    }
//                }
//            }
        }
        ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(standardId);
        model.addAttribute("rowList", this.displayProcess(rowList,columnList,examStandardBaseInfo));
        model.addAttribute("workflowId", workflowId);
        model.addAttribute("examStandardTemplateDefine", entity);
        model.addAttribute("segmentId", segmentId);
        List<ExamObjectType> examObjectTypeList = examObjectTypeService.getALLNameId(new ExamObjectType());
        model.addAttribute("examObjectTypeList", examObjectTypeList);
        return "modules/exam/segmentTask_content";
    }

    /**
     * 显示处理
     * @param rowList
     * @return
     */
    private List<Map<String, String>> displayProcess(List<Map<String, String>>  rowList,List<ExamStandardTemplateItemDefine> columnList, ExamStandardBaseInfo examStandardBaseInfo){
        List<Map<String, String>> resutList = new ArrayList<Map<String, String>>();
        if(rowList != null){
            String type ="";
            String tmpType ="";
            //公共加扣分
            if("2".equals(examStandardBaseInfo.getModelType())||"3".equals(examStandardBaseInfo.getModelType())){
                for(int i=0; i<rowList.size(); i++ ){
                    Map<String, String> row = rowList.get(i);
                    columnList.removeIf(item -> item==null);
                    if(i ==0 && columnList.size()>0){
                        String columnId = columnList.get(0).getId();
                        String r = row.get(columnId);
                        if (StringUtils.isBlank(r)){
                            r= "";
                        }
                        tmpType =r.replaceAll("[\\t\\n\\r]","");
                        tmpType = tmpType.replaceAll(" ","");
                        row.put("display","block");
                    }else {
                        row.put("display","none");
                    }
                    /*替换特殊字符 有特殊字符无法弹出单位选择*/
                    tmpType = StringUtils.getSpecialCharacter(tmpType);
                    row.put("type","all_"+tmpType);
                    resutList.add(row);
                }
            } else {
                for(Map<String, String> row:rowList){
                    tmpType =row.get(columnList.get(0).getId()).replaceAll("[\\t\\n\\r]","");
                    tmpType = tmpType.replaceAll(" ","");
                    if(!type.equals(tmpType)){
                        type = tmpType;
                        row.put("display","block");
                    }else{
                        row.put("display","none");
                    }
                    /*替换特殊字符 有特殊字符无法弹出单位选择*/
                    tmpType = StringUtils.getSpecialCharacter(tmpType);
                    //预防数字开头的id
                    row.put("type","_"+tmpType);
                    resutList.add(row);
                }
            }
        }
        return resutList;
    }

    /**
     * 获取默认自评、考核人员信息
     *
     * @param ruleList
     * @param item
     * @param segmentId
     * @return
     */
    private Map<String, String> getPersonInfo(List<ExamPersonsAssignRuleChild> ruleList, Map<String, String> item, String segmentId) {
        String ids = "";
        String personNames = "";
        if (null != ruleList) {
            if (null != item.get("examDepart")) {
                for (ExamPersonsAssignRuleChild rule : ruleList) {
                    //TODO考评部门回车
                    if (item.get("examDepart").equals(rule.getExamDepart())) {
                        //自评人
                        if ("1".equals(segmentId)) {
                            ids = rule.getSelfPersonIds();
                            personNames = rule.getSelfPersonNames();
                        } else {//考评人
                            ids = rule.getExamPersonIds();
                            personNames = rule.getExamPersonNames();
                        }
                        break;
                    }
                }
            } else if (null != item.get("selfDepart")) {
                for (ExamPersonsAssignRuleChild rule : ruleList) {
                    if (item.get("selfDepart").equals(rule.getExamDepart())) {
                        if ("1".equals(segmentId)) {
                            ids = rule.getSelfPersonIds();
                            personNames = rule.getSelfPersonNames();
                        } else {
                            ids = rule.getExamPersonIds();
                            personNames = rule.getExamPersonNames();
                        }
                        break;
                    }
                }
            } else {

            }
        }
        Map<String, String> personInfo = new HashMap<String, String>();
        personInfo.put("ids", ids);
        personInfo.put("personNames", personNames);
        return personInfo;
    }

    @RequiresPermissions("exam:examWorkflowSegmentsTask:view")
    @RequestMapping(value = "form")
    public String form(ExamWorkflowSegmentsTask examWorkflowSegmentsTask, Model model) {
        model.addAttribute("examWorkflowSegmentsTask", examWorkflowSegmentsTask);
        return "modules/exam/examWorkflowSegmentsTaskForm";
    }

//    @RequiresPermissions("exam:examWorkflowSegmentsTask:edit")
    @RequestMapping(value = "assignDatasave")
    public String assignDatasSave(ExamWorkflowSegmentsTaskAssign examWorkflowSegmentsTaskAssign, RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {
        if (null != examWorkflowSegmentsTaskAssign.getDatas()) {
            List<ExamWorkflowSegmentsTask> saveList = new ArrayList<ExamWorkflowSegmentsTask>();
            List<String> deleteIdList = new ArrayList<String>();
            for (ExamWorkflowSegmentsTask data : examWorkflowSegmentsTaskAssign.getDatas()) {
                /*仅供开发人员配置数据使用，不小心提交请及时修改*/
              /*  if (StringUtils.isBlank(data.getIds())){
                    data.setIds("66666666666666666666");
                    data.setPersonNames("配置数据");
                }*/
                if (data.getIds() != null && !"".equals(data.getIds())) {
                    saveList.add(data);
                }
                if(data.getIds().isEmpty()){
                    data.setIds(null);
                    data.setPersonNames(null);
                    deleteIdList.add(data.getId());
                }
//                saveList.add(data);
            }
            for (ExamWorkflowSegmentsTask data : saveList) {
                if (!beanValidator(model, data)) {
                    return form(data, model);
                }
                examWorkflowSegmentsTaskService.save(data);
            }
            if(deleteIdList.size()>0&&deleteIdList!=null)
            examWorkflowSegmentsTaskService.deleteByIds(deleteIdList);
            //examWorkflowSegmentsTaskService.updateAssignData(saveList);
        }
        String standardId = "";
        if (null != request.getParameter("standardId")) {
            standardId = request.getParameter("standardId").toString();
        }
        String workflowId = "";
        if (null != request.getParameter("workflowId")) {
            workflowId = request.getParameter("workflowId").toString();
        }
        String segmentId = "";
        if (null != request.getParameter("segmentId")) {
            segmentId = request.getParameter("segmentId").toString();
        }
        String segmentName = "";
        if (null != request.getParameter("segmentName")) {
            segmentName = request.getParameter("segmentName").toString();
        }
        addMessage(redirectAttributes, "保存数据成功");
        redirectAttributes.addAttribute("segmentName",segmentName);
        return "redirect:" + Global.getAdminPath() + "/exam/examWorkflowSegmentsTask/content?workflowId=" + workflowId + "&segmentId=" + segmentId + "&standardId=" + standardId;
    }

    /**
     * 人员任务分配保存
     *
     * @param examWorkflowSegmentsTask
     * @param model
     * @param redirectAttributes
     * @param request
     * @return
     */
    /*@RequiresPermissions("exam:examWorkflowSegmentsTask:edit")*/
    @RequestMapping(value = "save")
    public String save(ExamWorkflowSegmentsTask examWorkflowSegmentsTask, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, examWorkflowSegmentsTask)) {
            return form(examWorkflowSegmentsTask, model);
        }
        String[] templatesIds = examWorkflowSegmentsTask.getTemplatesIds();
        String[] pIds = request.getParameterValues("personIds");
        if (pIds != null && pIds.length > 0) {
            for (String id : pIds) {
                String personNames = request.getParameter("personNames" + id);
                String personIds = request.getParameter("ids" + id);
                //String workType = request.getParameter("workType" + id);
                //String segmentId = request.getParameter("segmentId" + id);
                //ExamWorkflowSegmentsTask task = new ExamWorkflowSegmentsTask();
                ExamWorkflowSegmentsTask task = examWorkflowSegmentsTaskService.get(id);
                //task.setId(id);
                //task.setSegmentId(segmentId);
                //task.setWorkflowId(examWorkflowSegmentsTask.getWorkflowId());
                if (personIds != null && personIds.length() > 0) {
                    task.setPersonNames(personNames);
                    task.setIds(personIds);
                }
                //task.setWorkType(workType);
                //task.setTagType(examWorkflowSegmentsTask.getTagType());
                examWorkflowSegmentsTaskService.save(task);
            }
            //更新exam_workflow_segment_guanlian表的status字段
            Integer sum = examWorkflowSegmentsTaskService.findSumByWdIdAndSegId(examWorkflowSegmentsTask.getWorkflowId(), request.getParameter("segmentId" + pIds[0]));
            Integer num = examWorkflowSegmentsTaskService.findNumByWdIdAndSegIdAndIds(examWorkflowSegmentsTask.getWorkflowId(), request.getParameter("segmentId" + pIds[0]));
            //ExamWorkflowSegmentGuanlian examWorkflowSegmentGuanlian = examWorkflowSegmentGuanlianService.get(request.getParameter("segmentId" + pIds[0]));
            ExamWorkflowSegmentGuanlian examWorkflowSegmentGuanlian = examWorkflowSegmentGuanlianService.findByWdIdAndSegDefId(examWorkflowSegmentsTask.getWorkflowId(), request.getParameter("segmentId" + pIds[0]));
            if (0 == num.intValue()) {
                examWorkflowSegmentGuanlian.setStatus("未分配");
                examWorkflowSegmentGuanlianService.save(examWorkflowSegmentGuanlian);
            } else if (sum.intValue() == num.intValue()) {
                examWorkflowSegmentGuanlian.setStatus("分配完成");
                examWorkflowSegmentGuanlianService.save(examWorkflowSegmentGuanlian);
            } else {
                examWorkflowSegmentGuanlian.setStatus("未全部分配完");
                examWorkflowSegmentGuanlianService.save(examWorkflowSegmentGuanlian);
            }
        }
        addMessage(redirectAttributes, "保存任务分配成功");
        String str1 = "";
        if (templatesIds != null && templatesIds.length > 0) {
            for (int i = 0; i < templatesIds.length; i++) {
                str1 += templatesIds[i];
                if (i != templatesIds.length - 1) {
                    str1 += ",";
                }
            }
        }

        if ("0".equals(request.getParameter("tagIndex"))) {
            return "redirect:" + Global.getAdminPath() + "/exam/examWorkflowSegmentsTask/?repage?repage&tagType=" + examWorkflowSegmentsTask.getTagType() + "&segmentId=" + request.getParameter("segmentId" + pIds[0]) + "&fileTemplatesIds=" + str1;
        } else {
            return "redirect:" + Global.getAdminPath() + "/exam/examWorkflowSegmentsTask/findBytTagType?repage&tagType=" + examWorkflowSegmentsTask.getTagType() + "&segmentId=" + request.getParameter("segmentId" + pIds[0]);
        }
    }

    @RequiresPermissions("exam:examWorkflowSegmentsTask:edit")
    @RequestMapping(value = "delete")
    public String delete(ExamWorkflowSegmentsTask examWorkflowSegmentsTask, RedirectAttributes redirectAttributes) {
        examWorkflowSegmentsTaskService.delete(examWorkflowSegmentsTask);
        addMessage(redirectAttributes, "删除考评流程任务分配成功");
        return "redirect:" + Global.getAdminPath() + "/exam/examWorkflowSegmentsTask/?repage";
    }

    /**
     * 部门公共加分/扣分页面
     *
     * @param examWorkflowSegmentsTask
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("exam:examWorkflowSegmentsTask:view")
    @RequestMapping(value = {"findBytTagType"})
    public String findBytTagType(ExamWorkflowSegmentsTask examWorkflowSegmentsTask, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        Page<ExamWorkflowSegmentsTask> page = examWorkflowSegmentsTaskService.findPage(new Page<ExamWorkflowSegmentsTask>(request, response, -1), examWorkflowSegmentsTask);
        model.addAttribute("page", page);
        model.addAttribute("segmentId", examWorkflowSegmentsTask.getSegmentId());
        model.addAttribute("workflowId", page.getList() != null && page.getList().size() > 0 ? page.getList().get(0).getWorkflowId() : null);
        return "modules/exam/examWorkflowSegmentsTaskJiaKouFenList";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setAutoGrowNestedPaths(true);
        binder.setAutoGrowCollectionLimit(2048);
    }

    @RequestMapping(value = {"selUnitNameByPids"})
    @ResponseBody
    public Result selUnitNameByPids(HttpServletRequest request,HttpServletResponse response,
                                @RequestParam("mySelIds") String[] mySelIds,
                                @RequestParam("mySelNames")String[] mySelNames,
                                @RequestParam("mySelPids")String[] mySelPids){
        Result result = new Result();
        //List<Office> officeList = officeService.findAll();
        List<Map<String,String>> mapList = new ArrayList<>();
        for (int i = 0;i<mySelIds.length;i++){
            Map<String,String> map = new HashMap<>();
            map.put("selId",mySelIds[i]);
            map.put("selName",mySelNames[i]);
            map.put("selPid",mySelPids[i]);
            String unitName ="";
            unitName = officeService.get(mySelPids[i]).getName();
            /*for(Office office : officeList){
                if(office.getId().equals(mySelPids[i])){
                    unitName = office.getName();
                    continue;
                }
            }*/
            map.put("unitName",unitName);
            mapList.add(map);
        }
        result.setResult(mapList);
        return result;

    }

    @RequestMapping(value = {"selUnitNameById"})
    @ResponseBody
    public Result selUnitNameById(HttpServletRequest request,HttpServletResponse response,
                                  @RequestParam("mySelIds") String[] mySelIds){
        Result result = new Result();
        List<Map<String,String>> list = new ArrayList<>();
        for (String id : mySelIds){
            Map<String,String> map = new HashMap<>();
            User user = UserUtils.get(id);
            map.put("selId",user.getId());
            map.put("selName",user.getName());
            map.put("unitName",user.getOffice().getName());
            list.add(map);
        }
        result.setResult(list);
        return result;
    }


    @RequestMapping(value = {"fastCheck"})
    @ResponseBody
    public Result fastCheck(HttpServletRequest request,HttpServletResponse response,@RequestParam("objectTypeId") String objectTypeId){
        Result result = new Result();
        ExamObjectType examObjectType = examObjectTypeService.get(objectTypeId);
        Map<String,String> resultMap = new HashMap<>();
        String[] objectUserIds = examObjectType.getObjectUserId().split(",");
        StringBuffer myselIds = null;
        StringBuffer myselNames = null;
        StringBuffer mySelPids = null;
        if(objectUserIds!=null && objectUserIds.length>0){
            for(String userId : objectUserIds){
                User user = UserUtils.get(userId);
               if(myselIds==null){
                   myselIds = new StringBuffer(user.getId());
               }else{
                   myselIds.append(","+user.getId());
               }
                if(myselNames==null){
                    myselNames = new StringBuffer(user.getName());
                }else{
                    myselNames.append(","+user.getName());
                }
                if(mySelPids==null){
                    mySelPids = new StringBuffer(user.getOffice().getId());
                }else{
                    mySelPids.append(","+user.getOffice().getId());
                }
            }
        }
        resultMap.put("myselIds",myselIds.toString());
        resultMap.put("myselNames",myselNames.toString());
        resultMap.put("mySelPids",mySelPids.toString());
        result.setResult(resultMap);
        result.setSuccess(true);
        return result;
    }
}