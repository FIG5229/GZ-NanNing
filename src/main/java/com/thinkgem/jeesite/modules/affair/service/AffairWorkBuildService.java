/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairWorkBuildDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkBuild;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 党建工作Service
 * @author Alan
 * @version 2020-06-03
 */
@Service
@Transactional(readOnly = true)
public class AffairWorkBuildService extends CrudService<AffairWorkBuildDao, AffairWorkBuild> {

	@Autowired
	private AffairWorkBuildDao affairWorkBuildDao;

	public AffairWorkBuild get(String id) {
		return super.get(id);
	}
	
	public List<AffairWorkBuild> findList(AffairWorkBuild affairWorkBuild) {
		return super.findList(affairWorkBuild);
	}
	
	public Page<AffairWorkBuild> findPage(Page<AffairWorkBuild> page, AffairWorkBuild affairWorkBuild) {
		affairWorkBuild.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairWorkBuild);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairWorkBuild affairWorkBuild) {
		super.save(affairWorkBuild);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairWorkBuild affairWorkBuild) {
		super.delete(affairWorkBuild);
	}

	@Transactional(readOnly = false)
	public void shenHeSave(AffairWorkBuild affairWorkBuild) {
		affairWorkBuildDao.shenHeSave(affairWorkBuild);
	}
}