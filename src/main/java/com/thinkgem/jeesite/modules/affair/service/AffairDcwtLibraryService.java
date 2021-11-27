/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairDcwtLibraryDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDcwtLibrary;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 督察问题管理Service
 * @author cecil.li
 * @version 2019-11-08
 */
@Service
@Transactional(readOnly = true)
public class AffairDcwtLibraryService extends CrudService<AffairDcwtLibraryDao, AffairDcwtLibrary> {

	@Autowired
	private AffairDcwtLibraryDao affairDcwtLibraryDao;

	public AffairDcwtLibrary get(String id) {
		return super.get(id);
	}
	
	public List<AffairDcwtLibrary> findList(AffairDcwtLibrary affairDcwtLibrary) {
		return super.findList(affairDcwtLibrary);
	}
	
	public Page<AffairDcwtLibrary> findPage(Page<AffairDcwtLibrary> page, AffairDcwtLibrary affairDcwtLibrary) {
//		affairDcwtLibrary.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		affairDcwtLibrary.setCreateBy(UserUtils.getUser());
		//68  南宁处督察    284 北海处督察  18 南宁局警务督察处 单位id 183  柳州处督察
		if("1".equals(UserUtils.getUser().getCompany().getId()) && "18".equals(UserUtils.getUser().getOffice().getId()) && "0000".equals(UserUtils.getUser().getNo())){
			//确认当前用户为 南宁局督察信息管理账号
			affairDcwtLibrary.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairDcwtLibrary.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairDcwtLibrary);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairDcwtLibrary affairDcwtLibrary) {
		super.save(affairDcwtLibrary);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairDcwtLibrary affairDcwtLibrary) {
		super.delete(affairDcwtLibrary);
	}

	//统计分析
	public Map<String, Object> findAllDcInfo(String id, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> total = dao.findAllTotalInfos(id, year, startDate, endDate, month);
		List<Map<String, Object>> complete = dao.findAllCompleteInfos(id, year, startDate, endDate, month);
		if(!CollectionUtils.isEmpty(total)) {
			List<String> labelList = new ArrayList<>();
			List<Integer> totalList = new ArrayList<>();
			List<Integer> completeList = new ArrayList<>();
			List<Integer> unCompleteList = new ArrayList<>();
			for(Map<String, Object> map : total ) {
				String flag = String.valueOf(map.get("label"));
				labelList.add(flag);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
				Integer completeInteger = isContain(flag, complete);
				completeList.add(completeInteger);
				unCompleteList.add(totalInteger - completeInteger);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("labelData", labelList);
			result.put("totalData", totalList);
			result.put("completeData", completeList);
			result.put("unCompleteData", unCompleteList);
			return result;
		}
		return null;
	}
	public Map<String, Object> findAllDcInfos(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> total = dao.findAllMultiTotalInfos(ids, year, startDate, endDate, month);
		List<Map<String, Object>> complete = dao.findAllMultiCompleteInfos(ids, year, startDate, endDate, month);
		if(!CollectionUtils.isEmpty(total)) {
			List<String> labelList = new ArrayList<>();
			List<Integer> totalList = new ArrayList<>();
			List<Integer> completeList = new ArrayList<>();
			List<Integer> unCompleteList = new ArrayList<>();
			for(Map<String, Object> map : total ) {
				String flag = String.valueOf(map.get("label"));
				labelList.add(flag);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
				Integer completeInteger = isContain(flag, complete);
				completeList.add(completeInteger);
				unCompleteList.add(totalInteger - completeInteger);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("labelData", labelList);
			result.put("totalData", totalList);
			result.put("completeData", completeList);
			result.put("unCompleteData", unCompleteList);
			return result;
		}
		return null;
	}

	public List<Map<String, Object>> findAllPieInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findAllPieInfoByCreateOrgId(id, year, startDate, endDate, month);
	}
	public List<Map<String, Object>> findAllPieInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findAllPieInfoByCreateOrgIds(ids, year, startDate, endDate, month);
	}

