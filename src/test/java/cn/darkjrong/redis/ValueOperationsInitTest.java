package cn.darkjrong.redis;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.TypeReference;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueOperationsInitTest extends RedisInitTest {

    @Test
    void set() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 1);

        redisUtils.set("a", map);

    }

    @Test
    void multiGet() {
        List<HashMap> hashMaps = redisUtils.multiGet(CollectionUtil.newArrayList("a"), HashMap.class);
        System.out.println(hashMaps);

        List<Map<String, Object>> hashMap2 = redisUtils.multiGet(CollectionUtil.newArrayList("a"), new TypeReference<Map<String, Object>>(){});
        System.out.println(hashMap2);
    }



}
