package com.thinkgem.jeesite.modules.search.enums;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.search.entity.SearchFors;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface SearchForsDao extends CrudDao<SearchFors> {

public List<Map<String, Object>> findListInfo(SearchFors searchFors);
//public List<Map<String, Object>> findBaseList(@Param(value = "sex") String sex, @Param(value = "nation") String nation, @Param(value = "mianmao") String mianmao, @Param(value = "unitId") String unitId);
public List<Map<String, Object>> findBaseList(SearchFors searchFors);
//public List<Map<String, Object>> findInfo(SearchFor searchFor);
}

