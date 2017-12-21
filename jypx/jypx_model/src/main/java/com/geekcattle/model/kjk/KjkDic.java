package com.geekcattle.model.kjk;

import java.io.Serializable;

import javax.persistence.*;

import com.geekcattle.model.BaseEntity;

@Table(name = "KJK_DIC")
public class KjkDic extends BaseEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1362898206521146287L;

	@Id
    @Column(name = "DIC_ID")
    private String dicId;

    @Column(name = "DIC_DESC")
    private String dicDesc;

    @Column(name = "DIC_TYPE")
    private String dicType;

    @Column(name = "PARENT_ID")
    private String parentId;

    @Column(name = "SEQ")
    private Integer seq;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "REMARK")
    private String remark;

    /**
     * @return DIC_ID
     */
    public String getDicId() {
        return dicId;
    }

    /**
     * @param dicId
     */
    public void setDicId(String dicId) {
        this.dicId = dicId;
    }

    /**
     * @return DIC_DESC
     */
    public String getDicDesc() {
        return dicDesc;
    }

    /**
     * @param dicDesc
     */
    public void setDicDesc(String dicDesc) {
        this.dicDesc = dicDesc;
    }

    /**
     * @return DIC_TYPE
     */
    public String getDicType() {
        return dicType;
    }

    /**
     * @param dicType
     */
    public void setDicType(String dicType) {
        this.dicType = dicType;
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
     * @return SEQ
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
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
}