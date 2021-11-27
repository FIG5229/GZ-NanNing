/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairTwUnitAwardDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTwUnitAward;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 团委集体表彰Service
 * @author cecil.li
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class AffairTwUnitAwardService extends CrudService<AffairTwUnitAwardDao, AffairTwUnitAward> {
	@Autowired
	AffairTwUnitAwardDao affairTwUnitAwardDao;

	public AffairTwUnitAward get(String id) {
		return super.get(id);
	}
	
	public List<AffairTwUnitAward> findList(AffairTwUnitAward affairTwUnitAward) {
		return super.findList(affairTwUnitAward);
	}
	
	public Page<AffairTwUnitAward> findPage(Page<AffairTwUnitAward> page, AffairTwUnitAward affairTwUnitAward) {
		affairTwUnitAward.setUserOffice((UserUtils.getUser().getOffice().getId()));
		User user = UserUtils.getUser();
		affairTwUnitAward.setCreateBy(user);
		affairTwUnitAward.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTwUnitAward);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTwUnitAward affairTwUnitAward) {
		if ("".equals(affairTwUnitAward.getStatus())||affairTwUnitAward.getStatus()== null) {
			affairTwUnitAward.setStatus("3");
		}
		if("".equals(affairTwUnitAward.getPushType())||affairTwUnitAward.getPushType()==null){
			affairTwUnitAward.setPushType("0");
		}
		super.save(affairTwUnitAward);
	}

	/**
	 * 批量查找
	 * @param ids
	 */
	public List<AffairTwUnitAward> findByIds(List<String> ids){
		List<AffairTwUnitAward> list = affairTwUnitAwardDao.findByIds(ids);
		return list;
	}

	@Transactional(readOnly = false)
	public void delete(AffairTwUnitAward affairTwUnitAward) {
		super.delete(affairTwUnitAward);
	}

/*	@Transactional(readOnly = false)
	public void shenHe(AffairTwUnitAward affairTwUnitAward) {
		affairTwUnitAward.setUpdateDate(new Date());
		affairTwUnitAward.setShPerson(UserUtils.getUser().getName());
		affairTwUnitAwardDao.shenHe(affairTwUnitAward);
	}*/

	public Map<String, Object> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}

	public List<AffairTwUnitAward> selectList(String orgId){
		return affairTwUnitAwardDao.selectList(orgId);
	}

	public AffairTwUnitAward selectBean(String id){
		return affairTwUnitAwardDao.selectBean(id);
	}

}