package cn.com.zhihetech.online.bean;

import cn.com.zhihetech.online.commons.Constant;
import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 商家联盟
 * Created by ShenYunjie on 2015/12/7.
 */
@Entity
@Table(name = "t_merchant_alliance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MerchantAlliance extends SerializableAndCloneable {
    private String allianceId;
    private String allianceName;    //默认为对应的活动名称
    private Activity activity;  //此联盟对应的活动
    private Merchant merchant;   //对应的商家
    private Date createDate;    //对应商家加入此联盟的时间
    private boolean agreed; //是否参加本次活动
    private String allianceDesc;
    private boolean promoted;   //是否是活动发起商家

    @javax.persistence.Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "alliance_id", length = 36)
    public String getAllianceId() {
        return allianceId;
    }

    public void setAllianceId(String allianceId) {
        this.allianceId = allianceId;
    }

    @Column(name = "alliance_name", length = 100)
    public String getAllianceName() {
        return allianceName;
    }

    public void setAllianceName(String allianceName) {
        this.allianceName = allianceName;
    }

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @ManyToOne
    @JoinColumn(name = "merchant_id", nullable = false)
    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    @JSONField(format = Constant.DEFAULT_DATE_TIME_FORMAT)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", updatable = false)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "agreed")
    public boolean isAgreed() {
        return agreed;
    }

    public void setAgreed(boolean agreed) {
        this.agreed = agreed;
    }

    @Column(name = "alliance_desc", length = 200)
    public String getAllianceDesc() {
        return allianceDesc;
    }

    public void setAllianceDesc(String allianceDesc) {
        this.allianceDesc = allianceDesc;
    }

    @Column(name = "promoted", updatable = false)
    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }
}
