package com.geekcattle.conf;

public enum ConstantEnum {
	/**
	 *  INFO删除状态
	 */
	INFO_DISABLE("1"),
	/**
	 * INFO删除状态
	 */
	INFO_ENABLE("0");	
	private String value;
	private ConstantEnum(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return value;
	}
}
