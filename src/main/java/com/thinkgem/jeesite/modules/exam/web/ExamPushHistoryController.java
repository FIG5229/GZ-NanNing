/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.entity.ExamPushHistory;
import com.thinkgem.jeesite.modules.exam.service.ExamPushHistoryService;

/**
 * 绩效考评项推送Controller
 * @author daniel.liu
 * @version 2020-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examPushHistory")
public class ExamPushHistoryController extends BaseController {

	@Autowired
	private ExamPushHistoryService examPushHistoryService;

	@Autowired
	private UserDao userDao;

	@ModelAttribute
	public ExamPushHistory get(@RequestParam(required=false) String id) {
		ExamPushHistory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examPushHistoryService.get(id);
		}
		if (entity == null){
			entity = new ExamPushHistory();
		}
		return entity;
	}
	
//	@RequiresPermissions("exam:examPushHistory:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamPushHistory examPushHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamPushHistory> page = examPushHistoryService.findPage(new Page<ExamPushHistory>(request, response), examPushHistory); 
		model.addAttribute("page", page);
		return "modules/exam/examPushHistoryList";
	}

	@RequestMapping(value = {"pushHistory"})
	public String pushHistory(ExamPushHistory examPushHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamPushHistory> page = examPushHistoryService.findPage(new Page<ExamPushHistory>(request, response), examPushHistory);
		model.addAttribute("page", page);
		return "modules/exam/examPushHistoryList";
	}

	@RequiresPermissions("exam:examPushHistory:view")
	@RequestMapping(value = "form")
	public String form(ExamPushHistory examPushHistory, Model model) {
		model.addAttribute("examPushHistory", examPushHistory);
		return "modules/exam/examPushHistoryForm";
	}

	/**
	 * 考评项接收历史（查看谁推过来的什么事）
	 * @param examPushHistory
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "receiveHistory")
	public String receiveHistory(ExamPushHistory examPushHistory, Model model) {
		model.addAttribute("examPushHistory", examPushHistory);
		return "modules/exam/examReceivedHistoryList";
	}

//	@RequiresPermissions("exam:examPushHistory:edit")
	@RequestMapping(value = "save")
	public String save(ExamPushHistory examPushHistory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examPushHistory)){
			return form(examPushHistory, model);
		}
		examPushHistory.setFromName(UserUtils.getUser().getName());
		examPushHistory.setFromName(UserUtils.getUser().getId());
//		examPushHistoryService.save(examPushHistory);
		addMessage(redirectAttributes, "保存绩效考评项推送成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examPushHistory/?repage";
	}

	@RequestMapping(value = "saveBeta")
	public String saveBeta(ExamPushHistory examPushHistory, Model model, RedirectAttributes redirectAttributes) {

		String[] ObjNames = examPushHistory.getObjName().split(",");
		String[] ObjIds = examPushHistory.getObjId().split(",");
		examPushHistory.setFromName(UserUtils.getUser().getName());
		examPushHistory.setFromId(UserUtils.getUser().getId());
		if (StringUtils.isBlank(examPushHistory.getStatus())){
			examPushHistory.setStatus("1");
		}
		for (int i =0;i <ObjIds.length;i++){
			if (StringUtils.isNotBlank(ObjNames[i])){
				examPushHistory.setId(null);
				User u = userDao.getUnitUserByOfficeId(ObjIds[i]);
				/*获取不到单位账号则可能是个人账号*/
				if (u==null){
					u = UserUtils.get(ObjIds[i]);
				}
				if (u!= null){
				examPushHistory.setObjId(u.getId());
				examPushHistory.setObjName(ObjNames[i]);
				examPushHistoryService.save(examPushHistory);
				}
			}
		}
		addMessage(redirectAttributes, "保存绩效考评项推送成功");
		model.addAttribute("saveResult","success");
		return "modules/exam/pushDialog";
	}
	
	@RequiresPermissions("exam:examPushHistory:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamPushHistory examPushHistory, RedirectAttributes redirectAttributes) {
		examPushHistoryService.delete(examPushHistory);
		addMessage(redirectAttributes, "删除绩效考评项推送成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examPushHistory/?repage";
	}

}