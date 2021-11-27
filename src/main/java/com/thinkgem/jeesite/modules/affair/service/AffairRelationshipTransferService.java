/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairPartyMemberDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairRelationshipTransferDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairTransferShDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairRelationshipTransfer;
import com.thinkgem.jeesite.modules.affair.entity.AffairTransferSh;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 系统内组织关系移交转接Service
 * @author eav.liu
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class AffairRelationshipTransferService extends CrudService<AffairRelationshipTransferDao, AffairRelationshipTransfer> {

	@Autowired
	private AffairTransferShService affairTransferShService;

	@Autowired
	private AffairTransferShDao affairTransferShDao;

	@Autowired
	private AffairPartyMemberDao affairPartyMemberDao;

	@Autowired
	private AffairRelationshipTransferDao affairRelationshipTransferDao;

	@Autowired
	private RoleDao roleDao;

	public AffairRelationshipTransfer get(String id) {
		return super.get(id);
	}
	
	public List<AffairRelationshipTransfer> findList(AffairRelationshipTransfer affairRelationshipTransfer) {
		return super.findList(affairRelationshipTransfer);
	}
	
	public Page<AffairRelationshipTransfer> findPage(Page<AffairRelationshipTransfer> page, AffairRelationshipTransfer affairRelationshipTransfer) {
		affairRelationshipTransfer.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		//存放当前登陆人
		affairRelationshipTransfer.setCreateBy(UserUtils.getUser());
		if(affairRelationshipTransfer.isHasManageAuth()) {
			Page<AffairRelationshipTransfer> page1 = null;
			if (UserUtils.getUser().getRoleList() != null && UserUtils.getUser().getRoleList().size() > 0) {
				for (Role r : UserUtils.getUser().getRoleList()) {
					if("8".equals(r.getId())){
						affairRelationshipTransfer.setRole("ju");
						page1 = super.findPage(page, affairRelationshipTransfer);
						break;
					}else if("9".equals(r.getId()) || "10".equals(r.getId()) ||  "11".equals(r.getId())){
						affairRelationshipTransfer.setRole("chu");
						page1 = super.findPage(page, affairRelationshipTransfer);
						break;
					}
				}
				return page1;
			}
		}else{
			return super.findPage(page, affairRelationshipTransfer);
		}
		return null;
	}
	
	@Transactional(readOnly = false)
	public void save(AffairRelationshipTransfer affairRelationshipTransfer) {
		affairRelationshipTransfer.setStatus("未审核");
		affairRelationshipTransfer.setStatusFlag("3");
		super.save(affairRelationshipTransfer);
	}

	@Transactional(readOnly = false)
	public void updateInfo(AffairRelationshipTransfer affairRelationshipTransfer) {
		affairRelationshipTransferDao.updateInfo(affairRelationshipTransfer);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairRelationshipTransfer affairRelationshipTransfer) {
		super.delete(affairRelationshipTransfer);
		//删除关联表数据（物理删除）
		affairTransferShService.deleteByIds(Arrays.asList(affairRelationshipTransfer.getId()));
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteByIds(List<String> ids) {
		super.deleteByIds(ids);
		//删除关联表数据（物理删除）
		affairTransferShService.deleteByIds(ids);
	}

	@Transactional(readOnly = false)
	public void shenHe(AffairRelationshipTransfer affairRelationshipTransfer) {
		AffairTransferSh affairTransferSh = affairRelationshipTransfer.getAffairTransferSh();
		//审批人
		affairTransferSh.setShPerson(UserUtils.getUser().getName());
		//审批时间
		affairTransferSh.setDate(new Date());
		affairTransferSh.setShOrganization(UserUtils.getUser().getOffice().getName());
		affairTransferSh.setShOrganizationId(UserUtils.getUser().getOffice().getId());
		//关联表保存数据
		affairTransferShService.save(affairTransferSh);
		//更新主表审核状态字段
		//判断关联表审批通过的个数
		Integer num = affairTransferShDao.findNumByTransferId(affairTransferSh.getTransferId());
		/**
		 * 需求：
		 *     1、处内转接：处审批人审核，审核过，审核结束
		 *     2、跨处转接：两种，（1）处到另一个处 （2）处到局  原组织的处审批人审核通过后，推给局审核，局再审核，如果处审核不过，审核结束未通过，不必推给局审核
		 *     3、局到处转接：局审批人通过，审核结束审核通过
		 *     4、局内转接：局审批人审核，审核过，审核结束
		 *     5、局外转接：暂时无法做，原组织局审核人通过后，推给转入组织的局审核人审核，如有一方审核不通过，审核结束未通过
		 */
		//1:处内转接 2:跨处转接 3：局到处转接  4：局内转接  5：局外转接
		//statusFlag:  1：通过 2：未通过 3：未审核
		if("1".equals(affairRelationshipTransfer.getTransferType())){//处内转接
			if("2".equals(affairTransferSh.getStatus())){//审批不通过
				affairRelationshipTransfer.setStatus("党处委审批未通过");
				affairRelationshipTransfer.setStatusFlag("2");
				transferClearCache(affairRelationshipTransfer.getOldOrganization(),affairRelationshipTransfer.getNowOrganizationId());
			}else{
				affairRelationshipTransfer.setStatus("党处委审批通过");
				affairRelationshipTransfer.setStatusFlag("1");
				//最终审核通过了，更新党员花名册
				updatePartyMember(affairRelationshipTransfer);
			}
		}else if("2".equals(affairRelationshipTransfer.getTransferType())){//跨处转接
			List<Role> roleList = UserUtils.getUser().getRoleList();
			String statusStr = "";
			if(roleList != null && roleList.size() >0){
				for (Role r:roleList) {
					if("8".equals(r.getId())){
						statusStr ="局委审批";
						break;
					}else{
						statusStr ="党处委审批";
					}
				}
			}
			if("2".equals(affairTransferSh.getStatus())){//审批不通过
				affairRelationshipTransfer.setStatus(statusStr+"未通过");
				affairRelationshipTransfer.setStatusFlag("2");
				transferClearCache(affairRelationshipTransfer.getOldOrganization(),affairRelationshipTransfer.getNowOrganizationId());
			}else{
				if(num == 1){
					affairRelationshipTransfer.setStatus("党处委审批通过");
					affairRelationshipTransfer.setStatusFlag("1");
				}else{
					affairRelationshipTransfer.setStatus("局委审批通过");
					affairRelationshipTransfer.setStatusFlag("1");
					//最终审核通过了，更新党员花名册
					updatePartyMember(affairRelationshipTransfer);
				}
			}
		}else if("3".equals(affairRelationshipTransfer.getTransferType())){
			if("2".equals(affairTransferSh.getStatus())){//审批不通过
				affairRelationshipTransfer.setStatus("局委审批未通过");
				affairRelationshipTransfer.setStatusFlag("2");
				transferClearCache(affairRelationshipTransfer.getOldOrganization(),affairRelationshipTransfer.getNowOrganizationId());
			}else{
				affairRelationshipTransfer.setStatus("局委审批通过");
				affairRelationshipTransfer.setStatusFlag("1");
				//最终审核通过了，更新党员花名册
				updatePartyMember(affairRelationshipTransfer);
			}
		}else if("4".equals(affairRelationshipTransfer.getTransferType())){
			if("2".equals(affairTransferSh.getStatus())){//审批不通过
				affairRelationshipTransfer.setStatus("局委审批未通过");
				affairRelationshipTransfer.setStatusFlag("2");
				transferClearCache(affairRelationshipTransfer.getOldOrganization(),affairRelationshipTransfer.getNowOrganizationId());
			}else{
				affairRelationshipTransfer.setStatus("局委审批通过");
				affairRelationshipTransfer.setStatusFlag("1");
				//最终审核通过了，更新党员花名册
				updatePartyMember(affairRelationshipTransfer);
			}
		}else{
			//暂时不能做,dao层sql未考虑局外转接的情况,后期有待完善
			List<Role> roleList = UserUtils.getUser().getRoleList();
			String statusStr = "";
			if(roleList != null && roleList.size() >0){
				for (Role r:roleList) {
					if("8".equals(r.getId())){
						statusStr ="局委审批";
						break;
					}else{
						statusStr ="党处委审批";
					}
				}
			}
			if("2".equals(affairTransferSh.getStatus())){//审批不通过
				affairRelationshipTransfer.setStatus(statusStr+"未通过");
				affairRelationshipTransfer.setStatusFlag("2");
				transferClearCache(affairRelationshipTransfer.getOldOrganization(),affairRelationshipTransfer.getNowOrganizationId());
			}else{
				if(num == 1){
					affairRelationshipTransfer.setStatus("南宁局委审批通过");
					affairRelationshipTransfer.setStatusFlag("1");
				}else{
					affairRelationshipTransfer.setStatus("xxx局委审批通过");
					affairRelationshipTransfer.setStatusFlag("1");
					//最终审核通过了，更新党员花名册
					//updatePartyMember(affairRelationshipTransfer);
				}
			}
		}
		//处理时间
		affairRelationshipTransfer.setHandleTime(new Date());
		super.save(affairRelationshipTransfer);
	}

	//更新党员花名册
	private void updatePartyMember(AffairRelationshipTransfer affairRelationshipTransfer) {
		List<AffairPartyMember> list = affairPartyMemberDao.findListByIdNumber(affairRelationshipTransfer.getIdNumber());
		if (list != null && list.size() > 0){
			AffairPartyMember nowAffairPartyMember = list.get(0);
			//清缓存
			CacheUtils.remove("partyMemberCache", "pm_"+affairRelationshipTransfer.getOldOrganizationId());
			CacheUtils.remove("partyMemberCache", "pm_"+affairRelationshipTransfer.getNowOrganizationId());
			nowAffairPartyMember.setPartyBranch(affairRelationshipTransfer.getNowOrganization());
			nowAffairPartyMember.setPartyBranchId(affairRelationshipTransfer.getNowOrganizationId());
			affairPartyMemberDao.update(nowAffairPartyMember);
		}
	}

	/**
	 * 清除原组织和现组织的缓存，防止审核通过后又审核不让通过
	 * @param oldId
	 * @param nowId
	 */
	private void transferClearCache(String oldId,String nowId) {
		//清缓存
		CacheUtils.remove("partyMemberCache", "pm_" + oldId);
		CacheUtils.remove("partyMemberCache", "pm_" + nowId);
	}

	/**
	 * 根据党员身份证号去组织关系转接表查询该党员最新的数据的主键
	 * @param idNumber
	 * @return
	 */
	public String findLastByIdNumber(String idNumber) {
		return affairRelationshipTransferDao.findLastByIdNumber(idNumber);
	}
}