/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.tw.entity.AcSaftyAudit;
import com.thinkgem.jeesite.modules.tw.dao.AcSaftyAuditDao;

/**
 * 自动考评-隐患信息Service
 * @author alan.wu
 * @version 2020-11-19
 */
@Service
@Transactional(readOnly = true)
public class AcSaftyAuditService extends CrudService<AcSaftyAuditDao, AcSaftyAudit> {

	@Autowired
	private AcSaftyAuditDao acSaftyAuditDao;

	public AcSaftyAudit get(String id) {
		return super.get(id);
	}
	
	public List<AcSaftyAudit> findList(AcSaftyAudit acSaftyAudit) {
		return super.findList(acSaftyAudit);
	}
	
	public Page<AcSaftyAudit> findPage(Page<AcSaftyAudit> page, AcSaftyAudit acSaftyAudit) {
		return super.findPage(page, acSaftyAudit);
	}
	
	@Transactional(readOnly = false)
	public void save(AcSaftyAudit acSaftyAudit) {
		super.save(acSaftyAudit);
	}
	
	@Transactional(readOnly = false)
	public void delete(AcSaftyAudit acSaftyAudit) {
		super.delete(acSaftyAudit);
	}

	public List<AcSaftyAudit> selectAllExcption(String unit,String checkTime){
		return acSaftyAuditDao.selectAllExcption(unit,checkTime);
	}

	
}