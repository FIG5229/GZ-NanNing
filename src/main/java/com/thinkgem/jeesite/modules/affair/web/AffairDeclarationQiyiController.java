/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairDeclarationQiyi;
import com.thinkgem.jeesite.modules.affair.service.AffairDeclarationQiyiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 工作申报Controller
 * @author daniel.liu
 * @version 2020-07-01
 * 创先争优 其他申报下的 4个tab都为一个页面
 * 上层tab使用topType控制
 * 下层使用type控制
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairDeclarationQiyi")
public class AffairDeclarationQiyiController extends BaseController {

	@Autowired
	private AffairDeclarationQiyiService affairDeclarationQiyiService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private DictDao dictDao;
	
	@ModelAttribute
	public AffairDeclarationQiyi get(@RequestParam(required=false) String id) {
		AffairDeclarationQiyi entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairDeclarationQiyiService.get(id);
		}
		if (entity == null){
			entity = new AffairDeclarationQiyi();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairDeclarationQiyi:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairDeclarationQiyi affairDeclarationQiyi, HttpServletRequest request, HttpServletResponse response, Model model) {
		String type =affairDeclarationQiyi.getType()==null?"1":affairDeclarationQiyi.getType();
		String topType = affairDeclarationQiyi.getTopType()==null?"1":affairDeclarationQiyi.getTopType();
		affairDeclarationQiyi.setType(type);
		affairDeclarationQiyi.setTopType(topType);
		Page<AffairDeclarationQiyi> page = affairDeclarationQiyiService.findPage(new Page<AffairDeclarationQiyi>(request, response), affairDeclarationQiyi);
		model.addAttribute("type",type);
		model.addAttribute("topType",topType);
		model.addAttribute("page", page);
		return "modules/affair/affairDeclarationQiyiList";
	}

	@RequiresPermissions("affair:affairDeclarationQiyi:view")
	@RequestMapping(value = "form")
	public String form(AffairDeclarationQiyi affairDeclarationQiyi, Model model) {
		model.addAttribute("affairDeclarationQiyi", affairDeclarationQiyi);
		model.addAttribute("type",affairDeclarationQiyi.getType()==null?"1":affairDeclarationQiyi.getType());
		model.addAttribute("topType",affairDeclarationQiyi.getTopType()==null?"1":affairDeclarationQiyi.getTopType());
		return "modules/affair/affairDeclarationQiyiForm";
	}
	@RequiresPermissions("affair:affairDeclarationQiyi:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairDeclarationQiyi affairDeclarationQiyi, Model model) {
		model.addAttribute("affairDeclarationQiyi", affairDeclarationQiyi);
		model.addAttribute("type",affairDeclarationQiyi.getType()==null?"1":affairDeclarationQiyi.getType());
		model.addAttribute("topType",affairDeclarationQiyi.getTopType()==null?"1":affairDeclarationQiyi.getTopType());
		if(affairDeclarationQiyi.getFilePath() != null && affairDeclarationQiyi.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairDeclarationQiyi.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairDeclarationQiyiFormDetail";
	}

	@RequiresPermissions("affair:affairDeclarationQiyi:edit")
	@RequestMapping(value = "save")
	public String save(AffairDeclarationQiyi affairDeclarationQiyi, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairDeclarationQiyi)){
			return form(affairDeclarationQiyi, model);
		}
		//审核状态为空时 添加默认值1 未审核
		if (StringUtils.isEmpty(affairDeclarationQiyi.getCheckType())){
			affairDeclarationQiyi.setCheckType("1");
		}
		affairDeclarationQiyiService.save(affairDeclarationQiyi);
		addMessage(redirectAttributes, "保存工作申报成功");
		request.setAttribute("saveResult","success");
		model.addAttribute("type",affairDeclarationQiyi.getType()==null?"1":affairDeclarationQiyi.getType());
		model.addAttribute("topType",affairDeclarationQiyi.getTopType()==null?"1":affairDeclarationQiyi.getTopType());
		return "modules/affair/affairDeclarationQiyiForm";
	}
	
	@RequiresPermissions("affair:affairDeclarationQiyi:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairDeclarationQiyi affairDeclarationQiyi, RedirectAttributes redirectAttributes) {
		affairDeclarationQiyiService.delete(affairDeclarationQiyi);
		addMessage(redirectAttributes, "删除工作申报成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairDeclarationQiyi/?type="+affairDeclarationQiyi.getType()+"&topType="+affairDeclarationQiyi.getTopType();
	}

	@ResponseBody
	@RequiresPermissions("affair:affairDeclarationQiyi:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairDeclarationQiyiService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairDeclarationQiyi:manage")
	@RequestMapping(value = {"examineView"})
	public String examineView() {
		return "modules/affair/affairDeclarationQiyiCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairDeclarationQiyi:manage")
	@RequestMapping(value = "examine")
	public Result examine(AffairDeclarationQiyi affairDeclarationQiyi, HttpServletRequest request) {
		if (StringUtils.isNotBlank(affairDeclarationQiyi.getCheckId())){
			affairDeclarationQiyi.setCheckMan(dictDao.findLabelByValue(affairDeclarationQiyi.getCheckId()));
		}
		affairDeclarationQiyiService.save(affairDeclarationQiyi);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

	@RequiresPermissions("affair:affairDeclarationQiyi:edit")
	@RequestMapping(value = {"submitByIds"})
	public String submitByIds(HttpServletRequest request) {
		User user = UserUtils.getUser();
		//接受前台传过来的ids
		String idsStr = request.getParameter("ids");
		//字符串转数组
		String[] idsArray = idsStr.split(",");
		//数组转集合
		List<String> userList = new ArrayList<String>();
		String CheckId = request.getParameter("checkId");
		String CheckMan = dictDao.findLabelByValue(CheckId);
		Collections.addAll(userList,idsArray);
		/*String companyId = UserUtils.getUser().getId();*/
		List <AffairDeclarationQiyi> list = affairDeclarationQiyiService.findByIds(userList);
		for (AffairDeclarationQiyi affairDeclarationQiyi: list){
			affairDeclarationQiyi.setCheckType("2");
			affairDeclarationQiyi.setCheckMan(CheckMan);
			affairDeclarationQiyi.setCheckId(CheckId);
			affairDeclarationQiyi.setSubmitId(user.getId());
			affairDeclarationQiyi.setSubmitMan(user.getName());
			affairDeclarationQiyiService.save(affairDeclarationQiyi);
		}
		String type = request.getParameter("type");
		String topType = request.getParameter("topType");
		return "redirect:"+Global.getAdminPath()+"/affair/affairDeclarationQiyi/list?type="+type+"&topType="+topType;
	}

}