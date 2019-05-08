package com.sun.flower.cahce;

import com.sun.flower.jetcache.JetCacheAppStart;
import com.sun.flower.jetcache.service.BaseCacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/5/8 09:42
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JetCacheAppStart.class)
public class BaseTest {

    @Autowired
    BaseCacheService cacheService;

    @Test
    public void baseTest() throws Exception {
        for (;;) {
            System.out.println("====" + cacheService.getCache("base"));

            Thread.sleep(30000);
        }
    }

}
