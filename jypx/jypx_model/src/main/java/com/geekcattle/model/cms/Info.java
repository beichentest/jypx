package com.geekcattle.model.cms;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geekcattle.conf.ModuleEnum;
import com.geekcattle.model.BaseEntity;

import tk.mybatis.mapper.util.StringUtil;

@Table(name = "JYPX_CMS_INFO")
public class Info extends BaseEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4841172963727091967L;

	@Id
    @Column(name = "INFO_ID")
    @GeneratedValue(generator = "UUID")
    private String infoId;

    @Column(name = "INFO_NAME")
    private String infoName;

    @Column(name = "MODULE_ID")
    private String moduleId;

    @Column(name = "IMG_URL")
    private String imgUrl;

    @Column(name = "IS_OPEN")
    private String isOpen;

    @Column(name = "LINK_URL")
    private String linkUrl;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "UPDTE_DATE")
    private Date updteDate;

    @Column(name = "CODE")
    private String code;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "ORG_ID")
    private String orgId;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "TAG")
    private String tag;
    
    @Column(name = "SUMMARY")
    private String summary;
    
    @Column(name = "DEL_FLAG")
    private String delFlag;
    
    @Transient
    private String imgPath;
    @Transient
    private String typeView;
    @Transient
    private String ordView;
    
    @Transient
    @JsonIgnore
    private String sort = "";

    @Transient
    @JsonIgnore
    private String order = "";

    /**
     * @return INFO_ID
     */
    public String getInfoId() {
        return infoId;
    }

    /**
     * @param infoId
     */
    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    /**
     * @return INFO_NAME
     */
    public String getInfoName() {
        return infoName;
    }

    /**
     * @param infoName
     */
    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

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
     * @return IMG_URL
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * @param imgUrl
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * @return IS_OPEN
     */
    public String getIsOpen() {
        return isOpen;
    }

    /**
     * @param isOpen
     */
    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * @return LINK_URL
     */
    public String getLinkUrl() {
        return linkUrl;
    }

    /**
     * @param linkUrl
     */
    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    /**
     * @return CREATE_DATE
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return UPDTE_DATE
     */
    public Date getUpdteDate() {
        return updteDate;
    }

    /**
     * @param updteDate
     */
    public void setUpdteDate(Date updteDate) {
        this.updteDate = updteDate;
    }

    /**
     * @return CODE
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return REMARK
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return CONTENT
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return ORG_ID
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * @param orgId
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * @return AUTHOR
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return CREATE_USER
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * @return TAG
     */
    public String getTag() {
        return tag;
    }

    public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
     * @param tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getSort() {
        if(StringUtils.isEmpty(sort)){
            return "createDate";
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

	public String getOrdView() {
		return ordView;
	}

	public void setOrdView(String ordView) {
		this.ordView = ordView;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getTypeView() {
		if(StringUtils.isNotBlank(moduleId)){
			if(ModuleEnum.COURSE_CME.toString().equals(moduleId)){
				return ModuleEnum.COURSE_CME_VIEW.toString();
			}else if(ModuleEnum.COURSE_ABROAD.toString().equals(moduleId)){
				return ModuleEnum.COURSE_ABROAD_VIEW.toString();
			}else if(moduleId.startsWith(ModuleEnum.DOCTOR__EXAM.toString())){
				return ModuleEnum.DOCTOR__EXAM_VIEW.toString();
			}else if(moduleId.startsWith(ModuleEnum.NURSE_EXAM.toString())){
				return ModuleEnum.NURSE_EXAM_VIEW.toString();
			}
		}		
		return typeView;
	}

	public void setTypeView(String typeView) {
		this.typeView = typeView;
	} 	
}