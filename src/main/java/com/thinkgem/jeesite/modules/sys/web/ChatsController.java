package com.thinkgem.jeesite.modules.sys.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.ChartLabelUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.affair.dao.AffairInteriorInstructorLibraryDao;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.affair.service.*;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.exam.service.ExamLdScoreMonthService;
import com.thinkgem.jeesite.modules.exam.service.ExamUnitAllPublicService;
import com.thinkgem.jeesite.modules.exam.service.ExamUnitAllPublicYearService;
import com.thinkgem.jeesite.modules.org.service.OrgBianzhiService;
import com.thinkgem.jeesite.modules.org.service.OrgJobNumberService;
import com.thinkgem.jeesite.modules.org.web.OrgBianzhiController;
import com.thinkgem.jeesite.modules.personnel.service.*;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 图表的控制器
 * 
 * @author laphicet
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/charts")
public class ChatsController {

	/**
	 * 顶级机构的id
	 */
	public static final String TOP_OFFICE_ID = "1";

	@Autowired
	PersonnelBaseService personnelBaseService;

	@Autowired
	OfficeService officeService;

	@Autowired
	OrgBianzhiService orgBianzhiService;

	@Autowired
	AffairNewsService affairNewsService;

	@Autowired
	AffairYqContolService affairYqContolService;

	@Autowired
	OrgBianzhiController orgBianzhiController;
	
	@Autowired
	OrgJobNumberService orgJobNumberService;

	@Autowired
	AffairDcwtLibraryService affairDcwtLibraryService;
	
	@Autowired
	AffairTousujubaoguanliService affairTousujubaoguanliService;
	
	@Autowired
	AffairConsolationWorkInfoService affairConsolationWorkInfoService;
	
	@Autowired
	PersonnelHrRecordService personnelHrRecordService;
	
	@Autowired
	AffairBorrowService affairBorrowService;
	
	@Autowired
	AffairPoliceRankService affairPoliceRankService;
	
	@Autowired
	AffairTwoOneService affairTwoOneService;

	@Autowired
	AffairOrgRewardPunishService affairOrgRewardPunishService;
	
	@Autowired
	AffairGeneralSituationService affairGeneralSituationService;
	
	@Autowired
	AffairPartyMemberService affairPartyMemberService;
	
	@Autowired
	AffairZyInfoService affairZyInfoService;
	
	@Autowired
	AffairZkInfoService affairZkInfoService;

	@Autowired
	AffairGroupStudyService affairGroupStudyService;

	@Autowired
	AffairFocusStudyService affairFocusStudyService;

	@Autowired
    AffairDisciplinaryActionService affairDisciplinaryActionService;

	@Autowired
    AffairTalkManagementService affairTalkManagementService;

	@Autowired
    AffairEmpiricalPracticeService affairEmpiricalPracticeService;

	@Autowired
    AffairZxInfoService affairZxInfoService;

	@Autowired
    AffairPoliceHomeService affairPoliceHomeService;

	@Autowired
    AffairXzyService affairXzyService;

	@Autowired
	AffairLzxxjyActivitiesService affairLzxxjyActivitiesService;

	@Autowired
	AffairTeamDisciplineService affairTeamDisciplineService;

    @Autowired
    AffairGzService affairGzService;

    @Autowired
    AffairMjxyReportService affairMjxyReportService;

    @Autowired
    AffairPolicewomanBaseService affairPolicewomanBaseService;

    @Autowired
    AffairOrganizationBulidService affairOrganizationBulidService;

    @Autowired
	AffairChildSubsidyService affairChildSubsidyService;

    @Autowired
	AffairGhActivityEnrollService affairGhActivityEnrollService;

    @Autowired
	AffairGhActivityRecordService affairGhActivityRecordService;

    @Autowired
	AffairCollectiveAwardService affairCollectiveAwardService;

    @Autowired
	AffairPersonalAwardService affairPersonalAwardService;

    @Autowired
	AffairTwPersonalAwardService affairTwPersonalAwardService;

    @Autowired
	AffairTwUnitAwardService affairTwUnitAwardService;

    @Autowired
	AffairBrandManagementService affairBrandManagementService;

    @Autowired
	AffairTnActivityEnrollService affairTnActivityEnrollService;

    @Autowired
	AffairTnActivityRecordService affairTnActivityRecordService;

    @Autowired
	AffairDeedsBriefingService affairDeedsBriefingService;

    @Autowired
	AffairThoughtAnalysisService affairThoughtAnalysisService;

    @Autowired
	AffairTuanzuzhiResearchArticleService affairTuanzuzhiResearchArticleService;

    @Autowired
	AffairWorkInfoService affairWorkInfoService;

    @Autowired
	AffairWorkSummaryService affairWorkSummaryService;

    @Autowired
	AffairActivityStyleService affairActivityStyleService;

    @Autowired
	AffairTixieActivityStyleService affairTixieActivityStyleService;

    @Autowired
    ExamUnitAllPublicService examUnitAllPublicService;

    @Autowired
	ExamLdScoreMonthService examLdScoreMonthService;

    @Autowired
	ExamUnitAllPublicYearService examUnitAllPublicYearService;

    @Autowired
	private AffairLifeMeetService affairLifeMeetService;

    @Autowired
	private AffairAssessService affairAssessService;

    @Autowired
	private AffairHealthCheckupService affairHealthCheckupService;

    @Autowired
	private AffairCommentService affairCommentService;

	@Autowired
	private AffairTjRegisterService affairTjRegisterService;

	@Autowired
	private AffairGroupManagementService affairGroupManagementService;

	@Autowired
	private AffairEducationCommentService affairEducationCommentService;

	@Autowired
	private AffairRqlzIntegrityService affairRqlzIntegrityService;

	@Autowired
	private AffairLianzhengSuperviseService affairLianzhengSuperviseService;

	@Autowired
	private AffairActivistService affairActivistService;

	@Autowired
	private AffairDevelopObjectService affairDevelopObjectService;

	@Autowired
	private AffairOtherPartyRepresentativeService affairOtherPartyRepresentativeService;

	@Autowired
	private AffairElectionService affairElectionService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private AffairTypicalPersonService affairTypicalPersonService;

	@Autowired
	private AffairTypicalTeamService affairTypicalTeamService;

	@Autowired
	private AffairActiveService affairActiveService;

	@Autowired
	private AffairXcUnitRewardService affairXcUnitRewardService;

	@Autowired
	private AffairPersonalRewardService affairPersonalRewardService;

	@Autowired
	private AffairWenyiService affairWenyiService;

	@Autowired
	private AffairWenhuaService affairWenhuaService;

	@Autowired
	private AffairTwBaseSignService affairTwBaseSignService;

	@Autowired
	private AffairYjGoOutReportService affairYjGoOutReportService;

	@Autowired
	private PersonnelSkillService personnelSkillService;//高层次人才

	@Autowired
	private AffairSevenKnowledgeService sevenKnowledgeService;

	@Autowired
	private PersonnelPoliceRankService personnelPoliceRankService;

	@Autowired
	private AffairInteriorInstructorLibraryDao affairInteriorInstructorLibraryDao;

	@Autowired
	private AffairBasicKnowledgeService affairBasicKnowledgeService;

	@Autowired
	private PersonnelXueliService personnelXueliService;

	@Autowired
	private PersonnelJobService personnelJobService;

	/*去掉左侧组织树
	@RequestMapping("index")
	public ModelAndView index(String target) {
		ModelAndView view = new ModelAndView();
		view.addObject("id", UserUtils.getUser().getId());
		view.addObject("target", target);
		view.setViewName("modules/charts/index");
		return view;
	}*/
	//党建统计分析
	/*此部分注释掉  不再使用机构树*/
	/*@RequestMapping("partyIndex")
	public ModelAndView partyIndex() {
		ModelAndView view = new ModelAndView();
		view.addObject("id", UserUtils.getUser().getId());
		view.setViewName("modules/charts/partyIndex");
		return view;
	}*/
	//机构统计分析
	@RequestMapping("organizationIndex")
	public ModelAndView organizationIndex() {
		ModelAndView view = new ModelAndView();
		view.addObject("id", UserUtils.getUser().getId());
		view.setViewName("modules/charts/organizationIndex");
		return view;
	}
/*不再使用左侧机构树
	//宣传教育统计分析
	@RequestMapping("publicityIndex")
	public ModelAndView publicityIndex() {
		ModelAndView view = new ModelAndView();
		view.addObject("id", UserUtils.getUser().getId());
		view.setViewName("modules/charts/publicityIndex");
		return view;
	}*/

	//教育训练情况   10.29隐藏左边树  菜单栏由 /sys/charts/trainIndex  →  /sys/charts/train
	@RequestMapping("trainIndex")
	public ModelAndView trainIndex() {
		ModelAndView view = new ModelAndView();
		view.addObject("id", UserUtils.getUser().getId());
		view.setViewName("modules/charts/trainIndex");
		return view;
	}

	//纪检监察统计分析
	@RequestMapping("monitorIndex")
	public ModelAndView monitorIndex() {
		ModelAndView view = new ModelAndView();
		view.addObject("id", UserUtils.getUser().getId());
		view.setViewName("modules/charts/monitorIndex");
		return view;
	}

	//j警务督察
	/*@RequestMapping("inspectorIndex")
	public ModelAndView inspectorIndex() {
		ModelAndView view = new ModelAndView();
		view.addObject("id", UserUtils.getUser().getId());
		view.setViewName("modules/charts/inspectorIndex");
		return view;
	}*/

	//工团统计分析
	/*去掉左侧机构树*/
/*	@RequestMapping("labourIndex")
	public ModelAndView labourIndex() {
		ModelAndView view = new ModelAndView();
		view.addObject("id", UserUtils.getUser().getId());
		view.setViewName("modules/charts/labourIndex");
		return view;
	}*/

	/*使用下边团委接口*/
	//青年团建分析
	@RequestMapping("groupIndex")
	public ModelAndView tuanWeiIndex() {
		ModelAndView view = new ModelAndView();
		view.addObject("id", UserUtils.getUser().getId());
		view.setViewName("modules/charts/groupIndex");
		return view;
	}

	//团委统计分析
	@RequestMapping("tuanWeiIndex")
	public ModelAndView youthLeagueCommitteeIndex() {
		ModelAndView view = new ModelAndView();
		view.addObject("id", UserUtils.getUser().getId());
		view.addObject("dateType", "1");
		view.addObject("year", DateUtils.getYear());
		view.addObject("month", DateUtils.getYear()+"-"+DateUtils.getMonth());
		view.setViewName("modules/charts/group");
		view.setViewName("modules/charts/youthLeagueIndex");
		return view;
	}
	//团委统计分析  年龄分布
	@RequestMapping("youthLeague")
	@ResponseBody
	public Map<String,String> youthLeagueCommittee(String dateType, Integer year, String dateStart, String dateEnd, String month) {
		String smailAge="28";
		String bigAge="35";
		Map<String,String> map = new HashMap<>();
		map = affairTjRegisterService.findTuanQingAge(dateType,year,dateStart,dateEnd,month,smailAge,bigAge);
		return map;
	}

	//团委统计分析  团费收缴情况
	@RequestMapping("youthLeagueFee")
	@ResponseBody
	public List<Map<String,String>> youthLeagueFee(String dateType,Integer year,String dateStart, String dateEnd,String month) {
		String officeId = UserUtils.getUser().getOffice().getId();
		List<Map<String,String>> map = new ArrayList<>();
		map = affairTjRegisterService.findTourFee(officeId,dateType,year,month,dateStart,dateEnd);
		return map;
	}

	//团委统计分析  学历情况
	@RequestMapping("youthLeagueEducation")
	@ResponseBody
	public List<Map<String,String>> youthLeagueEducation(String dateType, Integer year, String dateStart, String dateEnd, String month){
		String officeId =UserUtils.getUser().getOffice().getId();
		List<Map<String,String>> list = affairTjRegisterService.findEducation(officeId,dateType,year,dateStart,dateEnd,month);
		//最后一个为总数 通过总数计算 其他
		/*Map<String,String> map = list.get(list.size()-1);
		Object s = map.get("num");
		long other = 0;
		for (Map<String,String> item:list) {
			other = sum - Integer.valueOf(item.get("num"));
		}
		other+=sum;
		list.get(list.size()-1).put("num",other+"");
		list.get(list.size()-1).put("lable","其他");*/
		return list;
	}

	//团委统计分析  政治面貌情况
	@RequestMapping("youthLeaguePolitical")
	@ResponseBody
	public List<Map<String,String>> youthLeaguePolitical(String dateType, Integer year, String dateStart, String dateEnd, String month){
		String officeId =UserUtils.getUser().getOffice().getId();
		List<Map<String,String>> list = affairTjRegisterService.countPolitical(officeId,dateType,year,dateStart,dateEnd,month);
		return list;
	}

	/**
	 * 专兼职团干部
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("leagueCadres")
	@ResponseBody
	public List<Map<String,String>> youthLeagueCadres(String dateType, Integer year, String dateStart, String dateEnd, String month){
		String officeId =UserUtils.getUser().getOffice().getId();
		List<Map<String,String>> list = affairTwBaseSignService.countCares(officeId,dateType,year,dateStart,dateEnd,month);
		//List<Map<String,String>> list = affairTjRegisterService.countCadres(officeId,dateType,year,dateStart,dateEnd,month);
		return list;
	}

	/**
	 * 团内活动信息   本单位及以下
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("leagueActivity")
	@ResponseBody
	public List<Map<String,String>> youthLeagueActivity(String dateType, Integer year, String dateStart, String dateEnd, String month){

		List<Map<String,String>> list = affairTnActivityRecordService.countActivity(dateType,year,dateStart,dateEnd,month);
		return list;
	}

    /**
     * 团内活动信息   三处和局机关
     * @param dateType
     * @param year
     * @param dateStart
     * @param dateEnd
     * @param month
     * @return
     */
    @RequestMapping("leagueTopActivity")
    @ResponseBody
    public List<Map<String,String>> youthLeagueTopActivity(String dateType, Integer year, String dateStart, String dateEnd, String month){

        List<Map<String,String>> list = affairTnActivityRecordService.countTopActivity(dateType,year,dateStart,dateEnd,month);
        return list;
    }

	//团委统计分析  团员教育评议
	@RequestMapping("youthLeagueComment")
	@ResponseBody
	public List<Map<String,String>> youthLeagueComment(String dateType, Integer year, String dateStart, String dateEnd, String month){
		String officeId =UserUtils.getUser().getOffice().getId();
		List<Map<String,String>> list = affairEducationCommentService.countComment(dateType,year,dateStart,dateEnd,month,officeId);
		return list;
	}

	//组织干部统计分析
	@RequestMapping("leaderIndex")
	public ModelAndView leaderIndex() {

		ModelAndView view = new ModelAndView();
		view.addObject("id", UserUtils.getUser().getId());
		view.setViewName("modules/charts/leaderIndex");
		return view;
	}
	//绩效考核统计分析
	@RequestMapping("performanceIndex")
	public ModelAndView examIndex() {
		ModelAndView view = new ModelAndView();
		view.addObject("id", UserUtils.getUser().getId());
		view.setViewName("modules/charts/examIndex");
		return view;
	}
	/**
	 * 人员信息情况
	 * 
	 * @return
	 */
//	@RequestMapping("personnel")	去掉左侧组织树
	@RequestMapping("index")
	public ModelAndView personnel(String id, String target) {
		ModelAndView view = new ModelAndView();
		view.addObject("id", id);
		view.addObject("target", target);
		view.setViewName("modules/charts/personnel");
		return view;
	}

	/**
	 * 人员政治面貌信息
	 * 
	 * @return
	 */
	@RequestMapping("personnel-politics")
	public @ResponseBody List<Map<String, Object>> personnelPolitics(String id) {
		List<Map<String, Object>> infos;
		//原来用unit_code查询，改为用workunit_id查询
		/*去掉左侧组织树
		if (id != null && !TOP_OFFICE_ID.equals(id)) {
			List<String> ids = officeService.findIdsByParentId(id);
			if (CollectionUtils.isEmpty(ids)) {
				infos = personnelBaseService.findInfosByCode(id);
			} else {
				ids.add(id);
				infos = personnelBaseService.findInfosByCodes(ids);
			}
		} else {
			infos = personnelBaseService.findInfosByCode(null);
		}*/
		infos = personnelBaseService.findInfosByCode(null);
		return infos;

	}

	/**
	 * 民警因私外出
	 *
	 * @return
	 */
	@RequestMapping("policeYswc")
	@ResponseBody
	public List<Map<String, Object>> policeYswc() {

		List<AffairYjGoOutReport> affairYjGoOutReportList = affairYjGoOutReportService.selectAllBeanTjfx();
		//0-5
		Integer number1 = 0;
		//6-10
		Integer number2 = 0;
		//11-15
		Integer number3 = 0;
		//16-30
		Integer number4 = 0;
		//30以上
		Integer number5 = 0;
		for (int a = 0; a < affairYjGoOutReportList.size(); a++){
			AffairYjGoOutReport affairYjGoOutReport = affairYjGoOutReportList.get(a);
			String size = affairYjGoOutReport.getSize();
			double si = 0.0;
			if (StringUtils.isBlank(size)){
				si = 0.0;
			}if (StringUtils.isNotBlank(size)) {
				si = Double.parseDouble(size);
			}
			if (0.0 <= si && 5.0 >= si){
				number1++;
			}
			if (6.0 <= si && 10.0 >= si){
				number2++;
			}
			if (11.0 <= si && 15.0 >= si){
				number3++;
			}
			if (16.0 <= si && 30.0 >= si){
				number4++;
			}
			if (31.0 < si){
				number5++;
			}
		}
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String,Object> map = new HashMap<>();
		map.put("one",String.valueOf(number1));
		map.put("two",String.valueOf(number2));
		map.put("three",String.valueOf(number3));
		map.put("four",String.valueOf(number4));
		map.put("five",String.valueOf(number5));
		list.add(map);
		return list;
	}


	/**
	 * 人员岗位情况
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("personnel-job")
	public @ResponseBody List<Map<String, Object>> personnelJob(String id) {
		List<Map<String, Object>> infos;
		//原来用unit_code查询，改为用workunit_id查询
		if (id != null && !TOP_OFFICE_ID.equals(id)) {
			List<String> ids = officeService.findIdsByParentId(id);
			if (CollectionUtils.isEmpty(ids)) {
				infos = personnelBaseService.findJobInfosByCode(id);
			} else {
				ids.add(id);
				infos = personnelBaseService.findJobInfosByCodes(ids);
			}
		} else {
			infos = personnelBaseService.findJobInfosByCode(null);
		}
		return infos;
	}

	/**
	 * 机构信息情况
	 * 
	 * @return
	 */
	@RequestMapping("organization")
	public ModelAndView organization(String id) {
		ModelAndView view = new ModelAndView();
		view.addObject("id", id);
		view.addObject("year", DateUtils.getYear());
		view.setViewName("modules/charts/organization");
		return view;
	}

	/**
	 * 人员编制情况
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("bianzhi")
	public @ResponseBody List<Map<String, Object>> bianzhi(String id, Integer year) {
		List<Map<String, Object>> infos;
		if (id != null && !TOP_OFFICE_ID.equals(id)) {
			List<String> ids = officeService.findIdsByParentId(id);
			if (CollectionUtils.isEmpty(ids)) {
				infos = orgBianzhiService.findInfoByOrgId(id, year);
			} else {
				ids.add(id);
				infos = orgBianzhiService.findInfoByOrgIds(ids, year);
			}
		} else {
			infos = orgBianzhiService.findInfoByOrgId(null, year);
		}
		return infos;
	}
	
	/**职数
	 * @param id
	 * @return
	 */
	@RequestMapping("zhishu")
	public @ResponseBody List<Map<String, Object>> zhishu(String id, Integer year) {
		List<Map<String, Object>> infos;
		if(id != null && !TOP_OFFICE_ID.equals(id)) {
			List<String> ids = officeService.findIdsByParentId(id);
			if(CollectionUtils.isEmpty(ids)) {
				infos = orgJobNumberService.findInfoByOrgId(id, year);
			} else {
				ids.add(id);
				infos = orgJobNumberService.findInfoByOrgIds(ids, year);
			}
		} else {
			infos = orgJobNumberService.findInfoByOrgId(null, year);
		}
		return infos;
	}

	/**
	 * 宣传教育情况
	 * 
	 * @return
	 */
	/*不在使用左侧机构树*/
	@RequestMapping("publicityIndex")
	public ModelAndView publicity(String id) {
		String companyId = UserUtils.getUser().getCompany().getId();
		String parentId = UserUtils.getUser().getOffice().getParentId();
		String officeId = UserUtils.getUser().getOffice().getId();
		String id1 = UserUtils.getUser().getOffice().getId();
		ModelAndView view = new ModelAndView();
		view.addObject("id", id);
		view.addObject("dateType", "1");
		view.addObject("year", DateUtils.getYear());
		view.addObject("month", DateUtils.getYear()+"-"+DateUtils.getMonth());
		view.addObject("companyId", companyId);
		view.addObject("officeId", officeId);
		view.addObject("parentId", parentId);
		view.addObject("id1", id1);
		view.setViewName("modules/charts/publicity");
		return view;
	}

