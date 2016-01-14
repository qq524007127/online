package cn.com.zhihetech.online.controller.v1.api;

import cn.com.zhihetech.online.bean.ChargeInfo;
import cn.com.zhihetech.online.commons.PingPPConfig;
import cn.com.zhihetech.util.common.StringUtils;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.Charge;
import com.squareup.okhttp.internal.spdy.Ping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YangDaiChun on 2016/1/4.
 */
@Controller
public class PayApiController extends ApiController {

    /**
     * pingpp 管理平台对应的 API key
     */
    private static String apiKey = PingPPConfig.API_KEY;

    /**
     * pingpp 管理平台对应的应用 ID
     */
    private static String appId = PingPPConfig.APP_ID;

    /**
     * <h1>支付API-获取charge对象</h1>
     *
     * 移动端获取Charge对象
     *
     * URL ：api/charge/get
     *
     * 参数:
     * amount   ：int 类型，表示支付的金额，以分为单位。
     * orderNo ：String 类型，表示订单号，由字母和数字组成8-20位。
     * channel  ：int 类型，表示支付渠道。值为1表示用支付宝支付，值为2则用微信支付
     * currency :String 货币类型，不传该参数默认为 人民币 (建议不用传该参数，目前仅支持人民币)
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "charge/get", method = RequestMethod.POST)
    public Charge charge(ChargeInfo chargeInfo, HttpServletRequest request) {
        Pingpp.apiKey = this.apiKey;
        Charge charge = null;
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", chargeInfo.getAmount());
        chargeMap.put("currency", chargeInfo.getCurrency());
        chargeMap.put("subject", "Your's subject");
        chargeMap.put("body", "Your's body");
        chargeMap.put("order_no", chargeInfo.getOrderNo());
        chargeMap.put("channel", PingPPConfig.getChannel(chargeInfo.getChannel()));
        chargeMap.put("client_ip", request.getRemoteAddr());
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", this.appId);
        chargeMap.put("app", app);
        try {
            //发起交易请求
            charge = Charge.create(chargeMap);
            System.out.println(charge);
        } catch (PingppException e) {
            e.printStackTrace();
        }
        return charge;
    }
}
