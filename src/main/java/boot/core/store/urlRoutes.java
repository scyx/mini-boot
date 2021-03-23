package boot.core.store;

import io.netty.handler.codec.http.HttpMethod;
import org.omg.CORBA.ObjectHelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dell
 */
public class urlRoutes {
    public static Map<String, Method> GET_MAP = new HashMap<>(128);
    public static Map<String, Method> POST_MAP = new HashMap<>(128);
    public static Map<String, Method> DELETE_MAP = new HashMap<>(128);
    public static Map<String, Method> PUT_MAP = new HashMap<>(128);


    public static Map<String,Method> getMapByHttpMethod(HttpMethod method) {
        if (HttpMethod.GET.equals(method)) {
            return GET_MAP;
        } else if (HttpMethod.POST.equals(method)) {
            return POST_MAP;
        }
        return null;
    }
}
