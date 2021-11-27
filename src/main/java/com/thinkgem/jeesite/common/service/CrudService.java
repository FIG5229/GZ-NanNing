/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.service;

import com.thinkgem.jeesite.common.elasticsearch.service.EsThingService;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelBaseDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service基类
 * @author ThinkGem
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService {

	@Autowired
	private EsThingService esThingService;

	@Autowired
	private PersonnelBaseDao personnelBaseDao;

	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return dao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity) {
		return dao.get(entity);
	}
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity) {
		return dao.findList(entity);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findList(entity));
		return page;
	}
	public Page<PersonnelBase> findPageCopy(Page<PersonnelBase> page, PersonnelBase personnelBase) {
		personnelBase.setPage(page);
		page.setList(personnelBaseDao.findListCopy(personnelBase));
		return page;
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findInPage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findInList(entity));
		return page;
	}
	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void save(T entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			dao.insert(entity);
			esThingService.add(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
			esThingService.update(entity);
		}
	}
	
	/**
	 * 删除数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(T entity) {
		entity.preUpdate();
		logger.error("删除不执行此处语句？？？？？？？？？？？？？？？");
		dao.delete(entity);
		esThingService.delete(entity);
	}

	/**
	 * 批量删除数据
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void deleteByIds(List<String> ids){
		esThingService.deleteIds(ids);
		dao.deleteByIds(ids);
	};

	/**
	 * 统计汇总
	 * @param entity
	 * @return
	 */
	public List<T> tongJi(T entity) {
		return dao.tongJi(entity);
	}
}
