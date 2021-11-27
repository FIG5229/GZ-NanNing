package com.thinkgem.jeesite.common.elasticsearch.service.impl;

import com.thinkgem.jeesite.common.elasticsearch.EsDataService;
import com.thinkgem.jeesite.common.elasticsearch.bean.EsThing;
import com.thinkgem.jeesite.common.elasticsearch.service.EsThingService;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.search.enums.ThingEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 事务搜索
 */
@Service
public class EsThingServiceImpl implements EsThingService {

    private static final Logger logger = LoggerFactory.getLogger(EsDataService.class);

    @Autowired
    private EsDataService esDataService;

    private String esIndex = "es_thing";

    @Override
    public void add(Object entity) {
        EsThing esThing = dataAssignment(entity);

        //放行的esThing会返回null
        if (esThing == null) {
            return;
        }

        try {
            esDataService.add(esIndex, esThing.getId(), esThing);
        } catch (Exception e) {
            logger.error("Thing sync ElasticSearch add Error, " +
                    "EsThingType: " + esThing.getThingMessage() +
                    ".  ErrorMessage: " + e.getMessage());
        }
    }

    @Override
    public void update(Object entity) {
        EsThing esThing = dataAssignment(entity);

        if (esThing == null) {
            return;
        }

        try {
            esDataService.update(esIndex, esThing.getThingId(), esThing);
        } catch (Exception e) {
            logger.error("Thing sync ElasticSearch update Error, " +
                    "EsThingType: " + esThing.getThingMessage() +
                    ".  ErrorMessage: " + e.getMessage());
        }
    }

    @Override
    public void delete(Object entity) {
        EsThing esThing = dataAssignment(entity);

        if (esThing == null) {
            return;
        }

        try {
            esDataService.delete(esIndex, esThing.getThingId());
        } catch (Exception e) {
            logger.error("Thing sync ElasticSearch delete Error, " +
                    "EsThingType: " + esThing.getThingMessage() +
                    ".  ErrorMessage: " + e.getMessage());
        }
    }

    @Override
    public void deleteIds(List<String> ids) {
        if (ids.size() == 0) {
            return;
        }

        try {
            for (String id : ids) {
                esDataService.deleteByIds(esIndex, id);
            }
        } catch (Exception e) {
            logger.error("Thing sync ElasticSearch deleteIds Error, " +
                    ".  ErrorMessage: " + e.getMessage());
        }
    }

