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
import com.thinkgem.jeesite.modules.affair.dao.AffairTypicalTeamChildDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairTypicalTeamVisitDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTypicalTeamChild;
import com.thinkgem.jeesite.modules.affair.entity.AffairTypicalTeamVisit;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairTypicalTeam;
import com.thinkgem.jeesite.modules.affair.dao.AffairTypicalTeamDao;

/**
 * 典型集体Service
 * @author daniel.liu
 * @version 2020-06-16
 */
@Service
@Transactional(readOnly = true)
public class AffairTypicalTeamService extends CrudService<AffairTypicalTeamDao, AffairTypicalTeam> {

	@Autowired
	private AffairTypicalTeamVisitDao affairTypicalTeamChildDao;

	public AffairTypicalTeam get(String id) {
		return super.get(id);
	}
	
	public List<AffairTypicalTeam> findList(AffairTypicalTeam affairTypicalTeam) {
		return super.findList(affairTypicalTeam);
	}
	
	public Page<AffairTypicalTeam> findPage(Page<AffairTypicalTeam> page, AffairTypicalTeam affairTypicalTeam) {
/*
		affairTypicalTeam.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
*/
		affairTypicalTeam.setCreateBy(UserUtils.getUser());
		if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
			affairTypicalTeam.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairTypicalTeam.setUserId(UserUtils.getUser().getOffice().getId());
		}


		return super.findPage(page, affairTypicalTeam);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTypicalTeam affairTypicalTeam) {
		affairTypicalTeam.setVisitRecord(StringEscapeUtils.unescapeHtml4(affairTypicalTeam.getVisitRecord()));
		super.save(affairTypicalTeam);

		//遍历事迹材料保存
		if (affairTypicalTeam.getTypicalTeamChildList()!=null){
			for (AffairTypicalTeamVisit item : affairTypicalTeam.getTypicalTeamChildList()) {
				if (item.getId() == null) {
					continue;
				}
				if (AffairTypicalTeamVisit.DEL_FLAG_NORMAL.equals(item.getDelFlag())) {
					if (StringUtils.isBlank(item.getId())) {
						item.setTypicalTeamId(affairTypicalTeam.getId());
						item.preInsert();
						affairTypicalTeamChildDao.insert(item);
					} else {
						item.preUpdate();
						affairTypicalTeamChildDao.update(item);
					}
				} else {
					affairTypicalTeamChildDao.delete(item);
				}
			}
		}

	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTypicalTeam affairTypicalTeam) {
		super.delete(affairTypicalTeam);
	}

	/**
	 * 统计分析	典型集体分析
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
    public List<Map<String, String>> countTypicalTeam(String dateType, Integer year, String dateStart, String dateEnd, String month) {
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
		List<Map<String, String>> list = dao.countTypicalTeam(officeId,year,startDate,endDate,month);
		return ChartLabelUtils.fillLabel(list, DictUtils.getDictList("affair_approval_unitLevel"));
	}

	/**
	 * 统计分析	统计各级典型人物		明细
	 * @param page
	 * @param affairTypicalTeam
	 * @return
	 */
	public Page<AffairTypicalTeam> findTypicalDetailPage(Page<AffairTypicalTeam> page, AffairTypicalTeam affairTypicalTeam) {

		String officeId = UserUtils.getUser().getCompany().getId();
		affairTypicalTeam.setUnitId(officeId);
		String dateType = Optional.ofNullable(affairTypicalTeam.getDateType()).orElseGet(() -> {return "";});
		switch (dateType) {
			case "1":    //月度
				affairTypicalTeam.setYear(null);
				affairTypicalTeam.setBeginTime(null);
				affairTypicalTeam.setEndTime(null);
				break;
			case "3":    //时间段
				affairTypicalTeam.setYear(null);
				affairTypicalTeam.setBeginTime(DateUtils.parseDate(affairTypicalTeam.getDateStart()));
				affairTypicalTeam.setEndTime(DateUtils.parseDate(affairTypicalTeam.getDateEnd()));
				affairTypicalTeam.setMonth(null);
				break;
			default:    //年度
				affairTypicalTeam.setMonth(null);
				affairTypicalTeam.setBeginTime(null);
				affairTypicalTeam.setEndTime(null);
				break;
		}
		affairTypicalTeam.setPage(page);
		page.setList(dao.findTypicalList(affairTypicalTeam));
		return page;
	}
}
