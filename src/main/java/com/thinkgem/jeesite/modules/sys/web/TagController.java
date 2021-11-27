/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;

import com.thinkgem.jeesite.common.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 标签Controller
 * @author ThinkGem
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/tag")
public class TagController extends BaseController {
	
	/**
	 * 树结构选择标签（treeselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "treeselect")
	public String treeselect(HttpServletRequest request, Model model) {
		model.addAttribute("url", request.getParameter("url")); 	// 树结构数据URL
		model.addAttribute("extId", request.getParameter("extId")); // 排除的编号ID
		model.addAttribute("checked", request.getParameter("checked")); // 是否可复选
		model.addAttribute("selectIds", request.getParameter("selectIds")); // 指定默认选中的ID
		model.addAttribute("isAll", request.getParameter("isAll")); 	// 是否读取全部数据，不进行权限过滤
		model.addAttribute("module", request.getParameter("module"));	// 过滤栏目模型（仅针对CMS的Category树）
		String getUserUrl = "/sys/user/treeData";
		String examType = request.getParameter("examTreeType");

		if (StringUtils.isNotBlank(examType)){
/*			String roleId = "()";
			switch (examType){
				case "4":
					roleId = "230e916609a349e68f7186f784e11419";
					break;
				case "5":
					roleId = "";
					break;
				case "6":
					break;
				case "7":
					break;
			}*/
			getUserUrl = "/sys/user/examTreeData?examTreeType="+examType;
			model.addAttribute("examTreeType",examType);
			if (examType.equals("tym")){
                getUserUrl = "/affair/affairTjRegister/treeData";
                model.addAttribute("examTreeType",examType);
            }
			//党员树
			if (examType.equals("dym")){
                getUserUrl = "/affair/affairPartyMember/newTreeData";
                model.addAttribute("examTreeType",examType);
            }
			/*如果再添加examType 类型 不要再添加if 使用switch更加简洁清晰*/
		}
		model.addAttribute("getUserUrl", getUserUrl);	// 过滤栏目模型（仅针对CMS的Category树）
		return "modules/sys/tagTreeselect";
	}
	
	/**
	 * 图标选择标签（iconselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "iconselect")
	public String iconselect(HttpServletRequest request, Model model) {
		model.addAttribute("value", request.getParameter("value"));
		return "modules/sys/tagIconselect";
	}
	
}
