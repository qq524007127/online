package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.GoodsAttributeSet;
import cn.com.zhihetech.online.dao.IGoodsAttributeSetDao;
import cn.com.zhihetech.online.service.IGoodsAttributeSetService;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by YangDaiChun on 2015/12/4.
 */
@Service("goodsAttributeSetService")
public class GoodsAttributeSetServiceImpl implements IGoodsAttributeSetService {

    @Resource(name = "goodsAttributeSetDao")
    public IGoodsAttributeSetDao goodsAttributeSetDao;

    @Override
    public GoodsAttributeSet getById(String id) {
        return this.goodsAttributeSetDao.findEntityById(id);
    }

    @Override
    public void delete(GoodsAttributeSet goodsAttributeSet) {
        this.goodsAttributeSetDao.deleteEntity(goodsAttributeSet);
    }

    @Override
    public GoodsAttributeSet add(GoodsAttributeSet goodsAttributeSet) {
        return this.goodsAttributeSetDao.saveEntity(goodsAttributeSet);
    }

    @Override
    public void update(GoodsAttributeSet goodsAttributeSet) {
        this.goodsAttributeSetDao.updateEntity(goodsAttributeSet);
    }

    @Override
    public List<GoodsAttributeSet> getAllByParams(Pager pager, IQueryParams queryParams) {
        return this.goodsAttributeSetDao.getEntities(pager,queryParams);
    }

    @Override
    public PageData<GoodsAttributeSet> getPageData(Pager pager, IQueryParams queryParams) {
        return this.goodsAttributeSetDao.getPageData(pager,queryParams);
    }
}
