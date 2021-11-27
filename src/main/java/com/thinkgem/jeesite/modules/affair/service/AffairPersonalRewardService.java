/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairPersonalRewardDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPersonalReward;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 个人奖励Service
 * @author cecil.li
 * @version 2019-11-02
 */
@Service
@Transactional(readOnly = true)
public class AffairPersonalRewardService extends CrudService<AffairPersonalRewardDao, AffairPersonalReward> {

	@Autowired
	private AffairPersonalRewardDao affairPersonalRewardDao;

	public AffairPersonalReward get(String id) {
		return super.get(id);
	}
	
	public List<AffairPersonalReward> findList(AffairPersonalReward affairPersonalReward) {
		return super.findList(affairPersonalReward);
	}
	
	public Page<AffairPersonalReward> findPage(Page<AffairPersonalReward> page, AffairPersonalReward affairPersonalReward) {
		affairPersonalReward.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairPersonalReward);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPersonalReward affairPersonalReward) {
		super.save(affairPersonalReward);
		if("".equals(affairPersonalReward.getPushType())||affairPersonalReward.getPushType()==null){
			affairPersonalReward.setPushType("0");
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPersonalReward affairPersonalReward) {
		super.delete(affairPersonalReward);
	}

	public Map<String, Object> findInfoByRewardName(String id, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> peopleCount = new ArrayList<>();
		String parentId = UserUtils.getUser().getOffice().getParentId();
		String officeId = UserUtils.getUser().getCompany().getId();
		if (parentId.equals("1") || parentId.equals("0")){
			peopleCount = affairPersonalRewardDao.findInfoByRewardName(id, year, startDate, endDate, month);
		}else {
			peopleCount = affairPersonalRewardDao.findChuInfoByRewardName(officeId,id, year, startDate, endDate, month);
		}
		Map<String, Object> result = new HashMap<>();
		List<String> labelList = new ArrayList<>();
		List<Integer> totalList = new ArrayList<>();
		for (Map<String, Object> map : peopleCount){
			String label = String.valueOf(map.get("namecode"));
			labelList.add(label);
			Integer totalInteger = Integer.valueOf(String.valueOf(map.get("num")));
			totalList.add(totalInteger);
		}
		result.put("labelData", labelList);
		result.put("totalData", totalList);
		return result;
	}

	public Page<AffairPersonalReward> findDetailInfoByRewardName(Page<AffairPersonalReward> page, AffairPersonalReward affairPersonalReward){
		affairPersonalReward.setPage(page);
		if (!UserUtils.getUser().getOffice().equals("局机关")){
			String officeId = UserUtils.getUser().getCompany().getId();
			affairPersonalReward.setApprovalUnitId(officeId);
		}else {
			affairPersonalReward.setApprovalUnitId("top");
		}
		List<AffairPersonalReward> list = new ArrayList<>();
		if("1".equals(affairPersonalReward.getDateType())){//月度
			if (affairPersonalReward.getMonth() != null && affairPersonalReward.getMonth().length() > 0) {
				affairPersonalReward.setYear(null);
				affairPersonalReward.setStartDate(null);
				affairPersonalReward.setEndDate(null);
				list = affairPersonalRewardDao.findDetailInfoByRewardName(affairPersonalReward);
			}
		}else if("2".equals(affairPersonalReward.getDateType())){//年度
			affairPersonalReward.setMonth(null);
			affairPersonalReward.setStartDate(null);
			affairPersonalReward.setEndDate(null);
			list = affairPersonalRewardDao.findDetailInfoByRewardName(affairPersonalReward);
		}else{// 时间段
			affairPersonalReward.setMonth(null);
			affairPersonalReward.setYear(null);
			Date startDate = DateUtils.parseDate(affairPersonalReward.getStartDate());
			Date endDate = DateUtils.parseDate(affairPersonalReward.getEndDate());
			affairPersonalReward.setStartDate(startDate);
			affairPersonalReward.setEndDate(endDate);
			list = affairPersonalRewardDao.findDetailInfoByRewardName(affairPersonalReward);
		}
		page.setList(list);
		return page;
	}

	public Page<AffairPersonalReward> findOtherDetailInfoByRewardName(Page<AffairPersonalReward> page, AffairPersonalReward affairPersonalReward){
		affairPersonalReward.setPage(page);
		if (!UserUtils.getUser().getOffice().equals("局机关")){
			String officeId = UserUtils.getUser().getCompany().getId();
			affairPersonalReward.setApprovalUnitId(officeId);
		}else {
			affairPersonalReward.setApprovalUnitId("top");
		}
		List<AffairPersonalReward> list = new ArrayList<>();
		if("1".equals(affairPersonalReward.getDateType())){//月度
			if (affairPersonalReward.getMonth() != null && affairPersonalReward.getMonth().length() > 0) {
				affairPersonalReward.setYear(null);
				affairPersonalReward.setStartDate(null);
				affairPersonalReward.setEndDate(null);
				list = affairPersonalRewardDao.findOtherDetailInfoByRewardName(affairPersonalReward);
			}
		}else if("2".equals(affairPersonalReward.getDateType())){//年度
			affairPersonalReward.setMonth(null);
			affairPersonalReward.setStartDate(null);
			affairPersonalReward.setEndDate(null);
			list = affairPersonalRewardDao.findOtherDetailInfoByRewardName(affairPersonalReward);
		}else{// 时间段
			affairPersonalReward.setMonth(null);
			affairPersonalReward.setYear(null);
			Date startDate = DateUtils.parseDate(affairPersonalReward.getStartDate());
			Date endDate = DateUtils.parseDate(affairPersonalReward.getEndDate());
			affairPersonalReward.setStartDate(startDate);
			affairPersonalReward.setEndDate(endDate);
			list = affairPersonalRewardDao.findOtherDetailInfoByRewardName(affairPersonalReward);
		}
		page.setList(list);
		return page;
	}

	public List<AffairPersonalReward> selectBean(String idNumber){
		return affairPersonalRewardDao.selectBean(idNumber);
	}

	public List<AffairPersonalReward> selectAllReward(String idNumber,String yearL,String yearT){return affairPersonalRewardDao.selectAllReward(idNumber,yearL,yearT);}

	public List<AffairPersonalReward> selectAllRewardYear(String idNumber,String yearL,String yearT){return affairPersonalRewardDao.selectAllRewardYear(idNumber,yearL,yearT);}

}