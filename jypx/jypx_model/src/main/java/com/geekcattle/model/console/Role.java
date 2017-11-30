/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.model.console;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geekcattle.model.BaseEntity;
import com.geekcattle.util.DateUtil;

/**
 * author geekcattle
 * date 2016/10/21 0021 下午 15:11
 */
@Table(name = "JYPX_ROLE")
public class Role extends BaseEntity {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(generator = "UUID")
    private String roleId;

    @NotEmpty(message="角色名称不能为空")
    private String roleName;

    private String roleDesc;

    @Column(columnDefinition="enum(1,0)")
    private Integer enable;

    private Date createdAt;

    private Date updatedAt;
    
    @Transient
    private String createdAtText;
    @Transient
    private String updatedAtText;

    @Transient
    @JsonIgnore
    private String sort = "";

    @Transient
    @JsonIgnore
    private String order = "";

    //角色 -- 权限关系：多对多关系;
    @Transient
    private List<Menu> menuList;

    // 用户 - 角色关系定义;

    @Transient
    private List<Admin> adminList;// 一个角色对应多个用户*/

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSort() {
        if(StringUtils.isEmpty(sort)){
            return "createdAt";
        }else{
            return sort;
        }
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        if(StringUtils.isEmpty(sort)){
            return "desc";
        }else{
            return order;
        }
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public List<Admin> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<Admin> adminList) {
        this.adminList = adminList;
    }

    public String getCreatedAtText() {
		if(createdAt!=null)
			return DateUtil.dateToString(createdAt, DateUtil.yyyyMMddHHmmss);
		return "";
	}

	public void setCreatedAtText(String createdAtText) {
		this.createdAtText = createdAtText;
	}

	public String getUpdatedAtText() {
		if(updatedAt!=null)
			return DateUtil.dateToString(updatedAt, DateUtil.yyyyMMddHHmmss);
		return "";
	}

	public void setUpdatedAtText(String updatedAtText) {
		this.updatedAtText = updatedAtText;
	}

	@Override
    public String toString() {
        return "Role{" +
                "roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                ", enable=" + enable +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                ", menuList=" + menuList +
                ", adminList=" + adminList +
                '}';
    }
}
