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
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainOutsourceStatistics;
import com.thinkgem.jeesite.modules.affair.dao.AffairTrainOutsourceStatisticsDao;

/**
 * 委外培训统计Service
 * @author alan.wu
 * @version 2020-07-30
 */
@Service
@Transactional(readOnly = true)
public class AffairTrainOutsourceStatisticsService extends CrudService<AffairTrainOutsourceStatisticsDao, AffairTrainOutsourceStatistics> {

	public AffairTrainOutsourceStatistics get(String id) {
		return super.get(id);
	}
	
	public List<AffairTrainOutsourceStatistics> findList(AffairTrainOutsourceStatistics affairTrainOutsourceStatistics) {
		return super.findList(affairTrainOutsourceStatistics);
	}
	
	public Page<AffairTrainOutsourceStatistics> findPage(Page<AffairTrainOutsourceStatistics> page, AffairTrainOutsourceStatistics affairTrainOutsourceStatistics) {
		/*if ("105a3740108649a28b22ff633166de0e".equals(UserUtils.getUser().getId()) || "6dd6fe3db8144c17a45b9373a3bd3a6c".equals(UserUtils.getUser().getId()) || "a1fb3139ecfe4f2bb4e61abb18eae828".equals(UserUtils.getUser().getId())){
			affairTrainOutsourceStatistics.setUserId(UserUtils.getUser().getCompany().getId());
			affairTrainOutsourceStatistics.setOfficeId(UserUtils.getUser().getId());
		}else {
			affairTrainOutsourceStatistics.setUserId(UserUtils.getUser().getOffice().getId());
			affairTrainOutsourceStatistics.setOfficeId(UserUtils.getUser().getId());
		}*/
		affairTrainOutsourceStatistics.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTrainOutsourceStatistics);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTrainOutsourceStatistics affairTrainOutsourceStatistics) {
		super.save(affairTrainOutsourceStatistics);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTrainOutsourceStatistics affairTrainOutsourceStatistics) {
		super.delete(affairTrainOutsourceStatistics);
	}
	
}