package me.silentdoer.demowebfront.api.mock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 11/21/2018 10:28 AM
 */
@RestController
@RequestMapping("/api/mock")
public class PingController {

    @GetMapping("/ping")
    public String ping() {
        return "中文II";
    }

    @GetMapping("/bootstrapTest")
    public ModelAndView bootstrapTest() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("bootstrapTest");
        return mav;
    }
}
