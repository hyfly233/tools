package com.example.demo.entity.plan;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * github.com/hashicorp/terraform-json
 * <p>
 * // ConfigVariable defines a variable as defined in configuration.
 * type ConfigVariable struct {
 * // The defined default value of the variable.
 * Default interface{} `json:"default,omitempty"`
 * <p>
 * // The defined text description of the variable.
 * Description string `json:"description,omitempty"`
 * <p>
 * // Whether the variable is marked as sensitive
 * Sensitive bool `json:"sensitive,omitempty"`
 * }
 */
@Data
public class ConfigVariable {

    // The defined default value of the variable.
    @JSONField(name = "default")
    private Object defaultValue;

    // The defined text description of the variable.
    @JSONField(name = "description")
    private String description;

    // Whether the variable is marked as sensitive
    @JSONField(name = "sensitive")
    private boolean sensitive;
}
