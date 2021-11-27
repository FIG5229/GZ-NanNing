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
import com.thinkgem.jeesite.modules.affair.entity.AffairSpiritualReview;
import com.thinkgem.jeesite.modules.affair.dao.AffairSpiritualReviewDao;

/**
 * 复查情况报告Service
 * @author Alan.wu
 * @version 2020-06-15
 */
@Service
@Transactional(readOnly = true)
public class AffairSpiritualReviewService extends CrudService<AffairSpiritualReviewDao, AffairSpiritualReview> {

	@Autowired
	private AffairSpiritualReviewDao affairSpiritualReviewDao;

	public AffairSpiritualReview get(String id) {
		return super.get(id);
	}
	
	public List<AffairSpiritualReview> findList(AffairSpiritualReview affairSpiritualReview) {
		return super.findList(affairSpiritualReview);
	}
	
	public Page<AffairSpiritualReview> findPage(Page<AffairSpiritualReview> page, AffairSpiritualReview affairSpiritualReview) {
		affairSpiritualReview.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, affairSpiritualReview);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairSpiritualReview affairSpiritualReview) {
		super.save(affairSpiritualReview);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairSpiritualReview affairSpiritualReview) {
		super.delete(affairSpiritualReview);
	}

	/**
	 * 批量提交
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public List<AffairSpiritualReview> findByIds(List<String> ids){
		List<AffairSpiritualReview> list = affairSpiritualReviewDao.findByIds(ids);
		return list;
	}

	/*
	*
	* 审核
	* */
	@Transactional(readOnly = false)
	public void update(String id, String state, String oneCheckMan, String oneCheckId) {
		affairSpiritualReviewDao.upd(id ,state,oneCheckMan,oneCheckId);
	}


}