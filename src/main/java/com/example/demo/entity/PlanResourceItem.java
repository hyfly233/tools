package com.example.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class PlanResourceItem {
    /**
     * 操作类型：add, change, destroy
     */
    private String action;

    /**
     * 资源模式：resource
     */
    private String model;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源类型
     */
    private String resourceType;

    private List<PlanResourceField> planFields;
}
