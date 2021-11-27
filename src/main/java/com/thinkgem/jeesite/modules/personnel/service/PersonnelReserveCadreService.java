/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelReserveCadreDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelReserveCadre;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后备干部信息集Service
 * @author cecil.li
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class PersonnelReserveCadreService extends CrudService<PersonnelReserveCadreDao, PersonnelReserveCadre> {

	public PersonnelReserveCadre get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelReserveCadre> findList(PersonnelReserveCadre personnelReserveCadre) {
		return super.findList(personnelReserveCadre);
	}
	
	public Page<PersonnelReserveCadre> findPage(Page<PersonnelReserveCadre> page, PersonnelReserveCadre personnelReserveCadre) {
		personnelReserveCadre.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelReserveCadre);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelReserveCadre personnelReserveCadre) {
		super.save(personnelReserveCadre);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelReserveCadre personnelReserveCadre) {
		super.delete(personnelReserveCadre);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}