package iot.zjt.jscapy.util;

import com.alibaba.fastjson.JSONObject;

/**
 * Generate JSON request parameters
 * @author Mr Dk.
 * @version 2019.2.2
 */

public class RequestParameter {

    /*
        {
            'version': '1.0',
            'handler': 'arping',
            'params': {
                'timeout': 2,
                'net': '192.168.1.100/24'
            }
        }
    */
    public static String arpingParameter(int timeout, String net) {
        JSONObject params = new JSONObject();
        params.put("timeout", timeout);
        params.put("net", net);

        JSONObject json = new JSONObject();
        json.put("version", "1.0");
        json.put("handler", "arping");
        json.put("params", params);

        return json.toJSONString();
    }
}