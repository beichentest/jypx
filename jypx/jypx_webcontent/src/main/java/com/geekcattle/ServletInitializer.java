package com.geekcattle;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Title: ServletInitializer Description: �൱������Web.xml Company:
 * blog.csdn.net/lu1005287365/
 * 
 * @author L lulu
 * @version 1.0
 */
public class ServletInitializer {//extends SpringBootServletInitializer {

	//@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

}