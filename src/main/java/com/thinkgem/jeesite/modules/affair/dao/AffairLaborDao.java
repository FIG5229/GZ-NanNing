/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLabor;
import com.thinkgem.jeesite.modules.affair.entity.AffairSalaryGather;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelEndowmentInsuranceDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 劳资DAO接口
 * @author cecil.li
 * @version 2020-01-19
 */
@MyBatisDao
public interface AffairLaborDao extends CrudDao<AffairLabor> {

    List<String> selectAllId();

    List<AffairSalaryGather> selectAsgList(@Param("year")String year,@Param("treeId")String treeId,@Param("name")String name,@Param("unit")String unit, @Param("userOffice")String userOffice);

    List<AffairLabor> selectBeanByIdNumber(@Param("idNumber")String idNumber,@Param("year")String year);

    AffairLabor selectJFJS(@Param("idNumber") String idNumber,@Param("timeYear") String timeYear);

    List<AffairLabor> selectMessage();

    List<AffairLabor> selectByYearAndTreeId(@Param("year")String year, @Param("treeId")String treeId);

    List<AffairSalaryGather> selectAsgListMonth(@Param("year")String year);
}