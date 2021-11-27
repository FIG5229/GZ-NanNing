/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairEducationTrainDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairEducationTrain;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 教育训练培训计划Service
 * @author jack.xu
 * @version 2020-07-01
 */
@Service
@Transactional(readOnly = true)
public class AffairEducationTrainService extends CrudService<AffairEducationTrainDao, AffairEducationTrain> {
	@Autowired
	AffairEducationTrainDao affairEducationTrainDao;

	public AffairEducationTrain get(String id) {
		return super.get(id);
	}
	
	public List<AffairEducationTrain> findList(AffairEducationTrain affairEducationTrain) {
		affairEducationTrain.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(affairEducationTrain);
	}
	
	public Page<AffairEducationTrain> findPage(Page<AffairEducationTrain> page, AffairEducationTrain affairEducationTrain) {
		affairEducationTrain.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairEducationTrain);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairEducationTrain affairEducationTrain) {
//		affairEducationTrain.setContent(StringEscapeUtils.unescapeHtml4(affairEducationTrain.getContent()));
		if ("".equals(affairEducationTrain.getApproveResult())||affairEducationTrain.getApproveResult()== null) {
			affairEducationTrain.setApproveResult("3");
		}
		if("".equals(affairEducationTrain.getApproveStatus())||affairEducationTrain.getApproveStatus()==null){
			affairEducationTrain.setApproveStatus("0");
		}
		super.save(affairEducationTrain);
	}


	/**
	 * 批量查找
	 * @param ids
	 */
	public List<AffairEducationTrain> findByIds(List<String> ids){
		List<AffairEducationTrain> list = affairEducationTrainDao.findByIds(ids);
		return list;
	}

	@Transactional(readOnly = false)
	public void delete(AffairEducationTrain affairEducationTrain) {
		super.delete(affairEducationTrain);
	}

/*	@Transactional(readOnly = false)
	public void shenHe(AffairTwUnitAward affairTwUnitAward) {
		affairTwUnitAward.setUpdateDate(new Date());
		affairTwUnitAward.setShPerson(UserUtils.getUser().getName());
		affairTwUnitAwardDao.shenHe(affairTwUnitAward);
	}*/

//	public Map<String, Object> findInfoByCreateOrgId(String id) {
//		return dao.findInfoByCreateOrgId(id);
//	}
//
//	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids) {
//		return dao.findInfoByCreateOrgIds(ids);
//	}
	
}