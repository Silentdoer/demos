package me.silentdoer.springbootforwardtohtml.openapi.order.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootforwardtohtml.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
public class SuccessOrderController {

    @GetMapping("/test1")
    public ModelAndView test1(@ModelAttribute(value = "user", binding = false) User user, ModelAndView modelAndView) {
        HandlerMethodArgumentResolver a;
        log.info("sdfjklsfjjfkd:{}", user.toString());
        modelAndView.setViewName("test2");
        return modelAndView;
    }
}
