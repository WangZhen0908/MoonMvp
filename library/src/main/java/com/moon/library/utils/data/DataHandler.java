package com.moon.library.utils.data;

public interface DataHandler {

    //1、增删改查 接口
    //2、单个字段、实体类、集合
    //3、数据加密

    // key-value 数据，使用时会更改原来的
    // 普通缓存数据
    void save(String key, String value);

}
