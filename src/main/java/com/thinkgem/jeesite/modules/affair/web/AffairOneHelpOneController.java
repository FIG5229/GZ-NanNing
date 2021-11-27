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
import com.thinkgem.jeesite.common.utils.excel.ExcelTemplate;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairOneHelpOne;
import com.thinkgem.jeesite.modules.affair.entity.AffairOneHelpOneMain;
import com.thinkgem.jeesite.modules.affair.service.AffairOneHelpOneMainService;
import com.thinkgem.jeesite.modules.affair.service.AffairOneHelpOneService;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 一帮一内容Controller
 * @author mason.xv
 * @version 2020-04-15
 * 子表内容在子表controller
 * AffairOneHelpOneMainController
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairOneHelpOne")
public class AffairOneHelpOneController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairOneHelpOneService affairOneHelpOneService;

	@Autowired
	private AffairOneHelpOneMainService childService;

	@ModelAttribute
	public AffairOneHelpOne get(@RequestParam(required=false) String id) {
		AffairOneHelpOne entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairOneHelpOneService.get(id);
		}
		if (entity == null){
			entity = new AffairOneHelpOne();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairOneHelpOne:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairOneHelpOne affairOneHelpOne, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairOneHelpOne> page = affairOneHelpOneService.findPage(new Page<AffairOneHelpOne>(request, response), affairOneHelpOne);
		model.addAttribute("page", page);
		return "modules/affair/affairOneHelpOneList";
	}
	/**补充
	 *添加一帮一“方案”名称
	 */
	@RequiresPermissions("affair:affairOneHelpOne:view")
	@RequestMapping(value = "form")
	public String form(AffairOneHelpOne affairOneHelpOne, Model model) {
		if (!StringUtils.isEmpty(affairOneHelpOne.getId())){
			AffairOneHelpOneMain affairOneHelpOneMain=new AffairOneHelpOneMain();
			affairOneHelpOneMain.setMainId(affairOneHelpOne.getId());
			List<AffairOneHelpOneMain> list=childService.findList(affairOneHelpOneMain);
			affairOneHelpOne.setOneHelpOneMainList(list);
		}else {
			//导入字表时  先生成主表id 然后在保存
			/*导入子表时使用 ，不导入则无用*/
			IdGen idGen=new IdGen();
			String id = idGen.getNextId();
			model.addAttribute("id",id);
			model.addAttribute("alter","alter");
		}
		model.addAttribute("affairOneHelpOne", affairOneHelpOne);
		return "modules/affair/affairOneHelpOneForm";
	}
	/**补充
	 * 添加一帮一具体内容
	 * 添加内容为关联表的内容
	 */
	/*改为主子表 不在使用*/
	@RequiresPermissions("affair:affairOneHelpOne:edit")
	@RequestMapping(value = "formInDetail")
	public String formInDetail(AffairOneHelpOne affairOneHelpOne, Model model) {
		AffairOneHelpOne affairOneHelpOneBack = new AffairOneHelpOne();
		model.addAttribute("affairOneHelpOne", affairOneHelpOne);
		return "modules/affair/affairOneHelpOneInDetailForm";
	}

	@RequiresPermissions("affair:affairOneHelpOne:edit")
	@RequestMapping(value = "save")
	public String save(AffairOneHelpOne affairOneHelpOne, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairOneHelpOne)){
			return form(affairOneHelpOne, model);
		}
		/*不为空则是导入子表*/
		String isImport = affairOneHelpOne.getIsImport();
		/*genId生成的Id*/
		String genId = request.getParameter("genId");
		if (!StringUtils.isEmpty(isImport) && isImport.equals("true")){
			affairOneHelpOne.setIsNewRecord(true);
			if (!StringUtils.isEmpty(genId)){
				affairOneHelpOne.setId(genId);
			}
		}
		affairOneHelpOneService.save(affairOneHelpOne);
		addMessage(redirectAttributes, "保存一帮一内容成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairOneHelpOneForm";
	}

	@RequiresPermissions("affair:affairOneHelpOne:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairOneHelpOne affairOneHelpOne, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		affairOneHelpOneService.delete(affairOneHelpOne);
		addMessage(redirectAttributes, "删除一帮一内容成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairOneHelpOne/list";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairOneHelpOne:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairOneHelpOneService.deleteByIds(ids);
			//删除子表

			childService.deleteByParentIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairOneHelpOne:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairOneHelpOne affairOneHelpOne, Model model,HttpServletRequest request,HttpServletResponse response) {
//		affairOneHelpOne = affairOneHelpOneService.get(affairOneHelpOne.getId());
		//根据Id获取一帮一明细数据
		AffairOneHelpOneMain affairOneHelpOneMain=new AffairOneHelpOneMain();
		affairOneHelpOneMain.setMainId(affairOneHelpOne.getId());
		Page<AffairOneHelpOneMain> page = childService.findPage(new Page<AffairOneHelpOneMain>(request, response,-1), affairOneHelpOneMain);
		model.addAttribute("page", page);
		model.addAttribute("affairOneHelpOne", affairOneHelpOne);
		return "modules/affair/affairOneHelpOneFormDetail";
	}
	/**
	 * 主表和关联子表实体类形同
	 * 导入导出使用实体类
	 * 导入数据使用主表实体类 先保存主表信息 生成ID后保存子表信息
	 * 导出数据使用子表实体类，填充主表的标题和单位 根据主表Id查询到所有的关联子表数据
	 */

	/**
	 * 导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairOneHelpOne affairOneHelpOne, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairOneHelpOne> page = null;
			if(flag == true){
				page = affairOneHelpOneService.findPage(new Page<AffairOneHelpOne>(request, response), affairOneHelpOne);
			}else {
				page = affairOneHelpOneService.findPage(new Page<AffairOneHelpOne>(request, response,-1), affairOneHelpOne);
			}
			List<AffairOneHelpOne> oneHelpOneList = page.getList();
			AffairOneHelpOneMain affairOneHelpOneMain=new AffairOneHelpOneMain();
			//导出数据集
			List<AffairOneHelpOneMain> list=new ArrayList<>();
			//遍历主表信息
			for (AffairOneHelpOne item:oneHelpOneList){
				affairOneHelpOneMain.setMainId(item.getId());
				//根据Id获取子表数据
				List<AffairOneHelpOneMain> oneHelpOneMainList=childService.findList(affairOneHelpOneMain);
				//遍历子表  填充子表数据中的父表信息
				for (AffairOneHelpOneMain oneHelpOneMain:oneHelpOneMainList){
					oneHelpOneMain.setTitle(item.getTitle());
					oneHelpOneMain.setUnit(item.getUnit());
					//填充完成后添加到导出数据集
					list.add(oneHelpOneMain);
				}
			}
			/*if(flag == true){
				list = affairOneHelpOneService.findList(affairOneHelpOne);
			}else {
				list = affairOneHelpOneService.findList(affairOneHelpOne);
			}*/
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairOneHelpOne.class);
			exportExcelNew.setWb(wb);
			/*List<affairOneHelpOne> list =page.getList();*/
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
		return "redirect:" + adminPath + "/affair/affairOneHelpOne/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 2, 0);
			//导入数据好想不能读取第一行  headerNum从1修改为0 可以成功读取第一行数据
			List<AffairOneHelpOne> list = ei.getDataList(AffairOneHelpOne.class);
			//遍历读取到的Excel数据集
			for (AffairOneHelpOne affairOneHelpOne : list){
				try{
					BeanValidators.validateWithException(validator, affairOneHelpOne);
					if (StringUtils.isNotBlank(affairOneHelpOne.getTitle())&&StringUtils.isNotBlank(affairOneHelpOne.getUnit())){

						//生成查询条件 title和unit
						AffairOneHelpOne oneHelpOne=new AffairOneHelpOne();
						String unitId = officeService.findByName(oneHelpOne.getUnit());
						oneHelpOne.setUnitId(unitId);
						oneHelpOne.setTitle(affairOneHelpOne.getTitle());
						oneHelpOne.setUnit(affairOneHelpOne.getUnit());
						//通过title和unit查询是否存在主表数据
						AffairOneHelpOne one=affairOneHelpOneService.getIdByTitle(oneHelpOne);
						//如果根据title和unit查询到结果 则存在主表信息  保存子表
						//否则保存主表信息
						if (one != null){
							AffairOneHelpOneMain i=new AffairOneHelpOneMain();
							//查询到的主表数据
							i.setMainId(one.getId());
							//填充遍历到的数据
							i.setAddress(affairOneHelpOne.getAddress());
							i.setBeName(affairOneHelpOne.getBeName());
							i.setDate(affairOneHelpOne.getDate());
							i.setJob(affairOneHelpOne.getJob());
							i.setMoney(affairOneHelpOne.getMoney());
							i.setName(affairOneHelpOne.getName());
							i.setUnitJob(affairOneHelpOne.getUnitJob());
							i.setSituation(affairOneHelpOne.getSituation());
							i.setTel(affairOneHelpOne.getTel());
							childService.save(i);
						}else {
							affairOneHelpOne.setIsImport("import");
							affairOneHelpOneService.save(affairOneHelpOne);
						}
//						affairOneHelpOneService.save(affairOneHelpOne);
						successNum++;
					}else{
						throw new Exception();
					}
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("一帮一内容:"+" 导入失败："+ex.getMessage());
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

	@RequestMapping("template/import")
	public String download( HttpServletResponse response,HttpServletRequest request) {
		String id ="";
		if(null != request.getParameter("id") && !"".equals(request.getParameter("id"))){
			id=request.getParameter("id").toString();
			request.setAttribute("id",id);
		}
		if(null != request.getParameter("fileName") && !"".equals(request.getParameter("fileName"))){
			String template=request.getParameter("fileName").toString();
			request.setAttribute("template",template);
		}
		request.setAttribute("url","/affair/affairOneHelpOne/importChild");
		return "modules/affair/child_template_import";
	}

	@RequestMapping(value = "importChild", method= RequestMethod.POST)
	public String importVisitRecordFile(MultipartFile file, RedirectAttributes redirectAttributes,String id,Model model) {

		AffairOneHelpOne affairTypicalPerson = new AffairOneHelpOne();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairOneHelpOneMain> list = ei.getDataList(AffairOneHelpOneMain.class);
			affairTypicalPerson.setOneHelpOneMainList(list);
			for (AffairOneHelpOneMain oneHelpOneMain : list){
				try{
					BeanValidators.validateWithException(validator, oneHelpOneMain);
					oneHelpOneMain.setMainId(id);
					childService.save(oneHelpOneMain);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(oneHelpOneMain.getCreateBy().getOffice().getName()+"(姓名:"+oneHelpOneMain.getCreateBy().getName()+")"+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		addMessage(redirectAttributes, "已成功导入 ");
		redirectAttributes.addFlashAttribute("result","success");
		redirectAttributes.addFlashAttribute("affairTypicalPerson",affairTypicalPerson);
		model.addAttribute("result","success");
		model.addAttribute("url","/affair/affairOneHelpOne/importChild");
		return "modules/affair/child_template_import";
	}


	@RequestMapping("getChildById")
	@ResponseBody
	public Result getChildById(String id){
		Result result= new Result();
		AffairOneHelpOneMain typicalPersonVisit  = new AffairOneHelpOneMain();
		typicalPersonVisit.setMainId(id);
		try {
			List<AffairOneHelpOneMain> list = childService.findList(typicalPersonVisit);
			result.setResult(list);
			result.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}


	/*导出子表模板*/
	@RequestMapping(value = "exportChildFile")
	public String exportChildFile(AffairOneHelpOne affairTypicalPerson,HttpServletResponse response) {
		AffairOneHelpOneMain personVisit = new AffairOneHelpOneMain();
		personVisit.setMainId(affairTypicalPerson.getId());
		List<AffairOneHelpOneMain> list = childService.findList(personVisit);
		if (StringUtils.isBlank(affairTypicalPerson.getId())){
			list = new ArrayList<>();;
		}

		String fileSeperator = File.separator;
		String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
		String fileName = "一帮一-帮扶信息导出.xlsx";
		String path = filePath+fileName;

		ExcelTemplate excelTemplate =  new ExcelTemplate(path);
		if (list==null){
			list = new ArrayList<>();
		}
		LinkedHashMap<Integer, LinkedList<String>> areaValues = new LinkedHashMap<>();
		for (int i =0;i<list.size();i++){
			LinkedList<String> lineList = new LinkedList<>();
			AffairOneHelpOneMain item = list.get(i);
			lineList.add(affairTypicalPerson.getTitle());
			lineList.add(affairTypicalPerson.getUnit());
			lineList.add(DateUtils.formatDate(item.getDate(),"yyyy-MM-dd"));
			lineList.add(item.getName());
			lineList.add(item.getJob());
			lineList.add(item.getBeName());
			lineList.add(item.getUnitJob());
			lineList.add(item.getSituation());
			lineList.add(item.getAddress());
			lineList.add(item.getMoney());
			lineList.add(item.getTel());
			areaValues.put(i,lineList);
		}
		try {
			excelTemplate.addRowByExist(0,1,1,2,areaValues,true);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			/*导入导出模板不一致，导处模板需要占位符*/
			response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode("一帮一-帮扶信息.xlsx"));
			ServletOutputStream fout = response.getOutputStream();
			excelTemplate.getWorkbook().write(fout);
			fout.close();
			return null;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:"+Global.getAdminPath()+"/affair/affairOneHelpOne/form?id="+affairTypicalPerson.getId();
	}

}