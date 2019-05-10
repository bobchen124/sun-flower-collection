package com.sun.flower.cahce;

import com.sun.flower.jetcache.JetCacheAppStart;
import com.sun.flower.jetcache.param.ParamDTO;
import com.sun.flower.jetcache.service.IBaseCacheService;
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
    IBaseCacheService cacheService;

    @Test
    public void baseTest() throws Exception {
        ParamDTO paramDTO = new ParamDTO();
        paramDTO.setCat(1);
        paramDTO.setPage(1);

        for (;;) {
            System.out.println("====" + cacheService.getCache2(paramDTO));

            Thread.sleep(30000);
        }
    }

}
