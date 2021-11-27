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
import com.thinkgem.jeesite.modules.affair.entity.AffairSystemConstruction;
import com.thinkgem.jeesite.modules.affair.dao.AffairSystemConstructionDao;

/**
 * 制度建设Service
 * @author daniel.liu
 * @version 2020-07-07
 */
@Service
@Transactional(readOnly = true)
public class AffairSystemConstructionService extends CrudService<AffairSystemConstructionDao, AffairSystemConstruction> {

	public AffairSystemConstruction get(String id) {
		return super.get(id);
	}
	
	public List<AffairSystemConstruction> findList(AffairSystemConstruction affairSystemConstruction) {
		return super.findList(affairSystemConstruction);
	}
	
	public Page<AffairSystemConstruction> findPage(Page<AffairSystemConstruction> page, AffairSystemConstruction affairSystemConstruction) {
		affairSystemConstruction.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairSystemConstruction);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairSystemConstruction affairSystemConstruction) {
		affairSystemConstruction.setContent(StringEscapeUtils.unescapeHtml4(affairSystemConstruction.getContent()));
		super.save(affairSystemConstruction);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairSystemConstruction affairSystemConstruction) {
		super.delete(affairSystemConstruction);
	}
	
}