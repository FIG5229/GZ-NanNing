/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.affair.dao.AffairDocClassifyRangeDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDocClassifyRange;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairDocClassify;
import com.thinkgem.jeesite.modules.affair.dao.AffairDocClassifyDao;

/**
 * 文档分类Service
 * @author kevin.jia
 * @version 2020-07-29
 */
@Service
@Transactional(readOnly = true)
public class AffairDocClassifyService extends CrudService<AffairDocClassifyDao, AffairDocClassify> {
	@Autowired
	AffairDocClassifyDao affairDocClassifyDao;

	@Autowired
	AffairDocClassifyRangeDao affairDocClassifyRangeDao;

	public AffairDocClassify get(String id) {
		return super.get(id);
	}
	
	public List<AffairDocClassify> findList(AffairDocClassify affairDocClassify) {
		return super.findList(affairDocClassify);
	}
	
	public Page<AffairDocClassify> findPage(Page<AffairDocClassify> page, AffairDocClassify affairDocClassify) {
		return super.findPage(page, affairDocClassify);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairDocClassify affairDocClassify) {
		super.save(affairDocClassify);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairDocClassify affairDocClassify) {
		super.delete(affairDocClassify);
	}

    public List<AffairDocClassify> findTreeData() {
		AffairDocClassify affairDocClassify = new AffairDocClassify();
		return affairDocClassifyDao.findTreeData(affairDocClassify);
    }
	/*
	 * 根据id查找该分类的parentIds
	 * */
	public AffairDocClassify getParentIdsById(String id) {
		return affairDocClassifyDao.getParentIdsById(id);
	}
	/*
	* 根据临时id获取文档分类id
	* */
	public String getByTempId(String tempId) {
		return affairDocClassifyDao.getByTempId(tempId);
	}
}