package boot.util;

import boot.annotation.mvc.RestController;
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
}
