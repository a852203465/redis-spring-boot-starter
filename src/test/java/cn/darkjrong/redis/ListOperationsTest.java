package cn.darkjrong.redis;

import com.alibaba.fastjson.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ListOperationsTest extends RedisInitTest {

    @BeforeEach
    void lLeftPush() {

        Person person = new Person();
        person.setId(1);
        person.setName("Rong.Jia");
        person.setAge(23);
        person.setSex("男");

        redisUtils.lLeftPush("persons", person);

    }

    @Test
    void lIndex() {

        Person persons = redisUtils.lIndex("persons", 0, new TypeReference<Person>(){});
        System.out.println(persons);

    }

    @Test
    void lRange() {

        List<Person> persons = redisUtils.lRange("persons", 0, 1, new TypeReference<Person>(){});
        System.out.println(persons);

    }

    @Test
    void lLeftPop() {

        Person persons = redisUtils.lLeftPop("persons", new TypeReference<Person>(){});
        System.out.println(persons);

        Person persons1 = redisUtils.lLeftPop("persons", Person.class);
        System.out.println(persons1);

    }






}
