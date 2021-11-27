/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairActivistDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairXunLiDao;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 训厉功能
 * @author tom.fu
 * @version 2020-8-20
 */
@Service
@Transactional(readOnly = true)
public class AffairXunLiService extends CrudService<AffairXunLiDao,AffairXunLi> {

	@Autowired
	private AffairXunLiDao affairXunLiDao;

	public AffairXunLi get(String id) {
		return super.get(id);
	}

	public List<AffairXunLi> findList(AffairXunLi affairXunLi) {
		return super.findList(affairXunLi);
	}

	public Page<AffairXunLi> findPage(Page<AffairXunLi> page, AffairXunLi affairXunLi) {
		affairXunLi.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairXunLi);
	}


	@Transactional(readOnly = false)
	public void save(AffairXunLi affairXunLi) {
		super.save(affairXunLi);
	}

	@Transactional(readOnly = false)
	public void delete(AffairXunLi affairXunLi) {
		super.delete(affairXunLi);
	}

	public AffairXunLi findPersonnel(String idNumber){
		return affairXunLiDao.findPersonnel(idNumber);
	}

	public List<AffairXunLiDetails> findXunLiTrainOutSource(String idNumber){
		return affairXunLiDao.findXunLiTrainOutSource(idNumber);
	}
	public AffairXunLiDetails findXunLiExchangeLearning(String idNumber){
		return affairXunLiDao.findXunLiExchangeLearning(idNumber);
	}
	public List<AffairXunLiDetails> findXunLiJob(String idNumber){
		List<AffairXunLiDetails> xunLiJob = affairXunLiDao.findXunLiJob(idNumber);
		for (AffairXunLiDetails affairXunLiDetails : xunLiJob) {
		}
		return xunLiJob;
	}

	/**
	 * 在线课程
	 * @return
	 */
	public AffairXunLiDetails findXunLiOnlineCourse(){
		return affairXunLiDao.findXunLiOnlineCourse();
	}

	/**
	 * 培训班课程
	 * @param
	 * @return
	 */
	public List<AffairXunLiDetails> findXunLiTrainin(String idNumber){
		return affairXunLiDao.findXunLiTrainin(idNumber);
	}

	public List<AffairXunLi> findXunLiExamination(String idNumber){
		return affairXunLiDao.findXunLiExamination(idNumber);
	}


	public List<AffairXunLi> findXunLiExaminationTwo(String idNumber){
		return affairXunLiDao.findXunLiExaminationTwo(idNumber);
	}


	/**
	 * 导出训厉管理
	 * @param idNumber
	 * @return
	 */
	public  List<Map<String, String>> findPageTwo(String idNumber) {
		return affairXunLiDao.findPageTwo(idNumber);
	}

	@Transactional(readOnly = false)
	public void insertOne(String appendfile,String idNumber) {
		affairXunLiDao.insertOne(appendfile,idNumber);
	}

	@Transactional(readOnly = false)
	public void insertTwo(AffairXunLi affairXunLi) {
		affairXunLiDao.insertTwo(affairXunLi);
	}


	public AffairXunLi findXueWei(String idNumber) {
		return affairXunLiDao.findXueWei(idNumber);
	}

	public AffairXunLi findJob(String idNumber) {
		return affairXunLiDao.findJob(idNumber);
	}

	public AffairXunLi findTra(String idNumber) {
		return affairXunLiDao.findTra(idNumber);
	}

	public List<AffairXunLi> findXueLi(String idNumber) {
		return affairXunLiDao.findXueLi(idNumber);
	}

	public AffairXunLi findMes(String idNumber) {
		return affairXunLiDao.findMes(idNumber);
	}

	public AffairXunLi findDates(String idNumber) {
		return affairXunLiDao.findDates(idNumber);
	}

	public PersonnelBase findPerson(String idNumber1) {
		return affairXunLiDao.findPerson(idNumber1);
	}
	public List<Map<String,String>> findOuts(String idNumber1) {
		return affairXunLiDao.findOuts(idNumber1);
	}
	public List<Map<String,String>> findJobs(String idNumber1) {
		return affairXunLiDao.findJobs(idNumber1);
	}
	public List<Map<String,String>> findAcms(String idNumber1) {
		return affairXunLiDao.findAcms(idNumber1);
	}

	public List<AffairXunLi> findOutsDates(String idNumber1) {
		return affairXunLiDao.findOutsDates(idNumber1);
	}

	public List<AffairXunLi> findjobsDates(String idNumber1) {

		return affairXunLiDao.findJobsDates(idNumber1);
	}

	public AffairJobTraining findjobTrining(String idNumber1) {
		return affairXunLiDao.findjobTrining(idNumber1);
	}
}