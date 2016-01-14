package cn.com.zhihetech.online.controller.v1.api;

import cn.com.zhihetech.online.bean.Activity;
import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.commons.Constant;
import cn.com.zhihetech.online.commons.QiniuConfig;
import cn.com.zhihetech.online.service.IActivityService;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.SubQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.param.SubParam;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2015/12/22.
 */
@Controller
public class ActivityApiController extends ApiController {
    @Resource(name = "activityService")
    private IActivityService iActivityService;

    @ResponseBody
    @RequestMapping(value = "activity/list")
    public PageData<Activity> getActiviting(HttpServletRequest request) {
        IQueryParams iQueryParams = this.createQueryParams(request);
        SubQueryParams subQuery = new SubQueryParams();
        subQuery.orEqual("currentState", Constant.ACTIVITY_STATE_STARTED).orEqual("currentState", Constant.ACTIVITY_STATE_EXAMINED_OK);
        iQueryParams.andSubParams(subQuery);
        PageData<Activity> data = this.iActivityService.getPageData(createPager(request), iQueryParams);
        for (Activity activity : data.getRows()) {
            String merchantId = activity.getActivityPromoter().getMerchantId();
            Merchant merchant = new Merchant();
            merchant.setMerchantId(merchantId);
            activity.setActivityPromoter(merchant);
        }
        return data;
    }
}
