/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairGroupStudyDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGroupStudy;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 党委中心组学习Service
 * @author cecil.li
 * @version 2019-11-04
 */
@Service
@Transactional(readOnly = true)
public class AffairGroupStudyService extends CrudService<AffairGroupStudyDao, AffairGroupStudy> {

	@Autowired
	private AffairGroupStudyDao affairGroupStudyDao;

	public AffairGroupStudy get(String id) {
		return super.get(id);
	}
	
	public List<AffairGroupStudy> findList(AffairGroupStudy affairGroupStudy) {
		return super.findList(affairGroupStudy);
	}
	
	public Page<AffairGroupStudy> findPage(Page<AffairGroupStudy> page, AffairGroupStudy affairGroupStudy) {
		affairGroupStudy.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairGroupStudy);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairGroupStudy affairGroupStudy) {
		super.save(affairGroupStudy);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairGroupStudy affairGroupStudy) {
		super.delete(affairGroupStudy);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgId(id, year, startDate, endDate, month);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgIds(ids, year, startDate, endDate, month);
	}

	public List<Map<String,String>> countPartyStudy(String dateType, Integer year, String dateStart, String dateEnd, String month) {
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
		String companyId = UserUtils.getUser().getCompany().getId();
		if (companyId.equals("1")){
			return dao.countJuPartyStudy(companyId,year,startDate,endDate,month);
		}else {
			return dao.countChuPartyStudy(companyId,year,startDate,endDate,month);
		}
	}

	/**
	 * 统计分析	党委中心组学习明细
	 * @param page
	 * @param affairGroupStudy
	 * @return
	 */
	public Page<AffairGroupStudy> findPartyStudyPage(Page<AffairGroupStudy> page, AffairGroupStudy affairGroupStudy) {
		String dateType = Optional.ofNullable(affairGroupStudy.getDateType()).orElseGet(() -> {return "";});
		if (dateType.equals("1")){	//月度查询
			affairGroupStudy.setYear(null);
			affairGroupStudy.setStartDate(null);
			affairGroupStudy.setEndDate(null);
		}else if (dateType.equals("3")){	//时间段查询
			affairGroupStudy.setYear(null);
			affairGroupStudy.setStartDate(DateUtils.parseDate(affairGroupStudy.getDateStart()));
			affairGroupStudy.setEndDate(DateUtils.parseDate(affairGroupStudy.getDateEnd()));
			affairGroupStudy.setMonth(null);
		}else {	//年度查询
			affairGroupStudy.setMonth(null);
			affairGroupStudy.setStartDate(null);
			affairGroupStudy.setEndDate(null);
		}
		String unit = affairGroupStudy.getLabel()==null?"":affairGroupStudy.getLabel();
		if (unit.contains("北海处")){
			affairGroupStudy.setUnitId("156");
		}else if (unit.contains("柳州处")){
			affairGroupStudy.setUnitId("95");
		}else if (unit.contains("南宁处")){
			affairGroupStudy.setUnitId("34");
		}else {
			affairGroupStudy.setUnitId("top");
		}
		/*switch (unit){
			case "北海处":
				affairGroupStudy.setUnitId("156");
				break;
			case "柳州处":
				affairGroupStudy.setUnitId("95");
				break;
			case "南宁处":
				affairGroupStudy.setUnitId("34");
				break;
			default:
				affairGroupStudy.setUnitId("top");
				break;
		}*/
	affairGroupStudy.setPage(page);
	page.setList(dao.findPartyStudyList(affairGroupStudy));
	return page;
	}

	public List<String> selectAllYear(){
		return affairGroupStudyDao.selectAllYear();
	}
	public List<String> selectAllMonth(){
		return affairGroupStudyDao.selectAllMonth();
	}

	public Integer selectTime(String yearL,String yearT,String idNumber){
		return affairGroupStudyDao.selectTime(yearL,yearT,idNumber);
	}

}