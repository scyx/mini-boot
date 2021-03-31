package boot.core.store;

import io.netty.handler.codec.http.HttpMethod;
import org.omg.CORBA.ObjectHelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author cyx
 */
public class UrlAndMethodMapping {

    public static Map<String, Method> METHOD_GET_MAP = new HashMap<>(128);
    public static Map<String, Method> METHOD_POST_MAP = new HashMap<>(128);

    public static Map<String,String> URL_GET_MAP = new HashMap<>();
    public static Map<String,String> URL_POST_MAP = new HashMap<>();


    public static Map<String,Method> getMethodMapByHttpMethod(HttpMethod method) {
        if (HttpMethod.GET.equals(method)) {
            return METHOD_GET_MAP;
        } else if (HttpMethod.POST.equals(method)) {
            return METHOD_POST_MAP;
        }
        return null;
    }

    public static Map<String,String> getUrlMapByHttpMethod(HttpMethod method) {
        if (HttpMethod.GET.equals(method)) {
            return URL_GET_MAP;
        } else if (HttpMethod.POST.equals(method)) {
            return URL_POST_MAP;
        }
        return null;
    }

    public static void loadMapping(String url,HttpMethod httpMethod, Method method) {
        // formatUrl -> method
        Map<String,Method> methodMap = getMethodMapByHttpMethod(httpMethod);
        // formatUrl -> url
        Map<String,String> urlMap = getUrlMapByHttpMethod(httpMethod);
        // url的模式串
        String formatUrl = formatUrl(url);
        if (methodMap != null && methodMap.containsKey(formatUrl)) {
            throw new IllegalArgumentException("url" + url + "already Exist");
        }
        if (method != null) {
            methodMap.put(formatUrl,method);
        }
        if (urlMap != null) {
            urlMap.put(formatUrl,url);
        }
    }

    public static String formatUrl(String url) {
        String s = url.replaceAll("\\{\\w+}","[\\\\u4e00-\\\\u9fa5_a-zA-Z0-9]+");
        String p = "^" + s + "/?$";
        return p.replaceAll("/+","/");
    }

}
