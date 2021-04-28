package boot.util;

import boot.annotation.ioc.Component;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public class ReflectionUtil {
    public Set<Class<?>> getSetOfAnnotation(String[] packageName, Class<? extends Annotation> c) {
        Reflections reflections = new Reflections(packageName,new TypeAnnotationsScanner());
        Set<Class<?>> set = reflections.getTypesAnnotatedWith(c,true);
        return set;
    }

    /**
     * 找到指定包下实现了c的子类
     * @param packageName
     * @param c
     * @param <T>
     * @return
     */
    public static <T> Set<Class<? extends T>> getSubClass(String[] packageName, Class<T> c) {
        Reflections reflections = new Reflections((Object) packageName);
        return  reflections.getSubTypesOf(c);

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


    public static Object executeTargetMethod(Object targetObject, Method method, Object... args) {
        try {
            return method.invoke(targetObject, args);
        } catch (Throwable t) {
           t.printStackTrace();
        }
        return null;
    }

    public static void executeTargetMethodNoResult(Object targetObject, Method method, Object... args) {
        try {
            method.invoke(targetObject, args);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
