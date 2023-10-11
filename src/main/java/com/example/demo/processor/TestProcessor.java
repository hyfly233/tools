package com.example.demo.processor;

import com.alibaba.fastjson2.JSON;
import com.example.demo.entity.message.ChangeSummary;
import com.example.demo.entity.message.Diagnostic;
import com.example.demo.entity.message.MessageView;
import com.example.demo.entity.message.constants.MessageLevel;
import com.example.demo.entity.message.constants.MessageType;
import com.example.demo.entity.plan.Plan;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

@Data
@EqualsAndHashCode(callSuper = true)
public class TestProcessor extends BaseProcessor {

    private String planJson;

    private ChangeSummary changeSummary;

    @Override
    public void parse(String line) {
        if (hasErr || completed) {
            return;
        }

        if (StringUtils.isNotBlank(line)) {
            if (line.contains("@level") && line.contains("@message") &&
                    line.contains("@module") && line.contains("@timestamp")) {
                MessageView view = JSON.parseObject(line, MessageView.class);

                if (MessageLevel.ERROR.equals(view.getLevel())) {
                    // TODO: 2023/10/7 处理错误信息
                    String message = view.getMessage();
                    Diagnostic diagnostic = view.getDiagnostic();
                    if (diagnostic != null) {
                        message = message + ". " + diagnostic.getDetail();
                    }

                    hasErr = true;
                    errorBuilder.append(message);

                } else if (MessageLevel.INFO.equals(view.getLevel())) {
                    // TODO: 2023/10/7 处理 info 信息
                    if (MessageType.CHANGE_SUMMARY.equals(view.getType())) {
                        ChangeSummary summary = view.getChangeSummary();
                        if (summary != null) {
                            this.changeSummary = summary;
                        }
                    }
                }
            } else if (line.contains("planned_values") && line.contains("resource_changes") &&
                    line.contains("output_changes")) {
                // 解析 tfplan 的 json 格式数据
                // TODO: 2023/10/7 处理 plan json 数据
                Plan plan = JSON.parseObject(line, Plan.class);
                if (plan != null && plan.getResourceChanges() != null) {
                    completed = true;
                    planJson = line;
                }
            }
        }
    }

    @Override
    public void parseError(String line) {
        if (StringUtils.isNotBlank(line)) {
            hasErr = true;
            errorBuilder.append(line.trim()).append("\n");
        }
    }
}
