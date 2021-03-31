package boot.util;

import io.netty.handler.codec.http.QueryStringDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cyx
 */
public class UrlUtil {
    public static String getRequestPath(String url) {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(url, StandardCharsets.UTF_8);
        System.out.println("toString"+queryStringDecoder.toString());
        System.out.println("path"+queryStringDecoder.path());
        System.out.println("params"+getParamters(url).toString());
        return queryStringDecoder.path();
    }

    public static Map<String,String> getParamters(String url) {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(url);
        Map<String, List<String>> parameters = queryStringDecoder.parameters();
        Map<String,String> res = new HashMap<>(16);
        for (Map.Entry<String,List<String>> entry : parameters.entrySet()) {
            for (String val : entry.getValue()) {
                res.put(entry.getKey(),val);
            }
        }
        System.out.println(url + "'s paramter is " + res.toString());
        return res;
    }


}
