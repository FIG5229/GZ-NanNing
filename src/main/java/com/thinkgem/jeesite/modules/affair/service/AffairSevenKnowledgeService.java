/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairSevenKnowledgeDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairSevenKnowledge;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceRank;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelXueli;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelPoliceRankService;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelXueliService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 七知档案Service
 * @author daniel.liu
 * @version 2020-07-03
 */
@Service
@Transactional(readOnly = true)
public class AffairSevenKnowledgeService extends CrudService<AffairSevenKnowledgeDao, AffairSevenKnowledge> {

	@Autowired
	private AffairSevenKnowledgeDao affairSevenKnowledgeDao;

	@Autowired
	PersonnelPoliceRankService personnelPoliceRankService;

	@Autowired
	private PersonnelXueliService personnelXueliService;

	public AffairSevenKnowledge get(String id) {
		AffairSevenKnowledge affairSevenKnowledge =super.get(id);
		if (affairSevenKnowledge!= null){
			PersonnelPoliceRank personnelPoliceRank = new PersonnelPoliceRank();
			personnelPoliceRank.setIdNumber(affairSevenKnowledge.getIdNumber());
			//PersonnelPoliceRank rank = personnelPoliceRankService.findLastOneByIdNumber(personnelPoliceRank);
			//增加  当前记录  的约束条件
			PersonnelPoliceRank rank = personnelPoliceRankService.findNowPoliceRankByIdNumber(personnelPoliceRank);
			affairSevenKnowledge.setPoliceRank(rank.getPoliceRankLevel());

			PersonnelXueli personnelXueli =new PersonnelXueli();
			personnelXueli.setIdNumber(affairSevenKnowledge.getIdNumber());
			personnelXueli = personnelXueliService.getLastByIdNumber(personnelXueli);
			affairSevenKnowledge.setDegreeEducation(personnelXueli.getName());
		}
		return affairSevenKnowledge;
	}
	
	public List<AffairSevenKnowledge> findList(AffairSevenKnowledge affairSevenKnowledge) {
		return super.findList(affairSevenKnowledge);
	}
	
	public Page<AffairSevenKnowledge> findPage(Page<AffairSevenKnowledge> page, AffairSevenKnowledge affairSevenKnowledge) {
		/*affairSevenKnowledge.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/
		/*String id = UserUtils.getUser().getOffice().getId();
		affairSevenKnowledge.setUserId(UserUtils.getUser().getId());
		affairSevenKnowledge.setOfficeId(id);
		affairSevenKnowledge.setCreateBy(UserUtils.getUser());*/
		affairSevenKnowledge.setCreateBy(UserUtils.getUser());
		if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
			affairSevenKnowledge.setOfficeId(UserUtils.getUser().getCompany().getId());
		}else {
			affairSevenKnowledge.setOfficeId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairSevenKnowledge);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairSevenKnowledge affairSevenKnowledge) {
		super.save(affairSevenKnowledge);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairSevenKnowledge affairSevenKnowledge) {
		super.delete(affairSevenKnowledge);
	}

	public int selByIdNumber(String idNumber) {
		return affairSevenKnowledgeDao.selByIdNumber(idNumber);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumber(String idNumber) {
		affairSevenKnowledgeDao.deleteByIdNumber(idNumber);
	}
	/**
	 * 七知档案 统计分析
	 */
    public List<Map<String, Object>> sevenKnowledgeStatistics(String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Date startDate = DateUtils.parseDate(dateStart);
		Date endDate = DateUtils.parseDate(dateEnd);
		if (dateType!=null &&dateType.equals("1")){	//月份查询
			year=null;
			startDate=null;
			endDate=null;
		}else if (dateType!=null &&dateType.equals("3")){	//时间段查询
			year=null;
			month=null;
		}else {	//年度查询
			month=null;
			startDate=null;
			endDate=null;
		}
		String parentId = UserUtils.getUser().getCompany().getId();
		if (parentId.equals("1") || parentId.equals("0")){
			return dao.sevenKnowledgeStatistics(null,year,startDate,endDate,month);
		}else {
			return dao.sevenChuKnowledgeStatistics(parentId,year,startDate,endDate,month);
		}
    }
	//七知档案 统计分析 详情
	public Page<AffairSevenKnowledge> findSevenKnowledgeChartList(Page<AffairSevenKnowledge> page, AffairSevenKnowledge affairSevenKnowledge) {
		String companyId = UserUtils.getUser().getCompany().getId();
		affairSevenKnowledge.setUnitId(companyId);
		String dateType = Optional.ofNullable(affairSevenKnowledge.getDateType()).orElseGet(() -> {return "";});
		switch (dateType) {
			case "1":    //月度
				affairSevenKnowledge.setYear(null);
				affairSevenKnowledge.setStartDate(null);
				affairSevenKnowledge.setEndDate(null);
				break;
			case "3":    //时间段
				affairSevenKnowledge.setYear(null);
				affairSevenKnowledge.setStartDate(DateUtils.parseDate(affairSevenKnowledge.getDateStart()));
				affairSevenKnowledge.setEndDate(DateUtils.parseDate(affairSevenKnowledge.getDateEnd()));
				affairSevenKnowledge.setMonth(null);
				break;
			default:    //年度
				affairSevenKnowledge.setMonth(null);
				affairSevenKnowledge.setStartDate(null);
				affairSevenKnowledge.setEndDate(null);
				break;
		}

		switch (affairSevenKnowledge.getUnit()){
			case "南宁处":
				affairSevenKnowledge.setUnitId("34");
				break;
			case "北海处":
				affairSevenKnowledge.setUnitId("156");
				break;
			case "柳州处":
				affairSevenKnowledge.setUnitId("95");
				break;
			case "局机关":
				affairSevenKnowledge.setUnit("top");
			default:
				break;
		}

		affairSevenKnowledge.setPage(page);
		page.setList(dao.findSevenKnowledgeChartList(affairSevenKnowledge));
		return page;
    }
}