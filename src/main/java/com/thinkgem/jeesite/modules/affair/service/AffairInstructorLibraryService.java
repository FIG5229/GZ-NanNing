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
import com.thinkgem.jeesite.modules.affair.entity.AffairInstructorLibrary;
import com.thinkgem.jeesite.modules.affair.dao.AffairInstructorLibraryDao;

/**
 * 教官库Service
 * @author alan.wu
 * @version 2020-07-22
 */
@Service
@Transactional(readOnly = true)
public class AffairInstructorLibraryService extends CrudService<AffairInstructorLibraryDao, AffairInstructorLibrary> {

	public AffairInstructorLibrary get(String id) {
		return super.get(id);
	}
	
	public List<AffairInstructorLibrary> findList(AffairInstructorLibrary affairInstructorLibrary) {
		return super.findList(affairInstructorLibrary);
	}
	
	public Page<AffairInstructorLibrary> findPage(Page<AffairInstructorLibrary> page, AffairInstructorLibrary affairInstructorLibrary) {
		if ("105a3740108649a28b22ff633166de0e".equals(UserUtils.getUser().getId()) || "6dd6fe3db8144c17a45b9373a3bd3a6c".equals(UserUtils.getUser().getId()) || "a1fb3139ecfe4f2bb4e61abb18eae828".equals(UserUtils.getUser().getId())){
			affairInstructorLibrary.setUserId(UserUtils.getUser().getCompany().getId());
			affairInstructorLibrary.setOfficeId(UserUtils.getUser().getId());
		}else {
			affairInstructorLibrary.setUserId(UserUtils.getUser().getOffice().getId());
			affairInstructorLibrary.setOfficeId(UserUtils.getUser().getId());
		}
		return super.findPage(page, affairInstructorLibrary);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairInstructorLibrary affairInstructorLibrary) {
		super.save(affairInstructorLibrary);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairInstructorLibrary affairInstructorLibrary) {
		super.delete(affairInstructorLibrary);
	}
	
}