/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairHomeVisit;
import com.thinkgem.jeesite.modules.affair.dao.AffairHomeVisitDao;

/**
 * 心谈家访Service
 * @author daniel.liu
 * @version 2020-05-13
 */
@Service
@Transactional(readOnly = true)
public class AffairHomeVisitService extends CrudService<AffairHomeVisitDao, AffairHomeVisit> {

	public AffairHomeVisit get(String id) {
		return super.get(id);
	}
	
	public List<AffairHomeVisit> findList(AffairHomeVisit affairHomeVisit) {
		return super.findList(affairHomeVisit);
	}
	
	public Page<AffairHomeVisit> findPage(Page<AffairHomeVisit> page, AffairHomeVisit affairHomeVisit) {
		affairHomeVisit.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		/*南宁局办公室账号 有角色是看所有数据，谈心家访只看本部门及以下数据，修改数据过滤*/
		if ("54e8fb917a8241c08c04bb3dbe4dee46".equals(UserUtils.getUser().getId())){
			affairHomeVisit.getSqlMap().put("dsf", unitDataScopeFilter(UserUtils.getUser(), "o", "u"));
		}
		return super.findPage(page, affairHomeVisit);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairHomeVisit affairHomeVisit) {
		affairHomeVisit.setContent(StringEscapeUtils.unescapeHtml4(affairHomeVisit.getContent()));

		super.save(affairHomeVisit);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairHomeVisit affairHomeVisit) {
		super.delete(affairHomeVisit);
	}
	
}