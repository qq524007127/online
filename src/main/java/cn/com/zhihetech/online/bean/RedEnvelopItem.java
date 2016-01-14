package cn.com.zhihetech.online.bean;

import cn.com.zhihetech.online.commons.Constant;
import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 红包裂变明细
 * Created by ShenYunjie on 2015/12/14.
 */
@Entity
@Table(name = "t_redenvelop_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RedEnvelopItem extends SerializableAndCloneable {
    private String envelopItemId;
    private float amountOfMoney;    //金额
    private boolean received;   //是否已被领取
    private Date receivedDate;  //红包领取时间
    private RedEnvelop redEnvelop;

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "item_id", length = 36)
    public String getEnvelopItemId() {
        return envelopItemId;
    }

    public void setEnvelopItemId(String envelopItemId) {
        this.envelopItemId = envelopItemId;
    }


    @Column(name = "amount_money", nullable = false)
    public float getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(float amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    @Column(name = "received", nullable = false)
    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    @JSONField(format = Constant.DEFAULT_DATE_TIME_FORMAT)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "received_date")
    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    @ManyToOne
    @JoinColumn(name = "envelop_id", nullable = false)
    public RedEnvelop getRedEnvelop() {
        return redEnvelop;
    }

    public void setRedEnvelop(RedEnvelop redEnvelop) {
        this.redEnvelop = redEnvelop;
    }
}
