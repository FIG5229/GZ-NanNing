/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairInjuriesDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairInjuries;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 因公负伤Service
 * @author cecil.li
 * @version 2020-07-03
 */
@Service
@Transactional(readOnly = true)
public class AffairInjuriesService extends CrudService<AffairInjuriesDao, AffairInjuries> {

	public AffairInjuries get(String id) {
		return super.get(id);
	}
	
	public List<AffairInjuries> findList(AffairInjuries affairInjuries) {
		return super.findList(affairInjuries);
	}
	
	public Page<AffairInjuries> findPage(Page<AffairInjuries> page, AffairInjuries affairInjuries) {
		if (UserUtils.getUser().getId().equals("a58a91c7d4db4cd4b639c863c0e48832")){
			affairInjuries.setUserId(UserUtils.getUser().getCompany().getId());
		}else if ( UserUtils.getUser().getId().equals("e25def61e95f4864b15d203d17fcce79")){
			affairInjuries.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("8478b98cb7e249a2afe133bed5b5e5d8")){
			affairInjuries.setUserId("888");
		}else if ( UserUtils.getUser().getId().equals("76722f985c9e4ee7968fbd2a5d2feb2b")){
			affairInjuries.setUserId("999");
		}else if (UserUtils.getUser().getId().equals("73d8a1dc64874b9a8dca494db37488af")){
			affairInjuries.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("3e0a7e6b415a40fc976735050c253362")){
			affairInjuries.setUserId("888");
		}else if (UserUtils.getUser().getId().equals("13be1cd2f87046909e9a835ae52ec3d4")){
			affairInjuries.setUserId("999");
		}
		else {
			affairInjuries.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairInjuries);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairInjuries affairInjuries) {
		super.save(affairInjuries);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairInjuries affairInjuries) {
		super.delete(affairInjuries);
	}
	
}