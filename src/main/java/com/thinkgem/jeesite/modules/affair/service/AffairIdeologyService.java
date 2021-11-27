/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairIdeologyDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairIdeology;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 意识形态Service
 * @author daniel.liu
 * @version 2020-06-22
 */
@Service
@Transactional(readOnly = true)
public class AffairIdeologyService extends CrudService<AffairIdeologyDao, AffairIdeology> {

	@Autowired
	private AffairIdeologyDao affairIdeologyDao;

	@Autowired
	private OfficeService officeService;

	public AffairIdeology get(String id) {
		return super.get(id);
	}
	
	public List<AffairIdeology> findList(AffairIdeology affairIdeology) {
		return super.findList(affairIdeology);
	}

	@Transactional(readOnly = false)
	public Page<AffairIdeology> findPage(Page<AffairIdeology> page, AffairIdeology affairIdeology) {
        /*if (StringUtils.isBlank(affairIdeology.getReportType())){
            affairIdeology.setReportType("1");
        }
        if (affairIdeology.getYear()==null){
            Calendar date = Calendar.getInstance();
            affairIdeology.setYear(date.get(Calendar.YEAR));
        }*/
		/*签收 本公司及以下*/
		/*需求变更
		* 局查看时，值查看三个宣教处数据，查不到则添加 设置为未上报
		* 处查看时，查看处直属单位，查不到则添加，设置为未上报*/
		String companyId = UserUtils.getUser().getCompany().getId();
		String officeId = UserUtils.getUser().getOffice().getId();
		String userId = UserUtils.getUser().getId();
		if (affairIdeology.getHasAuth()){
			affairIdeology.setHasAuth(false);
			if (companyId.equals("1")){
				affairIdeology.getSqlMap().put("dsf","and o.id in ('29','144','265') and unit_id in ('34','95','156')");
			}else if (officeId.equals("29") || officeId.equals("144") || officeId.equals("265")){
				affairIdeology.getSqlMap().put("dsf", "and o.parent_id = '"+companyId+"'");
			}else {
				affairIdeology.getSqlMap().put("dsf", alterDataScopeFilter(UserUtils.getUser(), "o", "u"));
			}
		}else {
			/*上报时  查看本部门及以下*/
//			affairIdeology.getSqlMap().put("dsf", officeAndChildFilter(UserUtils.getUser(), "o", "u"));
                affairIdeology.getSqlMap().put("dsf", "and( o.id = '"+officeId+"' and u.id = '"+userId+"') and unit_id not in ('34','95','156')");
                return super.findPage(page, affairIdeology);
		}
		Page<AffairIdeology> ideologyPage = super.findPage(page, affairIdeology);
		/*公安处党委页面添加*/
		if (companyId.equals("1")){
			String[] ids = new String[]{"29","144","265"};
			Map<String,String> map = new HashMap<>();
			map.put("29","南宁处政治处宣传教育室（民警训练基地）");
			map.put("144","柳州处政治处宣传教育室（民警训练基地）");
			map.put("265","北海处政治处宣传教育室（民警训练基地）");

			for (String id : ids) {

				boolean has = false;
				List<AffairIdeology> list = page.getList();
				for(int i =0;i<list.size();i++){
					if (list.get(i).getUnitId().equals(id)){
						has=true;
					}
				}
				if (!has){
					AffairIdeology ideology = new AffairIdeology();
					ideology.setUnitId(id);
					ideology.setUnit(map.get(id));
					ideology.setReportStatus("1");
					Date date = DateUtils.parseDate(String.valueOf(affairIdeology.getYear())+ Calendar.getInstance().get(Calendar.MONTH) + Calendar.getInstance().get(Calendar.DATE));
					ideology.setReportTime(date);
					ideology.setReportType(affairIdeology.getReportType());
					ideology.preInsert();
					ideology.getCreateBy().getOffice().setId(id);
					affairIdeologyDao.insert(ideology);
					ideology.getCreateBy().getOffice().setId(officeId);
					list.add(ideology);
				}
			}

			/*基层党支部添加*/
		} else if (officeId.equals("29") || officeId.equals("144") || officeId.equals("265")) {
			List<String> ids = officeService.findIdsByParentId(companyId);
			ids.forEach(s -> {
				boolean has = false;
				List<AffairIdeology> list = page.getList();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getUnitId().equals(s)) {
						has = true;
					}
				}
				if (s.equals("29") || s.equals("144") || s.equals("265") ){
					has = true;
				}
				if (!has) {
					AffairIdeology ideology = new AffairIdeology();
					ideology.setUnitId(s);
					ideology.setUnit(officeService.findNameById(s));
					ideology.setReportStatus("1");
                    Date date = DateUtils.parseDate(String.valueOf(affairIdeology.getYear())+ Calendar.getInstance().get(Calendar.MONTH) + Calendar.getInstance().get(Calendar.DATE));
					ideology.setReportTime(date);
//					ideology.setYear(affairIdeology.getYear());
					ideology.setReportType(affairIdeology.getReportType());
					ideology.preInsert();
					ideology.getCreateBy().getOffice().setId(s);
					affairIdeologyDao.insert(ideology);
					ideology.getCreateBy().getOffice().setId(officeId);
					list.add(ideology);
				}

			});
		}
		return ideologyPage;
	}
	
	@Transactional(readOnly = false)
	public void save(AffairIdeology affairIdeology) {
		super.save(affairIdeology);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairIdeology affairIdeology) {
		super.delete(affairIdeology);
	}

	/*使用baseService  此方法只针对查看本部门及以下时使用*/
	/*public static String alterDataScopeFilter(User user, String officeAlias, String userAlias) {

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

	/*数据过滤范围固定为 本部门及以下*/
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

	// 绩效自动考评
	public int findCountByYear(String year, String unit, String unitId){
		return affairIdeologyDao.findCountByYear(year,unit,unitId);
	}

	public Integer selectNumber(String yearL,String yearT,String unitId){
		return affairIdeologyDao.selectNumber(yearL,yearT,unitId);
	}

	public Page<AffairIdeology> juPage(Page<AffairIdeology> page, AffairIdeology affairIdeology) {
		affairIdeology.setPage(page);
		page.setList(affairIdeologyDao.juList(affairIdeology));
		return page;
	}

	public Page<AffairIdeology> chuList(Page<AffairIdeology> page, AffairIdeology affairIdeology) {
		affairIdeology.setPage(page);
		page.setList(affairIdeologyDao.chuList(affairIdeology));
		return page;
	}

}