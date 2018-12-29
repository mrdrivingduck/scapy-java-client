package iot.zjt.jscapy;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.List;

import iot.zjt.jscapy.message.IPMessage;
import iot.zjt.jscapy.message.ScapyMessage;
import iot.zjt.jscapy.util.HttpRequestBuilder;
import iot.zjt.jscapy.util.MessageGenerator;
import iot.zjt.jscapy.util.UriGenerator;

public abstract class JScapyListener {

    private String host;
    private int port;

    private boolean running;
    private int timeout;
    private int interval;

    public abstract void onMessage(ScapyMessage msg);
    public abstract void onTerminate(String reason);

    private HashSet<Class<? extends ScapyMessage>> subscribed = new HashSet<>();

    public JScapyListener(String host, int port) {
        this.host = host;
        this.port = port;
        this.running = true;
        this.timeout = 3000;
        this.interval = 6000;

        new Thread() {

            @Override
            public void run() {
                
                while (running) {

                    try {
                        sleep(interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (subscribed.contains(IPMessage.class)) {
                        try {
                            getIPMessage();
                        } catch(IOException e) {
                            printException();
                        }
                    }

                }
            }
        }.start();
    }

    private void getIPMessage() throws IOException {
        URI uri = UriGenerator.buildUri(host, port, IPMessage.class, IPMessage.net);
        String res = HttpRequestBuilder.doGet(uri, timeout);
        List<ScapyMessage> msgs = MessageGenerator.generateIPMsg(res);
        for (int i = 0; i < msgs.size(); i++) {
            onMessage(msgs.get(i));
        }
    }

    private void printException() {
        System.err.println("Failed to connect to " + host + ":" + port);
    }

    public synchronized void subscrIPMsg(String net) {
        subscribed.add(IPMessage.class);
        IPMessage.net = net;
    }

    public synchronized void unSubscribe(Class<? extends ScapyMessage> clazz) {
        if (subscribed.contains(clazz)) {
            subscribed.remove(clazz);
        }
    }

    public synchronized void kill() {
        this.running = false;
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