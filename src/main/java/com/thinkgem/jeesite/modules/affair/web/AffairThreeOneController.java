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
import com.thinkgem.jeesite.modules.affair.entity.AffairOrganizationBulid;
import com.thinkgem.jeesite.modules.affair.entity.AffairThreeOne;
import com.thinkgem.jeesite.modules.affair.entity.AffairTjRegister;
import com.thinkgem.jeesite.modules.affair.service.AffairThreeOneService;
import com.thinkgem.jeesite.modules.affair.service.AffairTjRegisterService;
import com.thinkgem.jeesite.modules.affair.service.AffairTwBaseService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 三会一课Controller
 * @author cecil.li
 * @version 2019-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairThreeOne")
public class AffairThreeOneController extends BaseController {

	@Autowired
	private AffairThreeOneService affairThreeOneService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairTwBaseService affairTwBaseService;

    @Autowired
    private AffairTjRegisterService affairTjRegisterService;

    @ModelAttribute
	public AffairThreeOne get(@RequestParam(required=false) String id) {
		AffairThreeOne entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairThreeOneService.get(id);
		}
		if (entity == null){
			entity = new AffairThreeOne();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairThreeOne:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairThreeOne affairThreeOne, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairThreeOne> page = affairThreeOneService.findPage(new Page<AffairThreeOne>(request, response), affairThreeOne); 
		model.addAttribute("page", page);
		return "modules/affair/affairThreeOneList";
	}

	@RequiresPermissions("affair:affairThreeOne:view")
	@RequestMapping(value = "form")
	public String form(AffairThreeOne affairThreeOne, Model model) {
		/*使用团名名册组织树，不在需要判断是否为联合 显示全部单位*/
		/*List<AffairOrganizationBulid> twList = affairTwBaseService.findTreeData();
		twList.stream().forEach(item -> {
			if (item.getName().contains("联合")){
				 model.addAttribute("showAll","true");
			}
		});*/
		model.addAttribute("affairThreeOne", affairThreeOne);

		return "modules/affair/affairThreeOneForm";
	}

	@RequiresPermissions("affair:affairThreeOne:edit")
	@RequestMapping(value = "save")
	public String save(AffairThreeOne affairThreeOne, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairThreeOne)){
			return form(affairThreeOne, model);
		}
		try {
			String[] attendant = affairThreeOne.getAttendants().split(",");
			int sdNum = 0;
			if (StringUtils.isNotBlank(affairThreeOne.getAttendants())){
				sdNum = attendant.length;
			}
			affairThreeOne.setSdNum(sdNum);
			String[] absent = affairThreeOne.getAbsentPerson().split(",");
			int absentNum = 0;
			if (StringUtils.isNotBlank(affairThreeOne.getAbsentPerson())){
				absentNum = absent.length;
			}
			affairThreeOne.setAbsentNum(absentNum);
			affairThreeOne.setYdNum(affairThreeOne.getAbsentNum()+affairThreeOne.getSdNum());
		}catch (Exception e){
			e.printStackTrace();
		}
		affairThreeOneService.save(affairThreeOne);
		addMessage(redirectAttributes, "保存三会一课成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairThreeOneForm";
	}
	
	@RequiresPermissions("affair:affairThreeOne:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairThreeOne affairThreeOne, RedirectAttributes redirectAttributes) {
		affairThreeOneService.delete(affairThreeOne);
		addMessage(redirectAttributes, "删除三会一课成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairThreeOne/?repage";
	}

	/**
	 * 详情
	 * @param affairThreeOne
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairThreeOne:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairThreeOne affairThreeOne, Model model) {
		model.addAttribute("affairThreeOne", affairThreeOne);
		if(affairThreeOne.getFilePath() != null && affairThreeOne.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairThreeOne.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairThreeOneFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairThreeOne:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairThreeOneService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairThreeOne affairThreeOne, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairThreeOne> page = null;
			if(flag == true){
				page = affairThreeOneService.findPage(new Page<AffairThreeOne>(request, response), affairThreeOne);
			}else {
				page = affairThreeOneService.findPage(new Page<AffairThreeOne>(request, response,-1), affairThreeOne);
			}
/*
			Page<AffairThreeOne> page = affairThreeOneService.findPage(new Page<AffairThreeOne>(request, response,-1), affairThreeOne);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairThreeOne.class);
			exportExcelNew.setWb(wb);
			List<AffairThreeOne> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairThreeOne/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairThreeOne> list = ei.getDataList(AffairThreeOne.class);
			for (AffairThreeOne affairThreeOne : list){
				try{
					String organization = affairThreeOne.getOrganization();//团组织名称
					if(StringUtils.isNotBlank(organization)){
						String organizationId = affairTwBaseService.findIdByName(organization);
						if(StringUtils.isNotBlank(organizationId)){
							affairThreeOne.setOrganizationId(organizationId);
						}
					}
					BeanValidators.validateWithException(validator, affairThreeOne);
					affairThreeOneService.save(affairThreeOne);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append( " 导入失败："+ex.getMessage());
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

	@ResponseBody
    @RequestMapping("absentPerson")
    public Result absentPerson(String attendIds){
	    attendIds = attendIds.replaceAll("tym_","");
	    String[] attendIdArray = attendIds.split(",");

        Result result = new Result();
        try {
            List<AffairTjRegister> list = affairTjRegisterService.findPersonNotInList(attendIdArray);
            StringBuffer absentPersonId = new StringBuffer();
            StringBuffer absentPersonName = new StringBuffer();
            list.stream().forEach(item -> {
                absentPersonId.append(item.getIdNumber()+",");
                absentPersonName.append(item.getName()+",");
            });
            Map<String,String> map = new HashMap<>();
            map.put("absentPersonId",absentPersonId.toString());
            map.put("absentPersonName",absentPersonName.toString());
            result.setSuccess(true);
            result.setResult(map);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("计算缺席人员异常");
        }
	    return result;

    }

}
