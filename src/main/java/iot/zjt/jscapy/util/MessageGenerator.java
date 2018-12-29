package iot.zjt.jscapy.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import iot.zjt.jscapy.message.IPMessage;
import iot.zjt.jscapy.message.ScapyMessage;

public class MessageGenerator {

    public static List<ScapyMessage> generateIPMsg(String res) {
        JSONArray arr = JSON.parseArray(res);
        List<ScapyMessage> allMsgs = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            IPMessage msg = new IPMessage();
            msg.setIp(arr.getJSONObject(i).getString("ip"));
            msg.setMac(arr.getJSONObject(i).getString("mac"));
            allMsgs.add(msg);
        }
        return allMsgs;
    }

}