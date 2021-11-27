/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairSendTeacherDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairSendTeacher;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 送教上门Service
 * @author jack.xu
 * @version 2020-07-20
 */
@Service
@Transactional(readOnly = true)
public class AffairSendTeacherService extends CrudService<AffairSendTeacherDao, AffairSendTeacher> {
	
	@Autowired
	private AffairSendTeacherDao affairSendTeacherDao;

	public AffairSendTeacher get(String id) {
		return super.get(id);
	}
	
	public List<AffairSendTeacher> findList(AffairSendTeacher affairSendTeacher) {
		affairSendTeacher.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(affairSendTeacher);
	}
	
	public Page<AffairSendTeacher> findPage(Page<AffairSendTeacher> page, AffairSendTeacher affairSendTeacher) {
		affairSendTeacher.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairSendTeacher);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairSendTeacher affairSendTeacher) {
		String unitId = affairSendTeacher.getUnitId();
		String unit = affairSendTeacher.getUnit();
		if("".equals(unitId) || null == unitId && (!"".equals(unit) && null != unit)){
			String Id = affairSendTeacherDao.findofficeId(unit);
			affairSendTeacher.setUnitId(Id);
		}
		super.save(affairSendTeacher);
	}

	/**
	 * 批量查找
	 * @param
	 * */
	public List<AffairSendTeacher> findByIdS(List<String> ids){
		List<AffairSendTeacher> list = affairSendTeacherDao.findByIds(ids);
		return list;
	}
	@Transactional(readOnly = false)
	public void delete(AffairSendTeacher affairSendTeacher) {
		super.delete(affairSendTeacher);
	}
	
}