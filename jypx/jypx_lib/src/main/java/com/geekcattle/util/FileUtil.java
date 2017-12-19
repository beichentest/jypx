package com.geekcattle.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;

public class FileUtil {
	private static String basePath=System.getProperty("user.dir")+File.separator;
    
    private static final char UNIX_SEPARATOR = '/';

	private static final char WINDOWS_SEPARATOR = '\\';
	/**
	 * 写文件 
	 * @param f
	 * @param content
	 */
	public static void writeFile(File f,String content){
		  writeFile(f, content,"utf-8");
	}
	/**
	 * 写文件 
	 * @param f
	 * @param content
	 */
	public static void writeFile(File f,String content,String encode){
		  try {
		   if (!f.exists()) {
		    f.createNewFile();
		   }
		   OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(f),encode);
		   BufferedWriter utput = new BufferedWriter(osw);
		   utput.write(content);
		   utput.close();
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
	}
	/**
	 * 写文件
	 * @param path
	 * @param content
	 */
	public static void writeFile(String path, String content,String encode) {
	       File f = new File(path);
	       writeFile(f, content,encode);
	}
	/**
	 * 写文件
	 * @param path
	 * @param content
	 */
	public static void writeFile(String path, String content) {
	       File f = new File(path);
	       writeFile(f, content,"utf-8");
	}

	/**
	 * 读取文件
	 * @param file
	 * @return
	 */
	public static String readFile(File file){
		return readFile(file,"UTF-8");
	}
	/**
	 * 读取文件
	 * @param file
	 * @return
	 */
	public static String readFile(File file,String encode){
		String output = "";

		if (file.exists()) {
			if (file.isFile()) {
				try {
					InputStreamReader isr=new InputStreamReader(new FileInputStream(file),encode);
					BufferedReader input = new BufferedReader(isr);
					StringBuffer buffer = new StringBuffer();
					String text;
					while ((text = input.readLine()) != null)
						buffer.append(text + "\n");
					output = buffer.toString();

				} catch (IOException ioException) {
					System.err.println("File Error！");
				}
			} else if (file.isDirectory()) {
				String[] dir = file.list();
				output += "Directory contents：\n";
				for (int i = 0; i < dir.length; i++) {
					output += dir[i] + "\n";
				}
			}

		} else {
			System.err.println("Does not exist！");
		}

		return output;
	}
	/**
	 * 读取文件
	 * @param fileName
	 * @return
	 */
	public static String readFile(String fileName,String encode) {
		File file = new File(fileName);
		return readFile(file,encode);
	}
	/**
	 * 读取文件
	 * @param fileName
	 * @return
	 */
	public static String readFile(String fileName) {
		return readFile(fileName,"utf-8");
	}


