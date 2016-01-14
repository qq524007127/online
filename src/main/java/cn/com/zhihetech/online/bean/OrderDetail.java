package cn.com.zhihetech.online.bean;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by YangDaiChun on 2015/11/12.
 */
@Entity
@Table(name = "t_order_detail")
public class OrderDetail extends SerializableAndCloneable {

    private String orderDetailId;
    private Order order;
    private Goods goods;
    private long count;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid2")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "detial_id", length = 36)
    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    @JSONField(serialize = false)
    @ManyToOne
    @JoinColumn(name = "order_id")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "goods_id")
    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Column(name = "count")
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
