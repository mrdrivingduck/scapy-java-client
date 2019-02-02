/**
 * @author Mr Dk.
 * @version 2019.2.2
 */

package iot.zjt.jscapy.message;

import java.util.Date;

import iot.zjt.jscapy.annotation.FieldKey;
import iot.zjt.jscapy.annotation.MessageType;

@MessageType("ARPING")
public class ArpingMessage extends ScapyMessage {

    private String mac;
    private String ip;
    private Date firstSeen;
    private Date lastSeen;

    public String getMac() {
        return mac;
    }

    public String getIp() {
        return ip;
    }

    public Date getFirstSeen() {
        return firstSeen;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    @FieldKey("mac")
    public void setMac(String mac) {
        this.mac = mac;
    }

    @FieldKey("ip")
    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setFirstSeen(Date firstSeen) {
        this.firstSeen = firstSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }
}