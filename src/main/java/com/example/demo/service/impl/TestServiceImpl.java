package com.example.demo.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.test.TestEntity;
import com.example.demo.mapper.TestMapper;
import com.example.demo.service.ITestService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, TestEntity> implements ITestService {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void func1() throws Exception {
        log.info("test02 -> thread: " + Thread.currentThread().getName());
        String id = IdUtil.simpleUUID();
        log.info("test02 -> id: " + id);

        TestEntity testEntity = new TestEntity();
        testEntity.setId(id);
        testEntity.setName("test");

        this.save(testEntity);

        applicationContext.getBean(ITestService.class).func2(id);
        int i = 1 / 0;

        Thread.sleep(10000);
    }

    @Async("myThreadPoolTaskExecutor")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void func2(String id) {
        log.info("func1 -> thread: {}", Thread.currentThread().getName());

        TestEntity entity = this.getById(id);
        log.info("func1 -> entity: {}", entity.toString());

        entity.setHasErr(true);
        entity.setErrMsg("func1 -> errMsg");

        if (this.saveOrUpdate(entity)) {
            log.info("func1 -> update success");
        } else {
            log.info("func1 -> update failed");
        }
    }
}
