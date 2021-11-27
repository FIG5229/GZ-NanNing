/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairTypicalPersonNews;
import com.thinkgem.jeesite.modules.affair.dao.AffairTypicalPersonNewsDao;

/**
 * 典型培树事迹Service
 * @author daniel.liu
 * @version 2020-06-18
 */
@Service
@Transactional(readOnly = true)
public class AffairTypicalPersonNewsService extends CrudService<AffairTypicalPersonNewsDao, AffairTypicalPersonNews> {

	public AffairTypicalPersonNews get(String id) {
		return super.get(id);
	}
	
	public List<AffairTypicalPersonNews> findList(AffairTypicalPersonNews affairTypicalPersonNews) {
		return super.findList(affairTypicalPersonNews);
	}
	
	public Page<AffairTypicalPersonNews> findPage(Page<AffairTypicalPersonNews> page, AffairTypicalPersonNews affairTypicalPersonNews) {
		return super.findPage(page, affairTypicalPersonNews);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTypicalPersonNews affairTypicalPersonNews) {
		super.save(affairTypicalPersonNews);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTypicalPersonNews affairTypicalPersonNews) {
		super.delete(affairTypicalPersonNews);
	}

	/*主表删除时  子表也删除*/
	@Transactional(readOnly = false)
	public void deleteByParentId(String typicalPersonId){
		dao.deleteByParentId(typicalPersonId);
	}

	/*主表批量删除时 子表进行批量删除*/
	@Transactional(readOnly = false)
	public void deleteByParentIds(List<String> ids) {
		dao.deleteByParentIds(ids);
	}
}