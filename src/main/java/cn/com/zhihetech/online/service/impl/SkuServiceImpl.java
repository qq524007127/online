package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.Sku;
import cn.com.zhihetech.online.dao.ISkuDao;
import cn.com.zhihetech.online.service.ISkuService;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by YangDaiChun on 2015/12/2.
 */
@Service("skuService")
public class SkuServiceImpl implements ISkuService {


    @Resource(name = "skuDao")
    public ISkuDao skuDao;

    @Override
    public Sku getById(String id) {
        return this.skuDao.findEntityById(id);
    }

    @Override
    public void delete(Sku sku) {
        this.skuDao.deleteEntity(sku);
    }

    @Override
    public Sku add(Sku sku) {
        return this.skuDao.saveEntity(sku);
    }

    @Override
    public void update(Sku sku) {
        this.skuDao.updateEntity(sku);
    }

    @Override
    public List<Sku> getAllByParams(Pager pager, IQueryParams queryParams) {
        return this.skuDao.getEntities(pager,queryParams);
    }

    @Override
    public PageData<Sku> getPageData(Pager pager, IQueryParams queryParams) {
        return this.skuDao.getPageData(pager, queryParams);
    }
}
