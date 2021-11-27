/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairSwapExercise;
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainCombat;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 交流锻炼DAO接口
 * @author jack.xu
 * @version 2020-07-16
 */
@MyBatisDao
public interface AffairSwapExerciseDao extends CrudDao<AffairSwapExercise> {
    List<AffairSwapExercise> findByIds(@Param(value = "ids") List<String> ids);


    List<AffairSwapExercise> selectTrain(String idNubmer);

    List<AffairSwapExercise> findBean(@Param(value = "userName")String userName,
                                      @Param(value = "number")String number,
                                      @Param(value = "name")String name,
                                      @Param(value = "idNumber")String idNumber,
                                      @Param(value = "policeRank")String policeRank,
                                      @Param(value = "policeClassification")String policeClassification,
                                      @Param(value = "personType")String personType,
                                      @Param(value = "managementType")String managementType,
                                      @Param(value = "post")String post,
                                      @Param(value = "postLevel")String postLevel,
                                      @Param(value = "begin") Date begin,
                                      @Param(value = "end")Date end);

    String findofficeId(@Param("unit") String unit);
}