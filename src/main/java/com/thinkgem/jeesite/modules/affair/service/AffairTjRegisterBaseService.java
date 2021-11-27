/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairTjRegisterBaseDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTjRegisterBase;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 团籍注册Service
 * @author mason.xv
 * @version 2020-03-21
 */
@Service
@Transactional(readOnly = true)
public class AffairTjRegisterBaseService extends CrudService<AffairTjRegisterBaseDao, AffairTjRegisterBase> {
	@Autowired
	private AffairTjRegisterBaseDao affairTjRegisterBaseDao;
	public AffairTjRegisterBase get(String id) {
		return super.get(id);
	}
	
	public List<AffairTjRegisterBase> findList(AffairTjRegisterBase affairTjRegisterBase) {
		affairTjRegisterBase.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(affairTjRegisterBase);
	}
	
	public Page<AffairTjRegisterBase> findPage(Page<AffairTjRegisterBase> page, AffairTjRegisterBase affairTjRegisterBase) {
		return super.findPage(page, affairTjRegisterBase);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTjRegisterBase affairTjRegisterBase) {
	/*	AffairTjRegisterBase affairTjRegisterFromDb = affairTjRegisterBaseDao.findInfoByIdNumber(affairTjRegisterBase.getIdNumber());
		if(StringUtils.isNotBlank(affairTjRegisterFromDb.getId())){
			affairTjRegisterBase.setId(affairTjRegisterFromDb.getId());
			super.save(affairTjRegisterBase);
		}else{*/
			super.save(affairTjRegisterBase);
		/*}*/
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTjRegisterBase affairTjRegisterBase) {
		super.delete(affairTjRegisterBase);
	}
	public AffairTjRegisterBase getGroup(String idNumber){
		return affairTjRegisterBaseDao.getGroup(idNumber);
	}

	public List<String> findListByIdNo(String idNumber) {
		return affairTjRegisterBaseDao.findListByIdNo(idNumber);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNo(String idNumber) {
		affairTjRegisterBaseDao.deleteById(idNumber);
	}
}