package com.example.demo.entity;


import com.alibaba.fastjson2.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Output {
    private String name;
    private String type;
    private String value;
    private String desc;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
