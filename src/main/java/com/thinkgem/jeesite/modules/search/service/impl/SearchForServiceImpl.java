package com.thinkgem.jeesite.modules.search.service.impl;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.search.entity.SearchFor;
import com.thinkgem.jeesite.modules.search.enums.SearchForDao;
import com.thinkgem.jeesite.modules.search.service.SearchForService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class SearchForServiceImpl extends CrudService<SearchForDao, SearchFor> implements SearchForService {
    @Autowired
    private SearchForDao searchForDao;

   /* @Override
    public PagerSearchResult<PersonnelBase> findList(String sex, String nation, String mianmao, int from, int size) {
        return searchForDao.findList(sex, nation, mianmao, from, size);
    }*/
  /* @Override
    public List<Map<String, Object>> findList(String sex, String nation, String mianmao, String unitId, String rewardName, String approcalOrgType, String cjType, String cjName, String cjOrgType, String year, String conclusion, String xlName, String schoolName){
        return searchForDao.findList(sex, nation, mianmao, unitId, rewardName, approcalOrgType, cjType, cjName, cjOrgType, year, conclusion, xlName, schoolName);
      *//* SearchFors searchFors = new SearchFors();
       SearchFors.setSex(sex);
       SearchFors.setNation(nation);
       SearchFors.setMianmao(mianmao);
       SearchFors.setRewardName(rewardName);
       SearchFors.setApprocalOrgType(approcalOrgType);
       SearchFors.setCjType(cjType);
       SearchFors.setCjName(cjName);
       SearchFors.setCjOrgType(cjOrgType);
       SearchFors.setYear(year);
       SearchFors.setConclusion(conclusion);
       SearchFors.setXlName(xlName);
       SearchFors.setSchoolName(schoolName);
       SearchFors.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
       return searchForDao.findBaseLists(SearchFors);*//*
    }*/

    @Override
    public List<Map<String, Object>> findLists(String sex, String[] nation, String[] mianmao, String rewardName, String[] approcalOrgType, String[] cjType, String cjName, String[] cjOrgType, String year, String conclusion, String xlName, String schoolName) {
//        searchFor.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
//        return searchForDao.findLists(sex, nation, mianmao, rewardName, approcalOrgType, cjType, cjName, cjOrgType, year, conclusion, xlName, schoolName);
        SearchFor searchFor2 = new SearchFor();
        searchFor2.setSex(sex);
        searchFor2.setNation(nation);
        searchFor2.setMianmao(mianmao);
        searchFor2.setRewardName(rewardName);
        searchFor2.setApprocalOrgType(approcalOrgType);
        searchFor2.setCjType(cjType);
        searchFor2.setCjName(cjName);
        searchFor2.setCjOrgType(cjOrgType);
        searchFor2.setYear(year);
        searchFor2.setConclusion(conclusion);
        searchFor2.setXlName(xlName);
        searchFor2.setSchoolName(schoolName);
        searchFor2.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        //searchFor.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        return searchForDao.findBaseLists(searchFor2);
        //return searchForDao.findBaseLists(sex, nation, mianmao, age);

   }

   /* @Override
    public List<Map<String, Object>> findBaseList(String sex, String nation, String mianmao, String unitId) {
//        searchFor.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        return searchForDao.findBaseList(sex, nation, mianmao, unitId);
    }*/

    @Override
    public List<Map<String, Object>> findBaseLists(String sex, String[] nation, String[] mianmao, Date age) {
        SearchFor searchFor2 = new SearchFor();
        searchFor2.setSex(sex);
        searchFor2.setNation(nation);
        searchFor2.setMianmao(mianmao);
        searchFor2.setAge(age);
        searchFor2.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        //searchFor.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        return searchForDao.findBaseLists(searchFor2);
        //return searchForDao.findBaseLists(sex, nation, mianmao, age);
    }

    @Override
    public List<Map<String, Object>> findInfo(String kpYear, Integer score) {
//        searchFor.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        SearchFor searchFor2 = new SearchFor();
        searchFor2.setKpYear(kpYear);
        searchFor2.setScore(score);
        searchFor2.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        //searchFor.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        return searchForDao.findBaseLists(searchFor2);
    }

}
