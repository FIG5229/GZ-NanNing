/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairLaborOfficeDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairTuixiuDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLaborOffice;
import com.thinkgem.jeesite.modules.affair.entity.AffairTuixiu;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 退休Service
 * @author cecil.li
 * @version 2020-07-02
 */
@Service
@Transactional(readOnly = true)
public class AffairTuixiuService extends CrudService<AffairTuixiuDao, AffairTuixiu> {

	@Autowired
	private OfficeDao officeDao;

	@Autowired
	private AffairTuixiuDao affairTuixiuDao;

	@Autowired
	private AffairLaborOfficeDao affairLaborOfficeDao;

	public AffairTuixiu get(String id) {
		return super.get(id);
	}
	
	public List<AffairTuixiu> findList(AffairTuixiu affairTuixiu) {
		affairTuixiu.setUserId(UserUtils.getUser().getOffice().getId());
		affairTuixiu.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(affairTuixiu);
	}
	
	public Page<AffairTuixiu> findPage(Page<AffairTuixiu> page, AffairTuixiu affairTuixiu) {
		if (UserUtils.getUser().getId().equals("a58a91c7d4db4cd4b639c863c0e48832")){
			affairTuixiu.setUserId(UserUtils.getUser().getCompany().getId());
		}else if ( UserUtils.getUser().getId().equals("e25def61e95f4864b15d203d17fcce79")){
			affairTuixiu.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("8478b98cb7e249a2afe133bed5b5e5d8")){
			affairTuixiu.setUserId("888");
		}else if ( UserUtils.getUser().getId().equals("76722f985c9e4ee7968fbd2a5d2feb2b")){
			affairTuixiu.setUserId("999");
		}else if (UserUtils.getUser().getId().equals("73d8a1dc64874b9a8dca494db37488af")){
			affairTuixiu.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("3e0a7e6b415a40fc976735050c253362")){
			affairTuixiu.setUserId("888");
		}else if (UserUtils.getUser().getId().equals("13be1cd2f87046909e9a835ae52ec3d4")){
			affairTuixiu.setUserId("999");
		}
		else {
			affairTuixiu.setUserId(UserUtils.getUser().getOffice().getId());
		}
		List<AffairLaborOffice> childIds1 = affairLaborOfficeDao.findChildById(affairTuixiu.getTabFlag());
		ArrayList<String> officeIds = new ArrayList<>();
		for (AffairLaborOffice office:childIds1) {
			officeIds.add(office.getId());
		}
		/*List<Office> childIds1 = officeDao.findChildById(affairTuixiu.getTabFlag());
		ArrayList<String> officeIds = new ArrayList<>();
		for (Office office:childIds1) {
			officeIds.add(office.getId());
		}*/
		affairTuixiu.setOfficeIds(officeIds);
		return super.findPage(page, affairTuixiu);
	}

	public List<Map<String, String>> tuiList() {
		return affairTuixiuDao.tuiList();
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTuixiu affairTuixiu) {
		super.save(affairTuixiu);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTuixiu affairTuixiu) {
		super.delete(affairTuixiu);
	}

	@Transactional(readOnly = false)
	public void queDing(String id) {
		 affairTuixiuDao.queDing(id);
	}

	public List<String> findAllId(){
		return affairTuixiuDao.findAllId();
	}
}