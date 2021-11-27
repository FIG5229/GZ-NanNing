/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairLzxxjyActivitiesDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairLzxxjyActivitiesSignDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLzxxjyActivities;
import com.thinkgem.jeesite.modules.affair.entity.AffairLzxxjyActivitiesSign;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 廉政学习教育活动Service
 * @author cecil.li
 * @version 2019-11-08
 */
@Service
@Transactional(readOnly = true)
public class AffairLzxxjyActivitiesService extends CrudService<AffairLzxxjyActivitiesDao, AffairLzxxjyActivities> {

	@Autowired
	private AffairLzxxjyActivitiesSignDao affairLzxxjyActivitiesSignDao;

	@Autowired
	private AffairLzxxjyActivitiesDao affairLzxxjyActivitiesDao;

	@Autowired
	private OfficeDao officeDao;

	public AffairLzxxjyActivities get(String id) {
		return super.get(id);
	}
	
	public List<AffairLzxxjyActivities> findList(AffairLzxxjyActivities affairLzxxjyActivities) {
		return super.findList(affairLzxxjyActivities);
	}
	
	public Page<AffairLzxxjyActivities> findPage(Page<AffairLzxxjyActivities> page, AffairLzxxjyActivities affairLzxxjyActivities) {
		/*affairLzxxjyActivities.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairLzxxjyActivities);*/

		User user = UserUtils.getUser();
		//存放当前用户
		affairLzxxjyActivities.setCreateBy(user);
		affairLzxxjyActivities.getSqlMap().put("dsf", dataScopeFilter(user, "o", "u"));
		Page<AffairLzxxjyActivities> Page = super.findPage(page, affairLzxxjyActivities);
		if (affairLzxxjyActivities.isHasAuth() == true){
			if(Page.getList() != null && Page.getList().size() >0){
				for (AffairLzxxjyActivities a : Page.getList()){
					if(!"1".equals(a.getStatus()) && !"3".equals(a.getStatus())){//1:未发布  3:取消发布
						Integer signNum = affairLzxxjyActivitiesDao.findSignNum(a);
						Integer sumNum = affairLzxxjyActivitiesDao.findSumNum(a);
						a.setSignNum(signNum);
						a.setSumNum(sumNum);
					}
				}
			}
		}
		return Page;
	}
	
	@Transactional(readOnly = false)
	public void save(AffairLzxxjyActivities affairLzxxjyActivities) {
//		super.save(affairLzxxjyActivities);
		affairLzxxjyActivities.setPublisher(UserUtils.getUser().getName());
		//affairFileNotice.setPublishOrgId(UserUtils.getUser().getOffice().getId());
		super.save(affairLzxxjyActivities);
		if("2".equals(affairLzxxjyActivities.getStatus())){//发布
			String receiveDepIds = affairLzxxjyActivities.getReceiveDepId();
			//先删除关联表原来的数据
			affairLzxxjyActivitiesSignDao.deleteByNoticeId(affairLzxxjyActivities.getId());
			//关联表插入新的数据
			List<String> idList = Arrays.asList(receiveDepIds.split(","));
			for (String id : idList) {
				AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign = new AffairLzxxjyActivitiesSign();
				affairLzxxjyActivitiesSign.setNoticeId(affairLzxxjyActivities.getId());
				affairLzxxjyActivitiesSign.setOrgId(id);
				//单位名称
				Office office = officeDao.findOneById(id);
				affairLzxxjyActivitiesSign.setUnit(office.getName());
				affairLzxxjyActivitiesSign.preInsert();
				affairLzxxjyActivitiesSignDao.insert(affairLzxxjyActivitiesSign);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairLzxxjyActivities affairLzxxjyActivities) {
		super.delete(affairLzxxjyActivities);
		//删除关联表
		affairLzxxjyActivitiesSignDao.deleteByNoticeId(affairLzxxjyActivities.getId());
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	@Override
	@Transactional(readOnly = false)
	public void deleteByIds(List<String> ids) {
		//删除主表
		super.deleteByIds(ids);
		//删除关联表
		for (String id:ids) {
			affairLzxxjyActivitiesSignDao.deleteByNoticeId(id);
		}
	}

	/**
	 * 批量发布
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void publishByIds(List<String> ids) {
		List<AffairLzxxjyActivities> list = affairLzxxjyActivitiesDao.findByIds(ids);
		for (AffairLzxxjyActivities affairLzxxjyActivities: list) {
			//过滤掉已经发布的
			if (!"2".equals(affairLzxxjyActivities.getStatus())){
				affairLzxxjyActivities.setStatus("2");
				super.save(affairLzxxjyActivities);
				String receiveDepIds = affairLzxxjyActivities.getReceiveDepId();
				//先删除关联表原来的数据
				affairLzxxjyActivitiesSignDao.deleteByNoticeId(affairLzxxjyActivities.getId());
				//关联表插入新的数据
				List<String> idList = Arrays.asList(receiveDepIds.split(","));
				for (String id : idList) {
					AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign = new AffairLzxxjyActivitiesSign();
					affairLzxxjyActivitiesSign.setNoticeId(affairLzxxjyActivities.getId());
					affairLzxxjyActivitiesSign.setOrgId(id);
					//单位名称
					Office office = officeDao.findOneById(id);
					affairLzxxjyActivitiesSign.setUnit(office.getName());
					affairLzxxjyActivitiesSign.preInsert();
					affairLzxxjyActivitiesSignDao.insert(affairLzxxjyActivitiesSign);
				}
			}
		}
	}

	/**
	 * 批量取消发布
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void cancelByIds(List<String> ids) {
		List<AffairLzxxjyActivities> list = affairLzxxjyActivitiesDao.findByIds(ids);
		for (AffairLzxxjyActivities affairLzxxjyActivities: list) {
			affairLzxxjyActivities.setStatus("3");
			//主表更新
			super.save(affairLzxxjyActivities);
			//关联表删除
			affairLzxxjyActivitiesSignDao.deleteByNoticeId(affairLzxxjyActivities.getId());
		}
	}

	/**
	 *获得首页要展示的通知通报（最多五条，更新时间倒序排序）
	 */
	public List<AffairLzxxjyActivities> indexNoticeList() {
		return  affairLzxxjyActivitiesDao.indexNoticeList();
	}

	public Map<String, Object> findInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgId(id, year, startDate, endDate, month);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgIds(ids, year, startDate, endDate, month);
	}

	public List<String> selectAllYear(){
		return affairLzxxjyActivitiesDao.selectAllYear();
	}

	public List<String> selectAllMonth(){
		return affairLzxxjyActivitiesDao.selectAllMonth();
	}

	public Integer selectNumber(String yearL,String yearT,String idNumber){
		return affairLzxxjyActivitiesDao.selectNumber(yearL,yearT,idNumber);
	}

	public List<String> selectAllName(){
		return affairLzxxjyActivitiesDao.selectAllName();
	}



}