package com.geekcattle.service.importdata.filter;

/**
 * 导入文件用户信息过滤器
 * @author Administrator
 *
 */
public interface ImportDataFilter<T> {
	String doFilter(T t);
}
