package com.example.demo.entity;


import com.alibaba.fastjson2.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Event {
    private Date createTime;

    private String eventType;

    private String desc;

    private String templateResourceName; //模板资源名称 rf_doc_ecs

    private String resourceItemName; //资源项名称 huaweicloud_compute_instance

    private String resourceId;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