	public Map<String, Object> findJuDcInfo(String id, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> total = dao.findJuTotalInfos(id, year, startDate, endDate, month);
		List<Map<String, Object>> complete = dao.findJuCompleteInfos(id, year, startDate, endDate, month);
		if(!CollectionUtils.isEmpty(total)) {
			List<String> labelList = new ArrayList<>();
			List<Integer> totalList = new ArrayList<>();
			List<Integer> completeList = new ArrayList<>();
			List<Integer> unCompleteList = new ArrayList<>();
			for(Map<String, Object> map : total ) {
				String flag = String.valueOf(map.get("label"));
				labelList.add(flag);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
				Integer completeInteger = isContain(flag, complete);
				completeList.add(completeInteger);
				unCompleteList.add(totalInteger - completeInteger);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("labelData", labelList);
			result.put("totalData", totalList);
			result.put("completeData", completeList);
			result.put("unCompleteData", unCompleteList);
			return result;
		}
		return null;
	}
	public Map<String, Object> findJuDcInfos(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> total = dao.findJuMultiTotalInfos(ids, year, startDate, endDate, month);
		List<Map<String, Object>> complete = dao.findJuMultiCompleteInfos(ids, year, startDate, endDate, month);
		if(!CollectionUtils.isEmpty(total)) {
			List<String> labelList = new ArrayList<>();
			List<Integer> totalList = new ArrayList<>();
			List<Integer> completeList = new ArrayList<>();
			List<Integer> unCompleteList = new ArrayList<>();
			for(Map<String, Object> map : total ) {
				String flag = String.valueOf(map.get("label"));
				labelList.add(flag);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
				Integer completeInteger = isContain(flag, complete);
				completeList.add(completeInteger);
				unCompleteList.add(totalInteger - completeInteger);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("labelData", labelList);
			result.put("totalData", totalList);
			result.put("completeData", completeList);
			result.put("unCompleteData", unCompleteList);
			return result;
		}
		return null;
	}

	public List<Map<String, Object>> findJuPieInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findJuPieInfoByCreateOrgId(id, year, startDate, endDate, month);
	}
	public List<Map<String, Object>> findJuPieInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findJuPieInfoByCreateOrgIds(ids, year, startDate, endDate, month);
	}

	public Map<String, Object> findNncDcInfo(String id, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> total = dao.findNncTotalInfos(id, year, startDate, endDate, month);
		List<Map<String, Object>> complete = dao.findNncCompleteInfos(id, year, startDate, endDate, month);
		if(!CollectionUtils.isEmpty(total)) {
			List<String> labelList = new ArrayList<>();
			List<Integer> totalList = new ArrayList<>();
			List<Integer> completeList = new ArrayList<>();
			List<Integer> unCompleteList = new ArrayList<>();
			for(Map<String, Object> map : total ) {
				String flag = String.valueOf(map.get("label"));
				labelList.add(flag);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
				Integer completeInteger = isContain(flag, complete);
				completeList.add(completeInteger);
				unCompleteList.add(totalInteger - completeInteger);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("labelData", labelList);
			result.put("totalData", totalList);
			result.put("completeData", completeList);
			result.put("unCompleteData", unCompleteList);
			return result;
		}
		return null;
	}
	public Map<String, Object> findNncDcInfos(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> total = dao.findNncMultiTotalInfos(ids, year, startDate, endDate, month);
		List<Map<String, Object>> complete = dao.findNncMultiCompleteInfos(ids, year, startDate, endDate, month);
		if(!CollectionUtils.isEmpty(total)) {
			List<String> labelList = new ArrayList<>();
			List<Integer> totalList = new ArrayList<>();
			List<Integer> completeList = new ArrayList<>();
			List<Integer> unCompleteList = new ArrayList<>();
			for(Map<String, Object> map : total ) {
				String flag = String.valueOf(map.get("label"));
				labelList.add(flag);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
				Integer completeInteger = isContain(flag, complete);
				completeList.add(completeInteger);
				unCompleteList.add(totalInteger - completeInteger);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("labelData", labelList);
			result.put("totalData", totalList);
			result.put("completeData", completeList);
			result.put("unCompleteData", unCompleteList);
			return result;
		}
		return null;
	}

	public List<Map<String, Object>> findNncPieInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findNncPieInfoByCreateOrgId(id, year, startDate, endDate, month);
	}
	public List<Map<String, Object>> findNncPieInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findNncPieInfoByCreateOrgIds(ids, year, startDate, endDate, month);
	}

