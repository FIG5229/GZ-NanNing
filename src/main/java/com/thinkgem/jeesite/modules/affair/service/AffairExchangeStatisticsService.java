/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.affair.dao.AffairSwapExerciseDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairSwapExercise;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairExchangeStatistics;
import com.thinkgem.jeesite.modules.affair.dao.AffairExchangeStatisticsDao;

/**
 * 交流锻炼统计表Service
 * @author alan.wu
 * @version 2020-07-29
 */
@Service
@Transactional(readOnly = true)
public class AffairExchangeStatisticsService extends CrudService<AffairExchangeStatisticsDao, AffairExchangeStatistics> {

	@Autowired
	private AffairSwapExerciseDao affairSwapExerciseDao;

	public AffairExchangeStatistics get(String id) {
		return super.get(id);
	}
	
	public List<AffairExchangeStatistics> findList(AffairExchangeStatistics affairExchangeStatistics) {
		return super.findList(affairExchangeStatistics);
	}
	
	public Page<AffairExchangeStatistics> findPage(Page<AffairExchangeStatistics> page, AffairExchangeStatistics affairExchangeStatistics) {
		/*if ("105a3740108649a28b22ff633166de0e".equals(UserUtils.getUser().getId()) || "6dd6fe3db8144c17a45b9373a3bd3a6c".equals(UserUtils.getUser().getId()) || "a1fb3139ecfe4f2bb4e61abb18eae828".equals(UserUtils.getUser().getId())){
			affairExchangeStatistics.setUserId(UserUtils.getUser().getCompany().getId());
			affairExchangeStatistics.setOfficeId(UserUtils.getUser().getId());
		}else {
			affairExchangeStatistics.setUserId(UserUtils.getUser().getOffice().getId());
			affairExchangeStatistics.setOfficeId(UserUtils.getUser().getId());
		}*/
		affairExchangeStatistics.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairExchangeStatistics);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairExchangeStatistics affairExchangeStatistics) {
		super.save(affairExchangeStatistics);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairExchangeStatistics affairExchangeStatistics) {
		super.delete(affairExchangeStatistics);
	}
	
}