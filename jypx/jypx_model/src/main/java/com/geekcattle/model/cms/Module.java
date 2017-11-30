package com.geekcattle.model.cms;

import javax.persistence.*;

import com.geekcattle.model.BaseEntity;

@Table(name = "JYPX_CMS_MODULE")
public class Module extends BaseEntity {
    @Id
    @Column(name = "MODULE_ID")
    @GeneratedValue(generator = "UUID")
    private String moduleId;

    @Column(name = "MODULE_NAME")
    private String moduleName;

    @Column(name = "PARENT_ID")
    private String parentId;

    /**
     * @return MODULE_ID
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * @return MODULE_NAME
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @param moduleName
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * @return PARENT_ID
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}