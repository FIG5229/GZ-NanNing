/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairBrandManagementDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairBrandManagement;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 品牌创建Service
 * @author cecil.li
 * @version 2019-11-07
 */
@Service
@Transactional(readOnly = true)
public class AffairBrandManagementService extends CrudService<AffairBrandManagementDao, AffairBrandManagement> {

	@Autowired
	AffairBrandManagementDao affairBrandManagementDao;

	public AffairBrandManagement get(String id) {
		return super.get(id);
	}
	
	public List<AffairBrandManagement> findList(AffairBrandManagement affairBrandManagement) {
		return super.findList(affairBrandManagement);
	}
	
	public Page<AffairBrandManagement> findPage(Page<AffairBrandManagement> page, AffairBrandManagement affairBrandManagement) {
		affairBrandManagement.setUserId(UserUtils.getUser().getId());
		affairBrandManagement.setOfficeId(UserUtils.getUser().getOffice().getId());
		affairBrandManagement.setCreateBy(UserUtils.getUser());
		return super.findPage(page, affairBrandManagement);
	}

	public List<Map<String, Object>> findInfoByUnitId(String id) {
		return dao.findInfoByUnitId(id);
	}

	public List<Map<String, Object>> findInfoByUnitIds(List<String> ids) {
		return dao.findInfoByUnitIds(ids);
	}

	/**
	 * 批量提交
	 * @param ids
	 */
	public List<AffairBrandManagement> findByIds(List<String> ids){
		List<AffairBrandManagement> list = affairBrandManagementDao.findByIds(ids);
		return list;
	}

	@Transactional(readOnly = false)
	public void save(AffairBrandManagement affairBrandManagement) {
		if(affairBrandManagement.getCheckType()==null||"".equals(affairBrandManagement.getCheckType())){
			affairBrandManagement.setCheckType("1");
		}
		super.save(affairBrandManagement);
	}

	@Transactional(readOnly = false)
	public void delete(AffairBrandManagement affairBrandManagement) {
		super.delete(affairBrandManagement);
	}
}