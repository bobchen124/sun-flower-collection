package com.sun.flower.hbase.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/4/13 17:42
 **/
@Data
@ToString
@ConfigurationProperties(prefix = "hbase.zk")
public class HBaseProperties {

    private String host;

    private String port;

    private String nodeParent;
}
