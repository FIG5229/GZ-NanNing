/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.warn.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.warn.dao.WarnReceiveDao;
import com.thinkgem.jeesite.modules.warn.entity.WarnReceive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 预警接收部门Service
 * @author eav.liu
 * @version 2019-11-28
 */
@Service
@Transactional(readOnly = true)
public class WarnReceiveService extends CrudService<WarnReceiveDao, WarnReceive> {

	@Autowired
	private WarnReceiveDao warnReceiveDao;

	public WarnReceive get(String id) {
		return super.get(id);
	}
	
	public List<WarnReceive> findList(WarnReceive warnReceive) {
		return super.findList(warnReceive);
	}
	
	public Page<WarnReceive> findPage(Page<WarnReceive> page, WarnReceive warnReceive) {
		return super.findPage(page, warnReceive);
	}
	
	@Transactional(readOnly = false)
	public void save(WarnReceive warnReceive) {
		super.save(warnReceive);
	}
	
	@Transactional(readOnly = false)
	public void delete(WarnReceive warnReceive) {
		super.delete(warnReceive);
	}

	@Transactional(readOnly = false)
	public void deleteByWarnId(String warnId) {
		warnReceiveDao.deleteByWarnId(warnId);
	}

}