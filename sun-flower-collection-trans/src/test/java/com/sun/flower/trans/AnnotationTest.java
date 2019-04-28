package com.sun.flower.trans;

import com.alibaba.fastjson.JSONObject;
import com.sun.flower.trans.aspect.LoadCacheService;
import com.sun.flower.trans.aspect.exception.BaseException;
import com.sun.flower.trans.aspect.params.ParamDTO;
import com.sun.flower.trans.aspect.params.ResopneVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/4/26 19:11
 **/
@Slf4j
public class AnnotationTest extends BaseTest {

    @Autowired
    LoadCacheService cacheService;

    @Test
    public void testCache() throws BaseException {
        ParamDTO paramDTO = new ParamDTO();
        paramDTO.setCat(1);
        paramDTO.setPage(1);

        List<ResopneVO> resopneVOS = cacheService.findResopneList(paramDTO);
        log.info("resopneVOS = {}", JSONObject.toJSONString(resopneVOS));

        ParamDTO paramDTO2 = new ParamDTO();
        paramDTO2.setCat(2);
        paramDTO2.setPage(2);

        List<ResopneVO> resopneVOS2 = cacheService.findResopneList(paramDTO2);
        log.info("resopneVOS2 = {}", JSONObject.toJSONString(resopneVOS2));

        paramDTO.setPage(10);
        List<ResopneVO> resopneVOS1 = cacheService.baseService(paramDTO);
        log.info("resopneVOS1 = {}", JSONObject.toJSONString(resopneVOS1));

        List<ResopneVO> resopneVOS3 = cacheService.baseService(paramDTO2);
        log.info("resopneVOS3 = {}", JSONObject.toJSONString(resopneVOS3));
    }

}
