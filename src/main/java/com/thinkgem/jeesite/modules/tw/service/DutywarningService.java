/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.tw.entity.Dutywarning;
import com.thinkgem.jeesite.modules.tw.dao.DutywarningDao;

/**
 * 自动考评-打卡预警Service
 * @author alan.wu
 * @version 2020-11-19
 */
@Service
@Transactional(readOnly = true)
public class DutywarningService extends CrudService<DutywarningDao, Dutywarning> {

	public Dutywarning get(String id) {
		return super.get(id);
	}
	
	public List<Dutywarning> findList(Dutywarning dutywarning) {
		return super.findList(dutywarning);
	}
	
	public Page<Dutywarning> findPage(Page<Dutywarning> page, Dutywarning dutywarning) {
		return super.findPage(page, dutywarning);
	}
	
	@Transactional(readOnly = false)
	public void save(Dutywarning dutywarning) {
		super.save(dutywarning);
	}
	
	@Transactional(readOnly = false)
	public void delete(Dutywarning dutywarning) {
		super.delete(dutywarning);
	}
	
}