/**
 * @author mrdrivingduck
 * @version 2019.1.1
 */

package iot.zjt.jscapy;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.List;

import io.vertx.core.Vertx;
import iot.zjt.jscapy.message.ArpingMessage;
import iot.zjt.jscapy.message.PacketMessage;
import iot.zjt.jscapy.message.ScapyMessage;
import iot.zjt.jscapy.util.HttpRequestBuilder;
import iot.zjt.jscapy.util.MessageGenerator;
import iot.zjt.jscapy.util.UriGenerator;

public abstract class JScapyListener {

    private String host;
    private int port;
    private final Vertx vertx;

    private int timeout;
    private int interval;

    public abstract void onMessage(ScapyMessage msg);
    public abstract void onTerminate(String reason);

    private HashSet<Class<? extends ScapyMessage>> subscribed = new HashSet<>();

    public JScapyListener(String host, int port) {
        this.host = host;
        this.port = port;
        this.timeout = 3000;
        this.interval = 6000;
        this.vertx = Vertx.vertx();

        vertx.setPeriodic(30 * 1000, handler -> {
            if (subscribed.contains(ArpingMessage.class)) {
                try {
                    getArpingMessage();
                } catch(IOException e) {
                    printException();
                }
            }
        });

        vertx.setPeriodic(interval, handler -> {
            if (subscribed.contains(PacketMessage.class)) {
                try {
                    getPacketMessage();
                } catch(IOException e) {
                    printException();
                }
            }
        });
    }

    private void getArpingMessage() throws IOException {
        URI uri = UriGenerator.buildUri(host, port, ArpingMessage.class, ArpingMessage.net);
        String res = HttpRequestBuilder.doGet(uri, timeout);
        List<ScapyMessage> msgs = MessageGenerator.generateIPMsg(res);
        for (int i = 0; i < msgs.size(); i++) {
            onMessage(msgs.get(i));
        }
    }

    private void getPacketMessage() throws IOException {
        URI uri = UriGenerator.buildUri(host, port, PacketMessage.class, "");
        String res = HttpRequestBuilder.doGet(uri, timeout);
        List<ScapyMessage> msgs = MessageGenerator.generatePortMsg(res);
        for (int i = 0; i < msgs.size(); i++) {
            onMessage(msgs.get(i));
        }
    }

    private void printException() {
        System.err.println("Failed to connect to " + host + ":" + port);
    }

    public synchronized void subscrPktMsg() {
        subscribed.add(PacketMessage.class);
    }

    public synchronized void subscrArpingMsg(String net) {
        subscribed.add(ArpingMessage.class);
        ArpingMessage.net = net;
    }

    public synchronized void unSubscribe(Class<? extends ScapyMessage> clazz) {
        if (subscribed.contains(clazz)) {
            subscribed.remove(clazz);
        }
    }

    public synchronized void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public synchronized void setInterval(int interval) {
        this.interval = interval;
    }

    public int getTimeout() {
        return timeout;
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