/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.persistence.interceptor;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.elasticsearch.EsDataService;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * 数据库分页插件，只拦截查询语句.
 * @author poplar.yfyang / thinkgem
 * @version 2013-8-28
 */
@Intercepts({@Signature(type = Executor.class, method = "update",
        args = {MappedStatement.class, Object.class})})
public class EsUpdateInterceptor extends BaseInterceptor {
    private static final long serialVersionUID = 1L;

    EsDataService esDataService = null;
    List<String> enabledList;

    private EsDataService getEsDataService() {
        if(esDataService == null) {
            esDataService = SpringContextHolder.getBean(EsDataService.class);
        }
        return esDataService;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object proceed = invocation.proceed();

        final MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        String statementIds = mappedStatement.getId();
        String[] statementIdSplited  = statementIds.split("\\.");
        String statementId = statementIdSplited[statementIdSplited.length - 1];

        Object parameter = invocation.getArgs()[1];

        switch (statementId) {
            case "deleteByIds":
                BoundSql boundSql = mappedStatement.getBoundSql(parameter);
                String[] sqlSplited = boundSql.getSql().split("\\s+");
                String deleteTable = sqlSplited[1].toLowerCase();
                if(contains(deleteTable)) {
                    List<Object> ids = (List<Object>)((Map<String, Object>)parameter).get("ids");
                    List<String> idStrs = ids.stream().map(d -> {return d.toString();}).collect(Collectors.toList());
                    getEsDataService().deleteByIds(deleteTable, idStrs.toArray(new String[idStrs.size()-1]));
                }
                break;
            case "delete":
                DataEntity deleteEntity = (DataEntity)parameter;
                String deleteTable2 = StrUtil.toUnderlineCase(deleteEntity.getClass().getSimpleName());
                if(null != deleteEntity && contains(deleteTable2)) {
                    getEsDataService().delete(deleteEntity);
                }
                break;
            case "update":
                DataEntity updateEntity = (DataEntity)parameter;
                String updateTable = StrUtil.toUnderlineCase(updateEntity.getClass().getSimpleName());
                if(null != updateEntity && contains(updateTable)) {
                    getEsDataService().update(updateEntity);
                }
                break;
            case "insert":
                DataEntity insertEntity = (DataEntity)parameter;
                String insertTable = StrUtil.toUnderlineCase(insertEntity.getClass().getSimpleName());
                if( null != insertEntity && contains(insertTable)) {
                    getEsDataService().add(insertEntity);
                }
                break;
            default:
                break;
        }
        return proceed;
    }

    private boolean contains(String table) {
        Optional<String> exists = enabledList.stream().filter(e -> {
            return e.trim().toLowerCase().equals(table);
        }).findAny();
        if(exists.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String enabled = (String)properties.get("enabled");
        if(enabled != null) {
            String[] enabledArr = enabled.split(",");
            enabledList = Lists.newArrayList(enabledArr);
        }
        super.initProperties(properties);
    }

}
