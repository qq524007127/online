package cn.com.zhihetech.online.service;

import cn.com.zhihetech.online.bean.User;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import cn.com.zhihetech.util.hibernate.service.SupportService;

/**
 * Created by ShenYunjie on 2015/12/15.
 */
public interface IUserService extends SupportService<User> {
    /**
     * 获取还未参加指定活动的指定商家可邀请的有效用户
     *
     * @param pager
     * @param queryParams
     * @param activityId  指定活动ID
     * @param merchantId  指定商家
     * @return
     */
    PageData<User> getAbleUserByActivityIdAndMerchantId(Pager pager, IQueryParams queryParams, String activityId, String merchantId);

    /**
     * 获取用户Id
     * @param pager
     * @param queryParams
     * @return
     */
    String getUserId(Pager pager, IQueryParams queryParams);
}
