/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.affair.entity.AffairTnActivityRecord;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairDifficultHelp;
import com.thinkgem.jeesite.modules.affair.dao.AffairDifficultHelpDao;

/**
 * 困难帮扶申报Service
 * @author daniel.liu
 * @version 2020-06-03
 */
@Service
@Transactional(readOnly = true)
public class AffairDifficultHelpService extends CrudService<AffairDifficultHelpDao, AffairDifficultHelp> {


	@Autowired
	private AffairDifficultHelpDao affairDifficultHelpDao;
	public AffairDifficultHelp get(String id) {
		return super.get(id);
	}
	
	public List<AffairDifficultHelp> findList(AffairDifficultHelp affairDifficultHelp) {
		return super.findList(affairDifficultHelp);
	}
	
	public Page<AffairDifficultHelp> findPage(Page<AffairDifficultHelp> page, AffairDifficultHelp affairDifficultHelp) {
		affairDifficultHelp.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairDifficultHelp);
	}

	public List<AffairDifficultHelp> findByIds(List<String> ids){
		List<AffairDifficultHelp> list = affairDifficultHelpDao.findByIds(ids);
		return list;
	}
	
	@Transactional(readOnly = false)
	public void save(AffairDifficultHelp affairDifficultHelp) {
		super.save(affairDifficultHelp);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairDifficultHelp affairDifficultHelp) {
		super.delete(affairDifficultHelp);
	}
	
}