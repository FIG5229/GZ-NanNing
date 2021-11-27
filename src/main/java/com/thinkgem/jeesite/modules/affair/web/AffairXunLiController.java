/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExcelTemplate;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.affair.service.AffairExamEnteringService;
import com.thinkgem.jeesite.modules.affair.service.AffairTrainOutsourceService;
import com.thinkgem.jeesite.modules.affair.service.AffairXunLiService;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.ibatis.annotations.Param;
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
import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 训厉功能
 *
 * @author tom.fu
 * @version 2020-8-20
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairXunLi")
public class AffairXunLiController extends BaseController {

    @Autowired
    private AffairXunLiService affairXunLiService;

    @Autowired
    private AffairExamEnteringService affairExamEnteringService;

    @Autowired
    private UploadController uploadController;

    static String num;

    static String idnum;

    @ModelAttribute
    public AffairXunLi get(@RequestParam(required = false) String id) {
        AffairXunLi entity = null;
        if (StringUtils.isNotBlank(id)) {
        }
        if (entity == null) {
            entity = new AffairXunLi();
        }
        return entity;
    }

    @RequiresPermissions("affair:affairSendTeacher:view")
    @RequestMapping(value = {""})
    public String index(Office office, Model model) {
        return "modules/affair/affairXunLiIndex";
    }

    @RequiresPermissions("affair:affairXunLi:view")
    @RequestMapping(value = {"list"})
    public String list(AffairXunLi affairXunLi, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AffairXunLi> page = affairXunLiService.findPage(new Page<AffairXunLi>(request, response), affairXunLi);
        String idNumber = null;
        List<AffairXunLi> list = page.getList();
        for (int i = 0; i < list.size(); i++) {
            idNumber = list.get(i).getIdNumber();
        }
        model.addAttribute("page", page);
        model.addAttribute("idNumbder", idNumber);
        model.addAttribute("organization",affairXunLi.getOrganization());
        model.addAttribute("organizationId",affairXunLi.getOrganizationId());
        return "modules/affair/affairXunliList";

    }

    /**
     * 查看详情
     *
     * @param
     * @param model
     * @return
     */
    @RequiresPermissions("affair:affairXunLi:view")
    @RequestMapping(value = "formDetail")
    public String formDetail(Model model, @RequestParam("idNumber") String idNumber) {
        AffairXunLi xunLiPersonnel = affairXunLiService.findPersonnel(idNumber);
        //AffairXunLi xunLiXueWei = affairXunLiService.findXueWei(idNumber);
        /*AffairXunLi xunLiJob = affairXunLiService.findJob(idNumber);
        AffairXunLi xunLiMes = affairXunLiService.findMes(idNumber);
        AffairXunLi xunLiTra = affairXunLiService.findTra(idNumber);
        List<AffairXunLi> xunLiXueLi = affairXunLiService.findXueLi(idNumber);*/
		/*for (AffairXunLi affairXunLi : xunLiXueLi) {
			String schoolName = affairXunLi.getSchoolName();
			model.addAttribute("schoolName", schoolName);

		}
*/
        model.addAttribute("xunLiPersonnel", xunLiPersonnel);
        //model.addAttribute("xunLiXueWei", xunLiXueWei);
        /*model.addAttribute("xunLiJob", xunLiJob);
        model.addAttribute("xunLiMes", xunLiMes);
        model.addAttribute("xunLiTra", xunLiTra);
        model.addAttribute("xunLiXueLi", xunLiXueLi);*/
        return "modules/affair/affairXunLiFormDetail";
    }

    /**
     * 查看训厉档案在线课程页面展示
     *
     * @param model
     * @return
     */
    @RequiresPermissions("affair:affairXunLi:view")
    @RequestMapping(value = "onlineCourse")
    public String listOne(Model model, @RequestParam("idNumber") String idNumber) {
		/*AffairXunLiDetails page = affairXunLiService.findXunLiOnlineCourse(idNumber);
		model.addAttribute("page", page);*/
        model.addAttribute("idNumber", idNumber);
        return "modules/affair/affairXunLiOnlineCourseList";
    }

    /**
     * 查看训厉档案培训班课程页面展示
     *
     * @param model
     * @return
     */
    @RequiresPermissions("affair:affairXunLi:view")
    @RequestMapping(value = "trainingCourses")
    public String listFour(Model model, @RequestParam("idNumber") String idNumber) {
        List<AffairXunLiDetails> page = affairXunLiService.findXunLiTrainin(idNumber);
        model.addAttribute("page", page);
        model.addAttribute("idNumber", idNumber);
        return "modules/affair/affairXunLiTraininCoursesList";
    }


