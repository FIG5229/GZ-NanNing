/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.dao.AffairIdeologyDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairIdeology;
import com.thinkgem.jeesite.modules.affair.service.AffairIdeologyService;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 意识形态Controller
 * 数字相同的是同一个页面，同一页面根据权限不同显示上报或者审核
 *  局账号（审核2、3页面  查看1页面）
 *      基层党支部1   公安处党委2   公安局党委3
 *  处账号（1页面审核  2页面上报）
 *      基层党支部1   公安处党委2
 *  处下基层账号（1页面上报）
 *      基层党支部1
 *  局机关账号（3页面上报）
 *      机关党支部3
 *
 *      由于保密条例 此功能不在网站上显示
 * @author daniel.liu
 * @version 2020-06-22
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairIdeology")
public class AffairIdeologyController extends BaseController {

    @Autowired
    private AffairIdeologyService affairIdeologyService;

    @Autowired
    private UploadController uploadController;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private AffairIdeologyDao affairIdeologyDao;

    @ModelAttribute
    public AffairIdeology get(@RequestParam(required = false) String id) {
        AffairIdeology entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = affairIdeologyService.get(id);
        }
        if (entity == null) {
            entity = new AffairIdeology();
        }
        return entity;
    }

    @RequiresPermissions("affair:affairIdeology:view")
    @RequestMapping(value = {""})
    public String index(AffairIdeology affairIdeology, HttpServletRequest request, HttpServletResponse response, Model model) {
        String userId = UserUtils.getUser().getId();
        //id依次为南宁局宣传教育管理  南宁局政治部宣教  北海处宣教思想管理  柳州处宣教思想管理   南宁出宣教思想管理
        if (userId.equals("66937439b2124f328d1521968fab06db") || userId.equals("8a6819768aef40968e8f289842613276") ||
        userId.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || userId.equals("d30e324c8f73492d9b74103374fbc689") || userId.equals("d154234ecb35470e84fb95e53726866b")) {
            //9.2问题反馈 未上报可查看 不能签收
            affairIdeology.setHasAuth(true);
            if (StringUtils.isBlank(affairIdeology.getReportType())){
                affairIdeology.setReportType("1");
            }
            if (affairIdeology.getYear()==null){
                Calendar date = Calendar.getInstance();
                affairIdeology.setYear(date.get(Calendar.YEAR));
            }
            Page<AffairIdeology> page = affairIdeologyService.findPage(new Page<AffairIdeology>(request, response,-1), affairIdeology);
            String companyId = UserUtils.getUser().getCompany().getId();

            //不能签收自己上报的数据
           /* for (int i=0;i<page.getList().size();i++) {
                String userId=UserUtils.getUser().getId();
                String createBy =page.getList().get(i).getCreateBy().getId();
                if (StringUtils.isNotBlank(createBy) && createBy.equals(userId)){
                    page.getList().remove(i);
                }
            }*/
            model.addAttribute("userId", userId);
            model.addAttribute("page", page);
            return "modules/affair/affairIdeologyManage";
        } else {
            Page<AffairIdeology> page = affairIdeologyService.findPage(new Page<AffairIdeology>(request, response,-1), affairIdeology);
            model.addAttribute("page", page);
            model.addAttribute("userId", userId);
            return "modules/affair/affairIdeologyList";
        }
//		Page<AffairIdeology> page = affairIdeologyService.findPage(new Page<AffairIdeology>(request, response), affairIdeology);
//		model.addAttribute("page", page);
//		return "modules/affair/affairIdeologyList";
    }

    //普通账号进行添加 上报
    @RequiresPermissions("affair:affairIdeology:view")
    @RequestMapping(value = {"list"})
    public String list(AffairIdeology affairIdeology, HttpServletRequest request, HttpServletResponse response, Model model) {
        String id = UserUtils.getUser().getId();

        String idN = UserUtils.getUser().getId();
        if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
            affairIdeology.setClassify("3");
        }else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
            affairIdeology.setClassify("2");
        }else{
            affairIdeology.setClassify("1");
        }
        Page<AffairIdeology> page = affairIdeologyService.findPage(new Page<AffairIdeology>(request, response,-1), affairIdeology);
        model.addAttribute("page", page);
        model.addAttribute("userId", id);
        return "modules/affair/affairIdeologyList";
    }

    @RequestMapping(value = {"juList"})
    public String juList(AffairIdeology affairIdeology, HttpServletRequest request, HttpServletResponse response, Model model) {
        String userId = UserUtils.getUser().getId();
        if (userId.equals("66937439b2124f328d1521968fab06db") || userId.equals("8a6819768aef40968e8f289842613276") ||
                userId.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || userId.equals("d30e324c8f73492d9b74103374fbc689") || userId.equals("d154234ecb35470e84fb95e53726866b")) {
            //9.2问题反馈 未上报可查看 不能签收
            affairIdeology.setHasAuth(true);
            if (StringUtils.isBlank(affairIdeology.getReportType())){
                affairIdeology.setReportType("1");
            }
            if (affairIdeology.getYear()==null){
                Calendar date = Calendar.getInstance();
                affairIdeology.setYear(date.get(Calendar.YEAR));
            }
            String idN = UserUtils.getUser().getId();
            if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
                affairIdeology.setClassify("3");
            }else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
                affairIdeology.setClassify("2");
            }else{
                affairIdeology.setClassify("1");
            }
            Page<AffairIdeology> page = affairIdeologyService.juPage(new Page<AffairIdeology>(request, response,-1), affairIdeology);
            String companyId = UserUtils.getUser().getCompany().getId();

            //不能签收自己上报的数据
           /* for (int i=0;i<page.getList().size();i++) {
                String userId=UserUtils.getUser().getId();
                String createBy =page.getList().get(i).getCreateBy().getId();
                if (StringUtils.isNotBlank(createBy) && createBy.equals(userId)){
                    page.getList().remove(i);
                }
            }*/
            model.addAttribute("userId", userId);
            model.addAttribute("page", page);
            return "modules/affair/affairIdeologyJuManage";
        } else {
            Page<AffairIdeology> page = affairIdeologyService.juPage(new Page<AffairIdeology>(request, response,-1), affairIdeology);
            model.addAttribute("page", page);
            model.addAttribute("userId", userId);
            return "modules/affair/affairIdeologyJuList";
        }
    }

    //宣教账号进行查看 签收 退回
    @RequiresPermissions("affair:affairIdeology:view")
    @RequestMapping(value = {"manage"})
    public String manage(AffairIdeology affairIdeology, HttpServletRequest request, HttpServletResponse response, Model model) {
        String userId = UserUtils.getUser().getId();
        /*if (StringUtils.isBlank(affairIdeology.getReportType())){
            affairIdeology.setReportType("1");
        }
        if (affairIdeology.getYear()==null){
            Calendar date = Calendar.getInstance();
            affairIdeology.setYear(date.get(Calendar.YEAR));
        }*/
        String flag = request.getParameter("flag");
        if ("1".equals(flag)){
            if (StringUtils.isBlank(affairIdeology.getReportType())){
                affairIdeology.setReportType("1");
            }
            if (affairIdeology.getYear()==null){
                Calendar date = Calendar.getInstance();
                affairIdeology.setYear(date.get(Calendar.YEAR));
            }
//        affairIdeology.setReportType("1");
            //9.2问题反馈 未上报可查看 不能签收
            affairIdeology.setHasAuth(true);
            String idN = UserUtils.getUser().getId();
            if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
                affairIdeology.setClassify("3");
            }else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
                affairIdeology.setClassify("2");
            }else{
                affairIdeology.setClassify("1");
            }
            Page<AffairIdeology> page = affairIdeologyService.juPage(new Page<AffairIdeology>(request, response,-1), affairIdeology);
            String companyId = UserUtils.getUser().getCompany().getId();
      /*  if (companyId.equals("1")){
            String[] ids = new String[]{"29","144","265"};
            Map<String,String> map = new HashMap<>();
            map.put("29","南宁处政治处宣传教育室（民警训练基地）");
            map.put("144","柳州处政治处宣传教育室（民警训练基地）");
            map.put("265","北海处政治处宣传教育室（民警训练基地）");

            for (String id : ids) {
                boolean has = false;
                List<AffairIdeology> list = page.getList();
                for(int i =0;i<list.size();i++){
                    if (list.get(i).getUnitId().equals(id)){
                        has=true;
                    }
                }
                if (!has){
                    AffairIdeology ideology = new AffairIdeology();
                    ideology.setUnitId(id);
                    ideology.setUnit(map.get(id));
                    ideology.setReportStatus("1");
                    ideology.setReportTime(new Date());
                    ideology.setReportType(affairIdeology.getReportType());
                    ideology.setCreateOrgId(id);
                    affairIdeologyService.save(ideology);
                    list.add(ideology);
                }
            }
        }*/
            //不能签收自己上报的数据 页面中实现
