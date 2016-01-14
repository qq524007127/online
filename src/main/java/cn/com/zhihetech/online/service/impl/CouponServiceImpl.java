package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.Activity;
import cn.com.zhihetech.online.bean.Coupon;
import cn.com.zhihetech.online.commons.Constant;
import cn.com.zhihetech.online.dao.ICouponDao;
import cn.com.zhihetech.online.exception.SystemException;
import cn.com.zhihetech.online.service.IActivityService;
import cn.com.zhihetech.online.service.ICouponService;
import cn.com.zhihetech.util.common.StringUtils;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ShenYunjie on 2015/12/15.
 */
@Service("couponService")
public class CouponServiceImpl implements ICouponService {
    @Resource(name = "couponDao")
    private ICouponDao couponDao;
    @Resource(name = "activityService")
    private IActivityService activityService;

    /**
     * 根据ID获取持久化对象
     *
     * @param id 对象ID
     * @return
     */
    @Override
    public Coupon getById(String id) {
        return this.couponDao.findEntityById(id);
    }

    /**
     * 删除持久化对象(逻辑删除)
     *
     * @param coupon 需要删除的持久化对象
     */
    @Override
    public void delete(Coupon coupon) {
        if (!isEditAble(coupon)) {
            throw new SystemException("当前优惠券不可删除");
        }
        Map values = new HashMap<>();
        values.put("deleted", true);
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("couponId", coupon.getCouponId());
        this.couponDao.executeUpdate(values, queryParams);
    }

    /**
     * 添加一个对象到数据库
     *
     * @param coupon 需要持久化的对象
     * @return
     */
    @Override
    public Coupon add(Coupon coupon) {
        if (!isEditAble(coupon)) {
            throw new SystemException("当前优惠券不可编辑");
        }
        if (coupon.getActivity() != null && StringUtils.isEmpty(coupon.getActivity().getActivitId())) {
            coupon.setActivity(null);
        }
        coupon.setCreateDate(new Date());
        if (coupon.getCouponType() == Constant.COUPON_VOUCHER_TYPE) {
            coupon.setCouponName("优惠券");
        }
        coupon.setTotalReceived(0);
        return this.couponDao.saveEntity(coupon);
    }

    /**
     * 更新一个持久化对象
     *
     * @param coupon
     */
    @Override
    public void update(Coupon coupon) {
        this.couponDao.updateEntity(coupon);
    }

    /**
     * 根据分页参数和查询条件查询持久化对象
     *
     * @param pager       分页参数
     * @param queryParams 查询条件
     * @return
     */
    @Override
    public List<Coupon> getAllByParams(Pager pager, IQueryParams queryParams) {
        return this.couponDao.getEntities(pager, queryParams);
    }

    /**
     * 根据分页参数和查询条件查询持久化对象
     *
     * @param pager       分页参数
     * @param queryParams 查询条件
     * @return 经过分页的数据
     */
    @Override
    public PageData<Coupon> getPageData(Pager pager, IQueryParams queryParams) {
        return this.couponDao.getPageData(pager, queryParams);
    }

    /**
     * 判断优惠券信息是否可编辑
     *
     * @param coupon
     * @return
     */
    @Override
    public boolean isEditAble(Coupon coupon) {
        String activityId = null;
        IQueryParams queryParams = new GeneralQueryParams();
        if (!StringUtils.isEmpty(coupon.getCouponId())) {
            queryParams.andEqual("couponId", coupon.getCouponId());
            List<Object> tmps = this.couponDao.getProperty("activity.activitId", null, queryParams);
            if (tmps.size() > 0) {
                activityId = (String) tmps.get(0);
            }
        } else {
            if (coupon.getActivity() == null) {
                return true;
            }
            queryParams.andEqual("activity.activitId", coupon.getActivity().getActivitId());
            List<Object> tmps = this.couponDao.getProperty("activity.activitId", null, queryParams);
            if (tmps.size() > 0) {
                activityId = (String) tmps.get(0);
            }
        }
        if (StringUtils.isEmpty(activityId)) {
            throw new SystemException("系统错误");
        }
        Activity activity = new Activity();
        activity.setActivitId(activityId);
        return this.activityService.isEditAble(activity);
    }

    @Override
    public void updateBaseInfo(Coupon coupon) {
        if (!isEditAble(coupon)) {
            throw new SystemException("当前优惠券不可编辑");
        }
        Map<String, Object> values = new HashMap<>();
        if (coupon.getCouponType() == Constant.COUPON_VOUCHER_TYPE) {
            values.put("couponName", "优惠券");
        } else {
            values.put("couponName", "代金券");
        }
        values.put("faceValue", coupon.getFaceValue());
        values.put("total", coupon.getTotal());
        values.put("couponMsg", coupon.getCouponMsg());
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("couponId", coupon.getCouponId());
        this.couponDao.executeUpdate(values, queryParams);
    }
}
