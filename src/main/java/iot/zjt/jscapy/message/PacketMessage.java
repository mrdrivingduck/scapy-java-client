/**
 * @author mrdrivingduck
 * @version 2019.1.1
 */

package iot.zjt.jscapy.message;

import java.util.Date;

import iot.zjt.jscapy.annotation.ApiUrl;

@ApiUrl("/sniff")
public class PacketMessage extends ScapyMessage {

    private String mac;
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

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Date getFirstSeen() {
        return firstSeen;
    }

    public Date getLastSeen() {
        return lastSeen;
    }
}