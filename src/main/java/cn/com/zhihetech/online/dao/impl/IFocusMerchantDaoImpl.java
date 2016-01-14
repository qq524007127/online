package cn.com.zhihetech.online.dao.impl;

import cn.com.zhihetech.online.bean.FocusMerchant;
import cn.com.zhihetech.online.dao.IFocusMerchantDao;
import cn.com.zhihetech.util.hibernate.dao.SimpleSupportDao;
import org.springframework.stereotype.Repository;

/**
 * Created by ShenYunjie on 2015/12/15.
 */
@Repository("focusMerchantDao")
public class IFocusMerchantDaoImpl extends SimpleSupportDao<FocusMerchant> implements IFocusMerchantDao {
}
