package cn.com.zhihetech.online.controller;

import cn.com.zhihetech.online.bean.Admin;
import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.commons.Constant;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.exception.SystemException;
import cn.com.zhihetech.online.service.IMerchantService;
import cn.com.zhihetech.util.common.MD5Utils;
import cn.com.zhihetech.util.common.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/1.
 */
@Controller
public class MerchantRegistController extends SupportController {

    private final String REGIST_MERCHANT = "registerMerchant";
    private final String REGIST_MERCHANT_ADMIN = "admin";

    @Resource(name = "merchantService")
    private IMerchantService merchantService;

    @RequestMapping(value = "merchant/regist")
    public String indexPage() {
        return "merchant_register";
    }

    @RequestMapping(value = "merchant/regist/infoForm", method = RequestMethod.POST)
    public String merchantData(Merchant merchant, Admin admin, HttpServletRequest request) {
        if (StringUtils.isEmpty(merchant.getMerchName())) {
            throw new SystemException("请填写商家基本信息");
        }
        request.getSession().setAttribute(REGIST_MERCHANT, merchant);
        request.getSession().setAttribute(REGIST_MERCHANT_ADMIN, admin);
        return "redirect:/merchant/regist/imgForm";
    }

    @RequestMapping(value = "merchant/regist/imgForm")
    public String imgInfoForm(HttpServletRequest request) {
        if (request.getSession().getAttribute(REGIST_MERCHANT) != null && request.getSession().getAttribute(REGIST_MERCHANT_ADMIN) != null) {
            return "merchant_fileUpload";
        }
        return "redirect:/merchant/regist";
    }

    @RequestMapping(value = "merchant/register/uploadImg", method = RequestMethod.POST)
    public String submitMerchantInfo(HttpServletRequest request, Merchant merchantImg) {
        if (request.getSession().getAttribute(REGIST_MERCHANT) != null && request.getSession().getAttribute(REGIST_MERCHANT_ADMIN) != null) {

            Merchant merchant = (Merchant) request.getSession().getAttribute(REGIST_MERCHANT);
            merchant.setCoverImg(merchantImg.getCoverImg());
            merchant.setHeaderImg(merchantImg.getHeaderImg());
            merchant.setOpraterIDPhoto(merchantImg.getOpraterIDPhoto());
            merchant.setOrgPhoto(merchantImg.getOrgPhoto());
            merchant.setBusLicePhoto(merchantImg.getBusLicePhoto());
            merchant.setApplyLetterPhoto(merchantImg.getApplyLetterPhoto());
            merchant.setExaminState(Constant.EXAMINE_STATE_SUBMITED);
            merchant.setCreateDate(new Date());

            Admin admin = (Admin) request.getSession().getAttribute(REGIST_MERCHANT_ADMIN);
            admin.setPermit(true);
            admin.setAdminPwd(MD5Utils.getMD5Msg(admin.getAdminPwd()));
            admin.setMerchant(merchant);
            admin.setSuperAdmin(false);
            this.merchantService.addMerchantAndAdmin(merchant,admin);
            return "login";
        }
        return "redirect:/merchant/regist";
    }
}
