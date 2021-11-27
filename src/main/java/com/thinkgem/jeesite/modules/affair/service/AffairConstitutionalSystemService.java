/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairConstitutionalSystemDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairConstitutionalSystem;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 警官艺术团章程制度Service
 * @author cecil.li
 * @version 2019-11-07
 */
@Service
@Transactional(readOnly = true)
public class AffairConstitutionalSystemService extends CrudService<AffairConstitutionalSystemDao, AffairConstitutionalSystem> {

	public AffairConstitutionalSystem get(String id) {
		return super.get(id);
	}
	
	public List<AffairConstitutionalSystem> findList(AffairConstitutionalSystem affairConstitutionalSystem) {
		return super.findList(affairConstitutionalSystem);
	}
	
	public Page<AffairConstitutionalSystem> findPage(Page<AffairConstitutionalSystem> page, AffairConstitutionalSystem affairConstitutionalSystem) {
		affairConstitutionalSystem.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairConstitutionalSystem);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairConstitutionalSystem affairConstitutionalSystem) {
		affairConstitutionalSystem.setMainContent(StringEscapeUtils.unescapeHtml4(affairConstitutionalSystem.getMainContent()));
		super.save(affairConstitutionalSystem);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairConstitutionalSystem affairConstitutionalSystem) {
		super.delete(affairConstitutionalSystem);
	}
	
}