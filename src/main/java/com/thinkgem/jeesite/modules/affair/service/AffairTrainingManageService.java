/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairTrainingDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairTrainingManageDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairApplyManage;
import com.thinkgem.jeesite.modules.affair.entity.AffairTraining;
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainingManage;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 成绩管理员练兵比武Service
 * @author alan.wu
 * @version 2020-07-15
 */
@Service
@Transactional(readOnly = true)
public class AffairTrainingManageService extends CrudService<AffairTrainingManageDao, AffairTrainingManage> {

	public AffairTrainingManage get(String id) {
		return super.get(id);
	}
	
	public List<AffairTrainingManage> findList(AffairTrainingManage affairTrainingManage) {
		return super.findList(affairTrainingManage);
	}
	
	public Page<AffairTrainingManage> findPage(Page<AffairTrainingManage> page, AffairTrainingManage affairTrainingManage) {
		affairTrainingManage.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, affairTrainingManage);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTrainingManage affairTrainingManage) {
		super.save(affairTrainingManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTrainingManage affairTrainingManage) {
		super.delete(affairTrainingManage);
	}
	
}