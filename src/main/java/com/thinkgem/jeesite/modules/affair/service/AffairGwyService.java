/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairGwyDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGwy;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 公务员一次性抚恤Service
 * @author cecil.li
 * @version 2020-07-02
 */
@Service
@Transactional(readOnly = true)
public class AffairGwyService extends CrudService<AffairGwyDao, AffairGwy> {

	public AffairGwy get(String id) {
		return super.get(id);
	}
	
	public List<AffairGwy> findList(AffairGwy affairGwy) {
		return super.findList(affairGwy);
	}
	
	public Page<AffairGwy> findPage(Page<AffairGwy> page, AffairGwy affairGwy) {
		if (UserUtils.getUser().getId().equals("a58a91c7d4db4cd4b639c863c0e48832")){
			affairGwy.setUserId(UserUtils.getUser().getCompany().getId());
		}else if ( UserUtils.getUser().getId().equals("e25def61e95f4864b15d203d17fcce79")){
			affairGwy.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("8478b98cb7e249a2afe133bed5b5e5d8")){
			affairGwy.setUserId("888");
		}else if ( UserUtils.getUser().getId().equals("76722f985c9e4ee7968fbd2a5d2feb2b")){
			affairGwy.setUserId("999");
		}else if (UserUtils.getUser().getId().equals("73d8a1dc64874b9a8dca494db37488af")){
			affairGwy.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("3e0a7e6b415a40fc976735050c253362")){
			affairGwy.setUserId("888");
		}else if (UserUtils.getUser().getId().equals("13be1cd2f87046909e9a835ae52ec3d4")){
			affairGwy.setUserId("999");
		}
		else {
			affairGwy.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairGwy);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairGwy affairGwy) {
		super.save(affairGwy);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairGwy affairGwy) {
		super.delete(affairGwy);
	}
	
}