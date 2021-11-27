/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.thinkgem.jeesite.common.utils.ChartLabelUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairTypicalPersonNewsDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairTypicalPersonVisitDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTypicalPersonNews;
import com.thinkgem.jeesite.modules.affair.entity.AffairTypicalPersonVisit;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairTypicalPerson;
import com.thinkgem.jeesite.modules.affair.dao.AffairTypicalPersonDao;

/**
 * 典型人物Service
 * @author daniel.liu
 * @version 2020-06-16
 */
@Service
@Transactional(readOnly = true)
public class AffairTypicalPersonService extends CrudService<AffairTypicalPersonDao, AffairTypicalPerson> {

	@Autowired
	private AffairTypicalPersonVisitDao affairTypicalPersonNewsDao;

	public AffairTypicalPerson get(String id) {
		return super.get(id);
	}
	
	public List<AffairTypicalPerson> findList(AffairTypicalPerson affairTypicalPerson) {
		return super.findList(affairTypicalPerson);
	}
	
	public Page<AffairTypicalPerson> findPage(Page<AffairTypicalPerson> page, AffairTypicalPerson affairTypicalPerson) {
/*
		affairTypicalPerson.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
*/
		affairTypicalPerson.setCreateBy(UserUtils.getUser());
		if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
			affairTypicalPerson.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairTypicalPerson.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairTypicalPerson);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTypicalPerson affairTypicalPerson) {
		affairTypicalPerson.setVisitRecord(StringEscapeUtils.unescapeHtml4(affairTypicalPerson.getVisitRecord()));
		super.save(affairTypicalPerson);

		//遍历事迹材料保存
		if (affairTypicalPerson.getPersonNewsList()!=null){

			for (AffairTypicalPersonVisit item : affairTypicalPerson.getPersonNewsList()) {
				if (item.getId() == null) {
					continue;
				}
				if (AffairTypicalPersonNews.DEL_FLAG_NORMAL.equals(item.getDelFlag())) {
					if (StringUtils.isBlank(item.getId())) {
						item.setTypicalPersonId(affairTypicalPerson.getId());
						item.preInsert();
						affairTypicalPersonNewsDao.insert(item);
					} else {
						item.preUpdate();
						affairTypicalPersonNewsDao.update(item);
					}
				} else {
					affairTypicalPersonNewsDao.delete(item);
				}
			}
		}
	}

	@Transactional(readOnly = false)
	public void delete(AffairTypicalPerson affairTypicalPerson) {
		super.delete(affairTypicalPerson);
	}

	/**
	 * 统计分析 统计各级典型人物
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
    public List<Map<String,String>> countTypicalPerson(String dateType, Integer year, String dateStart, String dateEnd, String month) {
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
		String officeId = UserUtils.getUser().getCompany().getId();
		List<Map<String, String>> list = dao.countTypicalPerson(officeId,year,startDate,endDate,month);
		return ChartLabelUtils.fillLabel(list, DictUtils.getDictList("affair_approval_unitLevel"));
    }

	/**
	 * 统计分析	统计各级典型人物		明细
	 * @param page
	 * @param affairTypicalPerson
	 * @return
	 */
	public Page<AffairTypicalPerson> findTypicalDetailPage(Page<AffairTypicalPerson> page, AffairTypicalPerson affairTypicalPerson) {

		String officeId = UserUtils.getUser().getCompany().getId();
		affairTypicalPerson.setUnitId(officeId);
		String dateType = Optional.ofNullable(affairTypicalPerson.getDateType()).orElseGet(() -> {return "";});
		switch (dateType) {
			case "1":    //月度
				affairTypicalPerson.setYear(null);
				affairTypicalPerson.setBeginPsTime(null);
				affairTypicalPerson.setEndPsTime(null);
				break;
			case "3":    //时间段
				affairTypicalPerson.setYear(null);
				affairTypicalPerson.setBeginPsTime(DateUtils.parseDate(affairTypicalPerson.getDateStart()));
				affairTypicalPerson.setEndPsTime(DateUtils.parseDate(affairTypicalPerson.getDateEnd()));
				affairTypicalPerson.setMonth(null);
				break;
			default:    //年度
				affairTypicalPerson.setMonth(null);
				affairTypicalPerson.setBeginPsTime(null);
				affairTypicalPerson.setEndPsTime(null);
				break;
		}
		affairTypicalPerson.setPage(page);
		page.setList(dao.findTypicalList(affairTypicalPerson));
		return page;
	}
}