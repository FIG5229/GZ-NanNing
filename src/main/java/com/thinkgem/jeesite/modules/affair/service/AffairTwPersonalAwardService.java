/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairTwPersonalAwardDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTwPersonalAward;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 团委个人表彰Service
 * @author cecil.li
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class AffairTwPersonalAwardService extends CrudService<AffairTwPersonalAwardDao, AffairTwPersonalAward> {

	@Autowired
	private AffairTwPersonalAwardDao affairTwPersonalAwardDao;

	public AffairTwPersonalAward get(String id) {
		return super.get(id);
	}
	
	public List<AffairTwPersonalAward> findList(AffairTwPersonalAward affairTwPersonalAward) {
		return super.findList(affairTwPersonalAward);
	}
	
	public Page<AffairTwPersonalAward> findPage(Page<AffairTwPersonalAward> page, AffairTwPersonalAward affairTwPersonalAward) {
		affairTwPersonalAward.setUserId(UserUtils.getUser().getOffice().getId());
		User user = UserUtils.getUser();
		affairTwPersonalAward.setCreateBy(user);
		affairTwPersonalAward.setCardNum(UserUtils.getUser().getId());
		affairTwPersonalAward.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTwPersonalAward);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTwPersonalAward affairTwPersonalAward) {
		if ("".equals(affairTwPersonalAward.getStatus())||affairTwPersonalAward.getStatus()== null) {
			affairTwPersonalAward.setStatus("3");
		}
		if("".equals(affairTwPersonalAward.getPushType())||affairTwPersonalAward.getPushType()==null){
			affairTwPersonalAward.setPushType("0");
		}
		super.save(affairTwPersonalAward);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTwPersonalAward affairTwPersonalAward) {
		super.delete(affairTwPersonalAward);
	}


	/**
	 * 批量查找
	 * @param ids
	 */
	public List<AffairTwPersonalAward> findByIds(List<String> ids){
		List<AffairTwPersonalAward> list = affairTwPersonalAwardDao.findByIds(ids);
		return list;
	}

	public Map<String, Object> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}
}