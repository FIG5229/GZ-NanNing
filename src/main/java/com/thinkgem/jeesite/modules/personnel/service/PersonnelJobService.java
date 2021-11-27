/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelJobDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelJob;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 职务层次信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelJobService extends CrudService<PersonnelJobDao, PersonnelJob> {

	@Autowired
	private PersonnelJobDao personnelJobDao;

	public PersonnelJob get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelJob> findList(PersonnelJob personnelJob) {
		return super.findList(personnelJob);
	}
	
	public Page<PersonnelJob> findPage(Page<PersonnelJob> page, PersonnelJob personnelJob) {
		personnelJob.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelJob);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelJob personnelJob) {
		super.save(personnelJob);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelJob personnelJob) {
		super.delete(personnelJob);
	}

	/*
	 * 根据身份证号查询最新职务层次
	 * */
    public PersonnelJob findNewJobByIdNumber(String idNumber) {
		return personnelJobDao.findNewJobByIdNumber(idNumber);
    }
	//查询最新职务集合
	public List<Map<String,Object>> getNewestJobList() {
    	return personnelJobDao.getNewestJobList();
	}

	/**
	 * 职级统计分析
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	public List<Map<String, String>> countPersonnelJob(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
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
		return dao.countPersonnelJob(id,year,startDate,endDate,month);
	}

	public Page<PersonnelJob> findChartsPersonnelJobPage(Page<PersonnelJob> page, PersonnelJob personnelJob) {
		personnelJob.setPage(page);
		page.setList(dao.findChartsPersonnelJobList(personnelJob));
		return page;
	}

	@Transactional(readOnly = false)
    public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
    }
}