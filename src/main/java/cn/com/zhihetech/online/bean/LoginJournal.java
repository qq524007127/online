package cn.com.zhihetech.online.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by YangDaiChun on 2015/11/13.
 */

@Entity
@Table(name = "t_login_journal")
public class LoginJournal extends SerializableAndCloneable {

    private String loginJournalId;
    private User user;
    private Date loginDate;
    private String mobileName;      //手机型号
    private String loginId;
    private boolean success;
    private float longitude;               //登录位置的经度
    private float latitude;                  //登录位置的纬度
    private String osName;              //手机操作系统类型
    private String osVersion;           //手机操作系统版本


    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid2")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "journal_id", length = 36)
    public String getLoginJournalId() {
        return loginJournalId;
    }

    public void setLoginJournalId(String loginJournalId) {
        this.loginJournalId = loginJournalId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "login_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    @Column(name = "mobile_name", length = 50)
    public String getMobileName() {
        return mobileName;
    }

    public void setMobileName(String mobileName) {
        this.mobileName = mobileName;
    }

    @Column(name = "login_id", length = 20)
    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    @Column(name = "success")
    public boolean getIsSuccess() {
        return success;
    }

    public void setIsSuccess(boolean success) {
        this.success = success;
    }

    @Column(name = "longitude")
    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Column(name = "latitude")
    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    @Column(name = "os_name", length = 50)
    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    @Column(name = "os_version", length = 50)
    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
}
