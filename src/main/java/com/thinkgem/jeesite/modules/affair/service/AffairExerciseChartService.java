/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairExerciseChart;
import com.thinkgem.jeesite.modules.affair.dao.AffairExerciseChartDao;

/**
 * 练习报表Service
 * @author alan.wu
 * @version 2020-08-07
 */
@Service
@Transactional(readOnly = true)
public class AffairExerciseChartService extends CrudService<AffairExerciseChartDao, AffairExerciseChart> {

	@Autowired
	private AffairExerciseChartDao affairExerciseChartDao;

	public AffairExerciseChart get(String id) {
		return super.get(id);
	}
	
	public List<AffairExerciseChart> findList(AffairExerciseChart affairExerciseChart) {
		return super.findList(affairExerciseChart);
	}
	
	public Page<AffairExerciseChart> findPage(Page<AffairExerciseChart> page, AffairExerciseChart affairExerciseChart) {
		return super.findPage(page, affairExerciseChart);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairExerciseChart affairExerciseChart) {
		super.save(affairExerciseChart);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairExerciseChart affairExerciseChart) {
		super.delete(affairExerciseChart);
	}
	
	@Transactional(readOnly = false)
	public List<AffairExerciseChart> selectAll() {
		List<AffairExerciseChart> list = affairExerciseChartDao.selectAll();
		return list;
	}

}