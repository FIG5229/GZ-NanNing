/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.test.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.test.entity.Test;
import com.thinkgem.jeesite.modules.test.service.TestService;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
 * 测试Controller
 * @author ThinkGem
 * @version 2013-10-17
 */
@Controller
@RequestMapping(value = "${adminPath}/test/test")
public class TestController extends BaseController {

	@Autowired
	private TestService testService;

	@Autowired
	private SystemService systemService;
	@Autowired
	private RoleDao roleDao;

	@ModelAttribute
	public Test get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return testService.get(id);
		}else{
			return new Test();
		}
	}
	
	/**
	 * 显示列表页
	 * @param test
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("test:test:view")
	@RequestMapping(value = {"list", ""})
	public String list(Test test, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/test/testList";
	}
	
	/**
	 * 获取硕正列表数据（JSON）
	 * @param test
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("test:test:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Test> listData(Test test, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			test.setCreateBy(user);
		}
        Page<Test> page = testService.findPage(new Page<Test>(request, response), test); 
        return page;
	}
	
	/**
	 * 新建或修改表单
	 * @param test
	 * @param model
	 * @return
	 */
	@RequiresPermissions("test:test:view")
	@RequestMapping(value = "form")
	public String form(Test test, Model model) {
		model.addAttribute("test", test);
		return "modules/test/testForm";
	}

	/**
	 * 表单保存方法
	 * @param test
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("test:test:edit")
	@RequestMapping(value = "save")
	public String save(Test test, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, test)){
			return form(test, model);
		}
//		testService.save(test);
		addMessage(redirectAttributes, "保存测试'" + test.getName() + "'成功");
		return "redirect:" + adminPath + "/test/test/?repage";
	}
	
	/**
	 * 删除数据方法
	 * @param
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("test:test:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Test test, RedirectAttributes redirectAttributes) {
//		testService.delete(test);
//		addMessage(redirectAttributes, "删除测试成功");
//		return "redirect:" + adminPath + "/test/test/?repage";
		return "true";
	}

	@RequestMapping(value = "org")
	public String org(Test test, Model model) {
		return "modules/test/org";
	}

	@RequestMapping(value = "alarm")
	public String alarm(Test test, Model model) {
		return "modules/test/alarm";
	}

	@RequestMapping(value = "dialog")
	public String dialog(Test test, Model model) {
		return "modules/test/dialog";
	}

	@RequestMapping(value = "search")
	public String search(Test test, Model model) {
		return "modules/test/search";
	}

	@RequestMapping(value = "modal-custom")
	public String modalCustom(Test test, Model model) {
		return "modules/test/modal-custom";
	}

	@RequestMapping(value = "detail")
	public String detail(Test test, Model model) {
		return "modules/test/detail1";
	}

	@RequestMapping(value = "detail2")
	public String detail2(Test test, Model model) {
		return "modules/test/detail2";
	}
	@RequestMapping(value = "person_nav")
	public String personNav(Test test, Model model) {
		return "modules/test/person_nav";
	}

	@RequestMapping(value = "excel")
	public String download(Test test, Model model) {
		return "modules/test/excel";
	}

	@RequestMapping(value = "appraise")
	public String appraise(Test test, Model model) {
		return "modules/test/appraise";
	}

	@RequestMapping(value = "appraise_simple")
	public String appraiseSimple(Test test, Model model) {
		return "modules/test/appraise_simple";
	}

	@RequestMapping(value = "public")
	public String publics(Test test, Model model) {
		return "modules/test/public";
	}

	@RequestMapping(value = "sign")
	public String sign(Test test, Model model) {
		return "modules/test/sign";
	}

	@RequestMapping(value = "all_public")
	public String allPublics(Test test, Model model) {
		return "modules/test/all_public";
	}

	@RequestMapping(value = "check_count")
	public String affairCheckCount(Test test, Model model) {
		return "modules/affair/affairCheckCount";
	}

	@RequestMapping(value = "multiselect")
	public String multiselect(Test test, Model model) {
		return "modules/test/multiselect";
	}

	@RequestMapping(value = "multiselect2")
	public String multiselect2(Test test, Model model) {
		return "modules/test/multiselect2";
	}

	@RequestMapping(value = "webupload")
	public String webupload(Test test, Model model) {
		return "modules/test/webuploader";
	}

	/**
	 * 导出excel格式数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(1, User.class);
			exportExcelNew.setWb(wb);
			List<User> userList =page.getList();
			for(User tuser:userList){
				tuser.setRoleList(roleDao.findList(new Role(tuser)));
			}
			exportExcelNew.setDataList(userList);
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
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

}
