package cn.com.zhihetech.online.controller;

import cn.com.zhihetech.online.bean.Admin;
import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.bean.Order;
import cn.com.zhihetech.online.dao.IOrderDetailDao;
import cn.com.zhihetech.online.service.IAdminService;
import cn.com.zhihetech.online.service.IOrderDetailService;
import cn.com.zhihetech.online.service.IOrderService;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by YangDaiChun on 2016/1/5.
 */
@Controller
public class OrderController extends SupportController{

    @Resource(name = "orderService")
    private IOrderService orderService;

    @Resource(name = "orderDeatilService")
    private IOrderDetailService orderDetailService;

    @Resource(name = "adminService")
    private IAdminService adminService;

    @RequestMapping(value = "/admin/order")
    public String indexPage(){
        return "admin/order";
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/order/list")
    public PageData<Order> getAllOrderPageData(HttpServletRequest request){
        Admin admin = this.getCurrentAdmin(request);
        Merchant merchant = this.adminService.getMerchant(admin);
        return this.orderDetailService.getOrderPageData(this.createPager(request),this.createQueryParams(request),merchant);
    }
}
