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
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.affair.service.AffairTjRegisterBaseService;
import com.thinkgem.jeesite.modules.affair.service.AffairTjRegisterService;
import com.thinkgem.jeesite.modules.affair.service.AffairTwBaseService;
import com.thinkgem.jeesite.modules.affair.service.AffairTwBaseSignService;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
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
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 团委（支部）基本信息Controller
 * @author cecil.li
 * @version 2019-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTwBase")
public class AffairTwBaseController extends BaseController {

	@Autowired
	private AffairTwBaseService affairTwBaseService;

	@Autowired
	private AffairTwBaseSignService affairTwBaseSignService;

	@Autowired
	private AffairTjRegisterBaseService affairTjRegisterBaseService;

	@Autowired
	private AffairTjRegisterService affairTjRegisterService;

	@Autowired
	private PersonnelBaseService personnelBaseService;

	@ModelAttribute
	public AffairTwBase get(@RequestParam(required=false) String id) {
		AffairTwBase entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTwBaseService.get(id);
		}
		if (entity == null){
			entity = new AffairTwBase();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairTwBase:view")
	@RequestMapping(value = {""})
	public String index() {
		return "modules/affair/affairTwBaseIndex";
	}

	@RequiresPermissions("affair:affairTwBase:view")
	@RequestMapping(value = {"list"})
	public String list(AffairTwBase affairTwBase, HttpServletRequest request, HttpServletResponse response, Model model) {
		AffairTwBase paramObj;
		model.addAttribute("treeId", affairTwBase.getTreeId());
		if(request.getParameter("isTree") != null){
			paramObj =new AffairTwBase();
			paramObj.setTreeId(affairTwBase.getTreeId());
		}else{
			paramObj= affairTwBase;
		}
		List<AffairTwBase> affairTwBaseList = affairTwBaseService.getSum(paramObj);
		Page<AffairTwBase> page = affairTwBaseService.findPage(new Page<AffairTwBase>(request, response), paramObj);
		for(AffairTwBase affairTwBaseFromPage : page.getList()){
			for(AffairTwBase affairTwBaseFromChild :affairTwBaseList){
				if(StringUtils.isNotBlank(affairTwBaseFromPage.getId())){
					if (StringUtils.isNotBlank(affairTwBaseFromChild.getChildId())){
						if(affairTwBaseFromChild.getChildId().equals(affairTwBaseFromPage.getId())){
							affairTwBaseFromPage.setPartyNum(affairTwBaseFromChild.getPartyNum());
						}
					}
				}
				/*if(affairTwBaseFromChild.getChildId()!= null && affairTwBaseFromChild.getChildId().equals(affairTwBaseFromPage.getId())){
					affairTwBaseFromPage.setPartyNum(affairTwBaseFromChild.getPartyNum());
				}*/
			}
		}
		model.addAttribute("page", page);
		return "modules/affair/affairTwBaseList";
	}

	@RequiresPermissions("affair:affairTwBase:view")
	@RequestMapping(value = "form")
	public String form(AffairTwBase affairTwBase, Model model) {
		model.addAttribute("affairTwBase", affairTwBase);
		return "modules/affair/affairTwBaseForm";
	}

	@RequiresPermissions("affair:affairTwBase:edit")
	@RequestMapping(value = "save")
	public String save(AffairTwBase affairTwBase, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairTwBase)){
			return form(affairTwBase, model);
		}
		affairTwBaseService.save(affairTwBase);
		addMessage(redirectAttributes, "保存团委（支部）基本信息成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTwBaseForm";
	}

	@RequiresPermissions("affair:affairTwBase:edit")
	@RequestMapping(value = "zhuCe")
	public String zhuCe(AffairTwBase affairTwBase,AffairTjRegisterBase affairTjRegisterBase, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		/*if (!beanValidator(model, affairTjRegisterFromP)){
			return form(affairTwBase, model);
		}*/
		 int year =Calendar.getInstance().get(Calendar.YEAR);
		AffairTjRegisterBase affairTjRegisterBaseParam = new  AffairTjRegisterBase();
		affairTjRegisterBaseParam.setDate(String.valueOf(year));
		affairTjRegisterBaseParam.setPartyBranchId(request.getParameter("groupId"));
		affairTjRegisterBaseService.delete(affairTjRegisterBaseParam);
		String [] ids = request.getParameterValues("personId");
		if(ids!=null){
			for(int i=0;i<ids.length;i++){
				AffairTjRegister affairTjRegisterTmp = affairTjRegisterService.get(ids[i]);
				affairTjRegisterBase.setId(null);
				affairTjRegisterBase.setName(affairTjRegisterTmp.getName());
				affairTjRegisterBase.setIdNumber(affairTjRegisterTmp.getIdNumber());
				affairTjRegisterBase.setBirthday(affairTjRegisterTmp.getBirthday());
				affairTjRegisterBase.setDate(String.valueOf(year));
				affairTjRegisterBase.setPartyBranch(request.getParameter("groupName"));
				affairTjRegisterBase.setPartyBranchId(request.getParameter("groupId"));
				//更新性别为设置
				affairTjRegisterBase.setSex(affairTjRegisterTmp.getSex());
				affairTjRegisterBase.setJob(affairTjRegisterTmp.getJob());
				affairTjRegisterBase.setPoliceNo(affairTjRegisterTmp.getPoliceNo());
				affairTjRegisterBase.setRemark(affairTjRegisterTmp.getRemark());
				affairTjRegisterBaseService.save(affairTjRegisterBase);
			}
		}
		addMessage(redirectAttributes, "注册成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTwBaseFormDetail";
	}


	@RequiresPermissions("affair:affairTwBase:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTwBase affairTwBase, RedirectAttributes redirectAttributes, String treeId) {
		affairTwBaseService.delete(affairTwBase);
		affairTwBaseSignService.deleteByMainId(affairTwBase.getId());
		addMessage(redirectAttributes, "删除团委（支部）基本信息成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTwBase/list?treeId="+treeId+"&repage";
	}

	/**
	 * 详情
	 * @param affairTwBase
	 * @param affairTwBaseSign
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairTwBase:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTwBase affairTwBase, AffairTwBaseSign affairTwBaseSign, Model model) {
		affairTwBaseSign.setTbId(affairTwBase.getId());
		List<AffairTjRegisterFromP> list = affairTwBaseService.getPersonnelUnder28(affairTwBase.getId());
/*		List<AffairOneHelpOneMain> affairOneHelpOneMainList = affairOneHelpOneMainService.findList(affairOneHelpOneMain);
		affairOneHelpOne.setAffairOneHelpOneMainList(affairOneHelpOneMainList);*/
		model.addAttribute("affairTwBase", affairTwBase);
		model.addAttribute("list", list);
		//model.addAttribute("affairOneHelpOneMainList", affairOneHelpOneMainList);
		return "modules/affair/affairTwBaseFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairTwBase:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTwBaseService.deleteByIds(ids);
			affairTwBaseSignService.deleteByMainIds(ids);
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

	/**
	 * 获得团委团支部树形选择器 未放入缓存中
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(HttpServletRequest request) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<AffairOrganizationBulid> list = affairTwBaseService.findTreeData();
		for (int i=0; i<list.size(); i++){
			AffairOrganizationBulid b = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", b.getId());
			map.put("pId", b.getParentId());
			map.put("name", b.getName());
			mapList.add(map);
			if("tym".equals(request.getParameter("flag"))){
				map.put("isParent", true);
			}
		}
		return mapList;
	}
}