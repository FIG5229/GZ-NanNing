/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.tw.entity.Ren001;
import com.thinkgem.jeesite.modules.tw.dao.Ren001Dao;

/**
 * 自动考评-精神病患者基本信息Service
 * @author alan.wu
 * @version 2020-11-20
 */
@Service
@Transactional(readOnly = true)
public class Ren001Service extends CrudService<Ren001Dao, Ren001> {

	@Autowired
	private Ren001Dao ren001Dao;

	public Ren001 get(String id) {
		return super.get(id);
	}
	
	public List<Ren001> findList(Ren001 ren001) {
		return super.findList(ren001);
	}
	
	public Page<Ren001> findPage(Page<Ren001> page, Ren001 ren001) {
		return super.findPage(page, ren001);
	}
	
	@Transactional(readOnly = false)
	public void save(Ren001 ren001) {
		super.save(ren001);
	}
	
	@Transactional(readOnly = false)
	public void delete(Ren001 ren001) {
		super.delete(ren001);
	}

	public List<Ren001> selectAllPeople(String code,String dj){
		return ren001Dao.selectAllPeople(code,dj);
	}
}