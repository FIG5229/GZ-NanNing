/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairWorkAttendanceDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkAttendance;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 考勤信息Service
 * @author mason.xv
 * @version 2019-11-15
 */
@Service
@Transactional(readOnly = true)
public class AffairWorkAttendanceService extends CrudService<AffairWorkAttendanceDao, AffairWorkAttendance> {

	public AffairWorkAttendance get(String id) {
		return super.get(id);
	}
	
	public List<AffairWorkAttendance> findList(AffairWorkAttendance affairWorkAttendance) {
		return super.findList(affairWorkAttendance);
	}
	
	public Page<AffairWorkAttendance> findPage(Page<AffairWorkAttendance> page, AffairWorkAttendance affairWorkAttendance) {
		affairWorkAttendance.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairWorkAttendance);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairWorkAttendance affairWorkAttendance) {
		super.save(affairWorkAttendance);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairWorkAttendance affairWorkAttendance) {
		super.delete(affairWorkAttendance);
	}
	
}