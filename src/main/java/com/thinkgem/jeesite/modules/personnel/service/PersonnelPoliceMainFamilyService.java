/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPoliceMainFamilyDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelFamily;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceFamilyInfo;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceFamilyTwo;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceMainFamily;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.warn.entity.Warning;
import com.thinkgem.jeesite.modules.warn.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 民警家庭Service
 * @author cecil.li
 * @version 2020-11-04
 */
@Service
@Transactional(readOnly = true)
public class PersonnelPoliceMainFamilyService extends CrudService<PersonnelPoliceMainFamilyDao, PersonnelPoliceMainFamily> {

	@Autowired
	private PersonnelPoliceFamilyInfoService personnelPoliceFamilyInfoService;

	@Autowired
	private PersonnelPoliceMainFamilyDao personnelPoliceMainFamilyDao;

	@Autowired
	private PersonnelFamilyService personnelFamilyService;

	@Autowired
	private WarningService warningService;

	public PersonnelPoliceMainFamily get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelPoliceMainFamily> findList(PersonnelPoliceMainFamily personnelPoliceMainFamily) {
		return super.findList(personnelPoliceMainFamily);
	}
	
	public Page<PersonnelPoliceMainFamily> findPage(Page<PersonnelPoliceMainFamily> page, PersonnelPoliceMainFamily personnelPoliceMainFamily) {
		String userIsNull = this.findUserIsNull(UserUtils.getUser().getNo());//判断当前用户为民警还是单位账号
		if(userIsNull!=null){
			//当前用户为民警账号，民警账号只能本人信息
			personnelPoliceMainFamily.setIdNumber(userIsNull);
			personnelPoliceMainFamily.setUserId(UserUtils.getUser().getId());
		}else{
			//单位账号，可以查看本单位下人员信息
			personnelPoliceMainFamily.setUserId(UserUtils.getUser().getId());
			personnelPoliceMainFamily.setOfficeId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, personnelPoliceMainFamily);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelPoliceMainFamily personnelPoliceMainFamily) {
		if (personnelPoliceMainFamily.getPersonnelPoliceFamilyInfoList() != null && personnelPoliceMainFamily.getPersonnelPoliceFamilyInfoList().size() > 0){
			for (PersonnelPoliceFamilyInfo personnelPoliceFamilyInfo : personnelPoliceMainFamily.getPersonnelPoliceFamilyInfoList()){
				PersonnelPoliceFamilyInfo p = new PersonnelPoliceFamilyInfo();
				p.setPfId(personnelPoliceMainFamily.getId());
				p.setName(personnelPoliceFamilyInfo.getName());
				p.setIdNumber(personnelPoliceFamilyInfo.getIdNumber());
				p.setRelationship(personnelPoliceFamilyInfo.getRelationship());
				p.setSex(personnelPoliceFamilyInfo.getSex());
				p.setBirthday(personnelPoliceFamilyInfo.getBirthday());
				p.setPoliticalStatus(personnelPoliceFamilyInfo.getPoliticalStatus());
				p.setStatusQuo(personnelPoliceFamilyInfo.getStatusQuo());
				p.setJob(personnelPoliceFamilyInfo.getJob());
				p.setSteer(personnelPoliceFamilyInfo.getSteer());
				p.setNationality(personnelPoliceFamilyInfo.getNationality());
				p.setNation(personnelPoliceFamilyInfo.getNation());
				p.setEducation(personnelPoliceFamilyInfo.getEducation());
				p.setIdentity(personnelPoliceFamilyInfo.getIdentity());
				p.setPosition(personnelPoliceFamilyInfo.getPosition());
				p.setJobLevel(personnelPoliceFamilyInfo.getJobLevel());
				p.setContact(personnelPoliceFamilyInfo.getContact());
				p.setAddress(personnelPoliceFamilyInfo.getAddress());
				p.setRemark(personnelPoliceFamilyInfo.getRemark());
				personnelPoliceFamilyInfoService.save(p);
			}
		}
		super.save(personnelPoliceMainFamily);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelPoliceMainFamily personnelPoliceMainFamily) {
		super.delete(personnelPoliceMainFamily);
	}

	@Transactional(readOnly = false)
	public void saveInfo(PersonnelPoliceMainFamily personnelPoliceMainFamily) {
		if (personnelPoliceMainFamily.getPersonnelPoliceFamilyInfoList() != null && personnelPoliceMainFamily.getPersonnelPoliceFamilyInfoList().size() > 0){
			for (PersonnelPoliceFamilyInfo personnelPoliceFamilyInfo : personnelPoliceMainFamily.getPersonnelPoliceFamilyInfoList()){
				PersonnelPoliceFamilyInfo p = new PersonnelPoliceFamilyInfo();
				p.setId(personnelPoliceFamilyInfo.getId());
				p.setPfId(personnelPoliceMainFamily.getId());
				p.setName(personnelPoliceFamilyInfo.getName());
				p.setIdNumber(personnelPoliceFamilyInfo.getIdNumber());
				p.setRelationship(personnelPoliceFamilyInfo.getRelationship());
				p.setSex(personnelPoliceFamilyInfo.getSex());
				p.setBirthday(personnelPoliceFamilyInfo.getBirthday());
				p.setPoliticalStatus(personnelPoliceFamilyInfo.getPoliticalStatus());
				p.setStatusQuo(personnelPoliceFamilyInfo.getStatusQuo());
				p.setJob(personnelPoliceFamilyInfo.getJob());
				p.setSteer(personnelPoliceFamilyInfo.getSteer());
				p.setNationality(personnelPoliceFamilyInfo.getNationality());
				p.setNation(personnelPoliceFamilyInfo.getNation());
				p.setEducation(personnelPoliceFamilyInfo.getEducation());
				p.setIdentity(personnelPoliceFamilyInfo.getIdentity());
				p.setPosition(personnelPoliceFamilyInfo.getPosition());
				p.setJobLevel(personnelPoliceFamilyInfo.getJobLevel());
				p.setContact(personnelPoliceFamilyInfo.getContact());
				p.setAddress(personnelPoliceFamilyInfo.getAddress());
				p.setRemark(personnelPoliceFamilyInfo.getRemark());
				personnelPoliceFamilyInfoService.save(p);
			}
		}
		personnelPoliceMainFamilyDao.saveInfo(personnelPoliceMainFamily);
	}