	public Map<String, Object> findLzcDcInfo(String id, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> total = dao.findLzcTotalInfos(id, year, startDate, endDate, month);
		List<Map<String, Object>> complete = dao.findLzcCompleteInfos(id, year, startDate, endDate, month);
		if(!CollectionUtils.isEmpty(total)) {
			List<String> labelList = new ArrayList<>();
			List<Integer> totalList = new ArrayList<>();
			List<Integer> completeList = new ArrayList<>();
			List<Integer> unCompleteList = new ArrayList<>();
			for(Map<String, Object> map : total ) {
				String flag = String.valueOf(map.get("label"));
				labelList.add(flag);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
				Integer completeInteger = isContain(flag, complete);
				completeList.add(completeInteger);
				unCompleteList.add(totalInteger - completeInteger);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("labelData", labelList);
			result.put("totalData", totalList);
			result.put("completeData", completeList);
			result.put("unCompleteData", unCompleteList);
			return result;
		}
		return null;
	}
	public Map<String, Object> findLzcDcInfos(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> total = dao.findLzcMultiTotalInfos(ids, year, startDate, endDate, month);
		List<Map<String, Object>> complete = dao.findLzcMultiCompleteInfos(ids, year, startDate, endDate, month);
		if(!CollectionUtils.isEmpty(total)) {
			List<String> labelList = new ArrayList<>();
			List<Integer> totalList = new ArrayList<>();
			List<Integer> completeList = new ArrayList<>();
			List<Integer> unCompleteList = new ArrayList<>();
			for(Map<String, Object> map : total ) {
				String flag = String.valueOf(map.get("label"));
				labelList.add(flag);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
				Integer completeInteger = isContain(flag, complete);
				completeList.add(completeInteger);
				unCompleteList.add(totalInteger - completeInteger);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("labelData", labelList);
			result.put("totalData", totalList);
			result.put("completeData", completeList);
			result.put("unCompleteData", unCompleteList);
			return result;
		}
		return null;
	}

	public List<Map<String, Object>> findLzcPieInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findLzcPieInfoByCreateOrgId(id, year, startDate, endDate, month);
	}
	public List<Map<String, Object>> findLzcPieInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findLzcPieInfoByCreateOrgIds(ids, year, startDate, endDate, month);
	}

	public Map<String, Object> findBhcDcInfo(String id, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> total = dao.findBhcTotalInfos(id, year, startDate, endDate, month);
		List<Map<String, Object>> complete = dao.findBhcCompleteInfos(id, year, startDate, endDate, month);
		if(!CollectionUtils.isEmpty(total)) {
			List<String> labelList = new ArrayList<>();
			List<Integer> totalList = new ArrayList<>();
			List<Integer> completeList = new ArrayList<>();
			List<Integer> unCompleteList = new ArrayList<>();
			for(Map<String, Object> map : total ) {
				String flag = String.valueOf(map.get("label"));
				labelList.add(flag);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
				Integer completeInteger = isContain(flag, complete);
				completeList.add(completeInteger);
				unCompleteList.add(totalInteger - completeInteger);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("labelData", labelList);
			result.put("totalData", totalList);
			result.put("completeData", completeList);
			result.put("unCompleteData", unCompleteList);
			return result;
		}
		return null;
	}
	public Map<String, Object> findBhcDcInfos(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> total = dao.findBhcMultiTotalInfos(ids, year, startDate, endDate, month);
		List<Map<String, Object>> complete = dao.findBhcMultiCompleteInfos(ids, year, startDate, endDate, month);
		if(!CollectionUtils.isEmpty(total)) {
			List<String> labelList = new ArrayList<>();
			List<Integer> totalList = new ArrayList<>();
			List<Integer> completeList = new ArrayList<>();
			List<Integer> unCompleteList = new ArrayList<>();
			for(Map<String, Object> map : total ) {
				String flag = String.valueOf(map.get("label"));
				labelList.add(flag);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
				Integer completeInteger = isContain(flag, complete);
				completeList.add(completeInteger);
				unCompleteList.add(totalInteger - completeInteger);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("labelData", labelList);
			result.put("totalData", totalList);
			result.put("completeData", completeList);
			result.put("unCompleteData", unCompleteList);
			return result;
		}
		return null;
	}

	public List<Map<String, Object>> findBhcPieInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findBhcPieInfoByCreateOrgId(id, year, startDate, endDate, month);
	}
	public List<Map<String, Object>> findBhcPieInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findBhcPieInfoByCreateOrgIds(ids, year, startDate, endDate, month);
	}

