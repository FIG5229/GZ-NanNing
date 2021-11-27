/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairMediaManagementDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairMediaManagement;
import com.thinkgem.jeesite.modules.affair.entity.AffairTwUnitAward;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 刊用情况Service
 * @author cecil.li
 * @version 2019-11-07
 */
@Service
@Transactional(readOnly = true)
public class AffairMediaManagementService extends CrudService<AffairMediaManagementDao, AffairMediaManagement> {
	@Autowired
	AffairMediaManagementDao affairMediaManagementDao;
	public AffairMediaManagement get(String id) {
		return super.get(id);
	}
	
	public List<AffairMediaManagement> findList(AffairMediaManagement affairMediaManagement) {
		return super.findList(affairMediaManagement);
	}
	
	public Page<AffairMediaManagement> findPage(Page<AffairMediaManagement> page, AffairMediaManagement affairMediaManagement) {
		affairMediaManagement.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairMediaManagement);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairMediaManagement affairMediaManagement) {
		affairMediaManagement.setContent(StringEscapeUtils.unescapeHtml4(affairMediaManagement.getContent()));
		if ("".equals(affairMediaManagement.getStatus())||affairMediaManagement.getStatus()== null)
			affairMediaManagement.setStatus("3");
		super.save(affairMediaManagement);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairMediaManagement affairMediaManagement) {
		super.delete(affairMediaManagement);
	}

	/**
	 * 批量查找
	 * @param ids
	 */
	public List<AffairMediaManagement> findByIds(List<String> ids){
		List<AffairMediaManagement> list = affairMediaManagementDao.findByIds(ids);
		return list;
	}

}