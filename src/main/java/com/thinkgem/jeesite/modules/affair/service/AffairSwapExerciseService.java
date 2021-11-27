/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairSwapExerciseDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairSwapExercise;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 交流锻炼Service
 * @author jack.xu
 * @version 2020-07-16
 */
@Service
@Transactional(readOnly = true)
public class AffairSwapExerciseService extends CrudService<AffairSwapExerciseDao, AffairSwapExercise> {

	@Autowired
	private AffairSwapExerciseDao affairSwapExerciseDao;

	public AffairSwapExercise get(String id) {
		return super.get(id);
	}

	public List<AffairSwapExercise> findList(AffairSwapExercise affairSwapExercise) {
		affairSwapExercise.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(affairSwapExercise);
	}

	public Page<AffairSwapExercise> findPage(Page<AffairSwapExercise> page, AffairSwapExercise affairSwapExercise) {
		affairSwapExercise.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairSwapExercise);
	}

	@Transactional(readOnly = false)
	public void save(AffairSwapExercise affairSwapExercise) {
		String unitId = affairSwapExercise.getUnitId();
		String unit = affairSwapExercise.getUnit();
		if("".equals(unitId) || null == unitId && (!"".equals(unit) && null != unit)){
			String Id = affairSwapExerciseDao.findofficeId(unit);
			affairSwapExercise.setUnitId(Id);
		}

		super.save(affairSwapExercise);
	}

	/**
	 * 批量查找
	 *
	 * @param
	 */
	public List<AffairSwapExercise> findByIds(List<String> ids) {
		List<AffairSwapExercise> list = affairSwapExerciseDao.findByIds(ids);
		return list;
	}

	@Transactional(readOnly = false)
	public void delete(AffairSwapExercise affairSwapExercise) {
		super.delete(affairSwapExercise);
	}

	@Transactional(readOnly = false)
	public List<AffairSwapExercise> findBeanList(AffairSwapExercise affairExchangeStatistics) {

		//用户名
		String userName = affairExchangeStatistics.getUserName();
		//警号
		String number = affairExchangeStatistics.getNumber();
		//姓名
		String name = affairExchangeStatistics.getName();
		//证件号
		String idNumber = affairExchangeStatistics.getIdNumber();

		//警衔
		String rank = affairExchangeStatistics.getPoliceRank();
		//警种
		String classify = affairExchangeStatistics.getPoliceClassification();
		//人员类别
		String people = affairExchangeStatistics.getPersonType();
		//管理类别
		String manageMent = affairExchangeStatistics.getManagementType();
		//行政职务
		String post = affairExchangeStatistics.getPost();
		//职务级别
		String level = affairExchangeStatistics.getPostLevel();
		//培训开始时间
		Date begin = affairExchangeStatistics.getStartDate();
		//培训结束时间
		Date end = affairExchangeStatistics.getEndDate();

		List<AffairSwapExercise> list = affairSwapExerciseDao.findBean(userName,number,name,idNumber,rank,classify,people,manageMent,post,level,begin,end);
		return list;
	}

	@Transactional(readOnly = false)
	public List<AffairSwapExercise> selectTrain(String idNubmer) {
		List<AffairSwapExercise> list = affairSwapExerciseDao.selectTrain(idNubmer);
		return list;
	}



}