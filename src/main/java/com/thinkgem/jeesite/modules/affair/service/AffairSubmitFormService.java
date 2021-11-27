/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairSubmitFormDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairSubmitForm;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 档案报送单管理Service
 * @author mason.xv
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairSubmitFormService extends CrudService<AffairSubmitFormDao, AffairSubmitForm> {

	public AffairSubmitForm get(String id) {
		return super.get(id);
	}
	
	public List<AffairSubmitForm> findList(AffairSubmitForm affairSubmitForm) {
		return super.findList(affairSubmitForm);
	}
	
	public Page<AffairSubmitForm> findPage(Page<AffairSubmitForm> page, AffairSubmitForm affairSubmitForm) {
		affairSubmitForm.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairSubmitForm);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairSubmitForm affairSubmitForm) {
		super.save(affairSubmitForm);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairSubmitForm affairSubmitForm) {
		super.delete(affairSubmitForm);
	}
	
}