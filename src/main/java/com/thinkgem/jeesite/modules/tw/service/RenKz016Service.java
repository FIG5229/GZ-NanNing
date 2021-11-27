/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.tw.entity.RenKz016;
import com.thinkgem.jeesite.modules.tw.dao.RenKz016Dao;

/**
 * 自动考评-精神病患者信息拓展Service
 * @author alan.wu
 * @version 2020-11-20
 */
@Service
@Transactional(readOnly = true)
public class RenKz016Service extends CrudService<RenKz016Dao, RenKz016> {

	public RenKz016 get(String id) {
		return super.get(id);
	}
	
	public List<RenKz016> findList(RenKz016 renKz016) {
		return super.findList(renKz016);
	}
	
	public Page<RenKz016> findPage(Page<RenKz016> page, RenKz016 renKz016) {
		return super.findPage(page, renKz016);
	}
	
	@Transactional(readOnly = false)
	public void save(RenKz016 renKz016) {
		super.save(renKz016);
	}
	
	@Transactional(readOnly = false)
	public void delete(RenKz016 renKz016) {
		super.delete(renKz016);
	}
	
}