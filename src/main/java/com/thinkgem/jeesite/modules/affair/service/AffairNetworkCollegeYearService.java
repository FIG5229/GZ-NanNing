/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.affair.entity.AffairLearnPowerYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairNetworkCollegeYear;
import com.thinkgem.jeesite.modules.affair.dao.AffairNetworkCollegeYearDao;

/**
 * 中国干部网络学院--年度Service
 * @author alan.wu
 * @version 2020-08-03
 */
@Service
@Transactional(readOnly = true)
public class AffairNetworkCollegeYearService extends CrudService<AffairNetworkCollegeYearDao, AffairNetworkCollegeYear> {

	@Autowired
	private AffairNetworkCollegeYearDao affairNetworkCollegeYearDao;

	public AffairNetworkCollegeYear get(String id) {
		return super.get(id);
	}
	
	public List<AffairNetworkCollegeYear> findList(AffairNetworkCollegeYear affairNetworkCollegeYear) {
		return super.findList(affairNetworkCollegeYear);
	}
	
	public Page<AffairNetworkCollegeYear> findPage(Page<AffairNetworkCollegeYear> page, AffairNetworkCollegeYear affairNetworkCollegeYear) {
		return super.findPage(page, affairNetworkCollegeYear);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairNetworkCollegeYear affairNetworkCollegeYear) {
		super.save(affairNetworkCollegeYear);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairNetworkCollegeYear affairNetworkCollegeYear) {
		super.delete(affairNetworkCollegeYear);
	}


	public int findUserById(String yea , String userId){
		int i = affairNetworkCollegeYearDao.findUserById(yea,userId);
		return i;
	}

	@Transactional(readOnly = false)
	public void updateById(String yea,String userId,Double sumCode,Double thisYearCode,Double lastYearIntegral){
		affairNetworkCollegeYearDao.updateById(yea,userId,sumCode,thisYearCode,lastYearIntegral);
	}

	@Transactional(readOnly = false)
	public void creat(AffairNetworkCollegeYear affairNetworkCollegeYear){
		//super.save(affairLearnPowerYear);
		affairNetworkCollegeYearDao.creat(affairNetworkCollegeYear);
	}

	public String selectSumYearById(String year,String userId){
		String sum = affairNetworkCollegeYearDao.selectSumYearById(year,userId);
		return sum;
	}

	public String selectLastScore(String idNumber,String time){
		return affairNetworkCollegeYearDao.selectLastScore(idNumber,time);
	}

	public List<String> selectAllId(){
		return affairNetworkCollegeYearDao.selectAllId();
	}

	public String selectBean(String id,String year){
		return affairNetworkCollegeYearDao.selectBean(id,year);
	}

	public String selectUnit(String id){
		return affairNetworkCollegeYearDao.selectUnit(id);
	}

	public String selectName(String id){
		return affairNetworkCollegeYearDao.selectName(id);
	}

	public Page<AffairNetworkCollegeYear> findListByYear(Page<AffairNetworkCollegeYear> page, AffairNetworkCollegeYear affairNetworkCollegeYear, int year) {
		affairNetworkCollegeYear.setPage(page);
		List<AffairNetworkCollegeYear> affairNetworkCollegeYearList = affairNetworkCollegeYearDao.findListByYear(affairNetworkCollegeYear);
		for(AffairNetworkCollegeYear networkCollegeYear : affairNetworkCollegeYearList){
			networkCollegeYear.setTime(String.valueOf(year));
			networkCollegeYear.setLastYear(year-1);
			Double lastYearIntegral = affairNetworkCollegeYearDao.findlastYearIntegral(networkCollegeYear);
			if (lastYearIntegral==null){
				lastYearIntegral = 0.0;
			}
			networkCollegeYear.setLastYearIntegral(lastYearIntegral);
			if(networkCollegeYear.getThisYearIntegral()==null){
				networkCollegeYear.setThisYearIntegral(0.0);
			}
			networkCollegeYear.setThisYearStatistics(networkCollegeYear.getThisYearIntegral()+lastYearIntegral);

		}
		page.setList(affairNetworkCollegeYearList);
		return page;

	}
}