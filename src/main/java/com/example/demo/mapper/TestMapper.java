package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.test.TestEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper extends BaseMapper<TestEntity> {
}
