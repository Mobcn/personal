package com.mobingc.personal.common;

import com.mobingc.personal.base.BaseValidator;
import com.mobingc.personal.common.annotation.Validator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 校验器集
 *
 * @author Mo
 * @since 2022-08-25
 */
public class Validators {

    /**
     * 校验器包路径
     */
    private final static String VALIDATOR_PACKAGE = "com.mobingc.personal.validator";

    /**
     * 校验器集
     */
    private final static Map<Class<?>, Map<String, BaseValidator<?>>> validatorMap = new ConcurrentHashMap<>();

    /**
     * 校验
     *
     * @param entity 校验对象
     */
    public static <T> void validate(T entity) {
        Validators.validate(entity, Validator.DEFAULT_VALIDATOR_NAME);
    }

    /**
     * 校验
     *
     * @param entity        校验对象
     * @param validatorName 校验器名称
     */
    public static <T> void validate(T entity, String validatorName) {
        // noinspection unchecked
        BaseValidator<T> validator = (BaseValidator<T>) Validators.select(entity.getClass(), validatorName);
        validator.validate(entity);
    }

    /**
     * 选择校验器
     *
     * @param clazz 校验类对象
     * @return 校验器
     */
    public static BaseValidator<?> select(Class<?> clazz) {
        return Validators.select(clazz, Validator.DEFAULT_VALIDATOR_NAME);
    }

    /**
     * 选择校验器
     *
     * @param clazz         校验类对象
     * @param validatorName 校验器名称
     * @return 校验器
     */
    public static BaseValidator<?> select(Class<?> clazz, String validatorName) {
        Map<String, BaseValidator<?>> validators = validatorMap.computeIfAbsent(clazz, key -> new ConcurrentHashMap<>());
        BaseValidator<?> validator = validators.get(validatorName);
        if (validator == null) {
            validator = Validators.createValidate(clazz, validatorName);
            if (validator == null) {
                throw new RuntimeException("校验器不存在!");
            }
            validators.put(validatorName, validator);
        }
        return validator;
    }

    /**
     * 创建校验器对象
     *
     * @param clazz         校验类对象
     * @param validatorName 校验器名称
     * @return 校验器
     */
    private static BaseValidator<?> createValidate(Class<?> clazz, String validatorName) {
        // spring工具类，可以获取指定路径下的全部类
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(Validators.VALIDATOR_PACKAGE) + "/**/*.class";
        try {
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            // MetadataReader 的工厂类
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource : resources) {
                // 用于读取类信息
                MetadataReader reader = readerFactory.getMetadataReader(resource);
                // 扫描到的class
                String className = reader.getClassMetadata().getClassName();
                Class<?> eClazz = Class.forName(className);
                // 判断是否有指定注解
                Validator annotation = eClazz.getAnnotation(Validator.class);
                if (annotation != null) {
                    // 判断校验器名称是否匹配
                    if (annotation.name().equals(validatorName)) {
                        // 获取泛型类对象
                        ParameterizedType pt = (ParameterizedType) eClazz.getGenericInterfaces()[0];
                        Class<?> genericClass = (Class<?>) pt.getActualTypeArguments()[0];
                        // 判断校验类对象是否匹配
                        if (genericClass != null && genericClass.equals(clazz)) {
                            return (BaseValidator<?>) eClazz.newInstance();
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("创建校验器对象出错!", e);
        }
        return null;
    }

}
