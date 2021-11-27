/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairTemporaryPoliceDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTemporaryPolice;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 临时抽调民警管理Service
 * @author mason.xv
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class AffairTemporaryPoliceService extends CrudService<AffairTemporaryPoliceDao, AffairTemporaryPolice> {

	@Autowired
	private AffairTemporaryPoliceDao affairTemporaryPoliceDao;

	public AffairTemporaryPolice get(String id) {
		return super.get(id);
	}
	
	public List<AffairTemporaryPolice> findList(AffairTemporaryPolice affairTemporaryPolice) {
		return super.findList(affairTemporaryPolice);
	}
	
	public Page<AffairTemporaryPolice> findPage(Page<AffairTemporaryPolice> page, AffairTemporaryPolice affairTemporaryPolice) {
		affairTemporaryPolice.setUserId(UserUtils.getUser().getId());
		affairTemporaryPolice.setOfficeId(UserUtils.getUser().getOffice().getId());
		return super.findPage(page, affairTemporaryPolice);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTemporaryPolice affairTemporaryPolice) {
		super.save(affairTemporaryPolice);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTemporaryPolice affairTemporaryPolice) {
		super.delete(affairTemporaryPolice);
	}

	/**
	 * 批量提交
	 * @param ids
	 */
	public List<AffairTemporaryPolice> findByIds(List<String> ids){
		List<AffairTemporaryPolice> list = affairTemporaryPoliceDao.findByIds(ids);
		return list;
	}

	@Transactional(readOnly = false)
	public void revocation(String id, String checkType) {
		affairTemporaryPoliceDao.revocation(id,checkType);
	}
}