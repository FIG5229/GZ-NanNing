/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.Arrays;
import java.util.List;

import com.thinkgem.jeesite.modules.affair.dao.AffairPolicewomanWorkReceiveDao;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairPolicewomanWorkDao;

/**
 * 女警工作管理Service
 * @author eav.liu
 * @version 2020-03-26
 */
@Service
@Transactional(readOnly = true)
public class AffairPolicewomanWorkService extends CrudService<AffairPolicewomanWorkDao, AffairPolicewomanWork> {

	@Autowired
	private AffairPolicewomanWorkDao affairPolicewomanWorkDao;

	@Autowired
	private AffairPolicewomanWorkReceiveDao affairPolicewomanWorkReceiveDao;

	@Autowired
	private OfficeDao officeDao;

	public AffairPolicewomanWork get(String id) {
		return super.get(id);
	}
	
	public List<AffairPolicewomanWork> findList(AffairPolicewomanWork affairPolicewomanWork) {
		return super.findList(affairPolicewomanWork);
	}
	
	public Page<AffairPolicewomanWork> findPage(Page<AffairPolicewomanWork> page, AffairPolicewomanWork affairPolicewomanWork) {
		User user = UserUtils.getUser();
		//存放当前用户
		affairPolicewomanWork.setCreateBy(user);
		affairPolicewomanWork.getSqlMap().put("dsf", dataScopeFilter(user, "o", "u"));
		return super.findPage(page, affairPolicewomanWork);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPolicewomanWork affairPolicewomanWork) {
		affairPolicewomanWork.setPublisher(UserUtils.getUser().getName());
		affairPolicewomanWork.setContent(StringEscapeUtils.unescapeHtml4(affairPolicewomanWork.getContent()));
		super.save(affairPolicewomanWork);
		if("2".equals(affairPolicewomanWork.getStatus())){//发布
			String receiveDepIds = affairPolicewomanWork.getReceiveDepId();
			//先删除关联表原来的数据
			affairPolicewomanWorkReceiveDao.deleteByNoticeId(affairPolicewomanWork.getId());
			//关联表插入新的数据
			List<String> idList = Arrays.asList(receiveDepIds.split(","));
			for (String id : idList) {
				AffairPolicewomanWorkReceive affairPolicewomanWorkReceive = new AffairPolicewomanWorkReceive();
				affairPolicewomanWorkReceive.setPwWorkId(affairPolicewomanWork.getId());
				affairPolicewomanWorkReceive.setOrgId(id);
				affairPolicewomanWorkReceive.preInsert();
				affairPolicewomanWorkReceiveDao.insert(affairPolicewomanWorkReceive);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPolicewomanWork affairPolicewomanWork) {
		super.delete(affairPolicewomanWork);
        //删除关联表
        affairPolicewomanWorkReceiveDao.deleteByNoticeId(affairPolicewomanWork.getId());
	}

	@Transactional(readOnly = false)
	public void deleteByIds(List<String> ids){
		//删除关联表的数据
		super.deleteByIds(ids);
		//删除关联表
		for (String id:ids) {
			affairPolicewomanWorkReceiveDao.deleteByNoticeId(id);
		}
	};

	/**
	 * 批量发布
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void publishByIds(List<String> ids){
		List<AffairPolicewomanWork> list = affairPolicewomanWorkDao.findByIds(ids);
		for (AffairPolicewomanWork affairPolicewomanWork:list) {
			//过滤掉已经发布的
			if(!"2".equals(affairPolicewomanWork.getStatus())){
				affairPolicewomanWork.setStatus("2");
				super.save(affairPolicewomanWork);
				String receiveDepIds = affairPolicewomanWork.getReceiveDepId();
				//先删除关联表原来的数据
				affairPolicewomanWorkReceiveDao.deleteByNoticeId(affairPolicewomanWork.getId());
				//关联表插入新的数据
				List<String> idList = Arrays.asList(receiveDepIds.split(","));
				for (String id : idList) {
					AffairPolicewomanWorkReceive affairPolicewomanWorkReceive = new AffairPolicewomanWorkReceive();
					affairPolicewomanWorkReceive.setPwWorkId(affairPolicewomanWork.getId());
					affairPolicewomanWorkReceive.setOrgId(id);
					//单位名称
					Office office = officeDao.findOneById(id);
					affairPolicewomanWorkReceive.preInsert();
					affairPolicewomanWorkReceiveDao.insert(affairPolicewomanWorkReceive);
				}
			}
		}
	};

	/**
	 * 批量取消发布
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void cancelByIds(List<String> ids){
		List<AffairPolicewomanWork> list = affairPolicewomanWorkDao.findByIds(ids);
		for (AffairPolicewomanWork affairPolicewomanWork: list) {
			affairPolicewomanWork.setStatus("3");
			//主表更新
			super.save(affairPolicewomanWork);
			//关联表删除
			affairPolicewomanWorkReceiveDao.deleteByNoticeId(affairPolicewomanWork.getId());
		}
	};
}