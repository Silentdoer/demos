package me.silentdoer.demosimpleproj.api.common.controller;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/30/2018 4:44 PM
 */
@RestController
@RequestMapping("/api/common")
public class PingController {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("HHH");
    }
}
