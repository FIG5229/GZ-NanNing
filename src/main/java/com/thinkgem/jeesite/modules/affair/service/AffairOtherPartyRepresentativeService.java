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
import com.thinkgem.jeesite.modules.affair.entity.AffairOtherPartyRepresentative;
import com.thinkgem.jeesite.modules.affair.dao.AffairOtherPartyRepresentativeDao;

/**
 * 其他党代表会Service
 * @author daniel.liu
 * @version 2020-06-30
 */
@Service
@Transactional(readOnly = true)
public class AffairOtherPartyRepresentativeService extends CrudService<AffairOtherPartyRepresentativeDao, AffairOtherPartyRepresentative> {

	public AffairOtherPartyRepresentative get(String id) {
		return super.get(id);
	}
	
	public List<AffairOtherPartyRepresentative> findList(AffairOtherPartyRepresentative affairOtherPartyRepresentative) {
		return super.findList(affairOtherPartyRepresentative);
	}
	
	public Page<AffairOtherPartyRepresentative> findPage(Page<AffairOtherPartyRepresentative> page, AffairOtherPartyRepresentative affairOtherPartyRepresentative) {
		affairOtherPartyRepresentative.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairOtherPartyRepresentative);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairOtherPartyRepresentative affairOtherPartyRepresentative) {
		super.save(affairOtherPartyRepresentative);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairOtherPartyRepresentative affairOtherPartyRepresentative) {
		super.delete(affairOtherPartyRepresentative);
	}

	/**
	 * 统计分析明细  党代表会详情
	 * union all 上级党代表会 局代表会 处代表会
	 * @param page
	 * @param representative
	 * @return
	 */
	public Page<AffairOtherPartyRepresentative> findRepresentativePage(Page<AffairOtherPartyRepresentative> page, AffairOtherPartyRepresentative representative) {
		//按照月度查询
		if (representative.getDateType().equals("1")){
			representative.setDateEnd(null);
			representative.setDateStart(null);
			representative.setYear(null);
		}else if (representative.getDateType().equals("3")){//按照时间段查询
			representative.setYear(null);
			representative.setMonth(null);
		}else {	//按照年度查询  按照年度查询
			representative.setDateEnd(null);
			representative.setDateStart(null);
			representative.setMonth(null);
		}
		representative.setPage(page);
		page.setList(dao.findRepresentativeList(representative));

		return page;
	}
}