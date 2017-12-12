package com.geekcattle.model.kjk;

import java.io.Serializable;

import javax.persistence.*;

import com.geekcattle.model.BaseEntity;

@Table(name = "NCME_SUBJECT")
public class NcmeSubject extends BaseEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6172108307093695264L;

	@Id
    @Column(name = "SUBJECT_ID")
    private String subjectId;

    @Column(name = "SUBJECT_NAME")
    private String subjectName;

    @Column(name = "WEIGHT")
    private Short weight;

    @Column(name = "SUBJECT2_ID")
    private String subject2Id;

    @Column(name = "SUBJECT2_NAME")
    private String subject2Name;

    @Column(name = "GUIDE")
    private String guide;

    @Column(name = "EXP_ID")
    private String expId;

    @Column(name = "KEY_GUIDE")
    private String keyGuide;

    @Column(name = "PRAC_GUIDE")
    private String pracGuide;

    /**
     * @return SUBJECT_ID
     */
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * @param subjectId
     */
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * @return SUBJECT_NAME
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * @param subjectName
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * @return WEIGHT
     */
    public Short getWeight() {
        return weight;
    }

    /**
     * @param weight
     */
    public void setWeight(Short weight) {
        this.weight = weight;
    }

    /**
     * @return SUBJECT2_ID
     */
    public String getSubject2Id() {
        return subject2Id;
    }

    /**
     * @param subject2Id
     */
    public void setSubject2Id(String subject2Id) {
        this.subject2Id = subject2Id;
    }

    /**
     * @return SUBJECT2_NAME
     */
    public String getSubject2Name() {
        return subject2Name;
    }

    /**
     * @param subject2Name
     */
    public void setSubject2Name(String subject2Name) {
        this.subject2Name = subject2Name;
    }

    /**
     * @return GUIDE
     */
    public String getGuide() {
        return guide;
    }

    /**
     * @param guide
     */
    public void setGuide(String guide) {
        this.guide = guide;
    }

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
     * @return KEY_GUIDE
     */
    public String getKeyGuide() {
        return keyGuide;
    }

    /**
     * @param keyGuide
     */
    public void setKeyGuide(String keyGuide) {
        this.keyGuide = keyGuide;
    }

    /**
     * @return PRAC_GUIDE
     */
    public String getPracGuide() {
        return pracGuide;
    }

    /**
     * @param pracGuide
     */
    public void setPracGuide(String pracGuide) {
        this.pracGuide = pracGuide;
    }
}