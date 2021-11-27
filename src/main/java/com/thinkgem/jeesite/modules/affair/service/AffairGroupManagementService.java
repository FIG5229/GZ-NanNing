/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairGroupManagementDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGroupManagement;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SysOfficesService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 团费收缴Service
 * @author cecil.li
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class AffairGroupManagementService extends CrudService<AffairGroupManagementDao, AffairGroupManagement> {

	@Autowired
	private SysOfficesService sysOfficesService;

	public AffairGroupManagement get(String id) {
		return super.get(id);
	}
	
	public List<AffairGroupManagement> findList(AffairGroupManagement affairGroupManagement) {
		return super.findList(affairGroupManagement);
	}
	
	public Page<AffairGroupManagement> findPage(Page<AffairGroupManagement> page, AffairGroupManagement affairGroupManagement) {
		String groupIb = sysOfficesService.findGroupIbById(UserUtils.getUser().getOffice().getId());
		if (StringUtils.isNotBlank(groupIb)){
			affairGroupManagement.setUserGroup(groupIb);
		}
		if ("116e598c93ce48c2844925c2d01a73cd".equals(UserUtils.getUser().getId()) || "c9071b9148e540f8805563167febb5b5".equals(UserUtils.getUser().getId()) || "e9dcd22e65874cceb545589f597ebfcd".equals(UserUtils.getUser().getId()) || "1a684b36a7404c71b23aabf052cba815".equals(UserUtils.getUser().getId())){
			affairGroupManagement.setUserOffice((UserUtils.getUser().getCompany().getId()));
		}else {
			affairGroupManagement.setUserOffice((UserUtils.getUser().getOffice().getId()));
		}
		page.setOrderBy("a.payment_time DESC,a.group_id ,a.id DESC");
		/*affairGroupManagement.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/
		User user = UserUtils.getUser();
		affairGroupManagement.setCreateBy(user);
//		affairGroupManagement.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairGroupManagement);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairGroupManagement affairGroupManagement) {
		super.save(affairGroupManagement);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairGroupManagement affairGroupManagement) {
		super.delete(affairGroupManagement);
	}

	/**
	 * 统计分析 团费收缴明细
	 * @param page
	 * @param groupManagement
	 * @return
	 */
	public Page<AffairGroupManagement> findFeeDetailPage(Page<AffairGroupManagement> page, AffairGroupManagement groupManagement) {
		String dateType = Optional.ofNullable(groupManagement.getDateType()).orElseGet(() -> {return "";});
		switch (dateType) {
			case "1":    //月度
				groupManagement.setYear(null);
				groupManagement.setStartDate(null);
				groupManagement.setEndDate(null);
				break;
			case "3":    //时间段
				groupManagement.setYear(null);
				groupManagement.setStartDate(DateUtils.parseDate(groupManagement.getDateStart()));
				groupManagement.setEndDate(DateUtils.parseDate(groupManagement.getDateEnd()));
				groupManagement.setMonth(null);
				break;
			default:    //年度
				groupManagement.setMonth(null);
				groupManagement.setStartDate(null);
				groupManagement.setEndDate(null);
				break;
		}
		String officeId = UserUtils.getUser().getOffice().getId();
		/*借用payee 存放officeId*/
		groupManagement.setPayee(officeId);
		groupManagement.setPage(page);
		page.setList(dao.findFeeDetailList(groupManagement));
		return page;
	}
}