/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairComment;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 党员民主评议DAO接口
 * @author eav.liu
 * @version 2019-11-11
 */
@MyBatisDao
public interface AffairCommentDao extends CrudDao<AffairComment> {

    List<Map<String, String>> findGradeInfoById(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<Map<String, String>> findGradeInfoByIds(@Param("ids") List<String> ids, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<AffairComment> echartSexFindPageByPbId(AffairComment affairComment);

    List<AffairComment> echartSexFindPageByPbIds(AffairComment affairComment);

    // 审核
    void shenHeSave(@Param(value = "affairComment") AffairComment affairComment);

    int updateComment(@Param(value = "affairComment")AffairComment affairComment);

    Integer selectNumber(@Param("id") String id,@Param("yearL")  String yearL, @Param("yearT") String yearT);
}