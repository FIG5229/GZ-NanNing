/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.ChartLabelUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairTjRegisterDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTjRegister;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 团籍注册Service
 * @author cecil.li
 * @version 2019-11-20
 */
@Service
@Transactional(readOnly = true)
public class AffairTjRegisterService extends CrudService<AffairTjRegisterDao, AffairTjRegister> {

	@Autowired
	private AffairTjRegisterDao affairTjRegisterDao;
	@Autowired
	private PersonnelBaseService personnelBaseService;

	public AffairTjRegister get(String id) {
		return super.get(id);
	}

	/**
	 * 批量查找
	 * @param ids
	 */
	public List<AffairTjRegister> findByIds(List<String> ids){
		List<AffairTjRegister> list = affairTjRegisterDao.findByIds(ids);
		return list;
	}
	
	public List<AffairTjRegister> findList(AffairTjRegister affairTjRegister) {
		return super.findList(affairTjRegister);
	}
	
	public Page<AffairTjRegister> findPage(Page<AffairTjRegister> page, AffairTjRegister affairTjRegister) {
		/*affairTjRegister.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/
		return super.findPage(page, affairTjRegister);
	}

	@Transactional(readOnly = false)
	public void save(AffairTjRegister affairTjRegister) {
		/*PersonnelBase personnelBase = new PersonnelBase();
		//创建一个不带任何信息的容器
		AffairTjRegister affairTjRegister0 = new AffairTjRegister();
		//根据身份证号查重,一旦重复就删除数据库内数据,从新添加
		List<AffairTjRegister> list= affairTjRegisterDao.findAllList(affairTjRegister0);
		if (list.size() == 0 ){

			personnelBase.setIdNumber(affairTjRegister.getIdNumber());
			personnelBase.setPoliceIdNumber(affairTjRegister.getPoliceNo());
			personnelBase.setName(affairTjRegister.getName());
			personnelBase.setSex(affairTjRegister.getSex());
			personnelBase.setBirthday(affairTjRegister.getBirthday());
			personnelBaseService.save(personnelBase);
			super.save(affairTjRegister);
		}else{
			AffairTjRegister affairTjRegisterSame= findSame(affairTjRegister, list);
			if(affairTjRegisterSame != null){
				affairTjRegister.setId(affairTjRegisterSame.getId());
				super.save(affairTjRegister);
				personnelBase.setIdNumber(affairTjRegister.getIdNumber());
				personnelBase.setPoliceIdNumber(affairTjRegister.getPoliceNo());
				personnelBase.setName(affairTjRegister.getName());
				personnelBase.setSex(affairTjRegister.getSex());
				personnelBase.setBirthday(affairTjRegister.getBirthday());
				personnelBaseService.save(personnelBase);
			}
			else {
				//这个地方要加友好提示
				personnelBase.setIdNumber(affairTjRegister.getIdNumber());
				personnelBase.setPoliceIdNumber(affairTjRegister.getPoliceNo());
				personnelBase.setName(affairTjRegister.getName());
				personnelBase.setSex(affairTjRegister.getSex());
				personnelBase.setBirthday(affairTjRegister.getBirthday());
				super.save(affairTjRegister);
				personnelBaseService.save(personnelBase);
			}
		}*/
		AffairTjRegister oldAffairTjRegister = null;
		if(StringUtils.isNotBlank(affairTjRegister.getId())){
			oldAffairTjRegister = affairTjRegisterDao.get(affairTjRegister.getId());
		}
		AffairTjRegister affairTjRegisterFromDb = affairTjRegisterDao.findInfoByIdNumber(affairTjRegister.getIdNumber());
		if(affairTjRegisterFromDb != null){
			affairTjRegister.setId(affairTjRegisterFromDb.getId());
			super.save(affairTjRegister);
		}else{
			super.save(affairTjRegister);
		}
		//清缓存
		if(oldAffairTjRegister != null && StringUtils.isNotBlank(oldAffairTjRegister.getPartyBranchId())){
			CacheUtils.remove("tyMemberCache", "tym_"+oldAffairTjRegister.getPartyBranchId());
		}
		if(StringUtils.isNotBlank(affairTjRegister.getPartyBranchId())){
			CacheUtils.remove("tyMemberCache", "tym_"+affairTjRegister.getPartyBranchId());
		}
	}
	/*private AffairTjRegister findSame(AffairTjRegister affairTjRegister, List<AffairTjRegister> list){
		AffairTjRegister result = null;
		for(AffairTjRegister affairTjRegister1 : list) {
			if (affairTjRegister1.getIdNumber().equals(affairTjRegister.getIdNumber())) {
				result= affairTjRegister1;
				break;
			}
		}
		return  result;
	}*/
	@Transactional(readOnly = false)
	public void delete(AffairTjRegister affairTjRegister) {
		AffairTjRegister oldAffairTjRegister = affairTjRegisterDao.get(affairTjRegister.getId());
		super.delete(affairTjRegister);
		//清缓存
		if(oldAffairTjRegister != null && StringUtils.isNotBlank(oldAffairTjRegister.getPartyBranchId())){
			CacheUtils.remove("tyMemberCache", "tym_"+oldAffairTjRegister.getPartyBranchId());
		}
	}

