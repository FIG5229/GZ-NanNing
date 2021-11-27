/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 创岗建区Controller
 * @author eav.liu
 * @version 2019-12-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairChuangGangJianQu")
public class AffairChuangGangJianQuController extends BaseController {
	//权限先注释掉
	//@RequiresPermissions("affair:affairChuangGangJianQu:view")
	@RequestMapping(value = {"list", ""})
    public String list(String treeId, Model model) {
        model.addAttribute("treeId", treeId);
		return "modules/affair/affairChuangGangJianQuList";
	}


}