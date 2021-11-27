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
import com.thinkgem.jeesite.modules.tw.entity.TAcVisitLinkRen;
import com.thinkgem.jeesite.modules.tw.dao.TAcVisitLinkRenDao;

/**
 * 自动考评-回访记录和人员关联信息Service
 * @author alan.wu
 * @version 2020-11-20
 */
@Service
@Transactional(readOnly = true)
public class TAcVisitLinkRenService extends CrudService<TAcVisitLinkRenDao, TAcVisitLinkRen> {

	@Autowired
	private TAcVisitLinkRenDao tAcVisitLinkRenDao;

	public TAcVisitLinkRen get(String id) {
		return super.get(id);
	}
	
	public List<TAcVisitLinkRen> findList(TAcVisitLinkRen tAcVisitLinkRen) {
		return super.findList(tAcVisitLinkRen);
	}
	
	public Page<TAcVisitLinkRen> findPage(Page<TAcVisitLinkRen> page, TAcVisitLinkRen tAcVisitLinkRen) {
		return super.findPage(page, tAcVisitLinkRen);
	}
	
	@Transactional(readOnly = false)
	public void save(TAcVisitLinkRen tAcVisitLinkRen) {
		super.save(tAcVisitLinkRen);
	}
	
	@Transactional(readOnly = false)
	public void delete(TAcVisitLinkRen tAcVisitLinkRen) {
		super.delete(tAcVisitLinkRen);
	}

	public List<String> selectAllRecode(String id){
		return tAcVisitLinkRenDao.selectAllRecode(id);
	}
}