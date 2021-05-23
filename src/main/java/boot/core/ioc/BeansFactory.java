package boot.core.ioc;

import boot.annotation.ioc.Autowired;
import boot.annotation.ioc.Component;
import boot.annotation.ioc.Qualifier;
import boot.annotation.mvc.RestController;
import boot.core.aop.BeanPostProcesser;
import boot.core.store.ComponentStore;
import boot.util.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author cyx
 */
@Slf4j
public class BeansFactory {
    private String[] packageName;

    public static final Map<String, Object> SINGLETONS = new HashMap<>(128);

    public static final Map<String, Object> EARLY_BEANS = new HashMap<>(128);

    public static final Map<String, ObjectFactory<?>> SINGLETONFACTORIES = new HashMap<>(128);


    public void loadBeans(String[] packageName) throws IllegalAccessException {
        this.packageName = packageName;
        Set<Class<?>> restControllers = ComponentStore.CLASS_MAP.get(RestController.class);
        Set<Class<?>> components = ComponentStore.CLASS_MAP.get(Component.class);

        log.info("The number of class Annotated with @RestController " + ": {}", restControllers.size());
        log.info("The number of class Annotated with @Component " + ": {}", components.size());

        for (Class<?> clazz : components) {
            String beanName = ReflectionUtil.getBeanName(clazz);
            getBean(beanName,clazz);
        }

        for (Class<?> clazz : restControllers) {
            String beanName = clazz.getName();
            getBean(beanName,clazz);
        }

    }

    private Object getBean(String beanName, Class<?> clazz) throws IllegalAccessException {
        if (SINGLETONS.containsKey(beanName)) {
            return SINGLETONS.get(beanName);
        }
        Object obj = ReflectionUtil.newInstance(clazz);
        addSingletonFactories(beanName,() -> getEarlyBeanReference(obj));
        populateBean(beanName,obj);
        return obj;
    }

    private void populateBean(String beanName, Object obj) throws IllegalAccessException {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(Autowired.class)) {
                continue;
            }
            field.setAccessible(true);
            Class<?> type = field.getType();
            String fcName;
            if (type.isInterface()) {
                Set<Class<?>> subClassSet = ReflectionUtil.getSubClass(packageName, (Class<Object>) type);
                if (subClassSet.size() == 0) {
                    throw new RuntimeException("no subClass for " + type.getName());
                } else if (subClassSet.size() == 1) {
                    Class<?> subclass = subClassSet.iterator().next();
                    fcName = ReflectionUtil.getBeanName(subclass);
                } else {
                    Qualifier qualifier = field.getDeclaredAnnotation(Qualifier.class);
                    fcName = qualifier == null ? beanName : qualifier.value();
                }
            } else {
                fcName = ReflectionUtil.getBeanName(type);
            }
            Object fObj;
            if (SINGLETONS.get(fcName) != null) {
                fObj = SINGLETONS.get(fcName);
            } else if (EARLY_BEANS.get(fcName) != null) {
                fObj = EARLY_BEANS.get(fcName);
            } else if (SINGLETONFACTORIES.get(fcName) != null) {
                fObj = SINGLETONFACTORIES.get(fcName).getObject();
                EARLY_BEANS.put(beanName, obj);
                SINGLETONFACTORIES.remove(beanName);
            } else {
                fObj = getBean(fcName, type);
            }
            field.set(obj, fObj);
        }
        EARLY_BEANS.remove(beanName);
        obj = postProcessBean(obj);
        SINGLETONS.put(beanName, obj);
    }

    private Object postProcessBean(Object obj) {
        BeanPostProcesser beanPostProcesser = BeanPostProcesser.getProxy(obj.getClass());
        return beanPostProcesser.wrap(obj);
    }

    public Object getEarlyBeanReference(Object object) {
        Object exposedObject = object;
        BeanPostProcesser beanPostProcesser = BeanPostProcesser.getProxy(exposedObject.getClass());
        return beanPostProcesser.wrap(object);
    }

    public void addSingletonFactories(String beanName, ObjectFactory<?> objectFactory) {
        if (!SINGLETONFACTORIES.containsKey(beanName)){
            SINGLETONFACTORIES.put(beanName, objectFactory);
        }
    }
}
