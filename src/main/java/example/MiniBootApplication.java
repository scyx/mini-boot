package example;

import boot.annotation.start.ComponentScan;
import boot.core.ApplicationContext;

@ComponentScan(value = {"example"})
public class MiniBootApplication {
    public static void main(String[] args) throws IllegalAccessException {
        ApplicationContext applicationContext = ApplicationContext.getContext();
        applicationContext.run(MiniBootApplication.class);
    }
}
