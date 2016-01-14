package cn.com.zhihetech.online.service;

import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.bean.Order;
import cn.com.zhihetech.online.bean.OrderDetail;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import cn.com.zhihetech.util.hibernate.service.SupportService;

/**
 * Created by YangDaiChun on 2016/1/5.
 */
public interface IOrderDetailService extends SupportService<OrderDetail> {

    public PageData<Order> getOrderPageData(Pager pager,IQueryParams queryParams,Merchant merchant);
}
