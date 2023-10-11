package com.example.demo.entity.plan;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * github.com/hashicorp/terraform-json
 * <p>
 * // ExpressionData describes the format for an individual key in a
 * // Terraform configuration.
 * type ExpressionData struct {
 * // If the *entire* expression is a constant-defined value, this
 * // will contain the Go representation of the expression's data.
 * //
 * // Note that a nil here denotes and explicit null. When a value is
 * // unknown on part of the value coming from an expression that
 * // cannot be resolved at parse time, this field will contain
 * // UnknownConstantValue.
 * ConstantValue interface{} `json:"constant_value,omitempty"`
 * <p>
 * // If any part of the expression contained values that were not
 * // able to be resolved at parse-time, this will contain a list of
 * // the referenced identifiers that caused the value to be unknown.
 * References []string `json:"references,omitempty"`
 * <p>
 * // A list of complex objects that were nested in this expression.
 * // If this value is a nested block in configuration, sometimes
 * // referred to as a "sub-resource", this field will contain those
 * // values, and ConstantValue and References will be blank.
 * NestedBlocks []map[string]*Expression `json:"-"`
 * }
 */
@Data
public class Expression {
    // If the *entire* expression is a constant-defined value, this
    // will contain the Go representation of the expression's data.
    //
    // Note that a nil here denotes and explicit null. When a value is
    // unknown on part of the value coming from an expression that
    // cannot be resolved at parse time, this field will contain
    // UnknownConstantValue.
    @JSONField(name = "constant_value")
    private Object constantValue;

    // If any part of the expression contained values that were not
    // able to be resolved at parse-time, this will contain a list of
    // the referenced identifiers that caused the value to be unknown.
    @JSONField(name = "references")
    private List<String> references;

}
