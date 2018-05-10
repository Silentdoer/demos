package me.silentdoer.springbootschedule.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Service
public class SyncDataTask {

    /*
    "0 0 12 * * ?"      每天中午十二点触发
    "0 15 10 ? * *"    每天早上10：15触发
    "0 15 10 * * ?"    每天早上10：15触发
    "0 15 10 * * ? *"   每天早上10：15触发
    "0 15 10 * * ? 2005"    2005年的每天早上10：15触发
    “0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发
    "0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发
    "0 0/5 14,18 * * ?"     每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
    "0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发
    "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发
    "0 15 10 ? * MON-FRI"   每个周一、周二、周三、周四、周五的10：15触发
     */

    /**
     * TODO 计划任务 都是void且没有参数
     * 当同一个方法上有多个@Scheduled注解时，可以用@Schedules来替换，@Scheduled可以理解为new Scheduled{...}
     */
    /*@Schedules({@Scheduled(fixedDelay = 100*5), @Scheduled(cron = "* * * * * *")})*/
    // TODO 毫秒为单位，和fixedRate的区别在于fixedDelay是要等上一次执行完毕后  才等5秒  执行下一个，而fixedRate是不等执行完而是直接每隔5秒调用此方法
    @Scheduled(fixedDelay = 1000 * 5)
    public void syncData(){
        System.out.println("fixedDelay Schedule is called." + Thread.currentThread().getName());
    }

    /**
     * TODO 经过测试这个schedule任务和上面的schedule任务用的是同一个线程（默认线程池里只有一个线程来执行@Scheduled注解的方法），
     * TODO 但是实际情况里自然不希望所有的schedule任务都在同一个线程池里，因此可以自定义一个TaskScheduler来配置线程池的大小，看Application的bean
     */
    @Scheduled(fixedRate = 1000 * 3)
    public void test0(){
        System.out.println(String.join("", "fixedRate当前的线程：", Thread.currentThread().getName()));
    }

    /** 刚到39的时候调用了一次，所以第一个0是表示36-40之间每分钟的 第0秒 执行一次；*/
    @Scheduled(cron = "0 36-40 * * * *")
    public void test(){
        System.out.println("Cron Schedule is called.");
    }

    /**
     * 0/5表示每隔5分钟调用一次（因为没有定义小时之类的那么默认就是任意不限制），第一个0表示每隔5分钟的第0秒开始（即不是已启动就会执行第一次，而是要从0秒开始）
     */
    @Scheduled(cron = "0 0/5 * * * *")
    public void test2(){
        System.out.println("cron schedule two is called.");
    }
    /**
     * TODO Schedule任务实现比较简单，只需要@EnableScheduling，然后有个在IOC容器的bean，里面存在有@Scheduled注解的方法即可
     */
}
