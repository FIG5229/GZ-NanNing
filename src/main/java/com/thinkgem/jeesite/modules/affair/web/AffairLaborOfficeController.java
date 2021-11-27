/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairLaborOffice;
import com.thinkgem.jeesite.modules.affair.service.AffairLaborOfficeService;
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

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 劳资组织树Controller
 * @author cecil.li
 * @version 2020-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairLaborOffice")
public class AffairLaborOfficeController extends BaseController {

	@Autowired
	private AffairLaborOfficeService affairLaborOfficeService;
	
	@ModelAttribute
	public AffairLaborOffice get(@RequestParam(required=false) String id) {
		AffairLaborOffice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairLaborOfficeService.get(id);
		}
		if (entity == null){
			entity = new AffairLaborOffice();
		}
		return entity;
	}
	
/*	@RequiresPermissions("affair:affairLaborOffice:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairLaborOffice affairLaborOffice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairLaborOffice> page = affairLaborOfficeService.findPage(new Page<AffairLaborOffice>(request, response), affairLaborOffice); 
		model.addAttribute("page", page);
		return "modules/affair/affairLaborOfficeList";
	}*/

	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
											  @RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response,AffairLaborOffice affairLaborOffice) {
		if ("bcb9ae19e88a478fb66bd47322c9b637".equals(UserUtils.getUser().getId()) ){
			affairLaborOffice.setOfficeFlag("nnc");
		}else if ("c86a5e277ebb44c584972a81e039f890".equals(UserUtils.getUser().getId())){
			affairLaborOffice.setOfficeFlag("lzc");
		}else if (  "8d498ce4a66642f2ac57fd557269fa5c".equals(UserUtils.getUser().getId())){
			affairLaborOffice.setOfficeFlag("bhc");
		}
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<AffairLaborOffice> list = affairLaborOfficeService.findList(affairLaborOffice);
		for (int i=0; i<list.size(); i++){
			AffairLaborOffice e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (!type.equals("1") || type.equals(e.getType()))))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& Global.YES.equals(e.getUseable())){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}

	@RequiresPermissions("affair:affairLaborOffice:view")
	@RequestMapping(value = "form")
	public String form(AffairLaborOffice affairLaborOffice, Model model) {
		model.addAttribute("affairLaborOffice", affairLaborOffice);
		return "modules/affair/affairLaborOfficeForm";
	}

	@RequiresPermissions("affair:affairLaborOffice:edit")
	@RequestMapping(value = "save")
	public String save(AffairLaborOffice affairLaborOffice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairLaborOffice)){
			return form(affairLaborOffice, model);
		}
		affairLaborOfficeService.save(affairLaborOffice);
		addMessage(redirectAttributes, "保存劳资组织树成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairLaborOffice/?repage";
	}
	
	@RequiresPermissions("affair:affairLaborOffice:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairLaborOffice affairLaborOffice, RedirectAttributes redirectAttributes) {
		affairLaborOfficeService.delete(affairLaborOffice);
		addMessage(redirectAttributes, "删除劳资组织树成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairLaborOffice/?repage";
	}

}