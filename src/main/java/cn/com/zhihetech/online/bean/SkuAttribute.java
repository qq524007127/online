package cn.com.zhihetech.online.bean;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by YangDaiChun on 2015/12/2.
 *
 * sku属性,常见的sku属性如尺寸：XL/XXL/S/M ;颜色：红色，蓝色
 */
@Entity
@Table(name = "t_sku_attribute")
public class SkuAttribute extends SerializableAndCloneable {

    private String skuAttId;
    private String skuAttCode; //sku属性编号
    private String skuAttName; //sku属性名:如 红色
    private String skuAttDesc;
    private SkuAttribute parentySkuAttribute;
    private Set<SkuAttribute> childSkuAttributes;
    private Set<GoodsAttributeSet> goodsAttributeSets;

    @GenericGenerator(name = "systemUUID", strategy = "uuid2")
    @GeneratedValue(generator = "systemUUID")
    @Id
    @Column(name = "sku_att_id")
    public String getSkuAttId() {
        return skuAttId;
    }

    public void setSkuAttId(String skuAttId) {
        this.skuAttId = skuAttId;
    }

    @Column(name = "sku_att_code", length = 36)
    public String getSkuAttCode() {
        return skuAttCode;
    }

    public void setSkuAttCode(String skuAttCode) {
        this.skuAttCode = skuAttCode;
    }

    @Column(name = "sku_att_name",length =  300)
    public String getSkuAttName() {
        return skuAttName;
    }

    public void setSkuAttName(String skuAttName) {
        this.skuAttName = skuAttName;
    }

    @Column(name = "sku_att_desc")
    public String getSkuAttDesc() {
        return skuAttDesc;
    }

    public void setSkuAttDesc(String skuAttDesc) {
        this.skuAttDesc = skuAttDesc;
    }

    @ManyToOne
    @JoinColumn(name = "parent_Id")
    public SkuAttribute getParentySkuAttribute() {
        return parentySkuAttribute;
    }

    public void setParentySkuAttribute(SkuAttribute parentySkuAttribute) {
        this.parentySkuAttribute = parentySkuAttribute;
    }

    @JSONField(serialize = false)
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "parentySkuAttribute")
    public Set<SkuAttribute> getChildSkuAttributes() {
        return childSkuAttributes;
    }

    public void setChildSkuAttributes(Set<SkuAttribute> childSkuAttributes) {
        this.childSkuAttributes = childSkuAttributes;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "goodsatt_skuatt", joinColumns = {@JoinColumn(name = "sku_att_id")}, inverseJoinColumns = {@JoinColumn(name = "goods_att_set_id")})
    public Set<GoodsAttributeSet> getGoodsAttributeSets() {
        return goodsAttributeSets;
    }

    public void setGoodsAttributeSets(Set<GoodsAttributeSet> goodsAttributeSets) {
        this.goodsAttributeSets = goodsAttributeSets;
    }

}
