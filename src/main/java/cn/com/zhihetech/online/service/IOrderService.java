package cn.com.zhihetech.online.service;

import cn.com.zhihetech.online.bean.Order;
import cn.com.zhihetech.online.bean.OrderDetail;
import cn.com.zhihetech.util.hibernate.service.SupportService;

import java.util.List;
import java.util.Set;

/**
 * Created by YangDaiChun on 2016/1/4.
 */
public interface IOrderService extends SupportService<Order>{

    Order addAndGet(Order order,Set<OrderDetail> orderDetails);
}
