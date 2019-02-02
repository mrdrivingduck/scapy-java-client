/**
 * @author Mr Dk.
 * @version 2019.2.2
 */

package iot.zjt.jscapy;

import java.util.HashSet;
import java.util.List;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import iot.zjt.jscapy.message.ArpingMessage;
import iot.zjt.jscapy.message.PacketMessage;
import iot.zjt.jscapy.message.ScapyMessage;
import iot.zjt.jscapy.util.MessageGenerator;
import iot.zjt.jscapy.util.RequestParameter;

public abstract class JScapyListener {

    private String host;
    private int port;
    private HttpClient client;

    private int interval;

    public abstract void onMessage(ScapyMessage msg);
    public abstract void onTerminate(String reason);

    private HashSet<Class<? extends ScapyMessage>> subscribed = new HashSet<>();

    public JScapyListener(String host, int port, final Vertx vertx) {
        this.host = host;
        this.port = port;
        this.interval = 6000;

        HttpClientOptions options = new HttpClientOptions()
            .setDefaultHost(this.host)
            .setDefaultPort(this.port);
        this.client = vertx.createHttpClient(options);

        // conf
        vertx.setPeriodic(this.interval, handler -> {
            if (subscribed.contains(ArpingMessage.class)) {
                getArpingMessage();
            }
        });

        // vertx.setPeriodic(interval, handler -> {
        //     if (subscribed.contains(PacketMessage.class)) {
        //         try {
        //             getPacketMessage();
        //         } catch(IOException e) {
        //             printException();
        //         }
        //     }
        // });
    }

    // conf
    private void getArpingMessage() {
        HttpClientRequest request = client.post(port, host, "/", respose -> {
            respose.bodyHandler(buf -> {
                String res = buf.getString(0, buf.length());
                List<ScapyMessage> msgs = MessageGenerator.generateIPMsg(res);
                for (int i = 0; i < msgs.size(); i++) {
                    onMessage(msgs.get(i));
                }
            });
        });
        request.putHeader("Content-Type", "application/json");
        request.putHeader("charset", "utf-8");
        request.end(RequestParameter.arpingParameter(2, "192.168.1.100/24"));
    }

    // conf
    // private void getPacketMessage() throws IOException {
    //     URI uri = UriGenerator.buildUri(host, port, PacketMessage.class);
    //     String res = HttpRequestBuilder.doGet(uri, timeout);
    //     List<ScapyMessage> msgs = MessageGenerator.generatePortMsg(res);
    //     for (int i = 0; i < msgs.size(); i++) {
    //         onMessage(msgs.get(i));
    //     }
    // }

    public synchronized void subscrPktMsg() {
        subscribed.add(PacketMessage.class);
    }

    public synchronized void subscrArpingMsg() {
        subscribed.add(ArpingMessage.class);
    }

    public synchronized void unSubscribe(Class<? extends ScapyMessage> clazz) {
        if (subscribed.contains(clazz)) {
            subscribed.remove(clazz);
        }
    }

    public synchronized void setInterval(int interval) {
        this.interval = interval;
    }

    public int getInterval() {
        return interval;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}