package boot.core.ioc;

import boot.annotation.ioc.Component;
import boot.annotation.mvc.RestController;
import boot.core.store.ComponentStore;
import boot.util.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author cyx
 */
@Slf4j
public class BeansFactory {
    public static final Map<String, Object> BEANS = new HashMap<>(128);


    public void loadBeans() {
        Set<Class<?>> restControllers = ComponentStore.CLASS_MAP.get(RestController.class);
        Set<Class<?>> components = ComponentStore.CLASS_MAP.get(Component.class);

        log.info("The number of class Annotated with @RestController"  + ":[{}]", restControllers.size());
        log.info("The number of class Annotated with @Component"  + ":[{}]", components.size());

        for (Class clazz : components) {
            String beanName = ReflectionUtil.getBeanName(clazz);
            Object obj = ReflectionUtil.newInstance(clazz);
            BEANS.put(beanName, obj);
        }

        for (Class clazz : restControllers) {
            String beanName = clazz.getName();
            Object obj = ReflectionUtil.newInstance(clazz);
            BEANS.put(beanName, obj);
        }

    }
}
