package com.geekcattle.model.kjk;

import java.io.Serializable;

import javax.persistence.*;

import com.geekcattle.model.BaseEntity;

@Table(name = "KJK_PLAY_TYPE")
public class KjkPlayType extends BaseEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -9064881463252293399L;

	@Id
    @Column(name = "PLAY_TYPE")
    private String playType;

    @Column(name = "PLAY_TYPE_NAME")
    private String playTypeName;

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
     * @return PLAY_TYPE_NAME
     */
    public String getPlayTypeName() {
        return playTypeName;
    }

    /**
     * @param playTypeName
     */
    public void setPlayTypeName(String playTypeName) {
        this.playTypeName = playTypeName;
    }
}