	/**
	 * 宣传集体奖励统计分析
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("unitReward")
	public @ResponseBody Map<String, Object> unitReward(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
					infos = affairXcUnitRewardService.findInfoByRewardName(id, null, null, null, month);
			}
		} else if ("2".equals(dateType)) {//年度
			if (month != null && month.length() > 0) {
					infos = affairXcUnitRewardService.findInfoByRewardName(id, year, null, null, null);
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				if (month != null && month.length() > 0) {
						infos = affairXcUnitRewardService.findInfoByRewardName(id, null, startDate, endDate, month);
				}
			}
		}
		return infos;
	}

	/**
	 * 宣传教育个人奖励统计分析
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("personnelReward")
	public @ResponseBody Map<String, Object> personnelReward(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				infos = affairPersonalRewardService.findInfoByRewardName(id, null, null, null, month);
			}
		} else if ("2".equals(dateType)) {//年度
			if (month != null && month.length() > 0) {
				infos = affairPersonalRewardService.findInfoByRewardName(id, year, null, null, null);
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				if (month != null && month.length() > 0) {
					infos = affairPersonalRewardService.findInfoByRewardName(id, null, startDate, endDate, month);
				}
			}
		}
		return infos;
	}

	/**
	 *刊稿稿件 统计分析
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("news")
	public @ResponseBody List<Map<String, Object>> news(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Integer nncsumOne = 0;
		Integer nncsumTwo = 0;
		Integer nncsumThree = 0;
		Integer nncsumFour = 0;
		Integer nncsumFive = 0;

		Integer lzcsumOne = 0;
		Integer lzcsumTwo = 0;
		Integer lzcsumThree = 0;
		Integer lzcsumFour = 0;
		Integer lzcsumFive = 0;

		Integer bhcsumOne = 0;
		Integer bhcsumTwo = 0;
		Integer bhcsumThree = 0;
		Integer bhcsumFour = 0;
		Integer bhcsumFive = 0;

		List<Map<String, Object>> infos = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        //1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				infos = affairNewsService.findEchartsInfoByUnit(id, null, null, null, month);
				for (int i = 0; i < infos.size(); i++) {
					Object units = infos.get(i).get("unit");

					if (units.toString().contains("南宁处")){
						Object unit = infos.get(i).get("unit");
						Object sum1 = infos.get(i).get("sum1");
						Object sum2 = infos.get(i).get("sum2");
						Object sum3 = infos.get(i).get("sum3");
						Object sum4 = infos.get(i).get("sum4");
						Object sum5 = infos.get(i).get("sum5");
						Object sum = infos.get(i).get("sum");
						nncsumOne += Integer.valueOf(sum1.toString());
						nncsumTwo += Integer.valueOf(sum2.toString());
						nncsumThree += Integer.valueOf(sum3.toString());
						nncsumFour += Integer.valueOf(sum4.toString());
						nncsumFive += Integer.valueOf(sum5.toString());
					}
					if (units.toString().contains("柳州处")){
						Object unit = infos.get(i).get("unit");
						Object sum1 = infos.get(i).get("sum1");
						Object sum2 = infos.get(i).get("sum2");
						Object sum3 = infos.get(i).get("sum3");
						Object sum4 = infos.get(i).get("sum4");
						Object sum5 = infos.get(i).get("sum5");
						Object sum = infos.get(i).get("sum");
						lzcsumOne += Integer.valueOf(sum1.toString());
						lzcsumTwo += Integer.valueOf(sum2.toString());
						lzcsumThree += Integer.valueOf(sum3.toString());
						lzcsumFour += Integer.valueOf(sum4.toString());
						lzcsumFive += Integer.valueOf(sum5.toString());
					}
					if (units.toString().contains("北海处")){
						Object unit = infos.get(i).get("unit");
						Object sum1 = infos.get(i).get("sum1");
						Object sum2 = infos.get(i).get("sum2");
						Object sum3 = infos.get(i).get("sum3");
						Object sum4 = infos.get(i).get("sum4");
						Object sum5 = infos.get(i).get("sum5");
						Object sum = infos.get(i).get("sum");
						bhcsumOne += Integer.valueOf(sum1.toString());
						bhcsumTwo += Integer.valueOf(sum2.toString());
						bhcsumThree += Integer.valueOf(sum3.toString());
						bhcsumFour += Integer.valueOf(sum4.toString());
						bhcsumFive += Integer.valueOf(sum5.toString());
					}


				}
				Map<String,Object> nncmap = new HashMap<String,Object>();
				nncmap.put("unit","南宁处");
				nncmap.put("sumOne",nncsumOne);
				nncmap.put("sumTwo",nncsumTwo);
				nncmap.put("sumThree",nncsumThree);
				nncmap.put("sumFour",nncsumFour);
				nncmap.put("sumFive",nncsumFive);
				if(nncmap != null && nncmap.size() > 0) {
					list.add(nncmap);
				}
				Map<String,Object> lzcmap = new HashMap<String,Object>();
				lzcmap.put("unit","柳州处");
				lzcmap.put("sumOne",lzcsumOne);
				lzcmap.put("sumTwo",lzcsumTwo);
				lzcmap.put("sumThree",lzcsumThree);
				lzcmap.put("sumFour",lzcsumFour);
				lzcmap.put("sumFive",lzcsumFive);
				if(lzcmap != null && lzcmap.size() > 0) {
					list.add(lzcmap);
				}
				Map<String,Object> bhcmap = new HashMap<String,Object>();
				bhcmap.put("unit","北海处");
				bhcmap.put("sumOne",bhcsumOne);
				bhcmap.put("sumTwo",bhcsumTwo);
				bhcmap.put("sumThree",bhcsumThree);
				bhcmap.put("sumFour",bhcsumFour);
				bhcmap.put("sumFive",bhcsumFive);
				if(bhcmap != null && bhcmap.size() > 0) {
					list.add(bhcmap);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (month != null && month.length() > 0) {
				infos = affairNewsService.findEchartsInfoByUnit(id, year, null, null, null);
				for (int i = 0; i < infos.size(); i++) {
					Object units = infos.get(i).get("unit");

					if (units.toString().contains("南宁处")){
						Object unit = infos.get(i).get("unit");
						Object sum1 = infos.get(i).get("sum1");
						Object sum2 = infos.get(i).get("sum2");
						Object sum3 = infos.get(i).get("sum3");
						Object sum4 = infos.get(i).get("sum4");
						Object sum5 = infos.get(i).get("sum5");
						Object sum = infos.get(i).get("sum");
						nncsumOne += Integer.valueOf(sum1.toString());
						nncsumTwo += Integer.valueOf(sum2.toString());
						nncsumThree += Integer.valueOf(sum3.toString());
						nncsumFour += Integer.valueOf(sum4.toString());
						nncsumFive += Integer.valueOf(sum5.toString());
					}
					if (units.toString().contains("柳州处")){
						Object unit = infos.get(i).get("unit");
						Object sum1 = infos.get(i).get("sum1");
						Object sum2 = infos.get(i).get("sum2");
						Object sum3 = infos.get(i).get("sum3");
						Object sum4 = infos.get(i).get("sum4");
						Object sum5 = infos.get(i).get("sum5");
						Object sum = infos.get(i).get("sum");
						lzcsumOne += Integer.valueOf(sum1.toString());
						lzcsumTwo += Integer.valueOf(sum2.toString());
						lzcsumThree += Integer.valueOf(sum3.toString());
						lzcsumFour += Integer.valueOf(sum4.toString());
						lzcsumFive += Integer.valueOf(sum5.toString());
					}
					if (units.toString().contains("北海处")){
						Object unit = infos.get(i).get("unit");
						Object sum1 = infos.get(i).get("sum1");
						Object sum2 = infos.get(i).get("sum2");
						Object sum3 = infos.get(i).get("sum3");
						Object sum4 = infos.get(i).get("sum4");
						Object sum5 = infos.get(i).get("sum5");
						Object sum = infos.get(i).get("sum");
						bhcsumOne += Integer.valueOf(sum1.toString());
						bhcsumTwo += Integer.valueOf(sum2.toString());
						bhcsumThree += Integer.valueOf(sum3.toString());
						bhcsumFour += Integer.valueOf(sum4.toString());
						bhcsumFive += Integer.valueOf(sum5.toString());
					}


				}
				Map<String,Object> nncmap = new HashMap<String,Object>();
				nncmap.put("unit","南宁处");
				nncmap.put("sumOne",nncsumOne);
				nncmap.put("sumTwo",nncsumTwo);
				nncmap.put("sumThree",nncsumThree);
				nncmap.put("sumFour",nncsumFour);
				nncmap.put("sumFive",nncsumFive);
				if(nncmap != null && nncmap.size() > 0) {
					list.add(nncmap);
				}
				Map<String,Object> lzcmap = new HashMap<String,Object>();
				lzcmap.put("unit","柳州处");
				lzcmap.put("sumOne",lzcsumOne);
				lzcmap.put("sumTwo",lzcsumTwo);
				lzcmap.put("sumThree",lzcsumThree);
				lzcmap.put("sumFour",lzcsumFour);
				lzcmap.put("sumFive",lzcsumFive);
				if(lzcmap != null && lzcmap.size() > 0) {
					list.add(lzcmap);
				}
				Map<String,Object> bhcmap = new HashMap<String,Object>();
				bhcmap.put("unit","北海处");
				bhcmap.put("sumOne",bhcsumOne);
				bhcmap.put("sumTwo",bhcsumTwo);
				bhcmap.put("sumThree",bhcsumThree);
				bhcmap.put("sumFour",bhcsumFour);
				bhcmap.put("sumFive",bhcsumFive);
				if(bhcmap != null && bhcmap.size() > 0) {
					list.add(bhcmap);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				if (month != null && month.length() > 0) {
					infos = affairNewsService.findEchartsInfoByUnit(id, null, startDate, endDate, month);
					for (int i = 0; i < infos.size(); i++) {
						Object units = infos.get(i).get("unit");

						if (units.toString().contains("南宁处")){
							Object unit = infos.get(i).get("unit");
							Object sum1 = infos.get(i).get("sum1");
							Object sum2 = infos.get(i).get("sum2");
							Object sum3 = infos.get(i).get("sum3");
							Object sum4 = infos.get(i).get("sum4");
							Object sum5 = infos.get(i).get("sum5");
							Object sum = infos.get(i).get("sum");
							nncsumOne += Integer.valueOf(sum1.toString());
							nncsumTwo += Integer.valueOf(sum2.toString());
							nncsumThree += Integer.valueOf(sum3.toString());
							nncsumFour += Integer.valueOf(sum4.toString());
							nncsumFive += Integer.valueOf(sum5.toString());
						}
						if (units.toString().contains("柳州处")){
							Object unit = infos.get(i).get("unit");
							Object sum1 = infos.get(i).get("sum1");
							Object sum2 = infos.get(i).get("sum2");
							Object sum3 = infos.get(i).get("sum3");
							Object sum4 = infos.get(i).get("sum4");
							Object sum5 = infos.get(i).get("sum5");
							Object sum = infos.get(i).get("sum");
							lzcsumOne += Integer.valueOf(sum1.toString());
							lzcsumTwo += Integer.valueOf(sum2.toString());
							lzcsumThree += Integer.valueOf(sum3.toString());
							lzcsumFour += Integer.valueOf(sum4.toString());
							lzcsumFive += Integer.valueOf(sum5.toString());
						}
						if (units.toString().contains("北海处")){
							Object unit = infos.get(i).get("unit");
							Object sum1 = infos.get(i).get("sum1");
							Object sum2 = infos.get(i).get("sum2");
							Object sum3 = infos.get(i).get("sum3");
							Object sum4 = infos.get(i).get("sum4");
							Object sum5 = infos.get(i).get("sum5");
							Object sum = infos.get(i).get("sum");
							bhcsumOne += Integer.valueOf(sum1.toString());
							bhcsumTwo += Integer.valueOf(sum2.toString());
							bhcsumThree += Integer.valueOf(sum3.toString());
							bhcsumFour += Integer.valueOf(sum4.toString());
							bhcsumFive += Integer.valueOf(sum5.toString());
						}


					}
					Map<String,Object> nncmap = new HashMap<String,Object>();
					nncmap.put("unit","南宁处");
					nncmap.put("sumOne",nncsumOne);
					nncmap.put("sumTwo",nncsumTwo);
					nncmap.put("sumThree",nncsumThree);
					nncmap.put("sumFour",nncsumFour);
					nncmap.put("sumFive",nncsumFive);
					if(nncmap != null && nncmap.size() > 0) {
						list.add(nncmap);
					}
					Map<String,Object> lzcmap = new HashMap<String,Object>();
					lzcmap.put("unit","柳州处");
					lzcmap.put("sumOne",lzcsumOne);
					lzcmap.put("sumTwo",lzcsumTwo);
					lzcmap.put("sumThree",lzcsumThree);
					lzcmap.put("sumFour",lzcsumFour);
					lzcmap.put("sumFive",lzcsumFive);
					if(lzcmap != null && lzcmap.size() > 0) {
						list.add(lzcmap);
					}
					Map<String,Object> bhcmap = new HashMap<String,Object>();
					bhcmap.put("unit","北海处");
					bhcmap.put("sumOne",bhcsumOne);
					bhcmap.put("sumTwo",bhcsumTwo);
					bhcmap.put("sumThree",bhcsumThree);
					bhcmap.put("sumFour",bhcsumFour);
					bhcmap.put("sumFive",bhcsumFive);
					if(bhcmap != null && bhcmap.size() > 0) {
						list.add(bhcmap);
					}
				}
			}
		}
		return list;
	}

	/**
	 *刊稿稿件 统计分析
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("nncNews")
	public @ResponseBody List<Map<String, Object>> nncNews(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Integer nncsumOne = 0;
		Integer nncsumTwo = 0;
		Integer nncsumThree = 0;
		Integer nncsumFour = 0;
		Integer nncsumFive = 0;

		List<Map<String, Object>> infos = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				infos = affairNewsService.findEchartsInfoByUnit(id, null, null, null, month);
				for (int i = 0; i < infos.size(); i++) {
					Object units = infos.get(i).get("unit");

					if (units.toString().contains("南宁处")){
						Object unit = infos.get(i).get("unit");
						Object sum1 = infos.get(i).get("sum1");
						Object sum2 = infos.get(i).get("sum2");
						Object sum3 = infos.get(i).get("sum3");
						Object sum4 = infos.get(i).get("sum4");
						Object sum5 = infos.get(i).get("sum5");
						Object sum = infos.get(i).get("sum");
						nncsumOne += Integer.valueOf(sum1.toString());
						nncsumTwo += Integer.valueOf(sum2.toString());
						nncsumThree += Integer.valueOf(sum3.toString());
						nncsumFour += Integer.valueOf(sum4.toString());
						nncsumFive += Integer.valueOf(sum5.toString());
					}
				}
				Map<String,Object> nncmap = new HashMap<String,Object>();
				nncmap.put("unit","南宁处");
				nncmap.put("sumOne",nncsumOne);
				nncmap.put("sumTwo",nncsumTwo);
				nncmap.put("sumThree",nncsumThree);
				nncmap.put("sumFour",nncsumFour);
				nncmap.put("sumFive",nncsumFive);
				if(nncmap != null && nncmap.size() > 0) {
					list.add(nncmap);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (month != null && month.length() > 0) {
				infos = affairNewsService.findEchartsInfoByUnit(id, year, null, null, null);
				for (int i = 0; i < infos.size(); i++) {
					Object units = infos.get(i).get("unit");

					if (units.toString().contains("南宁处")){
						Object unit = infos.get(i).get("unit");
						Object sum1 = infos.get(i).get("sum1");
						Object sum2 = infos.get(i).get("sum2");
						Object sum3 = infos.get(i).get("sum3");
						Object sum4 = infos.get(i).get("sum4");
						Object sum5 = infos.get(i).get("sum5");
						Object sum = infos.get(i).get("sum");
						nncsumOne += Integer.valueOf(sum1.toString());
						nncsumTwo += Integer.valueOf(sum2.toString());
						nncsumThree += Integer.valueOf(sum3.toString());
						nncsumFour += Integer.valueOf(sum4.toString());
						nncsumFive += Integer.valueOf(sum5.toString());
					}
				}
				Map<String,Object> nncmap = new HashMap<String,Object>();
				nncmap.put("unit","南宁处");
				nncmap.put("sumOne",nncsumOne);
				nncmap.put("sumTwo",nncsumTwo);
				nncmap.put("sumThree",nncsumThree);
				nncmap.put("sumFour",nncsumFour);
				nncmap.put("sumFive",nncsumFive);
				if(nncmap != null && nncmap.size() > 0) {
					list.add(nncmap);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				if (month != null && month.length() > 0) {
					infos = affairNewsService.findEchartsInfoByUnit(id, null, startDate, endDate, month);
					for (int i = 0; i < infos.size(); i++) {
						Object units = infos.get(i).get("unit");

						if (units.toString().contains("南宁处")){
							Object unit = infos.get(i).get("unit");
							Object sum1 = infos.get(i).get("sum1");
							Object sum2 = infos.get(i).get("sum2");
							Object sum3 = infos.get(i).get("sum3");
							Object sum4 = infos.get(i).get("sum4");
							Object sum5 = infos.get(i).get("sum5");
							Object sum = infos.get(i).get("sum");
							nncsumOne += Integer.valueOf(sum1.toString());
							nncsumTwo += Integer.valueOf(sum2.toString());
							nncsumThree += Integer.valueOf(sum3.toString());
							nncsumFour += Integer.valueOf(sum4.toString());
							nncsumFive += Integer.valueOf(sum5.toString());
						}
					}
					Map<String,Object> nncmap = new HashMap<String,Object>();
					nncmap.put("unit","南宁处");
					nncmap.put("sumOne",nncsumOne);
					nncmap.put("sumTwo",nncsumTwo);
					nncmap.put("sumThree",nncsumThree);
					nncmap.put("sumFour",nncsumFour);
					nncmap.put("sumFive",nncsumFive);
					if(nncmap != null && nncmap.size() > 0) {
						list.add(nncmap);
					}
				}
			}
		}
		return list;
	}

	/**
	 *刊稿稿件 统计分析
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("lzcNews")
	public @ResponseBody List<Map<String, Object>> lzcNews(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {

		Integer lzcsumOne = 0;
		Integer lzcsumTwo = 0;
		Integer lzcsumThree = 0;
		Integer lzcsumFour = 0;
		Integer lzcsumFive = 0;

		List<Map<String, Object>> infos = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				infos = affairNewsService.findEchartsInfoByUnit(id, null, null, null, month);
				for (int i = 0; i < infos.size(); i++) {
					Object units = infos.get(i).get("unit");


					if (units.toString().contains("柳州处")){
						Object unit = infos.get(i).get("unit");
						Object sum1 = infos.get(i).get("sum1");
						Object sum2 = infos.get(i).get("sum2");
						Object sum3 = infos.get(i).get("sum3");
						Object sum4 = infos.get(i).get("sum4");
						Object sum5 = infos.get(i).get("sum5");
						Object sum = infos.get(i).get("sum");
						lzcsumOne += Integer.valueOf(sum1.toString());
						lzcsumTwo += Integer.valueOf(sum2.toString());
						lzcsumThree += Integer.valueOf(sum3.toString());
						lzcsumFour += Integer.valueOf(sum4.toString());
						lzcsumFive += Integer.valueOf(sum5.toString());
					}

				}

				Map<String,Object> lzcmap = new HashMap<String,Object>();
				lzcmap.put("unit","柳州处");
				lzcmap.put("sumOne",lzcsumOne);
				lzcmap.put("sumTwo",lzcsumTwo);
				lzcmap.put("sumThree",lzcsumThree);
				lzcmap.put("sumFour",lzcsumFour);
				lzcmap.put("sumFive",lzcsumFive);
				if(lzcmap != null && lzcmap.size() > 0) {
					list.add(lzcmap);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (month != null && month.length() > 0) {
				infos = affairNewsService.findEchartsInfoByUnit(id, year, null, null, null);
				for (int i = 0; i < infos.size(); i++) {
					Object units = infos.get(i).get("unit");


					if (units.toString().contains("柳州处")){
						Object unit = infos.get(i).get("unit");
						Object sum1 = infos.get(i).get("sum1");
						Object sum2 = infos.get(i).get("sum2");
						Object sum3 = infos.get(i).get("sum3");
						Object sum4 = infos.get(i).get("sum4");
						Object sum5 = infos.get(i).get("sum5");
						Object sum = infos.get(i).get("sum");
						lzcsumOne += Integer.valueOf(sum1.toString());
						lzcsumTwo += Integer.valueOf(sum2.toString());
						lzcsumThree += Integer.valueOf(sum3.toString());
						lzcsumFour += Integer.valueOf(sum4.toString());
						lzcsumFive += Integer.valueOf(sum5.toString());
					}

				}

				Map<String,Object> lzcmap = new HashMap<String,Object>();
				lzcmap.put("unit","柳州处");
				lzcmap.put("sumOne",lzcsumOne);
				lzcmap.put("sumTwo",lzcsumTwo);
				lzcmap.put("sumThree",lzcsumThree);
				lzcmap.put("sumFour",lzcsumFour);
				lzcmap.put("sumFive",lzcsumFive);
				if(lzcmap != null && lzcmap.size() > 0) {
					list.add(lzcmap);
				}

			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				if (month != null && month.length() > 0) {
					infos = affairNewsService.findEchartsInfoByUnit(id, null, startDate, endDate, month);
					for (int i = 0; i < infos.size(); i++) {
						Object units = infos.get(i).get("unit");
						if (units.toString().contains("柳州处")){
							Object unit = infos.get(i).get("unit");
							Object sum1 = infos.get(i).get("sum1");
							Object sum2 = infos.get(i).get("sum2");
							Object sum3 = infos.get(i).get("sum3");
							Object sum4 = infos.get(i).get("sum4");
							Object sum5 = infos.get(i).get("sum5");
							Object sum = infos.get(i).get("sum");
							lzcsumOne += Integer.valueOf(sum1.toString());
							lzcsumTwo += Integer.valueOf(sum2.toString());
							lzcsumThree += Integer.valueOf(sum3.toString());
							lzcsumFour += Integer.valueOf(sum4.toString());
							lzcsumFive += Integer.valueOf(sum5.toString());
						}
					}
					Map<String,Object> lzcmap = new HashMap<String,Object>();
					lzcmap.put("unit","柳州处");
					lzcmap.put("sumOne",lzcsumOne);
					lzcmap.put("sumTwo",lzcsumTwo);
					lzcmap.put("sumThree",lzcsumThree);
					lzcmap.put("sumFour",lzcsumFour);
					lzcmap.put("sumFive",lzcsumFive);
					if(lzcmap != null && lzcmap.size() > 0) {
						list.add(lzcmap);
					}
				}
			}
		}
		return list;
	}

	/**
	 *刊稿稿件 统计分析
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("bhcNews")
	public @ResponseBody List<Map<String, Object>> bhcNews(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {

		Integer bhcsumOne = 0;
		Integer bhcsumTwo = 0;
		Integer bhcsumThree = 0;
		Integer bhcsumFour = 0;
		Integer bhcsumFive = 0;

		List<Map<String, Object>> infos = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				infos = affairNewsService.findEchartsInfoByUnit(id, null, null, null, month);
				for (int i = 0; i < infos.size(); i++) {
					Object units = infos.get(i).get("unit");

					if (units.toString().contains("北海处")){
						Object unit = infos.get(i).get("unit");
						Object sum1 = infos.get(i).get("sum1");
						Object sum2 = infos.get(i).get("sum2");
						Object sum3 = infos.get(i).get("sum3");
						Object sum4 = infos.get(i).get("sum4");
						Object sum5 = infos.get(i).get("sum5");
						Object sum = infos.get(i).get("sum");
						bhcsumOne += Integer.valueOf(sum1.toString());
						bhcsumTwo += Integer.valueOf(sum2.toString());
						bhcsumThree += Integer.valueOf(sum3.toString());
						bhcsumFour += Integer.valueOf(sum4.toString());
						bhcsumFive += Integer.valueOf(sum5.toString());
					}
				}
				Map<String,Object> bhcmap = new HashMap<String,Object>();
				bhcmap.put("unit","北海处");
				bhcmap.put("sumOne",bhcsumOne);
				bhcmap.put("sumTwo",bhcsumTwo);
				bhcmap.put("sumThree",bhcsumThree);
				bhcmap.put("sumFour",bhcsumFour);
				bhcmap.put("sumFive",bhcsumFive);
				if(bhcmap != null && bhcmap.size() > 0) {
					list.add(bhcmap);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (month != null && month.length() > 0) {
				infos = affairNewsService.findEchartsInfoByUnit(id, year, null, null, null);
				for (int i = 0; i < infos.size(); i++) {
					Object units = infos.get(i).get("unit");

					if (units.toString().contains("北海处")){
						Object unit = infos.get(i).get("unit");
						Object sum1 = infos.get(i).get("sum1");
						Object sum2 = infos.get(i).get("sum2");
						Object sum3 = infos.get(i).get("sum3");
						Object sum4 = infos.get(i).get("sum4");
						Object sum5 = infos.get(i).get("sum5");
						Object sum = infos.get(i).get("sum");
						bhcsumOne += Integer.valueOf(sum1.toString());
						bhcsumTwo += Integer.valueOf(sum2.toString());
						bhcsumThree += Integer.valueOf(sum3.toString());
						bhcsumFour += Integer.valueOf(sum4.toString());
						bhcsumFive += Integer.valueOf(sum5.toString());
					}


				}

				Map<String,Object> bhcmap = new HashMap<String,Object>();
				bhcmap.put("unit","北海处");
				bhcmap.put("sumOne",bhcsumOne);
				bhcmap.put("sumTwo",bhcsumTwo);
				bhcmap.put("sumThree",bhcsumThree);
				bhcmap.put("sumFour",bhcsumFour);
				bhcmap.put("sumFive",bhcsumFive);
				if(bhcmap != null && bhcmap.size() > 0) {
					list.add(bhcmap);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				if (month != null && month.length() > 0) {
					infos = affairNewsService.findEchartsInfoByUnit(id, null, startDate, endDate, month);
					for (int i = 0; i < infos.size(); i++) {
						Object units = infos.get(i).get("unit");

						if (units.toString().contains("北海处")){
							Object unit = infos.get(i).get("unit");
							Object sum1 = infos.get(i).get("sum1");
							Object sum2 = infos.get(i).get("sum2");
							Object sum3 = infos.get(i).get("sum3");
							Object sum4 = infos.get(i).get("sum4");
							Object sum5 = infos.get(i).get("sum5");
							Object sum = infos.get(i).get("sum");
							bhcsumOne += Integer.valueOf(sum1.toString());
							bhcsumTwo += Integer.valueOf(sum2.toString());
							bhcsumThree += Integer.valueOf(sum3.toString());
							bhcsumFour += Integer.valueOf(sum4.toString());
							bhcsumFive += Integer.valueOf(sum5.toString());
						}
					}
					Map<String,Object> bhcmap = new HashMap<String,Object>();
					bhcmap.put("unit","北海处");
					bhcmap.put("sumOne",bhcsumOne);
					bhcmap.put("sumTwo",bhcsumTwo);
					bhcmap.put("sumThree",bhcsumThree);
					bhcmap.put("sumFour",bhcsumFour);
					bhcmap.put("sumFive",bhcsumFive);
					if(bhcmap != null && bhcmap.size() > 0) {
						list.add(bhcmap);
					}
				}
			}
		}
		return list;
	}


	/**
	 * 七知档案 统计分析
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("sevenKnowledge")
	public @ResponseBody List<Map<String, Object>> sevenKnowledge(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String,Object>> list ;
		try{
			list = sevenKnowledgeService.sevenKnowledgeStatistics(dateType, year, dateStart, dateEnd, month);
		}catch (Exception e){
			list = new ArrayList<>();
			System.out.println("七知档案统计分析发生错误");
			e.printStackTrace();
		}
		return list;
	}

//	@RequestMapping("news")
//	public @ResponseBody List<Map<String, Object>> news(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
//		List<Map<String, Object>> infos = null;
//		//1：月度 2：年度 3:时间段
//		if ("1".equals(dateType)) {//月度
//			if (month != null && month.length() > 0) {
//				if (id != null && !TOP_OFFICE_ID.equals(id)) {
//					List<String> ids = officeService.findIdsByParentId(id);
//					if (CollectionUtils.isEmpty(ids)) {
//						infos = affairNewsService.findInfoByUnitId(id, null, null, null, month);
//					} else {
//						ids.add(id);
//						infos = affairNewsService.findInfoByUnitIds(ids, null, null, null, month);
//					}
//				} else {
//					infos = affairNewsService.findInfoByUnitId(null, null, null, null, month);
//				}
//			}
//		}else if("2".equals(dateType)){//年度
//			if (id != null && !TOP_OFFICE_ID.equals(id)) {
//				List<String> ids = officeService.findIdsByParentId(id);
//				if (CollectionUtils.isEmpty(ids)) {
//					infos = affairNewsService.findInfoByUnitId(id, year, null, null, null);
//				} else {
//					ids.add(id);
//					infos = affairNewsService.findInfoByUnitIds(ids, year, null, null, null);
//				}
//			} else {
//				infos = affairNewsService.findInfoByUnitId(null, year, null, null, null);
//			}
//		}else{//年度
//			Date startDate = DateUtils.parseDate(dateStart);
//			Date endDate = DateUtils.parseDate(dateEnd);
//			if (!(startDate ==null && endDate == null)) {
//				if (id != null && !TOP_OFFICE_ID.equals(id)) {
//					List<String> ids = officeService.findIdsByParentId(id);
//					if (CollectionUtils.isEmpty(ids)) {
//						infos = affairNewsService.findInfoByUnitId(id, null, startDate, endDate, null);
//					} else {
//						ids.add(id);
//						infos = affairNewsService.findInfoByUnitIds(ids, null, startDate, endDate, null);
//					}
//				} else {
//					infos = affairNewsService.findInfoByUnitId(null, null, startDate, endDate, null);
//				}
//			}
//		}
//		return infos;
//	}

	/**
	 * 舆情管控情况
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("yuqing")
	public @ResponseBody List<Map<String, Object>> yuqing(String id,String flag, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if ("1".equals(flag)) {//来源
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							infos = affairYqContolService.findInfoByCreateOrgId(id, null, null, null, month);
						} else {
							ids.add(id);
							infos = affairYqContolService.findInfoByCreateOrgIds(ids, null, null, null, month);
						}
					} else {
						infos = affairYqContolService.findInfoByCreateOrgId(null, null, null, null, month);
					}
				} else {//处置情况
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							infos = affairYqContolService.findInfoByCreateOrgId2(id, null,  null, null, month);
						} else {
							ids.add(id);
							infos = affairYqContolService.findInfoByCreateOrgIds2(ids, null,  null, null, month);
						}
					} else {
						infos = affairYqContolService.findInfoByCreateOrgId2(null, null,  null, null, month);
					}
				}
			}
		}else if("2".equals(dateType)){//年度
			if ("1".equals(flag)) {//来源
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairYqContolService.findInfoByCreateOrgId(id, year, null, null, null);
					} else {
						ids.add(id);
						infos = affairYqContolService.findInfoByCreateOrgIds(ids, year, null, null, null);
					}
				} else {
					infos = affairYqContolService.findInfoByCreateOrgId(null, year, null, null, null);
				}
			} else {//处置情况
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairYqContolService.findInfoByCreateOrgId2(id, year, null, null, null);
					} else {
						ids.add(id);
						infos = affairYqContolService.findInfoByCreateOrgIds2(ids, year, null, null, null);
					}
				} else {
					infos = affairYqContolService.findInfoByCreateOrgId2(null, year, null,null, null);
				}
			}
			return infos;
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if ("1".equals(flag)) {//来源
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							infos = affairYqContolService.findInfoByCreateOrgId(id, null, startDate, endDate, null);
						} else {
							ids.add(id);
							infos = affairYqContolService.findInfoByCreateOrgIds(ids, null, startDate, endDate, null);
						}
					} else {
						infos = affairYqContolService.findInfoByCreateOrgId(null, null, startDate, endDate, null);
					}
				} else {//处置情况
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							infos = affairYqContolService.findInfoByCreateOrgId2(id, null,  startDate, endDate, null);
						} else {
							ids.add(id);
							infos = affairYqContolService.findInfoByCreateOrgIds2(ids, null,  startDate, endDate, null);
						}
					} else {
						infos = affairYqContolService.findInfoByCreateOrgId2(null, null,  startDate, endDate, null);
					}
				}
			}
		}
		return infos;
}

	/**
	 * 文艺作品
	 * @return
	 */
	@RequestMapping("literaryWorks")
	@ResponseBody
	public List<Map<String,String>> countLiteraryWorks(String dateType, Integer year, String dateStart, String dateEnd, String month){
		List<Map<String,String>> list = affairWenyiService.countLiteraryWorks(dateType, year, dateStart, dateEnd, month);
		return list;
	}
	@RequestMapping("literaryTalents")
	@ResponseBody
	public List<Map<String,String>> countLiteraryTalents(String dateType, Integer year, String dateStart, String dateEnd, String month){
		List<Map<String,String>> list = affairWenhuaService.countLiteraryTalent(dateType, year, dateStart, dateEnd, month);
		return list;
	}

