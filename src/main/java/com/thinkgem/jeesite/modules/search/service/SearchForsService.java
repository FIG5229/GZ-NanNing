package com.thinkgem.jeesite.modules.search.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SearchForsService {

      List<Map<String, Object>> findListinfo(@Param(value = "sex") String sex, @Param(value = "nation") String nation, @Param(value = "mianmao") String mianmao, @Param(value = "unitId") String unitId, @Param(value = "rewardName") String rewardName, @Param(value = "approcalOrgType") String approcalOrgType, @Param(value = "cjType") String cjType, @Param(value = "cjName") String cjName, @Param(value = "cjOrgType") String cjOrgType, @Param(value = "year") String year, @Param(value = "conclusion") String conclusion, @Param(value = "xlName") String xlName, @Param(value = "schoolName") String schoolName);
//      List<Map<String, Object>> findLists(@Param(value = "sex") String sex, @Param(value = "nation") String[] nation, @Param(value = "mianmao") String[] mianmao, @Param(value = "rewardName") String rewardName, @Param(value = "approcalOrgType") String[] approcalOrgType, @Param(value = "cjType") String[] cjType, @Param(value = "cjName") String cjName, @Param(value = "cjOrgType") String[] cjOrgType, @Param(value = "year") String year, @Param(value = "conclusion") String conclusion, @Param(value = "xlName") String xlName, @Param(value = "schoolName") String schoolName);
      List<Map<String, Object>> findBaseList(@Param(value = "sex") String sex, @Param(value = "nation") String nation, @Param(value = "mianmao") String mianmao, @Param(value = "unitId") String unitId);
//      List<Map<String, Object>> findBaseLists(@Param(value = "sex") String sex, @Param(value = "nation") String[] nation, @Param(value = "mianmao") String[] mianmao, @Param(value = "age") Date age);
//      List<Map<String, Object>> findInfo(@Param(value = "kpYear") String kpYear, @Param(value = "score") Integer score);

}
