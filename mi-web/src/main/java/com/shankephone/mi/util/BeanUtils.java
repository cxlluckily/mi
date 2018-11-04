package com.shankephone.mi.util;


import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;


/**
 * Bean操作类
 *
 * @author 司徒彬
 * @date 2017年1月12日10:54:59
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils
{

    /**
     * Deep clone t.
     * <p>
     * 深度拷贝注意，如果obj 实现 Serializable 接口 或者 非JavaBean 则不支持深度拷贝，会返回传入的obj
     *
     * @param <T> the type parameter
     * @param obj the obj
     * @return the t
     */
    public static <T> T deepCopy(Object obj)
    {
        try
        {
            //将对象写到流里
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            //从流里读出来
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (T) (objectInputStream.readObject());
        }
        catch (NotSerializableException ex)
        {
            try
            {
                return copyBean((T) obj);
            }
            catch (Exception e)
            {
                return (T) obj;
            }

        }
        catch (Exception ex)
        {
            return (T) obj;
        }
    }

    /**
     * Clone bean t.
     *
     * @param <T> the type parameter
     * @param obj the obj
     * @return the t
     * @throws Exception the exception
     */
    public static final <T> T copyBean(T obj) throws Exception
    {
        try
        {
            return copyBean(obj, (Class<T>) obj.getClass());
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    /**
     * 通过反射将指定的Bean复制到泛型的实体中，源实体中没有的属性将为null，并且只有属性名称以及属性类型相同的字段才会复制
     *
     * @param <T>          the type parameter
     * @param sourceEntity the obj 复制源实体
     * @param targetEntity the t 目标实体
     * @param isCover      the is cover 是否覆盖，如果是false，则目标实体中有值的属性不会被赋值
     * @return the t
     * @throws IllegalAccessException the illegal access exception
     * @throws InstantiationException the instantiation exception
     */
    public static <T> T copyBean(Object sourceEntity, T targetEntity, boolean isCover)
    {
        try
        {
            //把目标实体的继承实体中的属性，与目标实体中的属性合并成一个list
            Field[] targetFields = ReflectionUtils.getFields(targetEntity.getClass());// Arrays.asList(targetEntity.getClass().getDeclaredFields());


            //把源实体的继承实体中的属性，与源实体中的属性合并成一个list
            Field[] fieldsTemp = ReflectionUtils.getFields(sourceEntity.getClass());// Arrays.asList(sourceEntity.getClass().getDeclaredFields());


            Arrays.stream(targetFields).forEach(targetField -> {
                Optional<Field> fieldStreamOptional =
                        Arrays.stream(fieldsTemp).filter(field -> field.getName().equals(targetField.getName()) && field.getType().equals(targetField.getType())).findFirst();
                if (!fieldStreamOptional.equals(Optional.empty()))
                {
                    Field field = fieldStreamOptional.get();
                    boolean isFinal = ReflectionUtils.isFinal(field);
                    if (!isFinal)
                    {
                        Object targetValue = ReflectionUtils.getFieldValue(targetEntity, field.getName());
                        if (isCover || (isCover && targetValue == null))
                        {
                            Object value = ReflectionUtils.getFieldValue(sourceEntity, field.getName());
                            ReflectionUtils.setFieldValue(targetEntity, field.getName(), value);
                        }
                    }

                }
            });
            return targetEntity;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 通过反射将指定的Bean复制到泛型的实体中,全部覆盖
     *
     * @param <T>          the type parameter
     * @param sourceEntity the obj
     * @param clazz        the clazz
     * @return the t
     * @throws IllegalAccessException the illegal access exception
     * @throws InstantiationException the instantiation exception
     */
    public static <T> T copyBean(Object sourceEntity, Class<T> clazz) throws IllegalAccessException, InstantiationException
    {
        try
        {
            T targetEntity = clazz.newInstance();
            return copyBean(sourceEntity, targetEntity, true);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

}
