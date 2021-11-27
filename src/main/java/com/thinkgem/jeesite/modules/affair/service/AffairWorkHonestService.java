/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairWorkHonestDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkHonest;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 党风廉政建设Service
 * @author Alan
 * @version 2020-06-04
 */
@Service
@Transactional(readOnly = true)
public class AffairWorkHonestService extends CrudService<AffairWorkHonestDao, AffairWorkHonest> {

	@Autowired
	private AffairWorkHonestDao affairWorkHonestDao;

	public AffairWorkHonest get(String id) {
		return super.get(id);
	}
	
	public List<AffairWorkHonest> findList(AffairWorkHonest affairWorkHonest) {
		return super.findList(affairWorkHonest);
	}
	
	public Page<AffairWorkHonest> findPage(Page<AffairWorkHonest> page, AffairWorkHonest affairWorkHonest) {
		affairWorkHonest.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairWorkHonest);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairWorkHonest affairWorkHonest) {
		super.save(affairWorkHonest);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairWorkHonest affairWorkHonest) {
		super.delete(affairWorkHonest);
	}

	@Transactional(readOnly = false)
	public void shenHeSave(AffairWorkHonest affairWorkHonest) {
		affairWorkHonestDao.shenHeSave(affairWorkHonest);
	}
}