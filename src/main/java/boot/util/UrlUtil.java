package boot.util;

import io.netty.handler.codec.http.QueryStringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cyx
 */
@Slf4j
public class UrlUtil {
    public static String getRequestPath(String url) {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(url, StandardCharsets.UTF_8);
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
        log.info(url + "'s paramter is " + res.toString());
        return res;
    }


}
