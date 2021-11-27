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
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyMember;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
import com.thinkgem.jeesite.modules.affair.service.AffairPartyMemberService;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
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
 * 党员花名册Controller
 * @author eav.liu
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPartyMember")
public class AffairPartyMemberController extends BaseController {

	@Autowired
	private AffairPartyMemberService affairPartyMemberService;

	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;

	/**
	 * 顶级党组织的id
	 */
	public static final String TOP_OFFICE_ID = "1";
	
	@ModelAttribute
	public AffairPartyMember get(@RequestParam(required=false) String id) {
		AffairPartyMember entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPartyMemberService.get(id);
		}
		if (entity == null){
			entity = new AffairPartyMember();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairPartyMember:view")
	@RequestMapping(value = {""})
	public String index(AffairPartyMember affairPartyMember) {
		return "modules/affair/affairPartyMemberIndex";
	}

	@RequiresPermissions("affair:affairPartyMember:view")
	@RequestMapping(value = {"list"})
	public String list(AffairPartyMember affairPartyMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairPartyMember.getPartyBranchId());
		model.addAttribute("pmId", affairPartyMember.getCardNum());
		Page<AffairPartyMember> page = affairPartyMemberService.findPage(new Page<AffairPartyMember>(request, response), affairPartyMember); 
		model.addAttribute("page", page);
		/*如果是弹窗 重定向到组织关系转接  隐藏党员tab页*/
		String isDialog=request.getParameter("isDialog");
		if ("isDialog".equals(isDialog)){
			return "redirect:"+Global.getAdminPath()+"/affair/affairRelationshipTransfer/list?repage&partyBranchId="+affairPartyMember.getPartyBranchId()+"&IdNumber="+affairPartyMember.getCardNum();
		}
		return "modules/affair/affairPartyMemberList";
	}

	@RequiresPermissions("affair:affairPartyMember:view")
	@RequestMapping(value = "form")
	public String form(AffairPartyMember affairPartyMember, Model model) {
		model.addAttribute("affairPartyMember", affairPartyMember);
		return "modules/affair/affairPartyMemberForm";
	}