	@RequestMapping("talentJoin")
	@ResponseBody
	public List<Map<String,String>> countTalentJoin(String dateType, Integer year, String dateStart, String dateEnd, String month){
		List<Map<String,String>> list = affairWenhuaService.countTalentJoin(dateType, year, dateStart, dateEnd, month);
		return list;
	}

	/**
	 * 教育训练
	 *
	 * @return
	 */
	@RequestMapping("train")
	public ModelAndView train(String id) {
		ModelAndView view = new ModelAndView();
		if(StringUtils.isBlank(id)){
			id = UserUtils.getUser().getOffice().getId();
		}
		view.addObject("id", id);
		view.addObject("dateType", "1");
		view.addObject("year", DateUtils.getYear());
		view.addObject("month", DateUtils.getYear()+"-"+DateUtils.getMonth());
		view.setViewName("modules/charts/training");
		return view;
	}

	@RequestMapping("countInstructor")
	@ResponseBody
	public List<Map<String,String>> countInstructor( String dateType, Integer year, String dateStart, String dateEnd, String month){
		List<Map<String, String>> list  = affairInteriorInstructorLibraryDao.countInstructor();
		return list;
	}

	@RequestMapping("baseKonoledge")
	public @ResponseBody List<Map<String,String>> baseKonoledge(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		AffairBasicKnowledge affairBasicKnowledge = new AffairBasicKnowledge();
		if (dateType.equals("1")){		//月度查询
			affairBasicKnowledge.setMonth(month);
		}else if (dateType.equals("3")){	//时间段查询
			affairBasicKnowledge.setBeginDate(DateUtils.parseDate(dateStart));
			affairBasicKnowledge.setEndDate(DateUtils.parseDate(dateEnd));
		}else {		//年度查询
			affairBasicKnowledge.setYear(year);
		}
		List<AffairBasicKnowledge> list = affairBasicKnowledgeService.basicInfo(affairBasicKnowledge);

		List<Dict> dicts =DictUtils.getDictList("pass_status");
		int[] nums = new int[dicts.size()];
		list.forEach(item -> {
			for(int i = 0;i<dicts.size();i++) {
				if (StringUtils.isNotBlank(item.getStatus()) && item.getStatus().contains(dicts.get(i).getValue())){
					nums[i]++;
				}
			}
		});
		List<Map<String,String>> l= new ArrayList<>();
		for(int i =0;i<dicts.size();i++) {
			Map<String,String> map=new HashMap<>();
			map.put("num", String.valueOf(nums[i]));
			map.put("label",DictUtils.getDictLabel(String.valueOf(i+1),"pass_status",""));
			l.add(map);
		}
		return l;
	}



	/**
	 * 纪检监察情况
	 * 
	 * @return
	 */
	@RequestMapping("monitor")
	public ModelAndView monitor(String id) {
		ModelAndView view = new ModelAndView();
		view.addObject("id", id);
		view.addObject("dateType", "2");
		view.addObject("year", DateUtils.getYear());
		view.addObject("month", DateUtils.getYear()+"-"+DateUtils.getMonth());
		view.setViewName("modules/charts/monitor");
		return view;
	}
	/**
	 * 投诉举报
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("firstTouSuColumn")
	public @ResponseBody Map<String, Object> firstTouSuColumn(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairTousujubaoguanliService.findInfoBySource(id, null, null, null, month);
				} else {
					infos = affairTousujubaoguanliService.findInfoBySource(null, null, null, null, month);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (year != null ) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairTousujubaoguanliService.findInfoBySource(id, year, null, null, null);
				} else {
					infos = affairTousujubaoguanliService.findInfoBySource(null, year, null, null, null);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				/*if (month != null && month.length() > 0) {*/  //11.9  kevin.jia 问题跟踪记录1031 1、按照时间段，查询无数据
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						infos = affairTousujubaoguanliService.findInfoBySource(id, null, startDate, endDate, null);
					} else {
						infos = affairTousujubaoguanliService.findInfoBySource(null, null, startDate, endDate, null);
					}
				/*}*/
			}
		}
		return infos;
	}

	/**
	 * 投诉举报
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("echartsIsCheckTypeFindPageInfo")
	public @ResponseBody Map<String, Object> echartsIsCheckTypeFindPageInfo(String sdUnit, String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairTousujubaoguanliService.findPieInfoByIsCheck(sdUnit,id, null, null, null, month);
				} else {
					infos = affairTousujubaoguanliService.findPieInfoByIsCheck(sdUnit,null, null, null, null, month);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (year != null ) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairTousujubaoguanliService.findPieInfoByIsCheck(sdUnit,id, year, null, null, null);
				} else {
					infos = affairTousujubaoguanliService.findPieInfoByIsCheck(sdUnit,null, year, null, null, null);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				/*if (month != null && month.length() > 0) {*/ //11.9  kevin.jia 问题跟踪记录1031 1、按照时间段，查询无数据
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						infos = affairTousujubaoguanliService.findPieInfoByIsCheck(sdUnit,id, null, startDate, endDate, null);
					} else {
						infos = affairTousujubaoguanliService.findPieInfoByIsCheck(sdUnit,null, null, startDate, endDate, null);
					}
				/*}*/
			}
		}
		return infos;
	}

	@RequestMapping(value = {"echartsIsCheckTypeFindPage"})
	public String echartsIsCheckTypeFindPage(Model model,String sdUnit, String id, Integer year, String dateStart, String dateEnd, String month,String dateType){
		model.addAttribute("sdUnit",sdUnit);
		model.addAttribute("id",id);
		model.addAttribute("year",year);
		model.addAttribute("startDate",dateStart);
		model.addAttribute("endDate",dateEnd);
		model.addAttribute("month",month);
		model.addAttribute("dateType",dateType);
		return "modules/charts/monitorTouSu";
	}

	/**
	 * 投诉举报
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("secondTouSuColumn")
	public @ResponseBody Map<String, Object> secondTouSuColumn(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairTousujubaoguanliService.findInfoByQuestionType(id, null, null, null, month);
				} else {
					infos = affairTousujubaoguanliService.findInfoByQuestionType(null, null, null, null, month);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairTousujubaoguanliService.findInfoByQuestionType(id, year, null, null, null);
				} else {
					infos = affairTousujubaoguanliService.findInfoByQuestionType(null, year, null, null, null);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				/*if (month != null && month.length() > 0) {*/  //11.9  kevin.jia 问题跟踪记录1031 1、按照时间段，查询无数据
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						infos = affairTousujubaoguanliService.findInfoByQuestionType(id, null, startDate, endDate, null);
					} else {
						infos = affairTousujubaoguanliService.findInfoByQuestionType(null, null, startDate, endDate, null);
					}
				/*}*/
			}
		}
		return infos;
	}

	@RequestMapping(value = {"echartsQuestionTypeFindPage"})
	public String echartsQuestionTypeFindPage(Model model,String sdUnit, String id, Integer year, String dateStart, String dateEnd, String month,String dateType){
		model.addAttribute("sdUnit",sdUnit);
		model.addAttribute("id",id);
		model.addAttribute("year",year);
		model.addAttribute("startDate",dateStart);
		model.addAttribute("endDate",dateEnd);
		model.addAttribute("month",month);
		model.addAttribute("dateType",dateType);
		return "modules/charts/monitorTouSuPie";
	}

	/**
	 * 投诉举报
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("echartsQuestionTypeFindPageInfo")
	public @ResponseBody Map<String, Object> echartsQuestionTypeFindPageInfo(String sdUnit, String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairTousujubaoguanliService.findPieInfoByQuestionType(sdUnit,id, null, null, null, month);
				} else {
					infos = affairTousujubaoguanliService.findPieInfoByQuestionType(sdUnit,null, null, null, null, month);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (year != null) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairTousujubaoguanliService.findPieInfoByQuestionType(sdUnit,id, year, null, null, null);
				} else {
					infos = affairTousujubaoguanliService.findPieInfoByQuestionType(sdUnit,null, year, null, null, null);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				/*if (month != null && month.length() > 0) {*/  //11.9  kevin.jia 问题跟踪记录1031 1、按照时间段，查询无数据
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						infos = affairTousujubaoguanliService.findPieInfoByQuestionType(sdUnit,id, null, startDate, endDate, null);
					} else {
						infos = affairTousujubaoguanliService.findPieInfoByQuestionType(sdUnit,null, null, startDate, endDate, null);
					}
				/*}*/
			}
		}
		return infos;
	}

	/**
	 * 纪律处分
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("firstJiLvColumn")
	public @ResponseBody Map<String, Object> firstJiLvColumn(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairDisciplinaryActionService.findPeopleCount(id, null, null, null, month);
				} else {
					infos = affairDisciplinaryActionService.findPeopleCount(null, null, null, null, month);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (year != null) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairDisciplinaryActionService.findPeopleCount(id, year, null, null, null);
				} else {
					infos = affairDisciplinaryActionService.findPeopleCount(null, year, null, null, null);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				/*if (month != null && month.length() > 0) {*/ //11.9  kevin.jia 问题跟踪记录1031 1、按照时间段，查询无数据
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						infos = affairDisciplinaryActionService.findPeopleCount(id, null, startDate, endDate, null);
					} else {
						infos = affairDisciplinaryActionService.findPeopleCount(null, null, startDate, endDate, null);
					}
				/*}*/
			}
		}
		return infos;
	}

	/**
	 * 纪律处分
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("secondJiLvPie")
	public @ResponseBody List<Map<String, Object>> secondJiLvPie(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairDisciplinaryActionService.findPieInfoByNature(id, null, null, null, month);
				} else {
					infos = affairDisciplinaryActionService.findPieInfoByNature(null, null, null, null, month);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (year != null) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairDisciplinaryActionService.findPieInfoByNature(id, year, null, null, null);
				} else {
					infos = affairDisciplinaryActionService.findPieInfoByNature(null, year, null, null, null);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				/*if (month != null && month.length() > 0) {*/ //11.9  kevin.jia 问题跟踪记录1031 1、按照时间段，查询无数据
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						infos = affairDisciplinaryActionService.findPieInfoByNature(id, null, startDate, endDate, null);
					} else {
						infos = affairDisciplinaryActionService.findPieInfoByNature(null, null, startDate, endDate, null);
					}
				/*}*/
			}
		}
		return infos;
	}

	/**
	 * 纪律处分
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("thirdJiLvColumn")
	public @ResponseBody Map<String, Object> thirdJiLvColumn(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairDisciplinaryActionService.findCountByCfUnit(id, null, null, null, month);
				} else {
					infos = affairDisciplinaryActionService.findCountByCfUnit(null, null, null, null, month);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (year != null ) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairDisciplinaryActionService.findCountByCfUnit(id, year, null, null, null);
				} else {
					infos = affairDisciplinaryActionService.findCountByCfUnit(null, year, null, null, null);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				/*if (month != null && month.length() > 0) {*/ //11.9  kevin.jia 问题跟踪记录1031 1、按照时间段，查询无数据
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						infos = affairDisciplinaryActionService.findCountByCfUnit(id, null, startDate, endDate, null);
					} else {
						infos = affairDisciplinaryActionService.findCountByCfUnit(null, null, startDate, endDate, null);
					}
				/*}*/
			}
		}
		return infos;
	}

	/**
	 * 纪律处分
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("forthJiLvPie")
	public @ResponseBody List<Map<String, Object>> forthJiLvPie(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairDisciplinaryActionService.findPeopleCountByChuFen(id, null, null, null, month);
				} else {
					infos = affairDisciplinaryActionService.findPeopleCountByChuFen(null, null, null, null, month);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (year != null ) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairDisciplinaryActionService.findPeopleCountByChuFen(id, year, null, null, null);
				} else {
					infos = affairDisciplinaryActionService.findPeopleCountByChuFen(null, year, null, null, null);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				/*if (month != null && month.length() > 0) {*/ //11.9  kevin.jia 问题跟踪记录1031 1、按照时间段，查询无数据
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						infos = affairDisciplinaryActionService.findPeopleCountByChuFen(id, null, startDate, endDate, null);
					} else {
						infos = affairDisciplinaryActionService.findPeopleCountByChuFen(null, null, startDate, endDate, null);
					}
				/*}*/
			}
		}
		return infos;
	}

	/**
	 * 纪律处分
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("fifthJiLvPie")
	public @ResponseBody List<Map<String, Object>> fifthJiLvPie(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairDisciplinaryActionService.findPieInfoByDjChuFen(id, null, null, null, month);
				} else {
					infos = affairDisciplinaryActionService.findPieInfoByDjChuFen(null, null, null, null, month);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (year != null ) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairDisciplinaryActionService.findPieInfoByDjChuFen(id, year, null, null, null);
				} else {
					infos = affairDisciplinaryActionService.findPieInfoByDjChuFen(null, year, null, null, null);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				/*if (month != null && month.length() > 0) {*/ //11.9  kevin.jia 问题跟踪记录1031 1、按照时间段，查询无数据
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						infos = affairDisciplinaryActionService.findPieInfoByDjChuFen(id, null, startDate, endDate, null);
					} else {
						infos = affairDisciplinaryActionService.findPieInfoByDjChuFen(null, null, startDate, endDate, null);
					}
				/*}*/
			}
		}
		return infos;
	}

	/**
	 * 纪律处分
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("sixthJiLvPie")
	public @ResponseBody List<Map<String, Object>> sisthJiLvPie(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairDisciplinaryActionService.findPieInfoByXzChuFen(id, null, null, null, month);
				} else {
					infos = affairDisciplinaryActionService.findPieInfoByXzChuFen(null, null, null, null, month);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (year != null ) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairDisciplinaryActionService.findPieInfoByXzChuFen(id, year, null, null, null);
				} else {
					infos = affairDisciplinaryActionService.findPieInfoByXzChuFen(null, year, null, null, null);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				/*if (month != null && month.length() > 0) {*/  //11.9  kevin.jia 问题跟踪记录1031 1、按照时间段，查询无数据
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						infos = affairDisciplinaryActionService.findPieInfoByXzChuFen(id, null, startDate, endDate, null);
					} else {
						infos = affairDisciplinaryActionService.findPieInfoByXzChuFen(null, null, startDate, endDate, null);
					}
				/*}*/
			}
		}
		return infos;
	}

	/**
	 * 谈话函询
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("firstThCount")
	public @ResponseBody Map<String, Object> firstThCount(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairTalkManagementService.findCountInfoByZbUnit(id, null, null, null, month);
				} else {
					infos = affairTalkManagementService.findCountInfoByZbUnit(null, null, null, null, month);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (year != null) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairTalkManagementService.findCountInfoByZbUnit(id, year, null, null, null);
				} else {
					infos = affairTalkManagementService.findCountInfoByZbUnit(null, year, null, null, null);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				/*if (month != null && month.length() > 0) {*/  // //11.9  kevin.jia 问题跟踪记录1031 1、按照时间段，查询无数据
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
						infos = affairTalkManagementService.findCountInfoByZbUnit(id, null, startDate, endDate, null);
					} else {
						infos = affairTalkManagementService.findCountInfoByZbUnit(null, null, startDate, endDate, null);
					}
				/*}*/
			}
		}
		return infos;
	}

	/**
	 * 谈话函询
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("secondThPie")
	public @ResponseBody List<Map<String, Object>> secondThPie(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairTalkManagementService.findPieInfoByLx(id, null, null, null, month);
				} else {
					infos = affairTalkManagementService.findPieInfoByLx(null, null, null, null, month);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (year != null ) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairTalkManagementService.findPieInfoByLx(id, year, null, null, null);
				} else {
					infos = affairTalkManagementService.findPieInfoByLx(null, year, null, null, null);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				/*if (month != null && month.length() > 0) {*/ //11.9  kevin.jia 问题跟踪记录1031 1、按照时间段，查询无数据
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						infos = affairTalkManagementService.findPieInfoByLx(id, null, startDate, endDate, null);
					} else {
						infos = affairTalkManagementService.findPieInfoByLx(null, null, startDate, endDate, null);
					}
				/*}*/
			}
		}
		return infos;
	}

	/**
	 * 廉政鉴定
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("firstLzCount")
	public @ResponseBody Map<String, Object> firstLzCount(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairRqlzIntegrityService.findInfoByZbType(id, null, null, null, month);
				} else {
					infos = affairRqlzIntegrityService.findInfoByZbType(null, null, null, null, month);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (year != null) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairRqlzIntegrityService.findInfoByZbType(id, year, null, null, null);
				} else {
					infos = affairRqlzIntegrityService.findInfoByZbType(null, year, null, null, null);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				/*if (month != null && month.length() > 0) {*/ //11.9  kevin.jia 问题跟踪记录1031 1、按照时间段，查询无数据
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
						infos = affairRqlzIntegrityService.findInfoByZbType(id, null, startDate, endDate, null);
					} else {
						infos = affairRqlzIntegrityService.findInfoByZbType(null, null, startDate, endDate, null);
					}
				/*}*/
			}
		}
		return infos;
	}

	/**
	 * 廉政鉴定
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("secondLzPie")
	public @ResponseBody List<Map<String, Object>> secondLzPie(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairRqlzIntegrityService.findPieInfoByJdType(id, null, null, null, month);
				} else {
					infos = affairRqlzIntegrityService.findPieInfoByJdType(null, null, null, null, month);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (year != null ) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairRqlzIntegrityService.findPieInfoByJdType(id, year, null, null, null);
				} else {
					infos = affairRqlzIntegrityService.findPieInfoByJdType(null, year, null, null, null);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				/*if (month != null && month.length() > 0) {*/ //11.9 kevin.jia 问题跟踪记录1031  按照时间段，查询无数据
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						infos = affairRqlzIntegrityService.findPieInfoByJdType(id, null, startDate, endDate, null);
					} else {
						infos = affairRqlzIntegrityService.findPieInfoByJdType(null, null, startDate, endDate, null);
					}
				/*}*/
			}
		}
		return infos;
	}

	/**
	 * 廉政监督
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("firstJdCount")
	public @ResponseBody Map<String, Object> firstJdCount(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairLianzhengSuperviseService.findInfoByJdUnit(id, null, null, null, month);
				} else {
					infos = affairLianzhengSuperviseService.findInfoByJdUnit(null, null, null, null, month);
				}
			}
		} else if ("2".equals(dateType)) {//年度
			if (year != null ) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					infos = affairLianzhengSuperviseService.findInfoByJdUnit(id, year, null, null, null);
				} else {
					infos = affairLianzhengSuperviseService.findInfoByJdUnit(null, year, null, null, null);
				}
			}
		} else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate == null && endDate == null)) {
				/*if (month != null && month.length() > 0) {*/ //11.9  kevin.jia 问题跟踪记录1031 1、按照时间段，查询无数据
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						infos = affairLianzhengSuperviseService.findInfoByJdUnit(id, null, startDate, endDate, null);
					} else {
						infos = affairLianzhengSuperviseService.findInfoByJdUnit(null, null, startDate, endDate, null);
					}
				/*}*/
			}
		}
		return infos;
	}

	/**
	 * 纪律处分情况
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("jilv")
	public @ResponseBody List<Map<String, Object>> jilv(String id,String flag, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if ("2".equals(flag)) {
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							infos = affairDisciplinaryActionService.findInfoByUnitId(id, null, null, null, month);
						} else {
							ids.add(id);
							infos = affairDisciplinaryActionService.findInfoByUnitIds(ids, null, null, null, month);
						}
					} else {
						infos = affairDisciplinaryActionService.findInfoByUnitId(null, null, null, null, month);
					}
				} else {
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							infos = affairTalkManagementService.findInfoByUnitId(id, null, null, null, month);
						} else {
							ids.add(id);
							infos = affairTalkManagementService.findInfoByUnitIds(ids, null, null, null, month);
						}
					} else {
						infos = affairTalkManagementService.findInfoByUnitId(null, null, null, null, month);
					}
				}
			}
		}else if("2".equals(dateType)){//年度
			if ("2".equals(flag)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDisciplinaryActionService.findInfoByUnitId(id, year, null, null, null);
					} else {
						ids.add(id);
						infos = affairDisciplinaryActionService.findInfoByUnitIds(ids, year, null, null, null);
					}
				} else {
					infos = affairDisciplinaryActionService.findInfoByUnitId(null, year, null, null, null);
				}
				return infos;
			} else {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairTalkManagementService.findInfoByUnitId(id, year, null, null, null);
					} else {
						ids.add(id);
						infos = affairTalkManagementService.findInfoByUnitIds(ids, year, null, null, null);
					}
				} else {
					infos = affairTalkManagementService.findInfoByUnitId(null, year, null, null, null);
				}
			}
		}else{// 时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if ("2".equals(flag)) {
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							infos = affairDisciplinaryActionService.findInfoByUnitId(id, null, startDate, endDate, null);
						} else {
							ids.add(id);
							infos = affairDisciplinaryActionService.findInfoByUnitIds(ids, null, startDate, endDate, null);
						}
					} else {
						infos = affairDisciplinaryActionService.findInfoByUnitId(null, null, startDate, endDate, null);
					}
				} else {
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							infos = affairTalkManagementService.findInfoByUnitId(id, null, startDate, endDate, null);
						} else {
							ids.add(id);
							infos = affairTalkManagementService.findInfoByUnitIds(ids, null, startDate, endDate, null);
						}
					} else {
						infos = affairTalkManagementService.findInfoByUnitId(null, null, startDate, endDate, null);
					}
				}
			}
		}
		return infos;
	}
	/** 教育整顿情况
	 * @param id
	 * @return
	 */
	@RequestMapping("jiaoyu")
	public @ResponseBody Map<String, Object> jiaoyu(String id, String flag, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> lzxx = null;
		Map<String, Object> dwzf = null;
		Map<String, Object> jyzf = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if ("4".equals(flag)) {
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							lzxx = affairLzxxjyActivitiesService.findInfoByCreateOrgId(id, null, null, null, month);
							dwzf = affairTeamDisciplineService.findInfoByCreateOrgId(id, null, null, null, month);
							jyzf = affairEmpiricalPracticeService.findInfoByCreateOrgId(id, null, null, null, month);
						} else {
							ids.add(id);
							lzxx = affairLzxxjyActivitiesService.findInfoByCreateOrgIds(ids, null, null, null, month);
							dwzf = affairTeamDisciplineService.findInfoByCreateOrgIds(ids, null, null, null, month);
							jyzf = affairEmpiricalPracticeService.findInfoByCreateOrgIds(ids, null, null, null, month);
						}
					} else {
						lzxx = affairLzxxjyActivitiesService.findInfoByCreateOrgId(null, null, null, null, month);
						dwzf = affairTeamDisciplineService.findInfoByCreateOrgId(null, null, null, null, month);
						jyzf = affairEmpiricalPracticeService.findInfoByCreateOrgId(null, null, null, null, month);
					}
				}
			}
		}else if("2".equals(dateType)){//年度
			if ("4".equals(flag)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						lzxx = affairLzxxjyActivitiesService.findInfoByCreateOrgId(id, year, null, null, null);
						dwzf = affairTeamDisciplineService.findInfoByCreateOrgId(id, year, null, null, null);
						jyzf = affairEmpiricalPracticeService.findInfoByCreateOrgId(id, year, null, null, null);
					} else {
						ids.add(id);
						lzxx = affairLzxxjyActivitiesService.findInfoByCreateOrgIds(ids, year, null, null, null);
						dwzf = affairTeamDisciplineService.findInfoByCreateOrgIds(ids, year, null, null, null);
						jyzf = affairEmpiricalPracticeService.findInfoByCreateOrgIds(ids, year, null, null, null);
					}
				} else {
					lzxx = affairLzxxjyActivitiesService.findInfoByCreateOrgId(null, year, null, null, null);
					dwzf = affairTeamDisciplineService.findInfoByCreateOrgId(null, year, null, null, null);
					jyzf = affairEmpiricalPracticeService.findInfoByCreateOrgId(null, year, null, null, null);
				}
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if ("4".equals(flag)) {
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							lzxx = affairLzxxjyActivitiesService.findInfoByCreateOrgId(id, null, startDate, endDate, null);
							dwzf = affairTeamDisciplineService.findInfoByCreateOrgId(id, null, startDate, endDate, null);
							jyzf = affairEmpiricalPracticeService.findInfoByCreateOrgId(id, null, startDate, endDate, null);
						} else {
							ids.add(id);
							lzxx = affairLzxxjyActivitiesService.findInfoByCreateOrgIds(ids, null, startDate, endDate, null);
							dwzf = affairTeamDisciplineService.findInfoByCreateOrgIds(ids, null, startDate, endDate, null);
							jyzf = affairEmpiricalPracticeService.findInfoByCreateOrgIds(ids, null, startDate, endDate, null);
						}
					} else {
						lzxx = affairLzxxjyActivitiesService.findInfoByCreateOrgId(null, null, startDate, endDate, null);
						dwzf = affairTeamDisciplineService.findInfoByCreateOrgId(null, null, startDate, endDate, null);
						jyzf = affairEmpiricalPracticeService.findInfoByCreateOrgId(null, null, startDate, endDate, null);
					}
				}
			}
		}
		List<Double> countData = new ArrayList<>();
		countData.add(Double.valueOf(lzxx.get("count").toString()));
		countData.add(Double.valueOf(dwzf.get("count").toString()));
		countData.add(Double.valueOf(jyzf.get("count").toString()));
		Map<String, Object> result = new HashMap<>();
		result.put("countData", countData);
		return result;
	}

	/** 投诉举报-柱状图
	 * @param id
	 * @return
	 */
