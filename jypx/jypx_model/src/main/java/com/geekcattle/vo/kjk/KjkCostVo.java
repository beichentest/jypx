package com.geekcattle.vo.kjk;
 
import java.io.Serializable;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
 
public class KjkCostVo  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1100414633802463024L;
	/**
     * 劳务费id
     */
    @Excel(name = "劳务费ID")
    private String costId;
    /**
     * 课件ID
     */
    @Excel(name = "课件ID")
    private Long cwId;

    /**
     * 专家
     */
    @Excel(name = "专家")
    private String expertId;

    /**
     * 劳务费用
     */
    @Excel(name = "劳务费用")
    private Long cost;

    /**
     * 银行卡号
     */
    @Excel(name = "银行卡号")
    private String cardNo;

    /**
     * 开户行
     */
    @Excel(name = "开户行")
    private String openingBank;

    /**
     * 归属项目
     */
    @Excel(name = "归属项目")
    private String system;

    /**
     * 支付描述
     */
    @Excel(name = "支付描述")
    private String payDesc;

    /**
     * 申请时间
     */
    @Excel(name = "申请时间",format="yyyy-MM-dd")
    private Date applyTime;

    /**
     * 支付时间
     */
    @Excel(name = "支付时间",format="yyyy-MM-dd")
    private Date payTime;

    /**
     * 审核状态(0 未审核，1 已审核)
     */
    @Excel(name = "审核状态",replace= {"未审核_0","已审核_1"})
    private String auditStatus;

    /**
     * 操作人
     */
    @Excel(name = "操作人")
    private String operator;

    /**
     * 数据状态(0 正常，1 失效)
     */
    @Excel(name = "数据状态",replace= {"正常_0","失效_1"})
    private String status;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;
    /**
     * 专家姓名
     */
    @Excel(name="专家姓名")
    private String expertName;
    /**
     * 身份证号
     */
    @Excel(name="身份证号")
    private String idCard;
    /**
     * 手机号
     */
    @Excel(name="手机号")
    private String mobile;
    
    /**
     * 课件名称
     */
    @Excel(name="课件名称")
    private String cwareName;

	public String getCostId() {
		return costId;
	}

	public void setCostId(String costId) {
		this.costId = costId;
	}

	public Long getCwId() {
		return cwId;
	}

	public void setCwId(Long cwId) {
		this.cwId = cwId;
	}

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}

	public Long getCost() {
		return cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOpeningBank() {
		return openingBank;
	}

	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getPayDesc() {
		return payDesc;
	}

	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExpertName() {
		return expertName;
	}

	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCwareName() {
		return cwareName;
	}

	public void setCwareName(String cwareName) {
		this.cwareName = cwareName;
	}
    

}