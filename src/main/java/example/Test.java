package example;

import boot.core.aop.Interceptor;
import boot.core.aop.MethodInvocation;

import java.lang.reflect.Proxy;

/**
 * @author cyx
 */
public class Test {
    public String test() {
        return "123";
    }

    static int a = 0;
    private static String b = "b";

    public static void main(String[] args) {
        TestInvocationHandler testInvocationHandler = new TestInvocationHandler(new Test());
        Interceptor proxy = (Interceptor)Proxy.newProxyInstance(Test.class.getClassLoader(),Test.class.getInterfaces(),testInvocationHandler);
    }


//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(Test.class);
//        enhancer.setCallback(new FixedValue() {
//            @Override
//            public Object loadObject() throws Exception {
//                return "cglib";
//            }
//        });
////        Test test = (Test)enhancer.create();
//
////        System.out.println(test.test());
//        Scanner scanner = new Scanner(System.in);
//        try {
//            Socket socket = new Socket("127.0.0.1",10086);
//            ExecutorService executorService = new ThreadPoolExecutor(1,10,300, TimeUnit.SECONDS,new LinkedBlockingDeque<>(100));
//            executorService.execute(() -> {
//                try {
//                    InputStream input = socket.getInputStream();
//                    byte[] bytes = new byte[1024 * 4];
//                    while ((input.read(bytes)) != -1) {
//                        System.out.println("收到服务端消息:" + new String(bytes));
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//            OutputStream output = socket.getOutputStream();
//            while (true) {
//                String str = scanner.nextLine();
//                if ("exit".equals(str)) {
//                    output.close();
//                    socket.close();
//                    break;
//                } else {
//                    // 向服务端发送消息
//                    output.write(str.getBytes());
//                    output.flush();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

}
