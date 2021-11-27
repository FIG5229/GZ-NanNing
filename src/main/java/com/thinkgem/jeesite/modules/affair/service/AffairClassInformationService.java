/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.affair.dao.AffairCertificateManagementDao;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassInformation;
import com.thinkgem.jeesite.modules.affair.dao.AffairClassInformationDao;

/**
 * 课程信息Service
 * @author jack.xu
 * @version 2020-07-23
 */
@Service
@Transactional(readOnly = true)
public class AffairClassInformationService extends CrudService<AffairClassInformationDao, AffairClassInformation> {

	@Autowired
	private AffairClassInformationDao affairClassInformationDao;

	public AffairClassInformation get(String id) {
		return super.get(id);
	}
	
	public List<AffairClassInformation> findList(AffairClassInformation affairClassInformation) {
		affairClassInformation.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","u"));
		return super.findList(affairClassInformation);
	}
	
	public Page<AffairClassInformation> findPage(Page<AffairClassInformation> page, AffairClassInformation affairClassInformation) {
		affairClassInformation.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","u"));
		return super.findPage(page, affairClassInformation);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairClassInformation affairClassInformation) {
		super.save(affairClassInformation);
	}

	/**
	 * 批量查找
	 * @Param
	 * */
	public List<AffairClassInformation> findByIds(List<String> ids){
		List<AffairClassInformation> list = affairClassInformationDao.findByIds(ids);
		return list;
	}
	@Transactional(readOnly = false)
	public void delete(AffairClassInformation affairClassInformation) {
		super.delete(affairClassInformation);
	}


	public List<AffairClassInformation> findPageThree(String classManageId) {
		return affairClassInformationDao.findPageThree(classManageId);
	}

}