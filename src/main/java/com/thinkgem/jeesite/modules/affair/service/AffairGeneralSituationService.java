/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairGeneralSituationDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairGeneralSituation;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 党组织概况Service
 * @author eav.liu
 * @version 2019-10-31
 */
@Service
@Transactional(readOnly = true)
public class AffairGeneralSituationService extends CrudService<AffairGeneralSituationDao, AffairGeneralSituation> {
	@Autowired
	private AffairGeneralSituationDao affairGeneralSituationDao;

	public AffairGeneralSituation get(String id) {
		return super.get(id);
	}
	
	public List<AffairGeneralSituation> findList(AffairGeneralSituation affairGeneralSituation) {
		return super.findList(affairGeneralSituation);
	}
	
	public Page<AffairGeneralSituation> findPage(Page<AffairGeneralSituation> page, AffairGeneralSituation affairGeneralSituation) {
		affairGeneralSituation.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairGeneralSituation);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairGeneralSituation affairGeneralSituation) {
		//根据单位id维护parent_id,parent_ids字段,这两个字段必须维护，否则机构树的父子级会有问题
		/*11.11  kevin.jia  parent_id 及  parent_ids 字段赋值调整*/
		String unitId = affairGeneralSituation.getUnitId();
		if(StringUtils.isNotBlank(affairGeneralSituation.getOfPartyOrgId())){
			AffairGeneralSituation parentOrg = this.get(affairGeneralSituation.getOfPartyOrgId());
			affairGeneralSituation.setParentId(affairGeneralSituation.getOfPartyOrgId());//设置父id
			affairGeneralSituation.setParentIds(parentOrg.getParentIds()+parentOrg.getId()+",");
		}
		if(affairGeneralSituation.getUnitId() != null) {
			if ("1".equals(unitId)) {//南宁铁路公安局
				affairGeneralSituation.setUnit("南宁铁路公安局");
				//affairGeneralSituation.setParentId("2");
				//affairGeneralSituation.setParentIds("0,1,2,");
			} else if ("34".equals(unitId)) {//南宁铁路公安局南宁公安处
				affairGeneralSituation.setUnit("南宁铁路公安局南宁公安处");
				//affairGeneralSituation.setParentId("34");
				//affairGeneralSituation.setParentIds("0,1,34,");
			} else if ("95".equals(unitId)) {//南宁铁路公安局柳州公安处
				affairGeneralSituation.setUnit("南宁铁路公安局柳州公安处");
				//affairGeneralSituation.setParentId("95");
				//affairGeneralSituation.setParentIds("0,1,95,");
			} else if ("156".equals(unitId)) {//南宁铁路公安局北海公安处
				affairGeneralSituation.setUnit("南宁铁路公安局北海公安处");
				//affairGeneralSituation.setParentId("156");
				//affairGeneralSituation.setParentIds("0,1,156,");
			}
		}
		super.save(affairGeneralSituation);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairGeneralSituation affairGeneralSituation) {
		super.delete(affairGeneralSituation);
	}

	/**
	 * 根据党支部名字查找党支部id
	 * @param name
	 * @return
	 */
	public String findByName(String name) {
		if(!StringUtils.isNotBlank(name)){
			return null;
		}
		List<AffairGeneralSituation> list = affairGeneralSituationDao.findByPartyOrganization(name);
		if(list != null && list.size() >0){
			return list.get(0).getId();
		}
		return null;
	}

	public List<Map<String, Object>> findInfoById(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoById(id, year ,startDate, endDate, month);
	}

	public List<Map<String, Object>> findInfoByIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByIds(ids, year, startDate, endDate, month);
	}

	/**
	 * 获得党组织概况树
	 * @return
	 */
	public List<AffairGeneralSituation> findTreeData(Boolean isAll) {
		AffairGeneralSituation affairGeneralSituation= new AffairGeneralSituation();
//		User user = UserUtils.getUser();
//		affairGeneralSituation.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "o", "u"));
		if(!"1".equals(UserUtils.getUser().getOffice().getId())){
			//Map<String,String> orgInfo = affairGeneralSituationDao.getOrgInfo(UserUtils.getUser().getOffice().getId());
			Map<String,String> orgInfo = affairGeneralSituationDao.getOrgInfo(UserUtils.getUser().getOffice().getId());
			if(null != orgInfo){
				if(null != orgInfo.get("id")){
					affairGeneralSituation.setId(orgInfo.get("id"));
				}
				if(null != orgInfo.get("parent_ids")){
					affairGeneralSituation.setParentIds(orgInfo.get("parent_ids"));
				}
			}
			else{
				affairGeneralSituation.setId("-1");
				affairGeneralSituation.setParentIds("-1");
			}
		}
		if(isAll!=null && isAll){
			affairGeneralSituation.setId(null);
			affairGeneralSituation.setParentIds(null);
		}
		//List<AffairGeneralSituation> treeDataList = affairGeneralSituationDao.findTreeData(affairGeneralSituation);
		List<AffairGeneralSituation> treeDataList = affairGeneralSituationDao.findTreeData2(affairGeneralSituation);
//		List<AffairGeneralSituation> treeDataList = affairGeneralSituationDao.findTreeData();
//		AffairGeneralSituation nanNingJu = new AffairGeneralSituation();
//		nanNingJu.setId("1");
//		nanNingJu.setPartyOrganization("南宁铁路公安局党委");
//		nanNingJu.setParentId("0");
//		nanNingJu.setParentIds("0,");
//		AffairGeneralSituation jiGuanDangWei= new AffairGeneralSituation();
//		jiGuanDangWei.setId("2");
//		jiGuanDangWei.setPartyOrganization("南宁铁路公安局机关党委");
//		jiGuanDangWei.setParentId("1");
//		jiGuanDangWei.setParentIds("0,1,");
//		AffairGeneralSituation nanNingChu = new AffairGeneralSituation();
//		nanNingChu.setId("34");
//		nanNingChu.setPartyOrganization("南宁铁路公安处党委");
//		nanNingChu.setParentId("1");
//		nanNingChu.setParentIds("0,1,");
//		AffairGeneralSituation liuZhouChu = new AffairGeneralSituation();
//		liuZhouChu.setId("95");
//		liuZhouChu.setPartyOrganization("柳州铁路公安处党委");
//		liuZhouChu.setParentId("1");
//		liuZhouChu.setParentIds("0,1,");
//		AffairGeneralSituation beiHaiChu = new AffairGeneralSituation();
//		beiHaiChu.setId("156");
//		beiHaiChu.setPartyOrganization("北海铁路公安处党委");
//		beiHaiChu.setParentId("1");
//		beiHaiChu.setParentIds("0,1,");
//		treeDataList.add(nanNingJu);
//		treeDataList.add(jiGuanDangWei);
//		treeDataList.add(nanNingChu);
//		treeDataList.add(liuZhouChu);
//		treeDataList.add(beiHaiChu);
		return treeDataList;
	}

	/**
	 * 获得党组织概况树
	 * 党组织页面采用
	 * @return
	 */
	public List<AffairGeneralSituation> findTreeData2(Boolean isAll) {
		AffairGeneralSituation affairGeneralSituation= new AffairGeneralSituation();
//		User user = UserUtils.getUser();
//		affairGeneralSituation.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "o", "u"));
		if(!"1".equals(UserUtils.getUser().getOffice().getId())){
			User user = UserUtils.getUser();
			if(user.getOffice().getId().equals("1111")||user.getOffice().getId().equals("3434")||user.getOffice().getId().equals("9595")||user.getOffice().getId().equals("156156")){
				user.setOffice(user.getCompany());
			}
			Map<String,String> orgInfo = affairGeneralSituationDao.getOrgInfo(UserUtils.getUser().getOffice().getId());
			if(null != orgInfo){
				if(null != orgInfo.get("id")){
					affairGeneralSituation.setId(orgInfo.get("id"));
				}
				if(null != orgInfo.get("parent_ids")){
					affairGeneralSituation.setParentIds(orgInfo.get("parent_ids"));
				}
			}
			else{
				affairGeneralSituation.setId("-1");
				affairGeneralSituation.setParentIds("-1");
			}
		}
		if(isAll!=null && isAll){
			affairGeneralSituation.setId(null);
			affairGeneralSituation.setParentIds(null);
		}
		List<AffairGeneralSituation> treeDataList = affairGeneralSituationDao.findTreeData(affairGeneralSituation);
		return treeDataList;
	}

	/**
	 * 根据党组织名称查询list集合
	 * @return
	 */
	public List<AffairGeneralSituation> findByPartyOrganization(String partyOrganization){
		return affairGeneralSituationDao.findByPartyOrganization(partyOrganization);
	 }

	@Transactional(readOnly = false)
	public void deleteByPartyOrganization(String partyOrganization) {
		affairGeneralSituationDao.deleteByPartyOrganization(partyOrganization);
	}

	public List<String> findAllChildIdById(String id) {
		return affairGeneralSituationDao.findAllChildIdById(id);
	}

	public List<AffairGeneralSituation> findJLChFtreeData() {
		List<AffairGeneralSituation> treeDataList = affairGeneralSituationDao.findJLChFtreeData();
		return treeDataList;
	}

	public List<AffairGeneralSituation> transfertreeData() {
		List<AffairGeneralSituation> treeDataList = affairGeneralSituationDao.findTransfertreeData();
		return treeDataList;
	}

	//统计分析查询  民主（组织）生活会未完成情况明细
	//组织id不在民主生活会中 即 未完成
	public Page<AffairGeneralSituation> findLifeMeetPage(Page<AffairGeneralSituation> page, AffairGeneralSituation affairGeneralSituation) {
		affairGeneralSituation.setPage(page);
		page.setList(dao.findLifeMeetList(affairGeneralSituation));
		return page;
	}

	/**
	 * 统计分析 查看党书记述职完成情况 明细
	 * @param page
	 * @param affairGeneralSituation
	 * @return
	 */
    public Page<AffairGeneralSituation> findAssessDetailPage(Page<AffairGeneralSituation> page, AffairGeneralSituation affairGeneralSituation) {

		String dateType = Optional.ofNullable(affairGeneralSituation.getDateType()).orElseGet(() -> {return "";});
		if (dateType.equals("1")){	//月度查询
			affairGeneralSituation.setYear(null);
			affairGeneralSituation.setStartDate(null);
			affairGeneralSituation.setEndDate(null);
		}else if (dateType.equals("3")){	//时间段查询
			affairGeneralSituation.setYear(null);
			affairGeneralSituation.setMonth(null);
			affairGeneralSituation.setStartDate(DateUtils.parseDate(affairGeneralSituation.getDateStart()));
			affairGeneralSituation.setEndDate(DateUtils.parseDate(affairGeneralSituation.getDateEnd()));
		}else {	//年度查询
			affairGeneralSituation.setMonth(null);
			affairGeneralSituation.setStartDate(null);
			affairGeneralSituation.setEndDate(null);
		}
    	
    	affairGeneralSituation.setPage(page);
    	page.setList(dao.findAssessDetailList(affairGeneralSituation));
    	return page;
    }

    public String selectUnitId(String unitName ){
    	return affairGeneralSituationDao.selectUnitId(unitName);
	}

	/**
	 * 党日活动自动考评查询书记
	 * @param org
	 * @return
	 */
	public String findNameByOrg(String org){
		return affairGeneralSituationDao.findNameByOrg(org);
	}
	public String findNameByPartyName(String name){
		return affairGeneralSituationDao.findNameByPartyName(name);
	}

	public String selectNameById(String id){
		return affairGeneralSituationDao.selectNameById(id);
	}

	public String selectSJName(String name){
		return affairGeneralSituationDao.selectSJName(name);
	}

	public List<String> selectAllName(){
		return affairGeneralSituationDao.selectAllName();
	}

	@Transactional(readOnly = false)
	public void shenHeSave(AffairGeneralSituation affairGeneralSituation) {
		affairGeneralSituationDao.shenHeSave(affairGeneralSituation);
	}

	public String selectPartyOrganizationById(String id){
		return affairGeneralSituationDao.selectPartyOrganizationById(id);
	}
	//查询三个处的下的所有党支部
	public List<AffairGeneralSituation> selectChuPartyBranch() {
		return affairGeneralSituationDao.selectChuPartyBranch();
	}
	//查询局机关党组织
	public List<AffairGeneralSituation> getJJGPartyBranch() {
		return affairGeneralSituationDao.getJJGPartyBranch();
	}
	/**
	 * @param chuId 处单位id
	 * @param type  类型 1 ：处机关    2 ：派出所
	 */
	public List<AffairGeneralSituation> getChuPartyBranch(String chuId, String type) {
		return affairGeneralSituationDao.getChuPartyBranch(chuId,type);
	}
	//获取各处党委奖励信息
    public List<AffairGeneralSituation> getChuPartyCommittee() {
		return affairGeneralSituationDao.getChuPartyCommittee();
    }

	public List<AffairGeneralSituation> selectAllPartyBranch() {
		return affairGeneralSituationDao.selectAllPartyBranch();
	}

	public List<AffairGeneralSituation> selectAlldzz() {
		return affairGeneralSituationDao.selectAlldzz();
	}

	public List<AffairGeneralSituation> selectAllParty() {
		return affairGeneralSituationDao.selectAllParty();
	}

	public List<AffairGeneralSituation> selectAllPartydxz() {
		return affairGeneralSituationDao.selectAllPartydxz();
	}

	public List<AffairGeneralSituation> selectChuParty(String chuId) {
		return affairGeneralSituationDao.selectChuParty(chuId);
	}
}