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
import com.thinkgem.jeesite.modules.affair.entity.AffairYqContol;
import com.thinkgem.jeesite.modules.affair.service.AffairYqContolService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 舆情管控Controller
 * @author cecil.li
 * @version 2019-11-02
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairYqContol")
public class AffairYqContolController extends BaseController {

	@Autowired
	private AffairYqContolService affairYqContolService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private UserDao userDao;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AffairYqContol get(@RequestParam(required=false) String id) {
		AffairYqContol entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairYqContolService.get(id);
		}
		if (entity == null){
			entity = new AffairYqContol();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairYqContol:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairYqContol affairYqContol, HttpServletRequest request, HttpServletResponse response, Model model) {
		if ("5".equals(UserUtils.getUser().getOffice().getId())){
			affairYqContol.setUnitId(UserUtils.getUser().getCompany().getId());
		}else {
			affairYqContol.setUnitId(UserUtils.getUser().getOffice().getId());
		}

		affairYqContol.setCreateBy(UserUtils.getUser());
		Page<AffairYqContol> page = affairYqContolService.findPage(new Page<AffairYqContol>(request, response), affairYqContol);
		model.addAttribute("page", page);
		return "modules/affair/affairYqContolList";
	}

	@RequiresPermissions("affair:affairYqContol:view")
	@RequestMapping(value = "form")
	public String form(AffairYqContol affairYqContol, Model model) {
		model.addAttribute("affairYqContol", affairYqContol);
		return "modules/affair/affairYqContolForm";
	}

	@RequiresPermissions("affair:affairYqContol:edit")
	@RequestMapping(value = "save")
	public String save(AffairYqContol affairYqContol, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairYqContol)){
			return form(affairYqContol, model);
		}
		affairYqContolService.save(affairYqContol);
		addMessage(redirectAttributes, "保存舆情管控成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairYqContolForm";
	}
	
	@RequiresPermissions("affair:affairYqContol:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairYqContol affairYqContol, RedirectAttributes redirectAttributes) {
		affairYqContolService.delete(affairYqContol);
		addMessage(redirectAttributes, "删除舆情管控成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairYqContol/?repage";
	}

	/**
	 * 详情
	 * @param affairYqContol
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairYqContol:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairYqContol affairYqContol, Model model) {
		model.addAttribute("affairYqContol", affairYqContol);
		if(affairYqContol.getFilePath() != null && affairYqContol.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairYqContol.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairYqContolFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairYqContol:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairYqContolService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 批量提交
	 * @param
	 * @return
	 */
	@RequestMapping(value = {"submitByIds"})
	public String submitByIds(HttpServletRequest request, Model model) {
		User user = UserUtils.getUser();
		//接受前台传过来的ids
		String idsStr = request.getParameter("ids");
		//字符串转数组
		String[] idsArray = idsStr.split(",");
		//数组转集合
		List<String> userList = new ArrayList<String>();
		String manId = request.getParameter("manId");
		String man = userDao.findUserByTree(manId);
		Collections.addAll(userList,idsArray);
		List <AffairYqContol> list = affairYqContolService.findByIds(userList);
		for (AffairYqContol affairYqContol : list){
			affairYqContol.setSubmitMan(user.getName());
			affairYqContol.setSubmitManId(user.getId());
			affairYqContol.setMan(man);
			affairYqContol.setManId(manId);
			affairYqContolService.save(affairYqContol);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairYqContol/list";
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
	public String exportExcelByTemplate(AffairYqContol affairYqContol, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairYqContol> page = null;
			if ("5".equals(UserUtils.getUser().getOffice().getId())){
				affairYqContol.setUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairYqContol.setUnitId(UserUtils.getUser().getOffice().getId());
			}
			affairYqContol.setCreateBy(UserUtils.getUser());
			if(flag == true){
				page = affairYqContolService.findPage(new Page<AffairYqContol>(request, response), affairYqContol);
			}else{
				page = affairYqContolService.findPage(new Page<AffairYqContol>(request, response,-1), affairYqContol);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairYqContol.class);
			exportExcelNew.setWb(wb);
			List<AffairYqContol> list =page.getList();
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
			addMessage(redirectAttributes, "导出舆情处置信息失败！失败信息："+ex);
		}
		return "redirect:" + adminPath + "/affair/affairYqContol/?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairYqContol> list = ei.getDataList(AffairYqContol.class);
			for (AffairYqContol affairYqContol : list){
				try{
					//获取责任单位名称
					String unit = affairYqContol.getZrUnit();
					//设置责任单位id
					affairYqContol.setZrUnitId(officeService.findByName(unit)==null ? "":officeService.findByName(unit));
					affairYqContolService.save(affairYqContol);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureNum++;
					failureMsg.append("<br>舆情标题:"+affairYqContol.getTitle()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "从文件中读取到"+list.size()+"条数据，已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		//redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view?id=affair_yqContol";
	}
}