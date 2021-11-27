/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairTemHumDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTemHum;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 库房温湿度测试记录Service
 * @author mason.xv
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairTemHumService extends CrudService<AffairTemHumDao, AffairTemHum> {

	public AffairTemHum get(String id) {
		return super.get(id);
	}
	
	public List<AffairTemHum> findList(AffairTemHum affairTemHum) {
		return super.findList(affairTemHum);
	}
	
	public Page<AffairTemHum> findPage(Page<AffairTemHum> page, AffairTemHum affairTemHum) {
		affairTemHum.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTemHum);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTemHum affairTemHum) {
		super.save(affairTemHum);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTemHum affairTemHum) {
		super.delete(affairTemHum);
	}
	
}