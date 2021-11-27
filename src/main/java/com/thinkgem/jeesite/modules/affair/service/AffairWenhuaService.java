/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairWenhuaDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairActivityMien;
import com.thinkgem.jeesite.modules.affair.entity.AffairWenhua;
import com.thinkgem.jeesite.modules.affair.entity.AffairWenyi;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 文化人才Service
 * @author cecil.li
 * @version 2020-03-06
 */
@Service
@Transactional(readOnly = true)
public class AffairWenhuaService extends CrudService<AffairWenhuaDao, AffairWenhua> {

	@Autowired
	private AffairWenhuaDao affairWenhuaDao;

	public AffairWenhua get(String id) {
		return super.get(id);
	}
	
	public List<AffairWenhua> findList(AffairWenhua affairWenhua) {
		return super.findList(affairWenhua);
	}
	
	public Page<AffairWenhua> findPage(Page<AffairWenhua> page, AffairWenhua affairWenhua) {
		affairWenhua.setOfficeId(UserUtils.getUser().getOffice().getId());
		affairWenhua.setUserId(UserUtils.getUser().getId());
		affairWenhua.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairWenhua);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairWenhua affairWenhua) {
		super.save(affairWenhua);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairWenhua affairWenhua) {
		super.delete(affairWenhua);
	}
	@Transactional(readOnly = false)


	public List<String> findusernameList(String name){
		return affairWenhuaDao.findusernameList(name);
	}
	public List<String> fingproductionList(String workSituation){
		return affairWenhuaDao.fingproductionList(workSituation);
	}
	public String findManuscriptList(String name){
		return affairWenhuaDao.findManuscriptList(name);
	}

	/**
	 * 批量提交
	 * @param ids
	 */
	public List<AffairWenhua> findByIds(List<String> ids){
		List<AffairWenhua> list = affairWenhuaDao.findByIds(ids);
		return list;
	}

	/**
	 * 统计分析 各类型文艺人才
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
    public List<Map<String, String>> countLiteraryTalent(String dateType, Integer year, String dateStart, String dateEnd, String month) {
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
		String parentId = UserUtils.getUser().getOffice().getParentId();
		if (parentId.equals("1") || parentId.equals("0")){
			return dao.countJuLiteraryTalent(parentId,year,startDate,endDate,month);
		}else {
			return dao.countChuLiteraryTalent(parentId,year,startDate,endDate,month);
		}
    }

	/**
	 * 统计分析 加入不同协会的文艺人才
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	public List<Map<String, String>> countTalentJoin(String dateType, Integer year, String dateStart, String dateEnd, String month) {
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
			return dao.countJuTalentJoin(parentId,year,startDate,endDate,month);
		}else {
			return dao.countChuTalentJoin(parentId,year,startDate,endDate,month);
		}
	}

	/**
	 * 统计分析 各类型文艺人才明细
	 * @param page
	 * @param affairWenhua
	 * @return
	 */
	public Page<AffairWenhua> findLiteraryTalentPage(Page<AffairWenhua> page, AffairWenhua affairWenhua) {
		String companyId = UserUtils.getUser().getCompany().getId();
		affairWenhua.setUnitId(companyId);
		String dateType = Optional.ofNullable(affairWenhua.getDateType()).orElseGet(() -> {return "";});
		switch (dateType) {
			case "1":    //月度
				affairWenhua.setYear(null);
				affairWenhua.setStartDate(null);
				affairWenhua.setEndDate(null);
				break;
			case "3":    //时间段
				affairWenhua.setYear(null);
				affairWenhua.setStartDate(DateUtils.parseDate(affairWenhua.getDateStart()));
				affairWenhua.setEndDate(DateUtils.parseDate(affairWenhua.getDateEnd()));
				affairWenhua.setMonth(null);
				break;
			default:    //年度
				affairWenhua.setMonth(null);
				affairWenhua.setStartDate(null);
				affairWenhua.setEndDate(null);
				break;
		}
		switch (affairWenhua.getUnit()){
			case "南宁处":
				affairWenhua.setUnitId("34");
				break;
			case "北海处":
				affairWenhua.setUnitId("156");
				break;
			case "柳州处":
				affairWenhua.setUnitId("95");
				break;
			default:
				affairWenhua.setUnit("top");
				break;
		}
		affairWenhua.setPage(page);
		page.setList(dao.findLiteraryTalentList(affairWenhua));

		return page;
	}

	/**
	 * 统计分析	加入不同协会的文艺人才明细
	 * @param page
	 * @param affairWenhua
	 * @return
	 */
	public Page<AffairWenhua> findTalentJoinPage(Page<AffairWenhua> page, AffairWenhua affairWenhua) {
		String companyId = UserUtils.getUser().getCompany().getId();
		affairWenhua.setUnitId(companyId);
		String dateType = Optional.ofNullable(affairWenhua.getDateType()).orElseGet(() -> {return "";});
		switch (dateType) {
			case "1":    //月度
				affairWenhua.setYear(null);
				affairWenhua.setStartDate(null);
				affairWenhua.setEndDate(null);
				break;
			case "3":    //时间段
				affairWenhua.setYear(null);
				affairWenhua.setStartDate(DateUtils.parseDate(affairWenhua.getDateStart()));
				affairWenhua.setEndDate(DateUtils.parseDate(affairWenhua.getDateEnd()));
				affairWenhua.setMonth(null);
				break;
			default:    //年度
				affairWenhua.setMonth(null);
				affairWenhua.setStartDate(null);
				affairWenhua.setEndDate(null);
				break;
		}

		switch (affairWenhua.getUnit()){
			case "南宁处":
				affairWenhua.setUnitId("34");
				break;
			case "北海处":
				affairWenhua.setUnitId("156");
				break;
			case "柳州处":
				affairWenhua.setUnitId("95");
				break;
			default:
				affairWenhua.setUnit("top");
				break;
		}

		affairWenhua.setPage(page);
		page.setList(dao.findTalentJoinList(affairWenhua));
		return page;
	}

	public List<AffairWenhua> selectSpeciality(String name){
		return affairWenhuaDao.selectSpeciality(name);
	}


	public String selectName(String id) {
		return affairWenhuaDao.selectName(id);
	}
}