package me.silentdoer.demoactivemqusage.api.test.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/12/2018 8:36 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BgOrder implements Serializable {

    private static final long serialVersionUID = 7846365801786146792L;

    private Integer id;

    private String fack;
}