    /**
     * 考试
     *
     * @param model
     * @return
     */
    @RequiresPermissions("affair:affairXunLi:view")
    @RequestMapping(value = "examEntering")
    public String listExamination(Model model, @RequestParam("idNumber") String idNumber, AffairXunLi affairXunLi) {
        List<AffairXunLi> page = affairXunLiService.findXunLiExaminationTwo(idNumber);
        for (int i = 0; i < page.size(); i++) {
            affairXunLi = page.get(i);
        }
        model.addAttribute("affairXunLi", affairXunLi);
        idnum = idNumber;
        model.addAttribute("idNumber", idNumber);
        model.addAttribute("page", page);
        return "modules/affair/affairXunLiExamEnteringList";
    }


    /**
     * 查看训厉档案委外培训页面展示
     *
     * @return
     */
    @RequiresPermissions("affair:affairXunLi:view")
    @RequestMapping(value = "record")
    public String list(Model model, @RequestParam("idNumber") String idNumber) {
        List<AffairXunLiDetails> page = affairXunLiService.findXunLiTrainOutSource(idNumber);
        model.addAttribute("idNumber", idNumber);
        model.addAttribute("page", page);
        return "modules/affair/affairXunLiTrainOutsourceList";
    }

    /**
     * 查看训厉档案交流学习页面展示
     *
     * @param model
     * @return
     */
    @RequiresPermissions("affair:affairXunLi:view")
    @RequestMapping(value = "exchangeLearning")
    public String listTwo(Model model, @RequestParam("idNumber") String idNumber) {
        AffairXunLiDetails page = affairXunLiService.findXunLiExchangeLearning(idNumber);
        model.addAttribute("page", page);
        model.addAttribute("idNumber", idNumber);

        return "modules/affair/affairXunLiExchangeLearningList";
    }

    /**
     * 查看训厉档案岗位练兵页面展示  onlineCourse
     *
     * @param model
     * @return
     */
    @RequiresPermissions("affair:affairXunLi:view")
    @RequestMapping(value = "job")
    public String listThree(Model model, @RequestParam("idNumber") String idNumber) throws ParseException {
        List<AffairXunLiDetails> page = affairXunLiService.findXunLiJob(idNumber);

        model.addAttribute("page", page);

        num = idNumber;
        model.addAttribute("idNumber", idNumber);
        return "modules/affair/affairXunLiJobList";
    }

