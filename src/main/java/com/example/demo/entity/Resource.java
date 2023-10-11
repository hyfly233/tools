package com.example.demo.entity;


import com.alibaba.fastjson2.JSON;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class Resource {
    private String id;
    private String name; //资源名字 rf_teststack_ecs
    private String templateResourceName; //模板资源名称 rf_doc_ecs  resource_name
    private String resourceItemName; //资源项名称 huaweicloud_compute_instance  resource_type
    private String state;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
