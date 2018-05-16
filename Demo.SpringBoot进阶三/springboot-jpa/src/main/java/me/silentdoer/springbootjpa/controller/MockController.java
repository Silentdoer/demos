package me.silentdoer.springbootjpa.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootjpa.model.Account;
import me.silentdoer.springbootjpa.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
@RequestMapping("/account")
public class MockController {

    @Autowired
    private IAccountService accountService;

    /**
     * 测试成功，获取到了数据，@Min之类的可以直接注解到参数上
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable("id") @Min(value = 1, message = "id值不能小于1") Long id){
        return this.accountService.getSingleAccount(id);
    }

    @GetMapping("/list")
    public List<Account> getAccounts(){
        // TODO 待做
        return null;
    }

    @GetMapping("/test1")
    public void test1(){

    }
}
