package iot.zjt.jscapy.message;

import java.util.Date;

public class PortMessage extends ScapyMessage {

    private String ip;
    private int port;
    private Date firstSeen;
    private Date lastSeen;

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setFirstSeen(Date firstSeen) {
        this.firstSeen = firstSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public Date getFirstSeen() {
        return firstSeen;
    }

    public Date getLastSeen() {
        return lastSeen;
    }
}