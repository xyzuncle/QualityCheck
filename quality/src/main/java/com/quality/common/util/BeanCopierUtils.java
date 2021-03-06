package com.quality.common.util;

import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;

public class BeanCopierUtils {
    public static Map<String,BeanCopier> beanCopierMap = new HashMap<String,BeanCopier>();

    public static void copyProperties(Object source, Object target){
        String beanKey =  generateKey(source.getClass(), target.getClass());
        BeanCopier copier =  null;
        if(!beanCopierMap.containsKey(beanKey)){
            copier = BeanCopier.create(source.getClass(), target.getClass(), true);
            beanCopierMap.put(beanKey, copier);
        }else{
            copier = beanCopierMap.get(beanKey);
        }
        copier.copy(source, target, new DateConverterBeanCopier());
    }
    private static String generateKey(Class<?> class1,Class<?>class2){
        return class1.toString() + class2.toString();
    }
}
