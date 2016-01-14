package cn.com.zhihetech.online.controller;


import cn.com.zhihetech.online.bean.Activity;
import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.bean.RedEnvelop;
import cn.com.zhihetech.online.commons.Constant;
import cn.com.zhihetech.online.service.IActivityService;
import cn.com.zhihetech.online.service.IMerchantAllianceService;
import cn.com.zhihetech.online.service.IRedEnvelopService;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by ShenYunjie on 2015/12/9.
 */
@Controller
public class ActivityController extends SupportController {

    @Resource(name = "activityService")
    private IActivityService activityService;
    @Resource(name = "merchantAllianceService")
    private IMerchantAllianceService merchantAllianceService;
    @Resource(name = "redEnvelopService")
    private IRedEnvelopService redEnvelopService;
    @RequestMapping(value = "admin/activity")
    public String pageIndex() {
        return "admin/activityList";
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/activity/list")
    public PageData<Activity> getAllActivityList(HttpServletRequest request) {
        return this.activityService.getPageData(createPager(request), createQueryParams(request));
    }

    @RequestMapping(value = "admin/activity/info/{id}")
    public ModelAndView activityInfo(@PathVariable(value = "id") String id, HttpServletRequest request) {
        Activity activity = this.activityService.getById(id);
        IQueryParams iQueryParams = createQueryParams(request);
        iQueryParams.andEqual("activity.activitId", activity.getActivitId());
        PageData<Merchant> merchants = this.merchantAllianceService.getJoinMerchByActivity(createPager(request),iQueryParams);
        Map<String ,List<RedEnvelop>>redEnvelops=this.redEnvelopService.getAbleRedEnvelopByMerch(createPager(request),merchants.getRows(),activity.getActivitId());
        ModelAndView model = new ModelAndView("admin/merchantAliaceInfo");
        model.addObject("merchants", merchants);
        model.addObject("redEnvelops",redEnvelops);
        model.addObject("activity", activity);
        return model;
    }

}
