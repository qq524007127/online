package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.*;
import cn.com.zhihetech.online.commons.Constant;
import cn.com.zhihetech.online.dao.IActivityDao;
import cn.com.zhihetech.online.dao.IRedEnvelopDao;
import cn.com.zhihetech.online.exception.SystemException;
import cn.com.zhihetech.online.service.IActivityService;
import cn.com.zhihetech.online.service.IAdminService;
import cn.com.zhihetech.online.service.IMerchantAllianceService;
import cn.com.zhihetech.util.common.StringUtils;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by ShenYunjie on 2015/12/7.
 */
@Service("activityService")
public class ActivityServiceImpl implements IActivityService {
    @Resource(name = "activityDao")
    private IActivityDao activityDao;
    @Resource(name = "adminService")
    private IAdminService adminService;
    @Resource(name = "merchantAllianceService")
    private IMerchantAllianceService merchantAllianceService;
    @Resource(name = "redEnvelopDao")
    private IRedEnvelopDao redEnvelopDao;

    /**
     * 根据ID获取持久化对象
     *
     * @param id 对象ID
     * @return
     */
    @Override
    public Activity getById(String id) {
        return this.activityDao.findEntityById(id);
    }

    /**
     * 删除持久化对象
     *
     * @param activity 需要删除的持久化对象
     */
    @Override
    public void delete(Activity activity) {
        this.activityDao.deleteEntity(activity);
    }

    /**
     * 添加一个对象到数据库
     *
     * @param activity 需要持久化的对象
     * @return
     */
    @Override
    public Activity add(Activity activity) {
        return this.activityDao.saveEntity(activity);
    }

    /**
     * 更新一个持久化对象
     *
     * @param activity
     */
    @Override
    public void update(Activity activity) {
        if (!isEditAble(activity)) {
            throw new SystemException("当前活动信息不可更改");
        }
        this.activityDao.updateEntity(activity);
    }

    /**
     * 根据分页参数和查询条件查询持久化对象
     *
     * @param pager       分页参数
     * @param queryParams 查询条件
     * @return
     */
    @Override
    public List<Activity> getAllByParams(Pager pager, IQueryParams queryParams) {
        return this.activityDao.getEntities(pager, queryParams);
    }

    /**
     * 根据分页参数和查询条件查询持久化对象
     *
     * @param pager       分页参数
     * @param queryParams 查询条件
     * @return 经过分页的数据
     */
    @Override
    public PageData<Activity> getPageData(Pager pager, IQueryParams queryParams) {
        return this.activityDao.getPageData(pager, queryParams);
    }

    @Override
    public Activity saveOrUpdate(Activity activity) {
        if (!isEditAble(activity)) {
            throw new SystemException("当前活动信息不可更改");
        }
        return this.activityDao.saveOrUpdate(activity);
    }

    @Override
    public PageData<Activity> getPageDataByAdmin(Pager pager, GeneralQueryParams queryParams, Admin currentAdmin) {
        if (queryParams == null) {
            queryParams = new GeneralQueryParams();
        }
        Admin tmp = this.adminService.getById(currentAdmin.getAdminId());
        queryParams.andEqual("activityPromoter", tmp.getMerchant());
        return this.activityDao.getPageData(pager, queryParams);
    }

    @Override
    public void saveByAdmin(Activity activity, RedEnvelop redEnvelop,Admin admin) {
        activity.setCreateDate(new Date());
        Merchant merchant = this.adminService.getById(admin.getAdminId()).getMerchant();
        if (merchant == null) {
            throw new SystemException("当前账号异常,请联系管理员");
        }
        activity.setActivityPromoter(merchant);
        this.activityDao.saveEntity(activity);
        redEnvelop.setActivity(activity);
        redEnvelop.setMerchant(merchant);
        redEnvelop.setCreateDate(new Date());
        redEnvelop.setSendDate(new Date());
        redEnvelop.setSended(true);
        this.redEnvelopDao.saveEntity(redEnvelop);
        MerchantAlliance merchantAlliance = new MerchantAlliance();
        merchantAlliance.setCreateDate(new Date());
        merchantAlliance.setActivity(activity);
        merchantAlliance.setAllianceDesc(activity.getActivitDesc());
        merchantAlliance.setAllianceName(activity.getActivitName());
        merchantAlliance.setMerchant(activity.getActivityPromoter());
        merchantAlliance.setAgreed(true);   //活动发起人加入商家联盟默认为确认加入
        merchantAlliance.setPromoted(true); //活动发起人加入商家联盟默认为活动发起人
        this.merchantAllianceService.add(merchantAlliance);
    }

    @Override
    public boolean isEditAble(Activity activity) {
        if (StringUtils.isEmpty(activity.getActivitId())) {
            return true;    //如果活动为新增则可编辑
        }
        int currentState = getCurrentSateById(activity.getActivitId());
        if (currentState == Constant.ACTIVITY_STATE_UNSBUMIT || currentState == Constant.ACTIVITY_STATE_EXAMINED_NOT) {
            return true;    //如果未提交或审核未通过则可以编辑
        }
        return false;
    }

    @Override
    public int getCurrentSateById(String activitId) {
        IQueryParams queryParams = new GeneralQueryParams().andEqual("activitId", activitId);
        List<Object> tmps = this.activityDao.getProperty("currentState", null, queryParams);
        if (tmps.size() > 0) {
            return (int) tmps.get(0);
        }
        throw new SystemException("活动不存在");
    }
}
