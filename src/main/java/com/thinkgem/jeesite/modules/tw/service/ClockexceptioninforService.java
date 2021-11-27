/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.tw.entity.Clockexceptioninfor;
import com.thinkgem.jeesite.modules.tw.dao.ClockexceptioninforDao;

/**
 * 自动考评-打卡异常记录Service
 * @author alan.wu
 * @version 2020-11-19
 */
@Service
@Transactional(readOnly = true)
public class ClockexceptioninforService extends CrudService<ClockexceptioninforDao, Clockexceptioninfor> {

	public Clockexceptioninfor get(String id) {
		return super.get(id);
	}
	
	public List<Clockexceptioninfor> findList(Clockexceptioninfor clockexceptioninfor) {
		return super.findList(clockexceptioninfor);
	}
	
	public Page<Clockexceptioninfor> findPage(Page<Clockexceptioninfor> page, Clockexceptioninfor clockexceptioninfor) {
		return super.findPage(page, clockexceptioninfor);
	}
	
	@Transactional(readOnly = false)
	public void save(Clockexceptioninfor clockexceptioninfor) {
		super.save(clockexceptioninfor);
	}
	
	@Transactional(readOnly = false)
	public void delete(Clockexceptioninfor clockexceptioninfor) {
		super.delete(clockexceptioninfor);
	}
	
}