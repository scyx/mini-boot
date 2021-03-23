package boot.core.store;

import boot.annotation.ioc.Component;
import boot.annotation.mvc.RestController;
import boot.util.ReflectionUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cyx
 */
public class ComponentStore {

    private ReflectionUtil reflectionUtil;

    private static ComponentStore COMPONENT_STORE = new ComponentStore();

    public ComponentStore() {
        this.reflectionUtil = new ReflectionUtil();
    }

    public static final Map<Class<?>, Set<Class<?>>> CLASS_MAP = new ConcurrentHashMap<>(128);

    public static ComponentStore getStore() {
        return COMPONENT_STORE;
    }

    public void scanRestController(String[] packageName) {
        Set<Class<?>> set = reflectionUtil.getSetOfAnnotation(packageName, RestController.class);
        CLASS_MAP.put(RestController.class, set);
    }

    public void scanComponent(String[] packageName) {
        Set<Class<?>> set = reflectionUtil.getSetOfAnnotation(packageName, Component.class);
        CLASS_MAP.put(Component.class, set);
    }

}
