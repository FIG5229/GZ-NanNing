/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliticalGroup;
import com.thinkgem.jeesite.modules.affair.dao.AffairPoliticalGroupDao;

/**
 * 党小组Service
 * @author daniel.liu
 * @version 2020-05-26
 */
@Service
@Transactional(readOnly = true)
public class AffairPoliticalGroupService extends CrudService<AffairPoliticalGroupDao, AffairPoliticalGroup> {

	public AffairPoliticalGroup get(String id) {
		return super.get(id);
	}
	
	public List<AffairPoliticalGroup> findList(AffairPoliticalGroup affairPoliticalGroup) {
		return super.findList(affairPoliticalGroup);
	}
	
	public Page<AffairPoliticalGroup> findPage(Page<AffairPoliticalGroup> page, AffairPoliticalGroup affairPoliticalGroup) {
		affairPoliticalGroup.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairPoliticalGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPoliticalGroup affairPoliticalGroup) {
		super.save(affairPoliticalGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPoliticalGroup affairPoliticalGroup) {
		super.delete(affairPoliticalGroup);
	}

    public AffairPoliticalGroup findInfoByTreeId(String treeId) {
		return dao.findInfoByTreeId(treeId);
    }
}