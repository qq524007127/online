package cn.com.zhihetech.online.dao.impl;

import cn.com.zhihetech.online.bean.Sku;
import cn.com.zhihetech.online.dao.ISkuDao;
import cn.com.zhihetech.util.hibernate.dao.SimpleSupportDao;
import org.springframework.stereotype.Repository;

/**
 * Created by YangDaiChun on 2015/12/2.
 */
@Repository("skuDao")
public class SkuDaoImpl extends SimpleSupportDao<Sku> implements ISkuDao {

}
