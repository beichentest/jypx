package com.geekcattle.model.kjk;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geekcattle.model.BaseEntity;

@Table(name = "KJK_COST")
public class KjkCost extends BaseEntity implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2766533788321622168L;

	@Id
    @Column(name = "COST_ID")
    private String costId;

    @Column(name = "CW_ID")
    private Long cwId;

    @Column(name = "EXPERT_ID")
    private String expertId;

    @Column(name = "COST")
    private Long cost;

    @Column(name = "CARD_NO")
    @NotBlank(message="银行卡号不能为空")
    private String cardNo;

    @Column(name = "OPENING_BANK")
    @NotBlank(message="银行名称不能为空")
    private String openingBank;

    @Column(name = "SYSTEM")
    @NotBlank(message="归属项目不能为空")
    private String system;

    @Column(name = "PAY_DESC")
    private String payDesc;

    @Column(name = "APPLY_TIME")
    private Date applyTime;

    @Column(name = "PAY_TIME")
    private Date payTime;

    @Column(name = "AUDIT_STATUS")
    private String auditStatus;

    @Column(name = "OPERATOR")
    private String operator;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "REMARK")
    private String remark;
    /**
     * 专家姓名
     */
    @Column(name="EXPERT_NAME")
    @NotBlank(message="专家姓名不能为空")
    private String expertName;
    /**
     * 身份证号
     */
    @Column(name="ID_CARD")
    @NotBlank(message="身份证号不能为空")
    private String idCard;
    /**
     * 手机号
     */
    @Column(name="MOBILE")
    @NotBlank(message="手机号不能为空")
    private String mobile;
    
    @Column(name="CWARE_NAME")
    private String cwareName;
    
    @Transient
    @JsonIgnore
    private String sort = "";

    @Transient
    @JsonIgnore
    private String order = "";
    
    private String beginTime;
    private String endTime;
    
    /**
     * @return COST_ID
     */
    public String getCostId() {
        return costId;
    }

    /**
     * @param costId
     */
    public void setCostId(String costId) {
        this.costId = costId;
    }

    /**
     * @return CW_ID
     */
    public Long getCwId() {
        return cwId;
    }

    /**
     * @param cwId
     */
    public void setCwId(Long cwId) {
        this.cwId = cwId;
    }

    /**
     * @return EXPERT_ID
     */
    public String getExpertId() {
        return expertId;
    }

    /**
     * @param expertId
     */
    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    /**
     * @return COST
     */
    public Long getCost() {
        return cost;
    }

    /**
     * @param cost
     */
    public void setCost(Long cost) {
        this.cost = cost;
    }

    /**
     * @return CARD_NO
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * @param cardNo
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * @return OPENING_BANK
     */
    public String getOpeningBank() {
        return openingBank;
    }

    /**
     * @param openingBank
     */
    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank;
    }

    /**
     * @return SYSTEM
     */
    public String getSystem() {
        return system;
    }

    /**
     * @param system
     */
    public void setSystem(String system) {
        this.system = system;
    }

    /**
     * @return PAY_DESC
     */
    public String getPayDesc() {
        return payDesc;
    }

    /**
     * @param payDesc
     */
    public void setPayDesc(String payDesc) {
        this.payDesc = payDesc;
    }

    /**
     * @return APPLY_TIME
     */
    public Date getApplyTime() {
        return applyTime;
    }

    /**
     * @param applyTime
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * @return PAY_TIME
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * @param payTime
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * @return AUDIT_STATUS
     */
    public String getAuditStatus() {
        return auditStatus;
    }

    /**
     * @param auditStatus
     */
    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * @return OPERATOR
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * @return STATUS
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
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

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCwareName() {
		return cwareName;
	}

	public void setCwareName(String cwareName) {
		this.cwareName = cwareName;
	}
    
}