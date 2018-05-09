package me.silentdoer.springbootcontrolleradvice.api.mock.service;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootcontrolleradvice.api.AppResponse;
import me.silentdoer.springbootcontrolleradvice.api.enumerate.AppResponseEnum;
import me.silentdoer.springbootcontrolleradvice.api.model.FooModel;
import me.silentdoer.springbootcontrolleradvice.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0
 */
@Slf4j
@Service
public class MockService {

    public AppResponse<FooModel> doService(String uid){
        if(!NumberUtils.isCreatable(uid)){
            throw new BusinessException("字符串不能转换为验证码");
        }
        return AppResponseEnum.SUCCESS.getResult(new FooModel().setName("中文").setGender('m'));
    }
}
