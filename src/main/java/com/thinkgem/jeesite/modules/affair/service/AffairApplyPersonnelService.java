/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.affair.dao.AffairApplicantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairApplyPersonnel;
import com.thinkgem.jeesite.modules.affair.dao.AffairApplyPersonnelDao;

/**
 * 报名人员Service
 * @author alan.wu
 * @version 2020-07-10
 */
@Service
@Transactional(readOnly = true)
public class AffairApplyPersonnelService extends CrudService<AffairApplyPersonnelDao, AffairApplyPersonnel> {

	@Autowired
	private AffairApplyPersonnelDao affairApplyPersonnelDao;

	public AffairApplyPersonnel get(String id) {
		return super.get(id);
	}
	
	public List<AffairApplyPersonnel> findList(AffairApplyPersonnel affairApplyPersonnel) {
		return super.findList(affairApplyPersonnel);
	}
	
	public Page<AffairApplyPersonnel> findPage(Page<AffairApplyPersonnel> page, AffairApplyPersonnel affairApplyPersonnel) {
		return super.findPage(page, affairApplyPersonnel);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairApplyPersonnel affairApplyPersonnel) {
		super.save(affairApplyPersonnel);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairApplyPersonnel affairApplyPersonnel) {
		super.delete(affairApplyPersonnel);
	}

	public List<AffairApplyPersonnel> selectAllPersonnelByClassId (String classId){
		List<AffairApplyPersonnel> affairApplyPersonnelList = affairApplyPersonnelDao.selectAllPersonnelByClassId(classId);
		return affairApplyPersonnelList;

	}

	public List<AffairApplyPersonnel> findLike (String classId,String name,String applyState){
		List<AffairApplyPersonnel> affairApplyPersonnelList = affairApplyPersonnelDao.findLike(classId,name,applyState);
		return affairApplyPersonnelList;

	}


}