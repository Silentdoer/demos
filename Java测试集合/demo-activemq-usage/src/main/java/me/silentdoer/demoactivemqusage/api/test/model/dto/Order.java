package me.silentdoer.demoactivemqusage.api.test.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/12/2018 8:32 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    private static final long serialVersionUID = -1533643918739110894L;

    private Integer id;

    private String name;

    private Integer count;
}
