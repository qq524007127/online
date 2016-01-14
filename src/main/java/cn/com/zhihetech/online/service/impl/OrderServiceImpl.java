package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.Goods;
import cn.com.zhihetech.online.bean.Order;
import cn.com.zhihetech.online.bean.OrderDetail;
import cn.com.zhihetech.online.dao.IGoodsDao;
import cn.com.zhihetech.online.dao.IOrderDao;
import cn.com.zhihetech.online.dao.IOrderDetailDao;
import cn.com.zhihetech.online.service.IOrderService;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by YangDaiChun on 2016/1/4.
 */
@Service(value = "orderService")
public class OrderServiceImpl implements IOrderService {


    @Resource(name = "orderDao")
    private IOrderDao orderDao;

    @Resource(name = "goodsDao")
    private IGoodsDao goodsDao;

    @Resource(name = "orderDetailDao")
    private IOrderDetailDao orderDetailDao;

    @Override
    public Order getById(String id) {
        return this.orderDao.findEntityById(id);
    }

    @Override
    public void delete(Order order) {

    }

    @Override
    public Order add(Order order) {
        return this.orderDao.saveEntity(order);
    }

    @Override
    public void update(Order order) {

    }

    @Override
    public List<Order> getAllByParams(Pager pager, IQueryParams queryParams) {
        return null;
    }

    @Override
    public PageData<Order> getPageData(Pager pager, IQueryParams queryParams) {
        return this.orderDao.getPageData(pager, queryParams);
    }

    @Override
    public Order addAndGet(Order order, Set<OrderDetail> orderDetails) {
        Order _order = this.orderDao.saveEntity(order);
        Iterator<OrderDetail> it = orderDetails.iterator();
        while (it.hasNext()) {
            OrderDetail orderDetail = it.next();
            orderDetail.setOrder(_order);
            this.orderDetailDao.saveEntity(orderDetail);
        }
        Order order1 = this.orderDao.findEntityById(_order.getOrderId());
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("order.orderId", _order.getOrderId());
        List<OrderDetail> _orderDetails = this.orderDetailDao.getEntities(null, queryParams);
        List<OrderDetail> orderDetails1 = new LinkedList<>();
        Iterator<OrderDetail> it1 = _orderDetails.iterator();
        while(it1.hasNext()){
            OrderDetail orderDetail = it1.next();
            Goods goods = this.goodsDao.findEntityById(orderDetail.getGoods().getGoodsId());
            orderDetail.setGoods(goods);
            orderDetails1.add(orderDetail);
        }
        order1.setOrderDetails(orderDetails1);
        return order1;

    }
}
