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
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.dao.AffairPoliceHomeChildDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceHome;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceHomeChild;
import com.thinkgem.jeesite.modules.affair.service.AffairPoliceHomeService;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
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
import java.util.List;
import java.util.Map;

/**
 * ??????????????????Controller
 * @author cecil.li
 * @version 2019-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPoliceHome")
public class AffairPoliceHomeController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private OfficeDao officeDao;
	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairPoliceHomeService affairPoliceHomeService;

	@Autowired
	private AffairPoliceHomeChildDao childDao;
	
	@ModelAttribute
	public AffairPoliceHome get(@RequestParam(required=false) String id) {
		AffairPoliceHome entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPoliceHomeService.get(id);
		}
		if (entity == null){
			entity = new AffairPoliceHome();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairPoliceHome:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairPoliceHome affairPoliceHome, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairPoliceHome> page = affairPoliceHomeService.findPage(new Page<AffairPoliceHome>(request, response), affairPoliceHome); 
		model.addAttribute("page", page);
		return "modules/affair/affairPoliceHomeList";
	}

	@RequiresPermissions("affair:affairPoliceHome:view")
	@RequestMapping(value = "form")
	public String form(AffairPoliceHome affairPoliceHome, Model model) {
		if (!StringUtils.isEmpty(affairPoliceHome.getId())){
			List<AffairPoliceHomeChild> list=childDao.findByParentId(affairPoliceHome.getId());
			affairPoliceHome.setPoliceHomeChildList(list);
			model.addAttribute("id",affairPoliceHome.getId());
			affairPoliceHome.setIsAdd("false");
		}else{
			//???????????????  ???????????????id ???????????????
			/*????????????????????? ?????????????????????*/
			IdGen idGen=new IdGen();
			String id = idGen.getNextId();
			model.addAttribute("id",id);
			affairPoliceHome.setIsAdd("true");
		}
		model.addAttribute("affairPoliceHome", affairPoliceHome);
		return "modules/affair/affairPoliceHomeForm";
	}
	/*???????????????  ??????*/
    //?????????????????????  ?????????
	@RequiresPermissions("affair:affairPoliceHome:edit")
	@RequestMapping(value = "formInDetail")
	public String formInDetail(AffairPoliceHome affairPoliceHome, Model model) {
	    AffairPoliceHome affairPoliceHomeBack = new AffairPoliceHome();
        affairPoliceHomeBack.setPointDate(affairPoliceHome.getPointDate());
        affairPoliceHomeBack.setUnit(affairPoliceHome.getUnit());
        affairPoliceHomeBack.setUnitId(affairPoliceHome.getUnitId());
        affairPoliceHomeBack.setPointUnit(affairPoliceHome.getPointUnit());
        affairPoliceHomeBack.setPointUnitId(affairPoliceHome.getPointUnitId());
        affairPoliceHomeBack.setProject(affairPoliceHome.getProject());
        model.addAttribute("affairPoliceHome", affairPoliceHomeBack);
		return "modules/affair/affairPoliceHomeInDetailForm";
	}

	@RequiresPermissions("affair:affairPoliceHome:edit")
	@RequestMapping(value = "save")
	public String save(AffairPoliceHome affairPoliceHome, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairPoliceHome)){
			return form(affairPoliceHome, model);
		}
		/*???????????????????????????*/
		String isImport = affairPoliceHome.getIsImport();
		/*genId?????????Id*/
		String genId = request.getParameter("genId");
		if (!StringUtils.isEmpty(isImport) && isImport.equals("true")&&affairPoliceHome.getIsAdd().equals("true")){
			affairPoliceHome.setIsNewRecord(true);
			if (!StringUtils.isEmpty(genId)){
				affairPoliceHome.setId(genId);
			}

		}
		affairPoliceHomeService.save(affairPoliceHome);
		addMessage(redirectAttributes, "??????????????????????????????");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairPoliceHomeForm";
	}
	
	@RequiresPermissions("affair:affairPoliceHome:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPoliceHome affairPoliceHome, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		affairPoliceHomeService.delete(affairPoliceHome);
		addMessage(redirectAttributes, "??????????????????????????????");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPoliceHome/?repage";
//		return "modules/affair/affairPoliceHomeFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairPoliceHome:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPoliceHomeService.deleteByIds(ids);
			childDao.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("????????????");
		}else{
			result.setSuccess(false);
			result.setMessage("??????????????????????????????");
		}
		return result;
	}
	@RequiresPermissions("affair:affairPoliceHome:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairPoliceHome affairPoliceHome, Model model) {
//		affairPoliceHome = affairPoliceHomeService.get(affairPoliceHome.getId());
		List<AffairPoliceHomeChild>  affairPoliceHomeChild = childDao.findByParentId(affairPoliceHome.getId());
		affairPoliceHome.setPoliceHomeChildList(affairPoliceHomeChild);
		model.addAttribute("affairPoliceHome", affairPoliceHome);
		if(affairPoliceHome.getFilePath() != null && affairPoliceHome.getFilePath().length() > 0){
			List<Map<String, String>> materialPathList = uploadController.filePathHandle(affairPoliceHome.getFilePath());
			model.addAttribute("filePathList",materialPathList );
		}
		return "modules/affair/affairPoliceHomeFormDetail";
	}
	/**
	 * ??????excel????????????
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairPoliceHome affairPoliceHome, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			/*Page<AffairPoliceHome> page = null;
			if(flag == true){
				page = affairPoliceHomeService.findPage(new Page<AffairPoliceHome>(request, response), affairPoliceHome);
			}else {
				page = affairPoliceHomeService.findPage(new Page<AffairPoliceHome>(request, response,-1), affairPoliceHome);
			}*/
			//??????????????????
			List<AffairPoliceHome> list = null;
			Page<AffairPoliceHome> page = null;
			if(flag == true){
				page = affairPoliceHomeService.findPage(new Page<AffairPoliceHome>(request, response), affairPoliceHome);
				list = page.getList();
			}else {
				page = affairPoliceHomeService.findPage(new Page<AffairPoliceHome>(request, response,-1), affairPoliceHome);
				list = page.getList();
			}
			//????????????????????????
			List<AffairPoliceHomeChild> policeHomeList = new ArrayList<>();
			int num = 1;
			for(AffairPoliceHome item : list){
				List<AffairPoliceHomeChild> childList = childDao.findByParentId(item.getId());
				if (childList==null || childList.size()<1){
					AffairPoliceHomeChild affairPoliceHomeChild = new AffairPoliceHomeChild();
					affairPoliceHomeChild.setOrderNum(num);//??????
					affairPoliceHomeChild.setPointUnit(item.getPointUnit());//???????????????
					affairPoliceHomeChild.setProject(item.getProject());//????????????
					/*affairPoliceHomeChild.setDevice("");//????????????
					affairPoliceHomeChild.setNums(0);//??????
					affairPoliceHomeChild.setPrice(0.0);//??????
					affairPoliceHomeChild.setSum(0.0);//??????*/
					policeHomeList.add(affairPoliceHomeChild);
					num++;
				}else{
					for(AffairPoliceHomeChild affairPoliceHomeChild : childList){
						affairPoliceHomeChild.setOrderNum(num);
						affairPoliceHomeChild.setPointDate(item.getPointDate());
						affairPoliceHomeChild.setUnit(item.getUnit());
						affairPoliceHomeChild.setPointUnit(item.getPointUnit());
						affairPoliceHomeChild.setProject(item.getProject());
						policeHomeList.add(affairPoliceHomeChild);
						num++;
					}
				}


			}
			/*list.forEach(item -> {
				List<AffairPoliceHomeChild> childList = childDao.findByParentId(item.getId());
				childList.forEach(affairPoliceHomeChild -> {
					affairPoliceHomeChild.setPointDate(item.getPointDate());
					affairPoliceHomeChild.setUnit(item.getUnit());
					affairPoliceHomeChild.setPointUnit(item.getPointUnit());
					affairPoliceHomeChild.setProject(item.getProject());
					policeHomeList.add(affairPoliceHomeChild);
				});
			});*/
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
			//??????
			ExportExcelNew exportExcelNew = new ExportExcelNew(1, AffairPoliceHome.class);
			exportExcelNew.setWb(wb);
			/*List<AffairPoliceHome> list =page.getList();*/
			exportExcelNew.setDataList(policeHomeList);
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
			addMessage(redirectAttributes, "????????????????????????????????????"+ex);
		}
		//??????
		return "redirect:" + adminPath + "/affair/affairPoliceHome/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			//??????
			List<AffairPoliceHome> list = ei.getDataList(AffairPoliceHome.class);
			for (AffairPoliceHome affairPoliceHome : list){
				try{
					BeanValidators.validateWithException(validator, affairPoliceHome);
					if (StringUtils.isNotBlank(affairPoliceHome.getPointUnit())
							&&StringUtils.isNotBlank(affairPoliceHome.getProject())){
						/*StringUtils.isNotBlank(affairPoliceHome.getUnit()) &&
						&&affairPoliceHome.getPointDate()!=null*/
						AffairPoliceHome policeHome=new AffairPoliceHome();
						//policeHome.setPointDate(affairPoliceHome.getPointDate());
						//policeHome.setUnit(affairPoliceHome.getUnit());
						policeHome.setPointUnit(affairPoliceHome.getPointUnit());
						policeHome.setProject(affairPoliceHome.getProject());
						AffairPoliceHome home = affairPoliceHomeService.getOne(policeHome);
						if (home!=null){
							//??????????????????????????????????????? ?????????????????????
							if (!StringUtils.isEmpty(affairPoliceHome.getPointUnit())) {
								AffairPoliceHomeChild affairPoliceHomeChild = new AffairPoliceHomeChild();
								affairPoliceHomeChild.setPoliceHomeId(home.getId());
								affairPoliceHomeChild.setDevice(affairPoliceHome.getDevice());
								affairPoliceHomeChild.setNums(affairPoliceHome.getNums());
								affairPoliceHomeChild.setPrice(affairPoliceHome.getPrice());
								affairPoliceHomeChild.setSum(affairPoliceHome.getSum());
								affairPoliceHomeChild.setContent(affairPoliceHome.getContent());
								affairPoliceHomeChild.setJingBan(affairPoliceHome.getJingBan());
								affairPoliceHomeChild.setUnitShRen(affairPoliceHome.getUnitShRen());
								affairPoliceHomeChild.setChuShOpinion(affairPoliceHome.getChuShOpinion());
								affairPoliceHomeChild.setJuShOpinion(affairPoliceHome.getJuShOpinion());
								affairPoliceHomeChild.preInsert();
								childDao.insert(affairPoliceHomeChild);
							}
						}else {
							//???????????????????????????????????? ???????????????
							if (!StringUtils.isEmpty(affairPoliceHome.getUnit())){
								String unitId = officeService.findByName(affairPoliceHome.getUnit());
								affairPoliceHome.setUnitId(unitId);
							}
							if (!StringUtils.isEmpty(affairPoliceHome.getPointUnit())){
								String pointUnitId = officeService.findByName(affairPoliceHome.getPointUnit());
								affairPoliceHome.setPointUnitId(pointUnitId);
								String parentId = officeDao.findParentIdById(pointUnitId);
								if(parentId.indexOf("34")!= -1){
									affairPoliceHome.setUnit("???????????????");
									affairPoliceHome.setUnitId("34");
								}else if(parentId.indexOf("156")!= -1){
									affairPoliceHome.setUnit("???????????????");
									affairPoliceHome.setUnitId("156");
								}
								else if(parentId.indexOf("95")!= -1){
									affairPoliceHome.setUnit("???????????????");
									affairPoliceHome.setUnitId("95");
								}else{
									affairPoliceHome.setUnit("?????????????????????");
									affairPoliceHome.setUnitId("1");
								}
							}
							IdGen idGen=new IdGen();
							String id = idGen.getNextId();
							affairPoliceHome.setId(id);
							affairPoliceHome.setIsNewRecord(true);
							//????????????
							if(affairPoliceHome.getDevice()!=null && !StringUtils.isEmpty(affairPoliceHome.getDevice())){
								AffairPoliceHomeChild affairPoliceHomeChild=new AffairPoliceHomeChild();
								affairPoliceHomeChild.setPoliceHomeId(affairPoliceHome.getId());
								affairPoliceHomeChild.setDevice(affairPoliceHome.getDevice());
								affairPoliceHomeChild.setNums(affairPoliceHome.getNums());
								affairPoliceHomeChild.setPrice(affairPoliceHome.getPrice());
								affairPoliceHomeChild.setSum(affairPoliceHome.getSum());
								affairPoliceHomeChild.setContent(affairPoliceHome.getContent());
								affairPoliceHomeChild.setJingBan(affairPoliceHome.getJingBan());
								affairPoliceHomeChild.setUnitShRen(affairPoliceHome.getUnitShRen());
								affairPoliceHomeChild.setChuShOpinion(affairPoliceHome.getChuShOpinion());
								affairPoliceHomeChild.setJuShOpinion(affairPoliceHome.getJuShOpinion());
								affairPoliceHomeChild.preInsert();
								childDao.insert(affairPoliceHomeChild);
							}
							affairPoliceHomeService.save(affairPoliceHome);
						}
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
					failureMsg.append("??????????????????:"+affairPoliceHome.getPointUnit()+")"+" ???????????????"+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "????????? "+failureNum+" ???????????????????????????");
			}
			addMessage(redirectAttributes, "??????????????? "+successNum+" ???"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "??????????????????????????????"+e.getMessage());
		}
		redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view";
	}

	/*????????????????????????*/
	@RequestMapping(value = "exportChildRecordFile")
	public String exportChildRecordFile(AffairPoliceHome affairPoliceHome, HttpServletResponse response) {
		AffairPoliceHomeChild policeHomeChild = new AffairPoliceHomeChild();
		policeHomeChild.setPoliceHomeId(affairPoliceHome.getId());
		List<AffairPoliceHomeChild> exportList = new ArrayList<>();//???????????????list
		List<AffairPoliceHomeChild> list =childDao.findByParentId(affairPoliceHome.getId());
		if (StringUtils.isBlank(affairPoliceHome.getId())){
			list = new ArrayList<>();
		}
		if(list!=null && list.size()>0){
			int num = 1;
			for(AffairPoliceHomeChild childItem: list){
				AffairPoliceHome tempPoliceHome = affairPoliceHomeService.get(childItem.getPoliceHomeId());
				childItem.setPointUnit(tempPoliceHome.getPointUnit());//???????????????
				childItem.setProject(tempPoliceHome.getProject());//????????????
				childItem.setOrderNum(num);
				num++;
				exportList.add(childItem);
			}
		}

		String fileSeperator = File.separator;
		String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
		//String fileName = "??????????????????-?????????.xlsx";
		String fileName = "????????????????????????.xlsx";
		String path = filePath+fileName;
		XSSFWorkbook wb = null;
		try
		{
			InputStream inputStream = new FileInputStream(path);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(1, AffairPoliceHomeChild.class);
			exportExcelNew.setWb(wb);
			exportExcelNew.setDataList(exportList);
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
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairPoliceHome/?repage";
	}
	//??????????????????
	@RequestMapping(value = "template/import")
	public String download(HttpServletResponse response, HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String id ="";
		if(null != request.getParameter("id") && !"".equals(request.getParameter("id"))){
			id=request.getParameter("id").toString();
			request.setAttribute("id",id);
		}
		if(null != request.getParameter("fileName") && !"".equals(request.getParameter("fileName"))){
			String template=request.getParameter("fileName").toString();
			request.setAttribute("template",template);
		}
		request.setAttribute("url","/affair/affairPoliceHome/importChildRecordFile");
		return "modules/affair/child_template_import";
	}

	/**
	 * ????????????
	 * @param file
	 * @param redirectAttributes
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "importChildRecordFile", method= RequestMethod.POST)
	public String importChildRecordFile(MultipartFile file, RedirectAttributes redirectAttributes,String id,Model model) {

		AffairPoliceHome affairPoliceHome = new AffairPoliceHome();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<AffairPoliceHomeChild> list = ei.getDataList(AffairPoliceHomeChild.class);
			affairPoliceHome.setPoliceHomeChildList(list);
			for (AffairPoliceHomeChild affairPoliceHomeChild : list){
				try{
					BeanValidators.validateWithException(validator, affairPoliceHomeChild);
					//??????????????????id
					String orgId = officeService.findByName(affairPoliceHomeChild.getUnit());
					if(orgId != null){
						affairPoliceHomeChild.setUnitId(orgId);
					}
					if(affairPoliceHomeChild.getPointUnit()!=null && !affairPoliceHomeChild.getPointUnit().isEmpty()){
						affairPoliceHomeChild.setPoliceHomeId(id);
					}
					if(affairPoliceHomeChild.getDevice()!=null && !affairPoliceHomeChild.getDevice().isEmpty()){
						affairPoliceHomeChild.preInsert();
						childDao.insert(affairPoliceHomeChild);
					}
//					affairTypicalPersonService.save(affairPoliceHomeChild);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(????????????:"+affairPoliceHomeChild.getDevice()+")"+" ???????????????"+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "????????? "+failureNum+" ???????????????????????????");
			}
			addMessage(redirectAttributes, "??????????????? "+successNum+" ???"+failureMsg);
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "??????????????????????????????"+e.getMessage());
		}
		addMessage(redirectAttributes, "??????????????? ");
		redirectAttributes.addFlashAttribute("result","success");
		redirectAttributes.addFlashAttribute("affairPoliceHome",affairPoliceHome);
		model.addAttribute("result","success");
//		return "redirect:" + adminPath + "/affair/affairTypicalPerson/template/import";
		model.addAttribute("url","/affair/affairPoliceHome/importChildRecordFile");
		return "modules/affair/child_template_import";
	}

	@RequestMapping("getChildById")
	@ResponseBody
	public Result getChildById(String id){
		Result result= new Result();
		try {
			List<AffairPoliceHomeChild> list = childDao.findByParentId(id);
			result.setResult(list);
			result.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}
}