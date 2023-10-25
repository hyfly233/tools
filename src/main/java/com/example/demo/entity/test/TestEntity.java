package com.example.demo.entity.test;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("test")
public class TestEntity {

    private String id;

    private String name;

    private Boolean hasErr;

    private String errMsg;
}
