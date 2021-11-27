/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.test.web;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.elasticsearch.ImportEntityService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.test.entity.TestData;
import com.thinkgem.jeesite.test.service.TestDataService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 单表生成Controller
 * @author ThinkGem
 * @version 2015-04-06
 */
@Controller
@RequestMapping(value = "${adminPath}/test/testData")
public class TestDataController extends BaseController {

	@Autowired
	private TestDataService testDataService;

//	@Resource
//	private EsAdminService adminService;

	@Resource
	private ImportEntityService importEntityService;
	
	@ModelAttribute
	public TestData get(@RequestParam(required=false) String id) {
		TestData entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testDataService.get(id);
		}
		if (entity == null){
			entity = new TestData();
		}
		return entity;
	}
	
	@RequiresPermissions("test:testData:view")
	@RequestMapping(value = {"list", ""})
	public String list(TestData testData, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TestData> page = testDataService.findPage(new Page<TestData>(request, response), testData); 
		model.addAttribute("page", page);
		return "jeesite/test/testDataList";
	}

	@RequiresPermissions("test:testData:view")
	@RequestMapping(value = "form")
	public String form(TestData testData, Model model) {
		model.addAttribute("testData", testData);
		return "jeesite/test/testDataForm";
	}

	@RequiresPermissions("test:testData:edit")
	@RequestMapping(value = "save")
	public String save(TestData testData, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, testData)){
			return form(testData, model);
		}
		testDataService.save(testData);
		addMessage(redirectAttributes, "保存单表成功");
		return "redirect:"+Global.getAdminPath()+"/test/testData/?repage";
	}
	
	@RequiresPermissions("test:testData:edit")
	@RequestMapping(value = "delete")
	public String delete(TestData testData, RedirectAttributes redirectAttributes) {
		testDataService.delete(testData);
		addMessage(redirectAttributes, "删除单表成功");
		return "redirect:"+Global.getAdminPath()+"/test/testData/?repage";
	}

	@RequestMapping(value = "impESdata")
	@ResponseBody
	public  List<String> testImportEntityList(String num) {
        List<String> daoList = Lists.newArrayList();
		daoList.add("com.thinkgem.jeesite.modules.personnel.dao.PersonnelBaseDao");
		daoList.add("com.thinkgem.jeesite.modules.personnel.dao.PersonnelDailyContactDao");
		daoList.add("com.thinkgem.jeesite.modules.personnel.dao.PersonnelRecuperateDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairBelongToDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairElectionDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairOrganizationBulidDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairZkInfoDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairZxInfoDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairZyInfoDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairReportComplaintsDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairOrgRewardPunishDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairPartyRewardPunishDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairHealthCheckupDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairDisciplinaryActionDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairDcwtLibraryDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairWentiTalentDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairFileNoticeDao");
		daoList.add("com.thinkgem.jeesite.modules.cms.dao.ArticleDao");
		daoList.add("com.thinkgem.jeesite.modules.sys.dao.OfficeDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairCollectiveAwardDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairTwUnitAwardDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairOrgRewardPunishDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairXcUnitRewardDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairTwPersonalAwardDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairPersonalAwardDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairMjxyReportDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairMediaManagementDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairGeneralSituationDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairTwBaseDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairTousujubaoguanliDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairPartyMemberDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairTjRegisterDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairInteriorInstructorLibraryDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairWenhuaDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairSevenKnowledgeDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairNewsDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairCorrespondentDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairCommentatorsDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairTalkHeartDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairHomeVisitDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairPoliceHelpEducateDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairLearnPowerDao");
		daoList.add("com.thinkgem.jeesite.modules.exam.dao.ExamLdScoreMonthDao");
		daoList.add("com.thinkgem.jeesite.modules.exam.dao.ExamLdScoreDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairLearnPowerDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairPoliceHomeDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairXzyDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairBrandManagementDao");
		daoList.add("com.thinkgem.jeesite.modules.exam.dao.ExamUnitAllPublicDao");
		daoList.add("com.thinkgem.jeesite.modules.exam.dao.ExamUnitAllPublicYearDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairYqContolDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairYjBuildDao");
		daoList.add("com.thinkgem.jeesite.modules.affair.dao.AffairBasicKnowledgeDao");
		List<String> resDaoList = Lists.newArrayList();
		try {
			if(null == num || "".equals(num)){
				resDaoList = daoList;
			}else {
				int number = Integer.parseInt(num);
				resDaoList.add(daoList.get(number));
			}
			importEntityService.importDatas(resDaoList, 100000, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resDaoList;
	}
}