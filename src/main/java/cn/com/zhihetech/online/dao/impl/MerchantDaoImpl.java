package cn.com.zhihetech.online.dao.impl;

import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.dao.IMerchantDao;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.dao.SimpleSupportDao;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by ShenYunjie on 2015/11/20.
 */
@Repository("merchantDao")
public class MerchantDaoImpl extends SimpleSupportDao<Merchant> implements IMerchantDao {

}
