/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.affair.entity.AffairActivityMien;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.SysIndex;
import com.thinkgem.jeesite.modules.sys.service.SysIndexService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 索引管理Controller
 * @author tom.fu
 * @version 2020-09-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysIndex")
public class SysIndexController extends BaseController {

	@Autowired
	private SysIndexService sysIndexService;


	@ModelAttribute
	public SysIndex get(@RequestParam(required=false) String id) {
		SysIndex entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysIndexService.get(id);
		}
		if (entity == null){
			entity = new SysIndex();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysIndex:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysIndex sysIndex, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysIndex> page = sysIndexService.findPage(new Page<SysIndex>(request, response), sysIndex); 
		model.addAttribute("page", page);
		return "modules/sys/sysIndexList";
	}

	@RequiresPermissions("sys:sysIndex:view")
	@RequestMapping(value = "form")
	public String form(SysIndex sysIndex, Model model) {
		List<SysIndex> strings = sysIndexService.selectIds();
		model.addAttribute("strings",strings);
		model.addAttribute("sysIndex", sysIndex);
		String id = sysIndex.getRoleId();
		String selectNames = sysIndexService.selectName(id);
		model.addAttribute("selectNames",selectNames);
		return "modules/sys/sysIndexForm";
	}

	@RequiresPermissions("sys:sysIndex:edit")
	@RequestMapping(value = "save")
	public String save(SysIndex sysIndex, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, sysIndex)){
			return form(sysIndex, model);
		}
		String roleId = sysIndex.getRoleId();
		String[] split = roleId.split(":");
		String id = split[0];
		sysIndex.setRoleId(split[0]);
		String selectNames = sysIndexService.selectName(id);
		sysIndex.setName(selectNames);
		sysIndexService.save(sysIndex);
		addMessage(redirectAttributes, "保存索引管理成功");
		request.setAttribute("saveResult","sucess");
		return "modules/sys/sysIndexForm";
	}
	
	@RequiresPermissions("sys:sysIndex:edit")
	@RequestMapping(value = "delete")
	public String delete(SysIndex sysIndex, RedirectAttributes redirectAttributes) {
		sysIndexService.delete(sysIndex);
		addMessage(redirectAttributes, "删除索引管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysIndex/?repage";
	}

	/**
	 * 查看详情
	 * @param sysIndex
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:sysIndex:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(SysIndex sysIndex, Model model) {
		model.addAttribute("sysIndex", sysIndex);

		return "modules/sys/sysIndexFormDetail";
	}
	@ResponseBody
	@RequiresPermissions("sys:sysIndex:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			sysIndexService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	/**
	 * 从sys_role表查询id作为角色编号的id
	 */
	@ResponseBody
	@RequiresPermissions("sys:sysIndex:view")
	@RequestMapping(value = {"inquireIds"})
	public List<SysIndex> inquireIds(Model model){
		List<SysIndex> strings = sysIndexService.selectIds();
		model.addAttribute("strings",strings);
		return strings;
	}

	/**
	 * 从sys_role表查询id作为角色编号的id

	@ResponseBody
	@RequiresPermissions("sys:sysIndex:view")
	@RequestMapping(value = {"inquireNames"})
	public List<String> inquireNames(@RequestParam(value = "id") String id,Model model){
		List<String> selectNames = sysIndexService.selectName(id);
		model.addAttribute("selectNames",selectNames);
		return selectNames;
	}*/

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
			List<SysIndex> list = ei.getDataList(SysIndex.class);
			for (SysIndex sysIndex : list){
				try{

					BeanValidators.validateWithException(validator, sysIndex);

					sysIndexService.save(sysIndex);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(索引管理:"+")"+" 导入失败："+ex.getMessage());
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
		return "redirect:" + adminPath + "/file/template/download/view?repage";
	}
	/**
	 * 导出excel格式数据
	 * @param sysIndex
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(SysIndex sysIndex, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<SysIndex> page = null;
			if(flag == true){
				page = sysIndexService.findPage(new Page<SysIndex>(request, response), sysIndex);
			}else{
				page = sysIndexService.findPage(new Page<SysIndex>(request, response,-1), sysIndex);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, SysIndex.class);
			exportExcelNew.setWb(wb);
			List<SysIndex> list =page.getList();
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
			addMessage(redirectAttributes, "导出活动风采列表失败！失败信息："+ex);
		}
		return "redirect:" + adminPath + "/sys/SysIndex?repage";
	}
}