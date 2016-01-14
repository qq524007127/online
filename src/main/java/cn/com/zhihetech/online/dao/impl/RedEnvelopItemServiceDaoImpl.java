package cn.com.zhihetech.online.dao.impl;

import cn.com.zhihetech.online.bean.RedEnvelopItem;
import cn.com.zhihetech.online.dao.IRedEnvelopItemServiceDao;
import cn.com.zhihetech.util.hibernate.dao.SimpleSupportDao;
import org.springframework.stereotype.Repository;

/**
 * Created by ShenYunjie on 2015/12/14.
 */
@Repository("redEnvelopItemDao")
public class RedEnvelopItemServiceDaoImpl extends SimpleSupportDao<RedEnvelopItem> implements IRedEnvelopItemServiceDao {
}
