/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairOrganizationBulid;
import com.thinkgem.jeesite.modules.affair.service.AffairOrganizationBuildSing2Service;
import com.thinkgem.jeesite.modules.affair.service.AffairOrganizationBulidService;
import com.thinkgem.jeesite.modules.affair.service.AffairOrganziationBuildSignService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 组织建设Controller
 * @author cecil.li
 * @version 2019-12-16
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairOrganizationBulid")
public class AffairOrganizationBulidController extends BaseController {

	@Autowired
	private AffairOrganizationBulidService affairOrganizationBulidService;

	@Autowired
	private AffairOrganziationBuildSignService affairOrganziationBuildSignService;

	@Autowired
	private AffairOrganizationBuildSing2Service affairOrganizationBuildSing2Service;

	@ModelAttribute
	public AffairOrganizationBulid get(@RequestParam(required=false) String id) {
		AffairOrganizationBulid entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairOrganizationBulidService.get(id);
		}
		if (entity == null){
			entity = new AffairOrganizationBulid();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairOrganizationBulid:view")
	@RequestMapping(value = {""})
	public String index() {
		return "modules/affair/affairOrganizationBulidIndex";
	}
	
	@RequiresPermissions("affair:affairOrganizationBulid:view")
	@RequestMapping(value = {"list"})
	public String list(AffairOrganizationBulid affairOrganizationBulid, HttpServletRequest request, HttpServletResponse response, Model model) {
		AffairOrganizationBulid paramObj;
		model.addAttribute("treeId", affairOrganizationBulid.getTreeId());
		if(request.getParameter("isTree") != null){
			paramObj =new AffairOrganizationBulid();
			paramObj.setId(affairOrganizationBulid.getId());
			paramObj.setTreeId(affairOrganizationBulid.getTreeId());
		}else{
			paramObj= affairOrganizationBulid;
		}
		Page<AffairOrganizationBulid> page = affairOrganizationBulidService.findPage(new Page<AffairOrganizationBulid>(request, response), paramObj);
		model.addAttribute("page", page);
		return "modules/affair/affairOrganizationBulidList";
	}

	@RequiresPermissions("affair:affairOrganizationBulid:view")
	@RequestMapping(value = "form")
	public String form(AffairOrganizationBulid affairOrganizationBulid, Model model) {
		model.addAttribute("affairOrganizationBulid", affairOrganizationBulid);
		return "modules/affair/affairOrganizationBulidForm";
	}

	/**
	 * 详情
	 * @param affairOrganizationBulid
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairOrganizationBulid:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairOrganizationBulid affairOrganizationBulid, Model model) {
		model.addAttribute("affairOrganizationBulid", affairOrganizationBulid);
		return "modules/affair/affairOrganizationBulidFormDetail";
	}

	@RequiresPermissions("affair:affairOrganizationBulid:edit")
	@RequestMapping(value = "save")
	public String save(AffairOrganizationBulid affairOrganizationBulid, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairOrganizationBulid)){
			return form(affairOrganizationBulid, model);
		}
		affairOrganizationBulidService.save(affairOrganizationBulid);
		addMessage(redirectAttributes, "保存组织建设成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairOrganizationBulidForm";
	}
	
	@RequiresPermissions("affair:affairOrganizationBulid:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairOrganizationBulid affairOrganizationBulid, RedirectAttributes redirectAttributes) {
		affairOrganizationBulidService.delete(affairOrganizationBulid);
		affairOrganziationBuildSignService.deleteByMainId(affairOrganizationBulid.getId());
		affairOrganizationBuildSing2Service.deleteByMainId(affairOrganizationBulid.getId());
		addMessage(redirectAttributes, "删除组织建设成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairOrganizationBulid/list?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairOrganizationBulid:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairOrganizationBulidService.deleteByIds(ids);
			affairOrganziationBuildSignService.deleteByMainIds(ids);
			affairOrganizationBuildSing2Service.deleteByMainIds(ids);
			/*if(ids != null && ids.size() > 0) {
				for (String id : ids) {
					affairOneHelpOneMainService.deleteByMainId(id);
				}
			}*/
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	//这是用来跳部门审核dialog页面的
	@RequiresPermissions("affair:affairOrganizationBulid:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairOrganizationBulidCheckDialog";
	}

	//这是用来实现审核功能的controller
	@RequiresPermissions("affair:affairOrganizationBulid:edit")
	@RequestMapping(value = {"shenHe"})
	public Result shenHe(AffairOrganizationBulid affairOrganizationBulid){
		affairOrganizationBulidService.shenHe(affairOrganizationBulid);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

	/**
	 * 获得工会组织树形选择器  目前没做权限过滤处理，未放入缓存中
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData() {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<AffairOrganizationBulid> list = affairOrganizationBulidService.findTreeData();
		for (int i=0; i<list.size(); i++){
			AffairOrganizationBulid b = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", b.getId());
			map.put("pId", b.getParentId());
			map.put("name", b.getName());
			mapList.add(map);
		}
		return mapList;
	}
}