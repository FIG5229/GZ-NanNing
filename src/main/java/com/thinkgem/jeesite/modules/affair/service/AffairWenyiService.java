/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairWenyiDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairWenyi;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 文艺作品Service
 * @author kevin.jia
 * @version 2020-08-03
 */
@Service
@Transactional(readOnly = true)
public class AffairWenyiService extends CrudService<AffairWenyiDao, AffairWenyi> {
	@Autowired
	AffairWenyiDao affairWenyiDao;

	public AffairWenyi get(String id) {
		return super.get(id);
	}
	
	public List<AffairWenyi> findList(AffairWenyi affairWenyi) {
		return super.findList(affairWenyi);
	}
	
	public Page<AffairWenyi> findPage(Page<AffairWenyi> page, AffairWenyi affairWenyi) {
		affairWenyi.setUserId(UserUtils.getUser().getId());
		affairWenyi.setOfficeId(UserUtils.getUser().getOffice().getId());
		return super.findPage(page, affairWenyi);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairWenyi affairWenyi) {
		affairWenyi.setProContent(StringEscapeUtils.unescapeHtml4(affairWenyi.getProContent()));
		super.save(affairWenyi);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairWenyi affairWenyi) {
		super.delete(affairWenyi);
	}

    public List<AffairWenyi> findByIds(List<String> ids) {
		return affairWenyiDao.findByIds(ids);
    }

	/**
	 * 统计分析 文艺作品
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
    public List<Map<String, String>> countLiteraryWorks(String dateType, Integer year, String dateStart, String dateEnd, String month) {
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
		String parentId = UserUtils.getUser().getOffice().getParentId();
		if (parentId.equals("1")){
			List<Map<String, String>> list = dao.countJuLiteraryWorks(officeId,year,startDate,endDate,month);
			return list;
		}else {
			List<Map<String, String>> list = dao.countChuLiteraryWorks(officeId,year,startDate,endDate,month);
			return list;
		}
    }

	/**
	 * 统计分析 文艺工作明细
	 * @param page
	 * @param affairWenyi
	 * @return
	 */
	public Page<AffairWenyi> findLiteraryWorkPage(Page<AffairWenyi> page, AffairWenyi affairWenyi) {
		String dateType = Optional.ofNullable(affairWenyi.getDateType()).orElseGet(() -> {return "";});
		if (dateType.equals("1")){	//月度查询
			affairWenyi.setYear(null);
			affairWenyi.setStartDate(null);
			affairWenyi.setEndDate(null);
		}else if (dateType.equals("3")){	//时间段查询
			affairWenyi.setYear(null);
			affairWenyi.setStartDate(DateUtils.parseDate(affairWenyi.getDateStart()));
			affairWenyi.setEndDate(DateUtils.parseDate(affairWenyi.getDateEnd()));
			affairWenyi.setMonth(null);
		}else {	//年度查询
			affairWenyi.setMonth(null);
			affairWenyi.setStartDate(null);
			affairWenyi.setEndDate(null);
		}

		if (affairWenyi.getRatifyUnit().contains("北海处")){
			affairWenyi.setUnitId("156");
		}else if (affairWenyi.getRatifyUnit().contains("柳州处")){
			affairWenyi.setUnitId("95");
		}else if (affairWenyi.getRatifyUnit().contains("南宁处")){
			affairWenyi.setUnitId("34");
		}else {
			affairWenyi.setUnitId("top");
		}
		/*switch (affairWenyi.getRatifyUnit()){
			case "北海处":
				affairWenyi.setUnitId("156");
				break;
			case "柳州处":
				affairWenyi.setUnitId("95");
				break;
			case "南宁处":
				affairWenyi.setUnitId("34");
				break;
			default:
				affairWenyi.setUnitId("top");
				break;
		}*/

		affairWenyi.setPage(page);
		page.setList(dao.findLiteraryWorkList(affairWenyi));
		return page;
	}

}