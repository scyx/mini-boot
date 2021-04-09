package boot.core;

import boot.annotation.mvc.GetMapping;
import boot.annotation.mvc.PostMapping;
import boot.annotation.mvc.RestController;
import boot.annotation.start.ComponentScan;
import boot.core.ioc.BeansFactory;
import boot.core.store.ComponentStore;
import boot.core.store.UrlAndMethodMapping;
import boot.httpServer.HttpServer;
import io.netty.handler.codec.http.HttpMethod;

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

    public void run (Class<?> applicationClass) {
        String[] packageName =  getPackageName(applicationClass);

        // 扫描RestController
        scanComponent(packageName);

        loadBeans();

        loadRouteMethod();

        dependencyInject();

        startServer();
    }

    private void dependencyInject() {
        Map<String,Object> map = BeansFactory.BEANS;
        for (Map.Entry<String,Object> entry : map.entrySet()) {
            Class clazz = entry.getValue().getClass();
            Field[] fields = clazz.getFields();
            for (Field field : fields) {

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
    }


    public void startServer() {
        HttpServer httpServer = new HttpServer();
        httpServer.run();
    }
}
