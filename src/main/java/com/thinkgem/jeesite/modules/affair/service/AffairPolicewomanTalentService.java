/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairPolicewomanTalentDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPolicewomanTalent;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 各类特长人才Service
 * @author cecil.li
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairPolicewomanTalentService extends CrudService<AffairPolicewomanTalentDao, AffairPolicewomanTalent> {

	public AffairPolicewomanTalent get(String id) {
		return super.get(id);
	}
	
	public List<AffairPolicewomanTalent> findList(AffairPolicewomanTalent affairPolicewomanTalent) {
		return super.findList(affairPolicewomanTalent);
	}
	
	public Page<AffairPolicewomanTalent> findPage(Page<AffairPolicewomanTalent> page, AffairPolicewomanTalent affairPolicewomanTalent) {
		/*affairPolicewomanTalent.setUserId(UserUtils.getUser().getOffice().getId());
		affairPolicewomanTalent.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/
		if (UserUtils.getUser().getId().equals("4c40b4dd2aee459286a37538978e6261") || UserUtils.getUser().getId().equals("cb97bf7e17d549f09b51375a98a8eb55") || UserUtils.getUser().getId().equals("bdab6b25fc694e74bf7c3d1363c0062e") || UserUtils.getUser().getId().equals("abb70c88ca9d42758c489838fdd0e33b")){
			affairPolicewomanTalent.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairPolicewomanTalent.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairPolicewomanTalent);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPolicewomanTalent affairPolicewomanTalent) {
		affairPolicewomanTalent.setContent(StringEscapeUtils.unescapeHtml4(affairPolicewomanTalent.getContent()));
		super.save(affairPolicewomanTalent);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPolicewomanTalent affairPolicewomanTalent) {
		super.delete(affairPolicewomanTalent);
	}
	
}