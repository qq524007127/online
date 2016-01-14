package cn.com.zhihetech.online.dao.impl;

import cn.com.zhihetech.online.bean.Activity;
import cn.com.zhihetech.online.dao.IActivityDao;
import cn.com.zhihetech.util.hibernate.dao.SimpleSupportDao;
import org.springframework.stereotype.Repository;

/**
 * Created by ShenYunjie on 2015/12/7.
 */
@Repository("activityDao")
public class ActivityDaoImpl extends SimpleSupportDao<Activity> implements IActivityDao {
}
