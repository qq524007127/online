package cn.com.zhihetech.online.service;

import cn.com.zhihetech.online.bean.Activity;
import cn.com.zhihetech.online.bean.Admin;
import cn.com.zhihetech.online.bean.RedEnvelop;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import cn.com.zhihetech.util.hibernate.service.SupportService;

/**
 * Created by ShenYunjie on 2015/12/7.
 */
public interface IActivityService extends SupportService<Activity> {
    Activity saveOrUpdate(Activity activity);

    /**
     * 根据当前登录用于，查询活动。（如果当前用户为系统用于则显示所用活动，否则只显示与当前用户所属商家参加的活动）
     *
     * @param pager
     * @param queryParams
     * @param currentAdmin
     * @return
     */
    PageData<Activity> getPageDataByAdmin(Pager pager, GeneralQueryParams queryParams, Admin currentAdmin);

    /**
     * 商家发起一个新活动或修改自己发起的现有活动
     *
     * @param activity
     * @param redEnvelop
     * @param admin
     */
    void saveByAdmin(Activity activity, RedEnvelop redEnvelop,Admin admin);

    /**
     * 查询活动是否可编辑
     *
     * @param activity
     * @return
     */
    boolean isEditAble(Activity activity);

    /**
     * 根据活动ID获取活动的当前状态
     *
     * @param activitId
     * @return
     */
    int getCurrentSateById(String activitId);
}
