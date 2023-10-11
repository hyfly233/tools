package com.example.demo.entity.plan.constants;

/**
 * github.com/hashicorp/terraform-json
 * <p>
 * const (
 * // ActionNoop denotes a no-op operation.
 * ActionNoop Action = "no-op"
 * <p>
 * // ActionCreate denotes a create operation.
 * ActionCreate Action = "create"
 * <p>
 * // ActionRead denotes a read operation.
 * ActionRead Action = "read"
 * <p>
 * // ActionUpdate denotes an update operation.
 * ActionUpdate Action = "update"
 * <p>
 * // ActionDelete denotes a delete operation.
 * ActionDelete Action = "delete"
 * )
 */
public class PlanAction {

    public static final String NOOP = "no-op";

    public static final String CREATE = "create";

    public static final String READ = "read";

    public static final String UPDATE = "update";

    public static final String DELETE = "delete";
}
