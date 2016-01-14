package cn.com.zhihetech.online.controller;

import cn.com.zhihetech.online.bean.Banner;
import cn.com.zhihetech.online.bean.ImgInfo;
import cn.com.zhihetech.online.commons.Constant;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.service.IBannerService;
import cn.com.zhihetech.util.common.StringUtils;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/16.
 */
@Controller
public class BannerController extends SupportController {
    @Resource(name = "bannerService")
    private IBannerService iBannerService;

    @RequestMapping(value = "admin/banner")
    public String bannerPage() {
        return "admin/banner";
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/banner/list")
    public PageData<Banner> getPageDate(HttpServletRequest request) {
        return this.iBannerService.getPageData(createPager(request), createQueryParams(request));
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/banner/add")
    public ResponseMessage addBanner(Banner banner) {
        if (banner.getBannerType() != Constant.BANNER_MAIN  || banner.getViewType() != Constant.BANNER_VIEWTYPE_TARGET) {
            banner.setViewTargert(null);
        }
        banner.setCreateDate(new Date());
        this.iBannerService.add(banner);
        return executeResult();
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/banner/delete", method = RequestMethod.POST)
    public ResponseMessage deleteBanner(String bannerId, String imgInfoId) {
        this.iBannerService.deleteBannerAndImg(bannerId, imgInfoId);
        return executeResult();
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/banner/update")
    public ResponseMessage updateBanner(Banner banner) {
        banner.setCreateDate(new Date());
        this.iBannerService.update(banner);
        return executeResult();
    }
}
