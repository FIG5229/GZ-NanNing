/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.org.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.org.entity.OrgCommunication;
import com.thinkgem.jeesite.modules.org.dao.OrgCommunicationDao;

/**
 * 单位通讯信息Service
 * @author eav.liu
 * @version 2019-11-23
 */
@Service
@Transactional(readOnly = true)
public class OrgCommunicationService extends CrudService<OrgCommunicationDao, OrgCommunication> {

	public OrgCommunication get(String id) {
		return super.get(id);
	}
	
	public List<OrgCommunication> findList(OrgCommunication orgCommunication) {
		return super.findList(orgCommunication);
	}
	
	public Page<OrgCommunication> findPage(Page<OrgCommunication> page, OrgCommunication orgCommunication) {
		return super.findPage(page, orgCommunication);
	}
	
	@Transactional(readOnly = false)
	public void save(OrgCommunication orgCommunication) {
		super.save(orgCommunication);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrgCommunication orgCommunication) {
		super.delete(orgCommunication);
	}
	
}