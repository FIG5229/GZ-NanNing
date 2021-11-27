/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairElectionDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairElection;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 党组织换届选举Service
 * @author eav.liu
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairElectionService extends CrudService<AffairElectionDao, AffairElection> {

	@Autowired
	private AffairElectionDao affairElectionDao;

	public AffairElection get(String id) {
		return super.get(id);
	}
	
	public List<AffairElection> findList(AffairElection affairElection) {
		return super.findList(affairElection);
	}
	
	public Page<AffairElection> findPage(Page<AffairElection> page, AffairElection affairElection) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
		//存放当前登录用户
			affairElection.setCreateBy(user);
		}
		affairElection.getSqlMap().put("dsf", dataScopeFilter(user, "o", "u"));
		return super.findPage(page, affairElection);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairElection affairElection) {
		//提交
		if("2".equals(affairElection.getStatus1())){
			affairElection.setStatus2("3");//未审核
		}
		super.save(affairElection);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairElection affairElection) {
		super.delete(affairElection);
	}

	@Transactional(readOnly = false)
	public void shenHe(AffairElection affairElection) {
		affairElection.setUpdateDate(new Date());
		affairElection.setPerson(UserUtils.getUser().getName());
		affairElectionDao.shenHe(affairElection);
	}

	/**
	 * 批量提交
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void submitByIds(List<String> ids){
		List<AffairElection> list = affairElectionDao.findByIds(ids);
		for (AffairElection affairElection: list) {
			//过滤掉已经提交的数据
			if (!"2".equals(affairElection.getStatus1())){
				//提交
				affairElection.setStatus1("2");
				affairElection.setStatus2("3");//未审核
				super.save(affairElection);
			}
		}
	}

	/**
	 * 統計分析	党组织换届数
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
    public List<Map<String, String>> countElection(String dateType, Integer year, String dateStart, String dateEnd, String month) {

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

		return dao.countElection(year,startDate,endDate,month);
    }

	/**
	 * 统计分析	党组织换届明细
	 * @param page
	 * @param affairElection
	 * @return
	 */
	public Page<AffairElection> findElectionDetailPage(Page<AffairElection> page, AffairElection affairElection) {
		String dateType = affairElection.getDateType();
		if (dateType.equals("1")){	//月度查询
			affairElection.setYear(null);
			affairElection.setJmStartDate(null);
			affairElection.setJmEndDate(null);
		}else if (dateType.equals("3")){	//时间段查询
			affairElection.setYear(null);
			affairElection.setJmStartDate(DateUtils.parseDate(affairElection.getDateStart()));
			affairElection.setJmEndDate(DateUtils.parseDate(affairElection.getDateEnd()));
			affairElection.setMonth(null);
		}else {	//年度查询
			affairElection.setMonth(null);
			affairElection.setJmStartDate(null);
			affairElection.setJmEndDate(null);
		}
		affairElection.setPage(page);
		page.setList(dao.findElectionDetailList(affairElection));
		return page;
	}

	public List<AffairElection> findAllInfo(String year, String startYear, String endYear) {
		return affairElectionDao.findAllInfo(year, startYear, endYear);
	}

	public int isElection(String partyOrganizationName, String partyOrganizationId, Date hjDate, String year){
		return affairElectionDao.isElection(partyOrganizationName,partyOrganizationId,hjDate, year);
	}

	public List<String> findUnitId(String partyOrganizationId){
		return affairElectionDao.findUnitId(partyOrganizationId);
	}

	public String findUserId(String id){
		return affairElectionDao.findUserId(id);
	}
}