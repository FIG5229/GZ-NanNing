/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairVolunteerServiceDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairVolunteerService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 志愿服务活动Service
 * @author eav.liu
 * @version 2019-11-07
 */
@Service
@Transactional(readOnly = true)
public class AffairVolunteerServiceService extends CrudService<AffairVolunteerServiceDao, AffairVolunteerService> {

	@Autowired
	AffairVolunteerServiceDao affairVolunteerServiceDao;

	public AffairVolunteerService get(String id) {
		return super.get(id);
	}
	
	public List<AffairVolunteerService> findList(AffairVolunteerService affairVolunteerService) {
		return super.findList(affairVolunteerService);
	}
	
	public Page<AffairVolunteerService> findPage(Page<AffairVolunteerService> page, AffairVolunteerService affairVolunteerService) {
		affairVolunteerService.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairVolunteerService);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairVolunteerService affairVolunteerService) {
		affairVolunteerService.setContent(StringEscapeUtils.unescapeHtml4(affairVolunteerService.getContent()));
		super.save(affairVolunteerService);
	}

	public List<String> selectAllYear(){
		return affairVolunteerServiceDao.selectAllYear();
	}

	@Transactional(readOnly = false)
	public void delete(AffairVolunteerService affairVolunteerService) {
		super.delete(affairVolunteerService);
	}

	public List<String> selectYearById(String idN){
		return affairVolunteerServiceDao.selectYearById(idN);
	}

	public String selectNameById(String idN){
		return affairVolunteerServiceDao.selectNameById(idN);

	}

	public AffairVolunteerService selectBeanById(String idN,String year){
		return affairVolunteerServiceDao.selectBeanById(idN,year);

	}
	public Integer selectNumByName(String unitId,String yearL,String yearT){
		return affairVolunteerServiceDao.selectNumByName(unitId,yearL,yearT);
	}

	public int selectNumberById(String yearL, String yearT, String partyOrganizationId) {
		return affairVolunteerServiceDao.selectNumberById(yearL,yearT,partyOrganizationId);
	}
}