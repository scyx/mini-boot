package boot.core.store;

import io.netty.handler.codec.http.HttpMethod;
import org.omg.CORBA.ObjectHelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cyx
 */
public class UrlAndMethodMapping {

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

    public static void loadMapping(String url,HttpMethod httpMethod, Method method) {
        Map<String,Method> map = getMapByHttpMethod(httpMethod);
        if (map.containsKey(url)) {
            throw new IllegalArgumentException("url" + url + "already Exist");
        }
        map.put(url,method);
    }
}
