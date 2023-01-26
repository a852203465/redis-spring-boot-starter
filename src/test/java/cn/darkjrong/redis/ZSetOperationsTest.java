package cn.darkjrong.redis;

import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

public class ZSetOperationsTest extends RedisInitTest {

    @Test
    void zAdd() {

        for (int i = 1; i < 20; i++) {
            Person person = new Person();
            person.setId(i);
            person.setName("Rong.Jia" + i);
            person.setAge(i);
            person.setSex("男");

            redisUtils.zAdd("persons", person, i);
        }


    }

    @Test
    void zRange() {
        System.out.println(redisUtils.zRange("persons", 0, 2, Person.class));
//        System.out.println(redisUtils.zRange("persons", 0, 2, new TypeReference<Person>() {}));

    }

    @Test
    void zRangeWithScores() {
        Set<ZSetOperations.TypedTuple<Object>> persons = redisUtils.zRangeWithScores("persons", 0, 2);
        for (ZSetOperations.TypedTuple<Object> typedTuple : persons) {
            System.out.println(typedTuple.getScore());
            System.out.println(typedTuple.getValue());
        }
    }

    @Test
    void zRangeWithScores2() {
        Set<ZSetOperations.TypedTuple<Person>> persons = redisUtils.zRangeWithScores("persons", 0, 2, Person.class);
        for (ZSetOperations.TypedTuple<Person> typedTuple : persons) {
            System.out.println(typedTuple.getScore());
            System.out.println(typedTuple.getValue());
        }
    }

    @Test
    void zRangeByScore() {
        Set<Person> persons = redisUtils.zRangeByScore("persons", 0, 4, Person.class);
        for (Person person : persons) {
            System.out.println(person);
        }



    }







}
