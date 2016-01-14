package cn.com.zhihetech.online.controller.v1.api;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.com.zhihetech.online.bean.Messager;
import cn.com.zhihetech.online.commons.Constant;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.commons.ResponseStatusCode;
import cn.com.zhihetech.online.commons.WebchineseConfig;
import cn.com.zhihetech.online.service.IMessagerService;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;

/**
 * Created by YangDaiChun on 2015/12/18.
 */
@Controller
public class MessagerApiController extends ApiController {

    @Resource(name = "messagerService")
    private IMessagerService messagerService;


    @RequestMapping(value = "test")
    public String testMessager() {
        return "admin/test";
    }

    /**
     * 获取验证码<br>
     *
     * 访问URL: http://localhost:8080/api/securityCode/get <br>
     *
     * @param phoneNumber   手机号码
     * @param securityState 验证码状态 ；值为 1 则表明为注册用户，值为 2 则表明是修改密码
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "securityCode/get", method = RequestMethod.POST)
    public ResponseMessage getSecurityCode(String phoneNumber, int securityState) throws Exception {
        String securityCode;
        String smsText;

        String smsTestPrev = null;
        String smsTestBack = "，时长为1分钟,请注意不要泄露验证码！";
        if (Constant.SECURITY_REGISTER == securityState) {
            smsTestPrev = "【挚合电商】你正在注册挚合电商账号，验证码为：";
        }else if(Constant.SECURITY_ALTERPWD == securityState){
            smsTestPrev = "【挚合电商】你正在修改账号密码，验证码为：";
        }
        securityCode = this.generateSecurityCode();
        smsText = smsTestPrev + securityCode + smsTestBack;

        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn/");
        post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");
        NameValuePair[] data = {new NameValuePair("Uid", WebchineseConfig.UID), new NameValuePair("Key", WebchineseConfig.KEY),
                new NameValuePair("smsMob", phoneNumber), new NameValuePair("smsText", smsText)};
        post.setRequestBody(data);
        client.executeMethod(post);

        String result = new String(post.getResponseBodyAsString().getBytes("utf8"));
        if (Integer.parseInt(result) > 0) {
            Messager messager = new Messager();
            messager.setPhoneNumber(phoneNumber);
            messager.setSecurityCode(securityCode);
            messager.setSecurityMsg(smsText);
            messager.setSendDate(new Date());
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.MINUTE, 1);
            messager.setValidity(nowTime.getTime());
            this.messagerService.add(messager);
            return executeResult(ResponseStatusCode.SUCCESS_CODE,"获取验证码成功",Constant.VALIDITY_ONE_MINUTE);
        }
        return executeResult(ResponseStatusCode.SYSTEM_ERROR_CODE, "获取验证码失败");
    }


    /**
     * 验证验证码是否正确<br>
     * URL:http://localhost:8080/api/securityCode/verify<br>
     * @param phoneNumber   手机号码
     * @param securityCode       验证码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "securityCode/verify",method = RequestMethod.POST)
    public ResponseMessage verifySecurityCode(String phoneNumber,String securityCode){
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("phoneNumber",phoneNumber).andEqual("securityCode", securityCode).andMoreAndEq("validity", new Date());
        List<Messager> messagers = this.messagerService.getAllByParams(null,queryParams);
        if(messagers.size() > 0){
            return executeResult();
        }
        return executeResult(ResponseStatusCode.SYSTEM_ERROR_CODE,"验证码错误");
    }
}
