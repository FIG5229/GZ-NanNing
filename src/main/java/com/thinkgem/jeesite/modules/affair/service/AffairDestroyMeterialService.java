/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairDestroyMeterialDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDestroyMeterial;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 销毁清册Service
 * @author mason.xv
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairDestroyMeterialService extends CrudService<AffairDestroyMeterialDao, AffairDestroyMeterial> {

	public AffairDestroyMeterial get(String id) {
		return super.get(id);
	}
	
	public List<AffairDestroyMeterial> findList(AffairDestroyMeterial affairDestroyMeterial) {
		return super.findList(affairDestroyMeterial);
	}
	
	public Page<AffairDestroyMeterial> findPage(Page<AffairDestroyMeterial> page, AffairDestroyMeterial affairDestroyMeterial) {
		affairDestroyMeterial.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairDestroyMeterial);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairDestroyMeterial affairDestroyMeterial) {
		super.save(affairDestroyMeterial);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairDestroyMeterial affairDestroyMeterial) {
		super.delete(affairDestroyMeterial);
	}
	
}