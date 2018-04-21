import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-2-24 10:40
 */
public class Entrance {
    public static void main(String[] args){
        /** 配置文件名称是logback.xml，logback-test.xml等是会被自动扫描的，不需要主动指定配置文件路径 */
        Logger logger = LoggerFactory.getLogger("myLogger");
        //for(int i=0; i<10000;i ++) {
            logger.debug("Hello长度长度长度长度设计费拉反馈谁离开房间的李开复我IEof建瓯市将非杀戮空间发卡机否二");
        //}
        // 控制台和文件均未输出，因为这两个Appender的Level都是DEBUG以上
        logger.trace("HHH");
        logger.error("FFFFFF时代");
    }
}
