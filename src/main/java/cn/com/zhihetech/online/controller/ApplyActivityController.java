package cn.com.zhihetech.online.controller;

import cn.com.zhihetech.online.bean.Activity;
import cn.com.zhihetech.online.bean.ImgInfo;
import cn.com.zhihetech.online.bean.RedEnvelop;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.service.IActivityService;
import cn.com.zhihetech.util.common.StringUtils;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by ShenYunjie on 2015/12/7.
 */
@Controller
public class ApplyActivityController extends SupportController {
    @Resource(name = "activityService")
    private IActivityService activityService;

    @RequestMapping(value = "admin/applyActivity")
    public String pageIndex() {
        return "admin/applyActivity";
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/applyActivity/list")
    public PageData<Activity> getPageData(HttpServletRequest request) {
        return this.activityService.getPageDataByAdmin(createPager(request), createQueryParams(request), getCurrentAdmin(request));
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/activity/apply", method = RequestMethod.POST)
    public ResponseMessage saveOrUpdate(Activity activity, RedEnvelop redEnvelop,HttpServletRequest request) {
        if (StringUtils.isEmpty(activity.getActivitId())) {
            activity.setActivitId(null);
        }
        if(StringUtils.isEmpty(redEnvelop.getEnvelopId())){
            redEnvelop.setEnvelopId(null);
        }

        this.activityService.saveByAdmin(activity,redEnvelop,getCurrentAdmin(request));
        return executeResult();
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/activity/editApply", method = RequestMethod.POST)
    public ResponseMessage editApply(Activity activity) {
        this.activityService.update(activity);
        return executeResult();
    }
}
