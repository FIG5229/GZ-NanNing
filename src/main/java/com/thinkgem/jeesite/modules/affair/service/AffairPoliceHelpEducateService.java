/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceHelpEducate;
import com.thinkgem.jeesite.modules.affair.dao.AffairPoliceHelpEducateDao;

/**
 * 民警帮教Service
 * @author daniel.liu
 * @version 2020-05-14
 */
@Service
@Transactional(readOnly = true)
public class AffairPoliceHelpEducateService extends CrudService<AffairPoliceHelpEducateDao, AffairPoliceHelpEducate> {

	public AffairPoliceHelpEducate get(String id) {
		return super.get(id);
	}
	
	public List<AffairPoliceHelpEducate> findList(AffairPoliceHelpEducate affairPoliceHelpEducate) {
		return super.findList(affairPoliceHelpEducate);
	}
	
	public Page<AffairPoliceHelpEducate> findPage(Page<AffairPoliceHelpEducate> page, AffairPoliceHelpEducate affairPoliceHelpEducate) {
		/*affairPoliceHelpEducate.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/
		String id = UserUtils.getUser().getOffice().getId();
		affairPoliceHelpEducate.setUserId(UserUtils.getUser().getId());
		affairPoliceHelpEducate.setOfficeId(id);
		affairPoliceHelpEducate.setCreateBy(UserUtils.getUser());
		return super.findPage(page, affairPoliceHelpEducate);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPoliceHelpEducate affairPoliceHelpEducate) {
		affairPoliceHelpEducate.setContent(StringEscapeUtils.unescapeHtml4(affairPoliceHelpEducate.getContent()));
		super.save(affairPoliceHelpEducate);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPoliceHelpEducate affairPoliceHelpEducate) {
		super.delete(affairPoliceHelpEducate);
	}
	
}