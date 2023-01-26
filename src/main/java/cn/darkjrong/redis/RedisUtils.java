package cn.darkjrong.redis;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.TypeReference;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis工具类
 *
 * @author Rong.Jia
 * @date 2019/04/22 14:02:22
 */
@SuppressWarnings("ALL")
public class RedisUtils {

    private RedisTemplate<String, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return this.redisTemplate;
    }

    /* -------------------key相关操作--------------------- */

    /**
     * 删除key
     *
     * @param key Key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除key
     *
     * @param keys Key集合
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 序列化key
     *
     * @param key Key
     * @return {@link byte[]}
     */
    public byte[] dump(String key) {
        return redisTemplate.dump(key);
    }

    /**
     * 是否存在key
     *
     * @param key key
     * @return {@link Boolean}
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间
     *
     * @param timeout 超时时间
     * @param unit    单位
     * @param key     key
     * @return {@link Boolean}
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 设置过期时间
     *
     * @param key  key
     * @param date 日期
     * @return {@link Boolean}
     */
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 查找匹配的key
     *
     * @param pattern 匹配模式
     * @return {@link Set}<{@link String}>
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 将当前数据库的 key 移动到给定的数据库 db 当中
     *
     * @param key     key
     * @param dbIndex 数据库索引
     * @return {@link Boolean}
     */
    public Boolean move(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    /**
     * 移除 key 的过期时间，key 将持久保持
     *
     * @param key key
     * @return {@link Boolean}
     */
    public Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 返回 key 的剩余的过期时间
     *
     * @param key  key
     * @param unit 单位
     * @return {@link Long}
     */
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 返回 key 的剩余的过期时间
     *
     * @param key key
     * @return {@link Long}
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 从当前数据库中随机返回一个 key
     *
     * @return {@link String}
     */
    public String randomKey() {
        return redisTemplate.randomKey();
    }

    /**
     * 修改 key 的名称
     *
     * @param oldKey 老key
     * @param newKey 新key
     */
    public void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 仅当 newKey 不存在时，将 oldKey 改名为 newKey
     *
     * @param oldKey 老key
     * @param newKey 新key
     * @return {@link Boolean}
     */
    public Boolean renameIfAbsent(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 返回 key 所储存的值的类型
     *
     * @param key key
     * @return {@link DataType}
     */
    public DataType type(String key) {
        return redisTemplate.type(key);
    }

    /* -------------------string相关操作--------------------- */

    /**
     * 设置指定 key 的值
     *
     * @param key   key
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取指定 key 的值
     *
     * @param key key
     * @return {@link Object}
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取指定 key 的值
     *
     * @param key    key
     * @param tClass 返回值类型
     * @return {@link T} 返回值
     */
    public <T> T get(String key, Class<T> tClass) {
        return BeanUtils.copyProperties(get(key), tClass);
    }

    /**
     * 获取指定 key 的值
     *
     * @param key            key
     * @param tTypeReference 返回值类型
     * @return {@link T} 返回值
     */
    public <T> T get(String key, TypeReference<T> tTypeReference) {
        return BeanUtils.copyProperties(get(key), tTypeReference);
    }

    /**
     * 返回 key 中字符串值的子字符
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @return {@link String}
     */
    public String getRange(String key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     *
     * @param key   key
     * @param value 值
     * @return {@link Object}
     */
    public Object getAndSet(String key, Object value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)
     *
     * @param key    key
     * @param offset 偏移量
     * @return {@link Boolean}
     */
    public Boolean getBit(String key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * 批量获取
     *
     * @param keys keys
     * @return {@link List}<{@link Object}>
     */
    public List<Object> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 批量获取
     *
     * @param <T>    目标对象泛型
     * @param tClass 目标对象类型
     * @param keys   keys
     * @return {@link List}<{@link Object}>
     */
    public <T> List<T> multiGet(Collection<String> keys, Class<T> tClass) {
        List<Object> objects = redisTemplate.opsForValue().multiGet(keys);
        return BeanUtils.copyProperties(objects, tClass);
    }

    /**
     * 批量获取
     *
     * @param <T>            目标对象泛型
     * @param tTypeReference 目标对象类型
     * @param keys           keys
     * @return {@link List}<{@link Object}>
     */
    public <T> List<T> multiGet(Collection<String> keys, TypeReference<T> tTypeReference) {
        List<Object> objects = redisTemplate.opsForValue().multiGet(keys);
        return BeanUtils.copyProperties(objects, tTypeReference);
    }

    /**
     * 设置ASCII码, 字符串'a'的ASCII码是97, 转为二进制是'01100001', 此方法是将二进制第offset位值变为value
     *
     * @param offset 位置
     * @param value  值,true为1, false为0
     * @param key    key
     * @return {@link Boolean}
     */
    public Boolean setBit(String key, long offset, boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
     *
     * @param timeout 过期时间
     * @param unit    时间单位, 天:TimeUnit.DAYS 小时:TimeUnit.HOURS 分钟:TimeUnit.MINUTES
     *                秒:TimeUnit.SECONDS 毫秒:TimeUnit.MILLISECONDS
     * @param key     key
     * @param value   值
     */
    public void setEx(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param key   key
     * @param value 值
     * @return {@link Boolean} 之前已经存在返回false, 不存在返回true
     */
    public Boolean setIfAbsent(String key, Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始
     *
     * @param offset 从指定位置开始覆写
     * @param key    key
     * @param value  值
     */
    public void setRange(String key, Object value, long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * 获取字符串的长度
     *
     * @param key key
     * @return {@link Long}
     */
    public Long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 批量添加
     *
     * @param maps 集合
     */
    public void multiSet(Map<String, Object> maps) {
        redisTemplate.opsForValue().multiSet(maps);
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在
     *
     * @param maps 集合
     * @return {@link Boolean} 之前已经存在返回false, 不存在返回true
     */
    public Boolean multiSetIfAbsent(Map<String, Object> maps) {
        return redisTemplate.opsForValue().multiSetIfAbsent(maps);
    }

    /**
     * 增加(自增长)
     *
     * @param key key
     * @return {@link Long}
     */
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 增加(自增长), 负数则为自减
     *
     * @param key       key
     * @param increment 增量
     * @return {@link Long}
     */
    public Long increment(String key, long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 增加(自增长), 负数则为自减
     *
     * @param key       key
     * @param increment 自增值
     * @return {@link Double}
     */
    public Double increment(String key, double increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 减少(自减)，正数则为自增
     *
     * @param key key
     * @return {@link Long}
     */
    public Long decrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * 减少(自减)，正数则为自增
     *
     * @param key       key
     * @param increment 自减量
     * @return {@link Long}
     */
    public Long decrement(String key, long increment) {
        return redisTemplate.opsForValue().decrement(key, increment);
    }

    /**
     * 追加到末尾
     *
     * @param key   key
     * @param value 值
     * @return {@link Integer}
     */
    public Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    /* -------------------hash相关操作------------------------- */

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param key   key
     * @param field 字段名
     * @return {@link Object}
     */
    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param tClass 目标对象类型
     * @param <T>    目标对象泛型
     * @param key    key
     * @param field  字段名
     * @return {@link T}
     */
    public <T> T hGet(String key, String field, Class<T> tClass) {
        return BeanUtils.copyProperties(hGet(key, field), tClass);
    }

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param <T>            目标对象泛型
     * @param tTypeReference 目标对象类型
     * @param key            key
     * @param field          字段名
     * @return {@link T}
     */
    public <T> T hGet(String key, String field, TypeReference<T> tTypeReference) {
        return BeanUtils.copyProperties(hGet(key, field), tTypeReference);
    }

    /**
     * 获取哈希表中所有字段的值
     *
     * @param key key
     * @return {@link Map}<{@link Object}, {@link Object}>
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取哈希表中所有字段的值
     *
     * @param valueClass Value类型
     * @param key        key
     * @return {@link Map}<{@link String}, {@link V}>
     */
    public <V> Map<String, V> hGetAll(String key, Class<V> valueClass) {
        return BeanUtils.copyProperties(hGetAll(key), String.class, valueClass);
    }

    /**
     * 获取哈希表中所有字段的值
     *
     * @param key            key
     * @param vTypeReference Value类型
     * @return {@link Map}<{@link String}, {@link V}>
     */
    public <V> Map<String, V> hGetAll(String key, TypeReference<V> vTypeReference) {
        return BeanUtils.copyProperties(hGetAll(key), new TypeReference<String>(){}, vTypeReference);
    }

    /**
     * 获取在哈希表中给定字段的值
     *
     * @param key    key
     * @param fields 字段
     * @return {@link List}<{@link Object}>
     */
    public List<Object> hMultiGet(String key, Collection<String> fields) {
        if (CollectionUtil.isNotEmpty(fields)) {
            List<Object> fieldList = fields.stream().map(a -> (Object) a).collect(Collectors.toList());
            return redisTemplate.opsForHash().multiGet(key, fieldList);
        }
        return Collections.emptyList();
    }

    /**
     * 获取在哈希表中给定字段的值
     *
     * @param key    key
     * @param fields 字段
     * @param tClass 对象类型
     * @return {@link List}<{@link T}>
     */
    public <T> List<T> hMultiGet(String key, Collection<String> fields, Class<T> tClass) {
        return BeanUtils.copyProperties(this.hMultiGet(key, fields), tClass);
    }

    /**
     * 获取在哈希表中给定字段的值
     *
     * @param key            key
     * @param fields         字段
     * @param tTypeReference 对象类型
     * @return {@link List}<{@link T}>
     */
    public <T> List<T> hMultiGet(String key, Collection<String> fields, TypeReference<T> tTypeReference) {
        return BeanUtils.copyProperties(this.hMultiGet(key, fields), tTypeReference);
    }

    /**
     * 指定hashKey存储指定值
     *
     * @param key     key
     * @param hashKey 哈希key
     * @param value   值
     */
    public void hPut(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 指定Key存储所有的值
     *
     * @param key  key
     * @param maps 数据
     */
    public void hPutAll(String key, Map<String, Object> maps) {
        redisTemplate.opsForHash().putAll(key, maps);
    }

    /**
     * 仅当hashKey不存在时才设置值
     *
     * @param key     key
     * @param hashKey 哈希key
     * @param value   值
     * @return {@link Boolean}
     */
    public Boolean hPutIfAbsent(String key, String hashKey, Object value) {
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    /**
     * 删除一个或多个哈希表字段
     *
     * @param key    key
     * @param fields 字段
     * @return {@link Long}
     */
    public Long hDelete(String key, Object... fields) {
        return redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     *
     * @param key   key
     * @param field 字段
     * @return {@link Boolean}
     */
    public Boolean hExists(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key       key
     * @param field     字段
     * @param increment 增量
     * @return {@link Long}
     */
    public Long hIncrement(String key, Object field, long increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key   key
     * @param field 场
     * @param delta δ
     * @return {@link Double}
     */
    public Double hIncrement(String key, Object field, double delta) {
        return redisTemplate.opsForHash().increment(key, field, delta);
    }

    /**
     * 获取所有哈希表中的字段
     *
     * @param key key
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> hKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取所有哈希表中的字段
     *
     * @param key key
     * @return {@link Set}<{@link String}>
     */
    public Set<String> hKeysToStr(String key) {
        return hKeys(key, String.class);
    }

    /**
     * 获取所有哈希表中的字段
     *
     * @param key key
     * @param tClass 字段类型
     * @return {@link Set}<{@link T}>
     */
    public <T> Set<T> hKeys(String key, Class<T> tClass) {
        return BeanUtils.sCopyProperties(hKeys(key), tClass);
    }

    /**
     * 获取所有哈希表中的字段
     *
     * @param key key
     * @param typeReference 字段类型
     * @return {@link Set}<{@link T}>
     */
    public <T> Set<T> hKeys(String key, TypeReference<T> typeReference) {
        return BeanUtils.sCopyProperties(hKeys(key), typeReference);
    }

    /**
     * 获取哈希表中字段的数量
     *
     * @param key key
     * @return {@link Long}
     */
    public Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 获取哈希表中所有值
     *
     * @param key key
     * @return {@link List}<{@link Object}>
     */
    public List<Object> hValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 获取哈希表中所有值
     *
     * @param tClass 对象类型
     * @param key    key
     * @return {@link List}<{@link T}>
     */
    public <T> List<T> hValues(String key, Class<T> tClass) {
        return BeanUtils.copyProperties(hValues(key), tClass);
    }

    /**
     * 获取哈希表中所有值
     *
     * @param typeReference 对象类型
     * @param key           key
     * @return {@link List}<{@link T}>
     */
    public <T> List<T> hValues(String key, TypeReference<T> typeReference) {
        return BeanUtils.copyProperties(hValues(key), typeReference);
    }

    /**
     * 迭代哈希表中的键值对
     *
     * @param key     key
     * @param options 操作选项
     * @return {@link Map}<{@link Object}, {@link Object}>
     */
    public Map<Object, Object> hScan(String key, ScanOptions options) {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, options);
        try {
            return cursor.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }finally {
            if (!cursor.isClosed()) cursor.close();
        }
    }

    /**
     * 迭代哈希表中的键值对
     *
     * @param key     key
     * @param options 操作选项
     * @param tClass  对象类型
     * @return {@link Map}<{@link String}, {@link T}>
     */
    public <T> Map<String, T> hScan(String key, ScanOptions options, Class<T> tClass) {
        Map<Object, Object> objectMap = hScan(key, options);
        if (CollectionUtil.isNotEmpty(objectMap)) {
            return objectMap.entrySet().stream()
                    .collect(Collectors.toMap(a -> BeanUtils.copyProperties(a.getKey(), String.class),
                    v -> BeanUtils.copyProperties(v.getValue(), tClass)));
        }
        return Collections.emptyMap();
    }

    /**
     * 迭代哈希表中的键值对
     *
     * @param key     key
     * @param options 操作选项
     * @param typeReference  对象类型
     * @return {@link Map}<{@link String}, {@link T}>
     */
    public <T> Map<String, T> hScan(String key, ScanOptions options, TypeReference<T> typeReference) {
        Map<Object, Object> objectMap = hScan(key, options);
        if (CollectionUtil.isNotEmpty(objectMap)) {
            return objectMap.entrySet().stream()
                    .collect(Collectors.toMap(a -> BeanUtils.copyProperties(a.getKey(), String.class),
                            v -> BeanUtils.copyProperties(v.getValue(), typeReference)));
        }
        return Collections.emptyMap();
    }

    /* ------------------------list相关操作---------------------------- */

    /**
     * 通过索引获取列表中的元素
     *
     * @param key   key
     * @param index 索引, 从0开始
     * @return {@link Object}
     */
    public Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 通过索引获取列表中的元素
     *
     * @param key    key
     * @param index  索引, 从0开始
     * @param tClass 对象类型
     * @return {@link T}
     */
    public <T> T lIndex(String key, long index, Class<T> tClass) {
        return BeanUtils.copyProperties(lIndex(key, index), tClass);
    }

    /**
     * 通过索引获取列表中的元素
     *
     * @param key    key
     * @param index  索引, 从0开始
     * @param typeReference 对象类型
     * @return {@link T}
     */
    public <T> T lIndex(String key, long index, TypeReference<T> typeReference) {
        return BeanUtils.copyProperties(lIndex(key, index), typeReference);
    }

    /**
     * 获取列表指定范围内的元素
     *
     * @param start 开始位置, 0是开始位置
     * @param end   结束位置, -1返回所有
     * @param key   key
     * @return {@link List}<{@link Object}>
     */
    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取列表指定范围内的元素
     *
     * @param start  开始位置, 0是开始位置
     * @param end    结束位置, -1返回所有
     * @param key    key
     * @param tClass 对象类型
     * @return {@link List}<{@link T}>
     */
    public <T> List<T> lRange(String key, long start, long end, Class<T> tClass) {
        return BeanUtils.copyProperties(lRange(key, start, end), tClass);
    }

    /**
     * 获取列表指定范围内的元素
     *
     * @param start  开始位置, 0是开始位置
     * @param end    结束位置, -1返回所有
     * @param key    key
     * @param typeReference 对象类型
     * @return {@link List}<{@link T}>
     */
    public <T> List<T> lRange(String key, long start, long end, TypeReference<T> typeReference) {
        return BeanUtils.copyProperties(lRange(key, start, end), typeReference);
    }

    /**
     * 存储在list头部
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public Long lLeftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 往list头部存储 所有值
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public Long lLeftPushAll(String key, Object... value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 往list头部存储 所有值
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public Long lLeftPushAll(String key, Collection<Object> value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 当list存在的时候才加入头部
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public Long lLeftPushIfPresent(String key, Object value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 如果pivot存在,再pivot前面添加
     *
     * @param key   key
     * @param pivot 主
     * @param value 值
     * @return {@link Long}
     */
    public Long lLeftPush(String key, String pivot, Object value) {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 存储在list尾部
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public Long lRightPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 在list尾部存储所有值
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public Long lRightPushAll(String key, Object... value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 在list尾部存储所有值
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public Long lRightPushAll(String key, Collection<Object> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 为已存在的列表添加值到尾部
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public Long lRightPushIfPresent(String key, Object value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 在pivot元素的右边添加值
     *
     * @param key   key
     * @param pivot 主
     * @param value 值
     * @return {@link Long}
     */
    public Long lRightPush(String key, String pivot, Object value) {
        return redisTemplate.opsForList().rightPush(key, pivot, value);
    }

    /**
     * 通过索引设置列表元素的值
     *
     * @param index 位置
     * @param key   key
     * @param value 值
     */
    public void lSet(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移出并获取列表的第一个元素
     *
     * @param key key
     * @return {@link Object} 删除的元素
     */
    public Object lLeftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移出并获取列表的第一个元素
     *
     * @param key    key
     * @param tClass 对象类型
     * @return {@link T} 删除的元素
     */
    public <T> T lLeftPop(String key, Class<T> tClass) {
        return BeanUtils.copyProperties(lLeftPop(key), tClass);
    }

    /**
     * 移出并获取列表的第一个元素
     *
     * @param key    key
     * @param typeReference 对象类型
     * @return {@link T} 删除的元素
     */
    public <T> T lLeftPop(String key, TypeReference<T> typeReference) {
        return BeanUtils.copyProperties(lLeftPop(key), typeReference);
    }

    /**
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param timeout 超时时间
     * @param unit    时间单位
     * @param key     key
     * @return {@link Object} 删除的元素
     */
    public Object lBLeftPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param timeout 超时时间
     * @param unit    时间单位
     * @param key    key
     * @param tClass 对象类型
     * @return {@link T} 删除的元素
     */
    public <T> T lBLeftPop(String key, long timeout, TimeUnit unit, Class<T> tClass) {
        return BeanUtils.copyProperties(lBLeftPop(key, timeout, unit), tClass);
    }

    /**
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param timeout 超时时间
     * @param unit    时间单位
     * @param key    key
     * @param typeReference 对象类型
     * @return {@link T} 删除的元素
     */
    public <T> T lBLeftPop(String key, long timeout, TimeUnit unit, TypeReference<T> typeReference) {
        return BeanUtils.copyProperties(lBLeftPop(key, timeout, unit), typeReference);
    }

    /**
     * 移除并获取列表最后一个元素
     *
     * @param key key
     * @return {@link Object} 删除的元素
     */
    public Object lRightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移除并获取列表最后一个元素
     *
     * @param key    key
     * @param tClass 对象类型
     * @return {@link T} 删除的元素
     */
    public <T> T lRightPop(String key, Class<T> tClass) {
        return BeanUtils.copyProperties(lRightPop(key), tClass);
    }

    /**
     * 移除并获取列表最后一个元素
     *
     * @param key    key
     * @param typeReference 对象类型
     * @return {@link T} 删除的元素
     */
    public <T> T lRightPop(String key, TypeReference<T> typeReference) {
        return BeanUtils.copyProperties(lRightPop(key), typeReference);
    }

    /**
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param timeout 等待时间
     * @param unit    时间单位
     * @param key     key
     * @return {@link Object}
     */
    public Object lBRightPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param timeout 超时时间
     * @param unit    时间单位
     * @param key    key
     * @param tClass 对象类型
     * @return {@link T} 删除的元素
     */
    public <T> T lBRightPop(String key, long timeout, TimeUnit unit, Class<T> tClass) {
        return BeanUtils.copyProperties(lBRightPop(key, timeout, unit), tClass);
    }

    /**
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param timeout 超时时间
     * @param unit    时间单位
     * @param key    key
     * @param typeReference 对象类型
     * @return {@link T} 删除的元素
     */
    public <T> T lBRightPop(String key, long timeout, TimeUnit unit, TypeReference<T> typeReference) {
        return BeanUtils.copyProperties(lBRightPop(key, timeout, unit), typeReference);
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     *
     * @param sourceKey      源key
     * @param destinationKey 目标key
     * @return {@link Object}
     */
    public Object lRightPopAndLeftPush(String sourceKey, String destinationKey) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param sourceKey      源key
     * @param destinationKey 目标key
     * @param timeout        超时时间
     * @param unit           单位
     * @return {@link Object}
     */
    public Object lBRightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
    }

    /**
     * 删除集合中值等于value得元素
     *
     * @param index index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素;
     *              index<0, 从尾部开始删除第一个值等于value的元素;
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public Long lRemove(String key, long index, Object value) {
        return redisTemplate.opsForList().remove(key, index, value);
    }

    /**
     * 裁剪list
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     */
    public void lTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 获取列表长度
     *
     * @param key key
     * @return {@link Long}
     */
    public Long lLen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /* --------------------set相关操作-------------------------- */

    /**
     * set添加元素
     *
     * @param key    key
     * @param values 值
     * @return {@link Long}
     */
    public Long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * set移除元素
     *
     * @param key    key
     * @param values 值
     * @return {@link Long}
     */
    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 移除并返回集合的一个随机元素
     *
     * @param key key
     * @return {@link Object}
     */
    public Object sPop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 移除并返回集合的一个随机元素
     *
     * @param key    key
     * @param tClass 对象类型
     * @return {@link T}
     */
    public <T> T sPop(String key, Class<T> tClass) {
        return BeanUtils.copyProperties(sPop(key), tClass);
    }

    /**
     * 移除并返回集合的一个随机元素
     *
     * @param key    key
     * @param typeReference 对象类型
     * @return {@link T}
     */
    public <T> T sPop(String key, TypeReference<T> typeReference) {
        return BeanUtils.copyProperties(sPop(key), typeReference);
    }

    /**
     * 将元素value从一个集合移到另一个集合
     *
     * @param key     key
     * @param value   值
     * @param destKey 目标key
     * @return {@link Boolean}
     */
    public Boolean sMove(String key, Object value, String destKey) {
        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * 获取集合的大小
     *
     * @param key key
     * @return {@link Long}
     */
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 判断集合是否包含value
     *
     * @param key   key
     * @param value 值
     * @return {@link Boolean}
     */
    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取key集合的交集
     *
     * @param keys keys
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> sIntersect(Collection<String> keys) {
        return redisTemplate.opsForSet().intersect(keys);
    }

    /**
     * 获取两个集合的交集
     *
     * @param key      key
     * @param otherKey 其他key
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> sIntersect(String key, String otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的交集
     *
     * @param key       key
     * @param otherKeys 其他keys
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> sIntersect(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().intersect(key, otherKeys);
    }

    /**
     * key集合的交集存储到destKey集合中
     *
     * @param destKey 目标key
     * @param keys    keys
     * @return {@link Long}
     */
    public Long sIntersectAndStore(Collection<String> keys, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(keys, destKey);
    }

    /**
     * key与otherKey集合的交集存储到destKey集合中
     *
     * @param key      key
     * @param otherKey 其他key
     * @param destKey  目标key
     * @return {@link Long}
     */
    public Long sIntersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * key与多个集合的交集存储到destKey集合中
     *
     * @param key       key
     * @param otherKeys 其他keys
     * @param destKey   目标key
     * @return {@link Long}
     */
    public Long sIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取两个集合的并集
     *
     * @param key       key
     * @param otherKeys 其他keys
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> sUnion(String key, String otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * 获取key集合的并集
     *
     * @param keys keys
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> sUnion(Collection<String> keys) {
        return redisTemplate.opsForSet().union(keys);
    }

    /**
     * 获取key集合与多个集合的并集
     *
     * @param key       key
     * @param otherKeys 其他keys
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> sUnion(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的并集存储到destKey中
     *
     * @param key      key
     * @param otherKey 其他key
     * @param destKey  目标key
     * @return {@link Long}
     */
    public Long sUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * key集合与多个集合的并集存储到destKey中
     *
     * @param key       key
     * @param otherKeys 其他keys
     * @param destKey   目标key
     * @return {@link Long}
     */
    public Long sUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * key集合的并集存储到destKey中
     *
     * @param destKey 目标key
     * @param keys    keys
     * @return {@link Long}
     */
    public Long sUnionAndStore(Collection<String> keys, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(keys, destKey);
    }

    /**
     * 获取两个集合的差集
     *
     * @param key      key
     * @param otherKey 其他key
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> sDifference(String key, String otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的差集
     *
     * @param key       key
     * @param otherKeys 其他keys
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> sDifference(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().difference(key, otherKeys);
    }

    /**
     * 获取key集合的差集
     *
     * @param keys keys
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> sDifference(Collection<String> keys) {
        return redisTemplate.opsForSet().difference(keys);
    }

    /**
     * key集合与otherKey集合的差集存储到destKey中
     *
     * @param key      key
     * @param otherKey 其他key
     * @param destKey  目标key
     * @return {@link Long}
     */
    public Long sDifference(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    /**
     * key集合与多个集合的差集存储到destKey中
     *
     * @param key       key
     * @param otherKeys 其他keys
     * @param destKey   目标key
     * @return {@link Long}
     */
    public Long sDifference(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKeys, destKey);
    }

    /**
     * key集合的差集存储到destKey中
     *
     * @param destKey 目标key
     * @param keys    keys
     * @return {@link Long}
     */
    public Long sDifference(Collection<String> keys, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(keys, destKey);
    }

    /**
     * 获取Set集合的所有元素
     *
     * @param key key
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 获取Set集合的所有元素
     *
     * @param key    key
     * @param tClass 对象类型
     * @return {@link Set}<{@link T}>
     */
    public <T> Set<T> sMembers(String key, Class<T> tClass) {
        return BeanUtils.sCopyProperties(sMembers(key), tClass);
    }

    /**
     * 获取Set集合的所有元素
     *
     * @param key key
     * @param typeReference 对象类型
     * @return {@link Set}<{@link T}>
     */
    public <T> Set<T> sMembers(String key, TypeReference<T> typeReference) {
        return BeanUtils.sCopyProperties(sMembers(key), typeReference);
    }

    /**
     * 随机获取集合中的一个元素
     *
     * @param key key
     * @return {@link Object}
     */
    public Object sRandomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 随机获取集合中的一个元素
     *
     * @param key    key
     * @param tClass 对象类型
     * @return {@link T}
     */
    public <T> T sRandomMember(String key, Class<T> tClass) {
        return BeanUtils.copyProperties(sRandomMember(key), tClass);
    }

    /**
     * 随机获取集合中的一个元素
     *
     * @param key key
     * @param typeReference 对象类型
     * @return {@link Object}
     */
    public <T> T sRandomMember(String key, TypeReference<T> typeReference) {
        return BeanUtils.copyProperties(sRandomMember(key), typeReference);
    }

    /**
     * 随机获取集合中count个元素
     *
     * @param key   key
     * @param count 个数
     * @return {@link List}<{@link Object}>
     */
    public List<Object> sRandomMembers(String key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取集合中count个元素
     *
     * @param tClass 对象类型
     * @param key    key
     * @param count  个数
     * @return {@link List}<{@link T}>
     */
    public <T> List<T> sRandomMembers(String key, long count, Class<T> tClass) {
        return BeanUtils.copyProperties(sRandomMembers(key, count), tClass);
    }

    /**
     * 随机获取集合中count个元素
     *
     * @param key           key
     * @param typeReference 对象类型
     * @param count         个数
     * @return {@link List}<{@link T}>
     */
    public <T> List<T> sRandomMembers(String key, long count, TypeReference<T> typeReference) {
        return BeanUtils.copyProperties(sRandomMembers(key, count), typeReference);
    }

    /**
     * 随机获取集合中count个元素并且去除重复的
     *
     * @param key   key
     * @param count 个数
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> sDistinctRandomMembers(String key, long count) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * 随机获取集合中count个元素并且去除重复的
     *
     * @param key    key
     * @param count  个数
     * @param tClass 对象类型
     * @return {@link Set}<{@link T}>
     */
    public <T> Set<T> sDistinctRandomMembers(String key, long count, Class<T> tClass) {
        return BeanUtils.sCopyProperties(sDistinctRandomMembers(key, count), tClass);
    }

    /**
     * 随机获取集合中count个元素并且去除重复的
     *
     * @param key    key
     * @param count  个数
     * @param typeReference 对象类型
     * @return {@link Set}<{@link T}>
     */
    public <T> Set<T> sDistinctRandomMembers(String key, long count, TypeReference<T> typeReference) {
        return BeanUtils.sCopyProperties(sDistinctRandomMembers(key, count), typeReference);
    }

    /**
     * 迭代哈希表中的元素
     *
     * @param key     key
     * @param options 选项
     * @return {@link List}<{@link Object}>
     */
    public List<Object> sScan(String key, ScanOptions options) {
        Cursor<Object> cursor = redisTemplate.opsForSet().scan(key, options);;
        try {
            return cursor.stream().collect(Collectors.toList());
        }finally {
            if (!cursor.isClosed()) cursor.close();
        }
    }

    /**
     * 迭代哈希表中的元素
     *
     * @param key     key
     * @param tClass 对象类型
     * @param options 选项
     * @return {@link List}<{@link Object}>
     */
    public <T> List<T> sScan(String key, ScanOptions options, Class<T> tClass) {
       return BeanUtils.copyProperties(sScan(key, options), tClass);
    }

    /**
     * 迭代哈希表中的元素
     *
     * @param key     key
     * @param typeReference 对象类型
     * @param options 选项
     * @return {@link List}<{@link Object}>
     */
    public <T> List<T> sScan(String key, ScanOptions options, TypeReference<T> typeReference) {
        return BeanUtils.copyProperties(sScan(key, options), typeReference);
    }

    /*------------------zSet相关操作--------------------------------*/

    /**
     * 添加元素,有序集合是按照元素的score值由小到大排列
     *
     * @param key   key
     * @param value 值
     * @param score 分数
     * @return {@link Boolean}
     */
    public Boolean zAdd(String key, Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 添加元素,有序集合是按照元素的score值由小到大排列
     *
     * @param key    key
     * @param values 值
     * @return {@link Long}
     */
    public Long zAdd(String key, Set<TypedTuple<Object>> values) {
        return redisTemplate.opsForZSet().add(key, values);
    }

    /**
     * zSet删除元素
     *
     * @param key    key
     * @param values 值
     * @return {@link Long}
     */
    public Long zRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 增加元素的score值，并返回增加后的值
     *
     * @param key   key
     * @param value 值
     * @param delta δ
     * @return {@link Double}
     */
    public Double zIncrementScore(String key, Object value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
     *
     * @param key   key
     * @param value 值
     * @return 0表示第一位
     */
    public Long zRank(String key, Object value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 返回元素在集合的排名,按元素的score值由大到小排列
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public Long zReverseRank(String key, Object value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * 获取集合的元素, 从小到大排序
     *
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @param key   key
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 获取集合的元素, 从小到大排序
     *
     * @param start  开始位置
     * @param end    结束位置, -1查询所有
     * @param key    key
     * @param tClass 对象类型
     * @return {@link Set}<{@link T}>
     */
    public <T> Set<T> zRange(String key, long start, long end, Class<T> tClass) {
        return BeanUtils.zCopyProperties(zRange(key, start, end), tClass);
    }

    /**
     * 获取集合的元素, 从小到大排序
     *
     * @param start  开始位置
     * @param end    结束位置, -1查询所有
     * @param key    key
     * @param typeReference 对象类型
     * @return {@link Set}<{@link T}>
     */
    public <T> Set<T> zRange(String key, long start, long end, TypeReference<T> typeReference) {
        return BeanUtils.zCopyProperties(zRange(key, start, end), typeReference);
    }

    /**
     * 获取集合元素, 并且把score值也获取
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @return {@link Set}<{@link TypedTuple}<{@link Object}>>
     */
    public Set<TypedTuple<Object>> zRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 获取集合元素, 并且把score值也获取
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @param tClass 对象类型
     * @return {@link Set}<{@link TypedTuple}<{@link Object}>>
     */
    public <T> Set<TypedTuple<T>> zRangeWithScores(String key, long start, long end, Class<T> tClass) {
        Set<TypedTuple<Object>> typedTuples = zRangeWithScores(key, start, end);
        return BeanUtils.ztCopyProperties(typedTuples, tClass);
    }

    /**
     * 获取集合元素, 并且把score值也获取
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @param typeReference 对象类型
     * @return {@link Set}<{@link TypedTuple}<{@link Object}>>
     */
    public <T> Set<TypedTuple<T>> zRangeWithScores(String key, long start, long end, TypeReference<T> typeReference) {
        Set<TypedTuple<Object>> typedTuples = zRangeWithScores(key, start, end);
        return BeanUtils.ztCopyProperties(typedTuples, typeReference);
    }

    /**
     * 根据Score值查询集合元素
     *
     * @param min 最小值
     * @param max 最大值
     * @param key key
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> zRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 根据Score值查询集合元素
     *
     * @param min 最小值
     * @param max 最大值
     * @param key key
     * @param tClass 对象类型
     * @return {@link Set}<{@link T}>
     */
    public <T> Set<T> zRangeByScore(String key, double min, double max, Class<T> tClass) {
        return BeanUtils.sCopyProperties(zRangeByScore(key, min, max), tClass);
    }

    /**
     * 根据Score值查询集合元素
     *
     * @param min 最小值
     * @param max 最大值
     * @param key key
     * @param tClass 对象类型
     * @return {@link Set}<{@link T}>
     */
    public <T> Set<T> zRangeByScore(String key, double min, double max, TypeReference<T> typeReference) {
        return BeanUtils.sCopyProperties(zRangeByScore(key, min, max), typeReference);
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param min 最小值
     * @param max 最大值
     * @param key key
     * @return {@link Set}<{@link TypedTuple}<{@link Object}>>
     */
    public Set<TypedTuple<Object>> zRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param min 最小值
     * @param max 最大值
     * @param key key
     * @param tClass 对象类型
     * @return {@link Set}<{@link TypedTuple}<{@link T}>>
     */
    public <T> Set<TypedTuple<T>> zRangeByScoreWithScores(String key, double min, double max, Class<T> tClass) {
        return BeanUtils.ztCopyProperties(zRangeByScoreWithScores(key, min, max), tClass);
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param min 最小值
     * @param max 最大值
     * @param key key
     * @param tClass 对象类型
     * @return {@link Set}<{@link TypedTuple}<{@link T}>>
     */
    public <T> Set<TypedTuple<T>> zRangeByScoreWithScores(String key, double min, double max, TypeReference<T> typeReference) {
        return BeanUtils.ztCopyProperties(zRangeByScoreWithScores(key, min, max), typeReference);
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param key   key
     * @param min   最小值
     * @param max   最大值
     * @param start 开始
     * @param end   结束
     * @return {@link Set}<{@link TypedTuple}<{@link Object}>>
     */
    public Set<TypedTuple<Object>> zRangeByScoreWithScores(String key, double min, double max, long start, long end) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, start, end);
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param key   key
     * @param min   最小值
     * @param max   最大值
     * @param start 开始
     * @param end   结束
     * @param tClass 对象类型
     * @return {@link Set}<{@link TypedTuple}<{@link T}>>
     */
    public <T> Set<TypedTuple<T>> zRangeByScoreWithScores(String key, double min, double max, long start, long end, Class<T> tClass) {
        return BeanUtils.ztCopyProperties(zRangeByScoreWithScores(key, min, max, start, end), tClass);
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param key   key
     * @param min   最小值
     * @param max   最大值
     * @param start 开始
     * @param end   结束
     * @param typeReference 对象类型
     * @return {@link Set}<{@link TypedTuple}<{@link T}>>
     */
    public <T> Set<TypedTuple<T>> zRangeByScoreWithScores(String key, double min, double max, long start, long end, TypeReference<T> typeReference) {
        return BeanUtils.ztCopyProperties(zRangeByScoreWithScores(key, min, max, start, end), typeReference);
    }

    /**
     * 获取集合的元素, 从大到小排序
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> zReverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @param tClass 对象类型
     * @return {@link Set}<{@link T}>
     */
    public <T> Set<T> zReverseRange(String key, long start, long end, Class<T> tClass) {
        return BeanUtils.zCopyProperties(zReverseRange(key, start, end), tClass);
    }

    /**
     * 获取集合的元素, 从大到小排序
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @param typeReference 对象类型
     * @return {@link Set}<{@link T}>
     */
    public <T> Set<T> zReverseRange(String key, long start, long end, TypeReference<T> typeReference) {
        return BeanUtils.zCopyProperties(zReverseRange(key, start, end), typeReference);
    }

    /**
     * 获取集合的元素, 从大到小排序, 并返回score值
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @return {@link Set}<{@link TypedTuple}<{@link Object}>>
     */
    public Set<TypedTuple<Object>> zReverseRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序, 并返回score值
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @param tClass 对象类型
     * @return {@link Set}<{@link TypedTuple}<{@link T}>>
     */
    public <T> Set<TypedTuple<T>> zReverseRangeWithScores(String key, long start, long end, Class<T> tClass) {
        return BeanUtils.ztCopyProperties(zReverseRangeWithScores(key, start, end), tClass);
    }

    /**
     * 获取集合的元素, 从大到小排序, 并返回score值
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @param typeReference 对象类型
     * @return {@link Set}<{@link TypedTuple}<{@link T}>>
     */
    public <T> Set<TypedTuple<T>> zReverseRangeWithScores(String key, long start, long end, TypeReference<T> typeReference) {
        return BeanUtils.ztCopyProperties(zReverseRangeWithScores(key, start, end), typeReference);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> zReverseRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @param tClass 对象类型
     * @return {@link Set}<{@link T}>
     */
    public <T> Set<T> zReverseRangeByScore(String key, double min, double max, Class<T> tClass) {
        return BeanUtils.zCopyProperties(zReverseRangeByScore(key, min, max), tClass);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @param typeReference 对象类型
     * @return {@link Set}<{@link T}>
     */
    public <T> Set<T> zReverseRangeByScore(String key, double min, double max, TypeReference<T> typeReference) {
        return BeanUtils.zCopyProperties(zReverseRangeByScore(key, min, max), typeReference);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return {@link Set}<{@link TypedTuple}<{@link Object}>>
     */
    public Set<TypedTuple<Object>> zReverseRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @param tClass 对象类型
     * @return {@link Set}<{@link TypedTuple}<{@link T}>>
     */
    public <T> Set<TypedTuple<T>> zReverseRangeByScoreWithScores(String key, double min, double max, Class<T> tClass) {
        return BeanUtils.ztCopyProperties(zReverseRangeByScoreWithScores(key, min, max), tClass);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @param typeReference 对象类型
     * @return {@link Set}<{@link TypedTuple}<{@link T}>>
     */
    public <T> Set<TypedTuple<T>> zReverseRangeByScoreWithScores(String key, double min, double max, TypeReference<T> typeReference) {
        return BeanUtils.ztCopyProperties(zReverseRangeByScoreWithScores(key, min, max), typeReference);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key   key
     * @param min   最小值
     * @param max   最大值
     * @param start 开始
     * @param end   结束
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> zReverseRangeByScore(String key, double min, double max, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, start, end);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key   key
     * @param min   最小值
     * @param max   最大值
     * @param start 开始
     * @param end   结束
     * @param tClass 对象类型
     * @return {@link Set}<{@link T}>
     */
    public <T> Set<T> zReverseRangeByScore(String key, double min, double max, long start, long end, Class<T> tClass) {
        return BeanUtils.zCopyProperties(zReverseRangeByScore(key, min, max, start, end), tClass);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key   key
     * @param min   最小值
     * @param max   最大值
     * @param start 开始
     * @param end   结束
     * @param typeReference 对象类型
     * @return {@link Set}<{@link T}>
     */
    public <T> Set<T> zReverseRangeByScore(String key, double min, double max, long start, long end, TypeReference<T> typeReference) {
        return BeanUtils.zCopyProperties(zReverseRangeByScore(key, min, max, start, end), typeReference);
    }

    /**
     * 根据score值获取集合元素数量
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return {@link Long}
     */
    public Long zCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 获取集合大小
     *
     * @param key key
     * @return {@link Long}
     */
    public Long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取集合大小
     *
     * @param key key
     * @return {@link Long}
     */
    public Long zZCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 获取集合中value元素的score值
     *
     * @param key   key
     * @param value 值
     * @return {@link Double}
     */
    public Double zScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 移除指定索引位置的成员
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @return {@link Long}
     */
    public Long zRemoveRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 根据指定的score值的范围来移除成员
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return {@link Long}
     */
    public Long zRemoveRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     *
     * @param key      key
     * @param otherKey 其他key
     * @param destKey  目标key
     * @return {@link Long}
     */
    public Long zUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     *
     * @param key       key
     * @param otherKeys 其他keys
     * @param destKey   目标key
     * @return {@link Long}
     */
    public Long zUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取key和otherKey的交集并存储在destKey中
     *
     * @param key      key
     * @param otherKey 其他key
     * @param destKey  目标key
     * @return {@link Long}
     */
    public Long zIntersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * 获取key和otherKey的交集并存储在destKey中
     *
     * @param key       key
     * @param otherKeys 其他keys
     * @param destKey   目标key
     * @return {@link Long}
     */
    public Long zIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * 迭代哈希表中的元素
     *
     * @param key     key
     * @param options 选项
     * @return {@link Cursor}<{@link TypedTuple}<{@link Object}>>
     */
    public List<TypedTuple<Object>> zScan(String key, ScanOptions options) {
        Cursor<TypedTuple<Object>> cursor = redisTemplate.opsForZSet().scan(key, options);
        try {
            return cursor.stream().collect(Collectors.toList());
        }finally {
            if (!cursor.isClosed()) cursor.isClosed();
        }
    }

    /**
     * 迭代哈希表中的元素
     *
     * @param key     key
     * @param options 选项
     * @param tClass 对象类型
     * @return {@link Cursor}<{@link TypedTuple}<{@link T}>>
     */
    public <T> List<TypedTuple<T>> zScan(String key, ScanOptions options, Class<T> tClass) {
        return BeanUtils.atCopyProperties(zScan(key, options), tClass);
    }

    /**
     * 迭代哈希表中的元素
     *
     * @param key     key
     * @param options 选项
     * @param typeReference 对象类型
     * @return {@link Cursor}<{@link TypedTuple}<{@link T}>>
     */
    public <T> List<TypedTuple<T>> zScan(String key, ScanOptions options, TypeReference<T> typeReference) {
        return BeanUtils.atCopyProperties(zScan(key, options), typeReference);
    }







}