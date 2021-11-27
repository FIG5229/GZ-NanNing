/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairLaborOfficeDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLaborOffice;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 劳资组织树Service
 * @author cecil.li
 * @version 2020-11-08
 */
@Service
@Transactional(readOnly = true)
public class AffairLaborOfficeService extends CrudService<AffairLaborOfficeDao, AffairLaborOffice> {
	@Autowired
	private AffairLaborOfficeDao affairLaborOfficeDao;
/*
	public AffairLaborOffice get(String id) {
		return super.get(id);
	}
	
	public List<AffairLaborOffice> findList(AffairLaborOffice affairLaborOffice) {
		return super.findList(affairLaborOffice);
	}
	
	public Page<AffairLaborOffice> findPage(Page<AffairLaborOffice> page, AffairLaborOffice affairLaborOffice) {
		return super.findPage(page, affairLaborOffice);
	}*/

	public List<AffairLaborOffice> findAll(){
		return UserUtils.getLaborOfficeList();
	}

	public List<AffairLaborOffice> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getLaborOfficeAllList();
		}else{
			return UserUtils.getLaborOfficeList();
		}
	}

	@Transactional(readOnly = true)
	public List<AffairLaborOffice> findList(AffairLaborOffice affairLaborOffice){
		/*if(affairLaborOffice != null){
			affairLaborOffice.setParentIds(affairLaborOffice.getParentIds()+"%");
			return affairLaborOfficeDao.findByParentIdsLike(affairLaborOffice);
		}*/
		if (UserUtils.getUser().getId().equals("a58a91c7d4db4cd4b639c863c0e48832")){
			affairLaborOffice.setUserId(UserUtils.getUser().getCompany().getId());
		}else if ( UserUtils.getUser().getId().equals("e25def61e95f4864b15d203d17fcce79")){
			affairLaborOffice.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("8478b98cb7e249a2afe133bed5b5e5d8")){
			affairLaborOffice.setUserId("888");
		}else if ( UserUtils.getUser().getId().equals("76722f985c9e4ee7968fbd2a5d2feb2b")){
			affairLaborOffice.setUserId("999");
		}else if (UserUtils.getUser().getId().equals("73d8a1dc64874b9a8dca494db37488af")){
			affairLaborOffice.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("3e0a7e6b415a40fc976735050c253362")){
			affairLaborOffice.setUserId("888");
		}else if (UserUtils.getUser().getId().equals("13be1cd2f87046909e9a835ae52ec3d4")){
			affairLaborOffice.setUserId("999");
		}
		else {
			affairLaborOffice.setUserId(UserUtils.getUser().getOffice().getId());
		}

		return  affairLaborOfficeDao.findList(affairLaborOffice);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairLaborOffice affairLaborOffice) {
		super.save(affairLaborOffice);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairLaborOffice affairLaborOffice) {
		super.delete(affairLaborOffice);
	}

	public String findNameById(@Param(value = "id") String id){
		return affairLaborOfficeDao.findNameById(id);
	}

	/**
	 * 根据机构名字查找机构id
	 * @param name
	 * @return
	 */
	public String findByName(String name) {
		if(!StringUtils.isNotBlank(name)){
			return null;
		}
		List<String> list = affairLaborOfficeDao.findListByName(name);
		if(list != null && list.size() >0){
			return list.get(0);
		}
		return null;
	}
	
}