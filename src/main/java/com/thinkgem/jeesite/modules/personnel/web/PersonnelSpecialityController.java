/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelSkill;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelSpecialityService;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelSpeciality;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 专长信息集Controller
 * @author alan.wu
 * @version 2020-09-07
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelSpeciality")
public class PersonnelSpecialityController extends BaseController {

    @Autowired
    private PersonnelSpecialityService personnelSpecialityService;


    @RequiresPermissions("personnel:personnelSpeciality:view")
    @RequestMapping(value = {"list", ""})
    public String list(PersonnelSpeciality personnelSpeciality, HttpServletRequest request, HttpServletResponse response, Model model) {
        if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
            personnelSpeciality.setIdNumber(request.getParameter("idNumber").toString());
        }
        Page<PersonnelSpeciality> page = personnelSpecialityService.findPage(new Page<PersonnelSpeciality>(request, response), personnelSpeciality);
        if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
            request.setAttribute("mType",request.getParameter("mType").toString());
        }
        model.addAttribute("page",page);
        return "modules/personnel/personnelSpecialityList";
    }

    @RequiresPermissions("personnel:personnelSpeciality:view")
    @RequestMapping(value = "form")
    public String form(PersonnelSpeciality personnelSpeciality, Model model,HttpServletRequest request) {
        if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
            personnelSpeciality.setIdNumber(request.getParameter("idNumber").toString());
        }
        personnelSpeciality = personnelSpecialityService.selectBean(personnelSpeciality.getId());
        model.addAttribute("personnelSpeciality", personnelSpeciality);
        return "modules/personnel/personnelSpecialityForm";
    }

    @RequiresPermissions("personnel:personnelSpeciality:edit")
    @RequestMapping(value = "save")
    public String save(PersonnelSpeciality personnelSpeciality, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, personnelSpeciality)){
            return form(personnelSpeciality, model,request);
        }
        personnelSpecialityService.save(personnelSpeciality);
        addMessage(redirectAttributes, "保存成功");
        request.setAttribute("saveResult","success");
        return "modules/personnel/personnelSpecialityForm";
    }

    @RequiresPermissions("personnel:personnelSpeciality:view")
    @RequestMapping(value = "jumpForm")
    public String jumpForm(PersonnelSpeciality personnelSpeciality, Model model,HttpServletRequest request) {
        model.addAttribute("personnelSpeciality", personnelSpeciality);
        return "modules/personnel/personnelSpecialityForm";
    }
    @RequiresPermissions("personnel:personnelSpeciality:view")
    @RequestMapping(value = "formDetail")
    public String formDetail(PersonnelSpeciality personnelSpeciality, Model model) {
        personnelSpeciality = personnelSpecialityService.selectBean(personnelSpeciality.getId());
        model.addAttribute("personnelSpeciality", personnelSpeciality);
        return "modules/personnel/personnelSpecialityFormDetail";
    }
    @RequiresPermissions("personnel:personnelSpeciality:edit")
    @RequestMapping(value = "delete")
    public String delete(PersonnelSpeciality personnelSpeciality, RedirectAttributes redirectAttributes,HttpServletRequest request) {
        String url = "redirect:"+Global.getAdminPath()+"/personnel/personnelSpeciality/?repage&idNumber="+personnelSpeciality.getIdNumber();

        if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
            request.setAttribute("mType",request.getParameter("mType").toString());
            url = "redirect:"+Global.getAdminPath()+"/personnel/personnelSpeciality/?repage&mType="+request.getParameter("mType").toString();

        }
        personnelSpecialityService.delete(personnelSpeciality);
        addMessage(redirectAttributes, "删除专长信息成功");
        return url;
    }
    /**
     * 导出excel格式数据
     * @param
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "export", method= RequestMethod.POST)
    public String exportExcelByTemplate(PersonnelSpeciality personnelSpeciality, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
        XSSFWorkbook wb = null;
        try
        {
            String fileName = "";
            if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
                fileName= request.getParameter("fileName").toString();
            }
            //修改
            Page<PersonnelSpeciality> page = null;
            if(flag == true){
                page = personnelSpecialityService.findPage(new Page<PersonnelSpeciality>(request, response), personnelSpeciality);
            }else {
                page = personnelSpecialityService.findPage(new Page<PersonnelSpeciality>(request, response,-1), personnelSpeciality);
            }
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
            //修改
            ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelSpeciality.class);
            exportExcelNew.setWb(wb);
            List<PersonnelSpeciality> list =page.getList();
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
        //修改
        return "redirect:" + adminPath + "/personnel/personnelSpeciality/?repage";
    }

    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
        String isCover = request.getParameter("isCover");
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 0, 0);
            //修改
            List<PersonnelSpeciality> list = ei.getDataList(PersonnelSpeciality.class);
            //选择覆盖模式，须先将改身份证下相关履历信息删除
            if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
                List<String> idNumbers = new ArrayList<>();
                list.stream().forEach(personnelResume -> {
                    if(personnelResume.getIdNumber()!=null){
                        idNumbers.add(personnelResume.getIdNumber());
                    }
                });
                if(idNumbers.size()>0&&idNumbers!=null)
                    personnelSpecialityService.deleteByIdNumbers(idNumbers);
            }
            for (PersonnelSpeciality personnelSpeciality : list){
                try{
                    BeanValidators.validateWithException(validator, personnelSpeciality);
                    personnelSpecialityService.save(personnelSpeciality);
                    successNum++;
                }catch(ConstraintViolationException ex){
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                    for (String message : messageList){
                        failureMsg.append(message+"; ");
                        failureNum++;
                    }
                }catch (Exception ex) {
                    failureMsg.append("(身份证号码:"+personnelSpeciality.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
        return "redirect:" + adminPath + "/file/template/download/view?id=personnel_personnelSpeciality&isCover="+isCover;
    }

}