/*        for (int i=0;i<page.getList().size();i++) {
            String userId=UserUtils.getUser().getId();
            String createBy = page.getList().get(i).getCreateBy().getId();
            if (createBy!=null && createBy.equals(userId)){
                page.getList().remove(i);
            }
        }*/
            model.addAttribute("page", page);
            model.addAttribute("userId", userId);
            return "modules/affair/affairIdeologyJuManage";
        }else {
            if (StringUtils.isBlank(affairIdeology.getReportType())){
                affairIdeology.setReportType("1");
            }
            if (affairIdeology.getYear()==null){
                Calendar date = Calendar.getInstance();
                affairIdeology.setYear(date.get(Calendar.YEAR));
            }
//        affairIdeology.setReportType("1");
            //9.2问题反馈 未上报可查看 不能签收
            affairIdeology.setHasAuth(true);
            String idN = UserUtils.getUser().getId();
            if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
                affairIdeology.setClassify("3");
            }else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
                affairIdeology.setClassify("2");
            }else{
                affairIdeology.setClassify("1");
            }
            Page<AffairIdeology> page = affairIdeologyService.findPage(new Page<AffairIdeology>(request, response,-1), affairIdeology);
            String companyId = UserUtils.getUser().getCompany().getId();
      /*  if (companyId.equals("1")){
            String[] ids = new String[]{"29","144","265"};
            Map<String,String> map = new HashMap<>();
            map.put("29","南宁处政治处宣传教育室（民警训练基地）");
            map.put("144","柳州处政治处宣传教育室（民警训练基地）");
            map.put("265","北海处政治处宣传教育室（民警训练基地）");

            for (String id : ids) {
                boolean has = false;
                List<AffairIdeology> list = page.getList();
                for(int i =0;i<list.size();i++){
                    if (list.get(i).getUnitId().equals(id)){
                        has=true;
                    }
                }
                if (!has){
                    AffairIdeology ideology = new AffairIdeology();
                    ideology.setUnitId(id);
                    ideology.setUnit(map.get(id));
                    ideology.setReportStatus("1");
                    ideology.setReportTime(new Date());
                    ideology.setReportType(affairIdeology.getReportType());
                    ideology.setCreateOrgId(id);
                    affairIdeologyService.save(ideology);
                    list.add(ideology);
                }
            }
        }*/
            //不能签收自己上报的数据 页面中实现
