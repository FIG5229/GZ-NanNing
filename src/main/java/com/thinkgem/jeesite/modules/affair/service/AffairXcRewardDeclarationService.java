/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairXcRewardDeclarationDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairXcRewardDeclaration;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 单位集体奖励申报Service
 * @author cecil.li
 * @version 2019-12-21
 */
@Service
@Transactional(readOnly = true)
public class AffairXcRewardDeclarationService extends CrudService<AffairXcRewardDeclarationDao, AffairXcRewardDeclaration> {

	@Autowired
	AffairXcRewardDeclarationDao affairXcRewardDeclarationDao;

	public AffairXcRewardDeclaration get(String id) {
		return super.get(id);
	}
	
	public List<AffairXcRewardDeclaration> findList(AffairXcRewardDeclaration affairXcRewardDeclaration) {
		return super.findList(affairXcRewardDeclaration);
	}
	
	public Page<AffairXcRewardDeclaration> findPage(Page<AffairXcRewardDeclaration> page, AffairXcRewardDeclaration affairXcRewardDeclaration) {
		/*审核单位审核自己审核的内容*/
		/*affairXcRewardDeclaration.setApprovalUnitId(UserUtils.getUser().getOffice().getId());
		affairXcRewardDeclaration.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/
		String id = UserUtils.getUser().getOffice().getId();
		affairXcRewardDeclaration.setUserId(UserUtils.getUser().getId());
		affairXcRewardDeclaration.setOfficeId(id);
		affairXcRewardDeclaration.setCreateBy(UserUtils.getUser());
		return super.findPage(page, affairXcRewardDeclaration);
	}

	@Transactional(readOnly = false)
	public void save(AffairXcRewardDeclaration affairXcRewardDeclaration) {
		super.save(affairXcRewardDeclaration);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairXcRewardDeclaration affairXcRewardDeclaration) {
		super.delete(affairXcRewardDeclaration);
	}

	/*@Transactional(readOnly = false)
	public void shenHe(AffairXcRewardDeclaration affairXcRewardDeclaration) {
		affairXcRewardDeclaration.setUpdateDate(new Date());
		affairXcRewardDeclaration.setShPerson(UserUtils.getUser().getName());
		affairXcRewardDeclarationDao.shenHe(affairXcRewardDeclaration);
	}*/
	/**
	 * 批量提交
	 * @param ids
	 */
	public List<AffairXcRewardDeclaration> findByIds(List<String> ids){
		List<AffairXcRewardDeclaration> list = affairXcRewardDeclarationDao.findByIds(ids);
		return list;
	}
}