	@RequiresPermissions("affair:affairPartyMember:edit")
	@RequestMapping(value = "save")
	public String save(AffairPartyMember affairPartyMember, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairPartyMember)){
			return form(affairPartyMember, model);
		}
		affairPartyMemberService.save(affairPartyMember);
		addMessage(redirectAttributes, "保存党员信息成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairPartyMemberForm";
	}
	
	@RequiresPermissions("affair:affairPartyMember:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPartyMember affairPartyMember, RedirectAttributes redirectAttributes) {
		affairPartyMemberService.delete(affairPartyMember);
		addMessage(redirectAttributes, "删除党员信息成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPartyMember/list?repage&partyBranchId="+affairPartyMember.getPartyBranchId()+"&cardNum="+affairPartyMember.getCardNum();
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairPartyMember:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPartyMemberService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	/**
	 * form表单详情
	 * @param affairPartyMember
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPartyMember:view")
	@RequestMapping(value = {"formDetail"})
	public String formDetail(AffairPartyMember affairPartyMember, Model model) {
		model.addAttribute("affairPartyMember", affairPartyMember);
		//String transferId = affairRelationshipTransferService.findLastByIdNumber(affairPartyMember.getCardNum());
		//model.addAttribute("transferId", transferId);
		return "modules/affair/affairPartyMemberFormDetail";
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
			List<AffairPartyMember> list = ei.getDataList(AffairPartyMember.class);
			for (AffairPartyMember affairPartyMember : list){
				try{
					//根据身份证号去重
					if(StringUtils.isNotBlank(affairPartyMember.getCardNum())){
						List<String> oldList = affairPartyMemberService.findListByIdNo(affairPartyMember.getCardNum());
						if(oldList != null && oldList.size() >0){
							affairPartyMemberService.deleteByIdNo(affairPartyMember.getCardNum());
						}
						//党支部绑定党支部id
						String partyBranchId = affairGeneralSituationService.findByName(affairPartyMember.getPartyBranch());
						affairPartyMember.setPartyBranchId(partyBranchId);
						BeanValidators.validateWithException(validator, affairPartyMember);
						affairPartyMemberService.save(affairPartyMember);
						successNum++;
					}
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
                    failureMsg.append(affairPartyMember.getName()+"(身份证号码:"+affairPartyMember.getCardNum()+")"+" 导入失败："+ex.getMessage());
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

	/**
	 * 导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairPartyMember affairPartyMember, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairPartyMember> page = null;
			if(flag == true){
				page = affairPartyMemberService.findPage(new Page<AffairPartyMember>(request, response), affairPartyMember);
			}else{
				page = affairPartyMemberService.findPage(new Page<AffairPartyMember>(request, response,-1), affairPartyMember);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairPartyMember.class);
			exportExcelNew.setWb(wb);
			List<AffairPartyMember> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairPartyMember/list?repage&partyBranchId="+affairPartyMember.getPartyBranchId()+"&cardNum="+affairPartyMember.getCardNum();
	}

	/**
	 * 党员树
	 * @param partyBranchId
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String partyBranchId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<AffairPartyMember> list = affairPartyMemberService.findMemberByPartyBranchId(partyBranchId);
		for (int i=0; i<list.size(); i++){
			AffairPartyMember e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "pm_"+e.getCardNum());
			map.put("pId", partyBranchId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}

	@ResponseBody
	@RequestMapping(value = "newTreeData")
	public List<Map<String, Object>> newTreeData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<AffairPartyMember> list = affairPartyMemberService.findMemberByPartyBranchId(officeId);
		for (int i=0; i<list.size(); i++){
			AffairPartyMember e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getCardNum());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 统计分析-党建-党员名册-在职、离退休党员数统计
	 * @param affairPartyMember
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"echartWorkPlaceFindPage"})
	public String echartWorkPlaceFindPage(AffairPartyMember affairPartyMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairPartyMember> page;
		if(affairPartyMember.getPbId() != null && !TOP_OFFICE_ID.equals(affairPartyMember.getPbId())) {
			List<String> ids = affairGeneralSituationService.findAllChildIdById(affairPartyMember.getPbId());
			if(CollectionUtils.isEmpty(ids)) {
				page = affairPartyMemberService.echartWorkPlaceFindPageByPbId(new Page<AffairPartyMember>(request, response), affairPartyMember);
			} else {
				ids.add(affairPartyMember.getPbId());
				affairPartyMember.setPbIds(ids);
				page = affairPartyMemberService.echartWorkPlaceFindPageByPbIds(new Page<AffairPartyMember>(request, response), affairPartyMember);
			}
		} else {
			affairPartyMember.setPbId(null);
			page = affairPartyMemberService.echartWorkPlaceFindPageByPbId(new Page<AffairPartyMember>(request, response), affairPartyMember);
		}
		model.addAttribute("page", page);
		model.addAttribute("affairPartyMember", affairPartyMember);
		return "modules/affair/affairPartyMemberEchartWorkPlace";
	}

    /**
     * 统计分析-党建-党员名册-人员类别统计
     * @param affairPartyMember
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"echartCategoryFindPage"})
    public String echartCategoryFindPage(AffairPartyMember affairPartyMember, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AffairPartyMember> page;
        if(affairPartyMember.getPbId() != null && !TOP_OFFICE_ID.equals(affairPartyMember.getPbId())) {
            List<String> ids = affairGeneralSituationService.findAllChildIdById(affairPartyMember.getPbId());
            if(CollectionUtils.isEmpty(ids)) {
                page = affairPartyMemberService.echartCategoryFindPageByPbId(new Page<AffairPartyMember>(request, response), affairPartyMember);
            } else {
                ids.add(affairPartyMember.getPbId());
                affairPartyMember.setPbIds(ids);
                page = affairPartyMemberService.echartCategoryFindPageByPbIds(new Page<AffairPartyMember>(request, response), affairPartyMember);
            }
        } else {
            affairPartyMember.setPbId(null);
            page = affairPartyMemberService.echartCategoryFindPageByPbId(new Page<AffairPartyMember>(request, response), affairPartyMember);
        }
        model.addAttribute("page", page);
        model.addAttribute("affairPartyMember", affairPartyMember);
        return "modules/affair/affairPartyMemberEchartCategory";
    }

	/**
	 * 统计分析-党建-党员名册-民族统计
	 * @param affairPartyMember
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"echartNationFindPage"})
	public String echartNationFindPage(AffairPartyMember affairPartyMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairPartyMember> page;
		if(affairPartyMember.getPbId() != null && !TOP_OFFICE_ID.equals(affairPartyMember.getPbId())) {
			List<String> ids = affairGeneralSituationService.findAllChildIdById(affairPartyMember.getPbId());
			if(CollectionUtils.isEmpty(ids)) {
				page = affairPartyMemberService.echartNationFindPageByPbId(new Page<AffairPartyMember>(request, response), affairPartyMember);
			} else {
				ids.add(affairPartyMember.getPbId());
				affairPartyMember.setPbIds(ids);
				page = affairPartyMemberService.echartNationFindPageByPbIds(new Page<AffairPartyMember>(request, response), affairPartyMember);
			}
		} else {
			affairPartyMember.setPbId(null);
			page = affairPartyMemberService.echartNationFindPageByPbId(new Page<AffairPartyMember>(request, response), affairPartyMember);
		}
		model.addAttribute("page", page);
		model.addAttribute("affairPartyMember", affairPartyMember);
		return "modules/affair/affairPartyMemberEchartNation";
	}

	/**
	 * 统计分析-党建-党员名册-性别统计
	 * @param affairPartyMember
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"echartSexFindPage"})
	public String echartSexFindPage(AffairPartyMember affairPartyMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairPartyMember> page;
		if(affairPartyMember.getPbId() != null && !TOP_OFFICE_ID.equals(affairPartyMember.getPbId())) {
			List<String> ids = affairGeneralSituationService.findAllChildIdById(affairPartyMember.getPbId());
			if(CollectionUtils.isEmpty(ids)) {
				page = affairPartyMemberService.echartSexFindPageByPbId(new Page<AffairPartyMember>(request, response), affairPartyMember);
			} else {
				ids.add(affairPartyMember.getPbId());
				affairPartyMember.setPbIds(ids);
				page = affairPartyMemberService.echartSexFindPageByPbIds(new Page<AffairPartyMember>(request, response), affairPartyMember);
			}
		} else {
			affairPartyMember.setPbId(null);
			page = affairPartyMemberService.echartSexFindPageByPbId(new Page<AffairPartyMember>(request, response), affairPartyMember);
		}
		model.addAttribute("page", page);
		model.addAttribute("affairPartyMember", affairPartyMember);
		return "modules/affair/affairPartyMemberEchartSex";
	}




}