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
import com.thinkgem.jeesite.modules.affair.entity.AffairClassMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairGeneralSituation;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliticalGroup;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkBuild;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
import com.thinkgem.jeesite.modules.affair.service.AffairPoliticalGroupService;
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
import java.util.Map;

/**
 * 党组织概况Controller
 * @author eav.liu
 * @version 2019-10-31
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairGeneralSituation")
public class AffairGeneralSituationController extends BaseController {

	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairPoliticalGroupService affairPoliticalGroupService;
	
	@ModelAttribute
	public AffairGeneralSituation get(@RequestParam(required=false) String id) {
		AffairGeneralSituation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairGeneralSituationService.get(id);
		}
		if (entity == null){
			entity = new AffairGeneralSituation();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairGeneralSituation:view")
	@RequestMapping(value = {""})
	public String index(AffairGeneralSituation affairGeneralSituation) {
		return "modules/affair/affairGeneralSituationIndex";
	}

	@RequiresPermissions("affair:affairGeneralSituation:view")
	@RequestMapping(value = {"list"})
	public String list(AffairGeneralSituation affairGeneralSituation, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairGeneralSituation.getTreeId());
		AffairGeneralSituation affairGeneralSituation1 = affairGeneralSituationService.get(affairGeneralSituation.getTreeId());
		if(affairGeneralSituation1!=null){
			model.addAttribute("parentIds",affairGeneralSituation1.getParentIds());
		}else{
			model.addAttribute("parentIds",affairGeneralSituation.getParentIds());
		}
		AffairGeneralSituation paramObj;
		if(request.getParameter("isTree") != null){
			paramObj =new AffairGeneralSituation();
			///paramObj.setId(affairGeneralSituation.getId());
			paramObj.setTreeId(affairGeneralSituation.getTreeId());
		}else{
			paramObj= affairGeneralSituation;
		}
		Page<AffairGeneralSituation> page = affairGeneralSituationService.findPage(new Page<AffairGeneralSituation>(request, response), paramObj);
		model.addAttribute("page", page);

		//如果父节点的Id不为空 并且父Id是加密的 父Id集长度大于120（随便写的，其他父Id集长度都很短）
		/*党总支下的党支部被认定为党小组*/
		//从树传过来的是parentIds		tab页面传过来的是parentId
		String parentId= affairGeneralSituation.getParentIds();
		if (parentId==null){
			parentId=affairGeneralSituation.getParentId();
		}
		//党支部页面没有parentId
		if (parentId!=null){
				//组织数既不是北海党总支也不是南宁党总支
			if ( !parentId.equals("51a154399faf457f8e1ef12136cf1260") && !parentId.equals("8e66c12b4d3a4fc3ac780c3b44cb1078")){
				//判断当前机构为党支部还是党小组
				AffairPoliticalGroup affairPoliticalGroup = affairPoliticalGroupService.findInfoByTreeId(affairGeneralSituation.getTreeId());
				if(affairPoliticalGroup!=null){
					return "redirect:"+Global.getAdminPath()+"/affair/affairPoliticalGroup/list?repage&parentIds="+affairGeneralSituation.getParentIds()+"&treeId="+affairGeneralSituation.getTreeId();
				}
				/*if (!StringUtils.isEmpty(affairGeneralSituation.getParentIds()) && affairGeneralSituation.getParentIds().length()>10 ){
					return "redirect:"+Global.getAdminPath()+"/affair/affairPoliticalGroup/list?repage&parentIds="+affairGeneralSituation.getParentIds()+"&treeId="+affairGeneralSituation.getTreeId();
					}*/
			}
		}
		return "modules/affair/affairGeneralSituationList";
	}

	@RequiresPermissions("affair:affairGeneralSituation:view")
	@RequestMapping(value = "form")
	public String form(AffairGeneralSituation affairGeneralSituation, Model model) {
		if(affairGeneralSituation.getId() != null && (affairGeneralSituation.getOfPartyOrgId()==null || affairGeneralSituation.getOfPartyOrganization()==null)){
			if(StringUtils.isNotBlank(affairGeneralSituation.getParentId())){
				AffairGeneralSituation parentOrg = affairGeneralSituationService.get(affairGeneralSituation.getParentId());//查询父组织
				affairGeneralSituation.setOfPartyOrgId(parentOrg.getId());
				affairGeneralSituation.setOfPartyOrganization(parentOrg.getPartyOrganization());
			}
		}
		model.addAttribute("affairGeneralSituation", affairGeneralSituation);
		return "modules/affair/affairGeneralSituationForm";
	}

	@RequiresPermissions("affair:affairGeneralSituation:edit")
	@RequestMapping(value = "save")
	public String save(AffairGeneralSituation affairGeneralSituation, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairGeneralSituation)){
			return form(affairGeneralSituation, model);
		}

		String status = affairGeneralSituation.getStatus();
		if (StringUtils.isBlank(status)){
			affairGeneralSituation.setStatus("1");
		}
		if(!"".equals(affairGeneralSituation.getId()) && affairGeneralSituation.getId() != null){
			affairGeneralSituation.setStatus("1");
		}
		affairGeneralSituationService.save(affairGeneralSituation);
		addMessage(redirectAttributes, "保存党组织概况成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairGeneralSituationForm";
	}
	
	@RequiresPermissions("affair:affairGeneralSituation:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairGeneralSituation affairGeneralSituation, RedirectAttributes redirectAttributes) {
		affairGeneralSituationService.delete(affairGeneralSituation);
		addMessage(redirectAttributes, "删除党组织概况成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairGeneralSituation/list?repage&treeId="+affairGeneralSituation.getTreeId();
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairGeneralSituation:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairGeneralSituationService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 详情  暂不使用
	 * @param affairGeneralSituation
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairGeneralSituation:view")
	@RequestMapping(value = {"detail"})
	public String detail(AffairGeneralSituation affairGeneralSituation, Model model) {
		model.addAttribute("affairGeneralSituation", affairGeneralSituation);
		return "modules/affair/affairGeneralSituationDetail";
	}

	/**
	 * form表单详情
	 * @param affairGeneralSituation
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairGeneralSituation:view")
	@RequestMapping(value = {"formDetail"})
	public String formDetail(AffairGeneralSituation affairGeneralSituation, Model model) {
		model.addAttribute("affairGeneralSituation", affairGeneralSituation);
		return "modules/affair/affairGeneralSituationFormDetail";
	}

	/**
	 * 导入excel
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
			List<AffairGeneralSituation> list = ei.getDataList(AffairGeneralSituation.class);
			for (AffairGeneralSituation affairGeneralSituation : list){
				try{
					//根据党组织名称去重(目前党组织名称为全称，只根据党组织名称去重即可)
					if(StringUtils.isNotBlank(affairGeneralSituation.getPartyOrganization())){
						List<AffairGeneralSituation> oldList = affairGeneralSituationService.findByPartyOrganization(affairGeneralSituation.getPartyOrganization());
						if(oldList != null && oldList.size() > 0){
							//affairGeneralSituationService.deleteByPartyOrganization(affairGeneralSituation.getPartyOrganization());
							for (AffairGeneralSituation oldGeneralSituation :oldList) {
								affairGeneralSituation.setId(oldGeneralSituation.getId());
								affairGeneralSituation.setParentId(oldGeneralSituation.getParentId());
								affairGeneralSituation.setOfPartyOrgId(oldGeneralSituation.getOfPartyOrgId());
								String orgId = officeService.findByName(affairGeneralSituation.getUnit());
								affairGeneralSituation.setUnitId(orgId);
								//导入的excel模板中加了下拉框数据验证
								BeanValidators.validateWithException(validator, affairGeneralSituation);
								affairGeneralSituationService.save(affairGeneralSituation);
							}
							successNum++;
						}else{
							//单位绑定单位id
							String orgId = officeService.findByName(affairGeneralSituation.getUnit());
							affairGeneralSituation.setUnitId(orgId);
							//导入的excel模板中加了下拉框数据验证
							BeanValidators.validateWithException(validator, affairGeneralSituation);
							affairGeneralSituationService.save(affairGeneralSituation);
							successNum++;
						}
					}else{
						failureMsg.append("党组织名称不能为空; ");
						failureNum++;
					}

				}catch(ConstraintViolationException ex){
					ex.printStackTrace();
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					ex.printStackTrace();
					failureMsg.append("(党组织名称:"+affairGeneralSituation.getPartyOrganization()+")"+" 导入失败："+ex.getMessage());
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
		//redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view?id=affair_affairGeneralSituation";
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
	public String exportExcelByTemplate(AffairGeneralSituation affairGeneralSituation, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairGeneralSituation> page = null;
			if(flag == true){
				page = affairGeneralSituationService.findPage(new Page<AffairGeneralSituation>(request, response), affairGeneralSituation);
			}else{
				page = affairGeneralSituationService.findPage(new Page<AffairGeneralSituation>(request, response,-1), affairGeneralSituation);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairGeneralSituation.class);
			exportExcelNew.setWb(wb);
			List<AffairGeneralSituation> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairGeneralSituation/list?repage&treeId="+affairGeneralSituation.getTreeId();
	}

	/**
	 * 获得党组织概况树形选择器 ，未放入缓存中
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(HttpServletRequest request,@RequestParam(required=false) Boolean isAll) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<AffairGeneralSituation> list = affairGeneralSituationService.findTreeData(isAll);
		for (int i=0; i<list.size(); i++){
			AffairGeneralSituation e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("name", e.getPartyOrganization());
			mapList.add(map);
			if("pm".equals(request.getParameter("flag"))){
				map.put("isParent", true);
			}
		}
		return mapList;
	}

	/**
	 * 获得党组织概况树形选择器 ，未放入缓存中
	 * 党组织页面所采用树
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "treeData2")
	public List<Map<String, Object>> treeData2(HttpServletRequest request,@RequestParam(required=false) Boolean isAll) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<AffairGeneralSituation> list = affairGeneralSituationService.findTreeData2(isAll);
		for (int i=0; i<list.size(); i++){
			AffairGeneralSituation e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("name", e.getPartyOrganization());
			mapList.add(map);
			if("pm".equals(request.getParameter("flag"))){
				map.put("isParent", true);
			}
		}
		return mapList;
	}

	/**
	 * 纪检的党组织纪律处分的党组织概况树形选择器不加权限 ，要求获得全部党组织
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "jLChFtreeData")
	public List<Map<String, Object>> jLChFtreeData() {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<AffairGeneralSituation> list = affairGeneralSituationService.findJLChFtreeData();
		for (int i=0; i<list.size(); i++){
			AffairGeneralSituation e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("name", e.getPartyOrganization());
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 党建的党员组织移交转接，申请转入组织，树形选择器不加权限
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "transfertreeData")
	public List<Map<String, Object>> transfertreeData() {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<AffairGeneralSituation> list = affairGeneralSituationService.transfertreeData();
		for (int i=0; i<list.size(); i++){
			AffairGeneralSituation e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("name", e.getPartyOrganization());
			mapList.add(map);
		}
		return mapList;
	}

	@RequestMapping("assessDoneDetail")
	public String assessDetail(AffairGeneralSituation affairGeneralSituation, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<AffairGeneralSituation> page = affairGeneralSituationService.findAssessDetailPage(new Page<AffairGeneralSituation>(request,response),affairGeneralSituation);
		model.addAttribute("page",page);
		return "modules/charts/chartGeneralSituationList";
	}


	/*审核*/
	@RequiresPermissions("affair:affairGeneralSituation:edit")
	@RequestMapping(value = "examine")
	public String examine(AffairGeneralSituation affairGeneralSituation, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairGeneralSituation)){
			return form(affairGeneralSituation, model);
		}
		affairGeneralSituationService.save(affairGeneralSituation);
		addMessage(redirectAttributes, "审核成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairGeneralSituationForm";
	}

	@RequiresPermissions("affair:affairGeneralSituation:edit")
	@RequestMapping(value = "report")
	public String report(AffairGeneralSituation affairGeneralSituation, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairGeneralSituation)){
			return form(affairGeneralSituation, model);
		}
		affairGeneralSituation.setStatus("2");
		affairGeneralSituationService.save(affairGeneralSituation);
		addMessage(redirectAttributes, "上报成功");
		request.setAttribute("saveResult","sucess");
		return "redirect:"+Global.getAdminPath()+"/affair/affairGeneralSituation/list?repage&treeId="+affairGeneralSituation.getTreeId();
	}


	@RequiresPermissions("affair:affairGeneralSituation:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairGeneralSituationCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairGeneralSituation:edit")
	@RequestMapping(value = {"shenHeSave"})
	public Result shenHeSave(AffairGeneralSituation affairGeneralSituation) {
		affairGeneralSituationService.shenHeSave(affairGeneralSituation);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}


}