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
import com.thinkgem.jeesite.modules.affair.entity.AffairAdminStatistics;
import com.thinkgem.jeesite.modules.affair.dao.AffairAdminStatisticsDao;

/**
 * 管理员信息统计Service
 * @author alan.wu
 * @version 2020-07-24
 */
@Service
@Transactional(readOnly = true)
public class AffairAdminStatisticsService extends CrudService<AffairAdminStatisticsDao, AffairAdminStatistics> {

	public AffairAdminStatistics get(String id) {
		return super.get(id);
	}
	
	public List<AffairAdminStatistics> findList(AffairAdminStatistics affairAdminStatistics) {
		return super.findList(affairAdminStatistics);
	}
	
	public Page<AffairAdminStatistics> findPage(Page<AffairAdminStatistics> page, AffairAdminStatistics affairAdminStatistics) {
		/*if ("105a3740108649a28b22ff633166de0e".equals(UserUtils.getUser().getId()) || "6dd6fe3db8144c17a45b9373a3bd3a6c".equals(UserUtils.getUser().getId()) || "a1fb3139ecfe4f2bb4e61abb18eae828".equals(UserUtils.getUser().getId())){
			affairAdminStatistics.setUserId(UserUtils.getUser().getCompany().getId());
			affairAdminStatistics.setOfficeId(UserUtils.getUser().getId());
		}else {
			affairAdminStatistics.setUserId(UserUtils.getUser().getOffice().getId());
			affairAdminStatistics.setOfficeId(UserUtils.getUser().getId());
		}*/
		affairAdminStatistics.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairAdminStatistics);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairAdminStatistics affairAdminStatistics) {
		super.save(affairAdminStatistics);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairAdminStatistics affairAdminStatistics) {
		super.delete(affairAdminStatistics);
	}
	
}