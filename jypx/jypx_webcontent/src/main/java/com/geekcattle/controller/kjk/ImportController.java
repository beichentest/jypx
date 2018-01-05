package com.geekcattle.controller.kjk;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.geekcattle.util.FileUtil;
import com.geekcattle.util.IdUtil;

@Controller
public class ImportController {	
	private final static Logger logger = LoggerFactory.getLogger(ImportController.class);
	@Value("${upload.courseware.errfilepath}")
	private String uploadCoursewareErrPath;	
	@Value("${upload.courseware.template}")
	private String coursewareTemplate;
	@Value("${upload.question.template}")
	private String questionTemplate;
	@RequestMapping("/console/fromImport")
	public String fromImport(Model model, String type,String errFile) {
		if("courseware".equals(type)) {
			model.addAttribute("type", "import");
			model.addAttribute("action", "/console/kjk/courseware/importCourseware");
			model.addAttribute("template", coursewareTemplate);
			model.addAttribute("name", "上传课件");			
		}else if("err".equals(type)) {
			model.addAttribute("type", "err");
			model.addAttribute("name", "下载错误文件");
			model.addAttribute("errFile",errFile);
		}else if("close".equals(type)) {
			model.addAttribute("type", "close");
		}else if("question".equals(type)) {
			model.addAttribute("type", "import");
			model.addAttribute("action", "/console/kjk/question/importQuestion");
			model.addAttribute("template", questionTemplate);
			model.addAttribute("name", "上传试题");
		}		
		return "console/kjk/importData";
	}
	@RequestMapping("/console/downloadErr")
	public void downloadErrFile(HttpServletRequest request, HttpServletResponse response,String errFile) {
		String filePath = request.getSession().getServletContext().getRealPath(uploadCoursewareErrPath)+File.separator+errFile;
		File file = new File(filePath);
		response.setHeader("content-type", "application/octet-stream");
		response.setContentType("application/octet-stream");		
		response.setHeader("Content-Disposition", "attachment;filename=" + errFile);
		byte[] buff = new byte[1024];
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			bis = new BufferedInputStream(new FileInputStream(file));
			int i = bis.read(buff);
			while (i != -1) {
				os.write(buff, 0, buff.length);
				os.flush();
				i = bis.read(buff);
			}
		} catch (IOException e) {
			logger.error("======下载导入异常文件出现错误", e);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
