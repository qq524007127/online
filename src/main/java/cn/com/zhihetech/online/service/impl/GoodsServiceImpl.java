package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.Goods;
import cn.com.zhihetech.online.bean.GoodsBanner;
import cn.com.zhihetech.online.bean.GoodsDetail;
import cn.com.zhihetech.online.dao.IGoodsBannerDao;
import cn.com.zhihetech.online.dao.IGoodsDao;
import cn.com.zhihetech.online.dao.IGoodsDetailDao;
import cn.com.zhihetech.online.dao.IRecommendDao;
import cn.com.zhihetech.online.service.IGoodsService;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YangDaiChun on 2015/12/14.
 */
@Service(value = "goodsService")
public class GoodsServiceImpl implements IGoodsService {

    @Resource(name = "goodsDao")
    private IGoodsDao goodsDao;

    @Resource(name = "goodsDetailDao")
    private IGoodsDetailDao goodsDetailDao;

    @Resource(name = "goodsBannerDao")
    private IGoodsBannerDao goodsBannerDao;

    @Resource(name = "recommendDao")
    private IRecommendDao recommendDao;

    @Override
    public Goods getById(String id) {
        return this.goodsDao.findEntityById(id);
    }

    @Override
    public void delete(Goods goods) {

    }

    @Override
    public Goods add(Goods goods) {
        return this.goodsDao.saveEntity(goods);
    }

    @Override
    public void add(Goods goods, List<GoodsBanner> goodsBanners, List<GoodsDetail> goodsDetails) {
        Goods goods1 = this.goodsDao.saveEntity(goods);
        for (GoodsBanner goodsBanner : goodsBanners) {
            goodsBanner.setGoods(goods1);
            this.goodsBannerDao.saveEntity(goodsBanner);
        }

        for (GoodsDetail goodsDetail : goodsDetails) {
            goodsDetail.setGoods(goods1);
            this.goodsDetailDao.saveEntity(goodsDetail);
        }
    }

    /**
     * 根据商家ID获取可推荐的商品
     *
     * @param queryParams
     * @param pager
     * @param merchantId
     * @return
     */
    @Override
    public PageData<Goods> getAbleRecommendGoodsesByMerchantId(IQueryParams queryParams, Pager pager, String merchantId) {
        IQueryParams queryParams1 = new GeneralQueryParams().andEqual("merchant.merchantId", merchantId);
        List<Object> ids = this.recommendDao.getProperty("goods.goodsId", null, queryParams1);
        queryParams.andNotIn("goodsId", ids).andEqual("onsale", true).andProParam("volume < stock")
                .andEqual("merchant.merchantId", merchantId).andEqual("deleteState", false);
        return goodsDao.getPageData(pager, queryParams);
    }

    @Override
    public void update(Goods goods) {
        this.goodsDao.updateEntity(goods);
    }

    @Override
    public List<Goods> getAllByParams(Pager pager, IQueryParams queryParams) {
        return this.goodsDao.getEntities(pager, queryParams);
    }

    @Override
    public PageData<Goods> getPageData(Pager pager, IQueryParams queryParams) {
        return this.goodsDao.getPageData(pager, queryParams);
    }

    @Override
    public void updateDeleteState(String id) {
        Map<String, Object> paramAndValue = new HashMap<String, Object>();
        paramAndValue.put("deleteState", true);
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("goodsId", id);
        this.goodsDao.executeUpdate(paramAndValue, queryParams);
    }

    @Override
    public void updateOnsalState(String id, boolean onsal) {
        Map<String, Object> paramAndValue = new HashMap<String, Object>();
        paramAndValue.put("onsale", onsal);
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("goodsId", id);
        this.goodsDao.executeUpdate(paramAndValue, queryParams);
    }


}
