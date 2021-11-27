/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairResumeDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairResume;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 履历信息管理Service
 * @author mason.xv
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class AffairResumeService extends CrudService<AffairResumeDao, AffairResume> {

	public AffairResume get(String id) {
		return super.get(id);
	}
	
	public List<AffairResume> findList(AffairResume affairResume) {
		return super.findList(affairResume);
	}
	
	public Page<AffairResume> findPage(Page<AffairResume> page, AffairResume affairResume) {
		return super.findPage(page, affairResume);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairResume affairResume) {
		super.save(affairResume);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairResume affairResume) {
		super.delete(affairResume);
	}

	/*@Override
	public void deleteByIds(List<String> ids) {
		super.deleteByIds(ids);
	}*/
}