package cn.com.zhihetech.online.service;

import cn.com.zhihetech.online.bean.Coupon;
import cn.com.zhihetech.util.hibernate.service.SupportService;

/**
 * Created by ShenYunjie on 2015/12/15.
 */
public interface ICouponService extends SupportService<Coupon> {
    /**
     * 判断优惠券信息是否可编辑
     *
     * @param coupon
     * @return
     */
    boolean isEditAble(Coupon coupon);

    /**
     * 修改优惠券的基本信息
     *
     * @param coupon
     */
    void updateBaseInfo(Coupon coupon);
}
