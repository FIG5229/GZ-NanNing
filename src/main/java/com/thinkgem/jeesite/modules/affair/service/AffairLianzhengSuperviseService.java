/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairLianzhengSuperviseDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLianzhengSupervise;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 廉政监督Service
 * @author cecil.li
 * @version 2019-11-08
 */
@Service
@Transactional(readOnly = true)
public class AffairLianzhengSuperviseService extends CrudService<AffairLianzhengSuperviseDao, AffairLianzhengSupervise> {

	@Autowired
	private AffairLianzhengSuperviseDao affairLianzhengSuperviseDao;

	public AffairLianzhengSupervise get(String id) {
		return super.get(id);
	}
	
	public List<AffairLianzhengSupervise> findList(AffairLianzhengSupervise affairLianzhengSupervise) {
		affairLianzhengSupervise.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(affairLianzhengSupervise);
	}
	
	public Page<AffairLianzhengSupervise> findPage(Page<AffairLianzhengSupervise> page, AffairLianzhengSupervise affairLianzhengSupervise) {
		return super.findPage(page, affairLianzhengSupervise);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairLianzhengSupervise affairLianzhengSupervise) {
		affairLianzhengSupervise.setMainContent(StringEscapeUtils.unescapeHtml4(affairLianzhengSupervise.getMainContent()));
		super.save(affairLianzhengSupervise);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairLianzhengSupervise affairLianzhengSupervise) {
		super.delete(affairLianzhengSupervise);
	}


	public Map<String, Object> findInfoByJdUnit(String id, Integer year, Date startDate, Date endDate, String month) {
		id=dataScopeFilter(UserUtils.getUser(), "o", "u");

		List<Map<String, Object>> peopleCount = affairLianzhengSuperviseDao.findInfoByJdUnit(id, year, startDate, endDate, month);
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

	public Page<AffairLianzhengSupervise> findDetailInfo(Page<AffairLianzhengSupervise> page, AffairLianzhengSupervise affairLianzhengSupervise){
		affairLianzhengSupervise.setPage(page);
		List<AffairLianzhengSupervise> list = new ArrayList<>();
		if("1".equals(affairLianzhengSupervise.getDateType())){//月度
			if (affairLianzhengSupervise.getMonth() != null && affairLianzhengSupervise.getMonth().length() > 0) {
				affairLianzhengSupervise.setYear(null);
				affairLianzhengSupervise.setDateStart(null);
				affairLianzhengSupervise.setDateEnd(null);
				list = affairLianzhengSuperviseDao.findDetailInfo(affairLianzhengSupervise);
			}
		}else if("2".equals(affairLianzhengSupervise.getDateType())){//年度
			affairLianzhengSupervise.setMonth(null);
			affairLianzhengSupervise.setDateStart(null);
			affairLianzhengSupervise.setDateEnd(null);
			list = affairLianzhengSuperviseDao.findDetailInfo(affairLianzhengSupervise);
		}else{// 时间段
			affairLianzhengSupervise.setMonth(null);
			affairLianzhengSupervise.setYear(null);
			Date startDate = DateUtils.parseDate(affairLianzhengSupervise.getDateStart());
			Date endDate = DateUtils.parseDate(affairLianzhengSupervise.getDateEnd());
			affairLianzhengSupervise.setStartDate(startDate);
			affairLianzhengSupervise.setEndDate(endDate);
			list = affairLianzhengSuperviseDao.findDetailInfo(affairLianzhengSupervise);
		}
		page.setList(list);
		return page;
	}
}