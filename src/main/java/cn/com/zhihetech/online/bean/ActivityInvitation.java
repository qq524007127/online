package cn.com.zhihetech.online.bean;

import cn.com.zhihetech.online.commons.Constant;
import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 活动邀请
 * Created by ShenYunjie on 2015/12/7.
 */
@Entity
@Table(name = "t_activity_invitation")
public class ActivityInvitation extends SerializableAndCloneable {

    private String invitationId;
    private Activity activity;
    private Merchant invitedMerch;  //受邀商家
    private Date createDate;    //邀请时间
    private Date expiredDate;   //到期时间
    private String invitedMsg;  //邀请留言

    @javax.persistence.Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "invitation_id")
    public String getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(String invitationId) {
        this.invitationId = invitationId;
    }

    @ManyToOne
    @JoinColumn(name = "acitivity_id", nullable = false)
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @ManyToOne
    @JoinColumn(name = "invited_merch", nullable = false)
    public Merchant getInvitedMerch() {
        return invitedMerch;
    }

    public void setInvitedMerch(Merchant invitedMerch) {
        this.invitedMerch = invitedMerch;
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

    @JSONField(format = Constant.DEFAULT_DATE_TIME_FORMAT)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expired_date", nullable = false)
    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    @Column(name = "invited_msg", length = 300)
    public String getInvitedMsg() {
        return invitedMsg;
    }

    public void setInvitedMsg(String invitedMsg) {
        this.invitedMsg = invitedMsg;
    }
}
