/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainingStatistics;
import com.thinkgem.jeesite.modules.affair.dao.AffairTrainingStatisticsDao;

/**
 * 培训班学习统计Service
 * @author alan.wu
 * @version 2020-07-17
 */
@Service
@Transactional(readOnly = true)
public class AffairTrainingStatisticsService extends CrudService<AffairTrainingStatisticsDao, AffairTrainingStatistics> {

	public AffairTrainingStatistics get(String id) {
		return super.get(id);
	}
	
	public List<AffairTrainingStatistics> findList(AffairTrainingStatistics affairTrainingStatistics) {
		return super.findList(affairTrainingStatistics);
	}
	
	public Page<AffairTrainingStatistics> findPage(Page<AffairTrainingStatistics> page, AffairTrainingStatistics affairTrainingStatistics) {
		/*if ("105a3740108649a28b22ff633166de0e".equals(UserUtils.getUser().getId()) || "6dd6fe3db8144c17a45b9373a3bd3a6c".equals(UserUtils.getUser().getId()) || "a1fb3139ecfe4f2bb4e61abb18eae828".equals(UserUtils.getUser().getId())){
			affairTrainingStatistics.setUserId(UserUtils.getUser().getCompany().getId());
			affairTrainingStatistics.setOfficeId(UserUtils.getUser().getId());
		}else {
			affairTrainingStatistics.setUserId(UserUtils.getUser().getOffice().getId());
			affairTrainingStatistics.setOfficeId(UserUtils.getUser().getId());
		}*/
		affairTrainingStatistics.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTrainingStatistics);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTrainingStatistics affairTrainingStatistics) {
		super.save(affairTrainingStatistics);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTrainingStatistics affairTrainingStatistics) {
		super.delete(affairTrainingStatistics);
	}
	
}