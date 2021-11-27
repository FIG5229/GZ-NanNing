/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelXieguanCadreDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelXieguanCadre;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 协管干部信息集Service
 * @author cecil.li
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class PersonnelXieguanCadreService extends CrudService<PersonnelXieguanCadreDao, PersonnelXieguanCadre> {

	public PersonnelXieguanCadre get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelXieguanCadre> findList(PersonnelXieguanCadre personnelXieguanCadre) {
		return super.findList(personnelXieguanCadre);
	}
	
	public Page<PersonnelXieguanCadre> findPage(Page<PersonnelXieguanCadre> page, PersonnelXieguanCadre personnelXieguanCadre) {
		personnelXieguanCadre.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelXieguanCadre);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelXieguanCadre personnelXieguanCadre) {
		super.save(personnelXieguanCadre);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelXieguanCadre personnelXieguanCadre) {
		super.delete(personnelXieguanCadre);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}