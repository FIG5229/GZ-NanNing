/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairTjRegister;
import com.thinkgem.jeesite.modules.affair.service.AffairTjRegisterService;
import com.thinkgem.jeesite.modules.affair.service.AffairTwBaseService;
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
 * 团籍注册Controller
 * @author cecil.li
 * @version 2019-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTjRegister")
public class AffairTjRegisterController extends BaseController {

	@Autowired
	private AffairTwBaseService affairTwBaseService;
	@Autowired
	private AffairTjRegisterService affairTjRegisterService;

	
	@ModelAttribute
	public AffairTjRegister get(@RequestParam(required=false) String id) {
		AffairTjRegister entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTjRegisterService.get(id);
		}
		if (entity == null){
			entity = new AffairTjRegister();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairTjRegister:view")
	@RequestMapping(value = {""})
	public String index() {
		return "modules/affair/affairTjRegisterIndex";
	}
	
	@RequiresPermissions("affair:affairTjRegister:view")
	@RequestMapping(value = {"list"})
	public String list(AffairTjRegister affairTjRegister, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTjRegister> page = affairTjRegisterService.findPage(new Page<AffairTjRegister>(request, response), affairTjRegister); 
		model.addAttribute("page", page);
		return "modules/affair/affairTjRegisterList";
	}

	@RequiresPermissions("affair:affairTjRegister:view")
	@RequestMapping(value = "form")
	public String form(AffairTjRegister affairTjRegister, Model model) {
		model.addAttribute("affairTjRegister", affairTjRegister);
		return "modules/affair/affairTjRegisterForm";
	}

	@RequiresPermissions("affair:affairTjRegister:edit")
	@RequestMapping(value = "save")
	public String save(AffairTjRegister affairTjRegister, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairTjRegister)){
			return form(affairTjRegister, model);
		}
		affairTjRegisterService.save(affairTjRegister);
		addMessage(redirectAttributes, "保存团员名册成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTjRegisterForm";
	}
	
