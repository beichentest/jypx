package com.geekcattle.vo.kjk;

import java.io.Serializable;

public class Option implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -826155951943872686L;
	
	private String alisa;	 
	private String text;
	private String type;
	private boolean checked;
	public String getAlisa() {
		return alisa;
	}
	public void setAlisa(String alisa) {
		this.alisa = alisa;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(alisa).append("：").append(text).append("； ");
		return sb.toString();
	}
}
