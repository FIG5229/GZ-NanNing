/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairPartyDayActivitiesDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyDayActivities;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 党日活动Service
 * @author cecil.li
 * @version 2020-04-12
 */
@Service
@Transactional(readOnly = true)
public class AffairPartyDayActivitiesService extends CrudService<AffairPartyDayActivitiesDao, AffairPartyDayActivities> {

	@Autowired
	private AffairPartyDayActivitiesDao affairPartyDayActivitiesDao;

	public AffairPartyDayActivities get(String id) {
		return super.get(id);
	}
	
	public List<AffairPartyDayActivities> findList(AffairPartyDayActivities affairPartyDayActivities) {
		return super.findList(affairPartyDayActivities);
	}
	
	public Page<AffairPartyDayActivities> findPage(Page<AffairPartyDayActivities> page, AffairPartyDayActivities affairPartyDayActivities) {
		affairPartyDayActivities.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairPartyDayActivities);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPartyDayActivities affairPartyDayActivities) {
		affairPartyDayActivities.setContent(StringEscapeUtils.unescapeHtml4(affairPartyDayActivities.getContent()));
		super.save(affairPartyDayActivities);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPartyDayActivities affairPartyDayActivities) {
		super.delete(affairPartyDayActivities);
	}

	public List findBaseInfoList(String year, String month, String startYear, String endYear, String startTime, String endTime){
		return affairPartyDayActivitiesDao.findBaseInfoList(year,month, startYear, endYear, startTime, endTime);
	}

	public int isElection(String year, Date date, String partyBranch, String partyBranchId){
		return affairPartyDayActivitiesDao.isElection(year,date,partyBranch,partyBranchId);
	}

	public int unitCount(String startTime, String endTime, String partyOrganizationName, String partyOrganizationId){
		return affairPartyDayActivitiesDao.unitCount(startTime, endTime, partyOrganizationName, partyOrganizationId);
	}

    public Integer selectNumber(String id, String yearL, String yearT) {
		return affairPartyDayActivitiesDao.selectNumber(id,yearL,yearT);
    }
}