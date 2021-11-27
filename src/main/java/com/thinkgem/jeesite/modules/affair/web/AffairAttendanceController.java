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
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.dao.AffairAttendanceChildDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairAttendanceDao;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.affair.service.AffairAttendanceChildService;
import com.thinkgem.jeesite.modules.affair.service.AffairAttendanceFlagService;
import com.thinkgem.jeesite.modules.affair.service.AffairAttendanceService;
import com.thinkgem.jeesite.modules.affair.service.AffairLaborOfficeService;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.warn.entity.Warning;
import com.thinkgem.jeesite.modules.warn.service.WarningService;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 考勤记录Controller
 *
 * @author mason.xv
 * @version 2020-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairAttendance")
public class AffairAttendanceController extends BaseController {

    @Autowired
    private AffairAttendanceService affairAttendanceService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private AffairAttendanceChildService affairAttendanceChildService;

    @Autowired
    private AffairAttendanceFlagService affairAttendanceFlagService;

    @Autowired
    private WarningService warningService;

    @Autowired
    private AffairAttendanceChildDao affairAttendanceChildDao;

    @Autowired
    private AffairAttendanceDao affairAttendanceDao;

    @Autowired
    private AffairLaborOfficeService affairLaborOfficeService;

    @ModelAttribute
    public AffairAttendance get(@RequestParam(required = false) String id) {
        AffairAttendance entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = affairAttendanceService.get(id);
        }
        if (entity == null) {
            entity = new AffairAttendance();
        }
        return entity;
    }

    @RequiresPermissions("affair:affairAttendance:view")
    @RequestMapping(value = {""})
    public String index(AffairAttendance affairAttendance, HttpServletRequest request, HttpServletResponse response, Model model) {
        /*if (affairAttendance.getYear()==null || "".equals(affairAttendance.getYear())){
        request.setAttribute("year",request.getParameter("year"));
        if(request.getParameter("mouth")!=null && !"".equals(request.getParameter("mouth"))){
            request.setAttribute("mouth",request.getParameter("mouth").toString());
        }}
        model.addAttribute("page", request);*/
        return "modules/affair/affairAttendanceIndex";
    }

    @RequiresPermissions("affair:affairAttendance:view")
    @RequestMapping(value = {"list"})
    public String list(AffairAttendance affairAttendance, HttpServletRequest request, HttpServletResponse response, Model model) {
        Calendar calendar = Calendar.getInstance();
        if (affairAttendance.getYear() == null || "".equals(affairAttendance.getYear())) {
            affairAttendance.setYear(calendar.get(Calendar.YEAR));
            affairAttendance.setMouth(calendar.get(Calendar.MONTH) + 1);
        }
       /* String year = String.valueOf(affairAttendance.getYear());
        String month = String.valueOf(affairAttendance.getMouth());
        String date = year + "-" + month + "-" + "1";*/
        //Page<AffairAttendance> page = affairAttendanceService.findPage(new Page<AffairAttendance>(request, response), affairAttendance);
        AffairAttendanceChild affairAttendanceChild = new AffairAttendanceChild();
        affairAttendanceChild.setYear(affairAttendance.getYear());
        affairAttendanceChild.setMonth(affairAttendance.getMouth());
        affairAttendanceChild.setName(affairAttendance.getName());
        affairAttendanceChild.setIdNumber(affairAttendance.getIdNumber());
        affairAttendanceChild.setUnitId(affairAttendance.getUnitId());
       /* List tlist = TimeUtils.getDayListOfOneMonth(date);*/
        AffairAttendanceFlag affairAttendanceFlag = new AffairAttendanceFlag();
        affairAttendanceFlag.setYear(affairAttendance.getYear());
        affairAttendanceFlag.setMonth(affairAttendance.getMouth());
        List<AffairAttendanceFlag> flagList = affairAttendanceService.findAll(affairAttendanceFlag);
       /* List<String> holiday = new ArrayList<>();
        for (AffairAttendanceFlag a : flagList) {
            holiday.add(a.getDate());
        }*/
        //获得到所有的缺勤天数并判断是否超过150天
//        List<Map<String, String>> sumLackDay = affairAttendanceChildService.findSumLackDay();
        if(StringUtils.isNotBlank(affairAttendance.getUnitId())|| UserUtils.getUser().isAdmin()){
            String year = DateUtils.getYear();
            List<Map<String, String>> sumLackDay = affairAttendanceChildService.findSumLackDayByUnitId(affairAttendance.getUnitId(),Integer.valueOf(year));
            Warning warning = new Warning();
            for (int i = 0; i < sumLackDay.size(); i++) {
                if (Integer.valueOf(String.valueOf(sumLackDay.get(i).get("sumday"))) >= 150) {
                    warning.setName(sumLackDay.get(i).get("unit") + "单位下" + sumLackDay.get(i).get("name") + year+"年累计病事假达5个月");
                    Warning oldWarning = warningService.findKQOldWarnByName(warning.getName());
                    if(oldWarning != null){
                        warningService.save(oldWarning);
                    }else{
                        User user = officeService.findUserById(sumLackDay.get(i).get("unit_id"));
                        String userName = sumLackDay.get(i).get("unit");
                        String userId = sumLackDay.get(i).get("unit_id");
                        if(user!=null){
                            userName = user.getName();
                            userId = user.getId();
                        }
                        warning.setReceivePerName("劳资信息管理," + userName);
                        warning.setReceivePerId("a58a91c7d4db4cd4b639c863c0e48832," + userId);
                        warning.setRepeatCycle("4");
                        Calendar c = Calendar.getInstance();
                        warning.setDate(c.getTime());
                        warning.setRemind("20");
                        warning.setIsAlert("1");
                        warning.setAlertDegree("1");
                        warning.setContinueDay("0");
                        warning.setAlertContent(sumLackDay.get(i).get("unit") + "单位下" + sumLackDay.get(i).get("name") + year + "年，累计病事假达5个月");
                        warningService.save(warning);
                    }
                }
            }
        }
        Page<AffairAttendanceChild> page = affairAttendanceChildService.findPage(new Page<AffairAttendanceChild>(request, response,-1), affairAttendanceChild);
        model.addAttribute("page", page);
        model.addAttribute("flagList", flagList);
        /*model.addAttribute("holiday", holiday);*/
        model.addAttribute("uid", affairAttendance.getUnitId());
        model.addAttribute("mo",affairAttendance.getMouth());
        model.addAttribute("ye",affairAttendance.getYear());
        String nameById = affairLaborOfficeService.findNameById(affairAttendance.getUnitId());
        model.addAttribute("ui", nameById);
        return "modules/affair/affairAttendanceList";
    }

    @RequiresPermissions("affair:affairAttendance:view")
    @RequestMapping(value = "form")
    public String form(AffairAttendance affairAttendance, Model model, HttpServletRequest request) {
        /*List<String> holiday = new ArrayList<>();
        for (AffairAttendanceFlag a : flagList) {
            holiday.add(a.getDate());
        }*/
        String showFlag = "";
        //获得单位
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String unitId = request.getParameter("unitId");
        if(!StringUtils.isEmpty(unitId)){
            AffairLaborOffice office = affairLaborOfficeService.get(unitId);
            affairAttendance.setUnit(office.getName());
        }
        Calendar calendar = Calendar.getInstance();
        if (affairAttendance.getYear() == null || "".equals(affairAttendance.getYear())) {
            affairAttendance.setYear(calendar.get(Calendar.YEAR));
            affairAttendance.setMouth(calendar.get(Calendar.MONTH) + 1);
        }else {
            if(StringUtils.isNotEmpty(year) && StringUtils.isNotEmpty(month)){
                affairAttendance.setYear(Integer.valueOf(year));
                affairAttendance.setMouth(Integer.valueOf(month));
            }else {
                affairAttendance.setYear(calendar.get(Calendar.YEAR));
                affairAttendance.setMouth(calendar.get(Calendar.MONTH) + 1);
            }

        }
        AffairAttendanceFlag affairAttendanceFlag = new AffairAttendanceFlag();
        affairAttendanceFlag.setMonth(affairAttendance.getMouth());
        affairAttendanceFlag.setYear(affairAttendance.getYear());
        List<AffairAttendanceFlag> flagList = affairAttendanceService.findAll(affairAttendanceFlag);
       /* String year = String.valueOf(affairAttendance.getYear());
        String month = String.valueOf(affairAttendance.getMouth());
        String date = year + "-" + month + "-" + "1";
        List tlist = TimeUtils.getDayListOfOneMonth(date);*/
        //获取今天是几号
        String nowDay = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        List<AffairAttendanceChild> childList = null;
        if (StringUtils.isBlank(affairAttendance.getId())) {
            childList = new ArrayList<AffairAttendanceChild>();
//            List<PersonnelBase> plist = affairAttendanceChildService.findPerson(unitId);
            List<AffairLaborSort> plist = affairAttendanceChildService.findPersonInfo(unitId);
            for (int i = 0; i < plist.size(); i++) {
                AffairAttendanceChild test = new AffairAttendanceChild();
                AffairAttendanceChild affairAttendanceChild = affairAttendanceChildService.findPersonAttendanceByIdNumber(plist.get(i).getIdNumber(),unitId, Integer.parseInt(year),Integer.parseInt(month));
                if(affairAttendanceChild!=null){
                     BeanUtils.copyProperties(affairAttendanceChild,test);
                }else{
                    String name = plist.get(i).getName();
                    String idNumber = plist.get(i).getIdNumber();
                    test.setName(name);
                    test.setIdNumber(idNumber);
                    test.setJfDays("0");
                    test.setLockFlag("0");
                }
                childList.add(test);
            }
            affairAttendance.setAffairAttendanceChildList(childList);
        } else {
            childList = affairAttendance.getAffairAttendanceChildList();
        }
        if (null != childList) {
            for (int i = 0; i < childList.size(); i++) {
                AffairAttendanceChild child = childList.get(i);
                String[] locks = new String[36];
                if ("1".equals(child.getLockFlag()) || "".equals(child.getLockFlag()) || child.getLockFlag() == null) {
                   /* for (int t = 0; t < flagList.size()+5; t++) {*/
                    for (int t = 0; t < 36; t++) {
						/*if(t <Integer.parseInt(nowDay)-1 || t > Integer.parseInt(nowDay)- 1){
							locks[t] = "disabled";
						}*/
                        locks[t] = "disabled";
                        showFlag = "1";
                    }
                } else {
                    for (int t = 0; t < flagList.size()+5; t++) {
                        locks[t] = "";
                        showFlag = "0";
                    }
                }

                this.setLocks(locks, child);
                child.setLocks(locks);
            }
        }
        List<String> lockList = affairAttendanceChildService.findLock(affairAttendance.getId());
        model.addAttribute("affairAttendance", affairAttendance);
        model.addAttribute("flagList", flagList);
        model.addAttribute("nowDay", nowDay);
//        model.addAttribute("holiday", holiday);
        model.addAttribute("lockList", lockList);
        model.addAttribute("uid", affairAttendance.getUnitId());
        model.addAttribute("showFlag", showFlag);
        return "modules/affair/affairAttendanceForm";
    }

    @RequiresPermissions("affair:affairAttendance:view")
    @RequestMapping(value = "addOne")
    public String addOne(AffairAttendance affairAttendance, Model model, HttpServletRequest request) {
       /* List<String> holiday = new ArrayList<>();
        for (AffairAttendanceFlag a : flagList) {
            holiday.add(a.getDate());
        }*/
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        //获得单位
        String unitId = request.getParameter("unitId");
        if(!StringUtils.isEmpty(unitId)){
            AffairLaborOffice office = affairLaborOfficeService.get(unitId);
            affairAttendance.setUnit(office.getName());
        }
        Calendar calendar = Calendar.getInstance();
        if (affairAttendance.getYear() == null || "".equals(affairAttendance.getYear())) {
            affairAttendance.setYear(calendar.get(Calendar.YEAR));
            affairAttendance.setMouth(calendar.get(Calendar.MONTH) + 1);
        }else {
            if(StringUtils.isNotEmpty(year) && StringUtils.isNotEmpty(month)){
                affairAttendance.setYear(Integer.valueOf(year));
                affairAttendance.setMouth(Integer.valueOf(month));
            }else {
                affairAttendance.setYear(calendar.get(Calendar.YEAR));
                affairAttendance.setMouth(calendar.get(Calendar.MONTH) + 1);
            }

        }
        AffairAttendanceFlag affairAttendanceFlag = new AffairAttendanceFlag();
        affairAttendanceFlag.setMonth(affairAttendance.getMouth());
        affairAttendanceFlag.setYear(affairAttendance.getYear());
        List<AffairAttendanceFlag> flagList = affairAttendanceService.findAll(affairAttendanceFlag);
        /*String year = String.valueOf(affairAttendance.getYear());
        String month = String.valueOf(affairAttendance.getMouth());
        String date = year + "-" + month + "-" + "1";
        List tlist = TimeUtils.getDayListOfOneMonth(date);*/
        //获取今天是几号
        String nowDay = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        List<AffairAttendanceChild> childList = null;
        if (StringUtils.isBlank(affairAttendance.getId())) {
            childList = new ArrayList<AffairAttendanceChild>();
            AffairAttendanceChild test = new AffairAttendanceChild();
            test.setName("");
            test.setIdNumber("");
            test.setJfDays("0");
            test.setLockFlag("0");
            childList.add(test);
            affairAttendance.setAffairAttendanceChildList(childList);
        } else {
            childList = affairAttendance.getAffairAttendanceChildList();
        }
        if (null != childList) {
            for (int i = 0; i < childList.size(); i++) {
                AffairAttendanceChild child = childList.get(i);
                String[] locks = new String[36];
                if ("1".equals(child.getLockFlag()) || "".equals(child.getLockFlag()) || child.getLockFlag() == null) {
                    /* for (int t = 0; t < flagList.size()+5; t++) {*/
                    for (int t = 0; t < 36; t++) {
						/*if(t <Integer.parseInt(nowDay)-1 || t > Integer.parseInt(nowDay)- 1){
							locks[t] = "disabled";
						}*/
                        locks[t] = "readonly";
                    }
                } else {
                    for (int t = 0; t < flagList.size()+5; t++) {
                        locks[t] = "";
                    }
                }

                this.setLocks(locks, child);
                child.setLocks(locks);
            }
        }
        List<String> lockList = affairAttendanceChildService.findLock(affairAttendance.getId());
        model.addAttribute("affairAttendance", affairAttendance);
        model.addAttribute("flagList", flagList);
        model.addAttribute("nowDay", nowDay);
//        model.addAttribute("holiday", holiday);
        model.addAttribute("lockList", lockList);
        model.addAttribute("uid", affairAttendance.getUnitId());
        return "modules/affair/affairAttendanceFormOne";
    }

    @RequestMapping(value = "updateOne")
    public String updateOne(AffairAttendance affairAttendance, Model model, HttpServletRequest request) {
        /*List<String> holiday = new ArrayList<>();
        for (AffairAttendanceFlag a : flagList) {
            holiday.add(a.getDate());
        }*/
        String showFlag = "";
        //获得单位
        String idNumber = request.getParameter("idNumber");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String unitId = request.getParameter("unitId");
        if(!StringUtils.isEmpty(unitId)){
            AffairLaborOffice office = affairLaborOfficeService.get(unitId);
            affairAttendance.setUnit(office.getName());
        }
        Calendar calendar = Calendar.getInstance();
        if (affairAttendance.getYear() == null || "".equals(affairAttendance.getYear())) {
            affairAttendance.setYear(calendar.get(Calendar.YEAR));
            affairAttendance.setMouth(calendar.get(Calendar.MONTH) + 1);
        }else {
            if(StringUtils.isNotEmpty(year) && StringUtils.isNotEmpty(month)){
                affairAttendance.setYear(Integer.valueOf(year));
                affairAttendance.setMouth(Integer.valueOf(month));
            }else {
                affairAttendance.setYear(calendar.get(Calendar.YEAR));
                affairAttendance.setMouth(calendar.get(Calendar.MONTH) + 1);
            }

        }
        AffairAttendanceFlag affairAttendanceFlag = new AffairAttendanceFlag();
        affairAttendanceFlag.setMonth(affairAttendance.getMouth());
        affairAttendanceFlag.setYear(affairAttendance.getYear());
        List<AffairAttendanceFlag> flagList = affairAttendanceService.findAll(affairAttendanceFlag);
       /* String year = String.valueOf(affairAttendance.getYear());
        String month = String.valueOf(affairAttendance.getMouth());
        String date = year + "-" + month + "-" + "1";
        List tlist = TimeUtils.getDayListOfOneMonth(date);*/
        //获取今天是几号
        String nowDay = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        List<AffairAttendanceChild> childList = affairAttendanceChildService.findPersonById(idNumber,unitId,Integer.valueOf(year),Integer.valueOf(month));
        affairAttendance.setAffairAttendanceChildList(childList);
        if (null != childList) {
            for (int i = 0; i < childList.size(); i++) {
                AffairAttendanceChild child = childList.get(i);
                String[] locks = new String[36];
                if ("1".equals(child.getLockFlag()) || "".equals(child.getLockFlag()) || child.getLockFlag() == null) {
                    /* for (int t = 0; t < flagList.size()+5; t++) {*/
                    for (int t = 0; t < 36; t++) {
						/*if(t <Integer.parseInt(nowDay)-1 || t > Integer.parseInt(nowDay)- 1){
							locks[t] = "disabled";
						}*/
                        locks[t] = "disabled";
                        showFlag = "1";
                    }
                } else {
                    for (int t = 0; t < flagList.size()+5; t++) {
                        locks[t] = "";
                        showFlag = "0";
                    }
                }

                this.setLocks(locks, child);
                child.setLocks(locks);
            }
        }
        List<String> lockList = affairAttendanceChildService.findLock(affairAttendance.getId());
        model.addAttribute("affairAttendance", affairAttendance);
        model.addAttribute("flagList", flagList);
        model.addAttribute("nowDay", nowDay);
//        model.addAttribute("holiday", holiday);
        model.addAttribute("lockList", lockList);
        model.addAttribute("uid", affairAttendance.getUnitId());
        model.addAttribute("idNumber",idNumber);
        model.addAttribute("showFlag", showFlag);
        return "modules/affair/affairAttendanceFormOne";
    }

    private void setLocks(String[] locks, AffairAttendanceChild child) {
        child.setLock1(locks[0]);
        child.setLock2(locks[1]);
        child.setLock3(locks[2]);
        child.setLock4(locks[3]);
        child.setLock5(locks[4]);
        child.setLock6(locks[5]);
        child.setLock7(locks[6]);
        child.setLock8(locks[7]);
        child.setLock9(locks[8]);
        child.setLock10(locks[9]);
        child.setLock11(locks[10]);
        child.setLock12(locks[11]);
        child.setLock13(locks[12]);
        child.setLock14(locks[13]);
        child.setLock15(locks[14]);
        child.setLock16(locks[15]);
        child.setLock17(locks[16]);
        child.setLock18(locks[17]);
        child.setLock19(locks[18]);
        child.setLock20(locks[19]);
        child.setLock21(locks[20]);
        child.setLock22(locks[21]);
        child.setLock23(locks[22]);
        child.setLock24(locks[23]);
        child.setLock25(locks[24]);
        child.setLock26(locks[25]);
        child.setLock27(locks[26]);
        child.setLock28(locks[27]);
        child.setLock29(locks[28]);
        child.setLock30(locks[29]);
        child.setLock31(locks[30]);
        child.setLock32(locks[31]);
        child.setLock33(locks[32]);
        child.setLock34(locks[33]);
        child.setLock35(locks[34]);
        child.setLock36(locks[35]);

    }
   /* @RequiresPermissions("affair:affairAttendance:view")
    @RequestMapping(value = {"detail"})
    public String detail(AffairAttendance affairAttendance, HttpServletRequest request, HttpServletResponse response, Model model) {
        Calendar calendar = Calendar.getInstance();
        Page<AffairAttendance> page = affairAttendanceService.findPage(new Page<AffairAttendance>(request, response), affairAttendance);
        model.addAttribute("page", page);
        return "modules/affair/affairAttendanceFormDetail";
    }*/

    @RequiresPermissions("affair:affairAttendance:edit")
    @RequestMapping(value = "save")
    public String save(AffairAttendance affairAttendance, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, affairAttendance)) {
            return form(affairAttendance, model, request);
        }
        if (null != affairAttendance.getAffairAttendanceChildList()) {
            //处理锁定列产生的数据重复问题
            for (AffairAttendanceChild affairAttendanceChild : affairAttendance.getAffairAttendanceChildList()) {
                this.setAffairAttendanceChild(affairAttendanceChild);
            }
        }
        affairAttendanceService.save(affairAttendance);
        addMessage(redirectAttributes, "保存考勤记录成功");
        request.setAttribute("saveResult", "sucess");
        return "modules/affair/affairAttendanceForm";

    }

    @RequiresPermissions("affair:affairAttendance:edit")
    @RequestMapping(value = "saveOne")
    public String saveOne(AffairAttendance affairAttendance, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, affairAttendance)) {
            return form(affairAttendance, model, request);
        }
        String num = request.getParameter("idNumber");
        affairAttendance.setIdNumber(num);
        affairAttendanceService.saveOne(affairAttendance);
        addMessage(redirectAttributes, "保存考勤记录成功");
        request.setAttribute("saveResult", "sucess");
        return "modules/affair/affairAttendanceForm";

    }

    @RequiresPermissions("affair:affairAttendance:edit")
    @RequestMapping(value = "delete")
    public String delete(AffairAttendance affairAttendance, RedirectAttributes redirectAttributes) {
        affairAttendanceService.delete(affairAttendance);
        addMessage(redirectAttributes, "删除考勤记录成功");
        return "redirect:" + Global.getAdminPath() + "/affair/affairAttendance/list?repage";
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequiresPermissions("affair:affairAttendance:edit")
    @RequestMapping(value = {"deleteByIds"})
    public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
        Result result = new Result();
        if (ids != null && ids.size() > 0) {
            affairAttendanceService.deleteByIds(ids);
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
    public String exportExcelByTemplate(AffairAttendance affairAttendance, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
        XSSFWorkbook wb = null;
        try {
            String fileName = "";
            if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
                fileName = request.getParameter("fileName");
            }
            Page<AffairAttendanceChild> page = null;
            AffairAttendanceChild affairAttendanceChild = new AffairAttendanceChild();
            affairAttendanceChild.setYear(affairAttendance.getYear());
            affairAttendanceChild.setMonth(affairAttendance.getMouth());
            affairAttendanceChild.setName(affairAttendance.getName());
            affairAttendanceChild.setIdNumber(affairAttendance.getIdNumber());
            affairAttendanceChild.setUnitId(request.getParameter("unitId"));
           /* if (flag == true) {
                page = affairAttendanceChildService.findPage(new Page<AffairAttendanceChild>(request, response), affairAttendanceChild);
            } else {
                page = affairAttendanceChildService.findPage(new Page<AffairAttendanceChild>(request, response, -1), affairAttendanceChild);
            }*/
            page = affairAttendanceChildService.findPage(new Page<AffairAttendanceChild>(request, response,-1), affairAttendanceChild);
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
            // 导出标题
            String year = String.valueOf(affairAttendance.getYear());
            String mouth = String.valueOf(affairAttendance.getMouth());
            String unitId = request.getParameter("unitId");
            String unit = affairLaborOfficeService.findNameById(unitId);
            String title = year + "年" + mouth + "月" + unit + "考勤";
            //修改
            ExportExcelNew exportExcelNew = new ExportExcelNew(1, AffairAttendanceChild.class);
            exportExcelNew.setWb(wb);

            Row row = exportExcelNew.getNewRow();
            row.createCell(0).setCellValue(title);
            List<AffairAttendanceChild> list = page.getList();
            exportExcelNew.setDataList(list);
            HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
            ServletOutputStream fout = response.getOutputStream();
            wb.getSheet("0");
            wb.write(fout);
            fout.close();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            addMessage(redirectAttributes, "导出用户失败！失败信息：" + ex);
        }
        //修改
        return "redirect:" + adminPath + "/affair/affairAttendance/list?repage";
    }

    /**
     * 导入
     *
     * @param file
     * @param redirectAttributes
     * @param attendance
     * @return
     */
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes, AffairAttendance attendance, HttpServletRequest request) {
        String unitId = request.getParameter("unitId");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        if (StringUtils.isNotBlank(attendance.getUnitId()) && StringUtils.isNotBlank(attendance.getYear().toString()) && StringUtils.isNotBlank(attendance.getMouth().toString())) {
            String attId = affairAttendanceService.getId(attendance.getUnitId(), attendance.getYear(), attendance.getMouth());
            if (attId != null) {
                try {
                    int successNum = 0;
                    int failureNum = 0;
                    StringBuilder failureMsg = new StringBuilder();
                    ImportExcel ei = new ImportExcel(file, 1, 0);
                    //修改
                    List<AffairAttendanceChild> list = ei.getDataList(AffairAttendanceChild.class);
                    for (AffairAttendanceChild affairAttendanceChild : list) {
                        try {
                            BeanValidators.validateWithException(validator, affairAttendanceChild);
                            affairAttendanceChild.setAttId(attId);
                            affairAttendanceChildService.save(affairAttendanceChild);
                            successNum++;
                        } catch (ConstraintViolationException ex) {
                            List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                            for (String message : messageList) {
                                failureMsg.append(message + "; ");
                                failureNum++;
                            }
                        } catch (Exception ex) {
                            failureMsg.append("(身份证号码:" + affairAttendanceChild.getIdNumber() + ")" + " 导入失败：" + ex.getMessage());
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
            }
        } else {
            addMessage(redirectAttributes, "导入无效！");
        }
        return "redirect:" + adminPath + "/file/template/download/affairAttendanceDownloadView";
    }

    @ResponseBody
    @RequiresPermissions("affair:affairAttendance:edit")
    @RequestMapping(value = {"importSave"})
    public Result importSave(AffairAttendance affairAttendance) {
        Result result = new Result();
        affairAttendanceService.importSave(affairAttendance);
        result.setSuccess(true);
        result.setMessage("保存成功");
        return result;
    }

    private void setAffairAttendanceChild(AffairAttendanceChild affairAttendanceChild) {
        if (!StringUtils.isEmpty(affairAttendanceChild.getId())) {
            String[] ids = affairAttendanceChild.getId().split(",");
            if (ids.length > 3) {
                affairAttendanceChild.setId(ids[3]);
            }else if (ids.length > 2 && ids.length <= 3){
                affairAttendanceChild.setId(ids[2]);
            }
            else if (ids.length > 1 && ids.length <= 2){
                affairAttendanceChild.setId(ids[1]);
            }
            else if (ids.length > 0 && ids.length <= 1){
                affairAttendanceChild.setId(ids[0]);
            }
            else {
                affairAttendanceChild.setId("");
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getName())) {
            String[] name = affairAttendanceChild.getName().split(",");
            if (name.length > 3) {
                affairAttendanceChild.setName(name[3]);
            }
            else if (name.length > 2 && name.length <= 3){
                affairAttendanceChild.setName(name[2]);
            }
            else if (name.length > 1 && name.length <= 2){
                affairAttendanceChild.setName(name[1]);
            }
            else if (name.length > 0 && name.length <= 1){
                affairAttendanceChild.setName(name[0]);
            }
            else {
                affairAttendanceChild.setName("");
            }
//            affairAttendanceChild.setName(name[3]);
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getIdNumber())) {
            String[] idNumber = affairAttendanceChild.getIdNumber().split(",");
            if (idNumber.length > 3) {
                affairAttendanceChild.setIdNumber(idNumber[3]);
            }
            else if (idNumber.length > 2 && idNumber.length <= 3){
                affairAttendanceChild.setIdNumber(idNumber[2]);
            }
            else if (idNumber.length > 1 && idNumber.length <= 2){
                affairAttendanceChild.setIdNumber(idNumber[1]);
            }
            else if (idNumber.length > 0 && idNumber.length <= 1){
                affairAttendanceChild.setIdNumber(idNumber[0]);
            }
            else {
                affairAttendanceChild.setIdNumber("");
            }
//            affairAttendanceChild.setIdNumber(idNumber[3]);
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getPoliceType())) {
            String[] policeType = affairAttendanceChild.getPoliceType().split(",");
            if (policeType.length > 3) {
                affairAttendanceChild.setPoliceType(policeType[3]);
            }
            else if (policeType.length > 2 && policeType.length <= 3){
                affairAttendanceChild.setPoliceType(policeType[2]);
            }
            else if (policeType.length > 1 && policeType.length <= 2){
                affairAttendanceChild.setPoliceType(policeType[1]);
            }
            else if (policeType.length > 0 && policeType.length <= 1){
                affairAttendanceChild.setPoliceType(policeType[0]);
            }
            else {
                affairAttendanceChild.setPoliceType(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getLinePost())) {
            String[] linePost = affairAttendanceChild.getLinePost().split(",");
            if (linePost.length > 3) {
                affairAttendanceChild.setLinePost(linePost[3]);
            }
            else if (linePost.length > 2 && linePost.length <= 3){
                affairAttendanceChild.setLinePost(linePost[2]);
            }
            else if (linePost.length > 1 && linePost.length <= 2){
                affairAttendanceChild.setLinePost(linePost[1]);
            }
            else if (linePost.length > 0 && linePost.length <= 1){
                affairAttendanceChild.setLinePost(linePost[0]);
            }
            else {
                affairAttendanceChild.setLinePost(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getWorkType())) {
            String[] workType = affairAttendanceChild.getWorkType().split(",");
            if (workType.length >3) {
                affairAttendanceChild.setWorkType(workType[3]);
            }
            else if (workType.length > 2 && workType.length <= 3){
                affairAttendanceChild.setWorkType(workType[2]);
            }
            else if (workType.length > 1 && workType.length <= 2){
                affairAttendanceChild.setWorkType(workType[1]);
            }
            else if (workType.length > 0 && workType.length <= 1){
                affairAttendanceChild.setWorkType(workType[0]);
            }
            else {
                affairAttendanceChild.setWorkType(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getMonthHours())) {
            String[] monthHours = affairAttendanceChild.getMonthHours().split(",");
            if (monthHours.length > 3) {
                affairAttendanceChild.setMonthHours(monthHours[3]);
            }
            else if (monthHours.length > 2 && monthHours.length <= 3){
                affairAttendanceChild.setMonthHours(monthHours[2]);
            }
            else if (monthHours.length > 1 && monthHours.length <= 2){
                affairAttendanceChild.setMonthHours(monthHours[1]);
            }
            else if (monthHours.length > 0 && monthHours.length <= 1){
                affairAttendanceChild.setMonthHours(monthHours[0]);
            }
            else {
                affairAttendanceChild.setMonthHours(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getInWork())) {
            String[] inWork = affairAttendanceChild.getInWork().split(",");
            if (inWork.length > 3) {
                affairAttendanceChild.setInWork(inWork[3]);
            }
            else if (inWork.length > 2 && inWork.length <= 3){
                affairAttendanceChild.setInWork(inWork[2]);
            }
            else if (inWork.length > 1 && inWork.length <= 2){
                affairAttendanceChild.setInWork(inWork[1]);
            }
            else if (inWork.length > 0 && inWork.length <= 1){
                affairAttendanceChild.setInWork(inWork[0]);
            }
            else {
                affairAttendanceChild.setInWork(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getJfDays())) {
            String[] jfDay = affairAttendanceChild.getJfDays().split(",");
            if (jfDay.length > 3) {
                affairAttendanceChild.setJfDays(jfDay[3]);
            }
            else if (jfDay.length > 2 && jfDay.length <= 3){
                affairAttendanceChild.setJfDays(jfDay[2]);
            }
            else if (jfDay.length > 1 && jfDay.length <= 2){
                affairAttendanceChild.setJfDays(jfDay[1]);
            }
            else if (jfDay.length > 0 && jfDay.length <= 1){
                affairAttendanceChild.setJfDays(jfDay[0]);
            }
            else {
                affairAttendanceChild.setJfDays(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getBeizhu())) {
            String[] beiZhu = affairAttendanceChild.getBeizhu().split(",");
            if (beiZhu.length > 3) {
                affairAttendanceChild.setBeizhu(beiZhu[3]);
            }
            else if (beiZhu.length > 2 && beiZhu.length <= 3){
                affairAttendanceChild.setBeizhu(beiZhu[2]);
            }
            else if (beiZhu.length > 1 && beiZhu.length <= 2){
                affairAttendanceChild.setBeizhu(beiZhu[1]);
            }
            else if (beiZhu.length > 0 && beiZhu.length <= 1){
                affairAttendanceChild.setBeizhu(beiZhu[0]);
            }
            else {
                affairAttendanceChild.setBeizhu(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDelFlag())) {
            affairAttendanceChild.setDelFlag("0");
//            String[] delFlag = affairAttendanceChild.getDelFlag().split(",");
//            if (delFlag.length > 3) {
//                affairAttendanceChild.setDelFlag(delFlag[3]);
//            } else {
//                affairAttendanceChild.setDelFlag(null);
//            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay1())) {
            String[] day = affairAttendanceChild.getDay1().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay1(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay1(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay1(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay1(day[0]);
            }
            else {
                affairAttendanceChild.setDay1(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay2())) {
            String[] day = affairAttendanceChild.getDay2().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay2(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay2(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay2(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay2(day[0]);
            }
            else {
                affairAttendanceChild.setDay2(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay3())) {
            String[] day = affairAttendanceChild.getDay3().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay3(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay3(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay3(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay3(day[0]);
            }
            else {
                affairAttendanceChild.setDay3(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay4())) {
            String[] day = affairAttendanceChild.getDay4().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay4(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay4(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay4(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay4(day[0]);
            }
            else {
                affairAttendanceChild.setDay4(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay5())) {
            String[] day = affairAttendanceChild.getDay5().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay5(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay5(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay5(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay5(day[0]);
            }
            else {
                affairAttendanceChild.setDay5(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay6())) {
            String[] day = affairAttendanceChild.getDay6().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay6(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay6(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay6(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay6(day[0]);
            }
            else {
                affairAttendanceChild.setDay6(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay7())) {
            String[] day = affairAttendanceChild.getDay7().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay7(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay7(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay7(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay7(day[0]);
            }
            else {
                affairAttendanceChild.setDay7(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay8())) {
            String[] day = affairAttendanceChild.getDay8().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay8(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay8(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay8(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay8(day[0]);
            }
            else {
                affairAttendanceChild.setDay8(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay9())) {
            String[] day = affairAttendanceChild.getDay9().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay9(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay9(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay9(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay9(day[0]);
            }
            else {
                affairAttendanceChild.setDay9(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay10())) {
            String[] day = affairAttendanceChild.getDay10().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay10(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay10(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay10(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay10(day[0]);
            }
            else {
                affairAttendanceChild.setDay10(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay11())) {
            String[] day = affairAttendanceChild.getDay11().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay11(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay11(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay11(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay11(day[0]);
            }
            else {
                affairAttendanceChild.setDay11(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay12())) {
            String[] day = affairAttendanceChild.getDay12().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay12(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay12(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay12(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay12(day[0]);
            }
            else {
                affairAttendanceChild.setDay12(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay13())) {
            String[] day = affairAttendanceChild.getDay13().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay13(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay13(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay13(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay13(day[0]);
            }
            else {
                affairAttendanceChild.setDay13(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay14())) {
            String[] day = affairAttendanceChild.getDay14().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay14(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay14(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay14(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay14(day[0]);
            }
            else {
                affairAttendanceChild.setDay14(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay15())) {
            String[] day = affairAttendanceChild.getDay15().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay15(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay15(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay15(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay15(day[0]);
            }
            else {
                affairAttendanceChild.setDay15(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay16())) {
            String[] day = affairAttendanceChild.getDay16().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay16(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay16(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay16(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay16(day[0]);
            }
            else {
                affairAttendanceChild.setDay16(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay17())) {
            String[] day = affairAttendanceChild.getDay17().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay17(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay17(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay17(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay17(day[0]);
            }
            else {
                affairAttendanceChild.setDay17(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay18())) {
            String[] day = affairAttendanceChild.getDay18().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay18(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay18(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay18(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay18(day[0]);
            }
            else {
                affairAttendanceChild.setDay18(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay19())) {
            String[] day = affairAttendanceChild.getDay19().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay19(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay19(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay19(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay19(day[0]);
            }
            else {
                affairAttendanceChild.setDay19(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay20())) {
            String[] day = affairAttendanceChild.getDay20().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay20(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay20(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay20(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay20(day[0]);
            }
            else {
                affairAttendanceChild.setDay20(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay21())) {
            String[] day = affairAttendanceChild.getDay21().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay21(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay21(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay21(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay21(day[0]);
            }
            else {
                affairAttendanceChild.setDay21(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay22())) {
            String[] day = affairAttendanceChild.getDay22().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay22(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay22(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay22(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay22(day[0]);
            }
            else {
                affairAttendanceChild.setDay22(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay23())) {
            String[] day = affairAttendanceChild.getDay23().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay23(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay23(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay23(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay23(day[0]);
            }
            else {
                affairAttendanceChild.setDay23(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay24())) {
            String[] day = affairAttendanceChild.getDay24().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay24(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay24(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay24(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay24(day[0]);
            }
            else {
                affairAttendanceChild.setDay24(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay25())) {
            String[] day = affairAttendanceChild.getDay25().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay25(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay25(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay25(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay25(day[0]);
            }
            else {
                affairAttendanceChild.setDay25(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay26())) {
            String[] day = affairAttendanceChild.getDay26().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay26(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay26(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay26(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay26(day[0]);
            }
            else {
                affairAttendanceChild.setDay26(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay27())) {
            String[] day = affairAttendanceChild.getDay27().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay27(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay27(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay27(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay27(day[0]);
            }
            else {
                affairAttendanceChild.setDay27(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay28())) {
            String[] day = affairAttendanceChild.getDay28().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay28(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay28(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay28(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay28(day[0]);
            }
            else {
                affairAttendanceChild.setDay28(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay29())) {
            String[] day = affairAttendanceChild.getDay29().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay29(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay29(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay29(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay29(day[0]);
            }
            else {
                affairAttendanceChild.setDay29(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay30())) {
            String[] day = affairAttendanceChild.getDay30().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay30(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay30(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay30(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay30(day[0]);
            }
            else {
                affairAttendanceChild.setDay30(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getDay31())) {
            String[] day = affairAttendanceChild.getDay31().split(",");
            if (day.length > 3) {
                affairAttendanceChild.setDay31(day[3]);
            }
            else if (day.length > 2 && day.length <= 3){
                affairAttendanceChild.setDay31(day[2]);
            }
            else if (day.length > 1 && day.length <= 2){
                affairAttendanceChild.setDay31(day[1]);
            }
            else if (day.length > 0 && day.length <= 1){
                affairAttendanceChild.setDay31(day[0]);
            }
            else {
                affairAttendanceChild.setDay31(null);
            }
        }
        if (!StringUtils.isEmpty(affairAttendanceChild.getLockFlag())) {
            String[] lockFlag = affairAttendanceChild.getLockFlag().split(",");
            if (lockFlag.length >3) {
                affairAttendanceChild.setLockFlag(lockFlag[3]);
            }
            else if (lockFlag.length > 2 && lockFlag.length <= 3){
                affairAttendanceChild.setLockFlag(lockFlag[2]);
            }
            else if (lockFlag.length > 1 && lockFlag.length <= 2){
                affairAttendanceChild.setLockFlag(lockFlag[1]);
            }
            else if (lockFlag.length > 0 && lockFlag.length <= 1){
                affairAttendanceChild.setLockFlag(lockFlag[0]);
            }
            else {
                affairAttendanceChild.setLockFlag(null);
            }
        }
    }

    /**
     *
     * 乘警支队导入
     */
    @RequestMapping(value = "child/import")
    public String childDownload(HttpServletResponse response, HttpServletRequest request,RedirectAttributes redirectAttributes) {
        String unitId ="";
        if(null != request.getParameter("unitId") && !"".equals(request.getParameter("unitId"))){
            unitId= request.getParameter("unitId");
            request.setAttribute("unitId",unitId);
        }
            String year= request.getParameter("year");
            request.setAttribute("year",year);
           String month= request.getParameter("month");
            request.setAttribute("month",month);
        if(null != request.getParameter("fileName") && !"".equals(request.getParameter("fileName"))){
            String template= request.getParameter("fileName");
            request.setAttribute("template",template);
        }
        request.setAttribute("url","/affair/affairAttendance/importChild?unitId="+unitId+"&year="+year+"&month="+month);
        return "modules/affair/child_template_import";
    }

    Integer queQin = 0;
    Integer gongShang = 0;
    Integer nianXiu = 0;
    Integer chuChai = 0;
    Double zhiQin = 0.0;
    Integer jiaBan = 0;
    Double lingXingJB = 0.0;
    Integer kuang = 0;
    Double realZhiQin = 0.0;

    @RequestMapping(value = "importChild", method=RequestMethod.POST)
    public String importChildFile(MultipartFile file, RedirectAttributes redirectAttributes,String unitId,String year, String month,Model model) {

        AffairAttendance attendance = new AffairAttendance();
        String mainId = IdGen.uuid();
        attendance.setId(mainId);
        attendance.setUnit(affairLaborOfficeService.findNameById(unitId));
        attendance.setUnitId(unitId);
        attendance.setYear(Integer.valueOf(year));
        attendance.setMouth(Integer.valueOf(month));
        affairAttendanceDao.insert(attendance);
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<AffairAttendanceChild> list = ei.getDataList(AffairAttendanceChild.class);
            attendance.setAffairAttendanceChildList(list);
            for (AffairAttendanceChild affairAttendanceChild : list){
                try{
                    //单位绑定单位id
                    BeanValidators.validateWithException(validator, affairAttendanceChild);
                    affairAttendanceChild.setAttId(attendance.getId());
                    affairAttendanceChild.preInsert();
                    autoCalculate(affairAttendanceChild,Integer.valueOf(year),Integer.valueOf(month));
                    affairAttendanceChildDao.insert(affairAttendanceChild);
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
                    failureMsg.append(" 导入失败："+ex.getMessage());
                }
            }
            if (failureNum>0){
                failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
            }
            addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
        }
        addMessage(redirectAttributes, "已成功导入 ");
        redirectAttributes.addFlashAttribute("result","success");
        redirectAttributes.addFlashAttribute("attendance",attendance);
        model.addAttribute("result","success");
        model.addAttribute("url","/affair/attendance/importChild");
        return "modules/affair/child_template_import";
    }

    /**
     * 缺勤、工伤、年休、出差、执勤、加班、零星加班自动计算
     * @param affairAttendanceChild
     */
    private void autoCalculate(AffairAttendanceChild affairAttendanceChild,Integer ye, Integer mo){
        queQin = 0;
        gongShang = 0;
        nianXiu = 0;
        chuChai = 0;
        zhiQin = 0.0;
        jiaBan = 0;
        lingXingJB = 0.0;
        String weekday = null;
        String day = null;
        //获取今天是几号
        String nowDay = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        AffairAttendance affairAttendance = new AffairAttendance();
		/*Calendar calendar = Calendar.getInstance();
		if (affairAttendance.getYear() == null || "".equals(affairAttendance.getYear())) {
			affairAttendance.setYear(calendar.get(Calendar.YEAR));
			affairAttendance.setMouth(calendar.get(Calendar.MONTH) + 1);
		}
		String year = String.valueOf(affairAttendance.getYear());
		String month = String.valueOf(affairAttendance.getMouth());*/
        AffairAttendanceFlag affairAttendanceFlag = new AffairAttendanceFlag();
		/*affairAttendanceFlag.setMonth(calendar.get(Calendar.MONTH) + 1);
		affairAttendanceFlag.setYear(calendar.get(Calendar.YEAR));*/
        affairAttendanceFlag.setMonth(mo);
        affairAttendanceFlag.setYear(ye);
        // 获得某年某月实际出勤天数
        int allDay = affairAttendanceFlagService.findAllDay(ye, mo);
        List<AffairAttendanceFlag> flagList = affairAttendanceDao.findAll(affairAttendanceFlag);
		/*String date = year + "-" + month + "-" + "1";
		List<Map<String,String>> tlist = TimeUtils.getDayListOfOneMonth(date);*/
        ArrayList<String> list = new ArrayList<>();
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay1())){
            list.add(affairAttendanceChild.getDay1());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay2())){
            list.add(affairAttendanceChild.getDay2());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay3())){
            list.add(affairAttendanceChild.getDay3());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay4())){
            list.add(affairAttendanceChild.getDay4());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay5())){
            list.add(affairAttendanceChild.getDay5());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay6())){
            list.add(affairAttendanceChild.getDay6());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay7())){
            list.add(affairAttendanceChild.getDay7());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay8())){
            list.add(affairAttendanceChild.getDay8());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay9())){
            list.add(affairAttendanceChild.getDay9());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay10())){
            list.add(affairAttendanceChild.getDay10());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay11())){
            list.add(affairAttendanceChild.getDay11());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay12())){
            list.add(affairAttendanceChild.getDay12());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay13())){
            list.add(affairAttendanceChild.getDay13());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay14())){
            list.add(affairAttendanceChild.getDay14());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay15())){
            list.add(affairAttendanceChild.getDay15());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay16())){
            list.add(affairAttendanceChild.getDay16());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay17())){
            list.add(affairAttendanceChild.getDay17());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay18())){
            list.add(affairAttendanceChild.getDay18());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay19())){
            list.add(affairAttendanceChild.getDay19());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay20())){
            list.add(affairAttendanceChild.getDay20());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay21())){
            list.add(affairAttendanceChild.getDay21());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay22())){
            list.add(affairAttendanceChild.getDay22());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay23())){
            list.add(affairAttendanceChild.getDay23());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay24())){
            list.add(affairAttendanceChild.getDay24());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay25())){
            list.add(affairAttendanceChild.getDay25());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay26())){
            list.add(affairAttendanceChild.getDay26());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay27())){
            list.add(affairAttendanceChild.getDay27());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay28())){
            list.add(affairAttendanceChild.getDay28());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay29())){
            list.add(affairAttendanceChild.getDay29());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay30())){
            list.add(affairAttendanceChild.getDay30());
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay31())){
            list.add(affairAttendanceChild.getDay31());
        }

        /**
         * 需求：公式如下：
         *  缺勤=年+病+探+婚+事+旷+丧+产+哺+学+护+长+公
         *  工伤=公
         *  年休=年
         *  出差=差7
         *  执勤=√+加+差
         *  加班=加
         *  零星加班=半/2
         *
         *  字典值对应如下：
         *  √  ○  年  病  探  婚  加  半  事  旷  丧  产  差  哺  学  护  长  公
         *  0   1   2   3   4   5   6  7   8  9   10  11  12  13  14  15  16  17
         */
        //如果今天是周六周天或者节假日，出差不算天数
        for (int i = 0; i < flagList.size(); i++) {
//			Map<String, String> m = flagList.get(i);
            String week = flagList.get(i).getWeek();
            String date = flagList.get(i).getDate().substring(8,10);
//			weekday = m.get("weekday");
//			day = m.get("date");
            //用工形式为日勤制
            if ("0".equals(affairAttendanceChild.getWorkType())){
                //情况1：当月没有缺勤的值勤天数
                if (-1 == list.indexOf("2") && (-1 == list.indexOf("3") && -1 == list.indexOf("4") && -1 == list.indexOf("5") && -1 == list.indexOf("8") && -1 == list.indexOf("9") && -1 == list.indexOf("10") && -1 == list.indexOf("11") && -1 == list.indexOf("13") && -1 == list.indexOf("14") && -1 == list.indexOf("15") && -1 == list.indexOf("16") && -1 == list.indexOf("17"))){
                    //当月有出差没有缺勤的值勤天数
                    if (-1 != list.indexOf("12") && (-1 == list.indexOf("2") && -1 == list.indexOf("3") && -1 == list.indexOf("4") && -1 == list.indexOf("5") && -1 == list.indexOf("8") && -1 == list.indexOf("9") && -1 == list.indexOf("10") && -1 == list.indexOf("11") && -1 == list.indexOf("13") && -1 == list.indexOf("14") && -1 == list.indexOf("15") && -1 == list.indexOf("16") && -1 == list.indexOf("17"))){

                        sumDay(affairAttendanceChild,week,i,date,ye,mo);
                        if (queQin < allDay && chuChai < allDay){
                            if (jiaBan > 4){
                                jiaBan = 4;
                            }
                            if (lingXingJB > 1.5){
                                lingXingJB = 1.5;
                            }
                            if(22  - (chuChai - jiaBan ) >= 22){
                                zhiQin = 22.0;
                            }else if (22 - (chuChai - jiaBan ) >= 0 && 22 - (chuChai - jiaBan) <22){
                                zhiQin = Double.valueOf(22 - (chuChai - jiaBan));
                            }else {
                                zhiQin = 0.0;
                            }
                        }else {
                            zhiQin = 0.0;
                        }

                    }else {
                        sumDay(affairAttendanceChild,week,i,date,ye,mo);
                        if (queQin < allDay && chuChai < allDay){
                            if (zhiQin >= 22.0){
                                zhiQin = 22.0;
                            }else{
                                zhiQin = Double.valueOf(22);
                            }
                            if (jiaBan > 4){
                                jiaBan = 4;
                            }
                            if (lingXingJB > 1.5){
                                lingXingJB = 1.5;
                            }
                        }else {
                            zhiQin = 0.0;
                        }

                    }
                }
                //当月有缺勤没有出差
                else if ( -1 != list.indexOf("2") || -1 != list.indexOf("3") || -1 != list.indexOf("4") || -1 != list.indexOf("5") || -1 != list.indexOf("8") || -1 != list.indexOf("9") || -1 != list.indexOf("10") || -1 != list.indexOf("11") || -1 != list.indexOf("13") || -1 != list.indexOf("14") || -1 != list.indexOf("15") || -1 != list.indexOf("16") || -1 != list.indexOf("17")){
                    //当月有缺勤有出差
                    if (-1 != list.indexOf("12")){
                        sumDay(affairAttendanceChild,week,i,date,ye,mo);
                        if (queQin < allDay && chuChai < allDay){
                            if (queQin <= 22){
                                if (jiaBan  > chuChai){
                                    if (22 - queQin > 0){
                                        zhiQin = Double.valueOf(22 -queQin);
                                    }else {
                                        zhiQin = realZhiQin;
                                    }
                                    if (jiaBan > 4){
                                        jiaBan = 4;
                                    }
                                    if (lingXingJB > 1.5){
                                        lingXingJB = 1.5;
                                    }
                                }
                                else if (jiaBan <= chuChai) {
                                    if (queQin <= 22) {
                                        if (22 - (chuChai - jiaBan ) - queQin > 0 && 22 - (chuChai - jiaBan ) - queQin < 23) {
                                            zhiQin = Double.valueOf(22 - (chuChai - jiaBan) - queQin);
                                        }
                                        if (jiaBan > 4) {
                                            jiaBan = 4;
                                        }
                                        if (lingXingJB > 1.5) {
                                            lingXingJB = 1.5;
                                        }
                                    } else {
                                        zhiQin = realZhiQin;
                                    }
                                }
                            }else{
                                zhiQin = 0.0;
                            }
                        }else {
                            zhiQin = 0.0;
                        }
                    }
                    //当月有缺勤没有出差
                    else {
                        sumDay(affairAttendanceChild,week,i,date,ye,mo);
                        if (queQin < allDay && chuChai < allDay){
                            if (queQin <= 22){
                                if (22 - queQin >= 0){
                                    zhiQin = Double.valueOf(22 - queQin);
                                }else {
                                    zhiQin = zhiQin;
                                }
                                if (jiaBan > 4){
                                    jiaBan = 4;
                                }
                                if (lingXingJB > 1.5){
                                    lingXingJB = 1.5;
                                }
                            }else {
                                zhiQin = 0.0;
                            }
                        }else {
                            zhiQin = 0.0;
                        }
                    }

                }
				/*//当月有缺勤，有出差
				else if ( list.contains("12") && -1 != list.indexOf("2") || -1 != list.indexOf("3") || -1 != list.indexOf("4") || -1 != list.indexOf("5") || -1 != list.indexOf("8") || -1 != list.indexOf("9") || -1 != list.indexOf("10") || -1 != list.indexOf("11") || -1 != list.indexOf("13") || -1 != list.indexOf("14") || -1 != list.indexOf("15") || -1 != list.indexOf("16") || -1 != list.indexOf("17")){

				}*/
            }
            //用工形式为工时制
            else {
                if (affairAttendanceChild.getMonthHours() != null && !"".equals(affairAttendanceChild.getMonthHours())) {
                    //当月没有缺勤的值勤天数
                    Double hours = Double.valueOf(affairAttendanceChild.getMonthHours());
                    if (-1 == list.indexOf("2") && (-1 == list.indexOf("3") && -1 == list.indexOf("4") && -1 == list.indexOf("5") && -1 == list.indexOf("8") && -1 == list.indexOf("9") && -1 == list.indexOf("10") && -1 == list.indexOf("11") && -1 == list.indexOf("13") && -1 == list.indexOf("14") && -1 == list.indexOf("15") && -1 == list.indexOf("16") && -1 == list.indexOf("17"))) {
                        //当月有出差没有缺勤的值勤天数
                        if (-1 != list.indexOf("12") && (-1 == list.indexOf("2") && -1 == list.indexOf("3") && -1 == list.indexOf("4") && -1 == list.indexOf("5") && -1 == list.indexOf("8") && -1 == list.indexOf("9") && -1 == list.indexOf("10") && -1 == list.indexOf("11") && -1 == list.indexOf("13") && -1 == list.indexOf("14") && -1 == list.indexOf("15") && -1 == list.indexOf("16") && -1 == list.indexOf("17"))) {
                            sumDay(affairAttendanceChild,week,i,date,ye,mo);
                            //超过工时166.6小时代表有加班，满勤22天工时是174小时
                            if (queQin < allDay && chuChai < allDay){
                                if (hours > 166.6) {
                                    Double jiaBanHours = hours - 166.6;
                                    int sumJiaBan = (int) Math.floor((jiaBanHours / 4));
                                    if (sumJiaBan >= 4){
                                        jiaBan = 4;
                                    }else {
                                        jiaBan = sumJiaBan;
                                    }
                                    if (hours >= 174.0){
                                        Double sumZhiQin = 22.0 + Double.valueOf(jiaBan) - chuChai;
                                        if (sumZhiQin >= 22.0){
                                            zhiQin = 22.0;
                                        }else {
                                            zhiQin = sumZhiQin;
                                        }
                                    }else {
                                        Double sumZhiQin = Math.round(hours / 8.0) + Double.valueOf(jiaBan) - chuChai;
                                        if (sumZhiQin >= 22.0){
                                            zhiQin = 22.0;
                                        }else {
                                            zhiQin = sumZhiQin;
                                        }
                                    }
                                }
                                //不到166.6小时说明没有加班
                                else {
                                    Double sumZhiQin = Math.round(hours / 8.0) + Double.valueOf(jiaBan) - chuChai;
                                    if (sumZhiQin >= 22.0){
                                        zhiQin = 22.0;
                                    }else {
                                        zhiQin = sumZhiQin;
                                    }
                                }
                            }else {
                                zhiQin = 0.0;
                            }
                        }
                        //没有缺勤没有出差
                        else {
                            sumDay(affairAttendanceChild,week,i,date,ye,mo);
                            if (queQin < allDay && chuChai < allDay){
                                if (hours > 166.6){
                                    Double sumZhiQin = Double.valueOf(Math.round((hours / 8)));
                                    if (sumZhiQin >= 22.0){
                                        zhiQin = 22.0;
                                    }else {
                                        zhiQin = sumZhiQin;
                                    }
                                    Double jiaBanHours = hours - 166.6;
                                    int sumJiaBan = (int) Math.floor((jiaBanHours / 4));
                                    if (sumJiaBan >= 4){
                                        jiaBan = 4;
                                    }else {
                                        jiaBan = sumJiaBan;
                                    }
                                }else {
                                    Double sumZhiQin = Double.valueOf(Math.round((hours / 8)));
                                    if (sumZhiQin >= 22.0){
                                        zhiQin = 22.0;
                                    }else {
                                        zhiQin = sumZhiQin;
                                    }
                                }
                            }else {
                                zhiQin = 0.0;
                            }

                        }
                    }
                    //当月有缺勤没有出差
                    else if (-1 == list.indexOf("12") && (-1 != list.indexOf("2") || -1 != list.indexOf("3") || -1 != list.indexOf("4") || -1 != list.indexOf("5") || -1 != list.indexOf("8") || -1 != list.indexOf("9") || -1 != list.indexOf("10") || -1 != list.indexOf("11") || -1 != list.indexOf("13") || -1 != list.indexOf("14") || -1 != list.indexOf("15") || -1 != list.indexOf("16") || -1 != list.indexOf("17"))) {
                        sumDay(affairAttendanceChild,week,i,date,ye,mo);
                        if (queQin < allDay && chuChai < allDay){
                            if(queQin <= 22){
                                if (hours >= 166.6){
                                    Double jiaBanHours = hours - 166.6;
                                    int sumJiaBan = (int) Math.floor((jiaBanHours / 4));
                                    if (sumJiaBan >= 4){
                                        jiaBan = 4;
                                    }else {
                                        jiaBan = sumJiaBan;
                                    }
                                    zhiQin = 22.0 - queQin;
                                }else {
                                    zhiQin = 22.0 - queQin;
                                }

                            }else {
                                zhiQin = 0.0;
                            }
                        }else {
                            zhiQin = 0.0;
                        }

                    }
                    //当月有缺勤，有出差
                    else if (-1 != list.indexOf("2") || -1 != list.indexOf("3") || -1 != list.indexOf("4") || -1 != list.indexOf("5") || -1 != list.indexOf("8") || -1 != list.indexOf("9") || -1 != list.indexOf("10") || -1 != list.indexOf("11") || -1 != list.indexOf("13") || -1 != list.indexOf("14") || -1 != list.indexOf("15") || -1 != list.indexOf("16") || -1 != list.indexOf("17") || -1 != list.indexOf("12")) {
                        sumDay(affairAttendanceChild,week,i,date,ye,mo);
                        if (queQin < allDay && chuChai < allDay){
                            if (queQin <= 22){
                                if (hours > 166.6){
                                    Double jiaBanHours = hours - 166.6;
                                    int sumJiaBan = (int) Math.floor((jiaBanHours / 4));
                                    if (sumJiaBan >= 4){
                                        jiaBan = 4;
                                    }else {
                                        jiaBan = sumJiaBan;
                                    }
                                    if (jiaBan > chuChai){
                                        zhiQin = 22.0 - queQin;
                                    }else {
                                        if (hours >= 174.0){
                                            Double sumZhiQin = 22 + Double.valueOf(jiaBan) - queQin - chuChai;
                                            if (sumZhiQin >= 22.0){
                                                zhiQin = 22.0;
                                            }else {
                                                zhiQin = sumZhiQin;
                                            }
                                        }else {
                                            Double sumZhiQin = Double.valueOf(Math.round((hours / 8))) + Double.valueOf(jiaBan) - queQin - chuChai;
                                            if (sumZhiQin >= 22.0){
                                                zhiQin = 22.0;
                                            }else {
                                                zhiQin = sumZhiQin;
                                            }
                                        }
                                    }
                                }
                                //没有加班
                                else {
                                    Double sumZhiQin = Double.valueOf(Math.round((hours / 8)))  - queQin - chuChai;
                                    if (sumZhiQin >= 22.0){
                                        zhiQin = 22.0;
                                    }else {
                                        zhiQin = sumZhiQin;
                                    }
                                }
                            }else {
                                zhiQin = 0.0;
                            }
                        }else {
                            zhiQin = 0.0;
                        }
                    }
                }
            }
        }
        //
        affairAttendanceChild.setLackWork(queQin);
        affairAttendanceChild.setWorkInjury(gongShang.toString());
        affairAttendanceChild.setYearWeak(nianXiu.toString());
        affairAttendanceChild.setGoOut(chuChai.toString());
        affairAttendanceChild.setInWork(zhiQin.toString());
        affairAttendanceChild.setOvertime(jiaBan.toString());
        affairAttendanceChild.setOvertimeChip(lingXingJB.toString());
        affairAttendanceChild.setKuang(kuang);
    }

    //计算各种天数
    private void sumDay(AffairAttendanceChild affairAttendanceChild,String weekDay,int i,String day,Integer year, Integer month) {
        //获得到所有的节假日
        Calendar calendar = Calendar.getInstance();
        AffairAttendanceFlag affairAttendanceFlag = new AffairAttendanceFlag();
        affairAttendanceFlag.setYear(year);
        affairAttendanceFlag.setMonth(month);
        List<AffairAttendanceFlag> flagList = affairAttendanceDao.findAll(affairAttendanceFlag);
		/*List <String> holiday = new ArrayList<>();
		for (AffairAttendanceFlag a : flagList){
			holiday.add(a.getDate());
		}*/
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay1()) && i == 0){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay1());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay1());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay1());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay1());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay2()) && i == 1){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay2());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay2());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay2());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay2());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay3()) && i == 2){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay3());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay3());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay3());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay3());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay4()) && i == 3){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay4());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay4());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay4());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay4());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay5()) && i == 4){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay5());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay5());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay5());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay5());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay6()) && i == 5){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay6());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay6());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay6());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay6());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay7()) && i == 6){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay7());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay7());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay7());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay7());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay8()) && i == 7){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay8());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay8());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay8());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay8());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay9()) && i == 8){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay9());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay9());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay9());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay9());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay10()) && i == 9){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay10());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay10());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay10());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay10());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay11()) && i == 10){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay11());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay11());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay11());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay11());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay12()) && i == 11){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay12());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay12());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay12());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay12());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay13()) && i == 12){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay13());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay13());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay13());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay13());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay14()) && i == 13){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay14());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay14());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay14());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay14());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay15()) && i == 14){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay15());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay15());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay15());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay15());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay16()) && i == 15){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay16());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay16());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay16());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay16());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay17()) && i == 16){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay17());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay17());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay17());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay17());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay18()) && i == 17){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay18());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay18());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay18());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay18());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay19()) && i == 18){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay19());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay19());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay19());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay19());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay20()) && i == 19){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay20());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay20());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay20());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay20());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay21()) && i == 20){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay21());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay21());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay21());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay21());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay22()) && i == 21){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay22());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay22());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay22());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay22());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay23()) && i == 22){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay23());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay23());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay23());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay23());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay24()) && i == 23){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay24());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay24());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay24());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay24());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay25()) && i == 24){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay25());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay25());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay25());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay25());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay26()) && i == 25){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay26());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay26());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay26());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay26());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay27()) && i == 26){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay27());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay27());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay27());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay27());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay28()) && i == 27){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay28());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay28());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay28());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay28());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay29()) && i == 28){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay29());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay29());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay29());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay29());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay30()) && i == 29){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay30());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay30());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay30());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay30());
            }
        }
        if(StringUtils.isNotBlank(affairAttendanceChild.getDay31()) && i == 30){
            if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
                zhouMo(affairAttendanceChild.getDay31());
            }else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
                zhouMoG(affairAttendanceChild.getDay31());
            }
            else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
                normolDay(affairAttendanceChild.getDay31());
            }else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
                normolDayX(affairAttendanceChild.getDay31());
            }
        }
    }


    //周一到周五条件(工)
    private void normolDay(String str) {
        switch (str) {
            case "0":
                realZhiQin += 1;
                break;
            case "2":
                queQin += 1;
                nianXiu += 1;
                break;
            case "3":
                queQin += 1;
                break;
            case "4":
                queQin += 1;
                break;
            case "5":
                queQin += 1;
                break;
            case "6":
                break;
            case "7":
                break;
            case "8":
                queQin += 1;
                break;
            case "9":
                queQin += 1;
                kuang += 1;
                break;
            case "10":
                queQin += 1;
                break;
            case "11":
                queQin += 1;
                break;
            case "12":
                chuChai += 1;
                break;
            case "13":
                queQin += 1;
                break;
            case "14":
                queQin += 1;
                break;
            case "15":
                queQin += 1;
                break;
            case "16":
                queQin += 1;
                break;
            case "17":
                queQin += 1;
                gongShang += 1;
                break;
            default:
                break;
        }
    }

    //周一到周五（休）
    public void normolDayX(String str) {
        switch (str) {
            case "0":
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                break;
            case "6":
                jiaBan += 1;
                break;
            case "7":
                lingXingJB += 0.5;
                break;
            case "8":
                break;
            case "9":
                break;
            case "10":
                break;
            case "11":
                break;
            case "12":
                break;
            case "13":
                break;
            case "14":
                break;
            case "15":
                break;
            case "16":
                break;
            case "17":
                break;
            default:
                break;
        }
    }

    //周六周日条件（工）
    public void zhouMoG(String str) {
        switch (str) {
            case "0":
                realZhiQin += 1;
                break;
            case "2":
                queQin += 1;
                nianXiu += 1;
                break;
            case "3":
                queQin += 1;
                break;
            case "4":
                queQin += 1;
                break;
            case "5":
                queQin += 1;
                break;
            case "6":
                break;
            case "7":
                break;
            case "8":
                queQin += 1;
                break;
            case "9":
                queQin += 1;
                kuang += 1;
                break;
            case "10":
                queQin += 1;
                break;
            case "11":
                queQin += 1;
                break;
            case "12":
                chuChai += 1;
                break;
            case "13":
                queQin += 1;
                break;
            case "14":
                queQin += 1;
                break;
            case "15":
                queQin += 1;
                break;
            case "16":
                queQin += 1;
                break;
            case "17":
                queQin += 1;
                gongShang += 1;
                break;
            default:
                break;
        }
    }

    //周六周日条件（休）
    public void zhouMo(String str) {
        switch (str) {
            case "0":
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                break;
            case "6":
                jiaBan += 1;
                break;
            case "7":
                lingXingJB += 0.5;
                break;
            case "8":
                break;
            case "9":
                break;
            case "10":
                break;
            case "11":
                break;
            case "12":
                break;
            case "13":
                break;
            case "14":
                break;
            case "15":
                break;
            case "16":
                break;
            case "17":
                break;
            default:
                break;
        }
    }
}
