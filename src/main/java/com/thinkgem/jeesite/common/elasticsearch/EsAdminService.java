package com.thinkgem.jeesite.common.elasticsearch;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.elasticsearch.annotation.EsId;
import com.thinkgem.jeesite.common.elasticsearch.annotation.EsIgnore;
import com.thinkgem.jeesite.common.elasticsearch.bean.EsAttachment;
import com.thinkgem.jeesite.common.persistence.BaseEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.template.delete.DeleteIndexTemplateRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class EsAdminService /*implements InitializingBean*/ {/*

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource(name = "restHighLevelClient")
    protected RestHighLevelClient client;
    private String defaultTemplateName = "default_dynamic_template";
    @Value("${es.elasticsearch.analyzer:ik_max_word}")
    private String analyzer;
    @Value("${es.elasticsearch.search_analyzer:ik_smart}")
    private String searchAnalyzer;
    @Value("${es.elasticsearch.shard_num:3}")
    private int shardNum;
    @Value("${es.elasticsearch.replicas_num:1}")
    private int replicasNum;

    @Autowired
    EsDataService esDataService;

    public void init() throws IOException {
        if(!existsTemplate(defaultTemplateName)) {
            createTemplate(Lists.newArrayList("*"), defaultTemplateName);
        }
        String esAttachMent = StrUtil.toUnderlineCase(EsAttachment.class.getSimpleName());
        if(! existsIndex(esAttachMent)) {
            createIndex(esAttachMent);
        }
    }

    *//**
     * 批量导入数据
     * @param cls 类型
     * @param datas 数据
     * @param awaitSeconds 等待时间
     * @param dropIndex 导入前是否删除index
     * @param <T>
     * @return
     * @throws IOException
     * @throws InterruptedException
     *//*
    public <T> boolean batchImport(Class<T> cls, List<? extends BaseEntity> datas, Long awaitSeconds, boolean dropIndex) throws IOException, InterruptedException {
        return batchImport(StringUtils.toUnderScoreCase(cls.getSimpleName()), datas, awaitSeconds, dropIndex);
    }

    public boolean batchImport(String toIndex, List<?> datas, Long awaitSeconds, boolean dropIndex) throws IOException {
        if(dropIndex) {
            this.deleteIndex(toIndex);
        }
        datas.stream().forEach(d -> {
            try {
                java.lang.reflect.Field[] fields = datas.get(0).getClass().getDeclaredFields();
                if(d instanceof DataEntity) {
                    DataEntity dataEntity = (DataEntity)d;
                    esDataService.add(dataEntity);
//                    IndexRequest indexRequest = new IndexRequest(toIndex).id(dataEntity.getId()).source(beanMap);
//                    if (null == beanMap.get("sex")) {
//                        logger.info("name:" + beanMap.get("name") +",sex:" + beanMap.get("sex"));
//                    }
//                    client.index(indexRequest, RequestOptions.DEFAULT);
                } else {

                    for(java.lang.reflect.Field field : fields) {
                        if(field.getAnnotationsByType(EsId.class).length > 0) {
                            field.setAccessible(true);
                            try {
//                                IndexRequest indexRequest = new IndexRequest(toIndex).id(field.get(d).toString()).source(beanMap);
                                Map<String,Object> beanMap = beanToMap(d);
                                esDataService.add(toIndex, field.get(d).toString(), beanMap);
                            } catch (IllegalAccessException e) {
                                logger.error(e.getMessage(), e);
                            }
                        }
                    }
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
        return true;
    }


    private Map<String, Object> beanToMap(Object doc) {
        if (doc instanceof DataEntity) {
            return beanToMap((DataEntity) doc);
        }
        Map<String, Object> beanMap = BeanUtil.beanToMap(doc);
        java.lang.reflect.Field[] fields = doc.getClass().getDeclaredFields();
        for (java.lang.reflect.Field field : fields) {
            if (field.getAnnotationsByType(EsIgnore.class).length != 0) {
                beanMap.remove(field.getName());
            }
        }
        return beanMap;
    }

    private Map<String, Object> beanToMap(DataEntity entity) {
        Map<String, Object> beanMap = BeanUtil.beanToMap(entity);
        java.lang.reflect.Field[] fields = entity.getClass().getDeclaredFields();
        for (java.lang.reflect.Field field : fields) {
            if (field.getAnnotationsByType(EsIgnore.class).length != 0) {
                beanMap.remove(field.getName());
            }
        }

        if (entity.getCreateBy() != null) {
            beanMap.put("createBy", entity.getCreateBy().getId());
        } else {
            beanMap.remove("createBy");
        }
        if (entity.getCreateBy() != null && entity.getCreateBy().getOffice() != null) {
            beanMap.put("office", entity.getCreateBy().getOffice().getId());
        }
        if (entity.getUpdateBy() != null) {
            beanMap.put("updateBy", entity.getUpdateBy().getId());
        }
        List<String> deleteKeys = new ArrayList<>();
        for(String key : beanMap.keySet()) {
            Object value = beanMap.get(key);
            if (value instanceof BaseEntity || value instanceof Page || value instanceof List) {
                deleteKeys.add(key);
            }
        }
        for(String key : deleteKeys) {
            beanMap.remove(key);
        }
        beanMap.remove("currentUser");
        beanMap.remove("page");
        beanMap.remove("sqlMap");
        beanMap.remove("isNewRecord");
        return beanMap;
    }

    public void export(String fromIndex, String exportType, String exportFile) {

    }

    public void reIndex(String index){

    }

    *//**
     * 创建dynamic模版
     * 全局使用统一的模版进行数据类型映射
     * String->text,使用ik分词器
     * Date->date
     * @param indexPatterns
     * @param templateName
     * @return
     *//*
    public boolean createTemplate(List indexPatterns, String templateName){
        PutIndexTemplateRequest request = new PutIndexTemplateRequest(templateName);
        request.patterns(indexPatterns);
        request.settings(Settings.builder()
                .put("index.number_of_shards", shardNum)
                .put("index.number_of_replicas", replicasNum)
        );
        request.mapping(
                "{\n" +
                        "    \"dynamic_templates\": [\n" +
                        "        { \"createBys\": {\n" +
                        "              \"match\":              \"createBy\",\n" +
                        "              \"match_mapping_type\": \"string\",\n" +
                        "              \"mapping\": {\n" +
                        "                  \"type\":           \"keyword\"" +
                        "              }\n" +
                        "        }},\n" +
                        "        { \"offices\": {\n" +
                        "              \"match\":              \"office\",\n" +
                        "              \"match_mapping_type\": \"string\",\n" +
                        "              \"mapping\": {\n" +
                        "                  \"type\":           \"keyword\"" +
                        "              }\n" +
                        "        }},\n" +
                        "        { \"texts\": {\n" +
                        "              \"match\":              \"*\",\n" +
                        "              \"match_mapping_type\": \"string\",\n" +
                        "              \"mapping\": {\n" +
                        "                  \"type\":           \"text\",\n" +
                        "                  \"analyzer\":       \"" + analyzer + "\",\n" +
                        "                  \"search_analyzer\": \"" + searchAnalyzer + "\"\n" +
                        "              }\n" +
                        "        }},\n" +
                        "        { \"dates\": {\n" +
                        "              \"match\":              \"*\",\n" +
                        "              \"match_mapping_type\": \"date\",\n" +
                        "              \"mapping\": {\n" +
                        "                  \"type\":           \"date\",\n" +
                        "                  \"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||strict_date_optional_time||epoch_millis\"\n" +
                        "              }\n" +
                        "        }}\n" +
                        "    ]\n" +
                        "}",
                XContentType.JSON);
        try {
            AcknowledgedResponse putTemplateResponse = client.indices().putTemplate(request, RequestOptions.DEFAULT);
            return putTemplateResponse.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    *//**
     * 查询模版
     * @param templateName
     * @return
     * @throws IOException
     *//*
    public List<IndexTemplateMetaData> getTemplate(String templateName) throws IOException {
        GetIndexTemplatesRequest request = new GetIndexTemplatesRequest(templateName);
        GetIndexTemplatesResponse getTemplatesResponse = client.indices().getIndexTemplate(request, RequestOptions.DEFAULT);
        return getTemplatesResponse.getIndexTemplates();
    }

    *//**
     * 模版是否存在
     * @param templateName
     * @return
     * @throws IOException
     *//*
    public boolean existsTemplate(String templateName) throws IOException {
        IndexTemplatesExistRequest request = new IndexTemplatesExistRequest(templateName);
        return client.indices().existsTemplate(request, RequestOptions.DEFAULT);
    }

    *//**
     * 删除模版
     * @param templateName
     * @return
     * @throws IOException
     *//*
    public boolean deleteTemplate(String templateName) throws IOException {
        DeleteIndexTemplateRequest request = new DeleteIndexTemplateRequest(templateName);
        AcknowledgedResponse deleteTemplateAcknowledge = client.indices().deleteTemplate(request, RequestOptions.DEFAULT);
        return deleteTemplateAcknowledge.isAcknowledged();
    }

    *//**
     * 索引是否存在
     * @param index
     * @return
     * @throws IOException
     *//*
    private boolean existsIndex(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }

    *//**
     * 创建索引
     * @param index
     * @return
     * @throws IOException
     *//*
    public boolean createIndex(String index) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(index);
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        return createIndexResponse.isAcknowledged();
    }
    *//**
     * 删除索引
     * @param index
     * @return
     * @throws IOException
     *//*
    private boolean deleteIndex(String index) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        AcknowledgedResponse deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);
        return deleteIndexResponse.isAcknowledged();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }*/
}
