/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairOrgRewardPunishDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairOrgRewardPunish;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 组织奖惩信息Service
 * @author cecil.li
 * @version 2019-11-01
 */
@Service
@Transactional(readOnly = true)
public class AffairOrgRewardPunishService extends CrudService<AffairOrgRewardPunishDao, AffairOrgRewardPunish> {

	@Autowired
	private AffairOrgRewardPunishDao affairOrgRewardPunishDao;

	public AffairOrgRewardPunish get(String id) {
		return super.get(id);
	}
	
	public List<AffairOrgRewardPunish> findList(AffairOrgRewardPunish affairOrgRewardPunish) {
		return super.findList(affairOrgRewardPunish);
	}
	
	public Page<AffairOrgRewardPunish> findPage(Page<AffairOrgRewardPunish> page, AffairOrgRewardPunish affairOrgRewardPunish) {
		affairOrgRewardPunish.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairOrgRewardPunish);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairOrgRewardPunish affairOrgRewardPunish) {
		super.save(affairOrgRewardPunish);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairOrgRewardPunish affairOrgRewardPunish) {
		super.delete(affairOrgRewardPunish);
	}

	public List<Map<String, Object>> findInfoByCreateOrgId(String id, Integer year, String month) {
		return dao.findInfoByCreateOrgId(id, year, month);
	}
	public List<Map<String, Object>> findInfoByCreateOrgIds(List<String> ids, Integer year, String month) {
		return dao.findInfoByCreateOrgIds(ids, year, month);
	}
	public List<String> findListByFileNo(String fileNo) {
		return affairOrgRewardPunishDao.findListByFileNo(fileNo);
	}

	@Transactional(readOnly = false)
	public void deleteByFileNo(String fileNo) {
		affairOrgRewardPunishDao.deleteByFileNo(fileNo);
	}

	public Integer selectNumber(String time,String idNumber){
		return affairOrgRewardPunishDao.selectNumber(time,idNumber);
	}
	public Integer selectTjNumber(String time,String idNumber,String typeList){
		return affairOrgRewardPunishDao.selectTjNumber(time,idNumber,typeList);
	}
	// 根据党组织id，年，月查询相应的奖励信息
	public List<AffairOrgRewardPunish> getJLXXByNowLastTime(String partyId, Date nowCheckTime, Date lastCheckTime) {
		return affairOrgRewardPunishDao.getJLXXByNowLastTime(partyId,nowCheckTime,lastCheckTime);
	}
	public List<AffairOrgRewardPunish> getJLXXByMonth(String partyId, String checkTime) {
		return affairOrgRewardPunishDao.getJLXXByMonth(partyId,checkTime);
	}

    public List<AffairOrgRewardPunish> getJLXXByTime(String officeId, String checkTime) {
		return affairOrgRewardPunishDao.getJLXXByTime(officeId,checkTime);
    }
	public List<AffairOrgRewardPunish> getJLXXZJCByNowLastTime(String officeId, Date nowCheckTime, Date lastCheckTime) {
		return affairOrgRewardPunishDao.getJLXXZJCByNowLastTime(officeId,nowCheckTime,lastCheckTime);
	}
}