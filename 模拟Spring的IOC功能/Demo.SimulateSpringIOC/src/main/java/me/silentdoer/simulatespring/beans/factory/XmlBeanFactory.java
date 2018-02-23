package me.silentdoer.simulatespring.beans.factory;

import me.silentdoer.simulatespring.beans.factory.config.BeanInfo;
import me.silentdoer.simulatespring.util.PrimitiveParser;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static me.silentdoer.simulatespring.beans.factory.config.BeanInfo.KeyValueTypePair;

/**
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-2-19 21:01
 */
public class XmlBeanFactory {
    private InputStream resource = null;
    private boolean inited = false;
    private Map<String, BeanInfo> beansInfo;
    private Map<String, Class<?>> primitiveAndWrapperTable;

    public XmlBeanFactory(InputStream inputStream){
        this.resource = inputStream;
        primitiveAndWrapperTable = new HashMap<>(16);
        primitiveAndWrapperTable.put("long", long.class);
        primitiveAndWrapperTable.put("java.lang.Long", Long.class);
        primitiveAndWrapperTable.put("int", int.class);
        primitiveAndWrapperTable.put("java.lang.Integer", Integer.class);
        // etc.
    }

    protected final void init() throws DocumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        if (inited) {
            return;
        }
        Logger logger = LoggerFactory.getLogger("myLogger");
        final InputStream config = this.resource;
        if (null == config) {
            throw new IllegalStateException("初始化失败");
        }

        SAXReader reader = new SAXReader();
        Document document = reader.read(config);
        Element root = document.getRootElement();
        List<Element> beans = root.elements("bean");
        final Map<String, BeanInfo> beanInfoMap = this.beansInfo = new LinkedHashMap<String, BeanInfo>(beans.size());
        /** 先构建标签的属性 */
        for (Element bean : beans) {
            Attribute id = bean.attribute("id");
            Attribute clazz = bean.attribute("class");
            Attribute scope = bean.attribute("scope");
            Attribute lazyInit = bean.attribute("lazy-init");
            if (id == null || clazz == null) {
                throw new RuntimeException("配置不合法");
            }
            BeanInfo beanInfo = new BeanInfo();
            beanInfo.setId(id.getValue());
            beanInfo.setClazz(clazz.getValue());
            if (scope != null && scope.getValue().equals("prototype")) {
                beanInfo.setScope(BeanInfo.SCOPE_PROTOTYPE);
            }
            if (lazyInit != null && lazyInit.getValue().equals("true")) {
                beanInfo.setLazyInit(true);
            }
            beanInfoMap.put(id.getValue(), beanInfo);
        }

        /** 构建标签的子结点 */
        for (Element bean : beans) {
            List<Element> constructorParams = bean.elements("constructor-arg");
            List<Element> properties = bean.elements("property");
            String id = bean.attributeValue("id");
            BeanInfo beanInfo = beanInfoMap.get(id);
            List<KeyValueTypePair> conArgs = new ArrayList<KeyValueTypePair>(constructorParams.size());
            beanInfo.setConstructorArgs(conArgs);
            initKeyValueTypePair(conArgs, constructorParams.iterator(), beanInfoMap);

            List<KeyValueTypePair> pros = new ArrayList<KeyValueTypePair>(properties.size());
            beanInfo.setProperties(pros);
            initKeyValueTypePair(pros, properties.iterator(), beanInfoMap);
        }

