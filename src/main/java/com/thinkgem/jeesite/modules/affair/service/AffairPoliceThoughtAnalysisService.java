/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairPoliceThoughtAnalysisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceThoughtAnalysis;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 民警思想动态分析Service
 * @author daniel.liu
 * @version 2020-05-11
 */
@Service
@Transactional(readOnly = true)
public class AffairPoliceThoughtAnalysisService extends CrudService<AffairPoliceThoughtAnalysisDao, AffairPoliceThoughtAnalysis> {

	@Autowired
	private AffairPoliceThoughtAnalysisDao affairPoliceThoughtAnalysisDao;

	@Autowired
	private OfficeService officeService;

	public AffairPoliceThoughtAnalysis get(String id) {
		return super.get(id);
	}
	
	public List<AffairPoliceThoughtAnalysis> findList(AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis) {
		return super.findList(affairPoliceThoughtAnalysis);
	}
	@Transactional(readOnly = false)
	public Page<AffairPoliceThoughtAnalysis> findPage(Page<AffairPoliceThoughtAnalysis> page, AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis) {
		/*此处过于繁琐*/
		/*if (StringUtils.isBlank(affairPoliceThoughtAnalysis.getReportType())){
			affairPoliceThoughtAnalysis.setReportType("1");
		}*//*
		if (affairPoliceThoughtAnalysis.getYear()==null){
			Calendar date = Calendar.getInstance();
			affairPoliceThoughtAnalysis.setYear(date.get(Calendar.YEAR));
		}
		//宣教查看数据时 更改数据过滤
		*//*需求变更
		 * 局查看时，值查看三个宣教处数据，查不到则添加 设置为未上报
		 * 处查看时，查看处直属单位，查不到则添加，设置为未上报*//*
		String companyId = UserUtils.getUser().getCompany().getId();
		String officeId = UserUtils.getUser().getOffice().getId();
		String userId = UserUtils.getUser().getId();
		if (affairPoliceThoughtAnalysis.isSign()){
			affairPoliceThoughtAnalysis.setSign(false);
			if (companyId.equals("1")){
				affairPoliceThoughtAnalysis.getSqlMap().put("dsf","and o.id in ('29','144','265') and unit_id in ('29','144','265')");
			}else if (officeId.equals("29") || officeId.equals("144") || officeId.equals("265")){
				affairPoliceThoughtAnalysis.getSqlMap().put("dsf", "and o.parent_id = '"+companyId+"'");
			}
//			affairPoliceThoughtAnalysis.getSqlMap().put("dsf", alterDataScopeFilter(UserUtils.getUser(), "o", "u"));
		}else {
//			affairPoliceThoughtAnalysis.getSqlMap().put("dsf", alterDataScopeFilter(UserUtils.getUser(), "o", "u"));
			affairPoliceThoughtAnalysis.getSqlMap().put("dsf", "and( o.id = '"+officeId+"' and u.id = '"+userId+"')");
			return super.findPage(page, affairPoliceThoughtAnalysis);
		}

		Page<AffairPoliceThoughtAnalysis> analysisPage = super.findPage(page, affairPoliceThoughtAnalysis);
		Date date = DateUtils.parseDate(String.valueOf(affairPoliceThoughtAnalysis.getYear())+ Calendar.getInstance().get(Calendar.MONTH) + Calendar.getInstance().get(Calendar.DATE));
		if (companyId.equals("1")){
			String[] ids = new String[]{"29","144","265"};
			Map<String,String> map = new HashMap<>();
			map.put("29","南宁处政治处宣传教育室（民警训练基地）");
			map.put("144","柳州处政治处宣传教育室（民警训练基地）");
			map.put("265","北海处政治处宣传教育室（民警训练基地）");

			for (String id : ids) {
				boolean has = false;
				List<AffairPoliceThoughtAnalysis> list = page.getList();
				for(int i =0;i<list.size();i++){
					if (list.get(i).getUnitId().equals(id)){
						has=true;
					}
				}
				if (!has){
					AffairPoliceThoughtAnalysis analysis = new AffairPoliceThoughtAnalysis();
					analysis.setUnitId(id);
					analysis.setUnit(map.get(id));
					analysis.setReportStatus("1");
					analysis.setReportTime(date);
					analysis.setReportType(affairPoliceThoughtAnalysis.getReportType());
					User creatBy = new User();
					Office office = new Office();
					office.setId(id);
					creatBy.setOffice(office);

					analysis.setCreateBy(creatBy);
					analysis.preInsert();
					analysis.getCreateBy().getOffice().setId(id);
					affairPoliceThoughtAnalysisDao.insert(analysis);
					analysis.getCreateBy().getOffice().setId(officeId);
					list.add(analysis);
				}
			}
		} else if (officeId.equals("29") || officeId.equals("144") || officeId.equals("265")) {
			List<String> ids = officeService.findIdsByParentId(companyId);
			ids.forEach(s -> {
				boolean has = false;
				List<AffairPoliceThoughtAnalysis> list = page.getList();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getUnitId().equals(s)) {
						has = true;
					}
				}
				*//*基层党支部查询的时候  不添加这三个单位*//*
				if (s.equals("29") || s.equals("144") || s.equals("265") ){
					has = true;
				}
				if (!has) {
					AffairPoliceThoughtAnalysis analysis = new AffairPoliceThoughtAnalysis();
					analysis.setUnitId(s);
					analysis.setUnit(officeService.findNameById(s));
					analysis.setReportStatus("1");
					analysis.setReportTime(date);
					analysis.setReportType(affairPoliceThoughtAnalysis.getReportType());
					analysis.preInsert();
					analysis.getCreateBy().getOffice().setId(s);
					affairPoliceThoughtAnalysisDao.insert(analysis);
					analysis.getCreateBy().getOffice().setId(officeId);
					list.add(analysis);
				}

			});
		}*/
				String userId = UserUtils.getUser().getId();
		switch (affairPoliceThoughtAnalysis.getClassify()){
			case "1":
				if (userId.equals("d154234ecb35470e84fb95e53726866b") || userId.equals("南宁处政治处宣传教育室（民警训练基地）") ||
						userId.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || userId.equals("北海处政治处宣传教育室（民警训练基地）")||
						userId.equals("柳州处政治处宣传教育室（民警训练基地）") || userId.equals("d30e324c8f73492d9b74103374fbc689")){
					/*公安处查看基层时，只查看已上报数据*/
					affairPoliceThoughtAnalysis.setReportStatus("2");
				}
				break;
			case "2":
				if (userId.equals("66937439b2124f328d1521968fab06db") || userId.equals("南宁局政治部宣传教育处")){
					/*局查看时 查看已经上报的	*/
					affairPoliceThoughtAnalysis.setReportStatus("2");
				}
				break;
			case "3":
				if (userId.equals("66937439b2124f328d1521968fab06db") || userId.equals("南宁局政治部宣传教育处")){
					/*局查看时 查看已经上报的	*/
					affairPoliceThoughtAnalysis.setReportStatus("2");
				}
				break;
			case "4":
				break;
			case "5":
				break;
		}
		//柳州处宣传教育管理、北海处宣传思想管理、南宁处宣传思想管理、南宁局宣传思想管理
		if("d30e324c8f73492d9b74103374fbc689".equals(userId) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(userId) || "d154234ecb35470e84fb95e53726866b" .equals(userId)||"66937439b2124f328d1521968fab06db".equals(userId)){
			affairPoliceThoughtAnalysis.getSqlMap().put("dsf", alterDataScopeFilter(UserUtils.getUser(), "o", "u"));
		}else{
			affairPoliceThoughtAnalysis.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		}
		return super.findPage(page,affairPoliceThoughtAnalysis);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis) {
		affairPoliceThoughtAnalysis.setContent(StringEscapeUtils.unescapeHtml4(affairPoliceThoughtAnalysis.getContent()));
		/*
		String userId = UserUtils.getUser().getId();
		String officeId = UserUtils.getUser().getOffice().getId();
		String companyId = UserUtils.getUser().getCompany().getId();
		if (companyId.equals("34") || companyId.equals("95") || companyId.equals("156") ){
			if (!userId.equals("d154234ecb35470e84fb95e53726866b") && !"e3ac8381fb3247e0b64fd6e3c48bddc1".equals(userId) && !"d30e324c8f73492d9b74103374fbc689".equals(userId)){
				affairPoliceThoughtAnalysis.setClassify("1");
			}
		}
		if (userId.equals("d154234ecb35470e84fb95e53726866b") && "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(userId) && "d30e324c8f73492d9b74103374fbc689".equals(userId)) {
			affairPoliceThoughtAnalysis.setClassify("2");
		}
		if (companyId.equals("1") && !officeId.equals("34") && !officeId.equals("95") && !officeId.equals("156")){
			if (!userId.equals("8a6819768aef40968e8f289842613276") && !"66937439b2124f328d1521968fab06db".equals(userId) ){
				affairPoliceThoughtAnalysis.setClassify("3");
			}
		}*/
		super.save(affairPoliceThoughtAnalysis);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis) {
		super.delete(affairPoliceThoughtAnalysis);
	}

