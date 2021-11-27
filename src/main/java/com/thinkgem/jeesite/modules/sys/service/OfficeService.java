/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.org.dao.OrgBianzhiDao;
import com.thinkgem.jeesite.modules.org.dao.OrgCommunicationDao;
import com.thinkgem.jeesite.modules.org.dao.OrgJobNumberDao;
import com.thinkgem.jeesite.modules.org.dao.OrgRewardDao;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	@Autowired
	private OfficeDao officeDao;

	@Autowired
	private OrgBianzhiDao orgBianzhiDao;

	@Autowired
	private OrgJobNumberDao orgJobNumberDao;

	@Autowired
	private OrgRewardDao oegrewardDao;

	@Autowired
	private OrgCommunicationDao orgCommunicationDao;

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}

	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		if(office != null){
			office.setParentIds(office.getParentIds()+"%");
			return dao.findByParentIdsLike(office);
		}
		return  new ArrayList<Office>();
	}

	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}

	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
		//删除相关的编制信息、职数信息、奖励信息、通信信息
		orgBianzhiDao.deleteByOrgId(office.getId());
		orgJobNumberDao.deleteByOrgId(office.getId());
		oegrewardDao.deleteByOrgId(office.getId());
		orgCommunicationDao.deleteByOrgId(office.getId());
	}

	/**
	 * 根据机构名字查找机构id
	 * @param name
	 * @return
	 */
	public String findByName(String name) {
		if(!StringUtils.isNotBlank(name)){
			return null;
		}
		List<String> list = dao.findListByName(name);
		if(list != null && list.size() >0){
			return list.get(0);
		}
		return null;
	}

	public List<String> findCodesByParentId(String id) {
		return dao.findCodesByParentId(id);
	}

	public String findCodeById(String id) {
		return dao.findCodeById(id);
	}

	public List<String> findIdsByParentId(String id) {
		return dao.findIdsByParentId(id);
	}

	public String findUserByName(String name) {
		if(!StringUtils.isNotBlank(name)){
			return null;
		}
		List<String> list = dao.findListUserByName(name);
		if(list != null && list.size() >0){
			return list.get(0);
		}
		return null;
	}

	//根据id找name
	public String findNameById(String id){
		return officeDao.findNameById(id);
	}

	// 绩效自动考评
	public List<Map<String, String>> findAllOffice(){
		return officeDao.findAllOffice();
	}

	/**
	 * 绩效考评时 查询被考评对象的组织机构树
	 * isAll  true：查看本公司所有的被考评对象，在管理页查看环节详情使用
	 */
	public List<Map<String, Object>> findExamObjTree(String workflowId,String type,boolean isAll,String examType) {
		List<Map<String, Object>> mapList = new ArrayList<>();

//		List<Map<String, Object>> mapList = new ArrayList<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		String userId = UserUtils.getUser().getId();
		String companyId = null;
		/*绩效办 用户可查看本公司的考评对象 ，其他用户根据初核的对象查找*/
//		if (userId.equals("cca66e1339f14799b01f6db43ed16e16") || userId.equals("6af0e615e9b0477da82eff06ff532c8b") ||
//				userId.equals("46c521bf67e24db28772b3eac52dc454") || userId.equals("978958003ea44a4bba3eed8ee6ceff3c")){
		if (isAll){
			userId = "";
			companyId = UserUtils.getUser().getCompany().getId();
		}
		/*处绩效办查看处领导时 companyId改为1，处领导所在单位的父Id为 1 */
		if (StringUtils.isNotBlank(examType) && examType.equals("5")){
			companyId = "1";
		}
		mapList =dao.findExamObjTree(workflowId,"true",type,userId,companyId);
		mapList.stream().forEach(map -> {
			Map<String, Object> m = Maps.newHashMap();
			m.put("id",map.get("id"));
			m.put("pId",map.get("parentid"));
			m.put("name",map.get("name"));
			/*懒得写了，id长度小于5的都是单位，其他的是用户id */
			if (map.get("id").toString().length()<5)
			m.put("isParent", true);
			resultList.add(m);
		});

//		mapList =dao.findExamObjTree(workflowId,"",type,UserUtils.getUser().getId());
//		mapList.stream().forEach(map -> {
//			Map<String, Object> m = Maps.newHashMap();
//			m.put("id",map.get("id"));
//			m.put("pId",map.get("parentId"));
//			m.put("name",map.get("name"));
//			resultList.add(m);
//		});
		return resultList;
	}

	public String findCodeByUnitId(String unitId){
		return dao.findCodeByUnitId(unitId);
	}

	public String findUserIdByCode(String id){
		return dao.findUserIdByCode(id);
	}

	public Office findOfficeByCode(String code){return dao.findUnitIdByCode(code);}

	public User findUserById(String unitId) {
		User user = dao.findUserById(unitId);
		return user;
	}
}
