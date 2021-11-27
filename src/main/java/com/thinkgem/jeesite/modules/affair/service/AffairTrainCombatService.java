/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.affair.entity.AffairClassManage;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainCombat;
import com.thinkgem.jeesite.modules.affair.dao.AffairTrainCombatDao;

/**
 * 实弹训练Service
 * @author jack.xu
 * @version 2020-07-15
 */
@Service
@Transactional(readOnly = true)
public class AffairTrainCombatService extends CrudService<AffairTrainCombatDao, AffairTrainCombat> {

	@Autowired
	private  AffairTrainCombatDao affairTrainCombatDao;

	public AffairTrainCombat get(String id) {
		return super.get(id);
	}
	
	public List<AffairTrainCombat> findList(AffairTrainCombat affairTrainCombat) {
		affairTrainCombat.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(affairTrainCombat);
	}
	
	public Page<AffairTrainCombat> findPage(Page<AffairTrainCombat> page, AffairTrainCombat affairTrainCombat) {
		affairTrainCombat.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		if(!"".equals(affairTrainCombat.getOrderBy()) && null != affairTrainCombat.getOrderBy()){
			page.setOrderBy("cast(a.average as decimal) "+affairTrainCombat.getOrderBy());
		}
		/*String maxBulletNum = affairTrainCombat.getMaxBulletNum();
		affairTrainCombat.setMaxBulletNum(Float.parseFloat(maxBulletNum));*/
		return super.findPage(page, affairTrainCombat);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTrainCombat affairTrainCombat) {
		String idNumber = affairTrainCombat.getIdNumber();
		if(!"".equals(idNumber) && null != idNumber ){
			PersonnelBase personnelBase = affairTrainCombatDao.findofficeId(idNumber);
			affairTrainCombat.setOrganization(personnelBase.getWorkunitName());
			affairTrainCombat.setOrganizationId(personnelBase.getWorkunitId());
		}


		super.save(affairTrainCombat);
	}

	/**
	 * 批量查找
	 * @param
	 */
	public List<AffairTrainCombat> findByIds(List<String> ids){
		List<AffairTrainCombat> list = affairTrainCombatDao.findByIds(ids);
		return list;
	}
	@Transactional(readOnly = false)
	public void delete(AffairTrainCombat affairTrainCombat) {
		super.delete(affairTrainCombat);
	}
	
}