	/*此方法仅适用于查看本部门时 查看本公司及以下*/
/*	public static String alterDataScopeFilter(User user, String officeAlias, String userAlias) {

		StringBuilder sqlString = new StringBuilder();

		// 进行权限过滤，多个角色权限范围之间为或者关系。
		List<String> dataScope = Lists.newArrayList();

		// 超级管理员，跳过权限过滤
		if (!user.isAdmin()){
			boolean isDataScopeAll = false;
			for (Role r : user.getRoleList()){
				for (String oa : StringUtils.split(officeAlias, ",")){
					if (!dataScope.contains(r.getDataScope()) && StringUtils.isNotBlank(oa)){
						if (Role.DATA_SCOPE_ALL.equals(r.getDataScope())){
							isDataScopeAll = true;
						}
						//所在部门及以下数据时  改为所在公司及以下数据
						else if (Role.DATA_SCOPE_OFFICE_AND_CHILD.equals(r.getDataScope())){
							sqlString.append(" OR " + oa + ".id = '" + user.getCompany().getId() + "'");
							sqlString.append(" OR " + oa + ".parent_ids LIKE '" + user.getCompany().getParentIds() + user.getCompany().getId() + ",%'");
						}
						else if (Role.DATA_SCOPE_COMPANY.equals(r.getDataScope())){
							sqlString.append(" OR " + oa + ".id = '" + user.getCompany().getId() + "'");
							// 包括本公司下的部门 （type=1:公司；type=2：部门）
							sqlString.append(" OR (" + oa + ".parent_id = '" + user.getCompany().getId() + "' AND " + oa + ".type = '2')");
						}
						else if (Role.DATA_SCOPE_OFFICE_AND_CHILD.equals(r.getDataScope())){
//							sqlString.append(" OR " + oa + ".id = '" + user.getOffice().getId() + "'");
//							sqlString.append(" OR " + oa + ".parent_ids LIKE '" + user.getOffice().getParentIds() + user.getOffice().getId() + ",%'");
						}
						else if (Role.DATA_SCOPE_OFFICE.equals(r.getDataScope())){
							sqlString.append(" OR " + oa + ".id = '" + user.getOffice().getId() + "'");
						}
						else if (Role.DATA_SCOPE_CUSTOM.equals(r.getDataScope())){
//							String officeIds =  StringUtils.join(r.getOfficeIdList(), "','");
//							if (StringUtils.isNotEmpty(officeIds)){
//								sqlString.append(" OR " + oa + ".id IN ('" + officeIds + "')");
//							}
							sqlString.append(" OR EXISTS (SELECT 1 FROM sys_role_office WHERE role_id = '" + r.getId() + "'");
							sqlString.append(" AND office_id = " + oa +".id)");
						}
						//else if (Role.DATA_SCOPE_SELF.equals(r.getDataScope())){
						dataScope.add(r.getDataScope());
					}
				}
			}
			// 如果没有全部数据权限，并设置了用户别名，则当前权限为本人；如果未设置别名，当前无权限为已植入权限
			if (!isDataScopeAll){
				if (StringUtils.isNotBlank(userAlias)){
					for (String ua : StringUtils.split(userAlias, ",")){
						sqlString.append(" OR " + ua + ".id = '" + user.getId() + "'");
					}
				}else {
					for (String oa : StringUtils.split(officeAlias, ",")){
						//sqlString.append(" OR " + oa + ".id  = " + user.getOffice().getId());
						sqlString.append(" OR " + oa + ".id IS NULL");
					}
				}
			}else{
				// 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
				sqlString = new StringBuilder();
			}
		}
		if (StringUtils.isNotBlank(sqlString.toString())){
			return " AND (" + sqlString.substring(4) + ")";
		}
		return "";
	}*/

