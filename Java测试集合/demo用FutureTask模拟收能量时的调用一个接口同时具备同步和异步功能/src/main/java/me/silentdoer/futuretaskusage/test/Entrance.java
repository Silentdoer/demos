package me.silentdoer.futuretaskusage.test;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/21/2018 11:40 AM
 */
public class Entrance {

    public static void main(String[] args) {
        int processors = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(processors, processors * 2, 2, TimeUnit.MINUTES, new SynchronousQueue<>());

        UUID requestId = UUID.randomUUID();
        // 需要执行的收能量的请求，由主系统请求能量系统
        Callable reqAction = () -> {
            // 模拟耗时
            TimeUnit.SECONDS.sleep(10);
            String name = "silentdoer";
            // 作为requestId
            System.out.printf("%s执行收能量完成，请求Id %s\n", name, requestId);
            // 能量子系统要将requestId和这次操作的流水绑定
            return requestId;
        };

        // TODO 这里的requestId应该是主系统提供给能量子系统；
        // 客户端请求收能量，主系统将收能量的请求动作放入线程池里
        Future task = pool.submit(reqAction);
        // 主系统等待能量系统的处理，如果超过一定时间没有处理完则直接返回告诉客户端 能量球开小差啦
        boolean syncSuccess = false;
        try {
            // 返回requestId
            Object o = task.get(3, TimeUnit.SECONDS);
            System.out.println(o);
            syncSuccess = true;
        }catch (Exception e) {
            e.printStackTrace();
        }

        // 判断是否同步收能量成功，是则直接返回成功的ApiResult，否则返回 状态是提交任务成功，这样客户端就能知道要提示 能量小球开小差了；
        if (syncSuccess) {
            // 返回ApiResult=10000
        } else {
            // 返回ApiResult=11000和requestId，然后客户端可以通过requestId来查自己之前的某个请求是否成功执行完毕及结果是什么；
        }
    }
}
