/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairGzDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGz;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 固资管理Service
 * @author cecil.li
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairGzService extends CrudService<AffairGzDao, AffairGz> {

	@Autowired
	AffairGzDao affairGzDao;

	@Autowired
	OfficeDao officeDao;

	public AffairGz get(String id) {
		return super.get(id);
	}
	
	public List<AffairGz> findList(AffairGz affairGz) {
		return super.findList(affairGz);
	}
	
	public Page<AffairGz> findPage(Page<AffairGz> page, AffairGz affairGz) {
		affairGz.setCreateBy(UserUtils.getUser());
//		affairGz.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		if ("4c40b4dd2aee459286a37538978e6261".equals(UserUtils.getUser().getId())) {
			affairGz.setUserId(UserUtils.getUser().getCompany().getId());
			List<Office> childIds1 = officeDao.findChildById(affairGz.getTabFlag());
			ArrayList<String> officeIds = new ArrayList<>();
			for (Office office : childIds1) {
				officeIds.add(office.getId());
			}
			affairGz.setOfficeIds(officeIds);
		}else if("bdab6b25fc694e74bf7c3d1363c0062e".equals(UserUtils.getUser().getId()) || "cb97bf7e17d549f09b51375a98a8eb55".equals(UserUtils.getUser().getId()) || "abb70c88ca9d42758c489838fdd0e33b".equals(UserUtils.getUser().getId())){
			affairGz.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairGz.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairGz);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairGz affairGz) {
		if ("".equals(affairGz.getShType())||affairGz.getShType()== null)
		affairGz.setShType("3");
		super.save(affairGz);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairGz affairGz) {
		super.delete(affairGz);
	}

	@Transactional(readOnly = false)
	public void shenHe(AffairGz affairGz) {
		affairGz.setUpdateDate(new Date());
		affairGz.setShPerson(UserUtils.getUser().getName());
		affairGzDao.shenHe(affairGz);
		}

	public List<Map<String, Object>> findInfoByCreateOrgId(String id, Integer year, Date mStartDate, Date mEndDate) {
		return dao.findInfoByCreateOrgId(id, year, mStartDate, mEndDate);
	}

	public List<Map<String, Object>> findInfoByCreateOrgIds(List<String> ids, Integer year, Date mStartDate, Date mEndDate) {
		return dao.findInfoByCreateOrgIds(ids, year, mStartDate, mEndDate);
	}
	
}