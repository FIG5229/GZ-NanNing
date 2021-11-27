/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairPoliceHomeChildDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairPoliceHomeDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceHome;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceHomeChild;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 民警小家建设Service
 * @author cecil.li
 * @version 2019-11-28
 */
@Service
@Transactional(readOnly = true)
public class AffairPoliceHomeService extends CrudService<AffairPoliceHomeDao, AffairPoliceHome> {
@Autowired
	AffairPoliceHomeDao affairPoliceHomeDao;

	@Autowired
	AffairPoliceHomeChildDao childDao;

	public AffairPoliceHome get(String id) {
		AffairPoliceHome entity =super.get(id);
		if(entity != null){
		AffairPoliceHome param = new AffairPoliceHome();
		param.setUnit(entity.getUnit());
		param.setProject(entity.getProject());
		param.setPointDate(entity.getPointDate());
		param.setPointUnit(entity.getPointUnit());
		List<AffairPoliceHome> children = super.findList(param);
		entity.setChildrens(children);
		}
		return entity;
	}
	
	public List<AffairPoliceHome> findList(AffairPoliceHome affairPoliceHome) {
        affairPoliceHome.setUserId(UserUtils.getUser().getOffice().getId());
        affairPoliceHome.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(affairPoliceHome);
	}
	
	public Page<AffairPoliceHome> findPage(Page<AffairPoliceHome> page, AffairPoliceHome affairPoliceHome) {
	/*	affairPoliceHome.setUserId(UserUtils.getUser().getOffice().getId());
		affairPoliceHome.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/
		if (UserUtils.getUser().getId().equals("4c40b4dd2aee459286a37538978e6261") || UserUtils.getUser().getId().equals("cb97bf7e17d549f09b51375a98a8eb55") || UserUtils.getUser().getId().equals("bdab6b25fc694e74bf7c3d1363c0062e") || UserUtils.getUser().getId().equals("abb70c88ca9d42758c489838fdd0e33b")){
			affairPoliceHome.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairPoliceHome.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairPoliceHome);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPoliceHome affairPoliceHome) {
		//子表信息不保存到主表中
		AffairPoliceHome aph=affairPoliceHome;
		aph.setDevice(affairPoliceHome.getDevice());
		aph.setNums(null);
		aph.setPrice(null);
		aph.setSum(null);
		aph.setContent(null);
		aph.setJingBan(null);
		aph.setUnitShRen(null);
		aph.setChuShOpinion(null);
		aph.setJuShOpinion(null);
		super.save(aph);
		//遍历设备信息
		if (affairPoliceHome.getPoliceHomeChildList()!=null){

			for (AffairPoliceHomeChild item : affairPoliceHome.getPoliceHomeChildList()) {
				if (item.getId() == null) {
					continue;
				}
				if (AffairPoliceHomeChild.DEL_FLAG_NORMAL.equals(item.getDelFlag())) {
					if (StringUtils.isBlank(item.getId())) {
						item.setPoliceHomeId(aph.getId());
						item.preInsert();
						childDao.insert(item);
					} else {
						item.preUpdate();
						childDao.update(item);
					}
				} else {
					childDao.delete(item);
				}
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPoliceHome affairPoliceHome) {
        super.delete(affairPoliceHome);
	}
/*    @Transactional(readOnly = false)
    public void deleteInDetail(AffairPoliceHome affairPoliceHome) {
        affairPoliceHomeDao.deleteInDetail(affairPoliceHome);
    }*/

	public Map<String, Object> findInfoByCreateOrgId(String id, Integer year, Date mStartDate, Date mEndDate) {
		return dao.findInfoByCreateOrgId(id, year, mStartDate, mEndDate);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids, Integer year, Date mStartDate, Date mEndDate) {
		return dao.findInfoByCreateOrgIds(ids, year, mStartDate, mEndDate);
	}

	public AffairPoliceHome getOne(AffairPoliceHome policeHome) {
		return dao.getOne(policeHome);
	}
}