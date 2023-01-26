package cn.darkjrong.redis;

import com.alibaba.fastjson.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.ScanOptions;

import java.util.List;

public class SetOperationsTest extends RedisInitTest {

    @Test
    void sAdd() {

        for (int i = 1; i < 20; i++) {
            Person person = new Person();
            person.setId(i);
            person.setName("Rong.Jia" + i);
            person.setAge(i);
            person.setSex("男");

            redisUtils.sAdd("persons", person);
        }

    }

    @Test
    void sPop() {

        System.out.println(redisUtils.sPop("persons", Person.class));
        System.out.println(redisUtils.sPop("persons", new TypeReference<Person>() {}));

    }

    @Test
    void sMembers() {
        System.out.println(redisUtils.sMembers("persons", Person.class));
        System.out.println(redisUtils.sMembers("persons", new TypeReference<Person>() {}));



    }

    @Test
    void sRandomMember() {
        System.out.println(redisUtils.sRandomMember("persons", Person.class));
        System.out.println(redisUtils.sRandomMember("persons", new TypeReference<Person>() {}));

    }

    @Test
    void sRandomMembers() {
        System.out.println(redisUtils.sRandomMembers("persons", 1, Person.class));
        System.out.println(redisUtils.sRandomMembers("persons", 1, new TypeReference<Person>() {}));



    }

    @Test
    void sDistinctRandomMembers() {
        System.out.println(redisUtils.sDistinctRandomMembers("persons", 2, Person.class));
//        System.out.println(redisUtils.sDistinctRandomMembers("persons", 2, new TypeReference<Person>() {}));


    }

    @Test
    void sScan() {
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .count(20).match("*").build();

        List<Person> objects = redisUtils.sScan("persons", scanOptions, Person.class);
        List<Person> objects2 = redisUtils.sScan("persons", scanOptions, new TypeReference<Person>(){});
        System.out.println(objects.size());
        System.out.println(objects2.size());

    }









}
