/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairActive;
import com.thinkgem.jeesite.modules.affair.dao.AffairActiveDao;

/**
 * 活动情况Service
 * @author alan.wu
 * @version 2020-07-27
 */
@Service
@Transactional(readOnly = true)
public class AffairActiveService extends CrudService<AffairActiveDao, AffairActive> {

	@Autowired
	private AffairActiveDao affairActiveDao;

	@Autowired
	private OfficeService officeService;

	public AffairActive get(String id) {
		return super.get(id);
	}
	
	public List<AffairActive> findList(AffairActive affairActive) {
		return super.findList(affairActive);
	}
	
	public Page<AffairActive> findPage(Page<AffairActive> page, AffairActive affairActive) {
		affairActive.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, affairActive);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairActive affairActive) {
		super.save(affairActive);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairActive affairActive) {
		super.delete(affairActive);
	}

    public List<Map<String, String>> countReadBookActivity(String dateType, Integer year, String dateStart, String dateEnd, String month) {
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
		String officeId = UserUtils.getUser().getOffice().getId();
		String companyId = UserUtils.getUser().getCompany().getId();
		if (companyId.equals("1") && !officeId.equals("34") && !officeId.equals("156") && !officeId.equals("95") ){
			return dao.countJuReadActive(companyId,year,startDate,endDate,month);
		}else {
			return dao.countChuReadActive(companyId,year,startDate,endDate,month);
		}
    }

	public Page<AffairActive> findReadBookPage(Page<AffairActive> page, AffairActive affairActive) {
		String dateType = Optional.ofNullable(affairActive.getDateType()).orElseGet(() -> {return "";});
		if (dateType.equals("1")){	//月度查询
			affairActive.setYear(null);
			affairActive.setBeginTime(null);
			affairActive.setEndTime(null);
		}else if (dateType.equals("3")){	//时间段查询
			affairActive.setYear(null);
			affairActive.setBeginTime(DateUtils.parseDate(affairActive.getDateStart()));
			affairActive.setEndTime(DateUtils.parseDate(affairActive.getDateEnd()));
			affairActive.setMonth(null);
		}else {	//年度查询
			affairActive.setMonth(null);
			affairActive.setBeginTime(null);
			affairActive.setEndTime(null);
		}

		String officeId = UserUtils.getUser().getOffice().getId();
		String companyId = UserUtils.getUser().getCompany().getId();
		/*局账号查看明细*/
		if (companyId.equals("1") && !officeId.equals("34") && !officeId.equals("156") && !officeId.equals("95") ){
			String label = affairActive.getLabel();
			if (StringUtils.isNotBlank(label)){

				if (label.equals("局机关")){
					affairActive.setUnitId("top");
				}else if (label.equals("南宁处")){
					affairActive.setUnitId("34");
				}else if (label.equals("北海处")){
					affairActive.setUnitId("156");
				}else if (label.equals("柳州处")){
					affairActive.setUnitId("95");
				}
			}
		}else {
			/*处账号查看明细*/
			officeId = officeService.findByName(affairActive.getLabel());
			affairActive.setUnitId(officeId);
		}
		affairActive.setPage(page);
		return page.setList(dao.findReadingActivitiesList(affairActive));
	}

	public List<String> selectAllYear(){
		return affairActiveDao.selectAllYear();
	}

	public List<String> selectAllMonth(){
		return affairActiveDao.selectAllMonth();
	}

	public Integer selectNumber(String yearL,String yearT,String idNumber){
		return affairActiveDao.selectNumber(yearL,yearT,idNumber);
	}

	public List<String> selectAllName(){
		return affairActiveDao.selectAllName();
	}
}