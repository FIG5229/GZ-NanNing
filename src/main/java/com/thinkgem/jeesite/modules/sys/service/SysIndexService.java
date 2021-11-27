/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.SysIndex;
import com.thinkgem.jeesite.modules.sys.dao.SysIndexDao;

/**
 * 索引管理Service
 * @author tom.fu
 * @version 2020-09-16
 */
@Service
@Transactional(readOnly = true)
public class SysIndexService extends CrudService<SysIndexDao, SysIndex> {

	@Autowired
	private SysIndexDao sysIndexDao;

	public SysIndex get(String id) {
		return super.get(id);
	}
	
	public List<SysIndex> findList(SysIndex sysIndex) {
		return super.findList(sysIndex);
	}
	
	public Page<SysIndex> findPage(Page<SysIndex> page, SysIndex sysIndex) {
		return super.findPage(page, sysIndex);
	}
	
	@Transactional(readOnly = false)
	public void save(SysIndex sysIndex) {
		super.save(sysIndex);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysIndex sysIndex) {
		super.delete(sysIndex);
	}

	/**
	 * 查询出sys_role表中的所有id作为角色编号的下拉框
	 * @return
	 */
	public List<SysIndex> selectIds(){
		return sysIndexDao.selectIds();
	}

	/**
	 * 查询出sys_role表中的所有id作为角色编号的下拉框
	 * @return
	 */
	public String selectName(String id){
		return sysIndexDao.selectName(id);
	}

	public List<SysIndex> findListByRoles(){
		List<Role> roleList = UserUtils.getUser().getRoleList();
		List<String> idList = new ArrayList<String>();
		for (int r = 0; r < roleList.size(); r++){
			Role role = roleList.get(r);
			String id = role.getId();
			idList.add(id);
		}
		return sysIndexDao.findListByRoles(idList);
	}
	}