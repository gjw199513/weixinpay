package util;

import java.security.Key;
import java.util.*;

/**
 * Created by gjw19 on 2018/5/2.
 */
public class Sign {
	 static String Key="192006250b4c09247ec02edce69f6a2d";

    public static String sign() {
        String appid = "wxd930ea5d5a258f4f";
        String mch_id = "10000100";
        String device_info = "1000";
        String body = "test";
        String nonce_str = "ibuaiVcKdpRxkhJA";

        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
//	 Map<Object, Object> parameters = new HashMap<Object, Object>();

        parameters.put("appid", appid);
        parameters.put("mch_id", mch_id);
        parameters.put("device_info", device_info);
        parameters.put("body", body);
        parameters.put("nonce_str", nonce_str);

        String sign = createSign(parameters,Key);
        System.out.println(sign);
        return null;
    }

    public static String createSign(SortedMap<Object, Object> parameters, String Key) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator<?> it = es.iterator();
        while (it.hasNext()) {
            @SuppressWarnings("rawtypes")
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");

            }


        }
        sb.append("key=" + Key);
        String sign = MD5Util.MD5(sb.toString())
                .toUpperCase();
        return sign;
    }

    public static void main(String[] args) {
        sign();
    }
}
