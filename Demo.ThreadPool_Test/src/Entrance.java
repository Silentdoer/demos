import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public class Entrance {

    public static void main(String[] args){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0;i<3;i++) {
            try{
                Thread.sleep(1000);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
            executorService.execute(() -> System.out.println("Current thread id is: " + Thread.currentThread().getId()));
        }
    }
}
