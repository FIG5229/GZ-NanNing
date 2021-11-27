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
import com.thinkgem.jeesite.modules.affair.entity.AffairCertificateIssue;
import com.thinkgem.jeesite.modules.affair.dao.AffairCertificateIssueDao;

/**
 * 颁发证书Service
 * @author jack.xu
 * @version 2020-07-22
 */
@Service
@Transactional(readOnly = true)
public class AffairCertificateIssueService extends CrudService<AffairCertificateIssueDao, AffairCertificateIssue> {

	@Autowired
	private AffairCertificateIssueDao affairCertificateIssueDao;

	public AffairCertificateIssue get(String id) {
		return super.get(id);
	}
	
	public List<AffairCertificateIssue> findList(AffairCertificateIssue affairCertificateIssue) {
		affairCertificateIssue.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(affairCertificateIssue);
	}
	
	public Page<AffairCertificateIssue> findPage(Page<AffairCertificateIssue> page, AffairCertificateIssue affairCertificateIssue) {
		affairCertificateIssue.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairCertificateIssue);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCertificateIssue affairCertificateIssue) {
		super.save(affairCertificateIssue);
	}

	/**
	 * 批量查找
	 * @param
	 * */
	public List<AffairCertificateIssue> findByIdS(List<String> ids){
		List<AffairCertificateIssue> list = affairCertificateIssueDao.findByIds(ids);
		return list;
	}
	@Transactional(readOnly = false)
	public void delete(AffairCertificateIssue affairCertificateIssue) {
		super.delete(affairCertificateIssue);
	}
	
}