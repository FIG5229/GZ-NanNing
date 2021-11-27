/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.entity.ExamAutoPeopleInfo;
import com.thinkgem.jeesite.modules.exam.dao.ExamAutoPeopleInfoDao;

/**
 * 人员身份Service
 * @author cecil.li
 * @version 2020-08-26
 */
@Service
@Transactional(readOnly = true)
public class ExamAutoPeopleInfoService extends CrudService<ExamAutoPeopleInfoDao, ExamAutoPeopleInfo> {

	@Autowired
	ExamAutoPeopleInfoDao examAutoPeopleInfoDao;

	public ExamAutoPeopleInfo get(String id) {
		return super.get(id);
	}
	
	public List<ExamAutoPeopleInfo> findList(ExamAutoPeopleInfo examAutoPeopleInfo) {
		return super.findList(examAutoPeopleInfo);
	}
	
	public Page<ExamAutoPeopleInfo> findPage(Page<ExamAutoPeopleInfo> page, ExamAutoPeopleInfo examAutoPeopleInfo) {
		return super.findPage(page, examAutoPeopleInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamAutoPeopleInfo examAutoPeopleInfo) {
		super.save(examAutoPeopleInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamAutoPeopleInfo examAutoPeopleInfo) {
		super.delete(examAutoPeopleInfo);
	}

	public List<String> selectAllLead(){
		return examAutoPeopleInfoDao.selectAllLead();
	}

	public List<String> selectAllLeadIdNumber(){
		return examAutoPeopleInfoDao.selectAllLeadIdNumber();
	}

	public List<String> selectAllPoliceIdNumber(){
		return examAutoPeopleInfoDao.selectAllPoliceIdNumber();
	}

	public String selectSJId(String name){
		return examAutoPeopleInfoDao.selectSJId(name);
	}

	public String selectNameById(String idNumber){return examAutoPeopleInfoDao.selectNameById(idNumber);}
}