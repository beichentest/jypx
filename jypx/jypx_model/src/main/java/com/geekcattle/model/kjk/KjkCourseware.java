package com.geekcattle.model.kjk;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geekcattle.model.BaseEntity;

@Table(name = "KJK_COURSEWARE")
public class KjkCourseware extends BaseEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5721815388624799496L;
	@Id
    @Column(name = "ID")
    @SequenceGenerator(name="",sequenceName="select KJK_COURSEWARE_SEQ.nextval from dual")
    private Long id;
    /**
     * 课件名称
     */
    @Column(name = "NAME")
    private String name;
    /**
     * 项目名称
     */
    @Column(name = "P_NAME")
    private String pName;
    /**
     * 编号
     */
    @Column(name = "CODE")
    private String code;
    /**
     * 路径
     */
    @Column(name = "PATH")
    private String path;
    /**
     * 文件类型
     */
    @Column(name = "FILE_TYPE")
    private String fileType;
    /**
     * 播放类型（PC）
     */
    @Column(name = "PLAY_TYPE")
    private String playType;
    /**
     * 三级学科
     */
    @Column(name = "SUBJECT")
    private String subject;
    /**
     * 关键字
     */
    @Column(name = "KEYWORD")
    private String keyword;
	/**
	 * 简介
	 */
    @Column(name = "INTRODUCE")
    private String introduce;
    /**
     * 专家
     */
    @Column(name = "EXPERT")
    private String expert;
    /**
     * 专家单位
     */
    @Column(name = "EXPERT_UNIT")
    private String expertUnit;
    /**
     * 来源
     */
    @Column(name = "SOURCE")
    private String source;
    /**
     * 创建时间
     */
    @Column(name = "CREATE_DATE")
    private Date createDate;
    /**
     * 添加时间
     */
    @Column(name = "ADD_DATE")
    private Date addDate;
    /**
     * 修改时间
     */
    @Column(name = "UPDATE_DATE")
    private Date updateDate;
    /**
     * 点击量
     */
    @Column(name = "CLICK_COUNT")
    private Integer clickCount;
    /**
     * 状态 -1下架,0有效,1删除
     */
    @Column(name = "STATUS")
    private Integer status;
    /**
     * 时长(秒)
     */
    @Column(name = "CLASS_TIME")
    private BigDecimal classTime;
    /**
     * 课时/学时
     */
    @Column(name = "CLASS_HOUR")
    private BigDecimal classHour;
    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;
    /**
     * 参数1
     */
    @Column(name = "PAR1")
    private String par1;
    /**
     * 参数2
     */
    @Column(name = "PAR2")
    private String par2;
    /**
     * 参数3
     */
    @Column(name = "PAR3")
    private String par3;
    /**
     * 参数4
     */
    @Column(name = "PAR4")
    private String par4;
    /**
     * 播放类型（手机）
     */
    @Column(name = "MOBILE_TYPE")
    private String mobileType;
    /**
     * 二级学科
     */
    @Column(name = "SUBJECT2")
    private String subject2;
    /**
     * 缩略图
     */
    @Column(name = "SL_IMG")
    private String slImg;
    /**
     * logo
     */
    @Column(name = "LOGO")
    private String logo;
    /**
     * 片头
     */
    @Column(name = "PIANTOU")
    private String piantou;
    /**
     * 片尾
     */
    @Column(name = "PIANWEI")
    private String pianwei;
    /**
     * 拍摄年份
     */
    @Column(name = "SHOT_YEAR")
    private String shotYear;
    /**
     * 时长(时:分:秒)
     */
    @Column(name = "CLASS_TIME_STR")
    private String classTimeStr;        
    /**
     * 
     */
    @Column(name = "LABEL")
    private String label;
    @Transient
    @JsonIgnore
    private String sort = "";

    @Transient
    @JsonIgnore
    private String order = "";
    /**
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return NAME
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return P_NAME
     */
    public String getpName() {
        return pName;
    }

    /**
     * @param pName
     */
    public void setpName(String pName) {
        this.pName = pName;
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
     * @return PATH
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return FILE_TYPE
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return PLAY_TYPE
     */
    public String getPlayType() {
        return playType;
    }

    /**
     * @param playType
     */
    public void setPlayType(String playType) {
        this.playType = playType;
    }

    /**
     * @return SUBJECT
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return KEYWORD
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * @return INTRODUCE
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * @param introduce
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    /**
     * @return EXPERT
     */
    public String getExpert() {
        return expert;
    }

    /**
     * @param expert
     */
    public void setExpert(String expert) {
        this.expert = expert;
    }

    /**
     * @return EXPERT_UNIT
     */
    public String getExpertUnit() {
        return expertUnit;
    }

    /**
     * @param expertUnit
     */
    public void setExpertUnit(String expertUnit) {
        this.expertUnit = expertUnit;
    }

    /**
     * @return SOURCE
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source
     */
    public void setSource(String source) {
        this.source = source;
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
     * @return CLICK_COUNT
     */
    public Integer getClickCount() {
        return clickCount;
    }

    /**
     * @param clickCount
     */
    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    /**
     * @return STATUS
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return CLASS_TIME
     */
    public BigDecimal getClassTime() {
        return classTime;
    }

    /**
     * @param classTime
     */
    public void setClassTime(BigDecimal classTime) {
        this.classTime = classTime;
    }

    /**
     * @return CLASS_HOUR
     */
    public BigDecimal getClassHour() {
        return classHour;
    }

    /**
     * @param classHour
     */
    public void setClassHour(BigDecimal classHour) {
        this.classHour = classHour;
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
     * @return PAR1
     */
    public String getPar1() {
        return par1;
    }

    /**
     * @param par1
     */
    public void setPar1(String par1) {
        this.par1 = par1;
    }

    /**
     * @return PAR2
     */
    public String getPar2() {
        return par2;
    }

    /**
     * @param par2
     */
    public void setPar2(String par2) {
        this.par2 = par2;
    }

    /**
     * @return PAR3
     */
    public String getPar3() {
        return par3;
    }

    /**
     * @param par3
     */
    public void setPar3(String par3) {
        this.par3 = par3;
    }

    /**
     * @return PAR4
     */
    public String getPar4() {
        return par4;
    }

    /**
     * @param par4
     */
    public void setPar4(String par4) {
        this.par4 = par4;
    }

    /**
     * @return MOBILE_TYPE
     */
    public String getMobileType() {
        return mobileType;
    }

    /**
     * @param mobileType
     */
    public void setMobileType(String mobileType) {
        this.mobileType = mobileType;
    }

    /**
     * @return SUBJECT2
     */
    public String getSubject2() {
        return subject2;
    }

    /**
     * @param subject2
     */
    public void setSubject2(String subject2) {
        this.subject2 = subject2;
    }

    /**
     * @return SL_IMG
     */
    public String getSlImg() {
        return slImg;
    }

    /**
     * @param slImg
     */
    public void setSlImg(String slImg) {
        this.slImg = slImg;
    }

    /**
     * @return LOGO
     */
    public String getLogo() {
        return logo;
    }

    /**
     * @param logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * @return PIANTOU
     */
    public String getPiantou() {
        return piantou;
    }

    /**
     * @param piantou
     */
    public void setPiantou(String piantou) {
        this.piantou = piantou;
    }

    /**
     * @return PIANWEI
     */
    public String getPianwei() {
        return pianwei;
    }

    /**
     * @param pianwei
     */
    public void setPianwei(String pianwei) {
        this.pianwei = pianwei;
    }

    /**
     * @return SHOT_YEAR
     */
    public String getShotYear() {
        return shotYear;
    }

    /**
     * @param shotYear
     */
    public void setShotYear(String shotYear) {
        this.shotYear = shotYear;
    }

	public String getClassTimeStr() {
		return classTimeStr;
	}

	public void setClassTimeStr(String classTimeStr) {
		this.classTimeStr = classTimeStr;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}     
}