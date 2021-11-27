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
import com.thinkgem.jeesite.modules.tw.entity.Patrolrecord;
import com.thinkgem.jeesite.modules.tw.dao.PatrolrecordDao;

/**
 * 自动考评-打卡异常信息Service
 * @author alan.wu
 * @version 2020-11-19
 */
@Service
@Transactional(readOnly = true)
public class PatrolrecordService extends CrudService<PatrolrecordDao, Patrolrecord> {

	@Autowired
	private PatrolrecordDao patrolrecordDao;

	public Patrolrecord get(String id) {
		return super.get(id);
	}
	
	public List<Patrolrecord> findList(Patrolrecord patrolrecord) {
		return super.findList(patrolrecord);
	}
	
	public Page<Patrolrecord> findPage(Page<Patrolrecord> page, Patrolrecord patrolrecord) {
		return super.findPage(page, patrolrecord);
	}
	
	@Transactional(readOnly = false)
	public void save(Patrolrecord patrolrecord) {
		super.save(patrolrecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(Patrolrecord patrolrecord) {
		super.delete(patrolrecord);
	}

	public List<Patrolrecord> selectAllExcption(String code,String yearL,String yearT){
		return patrolrecordDao.selectAllExcption(code,yearL,yearT);
	}
}