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
import com.thinkgem.jeesite.modules.affair.entity.AffairInformationReport;
import com.thinkgem.jeesite.modules.affair.dao.AffairInformationReportDao;

/**
 * 信息上报Service
 * @author cecil.li
 * @version 2020-12-29
 */
@Service
@Transactional(readOnly = true)
public class AffairInformationReportService extends CrudService<AffairInformationReportDao, AffairInformationReport> {

	public AffairInformationReport get(String id) {
		return super.get(id);
	}
	
	public List<AffairInformationReport> findList(AffairInformationReport affairInformationReport) {
		return super.findList(affairInformationReport);
	}
	
	public Page<AffairInformationReport> findPage(Page<AffairInformationReport> page, AffairInformationReport affairInformationReport) {
		affairInformationReport.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairInformationReport);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairInformationReport affairInformationReport) {
		super.save(affairInformationReport);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairInformationReport affairInformationReport) {
		super.delete(affairInformationReport);
	}
	
}