/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairTeamDisciplineSignDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTeamDisciplineSign;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 纪律作风教育整顿Service
 * @author cecil.li
 * @version 2020-06-16
 */
@Service
@Transactional(readOnly = true)
public class AffairTeamDisciplineSignService extends CrudService<AffairTeamDisciplineSignDao, AffairTeamDisciplineSign> {

	@Autowired
	private AffairTeamDisciplineSignDao affairTeamDisciplineSignDao;

	public AffairTeamDisciplineSign get(String id) {
		return super.get(id);
	}
	
	public List<AffairTeamDisciplineSign> findList(AffairTeamDisciplineSign affairTeamDisciplineSign) {
		return super.findList(affairTeamDisciplineSign);
	}
	
	public Page<AffairTeamDisciplineSign> findPage(Page<AffairTeamDisciplineSign> page, AffairTeamDisciplineSign affairTeamDisciplineSign, int flag) {
		page.setPageSize(1);
		if(flag == 1){
			affairTeamDisciplineSign.setFlag("1");
		}else {
			affairTeamDisciplineSign.setFlag("2");
		}
		return super.findPage(page, affairTeamDisciplineSign);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTeamDisciplineSign affairTeamDisciplineSign) {
		super.save(affairTeamDisciplineSign);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTeamDisciplineSign affairTeamDisciplineSign) {
		super.delete(affairTeamDisciplineSign);
	}

	@Transactional(readOnly = false)
	public List<AffairTeamDisciplineSign> findSign(AffairTeamDisciplineSign affairTeamDisciplineSign) {
		return affairTeamDisciplineSignDao.findSign(affairTeamDisciplineSign);
	}

	@Transactional(readOnly = false)
	public List<AffairTeamDisciplineSign> findNotSign(AffairTeamDisciplineSign affairTeamDisciplineSign) {
		return affairTeamDisciplineSignDao.findNotSign(affairTeamDisciplineSign);
	}

	@Transactional(readOnly = false)
	public void urge(AffairTeamDisciplineSign affairTeamDisciplineSign) {
		AffairTeamDisciplineSign old = super.get(affairTeamDisciplineSign.getId());
		Integer urgeNUm = old.getUrge();
		if (urgeNUm == null){
			urgeNUm = 0;
		}
		old.setUrge(urgeNUm+1);
		affairTeamDisciplineSignDao.update(old);
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
		AffairTeamDisciplineSign affairTeamDisciplineSign = new AffairTeamDisciplineSign();
		affairTeamDisciplineSign.setNoticeId(id);
		affairTeamDisciplineSign.setOrgId(UserUtils.getUser().getOffice().getId());
		List<AffairTeamDisciplineSign> list = affairTeamDisciplineSignDao.findList(affairTeamDisciplineSign);
		if (list != null && list.size() > 0){
			AffairTeamDisciplineSign affairTeamDisciplineSign1 = list.get(0);
			affairTeamDisciplineSign1.setSign("1");
			affairTeamDisciplineSign1.setDate(new Date());
			affairTeamDisciplineSignDao.update(affairTeamDisciplineSign1);
		}
	}
	
}