package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.*;
import cn.com.zhihetech.online.commons.Constant;
import cn.com.zhihetech.online.dao.IMerchantAllianceDao;
import cn.com.zhihetech.online.exception.SystemException;
import cn.com.zhihetech.online.service.*;
import cn.com.zhihetech.util.common.StringUtils;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.Order;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShenYunjie on 2015/12/8.
 */
@Service("merchantAllianceService")
public class MerchantAllianceServiceImpl implements IMerchantAllianceService {
    @Resource(name = "merchantAllianceDao")
    private IMerchantAllianceDao merchantAllianceDao;
    @Resource(name = "merchantService")
    private IMerchantService merchantService;
    @Resource(name = "adminService")
    private IAdminService adminService;
    @Resource(name = "activityService")
    private IActivityService activityService;
    @Resource(name = "redEnvelopService")
    private IRedEnvelopService redEnvelopService;

    /**
     * 根据ID获取持久化对象
     *
     * @param id 对象ID
     * @return
     */
    @Override
    public MerchantAlliance getById(String id) {
        return this.merchantAllianceDao.findEntityById(id);
    }

    /**
     * 删除持久化对象
     *
     * @param merchantAlliance 需要删除的持久化对象
     */
    @Override
    public void delete(MerchantAlliance merchantAlliance) {
        if (StringUtils.isEmpty(merchantAlliance.getAllianceId())) {
            throw new SystemException("需要删除的联盟成员ID不能为空");
        }
        IQueryParams queryParams = new GeneralQueryParams().andEqual("allianceId", merchantAlliance.getAllianceId());
        this.merchantAllianceDao.executeDelete(queryParams);
    }

    /**
     * 添加一个对象到数据库
     *
     * @param merchantAlliance 需要持久化的对象
     * @return
     */
    @Override
    public MerchantAlliance add(MerchantAlliance merchantAlliance) {
        IQueryParams queryParams = new GeneralQueryParams().andEqual("merchant", merchantAlliance.getMerchant())
                .andEqual("activity", merchantAlliance.getActivity());
        List<Object> list = this.merchantAllianceDao.getProperty("allianceId", null, queryParams);
        if (list != null && list.size() > 0) {
            throw new SystemException("此商家已经参加了本次活动请勿重复操作");
        }
        return this.merchantAllianceDao.saveEntity(merchantAlliance);
    }

    /**
     * 更新一个持久化对象
     *
     * @param merchantAlliance
     */
    @Override
    public void update(MerchantAlliance merchantAlliance) {
        this.merchantAllianceDao.updateEntity(merchantAlliance);
    }

    /**
     * 根据分页参数和查询条件查询持久化对象
     *
     * @param pager       分页参数
     * @param queryParams 查询条件
     * @return
     */
    @Override
    public List<MerchantAlliance> getAllByParams(Pager pager, IQueryParams queryParams) {
        return this.merchantAllianceDao.getEntities(pager, queryParams);
    }

    /**
     * 根据分页参数和查询条件查询持久化对象
     *
     * @param pager       分页参数
     * @param queryParams 查询条件
     * @return 经过分页的数据
     */
    @Override
    public PageData<MerchantAlliance> getPageData(Pager pager, IQueryParams queryParams) {
        return this.merchantAllianceDao.getPageData(pager, queryParams);
    }

    @Override
    public PageData<MerchantAlliance> getMerchAllianceByActivity(Pager pager, GeneralQueryParams queryParams, String activitId) {
        if (StringUtils.isEmpty(activitId)) {
            throw new SystemException("活动不能为空");
        }
        if (!queryParams.queryContainsKey("activity.activitId")) {
            queryParams.andEqual("activity.activitId", activitId);
        }
        if (!queryParams.sortContainsKey("agreed")) {
            queryParams.sort("agreed", Order.DESC);
        }
        if (!queryParams.sortContainsKey("agreed")) {
            queryParams.sort("createDate", Order.DESC);
        }
        return this.merchantAllianceDao.getPageData(pager, queryParams);
    }

    /**
     * 根据活动ID获取受邀本次活动的所有商户
     *
     * @param pager
     * @param queryParams
     * @param activitId   活动ID
     * @param agreed      商家是否同意参加本次活动
     * @return
     */
    @Override
    public PageData<MerchantAlliance> getMerchAllianceByActivity(Pager pager, GeneralQueryParams queryParams, String activitId, boolean agreed) {
        if (StringUtils.isEmpty(activitId)) {
            throw new SystemException("活动不能为空");
        }
        if (!queryParams.queryContainsKey("activity.activitId")) {
            queryParams.andEqual("activity.activitId", activitId);
        }
        if (!queryParams.queryContainsKey("agreed")) {
            queryParams.andEqual("agreed", agreed);
        }
        if (!queryParams.sortContainsKey("agreed")) {
            queryParams.sort("agreed", Order.DESC);
        }
        if (!queryParams.sortContainsKey("agreed")) {
            queryParams.sort("createDate", Order.DESC);
        }
        return this.merchantAllianceDao.getPageData(pager, queryParams);
    }

    @Override
    public PageData<Merchant> getAbleMerchByActivity(Pager pager, GeneralQueryParams queryParams, String activitId) {
        if (StringUtils.isEmpty(activitId)) {
            throw new SystemException("活动不能为空");
        }
        IQueryParams _queryParam = new GeneralQueryParams().andEqual("activity.activitId", activitId);
        List<Object> merchIds = this.merchantAllianceDao.getProperty("merchant.merchantId", null, _queryParam);
        if (!queryParams.queryContainsKey("merchantId")) {
            queryParams.andNotIn("merchantId", merchIds);
        }
        if (!queryParams.queryContainsKey("examinState")) {
            queryParams.andEqual("examinState", Constant.ACTIVITY_STATE_EXAMINED_OK);
        }
        if (!queryParams.queryContainsKey("permit")) {
            queryParams.andEqual("permit", true);
        }

        return this.merchantService.getPageData(pager, queryParams);
    }

    @Override
    public PageData<Activity> getActivityListByAdmin(Pager pager, GeneralQueryParams queryParams, Admin admin) {
        Merchant merchant = this.adminService.getMerchant(admin);
        if (merchant == null) {
            throw new SystemException("账号异常，请联系管理员");
        }
        IQueryParams query = new GeneralQueryParams();
        query.andEqual("merchant", merchant);
        List<Object> activits = this.merchantAllianceDao.getProperty("distinct activity.activitId", null, query);
        queryParams.andIn("activitId", activits);
        return this.activityService.getPageData(pager, queryParams);
    }

    @Override
    public PageData<Merchant> getJoinMerchByActivity(Pager pager, IQueryParams iQueryParams) {
        List<MerchantAlliance> merchantAlliances = this.getAllByParams(pager, iQueryParams);
        PageData<Merchant> pageData = new PageData();
        List<Merchant> merchants = new ArrayList<Merchant>();
        for (MerchantAlliance merchantAlliance : merchantAlliances) {
            merchants.add(merchantAlliance.getMerchant());
        }
        pageData.setRows(merchants);
        return pageData;
    }


}
