/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairTemporaryPolice;
import com.thinkgem.jeesite.modules.affair.service.AffairTemporaryPoliceService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelBaseDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.warn.entity.Warning;
import com.thinkgem.jeesite.modules.warn.service.WarningService;
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

/**
 * 临时抽调民警管理Controller
 * @author mason.xv
 * @version 2019-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTemporaryPolice")
public class AffairTemporaryPoliceController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private AffairTemporaryPoliceService affairTemporaryPoliceService;

	@Autowired
	private DictDao dictDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private WarningService warningService;

	@Autowired
	private PersonnelBaseDao personnelBaseDao;

	@Autowired
	private PersonnelBaseService personnelBaseService;
	
	@ModelAttribute
	public AffairTemporaryPolice get(@RequestParam(required=false) String id) {
		AffairTemporaryPolice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTemporaryPoliceService.get(id);
		}
		if (entity == null){
			entity = new AffairTemporaryPolice();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTemporaryPolice:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTemporaryPolice affairTemporaryPolice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTemporaryPolice> page = affairTemporaryPoliceService.findPage(new Page<AffairTemporaryPolice>(request, response), affairTemporaryPolice);
		List<Role> roleList = UserUtils.getUser().getRoleList();
		boolean isLd = false;
		for (int i = 0; i < roleList.size(); i++) {
			if ("c134a888c2734b609be8a24a08e70ee5".equals(roleList.get(i).getId())){
				isLd = true;
			}
		}
		boolean isPerson = false;
		PersonnelBase personnel = personnelBaseService.findInfoByIdNumber(UserUtils.getUser().getNo());
		if(personnel!=null){
			isPerson = true;
		}
//		boolean isLd = UserUtils.getUser().getRoleList().contains("c134a888c2734b609be8a24a08e70ee5");
		model.addAttribute("isLd",isLd);
		model.addAttribute("isPerson",isPerson);
		model.addAttribute("page", page);
		return "modules/affair/affairTemporaryPoliceList";
	}

	@RequiresPermissions("affair:affairTemporaryPolice:view")
	@RequestMapping(value = "form")
	public String form(AffairTemporaryPolice affairTemporaryPolice, Model model) {
		model.addAttribute("affairTemporaryPolice", affairTemporaryPolice);
		return "modules/affair/affairTemporaryPoliceForm";
	}

	@RequiresPermissions("affair:affairTemporaryPolice:edit")
	@RequestMapping(value = "save")
	public String save(AffairTemporaryPolice affairTemporaryPolice, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairTemporaryPolice)){
			return form(affairTemporaryPolice, model);
		}
		affairTemporaryPoliceService.save(affairTemporaryPolice);
		addMessage(redirectAttributes, "保存临时抽调民警管理成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTemporaryPoliceForm";
	}
	
	@RequiresPermissions("affair:affairTemporaryPolice:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTemporaryPolice affairTemporaryPolice, RedirectAttributes redirectAttributes) {
		affairTemporaryPoliceService.delete(affairTemporaryPolice);
		addMessage(redirectAttributes, "删除临时抽调民警管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTemporaryPolice/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairTemporaryPolice:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTemporaryPoliceService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:affairTemporaryPolice:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTemporaryPolice affairTemporaryPolice, Model model) {
		model.addAttribute("affairTemporaryPolice", affairTemporaryPolice);
		return "modules/affair/affairTemporaryPoliceFormDetail";
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
	public String exportExcelByTemplate(AffairTemporaryPolice affairTemporaryPolice, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			Page<AffairTemporaryPolice> page = null;
			if(flag == true){
				page = affairTemporaryPoliceService.findPage(new Page<AffairTemporaryPolice>(request, response), affairTemporaryPolice);
			}else{
				page = affairTemporaryPoliceService.findPage(new Page<AffairTemporaryPolice>(request, response,-1), affairTemporaryPolice);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTemporaryPolice.class);
			exportExcelNew.setWb(wb);
			List<AffairTemporaryPolice> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTemporaryPolice";
	}
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairTemporaryPolice> list = ei.getDataList(AffairTemporaryPolice.class);
			for (AffairTemporaryPolice affairTemporaryPolice : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(affairTemporaryPolice.getCdUnit());
					if(orgId != null){
						affairTemporaryPolice.setCdUnitId(orgId);
					}
					String cdId = officeService.findByName(affairTemporaryPolice.getCdUnit());
					if (cdId != null){
						affairTemporaryPolice.setOfUnitId(cdId);
					}
					BeanValidators.validateWithException(validator, affairTemporaryPolice);
					affairTemporaryPoliceService.save(affairTemporaryPolice);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairTemporaryPolice.getName()+"(身份证号码:"+affairTemporaryPolice.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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

	@RequestMapping(value = {"check"})
	public String check(AffairTemporaryPolice affairTemporaryPolice,Model model, HttpServletRequest request) {
		String ids = request.getParameter("id");
		model.addAttribute("temporaryPolice",affairTemporaryPolice);
		model.addAttribute("ids",ids);
		if ("0".equals(affairTemporaryPolice.getCheckType()) || "6".equals(affairTemporaryPolice.getCheckType()) || "4".equals(affairTemporaryPolice.getCheckType())){
			return "modules/affair/affairTemporaryPoliceCheck";
		}else if("5".equals(affairTemporaryPolice.getCheckType())) {
			return "modules/affair/affairTemporaryPoliceLdCheck";
		}else {
			return  "modules/affair/affairTemporaryPoliceLdFinalCheck";
		}
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
//		String[] idsArray = idsStr.split(",");
		//数组转集合
		List<String> userList = new ArrayList<String>();

		String oneCheckId = request.getParameter("oneCheckId");
		String oneCheckMan = userDao.findUserByTree(oneCheckId);
		Collections.addAll(userList,idsStr);
		List <AffairTemporaryPolice> list = affairTemporaryPoliceService.findByIds(userList);
		for (AffairTemporaryPolice affairTemporaryPolice: list){
			// 未审核 组干部门审核未通过
			if ("0".equals(affairTemporaryPolice.getCheckType()) || "4".equals(affairTemporaryPolice.getCheckType())){
				affairTemporaryPolice.setCheckType("1");
				model.addAttribute(affairTemporaryPolice.getCheckType());
				affairTemporaryPolice.setOneCheckMan(oneCheckMan);
				affairTemporaryPolice.setOneCheckId(oneCheckId);
				affairTemporaryPolice.setSubmitId(user.getId());
				affairTemporaryPolice.setSubmitMan(user.getName());
			}
			// 抽调单位送审（组干部门审核通过） 抽调单位分管领导审核未通过
			else if ("5".equals(affairTemporaryPolice.getCheckType()) || "7".equals(affairTemporaryPolice.getCheckType())){
				String twoCheckId = request.getParameter("twoCheckId");
				String twoCheckMan = userDao.findUserByTree(twoCheckId);
				affairTemporaryPolice.setCheckType("2");
				affairTemporaryPolice.setTwoCheckMan(twoCheckMan);
				affairTemporaryPolice.setTwoCheckId(twoCheckId);
			}
			//抽调单位分管领导审核通过
			else if ("6".equals(affairTemporaryPolice.getCheckType())){
				affairTemporaryPolice.setCheckType("10");
			}
			// 组干部门送审 抽调单位分管领导审核通过
			else if ("10".equals(affairTemporaryPolice.getCheckType()) || "9".equals(affairTemporaryPolice.getCheckType())){
				String threeCheckId = request.getParameter("threeCheckId");
				String threeCheckMan = userDao.findUserByTree(threeCheckId);
				affairTemporaryPolice.setCheckType("3");
				affairTemporaryPolice.setThreeCheckMan(threeCheckMan);
				affairTemporaryPolice.setThreeCheckId(threeCheckId);
			}
			/*else if ("8".equals(affairTemporaryPolice.getCheckType())){
				String fiveChekId = request.getParameter("oneCheckId");
				String fiveCheckMan = userDao.findUserByTree(fiveChekId);
				affairTemporaryPolice.setCheckType("11");
				affairTemporaryPolice.setFiveCheckId(fiveChekId);
				affairTemporaryPolice.setFiveCheckMan(fiveCheckMan);
			}*/

			affairTemporaryPoliceService.save(affairTemporaryPolice);
		}
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTemporaryPoliceCheck";
	}
	@RequiresPermissions("affair:affairTemporaryPolice:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog(AffairTemporaryPolice affairTemporaryPolice,Model model) {
		model.addAttribute("temporaryPolice",affairTemporaryPolice);
		return "modules/affair/affairTemporaryPoliceCheckDialog";
	}
	//这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairTemporaryPolice:manage")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairTemporaryPolice affairTemporaryPolice, HttpServletRequest request,Model model) {
		String checkType = affairTemporaryPolice.getCheckType();
		if ("6".equals(affairTemporaryPolice.getCheckType())){
			affairTemporaryPolice.setCheckType("10");
			affairTemporaryPoliceService.save(affairTemporaryPolice);
		}
		if ("11".equals(affairTemporaryPolice.getCheckType())){
			PersonnelBase info = personnelBaseDao.findInfoByIdNumber(affairTemporaryPolice.getIdNumber());
			//实际工作单位
			info.setActualUnit(affairTemporaryPolice.getCdUnit());
			info.setActualUnitId(affairTemporaryPolice.getCdUnitId());
			personnelBaseService.save(info);
			//sys_user单位更改
			String companyIdAct = "";
			if ("南宁铁路公安局".equals(affairTemporaryPolice.getCdUnit()) || affairTemporaryPolice.getCdUnit().contains("公安局")){
				companyIdAct = "1";
			}else if ("南宁公安处".equals(affairTemporaryPolice.getCdUnit()) || affairTemporaryPolice.getCdUnit().contains("南宁处")){
				companyIdAct = "34";
			}else if ("柳州公安处".equals(affairTemporaryPolice.getCdUnit()) || affairTemporaryPolice.getCdUnit().contains("柳州处")){
				companyIdAct = "95";
			}else if ("北海公安处".equals(affairTemporaryPolice.getCdUnit()) || affairTemporaryPolice.getCdUnit().contains("北海处")){
				companyIdAct = "156";
			}
			User userInfo = userDao.getInfoByIdNumber(affairTemporaryPolice.getIdNumber());
			userDao.updateOfficeInfo(userInfo.getId(), affairTemporaryPolice.getCdUnitId(), companyIdAct);
			CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + userInfo.getOffice().getId() );
			CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_ID_ + userInfo.getId() );
			//预警
			Warning warning = new Warning();
			warning.setName("临时抽调民警");
			warning.setReceivePerName("公安局政治部组织干部处,"+affairTemporaryPolice.getOfUnit());
			Office ofOffice =officeService.get(affairTemporaryPolice.getOfUnitId());//根据单位id查询出单位信息
			String ofOfficeUserId = userDao.getIdByNo(ofOffice.getCode());
			warning.setReceivePerId("bfdf74f010c9466dba12c1589ecab7f3,"+ofOfficeUserId);//接受人员id为user表中id
			//warning.setReceivePerId("bfdf74f010c9466dba12c1589ecab7f3,"+affairTemporaryPolice.getOfUnitId());
			warning.setContinueDay("0");//持续时间 0 收到后停止
			warning.setRepeatCycle("4");//重复周期  永不
			warning.setDate(affairTemporaryPolice.getCdDate());
			warning.setRemind("5");//每天周期提醒时间
			warning.setIsAlert("1");//设置显示弹窗
			warning.setAlertDegree("1");//紧急程度
			warning.setAlertContent("民警"+affairTemporaryPolice.getName()+"将临时抽调至"+affairTemporaryPolice.getCdUnit());//弹窗内容
			warningService.save(warning);
			Warning endWarning = new Warning();
			endWarning.setName("临时抽调民警即将结束");
			endWarning.setReceivePerName("公安局政治部组织干部处,"+affairTemporaryPolice.getCdUnit());
			Office cdOffice =officeService.get(affairTemporaryPolice.getCdUnitId());//根据单位id查询出单位信息
			String cdOfficeUserId = userDao.getIdByNo(cdOffice.getCode());
			endWarning.setReceivePerId("bfdf74f010c9466dba12c1589ecab7f3,"+cdOfficeUserId);//接受人员id为user表中id
			//endWarning.setReceivePerId("bfdf74f010c9466dba12c1589ecab7f3,"+affairTemporaryPolice.getCdUnitId());
			endWarning.setContinueDay("0");//持续时间  0  收到后停止
			endWarning.setRepeatCycle("4");//重复周期  永不
			endWarning.setDate(affairTemporaryPolice.getEndDate());
			endWarning.setRemind("5");//每天周期提醒时间
			endWarning.setIsAlert("1");//设置显示弹窗
			endWarning.setAlertDegree("1");//紧急程度
			endWarning.setAlertContent("民警"+affairTemporaryPolice.getName()+"即将调回原单位");//弹窗内容
			warningService.save(endWarning);
		}
		model.addAttribute("checkType",checkType);
		affairTemporaryPoliceService.save(affairTemporaryPolice);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

	@RequestMapping(value = {"returnUnit"})
	public String returnUnit(AffairTemporaryPolice affairTemporaryPolice,Model model, RedirectAttributes redirectAttributes) {
		affairTemporaryPolice.setCheckType("12");
		affairTemporaryPoliceService.save(affairTemporaryPolice);
		PersonnelBase info = personnelBaseDao.findInfoByIdNumber(affairTemporaryPolice.getIdNumber());
		//实际工作单位
		info.setActualUnit(affairTemporaryPolice.getOfUnit());
		info.setActualUnitId(affairTemporaryPolice.getOfUnitId());
		personnelBaseService.save(info);
		//sys_user单位更改
		String companyIdAct = "";
		if ("南宁铁路公安局".equals(affairTemporaryPolice.getOfUnit()) || affairTemporaryPolice.getOfUnit().contains("公安局")){
			companyIdAct = "1";
		}else if ("南宁公安处".equals(affairTemporaryPolice.getOfUnit()) || affairTemporaryPolice.getOfUnit().contains("南宁处")){
			companyIdAct = "34";
		}else if ("柳州公安处".equals(affairTemporaryPolice.getOfUnit()) || affairTemporaryPolice.getOfUnit().contains("柳州处")){
			companyIdAct = "95";
		}else if ("北海公安处".equals(affairTemporaryPolice.getOfUnit()) || affairTemporaryPolice.getOfUnit().contains("北海处")){
			companyIdAct = "156";
		}
		User userInfo = userDao.getInfoByIdNumber(affairTemporaryPolice.getIdNumber());
		userDao.updateOfficeInfo(userInfo.getId(), userInfo.getBaseOfficeId(), userInfo.getBaseCompanyId());
		addMessage(redirectAttributes, "退回成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTemporaryPolice/?repage";
	}

	/**
	 * 撤销审核
	 * @param affairTemporaryPolice
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "revocation")
	public String revocation(AffairTemporaryPolice affairTemporaryPolice, RedirectAttributes redirectAttributes, @RequestParam("flag") String flag) {
		String checkType = null;
		if("1".equals(flag)){
			if("1".equals(affairTemporaryPolice.getCheckType())){
				checkType = "0";
			}else if("2".equals(affairTemporaryPolice.getCheckType())){
				checkType = "5";
			}
		}else if("2".equals(flag)){
			if("3".equals(affairTemporaryPolice.getCheckType())){
				checkType = "10";
			}else if("5".equals(affairTemporaryPolice.getCheckType())){
				checkType = "1";
			}else if("4".equals(affairTemporaryPolice.getCheckType())){
				checkType = "1";
			}
		}else if("3".equals(flag)){
			if("10".equals(affairTemporaryPolice.getCheckType())){
				checkType = "2";
			}else if("7".equals(affairTemporaryPolice.getCheckType())){
				checkType = "2";
			}
		}
		affairTemporaryPoliceService.revocation(affairTemporaryPolice.getId(),checkType);
		addMessage(redirectAttributes, "撤销临时抽调民警管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTemporaryPolice";
	}


}