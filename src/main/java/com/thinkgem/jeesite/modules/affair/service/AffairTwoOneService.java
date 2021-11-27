/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairTwoOneDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTwoOne;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * &quot;两学一做&quot;专题学习Service
 * @author mason.xv
 * @version 2019-11-02
 */
@Service
@Transactional(readOnly = true)
public class AffairTwoOneService extends CrudService<AffairTwoOneDao, AffairTwoOne> {

	@Autowired
	private AffairTwoOneDao affairTwoOneDao;


	public AffairTwoOne get(String id) {
		return super.get(id);
	}
	
	public List<AffairTwoOne> findList(AffairTwoOne affairTwoOne) {
		return super.findList(affairTwoOne);
	}
	
	public Page<AffairTwoOne> findPage(Page<AffairTwoOne> page, AffairTwoOne affairTwoOne) {
		affairTwoOne.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTwoOne);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTwoOne affairTwoOne) {
		super.save(affairTwoOne);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTwoOne affairTwoOne) {
		super.delete(affairTwoOne);
	}

	public List<Map<String, Object>> findInfoByCreateOrgId(String id, Integer year, String month) {
		return dao.findInfoByCreateOrgId(id, year, month);
	}

	public List<Map<String, Object>> findInfoByCreateOrgIds(List<String> ids, Integer year, String month) {
		return dao.findInfoByCreateOrgIds(ids, year, month);
	}

	public List<String> selectAllYear(){
		return affairTwoOneDao.selectAllYear();
	}

	public List<String> selectAllMonth(){
		return affairTwoOneDao.selectAllMonth();
	}

	public Integer selectNum(String time,String id){
		return affairTwoOneDao.selectNum(time,id);
	}

	public Integer selectNumYear(String year,String id){
		return affairTwoOneDao.selectNumYear(year,id);
	}

}