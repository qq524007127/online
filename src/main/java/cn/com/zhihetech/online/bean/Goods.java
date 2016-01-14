package cn.com.zhihetech.online.bean;

import cn.com.zhihetech.online.commons.Constant;
import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.text.Format;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by YangDaiChun on 2015/11/12.
 */

@Entity
@Table(name = "t_goods")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Goods extends SerializableAndCloneable {

    private String goodsId;
    private GoodsAttributeSet goodsAttributeSet;    //商品属于哪个种类
    private Merchant merchant;  //商品对应的商家
    private ImgInfo coverImg;  //封面图
    private String goodsName; //商品名
    private String goodsDesc;  //商品描述
    private boolean onsale; //是否已上架（只有经过审核的商品才可以上架销售）
    private int examinState = Constant.EXAMINE_STATE_NOT_SUBMIT;   //是否审核通过（商品一经下架如需重新上架则需重新审核,默认为未提交审核）
    private String examinMsg;   //审核结果
    private long stock;      //商品的原始库存量
    private long currentStock;  //商品的现有库存量
    private long volume;  //商品的销量
    private double price;          //商品的价格
    private boolean isPick;  //是否可自取货
    private String carriageMethod; //商品邮寄方式
    private float carriage; //运费
    private Date createDate;  //创建时间
    private boolean deleteState; //是否已被删除
    private boolean isActivityGoods; //是否是活动商品

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid2")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "goods_id", length = 36)
    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @ManyToOne
    @JoinColumn(name = "goods_att_set")
    public GoodsAttributeSet getGoodsAttributeSet() {
        return goodsAttributeSet;
    }

    public void setGoodsAttributeSet(GoodsAttributeSet goodsAttributeSet) {
        this.goodsAttributeSet = goodsAttributeSet;
    }


    @ManyToOne
    @JoinColumn(name = "merch_id")
    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }


    @ManyToOne
    @JoinColumn(name = "cover_img")
    public ImgInfo getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(ImgInfo coverImg) {
        this.coverImg = coverImg;
    }

    @Column(name = "goods_name", length = 300, nullable = false)
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }


    @Column(name = "goods_desc", length = 500, nullable = false)
    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    @Column(name = "onsale")
    public boolean isOnsale() {
        return onsale;
    }

    public void setOnsale(boolean onsale) {
        this.onsale = onsale;
    }

    @Column(name = "examin_state")
    public int getExaminState() {
        return examinState;
    }

    public void setExaminState(int examinState) {
        this.examinState = examinState;
    }

    @Column(name = "examin_msg", length = 500)
    public String getExaminMsg() {
        return examinMsg;
    }

    public void setExaminMsg(String examinMsg) {
        this.examinMsg = examinMsg;
    }

    @Column(name = "goods_stock")
    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    @Transient
    public long getCurrentStock() {
        currentStock = this.getStock() - this.getVolume();
        return currentStock;
    }

    public void setCurrentStock(long currentStock) {
        this.currentStock = currentStock;
    }

    @Column(name = "goods_volume")
    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    @Column(name = "goods_price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "is_pick")
    public boolean getIsPick() {
        return isPick;
    }

    public void setIsPick(boolean isPick) {
        this.isPick = isPick;
    }

    @Column(name = "carriage_method")
    public String getCarriageMethod() {
        return carriageMethod;
    }

    public void setCarriageMethod(String carriageMethod) {
        this.carriageMethod = carriageMethod;
    }

    @Column(name = "carriage")
    public float getCarriage() {
        return carriage;
    }

    public void setCarriage(float carriage) {
        this.carriage = carriage;
    }

    @JSONField(format=Constant.DEFAULT_DATE_TIME_FORMAT)
    @Column(name = "create_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "deleteState")
    public boolean getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(boolean deleteState) {
        this.deleteState = deleteState;
    }

    @Column(name = "is_activityGoods")
    public boolean getIsActivityGoods() {
        return isActivityGoods;
    }

    public void setIsActivityGoods(boolean isActivityGoods) {
        this.isActivityGoods = isActivityGoods;
    }
}
