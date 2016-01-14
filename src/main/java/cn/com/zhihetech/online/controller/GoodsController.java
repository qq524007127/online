package cn.com.zhihetech.online.controller;

import cn.com.zhihetech.online.bean.*;
import cn.com.zhihetech.online.commons.Constant;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.dao.IGoodsBannerDao;
import cn.com.zhihetech.online.dao.IGoodsDetailDao;
import cn.com.zhihetech.online.service.*;
import cn.com.zhihetech.util.common.StringUtils;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by YangDaiChun on 2015/12/14.
 */
@Controller
public class GoodsController extends SupportController {

    @Resource(name = "goodsService")
    private IGoodsService goodsService;

    @Resource(name = "goodsDetailService")
    private IGoodsDetailService goodsDetailService;

    @Resource(name = "goodsBannerService")
    private IGoodsBannerService goodsBannerService;

    @Resource(name = "merchantService")
    private IMerchantService merchantService;

    @Resource(name = "goodsAttributeSetService")
    private IGoodsAttributeSetService goodsAttributeSetService;

    /**
     * 根据登录的管理员返回对应的商家
     *
     * @param request
     * @return
     */
    private Merchant adminToMerchant(HttpServletRequest request) {
        Admin admin = this.getCurrentAdmin(request);
        Merchant merchant = this.merchantService.getById(admin.getMerchant().getMerchantId());
        return merchant;
    }

