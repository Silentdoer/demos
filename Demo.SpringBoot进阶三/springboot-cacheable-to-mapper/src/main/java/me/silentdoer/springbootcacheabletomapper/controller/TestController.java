package me.silentdoer.springbootcacheabletomapper.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootcacheabletomapper.dao.StudentMapper;
import me.silentdoer.springbootcacheabletomapper.model.StudentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
@RequestMapping(name = "测试Cacheable注解在Mapper接口上是否有用")
public class TestController {
    @Autowired
    private StudentMapper studentMapper;

    @GetMapping("/test1")
    @Cacheable(value = "mappingReturnVals", key = "#root.targetClass.name.concat('#') + #root.methodName.concat('#') + #root.args", condition = "true")
    public String test1(){
        log.info("test1");
        return "MMMMMMM";
    }

    @GetMapping("/test2")
    public StudentDO test2(){
        return this.studentMapper.selectStudentById(1L);
    }
}