	public List<Map<String, Object>> findAllSupervisoryUnitByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findAllSupervisoryUnitByCreateOrgId(id, year, startDate, endDate, month);
	}

	public List<Map<String, Object>> findAllSupervisoryUnitByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findAllSupervisoryUnitByCreateOrgIds(ids, year, startDate, endDate, month);
	}

	public List<Map<String, Object>> findSupervisoryUnitByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findSupervisoryUnitByCreateOrgId(id, year, startDate, endDate, month);
	}

	public List<Map<String, Object>> findSupervisoryUnitByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findSupervisoryUnitByCreateOrgIds(ids, year, startDate, endDate, month);
	}

	public List<AffairDcwtLibrary> findAllInfoList(AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return dao.findAllInfoList(affairDcwtLibrary);
	}

	public List<AffairDcwtLibrary> findAllPieInfoList(AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return dao.findAllPieInfoList(affairDcwtLibrary);
	}

	public List<AffairDcwtLibrary> findAllDetailPieInfoList(AffairDcwtLibrary affairDcwtLibrary){
		return dao.findAllDetailPieInfoList(affairDcwtLibrary);
	}

	public List<AffairDcwtLibrary> findAllDetailInfoList(AffairDcwtLibrary affairDcwtLibrary){
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findAllDetailInfoList(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findAllDetailInfoList(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findAllDetailInfoList(affairDcwtLibrary);
		}
		return list;
	}
/*	public Page<AffairDcwtLibrary> findAllDetailInfoList(Page<AffairDcwtLibrary> page, AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.setPage(page);
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findAllDetailInfoList(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findAllDetailInfoList(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findAllDetailInfoList(affairDcwtLibrary);
		}
		page.setList(list);
		return page;
	}*/

	public List<AffairDcwtLibrary> findAllPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary){
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findAllPieDetailInfoList(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findAllPieDetailInfoList(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findAllPieDetailInfoList(affairDcwtLibrary);
		}
		return list;
	}
/*	public Page<AffairDcwtLibrary> findAllPieDetailInfoList(Page<AffairDcwtLibrary> page, AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.setPage(page);
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findAllPieDetailInfoList(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findAllPieDetailInfoList(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findAllPieDetailInfoList(affairDcwtLibrary);
		}
		page.setList(list);
		return page;
	}*/

	public List<AffairDcwtLibrary> findJuInfoList(AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return dao.findJuInfoList(affairDcwtLibrary);
	}

	public List<AffairDcwtLibrary> findJuPieInfoList(AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return dao.findJuPieInfoList(affairDcwtLibrary);
	}

	public List<AffairDcwtLibrary> findJuDetailPieInfoList(AffairDcwtLibrary affairDcwtLibrary){
		return dao.findJuDetailPieInfoList(affairDcwtLibrary);
	}

	public List<AffairDcwtLibrary> findJuDetailInfoList(AffairDcwtLibrary affairDcwtLibrary){
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findJuDetailInfoList(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findJuDetailInfoList(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findJuDetailInfoList(affairDcwtLibrary);
		}
		return list;
	}

	/*public Page<AffairDcwtLibrary> findJuDetailInfoList(Page<AffairDcwtLibrary> page, AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.setPage(page);
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findJuDetailInfoList(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findJuDetailInfoList(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findJuDetailInfoList(affairDcwtLibrary);
		}
		page.setList(list);
		return page;
	}*/

	public List<AffairDcwtLibrary> findJuPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary){
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findJuPieDetailInfoList(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findJuPieDetailInfoList(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findJuPieDetailInfoList(affairDcwtLibrary);
		}
		return list;
	}

	/*public Page<AffairDcwtLibrary> findJuPieDetailInfoList(Page<AffairDcwtLibrary> page, AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.setPage(page);
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findJuPieDetailInfoList(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findJuPieDetailInfoList(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findJuPieDetailInfoList(affairDcwtLibrary);
		}
		page.setList(list);
		return page;
	}*/

	public List<AffairDcwtLibrary> findNncInfoList(AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return dao.findNncInfoList(affairDcwtLibrary);
	}

	public List<AffairDcwtLibrary> findNncPieInfoList(AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return dao.findNncPieInfoList(affairDcwtLibrary);
	}

	public List<AffairDcwtLibrary> findNncDetailPieInfoList(AffairDcwtLibrary affairDcwtLibrary){
		return dao.findNncDetailPieInfoList(affairDcwtLibrary);
	}

	public List<AffairDcwtLibrary> findNncDetailInfoList(AffairDcwtLibrary affairDcwtLibrary){
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findNncDetailInfoList(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findNncDetailInfoList(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findNncDetailInfoList(affairDcwtLibrary);
		}
		return list;
	}

	/*public Page<AffairDcwtLibrary> findNncDetailInfoList(Page<AffairDcwtLibrary> page, AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.setPage(page);
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findNncDetailInfoList(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findNncDetailInfoList(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findNncDetailInfoList(affairDcwtLibrary);
		}
		page.setList(list);
		return page;
	}*/

	public List<AffairDcwtLibrary> findNncPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary){
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findNncPieDetailInfoList(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findNncPieDetailInfoList(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findNncPieDetailInfoList(affairDcwtLibrary);
		}
		return list;
	}

	/*public Page<AffairDcwtLibrary> findNncPieDetailInfoList(Page<AffairDcwtLibrary> page, AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.setPage(page);
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findNncPieDetailInfoList(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findNncPieDetailInfoList(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findNncPieDetailInfoList(affairDcwtLibrary);
		}
		page.setList(list);
		return page;
	}*/

	public List<AffairDcwtLibrary> findLzcInfoList(AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));

		return dao.findLzcInfoList(affairDcwtLibrary);
	}

	public List<AffairDcwtLibrary> findLzcPieInfoList(AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return dao.findLzcPieInfoList(affairDcwtLibrary);
	}

	public List<AffairDcwtLibrary> findLzcDetailPieInfoList(AffairDcwtLibrary affairDcwtLibrary){
		return dao.findLzcDetailPieInfoList(affairDcwtLibrary);
	}

	public List<AffairDcwtLibrary> findLzcDetailInfoList(AffairDcwtLibrary affairDcwtLibrary){
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findLzcDetailInfoList(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findLzcDetailInfoList(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findLzcDetailInfoList(affairDcwtLibrary);
		}
		return list;
	}

	public List<AffairDcwtLibrary> findLzcPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary){
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findLzcPieDetailInfoList(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findLzcPieDetailInfoList(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findLzcPieDetailInfoList(affairDcwtLibrary);
		}
		return list;
	}

	public List<AffairDcwtLibrary> findBhcInfoList(AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return dao.findBhcInfoList(affairDcwtLibrary);
	}

	public List<AffairDcwtLibrary> findBhcPieInfoList(AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));

		return dao.findBhcPieInfoList(affairDcwtLibrary);
	}

	public List<AffairDcwtLibrary> findBhcDetailPieInfoList(AffairDcwtLibrary affairDcwtLibrary){
		return dao.findBhcDetailPieInfoList(affairDcwtLibrary);
	}

	public List<AffairDcwtLibrary> findBhcDetailInfoList(AffairDcwtLibrary affairDcwtLibrary){
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findBhcDetailInfoList(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findBhcDetailInfoList(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findBhcDetailInfoList(affairDcwtLibrary);
		}
		return list;
	}

	public List<AffairDcwtLibrary> findBhcPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary){
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findBhcPieDetailInfoList(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findBhcPieDetailInfoList(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findBhcPieDetailInfoList(affairDcwtLibrary);
		}
		return list;
	}

	public Page<AffairDcwtLibrary> findAllDcLibrary(Page<AffairDcwtLibrary> page, AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.setPage(page);
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findAllDcLibrary(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findAllDcLibrary(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findAllDcLibrary(affairDcwtLibrary);
		}
		page.setList(list);
		return page;
	}

	public Page<AffairDcwtLibrary> findAllDcLibraryByJdUnit(Page<AffairDcwtLibrary> page, AffairDcwtLibrary affairDcwtLibrary){
		affairDcwtLibrary.setPage(page);
		List<AffairDcwtLibrary> list = new ArrayList<>();
		if("1".equals(affairDcwtLibrary.getDateType())){//月度
			if (affairDcwtLibrary.getMonth() != null && affairDcwtLibrary.getMonth().length() > 0) {
				affairDcwtLibrary.setYear(null);
				affairDcwtLibrary.setStartDate(null);
				affairDcwtLibrary.setEndDate(null);
				list = affairDcwtLibraryDao.findAllDcLibraryByJdUnit(affairDcwtLibrary);
			}
		}else if("2".equals(affairDcwtLibrary.getDateType())){//年度
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setStartDate(null);
			affairDcwtLibrary.setEndDate(null);
			list = affairDcwtLibraryDao.findAllDcLibraryByJdUnit(affairDcwtLibrary);
		}else{// 时间段
			affairDcwtLibrary.setMonth(null);
			affairDcwtLibrary.setYear(null);
			Date startDate = DateUtils.parseDate(affairDcwtLibrary.getStartDate());
			Date endDate = DateUtils.parseDate(affairDcwtLibrary.getEndDate());
			affairDcwtLibrary.setStartDate(startDate);
			affairDcwtLibrary.setEndDate(endDate);
			list = affairDcwtLibraryDao.findAllDcLibraryByJdUnit(affairDcwtLibrary);
		}
		page.setList(list);
		return page;
	}

/*	public Map<String, Object> findInspectorAllPiePageInfo(String flag, String id, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> yiZhengGai = affairDcwtLibraryDao.findInspector1AllPiePageInfo(flag, id,year,startDate,endDate,month);
		List<Map<String, Object>> weiZhengGai = affairDcwtLibraryDao.findInspector2AllPiePageInfo(flag,id,year,startDate,endDate,month);
		Map<String, Object> result = new HashMap<>();
		List<String> labelList = new ArrayList<>();
		List<Integer> totalList = new ArrayList<>();
		if (CollectionUtils.isEmpty(yiZhengGai)){
			totalList.add(0);
			labelList.add("已整改");
		}else {
			for (Map<String, Object> map : yiZhengGai) {
				String label = String.valueOf(map.get("label"));
				labelList.add(label);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
			}
		}
		if (CollectionUtils.isEmpty(weiZhengGai)){
			totalList.add(0);
			labelList.add("未整改");
		}else {
			for (Map<String, Object> map : weiZhengGai) {
				String label = String.valueOf(map.get("label"));
				labelList.add(label);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
			}
		}
		result.put("labelData", labelList);
		result.put("totalData", totalList);
		return result;
	}*/


	/*public Map<String, Object> findInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> total = dao.findTotalInfos(id, year, startDate, endDate, month);
		List<Map<String, Object>> complete = dao.findCompleteInfos(id, year, startDate, endDate, month);
		if(!CollectionUtils.isEmpty(total)) {
			List<String> labelList = new ArrayList<>();
			List<Integer> totalList = new ArrayList<>();
			List<Integer> completeList = new ArrayList<>();
			List<Integer> unCompleteList = new ArrayList<>();
			for(Map<String, Object> map : total ) {
				String flag = String.valueOf(map.get("label"));
				labelList.add(flag);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
				Integer completeInteger = isContain(flag, complete);
				completeList.add(completeInteger);
				unCompleteList.add(totalInteger - completeInteger);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("labelData", labelList);
			result.put("totalData", totalList);
			result.put("completeData", completeList);
			result.put("unCompleteData", unCompleteList);
			return result;
		}
		return null;
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> total = dao.findMultiTotalInfos(ids, year, startDate, endDate, month);
		List<Map<String, Object>> complete = dao.findMultiCompleteInfos(ids, year, startDate, endDate, month);
		if(!CollectionUtils.isEmpty(total)) {
			List<String> labelList = new ArrayList<>();
			List<Integer> totalList = new ArrayList<>();
			List<Integer> completeList = new ArrayList<>();
			List<Integer> unCompleteList = new ArrayList<>();
			for(Map<String, Object> map : total ) {
				String flag = String.valueOf(map.get("label"));
				labelList.add(flag);
				Integer totalInteger = Integer.valueOf(String.valueOf(map.get("count")));
				totalList.add(totalInteger);
				Integer completeInteger = isContain(flag, complete);
				completeList.add(completeInteger);
				unCompleteList.add(totalInteger - completeInteger);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("labelData", labelList);
			result.put("totalData", totalList);
			result.put("completeData", completeList);
			result.put("unCompleteData", unCompleteList);
			return result;
		}
		return null;
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


	/*public List<Map<String, Object>> findPieInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findPieInfoByCreateOrgId(id, year, startDate, endDate, month);
	}
	public List<Map<String, Object>> findPieInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findPieInfoByCreateOrgIds(ids, year, startDate, endDate, month);
	}

	public List<Map<String, Object>> findSupervisoryUnitByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findSupervisoryUnitByCreateOrgId(id, year, startDate, endDate, month);
	}

	public List<Map<String, Object>> findSupervisoryUnitByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findSupervisoryUnitByCreateOrgIds(ids, year, startDate, endDate, month);
	}*/

	//时间排序
	public List<AffairDcwtLibrary> findListBy(AffairDcwtLibrary affairDcwtLibrary) {
		return dao.findListBy(affairDcwtLibrary);
	}
	public Page<AffairDcwtLibrary> findListByDatePage(Page<AffairDcwtLibrary> page, AffairDcwtLibrary affairDcwtLibrary) {
		affairDcwtLibrary.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		affairDcwtLibrary.setPage(page);
		page.setList(dao.findListBy(affairDcwtLibrary));
		return page;
	}
}