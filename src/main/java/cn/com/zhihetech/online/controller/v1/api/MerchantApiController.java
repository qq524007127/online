package cn.com.zhihetech.online.controller.v1.api;

import cn.com.zhihetech.online.bean.Goods;
import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.commons.Constant;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.commons.ResponseStatusCode;
import cn.com.zhihetech.online.service.IMerchantService;
import cn.com.zhihetech.util.common.StringUtils;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.Order;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by YangDaiChun on 2015/12/26.
 */
@Controller
public class MerchantApiController extends ApiController {

    @Resource(name = "merchantService")
    private IMerchantService merchantService;

    /**
     * 获取商家详细信息
     *
     * <p>
     * URL : /api/merchant/{id}/get
     * <p>
     * {id}商家ID
     *
     * @param merchantId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "merchant/{id}")
    public Merchant getMerchantDetails(@PathVariable(value = "id")String merchantId,HttpServletResponse response) throws IOException {
        Merchant merchant = null;
        merchant = this.merchantService.getById(merchantId);
        if(merchant == null){
            ResponseMessage responseMessage = new ResponseMessage();
            responseMessage.setCode(ResponseStatusCode.PAGE_NOT_FOUND_CODE);
            responseMessage.setMsg("未找到对应的商家信息");
            responseMessage.setSuccess(false);
            String json = JSONObject.toJSONString(responseMessage);
            response.setContentType("*/*;charset=UTF-8");
            response.getWriter().write(json);
            return null;
        }
        return merchant;
    }

    /**
     * <h2>获取所有商家</h2>
     *
     * URL: api/merchants<br>
     *
     * 参数： <br>
     *  searchName: 搜索的属性名,如merchantName
     * searchValue:   搜索的属性值，如 '"挚合"
     * page: 第几页  <br>
     * rows:每页多少条  <br>
     * 不传参数默认为获取第一页的20条数据  <br>
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "merchants")
    public PageData<Merchant> getAllMerchants(HttpServletRequest request,@RequestParam(value = "goodsNum", required = false, defaultValue = "3") int goodsNum){
        IQueryParams queryParams = this.createQueryParams(request);;
        queryParams.andEqual("examinState",Constant.EXAMINE_STATE_EXAMINED_OK);
        if (!queryParams.sortContainsKey("createDate")) {
            queryParams.sort("createDate", Order.DESC);
        }
        return this.merchantService.getMerchantAndGoods(this.createPager(request),queryParams,goodsNum);
    }

}
