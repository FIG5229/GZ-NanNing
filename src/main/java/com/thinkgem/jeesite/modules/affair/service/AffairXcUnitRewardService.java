/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairXcUnitRewardDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairXcUnitReward;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 单位集体奖励表Service
 * @author cecil.li
 * @version 2019-11-02
 */
@Service
@Transactional(readOnly = true)
public class AffairXcUnitRewardService extends CrudService<AffairXcUnitRewardDao, AffairXcUnitReward> {

	@Autowired
	private AffairXcUnitRewardDao affairXcUnitRewardDao;

	public AffairXcUnitReward get(String id) {
		return super.get(id);
	}

	public List<AffairXcUnitReward> findList(AffairXcUnitReward affairXcUnitReward) {
		return super.findList(affairXcUnitReward);
	}

	public Page<AffairXcUnitReward> findPage(Page<AffairXcUnitReward> page, AffairXcUnitReward affairXcUnitReward) {
		affairXcUnitReward.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairXcUnitReward);
	}

	@Transactional(readOnly = false)
	public void save(AffairXcUnitReward affairXcUnitReward) {
		super.save(affairXcUnitReward);
		if("".equals(affairXcUnitReward.getPushType())||affairXcUnitReward.getPushType()==null){
			affairXcUnitReward.setPushType("0");
		}
	}

	@Transactional(readOnly = false)
	public void delete(AffairXcUnitReward affairXcUnitReward) {
		super.delete(affairXcUnitReward);
	}

	public Map<String, Object> findInfoByRewardName(String id, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> peopleCount = new ArrayList<>();
		String parentId = UserUtils.getUser().getOffice().getParentId();
		String officeId = UserUtils.getUser().getCompany().getId();
		if (parentId.equals("1") || parentId.equals("0")){
			 peopleCount = affairXcUnitRewardDao.findInfoByRewardName(id, year, startDate, endDate, month);
		}else {
			 peopleCount = affairXcUnitRewardDao.findChuInfoByRewardName(officeId,id, year, startDate, endDate, month);
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

	public Page<AffairXcUnitReward> findDetailInfoByRewardName(Page<AffairXcUnitReward> page, AffairXcUnitReward affairXcUnitReward){
		affairXcUnitReward.setPage(page);
		if (!UserUtils.getUser().getOffice().equals("局机关")){
			String officeId = UserUtils.getUser().getCompany().getId();
			affairXcUnitReward.setUnitId(officeId);
		}else {
			affairXcUnitReward.setUnitId("top");
		}
		List<AffairXcUnitReward> list = new ArrayList<>();
		if("1".equals(affairXcUnitReward.getDateType())){//月度
			if (affairXcUnitReward.getMonth() != null && affairXcUnitReward.getMonth().length() > 0) {
				affairXcUnitReward.setYear(null);
				affairXcUnitReward.setStartDate(null);
				affairXcUnitReward.setEndDate(null);
				list = affairXcUnitRewardDao.findDetailInfoByRewardName(affairXcUnitReward);
			}
		}else if("2".equals(affairXcUnitReward.getDateType())){//年度
			affairXcUnitReward.setMonth(null);
			affairXcUnitReward.setStartDate(null);
			affairXcUnitReward.setEndDate(null);
			list = affairXcUnitRewardDao.findDetailInfoByRewardName(affairXcUnitReward);
		}else{// 时间段
			affairXcUnitReward.setMonth(null);
			affairXcUnitReward.setYear(null);
			Date startDate = DateUtils.parseDate(affairXcUnitReward.getStartDate());
			Date endDate = DateUtils.parseDate(affairXcUnitReward.getEndDate());
			affairXcUnitReward.setStartDate(startDate);
			affairXcUnitReward.setEndDate(endDate);
			list = affairXcUnitRewardDao.findDetailInfoByRewardName(affairXcUnitReward);
		}
		page.setList(list);
		return page;
	}

	public Page<AffairXcUnitReward> findOtherDetailInfoByRewardName(Page<AffairXcUnitReward> page, AffairXcUnitReward affairXcUnitReward){
		affairXcUnitReward.setPage(page);
		if (!UserUtils.getUser().getOffice().equals("局机关")){
			String officeId = UserUtils.getUser().getCompany().getId();
			affairXcUnitReward.setUnitId(officeId);
		}else {
			affairXcUnitReward.setUnitId("top");
		}
		List<AffairXcUnitReward> list = new ArrayList<>();
		if("1".equals(affairXcUnitReward.getDateType())){//月度
			if (affairXcUnitReward.getMonth() != null && affairXcUnitReward.getMonth().length() > 0) {
				affairXcUnitReward.setYear(null);
				affairXcUnitReward.setStartDate(null);
				affairXcUnitReward.setEndDate(null);
				list = affairXcUnitRewardDao.findOtherDetailInfoByRewardName(affairXcUnitReward);
			}
		}else if("2".equals(affairXcUnitReward.getDateType())){//年度
			affairXcUnitReward.setMonth(null);
			affairXcUnitReward.setStartDate(null);
			affairXcUnitReward.setEndDate(null);
			list = affairXcUnitRewardDao.findOtherDetailInfoByRewardName(affairXcUnitReward);
		}else{// 时间段
			affairXcUnitReward.setMonth(null);
			affairXcUnitReward.setYear(null);
			Date startDate = DateUtils.parseDate(affairXcUnitReward.getStartDate());
			Date endDate = DateUtils.parseDate(affairXcUnitReward.getEndDate());
			affairXcUnitReward.setStartDate(startDate);
			affairXcUnitReward.setEndDate(endDate);
			list = affairXcUnitRewardDao.findOtherDetailInfoByRewardName(affairXcUnitReward);
		}
		page.setList(list);
		return page;
	}

	public List<String> findCodeName(String nameCode){
		String code = null;
		if("集体一等功".equals(nameCode)){
			code="1";
		}else if("集体二等功".equals(nameCode)){
			code="2";
		}else if("集体三等功".equals(nameCode)){
			code="3";
		}else if("优秀公安局".equals(nameCode)){
			code="4";
		}else if("优秀公安基层单位".equals(nameCode)){
			code="5";
		}else if("公安机关爱民服务模范集体".equals(nameCode)){
			code="6";
		}else if("“五一”劳动奖状".equals(nameCode)){
			code="7";
		}else if("青年文明号".equals(nameCode)){
			code="8";
		}else if("“三八”红旗集体".equals(nameCode)){
			code="9";
		}else if("人民满意的公务员集体".equals(nameCode)){
			code="10";
		}else if("模范公务员集体".equals(nameCode)){
			code="11";
		}
		return affairXcUnitRewardDao.findCodeName(code);
	}

	public List<AffairXcUnitReward> selectUnitReward(String unitId){
		return affairXcUnitRewardDao.selectUnitReward(unitId);
	}

	public AffairXcUnitReward selectBean(String id){
		return affairXcUnitRewardDao.selectBean(id);
	}

	public Integer selectNumber(String checkTime,String unitId,String level){
		return affairXcUnitRewardDao.selectNumber(checkTime,unitId,level);
	}

	public List<AffairXcUnitReward> selectAllReward(String idN,String yearL,String yearT){
		return affairXcUnitRewardDao.selectAllReward(idN,yearL,yearT);
	}
	public List<AffairXcUnitReward> selectAllRewardYear(String idN,String year){
		return affairXcUnitRewardDao.selectAllRewardYear(idN,year);
	}


}