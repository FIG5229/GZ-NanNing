/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairTransferShDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTransferSh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统内组织关系移交转接关联表Service
 * @author eav.liu
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class AffairTransferShService extends CrudService<AffairTransferShDao, AffairTransferSh> {

	@Autowired
	private AffairTransferShDao affairTransferShdao;

	public AffairTransferSh get(String id) {
		return super.get(id);
	}
	
	public List<AffairTransferSh> findList(AffairTransferSh affairTransferSh) {
		return super.findList(affairTransferSh);
	}
	
	public Page<AffairTransferSh> findPage(Page<AffairTransferSh> page, AffairTransferSh affairTransferSh) {
		return super.findPage(page, affairTransferSh);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTransferSh affairTransferSh) {
		super.save(affairTransferSh);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTransferSh affairTransferSh) {
		super.delete(affairTransferSh);
	}

	public AffairTransferSh findInfoByTranSferId(String id) {
		return affairTransferShdao.findInfoByTranSferId(id);
	}
}