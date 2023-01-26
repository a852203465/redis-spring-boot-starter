package cn.darkjrong.redis;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.ScanOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashOperationsInitTest extends RedisInitTest {

    @Test
    void hset() {

        Person person = new Person();
        person.setId(1);
        person.setName("Rong.Jia");
        person.setAge(23);
        person.setSex("男");


        redisUtils.hPut("persons2", "1", CollectionUtil.newArrayList(person, person));

    }

    @Test
    void hget() {
        List<Person> list = redisUtils.hGet("persons2", "1", new TypeReference<List<Person>>(){});
        System.out.println(list);

    }

    @Test
    void hgetAll() {
        Map<String, HashMap> hashMap = redisUtils.hGetAll("hset", HashMap.class);
        System.out.println(hashMap);

        Map<String, Map<String, Object>> hashMap2 = redisUtils.hGetAll("hset", new TypeReference<Map<String, Object>>(){});
        System.out.println(hashMap2);
    }

    @Test
    void hMultiGet() {
        List<HashMap> hashMaps = redisUtils.hMultiGet("hset", CollectionUtil.newArrayList("a"), HashMap.class);
        System.out.println(hashMaps);

        List<Map<String, Object>> maps = redisUtils.hMultiGet("hset", CollectionUtil.newArrayList("a"), new TypeReference<Map<String, Object>>() {});
        System.out.println(maps);
    }

    @Test
    void hKey() {
        System.out.println(redisUtils.hKeys("hset"));

    }

    @Test
    void hValues() {
        System.out.println(redisUtils.hValues("hset"));

        System.out.println(redisUtils.hValues("hset", HashMap.class));

        System.out.println(redisUtils.hValues("hset", new TypeReference<Map<String, Object>>(){}));
    }

    @Test
    void hScan() {

        ScanOptions scanOptions = ScanOptions.scanOptions()
                .count(20).match("1").build();

        Map<String, Person> persons = redisUtils.hScan("persons", scanOptions, new TypeReference<Person>(){});

        System.out.println(persons);




    }

}
