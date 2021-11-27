/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairXzyDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairXzy;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 小种养Service
 * @author cecil.li
 * @version 2019-11-29
 */
@Service
@Transactional(readOnly = true)
public class AffairXzyService extends CrudService<AffairXzyDao, AffairXzy> {

	public AffairXzy get(String id) {
		return super.get(id);
	}
	
	public List<AffairXzy> findList(AffairXzy affairXzy) {
		return super.findList(affairXzy);
	}
	
	public Page<AffairXzy> findPage(Page<AffairXzy> page, AffairXzy affairXzy) {
		/*affairXzy.setUserId(UserUtils.getUser().getOffice().getId());
		affairXzy.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/
		if (UserUtils.getUser().getId().equals("4c40b4dd2aee459286a37538978e6261") || UserUtils.getUser().getId().equals("cb97bf7e17d549f09b51375a98a8eb55") || UserUtils.getUser().getId().equals("bdab6b25fc694e74bf7c3d1363c0062e") || UserUtils.getUser().getId().equals("abb70c88ca9d42758c489838fdd0e33b")){
			affairXzy.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairXzy.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairXzy);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairXzy affairXzy) {
		super.save(affairXzy);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairXzy affairXzy) {
		super.delete(affairXzy);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id, Integer year, Date mStartDate, Date mEndDate) {
		return dao.findInfoByCreateOrgId(id, year, mStartDate, mEndDate);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids, Integer year, Date mStartDate, Date mEndDate) {
		return dao.findInfoByCreateOrgIds(ids, year, mStartDate, mEndDate);
	}
}