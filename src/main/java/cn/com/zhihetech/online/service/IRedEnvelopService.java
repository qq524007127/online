package cn.com.zhihetech.online.service;

import cn.com.zhihetech.online.bean.Admin;
import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.bean.RedEnvelop;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import cn.com.zhihetech.util.hibernate.service.SupportService;

import java.util.List;
import java.util.Map;

/**
 * Created by ShenYunjie on 2015/12/11.
 */
public interface IRedEnvelopService extends SupportService<RedEnvelop> {
    /**
     * 获取指定用户对应商家的红包
     *
     * @param pager
     * @param queryParams
     * @param currentAdmin
     */
    PageData<RedEnvelop> getPageDataByAdmin(Pager pager, GeneralQueryParams queryParams, Admin currentAdmin);

    /**
     * 判断红包是否可添加、编辑或删除
     *
     * @param redEnvelop
     * @return
     */
    boolean isEditable(RedEnvelop redEnvelop);

    Map<String, List<RedEnvelop>> getAbleRedEnvelopByMerch(Pager pager, List<Merchant> merchants, String activityId);
}
