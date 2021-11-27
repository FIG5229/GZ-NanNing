/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.tw.entity.Punchingpointinfor;
import com.thinkgem.jeesite.modules.tw.dao.PunchingpointinforDao;

/**
 * 自动考评-打卡信息Service
 * @author alan.wu
 * @version 2020-11-19
 */
@Service
@Transactional(readOnly = true)
public class PunchingpointinforService extends CrudService<PunchingpointinforDao, Punchingpointinfor> {

	public Punchingpointinfor get(String id) {
		return super.get(id);
	}
	
	public List<Punchingpointinfor> findList(Punchingpointinfor punchingpointinfor) {
		return super.findList(punchingpointinfor);
	}
	
	public Page<Punchingpointinfor> findPage(Page<Punchingpointinfor> page, Punchingpointinfor punchingpointinfor) {
		return super.findPage(page, punchingpointinfor);
	}
	
	@Transactional(readOnly = false)
	public void save(Punchingpointinfor punchingpointinfor) {
		super.save(punchingpointinfor);
	}
	
	@Transactional(readOnly = false)
	public void delete(Punchingpointinfor punchingpointinfor) {
		super.delete(punchingpointinfor);
	}
	
}