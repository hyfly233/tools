package com.example.demo.entity.plan;


import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.entity.plan.constants.PlanAction;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * github.com/hashicorp/terraform-json
 * <p>
 * // Change is the representation of a proposed change for an object.
 * type Change struct {
 * // The action to be carried out by this change.
 * Actions Actions `json:"actions,omitempty"`
 * <p>
 * // Before and After are representations of the object value both
 * // before and after the action. For create and delete actions,
 * // either Before or After is unset (respectively). For no-op
 * // actions, both values will be identical. After will be incomplete
 * // if there are values within it that won't be known until after
 * // apply.
 * Before interface{} `json:"before,"`
 * After  interface{} `json:"after,omitempty"`
 * <p>
 * // A deep object of booleans that denotes any values that are
 * // unknown in a resource. These values were previously referred to
 * // as "computed" values.
 * //
 * // If the value cannot be found in this map, then its value should
 * // be available within After, so long as the operation supports it.
 * AfterUnknown interface{} `json:"after_unknown,omitempty"`
 * <p>
 * // BeforeSensitive and AfterSensitive are object values with similar
 * // structure to Before and After, but with all sensitive leaf values
 * // replaced with true, and all non-sensitive leaf values omitted. These
 * // objects should be combined with Before and After to prevent accidental
 * // display of sensitive values in user interfaces.
 * BeforeSensitive interface{} `json:"before_sensitive,omitempty"`
 * AfterSensitive  interface{} `json:"after_sensitive,omitempty"`
 * <p>
 * // Importing contains the import metadata about this operation. If importing
 * // is present (ie. not null) then the change is an import operation in
 * // addition to anything mentioned in the actions field. The actual contents
 * // of the Importing struct is subject to change, so downstream consumers
 * // should treat any values in here as strictly optional.
 * Importing *Importing `json:"importing,omitempty"`
 * <p>
 * // GeneratedConfig contains any HCL config generated for this resource
 * // during planning as a string.
 * //
 * // If this is populated, then Importing should also be populated but this
 * // might change in the future. However, not all Importing changes will
 * // contain generated config.
 * GeneratedConfig string `json:"generated_config,omitempty"`
 * }
 */
@Data
public class Change {

    /**
     * The action to be carried out by this change.
     * {@link PlanAction}
     */
    @JSONField(name = "actions")
    private List<String> actions;

    // Before and After are representations of the object value both
    // before and after the action. For create and delete actions,
    // either Before or After is unset (respectively). For no-op
    // actions, both values will be identical. After will be incomplete
    // if there are values within it that won't be known until after
    // apply.
    @JSONField(name = "before")
    private Object before;

    @JSONField(name = "after")
    private Object after;

    // A deep object of booleans that denotes any values that are
    // unknown in a resource. These values were previously referred to
    // as "computed" values.
    //
    // If the value cannot be found in this map, then its value should
    // be available within After, so long as the operation supports it.
    @JSONField(name = "after_unknown")
    private Object afterUnknown;

    // BeforeSensitive and AfterSensitive are object values with similar
    // structure to Before and After, but with all sensitive leaf values
    // replaced with true, and all non-sensitive leaf values omitted. These
    // objects should be combined with Before and After to prevent accidental
    // display of sensitive values in user interfaces.
    @JSONField(name = "before_sensitive")
    private Object beforeSensitive;

    @JSONField(name = "after_sensitive")
    private Object afterSensitive;

    // Importing contains the import metadata about this operation. If importing
    // is present (ie. not null) then the change is an import operation in
    // addition to anything mentioned in the actions field. The actual contents
    // of the Importing struct is subject to change, so downstream consumers
    // should treat any values in here as strictly optional.
    @JSONField(name = "importing")
    private Importing importing;

    // GeneratedConfig contains any HCL config generated for this resource
    // during planning as a string.
    //
    // If this is populated, then Importing should also be populated but this
    // might change in the future. However, not all Importing changes will
    // contain generated config.
    @JSONField(name = "generated_config")
    private String generatedConfig;
}
