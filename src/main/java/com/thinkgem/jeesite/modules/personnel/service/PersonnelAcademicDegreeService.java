/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelAcademicDegreeDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelAcademicDegree;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 学位信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelAcademicDegreeService extends CrudService<PersonnelAcademicDegreeDao, PersonnelAcademicDegree> {

	public PersonnelAcademicDegree get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelAcademicDegree> findList(PersonnelAcademicDegree personnelAcademicDegree) {
		return super.findList(personnelAcademicDegree);
	}
	
	public Page<PersonnelAcademicDegree> findPage(Page<PersonnelAcademicDegree> page, PersonnelAcademicDegree personnelAcademicDegree) {
		personnelAcademicDegree.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelAcademicDegree);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelAcademicDegree personnelAcademicDegree) {
		String name = personnelAcademicDegree.getName();
		String explain = personnelAcademicDegree.getExplain();
		if (name.contains("学士")){
			personnelAcademicDegree.setLevel(1);
		}else if (name.contains("硕士")){
			personnelAcademicDegree.setLevel(2);
		}else if (name.contains("博士")){
			personnelAcademicDegree.setLevel(3);
		}else {
			personnelAcademicDegree.setLevel(0);
		}
		if (explain.contains("全日制")){
			personnelAcademicDegree.setType("1");
		}else if (explain.contains("在职")){
			personnelAcademicDegree.setType("2");
		}else {
			personnelAcademicDegree.setType("0");
		}
		super.save(personnelAcademicDegree);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelAcademicDegree personnelAcademicDegree) {
		super.delete(personnelAcademicDegree);
	}

	@Transactional(readOnly = false)
    public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
    }
}