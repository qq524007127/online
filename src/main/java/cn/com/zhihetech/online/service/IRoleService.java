package cn.com.zhihetech.online.service;

import cn.com.zhihetech.online.bean.Admin;
import cn.com.zhihetech.online.bean.Menu;
import cn.com.zhihetech.online.bean.Role;
import cn.com.zhihetech.util.hibernate.service.SupportService;

import java.util.List;

/**
 * Created by ShenYunjie on 2015/11/17.
 */
public interface IRoleService extends SupportService<Role> {
    /**
     * 获取管理员所拥有的权限（菜单）
     *
     * @param currentAdmin
     * @return
     */
    List<Menu> getMenusByRoles(Admin currentAdmin);

    /**
     * 添加或更新一个实体类
     *
     * @param role
     * @return
     */
    Role saveOrUpdate(Role role);
}
