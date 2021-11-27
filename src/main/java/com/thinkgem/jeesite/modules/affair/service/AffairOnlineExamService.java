/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairOnlineExam;
import com.thinkgem.jeesite.modules.affair.dao.AffairOnlineExamDao;

/**
 * 线上考试Service
 * @author cecil.li
 * @version 2020-12-29
 */
@Service
@Transactional(readOnly = true)
public class AffairOnlineExamService extends CrudService<AffairOnlineExamDao, AffairOnlineExam> {

	public AffairOnlineExam get(String id) {
		return super.get(id);
	}
	
	public List<AffairOnlineExam> findList(AffairOnlineExam affairOnlineExam) {
		return super.findList(affairOnlineExam);
	}
	
	public Page<AffairOnlineExam> findPage(Page<AffairOnlineExam> page, AffairOnlineExam affairOnlineExam) {
		affairOnlineExam.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairOnlineExam);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairOnlineExam affairOnlineExam) {
		super.save(affairOnlineExam);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairOnlineExam affairOnlineExam) {
		super.delete(affairOnlineExam);
	}
	
}