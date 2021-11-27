/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.affair.entity.AffairSendTeacher;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairCertificateManagement;
import com.thinkgem.jeesite.modules.affair.dao.AffairCertificateManagementDao;

/**
 * 证书模板管理Service
 * @author jack.xu
 * @version 2020-07-23
 */
@Service
@Transactional(readOnly = true)
public class AffairCertificateManagementService extends CrudService<AffairCertificateManagementDao, AffairCertificateManagement> {
	@Autowired
	private AffairCertificateManagementDao affairCertificateManagementDao;

	public AffairCertificateManagement get(String id) {
		return super.get(id);
	}
	
	public List<AffairCertificateManagement> findList(AffairCertificateManagement affairCertificateManagement) {
		affairCertificateManagement.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(affairCertificateManagement);
	}
	
	public Page<AffairCertificateManagement> findPage(Page<AffairCertificateManagement> page, AffairCertificateManagement affairCertificateManagement) {
		affairCertificateManagement.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairCertificateManagement);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCertificateManagement affairCertificateManagement) {
		super.save(affairCertificateManagement);
	}

	/**
	 * 批量查找
	 * @param
	 * */
	public List<AffairCertificateManagement> findByIds(List<String> ids){
		List<AffairCertificateManagement> list = affairCertificateManagementDao.findByIds(ids);
		return list;
	}

	@Transactional(readOnly = false)
	public void delete(AffairCertificateManagement affairCertificateManagement) {
		super.delete(affairCertificateManagement);
	}
	
}