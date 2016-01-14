package cn.com.zhihetech.online.dao.impl;

import cn.com.zhihetech.online.bean.Order;
import cn.com.zhihetech.online.dao.IOrderDao;
import cn.com.zhihetech.util.hibernate.dao.SimpleSupportDao;
import org.springframework.stereotype.Repository;

/**
 * Created by YangDaiChun on 2016/1/4.
 */
@Repository(value = "orderDao")
public class OrderDaoImpl extends SimpleSupportDao<Order> implements IOrderDao {
}
