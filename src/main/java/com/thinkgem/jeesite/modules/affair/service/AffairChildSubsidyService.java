/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairChildSubsidyDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairChildSubsidy;
import com.thinkgem.jeesite.modules.affair.entity.AffairChildSubsidyChild;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 幼儿补助Service
 * @author cecil.li
 * @version 2019-12-03
 */
@Service
@Transactional(readOnly = true)
public class AffairChildSubsidyService extends CrudService<AffairChildSubsidyDao, AffairChildSubsidy> {

	public AffairChildSubsidy get(String id) {
		return super.get(id);
	}

	public List<AffairChildSubsidy> findList(AffairChildSubsidy affairChildSubsidy) {
		return super.findList(affairChildSubsidy);
	}

	public Page<AffairChildSubsidy> findPage(Page<AffairChildSubsidy> page, AffairChildSubsidy affairChildSubsidy) {
		if (UserUtils.getUser().getId().equals("4c40b4dd2aee459286a37538978e6261") || UserUtils.getUser().getId().equals("cb97bf7e17d549f09b51375a98a8eb55") || UserUtils.getUser().getId().equals("bdab6b25fc694e74bf7c3d1363c0062e") || UserUtils.getUser().getId().equals("abb70c88ca9d42758c489838fdd0e33b")){
			affairChildSubsidy.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairChildSubsidy.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairChildSubsidy);
	}

	@Transactional(readOnly = false)
	public void save(AffairChildSubsidy affairChildSubsidy) {
		super.save(affairChildSubsidy);
	}

	@Transactional(readOnly = false)
	public void delete(AffairChildSubsidy affairChildSubsidy) {
		super.delete(affairChildSubsidy);
	}

	public List<Map<String, Object>> findInfoByCreateOrgId(String id, Integer year, Date mStartDate, Date mEndDate) {
		return dao.findInfoByCreateOrgId(id, year, mStartDate, mEndDate);
	}

	public List<Map<String, Object>> findInfoByCreateOrgIds(List<String> ids, Integer year, Date mStartDate, Date mEndDate) {
		return dao.findInfoByCreateOrgIds(ids, year, mStartDate, mEndDate);
	}

	/**
	 * 遍历子女列表进行保存
	 * @param affairChildSubsidy
	 */
	@Transactional(readOnly = false)
	public void addSave(AffairChildSubsidy affairChildSubsidy) {
		if (affairChildSubsidy.getAffairChildSubsidyChildList() != null && affairChildSubsidy.getAffairChildSubsidyChildList().size() > 0) {
			for (AffairChildSubsidyChild affairChildSubsidyChild : affairChildSubsidy.getAffairChildSubsidyChildList()) {
				affairChildSubsidy.setChildName(affairChildSubsidyChild.getChildName());
				affairChildSubsidy.setChildSex(affairChildSubsidyChild.getChildSex());
				affairChildSubsidy.setChildBirthDay(affairChildSubsidyChild.getChildBirthDay());
				affairChildSubsidy.setChildNow(affairChildSubsidyChild.getChildNow());
				affairChildSubsidy.setId(null);
				super.save(affairChildSubsidy);
			}
		} else {
			super.save(affairChildSubsidy);

		}
	}



}