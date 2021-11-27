/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairLearnDailyDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLearnDaily;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 日常学习Service
 * @author Alan
 * @version 2020-06-11
 */
@Service
@Transactional(readOnly = true)
public class AffairLearnDailyService extends CrudService<AffairLearnDailyDao, AffairLearnDaily> {

	@Autowired
	private AffairLearnDailyDao affairLearnDailyDao;

	public AffairLearnDaily get(String id) {
		return super.get(id);
	}
	
	public List<AffairLearnDaily> findList(AffairLearnDaily affairLearnDaily) {
		return super.findList(affairLearnDaily);
	}
	
	public Page<AffairLearnDaily> findPage(Page<AffairLearnDaily> page, AffairLearnDaily affairLearnDaily) {
		affairLearnDaily.setCreateBy(UserUtils.getUser());
		if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
			affairLearnDaily.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairLearnDaily.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairLearnDaily);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairLearnDaily affairLearnDaily) {
		super.save(affairLearnDaily);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairLearnDaily affairLearnDaily) {
		super.delete(affairLearnDaily);
	}

	public List<String> selectAllYear(){
		return affairLearnDailyDao.selectAllYear();
	}
	public List<String> selectAllMonth(){
		return affairLearnDailyDao.selectAllMonth();
	}

	public Integer selectNum(String yearL,String yearT,String idNumber){
		return affairLearnDailyDao.selectNum(yearL,yearT,idNumber);
	}

	public String selectName(String idNumber){
		return affairLearnDailyDao.selectName(idNumber);
	}

	public Integer selectNumber(String yearL,String yearT,String idNumber){
		return affairLearnDailyDao.selectNumber(yearL,yearT,idNumber);
	}

	public List<String> selectAllName(){
		return affairLearnDailyDao.selectAllName();
	}

}