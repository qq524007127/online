package cn.com.zhihetech.online.controller.v1.api;

import cn.com.zhihetech.online.bean.Goods;
import cn.com.zhihetech.online.bean.GoodsBanner;
import cn.com.zhihetech.online.bean.GoodsDetail;
import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.commons.Constant;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.commons.ResponseStatusCode;
import cn.com.zhihetech.online.dao.IGoodsBannerDao;
import cn.com.zhihetech.online.service.*;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.Order;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by YangDaiChun on 2016/1/1.
 */
@Controller
public class GoodsApiController extends ApiController {

    @Resource(name = "merchantService")
    private IMerchantService merchantService;

    @Resource(name = "goodsService")
    private IGoodsService goodsService;

    @Resource(name = "goodsDetailService")
    private IGoodsDetailService goodsDetailService;

    @Resource(name = "goodsBannerService")
    private IGoodsBannerService goodsBannerService;

    /**
     * 获取商家的商品信息
     * URL ：api/goodses/{merchantId}
     * <p>
     * {merchantId}:商家id
     * <p>
     * 参数：
     * page: 第几页
     * rows:每页多少条
     * 不传参数默认为获取第一页的20条数据
     *
     * @param merchantId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "goodses/{merchantId}")
    public PageData<Goods> getGoodListByMerchantId(@PathVariable(value = "merchantId") String merchantId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Merchant merchant = null;
        merchant = this.merchantService.getById(merchantId);
        if (merchant == null) {
            ResponseMessage responseMessage = new ResponseMessage();
            responseMessage.setCode(ResponseStatusCode.PAGE_NOT_FOUND_CODE);
            responseMessage.setMsg("未找到对应商家的有关商品信息");
            responseMessage.setSuccess(false);
            String json = JSONObject.toJSONString(responseMessage);
            response.setContentType("*/*;charset=UTF-8");
            response.getWriter().write(json);
            return null;
        }
        IQueryParams queryParams = this.createQueryParams(request);
        long goodsNum = 0;
        queryParams.andEqual("merchant", merchant).andEqual("deleteState", false).andEqual("onsale", true).andMoreThan("stock", goodsNum);
        if (!queryParams.sortContainsKey("createDate")) {
            queryParams.sort("createDate", Order.DESC);
        }
        return this.goodsService.getPageData(this.createPager(request), queryParams);
    }

    /**
     * <h2>获取所有商品</h2>
     *
     * URL: api/goodses <br>
     *
     * 参数： <br>
     *  searchName: 搜索的属性名,如goodsName
     * searchValue:   搜索的属性值，如 '"沙发"
     * page: 第几页  <br>
     * rows:每页多少条  <br>
     * 不传参数默认为获取第一页的20条数据  <br>
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "goodses")
    public PageData<Goods> getAllGoods(HttpServletRequest request){
        IQueryParams queryParams = this.createQueryParams(request);
        long goodsNum = 0;
        queryParams.andEqual("examinState", Constant.EXAMINE_STATE_EXAMINED_OK).andEqual("deleteState", false).andEqual("onsale", true).andMoreThan("stock", goodsNum);
        if (!queryParams.sortContainsKey("createDate")) {
            queryParams.sort("createDate", Order.DESC);
        }
        return this.goodsService.getPageData(this.createPager(request), queryParams);
    }


    /**
     * 根据商品ID获取商品的详细信息
     *
     * URL:  api/goods/{goodsId}
     *
     * {goodsId}:商品id
     *
     * @param goodsId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "goods/{goodsId}")
    public ResponseMessage getGoodsByGoodsId(@PathVariable(value = "goodsId")String goodsId){
        Goods goods = this.goodsService.getById(goodsId);
        if( (goods == null) || (goods.getExaminState() != Constant.EXAMINE_STATE_EXAMINED_OK) ){
            return executeResult(ResponseStatusCode.PAGE_NOT_FOUND_CODE,"对不起，找不到对应的商品!");
        }

        if(goods.getDeleteState() ||  !goods.isOnsale()){
            return executeResult(ResponseStatusCode.NOT_ONSAL,"对不起！该商品已经下架了！");
        }

        if(goods.getCurrentStock() <= 0){
            return executeResult(ResponseStatusCode.NO_STOCK,"对不起！商品已经被抢光了！");
        }
        return executeResult(ResponseStatusCode.SUCCESS_CODE,"",goods);
    }

    /**
     *  根据商品Id获取商品的轮播图
     *  URL: api/goodsBanners/{goodsId}
     * {goodsId}:商品Id
     *
     * @param goodsId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "goodsBanners/{goodsId}")
    public List<GoodsBanner> getGoodsBannersByGoosId(@PathVariable(value = "goodsId")String goodsId){
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("goods.goodsId",goodsId).sort("bannerOrder",Order.ASC);
        return this.goodsBannerService.getAllByParams(null,queryParams);
    }

    /**
     *  根据商品Id获取商品的详情图
     *  URL: api/goodsDetails/{goodsId}
     * {goodsId}:商品Id
     *
     * @param goodsId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "goodsDetails/{goodsId}")
    public List<GoodsDetail> getGoodsDetailsByGoodsId(@PathVariable(value = "goodsId")String goodsId){
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("goods.goodsId",goodsId).sort("detailOrder",Order.ASC);
        return this.goodsDetailService.getAllByParams(null,queryParams);
    }

}
