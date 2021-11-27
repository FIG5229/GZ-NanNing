/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.*;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 新闻宣传Service
 * @author cecil.li
 * @version 2019-11-02
 */
@Service
@Transactional(readOnly = true)
public class AffairNewsService extends CrudService<AffairNewsDao, AffairNews> {

	@Autowired
	private AffairNewsDao affairNewsDao;

	@Autowired
	private AffairNewsAuthorDao affairNewsAuthorDao;

	@Autowired
	private AffairNewsUnitDao affairNewsUnitDao;

	@Autowired
	private AffairNewsNameDao affairNewsNameDao;

	@Autowired
	private AffairNewsChildDao affairNewsChildDao;

	public AffairNews get(String id) {
		return super.get(id);
	}

	public List<AffairNews> findList(AffairNews affairNews) {
		return super.findList(affairNews);
	}

	public Page<AffairNews> findPage(Page<AffairNews> page, AffairNews affairNews) {
		affairNews.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairNews);
	}

	@Transactional(readOnly = false)
	public void save(AffairNews affairNews) {
		affairNews.setContent(StringEscapeUtils.unescapeHtml4(affairNews.getContent()));
		super.save(affairNews);
		/*子表数据保存到主表*/
	/*	if (affairNews.getNewsChildList()!=null){
			for (AffairNewsChild item : affairNews.getNewsChildList()) {
				affairNews.setId("");
				affairNews.setAuthor(item.getNewsAuthor());
				affairNews.setUnit(item.getNewsUnit());
				affairNews.setUnitId(item.getNewsUnitId());
				affairNews.setName(item.getNewsName());
				affairNews.setIdNumber(item.getNewsIdNumber());
				super.save(affairNews);

				*//*if (AffairNewsChild.DEL_FLAG_NORMAL.equals(item.getDelFlag())) {
					if (StringUtils.isBlank(item.getId())) {
						item.setNewsId(affairNews.getId());
						item.preInsert();
						affairNewsChildDao.insert(item);
					} else {
						item.preUpdate();
						affairNewsChildDao.update(item);
					}
				} else {
					affairNewsChildDao.delete(item);
				}*//*
			}
		}else {
			super.save(affairNews);
		}*/


		if (affairNews.getAffairNewsAuthorList()!=null){
			for (AffairNewsAuthor item : affairNews.getAffairNewsAuthorList()) {
				if (item.getId() == null) {
					continue;
				}
				if (AffairNewsAuthor.DEL_FLAG_NORMAL.equals(item.getDelFlag())) {
					if (StringUtils.isBlank(item.getId())) {
						item.setNewsId(affairNews.getId());
						item.preInsert();
						affairNewsAuthorDao.insert(item);
					} else {
						item.preUpdate();
						affairNewsAuthorDao.update(item);
					}
				} else {
					affairNewsAuthorDao.delete(item);
				}
			}
		}

		if (affairNews.getAffairNewsUnitList()!=null){
			for (AffairNewsUnit item : affairNews.getAffairNewsUnitList()) {
				if (item.getId() == null) {
					continue;
				}
				if (AffairNewsUnit.DEL_FLAG_NORMAL.equals(item.getDelFlag())) {
					if (StringUtils.isBlank(item.getId())) {
						item.setNewsId(affairNews.getId());
						item.preInsert();
						affairNewsUnitDao.insert(item);
					} else {
						item.preUpdate();
						affairNewsUnitDao.update(item);
					}
				} else {
					affairNewsUnitDao.delete(item);
				}
			}
		}

		if (affairNews.getAffairNewsNameList()!=null){
			for (AffairNewsName item : affairNews.getAffairNewsNameList()) {
				if (item.getId() == null) {
					continue;
				}
				if (AffairNewsName.DEL_FLAG_NORMAL.equals(item.getDelFlag())) {
					if (StringUtils.isBlank(item.getId())) {
						item.setNewsId(affairNews.getId());
						item.preInsert();
						affairNewsNameDao.insert(item);
					} else {
						item.preUpdate();
						affairNewsNameDao.update(item);
					}
				} else {
					affairNewsNameDao.delete(item);
				}
			}
		}
	}

	@Transactional(readOnly = false)
	public void delete(AffairNews affairNews) {
		super.delete(affairNews);
	}

	public List<Map<String, Object>> findInfoByUnitId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByUnitId(id, year, startDate, endDate, month);
	}

	public List<Map<String, Object>> findInfoByUnitIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByUnitIds(ids, year, startDate, endDate, month);
	}

	public List<AffairCorrespondent> findPerson(AffairCorrespondent param){
		return affairNewsDao.findPerson(param);
	}

	public List<Map<String, Object>> findEchartsInfoByUnit(String id, Integer year, Date startDate, Date endDate, String month){
		/*String parentId = UserUtils.getUser().getOffice().getParentId();
		String id1 = UserUtils.getUser().getOffice().getId();
		String companyId = UserUtils.getUser().getCompany().getId();
		if (!id1.equals("34") && id1.equals("95") && id1.equals("156") && (parentId.equals("1") || parentId.equals("0"))){*/
			return dao.findEchartsInfoByUnit(id, year, startDate, endDate, month);
		/*}else {
			return dao.findChuEchartsInfoByUnit(companyId,id, year, startDate, endDate, month);
		}*/

	}

	public Page<AffairNews> findEchartsDetailInfo(Page<AffairNews> page, AffairNews affairNews){
		affairNews.setPage(page);
		if (!UserUtils.getUser().getOffice().equals("局机关")){
			String officeId = UserUtils.getUser().getCompany().getId();
			affairNews.setUnitId(officeId);
		}else {
			affairNews.setUnitId("top");
		}
		List<AffairNews> list = new ArrayList<>();
		if("1".equals(affairNews.getDateType())){//月度
			if (affairNews.getMonth() != null && affairNews.getMonth().length() > 0) {
				affairNews.setYear(null);
				affairNews.setStartDate(null);
				affairNews.setEndDate(null);
				list = affairNewsDao.findEchartsDetailInfo(affairNews);
			}
		}else if("2".equals(affairNews.getDateType())){//年度
			affairNews.setMonth(null);
			affairNews.setStartDate(null);
			affairNews.setEndDate(null);
			list = affairNewsDao.findEchartsDetailInfo(affairNews);
		}else{// 时间段
			affairNews.setMonth(null);
			affairNews.setYear(null);
			Date startDate = DateUtils.parseDate(affairNews.getStartDate());
			Date endDate = DateUtils.parseDate(affairNews.getEndDate());
			affairNews.setStartDate(startDate);
			affairNews.setEndDate(endDate);
			list = affairNewsDao.findEchartsDetailInfo(affairNews);
		}
		page.setList(list);
		return page;
	}

	public AffairNews getOne(AffairNews affairNews) {
		return affairNewsDao.getOne(affairNews);
	}

	public List<AffairNewsAuthor> findAuthor(String authorId) {
		return affairNewsDao.findAuthor(authorId);
	}

	public List<AffairNewsUnit> findUnit(String authorId) {
		return affairNewsDao.findUnit(authorId);
	}

	public String findUnitId(String s) {
		return affairNewsDao.findUnitId(s);
	}

	public List<AffairNews> findLintInIds(List<String> newsIdList) {
		return affairNewsDao.findListInIds(newsIdList);
	}
}
