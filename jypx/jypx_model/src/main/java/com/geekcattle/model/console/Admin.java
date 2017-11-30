/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.model.console;

import java.io.Serializable;
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
@Table(name = "JYPX_admin")
public class Admin extends BaseEntity  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2256480669860906207L;

	@Id
    @Column(name = "userid")
    @GeneratedValue(generator = "UUID")
    private String uid;

    @NotEmpty(message="账号不能为空")
    private String username;

    private String password;

    private String salt;

    private Integer state;

    private Integer isSystem;

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

    @Transient
    private String[] roleId;

    @Transient
    private List<Role> roleList;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Integer isSystem) {
        this.isSystem = isSystem;
    }

    public Date  getCreatedAt() {
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

    /**
     * 密码盐.
     * @return
     */
    public String getCredentialsSalt(){
        return this.username+this.salt;
    }

    public String[] getRoleId() {
        return roleId;
    }

    public void setRoleId(String[] roleId) {
        this.roleId = roleId;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
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
        return "Admin{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", state=" + state +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                ", roleList=" + roleList +
                '}';
    }

}