package com.example.demo.entity.schema;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 *
 */
@Data
public class Schema {
    @JSONField(name = "version")
    Integer version;

    @JSONField(name = "block")
    SchemaBlock block;
}
