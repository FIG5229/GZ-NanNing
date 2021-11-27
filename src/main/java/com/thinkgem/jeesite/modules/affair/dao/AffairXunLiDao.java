/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 训厉功能
 * @author tom.fu
 * @version 2020-8-20
 */
@MyBatisDao
public interface AffairXunLiDao extends CrudDao<AffairXunLi>{


       AffairXunLi findPersonnel(@Param("idNumber") String idNumber);

       /**
        * 委外培训
        * @param idNumber
        * @return
        */
       List<AffairXunLiDetails> findXunLiTrainOutSource(String idNumber);

       /**
        * 交流学历
        * @param idNumber
        * @return
        */
       AffairXunLiDetails findXunLiExchangeLearning(String idNumber);

       /**
        * 岗位练兵
        * @param idNumber
        * @return
        */
       List<AffairXunLiDetails> findXunLiJob(String idNumber);

       /**
        * 在线课程
        * @param
        * @return
        */
       AffairXunLiDetails findXunLiOnlineCourse();

       /**
        * 培训班课程
        * @param
        * @return
        */
       List<AffairXunLiDetails> findXunLiTrainin(String idNumber);

       /**
        * 考试
        * @param idNumber
        * @return
        */
       List<AffairXunLi> findXunLiExamination(String idNumber);


       /**
        * 考试
        * @param idNumber
        * @return
        */
       List<AffairXunLi> findXunLiExaminationTwo(String idNumber);

       /**
        * 导出
        * @param idNumber
        * @return
        */
       List<Map<String, String>> findPageTwo(@Param("idNumber") String idNumber);

       /**
        * 添加附件
        * @param appendfile
        * @param idNumber
        * @return
        */
       int insertOne(@Param("appendfile") String appendfile,@Param("idNumber") String idNumber);


       void insertTwo(AffairXunLi affairXunLi);

       AffairXunLi findXueWei(@Param("idNumber") String idNumber);

       AffairXunLi findJob(@Param("idNumber") String idNumber);

       AffairXunLi findTra(@Param("idNumber") String idNumber);

       List<AffairXunLi> findXueLi(@Param("idNumber") String idNumber);

       AffairXunLi findMes(@Param("idNumber") String idNumber);

    AffairXunLi findDates(@Param("idNumber")String idNumber);

       PersonnelBase findPerson(@Param("idNumber") String idNumber);

       List<Map<String,String>> findOuts(@Param("idNumber") String idNumber);
       List<Map<String,String>> findJobs(@Param("idNumber") String idNumber);
       List<Map<String,String>> findAcms(@Param("idNumber") String idNumber);

       List<AffairXunLi> findOutsDates(@Param("idNumber") String idNumber1);

       List<AffairXunLi> findJobsDates(@Param("idNumber") String idNumber1);

    AffairJobTraining findjobTrining(@Param("idNumber") String idNumber1);
}