package cn.darkjrong.redis;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONValidator;
import com.alibaba.fastjson.TypeReference;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 对象操作 工具类
 *
 * @author Rong.Jia
 * @date 2023/01/25
 */
@SuppressWarnings("ALL")
public class BeanUtils {

    /**
     * 复制属性
     *
     * @param objects 原对象
     * @param tClass  目标对象类型
     * @param <T>     目标对象泛型
     * @return {@link List}<{@link T}> 目标对象集合
     */
    public static <T> Set<T> sCopyProperties(Collection<Object> objects, Class<T> tClass) {
        if (CollectionUtil.isNotEmpty(objects)) {
            return objects.stream().map(a -> copyProperties(a, tClass)).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    /**
     * 复制属性
     *
     * @param objects        原对象
     * @param tTypeReference 目标对象类型
     * @param <T>            目标对象泛型
     * @return {@link Set}<{@link T}> 目标对象集合
     */
    public static <T> Set<T> sCopyProperties(Collection<Object> objects, TypeReference<T> tTypeReference) {
        if (CollectionUtil.isNotEmpty(objects)) {
            return objects.stream().map(a -> copyProperties(a, tTypeReference)).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    /**
     * 复制属性
     *
     * @param object 原对象
     * @param tClass 目标对象类型
     * @param <T>    目标对象泛型
     * @return {@link T} 目标对象
     */
    public static <T> T copyProperties(Object object, Class<T> tClass) {
        if (ObjectUtil.isNotNull(object)) {
            String json = JSONValidator.from(object.toString()).validate() ? object.toString() : JSON.toJSONString(object);
            return JSON.parseObject(json, tClass);
        }
        return null;
    }

    /**
     * 复制属性
     *
     * @param object         原对象
     * @param tTypeReference 目标对象类型
     * @param <T>            目标对象泛型
     * @return {@link T} 目标对象
     */
    public static <T> T copyProperties(Object object, TypeReference<T> tTypeReference) {
        if (ObjectUtil.isNotNull(object)) {
            String json = JSONValidator.from(object.toString()).validate() ? object.toString() : JSON.toJSONString(object);
            return JSON.parseObject(json, tTypeReference);
        }
        return null;
    }

    /**
     * 复制属性
     *
     * @param objects 原对象
     * @param tClass  目标对象类型
     * @param <T>     目标对象泛型
     * @return {@link List}<{@link T}> 目标对象集合
     */
    public static <T> List<T> copyProperties(Collection<Object> objects, Class<T> tClass) {
        if (CollectionUtil.isNotEmpty(objects)) {
            return objects.stream().map(a -> copyProperties(a, tClass)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 复制属性
     *
     * @param objects        原对象
     * @param tTypeReference 目标对象类型
     * @param <T>            目标对象泛型
     * @return {@link List}<{@link T}> 目标对象集合
     */
    public static <T> List<T> copyProperties(Collection<Object> objects, TypeReference<T> tTypeReference) {
        if (CollectionUtil.isNotEmpty(objects)) {
            return objects.stream().map(a -> copyProperties(a, tTypeReference)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 复制属性
     *
     * @param objects    原对象
     * @param keyClass   key类型
     * @param valueClass value类型
     * @return {@link Map}<{@link K}, {@link V}>
     */
    public static <K, V> Map<K, V> copyProperties(Map<Object, Object> objects, Class<K> keyClass, Class<V> valueClass) {
        if (CollectionUtil.isNotEmpty(objects)) {
            return objects.entrySet().stream()
                    .filter(a -> ObjectUtil.isAllNotEmpty(a.getKey(), a.getValue()))
                    .collect(Collectors.toMap(k -> copyProperties(k.getKey(), keyClass), v -> copyProperties(v.getValue(), valueClass)));
        }
        return Collections.emptyMap();
    }

    /**
     * 复制属性
     *
     * @param objects    原对象
     * @param keyClass   key类型
     * @param valueClass value类型
     * @return {@link Map}<{@link K}, {@link V}>
     */
    public static <K, V> Map<K, V> copyProperties(Map<Object, Object> objects, TypeReference<K> keyClass, TypeReference<V> valueClass) {
        if (CollectionUtil.isNotEmpty(objects)) {
            return objects.entrySet().stream()
                    .filter(a -> ObjectUtil.isAllNotEmpty(a.getKey(), a.getValue()))
                    .collect(Collectors.toMap(k -> copyProperties(k.getKey(), keyClass), v -> copyProperties(v.getValue(), valueClass)));
        }
        return Collections.emptyMap();
    }

    /**
     * 复制属性
     *
     * @param typedTuples 输入元组
     * @param tClass      对象类型
     * @return {@link Set}<{@link ZSetOperations.TypedTuple}<{@link T}>>
     */
    public static <T> Set<ZSetOperations.TypedTuple<T>> ztCopyProperties(Collection<ZSetOperations.TypedTuple<Object>> typedTuples, Class<T> tClass) {
        if (CollectionUtil.isNotEmpty(typedTuples)) {
            return typedTuples.stream()
                    .map(a -> ZSetOperations.TypedTuple.of(BeanUtils.copyProperties(a.getValue(), tClass), a.getScore()))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return Collections.emptySet();
    }

    /**
     * 复制属性
     *
     * @param typedTuples 输入元组
     * @param typeReference      对象类型
     * @return {@link Set}<{@link ZSetOperations.TypedTuple}<{@link T}>>
     */
    public static <T> Set<ZSetOperations.TypedTuple<T>> ztCopyProperties(Collection<ZSetOperations.TypedTuple<Object>> typedTuples, TypeReference<T> typeReference) {
        if (CollectionUtil.isNotEmpty(typedTuples)) {
            return typedTuples.stream()
                    .map(a -> ZSetOperations.TypedTuple.of(BeanUtils.copyProperties(a.getValue(), typeReference), a.getScore()))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return Collections.emptySet();
    }

    /**
     * 复制属性
     *
     * @param typedTuples 输入元组
     * @param typeReference      对象类型
     * @return {@link List}<{@link ZSetOperations.TypedTuple}<{@link T}>>
     */
    public static <T> List<ZSetOperations.TypedTuple<T>> atCopyProperties(Collection<ZSetOperations.TypedTuple<Object>> typedTuples, TypeReference<T> typeReference) {
        if (CollectionUtil.isNotEmpty(typedTuples)) {
            return typedTuples.stream()
                    .map(a -> ZSetOperations.TypedTuple.of(BeanUtils.copyProperties(a.getValue(), typeReference), a.getScore()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 复制属性
     *
     * @param typedTuples 输入元组
     * @param tClass      对象类型
     * @return {@link List}<{@link ZSetOperations.TypedTuple}<{@link T}>>
     */
    public static <T> List<ZSetOperations.TypedTuple<T>> atCopyProperties(Collection<ZSetOperations.TypedTuple<Object>> typedTuples, Class<T> tClass) {
        if (CollectionUtil.isNotEmpty(typedTuples)) {
            return typedTuples.stream()
                    .map(a -> ZSetOperations.TypedTuple.of(BeanUtils.copyProperties(a.getValue(), tClass), a.getScore()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 复制属性
     *
     * @param objects 原对象
     * @param tClass  目标对象类型
     * @param <T>     目标对象泛型
     * @return {@link List}<{@link T}> 目标对象集合
     */
    public static <T> Set<T> zCopyProperties(Collection<Object> objects, Class<T> tClass) {
        if (CollectionUtil.isNotEmpty(objects)) {
            return objects.stream().map(a -> copyProperties(a, tClass)).collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return Collections.emptySet();
    }

    /**
     * 复制属性
     *
     * @param objects        原对象
     * @param tTypeReference 目标对象类型
     * @param <T>            目标对象泛型
     * @return {@link Set}<{@link T}> 目标对象集合
     */
    public static <T> Set<T> zCopyProperties(Collection<Object> objects, TypeReference<T> tTypeReference) {
        if (CollectionUtil.isNotEmpty(objects)) {
            return objects.stream().map(a -> copyProperties(a, tTypeReference)).collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return Collections.emptySet();
    }


















}
