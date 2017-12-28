package com.geekcattle.model.kjk;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geekcattle.model.BaseEntity;
import com.geekcattle.util.JsonUtil;
import com.geekcattle.vo.kjk.Option;

@Table(name = "KJK_QUESTION")
public class KjkQuestion extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4876079301605043725L;

	@Id
    @Column(name = "Q_ID")	
	@GeneratedValue(generator = "UUID")
    private String qId;

    @Column(name = "CW_ID")
    @NotNull(message="课件ID不能为空")
    private Long cwId;
    /**
     * 试题类型
     */
    @Column(name = "Q_TYPE")
    @NotEmpty(message="试题类型不能为空")
    private String qType;
    /**
     * 题型
     */
    @Column(name = "Q_CLASS")
    @NotEmpty(message="题型不能为空")
    private String qClass;
    /**
     * 试题难度
     */
    @Column(name = "Q_LEVEL")
    private String qLevel;
    /**
     * 题干
     */
    @Column(name = "CONTENT")
    @NotEmpty(message="题干不能为空")
    private String content;
    /**
     * 答案
     */
    @Column(name = "Q_KEY")
    private String qKey;
    /**
     * 解析
     */
    @Column(name = "RESOLVE")
    private String resolve;
    /**
     * 过程题出现时间
     */
    @Column(name = "EXECUTE_TIME")
    private String executeTime;
    /**
     * 选项内容
     */
    @Column(name = "Q_DATA")
    private String qData;
    /**
     * 创建时间
     */
    @Column(name = "CREATE_DATE")
    private Date createDate;
    /**
     * 修改时间
     */
    @Column(name = "MODIFY_DATE")
    private Date modifyDate;
    /**
     * 操作人
     */
    @Column(name = "OPERATOR")
    private String operator;
    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;
    /**
     * 数据状态（0 正常,1 失效）
     */
    @Column(name = "STATUS")
    private String status;
    /**
     * 排序 降序
     */
    @Column(name = "SEQ")
    private Integer seq;
    /**
     * 选项
     */
    @Transient    
    private List<Option> options;
    
    @Transient    
    private String[]  keys;
    
    @Transient    
    private String qClassV;
    
    @Transient    
    private String qTypeV;
    
    @Transient
    private String optionsV;
    
    @Transient
    @JsonIgnore
    private String sort = "";

    @Transient
    @JsonIgnore
    private String order = "";
    /**
     * @return Q_ID
     */
    public String getqId() {
        return qId;
    }

    /**
     * @param qId
     */
    public void setqId(String qId) {
        this.qId = qId;
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
     * @return Q_TYPE
     */
    public String getqType() {
        return qType;
    }

    /**
     * @param qType
     */
    public void setqType(String qType) {
        this.qType = qType;
    }

    /**
     * @return Q_CLASS
     */
    public String getqClass() {
        return qClass;
    }

    /**
     * @param qClass
     */
    public void setqClass(String qClass) {
        this.qClass = qClass;
    }

    /**
     * @return Q_LEVEL
     */
    public String getqLevel() {
        return qLevel;
    }

    /**
     * @param qLevel
     */
    public void setqLevel(String qLevel) {
        this.qLevel = qLevel;
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
     * @return Q_KEY
     */
    public String getqKey() {
        return qKey;
    }

    /**
     * @param qKey
     */
    public void setqKey(String qKey) {
        this.qKey = qKey;
    }

    /**
     * @return RESOLVE
     */
    public String getResolve() {
        return resolve;
    }

    /**
     * @param resolve
     */
    public void setResolve(String resolve) {
        this.resolve = resolve;
    }

    /**
     * @return EXECUTE_TIME
     */
    public String getExecuteTime() {
        return executeTime;
    }

    /**
     * @param executeTime
     */
    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    /**
     * @return Q_DATA
     */
    public String getqData() {
        return qData;
    }

    /**
     * @param qData
     */
    public void setqData(String qData) {
        this.qData = qData;
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
     * @return MODIFY_DATE
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * @param modifyDate
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
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

	public String getqClassV() {
		return qClassV;
	}

	public void setqClassV(String qClassV) {
		this.qClassV = qClassV;
	}

	public String getqTypeV() {
		return qTypeV;
	}

	public void setqTypeV(String qTypeV) {
		this.qTypeV = qTypeV;
	}

	public String getOptionsV() {
		if(StringUtils.isNotBlank(qData)) {
			List<Option> list = JsonUtil.parse(qData,Option.class);
			StringBuilder sb = new StringBuilder();
			for (Option option : list) {
				sb.append(option.toString());
			}
			return sb.toString();
		}
		return optionsV;
	}

	public void setOptionsV(String optionsV) {
		this.optionsV = optionsV;
	}	
}