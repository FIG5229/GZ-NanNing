/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairStandardDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairStandard;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 个人合格党员标准Service
 * @author mason.xv
 * @version 2019-11-02
 */
@Service
@Transactional(readOnly = true)
public class AffairStandardService extends CrudService<AffairStandardDao, AffairStandard> {

	@Autowired
	AffairStandardDao affairStandardDao;

	public AffairStandard get(String id) {
		return super.get(id);
	}
	
	public List<AffairStandard> findList(AffairStandard affairStandard) {
		return super.findList(affairStandard);
	}
	
	public Page<AffairStandard> findPage(Page<AffairStandard> page, AffairStandard affairStandard) {
		affairStandard.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		//存放当前登录用户
		affairStandard.setCreateBy(UserUtils.getUser());
		return super.findPage(page, affairStandard);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairStandard affairStandard) {
		affairStandard.setStatus("3");//未审核
		super.save(affairStandard);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairStandard affairStandard) {
		super.delete(affairStandard);
	}

	@Transactional(readOnly = false)
	public void shenHe(AffairStandard affairStandard) {
		affairStandard.setUpdateDate(new Date());
		affairStandard.setShPerson(UserUtils.getUser().getName());
		affairStandardDao.shenHe(affairStandard);
	}
	
}