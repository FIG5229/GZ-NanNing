/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairTousujubaoguanliDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTousujubaoguanli;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 投诉举报Service
 * @author cecil.li
 * @version 2019-11-07
 */
@Service
@Transactional(readOnly = true)
public class AffairTousujubaoguanliService extends CrudService<AffairTousujubaoguanliDao, AffairTousujubaoguanli> {

	@Autowired
	AffairTousujubaoguanliDao affairTousujubaoguanliDao;

	@Autowired
	private DictDao dictDao;

	public AffairTousujubaoguanli get(String id) {
		return super.get(id);
	}
	
	public List<AffairTousujubaoguanli> findList(AffairTousujubaoguanli affairTousujubaoguanli) {
		return super.findList(affairTousujubaoguanli);
	}
	
	public Page<AffairTousujubaoguanli> findPage(Page<AffairTousujubaoguanli> page, AffairTousujubaoguanli affairTousujubaoguanli) {
		affairTousujubaoguanli.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTousujubaoguanli);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTousujubaoguanli affairTousujubaoguanli) {
		super.save(affairTousujubaoguanli);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTousujubaoguanli affairTousujubaoguanli) {
		super.delete(affairTousujubaoguanli);
	}


	@Transactional(readOnly = false)
	public void zhuanban(AffairTousujubaoguanli affairTousujubaoguanli, String zbUnitId, String zbUnit) {
		zbUnit = affairTousujubaoguanli.getZbUnit();
		zbUnitId = affairTousujubaoguanli.getZbUnitId();
		affairTousujubaoguanli.setUpdateDate(new Date());
		affairTousujubaoguanli.setZbUnitId(zbUnitId);
		affairTousujubaoguanli.setZbUnit(zbUnit);
		affairTousujubaoguanliDao.zhuanban(affairTousujubaoguanli,zbUnitId, zbUnit);
	}

	//主要是得到当前修改时间(update)当前用户(审核人shperson)
	@Transactional(readOnly = false)
	public void shenHe(AffairTousujubaoguanli affairTousujubaoguanli) {
		affairTousujubaoguanli.setUpdateDate(new Date());
		affairTousujubaoguanli.setRevierwe(UserUtils.getUser().getName());
		affairTousujubaoguanliDao.shenHe(affairTousujubaoguanli);
	}

	//统计分析
	public Map<String, Object> findInfoBySource(String id, Integer year, Date startDate, Date endDate, String month) {

		/*AffairTousujubaoguanli affairTousujubaoguanli = new AffairTousujubaoguanli();
		affairTousujubaoguanli.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/

		id=dataScopeFilter(UserUtils.getUser(), "o", "u");


		List<Map<String, Object>> benJi = affairTousujubaoguanliDao.findBjInfo(id, year, startDate, endDate, month);
		List<Map<String, Object>> exBenJi = affairTousujubaoguanliDao.findExBjInfo(id, year, startDate, endDate, month);
		Map<String, Object> result = new HashMap<>();
		List<String> labelList = new ArrayList<>();
		List<Integer> totalList = new ArrayList<>();
		List<Integer> completeList = new ArrayList<>();
		List<Map<String, Object>> benJiListInfo = new ArrayList<>();
		benJiListInfo.add(this.getInfoBySdUnitId("1",benJi));
		benJiListInfo.add(this.getInfoBySdUnitId("2",benJi));
		benJiListInfo.add(this.getInfoBySdUnitId("3",benJi));
		benJiListInfo.add(this.getInfoBySdUnitId("4",benJi));
		List<Map<String, Object>>exBenJiListInfo = new ArrayList<>();
		exBenJiListInfo.add(this.getInfoBySdUnitId("1",exBenJi));
		exBenJiListInfo.add(this.getInfoBySdUnitId("2",exBenJi));
		exBenJiListInfo.add(this.getInfoBySdUnitId("3",exBenJi));
		exBenJiListInfo.add(this.getInfoBySdUnitId("4",exBenJi));
		for (Map<String, Object> map : benJiListInfo) {
			Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
			totalList.add(totalInteger);
		}
		for (Map<String, Object> map : exBenJiListInfo) {
			Integer completeInteger = Integer.valueOf(String.valueOf(map.get("count")));
			completeList.add(completeInteger);
		}
		result.put("labelData", labelList);
		result.put("totalData", totalList);
		result.put("completeData", completeList);
		return result;
	}

	private  Map<String, Object> getInfoBySdUnitId(String unitId,List<Map<String, Object>> list){
		Map<String, Object> resultMap = null;
		for (Map<String, Object> map : list) {
			if(unitId.equals(map.get("sd_unit"))){
				resultMap = map;
				break;
			}
		}
		if(null == resultMap){
			resultMap = new HashMap<String, Object>();
			resultMap.put("sd_unit",unitId);
			resultMap.put("count",new Integer(0));
		}
		return resultMap;
	}

	public Map<String, Object> findPieInfoByIsCheck(String sdUnit, String id, Integer year, Date startDate, Date endDate, String month){
		/*AffairTousujubaoguanli affairTousujubaoguanli = new AffairTousujubaoguanli();
		affairTousujubaoguanli.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/

		id=dataScopeFilter(UserUtils.getUser(), "o", "u");

		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> chaShi = affairTousujubaoguanliDao.findChaShiInfo(sdUnit, id,year,startDate,endDate,month);
		List<Map<String, Object>> chaFou = affairTousujubaoguanliDao.findChaFouInfo(sdUnit, id,year,startDate,endDate,month);
		List<Map<String, Object>> buFen = affairTousujubaoguanliDao.findBuFenInfo(sdUnit, id,year,startDate,endDate,month);
		List<Map<String, Object>> zanCun = affairTousujubaoguanliDao.findZanCunInfo(sdUnit, id,year,startDate,endDate,month);
		List<String> labelList = new ArrayList<>();
		List<Integer> totalList = new ArrayList<>();
		if (CollectionUtils.isEmpty(chaShi)){
			totalList.add(0);
			labelList.add("查实");
		}else {
			for (Map<String, Object> map : chaShi) {
				String label = String.valueOf(map.get("label"));
				labelList.add(label);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
			}
		}
		if (CollectionUtils.isEmpty(chaFou)){
			totalList.add(0);
			labelList.add("查否");
		}else {
			for (Map<String, Object> map : chaFou) {
				String label = String.valueOf(map.get("label"));
				labelList.add(label);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
			}
		}
		if (CollectionUtils.isEmpty(buFen)){
			totalList.add(0);
			labelList.add("部分属实");
		}else {
			for (Map<String, Object> map : buFen) {
				String label = String.valueOf(map.get("label"));
				labelList.add(label);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
			}
		}
		if (CollectionUtils.isEmpty(zanCun)){
			totalList.add(0);
			labelList.add("暂存待查");
		}else {
			for (Map<String, Object> map : zanCun) {
				String label = String.valueOf(map.get("label"));
				labelList.add(label);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
			}
		}
			result.put("labelData", labelList);
			result.put("totalData", totalList);
		return result;
	}

	public Page<AffairTousujubaoguanli> findIsCheckInfoDetail(Page<AffairTousujubaoguanli> page, AffairTousujubaoguanli affairTousujubaoguanli){
		affairTousujubaoguanli.setPage(page);
		List<AffairTousujubaoguanli> list = new ArrayList<>();
		if("1".equals(affairTousujubaoguanli.getDateType())){//月度
			if (affairTousujubaoguanli.getMonth() != null && affairTousujubaoguanli.getMonth().length() > 0) {
				affairTousujubaoguanli.setYear(null);
				affairTousujubaoguanli.setDateStart(null);
				affairTousujubaoguanli.setDateEnd(null);
				list = affairTousujubaoguanliDao.findIsCheckInfoDetail(affairTousujubaoguanli);
			}
		}else if("2".equals(affairTousujubaoguanli.getDateType())){//年度
			affairTousujubaoguanli.setMonth(null);
			affairTousujubaoguanli.setDateStart(null);
			affairTousujubaoguanli.setDateEnd(null);
			list = affairTousujubaoguanliDao.findIsCheckInfoDetail(affairTousujubaoguanli);
		}else{// 时间段
			affairTousujubaoguanli.setMonth(null);
			affairTousujubaoguanli.setYear(null);
			Date startDate = DateUtils.parseDate(affairTousujubaoguanli.getDateStart());
			Date endDate = DateUtils.parseDate(affairTousujubaoguanli.getDateEnd());
			affairTousujubaoguanli.setStartDate(startDate);
			affairTousujubaoguanli.setEndDate(endDate);
			list = affairTousujubaoguanliDao.findIsCheckInfoDetail(affairTousujubaoguanli);
		}
		page.setList(list);
		return page;
	}

	public Page<AffairTousujubaoguanli> findBjTypeInfoDetail(Page<AffairTousujubaoguanli> page, AffairTousujubaoguanli affairTousujubaoguanli){
		affairTousujubaoguanli.setPage(page);
		List<AffairTousujubaoguanli> list = new ArrayList<>();
		if("1".equals(affairTousujubaoguanli.getDateType())){//月度
			if (affairTousujubaoguanli.getMonth() != null && affairTousujubaoguanli.getMonth().length() > 0) {
				affairTousujubaoguanli.setYear(null);
				affairTousujubaoguanli.setDateStart(null);
				affairTousujubaoguanli.setDateEnd(null);
				list = affairTousujubaoguanliDao.findBjTypeInfoDetail(affairTousujubaoguanli);
			}
		}else if("2".equals(affairTousujubaoguanli.getDateType())){//年度
			affairTousujubaoguanli.setMonth(null);
			affairTousujubaoguanli.setDateStart(null);
			affairTousujubaoguanli.setDateEnd(null);
			list = affairTousujubaoguanliDao.findBjTypeInfoDetail(affairTousujubaoguanli);
		}else{// 时间段
			affairTousujubaoguanli.setMonth(null);
			affairTousujubaoguanli.setYear(null);
			Date startDate = DateUtils.parseDate(affairTousujubaoguanli.getDateStart());
			Date endDate = DateUtils.parseDate(affairTousujubaoguanli.getDateEnd());
			affairTousujubaoguanli.setStartDate(startDate);
			affairTousujubaoguanli.setEndDate(endDate);
			list = affairTousujubaoguanliDao.findBjTypeInfoDetail(affairTousujubaoguanli);
		}
		page.setList(list);
		return page;
	}


	public Map<String, Object> findInfoByQuestionType(String id, Integer year, Date startDate, Date endDate, String month){
		/*AffairTousujubaoguanli affairTousujubaoguanli = new AffairTousujubaoguanli();
		affairTousujubaoguanli.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/
		id=dataScopeFilter(UserUtils.getUser(), "o", "u");

		List<Map<String, Object>> zjInfo = affairTousujubaoguanliDao.findZjInfo(id, year, startDate, endDate, month);
		List<Map<String, Object>> sfInfo = affairTousujubaoguanliDao.findSfInfo(id, year, startDate, endDate, month);
		List<Map<String, Object>> jjInfo = affairTousujubaoguanliDao.findJjInfo(id, year, startDate, endDate, month);
		Map<String, Object> result = new HashMap<>();
		List<Integer> labelList = new ArrayList<>();
		List<Integer> totalList = new ArrayList<>();
		List<Integer> completeList = new ArrayList<>();
		List<Map<String, Object>> zjInfoList = new ArrayList<>();
		zjInfoList.add(this.getInfoBySdUnitId("1",zjInfo));
		zjInfoList.add(this.getInfoBySdUnitId("2",zjInfo));
		zjInfoList.add(this.getInfoBySdUnitId("3",zjInfo));
		zjInfoList.add(this.getInfoBySdUnitId("4",zjInfo));
		List<Map<String, Object>> sfInfoList = new ArrayList<>();
		sfInfoList.add(this.getInfoBySdUnitId("1",sfInfo));
		sfInfoList.add(this.getInfoBySdUnitId("2",sfInfo));
		sfInfoList.add(this.getInfoBySdUnitId("3",sfInfo));
		sfInfoList.add(this.getInfoBySdUnitId("4",sfInfo));
		List<Map<String, Object>> jjInfoList = new ArrayList<>();
		jjInfoList.add(this.getInfoBySdUnitId("1",jjInfo));
		jjInfoList.add(this.getInfoBySdUnitId("2",jjInfo));
		jjInfoList.add(this.getInfoBySdUnitId("3",jjInfo));
		jjInfoList.add(this.getInfoBySdUnitId("4",jjInfo));
		for (Map<String, Object> map : zjInfoList) {
			Integer labelInteger = Integer.valueOf(String.valueOf(map.get("count")));
			labelList.add(labelInteger);
		}
		for (Map<String, Object> map : sfInfoList) {
			Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
			totalList.add(totalInteger);
		}
		for (Map<String, Object> map : jjInfoList) {
			Integer completeInteger = Integer.valueOf(String.valueOf(map.get("count")));
			completeList.add(completeInteger);
		}
		result.put("labelData", labelList);
		result.put("totalData", totalList);
		result.put("completeData", completeList);
		return result;
	}

	public Map<String, Object> findPieInfoByQuestionType(String sdUnit, String id, Integer year, Date startDate, Date endDate, String month){
	/*	AffairTousujubaoguanli affairTousujubaoguanli = new AffairTousujubaoguanli();
		affairTousujubaoguanli.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/
		id=dataScopeFilter(UserUtils.getUser(), "o", "u");
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> zjPieInfo = affairTousujubaoguanliDao.findZjPieInfo(sdUnit, id,year,startDate,endDate,month);
		List<Map<String, Object>> sfPieInfo = affairTousujubaoguanliDao.findSfPieInfo(sdUnit, id,year,startDate,endDate,month);
		List<Map<String, Object>> jjPieInfo = affairTousujubaoguanliDao.findJjPieInfo(sdUnit, id,year,startDate,endDate,month);
		List<String> labelList = new ArrayList<>();
		List<Integer> totalList = new ArrayList<>();
		if (CollectionUtils.isEmpty(zjPieInfo)){
			totalList.add(0);
			labelList.add("违反党纪行为");
		}else {
			for (Map<String, Object> map : zjPieInfo) {
				String label = String.valueOf(map.get("label"));
				labelList.add(label);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
			}
		}
		if (CollectionUtils.isEmpty(sfPieInfo)){
			totalList.add(0);
			labelList.add("涉法行为");
		}else {
			for (Map<String, Object> map : sfPieInfo) {
				String label = String.valueOf(map.get("label"));
				labelList.add(label);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
			}
		}
		if (CollectionUtils.isEmpty(jjPieInfo)){
			totalList.add(0);
			labelList.add("违反警纪行为");
		}else {
			for (Map<String, Object> map : jjPieInfo) {
				String label = String.valueOf(map.get("label"));
				labelList.add(label);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
			}
		}
		result.put("labelData", labelList);
		result.put("totalData", totalList);
		return result;
	}

	public Page<AffairTousujubaoguanli> findZjInfoDetail(Page<AffairTousujubaoguanli> page, AffairTousujubaoguanli affairTousujubaoguanli){
		affairTousujubaoguanli.setPage(page);
		List<AffairTousujubaoguanli> list = new ArrayList<>();
		if("1".equals(affairTousujubaoguanli.getDateType())){//月度
			if (affairTousujubaoguanli.getMonth() != null && affairTousujubaoguanli.getMonth().length() > 0) {
				affairTousujubaoguanli.setYear(null);
				affairTousujubaoguanli.setDateStart(null);
				affairTousujubaoguanli.setDateEnd(null);
				list = affairTousujubaoguanliDao.findZjInfoDetail(affairTousujubaoguanli);
			}
		}else if("2".equals(affairTousujubaoguanli.getDateType())){//年度
			affairTousujubaoguanli.setMonth(null);
			affairTousujubaoguanli.setDateStart(null);
			affairTousujubaoguanli.setDateEnd(null);
			list = affairTousujubaoguanliDao.findZjInfoDetail(affairTousujubaoguanli);
		}else{// 时间段
			affairTousujubaoguanli.setMonth(null);
			affairTousujubaoguanli.setYear(null);
			Date startDate = DateUtils.parseDate(affairTousujubaoguanli.getDateStart());
			Date endDate = DateUtils.parseDate(affairTousujubaoguanli.getDateEnd());
			affairTousujubaoguanli.setStartDate(startDate);
			affairTousujubaoguanli.setEndDate(endDate);
			list = affairTousujubaoguanliDao.findZjInfoDetail(affairTousujubaoguanli);
		}
		page.setList(list);
		return page;
	}

	public Page<AffairTousujubaoguanli> findSfInfoDetail(Page<AffairTousujubaoguanli> page, AffairTousujubaoguanli affairTousujubaoguanli){
		affairTousujubaoguanli.setPage(page);
		List<AffairTousujubaoguanli> list = new ArrayList<>();
		if("1".equals(affairTousujubaoguanli.getDateType())){//月度
			if (affairTousujubaoguanli.getMonth() != null && affairTousujubaoguanli.getMonth().length() > 0) {
				affairTousujubaoguanli.setYear(null);
				affairTousujubaoguanli.setDateStart(null);
				affairTousujubaoguanli.setDateEnd(null);
				list = affairTousujubaoguanliDao.findSfInfoDetail(affairTousujubaoguanli);
			}
		}else if("2".equals(affairTousujubaoguanli.getDateType())){//年度
			affairTousujubaoguanli.setMonth(null);
			affairTousujubaoguanli.setDateStart(null);
			affairTousujubaoguanli.setDateEnd(null);
			list = affairTousujubaoguanliDao.findSfInfoDetail(affairTousujubaoguanli);
		}else{// 时间段
			affairTousujubaoguanli.setMonth(null);
			affairTousujubaoguanli.setYear(null);
			Date startDate = DateUtils.parseDate(affairTousujubaoguanli.getDateStart());
			Date endDate = DateUtils.parseDate(affairTousujubaoguanli.getDateEnd());
			affairTousujubaoguanli.setStartDate(startDate);
			affairTousujubaoguanli.setEndDate(endDate);
			list = affairTousujubaoguanliDao.findSfInfoDetail(affairTousujubaoguanli);
		}
		page.setList(list);
		return page;
	}

	public Page<AffairTousujubaoguanli> findJjInfoDetail(Page<AffairTousujubaoguanli> page, AffairTousujubaoguanli affairTousujubaoguanli){
		affairTousujubaoguanli.setPage(page);
		List<AffairTousujubaoguanli> list = new ArrayList<>();
		if("1".equals(affairTousujubaoguanli.getDateType())){//月度
			if (affairTousujubaoguanli.getMonth() != null && affairTousujubaoguanli.getMonth().length() > 0) {
				affairTousujubaoguanli.setYear(null);
				affairTousujubaoguanli.setDateStart(null);
				affairTousujubaoguanli.setDateEnd(null);
				list = affairTousujubaoguanliDao.findJjInfoDetail(affairTousujubaoguanli);
			}
		}else if("2".equals(affairTousujubaoguanli.getDateType())){//年度
			affairTousujubaoguanli.setMonth(null);
			affairTousujubaoguanli.setDateStart(null);
			affairTousujubaoguanli.setDateEnd(null);
			list = affairTousujubaoguanliDao.findJjInfoDetail(affairTousujubaoguanli);
		}else{// 时间段
			affairTousujubaoguanli.setMonth(null);
			affairTousujubaoguanli.setYear(null);
			Date startDate = DateUtils.parseDate(affairTousujubaoguanli.getDateStart());
			Date endDate = DateUtils.parseDate(affairTousujubaoguanli.getDateEnd());
			affairTousujubaoguanli.setStartDate(startDate);
			affairTousujubaoguanli.setEndDate(endDate);
			list = affairTousujubaoguanliDao.findJjInfoDetail(affairTousujubaoguanli);
		}
		page.setList(list);
		return page;
	}

/*	public Map<String, Object> findInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> chashi = dao.findChashiInfos(id, year, startDate, endDate, month);
		List<Map<String, Object>> chufen = dao.findChufenInfos(id, year, startDate, endDate, month);
		List<String> labelData =  new ArrayList<>();
		for(Map<String, Object> map : chashi) {
			String label = String.valueOf(map.get("label"));
			if(!labelData.contains(label)) {
				labelData.add(label);
			}
		}
		for(Map<String, Object> map : chufen) {
			String label = String.valueOf(map.get("label"));
			if(!labelData.contains(label)) {
				labelData.add(label);
			}
		}
		List<Integer> chashiData = new ArrayList<>();
		List<Integer> chufenData = new ArrayList<>();

		for(String label : labelData) {
			chashiData.add(isContain(label, chashi));
			chufenData.add(isContain(label, chufen));
		}
		Map<String, Object> result = new HashMap<>();
		result.put("labelData", labelData);
		result.put("chashiData", chashiData);
		result.put("chufenData", chufenData);
		return result;
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> chashi = dao.findMultiChashiInfos(ids, year, startDate, endDate, month);
		List<Map<String, Object>> chufen = dao.findMultiChufenInfos(ids, year, startDate, endDate, month);
		List<String> labelData =  new ArrayList<>();
		for(Map<String, Object> map : chashi) {
			String label = String.valueOf(map.get("label"));
			if(!labelData.contains(label)) {
				labelData.add(label);
			}
		}
		for(Map<String, Object> map : chufen) {
			String label = String.valueOf(map.get("label"));
			if(!labelData.contains(label)) {
				labelData.add(label);
			}
		}
		List<Integer> chashiData = new ArrayList<>();
		List<Integer> chufenData = new ArrayList<>();

		for(String label : labelData) {
			chashiData.add(isContain(label, chashi));
			chufenData.add(isContain(label, chufen));
		}
		Map<String, Object> result = new HashMap<>();
		result.put("labelData", labelData);
		result.put("chashiData", chashiData);
		result.put("chufenData", chufenData);
		return result;
	}*/

	private static Integer isContain(String flag, List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			String label = String.valueOf(map.get("label"));
			if(flag.equals(label)) {
				return Integer.valueOf(String.valueOf(map.get("count")));
			}
		}
		return 0;
	}

/*	public List<Map<String, Object>> findPieInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findPieInfoByCreateOrgId(id, year, startDate, endDate, month);
	}

	public List<Map<String, Object>> findPieInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findPieInfoByCreateOrgIds(ids, year, startDate, endDate, month);
	}*/

	/**
	 * 办结功能（需求：点击办结按钮后公安处层面将再无法修改，只有公安局纪检监察管理员才能修改）
	 * @param affairTousujubaoguanli
	 */
	@Transactional(readOnly = false)
	public void banJie(AffairTousujubaoguanli affairTousujubaoguanli) {
		affairTousujubaoguanliDao.banJie(affairTousujubaoguanli.getId());
	}

	/**
	 * 批量提交
	 * @param ids
	 */
	public List<AffairTousujubaoguanli> findByIds(List<String> ids){
		List<AffairTousujubaoguanli> list = affairTousujubaoguanliDao.findByIds(ids);
		return list;
	}
}