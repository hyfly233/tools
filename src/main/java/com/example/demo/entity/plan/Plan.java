package com.example.demo.entity.plan;


import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * github.com/hashicorp/terraform-json
 * <p>
 * type Plan struct {
 * // The version of the plan format. This should always match the
 * // PlanFormatVersion constant in this package, or else an unmarshal
 * // will be unstable.
 * FormatVersion string `json:"format_version,omitempty"`
 * <p>
 * // The version of Terraform used to make the plan.
 * TerraformVersion string `json:"terraform_version,omitempty"`
 * <p>
 * // The variables set in the root module when creating the plan.
 * Variables map[string]*PlanVariable `json:"variables,omitempty"`
 * <p>
 * // The common state representation of resources within this plan.
 * // This is a product of the existing state merged with the diff for
 * // this plan.
 * PlannedValues *StateValues `json:"planned_values,omitempty"`
 * <p>
 * // The change operations for resources and data sources within this plan
 * // resulting from resource drift.
 * ResourceDrift []*ResourceChange `json:"resource_drift,omitempty"`
 * <p>
 * // The change operations for resources and data sources within this
 * // plan.
 * ResourceChanges []*ResourceChange `json:"resource_changes,omitempty"`
 * <p>
 * // The change operations for outputs within this plan.
 * OutputChanges map[string]*Change `json:"output_changes,omitempty"`
 * <p>
 * // The Terraform state prior to the plan operation. This is the
 * // same format as PlannedValues, without the current diff merged.
 * PriorState *State `json:"prior_state,omitempty"`
 * <p>
 * // The Terraform configuration used to make the plan.
 * Config *Config `json:"configuration,omitempty"`
 * <p>
 * // RelevantAttributes represents any resource instances and their
 * // attributes which may have contributed to the planned changes
 * RelevantAttributes []ResourceAttribute `json:"relevant_attributes,omitempty"`
 * <p>
 * // Checks contains the results of any conditional checks executed, or
 * // planned to be executed, during this plan.
 * Checks []CheckResultStatic `json:"checks,omitempty"`
 * <p>
 * // Timestamp contains the static timestamp that Terraform considers to be
 * // the time this plan executed, in UTC.
 * Timestamp string `json:"timestamp,omitempty"`
 * }
 */
@Data
public class Plan {

    // The version of the plan format. This should always match the
    // PlanFormatVersion constant in this package, or else an unmarshal
    // will be unstable.
    @JSONField(name = "format_version")
    private String formatVersion;

    // The version of Terraform used to make the plan.
    @JSONField(name = "terraform_version")
    private String terraformVersion;

    // The variables set in the root module when creating the plan.
    @JSONField(name = "variables")
    private Map<String, PlanVariable> variables;

    // The common state representation of resources within this plan.
    // This is a product of the existing state merged with the diff for
    // this plan.
    @JSONField(name = "planned_values")
    private StateValues plannedValues;

    // The change operations for resources and data sources within this plan
    // resulting from resource drift.
    @JSONField(name = "resource_drift")
    private List<ResourceChange> resourceDrift;

    // The change operations for resources and data sources within this
    // plan.
    @JSONField(name = "resource_changes")
    private List<ResourceChange> resourceChanges;

    // The change operations for outputs within this plan.
    @JSONField(name = "output_changes")
    private Map<String, Change> outputChanges;

    // The Terraform state prior to the plan operation. This is the
    // same format as PlannedValues, without the current diff merged.
    @JSONField(name = "prior_state")
    private State priorState;

    // The Terraform configuration used to make the plan.
    @JSONField(name = "configuration")
    private Config configuration;
}
