/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairOrganizationBuildSing2Dao;
import com.thinkgem.jeesite.modules.affair.entity.AffairOrganizationBuildSing2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 组织建设关联表2Service
 * @author cecil.li
 * @version 2019-12-16
 */
@Service
@Transactional(readOnly = true)
public class AffairOrganizationBuildSing2Service extends CrudService<AffairOrganizationBuildSing2Dao, AffairOrganizationBuildSing2> {

	public AffairOrganizationBuildSing2 get(String id) {
		return super.get(id);
	}
	
	public List<AffairOrganizationBuildSing2> findList(AffairOrganizationBuildSing2 affairOrganizationBuildSing2) {
		return super.findList(affairOrganizationBuildSing2);
	}
	
	public Page<AffairOrganizationBuildSing2> findPage(Page<AffairOrganizationBuildSing2> page, AffairOrganizationBuildSing2 affairOrganizationBuildSing2) {
		return super.findPage(page, affairOrganizationBuildSing2);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairOrganizationBuildSing2 affairOrganizationBuildSing2) {
		super.save(affairOrganizationBuildSing2);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairOrganizationBuildSing2 affairOrganizationBuildSing2) {
		super.delete(affairOrganizationBuildSing2);
	}

	@Transactional(readOnly = false)
	public void deleteByMainId(String obId){
		this.dao.deleteByMainId(obId);
	}

	@Transactional(readOnly = false)
	public void deleteByMainIds(List<String> obIds){
		dao.deleteByMainIds(obIds);
	};
	
}