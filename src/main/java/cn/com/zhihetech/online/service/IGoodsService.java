package cn.com.zhihetech.online.service;

import cn.com.zhihetech.online.bean.Goods;
import cn.com.zhihetech.online.bean.GoodsBanner;
import cn.com.zhihetech.online.bean.GoodsDetail;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import cn.com.zhihetech.util.hibernate.service.SupportService;

import java.util.List;

/**
 * Created by YangDaiChun on 2015/12/14.
 */
public interface IGoodsService extends SupportService<Goods> {

    /**
     * 逻辑删除商品
     *
     * @param id
     */
    void updateDeleteState(String id);

    /**
     * 更新上下架状态
     *
     * @param id
     * @param onsal
     */
    void updateOnsalState(String id, boolean onsal);

    /**
     * 添加商品
     *
     * @param goods
     * @param goodsBanners
     * @param goodsDetails
     */
    void add(Goods goods, List<GoodsBanner> goodsBanners, List<GoodsDetail> goodsDetails);

    /**
     * 根据商家ID获取可推荐的商品
     *
     * @param queryParams
     * @param pager
     * @param merchantId
     * @return
     */
    PageData<Goods> getAbleRecommendGoodsesByMerchantId(IQueryParams queryParams, Pager pager, String merchantId);
}
