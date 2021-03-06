package boot.core;

import boot.httpServer.HttpServer;

public class ApplicationContext {
    public static final ApplicationContext APPLICATION_CONTEXT = new ApplicationContext();

    public static ApplicationContext getContext() {
        return APPLICATION_CONTEXT;
    }

    public void run (Class<?> applicationClass) {

    }

    public void callRunners() {
        HttpServer httpServer = new HttpServer();
//        httpServer.start();
    }
}
