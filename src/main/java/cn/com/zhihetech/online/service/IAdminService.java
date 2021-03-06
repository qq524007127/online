package cn.com.zhihetech.online.service;

import cn.com.zhihetech.online.bean.Admin;
import cn.com.zhihetech.online.bean.Menu;
import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.util.hibernate.service.SupportService;

import java.util.List;

/**
 * Created by ShenYunjie on 2015/11/17.
 */
public interface IAdminService extends SupportService<Admin> {
    /**
     * 根据账号和密码获取管理员
     *
     * @param adminCode 登录账号
     * @param adminPwd  登录密码
     * @return
     */
    Admin executeLogin(String adminCode, String adminPwd);

    /**
     * 查询管理员所具有的权限（菜单）
     *
     * @param currentAdmin
     * @return
     */
    List<Menu> getMenusByAndmin(Admin currentAdmin);

    /**
     * 禁用管理员
     *
     * @param admin
     */
    void disableAdmin(Admin admin);

    /**
     * 将密码重置为123456
     */
    void resetPwd(Admin admin);

    /**
     * 获取管理员对应的商家
     *
     * @param admin
     * @return
     */
    Merchant getMerchant(Admin admin);

    /**
     * 通过amdin获取对应的商家ID
     *
     * @param admin
     * @return
     */
    String getMerchantId(Admin admin);

    /**
     * 修改登录密码
     *
     * @param admin
     * @param newPwd 新登录密码
     */
    void changePassword(Admin admin, String newPwd);

}
