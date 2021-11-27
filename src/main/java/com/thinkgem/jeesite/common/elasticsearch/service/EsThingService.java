package com.thinkgem.jeesite.common.elasticsearch.service;

import java.util.List;

/**
 * 事务搜索service
 */
public interface EsThingService {

    void add(Object entity);

    void update(Object entity);

    void delete(Object entity);

    void deleteIds(List<String> ids);
}
