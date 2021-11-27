/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairAttendanceFlagDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairAttendanceFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 节假日Service
 * @author cecil.li
 * @version 2020-06-30
 */
@Service
@Transactional(readOnly = true)
public class AffairAttendanceFlagService extends CrudService<AffairAttendanceFlagDao, AffairAttendanceFlag> {

	@Autowired
	private AffairAttendanceFlagDao affairAttendanceFlagDao;

	public AffairAttendanceFlag get(String id) {
		return super.get(id);
	}
	
	public List<AffairAttendanceFlag> findList(AffairAttendanceFlag affairAttendanceFlag) {
		return super.findList(affairAttendanceFlag);
	}
	
	public Page<AffairAttendanceFlag> findPage(Page<AffairAttendanceFlag> page, AffairAttendanceFlag affairAttendanceFlag) {
		return super.findPage(page, affairAttendanceFlag);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairAttendanceFlag affairAttendanceFlag) {
		super.save(affairAttendanceFlag);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairAttendanceFlag affairAttendanceFlag) {
		super.delete(affairAttendanceFlag);
	}

	public int findAllDay(Integer ye, Integer mo){
		return affairAttendanceFlagDao.findAllDay(ye,mo);
	}
}