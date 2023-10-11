package com.example.demo.entity.plan;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * github.com/hashicorp/terraform-json
 * <p>
 * // Importing is a nested object for the resource import metadata.
 * type Importing struct {
 * // The original ID of this resource used to target it as part of planned
 * // import operation.
 * ID string `json:"id,omitempty"`
 * }
 */
@Data
public class Importing {

    // The original ID of this resource used to target it as part of planned
    // import operation.
    @JSONField(name = "id")
    private String id;
}
