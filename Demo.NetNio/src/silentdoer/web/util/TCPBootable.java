package silentdoer.web.util;

import java.net.InetAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;

public interface TCPBootable extends Bootable {
    void init(SocketAddress address, Charset charset);
}
