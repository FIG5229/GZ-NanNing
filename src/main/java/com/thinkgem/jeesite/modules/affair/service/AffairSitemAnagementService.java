/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.affair.entity.AffairActivityMien;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassManage;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairSitemAnagement;
import com.thinkgem.jeesite.modules.affair.dao.AffairSitemAnagementDao;

/**
 * 场地管理Service
 * @author tom.Fu
 * @version 2020-07-30
 */
@Service
@Transactional(readOnly = true)
public class AffairSitemAnagementService extends CrudService<AffairSitemAnagementDao, AffairSitemAnagement> {

	@Autowired
	private AffairSitemAnagementDao affairSitemAnagementDao;

	public AffairSitemAnagement get(String id) {
		return super.get(id);
	}
	
	public List<AffairSitemAnagement> findList(AffairSitemAnagement affairSitemAnagement) {
		return super.findList(affairSitemAnagement);
	}


	public Page<AffairSitemAnagement> findPage(Page<AffairSitemAnagement> page, AffairSitemAnagement affairSitemAnagement) {
		affairSitemAnagement.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, affairSitemAnagement);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairSitemAnagement affairSitemAnagement) {
		super.save(affairSitemAnagement);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairSitemAnagement affairSitemAnagement) {
		super.delete(affairSitemAnagement);
	}
	/**
	 * 根据id查询数据
	 * @param
	 * @return
	}*/
	public AffairClassManage findOne(String id){
		return affairSitemAnagementDao.findOne(id);
	}

}