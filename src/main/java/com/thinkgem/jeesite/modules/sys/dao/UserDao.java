/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户DAO接口
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {

	/**
	 * 根据登录名称查询用户
	 *
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(User user);

	/**
	 * 根据用户名查找用户
	 *
	 * @param name
	 * @return
	 */
	public User getByUserName(String name);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 *
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);

	public List<User> findUserByRoleId(@Param(value = "roleId") String roleId);

	/**
	 * 查询全部用户数目
	 *
	 * @return
	 */
	public long findAllCount(User user);

	/**
	 * 更新用户密码
	 *
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);

	/**
	 * 更新登录信息，如：登录IP、登录时间
	 *
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 *
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);

	/**
	 * 插入用户角色关联数据
	 *
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);

	/**
	 * 更新用户信息
	 *
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);

	String getIdByNo(String no);

	List<Map<String, String>> getAllUser();


	String findUserByTree(@Param(value = "value") String value);

	//根据单位id查询党建管理用户信息
	User findDJUserByOfficeId(@Param(value = "unitId") String unitId);

    String selectUserId(@Param(value = "idNumber") String idNumber);

	List<User> findUserByOfficeIdRoleId(User user);

    String selectUserIdNumber(@Param("notSjName") String notSjName);

	String selectName(@Param("idNumber") String idNumber);

    String selectUnit(@Param("idN") String idN);

	/**
	 * 根据角色id查找所有的民警
	 */
	public List<User> findMinJingByRoleId(@Param("id") String id);

	String findUserIdByIdNumber(@Param("idNumber") String idNumber);

    String selectUnitName(@Param("name") String name);

    String selectUnitId(@Param("code") String code);

    List<Map<String, String>> findJcLdByRoleId(@Param("id") String id);

	/**
	 * 根据党组织id查询user
	 *
	 * @param partyId
	 */
    List<User> getUserByPartyId(String partyId);

    String selectJkcId(@Param("code") String code);

    List<User> getUserByRoleId(@Param("roleIds") List<String> roleIds);

	List<User> getUserByTwId(@Param("unitId") String unitId);

	// 获取党组织书记user信息
    User getPartyShujiUser(String partyBranchId);

	// 根据单位id 查询user信息
    List<User> getUsersByOfficeIds(@Param("unitIds") List<String> unitIds);

	// 根据团支部名称 查询user信息
    List<User> selUserBytPartyName(String tParty);

	//根据no查询user
	List<User> getUserByNo(String idNumber);

	User getInfoByIdNumber(String idNumber);

	public int updateOfficeInfo(@Param("id") String id, @Param("officeId") String officeId, @Param("companyId") String companyId);

	String selectTwId(@Param("twId") String twId);

	// 根据单位id 查询userId
	List<String> getUserIdByUnitIds(@Param("unitIds") List<String> unitIds);

	/*根据人员实际单位更新用户表的单位信息*/
	void updateActOfficeInfo(@Param("id") String id, @Param("officeId") String officeId, @Param("officeName") String officeName);

	User getUnitUserByOfficeId(@Param("officeId") String officeId);

	String selectUserIdNumberByNameAndPartyId(@Param("shuji") String shuji, @Param("id") String id);

	User selectUnits(String idNumber);

	void updateUnits(User user);

	void updateDelFlag(User u);

	//不区分是否被删除
	User getAllInfoByIdNumber(String idNumber);

	/**
	 * 根据登录名称查询用户
	 *
	 * @param loginName
	 * @return
	 */
	List<User> getListByLoginName(@Param("loginName") String loginName);
}
