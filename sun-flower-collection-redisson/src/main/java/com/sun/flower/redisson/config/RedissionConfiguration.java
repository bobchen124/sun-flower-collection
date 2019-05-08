package com.sun.flower.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.starter.RedissonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/4/26 11:21
 **/
@Configuration
@EnableConfigurationProperties({RedissonProperties.class})
public class RedissionConfiguration {

    @Autowired
    private RedissonProperties redissonProperties;

    @Autowired
    private ApplicationContext ctx;

    protected InputStream getConfigStream() throws IOException {
        Resource resource = this.ctx.getResource(this.redissonProperties.getConfig());
        InputStream is = resource.getInputStream();
        return is;
    }

    //@Bean
    public RedissonClient redisson() {
        Config config = null;

        if (this.redissonProperties.getConfig() != null) {
            try {
                InputStream is = this.getConfigStream();
                config = Config.fromJSON(is);
            } catch (IOException var10) {
                try {
                    InputStream is = this.getConfigStream();
                    config = Config.fromYAML(is);
                } catch (IOException var9) {
                    throw new IllegalArgumentException("Can't parse config", var9);
                }
            }
        }

        return Redisson.create(config);
    }

    @Bean
    public RedissonClient redissonClient() {
        return Redisson.create();
    }
}
