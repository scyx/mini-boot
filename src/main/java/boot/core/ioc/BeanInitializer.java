package boot.core.ioc;

import boot.annotation.ioc.Qualifier;
import boot.core.aop.BeanPostProcesser;
import boot.httpHandler.resolver.ParamterResolver;
import boot.util.ReflectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author cyx
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class BeanInitializer {
    private String[] packageNames;

    public void setValueForField(Field field, Object object) throws IllegalAccessException {
        field.setAccessible(true);
        Class<?> type = field.getType();
        String beanName = ReflectionUtil.getBeanName(type);
        if (type.isInterface()) {
            Set<Class<?>> subClass = ReflectionUtil.getSubClass(packageNames, (Class<Object>) type);
            if (subClass.size() == 0) {

            } else if (subClass.size() == 1) {
                Class<?> c = subClass.iterator().next();
                beanName = ReflectionUtil.getBeanName(c);
            } else {
                Qualifier qualifier = field.getDeclaredAnnotation(Qualifier.class);
                beanName = qualifier == null ? beanName : qualifier.value();
            }
        }
        Object value = BeansFactory.SINGLETONS.get(beanName);
        BeanPostProcesser beanPostProcessor = BeanPostProcesser.getProxy(field.getType());
        value = beanPostProcessor.wrap(value);
        field.set(object,value);
    }
}
