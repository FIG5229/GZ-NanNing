/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairDisciplinaryActionDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDisciplinaryAction;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 纪律处分Service
 * @author cecil.li
 * @version 2019-11-08
 */
@Service
@Transactional(readOnly = true)
public class AffairDisciplinaryActionService extends CrudService<AffairDisciplinaryActionDao, AffairDisciplinaryAction> {

	@Autowired
	private AffairDisciplinaryActionDao affairDisciplinaryActionDao;

	public AffairDisciplinaryAction get(String id) {
		return super.get(id);
	}
	
	public List<AffairDisciplinaryAction> findList(AffairDisciplinaryAction affairDisciplinaryAction) {
		return super.findList(affairDisciplinaryAction);
	}
	
	public Page<AffairDisciplinaryAction> findPage(Page<AffairDisciplinaryAction> page, AffairDisciplinaryAction affairDisciplinaryAction) {
		affairDisciplinaryAction.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairDisciplinaryAction);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairDisciplinaryAction affairDisciplinaryAction) {
		if("1".equals(affairDisciplinaryAction.getDisciplinaryType())){
			affairDisciplinaryAction.setSubOption(affairDisciplinaryAction.getXzSubOption());
		}else{
			affairDisciplinaryAction.setSubOption(affairDisciplinaryAction.getDjSubOption());
		}
		if("".equals(affairDisciplinaryAction.getPushType())||affairDisciplinaryAction.getPushType()==null){
			affairDisciplinaryAction.setPushType("0");
		}
		super.save(affairDisciplinaryAction);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairDisciplinaryAction affairDisciplinaryAction) {
		super.delete(affairDisciplinaryAction);
	}

	public List<Map<String, Object>> findInfoByUnitId(String id, Integer year, Date startDate, Date endDate, String month) {
        id=dataScopeFilter(UserUtils.getUser(), "syso", "sysu");
        return dao.findInfoByUnitId(id, year, startDate, endDate, month);
	}

	public List<Map<String, Object>> findInfoByUnitIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByUnitIds(ids, year, startDate, endDate, month);
	}

	public Map<String, Object> findPeopleCount(String id, Integer year, Date startDate, Date endDate, String month) {
		id=dataScopeFilter(UserUtils.getUser(), "syso", "sysu");
		List<Map<String, Object>> peopleCount = affairDisciplinaryActionDao.findPeopleCount(id, year, startDate, endDate, month);
		Map<String, Object> result = new HashMap<>();
		List<String> labelList = new ArrayList<>();
		List<Integer> totalList = new ArrayList<>();
		for (Map<String, Object> map : peopleCount){
			String label = String.valueOf(map.get("unit"));
			labelList.add(label);
			Integer totalInteger = Integer.valueOf(String.valueOf(map.get("num")));
			totalList.add(totalInteger);
		}
		result.put("labelData", labelList);
		result.put("totalData", totalList);
		return result;
	}

	public Page<AffairDisciplinaryAction> findJuInfoDetail(Page<AffairDisciplinaryAction> page, AffairDisciplinaryAction affairDisciplinaryAction){
		affairDisciplinaryAction.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "syso", "sysu"));
		affairDisciplinaryAction.setPage(page);
		List<AffairDisciplinaryAction> list = new ArrayList<>();
		if("1".equals(affairDisciplinaryAction.getDateType())){//月度
			if (affairDisciplinaryAction.getMonth() != null && affairDisciplinaryAction.getMonth().length() > 0) {
				affairDisciplinaryAction.setYear(null);
				affairDisciplinaryAction.setDateStart(null);
				affairDisciplinaryAction.setDateEnd(null);
				list = affairDisciplinaryActionDao.findJuInfoDetail(affairDisciplinaryAction);
			}
		}else if("2".equals(affairDisciplinaryAction.getDateType())){//年度
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setDateStart(null);
			affairDisciplinaryAction.setDateEnd(null);
			list = affairDisciplinaryActionDao.findJuInfoDetail(affairDisciplinaryAction);
		}else{// 时间段
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setYear(null);
			Date startDate = DateUtils.parseDate(affairDisciplinaryAction.getDateStart());
			Date endDate = DateUtils.parseDate(affairDisciplinaryAction.getDateEnd());
			affairDisciplinaryAction.setStartDate(startDate);
			affairDisciplinaryAction.setEndDate(endDate);
			list = affairDisciplinaryActionDao.findJuInfoDetail(affairDisciplinaryAction);
		}
		page.setList(list);
		return page;
	}

	public Page<AffairDisciplinaryAction> findNncInfoDetail(Page<AffairDisciplinaryAction> page, AffairDisciplinaryAction affairDisciplinaryAction){
		affairDisciplinaryAction.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "syso", "sysu"));

		affairDisciplinaryAction.setPage(page);
		List<AffairDisciplinaryAction> list = new ArrayList<>();
		if("1".equals(affairDisciplinaryAction.getDateType())){//月度
			if (affairDisciplinaryAction.getMonth() != null && affairDisciplinaryAction.getMonth().length() > 0) {
				affairDisciplinaryAction.setYear(null);
				affairDisciplinaryAction.setDateStart(null);
				affairDisciplinaryAction.setDateEnd(null);
				list = affairDisciplinaryActionDao.findNncInfoDetail(affairDisciplinaryAction);
			}
		}else if("2".equals(affairDisciplinaryAction.getDateType())){//年度
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setDateStart(null);
			affairDisciplinaryAction.setDateEnd(null);
			list = affairDisciplinaryActionDao.findNncInfoDetail(affairDisciplinaryAction);
		}else{// 时间段
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setYear(null);
			Date startDate = DateUtils.parseDate(affairDisciplinaryAction.getDateStart());
			Date endDate = DateUtils.parseDate(affairDisciplinaryAction.getDateEnd());
			affairDisciplinaryAction.setStartDate(startDate);
			affairDisciplinaryAction.setEndDate(endDate);
			list = affairDisciplinaryActionDao.findNncInfoDetail(affairDisciplinaryAction);
		}
		page.setList(list);
		return page;
	}

	public Page<AffairDisciplinaryAction> findLzcInfoDetail(Page<AffairDisciplinaryAction> page, AffairDisciplinaryAction affairDisciplinaryAction){
		affairDisciplinaryAction.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "syso", "sysu"));

		affairDisciplinaryAction.setPage(page);
		List<AffairDisciplinaryAction> list = new ArrayList<>();
		if("1".equals(affairDisciplinaryAction.getDateType())){//月度
			if (affairDisciplinaryAction.getMonth() != null && affairDisciplinaryAction.getMonth().length() > 0) {
				affairDisciplinaryAction.setYear(null);
				affairDisciplinaryAction.setDateStart(null);
				affairDisciplinaryAction.setDateEnd(null);
				list = affairDisciplinaryActionDao.findLzcInfoDetail(affairDisciplinaryAction);
			}
		}else if("2".equals(affairDisciplinaryAction.getDateType())){//年度
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setDateStart(null);
			affairDisciplinaryAction.setDateEnd(null);
			list = affairDisciplinaryActionDao.findLzcInfoDetail(affairDisciplinaryAction);
		}else{// 时间段
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setYear(null);
			Date startDate = DateUtils.parseDate(affairDisciplinaryAction.getDateStart());
			Date endDate = DateUtils.parseDate(affairDisciplinaryAction.getDateEnd());
			affairDisciplinaryAction.setStartDate(startDate);
			affairDisciplinaryAction.setEndDate(endDate);
			list = affairDisciplinaryActionDao.findLzcInfoDetail(affairDisciplinaryAction);
		}
		page.setList(list);
		return page;
	}

	public Page<AffairDisciplinaryAction> findBhcInfoDetail(Page<AffairDisciplinaryAction> page, AffairDisciplinaryAction affairDisciplinaryAction){
        affairDisciplinaryAction.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "syso", "sysu"));
        affairDisciplinaryAction.setPage(page);
		List<AffairDisciplinaryAction> list = new ArrayList<>();
		if("1".equals(affairDisciplinaryAction.getDateType())){//月度
			if (affairDisciplinaryAction.getMonth() != null && affairDisciplinaryAction.getMonth().length() > 0) {
				affairDisciplinaryAction.setYear(null);
				affairDisciplinaryAction.setDateStart(null);
				affairDisciplinaryAction.setDateEnd(null);
				list = affairDisciplinaryActionDao.findBhcInfoDetail(affairDisciplinaryAction);
			}
		}else if("2".equals(affairDisciplinaryAction.getDateType())){//年度
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setDateStart(null);
			affairDisciplinaryAction.setDateEnd(null);
			list = affairDisciplinaryActionDao.findBhcInfoDetail(affairDisciplinaryAction);
		}else{// 时间段
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setYear(null);
			Date startDate = DateUtils.parseDate(affairDisciplinaryAction.getDateStart());
			Date endDate = DateUtils.parseDate(affairDisciplinaryAction.getDateEnd());
			affairDisciplinaryAction.setStartDate(startDate);
			affairDisciplinaryAction.setEndDate(endDate);
			list = affairDisciplinaryActionDao.findBhcInfoDetail(affairDisciplinaryAction);
		}
		page.setList(list);
		return page;
	}

	public List<Map<String, Object>> findPieInfoByNature(String id, Integer year, Date startDate, Date endDate, String month) {
		id=dataScopeFilter(UserUtils.getUser(), "o", "u");
		return affairDisciplinaryActionDao.findPieInfoByNature(id, year, startDate, endDate, month);
	}

	public Page<AffairDisciplinaryAction> findInfoByNatureDetail(Page<AffairDisciplinaryAction> page, AffairDisciplinaryAction affairDisciplinaryAction){
        affairDisciplinaryAction.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        affairDisciplinaryAction.setPage(page);
		List<AffairDisciplinaryAction> list = new ArrayList<>();
		if("1".equals(affairDisciplinaryAction.getDateType())){//月度
			if (affairDisciplinaryAction.getMonth() != null && affairDisciplinaryAction.getMonth().length() > 0) {
				affairDisciplinaryAction.setYear(null);
				affairDisciplinaryAction.setDateStart(null);
				affairDisciplinaryAction.setDateEnd(null);
				list = affairDisciplinaryActionDao.findInfoByNatureDetail(affairDisciplinaryAction);
			}
		}else if("2".equals(affairDisciplinaryAction.getDateType())){//年度
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setDateStart(null);
			affairDisciplinaryAction.setDateEnd(null);
			list = affairDisciplinaryActionDao.findInfoByNatureDetail(affairDisciplinaryAction);
		}else{// 时间段
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setYear(null);
			Date startDate = DateUtils.parseDate(affairDisciplinaryAction.getDateStart());
			Date endDate = DateUtils.parseDate(affairDisciplinaryAction.getDateEnd());
			affairDisciplinaryAction.setStartDate(startDate);
			affairDisciplinaryAction.setEndDate(endDate);
			list = affairDisciplinaryActionDao.findInfoByNatureDetail(affairDisciplinaryAction);
		}
		page.setList(list);
		return page;
	}

	public Map<String, Object> findCountByCfUnit(String id, Integer year, Date startDate, Date endDate, String month) {
		id=dataScopeFilter(UserUtils.getUser(), "o", "u");
		List<Map<String, Object>> peopleCount = affairDisciplinaryActionDao.findCountByCfUnit(id, year, startDate, endDate, month);
		Map<String, Object> result = new HashMap<>();
		List<String> labelList = new ArrayList<>();
		List<Integer> totalList = new ArrayList<>();
		for (Map<String, Object> map : peopleCount){
			String label = String.valueOf(map.get("unit"));
			labelList.add(label);
			Integer totalInteger = Integer.valueOf(String.valueOf(map.get("num")));
			totalList.add(totalInteger);
		}
		result.put("labelData", labelList);
		result.put("totalData", totalList);
		return result;
	}

	public Page<AffairDisciplinaryAction> findDetailByCfUnit(Page<AffairDisciplinaryAction> page, AffairDisciplinaryAction affairDisciplinaryAction){
        affairDisciplinaryAction.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        affairDisciplinaryAction.setPage(page);
		List<AffairDisciplinaryAction> list = new ArrayList<>();
		if("1".equals(affairDisciplinaryAction.getDateType())){//月度
			if (affairDisciplinaryAction.getMonth() != null && affairDisciplinaryAction.getMonth().length() > 0) {
				affairDisciplinaryAction.setYear(null);
				affairDisciplinaryAction.setDateStart(null);
				affairDisciplinaryAction.setDateEnd(null);
				list = affairDisciplinaryActionDao.findDetailByCfUnit(affairDisciplinaryAction);
			}
		}else if("2".equals(affairDisciplinaryAction.getDateType())){//年度
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setDateStart(null);
			affairDisciplinaryAction.setDateEnd(null);
			list = affairDisciplinaryActionDao.findDetailByCfUnit(affairDisciplinaryAction);
		}else{// 时间段
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setYear(null);
			Date startDate = DateUtils.parseDate(affairDisciplinaryAction.getDateStart());
			Date endDate = DateUtils.parseDate(affairDisciplinaryAction.getDateEnd());
			affairDisciplinaryAction.setStartDate(startDate);
			affairDisciplinaryAction.setEndDate(endDate);
			list = affairDisciplinaryActionDao.findDetailByCfUnit(affairDisciplinaryAction);
		}
		page.setList(list);
		return page;
	}

	public List<Map<String, Object>> findPeopleCountByChuFen(String id, Integer year, Date startDate, Date endDate, String month) {
		id=dataScopeFilter(UserUtils.getUser(), "o", "u");
		return affairDisciplinaryActionDao.findPeopleCountByChuFen(id, year, startDate, endDate, month);
	}

	public Page<AffairDisciplinaryAction> findDetailInfoByChuFen(Page<AffairDisciplinaryAction> page, AffairDisciplinaryAction affairDisciplinaryAction){
        affairDisciplinaryAction.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        affairDisciplinaryAction.setPage(page);
		List<AffairDisciplinaryAction> list = new ArrayList<>();
		if("1".equals(affairDisciplinaryAction.getDateType())){//月度
			if (affairDisciplinaryAction.getMonth() != null && affairDisciplinaryAction.getMonth().length() > 0) {
				affairDisciplinaryAction.setYear(null);
				affairDisciplinaryAction.setDateStart(null);
				affairDisciplinaryAction.setDateEnd(null);
				list = affairDisciplinaryActionDao.findDetailInfoByChuFen(affairDisciplinaryAction);
			}
		}else if("2".equals(affairDisciplinaryAction.getDateType())){//年度
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setDateStart(null);
			affairDisciplinaryAction.setDateEnd(null);
			list = affairDisciplinaryActionDao.findDetailInfoByChuFen(affairDisciplinaryAction);
		}else{// 时间段
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setYear(null);
			Date startDate = DateUtils.parseDate(affairDisciplinaryAction.getDateStart());
			Date endDate = DateUtils.parseDate(affairDisciplinaryAction.getDateEnd());
			affairDisciplinaryAction.setStartDate(startDate);
			affairDisciplinaryAction.setEndDate(endDate);
			list = affairDisciplinaryActionDao.findDetailInfoByChuFen(affairDisciplinaryAction);
		}
		page.setList(list);
		return page;
	}

	public List<Map<String, Object>> findPieInfoByDjChuFen(String id, Integer year, Date startDate, Date endDate, String month) {
		id=dataScopeFilter(UserUtils.getUser(), "o", "u");

		return affairDisciplinaryActionDao.findPieInfoByDjChuFen(id, year, startDate, endDate, month);
	}

	public Page<AffairDisciplinaryAction> findDetailInfoByDjChuFen(Page<AffairDisciplinaryAction> page, AffairDisciplinaryAction affairDisciplinaryAction){
        affairDisciplinaryAction.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        affairDisciplinaryAction.setPage(page);
		List<AffairDisciplinaryAction> list = new ArrayList<>();
		if("1".equals(affairDisciplinaryAction.getDateType())){//月度
			if (affairDisciplinaryAction.getMonth() != null && affairDisciplinaryAction.getMonth().length() > 0) {
				affairDisciplinaryAction.setYear(null);
				affairDisciplinaryAction.setDateStart(null);
				affairDisciplinaryAction.setDateEnd(null);
				list = affairDisciplinaryActionDao.findDetailInfoByDjChuFen(affairDisciplinaryAction);
			}
		}else if("2".equals(affairDisciplinaryAction.getDateType())){//年度
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setDateStart(null);
			affairDisciplinaryAction.setDateEnd(null);
			list = affairDisciplinaryActionDao.findDetailInfoByDjChuFen(affairDisciplinaryAction);
		}else{// 时间段
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setYear(null);
			Date startDate = DateUtils.parseDate(affairDisciplinaryAction.getDateStart());
			Date endDate = DateUtils.parseDate(affairDisciplinaryAction.getDateEnd());
			affairDisciplinaryAction.setStartDate(startDate);
			affairDisciplinaryAction.setEndDate(endDate);
			list = affairDisciplinaryActionDao.findDetailInfoByDjChuFen(affairDisciplinaryAction);
		}
		page.setList(list);
		return page;
	}

	public List<Map<String, Object>> findPieInfoByXzChuFen(String id, Integer year, Date startDate, Date endDate, String month) {
		id=dataScopeFilter(UserUtils.getUser(), "o", "u");

		return affairDisciplinaryActionDao.findPieInfoByXzChuFen(id, year, startDate, endDate, month);
	}

	public Page<AffairDisciplinaryAction> findDetailInfoByXzChuFen(Page<AffairDisciplinaryAction> page, AffairDisciplinaryAction affairDisciplinaryAction){
        affairDisciplinaryAction.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        affairDisciplinaryAction.setPage(page);
		List<AffairDisciplinaryAction> list = new ArrayList<>();
		if("1".equals(affairDisciplinaryAction.getDateType())){//月度
			if (affairDisciplinaryAction.getMonth() != null && affairDisciplinaryAction.getMonth().length() > 0) {
				affairDisciplinaryAction.setYear(null);
				affairDisciplinaryAction.setDateStart(null);
				affairDisciplinaryAction.setDateEnd(null);
				list = affairDisciplinaryActionDao.findDetailInfoByXzChuFen(affairDisciplinaryAction);
			}
		}else if("2".equals(affairDisciplinaryAction.getDateType())){//年度
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setDateStart(null);
			affairDisciplinaryAction.setDateEnd(null);
			list = affairDisciplinaryActionDao.findDetailInfoByXzChuFen(affairDisciplinaryAction);
		}else{// 时间段
			affairDisciplinaryAction.setMonth(null);
			affairDisciplinaryAction.setYear(null);
			Date startDate = DateUtils.parseDate(affairDisciplinaryAction.getDateStart());
			Date endDate = DateUtils.parseDate(affairDisciplinaryAction.getDateEnd());
			affairDisciplinaryAction.setStartDate(startDate);
			affairDisciplinaryAction.setEndDate(endDate);
			list = affairDisciplinaryActionDao.findDetailInfoByXzChuFen(affairDisciplinaryAction);
		}
		page.setList(list);
		return page;
	}

	public List<AffairDisciplinaryAction> allInfo(String year, String month,String startTime, String endTime){
		return affairDisciplinaryActionDao.allInfo(year, month, startTime, endTime);
	}

}