/*        for (int i=0;i<page.getList().size();i++) {
            String userId=UserUtils.getUser().getId();
            String createBy = page.getList().get(i).getCreateBy().getId();
            if (createBy!=null && createBy.equals(userId)){
                page.getList().remove(i);
            }
        }*/
            model.addAttribute("page", page);
            model.addAttribute("userId", userId);
            return "modules/affair/affairIdeologyManage";
        }


    }

    @RequiresPermissions("affair:affairIdeology:view")
    @RequestMapping(value = "form")
    public String form(AffairIdeology affairIdeology, Model model) {
        model.addAttribute("affairIdeology", affairIdeology);
        return "modules/affair/affairIdeologyForm";
    }

    @RequiresPermissions("affair:affairIdeology:view")
    @RequestMapping(value = "formDetail")
    public String formDetail(AffairIdeology affairIdeology, Model model) {
        model.addAttribute("affairIdeology", affairIdeology);
        if (affairIdeology.getFilePath() != null && affairIdeology.getFilePath().length() > 0) {
            List<Map<String, String>> filePathList = uploadController.filePathHandle(affairIdeology.getFilePath());
            model.addAttribute("filePathList", filePathList);
        }
        return "modules/affair/affairIdeologyFormDetail";
    }

    @RequiresPermissions("affair:affairIdeology:edit")
    @RequestMapping(value = "save")
    public String save(AffairIdeology affairIdeology, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, affairIdeology)) {
            return form(affairIdeology, model);
        }
        //首次添加时 状态设置为未提交
        if (StringUtils.isEmpty(affairIdeology.getReportStatus())){
            affairIdeology.setReportStatus("1");
        }

        String idN = UserUtils.getUser().getId();


        String id = UserUtils.getUser().getOffice().getId();
        String companyId = UserUtils.getUser().getCompany().getId();
        if (companyId.equals("1") && !id.equals("34") && !id.equals("95") && !id.equals("156")){
            affairIdeology.setClassify("3");
        }
       /* if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
            affairIdeology.setClassify("3");
        }*/else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
            affairIdeology.setClassify("2");
        }else{
            affairIdeology.setClassify("1");
        }
        affairIdeologyService.save(affairIdeology);
        addMessage(redirectAttributes, "保存意识形态成功");
        request.setAttribute("saveResult", "success");
        return "modules/affair/affairIdeologyForm";
    }

    @RequiresPermissions("affair:affairIdeology:edit")
    @RequestMapping(value = "delete")
    public String delete(AffairIdeology affairIdeology, RedirectAttributes redirectAttributes) {
        affairIdeologyService.delete(affairIdeology);
        addMessage(redirectAttributes, "删除意识形态成功");
        return "redirect:" + Global.getAdminPath() + "/affair/affairIdeology/?repage";
    }
    @ResponseBody
    @RequiresPermissions("affair:affairIdeology:edit")
    @RequestMapping(value = {"deleteByIds"})
    public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
        Result result = new Result();
        if(ids != null && ids.size() > 0){
            affairIdeologyService.deleteByIds(ids);
            result.setSuccess(true);
            result.setMessage("删除成功");
        }else{
            result.setSuccess(false);
            result.setMessage("请先选择要删除的内容");
        }
        return result;
    }

    /*上报*/
    @RequiresPermissions("affair:affairIdeology:edit")
    @RequestMapping(value = "report")
    public String report(AffairIdeology affairIdeology, RedirectAttributes redirectAttributes) {
        affairIdeology.setReportStatus("2");
        affairIdeology.setReportTime(new Date());
        affairIdeologyService.save(affairIdeology);
        addMessage(redirectAttributes, "上报意识形态成功");
        return "redirect:" + Global.getAdminPath() + "/affair/affairIdeology/list?repage";
    }

    /*签收*/
    @RequiresPermissions("affair:affairIdeology:edit")
    @RequestMapping(value = "sign")
    public String sign(AffairIdeology affairIdeology, RedirectAttributes redirectAttributes) {
        //只有签收时 才添加签收时间
        if ("3".equals(affairIdeology.getReportStatus())){
            affairIdeology.setSignInTime(new Date());
        }
        affairIdeologyService.save(affairIdeology);
//		model.addAttribute("analysis", analysis);
        addMessage(redirectAttributes, "签收意识形态成功");
        return "redirect:" + Global.getAdminPath() + "/affair/affairIdeology/manage?repage";
    }

    /**
     * 导出excel格式数据
     * @param analysis
     * @param request
     * @param response
     * @param redirectAttributes
     * @param flag
     * @return
     */
    @RequestMapping(value = "export", method= RequestMethod.POST)
    public String exportExcelByTemplate(AffairIdeology analysis, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag,boolean hasAuth) {
        XSSFWorkbook wb = null;
        try
        {
            String fileName = "";
            if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
                fileName= request.getParameter("fileName").toString();
            }

            Page<AffairIdeology> page = null;
            //如果是签收页面导出则 手动修改导出时查询数据的 数据过滤范围
            analysis.setHasAuth(hasAuth);
            if(flag == true){
                page = affairIdeologyService.findPage(new Page<AffairIdeology>(request, response), analysis);
            }else{
                page = affairIdeologyService.findPage(new Page<AffairIdeology>(request, response,-1), analysis);
            }
       /*     for (int i=0;i<page.getList().size();i++) {
                String userId=UserUtils.getUser().getId();
                String createBy = page.getList().get(i).getCreateBy().getId();
                if (createBy!=null && createBy.equals(userId)){
                    page.getList().remove(i);
                }
            }*/
            String fileSeperator = File.separator;
			String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
            InputStream inputStream = new FileInputStream(filePath+fileName);
            if (null != inputStream)
            {
                try
                {
                    wb = new  XSSFWorkbook(inputStream);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairIdeology.class);
            exportExcelNew.setWb(wb);
            List<AffairIdeology> list =page.getList();
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
        return "redirect:" + adminPath + "/affair/affairPoliceThoughtAnalysis/?repage";
    }

    /**
     * 导入excel数据
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "import", method= RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 0, 0);
            List<AffairIdeology> list = ei.getDataList(AffairIdeology.class);
            for (AffairIdeology thoughtAnalysis : list){
                try{

                    BeanValidators.validateWithException(validator, thoughtAnalysis);
                    if (!StringUtils.isEmpty(thoughtAnalysis.getUnit())){
                        String unitId=officeService.findByName(thoughtAnalysis.getUnit());
                        thoughtAnalysis.setUnitId(unitId);
                    }
                    String idN = UserUtils.getUser().getId();
                    String id = UserUtils.getUser().getOffice().getId();
                    if (id.equals("1") && !id.equals("34") && !id.equals("95") && !id.equals("156")){
                        thoughtAnalysis.setClassify("3");
                    }
                   /* if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
                        thoughtAnalysis.setClassify("3");
                    }*/else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
                        thoughtAnalysis.setClassify("2");
                    }else{
                        thoughtAnalysis.setClassify("1");
                    }
                    affairIdeologyService.save(thoughtAnalysis);
                    successNum++;
                }catch(ConstraintViolationException ex){
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                    for (String message : messageList){
                        failureMsg.append(message+"; ");
                        failureNum++;
                    }
                }catch (Exception ex) {
                    failureMsg.append(thoughtAnalysis.getUnit()+"(单位:"+")"+" 导入失败："+ex.getMessage());
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
    }


    /**
     * 基层页面，
     *      基层账号(companyId为34,95,156或者officeId为34/95/156，但是处宣教不算基层账号，)进行上报，
     *      处宣教账号进行审核、局宣教查看
     * @param affairIdeology
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("affair:affairIdeology:view")
    @RequestMapping(value = {"jiceng"})
    public String jiceng(AffairIdeology affairIdeology, HttpServletRequest request, HttpServletResponse response, Model model) {
        String companyId = UserUtils.getUser().getCompany().getId();
        String officeId = UserUtils.getUser().getOffice().getId();
        String targetUrl = "modules/affair/affairIdeologyListjiceng";
        if (companyId.equals("1") && officeId != "34" && officeId != "95" && officeId != "156"){
            affairIdeology.setClassify("3");
            targetUrl = "modules/affair/affairIdeologyListju";
        }else {
            affairIdeology.setClassify("1");
            targetUrl = "modules/affair/affairIdeologyListjiceng";
        }
        String id = UserUtils.getUser().getId();
        Page<AffairIdeology> page = affairIdeologyService.findPage(new Page<AffairIdeology>(request, response,-1), affairIdeology);
        model.addAttribute("page", page);
        model.addAttribute("userId", id);
        return targetUrl;
    }

    /**
     * 公安处党委页面 处宣教账号是 三个处的宣教账号
     *  处宣教账号进行添加 上报
     *  公安局账号进行审核
     * @param affairIdeology
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("affair:affairIdeology:view")
    @RequestMapping(value = {"chu"})
    public String chu(AffairIdeology affairIdeology, HttpServletRequest request, HttpServletResponse response, Model model) {
        String id = UserUtils.getUser().getId();

        String idN = UserUtils.getUser().getId();
        if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
            affairIdeology.setClassify("3");
        }else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
            affairIdeology.setClassify("2");
        }else{
            affairIdeology.setClassify("1");
        }
        Page<AffairIdeology> page = affairIdeologyService.findPage(new Page<AffairIdeology>(request, response,-1), affairIdeology);
        model.addAttribute("page", page);
        model.addAttribute("userId", id);
        return "modules/affair/affairIdeologyListchu";
    }


    /**
     * 公安局党委页面  局机关账号为 companyId=1 但是局宣教账号不是 officeId为34 95 156也不是
     *      局机关账号进行添加上报
     *      局宣教账号进行审核
     * @param affairIdeology
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("affair:affairIdeology:view")
    @RequestMapping(value = {"ju"})
    public String ju(AffairIdeology affairIdeology, HttpServletRequest request, HttpServletResponse response, Model model) {
        String id = UserUtils.getUser().getId();

        String idN = UserUtils.getUser().getId();
        if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
            affairIdeology.setClassify("3");
        }else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
            affairIdeology.setClassify("2");
        }else{
            affairIdeology.setClassify("1");
        }
        Page<AffairIdeology> page = affairIdeologyService.findPage(new Page<AffairIdeology>(request, response,-1), affairIdeology);
        model.addAttribute("page", page);
        model.addAttribute("userId", id);
        return "modules/affair/affairIdeologyListju";
    }
}