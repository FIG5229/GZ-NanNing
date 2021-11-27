/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.tw.entity.TFeedback;
import com.thinkgem.jeesite.modules.tw.dao.TFeedbackDao;

/**
 * 微信警情-自动考评Service
 * @author alan.wu
 * @version 2020-10-16
 */
@Service
@Transactional(readOnly = true)
public class TFeedbackService extends CrudService<TFeedbackDao, TFeedback> {

	@Autowired
	private TFeedbackDao tFeedbackDao;

	public TFeedback get(String id) {
		return super.get(id);
	}
	
	public List<TFeedback> findList(TFeedback tFeedback) {
		return super.findList(tFeedback);
	}
	
	public Page<TFeedback> findPage(Page<TFeedback> page, TFeedback tFeedback) {
		return super.findPage(page, tFeedback);
	}
	
	@Transactional(readOnly = false)
	public void save(TFeedback tFeedback) {
		super.save(tFeedback);
	}
	
	@Transactional(readOnly = false)
	public void delete(TFeedback tFeedback) {
		super.delete(tFeedback);
	}

	public List<TFeedback> selectAll(String time,String name){
		return tFeedbackDao.selectAll(time,name);
	}

}