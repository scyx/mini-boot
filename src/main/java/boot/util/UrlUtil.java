package boot.util;

import io.netty.handler.codec.http.QueryStringDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author cyx
 */
public class UrlUtil {
    public static String getRequestPath(String url) {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(url, StandardCharsets.UTF_8);
        System.out.println("toString"+queryStringDecoder.toString());
        System.out.println("path"+queryStringDecoder.path());
        System.out.println("params"+queryStringDecoder.parameters());
        return queryStringDecoder.path();
    }


}
