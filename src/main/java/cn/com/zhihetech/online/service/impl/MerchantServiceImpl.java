package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.*;
import cn.com.zhihetech.online.commons.Constant;
import cn.com.zhihetech.online.dao.*;
import cn.com.zhihetech.online.service.IMerchantService;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.Order;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ShenYunjie on 2015/11/20.
 */
@Service("merchantService")
public class MerchantServiceImpl implements IMerchantService {

    @Resource(name = "merchantDao")
    private IMerchantDao merchantDao;

    @Resource(name = "adminDao")
    private IAdminDao adminDao;

    @Resource(name = "goodsDao")
    private IGoodsDao goodsDao;

    @Resource(name = "merchantAllianceDao")
    private IMerchantAllianceDao merchantAllianceDao;

    @Resource(name = "activityDao")
    private IActivityDao activityDao;

    @Resource(name = "imgInfoDao")
    private IImgInfoDao imgInfoDao;

    @Override
    public Merchant getById(String id) {
        return this.merchantDao.findEntityById(id);
    }

    @Override
    public void delete(Merchant merchant) {
        this.merchantDao.deleteEntity(merchant);
    }

    @Override
    public Merchant add(Merchant merchant) {
        return this.merchantDao.saveEntity(merchant);
    }

    @Override
    public void update(Merchant merchant) {
        this.merchantDao.updateEntity(merchant);
    }


    @Override
    public List<Merchant> getAllByParams(Pager pager, IQueryParams queryParams) {
        return this.merchantDao.getEntities(pager, queryParams);
    }

    @Override
    public PageData<Merchant> getPageData(Pager pager, IQueryParams queryParams) {
        return this.merchantDao.getPageData(pager, queryParams);
    }


    @Override
    public void updateCoverImg(Merchant merchant) {
        ImgInfo imgInfo = this.imgInfoDao.findEntityById(merchant.getCoverImg().getImgInfoId());
        Map paramAndValue = new HashMap();
        paramAndValue.put("coverImg",imgInfo);
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("merchantId",merchant.getMerchantId());
        this.merchantDao.executeUpdate(paramAndValue,queryParams);
    }

    @Override
    public void updateHeadImg(Merchant merchant) {
        ImgInfo imgInfo = this.imgInfoDao.findEntityById(merchant.getHeaderImg().getImgInfoId());
        Map paramAndValue = new HashMap();
        paramAndValue.put("headerImg",imgInfo);
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("merchantId",merchant.getMerchantId());
        this.merchantDao.executeUpdate(paramAndValue,queryParams);
    }

    @Override
    public void updateExaminStateOk(Merchant merchant, Admin admin) {
        Merchant _merchant = this.merchantDao.findEntityById(merchant.getMerchantId());
        _merchant.setExaminState(Constant.EXAMINE_STATE_EXAMINED_OK);
        _merchant.setPermit(true);
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("merchant.merchantId", merchant.getMerchantId());
        List<Admin> admins = this.adminDao.getEntities(queryParams);
        Admin _admin = admins.get(0);
        _admin.setPermit(admin.isPermit());
        _admin.setRoles(admin.getRoles());
        _admin.setSuperAdmin(admin.isSuperAdmin());
        this.adminDao.updateEntity(_admin);
        this.merchantDao.updateEntity(_merchant);
    }

    @Override
    public void updateExaminStateDissmiss(String merchantId, String examinMsg) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("examinMsg", examinMsg);
        param.put("examinState", Constant.EXAMINE_STATE_EXAMINED1_NUOK);
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("merchantId", merchantId);
        this.merchantDao.executeUpdate(param, queryParams);
    }

    @Override
    public void addMerchantAndAdmin(Merchant merchant, Admin admin) {
        this.merchantDao.saveEntity(merchant);
        this.adminDao.saveEntity(admin);
    }

    @Override
    public void editMerchantInfoForm(Merchant merchant) {
        this.merchantDao.updateEntity(merchant);
    }

    @Override
    public PageData<Merchant> getMerchantAndGoods(Pager pager, IQueryParams queryParams, int goodsNum) {
        PageData<Merchant> merchantPageData = this.merchantDao.getPageData(pager, queryParams);
        List<Merchant> merchants = merchantPageData.getRows();
        Iterator<Merchant> iterator = merchants.iterator();
        while (iterator.hasNext()) {
            Merchant merchant = iterator.next();
            IQueryParams queryParams1 = new GeneralQueryParams();
            queryParams1.andEqual("merchant", merchant).andEqual("deleteState", false).andEqual("onsale", true).andEqual("examinState", Constant.EXAMINE_STATE_EXAMINED_OK).sort("createDate", Order.DESC);
            Pager pager1 = new Pager(1, goodsNum);
            merchant.setGoodsNum(this.goodsDao.getRecordTotal(queryParams1));
            List<Goods> goodses = this.goodsDao.getEntities(pager1, queryParams1);
            if (goodses != null) {
                for (Goods goods : goodses) {
                    goods.setMerchant(null);
                }
            }
            merchant.setRecommendGoodses(goodses);

            IQueryParams queryParams2 = new GeneralQueryParams();
            queryParams2.andEqual("merchant",merchant);
            List<Object> activits = this.merchantAllianceDao.getProperty("distinct activity.activitId", null, queryParams2);
            IQueryParams queryParams3  = new GeneralQueryParams();
            /*===================该方法存在性能问题======================*/
            queryParams3.andIn("activitId", activits).andEqual("currentState",Constant.ACTIVITY_STATE_STARTED);
            if(this.activityDao.getRecordTotal(queryParams3) > 0){
                merchant.setIsActivating(true);
            }else {
                merchant.setIsActivating(false);
            }
        }
        return merchantPageData;
    }

    @Override
    public void updatePermit(String merchantId, boolean permit) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("permit", permit);
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("merchantId", merchantId);
        this.merchantDao.executeUpdate(param, queryParams);
        queryParams = new GeneralQueryParams();
        queryParams.andEqual("merchant.merchantId", merchantId);
        this.adminDao.executeUpdate(param, queryParams);
    }
}
