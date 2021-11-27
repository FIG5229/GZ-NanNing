/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.dao.ExamCheckDao1;
import com.thinkgem.jeesite.modules.exam.entity.ExamCheck1;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 检查法相情况录入Service
 * @author mason.xv
 * @version 2019-12-09
 */
@Service
@Transactional(readOnly = true)
public class ExamCheckService1 extends CrudService<ExamCheckDao1, ExamCheck1> {

	@Autowired
	private ExamCheckDao1 examCheckDao1;

	public ExamCheck1 get(String id) {
		return super.get(id);
	}
	
	public List<ExamCheck1> findList(ExamCheck1 examCheck1) {
		return super.findList(examCheck1);
	}
	
	public Page<ExamCheck1> findPage(Page<ExamCheck1> page, ExamCheck1 examCheck1) {
		examCheck1.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","u"));
		return super.findPage(page, examCheck1);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamCheck1 examCheck1) {
		examCheck1.setStatus("3");//未审核
		super.save(examCheck1);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamCheck1 examCheck1) {
		super.delete(examCheck1);
	}

	@Transactional(readOnly = false)
	public void shenHeSave(ExamCheck1 examCheck1) {
		examCheck1.setUpdateDate(new Date());
//		examCheck1.setShPerson(UserUtils.getUser().getName());
		examCheckDao1.shenHeSave(examCheck1);
	}
}