/*	@RequestMapping("tousu-column")
	public @ResponseBody Map<String, Object> tousuColumn(String id,String flag, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairTousujubaoguanliService.findInfoByCreateOrgId(id, null, null, null, month);
					} else {
						ids.add(id);
						infos = affairTousujubaoguanliService.findInfoByCreateOrgIds(ids, null, null, null, month);
					}
				} else {
					infos = affairTousujubaoguanliService.findInfoByCreateOrgId(null, null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairTousujubaoguanliService.findInfoByCreateOrgId(id, year, null, null, null);
				} else {
					ids.add(id);
					infos = affairTousujubaoguanliService.findInfoByCreateOrgIds(ids, year, null, null, null);
				}
			} else {
				infos = affairTousujubaoguanliService.findInfoByCreateOrgId(null, year, null, null, null);
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairTousujubaoguanliService.findInfoByCreateOrgId(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairTousujubaoguanliService.findInfoByCreateOrgIds(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairTousujubaoguanliService.findInfoByCreateOrgId(null, null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}*/

	/** 投诉举报-饼状图
	 * @param
	 * @return
	 */
/*	@RequestMapping("tousu-pie")
	public @ResponseBody List<Map<String, Object>> tousuPie(String id,String flag, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairTousujubaoguanliService.findPieInfoByCreateOrgId(id, null, null, null ,month);
					} else {
						ids.add(id);
						infos = affairTousujubaoguanliService.findPieInfoByCreateOrgIds(ids, null, null, null ,month);
					}
				} else {
					infos = affairTousujubaoguanliService.findPieInfoByCreateOrgId(null, null, null, null ,month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairTousujubaoguanliService.findPieInfoByCreateOrgId(id, year, null, null, null);
				} else {
					ids.add(id);
					infos = affairTousujubaoguanliService.findPieInfoByCreateOrgIds(ids, year, null, null, null);
				}
			} else {
				infos = affairTousujubaoguanliService.findPieInfoByCreateOrgId(null, year, null, null, null);
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairTousujubaoguanliService.findPieInfoByCreateOrgId(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairTousujubaoguanliService.findPieInfoByCreateOrgIds(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairTousujubaoguanliService.findPieInfoByCreateOrgId(null, null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}*/

	@RequestMapping("government")
	public ModelAndView government(){
		ModelAndView view = new ModelAndView();
//		view.addObject("id", id);
		view.addObject("dateType", "1");
		view.addObject("year", DateUtils.getYear());
		view.addObject("month", DateUtils.getYear()+"-"+DateUtils.getMonth());
		view.setViewName("modules/charts/government");
		return view;
	}

	@RequestMapping("workCount")
	@ResponseBody
	public List<Map<String,Object>> workCount(String dateType, Integer year, String dateStart, String dateEnd, String month){
		Article article  = new Article();
		if (StringUtils.isEmpty(dateType)){
			dateType="-1";
		}
		switch (dateType){
			case "1":	//月度
				article.setMonth(month);
				break;
			case "3":	//时间段
				article.setBeginDate(DateUtils.parseDate(dateStart));
				article.setEndDate(DateUtils.parseDate(dateEnd));
				break;
			default:	//年度
				article.setYear(year);
				break;
		}
		List<Map<String,Object>> result= articleService.columnSatistics(article);

		return result;
	}

	@RequestMapping("secrecyCount")
	@ResponseBody
	public List<Map<String,Object>> secrecyCount(String dateType, Integer year, String dateStart, String dateEnd, String month){
		Article article  = new Article();
		if (StringUtils.isEmpty(dateType)){
			dateType="-1";
		}
		switch (dateType){
			case "1":	//月度
				article.setMonth(month);
				break;
			case "3":	//时间段
				article.setBeginDate(DateUtils.parseDate(dateStart));
				article.setEndDate(DateUtils.parseDate(dateEnd));
				break;
			default:	//年度
				article.setYear(year);
				break;
		}
		List<Map<String,Object>> result= articleService.countSecrecy(article);

		return result;
	}


	/**
	 * 警务督察
	 *
	 * @return
	 */
	@RequestMapping("inspector")
	public ModelAndView inspector(String id) {
		ModelAndView view = new ModelAndView();
		view.addObject("id", id);
		view.addObject("dateType", "1");
		view.addObject("year", DateUtils.getYear());
		view.addObject("month", DateUtils.getYear()+"-"+DateUtils.getMonth());
		view.setViewName("modules/charts/inspector");
		return view;
	}
	/** 督察问题库情况-柱状图
	 * @param id
	 * @return
	 */
    @RequestMapping("all-ducha-column")
    public @ResponseBody List<Map<String,String>>  allDuchaColumn(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
        AffairDcwtLibrary affairDcwtLibrary = new AffairDcwtLibrary();
        if (dateType.equals("1")){		//月度查询
            affairDcwtLibrary.setMonth(month);
        }else if (dateType.equals("3")){	//时间段查询
            affairDcwtLibrary.setStartDate(DateUtils.parseDate(dateStart));
            affairDcwtLibrary.setEndDate(DateUtils.parseDate(dateEnd));
        }else {		//年度查询
            affairDcwtLibrary.setYear(year);
        }
        List<AffairDcwtLibrary> list = affairDcwtLibraryService.findAllInfoList(affairDcwtLibrary);
        List<Dict> dicts =DictUtils.getDictList("affair_wtlb");
		int[] nums = new int[dicts.size()];
		int[] yzgNums = new int[dicts.size()];
		int[] wzgNums = new int[dicts.size()];
		int[] xqNums = new int[dicts.size()];
		int[] wuNums = new int[dicts.size()];
        list.forEach(item -> {
            for(int i = 0;i<dicts.size();i++) {
            	// 存在不足字段为空处理  kevin.jia 11.9 没有南宁、北海、柳州处督察支队的统计分析数据
				if(item!=null && item.getProblemCategory()!=null){
					String[] catagorys = item.getProblemCategory().split(",");
					Set catSet = new HashSet();
					for(String catagory :catagorys){
						catSet.add(catagory);
					}
					if (catSet.contains(dicts.get(i).getValue())){
						nums[i]++;
						if (item.getRectification().contains("1")){
							yzgNums[i]++;
						}
						if (item.getRectification().contains("2")){
							wzgNums[i]++;
						}
						if (item.getRectification().contains("3")){
							xqNums[i]++;
						}
						if (item.getRectification().contains("4")){
							wuNums[i]++;
						}
					}
				}
            }
        });
        List<Map<String,String>> l= new ArrayList<>();
        for(int i =0;i<dicts.size();i++) {
            Map<String,String> map=new HashMap<>();
            map.put("total", String.valueOf(nums[i]));
            map.put("yzgTotal",String.valueOf(yzgNums[i]));
            map.put("wzgTotal",String.valueOf(wzgNums[i]));
            map.put("xqTotal",String.valueOf(xqNums[i]));
            map.put("wuTotal",String.valueOf(wuNums[i]));
            map.put("label",DictUtils.getDictLabel(String.valueOf(i+1),"affair_wtlb",""));
            l.add(map);
        }
        return l;
    }

/*	public @ResponseBody Map<String, Object> allDuchaColumn(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findAllDcInfo(id, null, null, null, month);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findAllDcInfos(ids, null, null, null, month);
					}
				} else {
					infos = affairDcwtLibraryService.findAllDcInfo(null, null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairDcwtLibraryService.findAllDcInfo(id, year, null, null,  null);
				} else {
					ids.add(id);
					infos = affairDcwtLibraryService.findAllDcInfos(ids, year, null, null, null);
				}
			} else {
				infos = affairDcwtLibraryService.findAllDcInfo(null, year, null, null, null);
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findAllDcInfo(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findAllDcInfos(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairDcwtLibraryService.findAllDcInfo(null, null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}*/

    @RequestMapping(value = {"inspectorAllPiePage"})
    public String inspectorAllPiePage(Model model,String flag, String id, Integer year, Date startDate, Date endDate, String month,String dateType){
        model.addAttribute("flag",flag);
        model.addAttribute("id",id);
        model.addAttribute("year",year);
        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);
        model.addAttribute("month",month);
        model.addAttribute("dateType",dateType);
        return "modules/charts/inspectorAllPieInfo";
    }

    /**
     * 全局督察问题库
     *
     * @param id
     * @return
     */
    @RequestMapping("inspectorAllPiePageInfo")
    public @ResponseBody List<Map<String, String>> inspectorAllPiePageInfo(String flag, String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		AffairDcwtLibrary affairDcwtLibrary = new AffairDcwtLibrary();
		if (StringUtils.isNotBlank(flag)){
			affairDcwtLibrary.setFlag(flag);
		}
		if (dateType.equals("1")){		//月度查询
			affairDcwtLibrary.setMonth(month);
		}else if (dateType.equals("3")){	//时间段查询
			affairDcwtLibrary.setStartDate(DateUtils.parseDate(dateStart));
			affairDcwtLibrary.setEndDate(DateUtils.parseDate(dateEnd));
		}else {		//年度查询
			affairDcwtLibrary.setYear(year);
		}
		List<AffairDcwtLibrary> tmpList = affairDcwtLibraryService.findAllDetailPieInfoList(affairDcwtLibrary);
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		if(null != list){
			for(AffairDcwtLibrary library: tmpList){
				String[] catagorys = library.getProblemCategory().split(",");
				Set catSet = new HashSet();
				for(String catagory :catagorys){
					catSet.add(catagory);
				}
				if (catSet.contains(flag)){
					list.add(library);
				}
			}
		}
		List<Dict> dicts =DictUtils.getDictList("affair_zhenggai");
		int[] nums = new int[dicts.size()];
		list.forEach(item->{
			for(int i = 0;i<dicts.size();i++) {
				String[] catagorys = item.getRectification().split(",");
				Set catSet = new HashSet();
				for(String catagory :catagorys){
					catSet.add(catagory);
				}
				if (catSet.contains(dicts.get(i).getValue())){
					nums[i]++;
				}
			}
		});
		List<Map<String,String>> l= new ArrayList<>();
		for(int i =0;i<dicts.size();i++) {
			Map<String, String> map = new HashMap<>();
			map.put("total", String.valueOf(nums[i]));
			map.put("label", DictUtils.getDictLabel(String.valueOf(i + 1), "affair_zhenggai", ""));
			l.add(map);
		}
		return l;
    }
	
	/** 督察问题库情况-饼状图
	 * @param id
	 * @return
	 */
	@RequestMapping("all-ducha-pie")
	public @ResponseBody List<Map<String, String>> allDuchaPie(String id, String dateType, Integer year,String dateStart, String dateEnd, String month) {
		AffairDcwtLibrary affairDcwtLibrary = new AffairDcwtLibrary();
		if (dateType.equals("1")){		//月度查询
			affairDcwtLibrary.setMonth(month);
		}else if (dateType.equals("3")){	//时间段查询
			affairDcwtLibrary.setStartDate(DateUtils.parseDate(dateStart));
			affairDcwtLibrary.setEndDate(DateUtils.parseDate(dateEnd));
		}else {		//年度查询
			affairDcwtLibrary.setYear(year);
		}
		List<AffairDcwtLibrary> list = affairDcwtLibraryService.findAllPieInfoList(affairDcwtLibrary);
		List<Dict> dicts =DictUtils.getDictList("affair_wtlb");
		int[] nums = new int[dicts.size()];
		list.forEach(item->{
			for(int i = 0;i<dicts.size();i++) {
				// 存在不足字段为空处理  kevin.jia 11.9 没有南宁、北海、柳州处督察支队的统计分析数据
				if(item!=null && item.getProblemCategory()!=null){
					String[] catagorys = item.getProblemCategory().split(",");
					Set catSet = new HashSet();
					for(String catagory :catagorys){
						catSet.add(catagory);
					}
					if (catSet.contains(dicts.get(i).getValue())){
						nums[i]++;
					}
				}
			}
		});
		List<Map<String,String>> l= new ArrayList<>();
		for(int i =0;i<dicts.size();i++) {
			Map<String, String> map = new HashMap<>();
			map.put("total", String.valueOf(nums[i]));
			map.put("label", DictUtils.getDictLabel(String.valueOf(i + 1), "affair_wtlb", ""));
			l.add(map);
		}
		return l;
	}
	/*public @ResponseBody List<Map<String, Object>> allDuchaPie(String id, String dateType, Integer year,String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findAllPieInfoByCreateOrgId(id, null, null, null, month);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findAllPieInfoByCreateOrgIds(ids, null, null, null, month);
					}
				} else {
					infos = affairDcwtLibraryService.findAllPieInfoByCreateOrgId(null, null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairDcwtLibraryService.findAllPieInfoByCreateOrgId(id, year, null, null, null);
				} else {
					ids.add(id);
					infos = affairDcwtLibraryService.findAllPieInfoByCreateOrgIds(ids, year, null, null, null);
				}
			} else {
				infos = affairDcwtLibraryService.findAllPieInfoByCreateOrgId(null, year, null, null, null);
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findAllPieInfoByCreateOrgId(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findAllPieInfoByCreateOrgIds(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairDcwtLibraryService.findAllPieInfoByCreateOrgId(null, null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}*/


	/** 局督察问题库情况-柱状图
	 * @param id
	 * @return
	 */
	@RequestMapping("ju-ducha-column")
	public @ResponseBody List<Map<String, String>> juDuchaColumn(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		AffairDcwtLibrary affairDcwtLibrary = new AffairDcwtLibrary();
		if (dateType.equals("1")){		//月度查询
			affairDcwtLibrary.setMonth(month);
		}else if (dateType.equals("3")){	//时间段查询
			affairDcwtLibrary.setStartDate(DateUtils.parseDate(dateStart));
			affairDcwtLibrary.setEndDate(DateUtils.parseDate(dateEnd));
		}else {		//年度查询
			affairDcwtLibrary.setYear(year);
		}
		List<AffairDcwtLibrary> list = affairDcwtLibraryService.findJuInfoList(affairDcwtLibrary);
		List<Dict> dicts =DictUtils.getDictList("affair_wtlb");
		int[] nums = new int[dicts.size()];
		int[] yzgNums = new int[dicts.size()];
		int[] wzgNums = new int[dicts.size()];
		int[] xqNums = new int[dicts.size()];
		int[] wuNums = new int[dicts.size()];
		list.forEach(item -> {
			for(int i = 0;i<dicts.size();i++) {
				if(item!=null && item.getProblemCategory()!=null){
				//// 存在不足字段为空处理  kevin.jia 11.9 没有南宁、北海、柳州处督察支队的统计分析数据
					String[] catagorys = item.getProblemCategory().split(",");
					Set catSet = new HashSet();
					for(String catagory :catagorys){
						catSet.add(catagory);
					}
					if (catSet.contains(dicts.get(i).getValue())){
						nums[i]++;
						if (item.getRectification().contains("1")){
							yzgNums[i]++;
						}
						if (item.getRectification().contains("2")){
							wzgNums[i]++;
						}
						if (item.getRectification().contains("3")){
							xqNums[i]++;
						}
						if (item.getRectification().contains("4")){
							wuNums[i]++;
						}
					}
				}
			}
		});
		List<Map<String,String>> l= new ArrayList<>();
		for(int i =0;i<dicts.size();i++) {
			Map<String,String> map=new HashMap<>();
			map.put("total", String.valueOf(nums[i]));
			map.put("yzgTotal",String.valueOf(yzgNums[i]));
			map.put("wzgTotal",String.valueOf(wzgNums[i]));
			map.put("xqTotal",String.valueOf(xqNums[i]));
			map.put("wuTotal",String.valueOf(wuNums[i]));
			map.put("label",DictUtils.getDictLabel(String.valueOf(i+1),"affair_wtlb",""));
			l.add(map);
		}
		return l;
	}
	/*public @ResponseBody Map<String, Object> juDuchaColumn(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findJuDcInfo(id, null, null, null, month);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findJuDcInfos(ids, null, null, null, month);
					}
				} else {
					infos = affairDcwtLibraryService.findJuDcInfo(null, null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairDcwtLibraryService.findJuDcInfo(id, year, null, null,  null);
				} else {
					ids.add(id);
					infos = affairDcwtLibraryService.findJuDcInfos(ids, year, null, null, null);
				}
			} else {
				infos = affairDcwtLibraryService.findJuDcInfo(null, year, null, null, null);
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findJuDcInfo(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findJuDcInfos(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairDcwtLibraryService.findJuDcInfo(null, null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}*/

	@RequestMapping(value = {"inspectorJuPiePage"})
	public String inspectorJuPiePage(Model model,String flag, String id, Integer year, Date startDate, Date endDate, String month,String dateType){
		model.addAttribute("flag",flag);
		model.addAttribute("id",id);
		model.addAttribute("year",year);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("month",month);
		model.addAttribute("dateType",dateType);
		return "modules/charts/inspectorJuPieInfo";
	}

	@RequestMapping("inspectorJuPiePageInfo")
	public @ResponseBody List<Map<String, String>> inspectorJuPiePageInfo(String flag, String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		AffairDcwtLibrary affairDcwtLibrary = new AffairDcwtLibrary();
		if (StringUtils.isNotBlank(flag)){
			affairDcwtLibrary.setFlag(flag);
		}
		if (dateType.equals("1")){		//月度查询
			affairDcwtLibrary.setMonth(month);
		}else if (dateType.equals("3")){	//时间段查询
			affairDcwtLibrary.setStartDate(DateUtils.parseDate(dateStart));
			affairDcwtLibrary.setEndDate(DateUtils.parseDate(dateEnd));
		}else {		//年度查询
			affairDcwtLibrary.setYear(year);
		}
		List<AffairDcwtLibrary> tmpList = affairDcwtLibraryService.findJuDetailPieInfoList(affairDcwtLibrary);
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		if(null != list){
			for(AffairDcwtLibrary library: tmpList){
				String[] catagorys = library.getProblemCategory().split(",");
				Set catSet = new HashSet();
				for(String catagory :catagorys){
					catSet.add(catagory);
				}
				if (catSet.contains(flag)){
					list.add(library);
				}
			}
		}
		List<Dict> dicts =DictUtils.getDictList("affair_zhenggai");
		int[] nums = new int[dicts.size()];
		list.forEach(item->{
			for(int i = 0;i<dicts.size();i++) {
				String[] catagorys = item.getRectification().split(",");
				Set catSet = new HashSet();
				for(String catagory :catagorys){
					catSet.add(catagory);
				}
				if (catSet.contains(dicts.get(i).getValue())){
					nums[i]++;
				}
			}
		});
		List<Map<String,String>> l= new ArrayList<>();
		for(int i =0;i<dicts.size();i++) {
			Map<String, String> map = new HashMap<>();
			map.put("total", String.valueOf(nums[i]));
			map.put("label", DictUtils.getDictLabel(String.valueOf(i + 1), "affair_zhenggai", ""));
			l.add(map);
		}
		return l;
	}

	/** 局督察问题库情况-饼状图
	 * @param id
	 * @return
	 */
	@RequestMapping("ju-ducha-pie")
	public @ResponseBody List<Map<String, String>> juDuchaPie(String id, String dateType, Integer year,String dateStart, String dateEnd, String month) {
		AffairDcwtLibrary affairDcwtLibrary = new AffairDcwtLibrary();
		if (dateType.equals("1")){		//月度查询
			affairDcwtLibrary.setMonth(month);
		}else if (dateType.equals("3")){	//时间段查询
			affairDcwtLibrary.setStartDate(DateUtils.parseDate(dateStart));
			affairDcwtLibrary.setEndDate(DateUtils.parseDate(dateEnd));
		}else {		//年度查询
			affairDcwtLibrary.setYear(year);
		}
		List<AffairDcwtLibrary> list = affairDcwtLibraryService.findJuPieInfoList(affairDcwtLibrary);
		List<Dict> dicts =DictUtils.getDictList("affair_wtlb");
		int[] nums = new int[dicts.size()];
		list.forEach(item->{
			for(int i = 0;i<dicts.size();i++) {
				if(item!=null && item.getProblemCategory()!=null){
				// 存在不足字段为空处理  kevin.jia 11.9 没有南宁、北海、柳州处督察支队的统计分析数据
					String[] catagorys = item.getProblemCategory().split(",");
					Set catSet = new HashSet();
					for(String catagory :catagorys){
						catSet.add(catagory);
					}
					if (catSet.contains(dicts.get(i).getValue())){
						nums[i]++;
					}
				}
			}
		});
		List<Map<String,String>> l= new ArrayList<>();
		for(int i =0;i<dicts.size();i++) {
			Map<String, String> map = new HashMap<>();
			map.put("total", String.valueOf(nums[i]));
			map.put("label", DictUtils.getDictLabel(String.valueOf(i + 1), "affair_wtlb", ""));
			l.add(map);
		}
		return l;
	}
