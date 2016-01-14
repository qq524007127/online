package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.ShoppingCart;
import cn.com.zhihetech.online.dao.IShoppingCartDao;
import cn.com.zhihetech.online.service.IShoppingCartService;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by ShenYunjie on 2016/1/12.
 */
@Service("shoppingCartService")
public class ShoppingCartServiceImpl implements IShoppingCartService {

    @Resource(name = "shoppingCartDao")
    private IShoppingCartDao shoppingCartDao;

    /**
     * 根据ID获取持久化对象
     *
     * @param id 对象ID
     * @return
     */
    @Override
    public ShoppingCart getById(String id) {
        return this.shoppingCartDao.findEntityById(id);
    }

    /**
     * 删除持久化对象
     *
     * @param shoppingCart 需要删除的持久化对象
     */
    @Override
    public void delete(ShoppingCart shoppingCart) {
        this.deleteById(shoppingCart.getShoppingCartId());
    }

    /**
     * 添加一个对象到数据库
     *
     * @param shoppingCart 需要持久化的对象
     * @return
     */
    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        IQueryParams queryParams = new GeneralQueryParams().andEqual("user.userId", shoppingCart.getUser().getUserId())
                .andEqual("goods.goodsId", shoppingCart.getGoods().getGoodsId());
        List<ShoppingCart> shoppingCarts = this.shoppingCartDao.getEntities(queryParams);
        if (shoppingCarts != null && shoppingCarts.size() > 0) {
            ShoppingCart tmpCart = shoppingCarts.get(0);
            tmpCart.setCount(tmpCart.getCount() + shoppingCart.getCount());
            this.update(tmpCart);
            return tmpCart;
        }
        shoppingCart.setFocusDate(new Date());
        return this.shoppingCartDao.saveEntity(shoppingCart);
    }

    /**
     * 更新一个持久化对象
     *
     * @param shoppingCart
     */
    @Override
    public void update(ShoppingCart shoppingCart) {
        this.shoppingCartDao.updateEntity(shoppingCart);
    }

    /**
     * 根据分页参数和查询条件查询持久化对象
     *
     * @param pager       分页参数
     * @param queryParams 查询条件
     * @return
     */
    @Override
    public List<ShoppingCart> getAllByParams(Pager pager, IQueryParams queryParams) {
        return this.shoppingCartDao.getEntities(pager, queryParams);
    }

    /**
     * 根据分页参数和查询条件查询持久化对象
     *
     * @param pager       分页参数
     * @param queryParams 查询条件
     * @return 经过分页的数据
     */
    @Override
    public PageData<ShoppingCart> getPageData(Pager pager, IQueryParams queryParams) {
        return this.shoppingCartDao.getPageData(pager, queryParams);
    }

    @Override
    public void deleteById(String shoppingCartId) {
        IQueryParams queryParams = new GeneralQueryParams().andEqual("shoppingCartId", shoppingCartId);
        this.shoppingCartDao.executeDelete(queryParams);
    }
}