    /**
     * 导出excel格式数据
     *
     * @param affairXunLiDetails
     * @param request
     * @param response
     * @param redirectAttributes
     * @param flag
     * @return
     */
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportExcelByTemplate(AffairXunLiDetails affairXunLiDetails, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model, boolean flag, @RequestParam("idNumber") String idNumber) {
        try {
            String idNumber1 = request.getParameter("idNumber");
            String name = request.getParameter("name");
            Map<String, String> map = new HashMap<String,String>();

            LinkedHashMap<Integer, LinkedList<String>> outsrows = new LinkedHashMap<>();
            LinkedHashMap<Integer, LinkedList<String>> jobsrows = new LinkedHashMap<>();
            LinkedHashMap<Integer, LinkedList<String>> acmsrows = new LinkedHashMap<>();

            PersonnelBase person = affairXunLiService.findPerson(idNumber1);
            map.put("name",person.getName());
            String sex = DictUtils.getDictLabel(person.getSex(), "sex", "");
            map.put("sex", sex);
            String nation = DictUtils.getDictLabel(person.getNation(), "nation", "");
            map.put("nation", nation);
            String publicSecurity = DateUtils.formatDate(person.getPublicSecurityDate(), "yyyy-MM-dd");
            map.put("public_security_date",publicSecurity);
            String politicsFace = DictUtils.getDictLabel(person.getPoliticsFace(), "political_status", "");
            map.put("politics_face", politicsFace);
            String birthdayStr = DateUtils.formatDate(person.getBirthday(), "yyyy-MM-dd");
            map.put("birthday", birthdayStr);
            map.put("phone_number",person.getPhoneNumber());
            map.put("job_abbreviation",person.getPhoneNumber());

            List<Map<String,String>> acms = affairXunLiService.findAcms(idNumber1);
            if(acms != null && acms.size() > 0) {
                for (int k = 0; k < acms.size(); k++) {
                    LinkedList<String> row = new LinkedList<>();
                    map.put("peixunname", acms.get(k).get("peiXunName"));
                    String type = DictUtils.getDictLabel(acms.get(k).get("type"), "affair_train_type", "");
                    map.put("type", type);
                    map.put("content", acms.get(k).get("content"));
                    map.put("site", acms.get(k).get("site"));
                    map.put("train_object", acms.get(k).get("train_object"));
                    String peiXunName = acms.get(k).get("peiXunName")==null?"":acms.get(k).get("peiXunName");
                    String content = acms.get(k).get("content")==null?"":acms.get(k).get("content");
                    String site = acms.get(k).get("site")==null?"":acms.get(k).get("site");
                    String trainObject = acms.get(k).get("train_object")==null?"":acms.get(k).get("train_object");
                    row.add(peiXunName);
                    row.add(type==null?"":type);
                    row.add(content);
                    row.add(site);
                    row.add(trainObject);
                    acmsrows.put(k,row);

                }
            }else{
                map.put("peixunname", "");
                map.put("type", "");
                map.put("content", "");
                map.put("site", "");
                map.put("train_object","");
            }

            List<Map<String,String>> outs = affairXunLiService.findOuts(idNumber1);
            if(outs != null && outs.size() >0) {
                LinkedList<String> row = new LinkedList<>();
                for (int i = 0; i < outs.size(); i++) {
                    map.put("external_name",outs.get(i).get("external_name"));
                    String externalType = outs.get(i).get("external_type")==null?"":DictUtils.getDictLabel(outs.get(i).get("external_type"), "external_type", "");
                    map.put("external_type", externalType);
                    map.put("unit", outs.get(i).get("unit"));
                    map.put("unit_name", outs.get(i).get("unit_name"));
                    map.put("unit_job", outs.get(i).get("unit_job"));
                    String externalName = outs.get(i).get("external_name")==null?"":outs.get(i).get("external_name");
                    String unit = outs.get(i).get("unit")==null?"":outs.get(i).get("unit");
                    String unitName = outs.get(i).get("unit_name")==null?"":outs.get(i).get("unit_name");
                    String unitJob = outs.get(i).get("unit_job")==null?"":outs.get(i).get("unit_job");
                    row.add(externalName);
                    row.add(externalType);
                    row.add(unit);
                    row.add(unitName);
                    row.add(unitJob);
                    outsrows.put(i, row);

                }
            }else {
                map.put("external_name","");
                map.put("external_type", "");
                map.put("unit","");
                map.put("unit_name", "");
                map.put("unit_job", "");
            }

            List<Map<String,String>> jobs = affairXunLiService.findJobs(idNumber1);
            if(jobs != null && jobs.size() > 0) {
                LinkedList<String> row = new LinkedList<>();
                for (int j = 0; j < jobs.size(); j++) {
                    String itemClassification = jobs.get(j).get("item_classification")==null?"":DictUtils.getDictLabel(jobs.get(j).get("item_classification"), "project_type", "");
                    Map<String, String> map1 = jobs.get(j);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:S");
                    String drill_date_begin = format.format(map1.get("drill_date_begin"));
                    String drill_date_over = format.format(map1.get("drill_date_over"));

                    String drillGeneralSituation =jobs.get(j).get("drill_general_situation")==null ? "":jobs.get(j).get("drill_general_situation");
                    String xunLianTime = jobs.get(j).get("xunlian_time")==null?"":jobs.get(j).get("xunlian_time");

                    map.put("item_classification", itemClassification);
                    map.put("drill_general_situation", jobs.get(j).get("drill_general_situation"));
                    map.put("xunlian_time", jobs.get(j).get("xunlian_time"));

                    row.add(drill_date_begin);
                    row.add(drill_date_over);
                    row.add(itemClassification);
                    row.add(drillGeneralSituation);
                    row.add(xunLianTime);
                    jobsrows.put(j,row);


                }
            }else{
                map.put("item_classification", "");
                map.put("drill_general_situation", "");
                map.put("xunlian_time", "");
            }


            //获取模板
            String fileSeperator = File.separator;
            String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;
            String fileName = request.getParameter("fileName");
            //初始化工具
            ExcelTemplate excel = new ExcelTemplate(filePath + fileName);
            try {
                //写入模板
                excel.fillVariable(0, map);
                /*int a = 7;
                int b = 8;
                int i = excel.addRowByExist(0, a, a, b, acmsrows, true);
                int outsRowsIndex = 7 + i + 2;
                int outsToRowsIndex = 7 + i + 3;
                int outsum = excel.addRowByExist(0, outsRowsIndex, outsRowsIndex, outsToRowsIndex, outsrows, true);
                int jobsRowsIndex = outsRowsIndex + outsum + 2;
                int jobsToRowsIndex = outsRowsIndex + outsum + 3;
                excel.addRowByExist(0, jobsRowsIndex, jobsRowsIndex, jobsToRowsIndex, jobsrows, true);*/

                excel.addRowByExist(0, 15, 15, 16, jobsrows, true);

                excel.addRowByExist(0, 11, 11, 12, outsrows, true);

                excel.addRowByExist(0, 7, 7, 8, acmsrows, true);

            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.toString());
                addMessage(redirectAttributes, "联系系统管理员导入模板");
                return "redirect:" + adminPath + "/affair/affairXunLi";
            }
            //下载
            try {
                // 清除buffer缓存
                response.reset();
                response.setContentType("application/octet-stream; charset=utf-8");
                //指定下载名字
                response.setHeader("Content-Disposition", "attachment; filename=" +
                        Encodes.urlEncode(name + "个人训厉档案" +fileName.replace("导出", "")));
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

            return "redirect:" + adminPath + "/affair/affairXunLi";


        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            addMessage(redirectAttributes, "发生错误，导出失败，请重试或联系统管理员");
            return "redirect:" + adminPath + "/affair/affairXunLi";
        }
    }


