package me.silentdoer.springbootsession;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@FunctionalInterface
public interface ITest<T> {
    T show(String str);
}
