package cn.com.zhihetech.online.dao.impl;

import cn.com.zhihetech.online.bean.Goods;
import cn.com.zhihetech.online.dao.IGoodsDao;
import cn.com.zhihetech.util.common.StringUtils;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import cn.com.zhihetech.util.hibernate.dao.SimpleSupportDao;
import cn.com.zhihetech.util.hibernate.exception.UpdateParamsException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by YangDaiChun on 2015/12/14.
 */
@Repository(value = "goodsDao")
public class GoodsDaoImpl extends SimpleSupportDao<Goods> implements IGoodsDao{
}
