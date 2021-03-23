package boot.util;

import boot.annotation.ioc.Component;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.util.Set;

public class ReflectionUtil {
    public Set<Class<?>> getSetOfAnnotation(String[] packageName, Class<? extends Annotation> c) {
        Reflections reflections = new Reflections(packageName,new TypeAnnotationsScanner());
        Set<Class<?>> set = reflections.getTypesAnnotatedWith(c,true);
        return set;
    }

    public static String getBeanName(Class<?> clazz) {
        Component component = clazz.getAnnotation(Component.class);
        String beanName = component != null && !"".equals(component.value()) ? component.value() : clazz.getName();
        return beanName;
    }

    public static Object newInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch(InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage(),e);
        }
    }
}
