/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairGhActivityRecordDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGhActivityRecord;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工会活动记录Service
 * @author cecil.li
 * @version 2019-11-29
 */
@Service
@Transactional(readOnly = true)
public class AffairGhActivityRecordService extends CrudService<AffairGhActivityRecordDao, AffairGhActivityRecord> {

	@Autowired
	private AffairGhActivityRecordDao affairGhActivityRecordDao;

	public AffairGhActivityRecord get(String id) {
		return super.get(id);
	}
	
	public List<AffairGhActivityRecord> findList(AffairGhActivityRecord affairGhActivityRecord) {
		return super.findList(affairGhActivityRecord);
	}

	/**
	 * 批量提交
	 * @param ids
	 */
	public List<AffairGhActivityRecord> findByIds(List<String> ids){
		List<AffairGhActivityRecord> list = affairGhActivityRecordDao.findByIds(ids);
		return list;
	}
	
	public Page<AffairGhActivityRecord> findPage(Page<AffairGhActivityRecord> page, AffairGhActivityRecord affairGhActivityRecord) {
		affairGhActivityRecord.setUserId(UserUtils.getUser().getId());
		return super.findPage(page, affairGhActivityRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairGhActivityRecord affairGhActivityRecord) {
		affairGhActivityRecord.setContent(StringEscapeUtils.unescapeHtml4(affairGhActivityRecord.getContent()));
		super.save(affairGhActivityRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairGhActivityRecord affairGhActivityRecord) {
		super.delete(affairGhActivityRecord);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}

	//统计分析 统计组织活动数
    public List<Map<String,String>> countActivity(Integer year, Date dateStart, Date dateEnd, String month) {

		return dao.countActivity(year,dateStart,dateEnd,month);
    }

	/**
	 * 統計分析查看明细
	 * @param page
	 * @param record
	 * @return
	 */
	public Page<AffairGhActivityRecord> findActivityDetailPage(Page<AffairGhActivityRecord> page, AffairGhActivityRecord record) {
		if ("1".equals(record.getDateType())){	//月度查询
			record.setYear(null);
			record.setDateStart(null);
			record.setDateEnd(null);
		}else if ("3".equals(record.getDateType())){//时间段查询
			record.setYear(null);
			record.setMonth(null);
		}else {	//年度查询
			record.setDateStart(null);
			record.setDateEnd(null);
			record.setMonth(null);
		}
		//删除 标签的 '处' 或 '局'  根据标签查询明细
		switch (record.getLabel()){
			case "南宁处":
				record.setLabel("34");
				break;
			case "北海处":
				record.setLabel("156");
				break;
			case "柳州处":
				record.setLabel("95");
				break;
			default:
				record.setLabel("top");
				break;
		}
		record.setPage(page);
		page.setList(dao.findActivityDetailList(record));
		return page;
	}

	public int selectGhhd(String lastYearDate,String nowYearDate, String unitId) {
		return affairGhActivityRecordDao.selectGhhd(lastYearDate,nowYearDate,unitId);
	}
}