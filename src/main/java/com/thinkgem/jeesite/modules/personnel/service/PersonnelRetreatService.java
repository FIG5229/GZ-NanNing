/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelRetreatDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelRetreat;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelRetreatSum;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 离退信息集Service
 * @author cecil.li
 * @version 2019-11-09
 */
@Service
@Transactional(readOnly = true)
public class PersonnelRetreatService extends CrudService<PersonnelRetreatDao, PersonnelRetreat> {
	@Autowired
	PersonnelRetreatDao personnelRetreatDao;
	@Autowired
	OfficeDao officeDao;

	public PersonnelRetreat get(String id) {
		return super.get(id);
	}

	public List<PersonnelRetreat> findList(PersonnelRetreat personnelRetreat) {
		return super.findList(personnelRetreat);
	}

	public Page<PersonnelRetreat> findPage(Page<PersonnelRetreat> page, PersonnelRetreat personnelRetreat) {
		personnelRetreat.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelRetreat);
	}

	@Transactional(readOnly = false)
	public void save(PersonnelRetreat personnelRetreat) {
		if (personnelRetreat.getStatus()==null||"".equals(personnelRetreat.getStatus())){
			personnelRetreat.setStatus("2");//未审核
		}
		super.save(personnelRetreat);
	}

	@Transactional(readOnly = false)
	public void delete(PersonnelRetreat personnelRetreat) {
		super.delete(personnelRetreat);
	}

	@Transactional(readOnly = false)
	public void shenHe(PersonnelRetreat personnelRetreat) {
		personnelRetreat.setUpdateDate(new Date());
		personnelRetreat.setShPerson(UserUtils.getUser().getName());
		personnelRetreatDao.shenHe(personnelRetreat);
	}

	/**
	 * 批量提交
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void submitByIds(List<String> ids){
		List<PersonnelRetreat> list = personnelRetreatDao.findByIds(ids);
		for (PersonnelRetreat personnelRetreat: list) {
			//过滤掉已经提交的数据
			if ("1".equals(personnelRetreat.getStatus())){
				//提交
				personnelRetreat.setStatus("2");//未审核
				super.save(personnelRetreat);
			}
		}
	}

	/**
	 * 离退休查询初始页面
	 * @param personnelRetreat
	 * @return
	 */
	public List<PersonnelRetreatSum> statistic(PersonnelRetreat personnelRetreat) {
		List<PersonnelRetreatSum> list = new ArrayList<PersonnelRetreatSum>();
		String id = UserUtils.getUser().getCompany().getId();
		if ("1".equals(id)){
			//局机关
			personnelRetreat.setNowUnitNameId("1");
			PersonnelRetreatSum juJiGuan = personnelRetreatDao.findByNowNameUnitId(personnelRetreat);
			juJiGuan.setUnitName("局机关");
			juJiGuan.setUnitId("1");
			list.add(juJiGuan);
			//南宁公安处
			personnelRetreat.setNowUnitNameId("34");
			PersonnelRetreatSum nanNingChu = personnelRetreatDao.findByNowNameUnitId(personnelRetreat);
			nanNingChu.setUnitName("南宁公安处");
			nanNingChu.setUnitId("34");
			list.add(nanNingChu);
			//柳州公安处
			personnelRetreat.setNowUnitNameId("95");
			PersonnelRetreatSum liuZhouChu = personnelRetreatDao.findByNowNameUnitId(personnelRetreat);
			liuZhouChu.setUnitName("柳州公安处");
			liuZhouChu.setUnitId("95");
			list.add(liuZhouChu);
			//北海公安处
			personnelRetreat.setNowUnitNameId("156");
			PersonnelRetreatSum beihaiChu = personnelRetreatDao.findByNowNameUnitId(personnelRetreat);
			beihaiChu.setUnitName("北海公安处");
			beihaiChu.setUnitId("156");
			list.add(beihaiChu);
		}else if("34".equals(id)){
			//南宁公安处
			personnelRetreat.setNowUnitNameId("34");
			PersonnelRetreatSum nanNingChu = personnelRetreatDao.findByNowNameUnitId(personnelRetreat);
			nanNingChu.setUnitName("南宁公安处");
			nanNingChu.setUnitId("34");
			list.add(nanNingChu);
		}else if("95".equals(id)){
			//柳州公安处
			personnelRetreat.setNowUnitNameId("95");
			PersonnelRetreatSum liuZhouChu = personnelRetreatDao.findByNowNameUnitId(personnelRetreat);
			liuZhouChu.setUnitName("柳州公安处");
			liuZhouChu.setUnitId("95");
			list.add(liuZhouChu);
		}else{
			//北海公安处
			personnelRetreat.setNowUnitNameId("156");
			PersonnelRetreatSum beihaiChu = personnelRetreatDao.findByNowNameUnitId(personnelRetreat);
			beihaiChu.setUnitName("北海公安处");
			beihaiChu.setUnitId("156");
			list.add(beihaiChu);
		}
		return list;
	}

	public List<PersonnelRetreatSum> findByUnitId(PersonnelRetreatSum personnelRetreatSum, PersonnelRetreat personnelRetreat) {
		List<PersonnelRetreatSum> list = new ArrayList<PersonnelRetreatSum>();
		List<Office> child = officeDao.findChildById(personnelRetreatSum.getUnitId());
		for (Office office : child) {
			personnelRetreat.setNowUnitNameId(office.getId());
			PersonnelRetreatSum statistic = personnelRetreatDao.findByNowNameUnitId(personnelRetreat);
			statistic.setUnitName(office.getName());
			statistic.setUnitId(office.getId());
			list.add(statistic);
		}
		return list;
	}

	/**
	 * 上传离退休信息
	 * @param personnelRetreat
	 */
	@Transactional(readOnly = false)
	public void upload(PersonnelRetreat personnelRetreat) {
		personnelRetreat.setStatus("2");//目前personnel_retreat_status字典值如下,1：未提交，2：未审核,3:未通过，4：通过
		super.save(personnelRetreat);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}