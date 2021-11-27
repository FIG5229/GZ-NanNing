/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairTixieActivityStyleDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTixieActivityStyle;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 体协工作活动风采Service
 * @author cecil.li
 * @version 2019-11-07
 */
@Service
@Transactional(readOnly = true)
public class AffairTixieActivityStyleService extends CrudService<AffairTixieActivityStyleDao, AffairTixieActivityStyle> {

	public AffairTixieActivityStyle get(String id) {
		return super.get(id);
	}
	
	public List<AffairTixieActivityStyle> findList(AffairTixieActivityStyle affairTixieActivityStyle) {
		return super.findList(affairTixieActivityStyle);
	}
	
	public Page<AffairTixieActivityStyle> findPage(Page<AffairTixieActivityStyle> page, AffairTixieActivityStyle affairTixieActivityStyle) {
		affairTixieActivityStyle.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTixieActivityStyle);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTixieActivityStyle affairTixieActivityStyle) {
		affairTixieActivityStyle.setMainContent(StringEscapeUtils.unescapeHtml4(affairTixieActivityStyle.getMainContent()));
		super.save(affairTixieActivityStyle);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTixieActivityStyle affairTixieActivityStyle) {
		super.delete(affairTixieActivityStyle);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}
}