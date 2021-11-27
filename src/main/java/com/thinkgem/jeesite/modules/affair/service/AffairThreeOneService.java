/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairThreeOneDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairThreeOne;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 三会一课Service
 * @author cecil.li
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class AffairThreeOneService extends CrudService<AffairThreeOneDao, AffairThreeOne> {

	@Autowired
	private AffairThreeOneDao affairThreeOneDao;

	public AffairThreeOne get(String id) {
		return super.get(id);
	}
	
	public List<AffairThreeOne> findList(AffairThreeOne affairThreeOne) {
		return super.findList(affairThreeOne);
	}
	
	public Page<AffairThreeOne> findPage(Page<AffairThreeOne> page, AffairThreeOne affairThreeOne) {
		affairThreeOne.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairThreeOne);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairThreeOne affairThreeOne) {
		super.save(affairThreeOne);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairThreeOne affairThreeOne) {
		super.delete(affairThreeOne);
	}

	public List<String> selectAllYear(){
		return affairThreeOneDao.selectAllYear();
	}
	public List<String> selectallMonth(){
		return affairThreeOneDao.selectallMonth();
	}

	public List<String> selectAllName(){
		return affairThreeOneDao.selectAllName();
	}

	public String selectName(String groupId){
		return affairThreeOneDao.selectName(groupId);
	}


	public Integer selectTime(String unitId,String year,String tzbzwh){
		return affairThreeOneDao.selectTime(unitId,year,tzbzwh);
	}

	public Integer selectNum(String niandu,String time,String id){
		return affairThreeOneDao.selectNum(niandu,time,id);
	}

	public Integer selectTydhNum(String yearL,String yearT,String twId,String type){
		return affairThreeOneDao.selectTydhNum(yearL,yearT,twId,type);
	}

	public Integer selectNumber(String yearL,String yearT,String twName,String tnzzshh){
		return affairThreeOneDao.selectNumber(yearL,yearT,twName,tnzzshh);
	}

	public Integer selectNu(String unitId,String yearL,String yearT){
		return affairThreeOneDao.selectNu(unitId,yearL,yearT);
	}
	public Integer selectNuJ(String unitId,String yearL,String yearT){
		return affairThreeOneDao.selectNuJ(unitId,yearL,yearT);
	}

}