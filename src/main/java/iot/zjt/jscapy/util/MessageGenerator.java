/**
 * @author mrdrivingduck
 * @version 2019.1.1
 */

package iot.zjt.jscapy.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import iot.zjt.jscapy.message.ArpingMessage;
import iot.zjt.jscapy.message.PacketMessage;
import iot.zjt.jscapy.message.ScapyMessage;

public class MessageGenerator {

    public static List<ScapyMessage> generateIPMsg(String res) {
        JSONArray arr = JSON.parseArray(res);
        List<ScapyMessage> allMsgs = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            ArpingMessage msg = new ArpingMessage();
            msg.setIp(arr.getJSONObject(i).getString("ip"));
            msg.setMac(arr.getJSONObject(i).getString("mac").toUpperCase());
            allMsgs.add(msg);
        }
        return allMsgs;
    }

    public static List<ScapyMessage> generatePortMsg(String res) {
        JSONArray arr = JSON.parseArray(res);
        List<ScapyMessage> allMsgs = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            PacketMessage source = new PacketMessage();
            PacketMessage destination = new PacketMessage();
            source.setMac(arr.getJSONObject(i).getString("src_mac").toUpperCase());
            source.setIp(arr.getJSONObject(i).getString("src_ip"));
            source.setPort(arr.getJSONObject(i).getIntValue("src_port"));
            destination.setMac(arr.getJSONObject(i).getString("dst_mac").toUpperCase());
            destination.setIp(arr.getJSONObject(i).getString("dst_ip"));
            destination.setPort(arr.getJSONObject(i).getIntValue("dst_port"));
            allMsgs.add(source);
            allMsgs.add(destination);
        }
        return allMsgs;
    }

}