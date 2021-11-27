/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairTalkManagementDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTalkManagement;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 谈话函询管理Service
 * @author cecil.li
 * @version 2019-11-08
 */
@Service
@Transactional(readOnly = true)
public class AffairTalkManagementService extends CrudService<AffairTalkManagementDao, AffairTalkManagement> {

	@Autowired
	private AffairTalkManagementDao affairTalkManagementDao;

	public AffairTalkManagement get(String id) {
		return super.get(id);
	}
	
	public List<AffairTalkManagement> findList(AffairTalkManagement affairTalkManagement) {
		return super.findList(affairTalkManagement);
	}
	
	public Page<AffairTalkManagement> findPage(Page<AffairTalkManagement> page, AffairTalkManagement affairTalkManagement) {
		affairTalkManagement.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTalkManagement);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTalkManagement affairTalkManagement) {
		super.save(affairTalkManagement);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTalkManagement affairTalkManagement) {
		super.delete(affairTalkManagement);
	}

	public List<AffairTalkManagement> tongJi(AffairTalkManagement affairTalkManagement) {
		return super.tongJi(affairTalkManagement);
	}

	public List<Map<String, Object>> findStatistic(AffairTalkManagement affairTalkManagement){
		List<Map<String, Object>> statistic = affairTalkManagementDao.findStatistic(affairTalkManagement);
		return statistic;
	}

	public List<Map<String, Object>> findInfoByUnitId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByUnitId(id, year, startDate, endDate, month);
	}

	public List<Map<String, Object>> findInfoByUnitIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByUnitIds(ids, year, startDate, endDate, month);
	}

	public Map<String, Object> findCountInfoByZbUnit(String id, Integer year, Date startDate, Date endDate, String month) {

	id=dataScopeFilter(UserUtils.getUser(), "o", "u");
		List<Map<String, Object>> peopleCount = affairTalkManagementDao.findCountInfoByZbUnit(id, year, startDate, endDate, month);
		Map<String, Object> result = new HashMap<>();
		List<String> labelList = new ArrayList<>();
		List<Integer> totalList = new ArrayList<>();
		for (Map<String, Object> map : peopleCount){
			String label = String.valueOf(map.get("label"));
			labelList.add(label);
			Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
			totalList.add(totalInteger);
		}
		result.put("labelData", labelList);
		result.put("totalData", totalList);
		return result;
	}

	public Page<AffairTalkManagement> findDeatilInfoByZbUnit(Page<AffairTalkManagement> page, AffairTalkManagement affairTalkManagement){
		affairTalkManagement.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		affairTalkManagement.setPage(page);
		List<AffairTalkManagement> list = new ArrayList<>();
		if("1".equals(affairTalkManagement.getDateType())){//月度
			if (affairTalkManagement.getMonth() != null && affairTalkManagement.getMonth().length() > 0) {
				affairTalkManagement.setYear(null);
				affairTalkManagement.setDateStart(null);
				affairTalkManagement.setDateEnd(null);
				list = affairTalkManagementDao.findDetailInfoByZbUnit(affairTalkManagement);
			}
		}else if("2".equals(affairTalkManagement.getDateType())){//年度
			affairTalkManagement.setMonth(null);
			affairTalkManagement.setDateStart(null);
			affairTalkManagement.setDateEnd(null);
			list = affairTalkManagementDao.findDetailInfoByZbUnit(affairTalkManagement);
		}else{// 时间段
			affairTalkManagement.setMonth(null);
			affairTalkManagement.setYear(null);
			Date startDate = DateUtils.parseDate(affairTalkManagement.getDateStart());
			Date endDate = DateUtils.parseDate(affairTalkManagement.getDateEnd());
			affairTalkManagement.setStartDate(startDate);
			affairTalkManagement.setEndDate(endDate);
			list = affairTalkManagementDao.findDetailInfoByZbUnit(affairTalkManagement);
		}
		page.setList(list);
		return page;
	}

	public List<Map<String, Object>> findPieInfoByLx(String id, Integer year, Date startDate, Date endDate, String month) {
		id=dataScopeFilter(UserUtils.getUser(), "o", "u");
		return affairTalkManagementDao.findPieInfoByLx(id, year, startDate, endDate, month);
	}

	public Page<AffairTalkManagement> findDetailInfoByLx(Page<AffairTalkManagement> page, AffairTalkManagement affairTalkManagement){
		affairTalkManagement.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		affairTalkManagement.setPage(page);
		List<AffairTalkManagement> list = new ArrayList<>();
		if("1".equals(affairTalkManagement.getDateType())){//月度
			if (affairTalkManagement.getMonth() != null && affairTalkManagement.getMonth().length() > 0) {
				affairTalkManagement.setYear(null);
				affairTalkManagement.setDateStart(null);
				affairTalkManagement.setDateEnd(null);
				list = affairTalkManagementDao.findDetailInfoByLx(affairTalkManagement);
			}
		}else if("2".equals(affairTalkManagement.getDateType())){//年度
			affairTalkManagement.setMonth(null);
			affairTalkManagement.setDateStart(null);
			affairTalkManagement.setDateEnd(null);
			list = affairTalkManagementDao.findDetailInfoByLx(affairTalkManagement);
		}else{// 时间段
			affairTalkManagement.setMonth(null);
			affairTalkManagement.setYear(null);
			Date startDate = DateUtils.parseDate(affairTalkManagement.getDateStart());
			Date endDate = DateUtils.parseDate(affairTalkManagement.getDateEnd());
			affairTalkManagement.setStartDate(startDate);
			affairTalkManagement.setEndDate(endDate);
			list = affairTalkManagementDao.findDetailInfoByLx(affairTalkManagement);
		}
		page.setList(list);
		return page;
	}

	//绩效自动考评根据单位获得被考评对象id
	public String findCodeByUnit(String unit){
		return affairTalkManagementDao.findCodeByUnit(unit);
	}
	public String findUserIdByCode(String code){
		return affairTalkManagementDao.findUserIdByCode(code);
	}

	public List<AffairTalkManagement> allInfo(String year, String month, String startTime, String endTime){
		return affairTalkManagementDao.allInfo(year, month, startTime, endTime);
	}
}