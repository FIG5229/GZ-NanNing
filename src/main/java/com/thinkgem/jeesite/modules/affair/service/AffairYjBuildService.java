/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairYjBuild;
import com.thinkgem.jeesite.modules.affair.dao.AffairYjBuildDao;

/**
 * 硬件建设Service
 * @author kevin.jia
 * @version 2020-08-04
 */
@Service
@Transactional(readOnly = true)
public class AffairYjBuildService extends CrudService<AffairYjBuildDao, AffairYjBuild> {

	@Autowired
	private AffairYjBuildDao affairYjBuildDao;

	public AffairYjBuild get(String id) {
		return super.get(id);
	}
	
	public List<AffairYjBuild> findList(AffairYjBuild affairYjBuild) {
		return super.findList(affairYjBuild);
	}
	
	public Page<AffairYjBuild> findPage(Page<AffairYjBuild> page, AffairYjBuild affairYjBuild) {
		affairYjBuild.setUserId(UserUtils.getUser().getId());
		affairYjBuild.setOfficeId(UserUtils.getUser().getOffice().getId());
		return super.findPage(page, affairYjBuild);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairYjBuild affairYjBuild) {
		affairYjBuild.setContent(StringEscapeUtils.unescapeHtml4(affairYjBuild.getContent()));
		super.save(affairYjBuild);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairYjBuild affairYjBuild) {
		super.delete(affairYjBuild);
	}

	public List<AffairYjBuild> findByIds(List<String> ids){
		return affairYjBuildDao.findByIds(ids);
	}
	
}