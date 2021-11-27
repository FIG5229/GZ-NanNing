/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairPushPartyDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPushParty;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 推优入党Service
 * @author cecil.li
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class AffairPushPartyService extends CrudService<AffairPushPartyDao, AffairPushParty> {

	@Autowired
	private AffairPushPartyDao affairPushPartyDao;

	public AffairPushParty get(String id) {
		return super.get(id);
	}
	
	public List<AffairPushParty> findList(AffairPushParty affairPushParty) {

		return super.findList(affairPushParty);
	}
	
	public Page<AffairPushParty> findPage(Page<AffairPushParty> page, AffairPushParty affairPushParty) {
		affairPushParty.setUserId(UserUtils.getUser().getId());
		affairPushParty.setOfficeId(UserUtils.getUser().getOffice().getId());
		affairPushParty.setCreateBy(UserUtils.getUser());
		return super.findPage(page, affairPushParty);
	}
	/**
	 * 批量提交
	 * @param ids
	 */
	public List<AffairPushParty> findByIds(List<String> ids){
		List<AffairPushParty> list = affairPushPartyDao.findByIds(ids);
		return list;
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPushParty affairPushParty) {
		if(affairPushParty.getCheckType()==null||"".equals(affairPushParty.getCheckType())){
			affairPushParty.setCheckType("1");
		}
		super.save(affairPushParty);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPushParty affairPushParty) {
		super.delete(affairPushParty);
	}

	public List<String> selectAllName(){
		return affairPushPartyDao.selectAllName();
	}

	public Integer selectNumber(String idNumber){
		return affairPushPartyDao.selectNumber(idNumber);
	}
}