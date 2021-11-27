/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.common.utils.excel.ExcelTemplate;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairPolicePersonalTrainingFile;
import com.thinkgem.jeesite.modules.affair.service.AffairPolicePersonalTrainingFileService;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 民警个人训历档案报表Controller
 *
 * @author kevin.jia
 * @version 2020-09-28
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPolicePersonalTrainingFile")
public class AffairPolicePersonalTrainingFileController extends BaseController {

    @Autowired
    private AffairPolicePersonalTrainingFileService affairPolicePersonalTrainingFileService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private PersonnelBaseService personnelBaseService;
	
	/*@ModelAttribute
	public AffairPolicePersonalTrainingFile get(@RequestParam(required=false) String id) {
		AffairPolicePersonalTrainingFile entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPolicePersonalTrainingFileService.get(id);
		}
		if (entity == null){
			entity = new AffairPolicePersonalTrainingFile();
		}
		return entity;
	}*/

    @ModelAttribute
    public PersonnelBase get(@RequestParam(required = false) String id) {
        PersonnelBase entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = personnelBaseService.get(id);
        }
        if (entity == null) {
            entity = new PersonnelBase();
        }
        return entity;
    }

    @RequiresPermissions("affair:affairPolicePersonalTrainingFile:view")
    @RequestMapping(value = {""})
    public String index(Office office, Model model) {
        return "modules/affair/affairPolicePersonalTrainingFileIndex";
    }

    @RequiresPermissions("affair:affairPolicePersonalTrainingFile:view")
    @RequestMapping(value = {"list"})
    public String list(PersonnelBase personnelBase, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<PersonnelBase> page = affairPolicePersonalTrainingFileService.findPersonBasePage(new Page<PersonnelBase>(request, response), personnelBase);
        //Page<AffairPolicePersonalTrainingFile> page = affairPolicePersonalTrainingFileService.findPage(new Page<AffairPolicePersonalTrainingFile>(request, response), affairPolicePersonalTrainingFile);
        model.addAttribute("page", page);
        model.addAttribute("unitId", personnelBase.getWorkunitId());
        model.addAttribute("unitName", personnelBase.getWorkunitName());
        return "modules/affair/affairPolicePersonalTrainingFileList";
    }

    @RequiresPermissions("affair:affairPolicePersonalTrainingFile:view")
    @RequestMapping(value = "form")
    public String form(AffairPolicePersonalTrainingFile affairPolicePersonalTrainingFile, Model model) {
        model.addAttribute("affairPolicePersonalTrainingFile", affairPolicePersonalTrainingFile);
        return "modules/affair/affairPolicePersonalTrainingFileForm";
    }
    @RequiresPermissions("affair:affairPolicePersonalTrainingFile:view")
    @RequestMapping(value = "formDetail")
    public String formDetail(String idNumber, Model model) {
        try {
            AffairPolicePersonalTrainingFile personalTrainingFileInfo = affairPolicePersonalTrainingFileService.findPersonnaInfoByIdNumber(idNumber);
            if(personalTrainingFileInfo!=null){
                String expertOpinion = personalTrainingFileInfo.getExpertOpinion();//处理鉴定意见空格/换行
                expertOpinion = expertOpinion.replace("\n","&#13;");
                expertOpinion = expertOpinion.replace("\r","&#10;");
                personalTrainingFileInfo.setExpertOpinion(expertOpinion);
            }
            model.addAttribute("affairPolicePersonalTrainingFile", personalTrainingFileInfo);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            model.addAttribute("message","发生错误，错误信息："+e.getCause());
        }
        return "modules/affair/affairPolicePersonalTrainingFileFormDetail";
    }

    @RequiresPermissions("affair:affairPolicePersonalTrainingFile:edit")
    @RequestMapping(value = "save")
    public String save(AffairPolicePersonalTrainingFile affairPolicePersonalTrainingFile, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, affairPolicePersonalTrainingFile)) {
            return form(affairPolicePersonalTrainingFile, model);
        }
        affairPolicePersonalTrainingFileService.save(affairPolicePersonalTrainingFile);
        addMessage(redirectAttributes, "保存民警个人训历档案报表成功");
        return "redirect:" + Global.getAdminPath() + "/affair/affairPolicePersonalTrainingFile/?repage";
    }

    @RequiresPermissions("affair:affairPolicePersonalTrainingFile:edit")
    @RequestMapping(value = "delete")
    public String delete(AffairPolicePersonalTrainingFile affairPolicePersonalTrainingFile, RedirectAttributes redirectAttributes) {
        affairPolicePersonalTrainingFileService.delete(affairPolicePersonalTrainingFile);
        addMessage(redirectAttributes, "删除民警个人训历档案报表成功");
        return "redirect:" + Global.getAdminPath() + "/affair/affairPolicePersonalTrainingFile/?repage";
    }

    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes, HttpServletResponse response, HttpServletRequest request) {
        String idNumber = request.getParameter("idNumber");
        AffairPolicePersonalTrainingFile affairPolicePersonalTrainingFile = new AffairPolicePersonalTrainingFile();
        affairPolicePersonalTrainingFile.setIdNumber(idNumber);
        PersonnelBase infoByIdNumber = personnelBaseService.findInfoByIdNumber(idNumber);
        String personnelName = infoByIdNumber.getName();
        try {
            StringBuilder failureMsg = new StringBuilder();
            /*读取文件*/
            ImportExcel ei = new ImportExcel(file, 1, 0);
            int lastRow = ei.getLastDataRowNum();//最后行号
            int lastCellNum = ei.getLastCellNum();//最后列号
            /*遍历导入模板的每一行数据*/
            for (int i = 0; i < lastRow; i++) {
                Row row = ei.getRow(i);
                if (i == 1) {//对应表中第二行
                    Object nameValue = ei.getCellValue(row, 1);
                    Object sexValue = ei.getCellValue(row, 3);
                    if (StringUtils.isNotBlank(personnelName) && !personnelName.equals(nameValue.toString())) {
                        throw new RuntimeException("请确认导入的训历档案信息是否为" + personnelName + "的信息");
                    }
                    if (nameValue.toString().isEmpty()) {
                        throw new RuntimeException("导入文件中姓名不能为空");
                    }
                    affairPolicePersonalTrainingFile.setName(nameValue.toString());
                    affairPolicePersonalTrainingFile.setSex(sexValue.toString());
                }
                if (i == 2) {//对应表中第三行
                    Object unitValue = ei.getCellValue(row, 1);
                    Object jobValue = ei.getCellValue(row, 3);
                    affairPolicePersonalTrainingFile.setUnit(unitValue.toString());
                    String unitId = officeService.findByName(unitValue.toString());
                    affairPolicePersonalTrainingFile.setUnitId(unitId);
                    affairPolicePersonalTrainingFile.setJob(jobValue.toString());
                }
                if (i == 3) {//对应表中第四行
                    Object birthdayValue = ei.getCellValue(row, 1);
                    Object hiredateValue = ei.getCellValue(row, 3);
                    //通过try  catch确定excel表格中该值类型，
                    try {
                        //日期型
                        affairPolicePersonalTrainingFile.setBirthday(DateUtil.getJavaDate((Double) birthdayValue));
                    }catch (Exception e){
                        //文本型
                        affairPolicePersonalTrainingFile.setBirthday(DateUtils.parseDate(birthdayValue.toString()));
                    }
                    try {
                        //日期型
                        affairPolicePersonalTrainingFile.setHiredate(DateUtil.getJavaDate((Double) hiredateValue));
                    }catch (Exception e){
                        //文本型
                        affairPolicePersonalTrainingFile.setHiredate(DateUtils.parseDate(hiredateValue.toString()));
                    }
                }
                if (i == 4) {//对应表中第五行
                    Object educationValue = ei.getCellValue(row, 1);
                    Object politicsFaceValue = ei.getCellValue(row, 3);
                    affairPolicePersonalTrainingFile.setEducation(educationValue.toString());//学历
                    affairPolicePersonalTrainingFile.setPoliticsFace(politicsFaceValue.toString());//政治面貌
                }
                if (i == 5) {//对应表中第六行
                    Object technicalTitleValue = ei.getCellValue(row, 1);
                    Object phoneValue = ei.getCellValue(row, 3);
                    affairPolicePersonalTrainingFile.setTechnicalTitle(technicalTitleValue.toString());//专业技术职称
                    affairPolicePersonalTrainingFile.setPhone(phoneValue.toString());//联系电话
                }
                if (i == 8) {//对应表中第9行,在线课程字段值
                    String onlineCourseNumValue = ei.getCellValue(row, 0).toString();//在线课程-参加次数
                    String onlineCoursePassingValue = ei.getCellValue(row, 1).toString();//在线课程-通过率
                    String onlineCourseAverageValue = ei.getCellValue(row, 2).toString();//在线课程-平均分
                    String onlineCourseTotalTimeValue = ei.getCellValue(row, 3).toString();//在线课程-总课时(分钟)
                    String onlineCourseCreditValue = ei.getCellValue(row, 4).toString();//在线课程-学分
                    affairPolicePersonalTrainingFile.setOnlineCourseNum(onlineCourseNumValue);
                    affairPolicePersonalTrainingFile.setOnlineCoursePassing(onlineCoursePassingValue);
                    affairPolicePersonalTrainingFile.setOnlineCourseAverage(onlineCourseAverageValue);
                    affairPolicePersonalTrainingFile.setOnlineCourseTotalTime(onlineCourseTotalTimeValue);
                    affairPolicePersonalTrainingFile.setOnlineCourseCredit(onlineCourseCreditValue);
                }
                if (i == 11) {//对应表中第12行,培训班课程字段值
                    String trainCourseNum = ei.getCellValue(row, 0).toString();//培训班课程-参加次数
                    String trainCoursePassing = ei.getCellValue(row, 1).toString();//培训班课程-通过率
                    String trainCourseAverage = ei.getCellValue(row, 2).toString();//培训班课程-平均分
                    String trainCourseTotalTime = ei.getCellValue(row, 3).toString();//培训班课程-总课时(分钟)
                    String trainCourseCredit = ei.getCellValue(row, 4).toString();//培训班课程-学分
                    affairPolicePersonalTrainingFile.setTrainCourseNum(trainCourseNum);
                    affairPolicePersonalTrainingFile.setTrainCoursePassing(trainCoursePassing);
                    affairPolicePersonalTrainingFile.setTrainCourseAverage(trainCourseAverage);
                    affairPolicePersonalTrainingFile.setTrainCourseTotalTime(trainCourseTotalTime);
                    affairPolicePersonalTrainingFile.setTrainCourseCredit(trainCourseCredit);
                }
                if (i == 14) {//对应表中第15行,岗位练兵字段值
                    String jobTrainingNum = ei.getCellValue(row, 0).toString();//岗位练兵-参加次数
                    String jobTrainingTotalTime = ei.getCellValue(row, 1).toString();//总时长(小时)
                    affairPolicePersonalTrainingFile.setJobTrainingNum(jobTrainingNum);
                    affairPolicePersonalTrainingFile.setJobTrainingTotalTime(jobTrainingTotalTime);

                }
                if (i == 17) {//对应表中第18行,委外培训字段值
                    String outTrainingNum = ei.getCellValue(row, 0).toString();//委外培训-参加次数
                    String outTrainingFinish = ei.getCellValue(row, 1).toString();//委外培训-完成率
                    String outTrainingMainType = ei.getCellValue(row, 2).toString();//主要类别
                    String outTrainingHighestLevel = ei.getCellValue(row, 3).toString();//最高级别
                    String outTrainingGetCcie = ei.getCellValue(row, 4).toString();//取得证书
                    affairPolicePersonalTrainingFile.setOutTrainingNum(outTrainingNum);
                    affairPolicePersonalTrainingFile.setOutTrainingFinish(outTrainingFinish);
                    affairPolicePersonalTrainingFile.setOutTrainingMainType(outTrainingMainType);
                    affairPolicePersonalTrainingFile.setOutTrainingHighestLevel(outTrainingHighestLevel);
                    affairPolicePersonalTrainingFile.setOutTrainingGetCcie(outTrainingGetCcie);
                }
                if (i == 20) {//对应表中第21行,交流学习字段值
                    String exchangeStudyNum = ei.getCellValue(row, 0).toString();//交流学习-参加次数
                    String exchangeStudyFinish = ei.getCellValue(row, 1).toString();//交流学习-完成率
                    String exchangeStudyMainJob = ei.getCellValue(row, 2).toString();//主要岗位
                    String exchangeStudyHighestSpec = ei.getCellValue(row, 3).toString();//最高规格
                    String exchangeStudyTotalTime = ei.getCellValue(row, 4).toString();//交流学习-总时长
                    affairPolicePersonalTrainingFile.setExchangeStudyNum(exchangeStudyNum);
                    affairPolicePersonalTrainingFile.setExchangeStudyFinish(exchangeStudyFinish);
                    affairPolicePersonalTrainingFile.setExchangeStudyMainJob(exchangeStudyMainJob);
                    affairPolicePersonalTrainingFile.setExchangeStudyHighestSpec(exchangeStudyHighestSpec);
                    affairPolicePersonalTrainingFile.setExchangeStudyTotalTime(exchangeStudyTotalTime);
                }
                if (i == 23) {//对应表中第24行,考试字段值
                    String examNum = ei.getCellValue(row, 0).toString();//考试-参加次数
                    String examPass = ei.getCellValue(row, 1).toString();//考试-合格率
                    String examAverage = ei.getCellValue(row, 2).toString();//考试-平均分
                    String examCheat = ei.getCellValue(row, 3).toString();//考试-作弊次数
                    affairPolicePersonalTrainingFile.setExamNum(examNum);
                    affairPolicePersonalTrainingFile.setExamPass(examPass);
                    affairPolicePersonalTrainingFile.setExamAverage(examAverage);
                    affairPolicePersonalTrainingFile.setExamCheat(examCheat);
                }
                if (i == 25) {//对应表中第25行,教育训练积分字段值
                    String trainingIntegral = ei.getCellValue(row, 0).toString();//教育训练积分
                    affairPolicePersonalTrainingFile.setTrainingIntegral(trainingIntegral);
                }
                if (i == 26) {//对应表中第27行,教育训练主管部门鉴定意见字段值
                    String expertOpinion = ei.getCellValue(row, 0).toString();//鉴定意见
                    affairPolicePersonalTrainingFile.setExpertOpinion(expertOpinion);
                }

            }
            int count = affairPolicePersonalTrainingFileService.selByIdNumber(idNumber);//查询数据库该人员的训历档案是否存在
            if (count > 0) {
                affairPolicePersonalTrainingFileService.deleteByIdNumber(idNumber);
            }
            User user = UserUtils.getUser();
            affairPolicePersonalTrainingFile.setCreateOrgId(user.getOffice().getId());
            affairPolicePersonalTrainingFile.setUpdateOrgId(user.getOffice().getId());
            affairPolicePersonalTrainingFileService.save(affairPolicePersonalTrainingFile);
            addMessage(redirectAttributes, affairPolicePersonalTrainingFile.getName() + "的个人训历档案已成功导入");
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/file/template/trainingFiledownload/view?id=affair_policePersonalTrainingFile&idNumber=" + idNumber;
    }


	/**
	 * 导出民警个人训历档案
	 * @param request
	 */
	@RequestMapping(value = "export")
	public String exportPoliceTrainingFile(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) {
        String idNumber = request.getParameter("idNumber");
	    PersonnelBase personnelBase = personnelBaseService.findInfoByIdNumber(idNumber);
	    try {
	        AffairPolicePersonalTrainingFile affairPolicePersonalTrainingFile = affairPolicePersonalTrainingFileService.findPersonnaInfoByIdNumber(idNumber);
            Map<String,String> map = affairPolicePersonalTrainingFileService.findPersonnaMapByIdNumber(idNumber);
            if(map==null || map.isEmpty() || affairPolicePersonalTrainingFile==null){
                addMessage(redirectAttributes,"导出失败，未从系统中找到"+personnelBase.getName()+"的个人训历档案。");
                redirectAttributes.addAttribute("workunitId",personnelBase.getWorkunitId());
                redirectAttributes.addAttribute("workunitName",personnelBase.getWorkunitName());
                redirectAttributes.addFlashAttribute("type","error");
                return "redirect:" + adminPath + "/affair/affairPolicePersonalTrainingFile/list";
            }
            Date birthday =affairPolicePersonalTrainingFile.getBirthday();//出生年月
            Date hiredate =affairPolicePersonalTrainingFile.getHiredate();//入警时间
            String birthdayStr = DateUtils.formatDate(birthday,"yyyy-MM-dd");
            String hiredateStr = DateUtils.formatDate(hiredate,"yyyy-MM-dd");
            map.put("birthday",birthdayStr);
            map.put("hiredate",hiredateStr);
            //获取模板
            String fileSeperator = File.separator;
            String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;
            String fileName=request.getParameter("fileName");
            //初始化工具
            ExcelTemplate excel = new ExcelTemplate(filePath + fileName);
            try {
                //写入模板
                int count = excel.fillVariable(0, map);
            } catch (IOException e) {
                e.printStackTrace();
                e.printStackTrace();
                logger.error(e.toString());
                addMessage(redirectAttributes,"联系系统管理员导入模板");
                redirectAttributes.addAttribute("workunitId",personnelBase.getWorkunitId());
                redirectAttributes.addAttribute("workunitName",personnelBase.getWorkunitName());
                return "redirect:" + adminPath + "/affair/affairPolicePersonalTrainingFile/list";
            }
            //下载
            try {
                // 清除buffer缓存
                response.reset();
                response.setContentType("application/octet-stream; charset=utf-8");
                //指定下载名字
                response.setHeader("Content-Disposition", "attachment; filename="+
                        Encodes.urlEncode(map.get("name")+fileName.replace("导出","")));
                response.addHeader("Pargam", "no-cache");
                response.addHeader("Cache-Control", "no-cache");
                OutputStream fout = response.getOutputStream();
                excel.getWorkbook().write(fout);
                fout.close();

            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.toString());
                addMessage(redirectAttributes,e.getMessage());
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString());
            addMessage(redirectAttributes,"发生错误，导出失败，请重试或联系统管理员");
            redirectAttributes.addAttribute("workunitId",personnelBase.getWorkunitId());
            redirectAttributes.addAttribute("workunitName",personnelBase.getWorkunitName());
            return "redirect:" + adminPath + "/affair/affairPolicePersonalTrainingFile/list";
	    }
	}


}