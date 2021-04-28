package boot.core;

import boot.annotation.ioc.Autowired;
import boot.annotation.mvc.GetMapping;
import boot.annotation.mvc.PostMapping;
import boot.annotation.mvc.RestController;
import boot.annotation.start.ComponentScan;
import boot.core.aop.BeanPostProcesser;
import boot.core.aop.Interceptor;
import boot.core.aop.InterceptorFactory;
import boot.core.ioc.BeanInitializer;
import boot.core.ioc.BeansFactory;
import boot.core.store.ComponentStore;
import boot.core.store.UrlAndMethodMapping;
import boot.httpServer.HttpServer;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author cyx
 */
public class ApplicationContext {
    private static final ApplicationContext APPLICATION_CONTEXT = new ApplicationContext();

    private BeansFactory beansFactory = new BeansFactory();



    public static ApplicationContext getContext() {
        return APPLICATION_CONTEXT;
    }

    public void run (Class<?> applicationClass) throws IllegalAccessException {
        String[] packageName =  getPackageName(applicationClass);
        // 扫描RestController Component Aspect
        scanComponent(packageName);
        loadBeans();
        loadRouteMethod();
        InterceptorFactory.loadInterceptors(packageName);
        dependencyInject(packageName);
        aopPostProcess(packageName);
        startServer();
    }

    private void aopPostProcess(String[] packageName) {

        BeansFactory.BEANS.replaceAll((beanName,object) -> {
            BeanPostProcesser beanPostProcesser = BeanPostProcesser.getProxy(object.getClass());
            return beanPostProcesser.wrap(object);
        });
    }

    private void dependencyInject(String[] packageNames) throws IllegalAccessException {
        Map<String,Object> map = BeansFactory.BEANS;
        BeanInitializer beanInitializer = new BeanInitializer(packageNames);
        for (Map.Entry<String,Object> entry : map.entrySet()) {
            Object obj = entry.getValue();
            Class clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    beanInitializer.setValueForField(field,obj);
                }
            }
        }
    }


    private void loadBeans() {
        beansFactory.loadBeans();
    }

    private void loadRouteMethod() {
        Set<Class<?>> set = ComponentStore.CLASS_MAP.get(RestController.class);
        for (Iterator<Class<?>> iter = set.iterator(); iter.hasNext(); ) {
            Class<?> clazz = iter.next();
            saveUrlAndMethod(clazz);
        }
    }

    public void saveUrlAndMethod(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        String baseUrl = clazz.getAnnotation(RestController.class).value();
        for (int i = 0; i < methods.length; i++) {
            GetMapping getMapping = methods[i].getAnnotation(GetMapping.class);
            if (getMapping != null) {
                UrlAndMethodMapping.loadMapping(baseUrl + getMapping.value(), HttpMethod.GET, methods[i]);
            }
            PostMapping postMapping = methods[i].getAnnotation(PostMapping.class);
            if (postMapping != null) {
                UrlAndMethodMapping.loadMapping(baseUrl + postMapping.value(), HttpMethod.POST, methods[i]);
            }
        }
    }

    public String[] getPackageName(Class<?> clazz) {
        ComponentScan componentScan = clazz.getAnnotation(ComponentScan.class);
        String[] packageName = componentScan != null ? componentScan.value() :
                new String[]{clazz.getPackage().getName()};
        return packageName;
    }


    public void scanComponent(String[] packageName) {
        ComponentStore componentStore = ComponentStore.getStore();
        componentStore.scanRestController(packageName);
        componentStore.scanComponent(packageName);
        componentStore.scanAspect(packageName);
    }


    public void startServer() {
        HttpServer httpServer = new HttpServer();
        httpServer.run();
    }
}
