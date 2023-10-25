package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.test.TestEntity;

public interface ITestService extends IService<TestEntity> {

    void func1() throws Exception;

    void func2(String id);
}
