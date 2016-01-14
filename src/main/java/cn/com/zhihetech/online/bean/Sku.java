package cn.com.zhihetech.online.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by YangDaiChun on 2015/12/2.
 *
 * 商品的SKU，例如一件上架的衣服，它的其中的一种组合 XL + 红色 就会形成这件衣服的一个SKU
 */
@Entity
@Table(name = "t_sku")
public class Sku extends SerializableAndCloneable {

    private String skuId;
    /**
     * 作为一个SKU的唯一标识，sku = 商品编码+各个sku组合
     */
    private String skuCode;
    /**
     * 商品单品价格
     */
    private float price;
    /**
     * 商品单品库存量
     */
    private long stock;

    @GenericGenerator(name = "systemUUID", strategy = "uuid2")
    @GeneratedValue(generator = "systemUUID")
    @Id
    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    @Column(name = "skuCode",length = 36)
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "price")
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Column(name = "stock")
    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }
}
