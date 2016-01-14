package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.Messager;
import cn.com.zhihetech.online.dao.IMessagerDao;
import cn.com.zhihetech.online.service.IMessagerService;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by YangDaiChun on 2015/12/18.
 */
@Service(value = "messagerService")
public class MessagerServiceImpl implements IMessagerService {

    @Resource(name = "messaerDao")
    private IMessagerDao messagerDao;

    @Override
    public Messager getById(String id) {
        return this.messagerDao.findEntityById(id);
    }

    @Override
    public void delete(Messager messager) {

    }

    @Override
    public Messager add(Messager messager) {
        return this.messagerDao.saveEntity(messager);
    }

    @Override
    public void update(Messager messager) {
            this.messagerDao.updateEntity(messager);
    }

    @Override
    public List<Messager> getAllByParams(Pager pager, IQueryParams queryParams) {
        return this.messagerDao.getEntities(pager,queryParams);
    }

    @Override
    public PageData<Messager> getPageData(Pager pager, IQueryParams queryParams) {
        return this.messagerDao.getPageData(pager,queryParams);
    }
}
