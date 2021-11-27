/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairGhActivityManageDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGhActivityManage;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工会活动管理Service
 * @author mason.xv
 * @version 2020-03-26
 */
@Service
@Transactional(readOnly = true)
public class AffairGhActivityManageService extends CrudService<AffairGhActivityManageDao, AffairGhActivityManage> {

	@Autowired
	private AffairGhActivityManageDao affairGhActivityManageDao;

	public AffairGhActivityManage get(String id) {
		return super.get(id);
	}
	
	public List<AffairGhActivityManage> findList(AffairGhActivityManage affairGhActivityManage) {
		affairGhActivityManage.setUserId(UserUtils.getUser().getId());
		return super.findList(affairGhActivityManage);
	}
	
	public Page<AffairGhActivityManage> findPage(Page<AffairGhActivityManage> page, AffairGhActivityManage affairGhActivityManage) {
		affairGhActivityManage.setUserId(UserUtils.getUser().getId());
		affairGhActivityManage.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairGhActivityManage);
	}
	public List<AffairGhActivityManage> findByIds(List<String> ids){
		List<AffairGhActivityManage> list = affairGhActivityManageDao.findByIds(ids);
		return list;
	}
	@Transactional(readOnly = false)
	public void save(AffairGhActivityManage affairGhActivityManage) {
	    if (affairGhActivityManage.getCheckType()==null||"".equals(affairGhActivityManage.getCheckType())){
            affairGhActivityManage.setCheckType("1");
        }
		super.save(affairGhActivityManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairGhActivityManage affairGhActivityManage) {
		super.delete(affairGhActivityManage);
	}
	
}