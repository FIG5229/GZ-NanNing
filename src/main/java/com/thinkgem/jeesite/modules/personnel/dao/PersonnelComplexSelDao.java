package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 人员复合查询 DAO 用于查询 表名，字段名
 * @author kevin.jia,
 * @version 2020/9/18
 */
@MyBatisDao
public interface PersonnelComplexSelDao {
    List<Map<String, String>> selTableNames();
    List<Map<String, String>> selColumnByTableName(String tableName);

    List<Map<String, Object>> selCustomSql(String customSql);

    List<PersonnelBase> selCustomBatchSql(String customBatchSqlStr);

    List<Map<String, Object>> selDictTypeByTC(@Param("tableSel") String tableSel, @Param("columnSel") String columnSel);
}
