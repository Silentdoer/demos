package me.silentdoer.javalockprinciple;

import me.silentdoer.javalockprinciple.support.lock.MyMutexLock;

import java.util.concurrent.TimeUnit;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/13/2018 5:22 PM
 */
public class Entrance extends Thread {

    public static void main(String[] args) {
        Entrance thd = new Entrance();
        Entrance thd2 = new Entrance();
        Entrance thd3 = new Entrance();
        thd.start();
        thd2.start();
        thd3.start();
    }

    /**
     * 在本例中这个lock必须是static的，否则上面new三个Entrance也同时new了三个MyLock
     */
    private static MyMutexLock lock = new MyMutexLock();

    @Override
    public void run() {
        if (lock.tryLock(-1, TimeUnit.SECONDS)) {
            System.out.println("Hello, MMM" + Thread.currentThread().getId());
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("MMM, TTT" + Thread.currentThread().getId());
        }
        try {
            lock.releaseLock();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            //System.exit(0);
        }
    }
}