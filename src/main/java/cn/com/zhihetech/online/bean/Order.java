package cn.com.zhihetech.online.bean;

import cn.com.zhihetech.online.commons.Constant;
import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by YangDaiChun on 2015/11/12.
 */
@Entity
@Table(name = "t_order")
public class Order extends SerializableAndCloneable {

    private String orderId;
    private String orderName;
    private User user;
    private String orderCode;
    private float orderTotal;
    private int orderState;
    private Date createDate;
    private Date payDate;
    private int  payType;
    private String userMsg;
    private String receiverName;
    private String receiverPhone;
    private String receiverAdd;
    private List<OrderDetail> orderDetails;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid2")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "order_id", length = 36)
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "order_name", nullable = false, length = 500)
    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "order_code", nullable = false, length = 50)
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "order_total", nullable = false)
    public float getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(float orderTotal) {
        this.orderTotal = orderTotal;
    }


    @Column(name = "order_state", nullable = false)
    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    @JSONField(format = Constant.DEFAULT_DATE_TIME_FORMAT)
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "pay_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    @Column(name = "pay_type")
    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    @Column(name = "user_msg", nullable = false, length = 500)
    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }

    @Column(name = "receiver_name", nullable = false, length = 50)
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Column(name ="receiver_phone", nullable = false, length = 20)
    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    @Column(name = "receiver_add", nullable = false, length = 400)
    public String getReceiverAdd() {
        return receiverAdd;
    }

    public void setReceiverAdd(String receiverAdd) {
        this.receiverAdd = receiverAdd;
    }

    @Transient
    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
