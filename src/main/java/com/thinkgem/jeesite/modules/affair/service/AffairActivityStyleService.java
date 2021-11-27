/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairActivityStyleDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairActivityStyle;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 警官艺术团活动风采Service
 * @author cecil.li
 * @version 2019-11-07
 */
@Service
@Transactional(readOnly = true)
public class AffairActivityStyleService extends CrudService<AffairActivityStyleDao, AffairActivityStyle> {

	public AffairActivityStyle get(String id) {
		return super.get(id);
	}
	
	public List<AffairActivityStyle> findList(AffairActivityStyle affairActivityStyle) {
		return super.findList(affairActivityStyle);
	}
	
	public Page<AffairActivityStyle> findPage(Page<AffairActivityStyle> page, AffairActivityStyle affairActivityStyle) {
		affairActivityStyle.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairActivityStyle);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairActivityStyle affairActivityStyle) {
		affairActivityStyle.setMainContent(StringEscapeUtils.unescapeHtml4(affairActivityStyle.getMainContent()));
		super.save(affairActivityStyle);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairActivityStyle affairActivityStyle) {
		super.delete(affairActivityStyle);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}
}