    /**
     * 考试导出excel格式数据
     *
     * @param
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportTwo", method = RequestMethod.POST)
    public String exportExcelByTemplate(AffairExamEntering affairExamEntering, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag, @RequestParam("idNumber") String idNumber) {
        XSSFWorkbook wb = null;
        try {
            affairExamEntering.setCreateBy(UserUtils.getUser());
            String fileName = "";
            if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
                fileName = request.getParameter("fileName").toString();
            }
            List<AffairXunLi> xunLiExamination = affairXunLiService.findXunLiExamination(idNumber);

            String fileSeperator = File.separator;
            String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;
            InputStream inputStream = new FileInputStream(filePath + fileName);
            if (null != inputStream) {
                try {
                    wb = new XSSFWorkbook(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairXunLi.class);
            exportExcelNew.setWb(wb);
            exportExcelNew.setDataList(xunLiExamination);
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
            addMessage(redirectAttributes, "导出用户失败！失败信息：" + ex);
        }
        return "redirect:" + adminPath + "/affair/affairXunLi?repage";
    }

    @RequiresPermissions("affair:affairXunLi:edit")
    @RequestMapping(value = "insertOne")
    public String insertOne(@RequestParam("appendfile") String appendfile, @RequestParam("idNumber") String idNumber, Model model) {
        affairXunLiService.insertOne(appendfile, idNumber);
        model.addAttribute("idNumber", idNumber);
        //return "modules/affair/affairXunLiExamEnteringList";
        //return "redirect:"+Global.getAdminPath()+"/affair/affairClassManage/idList?classId="+classId;   ?idNumber="+idNumber
        return "redirect:" + Global.getAdminPath() + "/affair/affairXunLi/examEntering";
    }


    /**
     * 考试导入
     *
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 0, 0);
            List<AffairXunLi> list = ei.getDataList(AffairXunLi.class);
            for (AffairXunLi affairXunLi : list) {
                try {

                    BeanValidators.validateWithException(validator, affairXunLi);
                    affairXunLi.setIdNumber(idnum);
                    String uuid = IdGen.uuid();
                    affairXunLi.setId(uuid);
                    affairXunLiService.insertTwo(affairXunLi);
                    successNum++;
                }/**/ catch (ConstraintViolationException ex) {
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                    for (String message : messageList) {
                        failureMsg.append(message + "; ");
                        failureNum++;
                    }
                } catch (Exception ex) {
                    failureMsg.append(" 导入失败：" + ex.getMessage());
                }
            }

            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入失败！失败信息：" + e.getMessage());
        }
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:" + adminPath + "/file/template/download/view";
    }

}