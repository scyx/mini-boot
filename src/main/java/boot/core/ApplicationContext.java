package boot.core;

import boot.annotation.start.ComponentScan;
import boot.annotation.mvc.RestController;
import boot.httpServer.HttpServer;
import boot.util.ReflectionUtil;

import java.util.Set;

public class ApplicationContext {
    public static final ApplicationContext APPLICATION_CONTEXT = new ApplicationContext();

    public static ApplicationContext getContext() {
        return APPLICATION_CONTEXT;
    }

    public void run (Class<?> applicationClass) {
        getPackageName(applicationClass);
    }

    public String[] getPackageName(Class<?> c) {
        ReflectionUtil reflection = new ReflectionUtil();
        ComponentScan componentScan = c.getAnnotation(ComponentScan.class);
        String[] packageName = componentScan != null ? componentScan.value() :
                new String[]{c.getPackage().getName()};
        Set<Class<?>> set = reflection.getSetOfAnnotation(packageName, RestController.class);
        System.out.println(set);
        return packageName;
    }


    public void callRunners() {
        HttpServer httpServer = new HttpServer();
        httpServer.run();
    }
}
