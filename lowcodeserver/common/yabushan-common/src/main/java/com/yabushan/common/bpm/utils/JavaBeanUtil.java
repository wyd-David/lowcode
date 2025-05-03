package com.yabushan.common.bpm.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yabushan.common.utils.bean.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Lanjin
 * @date 2019/3/6 11:20
 */
public class JavaBeanUtil {
    public static Map<String, Object> convertBeanToMap(Object bean) throws  Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (bean != null) {
            Class type = bean.getClass();
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (result != null) {
                        returnMap.put(propertyName, result);
                    } else {
                        returnMap.put(propertyName, "");
                    }
                }
            }
        }
        return returnMap;
    }


    /**
     * 忽略空为null
     * @param bean
     * @return
     * @throws Exception
     */
    public static Map<String, Object> convertBeanToMapIngoreNull(Object bean) throws  Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (bean != null) {
            Class type = bean.getClass();
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (result != null) {
                        returnMap.put(propertyName, result);
                    } else {
                        returnMap.put(propertyName, null);
                    }
                }
            }
        }
        return returnMap;
    }

    /**
     * 过滤掉我们不需要的属性
     * 转换属性只能是
     * @param bean
     * @param filters
     * @return
     * @throws Exception
     */
    public static Map<String, Object> convertBeanToMap(Object bean, List<String> filters) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (Objects.equals(null, bean)) {
            return returnMap;
        }
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class") && filters.contains(propertyName)) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }

        return returnMap;
    }


    /**
     * 将对象转换为json格式字符串
     *
     * @param
     * @return json string
     */
    public static String toJSON(Object obj) {
        ObjectMapper om = new ObjectMapper();
        try {
            String json = om.writeValueAsString(obj);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static Logger LOGGER = LoggerFactory.getLogger(BeanUtils.class);

    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                    return map;
                } else {
                    transBean2Map(property.getName().getBytes());
                }

            }
        } catch (Exception e) {
            LOGGER.error("transBean2Map Error {}", e);
        }
        return map;

    }

    //把Map转化为JavaBean
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clz) throws Exception {
        //创建一个需要转换为的类型的对象
        T obj = clz.newInstance();
        //从Map中获取和属性名称一样的值，把值设置给对象(setter方法)

        //得到属性的描述器
        BeanInfo b = Introspector.getBeanInfo(clz, Object.class);
        PropertyDescriptor[] pds = b.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            //得到属性的setter方法
            Method setter = pd.getWriteMethod();
            //得到key名字和属性名字相同的value设置给属性
            setter.invoke(obj, map.get(pd.getName()));
        }
        return obj;
    }


    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


    public static String[] getNotNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue != null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 属性拷贝(将targer 的值复制到src)，忽略src为null的字段
     *
     * @param src
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(target,src, getNullPropertyNames(src));
    }


   /**
   *@MethodName copyPropertiesIgnoreNull
   *@Desc 将targer 的值复制到src 并且拷贝忽略src自己存在值的，并且忽略自己添加的属性
   *@Author YvonneLee
   *@Param [src, target, ignoreProperties]
   *@return void
   *@Date 2020/8/27 17:10
   **/
    public static void copyPropertiesIgnoreNull(Object src, Object target,String[] ignoreProperties) {
        String[] both =  ArrayUtils.addAll(ignoreProperties,getNotNullPropertyNames(src));
        org.springframework.beans.BeanUtils.copyProperties(target, src,both);
    }

    /**
    *@MethodName copyProperties
    *@Desc 赋值（将targer 的值复制到src）并且包括不为空、为空 的属性字段（整个复制）
    *@Author YvonneLee
    *@Param [src, target, ignoreProperties]
    *@return void
    *@Date 2021/1/26 15:54
    **/
    public static void copyProperties(Object src, Object target,String[] ignoreProperties){
        org.springframework.beans.BeanUtils.copyProperties(target, src,ignoreProperties);
    }

}