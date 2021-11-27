/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairPersonnelMessage;
import com.thinkgem.jeesite.modules.affair.dao.AffairPersonnelMessageDao;

/**
 * 查看报名详细Service
 * @author tom.fu
 * @version 2020-08-12
 */
@Service
@Transactional(readOnly = true)
public class AffairPersonnelMessageService extends CrudService<AffairPersonnelMessageDao, AffairPersonnelMessage> {
	@Autowired
	private AffairPersonnelMessageDao affairPersonnelMessageDao;

	@Override
	public AffairPersonnelMessage get(String id) {
		return super.get(id);
	}
	@Override
	public List<AffairPersonnelMessage> findList(AffairPersonnelMessage affairPersonnelMessage) {
		return super.findList(affairPersonnelMessage);
	}
	@Override
	public Page<AffairPersonnelMessage> findPage(Page<AffairPersonnelMessage> page, AffairPersonnelMessage affairPersonnelMessage) {
		return super.findPage(page, affairPersonnelMessage);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(AffairPersonnelMessage affairPersonnelMessage) {
		super.save(affairPersonnelMessage);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(AffairPersonnelMessage affairPersonnelMessage) {
		super.delete(affairPersonnelMessage);
	}


	public List<AffairPersonnelMessage> findPageTwo(String id) {
		return affairPersonnelMessageDao.findPageTwo(id);
	}


}