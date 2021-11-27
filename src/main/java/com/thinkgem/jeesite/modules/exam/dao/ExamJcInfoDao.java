/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamJcInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 奖惩信息库DAO接口
 * @author mason.xv
 * @version 2020-01-03
 */
@MyBatisDao
public interface ExamJcInfoDao extends CrudDao<ExamJcInfo> {

    ExamJcInfo findInfoByFileNum(@Param(value = "fileNum") String fileNum);
    //jcType 奖惩类型  jcObject 奖惩对象  unitId 单位id
    List<ExamJcInfo> findJCListByYearMonth(@Param("jcObject")String jcObject,@Param("unitId")String unitId);

    List<ExamJcInfo> findExamList(ExamJcInfo examJcInfo);
}