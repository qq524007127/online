package cn.com.zhihetech.online.controller;

import cn.com.zhihetech.online.bean.Coupon;
import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.exception.SystemException;
import cn.com.zhihetech.online.service.IAdminService;
import cn.com.zhihetech.online.service.ICouponService;
import cn.com.zhihetech.util.common.StringUtils;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by ShenYunjie on 2015/12/15.
 */
@Controller
public class CouponController extends SupportController {
    @Resource(name = "adminService")
    private IAdminService adminService;
    @Resource(name = "couponService")
    private ICouponService couponService;

    @RequestMapping(value = "admin/activity/{activityId}/coupon")
    public ModelAndView indexPage(HttpServletRequest request, @PathVariable String activityId) {
        if (StringUtils.isEmpty(activityId)) {
            throw new SystemException("查询参数不符合");
        }
        ModelAndView mv = new ModelAndView("admin/coupon");
        Merchant merchant = adminService.getMerchant(getCurrentAdmin(request));
        mv.addObject("merchant", merchant);
        mv.addObject("activityId", activityId);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/activity/{activityId}/coupon/list")
    public PageData<Coupon> getActivityCoupon(HttpServletRequest request, @PathVariable String activityId) {
        if (StringUtils.isEmpty(activityId)) {
            throw new SystemException("查询参数不符合");
        }
        IQueryParams queryParams = createQueryParams(request).andEqual("activity.activitId", activityId)
                .andEqual("merchant.merchantId", getCurrentAdmin(request).getMerchant().getMerchantId()).andEqual("deleted", false);
        return this.couponService.getPageData(createPager(request), queryParams);
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/coupon/add")
    public ResponseMessage addCoupon(Coupon coupon) {
        this.couponService.add(coupon);
        return executeResult();
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/coupon/editInfo")
    public ResponseMessage editCouponInfo(Coupon coupon) {
        this.couponService.updateBaseInfo(coupon);
        return executeResult();
    }
    @ResponseBody
    @RequestMapping(value = "admin/api/coupon/delete")
    public ResponseMessage deleteCoupon(Coupon coupon){
        this.couponService.delete(coupon);
        return executeResult();
    }
}
