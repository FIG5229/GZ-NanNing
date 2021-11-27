package com.thinkgem.jeesite.modules.search.service.impl;

import com.thinkgem.jeesite.common.elasticsearch.EsDataService;
import com.thinkgem.jeesite.common.elasticsearch.bean.EsAttachment;
import com.thinkgem.jeesite.common.elasticsearch.bean.EsThing;
import com.thinkgem.jeesite.common.elasticsearch.bean.PagerSearchResult;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.search.service.SearchService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private EsDataService esDataService;

    @Override
    public PagerSearchResult<PersonnelBase> conditionalSearchPeople(String searchContent, String searchCondition, int from, int size) {
        return esDataService.searchByPersonnelBase(PersonnelBase.class, searchContent, null, from, size, searchCondition);
    }

    @Override
    public PagerSearchResult<Office> conditionalSearchInstitution(String searchContent, String searchCondition, int from, int size) {
        return esDataService.searchByOffice(Office.class, searchContent, null, from, size);
    }

    @Override
    public PagerSearchResult<EsAttachment> conditionalSearchFile(String searchContent, String searchCondition, int from, int size) {
        return esDataService.searchByFile(EsAttachment.class, searchContent, null, from, size);
    }

    @Override
    public PagerSearchResult<EsThing> conditionalSearchThing(String searchContent, String searchCondition, int from, int size) {
        return esDataService.searchByThing(EsThing.class, searchContent, null, from, size);
    }
}
