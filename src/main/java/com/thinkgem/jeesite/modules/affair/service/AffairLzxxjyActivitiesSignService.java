/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairLzxxjyActivitiesSignDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLzxxjyActivitiesSign;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 廉政教育管理Service
 * @author cecil.li
 * @version 2020-06-28
 */
@Service
@Transactional(readOnly = true)
public class AffairLzxxjyActivitiesSignService extends CrudService<AffairLzxxjyActivitiesSignDao, AffairLzxxjyActivitiesSign> {

	@Autowired
	private AffairLzxxjyActivitiesSignDao affairLzxxjyActivitiesSignDao;

	public AffairLzxxjyActivitiesSign get(String id) {
		return super.get(id);
	}
	
	public List<AffairLzxxjyActivitiesSign> findList(AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign) {
		return super.findList(affairLzxxjyActivitiesSign);
	}
	
	public Page<AffairLzxxjyActivitiesSign> findPage(Page<AffairLzxxjyActivitiesSign> page, AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign, int flag) {
		page.setPageSize(1);
		if(flag == 1){
			affairLzxxjyActivitiesSign.setFlag("1");
		}else {
			affairLzxxjyActivitiesSign.setFlag("2");
		}
		return super.findPage(page, affairLzxxjyActivitiesSign);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign) {
		super.save(affairLzxxjyActivitiesSign);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign) {
		super.delete(affairLzxxjyActivitiesSign);
	}

	@Transactional(readOnly = false)
	public List<AffairLzxxjyActivitiesSign> findSign(AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign) {
		return affairLzxxjyActivitiesSignDao.findSign(affairLzxxjyActivitiesSign);
	}

	@Transactional(readOnly = false)
	public List<AffairLzxxjyActivitiesSign> findNotSign(AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign) {
		return affairLzxxjyActivitiesSignDao.findNotSign(affairLzxxjyActivitiesSign);
	}

	@Transactional(readOnly = false)
	public void urge(AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign) {
		AffairLzxxjyActivitiesSign old = super.get(affairLzxxjyActivitiesSign.getId());
		Integer urgeNUm = old.getUrge();
		if (urgeNUm == null){
			urgeNUm = 0;
		}
		old.setUrge(urgeNUm+1);
		affairLzxxjyActivitiesSignDao.update(old);
	}

	/**
	 * 批量签收
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void sign(List<String> ids) {
		for (String id : ids){
			signOne(id);
		}
	}

	@Transactional(readOnly = false)
	public void signOne(String id) {
		AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign = new AffairLzxxjyActivitiesSign();
		affairLzxxjyActivitiesSign.setNoticeId(id);
		affairLzxxjyActivitiesSign.setOrgId(UserUtils.getUser().getOffice().getId());
		List<AffairLzxxjyActivitiesSign> list = affairLzxxjyActivitiesSignDao.findList(affairLzxxjyActivitiesSign);
		if (list != null && list.size() > 0){
			AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign1 = list.get(0);
			affairLzxxjyActivitiesSign1.setSign("1");
			affairLzxxjyActivitiesSign1.setDate(new Date());
			affairLzxxjyActivitiesSignDao.update(affairLzxxjyActivitiesSign1);
		}
	}
}