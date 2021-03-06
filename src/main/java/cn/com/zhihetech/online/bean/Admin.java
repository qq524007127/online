package cn.com.zhihetech.online.bean;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by YangDaiChun on 2015/11/12.
 */

@Entity
@Table(name = "t_admin")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Admin extends SerializableAndCloneable {

    private String adminId;
    private Set<Role> roles = new HashSet<Role>();
    private Merchant merchant;
    private String adminCode;
    private String adminPwd;
    private boolean permit;
    private boolean superAdmin; //是否是超级用户（超级管理员，拥有一切权限）
    private boolean official;   //是否是平台(官方)管理员

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid2")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "admin_id", length = 36)
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @OneToOne
    @JoinColumn(name = "merchant_id")
    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "admin_role", joinColumns = {@JoinColumn(name = "admin_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Column(name = "admin_code", length = 20, nullable = false, unique = true)
    public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }

    @Column(name = "admin_pwd", length = 32,nullable = false)
    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }

    @Column(name = "permit")
    public boolean isPermit() {
        return permit;
    }

    public void setPermit(boolean permit) {
        this.permit = permit;
    }

    @Column(name = "super_admin", nullable = false)
    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(boolean isAdmin) {
        this.superAdmin = isAdmin;
    }

    @Column(name = "sys_admin")
    public boolean isOfficial() {
        return official;
    }

    public void setOfficial(boolean official) {
        this.official = official;
    }
}
