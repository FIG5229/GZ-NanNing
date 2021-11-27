/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairYqContolDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairYqContol;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 舆情管控Service
 * @author cecil.li
 * @version 2019-11-02
 */
@Service
@Transactional(readOnly = true)
public class AffairYqContolService extends CrudService<AffairYqContolDao, AffairYqContol> {

	@Autowired
	private AffairYqContolDao affairYqContolDao;

	public AffairYqContol get(String id) {
		return super.get(id);
	}
	
	public List<AffairYqContol> findList(AffairYqContol affairYqContol) {
		return super.findList(affairYqContol);
	}
	
	public Page<AffairYqContol> findPage(Page<AffairYqContol> page, AffairYqContol affairYqContol) {
//		affairYqContol.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		affairYqContol.setUserId(UserUtils.getUser().getId());
		return super.findPage(page, affairYqContol);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairYqContol affairYqContol) {
		affairYqContol.setContent(StringEscapeUtils.unescapeHtml4(affairYqContol.getContent()));
		super.save(affairYqContol);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairYqContol affairYqContol) {
		super.delete(affairYqContol);
	}


		public List<Map<String, Object>> findInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
			return dao.findInfoByCreateOrgId(id, year, startDate, endDate, month);
		}

		public List<Map<String, Object>> findInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
			return dao.findInfoByCreateOrgIds(ids, year, startDate, endDate, month);
		}

		public List<Map<String, Object>> findInfoByCreateOrgId2(String id, Integer year, Date startDate, Date endDate, String month) {
			return dao.findInfoByCreateOrgId2(id, year, startDate, endDate, month);
		}

		public List<Map<String, Object>> findInfoByCreateOrgIds2(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
			return dao.findInfoByCreateOrgIds2(ids, year, startDate, endDate, month);
		}

	/**
	 * 批量提交
	 * @param ids
	 */
	public List<AffairYqContol> findByIds(List<String> ids){
		List<AffairYqContol> list = affairYqContolDao.findByIds(ids);
		return list;
	}
}