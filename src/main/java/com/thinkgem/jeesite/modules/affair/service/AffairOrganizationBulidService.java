/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairOrganizationBuildSing2Dao;
import com.thinkgem.jeesite.modules.affair.dao.AffairOrganizationBulidDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairOrganziationBuildSignDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairOrganizationBuildSing2;
import com.thinkgem.jeesite.modules.affair.entity.AffairOrganizationBulid;
import com.thinkgem.jeesite.modules.affair.entity.AffairOrganziationBuildSign;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 组织建设Service
 * @author cecil.li
 * @version 2019-12-16
 */
@Service
@Transactional(readOnly = true)
public class AffairOrganizationBulidService extends CrudService<AffairOrganizationBulidDao, AffairOrganizationBulid> {

	@Autowired
	private  AffairOrganizationBulidDao affairOrganizationBulidDao;
	@Autowired
	private AffairOrganziationBuildSignDao affairOrganziationBuildSignDao;

	@Autowired
	private AffairOrganizationBuildSing2Dao affairOrganizationBuildSing2Dao;
	
	public AffairOrganizationBulid get(String id) {
		AffairOrganizationBulid affairOrganizationBulid = super.get(id);
		affairOrganizationBulid.setAffairOrganziationBuildSignList(affairOrganziationBuildSignDao.findList(new AffairOrganziationBuildSign(affairOrganizationBulid.getId())));
		affairOrganizationBulid.setAffairOrganizationBuildSingList2(affairOrganizationBuildSing2Dao.findList(new AffairOrganizationBuildSing2(affairOrganizationBulid.getId())));
		return affairOrganizationBulid;
	}
	
	public List<AffairOrganizationBulid> findList(AffairOrganizationBulid affairOrganizationBulid) {
		return super.findList(affairOrganizationBulid);
	}
	
	public Page<AffairOrganizationBulid> findPage(Page<AffairOrganizationBulid> page, AffairOrganizationBulid affairOrganizationBulid) {
		affairOrganizationBulid.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairOrganizationBulid);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairOrganizationBulid affairOrganizationBulid) {
		if ("".equals(affairOrganizationBulid.getStatus())||affairOrganizationBulid.getStatus()== null)
			affairOrganizationBulid.setStatus("3");
		affairOrganizationBulid.setMethod(StringEscapeUtils.unescapeHtml4(affairOrganizationBulid.getMethod()));
		//所属上级工会组织对应的字段值：南宁铁路公安局工会为1，南宁公安处工会为2，柳州公安处工会为3，北海公安处工会为4
		//维护parentId和parentIds字段,orgname前端已加非空校验
		switch(affairOrganizationBulid.getOrgName()){
			case "1":
				affairOrganizationBulid.setParentId("1");
				affairOrganizationBulid.setParentIds("0,1,");
				break;
			case "2":
				affairOrganizationBulid.setParentId("34");
				affairOrganizationBulid.setParentIds("0,1,34,");
				break;
			case "3":
				affairOrganizationBulid.setParentId("95");
				affairOrganizationBulid.setParentIds("0,1,95,");
				break;
			case "4":
				affairOrganizationBulid.setParentId("156");
				affairOrganizationBulid.setParentIds("0,1,156,");
				break;
			default:
				break;
		}
		super.save(affairOrganizationBulid);
		for (AffairOrganziationBuildSign affairOrganziationBuildSign : affairOrganizationBulid.getAffairOrganziationBuildSignList()){
			if (affairOrganziationBuildSign.getId() == null){
				continue;
			}
			if (AffairOrganziationBuildSign.DEL_FLAG_NORMAL.equals(affairOrganziationBuildSign.getDelFlag())){
				if (StringUtils.isBlank(affairOrganziationBuildSign.getId())){
					affairOrganziationBuildSign.setObId(affairOrganizationBulid.getId());
					affairOrganziationBuildSign.preInsert();
					affairOrganziationBuildSignDao.insert(affairOrganziationBuildSign);
				}else{
					affairOrganziationBuildSign.preUpdate();
					affairOrganziationBuildSignDao.update(affairOrganziationBuildSign);
				}
			}else{
				affairOrganziationBuildSignDao.delete(affairOrganziationBuildSign);
			}
		}
		for (AffairOrganizationBuildSing2 affairOrganizationBuildSing2 : affairOrganizationBulid.getAffairOrganizationBuildSingList2()){
			if (affairOrganizationBuildSing2.getId() == null){
				continue;
			}
			if (AffairOrganizationBuildSing2.DEL_FLAG_NORMAL.equals(affairOrganizationBuildSing2.getDelFlag())){
				if (StringUtils.isBlank(affairOrganizationBuildSing2.getId())){
					affairOrganizationBuildSing2.setObId(affairOrganizationBulid.getId());
					affairOrganizationBuildSing2.preInsert();
					affairOrganizationBuildSing2Dao.insert(affairOrganizationBuildSing2);
				}else{
					affairOrganizationBuildSing2.preUpdate();
					affairOrganizationBuildSing2Dao.update(affairOrganizationBuildSing2);
				}
			}else{
				affairOrganizationBuildSing2Dao.delete(affairOrganizationBuildSing2);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairOrganizationBulid affairOrganizationBulid) {
		super.delete(affairOrganizationBulid);
		affairOrganziationBuildSignDao.delete(new AffairOrganziationBuildSign(affairOrganizationBulid.getId()));
		affairOrganizationBuildSing2Dao.delete(new AffairOrganizationBuildSing2(affairOrganizationBulid.getId()));
	}

	@Transactional(readOnly = false)
	public void shenHe(AffairOrganizationBulid affairOrganizationBulid) {
		affairOrganizationBulid.setUpdateDate(new Date());
		affairOrganizationBulid.setShPerson(UserUtils.getUser().getName());
		affairOrganizationBulidDao.shenHe(affairOrganizationBulid);
	}

	public List<AffairOrganizationBulid> findTreeData() {
		AffairOrganizationBulid affairOrganizationBulid= new AffairOrganizationBulid();
		if(!"1".equals(UserUtils.getUser().getOffice().getId())){
			Map<String,String> orgInfo = affairOrganizationBulidDao.getOrgInfo(UserUtils.getUser().getOffice().getId());
			if(null != orgInfo){
				if(null != orgInfo.get("id")){
					affairOrganizationBulid.setId(orgInfo.get("id"));
				}
				if(null != orgInfo.get("parent_ids")){
					affairOrganizationBulid.setParentIds(orgInfo.get("parent_ids"));
				}
			}
			else{
				affairOrganizationBulid.setId("-1");
				affairOrganizationBulid.setParentIds("-1");
			}
		}
		return affairOrganizationBulidDao.findTreeData(affairOrganizationBulid);
	}

	/**
	 * 根据团支部名字查找团支部id
	 * @param name
	 * @return
	 */
	public String findByName(String name) {
		if(name == null ){
			return null;
		}
		return affairOrganizationBulidDao.findByName(name);
	}

	public List<Map<String, Object>> findInfoByUnitId(String id) {
		return dao.findInfoByUnitId(id);
	}

	public List<Map<String, Object>> findInfoByUnitIds(List<String> ids) {
		return dao.findInfoByUnitIds(ids);
	}
}