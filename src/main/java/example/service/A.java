package example.service;

import boot.annotation.ioc.Autowired;
import boot.annotation.ioc.Component;

@Component
public class A {
    @Autowired
    private B b;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public String testB() {
        return "b.a" + ":" + b.getA();
    }

    public String testA() {
        return "b.a" + ":" + b.getA();
    }
}
