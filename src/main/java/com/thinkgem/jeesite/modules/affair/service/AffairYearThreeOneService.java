/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairYearThreeOneDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairYearThreeOne;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * &ldquo;三会一课&rdquo;录入Service
 * @author eav.liu
 * @version 2019-11-08
 */
@Service
@Transactional(readOnly = true)
public class AffairYearThreeOneService extends CrudService<AffairYearThreeOneDao, AffairYearThreeOne> {

	@Autowired
	private AffairYearThreeOneDao affairYearThreeOneDao;

	public AffairYearThreeOne get(String id) {
		return super.get(id);
	}
	
	public List<AffairYearThreeOne> findList(AffairYearThreeOne affairYearThreeOne) {
		return super.findList(affairYearThreeOne);
	}
	
	public Page<AffairYearThreeOne> findPage(Page<AffairYearThreeOne> page, AffairYearThreeOne affairYearThreeOne) {
		affairYearThreeOne.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairYearThreeOne);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairYearThreeOne affairYearThreeOne) {
		affairYearThreeOne.setStatus("3");//未审核
		super.save(affairYearThreeOne);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairYearThreeOne affairYearThreeOne) {
		super.delete(affairYearThreeOne);
	}

	@Transactional(readOnly = false)
	public void shenHeSave(AffairYearThreeOne affairYearThreeOne) {
		affairYearThreeOne.setUpdateDate(new Date());
		affairYearThreeOne.setShPerson(UserUtils.getUser().getName());
		affairYearThreeOneDao.shenHeSave(affairYearThreeOne);
	}

	public List<String> selectAllYear(){
		return affairYearThreeOneDao.selectAllYear();
	}

	public List<String> selectAllUnitId(){

		return affairYearThreeOneDao.selectAllUnitId();
	}
	public Integer selectTime(String id,String year,String type){
		return affairYearThreeOneDao.selectTime(id,year,type);
	}

	public String selectName(String unitId){
		return affairYearThreeOneDao.selectName(unitId);
	}

	public Integer selectNum(String year,String time,String id){
		return affairYearThreeOneDao.selectNum(year,time,id);
	}
	public Integer selectNumYear(String year,String id){
		return affairYearThreeOneDao.selectNumYear(year,id);
	}


	public List<AffairYearThreeOne> selectdzzzyThreeOne(Date startDate, Date endDate, String partyOrganization,String type) {
		return affairYearThreeOneDao.selectdzzzyThreeOne(startDate,endDate,partyOrganization,type);
	}
	public List<AffairYearThreeOne> selectdzbThreeOne(String startDate, String endDate, String partyOrganization) {
		return affairYearThreeOneDao.selectdzbThreeOne(startDate,endDate,partyOrganization);
	}

    public Integer selectHuiyiNumber(String unitId, String yearL, String yearT, String type) {
		return affairYearThreeOneDao.selectHuiyiNumber(unitId,yearL,yearT,type);
    }
}