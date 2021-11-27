/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairFileNoticeDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairFileNoticeSignDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairFileNotice;
import com.thinkgem.jeesite.modules.affair.entity.AffairFileNoticeSign;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


/**
 * 党建文件通知通报Service
 * @author eav.liu
 * @version 2019-11-01
 */
@Service
@Transactional(readOnly = true)
public class AffairFileNoticeService extends CrudService<AffairFileNoticeDao, AffairFileNotice> {

	@Autowired
	private AffairFileNoticeDao affairFileNoticeDao;

	@Autowired
	private AffairFileNoticeSignDao affairFileNoticeSignDao;

	@Autowired
	private OfficeDao officeDao;

	public AffairFileNotice get(String id) {
		return super.get(id);
	}
	
	public List<AffairFileNotice> findList(AffairFileNotice affairFileNotice) {
		return super.findList(affairFileNotice);
	}
	
	public Page<AffairFileNotice> findPage(Page<AffairFileNotice> page, AffairFileNotice affairFileNotice) {
		User user = UserUtils.getUser();
		//存放当前用户
		affairFileNotice.setCreateBy(user);
		affairFileNotice.getSqlMap().put("dsf", dataScopeFilter(user, "o", "u"));
		Page<AffairFileNotice> Page = super.findPage(page, affairFileNotice);
		if (affairFileNotice.isHasAuth() == true){
			if(Page.getList() != null && Page.getList().size() >0){
				for (AffairFileNotice a : Page.getList()){
					if(!"1".equals(a.getStatus()) && !"3".equals(a.getStatus())){//1:未发布  3:取消发布
						Integer signNum = affairFileNoticeDao.findSignNum(a);
						Integer sumNum = affairFileNoticeDao.findSumNum(a);
						a.setSignNum(signNum);
						a.setSumNum(sumNum);
					}
				}
			}
		}
		return Page;
	}
	
	@Transactional(readOnly = false)
	public void save(AffairFileNotice affairFileNotice) {
		affairFileNotice.setPublisher(UserUtils.getUser().getName());
		//affairFileNotice.setPublishOrgId(UserUtils.getUser().getOffice().getId());
		affairFileNotice.setContent(StringEscapeUtils.unescapeHtml4(affairFileNotice.getContent()));
		super.save(affairFileNotice);
		if("2".equals(affairFileNotice.getStatus())){//发布
			String receiveDepIds = affairFileNotice.getReceiveDepId();
			//先删除关联表原来的数据
			affairFileNoticeSignDao.deleteByNoticeId(affairFileNotice.getId());
			//关联表插入新的数据
			List<String> idList = Arrays.asList(receiveDepIds.split(","));
			for (String id : idList) {
				AffairFileNoticeSign affairFileNoticeSign = new AffairFileNoticeSign();
				affairFileNoticeSign.setNoticeId(affairFileNotice.getId());
				affairFileNoticeSign.setOrgId(id);
				//单位名称
				Office office = officeDao.findOneById(id);
				affairFileNoticeSign.setUnit(office.getName());
				affairFileNoticeSign.preInsert();
				affairFileNoticeSignDao.insert(affairFileNoticeSign);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairFileNotice affairFileNotice) {
		super.delete(affairFileNotice);
		//删除关联表
		affairFileNoticeSignDao.deleteByNoticeId(affairFileNotice.getId());
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
			affairFileNoticeSignDao.deleteByNoticeId(id);
		}
	}

	/**
	 * 批量发布
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void publishByIds(List<String> ids) {
		List<AffairFileNotice> list = affairFileNoticeDao.findByIds(ids);
		for (AffairFileNotice affairFileNotice: list) {
			//过滤掉已经发布的
			if (!"2".equals(affairFileNotice.getStatus())){
				affairFileNotice.setStatus("2");
				super.save(affairFileNotice);
				String receiveDepIds = affairFileNotice.getReceiveDepId();
				//先删除关联表原来的数据
				affairFileNoticeSignDao.deleteByNoticeId(affairFileNotice.getId());
				//关联表插入新的数据
				List<String> idList = Arrays.asList(receiveDepIds.split(","));
				for (String id : idList) {
					AffairFileNoticeSign affairFileNoticeSign = new AffairFileNoticeSign();
					affairFileNoticeSign.setNoticeId(affairFileNotice.getId());
					affairFileNoticeSign.setOrgId(id);
					//单位名称
					Office office = officeDao.findOneById(id);
					affairFileNoticeSign.setUnit(office.getName());
					affairFileNoticeSign.preInsert();
					affairFileNoticeSignDao.insert(affairFileNoticeSign);
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
		List<AffairFileNotice> list = affairFileNoticeDao.findByIds(ids);
		for (AffairFileNotice affairFileNotice: list) {
			affairFileNotice.setStatus("3");
			//主表更新
			super.save(affairFileNotice);
			//关联表删除
			affairFileNoticeSignDao.deleteByNoticeId(affairFileNotice.getId());
		}
	}

	/**
	 *获得首页要展示的通知通报（最多五条，更新时间倒序排序）
	 */
	public List<AffairFileNotice> indexNoticeList() {
		return  affairFileNoticeDao.indexNoticeList();
	}
}