package silentdoer.web.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static java.net.NetworkInterface.getNetworkInterfaces;

/**
 * 待实现
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-1-28 21:22
 */
public class InetAddressUtils {
    public static InetAddress getAvailableInetAddress() throws SocketException {
        InetAddress result = null;
        for(Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();networkInterfaces.hasMoreElements();){
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            //result = networkInterface.getInetAddresses();
        }
        return result;
    }
}
