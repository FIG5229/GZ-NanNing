/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceStudyStatistics;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceStatistics;
import com.thinkgem.jeesite.modules.affair.dao.AffairPoliceStatisticsDao;

/**
 * 民警课程学习统计Service
 * @author alan.wu
 * @version 2020-07-17
 */
@Service
@Transactional(readOnly = true)
public class AffairPoliceStatisticsService extends CrudService<AffairPoliceStatisticsDao, AffairPoliceStatistics> {

	@Autowired
	private AffairPoliceStatisticsDao affairPoliceStatisticsDao;

	public AffairPoliceStatistics get(String id) {
		return super.get(id);
	}
	
	public List<AffairPoliceStatistics> findList(AffairPoliceStatistics affairPoliceStatistics) {
		return super.findList(affairPoliceStatistics);
	}
	
	public Page<AffairPoliceStatistics> findPage(Page<AffairPoliceStatistics> page, AffairPoliceStatistics affairPoliceStatistics) {
		/*if ("105a3740108649a28b22ff633166de0e".equals(UserUtils.getUser().getId()) || "6dd6fe3db8144c17a45b9373a3bd3a6c".equals(UserUtils.getUser().getId()) || "a1fb3139ecfe4f2bb4e61abb18eae828".equals(UserUtils.getUser().getId())){
			affairPoliceStatistics.setUserId(UserUtils.getUser().getCompany().getId());
			affairPoliceStatistics.setOfficeId(UserUtils.getUser().getId());
		}else {
			affairPoliceStatistics.setUserId(UserUtils.getUser().getOffice().getId());
			affairPoliceStatistics.setOfficeId(UserUtils.getUser().getId());
		}*/
		affairPoliceStatistics.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairPoliceStatistics);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPoliceStatistics affairPoliceStatistics) {
		super.save(affairPoliceStatistics);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPoliceStatistics affairPoliceStatistics) {
		super.delete(affairPoliceStatistics);
	}
	//民警学习统计报表-->根据警号查询课程详情
    public List<AffairPoliceStatistics> findByPoliceIdNumber(AffairPoliceStudyStatistics affairPoliceStudyStatistics) {
		return  affairPoliceStatisticsDao.findByPoliceIdNumber(affairPoliceStudyStatistics);
    }
}