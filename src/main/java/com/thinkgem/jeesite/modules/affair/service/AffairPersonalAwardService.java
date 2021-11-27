/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairPersonalAwardDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPersonalAward;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 工会个人表彰Service
 * @author cecil.li
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class AffairPersonalAwardService extends CrudService<AffairPersonalAwardDao, AffairPersonalAward> {

	public AffairPersonalAward get(String id) {
		return super.get(id);
	}
	
	public List<AffairPersonalAward> findList(AffairPersonalAward affairPersonalAward) {
		return super.findList(affairPersonalAward);
	}
	
	public Page<AffairPersonalAward> findPage(Page<AffairPersonalAward> page, AffairPersonalAward affairPersonalAward) {
		if (UserUtils.getUser().getId().equals("4c40b4dd2aee459286a37538978e6261") || UserUtils.getUser().getId().equals("cb97bf7e17d549f09b51375a98a8eb55") || UserUtils.getUser().getId().equals("bdab6b25fc694e74bf7c3d1363c0062e") || UserUtils.getUser().getId().equals("abb70c88ca9d42758c489838fdd0e33b")){
			affairPersonalAward.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairPersonalAward.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairPersonalAward);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPersonalAward affairPersonalAward) {
		super.save(affairPersonalAward);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPersonalAward affairPersonalAward) {
		super.delete(affairPersonalAward);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}

}