package com.thinkgem.jeesite.modules.search.enums;

/**
 * 事务总体类型
 */
public enum ThingEnum {

    THING_AFFAIR_FILE_NOTICE(1, "党建文件通知通报"),//AffairFileNotice
    THING_AFFAIR_KNOWLEDGE_RECEIVE(2, "党规党章及党建知识"),//AffairKnowledgeReceive
    THING_AFFAIR_GENERAL_SITUATION(3, "党组织概况"),//AffairGeneralSituation
    THING_AFFAIR_ACTIVIST(4, "党组织综合信息"),//AffairActivist
    THING_AFFAIR_TWO_ONE(5, "两学一做"),//AffairTwoOne
    THING_AFFAIR_THREE_ONE(6, "三会一课"),//AffairThreeOne
    THING_AFFAIR_THEME_ACTIVITY(7, "党内主题实践活动"),//AffairThemeActivity
    THING_AFFAIR_LIFE_MEET(8, "民主（组织）生活会"),//AffairLifeMeet
    THING_AFFAIR_RESEARCH_ARTICLE(8, "党支部-专报简报/调研文章"),//AffairResearchArticle
    THING_DEBRIEF_EVALUATION(9, "党组织书记述职评测"),//AffairAssess
    THING_AFFAIR_SUBMIT_FORM(10, "档案管理"),//AffairSubmitForm
    THING_AFFAIR_POLICE_RANK(11, "警衔管理");//AffairPoliceRank


    private int type;

    private String message;

    ThingEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
