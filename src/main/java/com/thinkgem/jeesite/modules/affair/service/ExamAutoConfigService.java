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
import com.thinkgem.jeesite.modules.affair.entity.ExamAutoConfig;
import com.thinkgem.jeesite.modules.affair.dao.ExamAutoConfigDao;

/**
 * 自动考评配置Service
 * @author danil.liu
 * @version 2020-12-15
 */
@Service
@Transactional(readOnly = true)
public class ExamAutoConfigService extends CrudService<ExamAutoConfigDao, ExamAutoConfig> {

	public ExamAutoConfig get(String id) {
		return super.get(id);
	}
	
	public List<ExamAutoConfig> findList(ExamAutoConfig examAutoConfig) {
		return super.findList(examAutoConfig);
	}
	
	public Page<ExamAutoConfig> findPage(Page<ExamAutoConfig> page, ExamAutoConfig examAutoConfig) {
		examAutoConfig.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, examAutoConfig);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamAutoConfig examAutoConfig) {
		super.save(examAutoConfig);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamAutoConfig examAutoConfig) {
		super.delete(examAutoConfig);
	}
	
}