/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairLdgblzFileDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLdgblzFile;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 领导廉政干部档案表Service
 * @author eav.liu
 * @version 2020-03-04
 */
@Service
@Transactional(readOnly = true)
public class AffairLdgblzFileService extends CrudService<AffairLdgblzFileDao, AffairLdgblzFile> {

	public AffairLdgblzFile get(String id) {
		return super.get(id);
	}
	
	public List<AffairLdgblzFile> findList(AffairLdgblzFile affairLdgblzFile) {
		return super.findList(affairLdgblzFile);
	}
	
	public Page<AffairLdgblzFile> findPage(Page<AffairLdgblzFile> page, AffairLdgblzFile affairLdgblzFile) {
		affairLdgblzFile.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairLdgblzFile);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairLdgblzFile affairLdgblzFile) {
		super.save(affairLdgblzFile);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairLdgblzFile affairLdgblzFile) {
		super.delete(affairLdgblzFile);
	}
	
}