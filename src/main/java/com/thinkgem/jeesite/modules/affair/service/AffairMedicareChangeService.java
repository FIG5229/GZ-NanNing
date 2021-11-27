/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairMedicareChangeDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairMedicareChange;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 医保关系变动Service
 * @author cecil.li
 * @version 2020-07-08
 */
@Service
@Transactional(readOnly = true)
public class AffairMedicareChangeService extends CrudService<AffairMedicareChangeDao, AffairMedicareChange> {

	public AffairMedicareChange get(String id) {
		return super.get(id);
	}
	
	public List<AffairMedicareChange> findList(AffairMedicareChange affairMedicareChange) {
		return super.findList(affairMedicareChange);
	}
	
	public Page<AffairMedicareChange> findPage(Page<AffairMedicareChange> page, AffairMedicareChange affairMedicareChange) {
		if (UserUtils.getUser().getId().equals("a58a91c7d4db4cd4b639c863c0e48832")){
			affairMedicareChange.setUserId(UserUtils.getUser().getCompany().getId());
		}else if ( UserUtils.getUser().getId().equals("e25def61e95f4864b15d203d17fcce79")){
			affairMedicareChange.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("8478b98cb7e249a2afe133bed5b5e5d8")){
			affairMedicareChange.setUserId("888");
		}else if ( UserUtils.getUser().getId().equals("76722f985c9e4ee7968fbd2a5d2feb2b")){
			affairMedicareChange.setUserId("999");
		}else if (UserUtils.getUser().getId().equals("73d8a1dc64874b9a8dca494db37488af")){
			affairMedicareChange.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("3e0a7e6b415a40fc976735050c253362")){
			affairMedicareChange.setUserId("888");
		}else if (UserUtils.getUser().getId().equals("13be1cd2f87046909e9a835ae52ec3d4")){
			affairMedicareChange.setUserId("999");
		}
		else {
			affairMedicareChange.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairMedicareChange);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairMedicareChange affairMedicareChange) {
		super.save(affairMedicareChange);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairMedicareChange affairMedicareChange) {
		super.delete(affairMedicareChange);
	}
	
}