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
import com.thinkgem.jeesite.modules.tw.entity.AcReport;
import com.thinkgem.jeesite.modules.tw.dao.AcReportDao;

/**
 * 自动考评-警情信息Service
 * @author alan.wu
 * @version 2020-11-19
 */
@Service
@Transactional(readOnly = true)
public class AcReportService extends CrudService<AcReportDao, AcReport> {

	@Autowired
	private AcReportDao acReportDao;

	public AcReport get(String id) {
		return super.get(id);
	}
	
	public List<AcReport> findList(AcReport acReport) {
		return super.findList(acReport);
	}
	
	public Page<AcReport> findPage(Page<AcReport> page, AcReport acReport) {
		return super.findPage(page, acReport);
	}
	
	@Transactional(readOnly = false)
	public void save(AcReport acReport) {
		super.save(acReport);
	}
	
	@Transactional(readOnly = false)
	public void delete(AcReport acReport) {
		super.delete(acReport);
	}

	public List<AcReport> selectAllExcption(String unit,String checkTime){
		return acReportDao.selectAllExcption(unit,checkTime);
	}

}