/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairTrainApproveDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainApprove;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 培训计划审批Service
 * @author jack.xu
 * @version 2020-07-28
 */
@Service
@Transactional(readOnly = true)
public class AffairTrainApproveService extends CrudService<AffairTrainApproveDao, AffairTrainApprove> {

	@Autowired
	private AffairTrainApproveDao affairTrainApproveDao;
	
	public AffairTrainApprove get(String id) {
		return super.get(id);
	}
	
	public List<AffairTrainApprove> findList(AffairTrainApprove affairTrainApprove) {
		affairTrainApprove.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(affairTrainApprove);
	}
	
	public Page<AffairTrainApprove> findPage(Page<AffairTrainApprove> page, AffairTrainApprove affairTrainApprove) {
		affairTrainApprove.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTrainApprove);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTrainApprove affairTrainApprove) {
		if ("".equals(affairTrainApprove.getApproveStatus())||affairTrainApprove.getApproveStatus()== null) {
			affairTrainApprove.setApproveStatus("3");
		}
		if("".equals(affairTrainApprove.getApproveStatus())||affairTrainApprove.getApproveStatus()==null){
			affairTrainApprove.setApproveStatus("0");
		}
		super.save(affairTrainApprove);
	}

	/**
	 * 批量查找
	 * @param ids
	 */
	public List<AffairTrainApprove> findByIds(List<String> ids){
		List<AffairTrainApprove> list = affairTrainApproveDao.findByIds(ids);
		return list;
	}
	@Transactional(readOnly = false)
	public void delete(AffairTrainApprove affairTrainApprove) {
		super.delete(affairTrainApprove);
	}
	
}