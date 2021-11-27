/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairCreateWork;
import com.thinkgem.jeesite.modules.affair.dao.AffairCreateWorkDao;

/**
 * 创建工作Service
 * @author daniel.liu
 * @version 2020-07-06
 */
@Service
@Transactional(readOnly = true)
public class AffairCreateWorkService extends CrudService<AffairCreateWorkDao, AffairCreateWork> {

	public AffairCreateWork get(String id) {
		return super.get(id);
	}
	
	public List<AffairCreateWork> findList(AffairCreateWork affairCreateWork) {
		return super.findList(affairCreateWork);
	}
	
	public Page<AffairCreateWork> findPage(Page<AffairCreateWork> page, AffairCreateWork affairCreateWork) {
		affairCreateWork.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		/*改为台账形式*/
		String userId = UserUtils.getUser().getId();
		/*四个宣传管理账号查看本公司数据*/
		/*宣传思想管理的账号已设置为本公司及以下*/
		/*if (userId.equals("66937439b2124f328d1521968fab06db") || userId.equals("d154234ecb35470e84fb95e53726866b") ||
				userId.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || userId.equals("d30e324c8f73492d9b74103374fbc689")){
			affairCreateWork.getSqlMap().put("dsf", alterDataScopeFilter(UserUtils.getUser(), "o", "u"));
		}else {

			affairCreateWork.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		}*/

			affairCreateWork.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairCreateWork);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCreateWork affairCreateWork) {
		super.save(affairCreateWork);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCreateWork affairCreateWork) {
		super.delete(affairCreateWork);
	}

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
}