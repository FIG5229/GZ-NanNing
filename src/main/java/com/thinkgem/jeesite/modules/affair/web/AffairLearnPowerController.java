/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairLearnDaily;
import com.thinkgem.jeesite.modules.affair.entity.AffairLearnPower;
import com.thinkgem.jeesite.modules.affair.entity.AffairLearnPowerYear;
import com.thinkgem.jeesite.modules.affair.service.AffairLearnPowerService;
import com.thinkgem.jeesite.modules.affair.service.AffairLearnPowerYearService;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
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
import org.yaml.snakeyaml.Yaml;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 学习强国Controller
 *
 * @author Alan
 * @version 2020-06-04
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairLearnPower")
public class AffairLearnPowerController extends BaseController {

    @Autowired
    private AffairLearnPowerService affairLearnPowerService;

    @Autowired
    private AffairLearnPowerYearService affairLearnPowerYearService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private UploadController uploadController;

    @Autowired
    private PersonnelBaseService personnelBaseService;

    @Autowired
    IdGen idGen;

    @ModelAttribute
    public AffairLearnPower get(@RequestParam(required = false) String id) {
        AffairLearnPower entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = affairLearnPowerService.get(id);
        }
        if (entity == null) {
            entity = new AffairLearnPower();
        }
        return entity;
    }

    @RequiresPermissions("affair:affairLearnPower:view")
    @RequestMapping(value = {"list", ""})
    public String list(AffairLearnPower affairLearnPower, HttpServletRequest request, HttpServletResponse response, Model model) {

        String time = affairLearnPower.getTime();
        if (StringUtils.isBlank(time)) {

            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");

            //当前时间
            String dateString1 = formatter.format(currentTime);
            affairLearnPower.setTime(dateString1);
            List<AffairLearnPower> list = affairLearnPowerService.selectAllBean(affairLearnPower);


            for (int i = 0; i < list.size(); i++) {

                AffairLearnPower affairLearnPower1 = list.get(i);

                //身份证号
                String idNumber = affairLearnPower1.getIdNumber();

                //本月学习积分
                Double thisMonthIntegral = 0.0;
                if(affairLearnPower1.getThisMonthIntegral()!=null){
                    thisMonthIntegral = affairLearnPower1.getThisMonthIntegral();
                }
                //年份
                String year = dateString1.substring(0, 4);
                //月份
                String strh = dateString1.substring(dateString1.length() - 2, dateString1.length());

                int mon = Integer.valueOf(strh);
                int lastMo = mon - 1;

                //上月月份
                String lastMonth = String.valueOf(lastMo);

                if (lastMo < 10 && mon > 0) {
                    //上月时间
                    String lastTime = year + "-0" + lastMonth;
                    Double sum = 0.0;
                    //上月分数
                    Double lastScore = affairLearnPowerService.selectLastScore(idNumber, lastTime);
                    if (null != lastScore) {
                        affairLearnPower1.setLastMonthIntegral(lastScore);
                        sum = thisMonthIntegral + lastScore;
                    } else {
                        affairLearnPower1.setLastMonthIntegral(0.0);
                        sum = thisMonthIntegral + 0.0;
                    }
                    affairLearnPower1.setThisMonthStatistics(sum);

                    Double y = affairLearnPowerService.selectSumYearById(year, idNumber,affairLearnPower1.getUnitId());
                    /*Double yearScore;
                    try {
                        yearScore = Double.valueOf(y);
                    }catch (Exception e){
                        e.printStackTrace();
                        logger.error("学习强国：affairLearnPowerService.selectSumYearById(year, idNumber); 类型转换错误");
                        yearScore = 0.0;
                    }*/
                    affairLearnPower1.setThisYearStatistics(y);

                }
                else if (lastMo > 9){
                    //上个月时间
                    String lastTime = year + "-" + lastMonth;

                    //上月分数
                    Double lastScore = affairLearnPowerService.selectLastScore(idNumber, lastTime);

                    //本月学习分数
                    Double score = 0.0;
                    if(affairLearnPower1.getThisMonthIntegral()!=null){
                        score = affairLearnPower1.getThisMonthIntegral();
                    }
                    if (null != lastScore) {
                        affairLearnPower1.setLastMonthIntegral(lastScore);
                        //上月及本月分数总和
                        Double sum = lastScore + score;
                        affairLearnPower1.setThisMonthStatistics(sum);
                    } else {
                        affairLearnPower1.setThisMonthStatistics(score);
                    }
                    //一年分数总和
                    Double y = affairLearnPowerService.selectSumYearById(year, idNumber,affairLearnPower1.getUnitId());
                    // 11.7 为空处理 kevin.jia
                    /*Double yearScore ;
                    if(StringUtils.isNotBlank(y)){
                        yearScore = Double.valueOf(y);
                    }else{
                        yearScore = 0.0;
                    }*/
                    affairLearnPower1.setThisYearStatistics(y);

                }
            }

            model.addAttribute("list", list);
            boolean isPerson = false;
            PersonnelBase personnel = personnelBaseService.findInfoByIdNumber(UserUtils.getUser().getNo());
            if(personnel!=null){
                isPerson = true;
            }
            model.addAttribute("isPerson", isPerson);
            return "modules/affair/affairLearnPowerList";

        }
        else if (StringUtils.isNotBlank(time)) {

             List<AffairLearnPower> list = affairLearnPowerService.selectAllBean(affairLearnPower);
            for (int i = 0; i <list.size(); i++) {

                AffairLearnPower affairLearnPower1 = list.get(i);

                //身份证号
                String idNumber = affairLearnPower1.getIdNumber();

                //本月学习积分
                Double thisMonthIntegral = 0.0;
                if(affairLearnPower1.getThisMonthIntegral()!=null){
                    thisMonthIntegral = affairLearnPower1.getThisMonthIntegral();
                }

                //年份
                String year = time.substring(0, 4);
                //月份
                String strh = time.substring(time.length() - 2, time.length());

                int mon = Integer.valueOf(strh);
                int lastMo = mon - 1;

                //上月月份
                String lastMonth = String.valueOf(lastMo);

                if (lastMo < 10 && mon > 0) {
                    //上月时间
                    String lastTime = year + "-0" + lastMonth;

                    //上月分数
                    Double lastScore = affairLearnPowerService.selectLastScore(idNumber, lastTime);
                    Double sum = 0.0;
                    if (null != lastScore) {
                        affairLearnPower1.setLastMonthIntegral(lastScore);
                        sum = thisMonthIntegral + lastScore;
                    } else {
                        affairLearnPower1.setLastMonthIntegral(0.0);
                        sum = thisMonthIntegral + 0.0;
                    }
                    affairLearnPower1.setThisMonthStatistics(sum);
                    //一年分数总和
                    Double y = affairLearnPowerService.selectSumYearById(year, idNumber,affairLearnPower1.getUnitId());
                    affairLearnPower1.setThisYearStatistics(y);


                }
                else if (lastMo > 9){
                    //上个月时间
                    String lastTime = year + "-" + lastMonth;

                    //上月分数
                    Double lastScore = affairLearnPowerService.selectLastScore(idNumber, lastTime);

                    //本月学习分数
                    Double score = 0.0;
                    if(affairLearnPower1.getThisMonthIntegral()!=null){
                        score = affairLearnPower1.getThisMonthIntegral();
                    }

                    if (null != lastScore) {
                        affairLearnPower1.setLastMonthIntegral(lastScore);
                        //上月及本月分数总和
                        Double sum = lastScore + score;
                        affairLearnPower1.setThisMonthStatistics(sum);
                    } else {
                        affairLearnPower1.setThisMonthStatistics(score);
                    }
                    //一年分数总和
                    Double y = affairLearnPowerService.selectSumYearById(year, idNumber,affairLearnPower1.getUnitId());

                    //11.7  为空处理 kevin.jia
                    /*if(StringUtils.isNotBlank(y)){
                        yearScore = Double.valueOf(y);
                    }else{
                        yearScore = 0.0;
                    }*/
                    affairLearnPower1.setThisYearStatistics(y);

                }
            }

            model.addAttribute("list", list);
        }
        boolean isPerson = false;
        PersonnelBase personnel = personnelBaseService.findInfoByIdNumber(UserUtils.getUser().getNo());
        if(personnel!=null){
            isPerson = true;
        }
        model.addAttribute("isPerson", isPerson);

        return "modules/affair/affairLearnPowerList";
    }

    //@RequiresPermissions("affair:affairLearnPower:view")
    @RequestMapping(value = {"affairLearnPowerProblemDataList"})
    public String affairLearnPowerProblemDataList(AffairLearnPower affairLearnPower, HttpServletRequest request, HttpServletResponse response, Model model) {

        List<AffairLearnPower> affairLearnPowerList = new ArrayList<>();
        Page<AffairLearnPower> page = affairLearnPowerService.findProblemDataList(new Page<AffairLearnPower>(request,response),affairLearnPower);
        model.addAttribute("page", page);
        return "modules/affair/affairLearnPowerProblemDataList";
    }

    @RequiresPermissions("affair:affairLearnPower:view")
    @RequestMapping(value = "form")
    public String form(AffairLearnPower affairLearnPower, Model model) {
        model.addAttribute("affairLearnPower", affairLearnPower);
        return "modules/affair/affairLearnPowerForm";
    }



    /**
     * 详情
     *
     * @param affairLearnPower
     * @param model
     * @return
     */
    @RequiresPermissions("affair:affairLearnPower:view")
    @RequestMapping(value = "formDetail")
    public String formDetail(AffairLearnPower affairLearnPower, Model model) {
        model.addAttribute("affairLearnPower", affairLearnPower);
        if (affairLearnPower.getAdjunct() != null && affairLearnPower.getAdjunct().length() > 0) {
            List<Map<String, String>> filePathList = uploadController.filePathHandle(affairLearnPower.getAdjunct());
            model.addAttribute("filePathList", filePathList);
        }
        return "modules/affair/affairLearnPowerFormDetail";
    }


    @RequiresPermissions("affair:affairLearnPower:edit")
    @RequestMapping(value = "save")
    public String save(AffairLearnPower affairLearnPower, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, affairLearnPower)) {
            return form(affairLearnPower, model);
        }
        affairLearnPowerService.save(affairLearnPower);

        addMessage(redirectAttributes, "保存学习强国成功");
        model.addAttribute("saveResult", "sucess");
        return "modules/affair/affairLearnPowerForm";
    }

    @RequiresPermissions("affair:affairLearnPower:edit")
    @RequestMapping(value = "delete")
    public String delete(AffairLearnPower affairLearnPower, RedirectAttributes redirectAttributes) {
        affairLearnPowerService.delete(affairLearnPower);
        addMessage(redirectAttributes, "删除学习强国成功");
        return "redirect:" + Global.getAdminPath() + "/affair/affairLearnPower/?repage";
    }

    @RequiresPermissions("affair:affairLearnPower:edit")
    @RequestMapping(value = "affairLearnPowerProblemDataList/delete")
    public String problemDataDelete(AffairLearnPower affairLearnPower, RedirectAttributes redirectAttributes) {
        affairLearnPowerService.delete(affairLearnPower);
        addMessage(redirectAttributes, "删除学习强国成功");
        return "redirect:" + Global.getAdminPath() + "/affair/affairLearnPower/affairLearnPowerProblemDataList?repage";
    }


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequiresPermissions("affair:affairLearnPower:edit")
    @RequestMapping(value = {"deleteByIds"})
    public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
        Result result = new Result();
        if (ids != null && ids.size() > 0) {
            affairLearnPowerService.deleteByIds(ids);
            result.setSuccess(true);
            result.setMessage("删除成功");
        } else {
            result.setSuccess(false);
            result.setMessage("请先选择要删除的内容");
        }
        return result;
    }


    /**
     * 导出excel格式数据
     *
     * @param
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportExcelByTemplate(AffairLearnPower affairLearnPower, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

        XSSFWorkbook wb = null;
        try {

            if ("5".equals(UserUtils.getUser().getOffice().getId())) {
                affairLearnPower.setUnitId(UserUtils.getUser().getCompany().getId());
            } else {
                affairLearnPower.setUnitId(UserUtils.getUser().getOffice().getId());
            }
            affairLearnPower.setCreateBy(UserUtils.getUser());
            String fileName = "";
            if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
                fileName = request.getParameter("fileName").toString();
            }
            Page<AffairLearnPower> page = null;
            if (flag == true) {
                page = affairLearnPowerService.findPage(new Page<AffairLearnPower>(request, response), affairLearnPower);
            } else {
                page = affairLearnPowerService.findPage(new Page<AffairLearnPower>(request, response, -1), affairLearnPower);
            }
/*
			Page<AffairFocusStudy> page = affairFocusStudyService.findPage(new Page<AffairFocusStudy>(request, response,-1), affairFocusStudy);
*/
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
            ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairLearnPower.class);
            exportExcelNew.setWb(wb);
            List<AffairLearnPower> list = page.getList();
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
            addMessage(redirectAttributes, "导出用户失败！失败信息：" + ex);
        }
        return "redirect:" + adminPath + "/affair/affairLearnPower?repage";
    }

    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 0, 0);
            List<AffairLearnPower> list = ei.getDataList(AffairLearnPower.class);
            for (AffairLearnPower affairLearnPower : list) {
                    try {
                        Pattern pattern = Pattern.compile("[0-9]+[.]{0,1}[0-9]*[dD]{0,1}");
                        Matcher isNum = pattern.matcher((affairLearnPower.getThisMonthIntegral()).toString());
                        if (isNum.matches()) {
                            affairLearnPower.setUnitId(officeService.findByName(affairLearnPower.getUnit().trim()));
                            BeanValidators.validateWithException(validator, affairLearnPower);
                            if(StringUtils.isNotBlank(affairLearnPower.getUnitId())){
                                if(StringUtils.isNotBlank(affairLearnPower.getIdNumber().trim())){
                                    affairLearnPower.setName(affairLearnPower.getName().trim());
                                    affairLearnPowerService.save(affairLearnPower);
                                    successNum++;
                                }else{
                                    failureMsg.append(affairLearnPower.getName() + "导入失败，身份证号为空;<br> ");
                                    failureNum++;
                                }
                            }else{
                                failureMsg.append(affairLearnPower.getName() + "导入失败，请填写正确单位名称，请填写本系统所设定单位名称;<br> ");
                                failureNum++;
                            }
                        }else{
                            failureMsg.append(affairLearnPower.getName() + "导入失败，请填写正确积分;<br> ");
                            failureNum++;
                        }
                    } catch (ConstraintViolationException ex) {
                        List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                        for (String message : messageList) {
                            failureMsg.append(message + "; ");
                            failureNum++;
                        }
                    } catch (NullPointerException ex) {
                        failureMsg.append(" 导入失败：请检查Excel表格中是否有空行");
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
        //redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:" + adminPath + "/file/template/download/view?id=affair_affairLearnPower";
    }


}