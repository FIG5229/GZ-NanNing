/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairArchiveRegisterDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairArchiveRegister;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 在职干部档案登记花名册Service
 * @author mason.xv
 * @version 2019-11-04
 */
@Service
@Transactional(readOnly = true)
public class AffairArchiveRegisterService extends CrudService<AffairArchiveRegisterDao, AffairArchiveRegister> {

	public AffairArchiveRegister get(String id) {
		return super.get(id);
	}
	
	public List<AffairArchiveRegister> findList(AffairArchiveRegister affairArchiveRegister) {
		return super.findList(affairArchiveRegister);
	}
	
	public Page<AffairArchiveRegister> findPage(Page<AffairArchiveRegister> page, AffairArchiveRegister affairArchiveRegister) {
		affairArchiveRegister.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairArchiveRegister);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairArchiveRegister affairArchiveRegister) {
		super.save(affairArchiveRegister);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairArchiveRegister affairArchiveRegister) {
		super.delete(affairArchiveRegister);
	}

	/**
	 * 档案台账统计初始页面
	 * @param affairArchiveRegister
	 * @return
	 *//*
	public List<AffairArchiveRegister> statistic(AffairArchiveRegister affairArchiveRegister) {
		List<AffairArchiveRegister> list = new ArrayList<AffairArchiveRegister>();
		if(affairArchiveRegister.getAffirmDepId() == null || "".equals(affairArchiveRegister.getAffirmDepId())){
			getStatisticList(affairArchiveRegister, list);
		}else{
			affairArchiveRegister statistic = affairArchiveRegisterDao.findByUnitId(affairArchiveRegister.getAffirmDepId(),affairArchiveRegister);
			statistic.setSum(statistic.getXiSheng()+statistic.getBingGu()+statistic.getShangCan()+statistic.getShangWang());
			statistic.setUnitName(affairArchiveRegister.getAffirmDep());
			statistic.setUnitId(affairArchiveRegister.getAffirmDepId());
			list.add(statistic);
		}
		return list;
	}*/
}