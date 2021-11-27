/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairActivityMien;
import com.thinkgem.jeesite.modules.affair.dao.AffairActivityMienDao;

/**
 * 活动风采Service
 * @author kevin.jia
 * @version 2020-07-23
 */
@Service
@Transactional(readOnly = true)
public class AffairActivityMienService extends CrudService<AffairActivityMienDao, AffairActivityMien> {

	@Autowired
	AffairActivityMienDao affairActivityMienDao;

	public AffairActivityMien get(String id) {
		return super.get(id);
	}
	
	public List<AffairActivityMien> findList(AffairActivityMien affairActivityMien) {
		return super.findList(affairActivityMien);
	}
	
	public Page<AffairActivityMien> findPage(Page<AffairActivityMien> page, AffairActivityMien affairActivityMien) {
		affairActivityMien.setUserId(UserUtils.getUser().getId());
		affairActivityMien.setOfficeId(UserUtils.getUser().getOffice().getId());
		affairActivityMien.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, affairActivityMien);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairActivityMien affairActivityMien) {
		affairActivityMien.setContent(StringEscapeUtils.unescapeHtml4(affairActivityMien.getContent()));
		super.save(affairActivityMien);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairActivityMien affairActivityMien) {
		super.delete(affairActivityMien);
	}

	/**
	 * 批量提交
	 * @param ids
	 */
	public List<AffairActivityMien> findByIds(List<String> ids){
		List<AffairActivityMien> list = affairActivityMienDao.findByIds(ids);
		return list;
	}
}