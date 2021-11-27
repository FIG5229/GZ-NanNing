/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.org.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.org.entity.OrgReward;
import com.thinkgem.jeesite.modules.org.dao.OrgRewardDao;

/**
 * 单位奖励信息Service
 * @author eav.liu
 * @version 2019-11-22
 */
@Service
@Transactional(readOnly = true)
public class OrgRewardService extends CrudService<OrgRewardDao, OrgReward> {

	public OrgReward get(String id) {
		return super.get(id);
	}
	
	public List<OrgReward> findList(OrgReward orgReward) {
		return super.findList(orgReward);
	}
	
	public Page<OrgReward> findPage(Page<OrgReward> page, OrgReward orgReward) {
		return super.findPage(page, orgReward);
	}
	
	@Transactional(readOnly = false)
	public void save(OrgReward orgReward) {
		super.save(orgReward);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrgReward orgReward) {
		super.delete(orgReward);
	}
	
}