package cn.com.zhihetech.online.controller;

import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.bean.MerchantAlliance;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.service.IMerchantAllianceService;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ShenYunjie on 2015/12/10.
 */
@Controller
public class MerchantAllianceController extends SupportController {

    @Resource(name = "merchantAllianceService")
    private IMerchantAllianceService merchantAllianceService;

    @RequestMapping("admin/activity/{activId}/merchAlliance")
    public ModelAndView pageIndex(@PathVariable String activId) {
        ModelAndView mv = new ModelAndView("admin/merchantAlliance");
        Map queryParam = new HashMap<>();
        queryParam.put("activitId", activId);
        mv.addObject("currentActivity", queryParam);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/activity/merchAlliance/list")
    public PageData<MerchantAlliance> getMerchAllianceByActivity(HttpServletRequest request, String activitId) {
        return this.merchantAllianceService.getMerchAllianceByActivity(createPager(request), createQueryParams(request), activitId);
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/merchAlliance/ableList")
    public PageData<Merchant> getAbleMerchByActivity(HttpServletRequest request, String activitId) {
        return this.merchantAllianceService.getAbleMerchByActivity(createPager(request), createQueryParams(request), activitId);
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/merchAlliance/add")
    public ResponseMessage addMerchAlliance(MerchantAlliance merchantAlliance) {
        merchantAlliance.setAgreed(true);
        merchantAlliance.setCreateDate(new Date());
        this.merchantAllianceService.add(merchantAlliance);
        return executeResult();
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/merchAlliance/delete")
    public ResponseMessage deleteMerchAlliance(MerchantAlliance merchantAlliance) {
        this.merchantAllianceService.delete(merchantAlliance);
        return executeResult();
    }
}
