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
import com.thinkgem.jeesite.modules.tw.entity.TPoliceMessage;
import com.thinkgem.jeesite.modules.tw.dao.TPoliceMessageDao;

/**
 * 警情预警-自动考评Service
 * @author alan.wu
 * @version 2020-10-16
 */
@Service
@Transactional(readOnly = true)
public class TPoliceMessageService extends CrudService<TPoliceMessageDao, TPoliceMessage> {

	@Autowired
	private TPoliceMessageDao tPoliceMessageDao;

	public TPoliceMessage get(String id) {
		return super.get(id);
	}
	
	public List<TPoliceMessage> findList(TPoliceMessage tPoliceMessage) {
		return super.findList(tPoliceMessage);
	}
	
	public Page<TPoliceMessage> findPage(Page<TPoliceMessage> page, TPoliceMessage tPoliceMessage) {
		return super.findPage(page, tPoliceMessage);
	}
	
	@Transactional(readOnly = false)
	public void save(TPoliceMessage tPoliceMessage) {
		super.save(tPoliceMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(TPoliceMessage tPoliceMessage) {
		super.delete(tPoliceMessage);
	}

	@Transactional(readOnly = false)
	public List<TPoliceMessage> selectAllList(String yearL,String yearT){
		return tPoliceMessageDao.selectAllList(yearL,yearT);
	}



}