package com.example.demo.entity.message.constants;

/**
 * const (
 * // Generic messages
 * MessageVersion    MessageType = "version"
 * MessageLog        MessageType = "log"
 * MessageDiagnostic MessageType = "diagnostic"
 * <p>
 * // Operation results
 * MessageResourceDrift MessageType = "resource_drift"
 * MessagePlannedChange MessageType = "planned_change"
 * MessageChangeSummary MessageType = "change_summary"
 * MessageOutputs       MessageType = "outputs"
 * <p>
 * // Hook-driven messages
 * MessageApplyStart        MessageType = "apply_start"
 * MessageApplyProgress     MessageType = "apply_progress"
 * MessageApplyComplete     MessageType = "apply_complete"
 * MessageApplyErrored      MessageType = "apply_errored"
 * MessageProvisionStart    MessageType = "provision_start"
 * MessageProvisionProgress MessageType = "provision_progress"
 * MessageProvisionComplete MessageType = "provision_complete"
 * MessageProvisionErrored  MessageType = "provision_errored"
 * MessageRefreshStart      MessageType = "refresh_start"
 * MessageRefreshComplete   MessageType = "refresh_complete"
 * <p>
 * // Test messages
 * MessageTestAbstract  MessageType = "test_abstract"
 * MessageTestFile      MessageType = "test_file"
 * MessageTestRun       MessageType = "test_run"
 * MessageTestPlan      MessageType = "test_plan"
 * MessageTestState     MessageType = "test_state"
 * MessageTestSummary   MessageType = "test_summary"
 * MessageTestCleanup   MessageType = "test_cleanup"
 * MessageTestInterrupt MessageType = "test_interrupt"
 * MessageTestStatus    MessageType = "test_status"
 * MessageTestRetry     MessageType = "test_retry"
 * )
 */
public class MessageType {

    // Generic messages

    public static final String VERSION = "version";

    public static final String LOG = "log";

    public static final String DIAGNOSTIC = "diagnostic";

    // Operation results

    public static final String RESOURCE_DRIFT = "resource_drift";

    public static final String PLANNED_CHANGE = "planned_change";
    public static final String CHANGE_SUMMARY = "change_summary";

    public static final String OUTPUTS = "outputs";


    // Hook-driven messages

    public static final String APPLY_START = "apply_start";

    public static final String APPLY_PROGRESS = "apply_progress";

    public static final String APPLY_COMPLETE = "apply_complete";

    public static final String APPLY_ERRORED = "apply_errored";

    public static final String PROVISION_START = "provision_start";

    public static final String PROVISION_PROGRESS = "provision_progress";

    public static final String PROVISION_COMPLETE = "provision_complete";

    public static final String PROVISION_ERRORED = "provision_errored";

    public static final String REFRESH_START = "refresh_start";

    public static final String REFRESH_COMPLETE = "refresh_complete";

    // Test messages

    public static final String TEST_ABSTRACT = "test_abstract";

    public static final String TEST_FILE = "test_file";

    public static final String TEST_RUN = "test_run";

    public static final String TEST_PLAN = "test_plan";

    public static final String TEST_STATE = "test_state";

    public static final String TEST_SUMMARY = "test_summary";

    public static final String TEST_CLEANUP = "test_cleanup";

    public static final String TEST_INTERRUPT = "test_interrupt";

    public static final String TEST_STATUS = "test_status";

    public static final String TEST_RETRY = "test_retry";
}
