package cn.com.zhihetech.online.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 参加活动的商品
 * Created by ShenYunjie on 2015/12/7.
 */
@Entity
@Table(name = "t_activity_goods")
public class ActivityGoods extends SerializableAndCloneable {
    private String agId;
    private float activityPrice;    //活动价格
    private Activity activity;  //对应的活动
    private Goods goods;    //对应的商品

    @javax.persistence.Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "ag_id", length = 36)
    public String getAgId() {
        return agId;
    }

    public void setAgId(String agId) {
        this.agId = agId;
    }

    @Column(name = "activity_price")
    public float getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(float activityPrice) {
        this.activityPrice = activityPrice;
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
    @JoinColumn(name = "goods_id", nullable = false)
    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
