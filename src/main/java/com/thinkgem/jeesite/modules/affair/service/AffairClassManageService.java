/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairClassManageDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassInformation;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassManage;
import com.thinkgem.jeesite.modules.affair.entity.AffairPersonnelMessage;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 培训班管理Service
 * @author jack.xu
 * @version 2020-07-08
 */
@Service
@Transactional(readOnly = true)
public class AffairClassManageService extends CrudService<AffairClassManageDao, AffairClassManage> {
	@Autowired
	AffairClassManageDao affairClassManageDao;

	public AffairClassManage get(String id) {
		return super.get(id);
	}
	
	public List<AffairClassManage> findList(AffairClassManage affairClassManage) {
		affairClassManage.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(affairClassManage);
	}
	
	public Page<AffairClassManage> findPage(Page<AffairClassManage> page, AffairClassManage affairClassManage) {
		affairClassManage.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairClassManage);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairClassManage affairClassManage) {
		super.save(affairClassManage);
	}

	/**
	 * 批量查找
	 * @param
	 */
	public List<AffairClassManage> findByIds(List<String> ids){
		List<AffairClassManage> list = affairClassManageDao.findByIds(ids);
		return list;
	}
	@Transactional(readOnly = false)
	public void delete(AffairClassManage affairClassManage) {
		super.delete(affairClassManage);
	}


	public List<AffairClassInformation> idList(String id,AffairClassInformation affairClassInformation){
		List<AffairClassInformation> list = affairClassManageDao.idList(id,affairClassInformation);
		return list;
	}
	public List<AffairPersonnelMessage> idListRenYuan(String id,AffairPersonnelMessage affairPersonnelMessage){
		List<AffairPersonnelMessage> list = affairClassManageDao.idListRenYuan(id,affairPersonnelMessage);
		return list;
	}

	@Transactional(readOnly = false)
	public void deleteClassInfo(AffairClassInformation affairClassInformation) {
		affairClassManageDao.deleteClassInfo(affairClassInformation);
	}


}