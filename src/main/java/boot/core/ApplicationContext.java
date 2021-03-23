package boot.core;

import boot.annotation.mvc.GetMapping;
import boot.annotation.start.ComponentScan;
import boot.annotation.mvc.RestController;
import boot.core.store.ComponentStore;
import boot.core.store.urlRoutes;
import boot.core.ioc.BeansFactory;
import boot.httpServer.HttpServer;
import boot.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.util.Iterator;
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

        callRunners();
    }

    private void loadBeans() {
        beansFactory.loadBeans();
    }

    private void loadRouteMethod() {
        Set<Class<?>> set = ComponentStore.CLASS_MAP.get(RestController.class);
        for (Iterator<Class<?>> iter = set.iterator(); iter.hasNext(); ) {
            Class<?> it = iter.next();
            saveUrlAndMethod(it);
        }
    }

    public void saveUrlAndMethod(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            GetMapping getMapping = methods[i].getAnnotation(GetMapping.class);
            String value = getMapping.value();
            urlRoutes.GET_MAP.put(value,methods[i]);
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


    public void callRunners() {
        HttpServer httpServer = new HttpServer();
        httpServer.run();
    }
}
