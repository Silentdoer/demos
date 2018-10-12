package me.silentdoer.demousagerabbitmq.model;

import lombok.Data;
import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/24/2018 6:17 PM
 */
@Data
public class User implements Serializable {

    private Long id;

    private String name;
}