        /** 根据上面构建出的配置和参数构建bean */
        for(BeanInfo bean : beanInfoMap.values()){
            //boolean prototype = bean.getScope() == BeanInfo.SCOPE_PROTOTYPE;
            boolean lazyInit = bean.isLazyInit();
            // 只要是非lazyInit则初始化后BeanInfo内的instance一定不为null，这个主要是为了先初始化ref的bean后防止重复初始化该bean
            if(!lazyInit && bean.getInstance() == null){
                Object instance = instantiateBean(bean);
                bean.setInstance(instance);
            }
        }
        inited = true;
    }

    /**
     * 通过构建好的BeanInfo初始化具体的实例
     * @param beanInfo
     * @return 实例对象
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    protected Object instantiateBean(BeanInfo beanInfo) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Logger logger = LoggerFactory.getLogger("myLogger");
        Object result = beanInfo.getInstance();
        if(result != null)
            return result;
        Class<?> instanceClazz = Class.forName(beanInfo.getClazz());
        /** ----------------------constructor-arg---------------------- */
        List<KeyValueTypePair> constructorArgs = beanInfo.getConstructorArgs();
        List<Class> conArgTypes = new ArrayList<Class>(16);
        List<Object> conArgs = new ArrayList<Object>(16);
        for(KeyValueTypePair pair : constructorArgs){
            //logger.debug(pair.getType());
            conArgTypes.add(Class.forName(pair.getType()));
            Object value = pair.getValue();
            // ref的情况则先初始化ref对应的bean
            if(BeanInfo.class.isInstance(value)){
                // 递归优先初始化所有的依赖bean
                value = instantiateBean((BeanInfo)value);
            }
            conArgs.add(value);
        }
        /*if(logger.isDebugEnabled()) {
            logger.debug(conArgTypes.toString());
        }*/
        Constructor constructor = instanceClazz.getConstructor(conArgTypes.toArray(new Class[conArgTypes.size()]));
        Object[] initargs = conArgs.toArray(new Object[conArgs.size()]);
        if(logger.isDebugEnabled()){
            for(int i=0;i<initargs.length;i++){
                logger.debug("Tag:" + initargs[i].getClass());
            }
        }
        result = constructor.newInstance(initargs);
        /** ----------------------property---------------------- */
        List<KeyValueTypePair> propertyArgs = beanInfo.getProperties();
        for(KeyValueTypePair pair : propertyArgs){
            String type = pair.getType();
            String name = pair.getKey();
            String setter = String.format("set%s%s", name.substring(0, 1).toUpperCase(), name.substring(1));
            Method setterM = instanceClazz.getMethod(setter, Class.forName(type));
            Object value = pair.getValue();
            if(BeanInfo.class.isInstance(value)){
                value = instantiateBean((BeanInfo) value);
            }
            setterM.invoke(result, value);
        }
        return result;
    }

    /**
     * 通过bean的constructor-arg或property配置填充keyValueTypePairs
     * @param keyValueTypePairs
     * @param iterator
     * @param beansContainer
     */
    protected final void initKeyValueTypePair(final List<KeyValueTypePair> keyValueTypePairs, final Iterator<Element> iterator, final Map<String, BeanInfo> beansContainer){
        Logger logger = LoggerFactory.getLogger("myLogger");
        while(iterator.hasNext()){
            Element next = iterator.next();
            String name = next.attributeValue("name");
            Object value = next.attributeValue("value");
            String ref = next.attributeValue("ref");
            String type = next.attributeValue("type");
            if(value == null && ref == null || value != null && ref != null){
                throw new RuntimeException("配置不合法");
            }
            KeyValueTypePair e = new KeyValueTypePair();
            e.setKey(name);
            e.setType(type);
            if(value != null){
                // 需要转换
                if(primitiveAndWrapperTable.get(type) != null){
                    value = PrimitiveParser.parse(type, value);
                }
                e.setValue(value);
            }else{  // ref
                // NOTE 目前只是初始化BeanInfo，还没到初始化具体的Bean对象
                BeanInfo refBean = beansContainer.get(ref);  // name=gender ref=str1
                // 暂且规定ref的bean要先配置
                if(refBean == null){  // 也可以改成从配置里查找name，有则先初始化该BeanInfo，然后赋值
                    throw new RuntimeException("配置不合法");
                }
                e.setValue(refBean);
            }
            keyValueTypePairs.add(e);
        }
    }

    public <T> T getBean(String id){
        try {
            init();
        }catch (Throwable ex){
            throw new IllegalStateException(ex);
        }
        Object result = null;
        final Map<String, BeanInfo> beans = this.beansInfo;
        BeanInfo beanInfo = beans.get(id);
        result = beanInfo.getInstance();
        if(result == null){
            try {
                result = instantiateBean(beanInfo);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        if(beanInfo.getScope() == BeanInfo.SCOPE_PROTOTYPE){
            try {
                Method clone = Object.class.getMethod("clone");
                clone.setAccessible(true);
                result = clone.invoke(beanInfo.getInstance());
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return (T) result;
    }
}
