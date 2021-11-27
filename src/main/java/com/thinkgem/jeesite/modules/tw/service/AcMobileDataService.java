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
import com.thinkgem.jeesite.modules.tw.entity.AcMobileData;
import com.thinkgem.jeesite.modules.tw.dao.AcMobileDataDao;

/**
 * 自动考评-警情信息Service
 * @author alan.wu
 * @version 2020-11-19
 */
@Service
@Transactional(readOnly = true)
public class AcMobileDataService extends CrudService<AcMobileDataDao, AcMobileData> {

	@Autowired
	private AcMobileDataDao acMobileDataDao;

	public AcMobileData get(String id) {
		return super.get(id);
	}
	
	public List<AcMobileData> findList(AcMobileData acMobileData) {
		return super.findList(acMobileData);
	}
	
	public Page<AcMobileData> findPage(Page<AcMobileData> page, AcMobileData acMobileData) {
		return super.findPage(page, acMobileData);
	}
	
	@Transactional(readOnly = false)
	public void save(AcMobileData acMobileData) {
		super.save(acMobileData);
	}
	
	@Transactional(readOnly = false)
	public void delete(AcMobileData acMobileData) {
		super.delete(acMobileData);
	}

	public List<AcMobileData> selectAllExcption(String unit,String checkTime){
		return acMobileDataDao.selectAllExcption(unit,checkTime);
	}

	public Integer selectNum(String idN,String yearL,String yearT){
		return acMobileDataDao.selectNum(idN,yearL,yearT);
	}
}