    /**
     * 过滤entity
     *
     * @param entity
     * @return
     */
    private EsThing dataAssignment(Object entity) {
        EsThing esThing = new EsThing();
        if (entity.getClass().equals(AffairAssess.class)) {
            AffairAssess affairAssess = (AffairAssess) entity;
            esThing.setId(affairAssess.getId());
            esThing.setThingId(affairAssess.getId());
            esThing.setThingType(ThingEnum.THING_DEBRIEF_EVALUATION.getType());
            esThing.setThingMessage(ThingEnum.THING_DEBRIEF_EVALUATION.getMessage());
            esThing.setKeyWord(affairAssess.getShuji());

            return esThing;
        } else if (entity.getClass().equals(AffairFileNotice.class)) {
            AffairFileNotice affairFileNotice = (AffairFileNotice) entity;
            esThing.setId(affairFileNotice.getId());
            esThing.setThingId(affairFileNotice.getId());
            esThing.setThingType(ThingEnum.THING_AFFAIR_FILE_NOTICE.getType());
            esThing.setThingMessage(ThingEnum.THING_AFFAIR_FILE_NOTICE.getMessage());
            esThing.setKeyWord(affairFileNotice.getTitle());
            return esThing;
        } else if (entity.getClass().equals(AffairKnowledgeReceive.class)) {
            AffairKnowledgeReceive affairKnowledgeReceive = (AffairKnowledgeReceive) entity;
            esThing.setId(affairKnowledgeReceive.getId());
            esThing.setThingId(affairKnowledgeReceive.getId());
            esThing.setThingType(ThingEnum.THING_AFFAIR_KNOWLEDGE_RECEIVE.getType());
            esThing.setThingMessage(ThingEnum.THING_AFFAIR_KNOWLEDGE_RECEIVE.getMessage());
            esThing.setKeyWord(affairKnowledgeReceive.getNoticeId());
            return esThing;
        } else if (entity.getClass().equals(AffairGeneralSituation.class)) {
            AffairGeneralSituation affairGeneralSituation = (AffairGeneralSituation) entity;
            esThing.setId(affairGeneralSituation.getId());
            esThing.setThingId(affairGeneralSituation.getId());
            esThing.setThingType(ThingEnum.THING_AFFAIR_GENERAL_SITUATION.getType());
            esThing.setThingMessage(ThingEnum.THING_AFFAIR_GENERAL_SITUATION.getMessage());
            esThing.setKeyWord(affairGeneralSituation.getPartyOrganization());
            return esThing;
        } else if (entity.getClass().equals(AffairActivist.class)) {
            AffairActivist affairActivist = (AffairActivist) entity;
            esThing.setId(affairActivist.getId());
            esThing.setThingId(affairActivist.getId());
            esThing.setThingType(ThingEnum.THING_AFFAIR_ACTIVIST.getType());
            esThing.setThingMessage(ThingEnum.THING_AFFAIR_ACTIVIST.getMessage());
            esThing.setKeyWord(affairActivist.getName());
            return esThing;
        } else if (entity.getClass().equals(AffairTwoOne.class)) {
            AffairTwoOne affairTwoOne = (AffairTwoOne) entity;
            esThing.setId(affairTwoOne.getId());
            esThing.setThingId(affairTwoOne.getId());
            esThing.setThingType(ThingEnum.THING_AFFAIR_TWO_ONE.getType());
            esThing.setThingMessage(ThingEnum.THING_AFFAIR_TWO_ONE.getMessage());
            esThing.setKeyWord(affairTwoOne.getName());
            return esThing;
        } else if (entity.getClass().equals(AffairThreeOne.class)) {
            AffairThreeOne affairThreeOne = (AffairThreeOne) entity;
            esThing.setId(affairThreeOne.getId());
            esThing.setThingId(affairThreeOne.getId());
            esThing.setThingType(ThingEnum.THING_AFFAIR_THREE_ONE.getType());
            esThing.setThingMessage(ThingEnum.THING_AFFAIR_THREE_ONE.getMessage());
            esThing.setKeyWord(affairThreeOne.getName());
            return esThing;
        } else if (entity.getClass().equals(AffairThemeActivity.class)) {
            AffairThemeActivity affairThemeActivity = (AffairThemeActivity) entity;
            esThing.setId(affairThemeActivity.getId());
            esThing.setThingId(affairThemeActivity.getId());
            esThing.setThingType(ThingEnum.THING_AFFAIR_THEME_ACTIVITY.getType());
            esThing.setThingMessage(ThingEnum.THING_AFFAIR_THEME_ACTIVITY.getMessage());
            esThing.setKeyWord(affairThemeActivity.getName());
            return esThing;
        } else if (entity.getClass().equals(AffairLifeMeet.class)) {
            AffairLifeMeet affairLifeMeet = (AffairLifeMeet) entity;
            esThing.setId(affairLifeMeet.getId());
            esThing.setThingId(affairLifeMeet.getId());
            esThing.setThingType(ThingEnum.THING_AFFAIR_LIFE_MEET.getType());
            esThing.setThingMessage(ThingEnum.THING_AFFAIR_LIFE_MEET.getMessage());
            esThing.setKeyWord(affairLifeMeet.getName());
            return esThing;
        } else if (entity.getClass().equals(AffairResearchArticle.class)) {
            AffairResearchArticle affairResearchArticle = (AffairResearchArticle) entity;
            esThing.setId(affairResearchArticle.getId());
            esThing.setThingId(affairResearchArticle.getId());
            esThing.setThingType(ThingEnum.THING_AFFAIR_RESEARCH_ARTICLE.getType());
            esThing.setThingMessage(ThingEnum.THING_AFFAIR_RESEARCH_ARTICLE.getMessage());
            esThing.setKeyWord(affairResearchArticle.getTitle());
            return esThing;
        } else if (entity.getClass().equals(AffairSubmitForm.class)) {
            AffairSubmitForm affairSubmitForm = (AffairSubmitForm) entity;
            esThing.setId(affairSubmitForm.getId());
            esThing.setThingId(affairSubmitForm.getId());
            esThing.setThingType(ThingEnum.THING_AFFAIR_SUBMIT_FORM.getType());
            esThing.setThingMessage(ThingEnum.THING_AFFAIR_SUBMIT_FORM.getMessage());
            esThing.setKeyWord(affairSubmitForm.getName());
            return esThing;
        } else if (entity.getClass().equals(AffairPoliceRank.class)) {
            AffairPoliceRank affairPoliceRank = (AffairPoliceRank) entity;
            esThing.setId(affairPoliceRank.getId());
            esThing.setThingId(affairPoliceRank.getId());
            esThing.setThingType(ThingEnum.THING_AFFAIR_POLICE_RANK.getType());
            esThing.setThingMessage(ThingEnum.THING_AFFAIR_POLICE_RANK.getMessage());
            esThing.setKeyWord(affairPoliceRank.getName());
            return esThing;
        } else {
            //放行
            return null;
        }
    }
}
