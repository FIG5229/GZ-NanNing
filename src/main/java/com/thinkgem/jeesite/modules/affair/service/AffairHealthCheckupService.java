/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairHealthCheckupDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairHealthCheckup;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelBaseDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 健康体检Service
 * @author mason.xv
 * @version 2020-03-23
 */
@Service
@Transactional(readOnly = true)
public class AffairHealthCheckupService extends CrudService<AffairHealthCheckupDao, AffairHealthCheckup> {

	@Autowired
	private AffairHealthCheckupDao affairHealthCheckupdao;

	@Autowired
	private PersonnelBaseDao personnelBaseDao;

	public AffairHealthCheckup get(String id) {
		return super.get(id);
	}
	
	public List<AffairHealthCheckup> findList(AffairHealthCheckup affairHealthCheckup) {
//		affairHealthCheckup.setUserId(UserUtils.getUser().getOffice().getId());
		affairHealthCheckup.setUserOffice((UserUtils.getUser().getOffice().getId()));
		User user = UserUtils.getUser();
		affairHealthCheckup.setCreateBy(user);
		affairHealthCheckup.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(affairHealthCheckup);
	}
	
	public Page<AffairHealthCheckup> findPage(Page<AffairHealthCheckup> page, AffairHealthCheckup affairHealthCheckup) {
		/*10-7 问题反馈，权限问题：健康体检模块，贵港站派出所账号可以查看现有的茂名西所的信息，请修改账号权限为所队账号只能查看本单位内容。
		* companyId改成officeId
		* 当用户为工会信息管理账号时使用companyId查看本公司数据*/
		affairHealthCheckup.setUserOffice((UserUtils.getUser().getOffice().getId()));
		String userId = UserUtils.getUser().getId();
		if (userId.equals("4c40b4dd2aee459286a37538978e6261") || userId.equals("abb70c88ca9d42758c489838fdd0e33b") ||
				userId.equals("bdab6b25fc694e74bf7c3d1363c0062e") || userId.equals("cb97bf7e17d549f09b51375a98a8eb55") ){
			affairHealthCheckup.setUserOffice((UserUtils.getUser().getCompany().getId()));
		}
		User user = UserUtils.getUser();
		affairHealthCheckup.setCreateBy(user);
		affairHealthCheckup.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairHealthCheckup);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairHealthCheckup affairHealthCheckup) {
		super.save(affairHealthCheckup);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairHealthCheckup affairHealthCheckup) {
		super.delete(affairHealthCheckup);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		//参检人数
		id=dataScopeFilter(UserUtils.getUser(), "o", "u");
		Integer tj_num = affairHealthCheckupdao.findInfoByCreateOrgId(id, year, startDate, endDate, month);
		//去人员基础表查单位的总人数
		Integer num = personnelBaseDao.findPersonNumByWorkunitId(id);
		//未参检人数
		Integer un_tj_num = num - tj_num;
		Map<String, Object> map = new HashMap<>();
		map.put("tjNum",tj_num);
		map.put("unTjNum",un_tj_num);
		return map;
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		//参检人数
		Integer tj_num = affairHealthCheckupdao.findInfoByCreateOrgIds(ids, year, startDate, endDate, month);
		//去人员基础表查单位的总人数
		Integer num = personnelBaseDao.findPersonNumByWorkunitIds(ids);
		//未参检人数
		Integer un_tj_num = num - tj_num;
		Map<String, Object> map = new HashMap<>();
		map.put("tjNum",tj_num);
		map.put("unTjNum",un_tj_num);
		return map;
	}

	public List<Map<String, Object>> findTypeInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		return affairHealthCheckupdao.findTypeInfoByCreateOrgId(id, year, startDate, endDate, month);
	}

	public List<Map<String, Object>> findTypeInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return  affairHealthCheckupdao.findTypeInfoByCreateOrgIds(ids, year, startDate, endDate, month);
	}

	public Page<AffairHealthCheckup> findHealthCheckupPage(Page<AffairHealthCheckup> page,AffairHealthCheckup affairHealthCheckup){

		if ("1".equals(affairHealthCheckup.getDateType())){		//月度查询
			affairHealthCheckup.setYear(null);
			affairHealthCheckup.setDateStart(null);
			affairHealthCheckup.setDateEnd(null);
		} else if ("3".equals(affairHealthCheckup.getDateType())) {		//时间段查询
			affairHealthCheckup.setYear(null);
			affairHealthCheckup.setMonth(null);
		}else {		//年度查询
			affairHealthCheckup.setDateStart(null);
			affairHealthCheckup.setDateEnd(null);
			affairHealthCheckup.setMonth(null);
		}
		String label = DictUtils.getDictValue(affairHealthCheckup.getLabel(),"affair_health_checkup_type","");
		affairHealthCheckup.setLabel(label);

		affairHealthCheckup.setPage(page);
		affairHealthCheckup.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(), "o", "u"));
		page.setList(dao.findHealthCheckupList(affairHealthCheckup));
		return page;
	}

	public List<AffairHealthCheckup> findByDateList(AffairHealthCheckup healthCheckup) {
		healthCheckup.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return dao.findHealthCheckupList(healthCheckup);
	}

	/**
	 * 统计分析 健康体检 体检率明细
	 * @param page
	 * @param affairHealthCheckup
	 * @return
	 */
    public Page<AffairHealthCheckup> findHealthReferencePage(Page<AffairHealthCheckup> page, AffairHealthCheckup affairHealthCheckup) {
		if ("1".equals(affairHealthCheckup.getDateType())){		//月度查询
			affairHealthCheckup.setYear(null);
			affairHealthCheckup.setBeginTjDate(null);
			affairHealthCheckup.setEndTjDate(null);
		} else if ("3".equals(affairHealthCheckup.getDateType())) {		//时间段查询
			affairHealthCheckup.setYear(null);
			affairHealthCheckup.setBeginTjDate(DateUtils.parseDate(affairHealthCheckup.getDateStart()));
			affairHealthCheckup.setEndTjDate(DateUtils.parseDate(affairHealthCheckup.getDateEnd()));
			affairHealthCheckup.setMonth(null);
		}else {		//年度查询
			affairHealthCheckup.setBeginTjDate(null);
			affairHealthCheckup.setEndTjDate(null);
			affairHealthCheckup.setMonth(null);
		}
    	affairHealthCheckup.setPage(page);
		affairHealthCheckup.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(), "o", "u"));
    	page.setList(dao.findHealthReferenceList(affairHealthCheckup));
    	return page;
    }

	public int selectIsTiJian(String lastYearDate,String nowYearDate, String unitId) {
		return affairHealthCheckupdao.selectIsTiJian(lastYearDate,nowYearDate,unitId);
	}
}