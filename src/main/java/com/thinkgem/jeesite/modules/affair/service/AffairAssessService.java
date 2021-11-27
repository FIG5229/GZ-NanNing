/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairAssessDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairGeneralSituationDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairAssess;
import com.thinkgem.jeesite.modules.affair.entity.AffairGeneralSituation;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 党委书记述职测评Service
 * @author eav.liu
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class AffairAssessService extends CrudService<AffairAssessDao, AffairAssess> {

	@Autowired
	private AffairAssessDao affairAssessDao;

	@Autowired
	private AffairGeneralSituationDao affairGeneralSituationDao;

	public AffairAssess get(String id) {
		return super.get(id);
	}
	
	public List<AffairAssess> findList(AffairAssess affairAssess) {
		return super.findList(affairAssess);
	}
	
	public Page<AffairAssess> findPage(Page<AffairAssess> page, AffairAssess affairAssess) {
		affairAssess.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairAssess);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairAssess affairAssess) {
		super.save(affairAssess);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairAssess affairAssess) {
		super.delete(affairAssess);
	}

	/**
	 * 统计党组织书记是否测评
	 * @param id	不再使用组织树
	 * @param year	按着年份查询
	 * @param startDate		时间段查询
	 * @param endDate
	 * @param month		月份查询
	 *                  时间 不为空则查询
	 * @return
	 */
	public Map<String, Long> findPieInfoByPartyBranchId(String id, Integer year, Date startDate, Date endDate, String month) {
		Map<String, Long> map = affairAssessDao.findFinishSumByPId(id, year, startDate, endDate, month);
		/*也不知道干啥的 用不到了 组织树不使用了*/
		/*Integer unFinishSum = null;
		Map<String, Integer> map = new HashMap<>();
		if(id != null && finishSum == 0){
			map.put("finishSum", 1);
			map.put("unFinishSum", 0);
		}else if(id != null && unFinishSum == null){
			map.put("finishSum", 0);
			map.put("unFinishSum", 1);
		}else if(id == null){
			unFinishSum = affairGeneralSituationDao.findALLPartyBranch()-5-finishSum;
			map.put("finishSum", finishSum);
			map.put("unFinishSum", unFinishSum);
		}*/
		return map;
	}

	public Map<String, Integer> findPieInfoByPartyBranchIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		Integer finishSum = affairAssessDao.findFinishSumByPIds(ids, year, startDate, endDate, month);
		Integer unFinishSum = ids.size()-finishSum;
		Map<String, Integer> map = new HashMap<>();
		map.put("finishSum", finishSum);
		map.put("unFinishSum", unFinishSum);
		return map;
	}

	/*修改参数类型 便于添加不存在的label*/
	public List<Map<String, String>> findColumnInfoByPartyBranchId(String id, Integer year, Date startDate, Date endDate, String month) {
		return affairAssessDao.findPieInfoByPBId(id, year, startDate, endDate, month);
	}

	/*修改参数类型 便于添加不存在的label*/
	public List<Map<String, String>> findColumnInfoByPartyBranchIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return affairAssessDao.findPieInfoByPBIds(ids, year, startDate, endDate, month);
	}

	public Page<AffairAssess> findAssessLevelPage(Page<AffairAssess> page, AffairAssess affairAssess) {
		String dateType = Optional.ofNullable(affairAssess.getDateType()).orElseGet(() -> {return "";});
		if (dateType.equals("1")){	//月度查询
			affairAssess.setYears(null);
			affairAssess.setStartDate(null);
			affairAssess.setEndDate(null);
		}else if (dateType.equals("3")){	//时间段查询
			affairAssess.setYears(null);
			affairAssess.setStartDate(DateUtils.parseDate(affairAssess.getDateStart()));
			affairAssess.setEndDate(DateUtils.parseDate(affairAssess.getDateEnd()));
			affairAssess.setMonth(null);
		}else {	//年度查询
			affairAssess.setMonth(null);
			affairAssess.setStartDate(null);
			affairAssess.setEndDate(null);
		}
		affairAssess.setPage(page);
		page.setList(dao.findAssessLevelList(affairAssess));
		return page;
	}

	@Transactional(readOnly = false)
	public void shenHeSave(AffairAssess affairAssess) {
		affairAssessDao.shenHeSave(affairAssess);
	}

	/**
	 * 绩效自动考评
	 * @return
	 */
	public List<AffairGeneralSituation> findAllPartyOrg(AffairGeneralSituation affairGeneralSituation){
		return affairAssessDao.findAllPartyOrg(affairGeneralSituation);
	}
	public int findCount(String startYear, String endYear, String partyOrganization, String partyOrganizationId){
		return affairAssessDao.findCount(startYear, endYear, partyOrganization,partyOrganizationId);
	}
	public List<Map<String, String>> findPeopleInfo(String org, String orgId){
		return affairAssessDao.findPeopleInfo(org, orgId);
	}
	public String peopleJob(String name, String idNumber){
		return affairAssessDao.peopleJob(name,idNumber);
	}
}