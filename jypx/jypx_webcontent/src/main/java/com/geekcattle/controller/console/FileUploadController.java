package com.geekcattle.controller.console;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.geekcattle.conf.ModuleEnum;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.util.UploadUtil;

/**
 * author geekcattle
 * date  2017/5/24 0024 下午 3:40.
 */
@Controller
@RequestMapping("/console/upload")
public class FileUploadController {
	private final static Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	@Value("${spring.upload.path}")
	private String uploadPath;
	@Value("${spring.img.url}")
	private String imgPath;			
	private static Map<String,Object> config = new HashMap<String, Object>() {
		 {
		     put("imageActionName", "/console/upload/save");
		     put("imageFieldName", "imgFile");
		     put("imageAllowFiles",new String[]{".png",".jpg",".jpeg",".gif",".bmp",});
		     put("imageCompressEnable",true);
		 }
	};	
    @RequestMapping("/index")
    public String index(){
        return "console/file/index";
    }
    @ResponseBody  
    @RequestMapping("/config")
    public Map<String, Object> config(String action,String noCache){
    	config.put("imageUrlPrefix", imgPath);
        return config;
    }
    @RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> save(@RequestParam(value = "imgFile", required = false) MultipartFile file) {		
    	Map<String, Object> params = new HashMap<String, Object>();
    	String imgUrl = "";
    	if (file != null) {
	    	try {									
	    		imgUrl = UploadUtil.uploadFile(file, uploadPath);		
				params.put("state", "SUCCESS");
			} catch (Exception e) {
				logger.error("======", e);
				e.printStackTrace();
				params.put("state", "ERROR");
			}
	    	params.put("url", imgUrl);
	    	params.put("title", file.getOriginalFilename());
	    	params.put("original", file.getOriginalFilename());
    	}
		return params;
	}
}
