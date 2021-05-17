package example.service;

import boot.annotation.ioc.Autowired;
import boot.annotation.ioc.Component;

@Component
public class B {
    @Autowired
    private A a;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public String testB() {
        return "a" + ":" + a;
    }
}
