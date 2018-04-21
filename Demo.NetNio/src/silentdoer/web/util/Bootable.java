package silentdoer.web.util;

public interface Bootable {
    void start() throws Throwable;
    void stop() throws Throwable;
}
