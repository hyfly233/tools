package com.example.demo.entity.plan;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.Map;

/**
 * github.com/hashicorp/terraform-json
 * <p>
 * // ProviderConfig describes a provider configuration instance.
 * type ProviderConfig struct {
 * // The name of the provider, ie: "aws".
 * Name string `json:"name,omitempty"`
 * <p>
 * // The fully-specified name of the provider, ie: "registry.terraform.io/hashicorp/aws".
 * FullName string `json:"full_name,omitempty"`
 * <p>
 * // The alias of the provider, ie: "us-east-1".
 * Alias string `json:"alias,omitempty"`
 * <p>
 * // The address of the module the provider is declared in.
 * ModuleAddress string `json:"module_address,omitempty"`
 * <p>
 * // Any non-special configuration values in the provider, indexed by
 * // key.
 * Expressions map[string]*Expression `json:"expressions,omitempty"`
 * <p>
 * // The defined version constraint for this provider.
 * VersionConstraint string `json:"version_constraint,omitempty"`
 * }
 */
@Data
public class ProviderConfig {

    // The name of the provider, ie: "aws".
    @JSONField(name = "name")
    private String name;

    // The fully-specified name of the provider, ie: "registry.terraform.io/hashicorp/aws".
    @JSONField(name = "full_name")
    private String fullName;

    // The alias of the provider, ie: "us-east-1".
    @JSONField(name = "alias")
    private String alias;

    // The address of the module the provider is declared in.
    @JSONField(name = "module_address")
    private String moduleAddress;

    // Any non-special configuration values in the provider, indexed by
    // key.
    @JSONField(name = "expressions")
    private Map<String, Expression> expressions;

    // The defined version constraint for this provider.
    private String versionConstraint;
}