    @RequestMapping(value = "admin/goods")
    public String indexPage(HttpServletRequest request) {
        return "admin/goods";
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/goods/list")
    public PageData<Goods> getGoodsPageData(HttpServletRequest request) {
        Merchant merchant = this.adminToMerchant(request);
        IQueryParams queryParams = this.createQueryParams(request);
        queryParams.andEqual("merchant", merchant).andEqual("deleteState", false);
        return this.goodsService.getPageData(this.createPager(request), queryParams);
    }

    @RequestMapping(value = "admin/goods/addView")
    public ModelAndView addGoodsView(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("admin/goodsAdd");
        List<GoodsAttributeSet> goodsAttributeSets = this.goodsAttributeSetService.getAllByParams(createPager(request), createQueryParams(request));
        modelAndView.addObject("goodsAttributeSets", goodsAttributeSets);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/goods/add")
    public ResponseMessage addGoods(Goods goods, String[] goodsBanners,String[] goodsDetails,HttpServletRequest request) {
        Merchant merchant = this.adminToMerchant(request);
        goods.setMerchant(merchant);

        goods.setExaminState(Constant.EXAMINE_STATE_EXAMINED_OK);
        goods.setExaminMsg("审核通过");
        goods.setOnsale(true);
        goods.setIsActivityGoods(false);
        goods.setCreateDate(new Date());
        goods.setDeleteState(false);
        goods.setVolume(0);

        List<GoodsBanner> goodsBanners1 = new LinkedList<GoodsBanner>();
        for(String goodsBanner : goodsBanners){
            String imgInfoId = goodsBanner.substring(0,goodsBanner.indexOf("#"));
            String bannerOrder = goodsBanner.substring(goodsBanner.indexOf("#")+1,goodsBanner.length());
            if(StringUtils.isEmpty(imgInfoId)){
                continue;
            }
            GoodsBanner goodsBanner1 = new GoodsBanner();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setImgInfoId(imgInfoId);
            goodsBanner1.setImgInfo(imgInfo);
            goodsBanner1.setBannerOrder(Integer.parseInt(bannerOrder));
            goodsBanner1.setCreateDate(new Date());
            goodsBanners1.add(goodsBanner1);
        }

        List<GoodsDetail> goodsDetails1 = new LinkedList<GoodsDetail>();
        for(String goodsDetail : goodsDetails){
            String imgInfoId = goodsDetail.substring(0,goodsDetail.indexOf("#"));
            String viewType =goodsDetail.substring(goodsDetail.indexOf("#")+1,goodsDetail.indexOf("*"));
            String viewTarget = goodsDetail.substring(goodsDetail.indexOf("*")+1,goodsDetail.indexOf(","));
            String detailOrder = goodsDetail.substring(goodsDetail.indexOf(",")+1,goodsDetail.length());
            if(StringUtils.isEmpty(imgInfoId)){
                continue;
            }
            GoodsDetail goodsDetail1 = new GoodsDetail();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setImgInfoId(imgInfoId);
            goodsDetail1.setImgInfo(imgInfo);
            goodsDetail1.setViewType(Integer.parseInt(viewType));
            if(!StringUtils.isEmpty(viewType)  && Integer.parseInt(viewType) != 1){
                goodsDetail1.setViewTarget(null);
            }else {
                goodsDetail1.setViewTarget(viewTarget);
            }
            goodsDetail1.setDetailOrder(Integer.parseInt(detailOrder));
            goodsDetails1.add(goodsDetail1);
        }

        this.goodsService.add(goods,goodsBanners1,goodsDetails1);
        return executeResult();
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/goods/updateDeleteState", method = RequestMethod.POST)
    public ResponseMessage updateDeleteState(String goodsId) {
        this.goodsService.updateDeleteState(goodsId);
        return executeResult();
    }

    @RequestMapping(value = "admin/onsalGoods")
    public String onsalGoodsPage(){
        return "admin/goodsOnsal";
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/onsalGoods/list")
    public PageData<Goods> getOnsalGoodsPageData(HttpServletRequest request) {
        Merchant merchant = this.adminToMerchant(request);
        IQueryParams queryParams = this.createQueryParams(request);
        queryParams.andEqual("merchant", merchant).andEqual("deleteState", false).andEqual("onsale",true).andEqual("examinState",Constant.EXAMINE_STATE_EXAMINED_OK);
        return this.goodsService.getPageData(this.createPager(request), queryParams);
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/Goods/updateOnsalState")
    public ResponseMessage updateOnsalState(String goodsId , boolean onsale){
        this.goodsService.updateOnsalState(goodsId,onsale);
        return executeResult();
    }

    @RequestMapping(value = "admin/api/goods/edit/{id}")
    public ModelAndView editGoods(@PathVariable(value = "id") String id,HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("admin/goodsEdit");
        Goods goods = this.goodsService.getById(id);
        List<GoodsAttributeSet> goodsAttributeSets = this.goodsAttributeSetService.getAllByParams(createPager(request), createQueryParams(request));
        modelAndView.addObject("goodsAttributeSets", goodsAttributeSets);
        modelAndView.addObject("goods",goods);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/goods/update")
    public ResponseMessage updateGoods(Goods goods){
        this.goodsService.update(goods);
        return executeResult();
    }

    @RequestMapping(value = "admin/goodsExaminOk")
    public String examinOkPage(){
        return "admin/goodsExaminOk";
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/goods/examinOkList")
    public PageData<Goods> getExaminOkList(HttpServletRequest request) {
        Merchant merchant = this.adminToMerchant(request);
        IQueryParams queryParams = this.createQueryParams(request);
        queryParams.andEqual("merchant", merchant).andEqual("deleteState", false).andEqual("examinState",Constant.EXAMINE_STATE_EXAMINED_OK);
        return this.goodsService.getPageData(this.createPager(request), queryParams);
    }

    @RequestMapping(value = "admin/goodsExaminDismiss")
    public String examinDismissPage(){
        return "admin/goodsExaminDismiss";
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/goods/examinDismissList")
    public PageData<Goods> getExaminDisMissList(HttpServletRequest request) {
        Merchant merchant = this.adminToMerchant(request);
        IQueryParams queryParams = this.createQueryParams(request);
        queryParams.andEqual("merchant", merchant).andEqual("deleteState", false).andEqual("examinState",Constant.EXAMINE_STATE_EXAMINED1_NUOK);
        return this.goodsService.getPageData(this.createPager(request), queryParams);
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/goods/goodsDetail/{goodsId}")
    public ModelAndView goodsDetailInfo(@PathVariable(value = "goodsId")String goodsId){
        Goods goods = this.goodsService.getById(goodsId);
        ModelAndView modelAndView = new ModelAndView("admin/goodsDetailInfo");
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("goods.goodsId",goodsId);
        List<GoodsBanner> goodsBanners= this.goodsBannerService.getAllByParams(null,queryParams);
        List<GoodsDetail> goodsDetails = this.goodsDetailService.getAllByParams(null,queryParams);
        modelAndView.addObject("goods",goods);
        modelAndView.addObject("goodsBanners",goodsBanners);
        modelAndView.addObject("goodsDetails",goodsDetails);
        return modelAndView;
    }
}
