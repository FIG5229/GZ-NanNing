/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairLaborDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairSalaryGatherDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLabor;
import com.thinkgem.jeesite.modules.affair.entity.AffairSalaryGather;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 个人工资汇总Service
 * @author cecil.li
 * @version 2020-01-19
 */
@Service
@Transactional(readOnly = true)
public class AffairSalaryGatherService extends CrudService<AffairSalaryGatherDao, AffairSalaryGather> {

	public AffairSalaryGather get(String id) {
		return super.get(id);
	}
	
	public List<AffairSalaryGather> findList(AffairSalaryGather affairSalaryGather) {
		return super.findList(affairSalaryGather);
	}
	
	public Page<AffairSalaryGather> findPage(Page<AffairSalaryGather> page, AffairSalaryGather affairSalaryGather) {
		affairSalaryGather.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairSalaryGather);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairSalaryGather affairSalaryGather) {
		super.save(affairSalaryGather);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairSalaryGather affairSalaryGather) {
		super.delete(affairSalaryGather);
	}
	
}