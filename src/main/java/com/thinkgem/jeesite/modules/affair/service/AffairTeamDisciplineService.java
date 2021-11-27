/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairTeamDisciplineDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairTeamDisciplineSignDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTeamDiscipline;
import com.thinkgem.jeesite.modules.affair.entity.AffairTeamDisciplineSign;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 队伍作风纪律整顿Service
 * @author cecil.li
 * @version 2019-11-08
 */
@Service
@Transactional(readOnly = true)
public class AffairTeamDisciplineService extends CrudService<AffairTeamDisciplineDao, AffairTeamDiscipline> {

	@Autowired
	private AffairTeamDisciplineDao affairTeamDisciplineDao;

	@Autowired
	private AffairTeamDisciplineSignDao affairTeamDisciplineSignDao;

	@Autowired
	private OfficeDao officeDao;

	public AffairTeamDiscipline get(String id) {
		return super.get(id);
	}
	
	public List<AffairTeamDiscipline> findList(AffairTeamDiscipline affairTeamDiscipline) {
		return super.findList(affairTeamDiscipline);
	}
	
	public Page<AffairTeamDiscipline> findPage(Page<AffairTeamDiscipline> page, AffairTeamDiscipline affairTeamDiscipline) {
		/*affairTeamDiscipline.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTeamDiscipline);*/
		User user = UserUtils.getUser();
		//存放当前用户
		affairTeamDiscipline.setCreateBy(user);
		affairTeamDiscipline.getSqlMap().put("dsf", dataScopeFilter(user, "o", "u"));
		Page<AffairTeamDiscipline> Page = super.findPage(page, affairTeamDiscipline);
		if (affairTeamDiscipline.isHasAuth() == true){
			if(Page.getList() != null && Page.getList().size() >0){
				for (AffairTeamDiscipline a : Page.getList()){
					if(!"1".equals(a.getStatus()) && !"3".equals(a.getStatus())){//1:未发布  3:取消发布
						Integer signNum = affairTeamDisciplineDao.findSignNum(a);
						Integer sumNum = affairTeamDisciplineDao.findSumNum(a);
						a.setSignNum(signNum);
						a.setSumNum(sumNum);
					}
				}
			}
		}
		return Page;
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTeamDiscipline affairTeamDiscipline) {
//		super.save(affairTeamDiscipline);
		affairTeamDiscipline.setPublisher(UserUtils.getUser().getName());
		//affairFileNotice.setPublishOrgId(UserUtils.getUser().getOffice().getId());
		affairTeamDiscipline.setContent(StringEscapeUtils.unescapeHtml4(affairTeamDiscipline.getContent()));
		super.save(affairTeamDiscipline);
		if("2".equals(affairTeamDiscipline.getStatus())){//发布
			String receiveDepIds = affairTeamDiscipline.getReceiveDepId();
			//先删除关联表原来的数据
			affairTeamDisciplineSignDao.deleteByNoticeId(affairTeamDiscipline.getId());
			//关联表插入新的数据
			List<String> idList = Arrays.asList(receiveDepIds.split(","));
			for (String id : idList) {
				AffairTeamDisciplineSign affairTeamDisciplineSign = new AffairTeamDisciplineSign();
				affairTeamDisciplineSign.setNoticeId(affairTeamDiscipline.getId());
				affairTeamDisciplineSign.setOrgId(id);
				//单位名称
				Office office = officeDao.findOneById(id);
				affairTeamDisciplineSign.setUnit(office.getName());
				affairTeamDisciplineSign.preInsert();
				affairTeamDisciplineSignDao.insert(affairTeamDisciplineSign);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTeamDiscipline affairTeamDiscipline) {
		super.delete(affairTeamDiscipline);
		//删除关联表
		affairTeamDisciplineSignDao.deleteByNoticeId(affairTeamDiscipline.getId());
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
			affairTeamDisciplineSignDao.deleteByNoticeId(id);
		}
	}

	/**
	 * 批量发布
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void publishByIds(List<String> ids) {
		List<AffairTeamDiscipline> list = affairTeamDisciplineDao.findByIds(ids);
		for (AffairTeamDiscipline affairTeamDiscipline: list) {
			//过滤掉已经发布的
			if (!"2".equals(affairTeamDiscipline.getStatus())){
				affairTeamDiscipline.setStatus("2");
				super.save(affairTeamDiscipline);
				String receiveDepIds = affairTeamDiscipline.getReceiveDepId();
				//先删除关联表原来的数据
				affairTeamDisciplineSignDao.deleteByNoticeId(affairTeamDiscipline.getId());
				//关联表插入新的数据
				List<String> idList = Arrays.asList(receiveDepIds.split(","));
				for (String id : idList) {
					AffairTeamDisciplineSign affairTeamDisciplineSign = new AffairTeamDisciplineSign();
					affairTeamDisciplineSign.setNoticeId(affairTeamDiscipline.getId());
					affairTeamDisciplineSign.setOrgId(id);
					//单位名称
					Office office = officeDao.findOneById(id);
					affairTeamDisciplineSign.setUnit(office.getName());
					affairTeamDisciplineSign.preInsert();
					affairTeamDisciplineSignDao.insert(affairTeamDisciplineSign);
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
		List<AffairTeamDiscipline> list = affairTeamDisciplineDao.findByIds(ids);
		for (AffairTeamDiscipline affairTeamDiscipline: list) {
			affairTeamDiscipline.setStatus("3");
			//主表更新
			super.save(affairTeamDiscipline);
			//关联表删除
			affairTeamDisciplineSignDao.deleteByNoticeId(affairTeamDiscipline.getId());
		}
	}

	/**
	 *获得首页要展示的通知通报（最多五条，更新时间倒序排序）
	 */
	public List<AffairTeamDiscipline> indexNoticeList() {
		return  affairTeamDisciplineDao.indexNoticeList();
	}

	public Map<String, Object> findInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgId(id, year, startDate, endDate, month);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgIds(ids, year, startDate, endDate, month);
	}
}