	public String findUserIsNull(String id) {
		return personnelPoliceMainFamilyDao.findUserIsNull(id);
	}

	/**
	 * 批量提交
	 * @param ids
	 */
	public List<PersonnelPoliceMainFamily> findByIds(List<String> ids){
		List<PersonnelPoliceMainFamily> list = personnelPoliceMainFamilyDao.findByIds(ids);
		return list;
	}
	public String findCheckType(String id) {
		return personnelPoliceMainFamilyDao.findCheckType(id);
	}

	public List<PersonnelPoliceFamilyInfo> getSonTable(String id) {
		return personnelPoliceMainFamilyDao.getSonTable(id);
	}

	public List<PersonnelFamily> findIsPersonel(String idNumber) {
		return personnelPoliceMainFamilyDao.findIsPersonel(idNumber);
	}
	//创建民警家庭预警
	@Transactional(readOnly = false)
	public void creatPoliceFamilyWarning(String userName ,String idNumber, List<String> pfIdList,String unitCheckId) {
		try {
			List<PersonnelFamily> personnelFamilyList = personnelFamilyService.findListByIdNumber(idNumber);
			StringBuffer oldFamilyMember = null;//旧家庭成员
			for(PersonnelFamily personnelFamily : personnelFamilyList){
				if(oldFamilyMember == null){
					oldFamilyMember = new StringBuffer(personnelFamily.familyMemberInfo());
				}else{
					oldFamilyMember.append(";"+personnelFamily.familyMemberInfo());
				}
			}
			StringBuffer newFamilyMember = null;//新家庭成员
			for(String pfId : pfIdList){
				List<PersonnelPoliceFamilyInfo> policeFamilyInfoList = personnelPoliceFamilyInfoService.getListByPfId(pfId);
				for(PersonnelPoliceFamilyInfo personnelPoliceFamilyInfo : policeFamilyInfoList){
					if(newFamilyMember == null){
						newFamilyMember = new StringBuffer(personnelPoliceFamilyInfo.getFamilyMemberInfo());
					}else{
						newFamilyMember.append(";"+personnelPoliceFamilyInfo.getFamilyMemberInfo());
					}
				}
			}
			if(newFamilyMember!=null || oldFamilyMember!=null){
				Warning warning = new Warning();
				warning.setName("民警家庭提交审核预警");
				warning.setDate(new Date());
				warning.setContinueDay("0");//持续时间
				warning.setRepeatCycle("4");//周期
				warning.setIsAlert("1");//使用弹窗
				warning.setAlertDegree("1");//紧急程度
				warning.setRemind("20");//重复提醒
				//设置创建人，修改人
				User creatUser = new User();
				creatUser.setId("1");
				Office office = new Office();
				office.setId("1");
				creatUser.setOffice(office);
				warning.setCreateBy(creatUser);
				warning.setUpdateBy(creatUser);
				if(newFamilyMember!=null && oldFamilyMember==null){
					warning.setAlertContent(userName+"于"+ DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm")+"提交民警家庭信息，请及时审核。未从系统中发现已存储的家庭信息<br>"+"本次提交民警家庭信息："+newFamilyMember.toString());
				}
				if(oldFamilyMember!=null && newFamilyMember==null){
					warning.setAlertContent(userName+"于"+ DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm")+"提交民警家庭信息，请及时审核。系统存储信息："+oldFamilyMember.toString()+"<br>"+"本次提交民警家庭信息为空");
				}
				if(newFamilyMember != null && oldFamilyMember != null){
					warning.setAlertContent(userName+"于"+ DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm")+"提交民警家庭信息，请及时审核。系统存储信息："+oldFamilyMember.toString()+"<br>"+"本次提交民警家庭信息："+newFamilyMember.toString());
				}
				warning.setReceivePerId(unitCheckId);
				warning.setReceivePerName(UserUtils.get(unitCheckId).getName());
				warningService.save(warning);
			}
		}catch (Exception e){
			logger.error("创建民警家庭预警，发生错误"+e.getCause().toString());
			e.printStackTrace();
		}

	}
	@Transactional(readOnly = false)
	public void revocation(String id, String checkType) {
		personnelPoliceMainFamilyDao.revocation(id,checkType);
	}
}