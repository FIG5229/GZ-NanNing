/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.warn.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.warn.entity.WarnHistory;
import com.thinkgem.jeesite.modules.warn.dao.WarnHistoryDao;

/**
 * 预警历史记录Service
 * @author kevin.jia
 * @version 2020-12-28
 */
@Service
@Transactional(readOnly = true)
public class WarnHistoryService extends CrudService<WarnHistoryDao, WarnHistory> {

	public WarnHistory get(String id) {
		return super.get(id);
	}
	
	public List<WarnHistory> findList(WarnHistory warnHistory) {
		return super.findList(warnHistory);
	}
	
	public Page<WarnHistory> findPage(Page<WarnHistory> page, WarnHistory warnHistory) {
		warnHistory.setUserId(UserUtils.getUser().getId());
		page.setOrderBy("a.last_remind_time DESC ");
		return super.findPage(page, warnHistory);
	}
	
	@Transactional(readOnly = false)
	public void save(WarnHistory warnHistory) {
		super.save(warnHistory);
	}
	
	@Transactional(readOnly = false)
	public void delete(WarnHistory warnHistory) {
		super.delete(warnHistory);
	}

	public WarnHistory selByWarnIdReceiveId(String warnId, String receivePerId) {
		return dao.selByWarnIdReceiveId(warnId,receivePerId);
	}
}