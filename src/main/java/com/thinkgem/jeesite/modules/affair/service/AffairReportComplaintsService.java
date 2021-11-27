/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairReportComplaintsDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairReportComplaints;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 举报投诉Service
 * @author cecil.li
 * @version 2020-06-16
 */
@Service
@Transactional(readOnly = true)
public class AffairReportComplaintsService extends CrudService<AffairReportComplaintsDao, AffairReportComplaints> {

	public AffairReportComplaints get(String id) {
		return super.get(id);
	}
	
	public List<AffairReportComplaints> findList(AffairReportComplaints affairReportComplaints) {
		return super.findList(affairReportComplaints);
	}
	
	public Page<AffairReportComplaints> findPage(Page<AffairReportComplaints> page, AffairReportComplaints affairReportComplaints) {
		affairReportComplaints.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairReportComplaints);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairReportComplaints affairReportComplaints) {
		super.save(affairReportComplaints);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairReportComplaints affairReportComplaints) {
		super.delete(affairReportComplaints);
	}
	
}