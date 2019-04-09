package com.sun.flower.es.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Desc: 配置Rest Client和Rest High Level Client
 * @Author: chenbo
 * @Date: 2019/4/9 15:02
 **/
@Slf4j
@Configuration
public class ElasticSearchRestClient {

    /**
     * 长度 ip port 二个值
     */
    private static final int ADDRESS_LEN = 2;

    /**
     * http
     */
    private static final String HTTP_SCHEME = "http";

    /**
     * es集群地址
     */
    @Value("${elasticsearch.ip}")
    String[] esIpAddress;

    /**
     * RestClientBuilder bean
     * @return
     */
    @Bean
    public RestClientBuilder restClientBuilder() {
        HttpHost[] hosts = Arrays.stream(esIpAddress).map(this::makeHttpHost).filter(Objects::nonNull).toArray(HttpHost[]::new);
        log.info("hosts:{}", Arrays.toString(hosts));
        return RestClient.builder(hosts);
    }

    /**
     * RestHighLevelClient bean
     * @param
     * @return
     */
    @Bean(name = "restHighLevelClient")
    public RestHighLevelClient highLevelClient(@Autowired RestClientBuilder clientBuilder) {
        return new RestHighLevelClient(clientBuilder);
    }

    /**
     * 生成httphost
     * @param s ip:port格式
     * @return
     */
    private HttpHost makeHttpHost(String s) {
        assert StringUtils.isNotEmpty(s);

        String[] address = s.split(":");
        if (address.length == ADDRESS_LEN) {
            log.info("param s format is true, address = {}:{}", address[0], address[1]);
            return new HttpHost(address[0], Integer.parseInt(address[1]), HTTP_SCHEME);
        }

        log.info("param s format is false, return null");
        return null;
    }
}
