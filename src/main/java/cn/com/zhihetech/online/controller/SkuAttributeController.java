package cn.com.zhihetech.online.controller;

import cn.com.zhihetech.online.bean.GoodsAttributeSet;
import cn.com.zhihetech.online.bean.SkuAttribute;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.commons.ResponseStatusCode;
import cn.com.zhihetech.online.service.IGoodsAttributeSetService;
import cn.com.zhihetech.online.service.ISkuAttributeService;
import cn.com.zhihetech.util.common.MD5Utils;
import cn.com.zhihetech.util.common.StringUtils;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.channels.SelectionKey;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by YangDaiChun on 2015/12/2.
 */
@Controller
public class SkuAttributeController extends SupportController {

    @Resource(name = "skuAttributeService")
    private ISkuAttributeService skuAttributeService;

    @Resource(name = "goodsAttributeSetService")
    private IGoodsAttributeSetService goodsAttributeSetService;

    @RequestMapping(value = "admin/skuAttribute")
    public ModelAndView indexPage() {
        List<GoodsAttributeSet> goodsAttributeSets = this.goodsAttributeSetService.getAllByParams(null, null);
        ModelAndView modelAndView = new ModelAndView("admin/skuAttribute");
        modelAndView.addObject("goodsAttSets", goodsAttributeSets);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/skuAttribute/list")
    public PageData<SkuAttribute> getSkuAttributePageData(HttpServletRequest request) {
        return this.skuAttributeService.getPageData(this.createPager(request), this.createQueryParams(request));
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/skuAttribute/add", method = RequestMethod.POST)
    public ResponseMessage addSkuAttribute(SkuAttribute skuAttribute, String[] goodsAttSetIds) {
        if (StringUtils.isEmpty(skuAttribute.getParentySkuAttribute().getSkuAttId())) {
            skuAttribute.setParentySkuAttribute(null);
        } else {
            SkuAttribute parentSkuAttribute = this.skuAttributeService.getById(skuAttribute.getParentySkuAttribute().getSkuAttId());
            Iterator<GoodsAttributeSet> goodsAttributeSetIterator = parentSkuAttribute.getGoodsAttributeSets().iterator();
            Set<GoodsAttributeSet> goodsAttributeSets = new HashSet<GoodsAttributeSet>();
            while (goodsAttributeSetIterator.hasNext()) {
                GoodsAttributeSet goodsAttributeSet = new GoodsAttributeSet();
                goodsAttributeSet.setGoodsAttSetId(goodsAttributeSetIterator.next().getGoodsAttSetId());
                goodsAttributeSets.add(goodsAttributeSet);
            }
            skuAttribute.setGoodsAttributeSets(goodsAttributeSets);
        }
        if (goodsAttSetIds != null) {
            Set<GoodsAttributeSet> goodsAttributeSets = new HashSet<GoodsAttributeSet>();
            for (String goodsAttSetId : goodsAttSetIds) {
                GoodsAttributeSet goodsAttributeSet = new GoodsAttributeSet();
                goodsAttributeSet.setGoodsAttSetId(goodsAttSetId);
                goodsAttributeSets.add(goodsAttributeSet);
            }
            skuAttribute.setGoodsAttributeSets(goodsAttributeSets);
        }
        String skuCode = MD5Utils.getMD5Msg(skuAttribute.getSkuAttName()).substring(0, 8);
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("skuAttCode", skuCode);
        if (!(this.skuAttributeService.getAllByParams(null, queryParams)).isEmpty()) {
            return this.executeResult(ResponseStatusCode.SYSTEM_ERROR_CODE, "添加失败，该sku属性已经存在！");
        }
        skuAttribute.setSkuAttCode(skuCode);
        this.skuAttributeService.add(skuAttribute);
        return this.executeResult();
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/skuAttribute/edit", method = RequestMethod.POST)
    public ResponseMessage editSkuAttribute(SkuAttribute skuAttribute, String[] goodsAttSetIds) {
        if (StringUtils.isEmpty(skuAttribute.getParentySkuAttribute().getSkuAttId())) {
            skuAttribute.setParentySkuAttribute(null);
        } else {
            SkuAttribute parentSkuAttribute = this.skuAttributeService.getById(skuAttribute.getParentySkuAttribute().getSkuAttId());
            Iterator<GoodsAttributeSet> goodsAttributeSetIterator = parentSkuAttribute.getGoodsAttributeSets().iterator();
            Set<GoodsAttributeSet> goodsAttributeSets = new HashSet<GoodsAttributeSet>();
            while (goodsAttributeSetIterator.hasNext()) {
                GoodsAttributeSet goodsAttributeSet = new GoodsAttributeSet();
                goodsAttributeSet.setGoodsAttSetId(goodsAttributeSetIterator.next().getGoodsAttSetId());
                goodsAttributeSets.add(goodsAttributeSet);
            }
            skuAttribute.setGoodsAttributeSets(goodsAttributeSets);
        }
        if (goodsAttSetIds != null) {
            Set<GoodsAttributeSet> goodsAttributeSets = new HashSet<GoodsAttributeSet>();
            for (String goodsAttSetId : goodsAttSetIds) {
                GoodsAttributeSet goodsAttributeSet = new GoodsAttributeSet();
                goodsAttributeSet.setGoodsAttSetId(goodsAttSetId);
                goodsAttributeSets.add(goodsAttributeSet);
            }
            Set<SkuAttribute> childSkuAttributes = this.skuAttributeService.getById(skuAttribute.getSkuAttId()).getChildSkuAttributes();
            Iterator<SkuAttribute> skuAttributeIterator = childSkuAttributes.iterator();
            while (skuAttributeIterator.hasNext()) {
                SkuAttribute childSkuAttribute = skuAttributeIterator.next();
                childSkuAttribute.setGoodsAttributeSets(goodsAttributeSets);
                this.skuAttributeService.update(childSkuAttribute);
            }
            skuAttribute.setGoodsAttributeSets(goodsAttributeSets);
        }
        String skuCode = MD5Utils.getMD5Msg(skuAttribute.getSkuAttName()).substring(0, 8);
        skuAttribute.setSkuAttCode(skuCode);
        this.skuAttributeService.update(skuAttribute);
        return this.executeResult();
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/skuAttribute/rootList")
    public List<SkuAttribute> getParentSkuAttribute(String skuAttributeId) {
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andIsNull("parentySkuAttribute");
        if (!StringUtils.isEmpty(skuAttributeId)) {
            queryParams.andNotEq("skuAttId", skuAttributeId);
        }
        List<SkuAttribute> l = this.skuAttributeService.getAllByParams(null, queryParams);
        return l;
    }

}
