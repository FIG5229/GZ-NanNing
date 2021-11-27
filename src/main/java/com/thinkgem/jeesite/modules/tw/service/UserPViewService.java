/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.tw.entity.UserPView;
import com.thinkgem.jeesite.modules.tw.dao.UserPViewDao;

/**
 * 自动考评-人员组织情况Service
 * @author alan.wu
 * @version 2020-11-19
 */
@Service
@Transactional(readOnly = true)
public class UserPViewService extends CrudService<UserPViewDao, UserPView> {

	public UserPView get(String id) {
		return super.get(id);
	}
	
	public List<UserPView> findList(UserPView userPView) {
		return super.findList(userPView);
	}
	
	public Page<UserPView> findPage(Page<UserPView> page, UserPView userPView) {
		return super.findPage(page, userPView);
	}
	
	@Transactional(readOnly = false)
	public void save(UserPView userPView) {
		super.save(userPView);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserPView userPView) {
		super.delete(userPView);
	}
	
}