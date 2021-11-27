/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.ChartLabelUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairZxInfoChildDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairZxInfoDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairZxInfo;
import com.thinkgem.jeesite.modules.affair.entity.AffairZxInfoChild;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 子女信息Service
 * @author cecil.li
 * @version 2019-12-02
 */
@Service
@Transactional(readOnly = true)
public class AffairZxInfoService extends CrudService<AffairZxInfoDao, AffairZxInfo> {

	@Autowired
	private AffairZxInfoChildDao affairZxInfoChildDao;

	public AffairZxInfo get(String id) {
		return super.get(id);
	}

	public List<AffairZxInfo> findList(AffairZxInfo affairZxInfo) {
		return super.findList(affairZxInfo);
	}

	public Page<AffairZxInfo> findPage(Page<AffairZxInfo> page, AffairZxInfo affairZxInfo) {
		if (UserUtils.getUser().getId().equals("4c40b4dd2aee459286a37538978e6261") || UserUtils.getUser().getId().equals("cb97bf7e17d549f09b51375a98a8eb55") || UserUtils.getUser().getId().equals("bdab6b25fc694e74bf7c3d1363c0062e") || UserUtils.getUser().getId().equals("abb70c88ca9d42758c489838fdd0e33b")){
			affairZxInfo.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairZxInfo.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairZxInfo);
	}

	@Transactional(readOnly = false)
	public void save(AffairZxInfo affairZxInfo) {
		super.save(affairZxInfo);
	}

	@Transactional(readOnly = false)
	public void delete(AffairZxInfo affairZxInfo) {
		super.delete(affairZxInfo);
	}


	public Map<String, Object> findInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgId(id, year, startDate, endDate, month);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgIds(ids, year, startDate, endDate, month);
	}

	/**
	 * 统计分析
	 * 	助学
	 * 	统计时间、
	 * 	单位（局机关、南宁公安处、柳州公安处、北海公安处）、
	 * 	学校类别（本科、大专高职、中专人数）
	 * 	（能反查人员）
	 * @param id
	 * @param year
	 * @param startDate
	 * @param endDate
	 * @param month
	 * @return
	 */
    public List<Map<String, String>> countstudyAid(String id, Integer year, Date startDate, Date endDate, String month) {
    	id = dataScopeFilter(UserUtils.getUser(), "o", "u");
		List<Map<String, String>> list = dao.countstudyAid(id,year,startDate,endDate,month);
		String[] label = {"本科","大专（高职）","中专（中技）"};
		return ChartLabelUtils.fillLabel(list,label);
    }

	/**
	 * 统计分析 查看助学明细
	 * @param page
	 * @param affairZxInfo
	 * @return
	 */
	public Page<AffairZxInfo> findStudyAidPage(Page<AffairZxInfo> page, AffairZxInfo affairZxInfo) {
		//按照月度查询
		if (affairZxInfo.getDateType().equals("1")){
			affairZxInfo.setDateEnd(null);
			affairZxInfo.setDateStart(null);
			affairZxInfo.setYear(null);
		}else if (affairZxInfo.getDateType().equals("3")){//按照时间段查询
			affairZxInfo.setYear(null);
			affairZxInfo.setMonth(null);
		}else {	//按照年度查询
			affairZxInfo.setDateEnd(null);
			affairZxInfo.setDateStart(null);
			affairZxInfo.setMonth(null);

		}
		affairZxInfo.setPage(page);
		affairZxInfo.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(), "o", "u"));
		page.setList(dao.findStudyAidList(affairZxInfo));
		return page;
	}

	/**
	 * 遍历子女列表进行保存
	 * @param affairZxInfo
	 */
	@Transactional(readOnly = false)
	public void addSave(AffairZxInfo affairZxInfo) {
		if (affairZxInfo.getAffairZxInfoChildList() != null && affairZxInfo.getAffairZxInfoChildList().size() > 0) {
			for (AffairZxInfoChild affairZxInfoChild : affairZxInfo.getAffairZxInfoChildList()) {
				affairZxInfo.setChildName(affairZxInfoChild.getName());
				affairZxInfo.setChildSex(affairZxInfoChild.getSex());
				affairZxInfo.setChildBirthday(affairZxInfoChild.getBirthday());
				affairZxInfo.setChildSchoolMajor(affairZxInfoChild.getSchoolMajor());
				affairZxInfo.setChildMajor(affairZxInfoChild.getMajor());
				affairZxInfo.setChildType(affairZxInfoChild.getType());
				affairZxInfo.setChildYearSystem(affairZxInfoChild.getYearSystem());
				affairZxInfo.setChildGrade(affairZxInfoChild.getGrade());
				affairZxInfo.setMoney(affairZxInfoChild.getMoney());
				affairZxInfo.setChildSchoolType(affairZxInfoChild.getSchoolType());
				affairZxInfo.setId(null);
				super.save(affairZxInfo);
			}
		} else {
			super.save(affairZxInfo);

		}
	}

}