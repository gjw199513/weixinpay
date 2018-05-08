package action;

import com.opensymphony.xwork2.ActionSupport;
import entity.Pay;
import org.apache.struts2.interceptor.RequestAware;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import util.HttpsRequest;
import util.PayUtil;
import util.Xstreamutil;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by gjw19 on 2018/5/2.
 */
public class PayAction extends ActionSupport  implements RequestAware {

    //公众账号ID
    private static String appid = "wx730813f2bf556fa5";
    //商户号
    private static String mch_id = "1413962702";
    // 签名
    private static String sign = "MD5";
    // 商品描述
    private static String body = "测试商家-商品类目";
    // 标价金额
    private static int total_fee = 1;
    // 终端ip
    private static String spbill_create_ip = "123.12.12.123";
    // 通知地址
    private static String notify_url = "http://test.letiantian.com/wxpay/notify";
    // 交易类型
    private static String trade_type = "JSAPI";
    // 用户标识
    private static String openid="oMj6swMEffeGk9lA2ROdiyI6sULg";

    private static Map<String, Object> request;

    public Map<String, Object> getRequest() {
        return request;
    }

    public void setRequest(Map<String, Object> arg0) {
        this.request=arg0;
    }

    public static String pay() {
        String Key = "45d33f71d0e54864b2fa3054d7087896";

        String nonce_str = util.PayUtil.getRandomString(20);

        String out_trade_no = util.PayUtil.getCurrTime() + util.PayUtil.getRandomString(5);

        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();

        parameters.put("appid", appid);
        parameters.put("mch_id", mch_id);
        parameters.put("nonce_str", nonce_str);
        parameters.put("sign", sign);
        parameters.put("body", body);
        parameters.put("out_trade_no", out_trade_no);
        parameters.put("total_fee", total_fee);
        parameters.put("spbill_create_ip", spbill_create_ip);
        parameters.put("notify_url", notify_url);
        parameters.put("trade_type", trade_type);
        parameters.put("openid", openid);

        String sign = util.Sign.createSign(parameters, Key);

        Pay pay = new Pay();

        pay.setAppid(appid);
        pay.setBody(body);
        pay.setMch_id(mch_id);
        pay.setNonce_str(nonce_str);
        pay.setNotify_url(notify_url);
        pay.setOpenid(openid);
        pay.setOut_trade_no(out_trade_no);
        pay.setSign(sign);
        pay.setSpbill_create_ip(spbill_create_ip);
        pay.setTrade_type(trade_type);
        pay.setTotal_fee(total_fee);

        Xstreamutil.xstream.alias("xml", Pay.class);
        String xml = Xstreamutil.xstream.toXML(pay).replace("__", "_");
        System.out.println(xml);
        String requestUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String respxml = null;
        String prepay_id = null;
        try {
            respxml = HttpsRequest.HttpsRequest(requestUrl, "POST", xml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(respxml);
        // dom4j获取xml
        try {
            Document doc = DocumentHelper.parseText(respxml);
            Element root = doc.getRootElement();
            List<Element> elementList = root.elements();
            for (int i = 0; i < elementList.size(); i++) {
                if (elementList.get(i).getName().equals("prepay_id")) {
                    prepay_id = elementList.get(i).getTextTrim();
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        System.out.println("aa-------" + prepay_id);
        // 再次生成微信支付签名
        String timeStamp = PayUtil.getTempStamp();
        String Package = "prepay_id=" + prepay_id;
        SortedMap<Object, Object> h5parameters = new TreeMap<Object, Object>();
        h5parameters.put("appId", appid);
        h5parameters.put("timeStamp", timeStamp);
        h5parameters.put("nonceStr", nonce_str);
        h5parameters.put("package", Package);
        h5parameters.put("signType", "MD5");

        String Paysign = util.Sign.createSign(h5parameters, Key);
        System.out.println("pay--------"+Paysign);

        // 请求
//        request.put("appid",appid);
//        request.put("nonce_str",nonce_str);
//        request.put("Package",Package);
//        request.put("Paysign",Paysign);
//        request.put("timeStamp",timeStamp);

        return "pay";
    }



    public static void main(String[] args) {
        pay();
    }


}
