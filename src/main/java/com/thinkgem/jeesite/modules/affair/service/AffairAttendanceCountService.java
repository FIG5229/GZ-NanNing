/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairAttendanceCountDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairAttendanceCount;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 考勤数据统计Service
 * @author cecil.li
 * @version 2020-06-01
 */
@Service
@Transactional(readOnly = true)
public class AffairAttendanceCountService extends CrudService<AffairAttendanceCountDao, AffairAttendanceCount> {

	public AffairAttendanceCount get(String id) {
		return super.get(id);
	}
	
	public List<AffairAttendanceCount> findList(AffairAttendanceCount affairAttendanceCount) {
		return super.findList(affairAttendanceCount);
	}
	
	public Page<AffairAttendanceCount> findPage(Page<AffairAttendanceCount> page, AffairAttendanceCount affairAttendanceCount) {
//		affairAttendanceCount.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		if (UserUtils.getUser().getId().equals("a58a91c7d4db4cd4b639c863c0e48832")){
			affairAttendanceCount.setUserId(UserUtils.getUser().getCompany().getId());
		}else if ( UserUtils.getUser().getId().equals("e25def61e95f4864b15d203d17fcce79")){
			affairAttendanceCount.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("8478b98cb7e249a2afe133bed5b5e5d8")){
			affairAttendanceCount.setUserId("888");
		}else if ( UserUtils.getUser().getId().equals("76722f985c9e4ee7968fbd2a5d2feb2b")){
			affairAttendanceCount.setUserId("999");
		}else if (UserUtils.getUser().getId().equals("73d8a1dc64874b9a8dca494db37488af")){
			affairAttendanceCount.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("3e0a7e6b415a40fc976735050c253362")){
			affairAttendanceCount.setUserId("888");
		}else if (UserUtils.getUser().getId().equals("13be1cd2f87046909e9a835ae52ec3d4")){
			affairAttendanceCount.setUserId("999");
		}
		else {
			affairAttendanceCount.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairAttendanceCount);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairAttendanceCount affairAttendanceCount) {
		super.save(affairAttendanceCount);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairAttendanceCount affairAttendanceCount) {
		super.delete(affairAttendanceCount);
	}
	
}