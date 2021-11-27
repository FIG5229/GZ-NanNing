/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairZyInfoDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairZyInfo;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 助医管理Service
 * @author cecil.li
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairZyInfoService extends CrudService<AffairZyInfoDao, AffairZyInfo> {

	@Autowired
	private OfficeDao officeDao;

	public AffairZyInfo get(String id) {
		/*AffairZyInfo entity =
		AffairZyInfo param = new AffairZyInfo();
		param.setDate(entity.getDate());
		param.setUnit(entity.getUnit());
		param.setUnitId(entity.getUnitId());
		param.setBzJigou(entity.getBzJigou());
		param.setType(entity.getType());
		List<AffairZyInfo> children = super.findList(param);
		entity.setChildrens(children);*/
		return super.get(id);

	}

	public List<AffairZyInfo> findList(AffairZyInfo affairZyInfo) {
		affairZyInfo.setUserId(UserUtils.getUser().getOffice().getId());
		affairZyInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(affairZyInfo);
	}

	public Page<AffairZyInfo> findPage(Page<AffairZyInfo> page, AffairZyInfo affairZyInfo) {
		affairZyInfo.setCreateBy(UserUtils.getUser());
		if ("4c40b4dd2aee459286a37538978e6261".equals(UserUtils.getUser().getId())){
			affairZyInfo.setUserId(UserUtils.getUser().getOffice().getId());
			List<Office> childIds1 = officeDao.findChildById(affairZyInfo.getTabFlag());
			ArrayList<String> officeIds = new ArrayList<>();
			for (Office office:childIds1) {
				officeIds.add(office.getId());
			}
			if (affairZyInfo.getTabFlag().equals("1")) {
				officeIds.remove("34");
				officeIds.remove("95");
				officeIds.remove("156");
			}
			affairZyInfo.setOfficeIds(officeIds);
		}else {
			if ("cb97bf7e17d549f09b51375a98a8eb55".equals(UserUtils.getUser().getId()) || "abb70c88ca9d42758c489838fdd0e33b".equals(UserUtils.getUser().getId()) || "bdab6b25fc694e74bf7c3d1363c0062e".equals(UserUtils.getUser().getId())){
				affairZyInfo.setUserId(UserUtils.getUser().getCompany().getId());
			}else {
				affairZyInfo.setUserId(UserUtils.getUser().getOffice().getId());
			}

		}
//		affairZyInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));

		return super.findPage(page, affairZyInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairZyInfo affairZyInfo) {
		super.save(affairZyInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairZyInfo affairZyInfo) {
		super.delete(affairZyInfo);
	}

	public List<Map<String, Object>> findInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		String companyId  = dataScopeFilter(UserUtils.getUser(), "o", "u");
		return dao.findInfoByCreateOrgId(companyId, year, startDate, endDate, month);
	}

	public List<Map<String, Object>> findInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgIds(ids, year, startDate, endDate, month);
	}

    /**
     * 金额小计
     * @param affairZyInfo
     * @return
     */
	public Float getSumMoney(AffairZyInfo affairZyInfo){
		return dao.getSumMoney(affairZyInfo);
	}

	/**
	 * 统计分析 助医明细
	 * @param page
	 * @param affairZyInfo
	 * @return
	 */
    public Page<AffairZyInfo> findMedicalAidPage(Page<AffairZyInfo> page, AffairZyInfo affairZyInfo) {
		String dateType  = affairZyInfo.getDateType()==null?"":affairZyInfo.getDateType();
    	if (dateType.equals("1")){	//月度
    		affairZyInfo.setYear(null);
		}else if (dateType.equals(3)){	//时间段
			affairZyInfo.setYear(null);
			affairZyInfo.setStartDate(DateUtils.parseDate(affairZyInfo.getDateStart()));
			affairZyInfo.setEndDate(DateUtils.parseDate(affairZyInfo.getDateEnd()));
			affairZyInfo.setMonth(null);
		}else {		//年度
    		affairZyInfo.setMonth(null);
		}

    	affairZyInfo.setPage(page);
    	affairZyInfo.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(), "o", "u"));
    	page.setList(dao.findMedicalAidList(affairZyInfo));
    	return page;
    }
}