	public AffairTjRegister getGroup(String idNumber){
		return affairTjRegisterDao.getGroup(idNumber);
	}

	public List<String> findListByIdNo(String idNumber) {
		return affairTjRegisterDao.findListByIdNo(idNumber);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNo(String idNumber) {
		List<String> list = affairTjRegisterDao.getPartyBranchIdByIdNo(idNumber);
		//清缓存
		if(list != null && list.size() > 0){
			for (String partyBranchId: list) {
				if(StringUtils.isNotBlank(partyBranchId)){
					CacheUtils.remove("tyMemberCache", "tym_"+partyBranchId);
				}
			}
		}
		affairTjRegisterDao.deleteById(idNumber);
	}

	/**
	 * 通过团委组织ID获取团员列表
	 * @param partyBranchId
	 * @return
	 */
	public List<AffairTjRegister> findMemberByPartyBranchId(String partyBranchId) {
		List<AffairTjRegister> list = (List<AffairTjRegister>) CacheUtils.get("tyMemberCache","tym_" + partyBranchId);
		if (list == null){
			list = affairTjRegisterDao.findListByPartyBranchId(partyBranchId);
			CacheUtils.put("tyMemberCache", "tym_" + partyBranchId, list);
		}
		return list;

	}

	@Override
	@Transactional(readOnly = false)
	public void deleteByIds(List<String> ids) {
		List<String> list = affairTjRegisterDao.getPartyBranchIdByIds(ids);
		//清缓存
		if(list != null && list.size() > 0){
			for (String partyBranchId: list) {
				if(StringUtils.isNotBlank(partyBranchId)){
					CacheUtils.remove("tyMemberCache", "tym_"+partyBranchId);
				}
			}
		}
		super.deleteByIds(ids);
	}

	/**
	 * 团委人数比例
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @param age
	 * @param bigAge
	 * @return
	 */
	public Map<String,String> findTuanQingAge(String dateType, Integer year, String dateStart, String dateEnd, String month, String age, String bigAge) {
		String officeId = UserUtils.getUser().getCompany().getId();
		Date startDate = null;
		Date endDate = null;
		if (dateType.equals("1")){	//月度
			year=null;
		}else if (dateType.equals("3")){	//时间段
			startDate= DateUtils.parseDate(dateStart);
			endDate= DateUtils.parseDate(dateEnd);
		}else {	//年度
			month=null;
		}
		//return super.dao.findTuanQingAge(officeId,year,startDate,endDate,month,Integer.valueOf(age),Integer.valueOf(bigAge));
		//11.26  团员基本情况无需关联时间
		return super.dao.findTuanQingAge(officeId,null,null,null,null,Integer.valueOf(age),Integer.valueOf(bigAge));
	}

	/**
	 * 团费收缴情况
	 * @param officeId	查看本团及以下数据
	 * @param dateType
	 * @param year
	 * @param month
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
    public List<Map<String, String>> findTourFee(String officeId, String dateType, Integer year, String month,String dateStart, String dateEnd) {
		Date startDate = DateUtils.parseDate(dateStart);
		Date endDate = DateUtils.parseDate(dateEnd);
		if (dateType!=null &&dateType.equals("1")){	//月份查询
			year=null;
			startDate=null;
			endDate=null;
		}else if (dateType!=null &&dateType.equals("3")){	//时间段查询
			year=null;
			month=null;
		}else {	//年度查询
			month=null;
			startDate=null;
			endDate=null;
		}
		return dao.findTourFee(officeId, year,month,startDate,endDate);
    }

	/**
	 * 教育评议
	 * @param officeId	查看本团及以下数据
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	public List<Map<String, String>> findEducation(String officeId, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Date startDate = DateUtils.parseDate(dateStart);
		Date endDate = DateUtils.parseDate(dateEnd);
		if (dateType!=null &&dateType.equals("1")){	//月份查询
			year=null;
			startDate=null;
			endDate=null;
		}else if (dateType!=null &&dateType.equals("3")){	//时间段查询
			year=null;
			month=null;
		}else {	//年度查询
			month=null;
			startDate=null;
			endDate=null;
		}
		//officeId = dataScopeFilter(UserUtils.getUser(), "o", "t");
		officeId = UserUtils.getUser().getOffice().getId();
		String[] array = new String[]{"专科","本科","研究生及以上"};
		//List<Map<String, String>> list= dao.findEducation(officeId,year,startDate,endDate,month);
		//11.26 团员基本情况无需关联时间
		List<Map<String, String>> list= dao.findEducation(officeId,null,null,null,null);
		return ChartLabelUtils.fillLabel(list,array);

	}

	/**
	 * 教育评议明细
	 * @param affairTjRegisterPage
	 * @param affairTjRegister
	 * @return
	 */
	public Page<AffairTjRegister> findEducationPage(Page<AffairTjRegister> affairTjRegisterPage, AffairTjRegister affairTjRegister) {
		/*String dateType = Optional.ofNullable(affairTjRegister.getDateType()).orElseGet(() -> {return "";});
		if (dateType.equals("1")){	//月度查询
			affairTjRegister.setYear(null);
			affairTjRegister.setStartDate(null);
			affairTjRegister.setEndDate(null);
		}else if (dateType.equals("3")){	//时间段查询
			affairTjRegister.setYear(null);
			affairTjRegister.setStartDate(DateUtils.parseDate(affairTjRegister.getDateStart()));
			affairTjRegister.setEndDate(DateUtils.parseDate(affairTjRegister.getDateEnd()));
			affairTjRegister.setMonth(null);
		}else {	//年度查询
			affairTjRegister.setMonth(null);
			affairTjRegister.setStartDate(null);
			affairTjRegister.setEndDate(null);
		}*/
		//11.26 团员基本情况取消时间关联
		affairTjRegister.setMonth(null);
		affairTjRegister.setStartDate(null);
		affairTjRegister.setEndDate(null);
		affairTjRegister.setYear(null);
		//当反查研究生及以上时 数据库中没有 '及以上'  手动添加
		if (!StringUtils.isEmpty(affairTjRegister.getEducation()) && affairTjRegister.getEducation().contains("研究生")){
			//mapping里使用的${}
			affairTjRegister.setEducation("研究生%'or x.name like'%博士");
		}
		if (!StringUtils.isEmpty(affairTjRegister.getEducation()) && affairTjRegister.getEducation().contains("本科")){
			//mapping里使用的${}
			affairTjRegister.setEducation("本科%'or x.name like'%大学");
		}
		if (!StringUtils.isEmpty(affairTjRegister.getEducation()) && affairTjRegister.getEducation().contains("专科")){
			//mapping里使用的${}
			affairTjRegister.setEducation("专科%'or x.name like'%大专");
		}
		/*先设置page*/
		affairTjRegister.setPage(affairTjRegisterPage);
//		String officeId = UserUtils.getUser().getOffice().getId();
//		affairTjRegister.setPartyBranchId(officeId);
		//affairTjRegister.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(), "o", "u"));
		affairTjRegister.setOfficeId(UserUtils.getUser().getOffice().getId());
		affairTjRegisterPage.setList(super.dao.findEducationPage(affairTjRegister));
		return affairTjRegisterPage;
	}

	//统计团员政治面貌信息
	public List<Map<String, String>> countPolitical(String officeId, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Date startDate = DateUtils.parseDate(dateStart);
		Date endDate = DateUtils.parseDate(dateEnd);
		if (dateType.equals("1")) {    //月度
			year = null;
			startDate = null;
			endDate = null;
		} else if (dateType.equals("3")) {    //时间段
			year = null;
			month = null;
		} else {    //年度
			month = null;
			startDate = null;
			endDate = null;
		}
		officeId = UserUtils.getUser().getOffice().getId();
		//officeId= dataScopeFilter(UserUtils.getUser(), "o", "u");
		//return ChartLabelUtils.fillLabel(dao.countPolitical(officeId,year,startDate,endDate,month), DictUtils.getDictList("zzmm"));
		//11.26  团员基本情况无需关联时间
		return ChartLabelUtils.fillLabel(dao.countPolitical(officeId,null,null,null,null), DictUtils.getDictList("zzmm"));
	}

	/**
	 * 统计分析 政治面貌明细
	 * @param affairTjRegisterPage
	 * @param affairTjRegister
	 * @return
	 */
	public Page<AffairTjRegister> findPoliticalPage(Page<AffairTjRegister> affairTjRegisterPage, AffairTjRegister affairTjRegister) {
		/*String dateType = Optional.ofNullable(affairTjRegister.getDateType()).orElseGet(() -> {return "";});
		switch (dateType) {
			case "1":    //月度
				affairTjRegister.setYear(null);
				affairTjRegister.setStartDate(null);
				affairTjRegister.setEndDate(null);
				break;
			case "3":    //时间段
				affairTjRegister.setYear(null);
				affairTjRegister.setStartDate(DateUtils.parseDate(affairTjRegister.getDateStart()));
				affairTjRegister.setEndDate(DateUtils.parseDate(affairTjRegister.getDateEnd()));
				affairTjRegister.setMonth(null);
				break;
			default:    //年度
				affairTjRegister.setMonth(null);
				affairTjRegister.setStartDate(null);
				affairTjRegister.setEndDate(null);
				break;
		}*/
		//11.26 团员基本情况取消时间关联
		affairTjRegister.setMonth(null);
		affairTjRegister.setStartDate(null);
		affairTjRegister.setEndDate(null);
		affairTjRegister.setYear(null);
		affairTjRegister.setPage(affairTjRegisterPage);
//		String officeId = UserUtils.getUser().getOffice().getId();
//		affairTjRegister.setPartyBranchId(officeId);
		//affairTjRegister.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(), "o", "u"));
		affairTjRegister.setOfficeId(UserUtils.getUser().getOffice().getId());
		affairTjRegisterPage.setList(super.dao.findPoliticalPage(affairTjRegister));
		return affairTjRegisterPage;
	}


	public Page<AffairTjRegister> findPoliticalByAgePage(Page<AffairTjRegister> page, AffairTjRegister affairTjRegister) {
		/*String dateType = Optional.ofNullable(affairTjRegister.getDateType()).orElseGet(() -> {return "";});
		if (dateType.equals("1")){	//月度
			affairTjRegister.setYear(null);
		}else if (dateType.equals("3")){	//时间段
			affairTjRegister.setYear(null);
			affairTjRegister.setMonth(null);
			affairTjRegister.setStartDate(DateUtils.parseDate(affairTjRegister.getDateStart()));
			affairTjRegister.setEndDate(DateUtils.parseDate(affairTjRegister.getDateEnd()));
		}else {	//年度
			affairTjRegister.setMonth(null);
		}*/
		//11.26  团员基本情况取消关联时间
		affairTjRegister.setYear(null);
		affairTjRegister.setMonth(null);
		affairTjRegister.setStartDate(null);
		affairTjRegister.setEndDate(null);
		affairTjRegister.setPage(page);
		if (affairTjRegister.getLabel()!=null && affairTjRegister.getLabel().equals("小于28岁")){
			affairTjRegister.setLabel("28");
		}else {
			affairTjRegister.setLabel("35");
		}
		affairTjRegister.setPartyBranchId(UserUtils.getUser().getCompany().getId());
		page.setList(dao.findAgeList(affairTjRegister));
		return page;
	}

	/**
	 * 统计分析 专兼职团干部数
	 * @param officeId	查看本团及以下数据
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	public List<Map<String, String>> countCadres(String officeId, String dateType, Integer year, String dateStart, String dateEnd, String month) {

		Date startDate = DateUtils.parseDate(dateStart);
		Date endDate = DateUtils.parseDate(dateEnd);
		if (dateType.equals("1")) {    //月度
			year = null;
			startDate = null;
			endDate = null;
		} else if (dateType.equals("3")) {    //时间段
			year = null;
			month = null;
		} else {    //年度
			month = null;
			startDate = null;
			endDate = null;
		}
        List<Map<String, String>> list  = dao.countCadres(officeId,year,startDate,endDate,month);
		String[] labelArray = {"团委书记","团委副书记","团委委员","团支部书记","团支部副书记","团支部委员"};

		return ChartLabelUtils.fillLabel(list,labelArray);
	}

	/**
	 * 统计分析 专兼职团干部明细
	 * @param page
	 * @param affairTjRegister
	 * @return
	 */
	public Page<AffairTjRegister> findCadresPage(Page<AffairTjRegister> page, AffairTjRegister affairTjRegister) {
		String officeId = UserUtils.getUser().getOffice().getId();
		affairTjRegister.setPartyBranchId(officeId);
		String dateType = Optional.ofNullable(affairTjRegister.getDateType()).orElseGet(() -> {return "";});
		switch (dateType) {
			case "1":    //月度
				affairTjRegister.setYear(null);
				affairTjRegister.setStartDate(null);
				affairTjRegister.setEndDate(null);
				break;
			case "3":    //时间段
				affairTjRegister.setYear(null);
				affairTjRegister.setStartDate(DateUtils.parseDate(affairTjRegister.getDateStart()));
				affairTjRegister.setEndDate(DateUtils.parseDate(affairTjRegister.getDateEnd()));
				affairTjRegister.setMonth(null);
				break;
			default:    //年度
				affairTjRegister.setMonth(null);
				affairTjRegister.setStartDate(null);
				affairTjRegister.setEndDate(null);
				break;
		}
		affairTjRegister.setPage(page);
		page.setList(dao.findCadresList(affairTjRegister));
		return page;
	}


	/**
	 * 根据团员名册的团组织查询不在团员名册（查询条件）中的其他团员
	 * @param attendIdArray		团员名册的身份证号
	 * @return
	 */
	public List<AffairTjRegister> findPersonNotInList(String[] attendIdArray) {
		return dao.findPersonNotInList(attendIdArray);
	}
}

