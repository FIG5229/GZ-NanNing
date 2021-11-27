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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairCheckCount;
import com.thinkgem.jeesite.modules.affair.service.AffairCheckCountService;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 档案管理统计台账Controller
 * @author cecil.li
 * @version 2020-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCheckCount")
public class AffairCheckCountController extends BaseController {

    @Autowired
    private AffairCheckCountService affairCheckCountService;

    @ModelAttribute
    public AffairCheckCount get(@RequestParam(required=false) String id) {
        AffairCheckCount entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = affairCheckCountService.get(id);
        }
        if (entity == null){
            entity = new AffairCheckCount();
        }
        return entity;
    }

    @RequiresPermissions("affair:affairCheckCount:view")
    @RequestMapping(value = {"list", ""})
    public String list(AffairCheckCount affairCheckCount, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AffairCheckCount> page = affairCheckCountService.findPage(new Page<AffairCheckCount>(request, response, -1), affairCheckCount);
		model.addAttribute("page", page);
		List<AffairCheckCount> list = affairCheckCountService.findList(affairCheckCount);
//        model.addAttribute("list",page.getList());
        return "modules/affair/affairCheckCountList";
    }

    @RequiresPermissions("affair:affairCheckCount:view")
    @RequestMapping(value = "form")
    public String form(AffairCheckCount affairCheckCount, Model model) {
        model.addAttribute("affairCheckCount", affairCheckCount);
        return "modules/affair/affairCheckCountForm";
    }

    @RequiresPermissions("affair:affairCheckCount:edit")
    @RequestMapping(value = "save")
    public String save(AffairCheckCount affairCheckCount, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, affairCheckCount)){
            return form(affairCheckCount, model);
        }
        affairCheckCountService.save(affairCheckCount);
        addMessage(redirectAttributes, "保存档案管理统计台账成功");
        return "redirect:"+Global.getAdminPath()+"/affair/affairCheckCount/?repage";
    }

    @RequiresPermissions("affair:affairCheckCount:edit")
    @RequestMapping(value = "delete")
    public String delete(AffairCheckCount affairCheckCount, RedirectAttributes redirectAttributes) {
        affairCheckCountService.delete(affairCheckCount);
        addMessage(redirectAttributes, "删除档案管理统计台账成功");
        return "redirect:"+Global.getAdminPath()+"/affair/affairCheckCount/?repage";
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
    public String exportExcelByTemplate(AffairCheckCount affairCheckCount, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
        XSSFWorkbook wb = null;
        try
        {
            String fileName = "";
            if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
                fileName= request.getParameter("fileName").toString();
            }
            Page<AffairCheckCount> page = null;
            if(flag == true){
                page = affairCheckCountService.findPage(new Page<AffairCheckCount>(request, response), affairCheckCount);
            }else {
                page = affairCheckCountService.findPage(new Page<AffairCheckCount>(request, response,-1), affairCheckCount);
            }
/*
			Page<AffairGz> page = affairGzService.findPage(new Page<AffairGz>(request, response,-1), affairGz);
*/
            String fileSeperator = File.separator;
            String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
            InputStream inputStream = new FileInputStream(filePath+fileName);
            if (null != inputStream)
            {
                try
                {
                    wb = new XSSFWorkbook(inputStream);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            //修改
            ExportExcelNew exportExcelNew = new ExportExcelNew(1, AffairCheckCount.class);
            exportExcelNew.setWb(wb);
            List<AffairCheckCount> list =page.getList();
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
        return "redirect:" + adminPath + "/affair/affairCheckCount/?repage";
    }

    /**
     * 导入excel数据
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "import", method= RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        affairCheckCountService.deleteAll();
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 3, 0);
            List<AffairCheckCount> list = ei.getDataList(AffairCheckCount.class);
            for (AffairCheckCount affairCheckCount : list){
                try{
                    BeanValidators.validateWithException(validator, affairCheckCount);
                    affairCheckCountService.save(affairCheckCount);
                    successNum++;
                }catch(ConstraintViolationException ex){
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                    for (String message : messageList){
                        failureMsg.append(message+"; ");
                        failureNum++;
                    }
                }catch (Exception ex) {
                    failureMsg.append("导入失败："+ex.getMessage());
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

}