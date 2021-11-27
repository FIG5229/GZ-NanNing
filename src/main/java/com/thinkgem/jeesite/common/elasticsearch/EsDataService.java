package com.thinkgem.jeesite.common.elasticsearch;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.elasticsearch.annotation.EsAttach;
import com.thinkgem.jeesite.common.elasticsearch.annotation.EsIgnore;
import com.thinkgem.jeesite.common.elasticsearch.annotation.HighLight;
import com.thinkgem.jeesite.common.elasticsearch.bean.AuthorityParam;
import com.thinkgem.jeesite.common.elasticsearch.bean.EsAttachment;
import com.thinkgem.jeesite.common.elasticsearch.bean.PagerSearchResult;
import com.thinkgem.jeesite.common.persistence.BaseEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.FileDocUtil;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.Reflections;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.exception.TikaException;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RequestOptions.Builder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder.Field;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class EsDataService {
    private static final Logger logger = LoggerFactory.getLogger(EsDataService.class);
    @Resource(name = "restHighLevelClient")
    protected RestHighLevelClient client;
    private static final RequestOptions COMMON_OPTIONS;
    @Value("${es.elasticsearch.pre_tag:<strong>}")
    private String preTag;
    @Value("${es.elasticsearch.post_tag:</strong>}")
    private String postTag;
    @Value("${es.elasticsearch.max_text_length:200}")
    private int maxTextLength;
    @Value("${es.elasticsearch.fragment_size:20}")
    private int fragmentSize;

    public EsDataService() {
    }

    /**
     * 保存数据
     *
     * @param index 索引(表)名称
     * @param id    对象唯一标识，建议与主键一致
     * @param doc   待索引对象
     * @return
     * @throws IOException
     */
    public void add(String index, String id, Object doc) throws InterruptedException {
        Map<String, Object> beanMap = beanToMap(doc);
        IndexRequest indexRequest = new IndexRequest(index).id(id).source(beanMap);
        try {
            Map<String, List<EsAttachment>> attachementMap = extractAttachment(doc);
            List<DocWriteRequest> requestList = attachementMap.keySet().stream().map(s -> {
                List<EsAttachment> attachmentList = attachementMap.get(s);
                return attachmentList.stream().map(a -> {
                    IndexRequest addRequest = new IndexRequest(s);
                    addRequest.id(a.getId()).source(beanToMap(a));
                    logger.info("Add Attach: index={},id={}", s, a.getId());
                    return addRequest;
                }).collect(Collectors.toList());
            }).collect(() -> new ArrayList<>(),
                    (list, requests) -> list.addAll(requests),
                    (list1, list2) -> list1.addAll(list2));
//            IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
            logger.info("Add Document: index=" + index + ", id=" + id + ",doc=" + beanMap);
            requestList.add(indexRequest);
            bulkRequest(requestList, 30l);
        } catch (ElasticsearchException e) {
            logger.error("Add Document Fail: index=" + index + ", id=" + id + ",doc=" + beanMap);
        }
    }

    /**
     * 保存数据
     *
     * @param entity 待索引对象
     * @return
     * @throws IOException
     */
    public void add(DataEntity entity) throws InterruptedException {
        String index = StrUtil.toUnderlineCase(entity.getClass().getSimpleName());
        add(index, entity.getId(), entity);
    }

    /**
     * 更新数据
     *
     * @param index 索引(表)名称
     * @param id    对象唯一标识，建议与主键一致
     * @param doc   待索引对象
     * @return
     * @throws IOException
     */
    public boolean update(String index, String id, Object doc) throws InterruptedException {
        Map<String, Object> beanMap = beanToMap(doc);
        UpdateRequest updateRequest = new UpdateRequest(index, id).doc(beanMap);
        try {
            logger.info("Update Document: index=" + index + ", id=" + id + ",entity=" + beanMap);
            Map<String, List<EsAttachment>> attachementMap = extractAttachment(doc);

            attachementMap.keySet().stream().forEach(s -> {
                DeleteByQueryRequest attachRequest = new DeleteByQueryRequest(s);
                attachRequest.setQuery(QueryBuilders.termsQuery("srcDocId", id));
                try {
                    client.deleteByQuery(attachRequest, RequestOptions.DEFAULT);
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            });

            List<DocWriteRequest> requestList = attachementMap.keySet().stream().map(s -> {
                List<EsAttachment> attachmentList = attachementMap.get(s);
                return attachmentList.stream().map(a -> {
                    IndexRequest addRequest = new IndexRequest(s);
                    addRequest.id(a.getId()).source(beanToMap(a));
                    logger.info("Add Attach: index={},id={}", s, a.getId());
                    return addRequest;
                }).collect(Collectors.toList());
            }).collect(() -> new ArrayList<>(),
                    (list, requests) -> list.addAll(requests),
                    (list1, list2) -> list1.addAll(list2));
            requestList.add(updateRequest);
            return bulkRequest(requestList, 10l);
        } catch (ElasticsearchException e) {
            logger.error("Update Document Fail: index=" + index + ", id=" + id + ",entity=" + beanMap);
            throw e;
        }
    }

    /**
     * 更新数据
     *
     * @param entity 待索引对象
     * @return
     * @throws IOException
     */
    public boolean update(DataEntity entity) throws IOException, InterruptedException {
        String index = StrUtil.toUnderlineCase(entity.getClass().getSimpleName());
        return update(index, entity.getId(), entity);
    }

    /**
     * 删除数据
     *
     * @param index 索引(表)名称
     * @param id    对象唯一标识，建议与主键一致
     * @return
     * @throws IOException
     */
    public boolean delete(String index, String id) throws IOException {
        DeleteRequest request = new DeleteRequest(index, id);
        request.timeout(TimeValue.timeValueSeconds(3));
        try {
            DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
            logger.info("Delete Document: index=" + index + ", id=" + id + ",result=" + response.getResult());
            DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest("*");
            deleteByQueryRequest.setQuery(QueryBuilders.termsQuery("srcDocId", id));
            try {
                BulkByScrollResponse attachDeleteResponse = client.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
                if (attachDeleteResponse.getTotal() > 0) {
                    logger.info("DeleteAttachByIds: index=*, ids=" + id + ", totalDelete=" + attachDeleteResponse.getTotal());
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            if (response.getResult() == DocWriteResponse.Result.DELETED) {
                return true;
            }
        } catch (Exception e) {
            logger.error("Delete Document Fail: index=" + index + ", id=" + id);
            throw e;
        }
        return false;
    }

    /**
     * 删除数据
     *
     * @param dataEntity 待删除对象
     * @return
     * @throws IOException
     */
    public boolean delete(DataEntity dataEntity) throws IOException {
        String index = StrUtil.toUnderlineCase(dataEntity.getClass().getSimpleName());
        DeleteRequest request = new DeleteRequest(index, dataEntity.getId());
        request.timeout(TimeValue.timeValueSeconds(3));
        try {
            DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
            logger.info("Delete Document: index=" + index + ", id=" + dataEntity.getId() + ",result=" + response.getResult());
            Map<String, List<EsAttachment>> attachementMap = extractAttachment(dataEntity);
            attachementMap.keySet().stream().forEach(s -> {
                DeleteByQueryRequest attachRequest = new DeleteByQueryRequest(s);
                attachRequest.setQuery(QueryBuilders.termsQuery("srcDocId", dataEntity.getId()));
                try {
                    client.deleteByQuery(attachRequest, RequestOptions.DEFAULT);
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            });

            if (response.getResult() == DocWriteResponse.Result.DELETED) {
                return true;
            }
        } catch (Exception e) {
            logger.error("Delete Document Fail: index=" + index + ", id=" + dataEntity.getId());
            throw e;
        }
        return false;
    }

    /**
     * 批量删除数据
     *
     * @param index 索引(表)名称
     * @param ids
     * @return
     * @throws IOException
     */
    public long deleteByIds(String index, String... ids) throws IOException {
        DeleteByQueryRequest deleteRequest = new DeleteByQueryRequest(index);
        deleteRequest.setQuery(QueryBuilders.idsQuery().addIds(ids));
        try {
            BulkByScrollResponse response = client.deleteByQuery(deleteRequest, RequestOptions.DEFAULT);
            logger.info("DeleteByIds: index=" + index + ", ids=" + Arrays.asList(ids) + ", totalDelete=" + response.getTotal());
            DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest("*");
            deleteByQueryRequest.setQuery(QueryBuilders.termsQuery("srcDocId", ids));
            try {
                BulkByScrollResponse attachDeleteResponse = client.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
                if (attachDeleteResponse.getTotal() > 0) {
                    logger.info("DeleteAttachByIds: index=*, ids=" + Arrays.asList(ids) + ", totalDelete=" + attachDeleteResponse.getTotal());
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            return response.getTotal();
        } catch (IOException e) {
            logger.error("DeleteByIds Fail: index=" + index + ", ids=" + Arrays.asList(ids));
            throw e;
        }
    }

    private boolean bulkRequest(List<DocWriteRequest> requests, Long awaitSeconds) throws InterruptedException {
        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                int numberOfActions = request.numberOfActions();
                if (logger.isDebugEnabled()) {
                    logger.debug("Executing bulk [{}] with {} requests",
                            executionId, numberOfActions);
                }
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request,
                                  BulkResponse response) {
                if (response.hasFailures()) {
                    logger.warn("Bulk [{}] executed with failures {}", executionId, response.buildFailureMessage());
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Bulk [{}] completed in {} milliseconds",
                                executionId, response.getTook().getMillis());
                    }
                }
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request,
                                  Throwable failure) {
                logger.error("Failed to execute bulk", failure);
            }
        };
        BulkProcessor.Builder builder = BulkProcessor.builder(
                (request, bulkListener) ->
                        client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener),
                listener);
        builder.setBulkActions(500);
        builder.setBulkSize(new ByteSizeValue(1L, ByteSizeUnit.MB));
        builder.setConcurrentRequests(0);
        builder.setFlushInterval(TimeValue.timeValueSeconds(3L));
        builder.setBackoffPolicy(BackoffPolicy
                .constantBackoff(TimeValue.timeValueSeconds(1L), 3));

        BulkProcessor bulkProcessor = builder.build();
        try {
            requests.stream().forEach(r -> {
                bulkProcessor.add(r);
            });
//            bulkProcessor.flush();
            return bulkProcessor.awaitClose(awaitSeconds, TimeUnit.SECONDS);
        } finally {
            bulkProcessor.close();
        }
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
//        beanMap.remove("currentUser");
        beanMap.remove("page");
        beanMap.remove("sqlMap");
        beanMap.remove("isNewRecord");
        return beanMap;
    }

    public Map<String, List<EsAttachment>> extractAttachment(Object doc) {
        Map<String, List<EsAttachment>> result = Maps.newHashMap();
        java.lang.reflect.Field[] fields = doc.getClass().getDeclaredFields();
        Arrays.asList(fields).stream().forEach(field -> {
            if (field.getAnnotationsByType(EsAttach.class).length != 0) {
                String targetIndex = field.getAnnotation(EsAttach.class).index();
                List<EsAttachment> attachmentList = Lists.newArrayList();

                String srcIndex = StrUtil.toUnderlineCase(doc.getClass().getSimpleName());
                String srcFieldName = field.getName();
                String filePaths = (String) Reflections.getFieldValue(doc, srcFieldName);
                if (StringUtils.isNotEmpty(filePaths)) {
                    String srcId;
                    String createBy = null;
                    String office = null;

                    if (doc instanceof DataEntity) {
                        DataEntity entity = (DataEntity) doc;
                        srcId = entity.getId();

                        if (entity.getCreateBy() != null) {
                            createBy = entity.getCreateBy().getId();
                            if (entity.getCreateBy().getOffice() != null) {
                                office = entity.getCreateBy().getOffice().getId();
                            }
                        }
                    } else {
                        String srcIdField = field.getAnnotation(EsAttach.class).srcIdField();
                        srcId = (String) Reflections.getFieldValue(doc, srcIdField);
                        if (Reflections.containsField(doc, "createBy")) {
                            createBy = (String) Reflections.getFieldValue(doc, "createBy");
                        }
                        if (Reflections.containsField(doc, "office")) {
                            office = (String) Reflections.getFieldValue(doc, "office");
                        }
                    }


                    String[] paths = filePaths.split("\\|");
                    for (String path : paths) {
                        EsAttachment esAttachment = new EsAttachment();
                        esAttachment.setId(IdGen.uuid());

                        esAttachment.setSrcDocId(srcId);
                        esAttachment.setSrcFieldName(srcFieldName);
                        esAttachment.setSrcIndex(srcIndex);

                        String baseDir = Global.getUserfilesBaseDir();
                        String relativeDir = Global.USERFILES_BASE_URL + path;
                        String _path = baseDir + relativeDir;
                        esAttachment.setFileName(FileDocUtil.getFileName(_path));
                        esAttachment.setFilePath(path);
                        try {
                            esAttachment.setFileType(FileDocUtil.detect(_path));
                            esAttachment.setFileText(FileDocUtil.parseText(_path));
                        } catch (IOException e) {
                            logger.error(e.getMessage(), e);
                        } catch (TikaException e) {
                            logger.error(e.getMessage(), e);
                        }

                        esAttachment.setCreateBy(createBy);
                        esAttachment.setCreateDate(new Date());
                        esAttachment.setOffice(office);
                        attachmentList.add(esAttachment);
                    }
                }
                if (!result.containsKey(targetIndex)) {
                    result.put(targetIndex, attachmentList);
                } else {
                    result.get(targetIndex).addAll(attachmentList);
                }
            }
        });
        return result;
    }

    /**
     * 自定义搜索
     *
     * @param searchRequest
     * @param cls                 返回值类型
     * @param ishighlight         是否高亮显示
     * @param highLightFieldNames 高亮字段，若为空，则高亮显示@HightLight注解字段，若无@HightLight字段，则为所有匹配字段
     * @param <T>
     * @return
     */
    private <T> PagerSearchResult<T> search(SearchRequest searchRequest, Class<T> cls, boolean ishighlight, String... highLightFieldNames) {
        PagerSearchResult<T> pagerSearchResult = new PagerSearchResult<>();
        try {
            if (ishighlight) {
                HighlightBuilder highlightBuilder = new HighlightBuilder();
                highlightBuilder.preTags(new String[]{preTag})
                        .postTags(new String[]{postTag})
                        .fragmentSize(fragmentSize)
                        .numOfFragments(maxTextLength / fragmentSize)
                        .requireFieldMatch(false)
                        .order(HighlightBuilder.Order.SCORE)
                        .noMatchSize(maxTextLength)
                        .fragmenter("span");
                if (highLightFieldNames != null && highLightFieldNames.length > 0) {
                    for (String highLightFieldName : highLightFieldNames) {
                        Field highlightTitle = new Field(highLightFieldName);
                        highlightTitle.highlighterType("unified");
                        highlightBuilder.field(highlightTitle);
                    }
                } else {
                    java.lang.reflect.Field[] declaredFields = cls.getDeclaredFields();
                    List<String> highLighted = Lists.newArrayList();
                    for (java.lang.reflect.Field field : declaredFields) {
                        if (field.getAnnotationsByType(HighLight.class).length > 0) {
                            String highLightFieldName = field.getName();
                            highLighted.add(highLightFieldName);
                            Field highlightTitle = new Field(highLightFieldName);
                            highlightTitle.highlighterType("unified");
                            highlightBuilder.field(highlightTitle);
                        }
                    }
                    if (highLighted.size() == 0) {
                        highlightBuilder.field("*");
                    }
                }
                highlightBuilder.highlightQuery(searchRequest.source().query());
                searchRequest.source().highlighter(highlightBuilder);
            }
            SearchResponse searchResponse = this.client.search(searchRequest, COMMON_OPTIONS);
            SearchHit[] hits = searchResponse.getHits().getHits();
            List<T> ts = new ArrayList();
            Arrays.stream(hits).forEach((hit) -> {
                Map<String, Object> sourceAsMap = null;
                if (!ishighlight) {
                    sourceAsMap = hit.getSourceAsMap();
                } else {
                    Map<String, HighlightField> highlightFieldMap = hit.getHighlightFields();
                    sourceAsMap = hit.getSourceAsMap();
                    Set<String> keys = highlightFieldMap.keySet();
                    Iterator var7 = keys.iterator();

                    while (var7.hasNext()) {
                        String key = (String) var7.next();
                        Text[] texts = (highlightFieldMap.get(key)).getFragments();
                        StringBuffer highLightText = new StringBuffer(200);
                        for (int i = 0; i < texts.length; i++) {
                            Text text = texts[i];
                            if (i > 0 && preCompute(highLightText, text.string()) > maxTextLength) {
                                break;
                            }
                            highLightText.append(text.string());
                            if (i < texts.length - 1) {
                                highLightText.append("...");
                            }
                        }
                        sourceAsMap.put(key, highLightText.toString());
                    }
                }

                if (!cls.equals(HashMap.class) && !cls.equals(Map.class)) {
                    java.lang.reflect.Field[] fields = cls.getDeclaredFields();
                    List<String> ignoreFileds = new ArrayList<>(10);
                    if (fields != null) {
                        for (java.lang.reflect.Field field : fields) {
                            if (field.getAnnotationsByType(EsIgnore.class).length > 0) {
                                ignoreFileds.add(field.getName());
                            }
                        }
                    }
                    CopyOptions copyOptions = new CopyOptions();
                    copyOptions.setIgnoreError(true);
                    copyOptions.setIgnoreProperties(ignoreFileds.toArray(new String[ignoreFileds.size()]));
                    T t = BeanUtil.mapToBean(sourceAsMap, cls, copyOptions);
                    ts.add(t);
                } else {
                    ts.add((T) sourceAsMap);
                }

            });

            pagerSearchResult.setSuccess(true);
            pagerSearchResult.setFrom(searchRequest.source().from());
            pagerSearchResult.setSize(searchRequest.source().size());
            pagerSearchResult.setTotal(searchResponse.getHits().getTotalHits().value);
            pagerSearchResult.setDatas(ts);
        } catch (IOException var7) {
            logger.error(var7.getMessage(), var7);
            pagerSearchResult.setSuccess(false);
            pagerSearchResult.setMessage(var7.getMessage());
        }
        return pagerSearchResult;
    }

    /**
     * 关键字搜索,返回T类型对象
     *
     * @param cls
     * @param queryString         搜索关键字
     * @param from                分页页码
     * @param size                分页参数，每页记录数
     * @param ishighlight         是否高亮显示
     * @param highLightFieldNames 高亮字段，若为空，则高亮显示@HightLight注解字段，若无@HightLight字段，则为所有匹配字段
     * @param <T>
     * @return
     */
    public <T> PagerSearchResult<T> search(Class<T> cls, String queryString, int from, int size, boolean ishighlight, String... highLightFieldNames) {
        return this.searchWithFilter(cls, queryString, null, null, from, size, ishighlight, highLightFieldNames);
    }

    /**
     * 关键字搜索
     *
     * @param cls
     * @param queryString         搜索关键字
     * @param authorityParam      数据权限条件，按创建用户createBy、机构代码office验证过滤
     * @param from                分页页码
     * @param size                分页参数，每页记录数
     * @param ishighlight         是否高亮显示
     * @param highLightFieldNames 高亮字段，若为空，则高亮显示@HightLight注解字段，若无@HightLight字段，则为所有匹配字段
     * @param <T>
     * @return
     */
    public <T> PagerSearchResult<T> search(Class<T> cls, String queryString, AuthorityParam authorityParam, int from, int size, boolean ishighlight, String... highLightFieldNames) {
        return this.searchWithFilter(cls, queryString, null, authorityParam, from, size, ishighlight, highLightFieldNames);
    }

    public <T> PagerSearchResult<T> searchByPersonnelBase(Class<T> cls, String queryString, AuthorityParam authorityParam, int from, int size, String searchCondition) {
        return this.searchWithFilterByPersonnelBase(cls, queryString, null, authorityParam, from, size, searchCondition);
    }

    public <T> PagerSearchResult<T> searchByOffice(Class<T> cls, String queryString, AuthorityParam authorityParam, int from, int size) {
        return this.searchWithFilterByOffice(cls, queryString, null, authorityParam, from, size);
    }

    public <T> PagerSearchResult<T> searchByFile(Class<T> cls, String queryString, AuthorityParam authorityParam, int from, int size) {
        return this.searchWithFilterByFile(cls, queryString, null, authorityParam, from, size);
    }

    public <T> PagerSearchResult<T> searchByThing(Class<T> cls, String queryString, AuthorityParam authorityParam, int from, int size) {
        return this.searchWithFilterByThing(cls, queryString, null, authorityParam, from, size);
    }

    /**
     * 带过滤条件的高级搜索
     *
     * @param cls
     * @param queryString
     * @param filterParams        过滤条件，filterParams中放入{name:'xxx'}，表示搜索结果必须满足name='xxx'
     * @param authorityParam      数据权限条件，按创建用户createBy、机构代码office验证过滤
     * @param from                分页页码
     * @param size                分页参数，每页记录数
     * @param ishighlight         是否高亮显示
     * @param highLightFieldNames 高亮字段，若为空，则高亮显示@HightLight注解字段，若无@HightLight字段，则为所有匹配字段
     * @param <T>
     * @return
     */
    public <T> PagerSearchResult<T> searchWithFilter(Class<T> cls, String queryString, Map<String, Object> filterParams, AuthorityParam authorityParam, int from, int size, boolean ishighlight, String... highLightFieldNames) {
        QueryBuilder queryBuilder = this.convertQueryBuilderFromFilter(queryString, filterParams, authorityParam);
        return this.search(StrUtil.toUnderlineCase(cls.getSimpleName()), queryBuilder, cls, from, size, ishighlight, highLightFieldNames);
    }

    public <T> PagerSearchResult<T> searchWithFilterByPersonnelBase(Class<T> cls, String queryString, Map<String, Object> filterParams, AuthorityParam authorityParam, int from, int size, String searchCondition, String... highLightFieldNames) {
        QueryBuilder queryBuilder = this.convertQueryBuilderFromFilterByPersonnelBase(queryString, filterParams, authorityParam, searchCondition);
        return this.search(StrUtil.toUnderlineCase(cls.getSimpleName()), queryBuilder, cls, from, size, false, highLightFieldNames);
    }

    public <T> PagerSearchResult<T> searchWithFilterByOffice(Class<T> cls, String queryString, Map<String, Object> filterParams, AuthorityParam authorityParam, int from, int size, String... highLightFieldNames) {
        QueryBuilder queryBuilder = this.convertQueryBuilderFromFilterByOffice(queryString, filterParams, authorityParam);
        return this.search(StrUtil.toUnderlineCase(cls.getSimpleName()), queryBuilder, cls, from, size, false, highLightFieldNames);
    }

    public <T> PagerSearchResult<T> searchWithFilterByFile(Class<T> cls, String queryString, Map<String, Object> filterParams, AuthorityParam authorityParam, int from, int size, String... highLightFieldNames) {
        QueryBuilder queryBuilder = this.convertQueryBuilderFromFilterByFile(queryString, filterParams, authorityParam);
        return this.search(StrUtil.toUnderlineCase(cls.getSimpleName()), queryBuilder, cls, from, size, false, highLightFieldNames);
    }

    public <T> PagerSearchResult<T> searchWithFilterByThing(Class<T> cls, String queryString, Map<String, Object> filterParams, AuthorityParam authorityParam, int from, int size, String... highLightFieldNames) {
        QueryBuilder queryBuilder = this.convertQueryBuilderFromFilterByThing(queryString, filterParams, authorityParam);
        return this.search(StrUtil.toUnderlineCase(cls.getSimpleName()), queryBuilder, cls, from, size, false, highLightFieldNames);
    }


    public <T> PagerSearchResult<T> search(String index, QueryBuilder queryBuilder, Class<T> cls, int from, int size, boolean ishighlight, String... highLightFieldNames) {
        SearchRequest searchRequest = this.createSearchRequest(index, queryBuilder, from, size);
        return this.search(searchRequest, cls, ishighlight, highLightFieldNames);
    }

    public <T> PagerSearchResult<T> search(String index, String queryString, Class<T> cls, int from, int size, boolean ishighlight, String... highLightFieldNames) {
        return this.search(index, QueryBuilders.queryStringQuery(queryString), cls, from, size, ishighlight, highLightFieldNames);
    }

    public <T> PagerSearchResult<T> searchWithFilter(String index, Class<T> cls, String queryString, Map<String, Object> filterParams, int from, int size, boolean ishighlight, String... highLightFieldNames) {
        QueryBuilder queryBuilder = this.convertQueryBuilderFromFilter(queryString, filterParams, null);
        return this.search(index, queryBuilder, cls, from, size, ishighlight, highLightFieldNames);
    }

    private QueryBuilder convertQueryBuilderFromFilter(String queryString, Map<String, Object> filterParams, AuthorityParam authorityParam) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.queryStringQuery(queryString));
        if (filterParams != null && filterParams.size() != 0) {
            Set<String> filterNames = filterParams.keySet();
            for (String fieldName : filterNames) {
                Object fieldValue = filterParams.get(fieldName);
                boolQueryBuilder.must().add(QueryBuilders.matchQuery(fieldName, fieldValue));
            }
        }
        if (null != authorityParam) {
            if (authorityParam.getCreateBy() != null) {
                boolQueryBuilder.must(QueryBuilders.termsQuery("createBy", authorityParam.getCreateBy()));
            }
            if (authorityParam.getOfficeIds() != null) {
                boolQueryBuilder.must(QueryBuilders.termsQuery("office", authorityParam.getOfficeIds()));
            }
        }
        return boolQueryBuilder;
    }

    private QueryBuilder convertQueryBuilderFromFilterByPersonnelBase(String queryString, Map<String, Object> filterParams, AuthorityParam authorityParam, String searchCondition) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        //搜索条件，0 - 所有；1 - 姓名；2 - 身份证号；3 - 警号；
        //TODO 此处可改进为enum判断
        if (searchCondition.equals("0")) {
            boolQueryBuilder.should(QueryBuilders.wildcardQuery("name", "*" + queryString + "*"))
                    .should(QueryBuilders.wildcardQuery("policeIdNumber", "*" + queryString + "*"))
                    .should(QueryBuilders.wildcardQuery("idNumber", "*" + queryString + "*"));
        } else if (searchCondition.equals("1")) {
            boolQueryBuilder.should(QueryBuilders.wildcardQuery("name", "*" + queryString + "*"));
        } else if (searchCondition.equals("2")) {
            boolQueryBuilder.should(QueryBuilders.wildcardQuery("idNumber", "*" + queryString + "*"));
        } else if (searchCondition.equals("3")) {
            boolQueryBuilder.should(QueryBuilders.wildcardQuery("policeIdNumber", "*" + queryString + "*"));
        } else {
            return null;
        }

        if (filterParams != null && filterParams.size() != 0) {
            Set<String> filterNames = filterParams.keySet();
            for (String fieldName : filterNames) {
                Object fieldValue = filterParams.get(fieldName);
                boolQueryBuilder.must().add(QueryBuilders.matchQuery(fieldName, fieldValue));
            }
        }
        if (null != authorityParam) {
            if (authorityParam.getCreateBy() != null) {
                boolQueryBuilder.must(QueryBuilders.termsQuery("createBy", authorityParam.getCreateBy()));
            }
            if (authorityParam.getOfficeIds() != null) {
                boolQueryBuilder.must(QueryBuilders.termsQuery("office", authorityParam.getOfficeIds()));
            }
        }
        return boolQueryBuilder;
    }

    private QueryBuilder convertQueryBuilderFromFilterByOffice(String queryString, Map<String, Object> filterParams, AuthorityParam authorityParam) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        //搜索条件
        boolQueryBuilder.should(QueryBuilders.wildcardQuery("fullName", "*" + queryString + "*"));

        if (filterParams != null && filterParams.size() != 0) {
            Set<String> filterNames = filterParams.keySet();
            for (String fieldName : filterNames) {
                Object fieldValue = filterParams.get(fieldName);
                boolQueryBuilder.must().add(QueryBuilders.matchQuery(fieldName, fieldValue));
            }
        }
        if (null != authorityParam) {
            if (authorityParam.getCreateBy() != null) {
                boolQueryBuilder.must(QueryBuilders.termsQuery("createBy", authorityParam.getCreateBy()));
            }
            if (authorityParam.getOfficeIds() != null) {
                boolQueryBuilder.must(QueryBuilders.termsQuery("office", authorityParam.getOfficeIds()));
            }
        }
        return boolQueryBuilder;
    }

    private QueryBuilder convertQueryBuilderFromFilterByFile(String queryString, Map<String, Object> filterParams, AuthorityParam authorityParam) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.should(QueryBuilders.wildcardQuery("fileName", "*" + queryString + "*"))
                .should(QueryBuilders.wildcardQuery("fileText", "*" + queryString + "*"));

        if (filterParams != null && filterParams.size() != 0) {
            Set<String> filterNames = filterParams.keySet();
            for (String fieldName : filterNames) {
                Object fieldValue = filterParams.get(fieldName);
                boolQueryBuilder.must().add(QueryBuilders.matchQuery(fieldName, fieldValue));
            }
        }
        if (null != authorityParam) {
            if (authorityParam.getCreateBy() != null) {
                boolQueryBuilder.must(QueryBuilders.termsQuery("createBy", authorityParam.getCreateBy()));
            }
            if (authorityParam.getOfficeIds() != null) {
                boolQueryBuilder.must(QueryBuilders.termsQuery("office", authorityParam.getOfficeIds()));
            }
        }
        return boolQueryBuilder;
    }

    private QueryBuilder convertQueryBuilderFromFilterByThing(String queryString, Map<String, Object> filterParams, AuthorityParam authorityParam) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        //搜索条件
        boolQueryBuilder.should(QueryBuilders.wildcardQuery("keyWord", "*" + queryString + "*"));

        if (filterParams != null && filterParams.size() != 0) {
            Set<String> filterNames = filterParams.keySet();
            for (String fieldName : filterNames) {
                Object fieldValue = filterParams.get(fieldName);
                boolQueryBuilder.must().add(QueryBuilders.matchQuery(fieldName, fieldValue));
            }
        }
        if (null != authorityParam) {
            if (authorityParam.getCreateBy() != null) {
                boolQueryBuilder.must(QueryBuilders.termsQuery("createBy", authorityParam.getCreateBy()));
            }
            if (authorityParam.getOfficeIds() != null) {
                boolQueryBuilder.must(QueryBuilders.termsQuery("office", authorityParam.getOfficeIds()));
            }
        }
        return boolQueryBuilder;
    }

    private SearchRequest createSearchRequest(
            String index, QueryBuilder queryBuilder,
            int from, int size) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest(new String[]{index});
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);
        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }

    private int preCompute(StringBuffer currentHighlightSource, String toAttach) {
        String preStr = currentHighlightSource.toString() + toAttach;
        preStr.replaceAll(preTag, "");
        preStr.replaceAll(postTag, "");
        return preStr.length();
    }

    @PreDestroy
    public void close() {
        try {
            if (this.client != null) {
                this.client.close();
            }
        } catch (IOException var2) {
            logger.error(var2.getMessage(), var2);
        }

    }

    static {
        Builder builder = RequestOptions.DEFAULT.toBuilder();
        builder.setHttpAsyncResponseConsumerFactory(new HeapBufferedResponseConsumerFactory(31457280));
        COMMON_OPTIONS = builder.build();
    }
}