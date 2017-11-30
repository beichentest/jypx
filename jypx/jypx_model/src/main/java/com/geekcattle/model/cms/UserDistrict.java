package com.geekcattle.model.cms;

import java.util.List;

import javax.persistence.*;

@Table(name = "USER_DISTRICT")
public class UserDistrict {
    @Id
    @Column(name = "DISTRICT_ID")
    private String districtId;

    @Column(name = "DISTRICT_NAME")
    private String districtName;

    @Column(name = "PARENT_ID")
    private String parentId;

    @Column(name = "DIALLING_CODE")
    private String diallingCode;

    @Column(name = "CARD_FLAG")
    private Short cardFlag;
    
    @Transient
    private  List<UserDistrict> Children;
    /**
     * @return DISTRICT_ID
     */
    public String getDistrictId() {
        return districtId;
    }

    /**
     * @param districtId
     */
    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    /**
     * @return DISTRICT_NAME
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * @param districtName
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
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

    /**
     * @return DIALLING_CODE
     */
    public String getDiallingCode() {
        return diallingCode;
    }

    /**
     * @param diallingCode
     */
    public void setDiallingCode(String diallingCode) {
        this.diallingCode = diallingCode;
    }

    /**
     * @return CARD_FLAG
     */
    public Short getCardFlag() {
        return cardFlag;
    }

    /**
     * @param cardFlag
     */
    public void setCardFlag(Short cardFlag) {
        this.cardFlag = cardFlag;
    }

	public List<UserDistrict> getChildren() {
		return Children;
	}

	public void setChildren(List<UserDistrict> children) {
		Children = children;
	}    
}