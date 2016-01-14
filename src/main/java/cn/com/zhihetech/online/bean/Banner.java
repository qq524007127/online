package cn.com.zhihetech.online.bean;

import cn.com.zhihetech.online.commons.Constant;
import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by YangDaiChun on 2015/11/13.
 */

@Entity
@Table(name = "t_banner")
public class Banner extends SerializableAndCloneable {

    private String bannerId;
    private ImgInfo imgInfo;   //轮播图图片
    private int viewType;      //跳转的页面类型
    private String viewTargert;  //跳转到哪里
    private int bannerType = Constant.BANNER_MAIN;   //轮播图所处的位置
    private int bannerOrder;      //轮播图顺序
    private Date createDate;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid2")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "banner_id", length = 36)
    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    @ManyToOne
    @JoinColumn(name = "imginfo_id")
    public ImgInfo getImgInfo() {
        return imgInfo;
    }

    public void setImgInfo(ImgInfo imgInfo) {
        this.imgInfo = imgInfo;
    }

    @Column(name = "view_type", nullable = false)
    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    @Column(name = "view_target", length = 100)
    public String getViewTargert() {
        return viewTargert;
    }

    public void setViewTargert(String viewTargert) {
        this.viewTargert = viewTargert;
    }

    @Column(name = "banner_type")
    public int getBannerType() {
        return bannerType;
    }

    public void setBannerType(int bannerType) {
        this.bannerType = bannerType;
    }

    @Column(name = "banner_order", nullable = false)
    public int getBannerOrder() {
        return bannerOrder;
    }


    public void setBannerOrder(int bannerOrder) {
        this.bannerOrder = bannerOrder;
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
}
