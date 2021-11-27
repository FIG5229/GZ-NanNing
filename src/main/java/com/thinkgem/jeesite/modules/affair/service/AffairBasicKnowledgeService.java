/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairJobTrainingDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairHealthCheckup;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairBasicKnowledge;
import com.thinkgem.jeesite.modules.affair.dao.AffairBasicKnowledgeDao;

/**
 * 基本知识成绩Service
 * @author cecil.li
 * @version 2020-12-28
 */
@Service
@Transactional(readOnly = true)
public class AffairBasicKnowledgeService extends CrudService<AffairBasicKnowledgeDao, AffairBasicKnowledge> {

	@Autowired
	private AffairBasicKnowledgeDao affairBasicKnowledgeDao;

	@Autowired
	private AffairJobTrainingDao affairJobTrainingDao;

	public AffairBasicKnowledge get(String id) {
		return super.get(id);
	}
	
	public List<AffairBasicKnowledge> findList(AffairBasicKnowledge affairBasicKnowledge) {
		return super.findList(affairBasicKnowledge);
	}
	
	public Page<AffairBasicKnowledge> findPage(Page<AffairBasicKnowledge> page, AffairBasicKnowledge affairBasicKnowledge) {
		affairBasicKnowledge.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairBasicKnowledge);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairBasicKnowledge affairBasicKnowledge) {
		if("".equals(affairBasicKnowledge.getUnitId()) || null == affairBasicKnowledge.getUnitId() && (affairBasicKnowledge.getUnit() != null) || !"".equals(affairBasicKnowledge.getUnit())){
			String organization = affairBasicKnowledge.getUnit();
			String organizationId = affairJobTrainingDao.findofficeId(organization);
			affairBasicKnowledge.setUnitId(organizationId);
		}
		super.save(affairBasicKnowledge);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairBasicKnowledge affairBasicKnowledge) {
		super.delete(affairBasicKnowledge);
	}

	public int unitCount(String startTime, String endTime, String unitId, String idNumber){
		return affairBasicKnowledgeDao.unitCount(startTime, endTime, unitId, idNumber);
	}

	public List<AffairBasicKnowledge> noPassInfo(String year, String month, String unitId){
		return affairBasicKnowledgeDao.noPassInfo(year, month, unitId);
	}

	public List<AffairBasicKnowledge> allInfo(String year, String month){
		return affairBasicKnowledgeDao.allInfo(year, month);
	}

	//统计分析
	public List<AffairBasicKnowledge> basicInfo(AffairBasicKnowledge affairBasicKnowledge){
		affairBasicKnowledge.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return affairBasicKnowledgeDao.basicInfo(affairBasicKnowledge);
	}

	public Page<AffairBasicKnowledge> findBasicKnoledgePage(Page<AffairBasicKnowledge> page, AffairBasicKnowledge affairBasicKnowledge) {
		if ("1".equals(affairBasicKnowledge.getDateType())){		//月度查询
			affairBasicKnowledge.setYear(null);
			affairBasicKnowledge.setBeginDate(null);
			affairBasicKnowledge.setEndDate(null);
		} else if ("3".equals(affairBasicKnowledge.getDateType())) {		//时间段查询
			affairBasicKnowledge.setYear(null);
			affairBasicKnowledge.setBeginDate(DateUtils.parseDate(affairBasicKnowledge.getDateStart()));
			affairBasicKnowledge.setEndDate(DateUtils.parseDate(affairBasicKnowledge.getDateEnd()));
			affairBasicKnowledge.setMonth(null);
		}else {		//年度查询
			affairBasicKnowledge.setBeginDate(null);
			affairBasicKnowledge.setEndDate(null);
			affairBasicKnowledge.setMonth(null);
		}
		String label = DictUtils.getDictValue(affairBasicKnowledge.getStatus(),"pass_status","");
		affairBasicKnowledge.setStatus(label);
		affairBasicKnowledge.setPage(page);
		affairBasicKnowledge.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(), "o", "u"));
		page.setList(dao.findBasicKnoledgeList(affairBasicKnowledge));
		return page;
	}
	
}