/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelXueliDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelXueli;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 学历信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelXueliService extends CrudService<PersonnelXueliDao, PersonnelXueli> {

	public PersonnelXueli get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelXueli> findList(PersonnelXueli personnelXueli) {
		return super.findList(personnelXueli);
	}
	
	public Page<PersonnelXueli> findPage(Page<PersonnelXueli> page, PersonnelXueli personnelXueli) {
		personnelXueli.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelXueli);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelXueli personnelXueli) {
		String name = personnelXueli.getName();
		if (name.contains("小学")){
			personnelXueli.setLevel(1);
		}else if (name.contains("初中")){
			personnelXueli.setLevel(2);
		}else if (name.contains("中专") || name.contains("高中")){
			personnelXueli.setLevel(3);
		}else if (name.contains("专科")){
			personnelXueli.setLevel(4);
		}else if (name.contains("本科")){
			personnelXueli.setLevel(5);
		}else if (name.contains("硕士")){
			personnelXueli.setLevel(6);
		}else if (name.contains("博士")){
			personnelXueli.setLevel(7);
		}else {
			personnelXueli.setLevel(0);
		}
		super.save(personnelXueli);
	}


	/**
	 * 查询最高学历
	 * @param personnelXueli
	 * @return List<PersonnelXueli>
	 */
	public List<PersonnelXueli> findLastXueLiInfo(PersonnelXueli personnelXueli){
		return dao.findLastXueLiInfo(personnelXueli);
	}

	@Transactional(readOnly = false)
	public void delete(PersonnelXueli personnelXueli) {
		super.delete(personnelXueli);
	}


	/**
	 * 查询最高学历
	 * @param personnelXueli
	 * @return PersonnelXueli
	 */
	public PersonnelXueli getLastByIdNumber(PersonnelXueli personnelXueli) {
		List<PersonnelXueli> personnelXueliList = dao.findLastByIdNumber(personnelXueli);
		if(personnelXueliList!=null || personnelXueliList.size()>0){
			if(personnelXueliList.size()==1){
				personnelXueli = personnelXueliList.get(0);
			}else{
				personnelXueli = null;
				for(PersonnelXueli xueli : personnelXueliList){
					if(personnelXueli == null){
						personnelXueli = xueli;
					}else{
						Date endDate = personnelXueli.getEndDate();
						Date endDate1 = xueli.getEndDate();
						if(endDate.compareTo(endDate1)==-1){
							//date1.compareTo(date2)
							//date1小于date2返回-1，date1大于date2返回1，相等返回0
							personnelXueli = xueli;
						}
					}
				}
			}
		}else{
			personnelXueli = null;
		}

		if (personnelXueli == null){
			personnelXueli = new PersonnelXueli();
		}
		return personnelXueli;
	}

	public List<Map<String, String>> countFullTimeEducation(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
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
		return dao.countFullTimeEducation(id,null,startDate,endDate,month);
	}

    public List<Map<String, String>> countMaxEducation(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
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
		return dao.countMaxEducation(id,year,startDate,endDate,month);
    }

    public Page<PersonnelXueli> findFullTimePage(Page<PersonnelXueli> page, PersonnelXueli personnelXueli) {
		personnelXueli.setPage(page);
		page.setList(dao.findFullTimeList(personnelXueli));
		return page;
    }

	public Page<PersonnelXueli> findMaxPage(Page<PersonnelXueli> page, PersonnelXueli personnelXueli) {
		personnelXueli.setPage(page);
		page.setList(dao.findMaxList(personnelXueli));
		return page;
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}