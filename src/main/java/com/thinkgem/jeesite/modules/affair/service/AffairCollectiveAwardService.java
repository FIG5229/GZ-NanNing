/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairCollectiveAwardDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCollectiveAward;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 工会集体表彰Service
 * @author cecil.li
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class AffairCollectiveAwardService extends CrudService<AffairCollectiveAwardDao, AffairCollectiveAward> {

	@Autowired
	private AffairCollectiveAwardDao affairCollectiveAwardDao;

	public AffairCollectiveAward get(String id) {
		return super.get(id);
	}
	
	public List<AffairCollectiveAward> findList(AffairCollectiveAward affairCollectiveAward) {
		return super.findList(affairCollectiveAward);
	}
	
	public Page<AffairCollectiveAward> findPage(Page<AffairCollectiveAward> page, AffairCollectiveAward affairCollectiveAward) {
		/*affairCollectiveAward.setUserId(UserUtils.getUser().getOffice().getId());
		affairCollectiveAward.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/
		if (UserUtils.getUser().getId().equals("4c40b4dd2aee459286a37538978e6261") || UserUtils.getUser().getId().equals("cb97bf7e17d549f09b51375a98a8eb55") || UserUtils.getUser().getId().equals("bdab6b25fc694e74bf7c3d1363c0062e") || UserUtils.getUser().getId().equals("abb70c88ca9d42758c489838fdd0e33b")){
			affairCollectiveAward.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairCollectiveAward.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairCollectiveAward);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCollectiveAward affairCollectiveAward) {
		super.save(affairCollectiveAward);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCollectiveAward affairCollectiveAward) {
		super.delete(affairCollectiveAward);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}

	public List<AffairCollectiveAward> selectList(String orgId){
		return affairCollectiveAwardDao.selectList(orgId);
	}

	public AffairCollectiveAward selectBean(String id){
		return affairCollectiveAwardDao.selectBean(id);
	}
}