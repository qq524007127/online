package cn.com.zhihetech.online.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by YangDaiChun on 2016/1/4.
 */
@Entity
@Table(name = "t_charge")
public class ChargeInfo extends SerializableAndCloneable {

    private String chargeInfoId;
    private int amount;
    private  String orderNo;
    private int channel;
    private String currency = "cny";
    private String clientIp;
    private String subject;
    private String body;

    @Id
    @GenericGenerator(name = "systemUUID",strategy = "uuid2")
    @GeneratedValue(generator = "systemUUID")
    public String getChargeInfoId() {
        return chargeInfoId;
    }

    public void setChargeInfoId(String chargeInfoId) {
        this.chargeInfoId = chargeInfoId;
    }

    @Column(name = "amount")
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Column(name = "order_no")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Column(name = "channel")
    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    @Column(name = "currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Column(name = "client_ip")
    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    @Column(name = "subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(name = "body")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
