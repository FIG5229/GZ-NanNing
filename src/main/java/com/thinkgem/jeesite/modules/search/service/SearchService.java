package com.thinkgem.jeesite.modules.search.service;

import com.thinkgem.jeesite.common.elasticsearch.bean.EsAttachment;
import com.thinkgem.jeesite.common.elasticsearch.bean.EsThing;
import com.thinkgem.jeesite.common.elasticsearch.bean.PagerSearchResult;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.sys.entity.Office;

public interface SearchService {

    PagerSearchResult<PersonnelBase> conditionalSearchPeople(String searchContent, String searchCondition, int from, int size);

    PagerSearchResult<Office> conditionalSearchInstitution(String searchContent, String searchCondition, int from, int size);

    PagerSearchResult<EsAttachment> conditionalSearchFile(String searchContent, String searchCondition, int from, int size);

    PagerSearchResult<EsThing> conditionalSearchThing(String searchContent, String searchCondition, int from, int size);
}
