package com.example.demo.entity.plan;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * github.com/hashicorp/terraform-json
 * <p>
 * type PlanVariable struct {
 * // The value for this variable at plan time.
 * Value interface{} `json:"value,omitempty"`
 * }
 */
@Data
public class PlanVariable {
    @JSONField(name = "value")
    private Object value;
}
