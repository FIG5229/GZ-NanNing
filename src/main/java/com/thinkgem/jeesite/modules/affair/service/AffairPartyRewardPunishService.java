/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairPartyRewardPunishDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyRewardPunish;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 党员奖惩信息Service
 * @author eav.liu
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class AffairPartyRewardPunishService extends CrudService<AffairPartyRewardPunishDao, AffairPartyRewardPunish> {

	@Autowired
	private AffairPartyRewardPunishDao affairPartyRewardPunishDao;

	public AffairPartyRewardPunish get(String id) {
		return super.get(id);
	}
	
	public List<AffairPartyRewardPunish> findList(AffairPartyRewardPunish affairPartyRewardPunish) {
		return super.findList(affairPartyRewardPunish);
	}
	
	public Page<AffairPartyRewardPunish> findPage(Page<AffairPartyRewardPunish> page, AffairPartyRewardPunish affairPartyRewardPunish) {
		affairPartyRewardPunish.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairPartyRewardPunish);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPartyRewardPunish affairPartyRewardPunish) {
		super.save(affairPartyRewardPunish);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPartyRewardPunish affairPartyRewardPunish) {
		super.delete(affairPartyRewardPunish);
	}

	public List<AffairPartyRewardPunish> selectPerson(Date lastYearDate, Date nowYearDate, String unitId) {
		return affairPartyRewardPunishDao.selectPerson(lastYearDate,nowYearDate,unitId);
	}
	public List<AffairPartyRewardPunish> selectPersonMonth(Date lastMonthDate,Date nowMonthDate, String unitId) {
		return affairPartyRewardPunishDao.selectPersonMonth(lastMonthDate,nowMonthDate ,unitId);
	}
}