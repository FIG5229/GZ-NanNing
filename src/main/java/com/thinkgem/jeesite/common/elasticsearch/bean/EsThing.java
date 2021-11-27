package com.thinkgem.jeesite.common.elasticsearch.bean;

import com.thinkgem.jeesite.common.elasticsearch.annotation.EsId;
import com.thinkgem.jeesite.common.elasticsearch.annotation.HighLight;

/**
 * 事务搜索
 */
public class EsThing {

    @EsId
    private String id;

    /**
     * 事务类型id,对于事务类型表的id
     */
    private String thingId;

    /**
     * 事务类型，ThingEnum 类有详细说明
     */
    private int thingType;

    private String thingMessage;

    /**
     * 事务要搜索的关键词
     */
    @HighLight
    private String keyWord;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThingId() {
        return thingId;
    }

    public void setThingId(String thingId) {
        this.thingId = thingId;
    }

    public int getThingType() {
        return thingType;
    }

    public void setThingType(int thingType) {
        this.thingType = thingType;
    }

    public String getThingMessage() {
        return thingMessage;
    }

    public void setThingMessage(String thingMessage) {
        this.thingMessage = thingMessage;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
