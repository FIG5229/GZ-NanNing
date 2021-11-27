/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;
import java.util.Optional;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairActive;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairInteriorInstructorLibrary;
import com.thinkgem.jeesite.modules.affair.dao.AffairInteriorInstructorLibraryDao;

/**
 * 内部教官Service
 * @author alan.wu
 * @version 2020-07-23
 */
@Service
@Transactional(readOnly = true)
public class AffairInteriorInstructorLibraryService extends CrudService<AffairInteriorInstructorLibraryDao, AffairInteriorInstructorLibrary> {

	@Autowired
	private OfficeService officeService;

	public AffairInteriorInstructorLibrary get(String id) {
		return super.get(id);
	}
	
	public List<AffairInteriorInstructorLibrary> findList(AffairInteriorInstructorLibrary affairInteriorInstructorLibrary) {
		return super.findList(affairInteriorInstructorLibrary);
	}
	
	public Page<AffairInteriorInstructorLibrary> findPage(Page<AffairInteriorInstructorLibrary> page, AffairInteriorInstructorLibrary affairInteriorInstructorLibrary) {
		/*if ("105a3740108649a28b22ff633166de0e".equals(UserUtils.getUser().getId()) || "6dd6fe3db8144c17a45b9373a3bd3a6c".equals(UserUtils.getUser().getId()) || "a1fb3139ecfe4f2bb4e61abb18eae828".equals(UserUtils.getUser().getId())){
			affairInteriorInstructorLibrary.setUserId(UserUtils.getUser().getCompany().getId());
			affairInteriorInstructorLibrary.setOfficeId(UserUtils.getUser().getId());
		}else {
			affairInteriorInstructorLibrary.setUserId(UserUtils.getUser().getOffice().getId());
			affairInteriorInstructorLibrary.setOfficeId(UserUtils.getUser().getId());
		}*/
		affairInteriorInstructorLibrary.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairInteriorInstructorLibrary);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairInteriorInstructorLibrary affairInteriorInstructorLibrary) {
		super.save(affairInteriorInstructorLibrary);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairInteriorInstructorLibrary affairInteriorInstructorLibrary) {
		super.delete(affairInteriorInstructorLibrary);
	}

	public Page<AffairInteriorInstructorLibrary> findJiaoGuanPage(Page<AffairInteriorInstructorLibrary> page, AffairInteriorInstructorLibrary affairInteriorInstructorLibrary) {
		String dateType = Optional.ofNullable(affairInteriorInstructorLibrary.getDateType()).orElseGet(() -> {return "";});
		String officeId = UserUtils.getUser().getOffice().getId();
		String companyId = UserUtils.getUser().getCompany().getId();

			String label = affairInteriorInstructorLibrary.getLabel();
			if (StringUtils.isNotBlank(label)){

				if (label.equals("局机关")){
					affairInteriorInstructorLibrary.setUnitId("top");
				}else if (label.equals("南宁处")){
					affairInteriorInstructorLibrary.setUnitId("34");
				}else if (label.equals("北海处")){
					affairInteriorInstructorLibrary.setUnitId("156");
				}else if (label.equals("柳州处")){
					affairInteriorInstructorLibrary.setUnitId("95");
				}
			}
		affairInteriorInstructorLibrary.setPage(page);
		return page.setList(dao.findJiaoGuanList(affairInteriorInstructorLibrary));
	}
	
}