/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairTwBaseDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairTwBaseSignDao;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 团委（支部）基本信息Service
 * @author cecil.li
 * @version 2019-12-04
 */
@Service
@Transactional(readOnly = true)
public class AffairTwBaseService extends CrudService<AffairTwBaseDao, AffairTwBase> {

	@Autowired
	private AffairTwBaseSignDao affairTwBaseSignDao;

	@Autowired
	private AffairTwBaseDao affairTwBaseDao;


	public AffairTwBase get(String id) {
		AffairTwBase affairTwBase = super.get(id);
		affairTwBase.setAffairTwBaseSignList(affairTwBaseSignDao.findList(new AffairTwBaseSign(affairTwBase.getId())));
		return affairTwBase;
	}
	
	public List<AffairTwBase> findList(AffairTwBase affairTwBase) {
		return super.findList(affairTwBase);
	}
	
	public Page<AffairTwBase> findPage(Page<AffairTwBase> page, AffairTwBase affairTwBase) {
		/*affairTwBase.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));*/
		return super.findPage(page, affairTwBase);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTwBase affairTwBase) {
		//所属上级团组织对应的字段值：南宁铁路公安局团委为1，南宁公安处团委为2，柳州公安处团委为3，北海公安处团委为4
		//维护parentId和parentIds字段,orgname前端已加非空校验
		switch(affairTwBase.getOrgName()){
			case "1":
				affairTwBase.setParentId("1");
				affairTwBase.setParentIds("0,1,");
				break;
			case "2":
				affairTwBase.setParentId("34");
				affairTwBase.setParentIds("0,1,34,");
				break;
			case "3":
				affairTwBase.setParentId("95");
				affairTwBase.setParentIds("0,1,95,");
				break;
			case "4":
				affairTwBase.setParentId("156");
				affairTwBase.setParentIds("0,1,156,");
				break;
			default:
				break;
		}
		super.save(affairTwBase);
		for (AffairTwBaseSign affairTwBaseSign : affairTwBase.getAffairTwBaseSignList()){
			if (affairTwBaseSign.getId() == null){
				continue;
			}
			if (AffairTwBaseSign.DEL_FLAG_NORMAL.equals(affairTwBaseSign.getDelFlag())){
				if (StringUtils.isBlank(affairTwBaseSign.getId())){
					affairTwBaseSign.setTbId(affairTwBase.getId());
					affairTwBaseSign.preInsert();
					affairTwBaseSignDao.insert(affairTwBaseSign);
				}else{
					affairTwBaseSign.preUpdate();
					affairTwBaseSignDao.update(affairTwBaseSign);
				}
			}else{
				affairTwBaseSignDao.delete(affairTwBaseSign);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTwBase affairTwBase) {
		super.delete(affairTwBase);
		affairTwBaseSignDao.delete(new AffairTwBaseSign(affairTwBase.getId()));
	}

	public List<AffairOrganizationBulid> findTreeData() {
		AffairTwBase affairTwBase= new AffairTwBase();
		if(!"1".equals(UserUtils.getUser().getOffice().getId())&& !"ff7f9fe2597b40429ded58f8b76a2f65".equals(UserUtils.getUser().getId())){
			Map<String,String> orgInfo = affairTwBaseDao.getOrgInfo(UserUtils.getUser().getOffice().getId());
			if(null != orgInfo){
				if(null != orgInfo.get("id")){
					affairTwBase.setId(orgInfo.get("id"));
				}
				if(null != orgInfo.get("parent_ids")){
					affairTwBase.setParentIds(orgInfo.get("parent_ids"));
				}
			}
			else{
				affairTwBase.setId("-1");
				affairTwBase.setParentIds("-1");
			}
		}
		return affairTwBaseDao.findTreeData(affairTwBase);
	}

	/**
	 * 根据团支部名字查找团支部id
	 * @param name
	 * @return
	 */
	public String findByName(String name) {
		if(!StringUtils.isNotBlank(name)){
			return null;
		}
		return affairTwBaseDao.findByName(name);
	}

	public List<AffairTwBase> getSum(AffairTwBase affairTwBase){
		return  affairTwBaseDao.getSum(affairTwBase);
	}

	/*需求变更 已更改mapper 不再限制年龄*/
	public List<AffairTjRegisterFromP> getPersonnelUnder28(String id){
		return  affairTwBaseDao.getPersonnelUnder28(id);
	}

	/**
	 * 根据团组织名字查找团组织id
	 * @param name
	 * @return
	 */
	public String findIdByName(String name) {
		if(!StringUtils.isNotBlank(name)){
			return null;
		}
		return affairTwBaseDao.findListByName(name);
	}

	public List<String> selectAllTw(){
		return affairTwBaseDao.selectAllTw();
	}

	public String selectId(String twName){
		return affairTwBaseDao.selectId(twName);
	}

	public List<AffairTwBase> findAllTwUnit(){
		return affairTwBaseDao.findAllTwUnit();
	}

	public String findOrgNameById(String id){
		return affairTwBaseDao.findOrgNameById(id);
	}

    public List<AffairTwBase> getChuPartyBranch(String chuId, String type) {
		return affairTwBaseDao.getChuPartyBranch(chuId,type);
    }

	public List<AffairTwBase> selectChuTw(String twId) {
		return affairTwBaseDao.selectChuTw(twId);
	}

	public List<AffairTwBase> selectBeanTwById(String id) {
		return affairTwBaseDao.selectBeanTwById(id);
	}
	public AffairTwBase selectTwBeanById(String id) {
		return affairTwBaseDao.selectTwBeanById(id);
	}
}