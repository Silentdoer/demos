package me.silentdoer.concurrentusage;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * 这个不用管什么场景，反正要用随机数都用这个就OK
 * @author liqi.wang
 * @version 1.0.0
 * @date 11/15/2018 11:02 AM
 */
public class ThreadLocalRandomUsage {

    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        // API、接口、方法；这个方法是有问题的，Java莫名其妙不用的方法干嘛显示出来
        // random.setSeed(System.currentTimeMillis());
        // 下一个高斯分布/正态分布值
        System.out.println(random.nextGaussian());
        System.out.println(random.nextInt(0, 5));
        // reduce归纳【所以是将长的总结为短的】，左闭右开
        System.out.println(Arrays.asList(random.ints(10, 3, 5).boxed().toArray()));
    }
}
