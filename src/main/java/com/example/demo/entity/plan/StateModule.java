package com.example.demo.entity.plan;


import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * github.com/hashicorp/terraform-json
 * <p>
 * // StateModule is the representation of a module in the common state
 * // representation. This can be the root module or a child module.
 * type StateModule struct {
 * // All resources or data sources within this module.
 * Resources []*StateResource `json:"resources,omitempty"`
 * <p>
 * // The absolute module address, omitted for the root module.
 * Address string `json:"address,omitempty"`
 * <p>
 * // Any child modules within this module.
 * ChildModules []*StateModule `json:"child_modules,omitempty"`
 * }
 */
@Data
public class StateModule {

    // All resources or data sources within this module.
    @JSONField(name = "resources")
    private List<StateResource> resources;

    // The absolute module address, omitted for the root module.
    @JSONField(name = "address")
    private String address;

    // Any child modules within this module.
    @JSONField(name = "child_modules")
    private List<StateModule> childModules;
}
