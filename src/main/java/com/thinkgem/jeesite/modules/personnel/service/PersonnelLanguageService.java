/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelLanguageDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelLanguage;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 语言能力信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelLanguageService extends CrudService<PersonnelLanguageDao, PersonnelLanguage> {

	public PersonnelLanguage get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelLanguage> findList(PersonnelLanguage personnelLanguage) {
		return super.findList(personnelLanguage);
	}
	
	public Page<PersonnelLanguage> findPage(Page<PersonnelLanguage> page, PersonnelLanguage personnelLanguage) {
		personnelLanguage.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelLanguage);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelLanguage personnelLanguage) {
		super.save(personnelLanguage);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelLanguage personnelLanguage) {
		super.delete(personnelLanguage);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}