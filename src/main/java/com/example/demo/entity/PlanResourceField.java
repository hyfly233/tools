package com.example.demo.entity;

import lombok.Data;

@Data
public class PlanResourceField {
    /**
     * 字段名称
     */
    private String field;

    /**
     * 原始值
     */
    private String original;

    /**
     * 目标值
     */
    private String target;
}
