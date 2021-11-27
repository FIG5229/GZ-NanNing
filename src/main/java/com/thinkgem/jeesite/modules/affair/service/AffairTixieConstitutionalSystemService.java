/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairTixieConstitutionalSystemDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTixieConstitutionalSystem;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 体协工作章程制度Service
 * @author cecil.li
 * @version 2019-11-07
 */
@Service
@Transactional(readOnly = true)
public class AffairTixieConstitutionalSystemService extends CrudService<AffairTixieConstitutionalSystemDao, AffairTixieConstitutionalSystem> {

	public AffairTixieConstitutionalSystem get(String id) {
		return super.get(id);
	}
	
	public List<AffairTixieConstitutionalSystem> findList(AffairTixieConstitutionalSystem affairTixieConstitutionalSystem) {
		return super.findList(affairTixieConstitutionalSystem);
	}
	
	public Page<AffairTixieConstitutionalSystem> findPage(Page<AffairTixieConstitutionalSystem> page, AffairTixieConstitutionalSystem affairTixieConstitutionalSystem) {
		affairTixieConstitutionalSystem.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTixieConstitutionalSystem);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTixieConstitutionalSystem affairTixieConstitutionalSystem) {
		affairTixieConstitutionalSystem.setMainContent(StringEscapeUtils.unescapeHtml4(affairTixieConstitutionalSystem.getMainContent()));
		super.save(affairTixieConstitutionalSystem);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTixieConstitutionalSystem affairTixieConstitutionalSystem) {
		super.delete(affairTixieConstitutionalSystem);
	}
	
}