/*	public @ResponseBody List<Map<String, Object>> juDuchaPie(String id, String dateType, Integer year,String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findJuPieInfoByCreateOrgId(id, null, null, null, month);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findJuPieInfoByCreateOrgIds(ids, null, null, null, month);
					}
				} else {
					infos = affairDcwtLibraryService.findJuPieInfoByCreateOrgId(null, null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairDcwtLibraryService.findJuPieInfoByCreateOrgId(id, year, null, null, null);
				} else {
					ids.add(id);
					infos = affairDcwtLibraryService.findJuPieInfoByCreateOrgIds(ids, year, null, null, null);
				}
			} else {
				infos = affairDcwtLibraryService.findJuPieInfoByCreateOrgId(null, year, null, null, null);
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findJuPieInfoByCreateOrgId(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findJuPieInfoByCreateOrgIds(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairDcwtLibraryService.findJuPieInfoByCreateOrgId(null, null, startDate, endDate, null);
				}
			}
		}
		return infos;·
	}*/

	/** 南宁处督察问题库情况-柱状图
	 * @param id
	 * @return
	 */
	@RequestMapping("nnc-ducha-column")
	public @ResponseBody List<Map<String, String>> nncDuchaColumn(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		AffairDcwtLibrary affairDcwtLibrary = new AffairDcwtLibrary();
		if (dateType.equals("1")){		//月度查询
			affairDcwtLibrary.setMonth(month);
		}else if (dateType.equals("3")){	//时间段查询
			affairDcwtLibrary.setStartDate(DateUtils.parseDate(dateStart));
			affairDcwtLibrary.setEndDate(DateUtils.parseDate(dateEnd));
		}else {		//年度查询
			affairDcwtLibrary.setYear(year);
		}
		List<AffairDcwtLibrary> list = affairDcwtLibraryService.findNncInfoList(affairDcwtLibrary);
		List<Dict> dicts =DictUtils.getDictList("affair_wtlb");
		int[] nums = new int[dicts.size()];
		int[] yzgNums = new int[dicts.size()];
		int[] wzgNums = new int[dicts.size()];
		int[] xqNums = new int[dicts.size()];
		int[] wuNums = new int[dicts.size()];
		list.forEach(item -> {
			for(int i = 0;i<dicts.size();i++) {
				if(item!=null&&item.getProblemCategory()!=null){
					// 存在不足字段为空处理  kevin.jia 11.9 没有南宁、北海、柳州处督察支队的统计分析数据
					String[] catagorys = item.getProblemCategory().split(",");
					Set catSet = new HashSet();
					for(String catagory :catagorys){
						catSet.add(catagory);
					}
					if (catSet.contains(dicts.get(i).getValue())){
						nums[i]++;
						if (item.getRectification().contains("1")){
							yzgNums[i]++;
						}
						if (item.getRectification().contains("2")){
							wzgNums[i]++;
						}
						if (item.getRectification().contains("3")){
							xqNums[i]++;
						}
						if (item.getRectification().contains("4")){
							wuNums[i]++;
						}
					}
				}

			}
		});
		List<Map<String,String>> l= new ArrayList<>();
		for(int i =0;i<dicts.size();i++) {
			Map<String,String> map=new HashMap<>();
			map.put("total", String.valueOf(nums[i]));
			map.put("yzgTotal",String.valueOf(yzgNums[i]));
			map.put("wzgTotal",String.valueOf(wzgNums[i]));
			map.put("xqTotal",String.valueOf(xqNums[i]));
			map.put("wuTotal",String.valueOf(wuNums[i]));
			map.put("label",DictUtils.getDictLabel(String.valueOf(i+1),"affair_wtlb",""));
			l.add(map);
		}
		return l;
	}
	/*public @ResponseBody Map<String, Object> nncDuchaColumn(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findNncDcInfo(id, null, null, null, month);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findNncDcInfos(ids, null, null, null, month);
					}
				} else {
					infos = affairDcwtLibraryService.findNncDcInfo(null, null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairDcwtLibraryService.findNncDcInfo(id, year, null, null,  null);
				} else {
					ids.add(id);
					infos = affairDcwtLibraryService.findNncDcInfos(ids, year, null, null, null);
				}
			} else {
				infos = affairDcwtLibraryService.findNncDcInfo(null, year, null, null, null);
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findNncDcInfo(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findNncDcInfos(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairDcwtLibraryService.findNncDcInfo(null, null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}*/

	@RequestMapping(value = {"inspectorNncPiePage"})
	public String inspectorNncPiePage(Model model,String flag, String id, Integer year, Date startDate, Date endDate, String month,String dateType){
		model.addAttribute("flag",flag);
		model.addAttribute("id",id);
		model.addAttribute("year",year);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("month",month);
		model.addAttribute("dateType",dateType);
		return "modules/charts/inspectorNncPieInfo";
	}

	@RequestMapping("inspectorNncPiePageInfo")
	public @ResponseBody List<Map<String, String>> inspectorNncPiePageInfo(String flag, String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		AffairDcwtLibrary affairDcwtLibrary = new AffairDcwtLibrary();
		if (StringUtils.isNotBlank(flag)){
			affairDcwtLibrary.setFlag(flag);
		}
		if (dateType.equals("1")){		//月度查询
			affairDcwtLibrary.setMonth(month);
		}else if (dateType.equals("3")){	//时间段查询
			affairDcwtLibrary.setStartDate(DateUtils.parseDate(dateStart));
			affairDcwtLibrary.setEndDate(DateUtils.parseDate(dateEnd));
		}else {		//年度查询
			affairDcwtLibrary.setYear(year);
		}
		List<AffairDcwtLibrary> tmpList = affairDcwtLibraryService.findNncDetailPieInfoList(affairDcwtLibrary);
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		if(null != list){
			for(AffairDcwtLibrary library: tmpList){
				String[] catagorys = library.getProblemCategory().split(",");
				Set catSet = new HashSet();
				for(String catagory :catagorys){
					catSet.add(catagory);
				}
				if (catSet.contains(flag)){
					list.add(library);
				}
			}
		}
		List<Dict> dicts =DictUtils.getDictList("affair_zhenggai");
		int[] nums = new int[dicts.size()];
		list.forEach(item->{
			for(int i = 0;i<dicts.size();i++) {
				String[] catagorys = item.getRectification().split(",");
				Set catSet = new HashSet();
				for(String catagory :catagorys){
					catSet.add(catagory);
				}
				if (catSet.contains(dicts.get(i).getValue())){
					nums[i]++;
				}
			}
		});
		List<Map<String,String>> l= new ArrayList<>();
		for(int i =0;i<dicts.size();i++) {
			Map<String, String> map = new HashMap<>();
			map.put("total", String.valueOf(nums[i]));
			map.put("label", DictUtils.getDictLabel(String.valueOf(i + 1), "affair_zhenggai", ""));
			l.add(map);
		}
		return l;
	}

	/** 南宁处督察问题库情况-饼状图
	 * @param id
	 * @return
	 */
	@RequestMapping("nnc-ducha-pie")
	public @ResponseBody List<Map<String, String>> nncDuchaPie(String id, String dateType, Integer year,String dateStart, String dateEnd, String month) {
		AffairDcwtLibrary affairDcwtLibrary = new AffairDcwtLibrary();
		if (dateType.equals("1")){		//月度查询
			affairDcwtLibrary.setMonth(month);
		}else if (dateType.equals("3")){	//时间段查询
			affairDcwtLibrary.setStartDate(DateUtils.parseDate(dateStart));
			affairDcwtLibrary.setEndDate(DateUtils.parseDate(dateEnd));
		}else {		//年度查询
			affairDcwtLibrary.setYear(year);
		}
		List<AffairDcwtLibrary> list = affairDcwtLibraryService.findNncPieInfoList(affairDcwtLibrary);
		List<Dict> dicts =DictUtils.getDictList("affair_wtlb");
		int[] nums = new int[dicts.size()];
		list.forEach(item->{
			for(int i = 0;i<dicts.size();i++) {
				if(item!=null && item.getProblemCategory()!=null){
					// 存在不足字段为空处理  kevin.jia 11.9 没有南宁、北海、柳州处督察支队的统计分析数据
					String[] catagorys = item.getProblemCategory().split(",");
					Set catSet = new HashSet();
					for(String catagory :catagorys){
						catSet.add(catagory);
					}
					if (catSet.contains(dicts.get(i).getValue())){
						nums[i]++;
					}
				}
			}
		});
		List<Map<String,String>> l= new ArrayList<>();
		for(int i =0;i<dicts.size();i++) {
			Map<String, String> map = new HashMap<>();
			map.put("total", String.valueOf(nums[i]));
			map.put("label", DictUtils.getDictLabel(String.valueOf(i + 1), "affair_wtlb", ""));
			l.add(map);
		}
		return l;
	}
	/*public @ResponseBody List<Map<String, Object>> nncDuchaPie(String id, String dateType, Integer year,String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findNncPieInfoByCreateOrgId(id, null, null, null, month);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findNncPieInfoByCreateOrgIds(ids, null, null, null, month);
					}
				} else {
					infos = affairDcwtLibraryService.findNncPieInfoByCreateOrgId(null, null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairDcwtLibraryService.findNncPieInfoByCreateOrgId(id, year, null, null, null);
				} else {
					ids.add(id);
					infos = affairDcwtLibraryService.findNncPieInfoByCreateOrgIds(ids, year, null, null, null);
				}
			} else {
				infos = affairDcwtLibraryService.findNncPieInfoByCreateOrgId(null, year, null, null, null);
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findNncPieInfoByCreateOrgId(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findNncPieInfoByCreateOrgIds(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairDcwtLibraryService.findNncPieInfoByCreateOrgId(null, null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}*/

	/** 柳州处督察问题库情况-柱状图
	 * @param id
	 * @return
	 */
	@RequestMapping("lzc-ducha-column")
	public @ResponseBody List<Map<String, String>> lzcDuchaColumn(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		AffairDcwtLibrary affairDcwtLibrary = new AffairDcwtLibrary();
		if (dateType.equals("1")){		//月度查询
			affairDcwtLibrary.setMonth(month);
		}else if (dateType.equals("3")){	//时间段查询
			affairDcwtLibrary.setStartDate(DateUtils.parseDate(dateStart));
			affairDcwtLibrary.setEndDate(DateUtils.parseDate(dateEnd));
		}else {		//年度查询
			affairDcwtLibrary.setYear(year);
		}
		List<AffairDcwtLibrary> list = affairDcwtLibraryService.findLzcInfoList(affairDcwtLibrary);
		List<Dict> dicts =DictUtils.getDictList("affair_wtlb");
		int[] nums = new int[dicts.size()];
		int[] yzgNums = new int[dicts.size()];
		int[] wzgNums = new int[dicts.size()];
		int[] xqNums = new int[dicts.size()];
		int[] wuNums = new int[dicts.size()];
		list.forEach(item -> {
			for(int i = 0;i<dicts.size();i++) {
				// 存在不足字段为空处理  kevin.jia 11.9 没有南宁、北海、柳州处督察支队的统计分析数据
				if(item!=null && item.getProblemCategory()!=null){
					String[] catagorys = item.getProblemCategory().split(",");
					Set catSet = new HashSet();
					for(String catagory :catagorys){
						catSet.add(catagory);
					}
					if (catSet.contains(dicts.get(i).getValue())){
						nums[i]++;
						if (item.getRectification().contains("1")){
							yzgNums[i]++;
						}
						if (item.getRectification().contains("2")){
							wzgNums[i]++;
						}
						if (item.getRectification().contains("3")){
							xqNums[i]++;
						}
						if (item.getRectification().contains("4")){
							wuNums[i]++;
						}
					}
				}
			}
		});
		List<Map<String,String>> l= new ArrayList<>();
		for(int i =0;i<dicts.size();i++) {
			Map<String,String> map=new HashMap<>();
			map.put("total", String.valueOf(nums[i]));
			map.put("yzgTotal",String.valueOf(yzgNums[i]));
			map.put("wzgTotal",String.valueOf(wzgNums[i]));
			map.put("xqTotal",String.valueOf(xqNums[i]));
			map.put("wuTotal",String.valueOf(wuNums[i]));
			map.put("label",DictUtils.getDictLabel(String.valueOf(i+1),"affair_wtlb",""));
			l.add(map);
		}
		return l;
	}
	/*public @ResponseBody Map<String, Object> lzcDuchaColumn(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findLzcDcInfo(id, null, null, null, month);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findLzcDcInfos(ids, null, null, null, month);
					}
				} else {
					infos = affairDcwtLibraryService.findLzcDcInfo(null, null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairDcwtLibraryService.findLzcDcInfo(id, year, null, null,  null);
				} else {
					ids.add(id);
					infos = affairDcwtLibraryService.findLzcDcInfos(ids, year, null, null, null);
				}
			} else {
				infos = affairDcwtLibraryService.findLzcDcInfo(null, year, null, null, null);
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findLzcDcInfo(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findLzcDcInfos(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairDcwtLibraryService.findLzcDcInfo(null, null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}*/

	@RequestMapping(value = {"inspectorLzcPiePage"})
	public String inspectorLzcPiePage(Model model,String flag, String id, Integer year, Date startDate, Date endDate, String month,String dateType){
		model.addAttribute("flag",flag);
		model.addAttribute("id",id);
		model.addAttribute("year",year);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("month",month);
		model.addAttribute("dateType",dateType);
		return "modules/charts/inspectorLzcPieInfo";
	}

	@RequestMapping("inspectorLzcPiePageInfo")
	public @ResponseBody List<Map<String, String>> inspectorLzcPiePageInfo(String flag, String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		AffairDcwtLibrary affairDcwtLibrary = new AffairDcwtLibrary();
		if (StringUtils.isNotBlank(flag)){
			affairDcwtLibrary.setFlag(flag);
		}
		if (dateType.equals("1")){		//月度查询
			affairDcwtLibrary.setMonth(month);
		}else if (dateType.equals("3")){	//时间段查询
			affairDcwtLibrary.setStartDate(DateUtils.parseDate(dateStart));
			affairDcwtLibrary.setEndDate(DateUtils.parseDate(dateEnd));
		}else {		//年度查询
			affairDcwtLibrary.setYear(year);
		}
		List<AffairDcwtLibrary> tmpList = affairDcwtLibraryService.findLzcDetailPieInfoList(affairDcwtLibrary);
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		if(null != list){
			for(AffairDcwtLibrary library: tmpList){
				String[] catagorys = library.getProblemCategory().split(",");
				Set catSet = new HashSet();
				for(String catagory :catagorys){
					catSet.add(catagory);
				}
				if (catSet.contains(flag)){
					list.add(library);
				}
			}
		}
		List<Dict> dicts =DictUtils.getDictList("affair_zhenggai");
		int[] nums = new int[dicts.size()];
		list.forEach(item->{
			for(int i = 0;i<dicts.size();i++) {
				String[] catagorys = item.getRectification().split(",");
				Set catSet = new HashSet();
				for(String catagory :catagorys){
					catSet.add(catagory);
				}
				if (catSet.contains(dicts.get(i).getValue())){
					nums[i]++;
				}
			}
		});
		List<Map<String,String>> l= new ArrayList<>();
		for(int i =0;i<dicts.size();i++) {
			Map<String, String> map = new HashMap<>();
			map.put("total", String.valueOf(nums[i]));
			map.put("label", DictUtils.getDictLabel(String.valueOf(i + 1), "affair_zhenggai", ""));
			l.add(map);
		}
		return l;
	}

	/** 柳州处督察问题库情况-饼状图
	 * @param id
	 * @return
	 */
	@RequestMapping("lzc-ducha-pie")
	public @ResponseBody List<Map<String, String>> lzcDuchaPie(String id, String dateType, Integer year,String dateStart, String dateEnd, String month) {
		AffairDcwtLibrary affairDcwtLibrary = new AffairDcwtLibrary();
		if (dateType.equals("1")){		//月度查询
			affairDcwtLibrary.setMonth(month);
		}else if (dateType.equals("3")){	//时间段查询
			affairDcwtLibrary.setStartDate(DateUtils.parseDate(dateStart));
			affairDcwtLibrary.setEndDate(DateUtils.parseDate(dateEnd));
		}else {		//年度查询
			affairDcwtLibrary.setYear(year);
		}
		List<AffairDcwtLibrary> list = affairDcwtLibraryService.findLzcPieInfoList(affairDcwtLibrary);
		List<Dict> dicts =DictUtils.getDictList("affair_wtlb");
		int[] nums = new int[dicts.size()];
		list.forEach(item->{
			for(int i = 0;i<dicts.size();i++) {
				// 存在不足字段为空处理  kevin.jia 11.9 没有南宁、北海、柳州处督察支队的统计分析数据
				if(item!=null && item.getProblemCategory()!=null){
					String[] catagorys = item.getProblemCategory().split(",");
					Set catSet = new HashSet();
					for(String catagory :catagorys){
						catSet.add(catagory);
					}
					if (catSet.contains(dicts.get(i).getValue())){
						nums[i]++;
					}
				}
			}
		});
		List<Map<String,String>> l= new ArrayList<>();
		for(int i =0;i<dicts.size();i++) {
			Map<String, String> map = new HashMap<>();
			map.put("total", String.valueOf(nums[i]));
			map.put("label", DictUtils.getDictLabel(String.valueOf(i + 1), "affair_wtlb", ""));
			l.add(map);
		}
		return l;
	}
	/*public @ResponseBody List<Map<String, Object>> lzcDuchaPie(String id, String dateType, Integer year,String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findLzcPieInfoByCreateOrgId(id, null, null, null, month);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findLzcPieInfoByCreateOrgIds(ids, null, null, null, month);
					}
				} else {
					infos = affairDcwtLibraryService.findLzcPieInfoByCreateOrgId(null, null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairDcwtLibraryService.findLzcPieInfoByCreateOrgId(id, year, null, null, null);
				} else {
					ids.add(id);
					infos = affairDcwtLibraryService.findLzcPieInfoByCreateOrgIds(ids, year, null, null, null);
				}
			} else {
				infos = affairDcwtLibraryService.findLzcPieInfoByCreateOrgId(null, year, null, null, null);
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findLzcPieInfoByCreateOrgId(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findLzcPieInfoByCreateOrgIds(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairDcwtLibraryService.findLzcPieInfoByCreateOrgId(null, null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}*/

	/** 北海处督察问题库情况-柱状图
	 * @param id
	 * @return
	 */
	@RequestMapping("bhc-ducha-column")
	public @ResponseBody List<Map<String, String>> bhcDuchaColumn(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		AffairDcwtLibrary affairDcwtLibrary = new AffairDcwtLibrary();
		if (dateType.equals("1")){		//月度查询
			affairDcwtLibrary.setMonth(month);
		}else if (dateType.equals("3")){	//时间段查询
			affairDcwtLibrary.setStartDate(DateUtils.parseDate(dateStart));
			affairDcwtLibrary.setEndDate(DateUtils.parseDate(dateEnd));
		}else {		//年度查询
			affairDcwtLibrary.setYear(year);
		}
		List<AffairDcwtLibrary> list = affairDcwtLibraryService.findBhcInfoList(affairDcwtLibrary);
		List<Dict> dicts =DictUtils.getDictList("affair_wtlb");
		int[] nums = new int[dicts.size()];
		int[] yzgNums = new int[dicts.size()];
		int[] wzgNums = new int[dicts.size()];
		int[] xqNums = new int[dicts.size()];
		int[] wuNums = new int[dicts.size()];
		list.forEach(item -> {
			for(int i = 0;i<dicts.size();i++) {
				// 存在不足字段为空处理  kevin.jia 11.9 没有南宁、北海、柳州处督察支队的统计分析数据
				if(item != null && item.getProblemCategory()!=null){
					String[] catagorys = item.getProblemCategory().split(",");
					Set catSet = new HashSet();
					for(String catagory :catagorys){
						catSet.add(catagory);
					}
					if (catSet.contains(dicts.get(i).getValue())){
						nums[i]++;
						if (item.getRectification().contains("1")){
							yzgNums[i]++;
						}
						if (item.getRectification().contains("2")){
							wzgNums[i]++;
						}
						if (item.getRectification().contains("3")){
							xqNums[i]++;
						}
						if (item.getRectification().contains("4")){
							wuNums[i]++;
						}
					}
				}
			}
		});
		List<Map<String,String>> l= new ArrayList<>();
		for(int i =0;i<dicts.size();i++) {
			Map<String,String> map=new HashMap<>();
			map.put("total", String.valueOf(nums[i]));
			map.put("yzgTotal",String.valueOf(yzgNums[i]));
			map.put("wzgTotal",String.valueOf(wzgNums[i]));
			map.put("xqTotal",String.valueOf(xqNums[i]));
			map.put("wuTotal",String.valueOf(wuNums[i]));
			map.put("label",DictUtils.getDictLabel(String.valueOf(i+1),"affair_wtlb",""));
			l.add(map);
		}
		return l;
	}
	/*public @ResponseBody Map<String, Object> bhcDuchaColumn(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findBhcDcInfo(id, null, null, null, month);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findBhcDcInfos(ids, null, null, null, month);
					}
				} else {
					infos = affairDcwtLibraryService.findBhcDcInfo(null, null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairDcwtLibraryService.findBhcDcInfo(id, year, null, null,  null);
				} else {
					ids.add(id);
					infos = affairDcwtLibraryService.findBhcDcInfos(ids, year, null, null, null);
				}
			} else {
				infos = affairDcwtLibraryService.findBhcDcInfo(null, year, null, null, null);
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findBhcDcInfo(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findBhcDcInfos(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairDcwtLibraryService.findBhcDcInfo(null, null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}*/

	@RequestMapping(value = {"inspectorBhcPiePage"})
	public String inspectorBhcPiePage(Model model,String flag, String id, Integer year, Date startDate, Date endDate, String month,String dateType){
		model.addAttribute("flag",flag);
		model.addAttribute("id",id);
		model.addAttribute("year",year);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("month",month);
		model.addAttribute("dateType",dateType);
		return "modules/charts/inspectorBhcPieInfo";
	}

	@RequestMapping("inspectorBhcPiePageInfo")
	public @ResponseBody List<Map<String, String>> inspectorBhcPiePageInfo(String flag, String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		AffairDcwtLibrary affairDcwtLibrary = new AffairDcwtLibrary();
		if (StringUtils.isNotBlank(flag)){
			affairDcwtLibrary.setFlag(flag);
		}
		if (dateType.equals("1")){		//月度查询
			affairDcwtLibrary.setMonth(month);
		}else if (dateType.equals("3")){	//时间段查询
			affairDcwtLibrary.setStartDate(DateUtils.parseDate(dateStart));
			affairDcwtLibrary.setEndDate(DateUtils.parseDate(dateEnd));
		}else {		//年度查询
			affairDcwtLibrary.setYear(year);
		}
		List<AffairDcwtLibrary> tmpList = affairDcwtLibraryService.findBhcDetailPieInfoList(affairDcwtLibrary);
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		if(null != list){
			for(AffairDcwtLibrary library: tmpList){
				String[] catagorys = library.getProblemCategory().split(",");
				Set catSet = new HashSet();
				for(String catagory :catagorys){
					catSet.add(catagory);
				}
				if (catSet.contains(flag)){
					list.add(library);
				}
			}
		}
		List<Dict> dicts =DictUtils.getDictList("affair_zhenggai");
		int[] nums = new int[dicts.size()];
		list.forEach(item->{
			for(int i = 0;i<dicts.size();i++) {
				String[] catagorys = item.getRectification().split(",");
				Set catSet = new HashSet();
				for(String catagory :catagorys){
					catSet.add(catagory);
				}
				if (catSet.contains(dicts.get(i).getValue())){
					nums[i]++;
				}
			}
		});
		List<Map<String,String>> l= new ArrayList<>();
		for(int i =0;i<dicts.size();i++) {
			Map<String, String> map = new HashMap<>();
			map.put("total", String.valueOf(nums[i]));
			map.put("label", DictUtils.getDictLabel(String.valueOf(i + 1), "affair_zhenggai", ""));
			l.add(map);
		}
		return l;
	}

	/** 北海处督察问题库情况-饼状图
	 * @param id
	 * @return
	 */
	@RequestMapping("bhc-ducha-pie")
	public @ResponseBody List<Map<String, String>> bhcDuchaPie(String id, String dateType, Integer year,String dateStart, String dateEnd, String month) {
		AffairDcwtLibrary affairDcwtLibrary = new AffairDcwtLibrary();
		if (dateType.equals("1")){		//月度查询
			affairDcwtLibrary.setMonth(month);
		}else if (dateType.equals("3")){	//时间段查询
			affairDcwtLibrary.setStartDate(DateUtils.parseDate(dateStart));
			affairDcwtLibrary.setEndDate(DateUtils.parseDate(dateEnd));
		}else {		//年度查询
			affairDcwtLibrary.setYear(year);
		}
		List<AffairDcwtLibrary> list = affairDcwtLibraryService.findBhcPieInfoList(affairDcwtLibrary);
		List<Dict> dicts =DictUtils.getDictList("affair_wtlb");
		int[] nums = new int[dicts.size()];
		list.forEach(item->{
			for(int i = 0;i<dicts.size();i++) {
				// 存在不足字段为空处理  kevin.jia 11.9 没有南宁、北海、柳州处督察支队的统计分析数据
				if(item!=null && item.getProblemCategory()!=null){
					String[] catagorys = item.getProblemCategory().split(",");
					Set catSet = new HashSet();
					for(String catagory :catagorys){
						catSet.add(catagory);
					}
					if (catSet.contains(dicts.get(i).getValue())){
						nums[i]++;
					}
				}
			}
		});
		List<Map<String,String>> l= new ArrayList<>();
		for(int i =0;i<dicts.size();i++) {
			Map<String, String> map = new HashMap<>();
			map.put("total", String.valueOf(nums[i]));
			map.put("label", DictUtils.getDictLabel(String.valueOf(i + 1), "affair_wtlb", ""));
			l.add(map);
		}
		return l;
	}
	/*public @ResponseBody List<Map<String, Object>> bhcDuchaPie(String id, String dateType, Integer year,String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findBhcPieInfoByCreateOrgId(id, null, null, null, month);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findBhcPieInfoByCreateOrgIds(ids, null, null, null, month);
					}
				} else {
					infos = affairDcwtLibraryService.findBhcPieInfoByCreateOrgId(null, null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairDcwtLibraryService.findBhcPieInfoByCreateOrgId(id, year, null, null, null);
				} else {
					ids.add(id);
					infos = affairDcwtLibraryService.findBhcPieInfoByCreateOrgIds(ids, year, null, null, null);
				}
			} else {
				infos = affairDcwtLibraryService.findBhcPieInfoByCreateOrgId(null, year, null, null, null);
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findBhcPieInfoByCreateOrgId(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findBhcPieInfoByCreateOrgIds(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairDcwtLibraryService.findBhcPieInfoByCreateOrgId(null, null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}*/
	/**
	 * 全局督察问题库情况-监督单位柱状图
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("allDuchaSupervisoryUnit")
	public @ResponseBody List<Map<String, Object>> allDuchaSupervisoryUnit(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findAllSupervisoryUnitByCreateOrgId(id, null, null, null, month);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findAllSupervisoryUnitByCreateOrgIds(ids, null, null, null, month);
					}
				} else {
					infos = affairDcwtLibraryService.findAllSupervisoryUnitByCreateOrgId(null, null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairDcwtLibraryService.findAllSupervisoryUnitByCreateOrgId(id, year, null, null,  null);
				} else {
					ids.add(id);
					infos = affairDcwtLibraryService.findAllSupervisoryUnitByCreateOrgIds(ids, year, null, null, null);
				}
			} else {
				infos = affairDcwtLibraryService.findAllSupervisoryUnitByCreateOrgId(null, year, null, null, null);
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findAllSupervisoryUnitByCreateOrgId(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findAllSupervisoryUnitByCreateOrgIds(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairDcwtLibraryService.findAllSupervisoryUnitByCreateOrgId(null, null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}

	/**
	 * 督察问题库情况-监督单位柱状图
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("duchaSupervisoryUnit")
	public @ResponseBody List<Map<String, Object>> duchaSupervisoryUnit(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findSupervisoryUnitByCreateOrgId(id, null, null, null, month);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findSupervisoryUnitByCreateOrgIds(ids, null, null, null, month);
					}
				} else {
					infos = affairDcwtLibraryService.findSupervisoryUnitByCreateOrgId(null, null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairDcwtLibraryService.findSupervisoryUnitByCreateOrgId(id, year, null, null,  null);
				} else {
					ids.add(id);
					infos = affairDcwtLibraryService.findSupervisoryUnitByCreateOrgIds(ids, year, null, null, null);
				}
			} else {
				infos = affairDcwtLibraryService.findSupervisoryUnitByCreateOrgId(null, year, null, null, null);
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairDcwtLibraryService.findSupervisoryUnitByCreateOrgId(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairDcwtLibraryService.findSupervisoryUnitByCreateOrgIds(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairDcwtLibraryService.findSupervisoryUnitByCreateOrgId(null, null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}

	/**
	 * 工团工作情况
	 * 
	 * @return
	 */
	@RequestMapping("labourIndex")
	public ModelAndView labour(String id) {
		ModelAndView view = new ModelAndView();
		view.addObject("id", id);
		view.addObject("dateType", "1");
		view.addObject("year", DateUtils.getYear());
		view.addObject("month", DateUtils.getYear()+"-"+DateUtils.getMonth());
		view.setViewName("modules/charts/labour");
		return view;
	}
	
	
	/**慰问工作柱状图
	 * @return
	 */
	@RequestMapping("weiwen-column")
	public @ResponseBody Map<String, Object> weiwenColumn(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, String>> infos = null;
		List<String> labelData = new ArrayList<>();
		List<String> countData = new ArrayList<>();
		//List<Double> sumData = new ArrayList<>();
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairConsolationWorkInfoService.findInfoByCreateOrgId(id, null, null, null, month);
					} else {
						ids.add(id);
						infos = affairConsolationWorkInfoService.findInfoByCreateOrgIds(ids, null, null, null, month);
					}
				} else {
					infos = affairConsolationWorkInfoService.findInfoByCreateOrgId(null, null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairConsolationWorkInfoService.findInfoByCreateOrgId(id, year, null, null, null);
				} else {
					ids.add(id);
					infos = affairConsolationWorkInfoService.findInfoByCreateOrgIds(ids, year, null, null,  null);
				}
			} else {
				infos = affairConsolationWorkInfoService.findInfoByCreateOrgId(null, year, null, null, null);
			}
		}else {// 时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairConsolationWorkInfoService.findInfoByCreateOrgId(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairConsolationWorkInfoService.findInfoByCreateOrgIds(ids, null, startDate, endDate,  null);
					}
				} else {
					infos = affairConsolationWorkInfoService.findInfoByCreateOrgId(null, null, startDate, endDate, null);
				}
			}
		}
		List<Map<String, String>> list = ChartLabelUtils.fillLabel(infos,DictUtils.getDictList("affair_ww"));
		for (Map<String, String> map : list) {
			labelData.add(map.get("label"));
			countData.add(map.get("num"));
			//sumData.add(Double.valueOf(map.get("sum").toString()));
		}
		Map<String, Object> map = new HashMap<>();
		map.put("labelData", labelData);
		map.put("countData", countData);
		//map.put("sumData", sumData);
		return map;
	}
	/** 基础建设
	 * @param id
	 * @return
	 */
	@RequestMapping("xzy")
	public @ResponseBody Map<String, Object> xzy(String id, String dateType, Integer year, String monthStart, String monthEnd) {
		Map<String, Object> policeHome = null;
		Map<String, Object> Xzy = null;
		if ("1".equals(dateType)) {//月度
			Date mStartDate = DateUtils.parseDate(monthStart);
			Date mEndDate = DateUtils.parseDate(monthEnd);
			if (!(mStartDate ==null && mEndDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						policeHome = affairPoliceHomeService.findInfoByCreateOrgId(id, null, mStartDate, mEndDate);
						Xzy = affairXzyService.findInfoByCreateOrgId(id, null, mStartDate, mEndDate);
					} else {
						ids.add(id);
						policeHome = affairPoliceHomeService.findInfoByCreateOrgIds(ids, null, mStartDate, mEndDate);
						Xzy = affairXzyService.findInfoByCreateOrgIds(ids, null, mStartDate, mEndDate);
					}
				} else {
					policeHome = affairPoliceHomeService.findInfoByCreateOrgId(null, null, mStartDate, mEndDate);
					Xzy = affairXzyService.findInfoByCreateOrgId(null, null, mStartDate, mEndDate);
				}
			}
		}else{//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					policeHome = affairPoliceHomeService.findInfoByCreateOrgId(id, year, null, null);
					Xzy = affairXzyService.findInfoByCreateOrgId(id, year, null, null);
				} else {
					ids.add(id);
					policeHome = affairPoliceHomeService.findInfoByCreateOrgIds(ids, year, null, null);
					Xzy = affairXzyService.findInfoByCreateOrgIds(ids, year, null, null);
				}
			} else {
				policeHome = affairPoliceHomeService.findInfoByCreateOrgId(null, year, null, null);
				Xzy = affairXzyService.findInfoByCreateOrgId(null, year, null, null);
			}
		}

		List<Double> sumData = new ArrayList<>();
		sumData.add(Double.valueOf(policeHome.get("count").toString()));
		sumData.add(Double.valueOf(Xzy.get("count").toString()));
		Map<String, Object> result = new HashMap<>();
		result.put("sumData", sumData);
		return result;
	}

    /**
     * 固资情况
     *
     * @param id
     * @return
     */
    @RequestMapping("gz")
    public @ResponseBody Map<String, Object> gz(String id, String flag, String dateType, Integer year, String monthStart, String monthEnd){
        List<Map<String, Object>> infos = null;
		if ("1".equals(dateType)) {//月度
			Date mStartDate = DateUtils.parseDate(monthStart);
			Date mEndDate = DateUtils.parseDate(monthEnd);
			if (!(mStartDate ==null && mEndDate == null)) {
				if ("3".equals(flag)) {
					if (id != null && !TOP_OFFICE_ID.equals(id))  {
						List<String> ids = officeService.findIdsByParentId(id);
						if(CollectionUtils.isEmpty(ids)) {
							infos = affairGzService.findInfoByCreateOrgId(id, null, mStartDate, mEndDate);
						} else {
							ids.add(id);
							infos = affairGzService.findInfoByCreateOrgIds(ids, null, mStartDate, mEndDate);
						}
					} else {
						infos = affairGzService.findInfoByCreateOrgId(null, null, mStartDate, mEndDate);
					}
				}else if("7".equals(flag)){
					if (id != null && !TOP_OFFICE_ID.equals(id))  {
						List<String> ids = officeService.findIdsByParentId(id);
						if(CollectionUtils.isEmpty(ids)) {
							infos = affairChildSubsidyService.findInfoByCreateOrgId(id, null, mStartDate, mEndDate);
						} else {
							ids.add(id);
							infos = affairChildSubsidyService.findInfoByCreateOrgIds(ids, null, mStartDate, mEndDate);
						}
					} else {
						infos = affairChildSubsidyService.findInfoByCreateOrgId(null, null, mStartDate, mEndDate);
					}
				}
			}
		}else{//年度
			if ("3".equals(flag)) {
				if (id != null && !TOP_OFFICE_ID.equals(id))  {
					List<String> ids = officeService.findIdsByParentId(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = affairGzService.findInfoByCreateOrgId(id, year, null, null);
					} else {
						ids.add(id);
						infos = affairGzService.findInfoByCreateOrgIds(ids, year, null, null);
					}
				} else {
					infos = affairGzService.findInfoByCreateOrgId(null, year, null, null);
				}
			}else if("7".equals(flag)){
				if (id != null && !TOP_OFFICE_ID.equals(id))  {
					List<String> ids = officeService.findIdsByParentId(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = affairChildSubsidyService.findInfoByCreateOrgId(id, year, null, null);
					} else {
						ids.add(id);
						infos = affairChildSubsidyService.findInfoByCreateOrgIds(ids, year, null, null);
					}
				} else {
					infos = affairChildSubsidyService.findInfoByCreateOrgId(null, year, null, null);
				}
			}
		}
        List<String> labelData = new ArrayList<>();
        List<Double> countData = new ArrayList<>();
        List<Double> sumData = new ArrayList<>();
        for(Map<String, Object> map : infos) {
            labelData.add(map.get("label").toString());
            countData.add(Double.valueOf(map.get("count").toString()));
            sumData.add(Double.valueOf(map.get("sum").toString()));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("labelData", labelData);
        map.put("countData", countData);
        map.put("sumData", sumData);
        return map;
    }
    /**
     * 固资情况
     *
     * @param id
     * @return
     */
    @RequestMapping("guzi")
    public @ResponseBody List<Map<String, Object>> guzi(String id,String flag) {
        List<Map<String, Object>> infos = null;
        if("4".equals(flag)){
            if (id != null && !TOP_OFFICE_ID.equals(id)) {
                List<String> ids = officeService.findIdsByParentId(id);
                if (CollectionUtils.isEmpty(ids)) {
                    infos = affairOrganizationBulidService.findInfoByUnitId(id);
                } else {
                    ids.add(id);
                    infos = affairOrganizationBulidService.findInfoByUnitIds(ids);
                }
            } else {
                infos = affairOrganizationBulidService.findInfoByUnitId(null);
            }
        }else if("5".equals(flag)){
            if (id != null && !TOP_OFFICE_ID.equals(id)) {
                List<String> ids = officeService.findIdsByParentId(id);
                if (CollectionUtils.isEmpty(ids)) {
                    infos = affairMjxyReportService.findInfoByCreateOrgId(id);
                } else {
                    ids.add(id);
                    infos = affairMjxyReportService.findInfoByCreateOrgIds(ids);
                }
            } else {
                infos = affairMjxyReportService.findInfoByCreateOrgId(null);
            }
        }else if("6".equals(flag))
        {
            if (id != null && !TOP_OFFICE_ID.equals(id)) {
                List<String> ids = officeService.findIdsByParentId(id);
                if (CollectionUtils.isEmpty(ids)) {
                    infos = affairPolicewomanBaseService.findInfoByCreateOrgId(id);
                } else {
                    ids.add(id);
                    infos = affairPolicewomanBaseService.findInfoByCreateOrgIds(ids);
                }
            } else {
                infos = affairPolicewomanBaseService.findInfoByCreateOrgId(null);
            }
        }else if("12".equals(flag)){
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairBrandManagementService.findInfoByUnitId(id);
				} else {
					ids.add(id);
					infos = affairBrandManagementService.findInfoByUnitIds(ids);
				}
			} else {
				infos = affairBrandManagementService.findInfoByUnitId(null);
			}
		}
        return infos;
    }

	/**
	 * 工会活动情况
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("huodong")
	public @ResponseBody Map<String, Object> huodong(String id, String flag){
		Map<String, Object> baoming;
		Map<String, Object> jilu;
		Map<String, Object> result = new HashMap<>();
		if("8".equals(flag)){
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					baoming = affairGhActivityEnrollService.findInfoByCreateOrgId(id);
					jilu = affairGhActivityRecordService.findInfoByCreateOrgId(id);
				} else {
					ids.add(id);
					baoming = affairGhActivityEnrollService.findInfoByCreateOrgIds(ids);
					jilu = affairGhActivityRecordService.findInfoByCreateOrgIds(ids);
				}
			} else {
				baoming = affairGhActivityEnrollService.findInfoByCreateOrgId(null);
				jilu = affairGhActivityRecordService.findInfoByCreateOrgId(null);
			}
			List<Double> sumData = new ArrayList<>();
			sumData.add(Double.valueOf(baoming.get("count").toString()));
			sumData.add(Double.valueOf(jilu.get("count").toString()));
			result.put("sumData", sumData);
		}
		return result;
	}

	@RequestMapping("labourActivity")
	@ResponseBody
	public List<Map<String,String>> labourActivity(String dateType,Integer year,String dateStart,String dateEnd,String month){

		Date startDate = DateUtils.parseDate(dateStart);
		Date endDate = DateUtils.parseDate(dateEnd);
		if (dateType.equals("1")){//月查询
			year=null;
			endDate=null;
			startDate=null;
		}else if (dateType.equals("3")){//时间段查询
			year=null;
			month=null;
		}else {//年查询
			month=null;
			endDate=null;
			startDate=null;
		}
		List<Map<String, String>> list  = affairGhActivityRecordService.countActivity(year,startDate,endDate,month);
		return list;
	}
	/**
	 * 工会表彰
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("biaozhang")
	public @ResponseBody Map<String, Object> biaozhang(String id, String flag){
		Map<String, Object> danwei;
		Map<String, Object> geren;
		Map<String, Object> result = new HashMap<>();
		if("9".equals(flag)){
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					danwei = affairCollectiveAwardService.findInfoByCreateOrgId(id);
					geren = affairPersonalAwardService.findInfoByCreateOrgId(id);
				} else {
					ids.add(id);
					danwei = affairCollectiveAwardService.findInfoByCreateOrgIds(ids);
					geren = affairPersonalAwardService.findInfoByCreateOrgIds(ids);
				}
			} else {
				danwei = affairCollectiveAwardService.findInfoByCreateOrgId(null);
				geren = affairPersonalAwardService.findInfoByCreateOrgId(null);
			}
			List<Double> sumData = new ArrayList<>();
			sumData.add(Double.valueOf(danwei.get("count").toString()));
			sumData.add(Double.valueOf(geren.get("count").toString()));
			result.put("sumData", sumData);
		}
		return result;
	}
	/**
	 * 团委表彰
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("twbiaozhang")
	public @ResponseBody Map<String, Object> twbiaozhang(String id, String flag){
		Map<String, Object> danwei;
		Map<String, Object> geren;
		Map<String, Object> result = new HashMap<>();
		if("11".equals(flag)){
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					danwei = affairTwUnitAwardService.findInfoByCreateOrgId(id);
					geren = affairTwPersonalAwardService.findInfoByCreateOrgId(id);
				} else {
					ids.add(id);
					danwei = affairTwUnitAwardService.findInfoByCreateOrgIds(ids);
					geren = affairTwPersonalAwardService.findInfoByCreateOrgIds(ids);
				}
			} else {
				danwei = affairTwUnitAwardService.findInfoByCreateOrgId(null);
				geren = affairTwPersonalAwardService.findInfoByCreateOrgId(null);
			}
			List<Double> sumData = new ArrayList<>();
			sumData.add(Double.valueOf(danwei.get("count").toString()));
			sumData.add(Double.valueOf(geren.get("count").toString()));
			result.put("sumData", sumData);
		}
		return result;
	}
	/**
	 * 团内活动
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("tnhuodong")
	public @ResponseBody Map<String, Object> tnhuodong(String id, String flag){
		Map<String, Object> baoming;
		Map<String, Object> jilu;
		Map<String, Object> result = new HashMap<>();
		if("13".equals(flag)){
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					baoming = affairTnActivityEnrollService.findInfoByCreateOrgId(id);
					jilu = affairTnActivityRecordService.findInfoByCreateOrgId(id);
				} else {
					ids.add(id);
					baoming = affairTnActivityEnrollService.findInfoByCreateOrgIds(ids);
					jilu = affairTnActivityRecordService.findInfoByCreateOrgIds(ids);
				}
			} else {
				baoming = affairTnActivityEnrollService.findInfoByCreateOrgId(null);
				jilu = affairTnActivityRecordService.findInfoByCreateOrgId(null);
			}
			List<Double> sumData = new ArrayList<>();
			sumData.add(Double.valueOf(baoming.get("count").toString()));
			sumData.add(Double.valueOf(jilu.get("count").toString()));
			result.put("sumData", sumData);
		}
		return result;
	}
	/**
	 * 调研简报
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("jianbao")
	public @ResponseBody Map<String, Object> jianbao(String id, String flag){
		Map<String, Object> sixiang;
		Map<String, Object> shiji;
		Map<String, Object> diaoyan;
		Map<String, Object> result = new HashMap<>();
		if("15".equals(flag)){
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					sixiang = affairThoughtAnalysisService.findInfoByCreateOrgId(id);
					shiji = affairDeedsBriefingService.findInfoByCreateOrgId(id);
					diaoyan = affairTuanzuzhiResearchArticleService.findInfoByCreateOrgId(id);
				} else {
					ids.add(id);
					sixiang = affairThoughtAnalysisService.findInfoByCreateOrgIds(ids);
					shiji = affairDeedsBriefingService.findInfoByCreateOrgIds(ids);
					diaoyan = affairTuanzuzhiResearchArticleService.findInfoByCreateOrgIds(ids);
				}
			} else {
				sixiang = affairThoughtAnalysisService.findInfoByCreateOrgId(null);
				shiji = affairDeedsBriefingService.findInfoByCreateOrgId(null);
				diaoyan = affairTuanzuzhiResearchArticleService.findInfoByCreateOrgId(null);
			}
			List<Double> sumData = new ArrayList<>();
			sumData.add(Double.valueOf(sixiang.get("count").toString()));
			sumData.add(Double.valueOf(shiji.get("count").toString()));
			sumData.add(Double.valueOf(diaoyan.get("count").toString()));
			result.put("sumData", sumData);
		}
		return result;
	}
	/**
	 * 工委工作
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("gongwei")
	public @ResponseBody Map<String, Object> gongwei(String id, String flag){
		Map<String, Object> xinxi;
		Map<String, Object> zongjie;
		Map<String, Object> result = new HashMap<>();
		if("16".equals(flag)){
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					xinxi = affairWorkInfoService.findInfoByCreateOrgId(id);
					zongjie = affairWorkSummaryService.findInfoByCreateOrgId(id);
				} else {
					ids.add(id);
					xinxi = affairWorkInfoService.findInfoByCreateOrgIds(ids);
					zongjie = affairWorkSummaryService.findInfoByCreateOrgIds(ids);
				}
			} else {
				xinxi = affairWorkInfoService.findInfoByCreateOrgId(null);
				zongjie = affairWorkSummaryService.findInfoByCreateOrgId(null);
			}
			List<Double> sumData = new ArrayList<>();
			sumData.add(Double.valueOf(xinxi.get("count").toString()));
			sumData.add(Double.valueOf(zongjie.get("count").toString()));
			result.put("sumData", sumData);
		}
		return result;
	}
	/**
	 * 文艺活动
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("wenyi")
	public @ResponseBody Map<String, Object> weiyi(String id, String flag){
		Map<String, Object> yishutuan;
		Map<String, Object> tixie;
		Map<String, Object> result = new HashMap<>();
		if("17".equals(flag)){
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					yishutuan = affairActivityStyleService.findInfoByCreateOrgId(id);
					tixie = affairTixieActivityStyleService.findInfoByCreateOrgId(id);
				} else {
					ids.add(id);
					yishutuan = affairActivityStyleService.findInfoByCreateOrgIds(ids);
					tixie = affairTixieActivityStyleService.findInfoByCreateOrgIds(ids);
				}
			} else {
				yishutuan = affairActivityStyleService.findInfoByCreateOrgId(null);
				tixie = affairTixieActivityStyleService.findInfoByCreateOrgId(null);
			}
			List<Double> sumData = new ArrayList<>();
			sumData.add(Double.valueOf(yishutuan.get("count").toString()));
			sumData.add(Double.valueOf(tixie.get("count").toString()));
			result.put("sumData", sumData);
		}
		return result;
	}

	/** 档案管理情况
	 * @param id
	 * @return
	 */
	@RequestMapping("dangan")
	public @ResponseBody Map<String, Object> dangan(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		//1：月度 2：年度 3:时间段
		if("1".equals(dateType)){//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = personnelHrRecordService.findInfoByCreateOrgId(id, null, null, null, month);
					} else {
						ids.add(id);
						infos = personnelHrRecordService.findInfoByCreateOrgIds(ids, null, null, null, month);
					}
				} else {
					infos = personnelHrRecordService.findInfoByCreateOrgId(null, null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if(CollectionUtils.isEmpty(ids)) {
					infos = personnelHrRecordService.findInfoByCreateOrgId(id, year ,null, null, null);
				} else {
					ids.add(id);
					infos = personnelHrRecordService.findInfoByCreateOrgIds(ids, year, null, null, null);
				}
			} else {
				infos = personnelHrRecordService.findInfoByCreateOrgId(null, year ,null, null, null);
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = personnelHrRecordService.findInfoByCreateOrgId(id, null ,startDate, endDate, null);
					} else {
						ids.add(id);
						infos = personnelHrRecordService.findInfoByCreateOrgIds(ids, null,startDate, endDate, null);
					}
				} else {
					infos = personnelHrRecordService.findInfoByCreateOrgId(null, null ,startDate, endDate, null);
				}
			}
		}
		return infos;
	}



	/** 档案借阅情况
	 * @param id
	 * @return
	 */
	@RequestMapping("jieyue")
	public @ResponseBody List<Map<String, Object>> jieyue(String id) {
		List<Map<String, Object>> infos;
		if(id != null && !TOP_OFFICE_ID.equals(id)) {
			List<String> ids = officeService.findIdsByParentId(id);
			if(CollectionUtils.isEmpty(ids)) {
				infos = affairBorrowService.findInfoByCreateOrgId(id);
			} else {
				ids.add(id);
				infos = affairBorrowService.findInfoByCreateOrgIds(ids);
			}
		}else {
			infos = affairBorrowService.findInfoByCreateOrgId(null);
		}
		return infos;
	}
	
	/**警衔变动情况
	 * @param id
	 * @return
	 */
	@RequestMapping("jingxian")
	public @ResponseBody List<Map<String, Object>> jingxian(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = personnelPoliceRankService.findInfoByCreateOrgId(id, null ,null, null, month);
//						infos = affairPoliceRankService.findInfoByCreateOrgId(id, null ,null, null, month);
					} else {
						ids.add(id);
						infos = personnelPoliceRankService.findInfoByCreateOrgIds(ids, null,null, null, month);
//						infos = affairPoliceRankService.findInfoByCreateOrgIds(ids, null,null, null, month);
					}
				} else {
					infos = personnelPoliceRankService.findInfoByCreateOrgId(null, null ,null, null, month);
//					infos = affairPoliceRankService.findInfoByCreateOrgId(null, null ,null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if(id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if(CollectionUtils.isEmpty(ids)) {
					infos = personnelPoliceRankService.findInfoByCreateOrgId(id, year ,null, null, null);
//					infos = affairPoliceRankService.findInfoByCreateOrgId(id, year ,null, null, null);
				} else {
					ids.add(id);
					infos = personnelPoliceRankService.findInfoByCreateOrgIds(ids, year, null, null, null);
//					infos = affairPoliceRankService.findInfoByCreateOrgIds(ids, year, null, null, null);
				}
			} else {
				infos = personnelPoliceRankService.findInfoByCreateOrgId(null, year ,null, null, null);
//				infos = affairPoliceRankService.findInfoByCreateOrgId(null, year ,null, null, null);
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = personnelPoliceRankService.findInfoByCreateOrgId(id, null ,startDate, endDate, null);
//						infos = affairPoliceRankService.findInfoByCreateOrgId(id, null ,startDate, endDate, null);
					} else {
						ids.add(id);
						infos = personnelPoliceRankService.findInfoByCreateOrgIds(ids, null,startDate, endDate, null);
//						infos = affairPoliceRankService.findInfoByCreateOrgIds(ids, null,startDate, endDate, null);
					}
				} else {
					infos = personnelPoliceRankService.findInfoByCreateOrgId(null, null ,startDate, endDate, null);
//					infos = affairPoliceRankService.findInfoByCreateOrgId(null, null ,startDate, endDate, null);
				}
			}
		}
		return infos;
	}

	/** 专长情况
	 * @return
	 */
	@RequestMapping("specialityInfo")
	@ResponseBody
	public List<Map<String, Object>> specialityInfo(@RequestParam(required = false) String id, @RequestParam(required = false)String dateType,
											   @RequestParam(required = false)Integer year, @RequestParam(required = false)String dateStart,
											   @RequestParam(required = false)String dateEnd, @RequestParam(required = false)String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		infos = personnelSkillService.findSpecialityInfo(id);
		return infos;
	}

	/** 基层党组织
	 * @param id
	 * @return
	 */
	@RequestMapping("jiceng")
	public @ResponseBody List<Map<String, Object>> jiceng(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
        //1：月度 2：年度 3:时间段
        if("1".equals(dateType)){//月度
            if (month != null && month.length() > 0) {
                if(id != null && !TOP_OFFICE_ID.equals(id)) {
                    List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
                    if(CollectionUtils.isEmpty(ids)) {
                        infos = affairGeneralSituationService.findInfoById(id, null ,null, null, month);
                    } else {
                        ids.add(id);
                        infos = affairGeneralSituationService.findInfoByIds(ids, null, null, null, month);
                    }
                } else {
                    infos = affairGeneralSituationService.findInfoById(null,null, null, null, month);
                }
            }
        }else if("2".equals(dateType)){//年度
			if(id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
				if(CollectionUtils.isEmpty(ids)) {
					infos = affairGeneralSituationService.findInfoById(id, year ,null, null, null);
				} else {
					ids.add(id);
					infos = affairGeneralSituationService.findInfoByIds(ids, year, null, null, null);
				}
			} else {
				infos = affairGeneralSituationService.findInfoById(null, year, null, null, null);
			}
		}else{// 时间段
            Date startDate = DateUtils.parseDate(dateStart);
            Date endDate = DateUtils.parseDate(dateEnd);
            if (!(startDate ==null && endDate == null)) {
                if(id != null && !TOP_OFFICE_ID.equals(id)) {
                    List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
                    if(CollectionUtils.isEmpty(ids)) {
                        infos = affairGeneralSituationService.findInfoById(id, null ,startDate, endDate, null);
                    } else {
                        ids.add(id);
                        infos = affairGeneralSituationService.findInfoByIds(ids, null, startDate, endDate, null);
                    }
                } else {
                    infos = affairGeneralSituationService.findInfoById(null,null, startDate, endDate, null);
                }
            }
        }
		return infos;
	}
	
	/** 党员人数
	 * @param id
	 * @return
	 */
	@RequestMapping("renshu")
	public @ResponseBody List<Map<String, Object>> renshu(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
        //1：月度 2：年度 3:时间段
		if("1".equals(dateType)){//月度
            if (month != null && month.length() > 0) {
                if(id != null && !TOP_OFFICE_ID.equals(id)) {
                    List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
                    if(CollectionUtils.isEmpty(ids)) {
                        infos = affairPartyMemberService.findInfoByPartyBranchId(id, null ,null, null, month);
                    } else {
                        ids.add(id);
                        infos = affairPartyMemberService.findInfoByPartyBranchIds(ids, null ,null, null, month);
                    }
                } else {
                    infos = affairPartyMemberService.findInfoByPartyBranchId(null, null ,null, null, month);
                }
            }
        }else if("2".equals(dateType)){//年度
			if(id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
				if(CollectionUtils.isEmpty(ids)) {
					infos = affairPartyMemberService.findInfoByPartyBranchId(id, year, null, null, null);
				} else {
					ids.add(id);
					infos = affairPartyMemberService.findInfoByPartyBranchIds(ids, year,null, null, null);
				}
			} else {
				infos = affairPartyMemberService.findInfoByPartyBranchId(null, year, null, null, null);
			}
        }else {//时间段
            Date startDate = DateUtils.parseDate(dateStart);
            Date endDate = DateUtils.parseDate(dateEnd);
            if (!(startDate ==null && endDate == null)) {
                if(id != null && !TOP_OFFICE_ID.equals(id)) {
                    List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
                    if(CollectionUtils.isEmpty(ids)) {
                        infos = affairPartyMemberService.findInfoByPartyBranchId(id, null, startDate, endDate, null);
                    } else {
                        ids.add(id);
                        infos = affairPartyMemberService.findInfoByPartyBranchIds(ids, null,startDate, endDate, null);
                    }
                } else {
                    infos = affairPartyMemberService.findInfoByPartyBranchId(null, null, startDate, endDate, null);
                }
            }
        }
		return infos;
	}
	
	/** 帮扶救助情况
	 * @param id
	 * @return
	 */
	@RequestMapping("bangfu")
	public @ResponseBody Map<String, Object> bangfu(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infosZy = null;
		//Map<String, Object> infoZk = null;
        //Map<String, Object> infoZx = null;
		//1：月度 2：年度 3:时间段
		if("1".equals(dateType)){//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infosZy = affairZyInfoService.findInfoByCreateOrgId(id, null ,null, null, month);
						//infoZk = affairZkInfoService.findInfoByCreateOrgId(id, null ,null, null, month);
						//infoZx = affairZxInfoService.findInfoByCreateOrgId(id, null ,null, null, month);
					} else {
						ids.add(id);
						infosZy = affairZyInfoService.findInfoByCreateOrgIds(ids, null ,null, null, month);
						//infoZk = affairZkInfoService.findInfoByCreateOrgIds(ids, null ,null, null, month);
						//infoZx = affairZxInfoService.findInfoByCreateOrgIds(ids, null ,null, null, month);
					}
				} else {
					infosZy = affairZyInfoService.findInfoByCreateOrgId(null, null ,null, null, month);
					//infoZk = affairZkInfoService.findInfoByCreateOrgId(null, null ,null, null, month);
					//infoZx = affairZxInfoService.findInfoByCreateOrgId(null, null ,null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infosZy = affairZyInfoService.findInfoByCreateOrgId(id, year, null, null, null);
					//infoZk = affairZkInfoService.findInfoByCreateOrgId(id, year, null, null, null);
					//infoZx = affairZxInfoService.findInfoByCreateOrgId(id, year, null, null, null);
				} else {
					ids.add(id);
					infosZy = affairZyInfoService.findInfoByCreateOrgIds(ids, year, null, null, null);
					//infoZk = affairZkInfoService.findInfoByCreateOrgIds(ids, year, null, null, null);
					//infoZx = affairZxInfoService.findInfoByCreateOrgIds(ids, year, null, null, null);
				}
			} else {
				infosZy = affairZyInfoService.findInfoByCreateOrgId(null, year, null, null, null);
				//infoZk = affairZkInfoService.findInfoByCreateOrgId(null, year, null, null, null);
				//infoZx = affairZxInfoService.findInfoByCreateOrgId(null, year, null, null, null);
			}
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infosZy = affairZyInfoService.findInfoByCreateOrgId(id, null ,startDate, endDate, null);
						//infoZk = affairZkInfoService.findInfoByCreateOrgId(id, null ,startDate, endDate, null);
						//infoZx = affairZxInfoService.findInfoByCreateOrgId(id, null ,startDate, endDate, null);
					} else {
						ids.add(id);
						infosZy = affairZyInfoService.findInfoByCreateOrgIds(ids, null ,startDate, endDate, null);
						//infoZk = affairZkInfoService.findInfoByCreateOrgIds(ids, null ,startDate, endDate, null);
						//infoZx = affairZxInfoService.findInfoByCreateOrgIds(ids, null ,startDate, endDate, null);
					}
			}else {
					infosZy = affairZyInfoService.findInfoByCreateOrgId(null, null ,startDate, endDate, null);
					//infoZk = affairZkInfoService.findInfoByCreateOrgId(null, null ,startDate, endDate, null);
					//infoZx = affairZxInfoService.findInfoByCreateOrgId(null, null ,startDate, endDate, null);
				}
		}
	}
		List<Double> moneyData = new ArrayList<>();
		//moneyData.add(Double.valueOf(infosZy.get("sum").toString()));
		//moneyData.add(Double.valueOf(infoZk.get("sum").toString()));
		//moneyData.add(Double.valueOf(infoZx.get("sum").toString()));
		List<Double> countData = new ArrayList<>();
		//countData.add(Double.valueOf(infosZy.get("count").toString()));
		//countData.add(Double.valueOf(infoZk.get("count").toString()));
		//countData.add(Double.valueOf(infoZx.get("count").toString()));
		List<String> labelData = new ArrayList<>();
		Map<String, Object> result = new HashMap<>();
		if(infosZy != null && infosZy.size() > 0){
			for (Map<String, Object> m:infosZy) {
				moneyData.add(Double.valueOf(m.get("sum").toString()));
				countData.add(Double.valueOf(m.get("count").toString()));
				labelData.add(m.get("label").toString());
			}
		}
		result.put("moneyData", moneyData);
		result.put("countData", countData);
		result.put("labelData", labelData);
		return result;
	}
	@RequestMapping("studyAid")
	@ResponseBody
	public List<Map<String, String>> studyAid(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Date startDate = DateUtils.parseDate(dateStart);
		Date endDate = DateUtils.parseDate(dateEnd);
		if (dateType.equals("1")){//月度
			startDate=null;
			endDate=null;
			year=null;
		}else if (dateType.equals("3")){//时间段
			month=null;

			year=null;
		}else {//年度
			startDate=null;
			endDate=null;
			month=null;
		}
		List<Map<String, String>> list = affairZxInfoService.countstudyAid(id,year,startDate,endDate,month);
		return list;
	}

    /** 理论学习情况
     * @param id
     * @return
     */
    /*需求变动  不在使用*/
    @RequestMapping("xuexi")
	public @ResponseBody Map<String, Object> xuexi(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
        Map<String, Object> dwStudy;
        Map<String, Object> dzbStudy;
		Map<String, Object> result = new HashMap<>();
		//1：月度 2：年度 3:时间段
		if("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
                if (id != null && !TOP_OFFICE_ID.equals(id)) {
                    List<String> ids = officeService.findIdsByParentId(id);
                    if (CollectionUtils.isEmpty(ids)) {
                        dwStudy = affairGroupStudyService.findInfoByCreateOrgId(id, null, null, null, month);
                        dzbStudy = affairFocusStudyService.findInfoByCreateOrgId(id, null, null, null, month);
                    } else {
                        ids.add(id);
                        dwStudy = affairGroupStudyService.findInfoByCreateOrgIds(ids, null, null, null, month);
                        dzbStudy = affairFocusStudyService.findInfoByCreateOrgIds(ids, null, null, null, month);
                    }
                } else {
                    dwStudy = affairGroupStudyService.findInfoByCreateOrgId(null, null, null, null, month);
                    dzbStudy = affairFocusStudyService.findInfoByCreateOrgId(null, null, null, null, month);
                }
                List<Double> sumData = new ArrayList<>();
                sumData.add(Double.valueOf(dwStudy.get("count").toString()));
                sumData.add(Double.valueOf(dzbStudy.get("count").toString()));
                result.put("sumData", sumData);
            }
		}else if("2".equals(dateType)){//年度
                if (id != null && !TOP_OFFICE_ID.equals(id)) {
                    List<String> ids = officeService.findIdsByParentId(id);
                    if (CollectionUtils.isEmpty(ids)) {
                        dwStudy = affairGroupStudyService.findInfoByCreateOrgId(id, year, null, null, null);
                        dzbStudy = affairFocusStudyService.findInfoByCreateOrgId(id, year, null, null, null);
                    } else {
                        ids.add(id);
                        dwStudy = affairGroupStudyService.findInfoByCreateOrgIds(ids, year, null, null, null);
                        dzbStudy = affairFocusStudyService.findInfoByCreateOrgIds(ids, year, null, null, null);
                    }
                } else {
                    dwStudy = affairGroupStudyService.findInfoByCreateOrgId(null, year, null, null, null);
                    dzbStudy = affairFocusStudyService.findInfoByCreateOrgId(null, year, null, null, null);
                }
                List<Double> sumData = new ArrayList<>();
                sumData.add(Double.valueOf(dwStudy.get("count").toString()));
                sumData.add(Double.valueOf(dzbStudy.get("count").toString()));
                result.put("sumData", sumData);
		}else{//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						dwStudy = affairGroupStudyService.findInfoByCreateOrgId(id, null, startDate, endDate,null);
						dzbStudy = affairFocusStudyService.findInfoByCreateOrgId(id, null, startDate, endDate,null);
					} else {
						ids.add(id);
						dwStudy = affairGroupStudyService.findInfoByCreateOrgIds(ids, null, startDate, endDate,null);
						dzbStudy = affairFocusStudyService.findInfoByCreateOrgIds(ids, null, startDate, endDate,null);
					}
				} else {
					dwStudy = affairGroupStudyService.findInfoByCreateOrgId(null, null, startDate, endDate,null);
					dzbStudy = affairFocusStudyService.findInfoByCreateOrgId(null, null, startDate, endDate,null);
				}
				List<Double> sumData = new ArrayList<>();
				sumData.add(Double.valueOf(dwStudy.get("count").toString()));
				sumData.add(Double.valueOf(dzbStudy.get("count").toString()));
				result.put("sumData", sumData);
			}
		}
		return result;
	}

	@RequestMapping("partyStudy")
	@ResponseBody
	public List<Map<String,String>> countPartyStudy(String dateType, Integer year, String dateStart, String dateEnd, String month){
		List<Map<String, String>> list = affairGroupStudyService.countPartyStudy(dateType, year, dateStart, dateEnd, month);
		return list;
	}

	@RequestMapping("typicalPerson")
	@ResponseBody
	public List<Map<String,String>> countTypicalPerson( String dateType, Integer year, String dateStart, String dateEnd, String month){
		List<Map<String, String>> list  = affairTypicalPersonService.countTypicalPerson(dateType, year, dateStart, dateEnd, month);
    	return list;
	}
	@RequestMapping("typicalTeam")
	@ResponseBody
	public List<Map<String,String>> countTypicalTeam( String dateType, Integer year, String dateStart, String dateEnd, String month){
		List<Map<String, String>> list  = affairTypicalTeamService.countTypicalTeam(dateType, year, dateStart, dateEnd, month);
    	return list;
	}
	@RequestMapping("readBookActivity")
	@ResponseBody
	public List<Map<String,String>> countReadBookActivity( String dateType, Integer year, String dateStart, String dateEnd, String month){
		List<Map<String, String>> list  = affairActiveService.countReadBookActivity(dateType, year, dateStart, dateEnd, month);
    	return list;
	}



	/**
	 * 组织干部情况
	 * 
	 * @return
	 */
	@RequestMapping("leader")
	public ModelAndView leader(String id) {
		ModelAndView view = new ModelAndView();
		if(StringUtils.isBlank(id)){
			id = UserUtils.getUser().getOffice().getId();
		}
		view.addObject("id", id);
		view.addObject("dateType", "1");
		view.addObject("year", DateUtils.getYear());
		view.addObject("month", DateUtils.getYear()+"-"+DateUtils.getMonth());
		view.setViewName("modules/charts/leader");
		return view;
	}

	@ResponseBody
	@RequestMapping("countFullTimeEducation")
	public List<Map<String,String>> countFullTimeEducation(String id,String dateType,Integer year, String dateStart, String dateEnd, String month ){
		List<Map<String,String>> list= personnelXueliService.countFullTimeEducation(id,dateType,year,dateStart,dateEnd,month);
		return list;
	}
	@ResponseBody
	@RequestMapping("countMaxEducation")
	public List<Map<String,String>> countMaxEducation(String id,String dateType,Integer year, String dateStart, String dateEnd, String month ){
		List<Map<String,String>> list= personnelXueliService.countMaxEducation(id,dateType,year,dateStart,dateEnd,month);
		return list;
	}

	@ResponseBody
	@RequestMapping("countPersonnelAge")
	public List<Map<String,String>> countPersonnelAge(String id,String ageType,Integer age, Integer startAge, Integer endAge, String month ){
		List<Map<String,String>> list= personnelBaseService.countPersonnelAge(id,ageType,age,startAge,endAge,month);
		return list;
	}

	@ResponseBody
	@RequestMapping("countPoliceRank")
	public List<Map<String,String>> countPoliceRank(String id,String dateType,Integer year, String dateStart, String dateEnd, String month ){
		List<Map<String,String>> list= personnelPoliceRankService.countPoliceRank(id,dateType,year,dateStart,dateEnd,month);
		return list;
	}
	@ResponseBody
	@RequestMapping("countPersonnelJob")
	public List<Map<String,String>> countPersonnelJob(String id,String dateType,Integer year, String dateStart, String dateEnd, String month ){
		List<Map<String,String>> list= personnelJobService.countPersonnelJob(id,dateType,year,dateStart,dateEnd,month);
		return list;
	}

	@ResponseBody
	@RequestMapping("countNation")
	public List<Map<String,String>> countNation(String id,String dateType,Integer year, String dateStart, String dateEnd, String month ){
		List<Map<String,String>> list= personnelBaseService.countNation(id,dateType,year,dateStart,dateEnd,month);
		return list;
	}

	@ResponseBody
	@RequestMapping("countPersonnelCategory")
	public List<Map<String,String>> countPersonnelCategory(String id,String dateType,Integer year, String dateStart, String dateEnd, String month ){
		List<Map<String,String>> list= personnelBaseService.countPersonnelCategory(id,dateType,year,dateStart,dateEnd,month);
		return list;
	}

	@ResponseBody
	@RequestMapping("countWorkYear")
	public List<Map<String,String>> countWorkYear(String id,String dateType,Integer year, String dateStart, String dateEnd, String month ){
		List<Map<String,String>> list= personnelBaseService.countWorkYear(id,dateType,year,dateStart,dateEnd,month);
		return list;
	}

	/**
	 * 党建工作情况
	 * 
	 * @return
	 */
	@RequestMapping("partyIndex")
	public ModelAndView party(String id) {
		ModelAndView view = new ModelAndView();
		view.addObject("id", id);
		view.addObject("dateType", "1");
		view.addObject("year", DateUtils.getYear());
		view.addObject("month", DateUtils.getYear()+"-"+DateUtils.getMonth());
		view.setViewName("modules/charts/party");
		return view;
	}


	/**
	 *
	 * @param dateType		日期查询方式
	 * @param year			1、年份
	 * @param dateStart		2、时间段
	 * @param dateEnd		2、时间段
	 * @param month			3、月份
	 * @return
	 */
	@RequestMapping("partyNum")
	@ResponseBody
	public List<Map<String,String>> partyNum(String dateType,Integer year, String dateStart, String dateEnd, String month ){
		switch (dateType){
			case "1":		//月度
				year=null;
				dateStart=null;
				dateEnd=null;
				break;
			case "2":		//年度
				month=null;
				dateStart=null;
				dateEnd=null;
				break;
			case "3":		//时间段
				year=null;
				month=null;
				break;
				default:
					break;
		}
		//全部标签
		String[] labelArray = {"全局党员数","正式党员","预备党员","入党积极分子","入党发展对象","党代表","在职","离退","女","少数民族"};
		//转换为list
		List<String> labelList = Arrays.asList(labelArray);
		Date startDate = DateUtils.parseDate(dateStart);
		Date endDate = DateUtils.parseDate(dateEnd);
		List<Map<String, String>> list = affairPartyMemberService.countPartyMember(year,startDate,endDate,month);
		//查询到的所有标签
		List<String> label= new ArrayList<>();
		list.forEach(stringMap -> {
			label.add(stringMap.get("label"));
		});
		//查找label中不存在的标签
		labelList.forEach(s -> {
			if (!label.contains(s)){
				Map<String,String> map = new HashMap<>();
				map.put("label",s);
				map.put("num","0");
				list.add(map);
			}
		});
		/*if (label.equals("离退")){
			Map<String,String> map = new HashMap<>();
			map.put("label","离退");
			map.put("num","0");
			list.add(map);
		}else {

		}*/
		return list;
	}


	@RequestMapping("partyMemberDetail")
	public String partyNumDetail(AffairPartyMember partyMember,HttpServletRequest request,HttpServletResponse response,Model model){
		//借用name属性  查看的明细类型
		String flag = partyMember.getName();
		String type=partyMember.getDateType();
		partyMember.setStartDate(DateUtils.parseDate(partyMember.getDateStart()));
		partyMember.setEndDate(DateUtils.parseDate(partyMember.getDateEnd()));
		switch (type){
			case "1":		//月度
				partyMember.setYear(null);
				partyMember.setStartDate(null);
				partyMember.setEndDate(null);
				break;
			case "2":		//年度
				partyMember.setMonth(null);
				partyMember.setStartDate(null);
				partyMember.setEndDate(null);
				break;
			case "3":		//时间段
				partyMember.setYear(null);
				partyMember.setMonth(null);
				break;
			default:
				break;
		}
		model.addAttribute("year",partyMember.getYear());
		model.addAttribute("month",partyMember.getMonth());
		model.addAttribute("startDate",partyMember.getStartDate());
		model.addAttribute("endDate",partyMember.getEndDate());
		model.addAttribute("type",partyMember.getDateType());
		model.addAttribute("flag",partyMember.getName());
//		if (flag.equals("正式党员") || flag.equals("预备党员")){
			Page<AffairPartyMember> page =affairPartyMemberService.findPartyMemberPage(new Page<AffairPartyMember>(request,response),partyMember);
			model.addAttribute("page",page);
			return "modules/charts/chartsPartyMemberList";
//		}
		/*if (flag.equals("入党积极分子")){
			Page<AffairActivist> page =affairActivistService.findActivistPage(new Page<AffairActivist>(request,response),partyMember);
			model.addAttribute("page",page);
			return "modules/affair/affairActivistList";
		}
		if (flag.equals("入党发展对象")){
			Page<AffairDevelopObject> page =affairDevelopObjectService.findDevelopPage(new Page<AffairDevelopObject>(request,response),partyMember);
			model.addAttribute("page",page);
			return "modules/affair/affairDevelopObjectList";
		}
		if (flag.equals("党代表")){
			Page<AffairOtherPartyRepresentative> page = affairOtherPartyRepresentativeService.findRepresentativePage(new Page<AffairOtherPartyRepresentative>(request,response),chartsVo);
			model.addAttribute("page",page);
			return "modules/affair/affairOtherPartyRepresentativeList";
		}*/

//		return null;
	}

	@RequestMapping("election")
	@ResponseBody
	public List<Map<String,String>> countElection(String dateType,Integer year, String dateStart, String dateEnd, String month ){
		List<Map<String,String>> list = affairElectionService.countElection(dateType,year, dateStart, dateEnd, month);
		return list;
	}



	/** 两学一做学习教育
	 * @param id
	 * @return
	 */
	@RequestMapping("liangxueyizuo")
	public @ResponseBody List<Map<String, Object>> liangxueyizuo(String id, String dateType, Integer year, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度
		if("1".equals(dateType)){//月度
			if(month != null && month.length() > 0){
				String y =month.substring(0,4);
				String m =month.substring(month.lastIndexOf("-")+1);
				if("0".equals(m.substring(0,1))){
					m = m.substring(1);
				}
				String newMonth = y+m;
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = affairTwoOneService.findInfoByCreateOrgId(id, null, newMonth);
					} else {
						ids.add(id);
						infos = affairTwoOneService.findInfoByCreateOrgIds(ids,null, newMonth);
					}

				} else {
					infos = affairTwoOneService.findInfoByCreateOrgId(null, null ,newMonth);
				}
			}
		}else{
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if(CollectionUtils.isEmpty(ids)) {
					infos = affairTwoOneService.findInfoByCreateOrgId(id, year , null);
				} else {
					ids.add(id);
					infos = affairTwoOneService.findInfoByCreateOrgIds(ids, year, null);
				}

			} else {
				infos = affairTwoOneService.findInfoByCreateOrgId(null, year ,null);
			}
		}
		return infos;
	}

	/** 党建-组织奖惩信息
	 * @param id
	 * @return
	 */
	@RequestMapping("orgRewardPunish")
	public @ResponseBody List<Map<String, Object>> orgRewardPunish(String id, String dateType, Integer year, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度
		if("1".equals(dateType)){//月度
			if(month != null && month.length() > 0){
				String y =month.substring(0,4);
				String m =month.substring(month.lastIndexOf("-")+1);
				if("0".equals(m.substring(0,1))){
					m = m.substring(1);
				}
				String newMonth = y+m;
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = affairOrgRewardPunishService.findInfoByCreateOrgId(id, null ,newMonth);
					} else {
						ids.add(id);
						infos = affairOrgRewardPunishService.findInfoByCreateOrgIds(ids, null ,newMonth);
					}

				} else {
					infos = affairOrgRewardPunishService.findInfoByCreateOrgId(null, null, newMonth);
				}
			}
		}else{
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if(CollectionUtils.isEmpty(ids)) {
					infos = affairOrgRewardPunishService.findInfoByCreateOrgId(id, year, null);
				} else {
					ids.add(id);
					infos = affairOrgRewardPunishService.findInfoByCreateOrgIds(ids, year, null);
				}

			} else {
				infos = affairOrgRewardPunishService.findInfoByCreateOrgId(null, year, null);
			}
		}
		return infos;
	}

	/**
	 * 绩效考核情况
	 * @return
	 */
	@RequestMapping("performance")
	public ModelAndView performance(String id) {
		//绩效考评 统计分析 单位
		ModelAndView view = new ModelAndView();
		if(StringUtils.isBlank(id)){
			id = UserUtils.getUser().getOffice().getId();
		}
		Office office = officeService.get(id);
		view.addObject("unitName", office.getName());
		view.addObject("id" ,id);
		String unitType = null;
		switch (id){
			case "1":
				//南宁铁路公安局
				unitType = "isJu";
				break;
			case "34":
				//南宁处
				unitType = "isNNC";
				break;
			case "95":
				//柳州处
				unitType = "isLZC";
				break;
			case "156":
				//北海处
				unitType = "isBHC";
				break;
			default:
				break;
		}
		if(StringUtils.isBlank(unitType)){
			if(office.getParentIds().contains(",34,")){
				//南宁处
				if(office.getName().contains("派出所")
						&&!(office.getId().equals("121")||office.getName().equals("南宁处铁路局机关派出所")&&office.getCode().equals("800901740000"))){
					unitType = "isNNCpcs";
				}else{
					unitType = "isNNCjg";
				}
			}else if(office.getParentIds().contains(",95,")){
				//柳州处
				if(office.getName().contains("派出所")){
					unitType = "isLZCpcs";
				}else{
					unitType = "isLZCjg";
				}
			}else if(office.getParentIds().contains(",156,")){
				//北海处
				if(office.getName().contains("派出所")){
					unitType = "isBHCpcs";
				}else{
					unitType = "isBHCjg";
				}
			}else{
				//局机关
				unitType = "isJJG";
			}
		}
		List<Dict> examWeights = DictUtils.getDictList("exam_weigths");
		view.addObject("unitType",unitType);//选择单位类型
		view.addObject("dateType", "1");
        view.addObject("year",DateUtils.getYear());
        view.addObject("month", DateUtils.getYear()+DateUtils.getMonth());
		view.addObject("examWeigths",examWeights);
		view.setViewName("modules/charts/exam");
		return view;
	}

    /** 绩效考评情况
     * @param id
	 * @param workName 10月28新加参数工作项 workName kevin.jia
     * @return
     */
    @RequestMapping("exam")
    public @ResponseBody
	List<Map<String, Object>> exam(String id, String dateType, String month, String flag, String dateFlag,String workName,String startScore,String endScore) {
        List<Map<String, Object>> examMap = null;
		Double startScoreD = null;
		Double endScoreD = null;
        if (month != null && month.length() > 0) {
        	// 10.29 考评类型 flag 1  3  增加workName 工作项条件
			if ("1".equals(dateFlag)) {
				if ("1".equals(flag)){
					//10.28  局考处  sql增加workName 工作项条件 kevin.jia
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							examMap = examUnitAllPublicService.findInfoByUnitId(id, month,workName);
						} else {
							ids.add(id);
							examMap = examUnitAllPublicService.findInfoByUnitIds(ids, month,workName);
						}
					} else {
						examMap = examUnitAllPublicService.findInfoByUnitId(null, month,workName);
					}
				}else if ("2".equals(flag)){
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						String  userId =officeService.findUserIdByCode(officeService.findCodeById(id));
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							examMap = examUnitAllPublicService.findJkjInfoByUnitId(userId, month);
						} else {
							ids.add(userId);
							examMap = examUnitAllPublicService.findJkjInfoByUnitIds(ids, month);
						}
					} else {
						examMap = examUnitAllPublicService.findJkjInfoByUnitId(null, month);
					}
				}else if ("3".equals(flag)){
					//10.29  处考队所  sql增加workName 工作项条件 kevin.jia
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						String  userId =officeService.findUserIdByCode(officeService.findCodeById(id));
						if (CollectionUtils.isEmpty(ids)) {
							examMap = examUnitAllPublicService.findCkdsInfoByUnitId(userId, month,workName);
						} else {
							ids.add(id);
							examMap = examUnitAllPublicService.findCkdsInfoByUnitIds(ids, month,workName);
						}
					} else {
						examMap = examUnitAllPublicService.findCkdsInfoByUnitId(null, month,workName);
					}
				}else if("4".equals(flag)){
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						String  userId =officeService.findUserIdByCode(officeService.findCodeById(id));
						if (CollectionUtils.isEmpty(ids)) {
							examMap = examUnitAllPublicService.findCkcjgInfoByUnitId(userId, month);
						} else {
							ids.add(id);
							examMap = examUnitAllPublicService.findCkcjgInfoByUnitIds(ids, month);
						}
					} else {
						examMap = examUnitAllPublicService.findCkcjgInfoByUnitId(null, month);
					}
				}else if ("7".equals(flag)){
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							examMap = examLdScoreMonthService.findInfoByUnitId(id, month);
						} else {
							ids.add(id);
							examMap = examLdScoreMonthService.findInfoByUnitIds(ids, month);
						}
					} else {
						examMap = examLdScoreMonthService.findInfoByUnitId(null, month);
					}
				}
			}
	}
        return examMap;
    }
