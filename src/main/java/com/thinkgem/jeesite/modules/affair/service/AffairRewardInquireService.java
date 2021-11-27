/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairRewardInquireDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairRewardInquire;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 奖励查询Service
 * @author cecil.li
 * @version 2019-12-21
 */
@Service
@Transactional(readOnly = true)
public class AffairRewardInquireService extends CrudService<AffairRewardInquireDao, AffairRewardInquire> {

	@Autowired
	private AffairRewardInquireDao affairRewardInquireDao;


	public AffairRewardInquire get(String id) {
		return super.get(id);
	}
	
	public List<AffairRewardInquire> findList(AffairRewardInquire affairRewardInquire) {
		if ("d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId())){
			affairRewardInquire.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairRewardInquire.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findList(affairRewardInquire);
	}
	
	public Page<AffairRewardInquire> findPage(Page<AffairRewardInquire> page, AffairRewardInquire affairRewardInquire) {
		if ("d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId())){
			affairRewardInquire.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairRewardInquire.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairRewardInquire);
	}
}