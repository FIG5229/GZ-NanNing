/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairCommentatorsDeputyDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCommentatorsDeputy;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairCommentators;
import com.thinkgem.jeesite.modules.affair.dao.AffairCommentatorsDao;

/**
 * 网评员管理Service
 * @author alan.wu
 * @version 2020-06-19
 */
@Service
@Transactional(readOnly = true)
public class AffairCommentatorsService extends CrudService<AffairCommentatorsDao, AffairCommentators> {

	@Autowired
	AffairCommentatorsDeputyDao affairCommentatorsDeputyDao;

	public AffairCommentators get(String id) {
		return super.get(id);
	}
	
	public List<AffairCommentators> findList(AffairCommentators affairCommentators) {
		return super.findList(affairCommentators);
	}
	
	public Page<AffairCommentators> findPage(Page<AffairCommentators> page, AffairCommentators affairCommentators) {
/*
		affairCommentators.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
*/
		affairCommentators.setUnitId(UserUtils.getUser().getOffice().getId());
		affairCommentators.setUserId(UserUtils.getUser().getId());
		return super.findPage(page, affairCommentators);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCommentators affairCommentators) {
		super.save(affairCommentators);

	/*	//遍历账号情况
		for (AffairCommentatorsDeputy item : affairCommentators.getAffairCommentatorsDeputies()) {
			if (item.getId() == null) {
				continue;
			}
			if (AffairCommentators.DEL_FLAG_NORMAL.equals(item.getDelFlag())) {
				if (StringUtils.isBlank(item.getId())) {
					item.setIdNumber(affairCommentators.getIdNumber());
					item.preInsert();
					affairCommentatorsDeputyDao.insert(item);
				} else {
					item.preUpdate();
					affairCommentatorsDeputyDao.update(item);
				}
			} else {
				affairCommentatorsDeputyDao.delete(item);
			}
		}*/

		if (affairCommentators.getAffairCommentatorsDeputies()!=null){

			for (AffairCommentatorsDeputy item : affairCommentators.getAffairCommentatorsDeputies()) {
				if (item.getId() == null) {
					continue;
				}
				if (AffairCommentatorsDeputy.DEL_FLAG_NORMAL.equals(item.getDelFlag())) {
					if (StringUtils.isBlank(item.getId())) {
						item.setId(affairCommentators.getId());
						affairCommentatorsDeputyDao.insert(item);
					} else {
						item.preUpdate();
						item.setId(affairCommentators.getId());
						affairCommentatorsDeputyDao.update(item);
					}
				} else {
					affairCommentatorsDeputyDao.delete(item);
				}
			}
		}

	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCommentators affairCommentators) {
		super.delete(affairCommentators);
	}
	
}