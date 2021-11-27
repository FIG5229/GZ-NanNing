/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairYouthTalentDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairYouthTalent;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 青年人才库Service
 * @author cecil.li
 * @version 2019-11-19
 */
@Service
@Transactional(readOnly = true)
public class AffairYouthTalentService extends CrudService<AffairYouthTalentDao, AffairYouthTalent> {

	@Autowired
	AffairYouthTalentDao affairYouthTalentDao;
	@Autowired
	private PersonnelBaseService personnelBaseService;

	public AffairYouthTalent get(String id) {
		return super.get(id);
	}
	
	public List<AffairYouthTalent> findList(AffairYouthTalent affairYouthTalent) {
		return super.findList(affairYouthTalent);
	}
	
	public Page<AffairYouthTalent> findPage(Page<AffairYouthTalent> page, AffairYouthTalent affairYouthTalent) {
		affairYouthTalent.setUserOffice((UserUtils.getUser().getOffice().getId()));
		User user = UserUtils.getUser();
		affairYouthTalent.setCreateBy(user);
		affairYouthTalent.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairYouthTalent);
	}

	@Transactional(readOnly = false)
	public void save(AffairYouthTalent affairYouthTalent) {
		super.save(affairYouthTalent);
		/*PersonnelBase personnelBase = new PersonnelBase();
		//创建一个不带任何信息的容器
		AffairYouthTalent affairYouthTalent0 = new AffairYouthTalent();
		//根据身份证号查重,一旦重复就删除数据库内数据,从新添加
		List<AffairYouthTalent> list= affairYouthTalentDao.findAllList(affairYouthTalent0);
		if (list.size() == 0 ){
			super.save(affairYouthTalent);

			personnelBase.setIdNumber(affairYouthTalent.getIdNumber());
			personnelBase.setPoliceIdNumber(affairYouthTalent.getPoliceNo());
			personnelBase.setName(affairYouthTalent.getName());
			personnelBase.setBirthday(affairYouthTalent.getBirthday());
			personnelBase.setWorkunitName(affairYouthTalent.getUnit());
			personnelBaseService.save(personnelBase);
		}else{
			AffairYouthTalent affairYouthTalentSame= findSame(affairYouthTalent, list);
			if(affairYouthTalentSame != null){
				affairYouthTalent.setId(affairYouthTalentSame.getId());
				super.save(affairYouthTalent);

				personnelBase.setIdNumber(affairYouthTalent.getIdNumber());
				personnelBase.setPoliceIdNumber(affairYouthTalent.getPoliceNo());
				personnelBase.setName(affairYouthTalent.getName());
				personnelBase.setBirthday(affairYouthTalent.getBirthday());
				personnelBase.setWorkunitName(affairYouthTalent.getUnit());
				personnelBaseService.save(personnelBase);
			}
			else {
				//这个地方要加友好提示
				personnelBase.setIdNumber(affairYouthTalent.getIdNumber());
				personnelBase.setPoliceIdNumber(affairYouthTalent.getPoliceNo());
				personnelBase.setName(affairYouthTalent.getName());
				personnelBase.setBirthday(affairYouthTalent.getBirthday());
				personnelBase.setWorkunitName(affairYouthTalent.getUnit());
				super.save(affairYouthTalent);
				personnelBaseService.save(personnelBase);
			}*/
		}

	/*	AffairYouthTalent affairYouthTalentFromDb = affairYouthTalentDao.findInfoByIdNumber(affairYouthTalent.getIdNumber());
		PersonnelBase personnelBase = new PersonnelBase();
		if(StringUtils.isNotBlank(affairYouthTalentFromDb.getId())){
			personnelBase.setIdNumber(affairYouthTalent.getIdNumber());
			personnelBase.setPoliceIdNumber(affairYouthTalent.getPoliceNo());
			personnelBase.setName(affairYouthTalent.getName());
			personnelBase.setBirthday(affairYouthTalent.getBirthday());
			personnelBase.setWorkunitName(affairYouthTalent.getUnit());
			personnelBaseService.save(personnelBase);
			affairYouthTalent.setId(affairYouthTalentFromDb.getId());
			super.save(affairYouthTalent);
		}else{
			personnelBase.setIdNumber(affairYouthTalent.getIdNumber());
			personnelBase.setPoliceIdNumber(affairYouthTalent.getPoliceNo());
			personnelBase.setName(affairYouthTalent.getName());
			personnelBase.setBirthday(affairYouthTalent.getBirthday());
			personnelBase.setWorkunitName(affairYouthTalent.getUnit());
			personnelBaseService.save(personnelBase);
			super.save(affairYouthTalent);
		}

	}*/
	/*private AffairYouthTalent findSame(AffairYouthTalent affairYouthTalent, List<AffairYouthTalent> list){
		AffairYouthTalent result = null;
		for(AffairYouthTalent affairYouthTalent1 : list) {
			if (affairYouthTalent1.getIdNumber().equals(affairYouthTalent.getIdNumber())) {
				result= affairYouthTalent1;
				break;
			}
		}
		return  result;
	}*/
	@Transactional(readOnly = false)
	public void delete(AffairYouthTalent affairYouthTalent) {
		super.delete(affairYouthTalent);
	}

	public List<AffairYouthTalent> selectSpeciality(String idNumber){
		return affairYouthTalentDao.selectSpeciality(idNumber);
	}

}
/*          */