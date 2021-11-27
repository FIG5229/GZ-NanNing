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
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainOutsource;
import com.thinkgem.jeesite.modules.affair.dao.AffairTrainOutsourceDao;

/**
 * 委外培训Service
 * @author jack.xu
 * @version 2020-07-17
 */
@Service
@Transactional(readOnly = true)
public class AffairTrainOutsourceService extends CrudService<AffairTrainOutsourceDao, AffairTrainOutsource> {

	@Autowired
	private AffairTrainOutsourceDao affairTrainOutsourceDao;

	public AffairTrainOutsource get(String id) {
		return super.get(id);
	}
	
	public List<AffairTrainOutsource> findList(AffairTrainOutsource affairTrainOutsource) {
		affairTrainOutsource.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(affairTrainOutsource);
	}
	
	public Page<AffairTrainOutsource> findPage(Page<AffairTrainOutsource> page, AffairTrainOutsource affairTrainOutsource) {
		affairTrainOutsource.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTrainOutsource);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTrainOutsource affairTrainOutsource) {
		String unitId = affairTrainOutsource.getUnitId();
		String unit = affairTrainOutsource.getUnit();
		if("".equals(unitId) || null == unitId && (!"".equals(unit) && null != unit)){
			String Id = affairTrainOutsourceDao.findofficeId(unit);
			affairTrainOutsource.setUnitId(Id);
		}
		super.save(affairTrainOutsource);
	}

	/**
	 * 批量查找
	 * @param
	 * */
	public List<AffairTrainOutsource> findByIdS(List<String> ids){
		List<AffairTrainOutsource> list = affairTrainOutsourceDao.findByIds(ids);
		return list;
	}

	@Transactional(readOnly = false)
	public void delete(AffairTrainOutsource affairTrainOutsource) {
		super.delete(affairTrainOutsource);
	}



}