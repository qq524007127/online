package cn.com.zhihetech.online.controller.v1.api;

import cn.com.zhihetech.online.bean.User;
import cn.com.zhihetech.online.commons.Constant;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.commons.ResponseStatusCode;
import cn.com.zhihetech.online.commons.TokenAndUserId;
import cn.com.zhihetech.online.service.IUserService;
import cn.com.zhihetech.online.util.DateUtils;
import cn.com.zhihetech.util.common.MD5Utils;
import cn.com.zhihetech.util.common.StringUtils;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by ShenYunjie on 2015/12/16.
 */
@Controller
public class UserApiController extends ApiController {
    @Resource(name = "userService")
    private IUserService userService;

    /**
     * <h2>用户注册</h2>
     * URL：/api/user/register<br>
     * <p>参数:</p>
     * userPhone  - 用户电话号码<br>
     * userName  - 用户名<br>
     * pwd  - 用户密码<br>
     * area - 用户所在地区<br>
     * occupation - 用户职业<br>
     * income  - 用户收入<br>
     * sex - 用户性别<br>
     * userBirthday  - 用户生日<br>
     * invitCode  - 用户邀请码<br>
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "user/register", method = RequestMethod.POST)
    public ResponseMessage userRegister(HttpServletRequest request, User user, String userBirthday) {
        user.setPwd(MD5Utils.getMD5Msg(user.getPwd()));
        user.setBirthday(DateUtils.String2Date(userBirthday));
        user.setCreateDate(new Date());
        user.setPermit(true);
        this.userService.add(user);
        return executeResult();
    }


    /**
     * <h2>用户登录</h2>
     * URL：/api/user/login<br>
     *
     * @param userPhone 用户电话号码
     * @param pwd       用户密码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "user/login", method = RequestMethod.POST)
    public ResponseMessage userLogin(HttpServletRequest request, String userPhone, String pwd) {
        pwd = MD5Utils.getMD5Msg(pwd);
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("userPhone", userPhone).andEqual("pwd", pwd).andEqual("permit", true);
        String userId = this.userService.getUserId(null,queryParams);
        if(StringUtils.isEmpty(userId)){
            return executeResult(ResponseStatusCode.UNAUTHORIZED,"用户名或密码出错，请检查用户名和密码重新登录");
        }
        String token = this.getToken(userId);
        TokenAndUserId tokenAndUserId = new TokenAndUserId(userId, token);
        this.setCurrentToken(request, token);
        return executeResult(ResponseStatusCode.SUCCESS_CODE, "登录成功",tokenAndUserId);
    }

}
