/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairDeedsBriefingDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDeedsBriefing;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 事迹简报Service
 * @author cecil.li
 * @version 2019-11-07
 */
@Service
@Transactional(readOnly = true)
public class AffairDeedsBriefingService extends CrudService<AffairDeedsBriefingDao, AffairDeedsBriefing> {

	public AffairDeedsBriefing get(String id) {
		return super.get(id);
	}
	
	public List<AffairDeedsBriefing> findList(AffairDeedsBriefing affairDeedsBriefing) {
		return super.findList(affairDeedsBriefing);
	}
	
	public Page<AffairDeedsBriefing> findPage(Page<AffairDeedsBriefing> page, AffairDeedsBriefing affairDeedsBriefing) {
		affairDeedsBriefing.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairDeedsBriefing);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairDeedsBriefing affairDeedsBriefing) {
		affairDeedsBriefing.setMainContent(StringEscapeUtils.unescapeHtml4(affairDeedsBriefing.getMainContent()));
		super.save(affairDeedsBriefing);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairDeedsBriefing affairDeedsBriefing) {
		super.delete(affairDeedsBriefing);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}
}