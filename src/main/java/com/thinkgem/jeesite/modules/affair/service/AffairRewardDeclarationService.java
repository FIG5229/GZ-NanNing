/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairRewardDeclarationDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairRewardDeclaration;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 个人奖励申报Service
 * @author cecil.li
 * @version 2019-12-21
 */
@Service
@Transactional(readOnly = true)
public class AffairRewardDeclarationService extends CrudService<AffairRewardDeclarationDao, AffairRewardDeclaration> {

	@Autowired
	AffairRewardDeclarationDao affairRewardDeclarationDao;

	public AffairRewardDeclaration get(String id) {
		return super.get(id);
	}
	
	public List<AffairRewardDeclaration> findList(AffairRewardDeclaration affairRewardDeclaration) {
		return super.findList(affairRewardDeclaration);
	}
	
	public Page<AffairRewardDeclaration> findPage(Page<AffairRewardDeclaration> page, AffairRewardDeclaration affairRewardDeclaration) {
		affairRewardDeclaration.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairRewardDeclaration);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairRewardDeclaration affairRewardDeclaration) {
		super.save(affairRewardDeclaration);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairRewardDeclaration affairRewardDeclaration) {
		super.delete(affairRewardDeclaration);
	}

	/*@Transactional(readOnly = false)
	public void shenHe(AffairRewardDeclaration affairRewardDeclaration) {
		affairRewardDeclaration.setUpdateDate(new Date());
		affairRewardDeclaration.setShPerson(UserUtils.getUser().getName());
		affairRewardDeclarationDao.shenHe(affairRewardDeclaration);
	}*/
	/**
	 * 批量提交
	 * @param ids
	 */
	public List<AffairRewardDeclaration> findByIds(List<String> ids){
		List<AffairRewardDeclaration> list = affairRewardDeclarationDao.findByIds(ids);
		return list;
	}
}