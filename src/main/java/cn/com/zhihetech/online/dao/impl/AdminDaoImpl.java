package cn.com.zhihetech.online.dao.impl;

import cn.com.zhihetech.online.bean.Admin;
import cn.com.zhihetech.online.dao.IAdminDao;
import cn.com.zhihetech.util.hibernate.dao.SimpleSupportDao;
import org.springframework.stereotype.Repository;

/**
 * Created by ShenYunjie on 2015/11/17.
 */
@Repository("adminDao")
public class AdminDaoImpl extends SimpleSupportDao<Admin> implements IAdminDao {
}
