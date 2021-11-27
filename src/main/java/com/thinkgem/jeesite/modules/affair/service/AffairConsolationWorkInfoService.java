/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairConsolationWorkInfoDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairConsolationWorkInfo;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 慰问工作管理Service
 * @author cecil.li
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairConsolationWorkInfoService extends CrudService<AffairConsolationWorkInfoDao, AffairConsolationWorkInfo> {

	public AffairConsolationWorkInfo get(String id) {
		return super.get(id);
	}
	
	public List<AffairConsolationWorkInfo> findList(AffairConsolationWorkInfo affairConsolationWorkInfo) {
		return super.findList(affairConsolationWorkInfo);
	}
	
	public Page<AffairConsolationWorkInfo> findPage(Page<AffairConsolationWorkInfo> page, AffairConsolationWorkInfo affairConsolationWorkInfo) {
		/*affairConsolationWorkInfo.setUserId(UserUtils.getUser().getOffice().getId());
		affairConsolationWorkInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/
		if (UserUtils.getUser().getId().equals("4c40b4dd2aee459286a37538978e6261") || UserUtils.getUser().getId().equals("cb97bf7e17d549f09b51375a98a8eb55") || UserUtils.getUser().getId().equals("bdab6b25fc694e74bf7c3d1363c0062e") || UserUtils.getUser().getId().equals("abb70c88ca9d42758c489838fdd0e33b")){
			affairConsolationWorkInfo.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairConsolationWorkInfo.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairConsolationWorkInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairConsolationWorkInfo affairConsolationWorkInfo) {
		affairConsolationWorkInfo.setContent(StringEscapeUtils.unescapeHtml4(affairConsolationWorkInfo.getContent()));
		super.save(affairConsolationWorkInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairConsolationWorkInfo affairConsolationWorkInfo) {
		super.delete(affairConsolationWorkInfo);
	}

	public List<Map<String, String>> findInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		id = dataScopeFilter(UserUtils.getUser(), "o", "u");
		return dao.findInfoByCreateOrgId(id, year, startDate, endDate, month);
	}

	public List<Map<String, String>> findInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgIds(ids, year, startDate, endDate, month);
	}

	/**
	 * 统计分析 查看慰问工作明细
	 * @param page
	 * @param consolationWorkInfo
	 * @return
	 */
    public Page findSympathyDetailPage(Page<AffairConsolationWorkInfo> page, AffairConsolationWorkInfo consolationWorkInfo) {
    	String dateaType = Optional.ofNullable(consolationWorkInfo.getType()).orElseGet(() -> {return "";});
    	if (dateaType.equals("1")){	//月度查询
    		consolationWorkInfo.setYear(null);
		}else if (dateaType.equals("3")){//时间段查询
			consolationWorkInfo.setYear(null);
    		consolationWorkInfo.setStartDate(DateUtils.parseDate(consolationWorkInfo.getDateStart()));
    		consolationWorkInfo.setEndDate(DateUtils.parseDate(consolationWorkInfo.getDateEnd()));
			consolationWorkInfo.setMonth(null);
		}else {	//年度查询
    		consolationWorkInfo.setMonth(null);
		}
    	consolationWorkInfo.setPage(page);
		consolationWorkInfo.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(), "o", "u"));
    	page.setList(dao.findSympathyDetailList(consolationWorkInfo));
    	return page;
    }

    public List<AffairConsolationWorkInfo> allInfo(String year, String month, String startTime, String endTime){
    	return dao.allInfo(year,month, startTime, endTime);
	}

	public int unitCount(String startTime, String endTime, String unitId){
    	return dao.unitCount(startTime, endTime, unitId);
	}
}