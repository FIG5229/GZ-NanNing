/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairGhActivityEnrollChildrenDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairGhActivityEnrollDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGhActivityEnroll;
import com.thinkgem.jeesite.modules.affair.entity.AffairGhActivityEnrollChildren;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceHomeChild;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 工会活动报名Service
 * @author cecil.li
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class AffairGhActivityEnrollService extends CrudService<AffairGhActivityEnrollDao, AffairGhActivityEnroll> {

	@Autowired
	AffairGhActivityEnrollDao affairGhActivityEnrollDao;

	@Autowired
	AffairGhActivityEnrollChildrenDao childrenDao;

	public AffairGhActivityEnroll get(String id) {
		return super.get(id);
	}
	
	public List<AffairGhActivityEnroll> findList(AffairGhActivityEnroll affairGhActivityEnroll) {
        affairGhActivityEnroll.setUserId(UserUtils.getUser().getId());
        /*affairGhActivityEnroll.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/
		return super.findList(affairGhActivityEnroll);
	}
	
	public Page<AffairGhActivityEnroll> findPage(Page<AffairGhActivityEnroll> page, AffairGhActivityEnroll affairGhActivityEnroll) {
		affairGhActivityEnroll.setUserId(UserUtils.getUser().getId());
		return super.findPage(page, affairGhActivityEnroll);
	}

	//更新保存
	@Override
	@Transactional(readOnly = false)
	public void save(AffairGhActivityEnroll affairGhActivityEnroll) {
		if (affairGhActivityEnroll.getCheckType() == null||"".equals(affairGhActivityEnroll.getCheckType())){
			affairGhActivityEnroll.setCheckType("1");
		}
		super.save(affairGhActivityEnroll);
	}
	//添加保存
	@Transactional(readOnly = false)
	public void addSave(AffairGhActivityEnroll affairGhActivityEnroll) {
		if (affairGhActivityEnroll.getCheckType() == null||"".equals(affairGhActivityEnroll.getCheckType())){
			affairGhActivityEnroll.setCheckType("1");
		}
		//super.save(affairGhActivityEnroll);
		if(affairGhActivityEnroll.getActivityEnrollChildrenList()!=null){
			for (AffairGhActivityEnrollChildren children : affairGhActivityEnroll.getActivityEnrollChildrenList()){
				if (children.getId() == null) {
					continue;
				}
				children.setCreateOrgId(affairGhActivityEnroll.getCreateOrgId());//设置创建者机构id
				children.setUpdateOrgId(affairGhActivityEnroll.getUpdateOrgId());//设置更新者机构id
				if (AffairGhActivityEnrollChildren.DEL_FLAG_NORMAL.equals(children.getDelFlag())) {
					if (StringUtils.isBlank(children.getId())) {
						//设置基本信息
						AffairGhActivityEnroll activityEnroll = new AffairGhActivityEnroll();
						activityEnroll.setDate(affairGhActivityEnroll.getDate());
						activityEnroll.setProject(affairGhActivityEnroll.getProject());
						activityEnroll.setCreateBy(affairGhActivityEnroll.getCreateBy());
						activityEnroll.setCreateOrgId(affairGhActivityEnroll.getCreateOrgId());
						activityEnroll.setCreateIdNo(affairGhActivityEnroll.getCreateIdNo());
						activityEnroll.setUpdateBy(affairGhActivityEnroll.getUpdateBy());
						activityEnroll.setUpdateOrgId(affairGhActivityEnroll.getUpdateOrgId());
						activityEnroll.setUpdateIdNo(affairGhActivityEnroll.getUpdateIdNo());
						activityEnroll.setDelFlag("0");
						activityEnroll.setOpinion(affairGhActivityEnroll.getOpinion());
						activityEnroll.setCheckType(affairGhActivityEnroll.getCheckType());
						//设置人员信息
						activityEnroll.setRemark(children.getRemark());
						activityEnroll.setUnit(children.getUnit());
						activityEnroll.setUnitId(children.getUnitId());
						activityEnroll.setName(children.getName());
						activityEnroll.setIdNumber(children.getIdNumber());
						activityEnroll.setBirthday(children.getBirthday());
						activityEnroll.setSex(children.getSex());
						activityEnroll.setJob(children.getJob());
						activityEnroll.setIsNewRecord(true);
						IdGen idGen=new IdGen();
						String id = idGen.getNextId();
						activityEnroll.setId(id);
						super.save(activityEnroll);
						children.setGhActivityEnrollId(id);
						children.preInsert();
						childrenDao.insert(children);
					} else {
						//设置基本信息
						AffairGhActivityEnroll activityEnroll = new AffairGhActivityEnroll();
						activityEnroll.setDate(affairGhActivityEnroll.getDate());
						activityEnroll.setProject(affairGhActivityEnroll.getProject());
						activityEnroll.setCreateBy(affairGhActivityEnroll.getCreateBy());
						activityEnroll.setCreateOrgId(affairGhActivityEnroll.getCreateOrgId());
						activityEnroll.setCreateIdNo(affairGhActivityEnroll.getCreateIdNo());
						activityEnroll.setUpdateBy(affairGhActivityEnroll.getUpdateBy());
						activityEnroll.setUpdateOrgId(affairGhActivityEnroll.getUpdateOrgId());
						activityEnroll.setUpdateIdNo(affairGhActivityEnroll.getUpdateIdNo());
						activityEnroll.setDelFlag("0");
						activityEnroll.setOpinion(affairGhActivityEnroll.getOpinion());
						activityEnroll.setCheckType(affairGhActivityEnroll.getCheckType());
						//设置人员信息
						activityEnroll.setRemark(children.getRemark());
						activityEnroll.setUnit(children.getUnit());
						activityEnroll.setUnitId(children.getUnitId());
						activityEnroll.setName(children.getName());
						activityEnroll.setIdNumber(children.getIdNumber());
						activityEnroll.setBirthday(children.getBirthday());
						activityEnroll.setSex(children.getSex());
						activityEnroll.setJob(children.getJob());
						activityEnroll.setIsNewRecord(true);
						IdGen idGen=new IdGen();
						String id = idGen.getNextId();
						activityEnroll.setId(id);
						super.save(activityEnroll);
						children.setGhActivityEnrollId(id);
						children.preUpdate();
						childrenDao.update(children);
					}
				} else {
					childrenDao.delete(children);
				}
			}
		}else{
			super.save(affairGhActivityEnroll);
		}
	}
	/**
	 * 批量提交
	 * @param ids
	 */
	public List<AffairGhActivityEnroll> findByIds(List<String> ids){
		List<AffairGhActivityEnroll> list = affairGhActivityEnrollDao.findByIds(ids);
		return list;
	}

	@Transactional(readOnly = false)
	public void delete(AffairGhActivityEnroll affairGhActivityEnroll) {
		super.delete(affairGhActivityEnroll);
		childrenDao.deleteByGhActivityEnrolId(affairGhActivityEnroll.getId());
	}

	//审核功能,废弃不用
	/*@Transactional(readOnly = false)
	public void shenHe(AffairGhActivityEnroll affairGhActivityEnroll) {
		affairGhActivityEnroll.setUpdateDate(new Date());
		affairGhActivityEnrollDao.shenHe(affairGhActivityEnroll);
	}*/

	public Map<String, Object> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteByIds(List<String> ids) {
		super.deleteByIds(ids);
		childrenDao.deleteByGhActivityEnrolIds(ids);
	}
}