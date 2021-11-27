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
import com.thinkgem.jeesite.modules.affair.entity.AffairAdvancedPerson;
import com.thinkgem.jeesite.modules.affair.dao.AffairAdvancedPersonDao;

/**
 * 读书先进--个人Service
 * @author alan.wu
 * @version 2020-07-24
 */
@Service
@Transactional(readOnly = true)
public class AffairAdvancedPersonService extends CrudService<AffairAdvancedPersonDao, AffairAdvancedPerson> {

	public AffairAdvancedPerson get(String id) {
		return super.get(id);
	}
	
	public List<AffairAdvancedPerson> findList(AffairAdvancedPerson affairAdvancedPerson) {
		return super.findList(affairAdvancedPerson);
	}
	
	public Page<AffairAdvancedPerson> findPage(Page<AffairAdvancedPerson> page, AffairAdvancedPerson affairAdvancedPerson) {
		affairAdvancedPerson.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, affairAdvancedPerson);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairAdvancedPerson affairAdvancedPerson) {
		super.save(affairAdvancedPerson);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairAdvancedPerson affairAdvancedPerson) {
		super.delete(affairAdvancedPerson);
	}
	
}