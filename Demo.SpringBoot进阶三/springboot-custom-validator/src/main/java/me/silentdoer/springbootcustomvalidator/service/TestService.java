package me.silentdoer.springbootcustomvalidator.service;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootcustomvalidator.model.TestModel;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Service
@Slf4j
public class TestService {

    public void doService(@Valid TestModel model){
        log.info("doService{}", model.getPassword());
    }
}
