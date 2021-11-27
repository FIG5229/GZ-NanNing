/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.ChartLabelUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairEducationCommentDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairEducationComment;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 团员教育评议Service
 * @author cecil.li
 * @version 2019-11-20
 */
@Service
@Transactional(readOnly = true)
public class AffairEducationCommentService extends CrudService<AffairEducationCommentDao, AffairEducationComment> {

	@Autowired
	private PersonnelBaseService personnelBaseService;
	@Autowired
	private AffairEducationCommentDao affairEducationCommentDao;

	public AffairEducationComment get(String id) {
		return super.get(id);
	}
	
	public List<AffairEducationComment> findList(AffairEducationComment affairEducationComment) {
		return super.findList(affairEducationComment);
	}
	
	public Page<AffairEducationComment> findPage(Page<AffairEducationComment> page, AffairEducationComment affairEducationComment) {
		affairEducationComment.setUserId(UserUtils.getUser().getId());
		affairEducationComment.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, affairEducationComment);
	}

	/**
	 * 批量提交
	 * @param ids
	 */
	public List<AffairEducationComment> findByIds(List<String> ids){
		List<AffairEducationComment> list = affairEducationCommentDao.findByIds(ids);
		return list;
	}
	
	@Transactional(readOnly = false)
	public void save(AffairEducationComment affairEducationComment) {
		if(affairEducationComment.getCheckType()==null||"".equals(affairEducationComment.getCheckType())){
			affairEducationComment.setCheckType("1");
		}
		super.save(affairEducationComment);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairEducationComment affairEducationComment) {
		super.delete(affairEducationComment);
	}

	//统计分析	统计教育评议 及格 不及格
	public List<Map<String, String>> countComment(String dateType, Integer year, String dateStart, String dateEnd, String month, String officeId) {
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
		officeId = dataScopeFilter(UserUtils.getUser(), "o", "u");
		return ChartLabelUtils.fillLabel(dao.countComment(year,startDate,endDate,month,officeId), DictUtils.getDictList("affair_comment_grade"));
	}

	/**
	 * 教育评议的数据分析 反查明细
	 * @param affairEducationCommentPage
	 * @param affairEducationComment
	 * @return
	 */
	public Page<AffairEducationComment> findEducationCommentPage(Page<AffairEducationComment> affairEducationCommentPage, AffairEducationComment affairEducationComment) {

		String dateType = Optional.ofNullable(affairEducationComment.getDateType()).orElseGet(() -> {return "";});
		switch (dateType) {
			case "1":    //月度
				affairEducationComment.setYear(null);
				affairEducationComment.setStartDate(null);
				affairEducationComment.setEndDate(null);
				break;
			case "3":    //时间段
				affairEducationComment.setYear(null);
				affairEducationComment.setStartDate(DateUtils.parseDate(affairEducationComment.getDateStart()));
				affairEducationComment.setEndDate(DateUtils.parseDate(affairEducationComment.getDateEnd()));
				affairEducationComment.setMonth(null);
				break;
			default:    //年度
				affairEducationComment.setMonth(null);
				affairEducationComment.setStartDate(null);
				affairEducationComment.setEndDate(null);
				break;
		}

		affairEducationComment.setPage(affairEducationCommentPage);
		affairEducationComment.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(), "o", "u"));
		affairEducationCommentPage.setList(dao.findEducationCommentPage(affairEducationComment));
		return affairEducationCommentPage;
	}
	public List<String> selectAllYear(){
		return affairEducationCommentDao.selectAllYear();
	}
	public String selectUnitNameById(String unitName){
		return affairEducationCommentDao.selectUnitNameById(unitName);
	}

//	selectBeanById

	public List<AffairEducationComment> selectBeanById(String idN,Date nowYearDate, Date lastYearDate){
		return affairEducationCommentDao.selectBeanById(idN,nowYearDate,lastYearDate);

	}


}