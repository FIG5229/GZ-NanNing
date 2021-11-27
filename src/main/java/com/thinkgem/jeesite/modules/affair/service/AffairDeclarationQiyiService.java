/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairDeclarationQiyiDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDeclarationQiyi;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工作申报Service
 * @author daniel.liu
 * @version 2020-07-01
 */
@Service
@Transactional(readOnly = true)
public class AffairDeclarationQiyiService extends CrudService<AffairDeclarationQiyiDao, AffairDeclarationQiyi> {

	@Autowired
	private AffairDeclarationQiyiDao affairDeclarationQiyiDao;

	public AffairDeclarationQiyi get(String id) {
		return super.get(id);
	}
	
	public List<AffairDeclarationQiyi> findList(AffairDeclarationQiyi affairDeclarationQiyi) {
		return super.findList(affairDeclarationQiyi);
	}
	
	public Page<AffairDeclarationQiyi> findPage(Page<AffairDeclarationQiyi> page, AffairDeclarationQiyi affairDeclarationQiyi) {
		affairDeclarationQiyi.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairDeclarationQiyi);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairDeclarationQiyi affairDeclarationQiyi) {
		super.save(affairDeclarationQiyi);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairDeclarationQiyi affairDeclarationQiyi) {
		super.delete(affairDeclarationQiyi);
	}

	public List<AffairDeclarationQiyi> findByIds(List<String> ids){
		List<AffairDeclarationQiyi> list = affairDeclarationQiyiDao.findByIds(ids);
		return list;
	}
}