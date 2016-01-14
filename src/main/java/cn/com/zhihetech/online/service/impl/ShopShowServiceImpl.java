package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.ShopShow;
import cn.com.zhihetech.online.dao.IShopShowDao;
import cn.com.zhihetech.online.service.IShopShowService;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ShenYunjie on 2016/1/11.
 */
@Service("shopShowService")
public class ShopShowServiceImpl implements IShopShowService {

    @Resource(name = "shopShowDao")
    private IShopShowDao shopShowDao;

    /**
     * 根据ID获取持久化对象
     *
     * @param id 对象ID
     * @return
     */
    @Override
    public ShopShow getById(String id) {
        return this.shopShowDao.findEntityById(id);
    }

    /**
     * 删除持久化对象
     *
     * @param shopShow 需要删除的持久化对象
     */
    @Override
    public void delete(ShopShow shopShow) {
        this.shopShowDao.deleteEntity(shopShow);
    }

    /**
     * 添加一个对象到数据库
     *
     * @param shopShow 需要持久化的对象
     * @return
     */
    @Override
    public ShopShow add(ShopShow shopShow) {
        return this.shopShowDao.saveEntity(shopShow);
    }

    /**
     * 更新一个持久化对象
     *
     * @param shopShow
     */
    @Override
    public void update(ShopShow shopShow) {
        this.shopShowDao.updateEntity(shopShow);
    }

    /**
     * 根据分页参数和查询条件查询持久化对象
     *
     * @param pager       分页参数
     * @param queryParams 查询条件
     * @return
     */
    @Override
    public List<ShopShow> getAllByParams(Pager pager, IQueryParams queryParams) {
        return this.shopShowDao.getEntities(pager, queryParams);
    }

    /**
     * 根据分页参数和查询条件查询持久化对象
     *
     * @param pager       分页参数
     * @param queryParams 查询条件
     * @return 经过分页的数据
     */
    @Override
    public PageData<ShopShow> getPageData(Pager pager, IQueryParams queryParams) {
        return this.shopShowDao.getPageData(pager, queryParams);
    }
}
