package me.silentdoer.commonsjarusage;

import net.sf.cglib.beans.BeanMap;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections.Bag;
import org.apache.commons.collections.BagUtils;
import org.apache.commons.collections.bag.HashBag;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class Entrance {
    public static void main(String[] args) throws IOException {
        System.out.println(Hex.encodeHexString("中文".getBytes(StandardCharsets.UTF_8)).toUpperCase());
        System.out.println(RandomStringUtils.randomAlphanumeric(18));
        //org.apache.commons.io.FileUtils.copyFile(new File("source"), new File("dest"));
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 2, 2, 3);
        Bag bag = new HashBag(list);
        // 输出list里值为2的元素有几个
        System.out.println(bag.getCount(3));
        System.out.println((1 % 3 == 0));
        //MessageFormat.format()
    }
}
