/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairWorkDoneManageDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkDoneManage;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工作总结管理Service
 * @author Alan.wu
 * @version 2020-06-12
 */
@Service
@Transactional(readOnly = true)
public class AffairWorkDoneManageService extends CrudService<AffairWorkDoneManageDao, AffairWorkDoneManage> {

	@Autowired
	private AffairWorkDoneManageDao affairWorkDoneManageDao;

	public AffairWorkDoneManage get(String id) {
		return super.get(id);
	}
	
	public List<AffairWorkDoneManage> findList(AffairWorkDoneManage affairWorkDoneManage) {
		return super.findList(affairWorkDoneManage);
	}
	
	public Page<AffairWorkDoneManage> findPage(Page<AffairWorkDoneManage> page, AffairWorkDoneManage affairWorkDoneManage) {
		affairWorkDoneManage.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairWorkDoneManage);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairWorkDoneManage affairWorkDoneManage) {
		super.save(affairWorkDoneManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairWorkDoneManage affairWorkDoneManage) {
		super.delete(affairWorkDoneManage);
	}

	@Transactional(readOnly = false)
	public void shenHeSave(AffairWorkDoneManage affairWorkDoneManage) {
		affairWorkDoneManageDao.shenHeSave(affairWorkDoneManage);
	}
	
}