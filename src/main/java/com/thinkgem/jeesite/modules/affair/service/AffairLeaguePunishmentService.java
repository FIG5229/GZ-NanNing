/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairLeaguePunishment;
import com.thinkgem.jeesite.modules.affair.dao.AffairLeaguePunishmentDao;

/**
 * 团内惩罚Service
 * @author daniel.liu
 * @version 2020-05-19
 */
@Service
@Transactional(readOnly = true)
public class AffairLeaguePunishmentService extends CrudService<AffairLeaguePunishmentDao, AffairLeaguePunishment> {

	public AffairLeaguePunishment get(String id) {
		return super.get(id);
	}
	
	public List<AffairLeaguePunishment> findList(AffairLeaguePunishment affairLeaguePunishment) {
		return super.findList(affairLeaguePunishment);
	}
	
	public Page<AffairLeaguePunishment> findPage(Page<AffairLeaguePunishment> page, AffairLeaguePunishment affairLeaguePunishment) {
		affairLeaguePunishment.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairLeaguePunishment);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairLeaguePunishment affairLeaguePunishment) {
		super.save(affairLeaguePunishment);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairLeaguePunishment affairLeaguePunishment) {
		super.delete(affairLeaguePunishment);
	}
	
}