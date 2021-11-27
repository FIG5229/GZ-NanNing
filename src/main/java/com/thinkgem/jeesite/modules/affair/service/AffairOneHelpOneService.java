/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairOneHelpOneMainDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairOneHelpOneMain;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairOneHelpOne;
import com.thinkgem.jeesite.modules.affair.dao.AffairOneHelpOneDao;

/**
 * 一帮一Service
 * @author mason.xv
 * @version 2020-04-15
 */
@Service
@Transactional(readOnly = true)
public class AffairOneHelpOneService extends CrudService<AffairOneHelpOneDao, AffairOneHelpOne> {

	@Autowired
	private AffairOneHelpOneMainDao oneHelpOneMainDao;

	@Autowired
	private AffairOneHelpOneDao affairOneHelpOneDao;

	@Autowired
	private OfficeDao officeDao;

	public AffairOneHelpOne get(String id) {
		AffairOneHelpOne entity =super.get(id);
		AffairOneHelpOne param = new AffairOneHelpOne();
		if (entity!=null){
			param.setTitle(entity.getTitle());
			param.setUnit(entity.getUnit());
			List<AffairOneHelpOne> children = super.findList(param);
			entity.setChildrens(children);
		}
		return entity;
	}

	public List<AffairOneHelpOne> findList(AffairOneHelpOne affairOneHelpOne) {
		affairOneHelpOne.setUserId(UserUtils.getUser().getOffice().getId());
		affairOneHelpOne.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(affairOneHelpOne);
	}

	public Page<AffairOneHelpOne> findPage(Page<AffairOneHelpOne> page, AffairOneHelpOne affairOneHelpOne) {
		affairOneHelpOne.setUserId(UserUtils.getUser().getOffice().getId());
		affairOneHelpOne.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page,affairOneHelpOne);
//		修改为主子表 不在使用inPage方法
//		return super.findInPage(page, affairOneHelpOne);
	}

	@Transactional(readOnly = false)
	public void save(AffairOneHelpOne affairOneHelpOne) {
		//主表只存入标题  单位
		AffairOneHelpOne oneHelpOne=new AffairOneHelpOne();
		oneHelpOne.setTitle(affairOneHelpOne.getTitle());
		oneHelpOne.setUnit(affairOneHelpOne.getUnit());
		String unitId=officeDao.findByName(oneHelpOne.getUnit());
		oneHelpOne.setUnitId(unitId);
		oneHelpOne.setId(affairOneHelpOne.getId());
		/*子表导入时 不影响正常逻辑，有则有 空则空*/
		oneHelpOne.setId(affairOneHelpOne.getId());
//		affairOneHelpOne.getIsNewRecord();
//		boolean newRecord = StringUtils.isNotBlank(affairOneHelpOne.getId());
//		oneHelpOne.setIsNewRecord(newRecord);
		super.save(affairOneHelpOne);

		//遍历事迹材料保存
		//手动添加时 list不为空
		//导入添加时 list为空 实现下面的方法
		if (affairOneHelpOne.getOneHelpOneMainList()!=null){

			for (AffairOneHelpOneMain item : affairOneHelpOne.getOneHelpOneMainList()) {
				if (item.getId() == null) {
					continue;
				}
				if (AffairOneHelpOneMain.DEL_FLAG_NORMAL.equals(item.getDelFlag())) {
					if (StringUtils.isBlank(item.getId())) {
						//affairOneHelpOne 未保存
						//只保存了oneHelpOne（标题和单位）
						item.setMainId(affairOneHelpOne.getId());
						item.preInsert();
						oneHelpOneMainDao.insert(item);
					} else {
						item.preUpdate();
						oneHelpOneMainDao.update(item);
					}
				} else {
					oneHelpOneMainDao.delete(item);
				}
			}
		}

		//如果保存时  是导入保存 则保存子表信息
		if (affairOneHelpOne.getIsImport()!=null && affairOneHelpOne.getIsImport().equals("import")  ){
			AffairOneHelpOneMain i=new AffairOneHelpOneMain();
			i.setMainId(affairOneHelpOne.getId());
			i.setAddress(affairOneHelpOne.getAddress());
			i.setBeName(affairOneHelpOne.getBeName());
			i.setDate(affairOneHelpOne.getDate());
			i.setJob(affairOneHelpOne.getJob());
			i.setMoney(affairOneHelpOne.getMoney());
			i.setName(affairOneHelpOne.getName());
			i.setUnitJob(affairOneHelpOne.getUnitJob());
			i.setSituation(affairOneHelpOne.getSituation());
			i.setTel(affairOneHelpOne.getTel());
			i.preInsert();
			oneHelpOneMainDao.insert(i);

		}
	}

	@Transactional(readOnly = false)
	public void delete(AffairOneHelpOne affairOneHelpOne) {
		super.delete(affairOneHelpOne);
	}

	public AffairOneHelpOne getIdByTitle(AffairOneHelpOne oneHelpOne) {
		AffairOneHelpOne affairOneHelpOne=affairOneHelpOneDao.getIdByTitle(oneHelpOne);
		return affairOneHelpOne;
	}

	public int selectOneHelpOne(String lastYearDate,String nowYearDate, String unitId) {
		return affairOneHelpOneDao.selectOneHelpOne(lastYearDate,nowYearDate,unitId);
	}
}