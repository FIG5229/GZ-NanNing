/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairKnowledgeReceive;
import com.thinkgem.jeesite.modules.affair.dao.AffairKnowledgeReceiveDao;

/**
 * 党规党章及党建知识关联Service
 * @author eav.liu
 * @version 2019-11-04
 */
@Service
@Transactional(readOnly = true)
public class AffairKnowledgeReceiveService extends CrudService<AffairKnowledgeReceiveDao, AffairKnowledgeReceive> {

	public AffairKnowledgeReceive get(String id) {
		return super.get(id);
	}
	
	public List<AffairKnowledgeReceive> findList(AffairKnowledgeReceive affairKnowledgeReceive) {
		return super.findList(affairKnowledgeReceive);
	}
	
	public Page<AffairKnowledgeReceive> findPage(Page<AffairKnowledgeReceive> page, AffairKnowledgeReceive affairKnowledgeReceive) {
		return super.findPage(page, affairKnowledgeReceive);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairKnowledgeReceive affairKnowledgeReceive) {
		super.save(affairKnowledgeReceive);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairKnowledgeReceive affairKnowledgeReceive) {
		super.delete(affairKnowledgeReceive);
	}
	
}