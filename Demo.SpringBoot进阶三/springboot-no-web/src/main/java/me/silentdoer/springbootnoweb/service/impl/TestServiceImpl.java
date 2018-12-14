package me.silentdoer.springbootnoweb.service.impl;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/10/2018 10:18 AM
 */
@Service
public class TestServiceImpl {
    public static void main(String[] args) throws ParseException {
        String str = "2018040311113";
        Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(str);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(date));
        System.out.println(format.format(new Date(0L)));
    }
}
