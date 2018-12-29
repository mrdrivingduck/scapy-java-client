package iot.zjt.jscapy.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;

import iot.zjt.jscapy.annotation.ApiUrl;
import iot.zjt.jscapy.message.ScapyMessage;

public class UriGenerator {

    public static final URI buildUri(String host, int port, Class<? extends ScapyMessage> msgType, String subPath) {

        String path = null;
        URI uri = null;
        try {
            path = String.format(msgType.getAnnotation(ApiUrl.class).value(), subPath);

            URIBuilder builder = new URIBuilder();
            builder.setScheme("http");
            builder.setHost(String.format(host + ":%d", port));
            builder.setPath(path);
            uri = builder.build();

        } catch (NullPointerException | URISyntaxException e) {
            e.printStackTrace();
        }

        return uri;
    }

    // public static final URI buildUri(
    //     String host, int port,
    //     Class<? extends ScapyMessage> msgType) {

    //     return buildUri(host, port, msgType, null);
    // }
}
