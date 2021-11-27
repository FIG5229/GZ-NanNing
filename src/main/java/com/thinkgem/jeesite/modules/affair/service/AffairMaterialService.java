/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairMaterialDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairMaterial;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 干部档案材料收集Service
 * @author mason.xv
 * @version 2019-11-04
 */
@Service
@Transactional(readOnly = true)
public class AffairMaterialService extends CrudService<AffairMaterialDao, AffairMaterial> {

	public AffairMaterial get(String id) {
		return super.get(id);
	}
	
	public List<AffairMaterial> findList(AffairMaterial affairMaterial) {
		return super.findList(affairMaterial);
	}
	
	public Page<AffairMaterial> findPage(Page<AffairMaterial> page, AffairMaterial affairMaterial) {
		affairMaterial.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairMaterial);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairMaterial affairMaterial) {
		super.save(affairMaterial);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairMaterial affairMaterial) {
		super.delete(affairMaterial);
	}
	
}