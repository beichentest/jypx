package com.geekcattle.vo.kjk;
 
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
 
public class CoursewareVo  implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 6910030932341987148L;
    /**
     * 
     */
    @Excel(name="课件ID")
    private Long id;
    /**
     * 课件名称
     */
    @Excel(name="课件名称")
    private String name;
    /**
     * 项目名称
     */
    @Excel(name="课程名称")
    private String pName;
    /**
     * 编号
     */
    @Excel(name="课件编号")
    private String code;
    /**
     * 路径
     */
    @Excel(name="路径")
    private String path;
    /**
     * 文件类型
     */
    @Excel(name="文件类型")
    private String fileType;
    /**
     * 播放类型（PC）
     */    
    private String playType;
    /**
     * 三级学科
     */   
    @Excel(name="三级学科")
    private String subject;
    /**
     * 关键字
     */
    @Excel(name="关键字")
    private String keyword;
    /**
     * 简介
     */
    @Excel(name="简介")
    private String introduce;
    /**
     * 专家
     */
    @Excel(name="专家")
    private String expert;
    /**
     * 专家单位
     */
    @Excel(name="专家单位")
    private String expertUnit;
    /**
     * 来源
     */
    @Excel(name="来源")
    private String source;
    /**
     * 创建时间
     */
    @Excel(name="创建时间",format="yyyy-MM-dd")
    private Date createDate;
    /**
     * 添加时间
     */
    @Excel(name="添加时间",format="yyyy-MM-dd")
    private Date addDate;
    /**
     * 修改时间
     */
    @Excel(name="修改时间",format="yyyy-MM-dd")
    private Date updateDate;
    /**
     * 点击量
     */
    @Excel(name="点击量")
    private Integer clickCount;
    /**
     * 状态 0有效1删除
     */
    @Excel(name="状态",replace= {"有效_0","删除_1","下架_-1"})
    private Integer status;
    /**
     * 时长(秒)
     */
    @Excel(name="时长(秒)")
    private BigDecimal classTime;
    /**
     * 课时/学时
     */
    @Excel(name="课时/学时")
    private BigDecimal classHour;
    /**
     * 备注
     */
    @Excel(name="备注")
    private String remark;
    /**
     * 参数1
     */
    @Excel(name="参数1")
    private String par1;
    /**
     * 参数2
     */
    @Excel(name="参数2")
    private String par2;
    /**
     * 参数3
     */
    @Excel(name="参数3")
    private String par3;
    /**
     * 参数4
     */
    @Excel(name="参数4")
    private String par4;
    /**
     * 播放类型（手机）
     */    
    private String mobileType;
    /**
     * 二级学科
     */  
    @Excel(name="二级学科")
    private String subject2;
    /**
     * 缩略图
     */
    @Excel(name="缩略图")
    private String slImg;
    /**
     * logo
     */
    @Excel(name="logo")
    private String logo;
    /**
     * 片头
     */
    @Excel(name="片头")
    private String piantou;
    /**
     * 片尾
     */
    @Excel(name="片尾")
    private String pianwei;
    /**
     * 拍摄年份
     */
    @Excel(name="拍摄年份")
    private String shotYear;
    /**
     * 时长(时:分:秒)
     */
    @Excel(name="时长(时:分:秒)")
    private String classTimeStr;        
    
    @Excel(name="标签")
    private String label;
    
    @Excel(name="付费标志",replace= {"未付费_0","已付费_1","_0"})
    private String playFlag;
    
    @Excel(name="项目级别",replace= {"普通_0","国家级_1","_0"})
    private String projectLevel;
    @Excel(name="播放类型（PC）")   
    private String playTypeText;
    @Excel(name="播放类型（手机）")
    private String mobileTypeText;
    /**
     * 课件创建人id
     */
    private String creater;    
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
 
    public String getPlayFlag() {
        return playFlag;
    }
 
    public void setPlayFlag(String playFlag) {
        this.playFlag = playFlag;
    }

	public String getProjectLevel() {
		return projectLevel;
	}

	public void setProjectLevel(String projectLevel) {
		this.projectLevel = projectLevel;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getPlayTypeText() {
		return playTypeText;
	}

	public void setPlayTypeText(String playTypeText) {
		this.playTypeText = playTypeText;
	}

	public String getMobileTypeText() {
		return mobileTypeText;
	}

	public void setMobileTypeText(String mobileTypeText) {
		this.mobileTypeText = mobileTypeText;
	} 
}