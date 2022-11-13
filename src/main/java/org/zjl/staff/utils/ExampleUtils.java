package org.zjl.staff.utils;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;

@Slf4j
public class ExampleUtils {
    public static Example getExample(Object object) {
        Example example = new Example(object.getClass());
        try {
            build(object, example);
            example.orderBy("id").desc();
            return example;
        } catch (IllegalAccessException e) {
            log.error("reflect error",e);
        }
        return example;
    }
    public static Example getExampleV2(Object object) {
        Example example = new Example(object.getClass());
        try {
            build(object, example);
            example.orderBy("settlementTime").desc().orderBy("id").asc();
            return example;
        } catch (IllegalAccessException e) {
            log.error("reflect error",e);
        }
        return example;
    }

    private static void build(Object object, Example example) throws IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        Example.Criteria criteria = example.createCriteria();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(object);
            if (value != null) {
                if(value instanceof String){
                    if(value!=""){
                        criteria=criteria.andEqualTo(field.getName(),value);
                    }
                }else{
                    criteria=criteria.andEqualTo(field.getName(),value);
                }

            }
        }
    }
}
