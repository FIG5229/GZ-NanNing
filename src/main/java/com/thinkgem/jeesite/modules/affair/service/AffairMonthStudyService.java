/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairMonthStudyDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairMonthStudy;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 月度学习计划Service
 * @author mason.xv
 * @version 2019-11-02
 */
@Service
@Transactional(readOnly = true)
public class AffairMonthStudyService extends CrudService<AffairMonthStudyDao, AffairMonthStudy> {

	public AffairMonthStudy get(String id) {
		return super.get(id);
	}
	
	public List<AffairMonthStudy> findList(AffairMonthStudy affairMonthStudy) {
		return super.findList(affairMonthStudy);
	}
	
	public Page<AffairMonthStudy> findPage(Page<AffairMonthStudy> page, AffairMonthStudy affairMonthStudy) {
		/*affairMonthStudy.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/
		return super.findPage(page, affairMonthStudy);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairMonthStudy affairMonthStudy) {
		affairMonthStudy.setContent(StringEscapeUtils.unescapeHtml4(affairMonthStudy.getContent()));
		super.save(affairMonthStudy);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairMonthStudy affairMonthStudy) {
		super.delete(affairMonthStudy);
	}
	
}