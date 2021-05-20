package example.service;

import boot.annotation.ioc.Autowired;
import boot.annotation.ioc.Component;

/**
 * @author cyx
 */
@Component
public class C {
    @Autowired
    private A a;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }
}
