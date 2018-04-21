package me.silentdoer.simulatespring.beans.factory.config;

import java.io.Serializable;
import java.util.List;

/**
 * 这个不是VO，也不是涉及RPC的POJO，故可以用基础类型以及有默认值
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-2-19 21:37
 */
public class BeanInfo implements Serializable {
    public static final int SCOPE_PROTOTYPE = 1, SCOPE_SINGLETON = 2;
    private String id;
    private String clazz;
    private Object instance;
    private int scope = SCOPE_SINGLETON;
    private boolean lazyInit = false;
    private List<KeyValueTypePair> constructorArgs;
    private List<KeyValueTypePair> properties;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public List<KeyValueTypePair> getConstructorArgs() {
        return constructorArgs;
    }

    public void setConstructorArgs(List<KeyValueTypePair> constructorArgs) {
        this.constructorArgs = constructorArgs;
    }

    public List<KeyValueTypePair> getProperties() {
        return properties;
    }

    public void setProperties(List<KeyValueTypePair> properties) {
        this.properties = properties;
    }

    public static class KeyValueTypePair {
        private String key;
        private Object value;
        private String type;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
