package com.example.demo.entity.plan;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.Map;

/**
 * github.com/hashicorp/terraform-json
 * <p>
 * // Config represents the complete configuration source.
 * type Config struct {
 * // A map of all provider instances across all modules in the
 * // configuration.
 * //
 * // The index for this field is opaque and should not be parsed. Use
 * // the individual fields in ProviderConfig to discern actual data
 * // about the provider such as name, alias, or defined module.
 * ProviderConfigs map[string]*ProviderConfig `json:"provider_config,omitempty"`
 * <p>
 * // The root module in the configuration. Any child modules descend
 * // off of here.
 * RootModule *ConfigModule `json:"root_module,omitempty"`
 * }
 */
@Data
public class Config {

    // A map of all provider instances across all modules in the
    // configuration.
    //
    // The index for this field is opaque and should not be parsed. Use
    // the individual fields in ProviderConfig to discern actual data
    // about the provider such as name, alias, or defined module.
    @JSONField(name = "provider_config")
    private Map<String, ProviderConfig> providerConfigs;

    // The root module in the configuration. Any child modules descend
    // off of here.
    @JSONField(name = "root_module")
    private ConfigModule rootModule;
}