	/**
	 * 获取目录下所有文件
	 * @param folder
	 * @return
	 */
	public static List<File> getFiles(String folder){
		File file=new File(folder);
		List<File> files=new ArrayList<File>();
		if (file.exists()) {
			File[] sonFiles=file.listFiles();
			if (sonFiles!=null && sonFiles.length>0) {
				for (int i = 0; i < sonFiles.length; i++) {
					if (!sonFiles[i].isDirectory()) {
						files.add(sonFiles[i]);
					}
				}
			}
		}
		return files;
	}
	/**
	 * 获取目录下所有文件夹
	 * @param folder
	 * @return
	 */
	public static List<File> getFolders(String folder){
		File file=new File(folder);
		List<File> files=new ArrayList<File>();
		if (file.exists()) {
			File[] sonFiles=file.listFiles();
			if (sonFiles!=null && sonFiles.length>0) {
				for (int i = 0; i < sonFiles.length; i++) {
					if (sonFiles[i].isDirectory()) {
						files.add(sonFiles[i]);
					}
				}
			}
		}
		return files;
	}
	/**
	 * 判断是否有子目录
	 * @param folder
	 * @return
	 */
	public static boolean hasSonFolder(String folder){
		File file=new File(folder);
		return hasSonFolder(file);
	}
	/**
	 * 判断是否有子目录
	 * @param folder
	 * @return
	 */
	public static boolean hasSonFolder(File file){
		if (file.exists()) {
			File[] sonFiles=file.listFiles();
			if (sonFiles!=null && sonFiles.length>0) {
				for (int i = 0; i < sonFiles.length; i++) {
					if (sonFiles[i].isDirectory()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * 创建目录
	 * @param folder
	 */
	public static void mkdir(String folder){
		File file=new File(folder);
		if (!file.exists()) {
			file.mkdir();
		}
	}
	/**
	 * 复制文件
	 * @param src
	 * @param dst
	 */
	public static void copy(File src, File dst) {
		try {
			int BUFFER_SIZE = 32 * 1024;
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new FileInputStream(src);
				out = new FileOutputStream(dst);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count;
				while ((count = in.read(buffer)) != -1) {
					out.write(buffer, 0, count);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /**
     * 复制文件夹
     * @param sourceDir
     * @param targetDir
     * @throws IOException
     */
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
    	if (new File(sourceDir).exists()) {
            // 新建目标目录
        	File targetFolder=new File(targetDir);
        	if (!targetFolder.exists()) {
    			targetFolder.mkdirs();
    		}
            // 获取源文件夹当前下的文件或目录
            File[] file = (new File(sourceDir)).listFiles();
            for (int i = 0; i < file.length; i++) {
                if (file[i].isFile()) {
                    // 源文件
                    File sourceFile = file[i];
                    // 目标文件
                    File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                    copy(sourceFile, targetFile);
                }
                if (file[i].isDirectory()) {
                    // 准备复制的源文件夹
                    String dir1 = sourceDir + "/" + file[i].getName();
                    // 准备复制的目标文件夹
                    String dir2 = targetDir + "/" + file[i].getName();
                    copyDirectiory(dir1, dir2);
                }
            }
		}
    }

	/**
	 * 获取扩展名
	 */
	public static String getExt(File src){
		if (src!=null) {
			String name=src.getName();
			return name.substring(name.lastIndexOf("."), name.length());
		}
		return "";
	}
	/**
	 * 获取扩展名
	 */
	public static String getExt(String src){
		if (src!=null) {
			return src.substring(src.lastIndexOf("."), src.length());
		}
		return "";
	}
	/**
	 * 删除指定文件
	 * @param path
	 */
	public static void del(String path){
		File file=new File(path);
		deleteFile(file);
	}
	/**
	 * 递归删除文件夹下所有文件
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file.exists()) { //判断文件是否存在
			if (file.isFile()) { //判断是否是文件
				file.delete(); //delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { //否则如果它是一个目录
				File files[] = file.listFiles(); //声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { //遍历目录下所有的文件
					deleteFile(files[i]); //把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		}
	} 
	public static void upzip() throws Exception{
		File file = new File("D:\\test.zip");//压缩文件  
		ZipFile zipFile = new ZipFile(file);//实例化ZipFile，每一个zip压缩文件都可以表示为一个ZipFile  
		//实例化一个Zip压缩文件的ZipInputStream对象，可以利用该类的getNextEntry()方法依次拿到每一个ZipEntry对象  
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));  
		ZipEntry zipEntry = null;  
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {  
			String fileName = zipEntry.getName();  
			File temp = new File("D:\\un\\" + fileName);  
			temp.getParentFile().mkdirs();  
			OutputStream os = new FileOutputStream(temp);  
			//通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流  
			InputStream is = zipFile.getInputStream(zipEntry);  
			int len = 0;  
			while ((len = is.read()) != -1)  
				os.write(len);  
			os.close();  
			is.close();  
		}  
		zipInputStream.close();  
	}
	/**
     * 追加写入文件
     * @param path 文件绝对路径
     * @param text 要追加的文件内容
     * @return 
     */
    public static boolean appendText(String path,String text){
        try{
            File file=new File(path);
            BufferedWriter writer = null;
            try {
            	writer = new BufferedWriter(new FileWriter(file,true));
                writer.write(text);
            }
            finally {
            	if(writer != null)
            		writer.close();
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 系统的默认目录为当前用户目录，可通过此函数重新设置
     * @param basePath 
     */
    public static void setBasePath(String basePath) {
        Assert.notNull(basePath);
        
        if(!basePath.trim().endsWith(File.separator)){
            basePath+=File.separator;
        }
        FileUtil.basePath = basePath;
    }
   
    /**
     * 获取web根目录下面的资源的绝对路径
     * @param path 相对应WEB根目录的路径
     * @return 绝对路径
     */
    public static String getAbsolutePath(String path){
        Assert.notNull(path);
        //在windows下，如果路径包含：,为绝对路径，则不进行转换
        if(path.contains(":")){
            return path;
        }
        
        if(path!=null && path.trim().length()==1){
            return basePath;
        }
        if(path.startsWith("/")){
            path=path.substring(1);
        }
        path=basePath+path.replace("/", File.separator);
        return path;
    }
    
    public static void copyFile(File inFile, File outFile){
        try {
            copyFile(new FileInputStream(inFile),outFile);
        } catch (FileNotFoundException ex) {
        	ex.printStackTrace();
        }
    }
    
    public static void copyFile(InputStream in, File outFile){
        OutputStream out = null;
        try {
            byte[] data=readAll(in);
            out = new FileOutputStream(outFile);
            out.write(data, 0, data.length);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(in!=null){
                    in.close();
                }
            } catch (IOException ex) {
            	ex.printStackTrace();
            }
            try {
                if(out!=null){
                    out.close();
                }
            } catch (IOException ex) {
             	ex.printStackTrace();
            }
        }
    }
    
    public static byte[] readAll(File file){
        try {
            return readAll(new FileInputStream(file));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static byte[] readAll(InputStream in){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            for (int n ; (n = in.read(buffer))>0 ; ) {
                out.write(buffer, 0, n);
            }
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
        return out.toByteArray();
    }

    public static FileInputStream getInputStream(String path) {
        try {
            return new FileInputStream(getAbsolutePath(path));
        } catch (FileNotFoundException ex) {
        	ex.printStackTrace();
        }
        return null;
    }
   
    public static boolean existsFile(String path){
        try{
            File file=new File(getAbsolutePath(path));
            if(file.exists()){
                return true;
            }
        }catch(Exception ex){
        	ex.printStackTrace();
        }
        return false;
    }
    
    public static File createAndWriteFile(String path,byte[] data){
        try{
            File file=new File(path);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            OutputStream out = null;
            try {
            	out = new FileOutputStream(file);
                out.write(data, 0, data.length);
            }
            finally {
            	if(out != null)
            		out.close();
            }
            return file;
        }catch(Exception ex){
        	ex.printStackTrace();
        }
        return null;
    }
    
    public static File createAndWriteFile(String path,String text){
        try{
            File file=new File(path);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            BufferedWriter writer = null;
            try {
            	writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
                writer.write(text);
            }
            finally {
            	if(writer != null)
            		writer.close();
            }
            return file;
        }catch(Exception ex){
        	ex.printStackTrace();
        }
        return null;
    }
    
    public static boolean removeFile(String path){
        try{
            File file=new File(getAbsolutePath(path));
            if(file.exists()){
                file.delete();
            }
            return true;
        }catch(Exception ex){
        	ex.printStackTrace();
        }
        return false;
    }

    public static Collection<String> getTextFileContent(String path) {
        return getTextFileContent(getInputStream(path));
    }

    public static Collection<String> getTextFileContent(InputStream in) {
        Collection<String> result=new LinkedHashSet<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String line=reader.readLine();
            while(line!=null){
                //忽略空行和以#号开始的注释行
                if(!"".equals(line.trim()) && !line.trim().startsWith("#")){
                    result.add(line);
                }
                line=reader.readLine();
            }
        } catch (UnsupportedEncodingException ex) {
        	ex.printStackTrace();
        }  catch (IOException ex) {
        	ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
            	ex.printStackTrace();
            }
        }
        return result;
    }
    
    public static Collection<String> getClassPathTextFileContent(String path) {
        try {
            ClassPathResource cr = new ClassPathResource(path);
            return getTextFileContent(cr.getInputStream());
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
        return null;
    }
    
    public static Collection<File> listDictionary() {
        try {
            File file=new File(getAbsolutePath("/"));
            File[] files = file.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY);
            if(files == null || files.length == 0)
                return Collections.emptyList();
            return Arrays.asList(files);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public static String getFilename(final String filename) {
		if (filename == null) {
			return null;
		}
		final int lastUnixPos = filename.lastIndexOf(UNIX_SEPARATOR);
		final int lastWindowsPos = filename.lastIndexOf(WINDOWS_SEPARATOR);
		final int index = Math.max(lastUnixPos, lastWindowsPos);
		return filename.substring(index + 1);
	}

	public static String getFilenameExtension(final String filename) {
		final int index = filename.lastIndexOf('.');
		if (-1 == index) {
			return "";
		} else {
			return filename.substring(index + 1);
		}
	}

	public static String stripFilenameExtension(final String path) {
		if (path == null) {
			return null;
		}
		final int sepIndex = path.lastIndexOf(".");
		return (sepIndex != -1 ? path.substring(0, sepIndex) : path);
	}

	public static boolean createDirectoryRecursively(File directory) {
		if (directory == null) {
			return false;
		} else if (directory.exists()) {
			return !directory.isFile();
		} else if (!directory.isAbsolute()) {
			directory = new File(directory.getAbsolutePath());
		}
		final String parent = directory.getParent();
		if ((parent == null) || !createDirectoryRecursively(new File(parent))) {
			return false;
		}
		directory.mkdir();
		return directory.exists();
	}
    
    public static File createFile(final File file) throws IOException {
		if (!file.exists()) {
			createDirectoryRecursively(file.getParentFile());
			file.createNewFile();
		}
		return file;
	}
    
	public static long sizeOfDirectory(final File directory) {
		if (!directory.exists()) {
			return 0l;
		}

		if (!directory.isDirectory()) {
			return directory.length();
		}

		long size = 0;
		final File[] files = directory.listFiles();
		if (files == null) {
			return 0;
		}
		for (int i = 0; i < files.length; i++) {
			final File file = files[i];
			if (file.isDirectory()) {
				size += sizeOfDirectory(file);
			} else {
				size += file.length();
			}
		}
		return size;
	}

	public static void deleteAll(final File dir) throws IOException {
		deleteAll(dir, true);
	}

	public static void deleteAll(final File dir, final boolean self) throws IOException {
		if (!dir.exists()) {
			return;
		}
		if (!dir.isDirectory()) {
			dir.delete();
			return;
		}
		final String[] list = dir.list();
		if (list != null) {
			for (final String element : list) {
				final File child = new File(dir, element);
				deleteAll(child);
			}
		}
		if (self) {
			dir.delete();
		}
	}	
	public static void main(String[] args) {
		try {
			upzip();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
