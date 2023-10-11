package com.example.demo.entity.plan;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * github.com/hashicorp/terraform-json
 * <p>
 * // ConfigModule describes a module in Terraform configuration.
 * type ConfigModule struct {
 * // The outputs defined in the module.
 * Outputs map[string]*ConfigOutput `json:"outputs,omitempty"`
 * <p>
 * // The resources defined in the module.
 * Resources []*ConfigResource `json:"resources,omitempty"`
 * <p>
 * // Any "module" stanzas within the specific module.
 * ModuleCalls map[string]*ModuleCall `json:"module_calls,omitempty"`
 * <p>
 * // The variables defined in the module.
 * Variables map[string]*ConfigVariable `json:"variables,omitempty"`
 * }
 */
@Data
public class ConfigModule {

    // The outputs defined in the module.
    @JSONField(name = "outputs")
    private Map<String, Object> outputs;

    // The resources defined in the module.
    @JSONField(name = "resources")
    private List<Object> resources;

    // The variables defined in the module.
    @JSONField(name = "variables")
    private Map<String, ConfigVariable> variables;
}
