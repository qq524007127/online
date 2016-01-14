package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.Admin;
import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.bean.RedEnvelop;
import cn.com.zhihetech.online.dao.IAdminDao;
import cn.com.zhihetech.online.dao.IRedEnvelopDao;
import cn.com.zhihetech.online.service.IRedEnvelopService;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by ShenYunjie on 2015/12/11.
 */
@Service("redEnvelopService")
public class RedEnvelopServiceImpl implements IRedEnvelopService {
    @Resource(name = "redEnvelopDao")
    private IRedEnvelopDao redEnvelopDao;
    @Resource(name = "adminDao")
    private IAdminDao adminDao;

    /**
     * 根据ID获取持久化对象
     *
     * @param id 对象ID
     * @return
     */
    @Override
    public RedEnvelop getById(String id) {
        return this.redEnvelopDao.findEntityById(id);
    }

    /**
     * 删除持久化对象
     *
     * @param redEnvelop 需要删除的持久化对象
     */
    @Override
    public void delete(RedEnvelop redEnvelop) {
        this.redEnvelopDao.deleteEntity(redEnvelop);
    }

    /**
     * 添加一个对象到数据库
     *
     * @param redEnvelop 需要持久化的对象
     * @return
     */
    @Override
    public RedEnvelop add(RedEnvelop redEnvelop) {
        return this.redEnvelopDao.saveEntity(redEnvelop);
    }

    /**
     * 更新一个持久化对象
     *
     * @param redEnvelop
     */
    @Override
    public void update(RedEnvelop redEnvelop) {
        this.redEnvelopDao.updateEntity(redEnvelop);
    }

    /**
     * 根据分页参数和查询条件查询持久化对象
     *
     * @param pager       分页参数
     * @param queryParams 查询条件
     * @return
     */
    @Override
    public List<RedEnvelop> getAllByParams(Pager pager, IQueryParams queryParams) {
        return this.redEnvelopDao.getEntities(pager, queryParams);
    }

    /**
     * 根据分页参数和查询条件查询持久化对象
     *
     * @param pager       分页参数
     * @param queryParams 查询条件
     * @return 经过分页的数据
     */
    @Override
    public PageData<RedEnvelop> getPageData(Pager pager, IQueryParams queryParams) {
        return this.redEnvelopDao.getPageData(pager, queryParams);
    }

    /**
     * 获取指定用户对应商家的红包
     *
     * @param pager
     * @param queryParams
     * @param currentAdmin
     */
    @Override
    public PageData<RedEnvelop> getPageDataByAdmin(Pager pager, GeneralQueryParams queryParams, Admin currentAdmin) {
        IQueryParams query = new GeneralQueryParams().andEqual("adminId", currentAdmin.getAdminId());
        List<Object> merchIds = this.adminDao.getProperty("merchant.merchantId", null, query);
        if (!queryParams.queryContainsKey("merchant.merchantId")) {
            queryParams.andEqual("merchant.merchantId", merchIds.get(0));
        }
        return this.redEnvelopDao.getPageData(pager, queryParams);
    }

    @Override
    public boolean isEditable(RedEnvelop redEnvelop) {
        return false;
    }

    @Override
    public Map<String, List<RedEnvelop>> getAbleRedEnvelopByMerch(Pager pager, List<Merchant> merchants, String activityId) {
        return null;
    }
}
