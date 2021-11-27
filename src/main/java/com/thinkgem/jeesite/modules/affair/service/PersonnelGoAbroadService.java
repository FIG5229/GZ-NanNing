/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.PersonnelGoAbroadDao;
import com.thinkgem.jeesite.modules.affair.entity.PersonnelGoAbroad;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 领导干部出国管理表Service
 * @author mason.xv
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class PersonnelGoAbroadService extends CrudService<PersonnelGoAbroadDao, PersonnelGoAbroad> {

	public PersonnelGoAbroad get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelGoAbroad> findList(PersonnelGoAbroad personnelGoAbroad) {
		return super.findList(personnelGoAbroad);
	}
	
	public Page<PersonnelGoAbroad> findPage(Page<PersonnelGoAbroad> page, PersonnelGoAbroad personnelGoAbroad) {
		personnelGoAbroad.setCreateBy(UserUtils.getUser());
		personnelGoAbroad.setUserId(UserUtils.getUser().getOffice().getId());
		personnelGoAbroad.setCardNum(UserUtils.getUser().getId());
		Pattern pattern = Pattern.compile("^-?[0-9]+");
		boolean matches = pattern.matcher(UserUtils.getUser().getLoginName()).matches();
		if (matches){
			personnelGoAbroad.setLoginName(matches);
		}
//		personnelGoAbroad.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelGoAbroad);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelGoAbroad personnelGoAbroad) {
		super.save(personnelGoAbroad);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelGoAbroad personnelGoAbroad) {
		super.delete(personnelGoAbroad);
	}
	//获取今日回国人员
	public List<PersonnelGoAbroad> getTodayList(String returnDate) {
		return dao.getTodayList(returnDate);
	}
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}