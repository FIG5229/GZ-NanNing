/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairCommentDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairComment;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 党员民主评议Service
 * @author eav.liu
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class AffairCommentService extends CrudService<AffairCommentDao, AffairComment> {

	@Autowired
	private AffairCommentDao affairCommentDao;

	public AffairComment get(String id) {
		return super.get(id);
	}
	
	public List<AffairComment> findList(AffairComment affairComment) {
		return super.findList(affairComment);
	}
	
	public Page<AffairComment> findPage(Page<AffairComment> page, AffairComment affairComment) {
		affairComment.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairComment);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairComment affairComment) {
		super.save(affairComment);
	}

	@Transactional(readOnly = false)
	public void saveComment(AffairComment affairComment) {
		affairCommentDao.updateComment(affairComment);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairComment affairComment) {
		super.delete(affairComment);
	}

	/*修改参数类型 展示未查询到的label*/
	public List<Map<String, String>> findGradeInfoById(String id, Integer year, Date startDate, Date endDate, String month) {
		return affairCommentDao.findGradeInfoById(id, year, startDate, endDate, month);
	}

	public List<Map<String, String>> findGradeInfoByIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return affairCommentDao.findGradeInfoByIds(ids, year, startDate, endDate, month);
	}

	public Page<AffairComment> echartSexFindPageByPbId(Page<AffairComment> page, AffairComment affairComment) {
		affairComment.setPage(page);
		List<AffairComment> list = new ArrayList();
		if("1".equals(affairComment.getDateType())){//月度
			if (affairComment.getMonth() != null && affairComment.getMonth().length() > 0) {
				affairComment.setEchartYear(null);
				affairComment.setEchartStartDate(null);
				affairComment.setEchartEndDate(null);
				list = affairCommentDao.echartSexFindPageByPbId(affairComment);
			}
		}else if("2".equals(affairComment.getDateType())){//年度
			affairComment.setMonth(null);
			affairComment.setEchartStartDate(null);
			affairComment.setEchartEndDate(null);
			list = affairCommentDao.echartSexFindPageByPbId(affairComment);
		}else{// 时间段
			affairComment.setMonth(null);
			affairComment.setEchartYear(null);
			Date startDate = DateUtils.parseDate(affairComment.getDateStart());
			Date endDate = DateUtils.parseDate(affairComment.getDateEnd());
			affairComment.setEchartStartDate(startDate);
			affairComment.setEchartEndDate(endDate);
			list = affairCommentDao.echartSexFindPageByPbId(affairComment);
		}
		page.setList(list);
		return page;
	}

	public Page<AffairComment> echartSexFindPageByPbIds(Page<AffairComment> page, AffairComment affairComment) {
        affairComment.setPage(page);
        List<AffairComment> list = new ArrayList();
        if("1".equals(affairComment.getDateType())){//月度
            if (affairComment.getMonth() != null && affairComment.getMonth().length() > 0) {
                affairComment.setEchartYear(null);
                affairComment.setEchartStartDate(null);
                affairComment.setEchartEndDate(null);
                list = affairCommentDao.echartSexFindPageByPbIds(affairComment);
            }
        }else if("2".equals(affairComment.getDateType())){//年度
            affairComment.setMonth(null);
            affairComment.setEchartStartDate(null);
            affairComment.setEchartEndDate(null);
            list = affairCommentDao.echartSexFindPageByPbIds(affairComment);
        }else{// 时间段
            affairComment.setMonth(null);
            affairComment.setEchartYear(null);
            Date startDate = DateUtils.parseDate(affairComment.getDateStart());
            Date endDate = DateUtils.parseDate(affairComment.getDateEnd());
            affairComment.setEchartStartDate(startDate);
            affairComment.setEchartEndDate(endDate);
            list = affairCommentDao.echartSexFindPageByPbIds(affairComment);
        }
        page.setList(list);
        return page;
	}

	@Transactional(readOnly = false)
	public void shenHeSave(AffairComment affairComment) {
		affairCommentDao.shenHeSave(affairComment);
	}
}