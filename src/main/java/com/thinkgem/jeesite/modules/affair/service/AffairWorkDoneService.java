/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairWorkDoneDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkDone;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 主体责任落实中的工作总结Service
 * @author Alan.wu
 * @version 2020-05-29
 */
@Service
@Transactional(readOnly = true)
public class AffairWorkDoneService extends CrudService<AffairWorkDoneDao, AffairWorkDone> {

	@Autowired
	AffairWorkDoneDao affairWorkDoneDao;

	public AffairWorkDone get(String id) {
		return super.get(id);
	}
	
	public List<AffairWorkDone> findList(AffairWorkDone affairWorkDone) {
		return super.findList(affairWorkDone);
	}
	
	public Page<AffairWorkDone> findPage(Page<AffairWorkDone> page, AffairWorkDone affairWorkDone) {
		affairWorkDone.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairWorkDone);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairWorkDone affairWorkDone) {
		super.save(affairWorkDone);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairWorkDone affairWorkDone) {
		super.delete(affairWorkDone);
	}

	@Transactional(readOnly = false)
	public void shenHeSave(AffairWorkDone affairWorkDone) {
		affairWorkDoneDao.shenHeSave(affairWorkDone);
	}
}