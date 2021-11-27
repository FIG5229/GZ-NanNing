/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairLearnPowerYear;
import com.thinkgem.jeesite.modules.affair.dao.AffairLearnPowerYearDao;

/**
 * 学习强国--年度统计Service
 * @author Alan
 * @version 2020-06-08
 */
@Service
@Transactional(readOnly = true)
public class AffairLearnPowerYearService extends CrudService<AffairLearnPowerYearDao, AffairLearnPowerYear> {

	@Autowired
	private AffairLearnPowerYearDao affairLearnPowerYearDao;

	public AffairLearnPowerYear get(String id) {
		return super.get(id);
	}
	
	public List<AffairLearnPowerYear> findList(AffairLearnPowerYear affairLearnPowerYear) {
		return super.findList(affairLearnPowerYear);
	}
	
	public Page<AffairLearnPowerYear> findPage(Page<AffairLearnPowerYear> page, AffairLearnPowerYear affairLearnPowerYear) {
		return super.findPage(page, affairLearnPowerYear);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairLearnPowerYear affairLearnPowerYear) {
		super.save(affairLearnPowerYear);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairLearnPowerYear affairLearnPowerYear) {
		super.delete(affairLearnPowerYear);
	}

	@Transactional(readOnly = false)
	public void creat(AffairLearnPowerYear affairLearnPowerYear){
		//super.save(affairLearnPowerYear);
		affairLearnPowerYearDao.creat(affairLearnPowerYear);
	}

    public Page<AffairLearnPowerYear> findListByYear(Page<AffairLearnPowerYear> page, AffairLearnPowerYear affairLearnPowerYear,int year) {
		affairLearnPowerYear.setPage(page);
		List<AffairLearnPowerYear> affairLearnPowerYearList = affairLearnPowerYearDao.findListByYear(affairLearnPowerYear);
		//List<AffairLearnPowerYear> resultList = new ArrayList<>();
		for(AffairLearnPowerYear learnPowerYear:affairLearnPowerYearList){
			learnPowerYear.setTime(String.valueOf(year));
			learnPowerYear.setLastYear(year-1);
			Double lastYearIntegral = affairLearnPowerYearDao.findlastYearIntegral(learnPowerYear);
			if (lastYearIntegral==null){
				lastYearIntegral = 0.0;
			}
			learnPowerYear.setLastYearIntegral(lastYearIntegral);
			if(learnPowerYear.getThisYearIntegral()==null){
				learnPowerYear.setThisYearIntegral(0.0);
			}
			learnPowerYear.setThisYearStatistics(learnPowerYear.getThisYearIntegral()+lastYearIntegral);

		}
		page.setList(affairLearnPowerYearList);
		return page;
    }
}