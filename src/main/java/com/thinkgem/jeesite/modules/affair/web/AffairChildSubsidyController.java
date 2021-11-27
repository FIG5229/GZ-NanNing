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
import com.thinkgem.jeesite.modules.affair.entity.AffairChildSubsidy;
import com.thinkgem.jeesite.modules.affair.service.AffairChildSubsidyService;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
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
import java.util.List;

/**
 * 幼儿补助Controller
 * @author cecil.li
 * @version 2019-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairChildSubsidy")
public class AffairChildSubsidyController extends BaseController {

	@Autowired
	private AffairChildSubsidyService affairChildSubsidyService;
    @Autowired
    private OfficeService officeService;
    @Autowired
	private PersonnelBaseService personnelBaseService;
	
	@ModelAttribute
	public AffairChildSubsidy get(@RequestParam(required=false) String id) {
		AffairChildSubsidy entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairChildSubsidyService.get(id);
		}
		if (entity == null){
			entity = new AffairChildSubsidy();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairChildSubsidy:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairChildSubsidy affairChildSubsidy, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairChildSubsidy> page = affairChildSubsidyService.findPage(new Page<AffairChildSubsidy>(request, response), affairChildSubsidy); 
		model.addAttribute("page", page);
		return "modules/affair/affairChildSubsidyList";
	}

	@RequiresPermissions("affair:affairChildSubsidy:view")
	@RequestMapping(value = "form")
	public String form(AffairChildSubsidy affairChildSubsidy, Model model) {
		model.addAttribute("affairChildSubsidy", affairChildSubsidy);
		return "modules/affair/affairChildSubsidyForm";
	}

	@RequiresPermissions("affair:affairChildSubsidy:view")
	@RequestMapping(value = "add")
	public String add(AffairChildSubsidy affairChildSubsidy, Model model) {

		//导入字表时  先生成主表id 然后在保存
		/*导入子表时使用 ，不导入则无用*/
		IdGen idGen = new IdGen();
		String id = idGen.getNextId();
		model.addAttribute("id", id);
		model.addAttribute("alter", false);

		model.addAttribute("affairChildSubsidy", affairChildSubsidy);
		if (affairChildSubsidy.isHandleAdd()) {
			/*再操作里添加子女*/
			return "modules/affair/affairChildSubsidyHandleAdd";
		} else {
			return "modules/affair/affairChildSubsidyAdd";
		}
	}

	/*添加保存*/
	@RequiresPermissions("affair:affairChildSubsidy:edit")
	@RequestMapping(value = "addSave")
	public String addSave(AffairChildSubsidy affairChildSubsidy, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairChildSubsidy)){
			return form(affairChildSubsidy, model);
		}
		affairChildSubsidyService.addSave(affairChildSubsidy);
		addMessage(redirectAttributes, "保存助学管理成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairChildSubsidyForm";
	}

	@RequiresPermissions("affair:affairChildSubsidy:edit")
	@RequestMapping(value = "save")
	public String save(AffairChildSubsidy affairChildSubsidy, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairChildSubsidy)){
			return form(affairChildSubsidy, model);
		}
		affairChildSubsidyService.save(affairChildSubsidy);
		addMessage(redirectAttributes, "保存幼儿补助成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairChildSubsidyForm";
	}
	
	@RequiresPermissions("affair:affairChildSubsidy:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairChildSubsidy affairChildSubsidy, RedirectAttributes redirectAttributes) {
		affairChildSubsidyService.delete(affairChildSubsidy);
		addMessage(redirectAttributes, "删除幼儿补助成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairChildSubsidy/?repage";
	}

	/**
	 * 详情
	 * @param affairChildSubsidy
	 * @param
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairChildSubsidy:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairChildSubsidy affairChildSubsidy,  Model model) {
		model.addAttribute("affairChildSubsidy", affairChildSubsidy);
		return "modules/affair/affairChildSubsidyFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairChildSubsidy:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairChildSubsidyService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
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
	public String exportExcelByTemplate(AffairChildSubsidy affairChildSubsidy, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairChildSubsidy> page = null;
			if(flag == true){
				page = affairChildSubsidyService.findPage(new Page<AffairChildSubsidy>(request, response), affairChildSubsidy);
			}else {
				page = affairChildSubsidyService.findPage(new Page<AffairChildSubsidy>(request, response,-1), affairChildSubsidy);
			}
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairChildSubsidy.class);
			exportExcelNew.setWb(wb);
			List<AffairChildSubsidy> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairChildSubsidy/?repage";
	}
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 0, 0);
            //修改
            List<AffairChildSubsidy> list = ei.getDataList(AffairChildSubsidy.class);
            for (AffairChildSubsidy affairChildSubsidy : list){
                try{
                    //单位绑定单位id
                    String orgId = officeService.findByName(affairChildSubsidy.getUnit());
                    String idNumber = personnelBaseService.findByUnitName(affairChildSubsidy.getName(),affairChildSubsidy.getUnit());
                    if(orgId != null){
                        affairChildSubsidy.setUnitId(orgId);
                    }
					if(idNumber != null){
						affairChildSubsidy.setIdNumber(idNumber);
					}
                    BeanValidators.validateWithException(validator, affairChildSubsidy);
                    affairChildSubsidyService.save(affairChildSubsidy);
                    successNum++;
                }catch(ConstraintViolationException ex){
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                    for (String message : messageList){
                        failureMsg.append(message+"; ");
                        failureNum++;
                    }
                }catch (Exception ex) {
                    failureMsg.append("(身份证号码:"+affairChildSubsidy.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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