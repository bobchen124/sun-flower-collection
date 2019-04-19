package com.sun.flower.hbase.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.io.IOException;


/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/4/13 17:20
 **/
@Slf4j
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(HBaseProperties.class)
public class HBaseConfig {

    private static final String HBASE_ZK_QUORUM = "hbase.zookeeper.quorum";
    private static final String HBASE_ZK_PORT = "hbase.zookeeper.property.clientPort";
    private static final String HBASE_ZNODE_PARENT = "zookeeper.znode.parent";
    private static final String HBASE_CLIENT_POOL_SIZE = "hbase.client.ipc.pool.size";

    @Autowired
    private HBaseProperties hbaseProperties;

    /**
     * 客户端配置
     * @return
     */
    @Bean
    public Configuration initConfiguration() {
        Configuration configuration = new Configuration();

        configuration.set(HConstants.ZOOKEEPER_QUORUM, hbaseProperties.getHost());
        configuration.set(HConstants.ZOOKEEPER_CLIENT_PORT, hbaseProperties.getPort());
        configuration.set(HConstants.ZOOKEEPER_ZNODE_PARENT, hbaseProperties.getNodeParent());
        configuration.set(HConstants.HBASE_CLIENT_IPC_POOL_SIZE, "10");

        return configuration;
    }

    /**
     * 获取连接，线程安全
     * @param cof
     * @return
     */
    @Bean
    public Connection getConnection(@Autowired Configuration cof) {
        try {
            return ConnectionFactory.createConnection(cof);
        } catch (IOException ex) {
            log.error("getConnection IOException ", ex);
            throw new RuntimeException(ex);
        }
    }

}
