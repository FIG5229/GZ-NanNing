/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairDisciplinaryActionDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairDisciplinaryRegulationDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairDisciplinaryRegulationReceiveDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDisciplinaryRegulation;
import com.thinkgem.jeesite.modules.affair.entity.AffairDisciplinaryRegulationReceive;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 纪律规定Service
 * @author cecil.li
 * @version 2019-11-19
 */
@Service
@Transactional(readOnly = true)
public class AffairDisciplinaryRegulationService extends CrudService<AffairDisciplinaryRegulationDao, AffairDisciplinaryRegulation> {

	@Autowired
	private AffairDisciplinaryActionDao affairDisciplinaryActionDao;

	@Autowired
	private AffairDisciplinaryRegulationReceiveDao affairDisciplinaryRegulationReceiveDao;

	@Autowired
	private AffairDisciplinaryRegulationDao affairDisciplinaryRegulationDao;

	public AffairDisciplinaryRegulation get(String id) {
		return super.get(id);
	}
	
	public List<AffairDisciplinaryRegulation> findList(AffairDisciplinaryRegulation affairDisciplinaryRegulation) {
		return super.findList(affairDisciplinaryRegulation);
	}
	
	public Page<AffairDisciplinaryRegulation> findPage(Page<AffairDisciplinaryRegulation> page, AffairDisciplinaryRegulation affairDisciplinaryRegulation) {
//		affairDisciplinaryRegulation.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		User user = UserUtils.getUser();
		//存放当前用户
		affairDisciplinaryRegulation.setCreateBy(user);
		if (UserUtils.getUser().getRoleList() != null && UserUtils.getUser().getRoleList().size() > 0) {
			for (Role r : UserUtils.getUser().getRoleList()) {
				/**
				 * 1:系统管理员角色
				 * d2a319c7d61f4e5d8dbbd8bd860b71a3：公安局纪检监察管理纪律处分角色
				 * cdff05643493485baa447b8b65b8c537：公安局纪检监察处纪律规定角色
				 */
				if("1".equals(r.getId()) || "d2a319c7d61f4e5d8dbbd8bd860b71a3".equals(r.getId()) || "cdff05643493485baa447b8b65b8c537".equals(r.getId())){
					affairDisciplinaryRegulation.setHasManageAuth(true);
					break;
				}
			}
		}
		return super.findPage(page, affairDisciplinaryRegulation);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairDisciplinaryRegulation affairDisciplinaryRegulation) {
		affairDisciplinaryRegulation.setContent(StringEscapeUtils.unescapeHtml4(affairDisciplinaryRegulation.getContent()));
		super.save(affairDisciplinaryRegulation);
		//先删除关联表原来的数据
		affairDisciplinaryRegulationReceiveDao.deleteByDisRegId(affairDisciplinaryRegulation.getId());
		//插入关联表相关数据
		String receiveUnitIds = affairDisciplinaryRegulation.getReceiveUnitId();
		List<String> idList = Arrays.asList(receiveUnitIds.split(","));
		for (String id : idList) {
			AffairDisciplinaryRegulationReceive affairDisciplinaryRegulationReceive = new AffairDisciplinaryRegulationReceive();
			affairDisciplinaryRegulationReceive.setDisRegId(affairDisciplinaryRegulation.getId());
			affairDisciplinaryRegulationReceive.setUnitId(id);
			affairDisciplinaryRegulationReceive.preInsert();
			affairDisciplinaryRegulationReceiveDao.insert(affairDisciplinaryRegulationReceive);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairDisciplinaryRegulation affairDisciplinaryRegulation) {
		super.delete(affairDisciplinaryRegulation);
		//删除关联表原来的数据
		affairDisciplinaryRegulationReceiveDao.deleteByDisRegId(affairDisciplinaryRegulation.getId());
	}

	//重写批量删除方法
	@Transactional(readOnly = false)
	public void deleteByIds(List<String> ids){
		affairDisciplinaryRegulationDao.deleteByIds(ids);
		//删除关联表的数据
		for (String id:ids) {
			affairDisciplinaryRegulationReceiveDao.deleteByDisRegId(id);
		}
	}

	//置顶实现
	@Transactional(readOnly = false)
	public void updateOrderId(String id,String unitId){
		affairDisciplinaryRegulationDao.updateOrderId(id, unitId);
	}

	//取消置顶实现
	@Transactional(readOnly = false)
	public void reUpdateOrderId(String id,String unitId){
		affairDisciplinaryRegulationDao.reUpdateOrderId(id, unitId);
	}

	public String isExist(String unitId, String disId){
		return  affairDisciplinaryRegulationDao.isExist(unitId,disId);
	}
}