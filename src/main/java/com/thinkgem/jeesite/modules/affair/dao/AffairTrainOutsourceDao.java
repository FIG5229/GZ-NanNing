/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainOutsource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 委外培训DAO接口
 * @author jack.xu
 * @version 2020-07-17
 */
@MyBatisDao
public interface AffairTrainOutsourceDao extends CrudDao<AffairTrainOutsource> {
	List<AffairTrainOutsource> findByIds(@Param(value = "ids") List<String> ids);

    List<AffairTrainOutsource> selectBeanByUnit(@Param(value = "unit") String unit);

    List<String> selectAllId();

    String selectPeople(String id);

    String selectOffJob(@Param(value = "id")String id,@Param(value = "num")String num);


    String selecthq(@Param(value = "id")String id,@Param(value = "num") String num);


    String selectUnit(@Param(value = "id")String id);

    String findofficeId(@Param("unit") String unit);
}