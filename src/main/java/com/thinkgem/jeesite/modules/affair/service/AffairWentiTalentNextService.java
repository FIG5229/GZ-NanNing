/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairWentiTalentNext;
import com.thinkgem.jeesite.modules.affair.dao.AffairWentiTalentNextDao;

/**
 * 文艺人才库Service
 * @author mason.xv
 * @version 2020-04-02
 */
@Service
@Transactional(readOnly = true)
public class AffairWentiTalentNextService extends CrudService<AffairWentiTalentNextDao, AffairWentiTalentNext> {

	public AffairWentiTalentNext get(String id) {
		return super.get(id);
	}
	
	public List<AffairWentiTalentNext> findList(AffairWentiTalentNext affairWentiTalentNext) {
		return super.findList(affairWentiTalentNext);
	}
	
	public Page<AffairWentiTalentNext> findPage(Page<AffairWentiTalentNext> page, AffairWentiTalentNext affairWentiTalentNext) {
		return super.findPage(page, affairWentiTalentNext);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairWentiTalentNext affairWentiTalentNext) {
		super.save(affairWentiTalentNext);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairWentiTalentNext affairWentiTalentNext) {
		super.delete(affairWentiTalentNext);
	}
	
}