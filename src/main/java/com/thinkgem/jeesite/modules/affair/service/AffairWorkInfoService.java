/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairWorkInfoDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkInfo;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 工委工作信息Service
 * @author cecil.li
 * @version 2019-11-07
 */
@Service
@Transactional(readOnly = true)
public class AffairWorkInfoService extends CrudService<AffairWorkInfoDao, AffairWorkInfo> {

	public AffairWorkInfo get(String id) {
		return super.get(id);
	}
	
	public List<AffairWorkInfo> findList(AffairWorkInfo affairWorkInfo) {
		return super.findList(affairWorkInfo);
	}
	
	public Page<AffairWorkInfo> findPage(Page<AffairWorkInfo> page, AffairWorkInfo affairWorkInfo) {
		affairWorkInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairWorkInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairWorkInfo affairWorkInfo) {
		affairWorkInfo.setMainContent(StringEscapeUtils.unescapeHtml4(affairWorkInfo.getMainContent()));
		super.save(affairWorkInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairWorkInfo affairWorkInfo) {
		super.delete(affairWorkInfo);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}

	public int getCount(String id, String startTime, String endTime, String year){
		return dao.getCount(id, startTime, endTime, year);
	}
}