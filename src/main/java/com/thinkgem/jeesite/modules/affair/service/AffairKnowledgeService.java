/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairKnowledgeDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairKnowledgeReceiveDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairKnowledge;
import com.thinkgem.jeesite.modules.affair.entity.AffairKnowledgeReceive;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 党章党规及党建知识Service
 * @author eav.liu
 * @version 2019-11-04
 */
@Service
@Transactional(readOnly = true)
public class AffairKnowledgeService extends CrudService<AffairKnowledgeDao, AffairKnowledge> {

	@Autowired
	private AffairKnowledgeDao affairKnowledgeDao;

	@Autowired
	private AffairKnowledgeReceiveDao affairKnowledgeReceiveDao;

	@Autowired
	private OfficeDao officeDao;

	public AffairKnowledge get(String id) {
		return super.get(id);
	}
	
	public List<AffairKnowledge> findList(AffairKnowledge affairKnowledge) {
		return super.findList(affairKnowledge);
	}
	
	public Page<AffairKnowledge> findPage(Page<AffairKnowledge> page, AffairKnowledge affairKnowledge) {
		User user = UserUtils.getUser();
		//存放当前用户
		affairKnowledge.setCreateBy(user);
		affairKnowledge.getSqlMap().put("dsf", dataScopeFilter(user, "o", "u"));
		Page<AffairKnowledge> Page = super.findPage(page, affairKnowledge);
		return Page;
	}
	
	@Transactional(readOnly = false)
	public void save(AffairKnowledge affairKnowledge) {
		affairKnowledge.setPublisher(UserUtils.getUser().getName());
		 //affairKnowledge.setPublishOrgId(UserUtils.getUser().getOffice().getId());
		affairKnowledge.setContent(StringEscapeUtils.unescapeHtml4(affairKnowledge.getContent()));
		super.save(affairKnowledge);
		if("2".equals(affairKnowledge.getStatus())){//发布
			String receiveDepIds = affairKnowledge.getReceiveDepId();
			//先删除关联表原来的数据
			affairKnowledgeReceiveDao.deleteByNoticeId(affairKnowledge.getId());
			//关联表插入新的数据
			List<String> idList = Arrays.asList(receiveDepIds.split(","));
			for (String id : idList) {
				AffairKnowledgeReceive affairKnowledgeReceive = new AffairKnowledgeReceive();
				affairKnowledgeReceive.setNoticeId(affairKnowledge.getId());
				affairKnowledgeReceive.setOrgId(id);
				//单位名称
				Office office = officeDao.findOneById(id);
				affairKnowledgeReceive.preInsert();
				affairKnowledgeReceiveDao.insert(affairKnowledgeReceive);
			}
		}
	}

	@Transactional(readOnly = false)
	public void delete(AffairKnowledge affairKnowledge) {
		super.delete(affairKnowledge);
	}

	@Transactional(readOnly = false)
	public void deleteByIds(List<String> ids){
		//删除关联表的数据
		super.deleteByIds(ids);
		//删除关联表
		for (String id:ids) {
			affairKnowledgeReceiveDao.deleteByNoticeId(id);
		}
	};

	/**
	 * 批量发布
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void publishByIds(List<String> ids){
		List<AffairKnowledge> list = affairKnowledgeDao.findByIds(ids);
		for (AffairKnowledge affairKnowledge:list) {
			//过滤掉已经发布的
			if(!"2".equals(affairKnowledge.getStatus())){
				affairKnowledge.setStatus("2");
				super.save(affairKnowledge);
				String receiveDepIds = affairKnowledge.getReceiveDepId();
				//先删除关联表原来的数据
				affairKnowledgeReceiveDao.deleteByNoticeId(affairKnowledge.getId());
				//关联表插入新的数据
				List<String> idList = Arrays.asList(receiveDepIds.split(","));
				for (String id : idList) {
					AffairKnowledgeReceive affairKnowledgeReceive = new AffairKnowledgeReceive();
					affairKnowledgeReceive.setNoticeId(affairKnowledge.getId());
					affairKnowledgeReceive.setOrgId(id);
					//单位名称
					Office office = officeDao.findOneById(id);
					affairKnowledgeReceive.preInsert();
					affairKnowledgeReceiveDao.insert(affairKnowledgeReceive);
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
		List<AffairKnowledge> list = affairKnowledgeDao.findByIds(ids);
		for (AffairKnowledge affairKnowledge: list) {
			affairKnowledge.setStatus("3");
			//主表更新
			super.save(affairKnowledge);
			//关联表删除
			affairKnowledgeReceiveDao.deleteByNoticeId(affairKnowledge.getId());
		}
	};
}