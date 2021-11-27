/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExcelTemplate;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateData;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateDefine;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateItemDefine;
import com.thinkgem.jeesite.modules.exam.service.ExamStandardTemplateDataService;
import com.thinkgem.jeesite.modules.exam.service.ExamStandardTemplateDefineService;
import com.thinkgem.jeesite.modules.exam.service.ExamStandardTemplateItemDefineService;
import com.thinkgem.jeesite.modules.exam.service.ExamWorkflowSegmentsTaskService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.ss.usermodel.Row;
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
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 考评模板Controller
 *
 * @author bradley.zhao
 * @version 2019-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examStandardTemplateDefine")
public class ExamStandardTemplateDefineController extends BaseController {

    @Autowired
    private ExamStandardTemplateDefineService examStandardTemplateDefineService;

    @Autowired
    private ExamStandardTemplateItemDefineService examStandardTemplateItemDefineService;

    @Autowired
    private ExamStandardTemplateDataService examStandardTemplateDataService;

    @Autowired
    private ExamWorkflowSegmentsTaskService examWorkflowSegmentsTaskService;

    @ModelAttribute
    public ExamStandardTemplateDefine get(@RequestParam(required = false) String id) {
        ExamStandardTemplateDefine entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = examStandardTemplateDefineService.get(id);
        }
        if (entity == null) {
            entity = new ExamStandardTemplateDefine();
        }
        return entity;
    }

    @RequiresPermissions("exam:examStandardTemplateDefine:view")
    @RequestMapping(value = {"list", ""})
    public String list(ExamStandardTemplateDefine examStandardTemplateDefine, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ExamStandardTemplateDefine> page = examStandardTemplateDefineService.findPage(new Page<ExamStandardTemplateDefine>(request, response), examStandardTemplateDefine);
        model.addAttribute("page", page);
        return "modules/exam/examStandardTemplateDefineList";
    }

    @RequiresPermissions("exam:examStandardTemplateDefine:view")
    @RequestMapping(value = "form")
    public String form(ExamStandardTemplateDefine examStandardTemplateDefine, Model model) {
        model.addAttribute("examStandardTemplateDefine", examStandardTemplateDefine);
        return "modules/exam/examStandardTemplateDefineForm";
    }

    @RequiresPermissions("exam:examStandardTemplateDefine:edit")
    @RequestMapping(value = "save")
    public String save(ExamStandardTemplateDefine examStandardTemplateDefine, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, examStandardTemplateDefine)) {
            return form(examStandardTemplateDefine, model);
        }
        examStandardTemplateDefineService.save(examStandardTemplateDefine);
        addMessage(redirectAttributes, "保存考评标准模板成功");
        request.setAttribute("saveResult","success");
        return "redirect:" + Global.getAdminPath() + "/exam/examStandardTemplateDefine/?repage";
    }

    @RequiresPermissions("exam:examStandardTemplateDefine:edit")
    @RequestMapping(value = "delete")
    public String delete(ExamStandardTemplateDefine examStandardTemplateDefine, RedirectAttributes redirectAttributes) {
        examStandardTemplateDefineService.delete(examStandardTemplateDefine);
        addMessage(redirectAttributes, "删除考评标准模板成功");
        return "redirect:" + Global.getAdminPath() + "/exam/examStandardTemplateDefine/?repage";
    }

    @RequiresPermissions("exam:examStandardTemplateDefine:edit")
    @RequestMapping(value = "items")
    public String download(HttpServletResponse response, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        String targetUrl = "modules/exam/template_items";

        if (null != request.getParameter("operation") && !"".equals(request.getParameter("operation"))) {
            String operation = request.getParameter("operation");
            if (operation.equals("view")){
                targetUrl = "modules/exam/templateItemsView";
            }
        }

        /*考评标准ID*/
        String standardId = "";
        /*被考评对象类别*/
        String objType = "";
        /*考评类别*/
        String kpType = "";
        /*考评周期*/
        String cycle = "";
        /*模板Id*/
        String templateId = "";
        if (null != request.getParameter("standard_id") && !"".equals(request.getParameter("standard_id"))) {
            standardId = request.getParameter("standard_id");
            request.setAttribute("standard_id", standardId);
        }
        if (null != request.getParameter("objType") && !"".equals(request.getParameter("objType"))) {
            objType = request.getParameter("objType");
            request.setAttribute("objType", objType);
        }
        if (null != request.getParameter("kpType") && !"".equals(request.getParameter("kpType"))) {
            kpType = request.getParameter("kpType");
            request.setAttribute("kpType", kpType);
        }
        if (null != request.getParameter("cycle") && !"".equals(request.getParameter("cycle"))) {
            cycle = request.getParameter("cycle");
            request.setAttribute("cycle", cycle);
        }
        if (null != request.getParameter("template") && !"".equals(request.getParameter("template"))) {
            templateId = request.getParameter("template");
            request.setAttribute("template", templateId);
        }
        ExamStandardTemplateDefine examStandardTemplateDefine= new ExamStandardTemplateDefine();
        examStandardTemplateDefine.setDelFlag("0");
        examStandardTemplateDefine.setObjectCategory(objType);
        examStandardTemplateDefine.setKpType(kpType);
        examStandardTemplateDefine.setCycle(cycle);
        //根据类别查询符合标准的考评模板
        /*并且是自己创建的,避免导入时出现差错，只显示与自己相关的*/
        examStandardTemplateDefine.getSqlMap().put("dsf","and (a.create_by = '"+ UserUtils.getUser().getId() +"' or a.update_by = '"+UserUtils.getUser().getId()+"')");
        List<ExamStandardTemplateDefine> templateList = examStandardTemplateDefineService.findList(examStandardTemplateDefine);
        request.setAttribute("templateList",templateList);

        Map<String,String> dparam= new HashMap<String,String>();
        dparam.put("examStardardId",standardId);
//        dparam.put("id",templateId);
        ExamStandardTemplateDefine entity = examStandardTemplateDefineService.getNew(dparam);

        /*和getNew里的方法一样啊*/
        if(null != entity){
            if (StringUtils.isBlank(templateId)){
                templateId = entity.getId();
            }
            //获取考评项数据格式列
            ExamStandardTemplateItemDefine examStandardTemplateItemDefine = new ExamStandardTemplateItemDefine();
            examStandardTemplateItemDefine.setTemplateId(entity.getId());
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
        }
        if (StringUtils.isBlank(templateId) && templateList!=null && templateList.size()>0){
            templateId=templateList.get(0).getId();
        }
        request.setAttribute("templateId",templateId);
        return targetUrl;
    }

    /**
     * 导入考评标准的详细标准
     * @param file
     * @param redirectAttributes
     * @param response
     * @param request
     * @return
     */
    @RequiresPermissions("exam:examStandardTemplateDefine:edit")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes, HttpServletResponse response, HttpServletRequest request) {
        //获取标准导入模板格式
        /*考评标准Id*/
        String standardId = request.getParameter("standard_id");
        request.setAttribute("standardId",standardId);
        /*考评模板Id*/
        String templateId = request.getParameter("template");
        Map<String,String> param= new HashMap<String,String>();
        param.put("examStardardId",standardId);
        ExamStandardTemplateDefine entity = null;
        if (StringUtils.isNotBlank(standardId)) {
            /*更新模板的外键  考评标准与考评标准模板一一对应（一对一关系）*/
            /*把所有standardId相同的模板的standardId都设为空然后在一一对应，保证一个模板只有一个标准*/
            examStandardTemplateDefineService.clearExamStardardId(standardId);
            examStandardTemplateDefineService.updateExamStardardId(templateId,standardId);
            /*导入时  先删除之前导入的数据（标准）此处应该让用户进行选择是否清除*/
            /*用户检查数据丢失情况*/
            ExamStandardTemplateData standardTemplateData = new ExamStandardTemplateData();
            standardTemplateData.setItemId(standardId);
            standardTemplateData.setItemValue(UserUtils.getUser().getId()+"exam_standard_template_define id："+templateId+"：删除了exam_standard_template_define StandardId："+standardId);
            examStandardTemplateDataService.save(standardTemplateData);
            examStandardTemplateDataService.deleteByExamStandardId(standardId);
            /*考评项目重新导入时需要同时删除任务分配流程中的数据（逻辑删除）  不然会导致任务分配情况显示异常*/
            examWorkflowSegmentsTaskService.deleteByStandardId(standardId);
            logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            logger.error("\n");
            logger.error("\n");
            logger.error("用户"+UserUtils.getUser().getName()+"导入考评项目时删除了"+standardId);
            logger.error("\n");
            logger.error("\n");
            logger.error("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            /*得到新的模板(已包含模板的列)*/
            entity = examStandardTemplateDefineService.get(templateId);
            //获取模板格式项目
            ExamStandardTemplateItemDefine examStandardTemplateItemDefine = new ExamStandardTemplateItemDefine();
            examStandardTemplateItemDefine.setTemplateId(templateId);
            Page<ExamStandardTemplateItemDefine> page = null;
            Page<ExamStandardTemplateItemDefine> pageParam = new Page<ExamStandardTemplateItemDefine>(request, response, -1);
            /*再次获取模板的列  根据column_order排序*/
            pageParam.setOrderBy("column_order");
            page = examStandardTemplateItemDefineService.findPage(pageParam, examStandardTemplateItemDefine);
            try {
                int successNum = 0;
                StringBuilder failureMsg = new StringBuilder();
                int startRowNum = entity.getStartrowNum();
//                int endRowNum = entity.getEndrowNum();
                /*读取文件*/
                ImportExcel ei = new ImportExcel(file, startRowNum, 0);
                //处理导出模板问题
                if(ei.getRow(0)==null){
                    ei = new ImportExcel(file, startRowNum, 3);
                }
                /*导入数据少一行  去掉减一*/
                int lastRow = ei.getLastDataRowNum();
                /*模板的每一列*/
                List<ExamStandardTemplateItemDefine> columnList = page.getList();
                /*遍历导入模板的每一行数据*/
                for (int i = startRowNum; i < lastRow; i++) {
                    Row row = ei.getRow(i);
                    /*遍历模板的每一列，依次保存行中的数据*/
                    for (ExamStandardTemplateItemDefine item : columnList) {
                        Object val = ei.getCellValue(row, item.getColumnOrder());
                        ExamStandardTemplateData examStandardTemplateData = new ExamStandardTemplateData();
                        examStandardTemplateData.setItemId(item.getId());
                        String tempValue = val.toString();
                        String itemType = item.getColumnType();

                        //序号去掉0
                        if("7".equals(itemType)&& tempValue.indexOf(".")>0){
                            tempValue = tempValue.substring(0,tempValue.indexOf("."));
                        }
                        examStandardTemplateData.setItemValue(tempValue);
                        examStandardTemplateData.setRowNum(i);
                        examStandardTemplateDataService.save(examStandardTemplateData);
                    }
                    successNum++;
                }
                /*插入数据后，缓存设置为空，更新数据后，也设置为空，否则，再次导入时，读取到的数据为缓存中的值*/
                CacheUtils.put("template_"+standardId, null);
                //todo
                addMessage(redirectAttributes, "已成功导入 " + successNum + " 条" + failureMsg);
            } catch (Exception e) {
                e.printStackTrace();
                addMessage(redirectAttributes, "导入失败！失败信息：" + e.getMessage());
            }
        }

        /*考评标准Id*/
        if (null != request.getParameter("standard_id") && !"".equals(request.getParameter("standard_id"))) {
            standardId = request.getParameter("standard_id");
            redirectAttributes.addAttribute("standard_id", standardId);
        }
        /*考评类别*/
        if (null != request.getParameter("objType") && !"".equals(request.getParameter("objType"))) {
            String objType = request.getParameter("objType");
            redirectAttributes.addAttribute("objType", objType);
        }
        /*被考评对象类别*/
        if (null != request.getParameter("kpType") && !"".equals(request.getParameter("kpType"))) {
            String kpType = request.getParameter("kpType");
            redirectAttributes.addAttribute("kpType", kpType);
        }
        /*考评周期*/
        if (null != request.getParameter("cycle") && !"".equals(request.getParameter("cycle"))) {
            String cycle = request.getParameter("cycle");
            redirectAttributes.addAttribute("cycle", cycle);
        }
        /*考评模板ID*/
        if (null != request.getParameter("template") && !"".equals(request.getParameter("template"))) {
            templateId = request.getParameter("template");
            redirectAttributes.addAttribute("template", templateId);
        }
        return "redirect:" + adminPath+"/exam/examStandardTemplateDefine/items";
//        return "modules/exam/template_items";
    }

    /**
     * 下载考评标准模板
     * @param response
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("downloadTemplate")
    public String downloadTemplate(HttpServletResponse response, HttpServletRequest request, RedirectAttributes redirectAttributes){
        String standard_id = request.getParameter("standard_id");
        /*标准考评模板Id*/
        String templateDefineId = request.getParameter("template");
        /*查询模板名称*/
        ExamStandardTemplateDefine examStandardTemplateDefine = examStandardTemplateDefineService.get(templateDefineId);
        ExamStandardTemplateItemDefine examStandardTemplateItemDefine  = new ExamStandardTemplateItemDefine();
        examStandardTemplateItemDefine.setTemplateId(templateDefineId);

        /*查询模板的列*/
        Page<ExamStandardTemplateItemDefine> page  =new Page<>(request, response, -1);
        page.setOrderBy("column_order");
        page= examStandardTemplateItemDefineService.findPage(page,examStandardTemplateItemDefine);
        List<ExamStandardTemplateItemDefine> list=page.getList();
        String fileSeperator = File.separator;
        String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;
        String fileName = "绩效考评空模板.xlsx";

        ExcelTemplate excelTemplate = new ExcelTemplate(filePath+fileName);
        Map<String,String> map = new HashMap<>();
        map.put("title",examStandardTemplateDefine.getName());
        LinkedHashMap<Integer, LinkedList<String>> areaValues = new LinkedHashMap<>();
        LinkedList<String> valuesList = new LinkedList<>();
        /*按照列顺序号添加导入数据  列已经排序*/
        for (int i = 0; i < list.size(); i++) {
            valuesList.add(list.get(i).getColumnName());
        }
        //处理未被使用的占位符
        int oldSize = valuesList.size();
        if(valuesList.size()<19){
            for(int i = 0;i<(19-oldSize);i++){
                valuesList.add("");
            }
        }
        areaValues.put(0,valuesList);
        try {
            /*首行列宽*/
//            int width = examStandardTemplateDefine.getExamStandardTemplateItemDefineList().size()+20;
//            excelTemplate.getWorkbook().getSheetAt(0).setColumnWidth(0,width);
            /*写入标题*/
            excelTemplate.fillVariable(0,map);
            /*写入列*/
            excelTemplate.addRowByExist(0,1,1,2,areaValues,true);
            /*合并第一行标题单元格*/
            int lastCol = list.size()>0?list.size()-1:0;
            excelTemplate.mergedRegion(0,0,0,0,lastCol);
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(examStandardTemplateDefine.getName()+".xlsx"));
            ServletOutputStream outputStream = response.getOutputStream();
            excelTemplate.getWorkbook().write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:" + adminPath+"/exam/examStandardTemplateDefine/items";
    }

    /**
     *
     * @param optionId
     * @param standardId
     * @return
     */
    @ResponseBody
    @RequestMapping("getOptionsDetail")
    public Result getOptionsDetail(String optionId,String standardId){
        Result result = new Result();
        List<Map<String, String>> list = examStandardTemplateDefineService.findTemplateDatasByOption(optionId,standardId);
        StringBuffer stringBuffer = new StringBuffer();
        list.stream().forEach(item ->{
            stringBuffer.append(item.get("column_name")+":"+item.get("item_value")+"\n");
        });
        result.setSuccess(true);
        result.setResult(stringBuffer.toString());
        return result;
     }

}