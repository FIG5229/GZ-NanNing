package com.thinkgem.jeesite.common.elasticsearch.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RequestOptions.Builder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ElasticsearchAutoConfiguration {
    @Autowired
    private ElasticsearchProperties elasticsearchProperties;
    private List<HttpHost> httpHosts = new ArrayList();
    protected static final RequestOptions COMMON_OPTIONS;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestClientBuilder builder = this.getRestClientBuilder();
        return null/*getRestHighLevelClient(builder, this.elasticsearchProperties)*/;
    }

    private RestClientBuilder getRestClientBuilder() {
        List<String> clusterNodes = this.elasticsearchProperties.getClusterNodes();
        clusterNodes.forEach((node) -> {
            try {
                String[] parts = StringUtils.split(node, ":");
                Assert.notNull(parts, "Must defined");
                Assert.state(parts.length == 2, "Must be defined as 'host:port'");
                this.httpHosts.add(new HttpHost(parts[0], Integer.parseInt(parts[1]), this.elasticsearchProperties.getSchema()));
            } catch (Exception var3) {
                throw new IllegalStateException("Invalid ES nodes property '" + node + "'", var3);
            }
        });
        return null;/*RestClient.builder((HttpHost[])this.httpHosts.toArray(new HttpHost[0]));*/
    }

    private static RestHighLevelClient getRestHighLevelClient(RestClientBuilder builder, ElasticsearchProperties elasticsearchProperties) {/*
        builder.setRequestConfigCallback((requestConfigBuilder) -> {
            requestConfigBuilder.setConnectTimeout(elasticsearchProperties.getConnectTimeout());
            requestConfigBuilder.setSocketTimeout(elasticsearchProperties.getSocketTimeout());
            requestConfigBuilder.setConnectionRequestTimeout(elasticsearchProperties.getConnectionRequestTimeout());
            return requestConfigBuilder;
        });
        builder.setHttpClientConfigCallback((httpClientBuilder) -> {
            httpClientBuilder.setMaxConnTotal(elasticsearchProperties.getMaxConnectTotal());
            httpClientBuilder.setMaxConnPerRoute(elasticsearchProperties.getMaxConnectPerRoute());
            return httpClientBuilder;
        });
        ElasticsearchProperties.Account account = elasticsearchProperties.getAccount();
        if (!StringUtils.isEmpty(account.getUsername()) && !StringUtils.isEmpty(account.getUsername())) {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(account.getUsername(), account.getPassword()));
        }
*/
        return null/*new RestHighLevelClient(builder)*/;
    }

    static {
        Builder builder = RequestOptions.DEFAULT.toBuilder();
        builder.setHttpAsyncResponseConsumerFactory(new HeapBufferedResponseConsumerFactory(31457280));
        COMMON_OPTIONS = builder.build();
    }
}

