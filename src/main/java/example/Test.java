package example;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;

/**
 * @author cyx
 */
public class Test {
    public String test() {
        return "123";
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Test.class);
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "cglib";
            }
        });
        Test test = (Test)enhancer.create();

        System.out.println(test.test());

    }
}
