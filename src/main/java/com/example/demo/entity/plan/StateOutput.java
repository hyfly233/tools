package com.example.demo.entity.plan;


import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * github.com/hashicorp/terraform-json
 * <p>
 * // StateOutput represents an output value in a common state
 * // representation.
 * type StateOutput struct {
 * // Whether or not the output was marked as sensitive.
 * Sensitive bool `json:"sensitive"`
 * <p>
 * // The value of the output.
 * Value interface{} `json:"value,omitempty"`
 * <p>
 * // The type of the output.
 * Type cty.Type `json:"type,omitempty"`
 * }
 */
@Data
public class StateOutput {

    // Whether or not the output was marked as sensitive.
    @JSONField(name = "sensitive")
    private boolean sensitive;

    // The value of the output.
    @JSONField(name = "value")
    private Object value;

    // The type of the output.
    @JSONField(name = "type")
    private String type;
}
