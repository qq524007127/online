package cn.com.zhihetech.online.controller.v1.api;

import cn.com.zhihetech.online.bean.Messager;
import cn.com.zhihetech.online.bean.User;
import cn.com.zhihetech.online.commons.Constant;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.commons.ResponseStatusCode;
import cn.com.zhihetech.online.commons.WebchineseConfig;
import cn.com.zhihetech.online.service.IMessagerService;
import cn.com.zhihetech.online.service.IUserService;
import cn.com.zhihetech.util.common.MD5Utils;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.tags.EscapeBodyTag;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
@Controller
public class RetrievePwdControlller extends ApiController {
    @Resource(name = "messagerService")
    private IMessagerService iMessagerService;
    @Resource(name = "userService")
    private IUserService iUserService;


    /**
     * 获取验证码
     *
     * @param phoneNumber 手机号码
     * @param request     请求对象
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "securityCode/getBack", method = RequestMethod.POST)
    public ResponseMessage getSecurityCode(String phoneNumber, HttpServletRequest request) throws IOException {
        GeneralQueryParams generalQueryParams = null;
        List<User> users = null;
        String smsgTextPre = null;
        String securityCode = null;
        String smsgTextSuff = null;
        String smsgText = null;
        if (phoneNumber != null && phoneNumber.length() > 0) {
            generalQueryParams = new GeneralQueryParams();
            generalQueryParams.andEqual("userPhone", phoneNumber);
            users = this.iUserService.getAllByParams(createPager(request), generalQueryParams);
            if (users != null && users.size() > 0) {
                securityCode = this.generateSecurityCode();
                smsgTextPre = " 【挚合电商】你正在注册挚合电商账号，验证码为：";
                smsgTextSuff = "，时长为1分钟,请注意不要泄露验证码！";
                smsgText = smsgTextPre + securityCode + smsgTextSuff;

                HttpClient client = new HttpClient();
                PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn/");
                post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");// 在头文件中设置转码
                NameValuePair[] data = {new NameValuePair("Uid", WebchineseConfig.UID), new NameValuePair("Key", WebchineseConfig.KEY),
                        new NameValuePair("smsMob", phoneNumber), new NameValuePair("smsText", smsgText)};
                post.setRequestBody(data);
                client.executeMethod(post);


                String result = new String(post.getResponseBodyAsString().getBytes("utf8"));
                if (Integer.parseInt(result) > 0) {
                    Messager messager = new Messager();
                    messager.setPhoneNumber(phoneNumber);
                    messager.setSecurityCode(securityCode);
                    messager.setSecurityMsg(smsgText);
                    messager.setSendDate(new Date());
                    Calendar nowTime = Calendar.getInstance();
                    nowTime.add(Calendar.MINUTE, 1);
                    messager.setValidity(nowTime.getTime());
                    this.iMessagerService.add(messager);
                    return executeResult(ResponseStatusCode.SUCCESS_CODE, "获取验证码成功!", Constant.VALIDITY_ONE_MINUTE);
                }
                return executeResult(ResponseStatusCode.SYSTEM_ERROR_CODE, "获取验证码失败!");
            } else {
                return executeResult(ResponseStatusCode.UNAUTHORIZED, "此号码尚未注册!");
            }
        }
        return executeResult(ResponseStatusCode.SUCCESS_CODE, "号码为空!", Constant.VALIDITY_ONE_MINUTE);

    }

    /**
     * 验证验证码的有效性并找回密码
     *
     * @param phoneNumber  手机号码
     * @param securityCode 验证码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "securityCode/retrievePwd", method = RequestMethod.POST)
    public ResponseMessage verifySecurityCode(String phoneNumber, String securityCode) {
        IQueryParams queryParams = new GeneralQueryParams();
        IQueryParams queryParams1 = new GeneralQueryParams();
        queryParams.andEqual("phoneNumber", phoneNumber).andEqual("securityCode", securityCode).andMoreAndEq("validity", new Date());
        List<Messager> messagers = this.iMessagerService.getAllByParams(null, queryParams);
        if (messagers.size() > 0) {
            String userPwd = this.getNumber(6);
            String userPwd_ = MD5Utils.getMD5Msg(userPwd);
            queryParams1.andEqual("userPhone", phoneNumber);
            List<User> users = this.iUserService.getAllByParams(null, queryParams1);
            User u = users.get(0);
            u.setPwd(userPwd_);
            this.iUserService.update(u);
            return executeResult(ResponseStatusCode.SUCCESS_CODE, "密码成功找回!", userPwd);
        }
        return executeResult(ResponseStatusCode.SYSTEM_ERROR_CODE, "验证码错误!");
    }
}
