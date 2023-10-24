package com.example.demo.entity.schema;

import lombok.Data;

/**
 * // The attribute type
 * // Either AttributeType or AttributeNestedType is set, never both.
 * AttributeType cty.Type `json:"type,omitempty"`
 * <p>
 * // Details about a nested attribute type
 * // Either AttributeType or AttributeNestedType is set, never both.
 * AttributeNestedType *SchemaNestedAttributeType `json:"nested_type,omitempty"`
 * <p>
 * // The description field for this attribute. If no kind is
 * // provided, it can be assumed to be plain text.
 * Description     string                `json:"description,omitempty"`
 * DescriptionKind SchemaDescriptionKind `json:"description_kind,omitempty"`
 * <p>
 * // If true, this attribute is deprecated.
 * Deprecated bool `json:"deprecated,omitempty"`
 * <p>
 * // If true, this attribute is required - it has to be entered in
 * // configuration.
 * Required bool `json:"required,omitempty"`
 * <p>
 * // If true, this attribute is optional - it does not need to be
 * // entered in configuration.
 * Optional bool `json:"optional,omitempty"`
 * <p>
 * // If true, this attribute is computed - it can be set by the
 * // provider. It may also be set by configuration if Optional is
 * // true.
 * Computed bool `json:"computed,omitempty"`
 * <p>
 * // If true, this attribute is sensitive and will not be displayed
 * // in logs. Future versions of Terraform may encrypt or otherwise
 * // treat these values with greater care than non-sensitive fields.
 * Sensitive bool `json:"sensitive,omitempty"`
 */
@Data
public class SchemaAttribute {

}
