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
import com.thinkgem.jeesite.modules.affair.entity.AffairComment;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyMember;
import com.thinkgem.jeesite.modules.affair.service.AffairCommentService;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
import com.thinkgem.jeesite.modules.affair.service.AffairPartyMemberService;
import com.thinkgem.jeesite.modules.sys.service.SysOfficesService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
import java.util.Calendar;
import java.util.List;

/**
 * 党员民主评议Controller
 * @author eav.liu
 * @version 2019-11-11
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairComment")
public class AffairCommentController extends BaseController {

	@Autowired
	private AffairCommentService affairCommentService;

	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;

	@Autowired
	private AffairPartyMemberService affairPartyMemberService;
	@Autowired
	private SysOfficesService sysOfficesService;
	/**
	 * 顶级党组织的id
	 */
	public static final String TOP_OFFICE_ID = "1";

	@ModelAttribute
	public AffairComment get(@RequestParam(required=false) String id) {
		AffairComment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCommentService.get(id);
		}
		if (entity == null){
			entity = new AffairComment();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairComment:view")
	@RequestMapping(value = {""})
	public String index() {
		return "modules/affair/affairCommentIndex";
	}

	@RequiresPermissions("affair:affairComment:view")
	@RequestMapping(value = {"list"})
	public String list(AffairComment affairComment, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairComment.getTreeId());
		model.addAttribute("parentId", request.getParameter("parentId"));
		Page<AffairComment> page = affairCommentService.findPage(new Page<AffairComment>(request, response), affairComment);
		String officeId = UserUtils.getUser().getOffice().getId();
		String partyId = sysOfficesService.get(officeId).getPartyId();
		model.addAttribute("partyId",partyId);
		model.addAttribute("page", page);
		return "modules/affair/affairCommentList";
	}

	@RequiresPermissions("affair:affairComment:view")
	@RequestMapping(value = "form")
	public String form(AffairComment affairComment, Model model) {
		model.addAttribute("affairComment", affairComment);
		return "modules/affair/affairCommentForm";
	}

	@RequiresPermissions("affair:affairComment:view")
	@RequestMapping(value = "comment")
	public String comment(AffairComment affairComment, Model model, HttpServletRequest request) {
		String treeId = request.getParameter("treeId");
		model.addAttribute("treeId", treeId);
		model.addAttribute("affairComment", affairComment);
		return "modules/affair/affairCommentFormComment";
	}

	@RequiresPermissions("affair:affairComment:edit")
	@RequestMapping(value = "save")
	public String save(AffairComment affairComment, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairComment)){
			return form(affairComment, model);
		}
		affairComment.setStatus("1");
		affairCommentService.save(affairComment);
		addMessage(redirectAttributes, "保存党员民主评议成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairCommentForm";
	}

	@RequiresPermissions("affair:affairComment:edit")
	@RequestMapping(value = "saveComment")
	public String saveComment(AffairComment affairComment, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairComment)){
			return form(affairComment, model);
		}
		String treeId = request.getParameter("treeId");
		affairComment.setStatus("1");
		affairComment.setTreeId(treeId);
		affairCommentService.saveComment(affairComment);
		addMessage(redirectAttributes, "保存党员民主评议成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairCommentFormComment";
	}

	@RequiresPermissions("affair:affairComment:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairComment affairComment, RedirectAttributes redirectAttributes) {
		affairCommentService.delete(affairComment);
		addMessage(redirectAttributes, "删除党员民主评议成功");
		return "redirect:" + Global.getAdminPath() + "/affair/affairComment/list?repage&treeId="+affairComment.getTreeId();
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairComment:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairCommentService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairComment affairComment, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairComment> page = null;
			if(flag == true){
				page = affairCommentService.findPage(new Page<AffairComment>(request, response), affairComment);
			}else{
				page = affairCommentService.findPage(new Page<AffairComment>(request, response,-1), affairComment);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairComment.class);
			exportExcelNew.setWb(wb);
			List<AffairComment> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairComment/list?repage&treeId="+affairComment.getTreeId();
	}

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
			List<AffairComment> list = ei.getDataList(AffairComment.class);
			for (AffairComment affairComment : list){
				try{
					BeanValidators.validateWithException(validator, affairComment);
					//党组织要绑定党组织id
					String partyOrganizationId = affairGeneralSituationService.findByName(affairComment.getPartyOrganization());
					affairComment.setPartyOrganizationId(partyOrganizationId);
					affairCommentService.save(affairComment);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairComment.getName()+"(身份证号码:"+affairComment.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
	 * 统计分析-党建-民主评议党员反查
	 * @param affairComment
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"echartGradeFindPage"})
	public String echartGradeFindPage(AffairComment affairComment , HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairComment> page;
		if(affairComment.getPbId() != null && !TOP_OFFICE_ID.equals(affairComment.getPbId())) {
			List<String> ids = affairGeneralSituationService.findAllChildIdById(affairComment.getPbId());
			if(CollectionUtils.isEmpty(ids)) {
				page = affairCommentService.echartSexFindPageByPbId(new Page<AffairComment>(request, response), affairComment);
			} else {
				ids.add(affairComment.getPbId());
				affairComment.setPbIds(ids);
				page = affairCommentService.echartSexFindPageByPbIds(new Page<AffairComment>(request, response), affairComment);
			}
		} else {
			affairComment.setPbId(null);
			page = affairCommentService.echartSexFindPageByPbId(new Page<AffairComment>(request, response), affairComment);
		}
		model.addAttribute("page", page);
		model.addAttribute("affairComment", affairComment);
		return "modules/affair/affairCommentEchartGrade";
	}

    /**
     * 从党员名册获取相关数据 进行评议
     * @param affairComment
     * @return
     */
	@RequestMapping(value = "annualReview")
	public String annualReview (AffairComment affairComment){
		String treeId=affairComment.getTreeId();
		AffairPartyMember affairPartyMember =new AffairPartyMember();
		affairPartyMember.setPartyBranchId(treeId);
		List<AffairPartyMember> s=affairPartyMemberService.findList(affairPartyMember);
		for (AffairPartyMember member : s) {
            AffairComment comment=new AffairComment();
            comment.setName(member.getName());
            comment.setSex(member.getSex());
            comment.setPoliceNo(member.getPoliceNo());
            comment.setIdNumber(member.getCardNum());
            comment.setPartyOrganization(member.getPartyBranch());
            /*添加党支部查询，更换党支部后可重新评议*/
            comment.setPartyOrganizationId(member.getPartyBranchId());
            comment.setYear(Calendar.getInstance().get(Calendar.YEAR)+"");
            //此数据不在评议表里才进行添加
            if (affairCommentService.findList(comment).size()<=0){
                affairCommentService.save(comment);
            }
		}
        return "redirect:" + Global.getAdminPath() + "/affair/affairComment/list?repage&treeId="+treeId;

    }

	/**
	 * 上报
	 * @param affairComment
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("affair:affairComment:edit")
	@RequestMapping(value = "report")
	public String report(AffairComment affairComment, RedirectAttributes redirectAttributes) {
		affairComment.setStatus("2");
		affairCommentService.save(affairComment);
		addMessage(redirectAttributes, "上报成功");
		return "redirect:" + adminPath + "/affair/affairComment/list?repage&treeId="+affairComment.getTreeId();
	}

	@RequiresPermissions("affair:affairComment:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairCommentCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairComment:edit")
	@RequestMapping(value = {"shenHeSave"})
	public Result shenHeSave(AffairComment affairComment) {
		affairCommentService.shenHeSave(affairComment);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}
}