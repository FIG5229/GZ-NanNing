/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairLeaderCheckDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLeaderCheck;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 领导干部年度考核表Service
 * @author mason.xv
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairLeaderCheckService extends CrudService<AffairLeaderCheckDao, AffairLeaderCheck> {

	@Autowired
	AffairLeaderCheckDao affairLeaderCheckDao;

	public AffairLeaderCheck get(String id) {
		return super.get(id);
	}

	public List<AffairLeaderCheck> findList(AffairLeaderCheck affairLeaderCheck) {
		return super.findList(affairLeaderCheck);
	}

	public Page<AffairLeaderCheck> findPage(Page<AffairLeaderCheck> page, AffairLeaderCheck affairLeaderCheck) {
		return super.findPage(page, affairLeaderCheck);
	}

	@Transactional(readOnly = false)
	public void save(AffairLeaderCheck affairLeaderCheck) {
		if(StringUtils.isNotBlank(affairLeaderCheck.getStatus())){
			affairLeaderCheck.setStatus("0");
		}
		super.save(affairLeaderCheck);
	}

	@Transactional(readOnly = false)
	public void delete(AffairLeaderCheck affairLeaderCheck) {
		super.delete(affairLeaderCheck);
	}

	@Transactional(readOnly = false)
	public void shenHe(AffairLeaderCheck affairLeaderCheck) {
		affairLeaderCheck.setUpdateDate(new Date());
		affairLeaderCheck.setShPerson(UserUtils.getUser().getName());
		affairLeaderCheckDao.shenHe(affairLeaderCheck);

	}
	/**
	 * 批量提交
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void submitByIds(List<String> ids){
		List<AffairLeaderCheck> list = affairLeaderCheckDao.findByIds(ids);
		for (AffairLeaderCheck affairLeaderCheck:list) {
			//过滤掉已经提交的数据
			if ("0".equals(affairLeaderCheck.getStatus())){
				//提交
				affairLeaderCheck.setStatus("1");//未审核
				super.save(affairLeaderCheck);
			}
		}
	}
}