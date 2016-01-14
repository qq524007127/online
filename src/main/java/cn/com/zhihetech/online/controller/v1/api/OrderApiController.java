package cn.com.zhihetech.online.controller.v1.api;

import cn.com.zhihetech.online.bean.Goods;
import cn.com.zhihetech.online.bean.Order;
import cn.com.zhihetech.online.bean.OrderDetail;
import cn.com.zhihetech.online.commons.Constant;
import cn.com.zhihetech.online.commons.PingPPConfig;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.commons.ResponseStatusCode;
import cn.com.zhihetech.online.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by YangDaiChun on 2016/1/4.
 */
@Controller
public class OrderApiController extends ApiController {


    @Resource(name = "orderService")
    private IOrderService orderService;


    /**
     * 提交订单
     * URL: api/order/add
     *
     * 参数:<br>
     *      orderName: 订单名 <br>
     *      user.userId: 用户ID <br>
     *      orderTotal:订单金额<br>
     *      userMsg:给商家留言 <br>
     *      receiverName:签收人名<br>
     *      receiverPhone:签收人电话号码<br>
     *      receiverAdd:签收人地址<br>
     *      goodsIds:订单中包含的多个商品的商品Id,一个订单中可以包含一个商家的多个商品<br>
     *
     * @param order
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "order/add",method = RequestMethod.POST)
    public Order addOrder(Order order, String[] orderDetailInfos){
        long currentTime = System.currentTimeMillis();
        StringBuffer stringBuffer = new StringBuffer(currentTime+"");
        stringBuffer.append(this.get5RandomNum());

        order.setOrderCode(stringBuffer.toString());
        order.setCreateDate(new Date());
        order.setOrderState(Constant.ORDER_STATE_NO_PAYMENT);
        order.setPayDate(null);
        order.setPayType(PingPPConfig.PAY_NULL_CODE);

        Set<OrderDetail> _orderDetails = new HashSet<OrderDetail>();
        for(String orderDetailInfo : orderDetailInfos){
            String goodsId = orderDetailInfo.substring(0, orderDetailInfo.indexOf("#"));
            String goodsCount = orderDetailInfo.substring(orderDetailInfo.indexOf("#")+1,orderDetailInfo.length());

            OrderDetail _orderDetail = new OrderDetail();
            Goods goods = new Goods();
            goods.setGoodsId(goodsId);
            _orderDetail.setGoods(goods);
            _orderDetail.setCount(Long.parseLong(goodsCount));
            _orderDetails.add(_orderDetail);
        }
        Order order1 = this.orderService.addAndGet(order,_orderDetails);
        return order1;
    }
}
