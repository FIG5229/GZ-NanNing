package com.thinkgem.jeesite.modules.search.service.impl;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.search.entity.SearchFors;
import com.thinkgem.jeesite.modules.search.enums.SearchForsDao;
import com.thinkgem.jeesite.modules.search.service.SearchForsService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class SearchForsServiceImpl extends CrudService<SearchForsDao, SearchFors> implements SearchForsService {
    @Autowired
    private SearchForsDao searchForsDao;

   /* @Override
    public PagerSearchResult<PersonnelBase> findList(String sex, String nation, String mianmao, int from, int size) {
        return searchForDao.findList(sex, nation, mianmao, from, size);
    }*/
   @Override
    public List<Map<String, Object>> findListinfo(String sex, String nation, String mianmao, String unitId, String rewardName, String approcalOrgType, String cjType, String cjName, String cjOrgType, String year, String conclusion, String xlName, String schoolName){
//        return searchForDao.findList(sex, nation, mianmao, unitId, rewardName, approcalOrgType, cjType, cjName, cjOrgType, year, conclusion, xlName, schoolName);
       SearchFors searchFors = new SearchFors();
       searchFors.setSex(sex);
       searchFors.setNation(nation);
       searchFors.setMianmao(mianmao);
       searchFors.setRewardName(rewardName);
       searchFors.setApprocalOrgType(approcalOrgType);
       searchFors.setCjType(cjType);
       searchFors.setCjName(cjName);
       searchFors.setCjOrgType(cjOrgType);
       searchFors.setYear(year);
       searchFors.setConclusion(conclusion);
       searchFors.setXlName(xlName);
       searchFors.setSchoolName(schoolName);
       searchFors.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
       return searchForsDao.findListInfo(searchFors);
    }


//    @Override
//    public List<Map<String, Object>> findLists(String sex, String[] nation, String[] mianmao, String rewardName, String[] approcalOrgType, String[] cjType, String cjName, String[] cjOrgType, String year, String conclusion, String xlName, String schoolName) {
////        searchFor.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
////        return searchForDao.findLists(sex, nation, mianmao, rewardName, approcalOrgType, cjType, cjName, cjOrgType, year, conclusion, xlName, schoolName);
//        SearchFor searchFor2 = new SearchFor();
//        searchFor2.setSex(sex);
//        searchFor2.setNation(nation);
//        searchFor2.setMianmao(mianmao);
//        searchFor2.setRewardName(rewardName);
//        searchFor2.setApprocalOrgType(approcalOrgType);
//        searchFor2.setCjType(cjType);
//        searchFor2.setCjName(cjName);
//        searchFor2.setCjOrgType(cjOrgType);
//        searchFor2.setYear(year);
//        searchFor2.setConclusion(conclusion);
//        searchFor2.setXlName(xlName);
//        searchFor2.setSchoolName(schoolName);
//        searchFor2.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
//        //searchFor.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
//        return searchForDao.findBaseLists(searchFor2);
//        //return searchForDao.findBaseLists(sex, nation, mianmao, age);
//
//   }

    @Override
    public List<Map<String, Object>> findBaseList(String sex, String nation, String mianmao, String unitId) {
//        searchFor.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
//        return searchForDao.findBaseList(sex, nation, mianmao, unitId);
        SearchFors searchFors = new SearchFors();
        searchFors.setSex(sex);
        searchFors.setNation(nation);
        searchFors.setMianmao(mianmao);
        searchFors.setUnitId(unitId);
        searchFors.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        return searchForsDao.findBaseList(searchFors);
    }


   /* @Override
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
    }*/


}
