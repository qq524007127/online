package cn.com.zhihetech.online.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by YangDaiChun on 2015/11/12.
 */
@Entity
@Table(name = "t_received_goods_address")
public class ReceivedGoodsAddress extends SerializableAndCloneable{

    private String receivedGoodsId;
    private User user;
    private String receiverName;
    private String receiverPhone;
    private String detailAddress;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid2")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "addressId", length = 36)
    public String getReceivedGoodsId() {
        return receivedGoodsId;
    }

    public void setReceivedGoodsId(String receivedGoodsId) {
        this.receivedGoodsId = receivedGoodsId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "receiver_name", length = 50, nullable = false)
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Column(name = "receiver_phone", length = 20, nullable = false)
    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    @Column(name = "receiver_address", length = 400, nullable = false)
    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
}
