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
import com.thinkgem.jeesite.modules.affair.entity.AffairCertificateAdministrator;
import com.thinkgem.jeesite.modules.affair.dao.AffairCertificateAdministratorDao;

/**
 * 证书管理员Service
 * @author jack.xu
 * @version 2020-07-27
 */
@Service
@Transactional(readOnly = true)
public class AffairCertificateAdministratorService extends CrudService<AffairCertificateAdministratorDao, AffairCertificateAdministrator> {

	@Autowired
	private AffairCertificateAdministratorDao affairCertificateAdministratorDao;

	public AffairCertificateAdministrator get(String id) {
		return super.get(id);
	}
	
	public List<AffairCertificateAdministrator> findList(AffairCertificateAdministrator affairCertificateAdministrator) {
		affairCertificateAdministrator.getSqlMap().put("dfs",dataScopeFilter(UserUtils.getUser(),"o","u"));
		return super.findList(affairCertificateAdministrator);
	}
	
	public Page<AffairCertificateAdministrator> findPage(Page<AffairCertificateAdministrator> page, AffairCertificateAdministrator affairCertificateAdministrator) {
		affairCertificateAdministrator.getSqlMap().put("dfs",dataScopeFilter(UserUtils.getUser(),"o","u"));
		return super.findPage(page, affairCertificateAdministrator);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCertificateAdministrator affairCertificateAdministrator) {
		super.save(affairCertificateAdministrator);
	}

	/**
	 * 批量查找
	 * @Param
	 * */
	public List<AffairCertificateAdministrator> findByIds(List<String> ids){
		List<AffairCertificateAdministrator> list = affairCertificateAdministratorDao.findByIds(ids);
		return list;
	}
	@Transactional(readOnly = false)
	public void delete(AffairCertificateAdministrator affairCertificateAdministrator) {
		super.delete(affairCertificateAdministrator);
	}
	
}