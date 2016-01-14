package cn.com.zhihetech.online.controller;

import cn.com.zhihetech.online.bean.Menu;
import cn.com.zhihetech.online.service.IAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ShenYunjie on 2015/11/17.
 */
@Controller
public class IndexController extends SupportController {

    @Resource(name = "adminService")
    private IAdminService adminService;

    @RequestMapping("admin/index")
    public ModelAndView indexPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("admin/index");
        if(getCurrentAdmin(request) == null){
            modelAndView.setViewName("redirect:/admin/login");
            return modelAndView;
        }
        List<Menu> menus = this.adminService.getMenusByAndmin(getCurrentAdmin(request));
        modelAndView.addObject("menus",menus);
        return modelAndView;
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcomePage(HttpServletRequest request) {
        return "admin/welcome";
    }

    @RequestMapping("/jeasyui")
    public String jeasyuiPage(){
        return "merchant";
    }
}
