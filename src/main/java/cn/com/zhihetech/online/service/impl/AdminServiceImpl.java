package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.Admin;
import cn.com.zhihetech.online.bean.Menu;
import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.dao.IAdminDao;
import cn.com.zhihetech.online.exception.DataRepeatException;
import cn.com.zhihetech.online.service.IAdminService;
import cn.com.zhihetech.online.service.IRoleService;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ShenYunjie on 2015/11/17.
 */
@Service("adminService")
@Transactional
public class AdminServiceImpl implements IAdminService {
    @Resource(name = "adminDao")
    private IAdminDao adminDao;
    @Resource(name = "roleService")
    private IRoleService roleService;

    @Override
    public Admin getById(String id) {
        return this.adminDao.findEntityById(id);
    }

    @Override
    public void delete(Admin admin) {
        this.adminDao.deleteEntity(admin);
    }

    @Override
    public Admin add(Admin admin) {
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("adminCode", admin.getAdminCode());
        List<Admin> admins = getAllByParams(null, queryParams);
        if (admins.size() > 0) {
            throw new DataRepeatException("管理员登录账号已存在");
        }
        return this.adminDao.saveEntity(admin);
    }

    @Override
    public void update(Admin admin) {
        this.adminDao.updateEntity(admin);
    }

    @Override
    public List<Admin> getAllByParams(Pager pager, IQueryParams queryParams) {
        return this.adminDao.getEntities(pager, queryParams);
    }

    @Override
    public PageData<Admin> getPageData(Pager pager, IQueryParams queryParams) {
        return this.adminDao.getPageData(pager, queryParams);
    }

    @Override
    public Admin executeLogin(String adminCode, String adminPwd) {
        IQueryParams queryParams = new GeneralQueryParams().andEqual("adminCode", adminCode)
                .andEqual("adminPwd", adminPwd).andEqual("permit", true);
        List<Admin> admins = this.adminDao.getEntities(queryParams);
        if (admins.size() < 1) {
            return null;
        }
        Admin admin = admins.get(0);
        return admin;
    }

    @Override
    public List<Menu> getMenusByAndmin(Admin currentAdmin) {
        return this.roleService.getMenusByRoles(currentAdmin);
    }

    @Override
    public void disableAdmin(Admin admin) {
        Admin _admin = this.adminDao.findEntityById(admin.getAdminId());
        _admin.setPermit(admin.isPermit());
        this.adminDao.updateEntity(_admin);
    }

    @Override
    public void resetPwd(Admin admin) {
        Admin _admin = this.adminDao.findEntityById(admin.getAdminId());
        _admin.setAdminPwd(admin.getAdminPwd());
        this.adminDao.updateEntity(_admin);
    }

    @Override
    public Merchant getMerchant(Admin admin) {
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("adminId", admin.getAdminId());
        Admin tmp = this.adminDao.findEntityById(admin.getAdminId());
        return tmp.getMerchant();
    }

    @Override
    public String getMerchantId(Admin admin) {
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("adminId", admin.getAdminId());
        List<Object> tmps = this.adminDao.getProperty("merchant.merchantId", null, queryParams);
        if(tmps.size() > 0){
            return (String) tmps.get(0);
        }
        return null;
    }

    @Override
    public void changePassword(Admin admin, String newPwd) {
        Map<String, Object> params = new HashMap<>();
        params.put("adminPwd", newPwd);
        IQueryParams queryParams = new GeneralQueryParams();
        queryParams.andEqual("adminId", admin.getAdminId());
        this.adminDao.executeUpdate(params, queryParams);
    }
}
