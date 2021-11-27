/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelSkill;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelTalents;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 专长信息集DAO接口
 * @author cecil.li
 * @version 2019-11-11
 */
@MyBatisDao
public interface PersonnelSkillDao extends CrudDao<PersonnelSkill> {

    PersonnelSkill selectIdNumber(@Param("id") String id);

    PersonnelSkill selectBean(@Param("id") String id);

    List<PersonnelTalents> findRenCaiList(PersonnelTalents personnelTalents);
    //专长情况-统计分析
    List<Map<String,Object>> findSpecialityCount(@Param("unitId") String unitId);
    //专长情况 - 统计分析 - 查看详情
    List<PersonnelTalents> findSpecialityList(PersonnelTalents personnelTalents);

    void deleteByIdNumber(String idNumber);

    void deleteByIdNumbers(@Param("idNumbers") List<String> idNumbers);


}