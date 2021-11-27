/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairGonghuiPersonnelDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGonghuiPersonnel;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工会名册Service
 * @author mason.xv
 * @version 2019-12-30
 */
@Service
@Transactional(readOnly = true)
public class AffairGonghuiPersonnelService extends CrudService<AffairGonghuiPersonnelDao, AffairGonghuiPersonnel> {

	@Autowired
	private AffairGonghuiPersonnelDao affairGonghuiPersonnelDao;

	public AffairGonghuiPersonnel get(String id) {
		return super.get(id);
	}

	/**
	 * 批量查找
	 * @param ids
	 */
	public List<AffairGonghuiPersonnel> findByIds(List<String> ids){
		List<AffairGonghuiPersonnel> list = affairGonghuiPersonnelDao.findByIds(ids);
		return list;
	}
	
	public List<AffairGonghuiPersonnel> findList(AffairGonghuiPersonnel affairGonghuiPersonnel) {
		return super.findList(affairGonghuiPersonnel);
	}
	
	public Page<AffairGonghuiPersonnel> findPage(Page<AffairGonghuiPersonnel> page, AffairGonghuiPersonnel affairGonghuiPersonnel) {
		affairGonghuiPersonnel.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairGonghuiPersonnel);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairGonghuiPersonnel affairGonghuiPersonnel) {
		super.save(affairGonghuiPersonnel);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairGonghuiPersonnel affairGonghuiPersonnel) {
		super.delete(affairGonghuiPersonnel);
	}
	
}