package me.silentdoer.rsausage;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Data
@Accessors(chain = true)
public class Entrance3 {
    private Long ss;
    private String uu;

    public static void main(String[] args){
        Entrance3 e = new Entrance3();
        System.out.println(e.getSs());
    }
}
