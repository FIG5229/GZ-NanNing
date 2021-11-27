/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairActivistDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairActivist;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 入党积极分子Service
 * @author eav.liu
 * @version 2019-10-31
 */
@Service
@Transactional(readOnly = true)
public class AffairActivistService extends CrudService<AffairActivistDao, AffairActivist> {

	@Autowired
	private AffairActivistDao affairActivistDao;

	public AffairActivist get(String id) {
		return super.get(id);
	}
	
	public List<AffairActivist> findList(AffairActivist affairActivist) {
		return super.findList(affairActivist);
	}
	
	public Page<AffairActivist> findPage(Page<AffairActivist> page, AffairActivist affairActivist) {
		affairActivist.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairActivist);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairActivist affairActivist) {
		super.save(affairActivist);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairActivist affairActivist) {
		super.delete(affairActivist);
	}

	public List<String> findListByIdNo(String idNo) {
		return affairActivistDao.findListByIdNo(idNo);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNo(String idNo) {
		affairActivistDao.deleteByIdNo(idNo);
	}

	/**
	 * 统计分析明细 	入党积极分子详情
	 * @param affairActivistPage
	 * @param affairActivist
	 * @return
	 */
	public Page<AffairActivist> findActivistPage(Page<AffairActivist> affairActivistPage, AffairActivist affairActivist) {
		affairActivist.setPage(affairActivistPage);
		//按照月度查询
		if (affairActivist.getDateType().equals("1")){
			affairActivist.setDateEnd(null);
			affairActivist.setDateStart(null);
			affairActivist.setYear(null);
		}else if (affairActivist.getDateType().equals("2")){//按照年度查询
			affairActivist.setDateEnd(null);
			affairActivist.setDateStart(null);
			affairActivist.setMonth(null);
		}else {	//按照时间段查询
			affairActivist.setYear(null);
			affairActivist.setMonth(null);

		}
		affairActivistPage.setList(dao.finActivistList(affairActivist));
		return affairActivistPage;
    }

	public List<AffairActivist> findAllActivistList() {
		return dao.findAllActivistList();
	}
}