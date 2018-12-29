package iot.zjt.jscapy;

import iot.zjt.jscapy.message.IPMessage;
import iot.zjt.jscapy.message.ScapyMessage;

public class JScapy {

    public static void main(String[] args) {

        String host = "localhost";
        int port = 8888;
        
        JScapyListener listener = new JScapyListener(host, port){
        
            @Override
            public void onTerminate(String reason) {
                
            }
        
            @Override
            public void onMessage(ScapyMessage msg) {
                IPMessage msgg = (IPMessage) msg;
                System.out.println(msgg.getMac());
            }
        };

        listener.subscrIPMsg("192.168.2.*");

        // listener.kill();
    }
}