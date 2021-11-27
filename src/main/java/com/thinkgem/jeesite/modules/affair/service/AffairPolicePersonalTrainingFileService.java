/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairPolicePersonalTrainingFile;
import com.thinkgem.jeesite.modules.affair.dao.AffairPolicePersonalTrainingFileDao;

/**
 * 民警个人训历档案报表Service
 * @author kevin.jia
 * @version 2020-09-28
 */
@Service
@Transactional(readOnly = true)
public class AffairPolicePersonalTrainingFileService extends CrudService<AffairPolicePersonalTrainingFileDao, AffairPolicePersonalTrainingFile> {

	@Autowired
	private AffairPolicePersonalTrainingFileDao personalTrainingFileDao;

	public AffairPolicePersonalTrainingFile get(String id) {
		return super.get(id);
	}
	
	public List<AffairPolicePersonalTrainingFile> findList(AffairPolicePersonalTrainingFile affairPolicePersonalTrainingFile) {
		return super.findList(affairPolicePersonalTrainingFile);
	}
	
	public Page<AffairPolicePersonalTrainingFile> findPage(Page<AffairPolicePersonalTrainingFile> page, AffairPolicePersonalTrainingFile affairPolicePersonalTrainingFile) {
		return super.findPage(page, affairPolicePersonalTrainingFile);
	}

	public Page<PersonnelBase> findPersonBasePage(Page<PersonnelBase> page, PersonnelBase personnelBase) {
		personnelBase.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		personnelBase.setPage(page);
		page.setList(personalTrainingFileDao.findPersonnelBaseList(personnelBase));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPolicePersonalTrainingFile affairPolicePersonalTrainingFile) {
		super.save(affairPolicePersonalTrainingFile);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPolicePersonalTrainingFile affairPolicePersonalTrainingFile) {
		super.delete(affairPolicePersonalTrainingFile);
	}

    public int selByIdNumber(String idNumber) {
		return personalTrainingFileDao.selByIdNumber(idNumber);
    }
	@Transactional(readOnly = false)
	public void deleteByIdNumber(String idNumber) {
		personalTrainingFileDao.deleteByIdNumber(idNumber);
	}

	public AffairPolicePersonalTrainingFile findPersonnaInfoByIdNumber(String idNumber) {
		return personalTrainingFileDao.findPersonnaInfoByIdNumber(idNumber);
	}

	public Map<String, String> findPersonnaMapByIdNumber(String idNumber) {
		return personalTrainingFileDao.findPersonnaMapByIdNumber(idNumber);
	}
}