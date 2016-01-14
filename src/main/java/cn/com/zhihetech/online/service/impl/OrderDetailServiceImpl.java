package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.bean.Order;
import cn.com.zhihetech.online.bean.OrderDetail;
import cn.com.zhihetech.online.dao.IOrderDetailDao;
import cn.com.zhihetech.online.service.IOrderDetailService;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YangDaiChun on 2016/1/5.
 */
@Service(value = "orderDeatilService")
public class OrderDetailServiceImpl implements IOrderDetailService {

    @Resource(name = "orderDetailDao")
    private IOrderDetailDao orderDetailDao;

    @Override
    public OrderDetail getById(String id) {
        return null;
    }

    @Override
    public void delete(OrderDetail orderDetail) {

    }

    @Override
    public OrderDetail add(OrderDetail orderDetail) {
        return null;
    }

    @Override
    public void update(OrderDetail orderDetail) {

    }

    @Override
    public List<OrderDetail> getAllByParams(Pager pager, IQueryParams queryParams) {
        return null;
    }

    @Override
    public PageData<OrderDetail> getPageData(Pager pager, IQueryParams queryParams) {
        return null;
    }

    @Override
    public PageData<Order> getOrderPageData(Pager pager, IQueryParams queryParams, Merchant merchant) {
        IQueryParams queryParams1 = new GeneralQueryParams();
        queryParams1.andEqual("goods.merchant", merchant);
        List<OrderDetail> orderDetails = this.orderDetailDao.getEntities(null, queryParams1);
        Map<String ,OrderDetail> orderDetailsMap = new HashMap<>();
        for(OrderDetail orderDetail : orderDetails){
            orderDetailsMap.put(orderDetail.getOrder().getOrderId(),orderDetail);
        }
        orderDetails.clear();
        orderDetails.addAll(orderDetailsMap.values());


        return null;
    }
}
