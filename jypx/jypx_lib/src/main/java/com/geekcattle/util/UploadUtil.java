package com.geekcattle.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {
	public static String uploadFile(MultipartFile file,String path) throws IOException {
		Random rand = new Random();
		if (file != null) {
			// 1、用户照片按月存储
			String folderName =  DateUtil.dateToString(new Date(), "yyyyMM");
			File folderDic = new File(path+folderName);
			if (!folderDic.exists())
				folderDic.mkdirs();
			// 2、将输入的文件流按时间戳进行文件存储
			String fileName = System.currentTimeMillis() + StringPool.BLANK + rand.nextInt(20) + StringPool.PERIOD
					+ FilenameUtils.getExtension(file.getOriginalFilename());
			File destFile = new File(path+folderName, fileName);
			FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
			// 3、上传成功后将文件访问路径返回
			return folderName + "/" + fileName;
		}
		return null;
	}
}
