package cn.com.zhihetech.online.controller;

import cn.com.zhihetech.online.bean.ImgInfo;
import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.bean.ShopShow;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.service.IShopShowService;
import cn.com.zhihetech.util.common.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by ShenYunjie on 2016/1/11.
 */
@Controller
@RequestMapping("admin/")
public class ShopShowController extends SupportController {

    @Resource(name = "shopShowService")
    private IShopShowService shopShowService;

    @RequestMapping("merchant/shopshow")
    public String indexPage() {
        return "admin/merchant/shopShow";
    }

    @ResponseBody
    @RequestMapping(value = "api/shopShow/add",method = RequestMethod.POST)
    public ResponseMessage addShopShow(String merchantId,String[] shopShows){


        for(String _shopShow : shopShows){
            String imgId = _shopShow.substring(0,_shopShow.indexOf("#"));
            String showDesc = _shopShow.substring(_shopShow.indexOf("#")+1,_shopShow.length());
            if(StringUtils.isEmpty(imgId)){
                continue;
            }
            ShopShow shopShow = new ShopShow();

            if(StringUtils.isEmpty(showDesc)){
                shopShow.setShowDesc(null);
            }else {
                shopShow.setShowDesc(showDesc);
            }

            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setImgInfoId(imgId);
            Merchant merchant = new Merchant();
            merchant.setMerchantId(merchantId);

            shopShow.setMerchant(merchant);
            shopShow.setImgInfo(imgInfo);

            this.shopShowService.add(shopShow);
        }
        return executeResult();
    }
}
