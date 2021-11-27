/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowDefine;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowSegmentGuanlian;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowSegmentsDefine;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowSegmentsTask;
import com.thinkgem.jeesite.modules.exam.service.*;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 考评流程定义Controller
 * @author eav.liu
 * @version 2019-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examWorkflowDefine")
public class ExamWorkflowDefineController extends BaseController {

	@Autowired
	private ExamWorkflowDefineService examWorkflowDefineService;

	@Autowired
	private ExamWorkflowSegmentsDefineService examWorkflowSegmentsDefineService;

	@Autowired
	private ExamWorkflowSegmentsTaskService examWorkflowSegmentsTaskService;

	@Autowired
	private ExamStandardTemplateDataService examStandardTemplateDataService;
	
	@ModelAttribute
	public ExamWorkflowDefine get(@RequestParam(required=false) String id) {
		ExamWorkflowDefine entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examWorkflowDefineService.get(id);
		}
		if (entity == null){
			entity = new ExamWorkflowDefine();
		}
		return entity;
	}

	@RequiresPermissions("exam:examWorkflowDefine:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamWorkflowDefine examWorkflowDefine, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamWorkflowDefine> page = examWorkflowDefineService.findPage(new Page<ExamWorkflowDefine>(request, response), examWorkflowDefine); 
		model.addAttribute("page", page);
		return "modules/exam/examWorkflowDefineList";
	}

	@RequiresPermissions("exam:examWorkflowDefine:view")
	@RequestMapping(value = "form")
	public String form(ExamWorkflowDefine examWorkflowDefine, Model model) {
		if (examWorkflowDefine.getTemplatesIds() != null && examWorkflowDefine.getTemplatesIds().length() >0){
			String[] templatesIdsArr = examWorkflowDefine.getTemplatesIds().split(",");
			examWorkflowDefine.setTemplatesIdsArr(templatesIdsArr);
		}
		model.addAttribute("examWorkflowDefine", examWorkflowDefine);
		model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
		return "modules/exam/examWorkflowDefineForm";
	}

	@RequiresPermissions("exam:examWorkflowDefine:edit")
	@RequestMapping(value = "save")
	public String save(ExamWorkflowDefine examWorkflowDefine, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, examWorkflowDefine)){
			return form(examWorkflowDefine, model);
		}
		String[] templateIds;
		/*添加流程*/
		if (StringUtils.isBlank(examWorkflowDefine.getId())){
			templateIds = examWorkflowDefine.getTemplatesIdsArr();
		}else {
			/*修改流程*/
			templateIds = examWorkflowDefine.getTemplatesIds().split(",");
			/*考评标准删除时  同时删除任务分配*/
			examWorkflowSegmentsTaskService.updateTaskByWorkflowId(templateIds,examWorkflowDefine.getId());
		}
		examWorkflowDefineService.save(examWorkflowDefine);
		addMessage(redirectAttributes, "保存考评流程成功");
		request.setAttribute("saveResult","sucess");
		//return "redirect:"+Global.getAdminPath()+"/exam/examWorkflowDefine/?repage";
		return "modules/exam/examWorkflowDefineForm";
	}
	
	@RequiresPermissions("exam:examWorkflowDefine:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamWorkflowDefine examWorkflowDefine, RedirectAttributes redirectAttributes) {
		//examWorkflowDefineService.delete(examWorkflowDefine);
		examWorkflowDefineService.deleteById(examWorkflowDefine.getId());
		addMessage(redirectAttributes, "删除考评流程成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examWorkflowDefine/?repage";
	}

    /***
     * 流程环节页面
     * @param examWorkflowDefine
     * @param model
     * @return
     */
    @RequiresPermissions("exam:examWorkflowDefine:view")
    @RequestMapping(value = "segmentDialog")
    public String segmentDialog(ExamWorkflowDefine examWorkflowDefine, Model model) {
		List<ExamWorkflowSegmentsDefine> list = examWorkflowSegmentsDefineService.findByType(examWorkflowDefine.getFlowType().toString());
		List<Map<String,Object>> itemList = null;
		/*考评标准中 标准的行数*/
		int standardsItemNum = 0;
		if(null != examWorkflowDefine.getTemplatesIds() && !"".equals(examWorkflowDefine.getTemplatesIds())){
			itemList = examStandardTemplateDataService.findExamItemNumList(examWorkflowDefine.getTemplatesIds().split(","));
			standardsItemNum = itemList.size();
		}
		ExamWorkflowSegmentsTask task = new ExamWorkflowSegmentsTask();
		task.setWorkflowId(examWorkflowDefine.getId());
		task.setTemplatesIds(examWorkflowDefine.getTemplatesIds().split(","));
		/*查询任务分配的数量*/
		List<Map<String, Object>>  assignNumberList = examWorkflowSegmentsTaskService.findAssignListNumber(task);
		List<Map<String, Object>> assginList = new ArrayList<Map<String, Object>>();
		if(null != list){
			for(ExamWorkflowSegmentsDefine segment:list){
				Map<String, Object> assign = new HashMap<String, Object>();
				assign.put("type",segment.getType());
				assign.put("sort",segment.getSort());
				assign.put("name",segment.getName());
				String segmentId = segment.getSort();
				if(null != itemList){
					for(Map<String, Object> item:assignNumberList){
						if(segmentId.equals(item.get("segmentId"))){
							/*任务分配表中 分配的行数*/
							int itemNum = Integer.parseInt(item.get("number").toString());
							if(itemNum==standardsItemNum){
								assign.put("status","已分配");
							}else if(itemNum ==0){
								assign.put("status","未分配");
							}
							else if (itemNum <standardsItemNum){
								assign.put("status","部分分配");
							}else if (itemNum >standardsItemNum){
								/*存在分配的总行数大于所有标准行数的情况，如果全部分配完成则没问题。假如多分配（分配重复等）的数据恰巧等于未分配的
								* 行数，则显示已分配，但实际上未分配完成。*/
								/*此处未发现如何生成的重复分配，暂时当做已分配处理*/
								assign.put("status","已分配");
							}else {
								assign.put("status","分配异常");
							}
						}
					}
				}
				assginList.add(assign);
			}
		}
		model.addAttribute("list", assginList);
		model.addAttribute("workflowId",examWorkflowDefine.getId());
		model.addAttribute("templatesIds",examWorkflowDefine.getTemplatesIds());
		return "modules/exam/examWorkflowDefineTaskDialog";
    }

	/**
	 * 民警考评  分配人员时查询
	 */
	@RequestMapping(value = "queryPoliceDatas")
	public void queryPoliceDatas(){
    	/*绩效考核  处领导*/
    	String chuRoleId = "230e916609a349e68f7186f784e11419";

    	/*以下需要数据过滤，局考局   处考处*/

//		-- 绩效考核 普通民警
    	String policeRoleId = "2a7ea380b14f4870b6c44be21d2443c3";

    	String  juJiGuanRoleId = "13bac520ae4d4160b595384691b443fd";
    	String chuJiGuanRoleId= "230e916609a349e68f7186f784e11419";
    	String suoduiJiGuanRoleId = "ff24c3288860447d867d08d462a2a2b9";

    	String[] zhongJiCeng = new String[3];
    	zhongJiCeng[0]=chuJiGuanRoleId;
    	zhongJiCeng[1]=juJiGuanRoleId;
    	zhongJiCeng[2]=suoduiJiGuanRoleId;

		/*绩效考核  处领导*/
    	StringBuffer chuIds = new StringBuffer();
    	StringBuffer chuPersonNames = new StringBuffer();
		ExamWorkflowDefine define = new ExamWorkflowDefine();
		define.setName(chuRoleId);
    	List<User> chuList = examWorkflowSegmentsDefineService.queryPoliceDatas(define);
    	chuList.stream().forEach(user -> {
    		chuPersonNames.append(user.getName()+",");
    		chuIds.append(user.getId()+",");
		});

		/*-- 绩效考核 普通民警*/
		StringBuffer policeIds = new StringBuffer();
		StringBuffer policePersonNames = new StringBuffer();
		ExamWorkflowDefine policeDefine = new ExamWorkflowDefine();
		policeDefine.setName(policeRoleId);
		String companyId = "156";
		if (companyId.equals("1")){
			policeDefine.getSqlMap().put("dsf","and u.company_id = '"+companyId+"' and u.office_id not in ('34','95','156')");
		}else {
			policeDefine.getSqlMap().put("dsf","and u.company_id = '"+companyId+"'");
		}
		List<User> policeList = examWorkflowSegmentsDefineService.queryPoliceDatas(policeDefine);
		policeList.stream().forEach(user -> {
			policePersonNames.append(user.getName()+",");
			policeIds.append(user.getId()+",");
		});

		/*中基层*/
		List<User> temzhongJiCengList =new ArrayList<>();
		Arrays.stream(zhongJiCeng).forEach(s -> {
			ExamWorkflowDefine zhongDefine = new ExamWorkflowDefine();
			zhongDefine.setName(s);
			if (companyId.equals("1")){
				zhongDefine.getSqlMap().put("dsf","and u.company_id = '"+companyId+"' and u.office_id not in ('34','95','156')");
			}else {
				zhongDefine.getSqlMap().put("dsf","and u.company_id = '"+companyId+"'");
			}
			temzhongJiCengList.addAll(examWorkflowSegmentsDefineService.queryPoliceDatas(zhongDefine));
		});

		StringBuffer zhongJiCengIds = new StringBuffer();
		StringBuffer zhongJiCengPersonNames = new StringBuffer();
		temzhongJiCengList.stream().forEach(user -> {
			zhongJiCengIds.append(user.getId()+",");
			zhongJiCengPersonNames.append(user.getName()+",");
		});
		System.out.println("处领导============================================================处领导");
		System.out.println(chuPersonNames);
		System.out.println(chuIds);
		System.out.println("民警==================================================================民警");
		System.out.println(policeIds);
		System.out.println(policePersonNames);
		System.out.println("中基层领导=====================================================中基层领导");
		System.out.println(zhongJiCengIds);
		System.out.println(zhongJiCengPersonNames);
	}

	/**
	 * 禁用考评流程
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("exam:examWorkflowDefine:edit")
	@RequestMapping(value = "diasble")
	public String diasble(String id, RedirectAttributes redirectAttributes) {
		examWorkflowDefineService.isUsable(id,"0");
		addMessage(redirectAttributes, "禁用考评流程成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examWorkflowDefine/?repage";
	}

	/**
	 * 启用考评流程
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("exam:examWorkflowDefine:edit")
	@RequestMapping(value = "usable")
	public String usable(String id, RedirectAttributes redirectAttributes) {
		examWorkflowDefineService.isUsable(id,"1");
		//刷新考评流程任务缓存
		examWorkflowSegmentsTaskService.freshAllTaskSegmentList();
		addMessage(redirectAttributes, "启用考评流程成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examWorkflowDefine/?repage";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("exam:examWorkflowDefine:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			for (String id:ids) {
				examWorkflowDefineService.deleteById(id);
			}
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
}