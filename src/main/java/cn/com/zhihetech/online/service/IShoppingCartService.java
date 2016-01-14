package cn.com.zhihetech.online.service;

import cn.com.zhihetech.online.bean.ShoppingCart;
import cn.com.zhihetech.util.hibernate.service.SupportService;

/**
 * Created by ShenYunjie on 2016/1/12.s
 */
public interface IShoppingCartService extends SupportService<ShoppingCart> {
    /**
     * 根据ID删除购物车中的商品
     *
     * @param shoppingCartId
     */
    void deleteById(String shoppingCartId);
}
