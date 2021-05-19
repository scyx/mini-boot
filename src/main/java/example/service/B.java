package example.service;

import boot.annotation.ioc.Autowired;
import boot.annotation.ioc.Component;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class B {
    @Autowired
    private A a;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

}
