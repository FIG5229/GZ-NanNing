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
import com.thinkgem.jeesite.modules.affair.entity.AffairTeacherApproval;
import com.thinkgem.jeesite.modules.affair.dao.AffairTeacherApprovalDao;

/**
 * 教官晋级审批Service
 * @author alan.wu
 * @version 2020-07-27
 */
@Service
@Transactional(readOnly = true)
public class AffairTeacherApprovalService extends CrudService<AffairTeacherApprovalDao, AffairTeacherApproval> {

	public AffairTeacherApproval get(String id) {
		return super.get(id);
	}
	
	public List<AffairTeacherApproval> findList(AffairTeacherApproval affairTeacherApproval) {
		return super.findList(affairTeacherApproval);
	}
	
	public Page<AffairTeacherApproval> findPage(Page<AffairTeacherApproval> page, AffairTeacherApproval affairTeacherApproval) {
		/*if ("105a3740108649a28b22ff633166de0e".equals(UserUtils.getUser().getId()) || "6dd6fe3db8144c17a45b9373a3bd3a6c".equals(UserUtils.getUser().getId()) || "a1fb3139ecfe4f2bb4e61abb18eae828".equals(UserUtils.getUser().getId())){
			affairTeacherApproval.setUserId(UserUtils.getUser().getCompany().getId());
			affairTeacherApproval.setOfficeId(UserUtils.getUser().getId());
		}else {
			affairTeacherApproval.setUserId(UserUtils.getUser().getOffice().getId());
			affairTeacherApproval.setOfficeId(UserUtils.getUser().getId());
		}*/
		affairTeacherApproval.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTeacherApproval);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTeacherApproval affairTeacherApproval) {
		super.save(affairTeacherApproval);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTeacherApproval affairTeacherApproval) {
		super.delete(affairTeacherApproval);
	}
	
}