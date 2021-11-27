/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExcelTemplate;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.affair.entity.AffairActivityMien;
import com.thinkgem.jeesite.modules.affair.entity.AffairTjRegister;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.ss.usermodel.DateUtil;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairYjGoOutReport;
import com.thinkgem.jeesite.modules.affair.service.AffairYjGoOutReportService;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 民警因私外出报备Controller
 *
 * @author kevin.jia
 * @version 2020-11-10
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairYjGoOutReport")
public class AffairYjGoOutReportController extends BaseController {

    @Autowired
    private AffairYjGoOutReportService affairYjGoOutReportService;

    @Autowired
    private DictDao dictDao;

    @ModelAttribute
    public AffairYjGoOutReport get(@RequestParam(required = false) String id) {
        AffairYjGoOutReport entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = affairYjGoOutReportService.get(id);
        }
        if (entity == null) {
            entity = new AffairYjGoOutReport();
        }
        return entity;
    }

    @RequiresPermissions("affair:affairYjGoOutReport:view")
    @RequestMapping(value = {"list", ""})
    public String list(AffairYjGoOutReport affairYjGoOutReport, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AffairYjGoOutReport> page = affairYjGoOutReportService.findPage(new Page<AffairYjGoOutReport>(request, response), affairYjGoOutReport);
        model.addAttribute("page", page);
        return "modules/affair/affairYjGoOutReportList";
    }

    @RequiresPermissions("affair:affairYjGoOutReport:view")
    @RequestMapping(value = "form")
    public String form(AffairYjGoOutReport affairYjGoOutReport, Model model) {
        model.addAttribute("affairYjGoOutReport", affairYjGoOutReport);
        return "modules/affair/affairYjGoOutReportForm";
    }

    @RequiresPermissions("affair:affairYjGoOutReport:edit")
    @RequestMapping(value = "save")
    public String save(AffairYjGoOutReport affairYjGoOutReport, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, affairYjGoOutReport)) {
            return form(affairYjGoOutReport, model);
        }
        affairYjGoOutReportService.save(affairYjGoOutReport);
        addMessage(redirectAttributes, "保存民警因私外出报备成功");
        model.addAttribute("saveResult", "success");
        return "modules/affair/affairYjGoOutReportForm";
    }

    @RequiresPermissions("affair:affairYjGoOutReport:edit")
    @RequestMapping(value = "delete")
    public String delete(AffairYjGoOutReport affairYjGoOutReport, RedirectAttributes redirectAttributes) {
        affairYjGoOutReportService.delete(affairYjGoOutReport);
        addMessage(redirectAttributes, "删除民警因私外出报备成功");
        return "redirect:" + Global.getAdminPath() + "/affair/affairYjGoOutReport/?repage";
    }

    @RequiresPermissions("affair:affairYjGoOutReport:edit")
    @RequestMapping(value = {"deleteByIds"})
    @ResponseBody
    public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
        Result result = new Result();
        if (ids != null && ids.size() > 0) {
            affairYjGoOutReportService.deleteByIds(ids);
            result.setSuccess(true);
            result.setMessage("删除成功");
        } else {
            result.setSuccess(false);
            result.setMessage("请先选择要删除的内容");
        }
        return result;
    }

    @RequestMapping(value = {"plshByIds"})
    @ResponseBody
    public Result plshByIds(@RequestParam("ids[]") List<String> ids) {
        Result result = new Result();
        if (ids != null && ids.size() > 0) {
            affairYjGoOutReportService.plshByIds(ids);
            result.setSuccess(true);
            result.setMessage("审核通过");
        } else {
            result.setSuccess(false);
            result.setMessage("请先选择要审核的内容");
        }
        return result;
    }
    @RequestMapping(value = {"plshthByIds"})
    @ResponseBody
    public Result plshthByIds(@RequestParam("ids[]") List<String> ids) {
        Result result = new Result();
        if (ids != null && ids.size() > 0) {
            affairYjGoOutReportService.plshthByIds(ids);
            result.setSuccess(true);
            result.setMessage("退回整改");
        } else {
            result.setSuccess(false);
            result.setMessage("请先选择要审核的内容");
        }
        return result;
    }


    /**
     * 批量申报
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"submitByIds"})
    public String submitByIds(HttpServletRequest request, RedirectAttributes redirectAttributes) {

        String officeId = UserUtils.getUser().getOffice().getId();
        String compId = UserUtils.getUser().getCompany().getId();

        String checkId = "";
        String checkName = "";

        if (officeId.equals("34") || compId.equals("34") && officeId != "27"){
            checkId = "34e8d855cf6b4b1ab5e7e23e7aaba658";
            checkName = "南宁处政治处组织干部室";
        }
        if (officeId.equals("156") || compId.equals("156") && officeId != "264"){
            checkId = "c90918faf2614baa8fa85230482bd43e";
            checkName = "北海处政治处组织干部室";
        }
        if (officeId.equals("95") || compId.equals("95") && officeId != "142"){
            checkId = "ec3ba2efdd404f2faa520f6e8a71ec4c";
            checkName = "柳州处政治处组织干部室";
        }
        if ((officeId.equals("1") || compId.equals("1")  || officeId.equals("27") || officeId .equals("142") || officeId.equals("264")) && officeId!="95" && officeId!="156" && officeId!="34"){
            checkId = "bfdf74f010c9466dba12c1589ecab7f3";
            checkName = "南宁局政治部组织干部处";
        }

        boolean b = affairYjGoOutReportService.submitByIds(request,checkId,checkName);
         if (b) {
            addMessage(redirectAttributes, "申报成功");
        } else {
            addMessage(redirectAttributes, "申报失败，发生错误");
        }
        return "redirect:" + Global.getAdminPath() + "/affair/affairYjGoOutReport/list";
    }


    @RequiresPermissions("affair:affairYjGoOutReport:view")
    @RequestMapping(value = "formDetail")
    public String formDetail(AffairYjGoOutReport affairYjGoOutReport, Model model) {
        model.addAttribute("affairYjGoOutReport", affairYjGoOutReport);
        if ("1".equals(affairYjGoOutReport.getPersonType())) {
            return "modules/affair/affairYjGoOutReportFormDetail";
        } else {
            model.addAttribute("year", DateUtils.getYear());
            model.addAttribute("month", DateUtils.getMonth());
            model.addAttribute("date", DateUtils.getDay());
            return "modules/affair/affairYjGoOutReportFormDetailLd";
        }
    }

    @RequestMapping(value = "export")
    public String exportPoliceTrainingFile(AffairYjGoOutReport affairYjGoOutReport, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        String fileName = "";
        if (affairYjGoOutReport.getPersonType() != null && "2".equals(affairYjGoOutReport.getPersonType())) {
            fileName = "南宁铁路公安局领导干部外出报备单-导出.xlsx";
        } else {
            fileName = "南宁铁路公安局民警因私外出报备单-导出.xlsx";
        }
        try {
            //获取模板
            String fileSeperator = File.separator;
            String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;
            //初始化工具
            ExcelTemplate excel = new ExcelTemplate(filePath + fileName);
            Map<String, String> map = new HashMap<>();
            map.put("unit", affairYjGoOutReport.getUnit());
            map.put("name", affairYjGoOutReport.getName());
            map.put("leaveTime", DateUtils.formatDate(affairYjGoOutReport.getLeaveTime(), "yyyy-MM-dd HH:mm"));
            map.put("backTime", DateUtils.formatDate(affairYjGoOutReport.getBackTime(), "yyyy-MM-dd HH:mm"));
            map.put("goPlace", affairYjGoOutReport.getGoPlace());//外出地点
            map.put("thing", affairYjGoOutReport.getThing());//事由
            if ("2".equals(affairYjGoOutReport.getPersonType())) {
                map.put("fgldOpinion", affairYjGoOutReport.getFgldOpinion());//分管领导意见
                map.put("mainOpinion", affairYjGoOutReport.getMainldOpinion());//主管领导意见
            } else {
                map.put("bmzzldOpinion", affairYjGoOutReport.getBmzzldOpinion());//部门正职领导意见
            }
            try {
                //写入模板
                int count = excel.fillVariable(0, map);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.toString());
                addMessage(redirectAttributes, "联系系统管理员导入模板");
                redirectAttributes.addAttribute("workunitId", affairYjGoOutReport.getUnitId());
                redirectAttributes.addAttribute("workunitName", affairYjGoOutReport.getUnit());
                return "redirect:" + adminPath + "/affair/affairYjGoOutReport/list";
            }
            //下载
            try {
                // 清除buffer缓存
                response.reset();
                response.setContentType("application/octet-stream; charset=utf-8");
                //指定下载名字
                response.setHeader("Content-Disposition", "attachment; filename=" +
                        Encodes.urlEncode(affairYjGoOutReport.getName() + fileName.replace("-导出", "")));
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
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            addMessage(redirectAttributes, "发生错误，导出失败，请重试或联系统管理员");
            redirectAttributes.addAttribute("workunitId", affairYjGoOutReport.getUnit());
            redirectAttributes.addAttribute("workunitName", affairYjGoOutReport.getUnitId());
            return "redirect:" + adminPath + "/affair/affairYjGoOutReport/list";
        }
    }
    /*
     * 审核页面
     * */
   // @RequiresPermissions("affair:affairYjGoOutReport:shenhe")
    @RequestMapping(value = {"shenHeDialog"})
    public String shenHeDialog() {
        return "modules/affair/affairYjGoOutReportCheckDialog";
    }


    @RequestMapping(value = "shenHe")
    @ResponseBody
    public Result shenHe(AffairYjGoOutReport affairYjGoOutReport, HttpServletRequest request) {
        if (!affairYjGoOutReport.getJuCheckId().isEmpty()) {
            //如果审核方式为转为上一级,则通过juCheckId,为juCheckMan字段赋值
            String juCheckMan = dictDao.findLabelByValue(affairYjGoOutReport.getJuCheckId());
            affairYjGoOutReport.setJuCheckMan(juCheckMan);
        }
        affairYjGoOutReportService.save(affairYjGoOutReport);
        Result result = new Result();
        result.setSuccess(true);
        result.setMessage("审核成功");
        return result;
    }

    /*
    * 统计分析页面-民警因私外出点击详情弹窗
    * */
    @RequiresPermissions("affair:affairYjGoOutReport:view")
    @RequestMapping(value = "tjfxFormDetail")
    public String tjfxFormDetail(AffairYjGoOutReport affairYjGoOutReport,Model model,HttpServletRequest request, HttpServletResponse response) {

         String size = affairYjGoOutReport.getSize();
         Double newSize = null;
         Double lastSize = null;
         if ("0-5天".equals(size)){
             lastSize = 0.0;
             newSize = 5.0;
         }
         if ("6-10天".equals(size)){
             lastSize = 6.0;
             newSize = 10.0;
         }
         if ("11-15天".equals(size)){
             lastSize = 11.0;
             newSize = 15.0;
         }
         if ("16-30天".equals(size)){
             lastSize = 16.0;
             newSize = 30.0;
         }
         if ("30天以上".equals(size)){
             newSize = 31.0;
         }
         /*List<AffairYjGoOutReport> affairYjGoOutReportList = affairYjGoOutReportService.selectBeanDetails(lastSize,newSize);*/
        affairYjGoOutReport.setLastSize(lastSize);
        affairYjGoOutReport.setNewSize(newSize);
        Page<AffairYjGoOutReport> page = affairYjGoOutReportService.selectBeanDetails(new Page<AffairYjGoOutReport>(request, response), affairYjGoOutReport);

        model.addAttribute("page",page);
         return "modules/affair/affairTjfxYswcDetailsForm";

    }

    /**
     * 撤销审核操作
     * @param affairYjGoOutReport
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "revocation")
    public String revocation(AffairYjGoOutReport affairYjGoOutReport, RedirectAttributes redirectAttributes) {
        affairYjGoOutReportService.revocation(affairYjGoOutReport);
        addMessage(redirectAttributes, "撤销民警因私外出报备成功");
        return "redirect:" + Global.getAdminPath() + "/affair/affairYjGoOutReport";
    }

}