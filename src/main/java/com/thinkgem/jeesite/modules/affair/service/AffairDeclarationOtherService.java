/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairDeclarationOther;
import com.thinkgem.jeesite.modules.affair.dao.AffairDeclarationOtherDao;

/**
 * 其他申报Service
 * @author daniel.liu
 * @version 2020-06-05
 */
@Service
@Transactional(readOnly = true)
public class AffairDeclarationOtherService extends CrudService<AffairDeclarationOtherDao, AffairDeclarationOther> {

	@Autowired
	private AffairDeclarationOtherDao affairDeclarationOtherDao;

	public AffairDeclarationOther get(String id) {
		return super.get(id);
	}
	
	public List<AffairDeclarationOther> findList(AffairDeclarationOther affairDeclarationOther) {
		return super.findList(affairDeclarationOther);
	}
	
	public Page<AffairDeclarationOther> findPage(Page<AffairDeclarationOther> page, AffairDeclarationOther affairDeclarationOther) {
		affairDeclarationOther.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairDeclarationOther);
	}

	public List<AffairDeclarationOther> findByIds(List<String> ids){
		List<AffairDeclarationOther> list = affairDeclarationOtherDao.findByIds(ids);
		return list;
	}
	
	@Transactional(readOnly = false)
	public void save(AffairDeclarationOther affairDeclarationOther) {
		super.save(affairDeclarationOther);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairDeclarationOther affairDeclarationOther) {
		super.delete(affairDeclarationOther);
	}
	
}