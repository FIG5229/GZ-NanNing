/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelBaseDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.entity.PoliceCertificate;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.warn.entity.Warning;
import com.thinkgem.jeesite.modules.warn.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 人员基本情况信息Service
 * @author cecil.li
 * @version 2019-11-09
 */
@Service
@Transactional(readOnly = true)
public class PersonnelBaseService extends CrudService<PersonnelBaseDao, PersonnelBase> {

	@Autowired
	private PersonnelBaseDao personnelBaseDao;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private OfficeDao officeDao;

	@Autowired
	private WarningService warningService;

	@Autowired
	private UserDao userDao;

	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";
	@Autowired
	private RoleDao roleDao;


	public PersonnelBase get(String id) {

		return super.get(id);
	}

	public Page<PersonnelBase>  findAllWorkInfo(Page<PersonnelBase> page,PersonnelBase personnelBase){
		personnelBase.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));

		PersonnelBase isPersonnel = personnelBaseDao.findInfoByIdNumber(UserUtils.getUser().getNo());
		if(isPersonnel!=null && !"1".equals(UserUtils.getUser().getId())){
			//当前用户为民警时，局领导看到所有，处领导看到本处，民警看到自己
			List<Role> roleList = UserUtils.getUser().getRoleList();
			if(roleList!=null){
				for (int i = 0; i < roleList.size(); i++) {
					if ("c403b7c1f8fb4bf188b4b535765978c9".equals(roleList.get(i).getId())){
						//局领导
						personnelBase.setJuFlag("true");
					}else if ("230e916609a349e68f7186f784e11419".equals(roleList.get(i).getId())){
						//处领导
						String parentId = UserUtils.getUser().getOffice().getParentId();
						if("34".equals(UserUtils.getUser().getOffice().getParentId())){
							personnelBase.setChuFlag("34");
						}else if ("95".equals(UserUtils.getUser().getOffice().getParentId())){
							personnelBase.setChuFlag("95");
						}else if ("156".equals(UserUtils.getUser().getOffice().getParentId())){
							personnelBase.setChuFlag("156");
						}
					}else{
						personnelBase.setMinFlag(UserUtils.getUser().getNo());
					}
				}
			}else{
				//未分配任何角色
				personnelBase.setMinFlag(UserUtils.getUser().getNo());
			}

		}
		personnelBase.setPage(page);
		page.setList(personnelBaseDao.findAllWorkInfo(personnelBase));
		return page;
	}

	public Page<PersonnelBase>  findAllWorkInfoTwo(Page<PersonnelBase> page,PersonnelBase personnelBase,Boolean isAll){
		personnelBase.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));

		PersonnelBase isPersonnel = personnelBaseDao.findInfoByIdNumber(UserUtils.getUser().getNo());
		if(isPersonnel!=null && !"1".equals(UserUtils.getUser().getId())){
			//当前用户为民警时，局领导看到所有，处领导看到本处，民警看到自己
			List<Role> roleList = UserUtils.getUser().getRoleList();
			if(roleList!=null){
				for (int i = 0; i < roleList.size(); i++) {
					if ("c403b7c1f8fb4bf188b4b535765978c9".equals(roleList.get(i).getId())){
						//局领导
						personnelBase.setJuFlag("true");
					}else if ("230e916609a349e68f7186f784e11419".equals(roleList.get(i).getId())){
						//处领导
						String parentId = UserUtils.getUser().getOffice().getParentId();
						if("34".equals(UserUtils.getUser().getOffice().getParentId())){
							personnelBase.setChuFlag("34");
						}else if ("95".equals(UserUtils.getUser().getOffice().getParentId())){
							personnelBase.setChuFlag("95");
						}else if ("156".equals(UserUtils.getUser().getOffice().getParentId())){
							personnelBase.setChuFlag("156");
						}
					}else{
						personnelBase.setMinFlag(UserUtils.getUser().getNo());
					}
				}
			}else{
				//未分配任何角色
				personnelBase.setMinFlag(UserUtils.getUser().getNo());
			}

		}

		/*personnelBase.setPage(page);
		page.setList(personnelBaseDao.findAllWorkInfo(personnelBase));*/

		List<PersonnelBase> personnelBaseList = personnelBaseDao.findAllWorkInfo(personnelBase);
		return customPaging(page,personnelBaseList,isAll);

		//return page;
	}

	public List<PersonnelBase> findList(PersonnelBase personnelBase) {
		return super.findList(personnelBase);
	}

	public Page<PersonnelBase> findPage(Page<PersonnelBase> page, PersonnelBase personnelBase) {
		personnelBase.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelBase);
	}
	/**
	 * @param isAll 用于判断显示部分还是全部
	 */
	public Page<PersonnelBase> findPageBeta(Page<PersonnelBase> page, PersonnelBase personnelBase,Boolean isAll) {
		//处理分页问题
		personnelBase.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		List<PersonnelBase> personnelBaseList = personnelBaseDao.findList(personnelBase);
		return customPaging(page,personnelBaseList,isAll);
	}

	public Page<PersonnelBase> findPageCopy(Page<PersonnelBase> page, PersonnelBase personnelBase) {
		personnelBase.setOfficeId(UserUtils.getUser().getOffice().getId());
		User user = UserUtils.getUser();
		personnelBase.setCreateBy(user);
		personnelBase.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPageCopy(page, personnelBase);
	}


	//在service层添加数据查重功能
	@Transactional(readOnly = false)
	public void save(PersonnelBase personnelBase) {


		User user = userDao.selectUnits(personnelBase.getIdNumber());
		if(user != null && StringUtils.isNotBlank(personnelBase.getActualUnitId())) {
			if (!personnelBase.getActualUnitId().equals(user.getOffice().getId()) && personnelBase.getActualUnitId() != null) {
				String actualUnitId = personnelBase.getActualUnitId();//实际单位id
				Office sjOffice = officeService.get(personnelBase.getActualUnitId());//实际工作单位
				String sjCompanyId = this.getCompanyIdByParentIds(sjOffice.getParentIds());
				user.getOffice().setId(actualUnitId);//实际工作单位id
				user.getCompany().setId(sjCompanyId);//实际单位所属公司id
			}
			String unitName = personnelBase.getWorkunitName();//人事命令单位
			String unitId = personnelBase.getWorkunitId();//人事命令单位id
			Office rsOffice = officeService.get(personnelBase.getWorkunitId());//人事命令单位
			if (!personnelBase.getWorkunitId().equals(this.getCompanyIdByParentIds(rsOffice.getParentIds())) && personnelBase.getWorkunitId() != null) {
				String rsCompanyId = this.getCompanyIdByParentIds(rsOffice.getParentIds());
				user.setBaseCompanyId(rsCompanyId);//原公司id
				user.setBaseOfficeId(unitId);//原单位id
				user.setBaseOfficeName(unitName);//原单位
			}
			userDao.updateUnits(user);
			// 清除原用户机构用户缓存
			CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId() );
			CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + user.getBaseOfficeId() );
			CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_ID_+ user.getId());
		}


		/*PersonnelBase personnelBaseFromBase = new PersonnelBase();*/
		/*List<PersonnelBase> list= personnelBaseDao.findAllList(personnelBaseFromBase);*/
		PersonnelBase personnelBaseFromDb = personnelBaseDao.findInfoByIdNumber(personnelBase.getIdNumber());
		if (personnelBaseFromDb != null){
			if(StringUtils.isNotBlank(personnelBaseFromDb.getId())){
				personnelBase.setId(personnelBaseFromDb.getId());
				//如果单位发生变化，生成预警信息
				if(!personnelBase.getWorkunitName().equals(personnelBaseFromDb.getWorkunitName())&&(personnelBaseFromDb.getWorkunitId() == null || !personnelBase.getWorkunitId().equals(personnelBaseFromDb.getWorkunitId()))){
					List<String> ids = new ArrayList<>();
					ids.add(personnelBaseFromDb.getId());
					detectionUnitChange(ids,personnelBase.getWorkunitId(),personnelBase.getWorkunitName());
				}
				if (StringUtils.isNotBlank(personnelBase.getActualUnitId()) && !personnelBase.getActualUnitId().equals(personnelBase.getWorkunitId())){
					/*修改user表的单位*/
					User userInfo = userDao.getInfoByIdNumber(personnelBase.getIdNumber());

					userDao.updateActOfficeInfo(userInfo.getId(), personnelBase.getActualUnitId(),personnelBase.getActualUnit());
					CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + userInfo.getOffice().getId() );
					/*绩效启动时查询的用户缓存*/
					CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_ID_+ userInfo.getId());
				}
				if (!personnelBase.getStatus().equals(personnelBaseFromDb.getStatus())){
						//User u= userDao.getInfoByIdNumber(personnelBase.getIdNumber());
						User u= userDao.getAllInfoByIdNumber(personnelBase.getIdNumber());
						if(u!=null){
							if ("1,19,2,3,4,5,6".contains(personnelBase.getStatus())){
								/*在职 恢复user表账号*/
								u.setDelFlag("0");
								userDao.updateDelFlag(u);
								CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_ID_+ u.getId());

							}else {
								userDao.delete(u);
								CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_ID_+ u.getId());
								/*不在职 删除user表账号*/
							}
						}

				}
				super.save(personnelBase);
			}else {
				super.save(personnelBase);
			}
		}else {
			super.save(personnelBase);
		}
		/*if(StringUtils.isNotBlank(personnelBaseFromDb.getId())){
			personnelBase.setId(personnelBaseFromDb.getId());
			super.save(personnelBase);
		}else{
			super.save(personnelBase);
		}*/

		/*if (personnelBase.getIdNumber().equals())*/
		/*if (list.size()== 0 ){
			super.save(personnelBase);
		}else{
			PersonnelBase personnelBaseSame= findSame(personnelBase, list);
			if(personnelBaseSame != null){
				personnelBase.setId(personnelBaseSame.getId());
				super.save(personnelBase);
			}
			else {
				//这个地方要加友好提示
				super.save(personnelBase);
			}
		}*/
	}
	/*private PersonnelBase findSame(PersonnelBase personnelBase, List<PersonnelBase> list){
		PersonnelBase result = null;
		for(PersonnelBase personnelBase1 : list) {
			if (personnelBase1.getIdNumber().equals(personnelBase.getIdNumber())) {
				result= personnelBase1;
				break;
			}
		}
		return  result;
	}
	*/
	@Transactional(readOnly = false)
	public void delete(PersonnelBase personnelBase) {
		super.delete(personnelBase);
	}

	public List<Map<String,String>> findNavList(){return super.dao.findNavList();}
	public List<Map<String,String>> findTotalNum(Map<String,String> param){return super.dao.findTotalNum(param);}

	public List<Map<String, Object>> findInfosByCode(String code) {
		return dao.findInfosByCode(code);
	}

	public List<Map<String, Object>> findInfosByCodes(List<String> codes) {
		return dao.findInfosByCodes(codes);
	}

	public List<Map<String, Object>> findJobInfosByCode(String code) {
		return dao.findJobInfosByCode(code);
	}

	public List<Map<String, Object>> findJobInfosByCodes(List<String> codes) {
		return dao.findJobInfosByCodes(codes);
	}

	/**
	 * 根据身份证号去人员基本信息表中查信息
	 * @param idNumber
	 * @return
	 */
	public PersonnelBase findInfoByIdNumber(String idNumber){
		PersonnelBase personnelBase = personnelBaseDao.findInfoByIdNumber(idNumber);
		return personnelBase;
	}

    /**
     * 保存ids批量修改工作单位
     *
     * @param ids
     * @param workUnitId
     * @param workUnitName
     */
	@Transactional(readOnly = false)
	public void updateWorkUnitByIds(List<String> ids, String workUnitId, String workUnitName) {
		//添加预警任务
		detectionUnitChange(ids,workUnitId,workUnitName);
		personnelBaseDao.updateWorkUnitByIds(ids, workUnitId, workUnitName);
		personnelBaseDao.updateActualWorkUnitByIds(ids, workUnitId, workUnitName);
	}

	//检测人员信息单位信息，若发生变化生成预警信息 ，提醒相应人员及单位
	public void detectionUnitChange(List<String> ids, String workUnitId, String workUnitName){
		List<PersonnelBase> list = personnelBaseDao.findInfosByIds(ids);//获取调动人员相关信息
		//遍历生成相应的预警信息，用于提醒各组干完成档案移交
		logger.info("预警：单位调动");
		try {
			for (PersonnelBase personnelBase: list) {
				Warning warning = new Warning();
				warning.setName("单位调动");//设置预警名称
				String oldUnitId = personnelBase.getWorkunitId();
				String oldUnitName = personnelBase.getWorkunitName();
				String newUnitId = workUnitId;
				String newUnitName = workUnitName;
				Office oldOffice;
				String oldPrentUnitIds;
				if(oldUnitId!=null){
					oldOffice = officeService.get(oldUnitId);
					oldPrentUnitIds = officeDao.findParentIdById(oldUnitId);
				}else{
					if(oldUnitName!=null && officeService.findUserByName(oldUnitName)!=null && !officeService.findUserByName(oldUnitName).isEmpty()){
						oldUnitId = officeService.findUserByName(oldUnitName);
						oldOffice = officeService.get(oldUnitId);
						oldPrentUnitIds = officeDao.findParentIdById(oldUnitId);
					}else{
						oldOffice = null;
						oldPrentUnitIds = null;
					}
				}
				Office newOffice = officeService.get(newUnitId);
				String newPrentUnitIds = officeDao.findParentIdById(newUnitId);
				String receivePerName = personnelBase.getName()+",";
				String receivePerId = personnelBase.getIdNumber()+",";
				if(oldUnitId!=null && oldUnitId.equals(newUnitId)){
					if(officeService.findUserByName(oldUnitName)==null || officeService.findUserByName(oldUnitName).isEmpty()){
						if(oldOffice!=null){
							String id = userDao.getIdByNo(oldOffice.getCode());
							User sqlUser = userDao.get(id);
							receivePerName += sqlUser.getName()+",";
							receivePerId += sqlUser.getId()+",";
						}
					}else{
						receivePerName += personnelBase.getWorkunitName()+",";
						receivePerId += officeService.findUserByName(personnelBase.getWorkunitName())+",";
					}
					if("156".equals(oldUnitId)||oldPrentUnitIds.indexOf("156")!=-1){
						receivePerId += "c90918faf2614baa8fa85230482bd43e,";
						receivePerName += "北海处政治处组织干部室";
					}else if("34".equals(oldUnitId)||oldPrentUnitIds.indexOf("34")!=-1){
						receivePerId += "34e8d855cf6b4b1ab5e7e23e7aaba658,";
						receivePerName += "南宁处政治处组织干部室";
					}else if(("95".equals(oldUnitId)||oldPrentUnitIds.indexOf("95")!=-1)){
						receivePerId += "ec3ba2efdd404f2faa520f6e8a71ec4c,";
						receivePerName += "柳州处政治处组织干部室";
					}else {
						receivePerId += "bfdf74f010c9466dba12c1589ecab7f3,";
						receivePerName += "公安局政治部组织干部处";
					}
				}
				else{
					if(officeService.findUserByName(oldUnitName)==null || officeService.findUserByName(oldUnitName).isEmpty()){
						if(oldOffice!=null){
							String id = userDao.getIdByNo(oldOffice.getCode());
							User sqlUser = userDao.get(id);
							if(sqlUser!=null){
								receivePerName += sqlUser.getName()+",";
								receivePerId += sqlUser.getId()+",";
							}

						}
					}else{
						receivePerName += oldUnitName+",";
						receivePerId += officeService.findUserByName(oldUnitName)+",";
					}
					if(officeService.findUserByName(newUnitName)==null || officeService.findUserByName(newUnitName).isEmpty()){
						String id = userDao.getIdByNo(newOffice.getCode());
						User sqlUser = userDao.get(id);
						if(sqlUser!=null){
							receivePerName += sqlUser.getName()+",";
							receivePerId += sqlUser.getId()+",";
						}
					}else{
						receivePerName += newUnitName+",";
						receivePerId += officeService.findUserByName(newUnitName)+",";
					}
					if(oldUnitId!=null){
						Map<String,String> oldMap = getZGNameAndId(oldUnitId,oldPrentUnitIds);
						Map<String,String> newMap = getZGNameAndId(newUnitId,newPrentUnitIds);
						String oldId = oldMap.get("id");
						String oldName = oldMap.get("name");
						String newId = newMap.get("id");
						String newName = newMap.get("name");
						if(oldId.equals(newId)){
							receivePerName += oldName;
							receivePerId += oldId;
						}else{
							receivePerName += oldName;
							receivePerId += oldId;
							receivePerName += newName;
							receivePerId += newId;
						}
					}else{
						//原单位id为null
						if(officeService.findUserByName(oldUnitName)!=null && !officeService.findUserByName(oldUnitName).isEmpty()){
							String oldUnitID = officeService.findUserByName(oldUnitName);
							Map<String,String> oldMap = getZGNameAndId(oldUnitId,oldPrentUnitIds);
							Map<String,String> newMap = getZGNameAndId(newUnitId,newPrentUnitIds);
							String oldId = oldMap.get("id");
							String oldName = oldMap.get("name");
							String newId = newMap.get("id");
							String newName = newMap.get("name");
							if(oldId.equals(newId)){
								receivePerName += oldName;
								receivePerId += oldId;
							}else{
								receivePerName += oldName;
								receivePerId += oldId;
								receivePerName += newName;
								receivePerId += newId;
							}
						}else{
							Map<String,String> newMap = getZGNameAndId(newUnitId,newPrentUnitIds);
							String newId = newMap.get("id");
							String newName = newMap.get("name");
							receivePerName += newName;
							receivePerId += newId;
						}


					}

				}
				warning.setReceivePerName(receivePerName); //接受人员/部门名称  调动人员，原单位，新单位，各级组干
				warning.setReceivePerId(receivePerId); //接受人员/部门 id 调动人员，原单位，新单位，各级组干在sys_user表中的id
				warning.setRepeatCycle("4");//设置重复周期  4 : 永不
				warning.setRemind("30"); //设置重复提醒时间  每半小时提醒一次
				warning.setDate(new Date());//设置预警提醒开始时间
				warning.setContinueDay("0");//收到后停止
				warning.setIsAlert("1");//选择使用弹窗
				warning.setAlertContent(personnelBase.getName()+"(身份证号为："+personnelBase.getIdNumber()+")由"+personnelBase.getWorkunitName()+"调动至"+workUnitName+",各部门请做好晋级人员档案移交");//设置弹窗内容
				warning.setAlertDegree("1");//紧急程度

				//设置创建人及更新人信息
				warning.setCreateBy(UserUtils.getUser());
				warning.setUpdateBy(UserUtils.getUser());
				warningService.save(warning);
			}
		}catch (Exception e){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			e.printStackTrace(new PrintStream(baos));
			logger.error(baos.toString());
		}

	}

	//查询所在组干的名称及id
	private Map<String,String> getZGNameAndId(String unitId, String prentUnitIds) {
		Map<String,String> map = new HashMap<String, String>();
		String name = "";
		String id = "";
		if("156".equals(unitId)||prentUnitIds.indexOf("156")!=-1){
			id = "c90918faf2614baa8fa85230482bd43e";
			name = "北海处政治处组织干部室";
		}else if("34".equals(unitId)||prentUnitIds.indexOf("34")!=-1){
			id += "34e8d855cf6b4b1ab5e7e23e7aaba658";
			name += "南宁处政治处组织干部室";
		}else if(("95".equals(unitId)||prentUnitIds.indexOf("95")!=-1)){
			id += "ec3ba2efdd404f2faa520f6e8a71ec4c";
			name += "柳州处政治处组织干部室";
		}else {
			id += "bfdf74f010c9466dba12c1589ecab7f3";
			name += "公安局政治部组织干部处";
		}
		map.put("id",id);
		map.put("name",name);
		return map;
	}


	/**
	 * 根据单位名及姓名查找身份证号
	 * @param name
	 * @return
	 */
	public String findByUnitName(String name,String unit) {
		if(!StringUtils.isNotBlank(name)&&!StringUtils.isNotBlank(unit)){
			return null;
		}
		List<String> list = dao.findByUnitName(name,unit);
		if(list != null && list.size() >0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据姓名去人员基本信息表中查信息
	 * @param name
	 * @return
	 */
	public PersonnelBase findInfoByName(String name) {
		PersonnelBase personnelBase = new PersonnelBase();
		personnelBase.setName(name);
		personnelBase.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		PersonnelBase personnelBase1 = personnelBaseDao.findInfoByIdName(personnelBase);
		return personnelBase1;
	}
	public List<PersonnelBase> findPerson(PersonnelBase param){
		return personnelBaseDao.findPerson(param);
	}

	public List<Map<String, String>> findNavTree() {
		return super.dao.findNavTree();
	}

	/**
	 * 以下方法全部添加有参
	 *
	 */

	//人员名册基本信息
	/*此方法会查出重复数据*/
	public List<Map<String, String>> findRegisterBase(PersonnelBase personnelBase){
		return personnelBaseDao.findRegisterBase(personnelBase);
	}

	/**
	 * 人员基本信息 关联 最新的警衔 行政职务  职务层次  警务技术任职资格数据
	 * 	此方法查询缓慢  务必添加身份证号索引
	 * @param personnelBase
	 * @return
	 */
	public List<Map<String, String>> findLastRegisterBase(PersonnelBase personnelBase){
		return personnelBaseDao.findLastRegisterBase(personnelBase);
	}
	public List<Map<String, String>> findNoRegisterBase(PersonnelBase personnelBase){
		return personnelBaseDao.findNoRegisterBase(personnelBase);
	}

	//全日制最高学历信息
	public List<Map<String, String>> findQrzXueLiInfo(PersonnelBase personnelBase){
		return personnelBaseDao.findQrzXueLiInfo(personnelBase);
	}

	//在职最高学历信息
	public List<Map<String, String>> findZzXueLiInfo(PersonnelBase personnelBase){
		return personnelBaseDao.findZzXueLiInfo(personnelBase);
	}

	//全日制最高学位信息
	public List<Map<String, String>> findQrzXueWeiInfo(PersonnelBase personnelBase){
		return personnelBaseDao.findQrzXueWeiInfo(personnelBase);
	}

	//在职最高学位信息
	public List<Map<String, String>> findZzXueWeiInfo(PersonnelBase personnelBase){
		return personnelBaseDao.findZzXueWeiInfo(personnelBase);
	}

	//考核信息
	public List<Map<String, String>> findCheckInfo(PersonnelBase personnelBase){
		return personnelBaseDao.findCheckInfo(personnelBase);
	}

	//履历信息
	public List<Map<String,String>> findLvLiInfo(PersonnelBase personnelBase){
		return personnelBaseDao.findLvLiInfo(personnelBase);
	}

	//奖励信息
	public List<Map<String, String>> findJiangLiInfo(PersonnelBase personnelBase){
		return personnelBaseDao.findJiangLiInfo(personnelBase);
	}

	//惩戒信息
	public List<Map<String, String>> findChengJieInfo(PersonnelBase personnelBase){
		return personnelBaseDao.findChengJieInfo(personnelBase);
	}

	//家庭成员信息
	public List<Map<String, String>> findFamilyInfo(PersonnelBase personnelBase){
		return personnelBaseDao.findFamilyInfo(personnelBase);
	}

	public Page<PoliceCertificate> findByIdNumbers(Page<PoliceCertificate> page,List<String> idNumbers) {
		return page.setList(personnelBaseDao.findByIdNumbers(idNumbers));
	}
	public Page<PoliceCertificate> selectByIdNumbers(Page<PoliceCertificate> page,List<String> idNumbers,String status) {
		return page.setList(personnelBaseDao.selectByIdNumbers(idNumbers,status));
	}
	//根据证件号idNumber获取人员信息
	public PersonnelBase findPersoByIdNumber(String idNumber) {
		return personnelBaseDao.findInfoByIdNumber(idNumber);
	}

	/**
	 * 统计分析 健康体检 未参检明细
	 * @param page
	 * @param personnelBase
	 * @return
	 */
    public Page<PersonnelBase> findHealthReferencePage(Page<PersonnelBase> page, PersonnelBase personnelBase) {
		String dateType = Optional.ofNullable(personnelBase.getDateType()).orElseGet(() -> {return "";});
		if (dateType.equals("1")){	//月度查询
			personnelBase.setYear(null);
			personnelBase.setStartDate(null);
			personnelBase.setEndDate(null);
		}else if (dateType.equals("3")){	//时间段查询
			personnelBase.setYear(null);
			personnelBase.setMonth(null);
			personnelBase.setStartDate(DateUtils.parseDate(personnelBase.getDateStart()));
			personnelBase.setEndDate(DateUtils.parseDate(personnelBase.getDateEnd()));
		}else {	//年度查询
			personnelBase.setMonth(null);
			personnelBase.setStartDate(null);
			personnelBase.setEndDate(null);
		}
    	personnelBase.setPage(page);

		personnelBase.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(), "o", "u"));
    	page.setList(dao.findHealthReferenceList(personnelBase));
    	return page;
    }

	/**
	 * 统计分析 查询统计分析明细
	 * @param page
	 * @param personnelBase
	 * @return
	 */
	public Page<PersonnelBase> findPoliticsFacePage(Page<PersonnelBase> page, PersonnelBase personnelBase) {
		personnelBase.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		String dateType = Optional.ofNullable(personnelBase.getDateType()).orElseGet(() -> {return "";});
		if (dateType.equals("1")){	//月度查询
			personnelBase.setYear(null);
			personnelBase.setStartDate(null);
			personnelBase.setEndDate(null);
		}else if (dateType.equals("3")){	//时间段查询
			personnelBase.setYear(null);
			personnelBase.setMonth(null);
			personnelBase.setStartDate(DateUtils.parseDate(personnelBase.getDateStart()));
			personnelBase.setEndDate(DateUtils.parseDate(personnelBase.getDateEnd()));
		}else {	//年度查询
			personnelBase.setMonth(null);
			personnelBase.setStartDate(null);
			personnelBase.setEndDate(null);
		}
		personnelBase.setPage(page);
		page.setList(dao.findPoliticsFaceList(personnelBase));
		return page;
	}


	public List<PersonnelBase> findOneMonthTX() {
		return personnelBaseDao.findOneMonthTX();
	}

	/*
	* 根据单位id查询用户
	* */
	public List<PersonnelBase> findInfoByIdUnitId(String officeId) {
		List<PersonnelBase> list =personnelBaseDao.findInfoByIdUnitId(officeId);
		return list;
	}
	/*
	* 复合查询，信息集下拉框
	* */
	public List<Map<String, String>> findNavTableName() {
		return super.dao.findTableName();
	}

	public PersonnelBase selectBean(String idNumber){
		return personnelBaseDao.selectBean(idNumber);
	}

	//查询男士已达到59 60 的年龄 即将达到退休年龄的人员信息
	public List<PersonnelBase> findManTXList() {
		return personnelBaseDao.findManTXList();
	}

	//查询出所有女士的人员信息
	public List<PersonnelBase> findWomanList() {
		return personnelBaseDao.findWomanList();
	}

	//通过身份证集合获取人员详细信息
    public List<PersonnelBase> findInfoByList(List<PersonnelBase> list) {
		return personnelBaseDao.findInfoByList(list);
    }
    public PersonnelBase findUserMessage(String loginName){
		return personnelBaseDao.findUserMessage(loginName);
	}

    public List <Map<String, String>> findUserUnit(String loginName){
		return personnelBaseDao.findUserUnit(loginName);
	}

	public String findUserUnitId(String code){
		return personnelBaseDao.findUserUnitId(code);
	}

	//根据单位id查询部门和警种
    public List<String> findBmhjzByUnitId(String unitId) {
		return personnelBaseDao.findBmhjzByUnitId(unitId);
    }
	//根据单位id查询职位简称
	public List<String> findjobAbbreviationByUnitId(String unitId) {
		return personnelBaseDao.findjobAbbreviationByUnitId(unitId);
	}

	public Map<String, String> findUnitByIdNumber(String idNumber){
		return personnelBaseDao.findUnitByIdNumber(idNumber);
	}

	public List<PersonnelBase> findAllUserByUnitId(String unitId){
		return personnelBaseDao.findAllUserByUnitId(unitId);
	}

	/**
	 * 性别 年龄统计分析
	 * 根据年龄计算出生日期进行统计
	 * @param id
	 * @param ageType
	 * @param age
	 * @param startAge
	 * @param endAge
	 * @param month
	 * @return
	 */
    public List<Map<String, String>> countPersonnelAge(String id, String ageType, Integer age, int startAge, int endAge, String month) {
		Date yearDate = TimeUtils.getBornBirthday(age);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(yearDate);
		Integer year = calendar.get(Calendar.YEAR);
		Date startDate = TimeUtils.getBornBirthday(endAge);
		Date endDate = TimeUtils.getBornBirthday(startAge);
		if (ageType !=null && ageType.equals("1")){	//年龄查询
			startDate=null;
			endDate=null;
		}else if (ageType !=null && ageType.equals("2")){	//年龄段查询
			year=null;
		}
		return ChartLabelUtils.fillLabel(dao.countPersonnelAge(id,year,startDate,endDate,month), DictUtils.getDictList("sex"));
    }

	public List<Map<String, String>> countNation(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Date startDate = DateUtils.parseDate(dateStart);
		Date endDate = DateUtils.parseDate(dateEnd);
		if (dateType!=null &&dateType.equals("1")){	//月份查询
			year=null;
			startDate=null;
			endDate=null;
		}else if (dateType!=null &&dateType.equals("3")){	//时间段查询
			year=null;
			month=null;
		}else {	//年度查询
			month=null;
			startDate=null;
			endDate=null;
		}
		return dao.countNation(id,year,startDate,endDate,month);
	}

	public List<Map<String, String>> countWorkYear(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Date startDate = DateUtils.parseDate(dateStart);
		Date endDate = DateUtils.parseDate(dateEnd);
		if (dateType!=null &&dateType.equals("1")){	//月份查询
			year=null;
			startDate=null;
			endDate=null;
		}else if (dateType!=null &&dateType.equals("3")){	//时间段查询
			year=null;
			month=null;
		}else {	//年度查询
			month=null;
			startDate=null;
			endDate=null;
		}
		List<Map<String, Integer>> list = dao.countWorkYear(id,year,startDate,endDate,month);
		List<Map<String,String>> result = new ArrayList<>();
		int start= 0;
		int end= 5;
//		list.stream().filter(item -> item.get("label")>= start.get() && item.get("label")<= end.get()).collect(Collectors.summarizingInt(item->item.get("count")));
		String[] labelArrays = new String[]{"0-5","6-10","11-15","16-20","21-25","26-30","31-35","36-40","41年及以上"};
		for (String label:labelArrays){
			int finalStart = start;
			int finalEnd = end;
			long count = list.stream().filter(item -> item.get("label")> finalStart && item.get("label")<= finalEnd).collect(Collectors.summarizingInt(item->item.get("count"))).getSum();
			start+=5;
			end+=5;
			if (start==40){
				end = 100;
			}
			Map<String,String> map = new HashMap<>();
			map.put("count",count+"");
			map.put("label",label);
			result.add(map);
		}
		return result;
	}

	/**
	 * 人员类别统计
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
	public List<Map<String, String>> countPersonnelCategory(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Date startDate = DateUtils.parseDate(dateStart);
		Date endDate = DateUtils.parseDate(dateEnd);
		if (dateType!=null &&dateType.equals("1")){	//月份查询
			year=null;
			startDate=null;
			endDate=null;
		}else if (dateType!=null &&dateType.equals("3")){	//时间段查询
			year=null;
			month=null;
		}else {	//年度查询
			month=null;
			startDate=null;
			endDate=null;
		}
		return dao.countPersonnelCategory(id,year,startDate,endDate,month);
	}

	public Page<PersonnelBase> findAgePage(Page<PersonnelBase> page, PersonnelBase personnelBase) {
		personnelBase.setPage(page);
		Date yearDate = TimeUtils.getBornBirthday(personnelBase.getAge());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(yearDate);
		Integer year = calendar.get(Calendar.YEAR);
		Date startDate = TimeUtils.getBornBirthday(personnelBase.getEndAge());
		Date endDate = TimeUtils.getBornBirthday(personnelBase.getStartAge());
		String ageType = personnelBase.getAgeType();
		if (ageType !=null && ageType.equals("1")){	//年龄查询
			personnelBase.setYear(year);
			personnelBase.setStartDate(null);
			personnelBase.setEndDate(null);
		}else if (ageType !=null && ageType.equals("2")){	//年龄段查询
			personnelBase.setYear(null);
			personnelBase.setStartDate(startDate);
			personnelBase.setEndDate(endDate);
		}
		page.setList(dao.findAgeList(personnelBase));
		return page;
	}

	public Page<PersonnelBase> findChartsNationPage(Page<PersonnelBase> page, PersonnelBase personnelBase) {
		personnelBase.setPage(page);
		page.setList(dao.findChartsNationList(personnelBase));
		return page;
	}

	public Page<PersonnelBase> findChartsCategoryPage(Page<PersonnelBase> page, PersonnelBase personnelBase) {
		personnelBase.setPage(page);
		page.setList(dao.findChartsCategoryList(personnelBase));
		return page;
	}

	public Page<PersonnelBase> findChartsWorkYearPage(Page<PersonnelBase> page, PersonnelBase personnelBase) {
		personnelBase.setPage(page);
		int start = 0;
		int end = 5;
		String[] labelArrays = new String[]{"0-5","6-10","11-15","16-20","21-25","26-30","31-35","36-40","41年及以上"};
		for (String label:labelArrays){
			if (label.equals("41年及以上")){
				start = 40;
				end = 100;
				personnelBase.setStartAge(start);
				personnelBase.setEndAge(end);
				page.setList(dao.findChartsWorkYearList(personnelBase));
				break;
			}else if (label.equals(personnelBase.getLabel())){
				personnelBase.setStartAge(start);
				personnelBase.setEndAge(end);
				page.setList(dao.findChartsWorkYearList(personnelBase));
				break;
			}else {
				start += 5;
				end += 5;
			}
		}
		return page;
	}

	public String getIdNumberById(String id){
		return personnelBaseDao.getIdNumberById(id);
	}


	public Page customPaging(Page<PersonnelBase> page,List<PersonnelBase> resultList,Boolean isAll){
		//自定义分页
		if(isAll){
			//返回全部
			page.setCount(resultList.size());
			page.setList(resultList);
		}else{
			//返回部分
			page.setCount(resultList.size());
			int pageNo = page.getPageNo();
			int pageSize = page.getPageSize();
			int count = resultList.size();
			List<PersonnelBase> partMapList;
			if(pageSize*pageNo<count){
				partMapList = resultList.subList((pageNo-1)*pageSize,pageSize*pageNo);
			}else{
				partMapList = resultList.subList((pageNo-1)*pageSize,count);
			}
			page.setList(partMapList);
		}
		return page;
	}

	public String getCompanyIdByParentIds(String parentIds) {
		if(parentIds.contains(",34,")){
			return "34";
		}else if(parentIds.contains(",95,")){
			return "95";
		}else if(parentIds.contains(",156,")){
			return "156";
		}else {
			return "1";
		}

	}

	/**
	 * 按着人事命令字段进行数据过滤  查看本处
	 * @param page
	 * @param personnelBase
	 * @return
	 */
	public Page<PersonnelBase> findPageOfWorkUnit(Page<PersonnelBase> page, PersonnelBase personnelBase) {
		personnelBase.getSqlMap().put("dsf",alterDataScopeFilter(UserUtils.getUser(),"o","a"));
		personnelBase.setPage(page);
		page.setList(dao.findPageOfWorkUnit(personnelBase));
		return page;
	}
}