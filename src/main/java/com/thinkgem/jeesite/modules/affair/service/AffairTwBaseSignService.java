/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.ChartLabelUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairTwBaseSignDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTjRegister;
import com.thinkgem.jeesite.modules.affair.entity.AffairTwBaseSign;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 团委（支部）基本信息关联Service
 * @author cecil.li
 * @version 2019-12-04
 */
@Service
@Transactional(readOnly = true)
public class AffairTwBaseSignService extends CrudService<AffairTwBaseSignDao, AffairTwBaseSign> {

	@Autowired
	private AffairTwBaseSignDao affairTwBaseSignDao;

	public AffairTwBaseSign get(String id) {
		return super.get(id);
	}
	
	public List<AffairTwBaseSign> findList(AffairTwBaseSign affairTwBaseSign) {
		return super.findList(affairTwBaseSign);
	}
	
	public Page<AffairTwBaseSign> findPage(Page<AffairTwBaseSign> page, AffairTwBaseSign affairTwBaseSign) {
		return super.findPage(page, affairTwBaseSign);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTwBaseSign affairTwBaseSign) {
		super.save(affairTwBaseSign);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTwBaseSign affairTwBaseSign) {
		super.delete(affairTwBaseSign);
	}

	@Transactional(readOnly = false)
	public void deleteByMainId(String tbId){
		this.dao.deleteByMainId(tbId);
	}

	@Transactional(readOnly = false)
	public void deleteByMainIds(List<String> tbIds){
		dao.deleteByMainIds(tbIds);
	};

	public Integer selectNumber(String id){
		return affairTwBaseSignDao.selectNumber(id);
	}
	//统计分析专兼职团干部情况
    public List<Map<String, String>> countCares(String officeId, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Date startDate = DateUtils.parseDate(dateStart);
		Date endDate = DateUtils.parseDate(dateEnd);
		if (dateType.equals("1")) {    //月度
			year = null;
			startDate = null;
			endDate = null;
		} else if (dateType.equals("3")) {    //时间段
			year = null;
			month = null;
		} else {    //年度
			month = null;
			startDate = null;
			endDate = null;
		}
		//List<Map<String, String>> list  = dao.countCares(officeId,year,startDate,endDate,month);
		//11.26  团员基本情况无需关联时间
		List<Map<String, String>> list  = dao.countCares(officeId,null,null,null,null);
		String[] labelArray = {"书记","副书记","宣传委员","组织委员","生活委员","文体委员"};
		return ChartLabelUtils.fillLabel(list,labelArray);
    }
	//统计分析专兼职团干部情况  明细
	public Page<AffairTwBaseSign> findCadresPage(Page<AffairTwBaseSign> page, AffairTwBaseSign affairTwBaseSign) {
		String officeId = UserUtils.getUser().getOffice().getId();
		affairTwBaseSign.setOfficeId(officeId);
		/*String dateType = Optional.ofNullable(affairTwBaseSign.getDateType()).orElseGet(() -> {return "";});
		switch (dateType) {
			case "1":    //月度
				affairTwBaseSign.setYear(null);
				affairTwBaseSign.setStartDate(null);
				affairTwBaseSign.setEndDate(null);
				break;
			case "3":    //时间段
				affairTwBaseSign.setYear(null);
				affairTwBaseSign.setStartDate(DateUtils.parseDate(affairTwBaseSign.getDateStart()));
				affairTwBaseSign.setEndDate(DateUtils.parseDate(affairTwBaseSign.getDateEnd()));
				affairTwBaseSign.setMonth(null);
				break;
			default:    //年度
				affairTwBaseSign.setMonth(null);
				affairTwBaseSign.setStartDate(null);
				affairTwBaseSign.setEndDate(null);
				break;
		}*/
		//11.26  团员基本情况取消关联时间
		affairTwBaseSign.setMonth(null);
		affairTwBaseSign.setStartDate(null);
		affairTwBaseSign.setEndDate(null);
		affairTwBaseSign.setYear(null);
		affairTwBaseSign.setPage(page);
		page.setList(dao.findCadresList(affairTwBaseSign));
		return page;
	}
}