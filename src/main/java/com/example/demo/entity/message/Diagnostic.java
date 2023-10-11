package com.example.demo.entity.message;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.entity.message.constants.DiagnosticSeverity;
import lombok.Data;

/**
 * github.com/hashicorp/terraform/json/diagnostic.go
 */
@Data
public class Diagnostic {

    /**
     * unknown, error, warning
     * {@link DiagnosticSeverity}
     */
    @JSONField(name = "severity")
    private String severity;

    @JSONField(name = "summary")
    private String summary;

    @JSONField(name = "detail")
    private String detail;

    @JSONField(name = "address")
    private Object address;

    @JSONField(name = "range")
    private Object range;

    @JSONField(name = "snippet")
    private Object snippet;
}
