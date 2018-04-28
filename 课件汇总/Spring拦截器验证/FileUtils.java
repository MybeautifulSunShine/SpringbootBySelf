package cn.mldn.util.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * 本类的主要功能是进行文件名称的创建以及文件信息的保存
 * @author mldn
 */
public class FileUtils {
	private MultipartFile multipartFile ;	// 用于进行上传文件的操作类对象
	/**
	 * 实例化FileUtils程序类
	 * @param multipartFile 所有的上传文件信息
	 */
	public FileUtils(MultipartFile multipartFile) {
		this.multipartFile = multipartFile ;
	}
	/**
	 * 如果要进行文件的上传保存， 那么必须知道文件的存放父目录
	 * 必须保证传入的保存路径存在
	 * @param request request请求对象利用此对象获得ServletContext
	 * @param parentDir 保存的目录，例如：“upload/emp/”；
	 * @param fileName 要保存的文件名称
	 * @return 成功返回true
	 */
	public boolean saveFile(HttpServletRequest request,String dir,String fileName) {
		boolean flag = false ;
		String parentDir = request.getServletContext().getRealPath("/WEB-INF/" + dir) ;
		File outFile = new File(parentDir,fileName) ;
		OutputStream output = null ;
		try {
			output = new FileOutputStream(outFile) ;
			InputStream input = this.multipartFile.getInputStream() ;
			int len = 0 ;	// 保存每次的读取数据长度
			byte [] data = new byte [2048] ;
			while ((len = input.read(data)) != -1) {
				output.write(data, 0, len); ;
			}
			flag = true ;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false ;
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		return flag ;
	}
	/**
	 * 获取新的文件名称，该名称一定用于信息的保存
	 * @return 文件名称
	 */
	public String createFileName() {
		return UUID.randomUUID() + "." + this.multipartFile.getContentType()
				.substring(this.multipartFile.getContentType().lastIndexOf("/") + 1);
	}
}
