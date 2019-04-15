package com.sun.flower.hbase.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/4/13 17:20
 **/
@Slf4j
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(HBaseProperties.class)
public class HBaseConfiguration {

    private static final String HBASE_ZK_QUORUM = "hbase.zookeeper.quorum";
    private static final String HBASE_ZK_PORT = "hbase.zookeeper.property.clientPort";
    private static final String HBASE_ZNODE_PARENT = "zookeeper.znode.parent";

    @Autowired
    private HBaseProperties hbaseProperties;

    /**
     * hbase模板
     * @return
     */
    @Bean
    public HbaseTemplate hbaseTemplate() {
        log.info("hbaseTemplate config = {}", hbaseProperties);
        Configuration configuration = new Configuration();

        // 初始化配置
        configuration.set(HBASE_ZK_QUORUM, hbaseProperties.getHost());
        configuration.set(HBASE_ZK_PORT, hbaseProperties.getPort());
        configuration.set(HBASE_ZNODE_PARENT, hbaseProperties.getNodeParent());

        return new HbaseTemplate(configuration);
    }

}
