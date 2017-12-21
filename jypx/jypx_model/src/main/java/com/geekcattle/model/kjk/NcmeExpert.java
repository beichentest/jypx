package com.geekcattle.model.kjk;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import com.geekcattle.model.BaseEntity;

@Table(name = "NCME_EXPERT_NEW")
public class NcmeExpert extends BaseEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -534555978321347338L;

	@Id
    @Column(name = "EXP_ID")
    private String expId;

    @Column(name = "AREA")
    private String area;

    @Column(name = "MAJOR")
    private String major;

    @Column(name = "EXP_NAME")
    private String expName;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "UNIT")
    private String unit;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ID_CARD")
    private String idCard;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "BANK_CARD")
    private String bankCard;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "ADD_DATE")
    private Date addDate;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @Column(name = "LOG")
    private String log;

    /**
     * @return EXP_ID
     */
    public String getExpId() {
        return expId;
    }

    /**
     * @param expId
     */
    public void setExpId(String expId) {
        this.expId = expId;
    }

    /**
     * @return AREA
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return MAJOR
     */
    public String getMajor() {
        return major;
    }

    /**
     * @param major
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * @return EXP_NAME
     */
    public String getExpName() {
        return expName;
    }

    /**
     * @param expName
     */
    public void setExpName(String expName) {
        this.expName = expName;
    }

    /**
     * @return TITLE
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return UNIT
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return TEL
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return MOBILE
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return EMAIL
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return ID_CARD
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * @param idCard
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * @return BANK_NAME
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * @return BANK_CARD
     */
    public String getBankCard() {
        return bankCard;
    }

    /**
     * @param bankCard
     */
    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    /**
     * @return ADDRESS
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
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
     * @return ADD_DATE
     */
    public Date getAddDate() {
        return addDate;
    }

    /**
     * @param addDate
     */
    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    /**
     * @return UPDATE_DATE
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return LOG
     */
    public String getLog() {
        return log;
    }

    /**
     * @param log
     */
    public void setLog(String log) {
        this.log = log;
    }
}