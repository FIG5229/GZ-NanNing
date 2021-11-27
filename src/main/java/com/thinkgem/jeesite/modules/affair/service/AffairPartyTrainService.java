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
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyTrain;
import com.thinkgem.jeesite.modules.affair.dao.AffairPartyTrainDao;

/**
 * 党内培训Service
 * @author daniel.liu
 * @version 2020-06-09
 */
@Service
@Transactional(readOnly = true)
public class AffairPartyTrainService extends CrudService<AffairPartyTrainDao, AffairPartyTrain> {

	public AffairPartyTrain get(String id) {
		return super.get(id);
	}
	
	public List<AffairPartyTrain> findList(AffairPartyTrain affairPartyTrain) {
		return super.findList(affairPartyTrain);
	}
	
	public Page<AffairPartyTrain> findPage(Page<AffairPartyTrain> page, AffairPartyTrain affairPartyTrain) {
		affairPartyTrain.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairPartyTrain);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPartyTrain affairPartyTrain) {
		super.save(affairPartyTrain);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPartyTrain affairPartyTrain) {
		super.delete(affairPartyTrain);
	}
	
}