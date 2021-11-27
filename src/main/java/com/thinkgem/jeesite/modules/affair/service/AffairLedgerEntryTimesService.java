/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairLedgerEntryTimes;
import com.thinkgem.jeesite.modules.affair.dao.AffairLedgerEntryTimesDao;

/**
 * 台账录入次数Service
 * @author cecil.li
 * @version 2020-12-29
 */
@Service
@Transactional(readOnly = true)
public class AffairLedgerEntryTimesService extends CrudService<AffairLedgerEntryTimesDao, AffairLedgerEntryTimes> {

	@Autowired
	private AffairLedgerEntryTimesDao affairLedgerEntryTimesDao;

	public AffairLedgerEntryTimes get(String id) {
		return super.get(id);
	}
	
	public List<AffairLedgerEntryTimes> findList(AffairLedgerEntryTimes affairLedgerEntryTimes) {
		return super.findList(affairLedgerEntryTimes);
	}
	
	public Page<AffairLedgerEntryTimes> findPage(Page<AffairLedgerEntryTimes> page, AffairLedgerEntryTimes affairLedgerEntryTimes) {
		affairLedgerEntryTimes.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairLedgerEntryTimes);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairLedgerEntryTimes affairLedgerEntryTimes) {
		super.save(affairLedgerEntryTimes);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairLedgerEntryTimes affairLedgerEntryTimes) {
		super.delete(affairLedgerEntryTimes);
	}

	public String monthTimes(String year, String month, String unitId){
		return affairLedgerEntryTimesDao.monthTimes(year, month, unitId);
	}

	public List<AffairLedgerEntryTimes> yearTimes(String year, String unitId){
		return affairLedgerEntryTimesDao.yearTimes(year, unitId);
	}

	@Transactional(readOnly = false)
	public void deleteAllInfo(String year, String unitId){
		affairLedgerEntryTimesDao.deleteAllInfo(year, unitId);
	}
	
}