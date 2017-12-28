package com.geekcattle.vo.kjk;

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

import cn.afterturn.easypoi.excel.annotation.Excel;

public class QuestionVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8187649878130224235L;
	

	@Excel(name="课件ID")
    private Long cwId;
	
	@Excel(name="试题类型")
    private String qType;
	
	@Excel(name="题型")
    private String qClass;
    
	@Excel(name="题干")
    private String content;
	
	@Excel(name="答案")
    private String qKey;
	
	@Excel(name="解析")
    private String resolve;
	
	@Excel(name="出现时间")
    private String executeTime;
	
	@Excel(name="选项A")
    private String optionA;
	@Excel(name="选项B")
    private String optionB;  
	@Excel(name="选项C")
    private String optionC;  
	@Excel(name="选项D")
    private String optionD;
	@Excel(name="选项E")
    private String optionE;
	@Excel(name="选项F")
    private String optionF;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 备注
     */
    private String remark;
    
    private String option;
    
    private String qId;

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


    public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public String getOptionE() {
		return optionE;
	}

	public void setOptionE(String optionE) {
		this.optionE = optionE;
	}

	public String getOptionF() {
		return optionF;
	}

	public void setOptionF(String optionF) {
		this.optionF = optionF;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
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

	public String getqId() {
		return qId;
	}

	public void setqId(String qId) {
		this.qId = qId;
	}	
}