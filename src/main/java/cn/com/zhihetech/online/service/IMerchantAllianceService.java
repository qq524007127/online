package cn.com.zhihetech.online.service;

import cn.com.zhihetech.online.bean.*;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import cn.com.zhihetech.util.hibernate.service.SupportService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ShenYunjie on 2015/12/8.
 */
public interface IMerchantAllianceService extends SupportService<MerchantAlliance> {
    /**
     * 根据活动ID获取受邀本次活动的所有商户
     *
     * @param pager
     * @param queryParams
     * @param activitId   活动ID
     * @return
     */
    PageData<MerchantAlliance> getMerchAllianceByActivity(Pager pager, GeneralQueryParams queryParams, String activitId);

    /**
     * 根据活动ID获取受邀本次活动的所有商户
     *
     * @param pager
     * @param queryParams
     * @param activitId   活动ID
     * @param agreed      商家是否同意参加本次活动
     * @return
     */
    PageData<MerchantAlliance> getMerchAllianceByActivity(Pager pager, GeneralQueryParams queryParams, String activitId, boolean agreed);

    /**
     * 获取可以参加本次活动的商家
     *
     * @param pager
     * @param queryParams
     * @param activitId
     * @return
     */
    PageData<Merchant> getAbleMerchByActivity(Pager pager, GeneralQueryParams queryParams, String activitId);

    /**
     *获取参加本次活动的所有商家
     * @param pager
     * @param activitId
     * @return
     */
    PageData<Merchant> getJoinMerchByActivity(Pager pager,IQueryParams iQueryParams);
    /**
     * 根据当前登录用户，获取对应商家参加的活动
     *
     * @param pager
     * @param queryParams
     * @param admin
     * @return
     */
    PageData<Activity> getActivityListByAdmin(Pager pager, GeneralQueryParams queryParams, Admin admin);


}
