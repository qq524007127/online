package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.Admin;
import cn.com.zhihetech.online.bean.Menu;
import cn.com.zhihetech.online.bean.Role;
import cn.com.zhihetech.online.dao.IRoleDao;
import cn.com.zhihetech.online.service.IAdminService;
import cn.com.zhihetech.online.service.IMenuService;
import cn.com.zhihetech.online.service.IRoleService;
import cn.com.zhihetech.util.hibernate.GeneralQueryParams;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by ShenYunjie on 2015/11/17.
 */
@Service("roleService")
public class RoleServiceImpl implements IRoleService {
    @Resource(name = "roleDao")
    private IRoleDao roleDao;
    @Resource(name = "menuService")
    private IMenuService menuService;
    @Resource(name="adminService")
    private IAdminService adminService;

    @Override
    public Role getById(String id) {
        return this.roleDao.findEntityById(id);
    }

    @Override
    public void delete(Role role) {
        this.roleDao.deleteEntity(role);
    }

    @Override
    public Role add(Role role) {
        return this.roleDao.saveEntity(role);
    }

    @Override
    public void update(Role role) {
        this.roleDao.updateEntity(role);
    }

    @Override
    public List<Role> getAllByParams(Pager pager, IQueryParams queryParams) {
        return this.roleDao.getEntities(pager, queryParams);
    }

    @Override
    public PageData<Role> getPageData(Pager pager, IQueryParams queryParams) {
        return this.roleDao.getPageData(pager, queryParams);
    }

    @Override
    public List<Menu> getMenusByRoles(Admin admin) {
        Admin currentAdmin = this.adminService.getById(admin.getAdminId());
        if(!currentAdmin.isPermit()){
            //throw new SystemException("用户已禁用");
            return new ArrayList<Menu>();
        }
        List<Role> roleList = new ArrayList<>(currentAdmin.getRoles());

        Map<String, Menu> menuMap = new HashMap<>();
        List<Menu> menuList = new ArrayList<Menu>();
        for(Role role : roleList){

            menuList.addAll(role.getMenus());   //默认获取当前用户所用户的权限
        }

        /*
        如果是超级用户则获取全部有效权限
         */
        if(currentAdmin.isSuperAdmin()){
            IQueryParams queryParams = new GeneralQueryParams();
            queryParams.andEqual("permit",true).andNotNull("parentMenu");
            menuList = this.menuService.getAllByParams(null,queryParams);
        }

        for (Menu menu : menuList) {
            if(menu.isPermit()){
                menuMap.put(menu.getMenuId(), menu);    //去掉ID相同的菜单(即：去掉相同的菜单)
            }
        }

        menuList.clear();
        menuList.addAll(menuMap.values());
        menuMap.clear();
        for (Menu menu:menuList){
            Menu rootMenu = menu.getParentMenu();
            if(rootMenu == null){
                //menuMap.put(menu.getMenuId(),menu); //如果无父菜单则本身就为根菜单，将其自己加入根菜单列表
                continue;
            }
            menu.setParentMenu(rootMenu);
            rootMenu.getChildList().add(menu);
            menuMap.put(rootMenu.getMenuId(),rootMenu);
        }
        menuList.clear();
        menuList.addAll(menuMap.values());
        Comparator<Menu> comparator = new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                if(o1.getMenuOrder() != o2.getMenuOrder()){
                    return o1.getMenuOrder() - o2.getMenuOrder();
                }
                return o2.getMenuOrder() - o1.getMenuOrder();
            }
        };
        Collections.sort(menuList,comparator);
        for(Menu menu : menuList){
            Collections.sort(menu.getChildList(),comparator);
        }
        return menuList;
    }



    @Override
    public Role saveOrUpdate(Role role) {
        return this.roleDao.saveOrUpdate(role);
    }
}
