/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairPartyMemberDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyMember;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 党员花名册Service
 * @author eav.liu
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairPartyMemberService extends CrudService<AffairPartyMemberDao, AffairPartyMember> {

	@Autowired
	private AffairPartyMemberDao affairPartyMemberDao;

	@Autowired
	private AffairPartyCostService affairPartyService;

	public AffairPartyMember get(String id) {
		return super.get(id);
	}
	
	public List<AffairPartyMember> findList(AffairPartyMember affairPartyMember) {
		return super.findList(affairPartyMember);
	}
	
	public Page<AffairPartyMember> findPage(Page<AffairPartyMember> page, AffairPartyMember affairPartyMember) {
		affairPartyMember.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairPartyMember);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPartyMember affairPartyMember) {
		//预备党员审核状态
		if(affairPartyMember.getStatus2()==null||"".equals(affairPartyMember.getStatus2())){
			affairPartyMember.setStatus2("3");
		}
		AffairPartyMember oldAffairPartyMember = null;
		if(StringUtils.isNotBlank(affairPartyMember.getId())){
			oldAffairPartyMember = affairPartyMemberDao.get(affairPartyMember.getId());
		}

		if (affairPartyMember.getDeleteReason()==null || "".equals(affairPartyMember.getDeleteReason())){
			affairPartyMember.setDeleteReason("0");
		}
		super.save(affairPartyMember);
		//清缓存
		if(oldAffairPartyMember != null && StringUtils.isNotBlank(oldAffairPartyMember.getPartyBranchId())){
			CacheUtils.remove("partyMemberCache", "pm_"+oldAffairPartyMember.getPartyBranchId());
		}
		if(StringUtils.isNotBlank(affairPartyMember.getPartyBranchId())){
			CacheUtils.remove("partyMemberCache", "pm_"+affairPartyMember.getPartyBranchId());
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPartyMember affairPartyMember) {
		AffairPartyMember  oldAffairPartyMember = affairPartyMemberDao.get(affairPartyMember.getId());
		super.delete(affairPartyMember);
        //清缓存
        if(oldAffairPartyMember != null && StringUtils.isNotBlank(oldAffairPartyMember.getPartyBranchId())){
            CacheUtils.remove("partyMemberCache", "pm_"+oldAffairPartyMember.getPartyBranchId());
        }
        //删除党费表今年的相关信息
		affairPartyService.deleteThisYearByIdNumber(affairPartyMember.getCardNum());
	}

	public List<Map<String, Object>> findInfoByPartyBranchId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByPartyBranchId(id, year, startDate, endDate, month);
	}

	public List<Map<String, Object>> findInfoByPartyBranchIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByPartyBranchIds(ids, year, startDate, endDate, month);
	}

	public List<String> findListByIdNo(String idNo) {
		return affairPartyMemberDao.findListByIdNo(idNo);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNo(String idNo) {
        List<String> list = affairPartyMemberDao.getPartyBranchIdByIdNo(idNo);
        //清缓存
		if(list != null && list.size() > 0){
			for (String partyBranchId: list) {
				if(StringUtils.isNotBlank(partyBranchId)){
					CacheUtils.remove("partyMemberCache", "pm_"+partyBranchId);
				}
			}
		}
		affairPartyMemberDao.deleteByIdNo(idNo);
	}

	public AffairPartyMember getGroup(String idNumber) {
		return affairPartyMemberDao.getGroup(idNumber);
	}

	/**
	 * 通过党组织ID获取党员列表，（树查询党员时用）
	 * @param partyBranchId
	 * @return
	 */
	public List<AffairPartyMember> findMemberByPartyBranchId(String partyBranchId) {
//		List<AffairPartyMember> list = (List<AffairPartyMember>) CacheUtils.get("partyMemberCache","pm_" + partyBranchId);
//		if (list == null){
//			list = affairPartyMemberDao.findListByPartyBranchId(partyBranchId);
//			CacheUtils.put("partyMemberCache", "pm_" + partyBranchId, list);
//		}
		List<AffairPartyMember>  list = affairPartyMemberDao.findListByPartyBranchId(partyBranchId);
		return list;
	}

	public List<Map<String, Object>> findCategoryInfoById(String id, Integer year, Date startDate, Date endDate, String month) {
		return affairPartyMemberDao.findCategoryInfoById(id, year ,startDate, endDate, month);
	}

	public List<Map<String, Object>> findCategoryInfoByIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return affairPartyMemberDao.findCategoryInfoByIds(ids, year ,startDate, endDate, month);
	}

	public List<Map<String, Object>> findSexInfoById(String id, Integer year, Date startDate, Date endDate, String month) {
		return affairPartyMemberDao.findSexInfoById(id, year ,startDate, endDate, month);
	}

	public List<Map<String, Object>> findSexInfoByIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return affairPartyMemberDao.findSexInfoByIds(ids, year ,startDate, endDate, month);
	}

	public List<Map<String, Object>> findNationInfoById(String id, Integer year, Date startDate, Date endDate, String month) {
		return affairPartyMemberDao.findNationInfoById(id, year ,startDate, endDate, month);
	}

	public List<Map<String, Object>> findNationInfoByIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return affairPartyMemberDao.findNationInfoByIds(ids, year ,startDate, endDate, month);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteByIds(List<String> ids) {
		List<Map<String, String>> list = affairPartyMemberDao.getPartyBranchIdByIds(ids);
		if(list != null && list.size() > 0){
			for (Map<String, String> m: list) {
				//清缓存
				if(StringUtils.isNotBlank(m.get("partyBranchId"))){
					CacheUtils.remove("partyMemberCache", "pm_"+m.get("partyBranchId"));
				}
				//删除党费表今年的相关信息
				affairPartyService.deleteThisYearByIdNumber(m.get("cardNum"));
			}
		}
		affairPartyMemberDao.deleteByIds(ids);
	}

	public Map<String, Integer> findWorkPlaceInfoById(String id, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> list = affairPartyMemberDao.findWorkPlaceInfoById(id, year, startDate, endDate, month);
		Map<String, Integer> map = new HashMap<>();
		Integer zzhCount = 0;// 在职党员数
		Integer ltxCount = 0;// 离退休党员数
		for (Map<String, Object> m: list) {
			if("离休干部".equals(m.get("workPlace")) || "退休人员 ".equals(m.get("workPlace"))){
				ltxCount += Integer.parseInt(m.get("count").toString());
			}else{
				zzhCount += Integer.parseInt(m.get("count").toString());
			}
		}
		map.put("zzhCount", zzhCount);
		map.put("ltxCount", ltxCount);
		return map;
	}

	public Map<String, Integer> findWorkPlaceInfoByIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> list = affairPartyMemberDao.findWorkPlaceInfoByIds(ids, year, startDate, endDate, month);
		Map<String, Integer> map = new HashMap<>();
		Integer zzhCount = 0;// 在职党员数
		Integer ltxCount = 0;// 离退休党员数
		for (Map<String, Object> m: list) {
			if("离休干部".equals(m.get("workPlace")) || "退休人员 ".equals(m.get("workPlace"))){
				ltxCount += Integer.parseInt(m.get("count").toString());
			}else{
				zzhCount += Integer.parseInt(m.get("count").toString());
			}
		}
		map.put("zzhCount", zzhCount);
		map.put("ltxCount", ltxCount);
		return map;
	}

	public Page<AffairPartyMember> echartWorkPlaceFindPageByPbId(Page<AffairPartyMember> page, AffairPartyMember affairPartyMember) {
		affairPartyMember.setPage(page);
		List<AffairPartyMember> list = new ArrayList();
		if("1".equals(affairPartyMember.getDateType())){//月度
			if (affairPartyMember.getMonth() != null && affairPartyMember.getMonth().length() > 0) {
				affairPartyMember.setYear(null);
				affairPartyMember.setDateStart(null);
				affairPartyMember.setDateEnd(null);
				list = affairPartyMemberDao.echartWorkPlaceFindPageByPbId(affairPartyMember);
			}
		}else if("2".equals(affairPartyMember.getDateType())){//年度
			affairPartyMember.setMonth(null);
			affairPartyMember.setDateStart(null);
			affairPartyMember.setDateEnd(null);
			list = affairPartyMemberDao.echartWorkPlaceFindPageByPbId(affairPartyMember);
		}else{// 时间段
			affairPartyMember.setMonth(null);
			affairPartyMember.setYear(null);
			Date startDate = DateUtils.parseDate(affairPartyMember.getDateStart());
			Date endDate = DateUtils.parseDate(affairPartyMember.getDateEnd());
			affairPartyMember.setStartDate(startDate);
			affairPartyMember.setEndDate(endDate);
			list = affairPartyMemberDao.echartWorkPlaceFindPageByPbId(affairPartyMember);
		}
		page.setList(list);
		return page;
	}

	public Page<AffairPartyMember> echartWorkPlaceFindPageByPbIds(Page<AffairPartyMember> page, AffairPartyMember affairPartyMember) {
		affairPartyMember.setPage(page);
		List<AffairPartyMember> list = new ArrayList();
		if("1".equals(affairPartyMember.getDateType())){//月度
			if (affairPartyMember.getMonth() != null && affairPartyMember.getMonth().length() > 0) {
				affairPartyMember.setYear(null);
				affairPartyMember.setDateStart(null);
				affairPartyMember.setDateEnd(null);
				list = affairPartyMemberDao.echartWorkPlaceFindPageByPbIds(affairPartyMember);
			}
		}else if("2".equals(affairPartyMember.getDateType())){//年度
			affairPartyMember.setMonth(null);
			affairPartyMember.setDateStart(null);
			affairPartyMember.setDateEnd(null);
			list = affairPartyMemberDao.echartWorkPlaceFindPageByPbIds(affairPartyMember);
		}else{// 时间段
			affairPartyMember.setMonth(null);
			affairPartyMember.setYear(null);
			Date startDate = DateUtils.parseDate(affairPartyMember.getDateStart());
			Date endDate = DateUtils.parseDate(affairPartyMember.getDateEnd());
			affairPartyMember.setStartDate(startDate);
			affairPartyMember.setEndDate(endDate);
			list = affairPartyMemberDao.echartWorkPlaceFindPageByPbIds(affairPartyMember);
		}
		page.setList(list);
		return page;
	}

    public Page<AffairPartyMember> echartCategoryFindPageByPbId(Page<AffairPartyMember> page, AffairPartyMember affairPartyMember) {
        affairPartyMember.setPage(page);
        List<AffairPartyMember> list = new ArrayList();
        if("1".equals(affairPartyMember.getDateType())){//月度
            if (affairPartyMember.getMonth() != null && affairPartyMember.getMonth().length() > 0) {
                affairPartyMember.setYear(null);
                affairPartyMember.setDateStart(null);
                affairPartyMember.setDateEnd(null);
                list = affairPartyMemberDao.echartCategoryFindPageByPbId(affairPartyMember);
            }
        }else if("2".equals(affairPartyMember.getDateType())){//年度
            affairPartyMember.setMonth(null);
            affairPartyMember.setDateStart(null);
            affairPartyMember.setDateEnd(null);
            list = affairPartyMemberDao.echartCategoryFindPageByPbId(affairPartyMember);
        }else{// 时间段
            affairPartyMember.setMonth(null);
            affairPartyMember.setYear(null);
            Date startDate = DateUtils.parseDate(affairPartyMember.getDateStart());
            Date endDate = DateUtils.parseDate(affairPartyMember.getDateEnd());
            affairPartyMember.setStartDate(startDate);
            affairPartyMember.setEndDate(endDate);
            list = affairPartyMemberDao.echartCategoryFindPageByPbId(affairPartyMember);
        }
        page.setList(list);
        return page;
    }

    public Page<AffairPartyMember> echartCategoryFindPageByPbIds(Page<AffairPartyMember> page, AffairPartyMember affairPartyMember) {
        affairPartyMember.setPage(page);
        List<AffairPartyMember> list = new ArrayList();
        if("1".equals(affairPartyMember.getDateType())){//月度
            if (affairPartyMember.getMonth() != null && affairPartyMember.getMonth().length() > 0) {
                affairPartyMember.setYear(null);
                affairPartyMember.setDateStart(null);
                affairPartyMember.setDateEnd(null);
                list = affairPartyMemberDao.echartCategoryFindPageByPbIds(affairPartyMember);
            }
        }else if("2".equals(affairPartyMember.getDateType())){//年度
            affairPartyMember.setMonth(null);
            affairPartyMember.setDateStart(null);
            affairPartyMember.setDateEnd(null);
            list = affairPartyMemberDao.echartCategoryFindPageByPbIds(affairPartyMember);
        }else{// 时间段
            affairPartyMember.setMonth(null);
            affairPartyMember.setYear(null);
            Date startDate = DateUtils.parseDate(affairPartyMember.getDateStart());
            Date endDate = DateUtils.parseDate(affairPartyMember.getDateEnd());
            affairPartyMember.setStartDate(startDate);
            affairPartyMember.setEndDate(endDate);
            list = affairPartyMemberDao.echartCategoryFindPageByPbIds(affairPartyMember);
        }
        page.setList(list);
        return page;
    }

	public Page<AffairPartyMember> echartNationFindPageByPbId(Page<AffairPartyMember> page, AffairPartyMember affairPartyMember) {
		affairPartyMember.setPage(page);
		List<AffairPartyMember> list = new ArrayList();
		if("1".equals(affairPartyMember.getDateType())){//月度
			if (affairPartyMember.getMonth() != null && affairPartyMember.getMonth().length() > 0) {
				affairPartyMember.setYear(null);
				affairPartyMember.setDateStart(null);
				affairPartyMember.setDateEnd(null);
				list = affairPartyMemberDao.echartNationFindPageByPbId(affairPartyMember);
			}
		}else if("2".equals(affairPartyMember.getDateType())){//年度
			affairPartyMember.setMonth(null);
			affairPartyMember.setDateStart(null);
			affairPartyMember.setDateEnd(null);
			list = affairPartyMemberDao.echartNationFindPageByPbId(affairPartyMember);
		}else{// 时间段
			affairPartyMember.setMonth(null);
			affairPartyMember.setYear(null);
			Date startDate = DateUtils.parseDate(affairPartyMember.getDateStart());
			Date endDate = DateUtils.parseDate(affairPartyMember.getDateEnd());
			affairPartyMember.setStartDate(startDate);
			affairPartyMember.setEndDate(endDate);
			list = affairPartyMemberDao.echartNationFindPageByPbId(affairPartyMember);
		}
		page.setList(list);
		return page;
	}

	public Page<AffairPartyMember> echartNationFindPageByPbIds(Page<AffairPartyMember> page, AffairPartyMember affairPartyMember) {
		affairPartyMember.setPage(page);
		List<AffairPartyMember> list = new ArrayList();
		if("1".equals(affairPartyMember.getDateType())){//月度
			if (affairPartyMember.getMonth() != null && affairPartyMember.getMonth().length() > 0) {
				affairPartyMember.setYear(null);
				affairPartyMember.setDateStart(null);
				affairPartyMember.setDateEnd(null);
				list = affairPartyMemberDao.echartNationFindPageByPbIds(affairPartyMember);
			}
		}else if("2".equals(affairPartyMember.getDateType())){//年度
			affairPartyMember.setMonth(null);
			affairPartyMember.setDateStart(null);
			affairPartyMember.setDateEnd(null);
			list = affairPartyMemberDao.echartNationFindPageByPbIds(affairPartyMember);
		}else{// 时间段
			affairPartyMember.setMonth(null);
			affairPartyMember.setYear(null);
			Date startDate = DateUtils.parseDate(affairPartyMember.getDateStart());
			Date endDate = DateUtils.parseDate(affairPartyMember.getDateEnd());
			affairPartyMember.setStartDate(startDate);
			affairPartyMember.setEndDate(endDate);
			list = affairPartyMemberDao.echartNationFindPageByPbIds(affairPartyMember);
		}
		page.setList(list);
		return page;
	}

	public Page<AffairPartyMember> echartSexFindPageByPbId(Page<AffairPartyMember> page, AffairPartyMember affairPartyMember) {
		affairPartyMember.setPage(page);
		List<AffairPartyMember> list = new ArrayList();
		if("1".equals(affairPartyMember.getDateType())){//月度
			if (affairPartyMember.getMonth() != null && affairPartyMember.getMonth().length() > 0) {
				affairPartyMember.setYear(null);
				affairPartyMember.setDateStart(null);
				affairPartyMember.setDateEnd(null);
				list = affairPartyMemberDao.echartSexFindPageByPbId(affairPartyMember);
			}
		}else if("2".equals(affairPartyMember.getDateType())){//年度
			affairPartyMember.setMonth(null);
			affairPartyMember.setDateStart(null);
			affairPartyMember.setDateEnd(null);
			list = affairPartyMemberDao.echartSexFindPageByPbId(affairPartyMember);
		}else{// 时间段
			affairPartyMember.setMonth(null);
			affairPartyMember.setYear(null);
			Date startDate = DateUtils.parseDate(affairPartyMember.getDateStart());
			Date endDate = DateUtils.parseDate(affairPartyMember.getDateEnd());
			affairPartyMember.setStartDate(startDate);
			affairPartyMember.setEndDate(endDate);
			list = affairPartyMemberDao.echartSexFindPageByPbId(affairPartyMember);
		}
		page.setList(list);
		return page;
	}

	public Page<AffairPartyMember> echartSexFindPageByPbIds(Page<AffairPartyMember> page, AffairPartyMember affairPartyMember) {
		affairPartyMember.setPage(page);
		List<AffairPartyMember> list = new ArrayList();
		if("1".equals(affairPartyMember.getDateType())){//月度
			if (affairPartyMember.getMonth() != null && affairPartyMember.getMonth().length() > 0) {
				affairPartyMember.setYear(null);
				affairPartyMember.setDateStart(null);
				affairPartyMember.setDateEnd(null);
				list = affairPartyMemberDao.echartSexFindPageByPbIds(affairPartyMember);
			}
		}else if("2".equals(affairPartyMember.getDateType())){//年度
			affairPartyMember.setMonth(null);
			affairPartyMember.setDateStart(null);
			affairPartyMember.setDateEnd(null);
			list = affairPartyMemberDao.echartSexFindPageByPbIds(affairPartyMember);
		}else{// 时间段
			affairPartyMember.setMonth(null);
			affairPartyMember.setYear(null);
			Date startDate = DateUtils.parseDate(affairPartyMember.getDateStart());
			Date endDate = DateUtils.parseDate(affairPartyMember.getDateEnd());
			affairPartyMember.setStartDate(startDate);
			affairPartyMember.setEndDate(endDate);
			list = affairPartyMemberDao.echartSexFindPageByPbIds(affairPartyMember);
		}
		page.setList(list);
		return page;
	}

	public AffairPartyMember byIdNumber(AffairPartyMember affairPartyMember){
		return affairPartyMemberDao.byIdNumber(affairPartyMember);
	}

	//预备党员转正
	public Page<AffairPartyMember> zhuanzheng(Page<AffairPartyMember> page, AffairPartyMember affairPartyMember) {
		affairPartyMember.setPage(page);
		page.setList(affairPartyMemberDao.zhuanzheng(affairPartyMember));
		return page;
	}
	//预备党员审核
	@Transactional(readOnly = false)
	public void shenHe(AffairPartyMember affairPartyMember) {
		affairPartyMember.setUpdateDate(new Date());
		affairPartyMember.setPerson(UserUtils.getUser().getName());
		affairPartyMemberDao.shenHe(affairPartyMember);
	}
	/**
	 * 批量提交
	 * @param ids
	 */
	public List<AffairPartyMember> findByIds(List<String> ids){
		List<AffairPartyMember> list = affairPartyMemberDao.findByIds(ids);
		return list;
	}

	/**
	 * 统计党员数，入党积极分子数，入党发展对象人数，党代表人数
	 * @param year
	 * @param startDate
	 * @param endDate
	 * @param month
	 * @return
	 */
	public List<Map<String,String>> countPartyMember(Integer year, Date startDate, Date endDate, String month) {
		return dao.countPartyMember(year, startDate,endDate,month);
	}

	/**
	 * 统计分析明细 查询党员人数详情
	 *
	 * @param affairPartyMemberPage
	 * @param partyMember
	 * @return
	 */
	public Page<AffairPartyMember> findPartyMemberPage(Page<AffairPartyMember> affairPartyMemberPage, AffairPartyMember partyMember) {
		List<AffairPartyMember> list = new ArrayList<>();
		partyMember.setPage(affairPartyMemberPage);
		switch (partyMember.getName()){
			case "正式党员":
			case "预备党员":
				partyMember.setPersonnelCategory(DictUtils.getDictValue(partyMember.getName(),"affair_personnel_category",""));
				list  = dao.findPartyMemberList(partyMember);
				break;
			case "女":
				partyMember.setSex("2");
				list  = dao.findPartyMemberList(partyMember);
				break;
			case "在职":
			case "离退":
				partyMember.setPersonnelType(DictUtils.getDictValue(partyMember.getName(),"affair_personnel_type",""));
				list  = dao.findPartyMemberList(partyMember);
				break;
			case "少数民族":
				list = dao.findPartyNationList(partyMember);
				break;
			default:
				list = dao.findPartyMemberList(partyMember);
				break;
		}
		//借用党员的name字段 查询类型
		affairPartyMemberPage.setList(list);
		return affairPartyMemberPage;
	}

	/**
	 * 党日活动自动考评
	 * 查找党支部下的人元
	 */
	public List<Map<String, String>> peopleList(String org){
		return affairPartyMemberDao.peopleList(org);
	}

	public List<String> selectNoSJNameList(String name,String unitId){
		return affairPartyMemberDao.selectNoSJNameList(name,unitId);

	}
	public String selectUnit(String id){
		return affairPartyMemberDao.selectUnit(id);
	}

	public String selectUnitId(String unit) {
		return affairPartyMemberDao.selectUnitId(unit);
	}

	public List<AffairPartyMember> selectAllPeople(String yearL,String yearT,String unitId){
		return affairPartyMemberDao.selectAllPeople(yearL,yearT,unitId);
	}
	//查询几天后过政治生日人员
	public List<AffairPartyMember> selThreeDayAfterBirthday(String threeDayAfter) {
		return affairPartyMemberDao.selThreeDayAfterBirthday(threeDayAfter);
	}

	public int getNumByPartyId(String partyId) {
		return affairPartyMemberDao.getNumByPartyId(partyId);
	}
}