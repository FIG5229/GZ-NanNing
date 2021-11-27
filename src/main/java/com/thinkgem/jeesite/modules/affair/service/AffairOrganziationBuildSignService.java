/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairOrganziationBuildSignDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairOrganziationBuildSign;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 组织建设关联表Service
 * @author cecil.li
 * @version 2019-12-16
 */
@Service
@Transactional(readOnly = true)
public class AffairOrganziationBuildSignService extends CrudService<AffairOrganziationBuildSignDao, AffairOrganziationBuildSign> {

	public AffairOrganziationBuildSign get(String id) {
		return super.get(id);
	}
	
	public List<AffairOrganziationBuildSign> findList(AffairOrganziationBuildSign affairOrganziationBuildSign) {
		return super.findList(affairOrganziationBuildSign);
	}
	
	public Page<AffairOrganziationBuildSign> findPage(Page<AffairOrganziationBuildSign> page, AffairOrganziationBuildSign affairOrganziationBuildSign) {
		return super.findPage(page, affairOrganziationBuildSign);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairOrganziationBuildSign affairOrganziationBuildSign) {
		super.save(affairOrganziationBuildSign);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairOrganziationBuildSign affairOrganziationBuildSign) {
		super.delete(affairOrganziationBuildSign);
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