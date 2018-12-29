package iot.zjt.jscapy.util;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpRequestBuilder {

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    private HttpRequestBuilder() {

    }

    public static final CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public static final String doGet(URI uri, int timeout) throws IOException {
        CloseableHttpResponse response = null;
        String res = null;
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setConfig(timeoutConfig(timeout));
        
        response = httpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            res = EntityUtils.toString(response.getEntity(), "utf-8");
        }
        response.close();

        return res;
    }

    public static final String doPost(URI uri, String jsonParam, int timeout) throws IOException {
        String res = null;
        CloseableHttpResponse response = null;
        List<BasicNameValuePair> paramPairs = new ArrayList<>();
        paramPairs.add(new BasicNameValuePair("json", jsonParam));

        HttpPost httpPost = new HttpPost(uri);
        UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(paramPairs);
        postEntity.setContentType("application/x-www-form-urlencoded");
        postEntity.setContentEncoding("utf-8");
        httpPost.setEntity(postEntity);
        httpPost.setConfig(timeoutConfig(timeout));

        response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            res = EntityUtils.toString(response.getEntity(), "utf-8");
        }
        response.close();

        return res;
    }

    private static RequestConfig timeoutConfig(int ms) {
        return RequestConfig.custom()
            .setConnectTimeout(ms)
            .setConnectionRequestTimeout(ms)
            .setSocketTimeout(ms)
            .build();
    }

}
