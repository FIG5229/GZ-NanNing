/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairTalkHeart;
import com.thinkgem.jeesite.modules.affair.dao.AffairTalkHeartDao;

/**
 * 谈心Service
 * @author daniel.liu
 * @version 2020-06-11
 */
@Service
@Transactional(readOnly = true)
public class AffairTalkHeartService extends CrudService<AffairTalkHeartDao, AffairTalkHeart> {

	@Autowired
	private AffairTalkHeartDao affairTalkHeartDao;

	public AffairTalkHeart get(String id) {
		return super.get(id);
	}
	
	public List<AffairTalkHeart> findList(AffairTalkHeart affairTalkHeart) {
		return super.findList(affairTalkHeart);
	}
	
	public Page<AffairTalkHeart> findPage(Page<AffairTalkHeart> page, AffairTalkHeart affairTalkHeart) {
		affairTalkHeart.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		/*南宁局办公室账号 有角色是看所有数据，谈心家访只看本部门及以下数据，修改数据过滤*/
		if ("54e8fb917a8241c08c04bb3dbe4dee46".equals(UserUtils.getUser().getId())){
			affairTalkHeart.getSqlMap().put("dsf", unitDataScopeFilter(UserUtils.getUser(), "o", "u"));
		}
		String id = UserUtils.getUser().getOffice().getId();
		affairTalkHeart.setUserId(UserUtils.getUser().getId());
		affairTalkHeart.setOfficeId(id);
		affairTalkHeart.setCreateBy(UserUtils.getUser());
		return super.findPage(page, affairTalkHeart);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTalkHeart affairTalkHeart) {
		super.save(affairTalkHeart);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTalkHeart affairTalkHeart) {
		super.delete(affairTalkHeart);
	}

	public List<String> selectAllYear(){
		return affairTalkHeartDao.selectAllYear();
	}
	public List<String> selectAllMonth(){
		return affairTalkHeartDao.selectAllMonth();
	}
	public List<String> selectAllName(){
		return affairTalkHeartDao.selectAllName();
	}
	public Integer selectNumber(String time,String idNumber){return affairTalkHeartDao.selectNumber(time,idNumber);}


}