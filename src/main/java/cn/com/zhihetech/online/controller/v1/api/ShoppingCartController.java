package cn.com.zhihetech.online.controller.v1.api;

import cn.com.zhihetech.online.bean.ShoppingCart;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.exception.ParamException;
import cn.com.zhihetech.online.service.IShoppingCartService;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by ShenYunjie on 2016/1/12.
 */
@Controller
public class ShoppingCartController extends ApiController {
    @Resource(name = "shoppingCartService")
    private IShoppingCartService shoppingCartService;

    /**
     * <h3>将商品添加到购物车</h3>
     * <p>
     * url:api/shoppingCart/add
     * <ul>
     * <li>参数</li>
     * <li>
     * user.userId 用户ID,不能为空
     * </li>
     * <li>
     * goods.goodsId  商品ID,不能为空
     * </li>
     * <li>
     * count 添加到购物车的商品数量，必须大于0
     * </li>
     * </ul>
     * </p>
     *
     * @param shoppingCart
     * @return
     */
    @RequestMapping(value = "shoppingCart/add", method = RequestMethod.POST)
    public ResponseMessage addShopCart(ShoppingCart shoppingCart) {
        if (shoppingCart.getCount() <= 0) {
            throw new ParamException("参数错误");
        }
        this.shoppingCartService.add(shoppingCart);
        return executeResult();
    }

    /**
     * <h3>查询用户的购物车</h3>
     * <p>
     * URL:api/user/{userId}/shoppingCarts  其中{userId}为当前用户ID
     * <ul>
     * <li>分页参数</li>
     * </ul>
     * </p>
     *
     * @param request
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "user/{userId}/shoppingCarts")
    public PageData<ShoppingCart> getShoppingCartByUserId(HttpServletRequest request, @PathVariable("userId") @NotNull String userId) {
        IQueryParams queryParams = createQueryParams(request);
        queryParams.andEqual("user.userId", userId);
        return this.shoppingCartService.getPageData(createPager(request), queryParams);
    }

    /**
     * <h3>删除购物车数据</h3>
     * <p>
     * url:api/shoppingCart/{shoppingCartId}/delete
     * {shoppingCartId}    购物车数据ID
     * </p>
     *
     * @param shoppingCartId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "shoppingCart/{shoppingCartId}/delete")
    public ResponseMessage deleteShopCart(@PathVariable("shoppingCartId") @NotNull String shoppingCartId) {
        this.shoppingCartService.deleteById(shoppingCartId);
        return executeResult();
    }
}
