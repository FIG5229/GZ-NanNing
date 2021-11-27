package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairNewsCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface AffairNewsCountDao extends CrudDao<AffairNewsCount> {

    List<AffairNewsCount> countByUnit(AffairNewsCount affairNewsCount);
    List<AffairNewsCount> countByJuUnit(AffairNewsCount affairNewsCount);
    List<AffairNewsCount> countByAuthor(AffairNewsCount affairNewsCount);
    List<AffairNewsCount> countByName(AffairNewsCount affairNewsCount);
    List<AffairNewsCount> detailCountByUnit(AffairNewsCount affairNewsCount);
    List<AffairNewsCount> detailCountByAuthor(AffairNewsCount affairNewsCount);
    List<AffairNewsCount> detailCountByName(AffairNewsCount affairNewsCount);
    List<Map<String,String>> findAllUnit(String officeId);
}
