/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairCertificateDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairCertificateIssueDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCertificate;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 证书Service
 * @author jack.xu
 * @version 2020-07-22
 */
@Service
@Transactional(readOnly = true)
public class AffairCertificateService extends CrudService<AffairCertificateDao, AffairCertificate> {

	@Autowired
	private AffairCertificateDao affairCertificateDao;

	public AffairCertificate get(String id) {
		return super.get(id);
	}
	
	public List<AffairCertificate> findList(AffairCertificate affairCertificate) {
		affairCertificate.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(affairCertificate);
	}
	
	public Page<AffairCertificate> findPage(Page<AffairCertificate> page, AffairCertificate affairCertificate) {
		affairCertificate.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairCertificate);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCertificate affairCertificate) {
		super.save(affairCertificate);
	}

	/**
	 * 批量查找
	 * @param
	 * */
	public List<AffairCertificate> findByIdS(List<String> ids){
		List<AffairCertificate> list = affairCertificateDao.findByIds(ids);
		return list;
	}
	@Transactional(readOnly = false)
	public void delete(AffairCertificate affairCertificate) {
		super.delete(affairCertificate);
	}
	
}