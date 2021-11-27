/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairActivityMien;
import com.thinkgem.jeesite.modules.affair.entity.AffairWenhua;
import com.thinkgem.jeesite.modules.affair.entity.AffairWenyi;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文化人才DAO接口
 * @author cecil.li
 * @version 2020-03-06
 */
@MyBatisDao
public interface AffairWenhuaDao extends CrudDao<AffairWenhua> {

    /**
     * 根据姓名查询出对应的作品
     * @param name
     * @return
     */
    List<String> findusernameList(@Param(value = "name") String name);

    /**
     * 根据作品情况获取对应的奖项
     * @param workSituation
     * @return
     */
    List<String> fingproductionList(@Param(value = "workSituation") String workSituation);

    /**
     * 获取稿件统计
     * @param name
     * @return
     */
    String findManuscriptList(@Param(value = "name") String name);

    /**
     *
     * 批量
     * @param ids
     * @return
     */
    List<AffairWenhua> findByIds(@Param(value = "ids") List<String> ids);


    List<Map<String, String>> countJuLiteraryTalent(@Param("parentId") String parentId, @Param("year")Integer year,
                                                    @Param("startDate") Date startDate, @Param("endDate")Date endDate, @Param("month")String month);

    List<Map<String, String>> countChuLiteraryTalent(@Param("parentId") String parentId, @Param("year")Integer year,
                                                     @Param("startDate") Date startDate, @Param("endDate")Date endDate, @Param("month")String month);

    List<Map<String, String>> countJuTalentJoin(@Param("parentId") String parentId, @Param("year")Integer year,
                                                @Param("startDate") Date startDate, @Param("endDate")Date endDate, @Param("month")String month);

    List<Map<String, String>> countChuTalentJoin(@Param("parentId") String parentId, @Param("year")Integer year,
                                                 @Param("startDate") Date startDate, @Param("endDate")Date endDate, @Param("month")String month);

    List<AffairWenhua> findLiteraryTalentList(AffairWenhua affairWenhua);

    List<AffairWenhua> findTalentJoinList(AffairWenhua affairWenhua);

    List<AffairWenhua> selectSpeciality(@Param("name")String name);

    String selectName(@Param("id")String id);
}