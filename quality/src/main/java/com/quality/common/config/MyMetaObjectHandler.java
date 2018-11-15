package com.quality.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;


public class MyMetaObjectHandler  implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        Object creatTime = getFieldValByName("crtTime",metaObject);
        if(creatTime==null){
            setFieldValByName("crtTime", new Date(System.currentTimeMillis()), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //logger.info("更新的时候干点不可描述的事情");
    }
}