/*
	*//** 绩效考评情况
	 * @param id
	 * @return
	 */
	/*
	@RequestMapping("examPerson")
	public List<Map<String, Object>> examPerson(String id, String dateType, String month, String flag, String dateFlag) {
		List<Map<String, Object>> examMap = null;
		if (month != null && month.length() > 0) {
			if ("1".equals(dateFlag)) {
				if ("1".equals(flag)){
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							examMap = examUnitAllPublicService.findInfoByUnitId(id, month);
						} else {
							ids.add(id);
							examMap = examUnitAllPublicService.findInfoByUnitIds(ids, month);
						}
					} else {
						examMap = examUnitAllPublicService.findInfoByUnitId(null, month);
					}
				}else if ("2".equals(flag)){
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							examMap = examUnitAllPublicService.findJkjInfoByUnitId(id, month);
						} else {
							ids.add(id);
							examMap = examUnitAllPublicService.findJkjInfoByUnitIds(ids, month);
						}
					} else {
						examMap = examUnitAllPublicService.findJkjInfoByUnitId(null, month);
					}
				}else if ("3".equals(flag)){
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							examMap = examUnitAllPublicService.findCkdsInfoByUnitId(id, month);
						} else {
							ids.add(id);
							examMap = examUnitAllPublicService.findCkdsInfoByUnitIds(ids, month);
						}
					} else {
						examMap = examUnitAllPublicService.findCkdsInfoByUnitId(null, month);
					}
				}else if("4".equals(flag)){
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							examMap = examUnitAllPublicService.findCkcjgInfoByUnitId(id, month);
						} else {
							ids.add(id);
							examMap = examUnitAllPublicService.findCkcjgInfoByUnitIds(ids, month);
						}
					} else {
						examMap = examUnitAllPublicService.findCkcjgInfoByUnitId(null, month);
					}
				}else if ("7".equals(flag)){
					if (id != null && !TOP_OFFICE_ID.equals(id)) {
						List<String> ids = officeService.findIdsByParentId(id);
						if (CollectionUtils.isEmpty(ids)) {
							examMap = examLdScoreMonthService.findInfoByUnitId(id, month);
						} else {
							ids.add(id);
							examMap = examLdScoreMonthService.findInfoByUnitIds(ids, month);
						}
					} else {
						examMap = examLdScoreMonthService.findInfoByUnitId(null, month);
					}
				}
			}
		}

		return "";
	}*/

	/** 绩效年度考评情况
	 * @param id
	 * @param workName 10月29新加 参数工作项 workName(局考处，处考队所采用) kevin.jia
	 * @return
	 */
	@RequestMapping("examYear")
	public @ResponseBody
	List<Map<String, Object>> examYear(String id, String dateType, String year, String flag, String dateFlag,String workName,String startScore,String endScore) {
		List<Map<String, Object>> examMap = null;
		if ("2".equals(dateFlag)){
			// 10.29 考评类型 flag 1  3  增加workName 工作项条件
			if ("1".equals(flag)){
				//10.29  局考处  sql增加workName 工作项条件 kevin.jia
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						examMap = examUnitAllPublicYearService.findInfoByUnitId(id, year,workName);
					} else {
						ids.add(id);
						examMap = examUnitAllPublicYearService.findInfoByUnitIds(ids, year,workName);
					}
				} else {
					examMap = examUnitAllPublicYearService.findInfoByUnitId(null, year,workName);
				}
			}else if ("2".equals(flag)){
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					String  userId =officeService.findUserIdByCode(officeService.findCodeById(id));
					if (CollectionUtils.isEmpty(ids)) {
						examMap = examUnitAllPublicYearService.findJkjInfoByUnitId(userId, year);
					} else {
						ids.add(id);
						examMap = examUnitAllPublicYearService.findJkjInfoByUnitIds(ids, year);
					}
				} else {
					examMap = examUnitAllPublicYearService.findJkjInfoByUnitId(null, year);
				}
			}else if ("3".equals(flag)){
				//处考队所  sql增加workName 工作项条件 kevin.jia
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					String  userId =officeService.findUserIdByCode(officeService.findCodeById(id));
					if (CollectionUtils.isEmpty(ids)) {
						examMap = examUnitAllPublicYearService.findCkdsInfoByUnitId(userId, year,workName);
					} else {
						ids.add(id);
						examMap = examUnitAllPublicYearService.findCkdsInfoByUnitIds(ids, year,workName);
					}
				} else {
					examMap = examUnitAllPublicYearService.findCkdsInfoByUnitId(null, year,workName);
				}
			}else {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					String  userId =officeService.findUserIdByCode(officeService.findCodeById(id));
					if (CollectionUtils.isEmpty(ids)) {
						examMap = examUnitAllPublicYearService.findCkcjgInfoByUnitId(userId, year);
					} else {
						ids.add(id);
						examMap = examUnitAllPublicYearService.findCkcjgInfoByUnitIds(ids, year);
					}
				} else {
					examMap = examUnitAllPublicYearService.findCkcjgInfoByUnitId(null, year);
				}
			}
		}
		return examMap;
	}


    /** 领导绩效考评情况
     * @param id
     * @return
     *//*
    @RequestMapping("ldExam")
    public @ResponseBody
    List<Map<String, Object>> ldExam(String id, String dateType, Integer year, String month) {
        List<Map<String, Object>> examMap = null;
        if (month != null && month.length() > 0) {
            String y = month.substring(0, 4);
            String m = month.substring(month.lastIndexOf("-") + 1);
            if("0".equals(m.substring(0,1))){
                m = m.substring(1);
            }
            String newMonth = y + m;
            if(id != null && !TOP_OFFICE_ID.equals(id)) {
                List<String> ids = officeService.findIdsByParentId(id);
                if(CollectionUtils.isEmpty(ids)) {
                    examMap = examLdScoreMonthService.findInfoByUnitId(id, null ,newMonth);
                } else {
                    ids.add(id);
                    examMap = examLdScoreMonthService.findInfoByUnitIds(ids, null,newMonth);
                }
            } else {
                examMap = examLdScoreMonthService.findInfoByUnitId(null,null ,newMonth);
            }
        }else {
            if(id != null && !TOP_OFFICE_ID.equals(id)) {
                List<String> ids = officeService.findIdsByParentId(id);
                if(CollectionUtils.isEmpty(ids)) {
                    examMap = examLdScoreMonthService.findInfoByUnitId(id, year ,null);
                } else {
                    ids.add(id);
                    examMap = examLdScoreMonthService.findInfoByUnitIds(ids, year, null);
                }
            } else {
                examMap = examLdScoreMonthService.findInfoByUnitId(null, year, null);
            }
        }
        return examMap;
    }*/

	/**
	 * 民主（组织）生活会
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("lifeMeet")
	public @ResponseBody Map<String, Long> lifeMeet(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Long> infos = null;
		/*全部传到mapping sql中 值不是空就查询*/
		Date startDate = DateUtils.parseDate(dateStart);
		Date endDate = DateUtils.parseDate(dateEnd);
		infos = affairLifeMeetService.findInfoByPartyBranchId(id ,year, startDate, endDate,month);

		//1：月度 2：年度 3:时间段
		/*看不懂 都给注释了  组织树也不需要了*/
		if("1".equals(dateType)){//月度
			if (month != null && month.length() > 0) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = affairLifeMeetService.findInfoByPartyBranchId(id, null ,null, null, month);
					} else {
						//ids.add(id);父级不add
//						infos = affairLifeMeetService.findInfoByPartyBranchIds(ids, null ,null, null, month);
					}
				} else {
					infos = affairLifeMeetService.findInfoByPartyBranchId(null, null ,null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if(id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
				if(CollectionUtils.isEmpty(ids)) {
					infos = affairLifeMeetService.findInfoByPartyBranchId(id, year, null, null, null);
				} else {
					//ids.add(id);父级不add
//					infos = affairLifeMeetService.findInfoByPartyBranchIds(ids, year,null, null, null);
				}
			} else {
				infos = affairLifeMeetService.findInfoByPartyBranchId(null, year, null, null, null);
			}
		}else {//时间段
//			Date startDate = DateUtils.parseDate(dateStart);
//			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = affairLifeMeetService.findInfoByPartyBranchId(id, null, startDate, endDate, null);
					} else {
						//ids.add(id);父级不add
//						infos = affairLifeMeetService.findInfoByPartyBranchIds(ids, null,startDate, endDate, null);
					}
				} else {
					infos = affairLifeMeetService.findInfoByPartyBranchId(null, null, startDate, endDate, null);
				}
			}
		}
		Long i =infos.get("sum")-infos.get("done");
		infos.remove("sum");
		infos.put("unDone",i);
		return infos;
	}

	/**
	 * 民主（组织）生活会明细
	 * @param label		完成未完成
	 * @param request
	 * @param response
	 * @param model
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping(value = "lifeMeet/detail")
	public String lifeMeetDetail(String label, HttpServletRequest request, HttpServletResponse response, Model model,
								 Integer year, String dateStart, String dateEnd, String month,String dateType){
		AffairGeneralSituation affairGeneralSituation = new AffairGeneralSituation();
		if ("1".equals(dateType)){
			//使用党组织联系人进行按月查询
			affairGeneralSituation.setContactor(month);
		}else if ("2".equals(dateType)){
			//借用党组织字段
			//使用党组织成员数按着年查询
			affairGeneralSituation.setNum(year);
		}else {
			//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			affairGeneralSituation.setStartDate(startDate);
			affairGeneralSituation.setEndDate(endDate);
		}

		if (!StringUtils.isEmpty(label) && label.equals("完成")){
			//查询完成民主生活会的党组织
			affairGeneralSituation.setPartyOrganization("done");
		}else {
			//查询未完成民主生活会的党组织
			affairGeneralSituation.setPartyOrganization("unDone");
		}
		Page<AffairGeneralSituation> page=affairGeneralSituationService.findLifeMeetPage(new Page<AffairGeneralSituation>(request,response),affairGeneralSituation);
		model.addAttribute("page",page);
		//分页使用
		model.addAttribute("label",label);
		model.addAttribute("year",year);
		model.addAttribute("dateStart",dateStart);
		model.addAttribute("dateEnd",dateEnd);
		model.addAttribute("month",month);
		model.addAttribute("dateType",dateType);
		return "modules/charts/chartsGeneralSituationList";
	}

	/**
	 * 党书记述职测评完成情况
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	/*修改参数类型 便于添加不存在的label*/
	@RequestMapping("assessColumn")
	public @ResponseBody List<Map<String, String>> assessColumn(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, String>> pieInfos = null;
		//1：月度 2：年度 3:时间段
		if("1".equals(dateType)){//月度
			if (month != null && month.length() > 0) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
					if(CollectionUtils.isEmpty(ids)) {
						pieInfos = affairAssessService.findColumnInfoByPartyBranchId(id, null ,null, null, month);
					} else {
						ids.add(id);
						pieInfos = affairAssessService.findColumnInfoByPartyBranchIds(ids, null ,null, null, month);
					}
				} else {
					pieInfos = affairAssessService.findColumnInfoByPartyBranchId(null, null ,null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if(id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
				if(CollectionUtils.isEmpty(ids)) {
					pieInfos = affairAssessService.findColumnInfoByPartyBranchId(id, year, null, null, null);
				} else {
					ids.add(id);
					pieInfos = affairAssessService.findColumnInfoByPartyBranchIds(ids, year,null, null, null);
				}
			} else {
				pieInfos = affairAssessService.findColumnInfoByPartyBranchId(null, year, null, null, null);
			}
		}else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
					if(CollectionUtils.isEmpty(ids)) {
						pieInfos = affairAssessService.findColumnInfoByPartyBranchId(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						pieInfos = affairAssessService.findColumnInfoByPartyBranchIds(ids, null,startDate, endDate, null);
					}
				} else {
					pieInfos = affairAssessService.findColumnInfoByPartyBranchId(null, null, startDate, endDate, null);
				}
			}
		}
		List<Dict> dicts = DictUtils.getDictList("affair_assess_grade");
		ChartLabelUtils.fillLabel(pieInfos,dicts);
		return pieInfos;
	}

	/**
	 * 党书记述职测评等级
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("assessPie")
	public @ResponseBody Map<String, Long> assessPie(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Long> pieInfos = null;
//		Date startDate = DateUtils.parseDate(dateStart);
//		Date endDate = DateUtils.parseDate(dateEnd);
//		pieInfos = affairAssessService.findPieInfoByPartyBranchId(null, year, startDate, endDate, month);

		//1：月度 2：年度 3:时间段
		if("1".equals(dateType)){//月度
			if (month != null && month.length() > 0) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
					if(CollectionUtils.isEmpty(ids)) {
						pieInfos = affairAssessService.findPieInfoByPartyBranchId(id, null ,null, null, month);
					} else {
						ids.add(id);
						pieInfos = affairAssessService.findPieInfoByPartyBranchId(id, null ,null, null, month);
					}
				} else {
					pieInfos = affairAssessService.findPieInfoByPartyBranchId(null, null ,null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if(id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
				if(CollectionUtils.isEmpty(ids)) {
					pieInfos = affairAssessService.findPieInfoByPartyBranchId(id, year, null, null, null);
				} else {
					ids.add(id);
					pieInfos = affairAssessService.findPieInfoByPartyBranchId(id, year,null, null, null);
				}
			} else {
				pieInfos = affairAssessService.findPieInfoByPartyBranchId(null, year, null, null, null);
			}
		}else {//时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
					if(CollectionUtils.isEmpty(ids)) {
						pieInfos = affairAssessService.findPieInfoByPartyBranchId(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						pieInfos = affairAssessService.findPieInfoByPartyBranchId(id, null,startDate, endDate, null);
					}
				} else {
					pieInfos = affairAssessService.findPieInfoByPartyBranchId(null, null, startDate, endDate, null);
				}
			}
		}
		Long i = pieInfos.get("sum")-pieInfos.get("num");
		pieInfos.remove("sum");
		pieInfos.put("unDone",i);

		return pieInfos;
	}

	@RequestMapping("assessPie/detail")
	public String assessPieDetail(AffairAssess affairAssess,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<AffairAssess> page=affairAssessService.findPage(new Page<AffairAssess>(request,response),affairAssess);
		model.addAttribute("page",page);
		return "modules/charts/chartsAffairAssessList";
	}
	/**
	 * 统计党员名册中的人员类别人数（预备党员、正式党员）
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("partyMemberCategory")
	public @ResponseBody List<Map<String, Object>> partyMemberCategory(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if("1".equals(dateType)){//月度
			if (month != null && month.length() > 0) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = affairPartyMemberService.findCategoryInfoById(id, null ,null, null, month);
					} else {
						ids.add(id);
						infos = affairPartyMemberService.findCategoryInfoByIds(ids, null, null, null, month);
					}
				} else {
					infos = affairPartyMemberService.findCategoryInfoById(null,null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if(id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
				if(CollectionUtils.isEmpty(ids)) {
					infos = affairPartyMemberService.findCategoryInfoById(id, year ,null, null, null);
				} else {
					ids.add(id);
					infos = affairPartyMemberService.findCategoryInfoByIds(ids, year, null, null, null);
				}
			} else {
				infos = affairPartyMemberService.findCategoryInfoById(null, year, null, null, null);
			}
		}else{// 时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = affairPartyMemberService.findCategoryInfoById(id, null ,startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairPartyMemberService.findCategoryInfoByIds(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairPartyMemberService.findCategoryInfoById(null,null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}

	/**
	 * 统计党员名册中的性别人数（男性、女性）
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("partyMemberSex")
	public @ResponseBody List<Map<String, Object>> partyMemberSex(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if("1".equals(dateType)){//月度
			if (month != null && month.length() > 0) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = affairPartyMemberService.findSexInfoById(id, null ,null, null, month);
					} else {
						ids.add(id);
						infos = affairPartyMemberService.findSexInfoByIds(ids, null, null, null, month);
					}
				} else {
					infos = affairPartyMemberService.findSexInfoById(null,null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if(id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
				if(CollectionUtils.isEmpty(ids)) {
					infos = affairPartyMemberService.findSexInfoById(id, year ,null, null, null);
				} else {
					ids.add(id);
					infos = affairPartyMemberService.findSexInfoByIds(ids, year, null, null, null);
				}
			} else {
				infos = affairPartyMemberService.findSexInfoById(null, year, null, null, null);
			}
		}else{// 时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = affairPartyMemberService.findSexInfoById(id, null ,startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairPartyMemberService.findSexInfoByIds(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairPartyMemberService.findSexInfoById(null,null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}

	/**
	 * 统计党员名册中的民族人数
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("partyMemberNation")
	public @ResponseBody List<Map<String, Object>> partyMemberNation(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, Object>> infos = null;
		//1：月度 2：年度 3:时间段
		if("1".equals(dateType)){//月度
			if (month != null && month.length() > 0) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = affairPartyMemberService.findNationInfoById(id, null ,null, null, month);
					} else {
						ids.add(id);
						infos = affairPartyMemberService.findNationInfoByIds(ids, null, null, null, month);
					}
				} else {
					infos = affairPartyMemberService.findNationInfoById(null,null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if(id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
				if(CollectionUtils.isEmpty(ids)) {
					infos = affairPartyMemberService.findNationInfoById(id, year ,null, null, null);
				} else {
					ids.add(id);
					infos = affairPartyMemberService.findNationInfoByIds(ids, year, null, null, null);
				}
			} else {
				infos = affairPartyMemberService.findNationInfoById(null, year, null, null, null);
			}
		}else{// 时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = affairPartyMemberService.findNationInfoById(id, null ,startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairPartyMemberService.findNationInfoByIds(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairPartyMemberService.findNationInfoById(null,null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}

	/**
	 * 统计工会健康体检患病史种类
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("tiJianType")
	public @ResponseBody List<Map<String,String>> tiJianType(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		/*健康体检需求更改 患病史改为多选*/
		AffairHealthCheckup healthCheckup = new AffairHealthCheckup();
		if (dateType.equals("1")){		//月度查询
			healthCheckup.setMonth(month);
		}else if (dateType.equals("3")){	//时间段查询
			healthCheckup.setBeginTjDate(DateUtils.parseDate(dateStart));
			healthCheckup.setEndTjDate(DateUtils.parseDate(dateEnd));
		}else {		//年度查询
			healthCheckup.setYear(year);
		}
		List<AffairHealthCheckup> list = affairHealthCheckupService.findByDateList(healthCheckup);

		List<Dict> dicts =DictUtils.getDictList("affair_health_checkup_type");
		int[] nums = new int[dicts.size()];
		list.forEach(item -> {
			for(int i = 0;i<dicts.size();i++) {
				if (StringUtils.isNotBlank(item.getType()) && item.getType().contains(dicts.get(i).getValue())){
					nums[i]++;
				}
			}
		});
		List<Map<String,String>> l= new ArrayList<>();
		for(int i =0;i<dicts.size();i++) {
			Map<String,String> map=new HashMap<>();
			map.put("num", String.valueOf(nums[i]));
			map.put("label",DictUtils.getDictLabel(String.valueOf(i+1),"affair_health_checkup_type",""));
			l.add(map);
		}

		/*健康体检需求更改 患病史改为多选  不再使用此方式*/
		/*List<Map<String, Object>> infos = null;
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairHealthCheckupService.findTypeInfoByCreateOrgId(id, null, null, null, month);
					} else {
						ids.add(id);
						infos = affairHealthCheckupService.findTypeInfoByCreateOrgIds(ids, null, null, null, month);
					}
				} else {
					infos = affairHealthCheckupService.findTypeInfoByCreateOrgId(null, null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairHealthCheckupService.findTypeInfoByCreateOrgId(id, year, null, null, null);
				} else {
					ids.add(id);
					infos = affairHealthCheckupService.findTypeInfoByCreateOrgIds(ids, year, null, null,  null);
				}
			} else {
				infos = affairHealthCheckupService.findTypeInfoByCreateOrgId(null, year, null, null, null);
			}
		}else {// 时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairHealthCheckupService.findTypeInfoByCreateOrgId(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairHealthCheckupService.findTypeInfoByCreateOrgIds(ids, null, startDate, endDate,  null);
					}
				} else {
					infos = affairHealthCheckupService.findTypeInfoByCreateOrgId(null, null, startDate, endDate, null);
				}
			}
		}
		List<String> countData = new ArrayList<>();
		List<String> labelData = new ArrayList<>();
		Map<String, Object> result = new HashMap<>();
		if(infos != null && infos.size() > 0){
			for (Map<String, Object> m:infos) {
				countData.add(m.get("count").toString());
				labelData.add(m.get("label").toString());
			}
		}
		result.put("countData", countData);
		result.put("labelData", labelData);
		return result;*/
		return l;
	}

	/**
	 * 统计工会健康体检的参检率
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("tiJian")
	public @ResponseBody Map<String, Object> tiJian(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Object> infos = null;
		if ("1".equals(dateType)) {//月度
			if (month != null && month.length() > 0) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairHealthCheckupService.findInfoByCreateOrgId(id, null, null, null, month);
					} else {
						ids.add(id);
						infos = affairHealthCheckupService.findInfoByCreateOrgIds(ids, null, null, null, month);
					}
				} else {
					infos = affairHealthCheckupService.findInfoByCreateOrgId(null, null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if (id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = officeService.findIdsByParentId(id);
				if (CollectionUtils.isEmpty(ids)) {
					infos = affairHealthCheckupService.findInfoByCreateOrgId(id, year, null, null, null);
				} else {
					ids.add(id);
					infos = affairHealthCheckupService.findInfoByCreateOrgIds(ids, year, null, null,  null);
				}
			} else {
				infos = affairHealthCheckupService.findInfoByCreateOrgId(null, year, null, null, null);
			}
		}else {// 时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if (id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = officeService.findIdsByParentId(id);
					if (CollectionUtils.isEmpty(ids)) {
						infos = affairHealthCheckupService.findInfoByCreateOrgId(id, null, startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairHealthCheckupService.findInfoByCreateOrgIds(ids, null, startDate, endDate,  null);
					}
				} else {
					infos = affairHealthCheckupService.findInfoByCreateOrgId(null, null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}

	/**
	 * 党建-民主评议党员统计各等级的党员人数
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	@RequestMapping("commentGrade")
	public @ResponseBody List<Map<String, String>> commentGrade(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		List<Map<String, String>> infos = null;
		//1：月度 2：年度 3:时间段
		if("1".equals(dateType)){//月度
			if (month != null && month.length() > 0) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = affairCommentService.findGradeInfoById(id, null ,null, null, month);
					} else {
						ids.add(id);
						infos = affairCommentService.findGradeInfoByIds(ids, null, null, null, month);
					}
				} else {
					infos = affairCommentService.findGradeInfoById(null,null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if(id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
				if(CollectionUtils.isEmpty(ids)) {
					infos = affairCommentService.findGradeInfoById(id, year ,null, null, null);
				} else {
					ids.add(id);
					infos = affairCommentService.findGradeInfoByIds(ids, year, null, null, null);
				}
			} else {
				infos = affairCommentService.findGradeInfoById(null, year, null, null, null);
			}
		}else{// 时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = affairCommentService.findGradeInfoById(id, null ,startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairCommentService.findGradeInfoByIds(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairCommentService.findGradeInfoById(null,null, startDate, endDate, null);
				}
			}
		}

		return ChartLabelUtils.fillLabel(infos,DictUtils.getDictList("affair_comment_grade"));
	}

	@RequestMapping("partyMemberWorkPlace")
	public @ResponseBody Map<String, Integer> partyMemberWorkPlace(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Map<String, Integer> infos = null;
		//1：月度 2：年度 3:时间段
		if("1".equals(dateType)){//月度
			if (month != null && month.length() > 0) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = affairPartyMemberService.findWorkPlaceInfoById(id, null ,null, null, month);
					} else {
						ids.add(id);
						infos = affairPartyMemberService.findWorkPlaceInfoByIds(ids, null, null, null, month);
					}
				} else {
					infos = affairPartyMemberService.findWorkPlaceInfoById(null,null, null, null, month);
				}
			}
		}else if("2".equals(dateType)){//年度
			if(id != null && !TOP_OFFICE_ID.equals(id)) {
				List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
				if(CollectionUtils.isEmpty(ids)) {
					infos = affairPartyMemberService.findWorkPlaceInfoById(id, year ,null, null, null);
				} else {
					ids.add(id);
					infos = affairPartyMemberService.findWorkPlaceInfoByIds(ids, year, null, null, null);
				}
			} else {
				infos = affairPartyMemberService.findWorkPlaceInfoById(null, year, null, null, null);
			}
		}else{// 时间段
			Date startDate = DateUtils.parseDate(dateStart);
			Date endDate = DateUtils.parseDate(dateEnd);
			if (!(startDate ==null && endDate == null)) {
				if(id != null && !TOP_OFFICE_ID.equals(id)) {
					List<String> ids = affairGeneralSituationService.findAllChildIdById(id);
					if(CollectionUtils.isEmpty(ids)) {
						infos = affairPartyMemberService.findWorkPlaceInfoById(id, null ,startDate, endDate, null);
					} else {
						ids.add(id);
						infos = affairPartyMemberService.findWorkPlaceInfoByIds(ids, null, startDate, endDate, null);
					}
				} else {
					infos = affairPartyMemberService.findWorkPlaceInfoById(null,null, startDate, endDate, null);
				}
			}
		}
		return infos;
	}

	/**
	 * 青年团见
	 *
	 * @return
	 */
	@RequestMapping("group")
	public ModelAndView group(String id) {
		ModelAndView view = new ModelAndView();
		view.addObject("id", id);
		view.addObject("dateType", "1");
		view.addObject("year", DateUtils.getYear());
		view.addObject("month", DateUtils.getYear()+"-"+DateUtils.getMonth());
		view.setViewName("modules/charts/group");
		return view;
	}
}