	@RequiresPermissions("affair:affairTjRegister:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTjRegister affairTjRegister, RedirectAttributes redirectAttributes, String partyBranchId) {
		affairTjRegisterService.delete(affairTjRegister);
		addMessage(redirectAttributes, "删除团员名册成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTjRegister/list?partyBranchId="+partyBranchId;
	}

	@ResponseBody
	@RequiresPermissions("affair:affairTjRegister:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTjRegisterService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	/**
	 * 修改工作单位的弹窗
	 *
	 * @param ids
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairTjRegister:edit")
	@RequestMapping(value = {"editTwBaseDialog"})
	public String editGonghuiDialog(String ids, Model model) {
		model.addAttribute("editPersonIds", ids);
		return "modules/affair/affairTjRegisterTree";
	}
	@RequiresPermissions("affair:affairTjRegister:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTjRegister affairTjRegister, Model model) {
		model.addAttribute("affairTjRegister", affairTjRegister);
		return "modules/exam/affairTjRegisterFormDetail";
	}
	/**
	 * 保存批量修改工作单位
	 *
	 * @param
	 * @param request
	 * @return
	 */
	@RequiresPermissions("affair:affairTjRegister:edit")
	@RequestMapping(value = {"editTwBase"})
	public String editWorkUnit(AffairTjRegister affairTjRegister , HttpServletRequest request) {
		String partyBranchId = affairTjRegister.getPartyBranchId();
		String partyBranch = affairTjRegister.getPartyBranch();
		String idsStr = request.getParameter("editPersonIds");
		String[] idsArray = idsStr.split(",");
		List<String> userList = new ArrayList<String>();
		Collections.addAll(userList,idsArray);
		List <AffairTjRegister> list = affairTjRegisterService.findByIds(userList);
		for (AffairTjRegister  affairTjRegisterFromDb:list){
			affairTjRegisterFromDb.setPartyBranchId(partyBranchId);
			affairTjRegisterFromDb.setPartyBranch(partyBranch);
			affairTjRegisterService.save(affairTjRegisterFromDb);
		}
		request.setAttribute("saveResult", "sucess");
		return "modules/affair/affairTjRegisterTree";
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
	public String exportExcelByTemplate(AffairTjRegister affairTjRegister, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairTjRegister> page = null;
			if(flag == true){
				page = affairTjRegisterService.findPage(new Page<AffairTjRegister>(request, response), affairTjRegister);
			}else {
				page = affairTjRegisterService.findPage(new Page<AffairTjRegister>(request, response,-1), affairTjRegister);
			}
/*
			Page<AffairTjRegister> page = affairTjRegisterService.findPage(new Page<AffairTjRegister>(request, response,-1), affairTjRegister);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTjRegister.class);
			exportExcelNew.setWb(wb);
			List<AffairTjRegister> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTjRegister/list?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairTjRegister> list = ei.getDataList(AffairTjRegister.class);
			for (AffairTjRegister affairTjRegister : list){
				try{
					//根据身份证号去重
					if(StringUtils.isNotBlank(affairTjRegister.getIdNumber())){
						List<String> oldList = affairTjRegisterService.findListByIdNo(affairTjRegister.getIdNumber());
						if(oldList != null && oldList.size() >0){
							affairTjRegisterService.deleteByIdNo(affairTjRegister.getIdNumber());
						}
						String partyBranchId = affairTwBaseService.findByName(affairTjRegister.getPartyBranch());
						if(partyBranchId==null){
							//如果表格填写的 团支部名称 在系统中不存在，则不存储该用户

							throw new NullPointerException("所在团组织"+affairTjRegister.getPartyBranch()+"，在该系统中未发现;");
						}else{
							affairTjRegister.setPartyBranchId(partyBranchId);
							BeanValidators.validateWithException(validator, affairTjRegister);
							affairTjRegisterService.save(affairTjRegister);
							successNum++;
						}
					}else{
						throw new NullPointerException("该条数据，身份证信息为空");
					}
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureNum++;
					failureMsg.append("<br>姓名:"+affairTjRegister.getName()+"(身份证号码:"+affairTjRegister.getIdNumber()+")"+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，失败信息如下：");
			}
			addMessage(redirectAttributes, "从文件中读取到"+list.size()+"条数据，已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		//redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view?id=affair_affairTjRegister";
	}

	/**
	 * 团员树
	 * @param officeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String officeId) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<AffairTjRegister> list = affairTjRegisterService.findMemberByPartyBranchId(officeId);
		for (int i=0; i<list.size(); i++){
			AffairTjRegister e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "tym_"+e.getIdNumber());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 根据统计数据时的lable反查详细数据
	 * @param affairTjRegister
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "echart")
	public String education(AffairTjRegister affairTjRegister, Model model, HttpServletRequest request, HttpServletResponse response){

		Page<AffairTjRegister> page = affairTjRegisterService.findEducationPage(new Page<AffairTjRegister>(request, response), affairTjRegister);
		model.addAttribute("page", page);
		//公用页面 显示学历字段
		model.addAttribute("type","1");
		return "modules/charts/chartsTjRegisterList";
	}

	@RequestMapping(value = "echart/political")
	public String political(AffairTjRegister affairTjRegister,Model model, HttpServletRequest request, HttpServletResponse response){
		Page<AffairTjRegister> page = affairTjRegisterService.findPoliticalPage(new Page<AffairTjRegister>(request, response), affairTjRegister);
		model.addAttribute("page", page);
		return "modules/charts/chartsTjRegisterList";
	}

	@RequestMapping(value = "personnelAge")
	public String personnelAge(AffairTjRegister affairTjRegister,Model model, HttpServletRequest request, HttpServletResponse response){
		Page<AffairTjRegister> page = affairTjRegisterService.findPoliticalByAgePage(new Page<AffairTjRegister>(request, response), affairTjRegister);
		model.addAttribute("page", page);
		return "modules/charts/chartsTjRegisterList";
	}

	@RequestMapping("cadresDetail")
	public String cadresDetail(AffairTjRegister affairTjRegister,Model model, HttpServletRequest request, HttpServletResponse response){
		Page<AffairTjRegister> page = affairTjRegisterService.findCadresPage(new Page<AffairTjRegister>(request, response), affairTjRegister);
		model.addAttribute("page", page);
		return "modules/charts/chartsTjRegisterList";
	}
}