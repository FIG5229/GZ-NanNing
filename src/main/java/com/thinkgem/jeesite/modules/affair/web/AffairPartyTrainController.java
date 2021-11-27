/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyTrain;
import com.thinkgem.jeesite.modules.affair.service.AffairPartyTrainService;
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

/**
 * 党内培训Controller
 * @author daniel.liu
 * @version 2020-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPartyTrain")
public class AffairPartyTrainController extends BaseController {

	@Autowired
	private AffairPartyTrainService affairPartyTrainService;

	static String idNum;

	@ModelAttribute
	public AffairPartyTrain get(@RequestParam(required=false) String id) {
		AffairPartyTrain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPartyTrainService.get(id);
		}
		if (entity == null){
			entity = new AffairPartyTrain();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairPartyTrain:view")
	@RequestMapping(value = {""})
	public String index(Model model) {
		return "modules/affair/affairPartyTrainingIndex";
	}

	@RequiresPermissions("affair:affairPartyTrain:view")
	@RequestMapping(value = {"list"})
	public String list(AffairPartyTrain affairPartyTrain, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairPartyTrain.getTreeId());
		model.addAttribute("pmId", affairPartyTrain.getIdNumber());
		idNum = affairPartyTrain.getIdNumber();
		Page<AffairPartyTrain> page = affairPartyTrainService.findPage(new Page<AffairPartyTrain>(request, response), affairPartyTrain); 
		model.addAttribute("page", page);
		return "modules/affair/affairPartyTrainList";
	}

	@RequiresPermissions("affair:affairPartyTrain:view")
	@RequestMapping(value = "form")
	public String form(AffairPartyTrain affairPartyTrain, Model model) {
		affairPartyTrain.setIdNum(idNum);
		model.addAttribute("affairPartyTrain", affairPartyTrain);
		return "modules/affair/affairPartyTrainForm";
	}

	@RequiresPermissions("affair:affairPartyTrain:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairPartyTrain affairPartyTrain, Model model) {
		model.addAttribute("affairPartyTrain", affairPartyTrain);
		return "modules/affair/affairPartyTrainFormDetail";
	}

	@RequiresPermissions("affair:affairPartyTrain:edit")
	@RequestMapping(value = "save")
	public String save(AffairPartyTrain affairPartyTrain, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairPartyTrain)){
			return form(affairPartyTrain, model);
		}
		affairPartyTrainService.save(affairPartyTrain);
		addMessage(redirectAttributes, "保存党内培训成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairPartyTrainForm";
	}
	
	@RequiresPermissions("affair:affairPartyTrain:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPartyTrain affairPartyTrain, RedirectAttributes redirectAttributes) {
		affairPartyTrainService.delete(affairPartyTrain);
		addMessage(redirectAttributes, "删除党内培训成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPartyTrain/list?repage&treeId="+affairPartyTrain.getTreeId()+"&idNumber="+affairPartyTrain.getIdNumber();
	}

	@ResponseBody
	@RequiresPermissions("affair:affairPartyTrain:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPartyTrainService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

}