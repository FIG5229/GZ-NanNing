/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.entity.ExamObjectType;
import com.thinkgem.jeesite.modules.exam.dao.ExamObjectTypeDao;

/**
 * 被考评对象类别关系表Service
 * @author kevin.jia
 * @version 2021-02-22
 */
@Service
@Transactional(readOnly = true)
public class ExamObjectTypeService extends CrudService<ExamObjectTypeDao, ExamObjectType> {

	public ExamObjectType get(String id) {
		return super.get(id);
	}
	
	public List<ExamObjectType> findList(ExamObjectType examObjectType) {
		return super.findList(examObjectType);
	}
	
	public Page<ExamObjectType> findPage(Page<ExamObjectType> page, ExamObjectType examObjectType) {
		//examObjectType.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		String userId = UserUtils.getUser().getId();
		if("1".equals(userId)){
			examObjectType.setUserId(null);
		}else{
			examObjectType.setUserId(userId);
		}
		return super.findPage(page, examObjectType);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamObjectType examObjectType) {
		super.save(examObjectType);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamObjectType examObjectType) {
		super.delete(examObjectType);
	}

    public List<ExamObjectType> getALLNameId(ExamObjectType examObjectType) {
		//examObjectType.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		String userId = UserUtils.getUser().getId();
		if("1".equals(userId)){
			examObjectType.setUserId(null);
		}else{
			examObjectType.setUserId(userId);
		}
		return dao.getAllNameId(examObjectType);
    }

}