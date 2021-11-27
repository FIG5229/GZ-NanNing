/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelSkillDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelSkill;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelTalents;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 专长信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelSkillService extends CrudService<PersonnelSkillDao, PersonnelSkill> {

	@Autowired
	private PersonnelSkillDao personnelSkillDao;

	public PersonnelSkill get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelSkill> findList(PersonnelSkill personnelSkill) {
		return super.findList(personnelSkill);
	}
	
	public Page<PersonnelSkill> findPage(Page<PersonnelSkill> page, PersonnelSkill personnelSkill) {
		personnelSkill.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelSkill);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelSkill personnelSkill) {
		super.save(personnelSkill);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelSkill personnelSkill) {
		super.delete(personnelSkill);
	}

	public PersonnelSkill selectIdNumber(String id){
		return personnelSkillDao.selectIdNumber(id);
	}

	public PersonnelSkill selectBean(String id){
		return personnelSkillDao.selectBean(id);
	}

	/*public List<PersonnelTalents> findRenCaiList(){
		return personnelSkillDao.findRenCaiList();
	}*/


	public Page<PersonnelTalents> findRenCaiList(Page<PersonnelTalents> page,PersonnelTalents personnelTalents){
		List<PersonnelTalents> mapList = personnelSkillDao.findRenCaiList(personnelTalents);
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		int count = mapList.size();
		List<PersonnelTalents> partMapList;
		if(pageSize*pageNo<count){
			partMapList = mapList.subList((pageNo-1)*pageSize,pageSize*pageNo);
		}else{
			partMapList = mapList.subList((pageNo-1)*pageSize,count);
		}
		page.setCount(count);
		page.setList(partMapList);
		//page.setFuncName("complexSelPageChange");
		return page;
	}
	//专长情况 - 统计分析
	public List<Map<String,Object>> findSpecialityInfo(String unitId){
		List<Map<String,Object>> mapList = personnelSkillDao.findSpecialityCount(unitId);
		return mapList;
	}
	//专长情况 - 统计分析 - 查看详情
	public Page<PersonnelTalents> findSpecialityList(Page<PersonnelTalents> page, PersonnelTalents personnelTalents) {
		personnelTalents.setPage(page);
		List<PersonnelTalents> list = personnelSkillDao.findSpecialityList(personnelTalents);
		page.setList(list);
		return page;
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumber(String idNumber) {
		dao.deleteByIdNumber(idNumber);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}