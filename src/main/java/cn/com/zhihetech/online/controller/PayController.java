package cn.com.zhihetech.online.controller;

import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Refund;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YangDaiChun on 2015/11/30.
 */
@Controller
public class PayController extends SupportController{

    /**
     * pingpp 管理平台对应的 API key
     */
    public static String apiKey = "sk_test_nn9eb9fHqT08COmzz5q5efXL";
    /**
     * pingpp 管理平台对应的应用 ID
     */
    public static String appId = "app_z5mT8OCOuXXTinPy";

    /**
     * 付款
     *
     * 创建 Charge
     *
     * 创建 Charge 用户需要组装一个 map 对象作为参数传递给 Charge.create();
     * map 里面参数的具体说明请参考：https://pingxx.com/document/api#api-c-new
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "pingpp/api/pay/charge",method = RequestMethod.GET)
    public Charge charge(){
        Pingpp.apiKey = apiKey;
        Charge charge = null;
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", 100);
        chargeMap.put("currency", "cny");
        chargeMap.put("subject", "Your Subject");
        chargeMap.put("body", "Your Body");
        chargeMap.put("order_no", "123456789");
        chargeMap.put("channel", "alipay");
        chargeMap.put("client_ip", "127.0.0.1");
        Map<String, String> app = new HashMap<String, String>();
        app.put("id",appId);
        chargeMap.put("app", app);
        try {
            //发起交易请求
            charge = Charge.create(chargeMap);
            System.out.println(charge);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charge;
    }

    /**
     * 退款
     *
     * 创建退款，需要先获得 charge ,然后调用 charge.getRefunds().create();
     * 参数具体说明参考：https://pingxx.com/document/api#api-r-new
     *
     * 可以一次退款，也可以分批退款。
     *
     * @param charge
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "pingpp/api/pay/refund", method = RequestMethod.POST)
    public Refund refund(Charge charge) {
        Refund refund = null;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("description", "苹果被咬了一口。");

        try {
            refund = charge.getRefunds().create(params);
            System.out.println(refund);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return refund;
    }

    @RequestMapping(value = "admin/pay")
    public String indexPage(){
        return "pay";
    }

}
