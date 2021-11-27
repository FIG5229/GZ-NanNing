/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairZkInfoDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairZkInfo;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 助困管理Service
 * @author cecil.li
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairZkInfoService extends CrudService<AffairZkInfoDao, AffairZkInfo> {

	public AffairZkInfo get(String id) {
		return super.get(id);
	}
	
	public List<AffairZkInfo> findList(AffairZkInfo affairZkInfo) {
		return super.findList(affairZkInfo);
	}
	
	public Page<AffairZkInfo> findPage(Page<AffairZkInfo> page, AffairZkInfo affairZkInfo) {
		/*affairZkInfo.setUserId(UserUtils.getUser().getOffice().getId());
		affairZkInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/
		if (UserUtils.getUser().getId().equals("4c40b4dd2aee459286a37538978e6261") || UserUtils.getUser().getId().equals("cb97bf7e17d549f09b51375a98a8eb55") || UserUtils.getUser().getId().equals("bdab6b25fc694e74bf7c3d1363c0062e") || UserUtils.getUser().getId().equals("abb70c88ca9d42758c489838fdd0e33b")){
			affairZkInfo.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairZkInfo.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairZkInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairZkInfo affairZkInfo) {
		super.save(affairZkInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairZkInfo affairZkInfo) {
		super.delete(affairZkInfo);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgId(id, year, startDate, endDate, month);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgIds(ids, year, startDate, endDate, month);
	}
	
}