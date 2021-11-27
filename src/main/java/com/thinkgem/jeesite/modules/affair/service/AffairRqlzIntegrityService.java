/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairRqlzIntegrityDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairRqlzIntegrity;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 任前廉政鉴定Service
 * @author cecil.li
 * @version 2019-11-08
 */
@Service
@Transactional(readOnly = true)
public class AffairRqlzIntegrityService extends CrudService<AffairRqlzIntegrityDao, AffairRqlzIntegrity> {

	@Autowired
	private AffairRqlzIntegrityDao affairRqlzIntegrityDao;

	public AffairRqlzIntegrity get(String id) {
		return super.get(id);
	}
	
	public List<AffairRqlzIntegrity> findList(AffairRqlzIntegrity affairRqlzIntegrity) {
		return super.findList(affairRqlzIntegrity);
	}
	
	public Page<AffairRqlzIntegrity> findPage(Page<AffairRqlzIntegrity> page, AffairRqlzIntegrity affairRqlzIntegrity) {
		affairRqlzIntegrity.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairRqlzIntegrity);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairRqlzIntegrity affairRqlzIntegrity) {
		affairRqlzIntegrity.setMainContent(StringEscapeUtils.unescapeHtml4(affairRqlzIntegrity.getMainContent()));
		super.save(affairRqlzIntegrity);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairRqlzIntegrity affairRqlzIntegrity) {
		super.delete(affairRqlzIntegrity);
	}

	public Map<String, Object> findInfoByZbType(String id, Integer year, Date startDate, Date endDate, String month) {

		id=dataScopeFilter(UserUtils.getUser(), "o", "u");

		List<Map<String, Object>> peopleCount = affairRqlzIntegrityDao.findInfoByJdType(id, year, startDate, endDate, month);
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

	public Page<AffairRqlzIntegrity> findDetailInfoByType(Page<AffairRqlzIntegrity> page, AffairRqlzIntegrity affairRqlzIntegrity){
		affairRqlzIntegrity.setPage(page);
		List<AffairRqlzIntegrity> list = new ArrayList<>();
		if("1".equals(affairRqlzIntegrity.getDateType())){//月度
			if (affairRqlzIntegrity.getMonth() != null && affairRqlzIntegrity.getMonth().length() > 0) {
				affairRqlzIntegrity.setYear(null);
				affairRqlzIntegrity.setDateStart(null);
				affairRqlzIntegrity.setDateEnd(null);
				list = affairRqlzIntegrityDao.findDetailInfoByType(affairRqlzIntegrity);
			}
		}else if("2".equals(affairRqlzIntegrity.getDateType())){//年度
			affairRqlzIntegrity.setMonth(null);
			affairRqlzIntegrity.setDateStart(null);
			affairRqlzIntegrity.setDateEnd(null);
			list = affairRqlzIntegrityDao.findDetailInfoByType(affairRqlzIntegrity);
		}else{// 时间段
			affairRqlzIntegrity.setMonth(null);
			affairRqlzIntegrity.setYear(null);
			Date startDate = DateUtils.parseDate(affairRqlzIntegrity.getDateStart());
			Date endDate = DateUtils.parseDate(affairRqlzIntegrity.getDateEnd());
			affairRqlzIntegrity.setStartDate(startDate);
			affairRqlzIntegrity.setEndDate(endDate);
			list = affairRqlzIntegrityDao.findDetailInfoByType(affairRqlzIntegrity);
		}
		page.setList(list);
		return page;
	}

	public List<Map<String, Object>> findPieInfoByJdType(String id, Integer year, Date startDate, Date endDate, String month) {
		id=dataScopeFilter(UserUtils.getUser(), "o", "u");
		return affairRqlzIntegrityDao.findPieInfoByJdType(id, year, startDate, endDate, month);
	}
	
}