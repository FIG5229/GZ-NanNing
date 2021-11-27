package com.thinkgem.jeesite.modules.search.enums;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.search.entity.SearchFor;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface SearchForDao extends CrudDao<SearchFor> {

//    public List<Map<String, Object>> findList(@Param(value = "sex") String sex, @Param(value = "nation") String nation, @Param(value = "mianmao") String mianmao, @Param(value = "unitId") String unitId, @Param(value = "rewardName") String rewardName, @Param(value = "approcalOrgType") String approcalOrgType, @Param(value = "cjType") String cjType, @Param(value = "cjName") String cjName, @Param(value = "cjOrgType") String cjOrgType, @Param(value = "year") String year, @Param(value = "conclusion") String conclusion, @Param(value = "xlName") String xlName, @Param(value = "schoolName") String schoolName);
//    public List<Map<String, Object>> findLists(@Param(value = "sex") String sex, @Param(value = "nation") String[] nation, @Param(value = "mianmao") String[] mianmao, @Param(value = "rewardName") String rewardName, @Param(value = "approcalOrgType") String[] approcalOrgType, @Param(value = "cjType") String[] cjType, @Param(value = "cjName") String cjName, @Param(value = "cjOrgType") String[] cjOrgType, @Param(value = "year") String year, @Param(value = "conclusion") String conclusion, @Param(value = "xlName") String xlName, @Param(value = "schoolName") String schoolName);
public List<Map<String, Object>> findLists(SearchFor searchFor);
//    public List<Map<String, Object>> findBaseList(@Param(value = "sex") String sex, @Param(value = "nation") String nation, @Param(value = "mianmao") String mianmao, @Param(value = "unitId") String unitId);
//    public List<Map<String, Object>> findBaseLists(@Param(value = "sex") String sex, @Param(value = "nation") String[] nation, @Param(value = "mianmao") String[] mianmao, @Param(value = "age") Date age);
public List<Map<String, Object>> findBaseLists(SearchFor searchFor);
//    public List<Map<String, Object>> findInfo(@Param(value = "kpYear") String kpYear, @Param(value = "score") Integer score);
public List<Map<String, Object>> findInfo(SearchFor searchFor);
}

