/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkRecord;
import com.thinkgem.jeesite.modules.affair.dao.AffairWorkRecordDao;

/**
 * 民警记实功能Service
 * @author tom.fu
 * @version 2020-08-21
 */
@Service
@Transactional(readOnly = true)
public class AffairWorkRecordService extends CrudService<AffairWorkRecordDao, AffairWorkRecord> {
	@Autowired
	private PersonnelBaseService personnelBaseService;

	public AffairWorkRecord get(String id) {
		return super.get(id);
	}
	
	public List<AffairWorkRecord> findList(AffairWorkRecord affairWorkRecord) {
		return super.findList(affairWorkRecord);
	}
	
	public Page<AffairWorkRecord> findPage(Page<AffairWorkRecord> page, AffairWorkRecord affairWorkRecord) {
		User user = UserUtils.getUser();
		String no = user.getNo();
		PersonnelBase personnel = personnelBaseService.findInfoByIdNumber(no);
		if(personnel!= null && StringUtils.isNotBlank(personnel.getId())){
			affairWorkRecord.setIsPersonnel(true);
		}else{
			affairWorkRecord.setIsPersonnel(false);
		}
		// 调整拼接过滤sql，增加审核人
		if(StringUtils.isNotBlank(dataScopeFilter(UserUtils.getUser(),"o","u"))){
			String sql = dataScopeFilter(UserUtils.getUser(),"o","u").replace("AND","OR");
			affairWorkRecord.getSqlMap().put("dsf","AND (a.chu_check_id = '"+user.getId() + "'  "+sql+" )");
		}else{
			affairWorkRecord.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","u"));
		}
		affairWorkRecord.setUserId(user.getId());
		affairWorkRecord.setOfficeId(user.getOffice().getId());
		return super.findPage(page, affairWorkRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairWorkRecord affairWorkRecord) {
		super.save(affairWorkRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairWorkRecord affairWorkRecord) {
		super.delete(affairWorkRecord);
	}

	public List<AffairWorkRecord> findByIds(List<String> idList) {
		return dao.findByIds(idList);
	}
}