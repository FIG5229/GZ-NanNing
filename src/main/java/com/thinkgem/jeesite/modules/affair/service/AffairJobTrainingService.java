/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairJobTraining;
import com.thinkgem.jeesite.modules.affair.dao.AffairJobTrainingDao;

/**
 * 岗位练兵功能Service
 * @author tom.fu
 * @version 2020-08-03
 */
@Service
@Transactional(readOnly = true)
public class AffairJobTrainingService extends CrudService<AffairJobTrainingDao, AffairJobTraining> {

	@Autowired
	private AffairJobTrainingDao affairJobTrainingDao;

	public AffairJobTraining get(String id) {
		return super.get(id);
	}
	
	public List<AffairJobTraining> findList(AffairJobTraining affairJobTraining) {
		return super.findList(affairJobTraining);
	}
	
	public Page<AffairJobTraining> findPage(Page<AffairJobTraining> page, AffairJobTraining affairJobTraining) {
		affairJobTraining.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, affairJobTraining);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairJobTraining affairJobTraining) {
		if(!"".equals(affairJobTraining.getDrillDateBegin()) && null != affairJobTraining.getDrillDateBegin() && !"".equals(affairJobTraining.getDrillDateOver()) && null != affairJobTraining.getDrillDateOver()){
			Date drillDateBegin = affairJobTraining.getDrillDateBegin();
			Date drillDateOver = affairJobTraining.getDrillDateOver();
			long time = drillDateBegin.getTime();
			long time1 = drillDateOver.getTime();
			long hm = time1 - time;
			long m = hm / 1000;
			long fz = m / 60;
			Long xunLianTime = fz / 60;
			affairJobTraining.setXunlianTime(String.valueOf(xunLianTime));
		}
		if("".equals(affairJobTraining.getOrganizationId()) || null == affairJobTraining.getOrganizationId() && (affairJobTraining.getOrganization() != null) || !"".equals(affairJobTraining.getOrganizationId())){
			String organization = affairJobTraining.getOrganization();
			String organizationId = affairJobTrainingDao.findofficeId(organization);

			affairJobTraining.setOrganizationId(organizationId);

		}

		super.save(affairJobTraining);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairJobTraining affairJobTraining) {
		super.delete(affairJobTraining);
	}
	
}