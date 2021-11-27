/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.affair.service.AffairRewardInquireService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 奖励查询Controller
 * @author cecil.li
 * @version 2019-12-21
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairRewardInquire")
public class AffairRewardInquireController extends BaseController {

	@Autowired
	private AffairRewardInquireService affairRewardInquireService;

	
	@RequiresPermissions("affair:affairRewardInquire:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairRewardInquire affairRewardInquire, HttpServletRequest request, HttpServletResponse response, Model model) {
//		String officeId = UserUtils.getUser().getOffice().getId();
//		affairRewardInquire.setUnitId(officeId);
		affairRewardInquire.setUserId(UserUtils.getUser().getOffice().getId());
		Page<AffairRewardInquire> page = affairRewardInquireService.findPage(new Page<AffairRewardInquire>(request, response), affairRewardInquire);
		model.addAttribute("page", page);
		return "modules/affair/affairRewardInquireList";
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
	public String exportExcelByTemplate(AffairRewardInquire affairRewardInquire, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairRewardInquire.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			Page<AffairRewardInquire> page = null;
			if(flag == true){
				page = affairRewardInquireService.findPage(new Page<AffairRewardInquire>(request, response), affairRewardInquire);
			}else {
				page = affairRewardInquireService.findPage(new Page<AffairRewardInquire>(request, response,-1), affairRewardInquire);

			}
/*
			Page<AffairFocusStudy> page = affairFocusStudyService.findPage(new Page<AffairFocusStudy>(request, response,-1), affairFocusStudy);
*/
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairRewardInquire.class);
			exportExcelNew.setWb(wb);
			List<AffairRewardInquire> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairRewardInquire?repage";
	}
}