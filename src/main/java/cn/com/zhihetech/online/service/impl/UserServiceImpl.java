package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.User;
import cn.com.zhihetech.online.dao.IActivityFansDao;
import cn.com.zhihetech.online.dao.IFocusMerchantDao;
import cn.com.zhihetech.online.dao.IUserDao;
import cn.com.zhihetech.online.exception.UserPhoneRepeatException;
import cn.com.zhihetech.online.service.IUserService;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ShenYunjie on 2015/12/15.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    @Resource(name = "userDao")
    private IUserDao userDao;
    @Resource(name = "activityFansDao")
    private IActivityFansDao activityFansDao;
    @Resource(name = "focusMerchantDao")
    private IFocusMerchantDao focusMerchantDao;

    /**
     * 根据ID获取持久化对象
     *
     * @param id 对象ID
     * @return
     */
    @Override
    public User getById(String id) {
        return this.userDao.findEntityById(id);
    }

    /**
     * 删除持久化对象
     *
     * @param user 需要删除的持久化对象
     */
    @Override
    public void delete(User user) {

    }

    /**
     * 添加一个对象到数据库
     *
     * @param user 需要持久化的对象
     * @return
     */
    @Override
    public User add(User user) {
        if (isExists(user)) {
            throw new UserPhoneRepeatException("此号码已注册，请勿重复注册");
        }
        return this.userDao.saveEntity(user);
    }

    /**
     * 更新一个持久化对象
     *
     * @param user
     */
    @Override
    public void update(User user) {
      this.userDao.updateEntity(user);
    }

    /**
     * 根据分页参数和查询条件查询持久化对象
     *
     * @param pager       分页参数
     * @param queryParams 查询条件
     * @return
     */
    @Override
    public List<User> getAllByParams(Pager pager, IQueryParams queryParams) {
        return this.userDao.getEntities(pager, queryParams);
    }

    /**
     * @param pager
     * @param queryParams
     * @return 返回用户的id
     */
    public String getUserId(Pager pager, IQueryParams queryParams) {
        List<User> users = this.userDao.getEntities(pager,queryParams);
        if (users.size() == 0) {
            return null;
        }
        User user = users.get(0);
        return user.getUserId();
    }


    /**
     * 根据分页参数和查询条件查询持久化对象
     *
     * @param pager       分页参数
     * @param queryParams 查询条件
     * @return 经过分页的数据
     */
    @Override
    public PageData<User> getPageData(Pager pager, IQueryParams queryParams) {
        return null;
    }

    /**
     * @param pager
     * @param queryParams
     * @param activityId  指定活动ID
     * @param merchantId  指定商家
     * @return
     */
    @Override
    public PageData<User> getAbleUserByActivityIdAndMerchantId(Pager pager, IQueryParams queryParams, String activityId, String merchantId) {
        IQueryParams _queryParams = new GeneralQueryParams();
        _queryParams.andEqual("activity.activitId", activityId);
        List<Object> joindIds = this.activityFansDao.getProperty("user.userId", null, _queryParams);
        _queryParams = new GeneralQueryParams();
        _queryParams.andEqual("merchant.merchantId", merchantId);
        List<Object> unJoinIds = this.focusMerchantDao.getProperty("user.userId", null, _queryParams);

        queryParams.andNotIn("userId", joindIds).andIn("userId", unJoinIds).andEqual("permit", true);
        return this.userDao.getPageData(pager, queryParams);
    }

    /**
     * 判断一个用户是否一存在
     *
     * @param user
     * @return
     */
    private boolean isExists(User user) {
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("userPhone", user.getUserPhone());
        List<Object> ids = this.userDao.getProperty("userId", null, queryParams);
        if (ids.size() > 0) {
            return true;
        }
        return false;
    }
}
