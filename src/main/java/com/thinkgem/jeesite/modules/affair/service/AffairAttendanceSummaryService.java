/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairAttendanceSummaryDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairAttendanceSummary;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 考勤关联计算Service
 * @author cecil.li
 * @version 2020-06-02
 */
@Service
@Transactional(readOnly = true)
public class AffairAttendanceSummaryService extends CrudService<AffairAttendanceSummaryDao, AffairAttendanceSummary> {

	public AffairAttendanceSummary get(String id) {
		return super.get(id);
	}
	
	public List<AffairAttendanceSummary> findList(AffairAttendanceSummary affairAttendanceSummary) {
		return super.findList(affairAttendanceSummary);
	}
	
	public Page<AffairAttendanceSummary> findPage(Page<AffairAttendanceSummary> page, AffairAttendanceSummary affairAttendanceSummary) {
//		affairAttendanceSummary.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		//局/处劳资信息管理及各处单位账号
		if (UserUtils.getUser().getId().equals("a58a91c7d4db4cd4b639c863c0e48832")){
			affairAttendanceSummary.setUserId(UserUtils.getUser().getCompany().getId());
		}else if ( UserUtils.getUser().getId().equals("e25def61e95f4864b15d203d17fcce79")){
			affairAttendanceSummary.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("8478b98cb7e249a2afe133bed5b5e5d8")){
			affairAttendanceSummary.setUserId("888");
		}else if ( UserUtils.getUser().getId().equals("76722f985c9e4ee7968fbd2a5d2feb2b")){
			affairAttendanceSummary.setUserId("999");
		}else if (UserUtils.getUser().getId().equals("73d8a1dc64874b9a8dca494db37488af")){
			affairAttendanceSummary.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("3e0a7e6b415a40fc976735050c253362")){
			affairAttendanceSummary.setUserId("888");
		}else if (UserUtils.getUser().getId().equals("13be1cd2f87046909e9a835ae52ec3d4")){
			affairAttendanceSummary.setUserId("999");
		}
		else {
			affairAttendanceSummary.setUserId(UserUtils.getUser().getOffice().getId());
		}
		//数据来源 考勤录入子表
		return super.findPage(page, affairAttendanceSummary);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairAttendanceSummary affairAttendanceSummary) {
		super.save(affairAttendanceSummary);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairAttendanceSummary affairAttendanceSummary) {
		super.delete(affairAttendanceSummary);
	}
	
}