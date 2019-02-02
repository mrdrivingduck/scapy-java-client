/**
 * @author mrdrivingduck
 * @version 2019.1.6
 */

package iot.zjt.jscapy;

import io.vertx.core.Vertx;
import iot.zjt.jscapy.message.ArpingMessage;
import iot.zjt.jscapy.message.PacketMessage;
import iot.zjt.jscapy.message.ScapyMessage;

public class JScapy {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();
        String host = "localhost";
        int port = 8888;
        
        JScapyListener listener = new JScapyListener(host, port, vertx) {
        
            @Override
            public void onTerminate(String reason) {
                
            }
        
            @Override
            public void onMessage(ScapyMessage msg) {
                if (msg instanceof PacketMessage) {
                    System.out.println(((PacketMessage) msg).getMac());
                } else if (msg instanceof ArpingMessage) {
                    System.out.println(((ArpingMessage) msg).getIp());
                }
            }
        };

        listener.subscrArpingMsg();
        // listener.subscrPktMsg();

    }
}