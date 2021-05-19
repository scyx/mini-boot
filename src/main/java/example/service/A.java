package example.service;

import boot.annotation.ioc.Autowired;
import boot.annotation.ioc.Component;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class A {
    @Autowired
    private B b;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

}
