package cn.com.zhihetech.online.controller.v1.api;

import cn.com.zhihetech.online.bean.FocusMerchant;
import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.bean.User;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.commons.ResponseStatusCode;
import cn.com.zhihetech.online.service.IFocusMerchantService;
import cn.com.zhihetech.online.service.IMerchantService;
import cn.com.zhihetech.online.service.IUserService;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by YangDaiChun on 2016/1/1.
 */
@Controller
public class FocusMerchantApiController extends ApiController {

    @Resource(name = "merchantService")
    private IMerchantService merchantService;

    @Resource(name = "userService")
    private IUserService userService;

    @Resource(name = "focusMerchantService")
    private IFocusMerchantService focusMerchantService;

    /**
     * 添加一个商家关注
     *
     * URL: api/focusMerchant/add
     *
     * 参数
     * @param merchantId  商家id
     * @param userId  用户id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "focusMerchant/add",method = RequestMethod.POST)
    public ResponseMessage saveFousMerchant(String merchantId,String userId){
        Merchant merchant = this.merchantService.getById(merchantId);
        User user =  this.userService.getById(userId);
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("merchant",merchant).andEqual("user",user);
        List<FocusMerchant> focusMerchants = this.focusMerchantService.getAllByParams(null, queryParams);
        if(focusMerchants.size() > 0){
            return executeResult(ResponseStatusCode.SYSTEM_ERROR_CODE,"你已经关注过该商家!");
        }
        FocusMerchant focusMerchant = new FocusMerchant();
        focusMerchant.setFocusMerchantId(null);
        focusMerchant.setMerchant(merchant);
        focusMerchant.setUser(user);
        focusMerchant.setFocusDate(new Date());
        this.focusMerchantService.add(focusMerchant);
        return executeResult();
    }

    /**
     * 检查是否关注过了该商家
     *
     * URL：api/checkFocus
     *
     * 参数
     * @param merchantId    商家ID
     * @param userId   用户ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkFocus")
    public ResponseMessage checkFocusMerchant(String merchantId,String userId){
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("merchant.merchantId",merchantId).andEqual("user.userId",userId);
        List<FocusMerchant> focusMerchants = this.focusMerchantService.getAllByParams(null, queryParams);
        if(focusMerchants.size() > 0){
            return executeResult("你已经关注过该商家!");
        }
        return executeResult(ResponseStatusCode.SYSTEM_ERROR_CODE,"你还未关注过该商家");
    }
}
