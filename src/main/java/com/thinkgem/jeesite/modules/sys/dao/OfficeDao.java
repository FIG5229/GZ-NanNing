/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGeneralSituation;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.Map;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {

    Office findOneById(@Param(value="id") String id);

    String findByName(@Param(value = "name") String name);

    List<Office> findChildById(@Param(value="id") String id);
    
	List<String> findCodesByParentId(@Param("id") String id);

	String findCodeById(@Param("id") String id);

	List<String> findIdsByParentId(@Param("id") String id);

    List<String> findListByName(@Param(value = "name") String name);

    Map<String,String> getOrgRelation(@Param(value="id") String id);

    List<String> findListUserByName(@Param(value = "name") String name);

    String findParentIdById(@Param("id") String id);

    //根据id找name
    String findNameById(@Param(value = "id")String id);

    //绩效自动考评
    List<Map<String, String>> findAllOffice();

    /*查询绩效考评树*/
    List<Map<String, Object>> findExamObjTree(@Param("workflowId") String workFlowId,@Param("parent")String parent,
                              @Param("type")String type,@Param("userId")String userId,@Param("companyId")String companyId);

    String findCodeByUnitId(@Param("unitId")String unitId);

    String findUserIdByCode(@Param("id")String id);

    List<Office> selectChuUnit();

    List<Office> selectChuUnitChu();

    String selectCode(@Param("name") String name);

    String selectPartyBranch(@Param("unitId") String unitId);

    List<Map<String, Object>> selectUnitId(@Param("idNumber") String idNumber);

    List<Office> selectChuXuanchuanshi();

    List<Office> selectJkcUnit();

    List<Office> getChuUnit(@Param("chuId")String chuId, @Param("type")String type);

    List<Office> selectChuUnitList(@Param("chuId") String chuId);

    String selectUnitName(@Param("idNumber") String idNumber);

    String selectUnitCode(@Param("idNumber") String idNumber);

    Office selectBeanById(@Param("id") String id);

    Office findUnitIdByCode(String code);

    User findUserById(String unitId);
}
