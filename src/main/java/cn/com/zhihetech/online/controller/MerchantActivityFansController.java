package cn.com.zhihetech.online.controller;

import cn.com.zhihetech.online.bean.ActivityFans;
import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.bean.User;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.exception.SystemException;
import cn.com.zhihetech.online.service.IActivityFansService;
import cn.com.zhihetech.online.service.IAdminService;
import cn.com.zhihetech.online.service.IUserService;
import cn.com.zhihetech.util.common.StringUtils;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by ShenYunjie on 2015/12/15.
 */
@Controller
public class MerchantActivityFansController extends SupportController {
    @Resource(name = "adminService")
    private IAdminService adminService;
    @Resource(name = "activityFansService")
    private IActivityFansService activityFansService;
    @Resource(name = "userService")
    private IUserService userService;

    @RequestMapping(value = "admin/merchActivity/{activityId}/fans")
    public ModelAndView indexPage(HttpServletRequest request, @PathVariable String activityId) {
        if (StringUtils.isEmpty(activityId)) {
            throw new SystemException("查询参数不足");
        }
        ModelAndView modelAndView = new ModelAndView("admin/merchAcitvityFans");
        Merchant merchant = this.adminService.getMerchant(getCurrentAdmin(request));
        modelAndView.addObject("merchant", merchant).addObject("activityId", activityId);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "admin/merchActivity/{activityId}/fans/list")
    public PageData<ActivityFans> getActivityFansByCurrentAdmin(HttpServletRequest request, @PathVariable String activityId) {
        if (StringUtils.isEmpty(activityId)) {
            throw new SystemException("查询参数不足");
        }
        String merchantId = this.adminService.getMerchantId(getCurrentAdmin(request));
        IQueryParams queryParams = createQueryParams(request).andEqual("invitationMerch.merchantId", merchantId).andEqual("activity.activitId", activityId);
        return this.activityFansService.getPageData(createPager(request), queryParams);
    }

    @ResponseBody
    @RequestMapping(value = "admin/merchActivity/fans/add")
    public ResponseMessage addActivityFans(ActivityFans activityFans) {
        activityFans.setJoinDate(new Date());
        this.activityFansService.add(activityFans);
        return executeResult();
    }

    @ResponseBody
    @RequestMapping(value = "admin/merchActivity/fans/delete")
    public ResponseMessage deleteActivityFans(ActivityFans activityFans) {
        this.activityFansService.delete(activityFans);
        return executeResult();
    }

    @ResponseBody
    @RequestMapping(value = "admin/merchActivity/{activityId}/ableUsers")
    public PageData<User> getAbleUserByActivityId(HttpServletRequest request, @PathVariable String activityId) {
        if (StringUtils.isEmpty(activityId)) {
            throw new SystemException("查询参数不足");
        }
        String merchantId = this.adminService.getMerchantId(getCurrentAdmin(request));
        IQueryParams queryParams = createQueryParams(request);
        return this.userService.getAbleUserByActivityIdAndMerchantId(createPager(request), queryParams, activityId, merchantId);
    }
}
