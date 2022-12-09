package com.onefly.united.common.utils;

import com.onefly.united.common.service.IAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 转换工具类
 *
 * @author Mark Rundon
 */
@Slf4j
public class ConvertUtils {

    /**
     * 使用缓存提高效率
     */
    private static final ConcurrentHashMap<String, BeanCopier> mapCaches = new ConcurrentHashMap<>();

    public static <O, T> T sourceToTarget(O source, Class<T> target) {
        T instance = baseMapper(source, target);
        return instance;
    }

    /**
     * 复制到指定类型的对象 lambda
     *
     * @param <O>
     * @param <T>
     * @param source
     * @param target
     * @param action
     * @return
     */
    public static <O, T> T sourceToTarget(O source, Class<T> target, IAction<T> action) {
        T instance = baseMapper(source, target);
        action.run(instance);
        return instance;
    }


    /**
     * 根据传进来的对象类型生成新的对象并复制
     *
     * @param <O>
     * @param <T>
     * @param source
     * @param target
     * @return
     */
    private static <O, T> T baseMapper(O source, Class<T> target) {
        String baseKey = generateKey(source.getClass(), target);
        BeanCopier copier;
        if (!mapCaches.containsKey(baseKey)) {
            copier = BeanCopier.create(source.getClass(), target, false);
            mapCaches.put(baseKey, copier);
        } else {
            copier = mapCaches.get(baseKey);
        }
        T instance = null;
        try {
            instance = target.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("mapper 创建对象异常" + e.getMessage());
        }
        copier.copy(source, instance, null);
        return instance;
    }

    /**
     * 生成key
     *
     * @param class1
     * @param class2
     * @return
     */
    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }


    public static <T> List<T> sourceToTarget(Collection<?> sourceList, Class<T> target) {
        if (sourceList == null) {
            return null;
        }
        List targetList = new ArrayList<>(sourceList.size());
        try {
            for (Object source : sourceList) {
                T instance = baseMapper(source, target);
                targetList.add(instance);
            }
        } catch (Exception e) {
            log.error("convert error ", e);
        }

        return targetList;
    }
}