	/*固定为  本部门及以下数据*/
	public static String officeAndChildFilter(User user, String officeAlias, String userAlias) {

		StringBuilder sqlString = new StringBuilder();

		// 进行权限过滤，多个角色权限范围之间为或者关系。
		List<String> dataScope = Lists.newArrayList();

		// 超级管理员，跳过权限过滤
		if (!user.isAdmin()){
			boolean isDataScopeAll = false;
			for (Role r : user.getRoleList()){
				for (String oa : StringUtils.split(officeAlias, ",")){
					if (!dataScope.contains(r.getDataScope()) && StringUtils.isNotBlank(oa)){
						sqlString.append(" OR " + oa + ".id = '" + user.getOffice().getId() + "'");
						sqlString.append(" OR " + oa + ".parent_ids LIKE '" + user.getOffice().getParentIds() + user.getOffice().getId() + ",%'");
					}
					dataScope.add(r.getDataScope());
				}
			}
			// 如果没有全部数据权限，并设置了用户别名，则当前权限为本人；如果未设置别名，当前无权限为已植入权限
			if (!isDataScopeAll){
				if (StringUtils.isNotBlank(userAlias)){
					for (String ua : StringUtils.split(userAlias, ",")){
						sqlString.append(" OR " + ua + ".id = '" + user.getId() + "'");
					}
				}else {
					for (String oa : StringUtils.split(officeAlias, ",")){
						//sqlString.append(" OR " + oa + ".id  = " + user.getOffice().getId());
						sqlString.append(" OR " + oa + ".id IS NULL");
					}
				}
			}else{
				// 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
				sqlString = new StringBuilder();
			}
		}
		if (StringUtils.isNotBlank(sqlString.toString())){
			return " AND (" + sqlString.substring(4) + ")";
		}
		return "";
	}


	public List<String> selectAllYear(){
		return affairPoliceThoughtAnalysisDao.selectAllYear();
	}
	public List<String> selectAllMonth(){
		return affairPoliceThoughtAnalysisDao.selectAllMonth();
	}
	public List<String> selectAllName(){
		return affairPoliceThoughtAnalysisDao.selectAllName();
	}
	public Integer selectNumber(String yearL,String yearT,String idNumber){
		return affairPoliceThoughtAnalysisDao.selectNumber(yearL,yearT,idNumber);
	}
}