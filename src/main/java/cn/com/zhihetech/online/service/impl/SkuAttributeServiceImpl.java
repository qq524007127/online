package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.SkuAttribute;
import cn.com.zhihetech.online.dao.ISkuAttributeDao;
import cn.com.zhihetech.online.service.ISkuAttributeService;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by YangDaiChun on 2015/12/2.
 */
@Service("skuAttributeService")
public class SkuAttributeServiceImpl implements ISkuAttributeService{

    @Resource(name = "skuAttributeDao")
    public ISkuAttributeDao skuAttributeDao;

    @Override
    public SkuAttribute getById(String id) {
        return this.skuAttributeDao.findEntityById(id);
    }

    @Override
    public void delete(SkuAttribute skuAttribute) {
        this.skuAttributeDao.deleteEntity(skuAttribute);
    }

    @Override
    public SkuAttribute add(SkuAttribute skuAttribute) {
        return this.skuAttributeDao.saveEntity(skuAttribute);
    }

    @Override
    public void update(SkuAttribute skuAttribute) {
        this.skuAttributeDao.updateEntity(skuAttribute);
    }

    @Override
    public List<SkuAttribute> getAllByParams(Pager pager, IQueryParams queryParams) {
        return this.skuAttributeDao.getEntities(pager,queryParams);
    }

    @Override
    public PageData<SkuAttribute> getPageData(Pager pager, IQueryParams queryParams) {
        return this.skuAttributeDao.getPageData(pager,queryParams);
    }
}
