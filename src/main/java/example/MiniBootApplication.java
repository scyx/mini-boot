package example;

import boot.annotation.start.ComponentScan;
import boot.core.ApplicationContext;

import java.io.IOException;


@ComponentScan(value = {"example"})
public class MiniBootApplication {
    public static void main(String[] args) throws IOException, IllegalAccessException {
        ApplicationContext applicationContext = ApplicationContext.getContext();
        applicationContext.run(MiniBootApplication.class);
    }
}
