/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairMedicalInsurance;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 医疗保险DAO接口
 * @author jack.xu
 * @version 2020-07-03
 */
@MyBatisDao
public interface AffairMedicalInsuranceDao extends CrudDao<AffairMedicalInsurance> {
//    AffairMedicalInsurance selectCardinalByIdNumber(String idNumber);

    //从劳资模块获得信息
    List<Map<String, String>> findBase(@Param("year") String year, @Param("userOffice")String userOffice);

}